package com.xxb.notesync.utls;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class IdResponse<T> {

    @XmlElement(name = "id")
    public String id;

    public static <T> IdResponse<T> build(T id) {
        IdResponse<T> response = new IdResponse<>();
        if (id == null) response.id = null;
        else response.id = String.valueOf(id);
        return response;
    }

    public void set(T id) {
        this.id = String.valueOf(id);
    }
}