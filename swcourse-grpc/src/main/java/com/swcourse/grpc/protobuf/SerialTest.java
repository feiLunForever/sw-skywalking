package com.swcourse.grpc.protobuf;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author your name or email
 * @version 0.1.0
 * @create 2022-04-08 15:10
 * @since 0.1.0
 **/
public class SerialTest {
    public static void main(String[] args) {
        try {
            // 开始生成 SwcoursePerson 对象
            SwcoursePersons.SwcoursePerson.Builder personBuilder = SwcoursePersons.SwcoursePerson.newBuilder();
            // person 赋值
            personBuilder.setName("车辙");
            personBuilder.setId(1);
            // PhoneNumber 赋值
            SwcoursePersons.SwcoursePerson.PhoneNumber.Builder phoneNumberBuilder = SwcoursePersons.SwcoursePerson.PhoneNumber.newBuilder();
            phoneNumberBuilder.setType(SwcoursePersons.SwcoursePerson.PhoneNumber.PhoneType.MOBILE);
            phoneNumberBuilder.setNumber("18312913807");
            // person 设置 PhoneNumber
            personBuilder.addPhone(phoneNumberBuilder);
            // 生成对象
            SwcoursePersons.SwcoursePerson swcoursePerson = personBuilder.build();

            // 序列化和反序列化开始
            // 序列化
            byte[] bytes = swcoursePerson.toByteArray();
            // 反序列化
            SwcoursePersons.SwcoursePerson personResult = SwcoursePersons.SwcoursePerson.parseFrom(bytes);
            System.out.println(String.format("反序列化得到的信息，姓名：%s，手机号：%s", personResult.getName(), personResult.getPhone(0).getNumber()));


            // 方式二 ByteString：
            // 序列化
            ByteString byteString = swcoursePerson.toByteString();
            System.out.println(byteString.toString());
            // 反序列化
            SwcoursePersons.SwcoursePerson personResult2 = SwcoursePersons.SwcoursePerson.parseFrom(byteString);
            System.out.println(String.format("反序列化得到的信息，姓名：%s，手机号：%s", personResult2.getName(), personResult2.getPhone(0).getNumber()));


            // 方式三 InputStream
            // 将一个或者多个 protobuf 对象字节写入 stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            swcoursePerson.writeDelimitedTo(byteArrayOutputStream);
            // 反序列化，从 steam 中读取一个或者多个 protobuf 字节对象
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            SwcoursePersons.SwcoursePerson personResult3 = SwcoursePersons.SwcoursePerson.parseDelimitedFrom(byteArrayInputStream);
            System.out.println(String.format("反序列化得到的信息，姓名：%s，手机号：%s", personResult3.getName(), personResult3.getPhone(0).getNumber()));

        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
