package com.xukui.library.domainadapter.bean;

import android.support.annotation.NonNull;

import java.io.Serializable;

public class KeyValue implements Serializable, Cloneable {

    private String key;
    private String value;

    public KeyValue(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public KeyValue() {
    }

    @Override
    public KeyValue clone() throws CloneNotSupportedException {
        return (KeyValue) super.clone();
    }

    @NonNull
    @Override
    public String toString() {
        return super.toString() + "-> (" + this.key + ", " + this.value + ")";
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}