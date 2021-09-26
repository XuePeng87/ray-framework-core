package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.lang3.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * AES加密工具类。
 *
 * @author xuepeng
 */
public class AES128Util {

    /**
     * 加密算法。
     */
    private static final String ALGORITHM = "AES";
    /**
     * 加密算法/加密模式/填充方式。
     */
    private static final String CIPHER_ALGORITHM = "AES/CBC/PKCS5Padding";
    /**
     * IV字符串。
     */
    private static final String IV_STRING = "YESWAY1234567890";

    /**
     * 构造函数。
     */
    private AES128Util() {
    }

    /**
     * AES加密。
     *
     * @param content 需要加密的字符串。
     * @param secret  密钥。
     * @return 加密后的字符串。
     */
    public static String encode(final String content, final String secret) {
        if (StringUtils.isBlank(secret)) {
            throw new IllegalArgumentException("密钥不能为空。");
        }
        if (StringUtils.length(secret) != 16) {
            throw new IllegalArgumentException("密钥长度必须为16。");
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(
                    secret.getBytes(StandardCharsets.UTF_8.name()),
                    ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(
                    IV_STRING.getBytes(StandardCharsets.UTF_8.name())
            );
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);
            byte[] encryptedBytes = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8.name()));
            return Base64Util.encode2String(encryptedBytes);
        } catch (Exception e) {
            throw new IllegalArgumentException("AES128加密失败。", e);
        }
    }

    /**
     * AES解密。
     *
     * @param content 需要解密的字符串。
     * @param secret  密钥。
     * @return 解密后的字符串。
     */
    public static String decode(final String content, final String secret) {
        if (StringUtils.isBlank(secret)) {
            throw new IllegalArgumentException("密钥不能为空。");
        }
        if (StringUtils.length(secret) != 16) {
            throw new IllegalArgumentException("密钥长度必须为16。");
        }
        try {
            IvParameterSpec ivParameterSpec = new IvParameterSpec(
                    IV_STRING.getBytes(StandardCharsets.UTF_8.name())
            );
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
            cipher.init(
                    Cipher.DECRYPT_MODE,
                    new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8.name()), ALGORITHM),
                    ivParameterSpec);
            byte[] result = cipher.doFinal(Base64Util.decode(content));
            return new String(result, StandardCharsets.UTF_8.name());
        } catch (Exception e) {
            throw new IllegalArgumentException("AES128解密失败。", e);
        }
    }

}
