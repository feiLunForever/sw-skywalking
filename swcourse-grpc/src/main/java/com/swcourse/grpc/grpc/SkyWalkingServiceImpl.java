package com.swcourse.grpc.grpc;


import com.swcourse.grpc.protobuf.SkyWalkingServiceApi;
import com.swcourse.grpc.protobuf.SkyWalkingServiceGrpc;
import io.grpc.stub.StreamObserver;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SkyWalkingServiceImpl extends SkyWalkingServiceGrpc.SkyWalkingServiceImplBase {
    @Override
    public void helloSkyWalking(SkyWalkingServiceApi.SkyWalkingRequest request, StreamObserver<SkyWalkingServiceApi.SkyWalkingResponse> responseObserver) {
        //请求结果，我们定义的
        SkyWalkingServiceApi.SkyWalkingResponse skyWalkingResponse = null;
        String word = request.getWord();
        System.out.println("Service 实现类接受客户端的参数" + word);
        try {
            // 定义响应,是一个builder构造器.
            skyWalkingResponse = SkyWalkingServiceApi.SkyWalkingResponse.newBuilder()
                    .setWord("实现类返回参数:9999")
                    .build();
        } catch (Exception e) {
            responseObserver.onError(e);
        } finally {
            responseObserver.onNext(skyWalkingResponse);
        }
        responseObserver.onCompleted();
    }
}