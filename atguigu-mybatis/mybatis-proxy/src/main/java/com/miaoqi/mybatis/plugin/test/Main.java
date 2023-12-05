package com.miaoqi.mybatis.plugin.test;

import com.miaoqi.mybatis.plugin.container.InterceptorChain;
import com.miaoqi.mybatis.plugin.interceptor.FirstInterceptor;
import com.miaoqi.mybatis.plugin.interceptor.SecondInterceptor;
import com.miaoqi.mybatis.plugin.service.IGetStrService;
import com.miaoqi.mybatis.plugin.service.impl.GetStrServiceImpl;

public class Main {

    public static void main(String[] args) {

        // 配置插件
        InterceptorChain interceptorChain = new InterceptorChain();
        interceptorChain.addInterceptor(new FirstInterceptor());
        interceptorChain.addInterceptor(new SecondInterceptor());

        // 获得代理对象
        IGetStrService getStr = new GetStrServiceImpl();
        getStr = (IGetStrService) interceptorChain.pluginAll(getStr);

        String result = getStr.getStrZero();
        // plugin2 plugin1 0 plugin1 plugin2
        System.out.println(result);

        result = getStr.getStrOne();
        // 1
        System.out.println(result);
    }
}