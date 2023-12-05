package com.miaoqi.mybatis.dao;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;

/**
 * 完成插件签名
 *      告诉MyBatis当前插件用来拦截哪个对象的哪个方法
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "parameterize", args = java.sql.Statement.class)})
public class MySecondPlugin implements Interceptor {

    /**
     * intercept: 拦截
     *      拦截目标对象的目标方法的执行
     */
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        System.out.println("MySecondPlugin...intercept: " + invocation.getMethod());
        // 执行目标方法
        Object proceed = invocation.proceed();
        // 返回执行后的返回值
        return proceed;
    }

    /**
     * plugin: 插件
     *      包装目标对象, 为目标对象创建一个代理对象
     */
    @Override
    public Object plugin(Object target) {
        // 我们可以借助Plugin的wrap方法来使用当前Interceptor包装我们的目标对象
        System.out.println("MySecondPlugin...plugin: " + target);
        Object wrap = Plugin.wrap(target, this);
        // 返回当前target动态代理对象
        return wrap;
    }

    /**
     * setProperties:
     *      将插件注册时的property属性设置进来
     */
    @Override
    public void setProperties(Properties properties) {
        // TODO Auto-generated method stub
        System.out.println("插件配置信息");
    }

}
