package com.xxb.notesync.service.impl;

import com.xxb.notesync.dao.BlogCreate;
import com.xxb.notesync.dao.BlogView;
import com.xxb.notesync.entity.BlogEntity;
import com.xxb.notesync.service.BlogService;
import com.xxb.notesync.utls.IdResponse;
import com.xxb.notesync.utls.Instances;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private MongoTemplate mongoTemplate;

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
    public IdResponse update(String id) {

        Instances.copy();
    }
}
