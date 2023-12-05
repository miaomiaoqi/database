package com.miaoqi.mybatis.proxy.service.impl;

import com.miaoqi.mybatis.proxy.service.IProductService;

public class ProductServiceImpl implements IProductService {

    @Override
    public String getProductInfo() {
        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "product info";
    }
}
