package com.appframe.utils.data.encryption;

import android.text.TextUtils;
import android.util.Base64;

import com.appframe.utils.logger.Logger;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 *  对称加密
 */
public class Symmetric {


    /**
     *  加密算法有四种：AES，DES，DESede(DES3)和RSA
     *  模式有：CBC(有向量模式)、ECB(无向量模式)
     *  填充模式：NoPadding: 加密内容不足8位用0补足8位
     *          PKCS5Padding: 加密内容不足8位用余位数补足8位
     */

    // 算法/模式/填充模式
    public static final String aec_cbc_nopaddinig = "Symmetric/CBC/NoPadding";
    public static final String aec_cbc_pkcs5padding = "Symmetric/CBC/PKCS5Padding";
    public static final String aec_ecb_nopadding = "Symmetric/ECB/NoPadding";
    public static final String aec_ecb_pkcs5paddiing = "Symmetric/ECB/PKCS5Padding";

    public static final String des_cbc_nopaddinig = "DES/CBC/NoPadding";
    public static final String des_cbc_pkcs5padding = "DES/CBC/PKCS5Padding";
    public static final String des_ecb_nopadding = "DES/ECB/NoPadding";
    public static final String des_ecb_pkcs5paddiing = "DES/ECB/PKCS5Padding";

    public static final String desede_cbc_nopaddinig = "DESede/CBC/NoPadding";
    public static final String desede_cbc_pkcs5padding = "DESede/CBC/PKCS5Padding";
    public static final String desede_ecb_nopadding = "DESede/ECB/NoPadding";
    public static final String desede_ecb_pkcs5paddiing = "DESede/ECB/PKCS5Padding";

    /**
     *  加密算法    密钥长度    向量长度
     *  AES         16          16
     *  DES         8           8
     *  DES3        24          24
     */

    /**
     * 加密
     * @param data              需要加密数据
     * @param transformation    算法类型
     * @param slatKey           盐值
     * @param vectorKey         向量值
     * @return
     */
    public static String encrypt(String data, String transformation, String slatKey, String vectorKey) {
        try {
            String algorithm = getAlgorithm(transformation);
            if (TextUtils.isEmpty(algorithm)) {
                Logger.getLogger().e("算法类型错误：" + transformation);
                return null;
            }
            SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] encrypted = cipher.doFinal(data.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("对称加密失败：" + e.toString());
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
            String algorithm = getAlgorithm(transformation);
            if (TextUtils.isEmpty(algorithm)) {
                Logger.getLogger().e("算法类型错误：" + transformation);
                return null;
            }
            SecretKey secretKey = new SecretKeySpec(slatKey.getBytes(), algorithm);
            IvParameterSpec iv = new IvParameterSpec(vectorKey.getBytes());
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
            byte[] content = Base64.decode(data, Base64.DEFAULT);
            byte[] encrypted = cipher.doFinal(content);
            return new String(encrypted);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("对称加密失败：" + e.toString());
            return null;
        }
    }

    private static String getAlgorithm(String transformation) {
        if (transformation.equals(aec_cbc_nopaddinig) || transformation.equals(aec_cbc_pkcs5padding)
                || transformation.equals(aec_ecb_nopadding) || transformation.equals(aec_ecb_pkcs5paddiing)) {
            return "AES";
        } else if (transformation.equals(des_cbc_nopaddinig) || transformation.equals(des_cbc_pkcs5padding)
                || transformation.equals(des_ecb_nopadding) || transformation.equals(des_ecb_pkcs5paddiing)){
            return "DES";
        } else if (transformation.equals(desede_cbc_nopaddinig) || transformation.equals(desede_cbc_pkcs5padding)
                || transformation.equals(desede_ecb_nopadding) || transformation.equals(desede_ecb_pkcs5paddiing)) {
            return "DESede";
        }
        return null;
    }
}
