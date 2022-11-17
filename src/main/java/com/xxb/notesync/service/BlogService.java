package com.xxb.notesync.service;

import com.xxb.notesync.dao.BlogCreate;
import com.xxb.notesync.dao.BlogUpdate;
import com.xxb.notesync.dao.BlogView;
import com.xxb.notesync.utls.IdResponse;
import org.springframework.stereotype.Service;


public interface BlogService {
    IdResponse create(BlogCreate create);

    BlogView get(String id);

    IdResponse update(String id, BlogUpdate blogUpdate);

    IdResponse delete(String id);
}
