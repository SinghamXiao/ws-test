package com.singham.yuan.ws.test.client;

import org.springframework.util.Assert;

public class User {

    private String name;

    private Integer age;

    public User() {
    }

    public User(String name) {
        Assert.notNull(name, "name must not be null");
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        age = 5;
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
