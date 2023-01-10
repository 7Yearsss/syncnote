package com.xxb.notesync.service;

import com.xxb.notesync.dao.*;
import com.xxb.notesync.entity.BlogEntity;
import com.xxb.notesync.utls.IdResponse;
import org.springframework.stereotype.Service;

import java.util.List;


public interface BlogService {
    IdResponse create(BlogCreate create);

    BlogView get(String id);

    IdResponse update(String id, BlogUpdate blogUpdate);

    List<BlogEntity> search(BlogSearch blogSearch);

    BlogPagingResult searchByPaging(BlogSearch blogSearch);
}
