package com.miaoqi.mapper.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.miaoqi.mapper.entities.User;
import com.miaoqi.mapper.mappers.UserMapper;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void saveUser(User user) {
        userMapper.insert(user);
    }

    public User getUserById(Integer userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

}
