package com.simbirsoft.sailserver.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DAOResponce {

    private String name;

    private String value;

    private List<String> orderlist = new ArrayList<>();

    private Map<String, String> content = new HashMap<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getOrderlist() {
        return orderlist;
    }

    public void setOrderlist(List<String> orderlist) {
        this.orderlist = orderlist;
    }

    public Map<String, String> getContent() {
        return content;
    }

    public void setContent(Map<String, String> content) {
        this.content = content;
    }
}
