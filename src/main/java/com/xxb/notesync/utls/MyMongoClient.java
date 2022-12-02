package com.xxb.notesync.utls;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;

@Configuration
public class MyMongoClient {
    @Value("${mongodb.host}")
    private String host;

    @Value("${mongodb.port}")
    private int port;

    @Value("${mongodb.dbname}")
    private String dbname;

    public static Jongo jongo;
    private static DB db;
    @Bean
    public Jongo init(){
        db = new MongoClient(host, port).getDB(dbname);
        jongo = new Jongo(db);
        return jongo;
    }
}
