package com.xps.rest.util;

import org.apache.commons.codec.binary.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * Created by shenxy on 2016/12/19.
 */
public class RSAUtil {

    public static final String KEY_ALGORITHM = "RSA";
    public static final int KEY_INIT_SIZE = 512;
    public static final String UTF8 = "UTF-8";

    /**
     * 取得公钥
     *
     * @param keyPair
     * @return
     * @throws Exception
     */
    public static String getPublicKey(KeyPair keyPair) throws Exception {
        RSAPublicKey publicKeyObj = (RSAPublicKey) keyPair.getPublic();
        byte[] publicKey = publicKeyObj.getEncoded();
        return encryptBASE64(publicKey);
    }

    /**
     * 取得私钥
     *
     * @param keyPair
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(KeyPair keyPair) throws Exception {
        RSAPrivateKey privateKeyObj = (RSAPrivateKey) keyPair.getPrivate();
        byte[] privateKey =privateKeyObj.getEncoded();
        return encryptBASE64(privateKey);
    }



    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }


    /**
     * 公钥BASE64加密后转成公钥对象
     *
     * @param pubStr
     * @return
     * @throws Exception
     */

    public static RSAPublicKey string2PublicKey(String pubStr) throws Exception {

        try {
            byte[] keyBytes = decryptBASE64(pubStr);

            X509EncodedKeySpec KeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPublicKey publicKey = (RSAPublicKey) keyFactory.generatePublic(KeySpec);
            return publicKey;
        }catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("公钥非法");
        } catch (NullPointerException e) {
            throw new Exception("公钥数据为空");
        }
    }

    /**
     * 私钥BASE64加密后转成私钥对象
     *
     * @param priStr
     * @return
     * @throws Exception
     */
    public static RSAPrivateKey string2PrivateKey(String priStr) throws Exception {

        try {
            byte[] keyBytes = decryptBASE64(priStr);

            PKCS8EncodedKeySpec KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            RSAPrivateKey privateKey = (RSAPrivateKey) keyFactory.generatePrivate(KeySpec);
            return privateKey;
        }catch (NoSuchAlgorithmException e) {
            throw new Exception("无此算法");
        } catch (InvalidKeySpecException e) {
            throw new Exception("私钥非法");
        } catch (NullPointerException e) {
            throw new Exception("私钥数据为空");
        }
    }

    /**
     * byte数组转为string
     *
     * @param encrytpByte
     * @return
     */
    public String bytesToString(byte[] encrytpByte) {
        String result = "";
        for (Byte bytes : encrytpByte) {
            result += (char) bytes.intValue();
        }
        return result;
    }

    /**
     * 加密方法
     *
     * @param publicKey
     * @param obj
     * @return
     */
    public static byte[] encrypt(RSAPublicKey publicKey, byte[] obj) throws Exception {
        if (publicKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
                cipher.init(Cipher.ENCRYPT_MODE, publicKey);
                return cipher.doFinal(obj);
            } catch (NoSuchPaddingException e) {
                e.printStackTrace();
                return null;
            }catch (IllegalBlockSizeException e) {
                throw new Exception("明文长度非法");
            }
        }else {
            throw new Exception("加密公钥为空, 请设置");
        }
    }

    /**
     * 解密方法
     *
     * @param privateKey
     * @param obj
     * @return
     */
    public static byte[] decrypt(RSAPrivateKey privateKey, byte[] obj) throws Exception {

        if (privateKey != null) {
            try {
                Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
                cipher.init(Cipher.DECRYPT_MODE, privateKey);
                return cipher.doFinal(obj);
            } catch (NoSuchPaddingException  e) {
                e.printStackTrace();
                return null;
            }catch (InvalidKeyException e) {
                throw new Exception("解密私钥非法,请检查");
            } catch (IllegalBlockSizeException e) {
                throw new Exception("密文长度非法");
            } catch (BadPaddingException e) {
                throw new Exception("密文数据已损坏");
            }
        }else {
            throw new Exception("解密私钥为空, 请设置");
        }

    }
    
    /**  
     * @Title:	decodePassword
     * @Description: 用密钥解密  
     * @param privateKey 密钥
     * @param password 加密密码
     * @return
     * @throws Exception  String  
     * @author zym
     * @date 2016-12-22 下午3:00:21   
    */
    public static String decodePassword(String privateKey, String password) throws Exception {
    	byte[] aa = decryptBASE64(password);
		byte[] de = decrypt(string2PrivateKey(privateKey), aa);
		return new String(de,"UTF-8");
    }

    public static void main(String[] args) {
        try {
            RSAUtil encrypt = new RSAUtil();
            String encryptText = "1qaz2wsx3edc$RFV5tgbnhy67ujmZXCVBNM,你好!world.";
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
            keyPairGen.initialize(KEY_INIT_SIZE);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            String pub = getPublicKey(keyPair);

            String pri = getPrivateKey(keyPair);

            RSAPublicKey publicKey = string2PublicKey(pub);

            RSAPrivateKey privateKey = string2PrivateKey(pri);

            System.out.println("私钥为：" + pri);

            System.out.println("公钥为：" + pub);

            byte[] e = encrypt(publicKey, encryptText.getBytes());
            byte[] de = decrypt(privateKey, e);

            System.out.println("准备用公钥加密的字符串为：" + encryptText);

            System.out.println("用公钥加密后的结果为:" + encrypt.encryptBASE64(e));

            System.out.println("用私钥解密后的字符串为：" + new String(de,UTF8));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
