package com.swcourse.bytebuddy.project;

import java.nio.charset.Charset;

/**
 * @author zhangyuqiang
 * @version 1.0.0
 * @des 字符串加密策略
 * @create 2022-05-13 17:24
 **/
public interface StrEncryptAlgorithmStrategy {

    String encrypt(String source, Charset charset, String secretKey);

    String decrypt(String source, Charset charset, String secretKey);

    byte[] encrypt(byte[] source, String secretKey);

    byte[] decrypt(byte[] source, String secretKey);

}
