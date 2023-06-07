package com.swcourse.grpc.grpc;

import com.swcourse.grpc.protobuf.SkyWalkingServiceApi;
import com.swcourse.grpc.protobuf.SkyWalkingServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-04-08 16:55
 * @since 0.1.0
 **/
public class GRPCClient {
    private static final String host = "localhost";
    private static final int serverPort = 9999;

    public static void main(String[] args) {
        //获取channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(host, serverPort).
                usePlaintext().build();
        try {
            //2.拿到stub对象
            SkyWalkingServiceGrpc.SkyWalkingServiceBlockingStub skyWalkingService = SkyWalkingServiceGrpc.newBlockingStub(channel);
            SkyWalkingServiceApi.SkyWalkingRequest request = SkyWalkingServiceApi.SkyWalkingRequest.newBuilder()
                    .setWord("hello skywalking")
                    .build();
            SkyWalkingServiceApi.SkyWalkingResponse response = skyWalkingService.helloSkyWalking(request);
            System.out.println(response.getWord());
        } finally {
            // 5.关闭channel, 释放资源.
            channel.shutdown();
        }
    }
}
