package com.xxb.notesync.service.impl;

import com.mongodb.client.MongoClient;
import com.xxb.notesync.dao.BlogCreate;
import com.xxb.notesync.dao.BlogUpdate;
import com.xxb.notesync.dao.BlogView;
import com.xxb.notesync.entity.BlogEntity;
import com.xxb.notesync.service.BlogService;
import com.xxb.notesync.utls.IdResponse;
import com.xxb.notesync.utls.Instances;
import com.xxb.notesync.utls.MongoCollection;
import com.xxb.notesync.utls.MongoDBUtil;
import io.github.classgraph.json.Id;
import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Autowired
    private MongoCollection<BlogEntity> blogEntityMongoCollection;



    @Override
    public IdResponse create(BlogCreate create) {
        BlogEntity blogEntity =new BlogEntity();
        Instances.copy(create, blogEntity);
        mongoTemplate.insert(blogEntity);
        return IdResponse.build(blogEntity.id);
    }

    @Override
    public BlogView get(String id) {
       BlogView blogView=new BlogView();
       Instances.copy(getEntity(id),blogView);
       return blogView;
    }
    private BlogEntity getEntity(String id){
        return mongoTemplate.findById(id, BlogEntity.class);
    }

    @Override
    public IdResponse update(String id, BlogUpdate blogUpdate) {
        BlogEntity blogEntity=getEntity(id);
        Instances.copy(blogUpdate,blogEntity);
        MongoDBUtil.updateById(blogEntity);
        return IdResponse.build(id);
    }

    @Override
    public IdResponse delete(String id) {
        Query query=new Query(new Criteria().and("id").is(id));
        mongoTemplate.remove(query,BlogEntity.class);
        return IdResponse.build(id);
    }
}
