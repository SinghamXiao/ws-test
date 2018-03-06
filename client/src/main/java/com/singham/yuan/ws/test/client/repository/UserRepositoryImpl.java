package com.singham.yuan.ws.test.client.repository;

import com.singham.yuan.ws.test.client.model.db.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Repository
public class UserRepositoryImpl extends BaseRepository<User> implements UserRepository {

    @Override
    public void save(User user) {
        super.save(user);
    }

    @Override
    public User getByID(Long id) {
        return load(id);
    }

    @Override
    public User getByName(String name) {
        return load("name", name);
    }

}
