package cc.xuepeng.ray.framework.core.util.codec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import java.io.ByteArrayOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类。
 *
 * @author xuepeng
 */
public class RSAUtil {

    /**
     * 加密算法。
     */
    private static final String KEY_ALGORITHM = "RSA";
    /**
     * 加密算法/工作模式/填充方式。
     */
    private static final String CIPHER_ALGORITHM = "RSA/ECB/PKCS1Padding";
    /**
     * 密钥长度。
     */
    private static final int KEY_SIZE = 1024;
    /**
     * RSA最大加密明文大小。
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    /**
     * RSA最大解密密文大小。
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
    /**
     * 生存密钥对map公钥key。
     */
    private static final String PUBLIC_KEY = "publicKey";
    /**
     * 生存密钥对map私钥key。
     */
    private static final String PRIVATE_KEY = "privateKey";

    /**
     * 构造函数。
     */
    private RSAUtil() {
    }

    /**
     * 对输入的字符进行RSA加密。
     *
     * @param content   需要加密的字符。
     * @param publicKey 加密公钥。
     * @return 加密后的字符串
     */
    public static String encode(final String content, final String publicKey) {
        try {
            byte[] data = content.getBytes(StandardCharsets.UTF_8.name());
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(Base64Util.decode(publicKey));
            cipher.init(Cipher.ENCRYPT_MODE, keyFactory.generatePublic(x509KeySpec));
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                segment(data, cipher, out, MAX_ENCRYPT_BLOCK);
                byte[] encryptedData = out.toByteArray();
                return Base64Util.encode2String(encryptedData);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("RSA算法加密失败。", e);
        }
    }

    /**
     * 对输入的字符串进行RSA解密。
     *
     * @param content    解密字符。
     * @param privateKey 解密私钥。
     * @return 解密后的字符串
     */
    public static String decode(final String content, final String privateKey) {
        try {
            byte[] data = Base64Util.decode(content);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(Base64Util.decode(privateKey));
            cipher.init(Cipher.DECRYPT_MODE, keyFactory.generatePrivate(pkcs8KeySpec));
            try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
                segment(data, cipher, out, MAX_DECRYPT_BLOCK);
                byte[] decryptedData = out.toByteArray();
                return new String(decryptedData, Charset.forName(StandardCharsets.UTF_8.name()));
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("RSA算法解密失败。", e);
        }
    }

    /**
     * 分段加密
     *
     * @param data   数据。
     * @param cipher 暗号。
     * @param out    输出。
     * @throws BadPaddingException       位移异常。
     * @throws IllegalBlockSizeException 区域异常。
     */
    private static void segment(final byte[] data, final Cipher cipher, final ByteArrayOutputStream out, final int length) throws BadPaddingException, IllegalBlockSizeException {
        int inputLen = data.length;
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > length) {
                cache = cipher.doFinal(data, offSet, length);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * length;
        }
    }

    /**
     * 生成公钥和私钥。
     *
     * @return 公钥和私钥的Map。
     */
    public static Map<String, String> getKeys() {
        KeyPairGenerator keyPairGen;
        Map<String, String> map = new HashMap<>();
        try {
            keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            keyPairGen.initialize(KEY_SIZE);
            KeyPair keyPair = keyPairGen.generateKeyPair();
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            map.put(PUBLIC_KEY, Base64Util.encode2String(publicKey.getEncoded()));
            map.put(PRIVATE_KEY, Base64Util.encode2String(privateKey.getEncoded()));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("生成公钥和私钥map失败。", e);
        }
        return map;
    }

}
