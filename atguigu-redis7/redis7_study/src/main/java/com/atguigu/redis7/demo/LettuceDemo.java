package com.atguigu.redis7.demo;


/**
 * @auther zzyy
 * @create 2022-12-13 20:39
 */
public class LettuceDemo {

    public static void main(String[] args) {
        // // 1 使用构建器链式编程来builder我们RedisURI
        // RedisURI uri = RedisURI.builder().redis("127.0.0.1").withPort(6379).withAuthentication("default", "111111").build();
        //
        // //2 创建连接客户端
        // RedisClient redisClient = RedisClient.create(uri);
        // StatefulRedisConnection conn = redisClient.connect();
        //
        // //3 通过conn创建操作的command
        // RedisCommands commands = conn.sync();
        //
        // //========biz====================
        // //keys
        // List keys = commands.keys("*");
        // System.out.println("***********" + keys);
        //
        // //string
        // commands.set("k5", "hello-lettuce");
        // System.out.println("***********" + commands.get("k5"));
        // //....
        //
        // //========biz====================
        //
        // //4 各种关闭释放资源
        // conn.close();
        // redisClient.shutdown();
    }

}
