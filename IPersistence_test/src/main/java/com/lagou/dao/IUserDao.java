package com.lagou.dao;

import com.lagou.pojo.User;

import java.util.List;

public interface IUserDao {

    List<User> findAll() throws Exception;

    User findByCondition(User user) throws Exception;

    void updateUserNameById(User user) throws Exception;

    void deleteUserById(User user) throws Exception;

}
