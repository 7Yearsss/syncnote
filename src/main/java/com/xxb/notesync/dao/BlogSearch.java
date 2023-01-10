package com.xxb.notesync.dao;

import com.xxb.notesync.annotation.Operation;
import com.xxb.notesync.utls.PagingRequest;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
@XmlAccessorType(XmlAccessType.FIELD)
@Document(collection = "blog")
public class BlogSearch extends PagingRequest {
    @Operation(operate = "$eq",field = "title")
    @XmlElement(name = "title")
    public String title;
    @Operation(operate = "$in",field = "content")
    @XmlElement(name = "content")
    public String content;
}
