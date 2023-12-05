package com.miaoqi.mybatis.plugin.service.impl;

import com.miaoqi.mybatis.plugin.service.IGetStrService;

public class GetStrServiceImpl implements IGetStrService {
    @Override
    public String getStrZero() {
        return "0";
    }

    @Override
    public String getStrOne() {
        return "1";
    }
}
