package com.miaoqi.mybatis.proxy.handler;

import com.miaoqi.mybatis.proxy.service.IProductService;
import com.miaoqi.mybatis.proxy.service.IUserService;
import com.miaoqi.mybatis.proxy.service.impl.ProductServiceImpl;
import com.miaoqi.mybatis.proxy.service.impl.UserServiceImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 横切面类, 统计方法访问时间
 *
 * @author miaoqi
 * @date 2019-07-26
 */
public class RequestCostInvocationHandler implements InvocationHandler {

    private Object target;

    public RequestCostInvocationHandler(Object target) {
        this.target = target;
    }

    /** 被代理对象的任何方法被执行时，都会先进入这个方法 */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 执行目标对象的方法
        method.invoke(this.target, args);
        System.out.println("reques cost :" + (System.currentTimeMillis() - startTime));
        return null;
    }

    public static void main(String[] args) {
        // 3个参数解释如下
        // classloader,生成代理类
        // 代理类应该实现的接口
        // 实现InvocationHandler的切面类
        IUserService userService = (IUserService) Proxy.newProxyInstance(IUserService.class.getClassLoader(),
                new Class[]{IUserService.class}, new RequestCostInvocationHandler(new UserServiceImpl()));

        IProductService productService = (IProductService) Proxy.newProxyInstance(
                IProductService.class.getClassLoader(),
                new Class[]{IProductService.class}, new RequestCostInvocationHandler(new ProductServiceImpl()));

        // this is userService
        // reques cost :0
        userService.getUserInfo();

        // this is productService
        // reques cost :0
        productService.getProductInfo();
    }
}