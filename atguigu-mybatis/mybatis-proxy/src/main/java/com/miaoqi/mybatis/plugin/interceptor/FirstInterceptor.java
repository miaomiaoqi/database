package com.miaoqi.mybatis.plugin.interceptor;

import com.miaoqi.mybatis.plugin.util.Plugin;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.util.Properties;


public class FirstInterceptor implements Interceptor {

    /** 执行拦截逻辑的方法 */
    @Override
    public Object intercept(Invocation invocation) {
        try {
            return "plugin1 " + invocation.proceed() + " plugin1";
        } catch (Exception e) {
            return null;
        }
    }

    /** 为原先的类生成代理对象 */
    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }
}