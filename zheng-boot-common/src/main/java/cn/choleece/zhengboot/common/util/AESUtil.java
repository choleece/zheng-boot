package cn.choleece.zhengboot.common.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * @author choleece
 * @description: AES加密解密工具类
 * @date 2018/7/20 14:02
 */
public class AESUtil {

    private static final String ALGORITHM  = "AES";

    private static final String ENCODE_RULES = "zheng";

    private static final String RANDOM_SOURCE = "SHA1PRNG";

    private static final String CHARSET = "utf-8";

    private static final int BITS = 128;

    /**
     * 加密
     * 1. 构造密钥生成器
     * 2. 根据encode rules规则初始化密钥生成器
     * 3. 产生密钥
     * 4. 创建和初始化密码器
     * 5. 内容加密
     * 6. 返回字符串
     * @param content
     * @return
     */
    public static String aesEncode(String content) {
        try {
            // 1. 构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            // 2. 根据encode rules规则初始化密钥生成器
            // 生成一个128位的随机源，根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance(RANDOM_SOURCE);
            random.setSeed(ENCODE_RULES.getBytes());
            keyGenerator.init(BITS, random);
            // 3. 产生原始对称密钥
            SecretKey originalKey = keyGenerator.generateKey();
            // 4. 获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            // 5. 根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, ALGORITHM);
            // 6. 根据指定算法AES自称密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 7. 初始化密码器，第一个参数为加密（encrypt_mode）或者解密（decrypt_mode）操作，第二个参数为使用的KEY
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 8. 获取加密内容的字节数组（这里要设置为UTF-8）不然内容中如果有中文或英文混合中文的就会解密为乱码
            byte[] byteEncode = content.getBytes(CHARSET);
            // 9. 根据解码器的初始化方式--加密：将数据加密
            byte[] byteAES = cipher.doFinal(byteEncode);
            // 10. 将加密后的数据转换为字符串
            // 这里用Base64Encoder中会找不到包
            // 解决办法
            String aesEncode = new String(new BASE64Encoder().encode(byteAES));
            // 11. 将字符串返回
            return aesEncode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 有错就返回null
        return null;
    }

    /**
     * 解密
     * 解密过程
     * 1. 同加密1-4步
     * 2. 将加密后的字符串反纺成byte[]数组
     * 3. 将加密内容解密
     * @param content
     * @return
     */
    public static String aesDecode(String content) {
        try {
            // 1. 构造密钥生成器，指定为AES算法，不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance(ALGORITHM);
            // 2. 根据encode rules 规则初始化密钥生成器
            // 生成一个128位的随机源，根据传入的字节数组
            SecureRandom random = SecureRandom.getInstance(RANDOM_SOURCE);
            random.setSeed(ENCODE_RULES.getBytes());
            keygen.init(BITS, random);
            // 3. 产生原始对称密钥
            SecretKey originalKey = keygen.generateKey();
            // 4. 获得原始对称密钥的字节数组
            byte[] raw = originalKey.getEncoded();
            // 5. 根据字节数组生成AES密钥
            SecretKey key = new SecretKeySpec(raw, ALGORITHM);
            // 6. 根据指定算法AES自成密码器
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            // 7. 初始化密码器，第一个参数为加密（encrypt module）或者解密操作，第二个参数为使用的KEY
            cipher.init(Cipher.DECRYPT_MODE, key);
            // 8. 将加密编码后的内容解码成字节数组
            byte[] byteContent = new BASE64Decoder().decodeBuffer(content);
            // 9. 解密
            byte[] byteDecode = cipher.doFinal(byteContent);
            String aesDecode = new String(byteDecode, CHARSET);
            return aesDecode;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("配置文件中的密码需要用AES加密，，请使用cn.choleece.zhengboot.common.util.AESUtil工具类修改这些值！");
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        // 有错返回null
        return null;
    }
}
