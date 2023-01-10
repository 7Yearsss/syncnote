package com.xxb.notesync.controller;

import com.xxb.notesync.dao.*;
import com.xxb.notesync.entity.BlogEntity;
import com.xxb.notesync.service.BlogService;
import com.xxb.notesync.utls.IdResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;


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
    public IdResponse update(@PathVariable("id")String id,@RequestBody BlogUpdate update){
        return blogService.update(id,update);
    }

    @PostMapping("/blog/search")
    public List<BlogEntity> search(@RequestBody BlogSearch blogSearch){
        return blogService.search(blogSearch);
    }

    @PostMapping("/blog/search-by-paging")
    public BlogPagingResult searchByPaging(@RequestBody BlogSearch blogSearch) {

        return blogService.searchByPaging(blogSearch);
    }

}