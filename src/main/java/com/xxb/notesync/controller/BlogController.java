package com.xxb.notesync.controller;

import com.xxb.notesync.dao.BlogCreate;
import com.xxb.notesync.dao.BlogView;
import com.xxb.notesync.service.BlogService;
import com.xxb.notesync.utls.IdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


@RestController
class BlogController {
    @Resource
    BlogService blogService;

    @PostMapping("/blog")
    public IdResponse create(@RequestBody BlogCreate create){
        return blogService.create(create);
    }

    @GetMapping("/blog/{id}")
    public BlogView get(@PathVariable("id") String id){
        return blogService.get(id);
    }

    @PutMapping("/blog/{id}")
    public IdResponse update(@PathVariable("id")String id){
        return blogService.update(id);
    }

}