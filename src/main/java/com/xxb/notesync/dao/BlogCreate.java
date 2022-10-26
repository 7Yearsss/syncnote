package com.xxb.notesync.dao;

import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class BlogCreate {

    @XmlElement(name = "title")
    public String title;

    @XmlElement(name = "desci")
    public String desci;

    @XmlElement(name = "content")
    public String content;

    @XmlElement(name = "pic")
    public String pic;

    @XmlElement(name = "updateBy")
    public String updateBy;

    @XmlElement(name = "updateWhen")
    public LocalDateTime updateWhen;

    @XmlElement(name = "createdBy")
    public String createdBy;

    @XmlElement(name = "createdWhen")
    public LocalDateTime createdWhen;
}
