package com.miaoqi.mybatis.plugin.interceptor;

import com.miaoqi.mybatis.plugin.util.Plugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;

public class SecondInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) {
        try {
            return "plugin2 " + invocation.proceed() + " plugin2";
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}