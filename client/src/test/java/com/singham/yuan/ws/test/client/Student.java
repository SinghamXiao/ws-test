package com.singham.yuan.ws.test.client;

public class Student {

    private String id;

    private User user1;

    public Student() {
    }

    public Student(String id, User user1) {
        this.id = id;
        this.user1 = user1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

}
