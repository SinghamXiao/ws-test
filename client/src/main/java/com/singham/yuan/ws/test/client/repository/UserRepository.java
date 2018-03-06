package com.singham.yuan.ws.test.client.repository;

import com.singham.yuan.ws.test.client.model.db.User;

public interface UserRepository {

    void save(User user);

    User getByID(Long id);

    User getByName(String name);

}
