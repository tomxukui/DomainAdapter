package com.xukui.library.domainadapter.bean;

import java.io.Serializable;
import java.util.Map;

public class DomainTemplate implements Serializable {

    private String name;
    private Map<String, String> map;

    public DomainTemplate(String name, Map<String, String> map) {
        this.name = name;
        this.map = map;
    }

    public DomainTemplate() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

}