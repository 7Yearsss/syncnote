package com.xxb.notesync.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collation = "blog")
@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BlogEntity implements Serializable {

    @Id
    public String id;

    @Field(name = "title")
    public String title;

    @Field(name = "desci")
    public String desci;

    @Field(name = "content")
    public String content;

    @Field(name = "pic")
    public String pic;

    @Field(name = "updateBy")
    public String updateBy;

    @Field(name = "updateWhen")
    public LocalDateTime updateWhen;

    @Field(name = "createdBy")
    public String createdBy;

    @Field(name = "createdWhen")
    public LocalDateTime createdWhen;





}
