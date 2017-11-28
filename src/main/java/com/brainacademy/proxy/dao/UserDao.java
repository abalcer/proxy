package com.brainacademy.proxy.dao;

import com.brainacademy.proxy.dao.annotaion.Query;
import com.brainacademy.proxy.model.User;

import java.util.List;

public interface UserDao {

    @Query("from User where name=? and address=?")
    List<User> findUsers(String name, String address);

    List<User> findByName(String name);

    List<User> findByAddress(String address);

    List<User> findAll();
}
