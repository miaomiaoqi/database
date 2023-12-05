package com.miaoqi.mybatis.plugin.util;

import com.miaoqi.mybatis.plugin.service.IGetStrService;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Plugin implements InvocationHandler {

    /** 目标对象 */
    private final Object target;
    /** Interceptor对象 */
    private final Interceptor interceptor;

    public Plugin(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    /** 生成代理对象 */
    public static Object wrap(Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                new Class[]{IGetStrService.class},
                new Plugin(target, interceptor));
    }

    /** 被代理对象的方法执行时，这个方法会被执行 */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 只为方法getStrZero生成代理对象
        if (method.getName().equals("getStrZero")) {
            return this.interceptor.intercept(new Invocation(this.target, method, args));
        }
        return method.invoke(this.target, args);
    }
}