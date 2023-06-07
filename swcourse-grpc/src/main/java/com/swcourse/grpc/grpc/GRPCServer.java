package com.swcourse.grpc.grpc;

import io.grpc.Server;
import io.grpc.ServerBuilder;

import java.io.IOException;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-04-08 16:54
 * @since 0.1.0
 **/
public class GRPCServer {
    private static final int port = 9999;

    public static void main(String[] args) throws IOException, InterruptedException {
        //设置service端口
        Server server = ServerBuilder.forPort(port)
                .addService(new SkyWalkingServiceImpl())
                .build().start();
        System.out.println(String.format("服务端启动成功, 端口: %d.", port));
        server.awaitTermination();
    }
}
