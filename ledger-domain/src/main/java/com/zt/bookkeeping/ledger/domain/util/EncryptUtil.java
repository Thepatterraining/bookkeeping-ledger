package com.zt.bookkeeping.ledger.domain.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author thepatter
 */
public class EncryptUtil {

    public static String sha256(String inputString) {
        try {
            // 获取 MessageDigest 实例，指定使用 SHA-512 算法
            MessageDigest md = MessageDigest.getInstance("SHA-512");

            // 将待加密的字符串转换为字节数组
            byte[] inputBytes = inputString.getBytes(StandardCharsets.UTF_8);

            // 执行 SHA-512 加密
            byte[] hashBytes = md.digest(inputBytes);

            // 将哈希值转换为十六进制字符串
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            // 处理 SHA-512 算法不存在的异常
            throw new RuntimeException("SHA-512 algorithm not available", e);
        }
    }
}
