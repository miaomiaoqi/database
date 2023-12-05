package com.miaoqi.mybatis.proxy.service.impl;

import com.miaoqi.mybatis.proxy.service.IUserService;

public class UserServiceImpl implements IUserService {
    @Override
    public String getUserInfo() {
        try {
            Thread.sleep(1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "user info";
    }
}
