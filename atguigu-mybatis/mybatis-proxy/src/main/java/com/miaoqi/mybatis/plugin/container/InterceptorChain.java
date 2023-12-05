package com.miaoqi.mybatis.plugin.container;

import org.apache.ibatis.plugin.Interceptor;

import java.util.ArrayList;
import java.util.List;

public class InterceptorChain {

    /** 放拦截器 */
    private final List<Interceptor> interceptors = new ArrayList<Interceptor>();

    public Object pluginAll(Object target) {
        for (Interceptor interceptor : this.interceptors) {
            target = interceptor.plugin(target);
        }
        return target;
    }

    public void addInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
    }
}