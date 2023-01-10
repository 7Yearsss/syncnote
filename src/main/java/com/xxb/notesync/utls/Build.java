package com.xxb.notesync.utls;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mongodb.BasicDBObject;
import com.mongodb.ConnectionString;
import com.mongodb.DBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.UpdateResult;
import com.xxb.notesync.annotation.Operation;
import com.xxb.notesync.entity.BlogEntity;
import lombok.SneakyThrows;
import org.bson.conversions.Bson;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.BasicUpdate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Build<T> {

    public static <T> List<Bson> buildCriteria(T search) {
        ArrayList<Bson> bsonArrayList = new ArrayList<>();
        Field[] declaredFields = search.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            declaredField.setAccessible(true);
            Object o = null;
            try {
                o = declaredField.get(search);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if (o == null) continue;
            Operation operation = declaredField.getAnnotation(Operation.class);
            bsonArrayList.add(switchOperation(operation.operate(), operation.field(), o));
        }
        return bsonArrayList;
    }


    public static <T, R> List<R> buildSearch(T search, R toEntity, MongoTemplate template) {
        List<Bson> bsonArrayList = buildCriteria(search);

        String collection = toEntity.getClass().getAnnotation(Document.class).collection();
        Bson searchBson = Filters.and(bsonArrayList);
        FindIterable<org.bson.Document> iterable = template.getCollection(collection).find(searchBson);
        List<R> list = new ArrayList<>();
        MongoCursor<org.bson.Document> cursor = iterable.cursor();
        while (cursor.hasNext()) {
            list.add((R) JSON.parseObject(cursor.next().toJson(), toEntity.getClass()));
        }
        return list;
    }

    @SneakyThrows
    public static <T extends PagingRequest, R extends PagingResult> R buildPagingSearch(T search, R pagingResult, List<?> list, MongoTemplate template) {
        List<Bson> bsonArrayList = buildCriteria(search);

        String collection = search.getClass().getAnnotation(Document.class).collection();
        Bson searchBson = Filters.and(bsonArrayList);
        FindIterable<org.bson.Document> iterable = template.getCollection(collection).find(searchBson);
        long count = template.getCollection(collection).countDocuments(searchBson);
        iterable.skip((search.paging.pageNo - 1) * search.paging.limit);
        iterable.limit(search.paging.limit);
        //get the entity in the list
        Field declaredField = pagingResult.getClass().getDeclaredFields()[0];
        String typeName = ((ParameterizedTypeImpl) declaredField.getGenericType()).getActualTypeArguments()[0].getTypeName();
        Class<?> aClass = Class.forName(typeName);

        MongoCursor<org.bson.Document> cursor = iterable.cursor();
        list = new ArrayList<>();
        while (cursor.hasNext()) {
            //remove the $oid that auto generate in mongodb
            org.bson.Document document = cursor.next();
            document.put("_id", document.get("_id").toString());
            list.add(JSON.parseObject(document.toJson(), (Type) aClass));
        }
        declaredField.set(pagingResult, list);
        pagingResult.paging = new PagingResultParam();
        pagingResult.paging.pageNo = (long) search.paging.pageNo;
        pagingResult.paging.limit = (long) search.paging.limit;
        pagingResult.paging.totalCount = count;
        //take up the whole page if the last page is not full
        if (count % search.paging.limit == 0) {
            pagingResult.paging.totalPage = count / search.paging.limit;
        } else {
            pagingResult.paging.totalPage = count / search.paging.limit + 1;
        }
        return pagingResult;
    }

    private static Bson switchOperation(String op, String filed, Object o) {
        Bson bson = Filters.empty();
        switch (op) {
            case "$in":
                bson = Filters.in(filed, o);
                break;
            case "$eq":
                bson = Filters.eq(filed, o);
                break;
            case "$regex":
                bson = Filters.regex(filed, o.toString());
        }
        return bson;
    }
    private static <T> Serializable getIdValue(T entity) {
        try {
            Field field = entity.getClass().getDeclaredField("id");
            field.setAccessible(true);
            return (Serializable) field.get(entity);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
    private static Query idEqQuery(Serializable id) {
        Criteria criteria = Criteria.where("id").is(id);
        return Query.query(criteria);
    }
    public static <T> boolean buildUpdate(T entity,MongoTemplate mongoTemplate){
        JSONObject obj = (JSONObject) JSONObject.toJSON(entity);
        DBObject update = new BasicDBObject();
        update.put("$set", obj);
        UpdateResult result = mongoTemplate.updateFirst(idEqQuery(getIdValue(entity)), new BasicUpdate(update.toString()), entity.getClass());
        return result.getModifiedCount() == 1L;
    }

}
