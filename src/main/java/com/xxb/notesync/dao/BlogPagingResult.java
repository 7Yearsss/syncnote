package com.xxb.notesync.dao;

import com.xxb.notesync.entity.BlogEntity;
import com.xxb.notesync.utls.PagingResult;

import javax.xml.bind.annotation.XmlElement;
import java.util.List;
//目前分页result类中只支持放一个list字段
public class BlogPagingResult extends PagingResult {
    @XmlElement(name = "blogEntityList")
    public List<BlogEntity> blogEntityList;
}
