package com.xxb.notesync.utls;

import com.mongodb.DBCollection;
import com.mongodb.ReadConcern;
import com.mongodb.ReadPreference;
import com.mongodb.WriteConcern;
import org.jongo.Jongo;
import org.jongo.Mapper;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.mapping.Document;

@Configuration
public class MongoCollection<T> {

    ApplicationContext context = new AnnotationConfigApplicationContext(MyMongoClient.class);
    Class<T> entityClass;
    public static org.jongo.MongoCollection collection;

    public MongoCollection(){

        Jongo jongo = context.getBean(Jongo.class);
        String collectionName = entityClass.getDeclaredAnnotation(Document.class).value();
        collection = jongo.getCollection(collectionName);
    }
    public org.jongo.MongoCollection getCollection(){
        return collection;
    }

}
