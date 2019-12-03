package com.appframe.utils.data.hash;

import com.appframe.utils.data.encoded.Hex;
import com.appframe.utils.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 信息摘要算法
 */
public class MD5 {

    // 对字符串加密，转明文展示
    public static String encryptHex(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            return Hex.encode(digest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Logger.getLogger().e("md5加密失败：" + e.toString());
            return null;
        }
    }

    // 对字符串加密
    public static byte[] encrypt(byte[] bytes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(bytes);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            Logger.getLogger().e("md5加密失败：" + e.toString());
            return null;
        }
    }
}
