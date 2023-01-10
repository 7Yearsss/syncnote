package com.xxb.notesync.service.impl;


import com.mongodb.client.FindIterable;
import com.xxb.notesync.dao.*;
import com.xxb.notesync.entity.BlogEntity;
import com.xxb.notesync.service.BlogService;
import com.xxb.notesync.utls.Build;
import com.xxb.notesync.utls.IdResponse;
import com.xxb.notesync.utls.Instances;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.BeanUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.xxb.notesync.utls.Build.*;

@Service
public class BlogServiceImpl implements BlogService {

    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public IdResponse create(BlogCreate create) {
        BlogEntity blogEntity = new BlogEntity();
        Instances.copy(create, blogEntity);
        mongoTemplate.insert(blogEntity);
        return IdResponse.build(blogEntity.id);
    }

    @Override
    public BlogView get(String id) {
        BlogView blogView = new BlogView();
        Instances.copy(getEntity(id), blogView);
        return blogView;
    }

    private BlogEntity getEntity(String id) {
//        return blogRepository.findById(id).orElseThrow(Error::new);
//
        return mongoTemplate.findById(id, BlogEntity.class);
    }

    @Override
    public IdResponse update(String id, BlogUpdate blogUpdate) {
        BlogEntity entity = getEntity(id);
        Instances.copy(blogUpdate, entity);
        buildUpdate(entity, mongoTemplate);
        return IdResponse.build(id);
    }

    @Override
    public List<BlogEntity> search(BlogSearch blogSearch) {
        List<BlogEntity> blogEntities = buildSearch(blogSearch, new BlogEntity(), mongoTemplate);
        return blogEntities;
    }

    @Override
    public BlogPagingResult searchByPaging(BlogSearch blogSearch) {
        BlogPagingResult blogPagingResult = new BlogPagingResult();
        return buildPagingSearch(blogSearch, blogPagingResult, blogPagingResult.blogEntityList, mongoTemplate);
    }
}
