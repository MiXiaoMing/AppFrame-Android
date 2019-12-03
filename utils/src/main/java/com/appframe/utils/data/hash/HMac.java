package com.appframe.utils.data.hash;


import com.appframe.utils.logger.Logger;

import javax.crypto.spec.SecretKeySpec;

// HMAC (Hash-based Message Authentication Code) 常用于接口签名验证
// 有 HmacMD5 HmacSHA1 HmacSHA224 HmacSHA256 HmacSHA384 HmacSHA512

/**
 * 散列消息鉴别码
 */
public class HMac {

    // 支持的算法
    public static final String hmac_md5 = "HmacMD5";
    public static final String hmac_sha1 = "HmacSHA1";
    public static final String hmac_sha224 = "HmacSHA224";
    public static final String hmac_sha256 = "HmacSHA256";
    public static final String hmac_sha384 = "HmacSHA384";
    public static final String hmac_sha512 = "HmacSHA512";

     /*
      *   对字符串进行加密
      *   data：需要加密数据
      *   algorithm：加密算法，如上所例
      *   key：添加key值
      */
    public static byte[] encrypt(byte[] data, String algorithm, String key) {
        try {
            javax.crypto.Mac mac = javax.crypto.Mac.getInstance(algorithm);
            SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), algorithm);
            mac.init(secretKeySpec);
            return mac.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger().e("HmacMD5加密失败：" + e.toString());
            return null;
        }
    }
}
