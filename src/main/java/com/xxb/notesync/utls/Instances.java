package com.xxb.notesync.utls;

import java.lang.reflect.Field;
import java.util.Objects;

public class Instances {


    public static void copy(Object source,Object target) {
        Class<?> sourceClass= null;
        Class<?> targetClass=null;
        try {
            sourceClass = Class.forName(source.getClass().getName());
            targetClass= Class.forName(target.getClass().getName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        Field[] sourceFields=sourceClass.getFields();
        Field[] targetFields=targetClass.getFields();
        for(Field sourceField : sourceFields){
            sourceField.setAccessible(true);
            Object value= null;
            try {
                value = sourceField.get(source);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            if(Objects.isNull(value)) continue;
            for(Field targetField: targetFields){
                targetField.setAccessible(true);
                if(targetField.getName().equals(sourceField.getName())){
                    try {
                        targetField.set(target,value);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
