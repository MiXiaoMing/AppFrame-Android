package com.appframe.utils.data.encryption;

import android.util.Base64;

import com.appframe.utils.logger.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 非对称加密
 */
public class Asymmetric {


    /**
     *  加密算法有四种：Symmetric，DES，DESede(DES3)和RSA
     *  模式有：CBC(有向量模式)、ECB(无向量模式)
     *  填充模式：NoPadding: 加密内容不足8位用0补足8位
     *          PKCS5Padding: 加密内容不足8位用余位数补足8位
     */

    // 算法/模式/填充模式
    public static final String rsa_ecb_pkcs1padding = "RSA/ECB/PKCS1Padding";
    public static final String rsa_ecb_oaepwidthsha_1andmgf1padding = "RSA/ECB/OAEPWithSHA-1AndMGF1Padding";
    public static final String rsa_ecb_oaepwithsha_256andmgf1padding = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding";


    // TODO: 2019/8/8 需要调整  https://www.cnblogs.com/caizhaokai/p/10944667.html
    // TODO: 2019/8/8  获取密钥方式错误：

    /**
     * 加密
     * @param data              需要加密数据
     * @param transformation    算法类型
     * @param slatKey           盐值：16位字符串
     * @param vectorKey         向量值：16位字符串
     * @return
     */
    public static String encrypt(String data, String transformation, String slatKey, String vectorKey) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "Symmetric");
            IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("aes加密失败：" + e.toString());
            return null;
        }
    }

    /**
     * aes加密
     * @param data              需要解密数据
     * @param transformation    算法类型
     * @param slatKey           盐值：16位字符串
     * @param vectorKey         向量值：16位字符串
     * @return
     */
    public static String decrypt(String data, String transformation, String slatKey, String vectorKey) {
        try {
            Cipher cipher = Cipher.getInstance(transformation);
            SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), "Symmetric");
            IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] content = Base64.decode(data, Base64.DEFAULT);
            byte[] encrypted = cipher.doFinal(content);
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("aes加密失败：" + e.toString());
            return null;
        }
    }
}
