package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Base64加密工具类。
 *
 * @author xuepeng
 */
public class Base64Util {

    /**
     * 构造函数。
     */
    private Base64Util() {
    }

    /**
     * 对输入的字符串进行Base64加密。
     *
     * @param content 要加密的字符串。
     * @return 加密后的byte数组。
     */
    public static byte[] encode(final String content) {
        Objects.requireNonNull(content, "要加密的字符串不能为null。");
        try {
            return Base64.encodeBase64(content.getBytes(StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("encodeData时，byte转码时发生了异常。");
        }
    }

    /**
     * 对输入的字符串进行Base64加密。
     *
     * @param content 要加密的byte数组。
     * @return 加密后的byte数组。
     */
    public static byte[] encode(final byte[] content) {
        Objects.requireNonNull(content, "encodeData时，要加密的byte不能为null。");
        return Base64.encodeBase64(content);
    }


    /**
     * 对输入的字符串进行Base64加密。
     *
     * @param content 要加密的字符串。
     * @return 加密后的字符串。
     */
    public static String encode2String(final String content) {
        Objects.requireNonNull(content, "要加密的字符串不能为null。");
        try {
            return Base64.encodeBase64String(content.getBytes(StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("encodeString时，byte转码时发生了异常。");
        }
    }

    /**
     * 对输入的byte数组进行Base64加密。
     *
     * @param content 要加密的byte数组。
     * @return 加密后的字符串。
     */
    public static String encode2String(final byte[] content) {
        Objects.requireNonNull(content, "encode2String时，要加密的byte不能为null。");
        return Base64.encodeBase64String(content);
    }

    /**
     * 对输入的字符串进行Base64解密。
     *
     * @param content 要解密的字符串。
     * @return 解密后的byte数组。
     */
    public static byte[] decode(final String content) {
        Objects.requireNonNull(content, "要解密的字符串不能为null。");
        try {
            return Base64.decodeBase64(content.getBytes(StandardCharsets.UTF_8.toString()));
        } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException("decodeData时，byte转码时发生了异常。");
        }
    }

    /**
     * 对输入的byte数组进行Base64解密。
     *
     * @param content 要解密的byte数组。
     * @return 解密后的byte数组。
     */
    public static byte[] decode(final byte[] content) {
        Objects.requireNonNull(content, "要加密的byte不能为null。");
        return Base64.decodeBase64(content);
    }

    /**
     * 对输入的字符串进行Base64解密。
     *
     * @param content 要解密的字符串。
     * @return 解密后的字符串。
     */
    public static String decode2String(final String content) {
        Objects.requireNonNull(content, "要解密的字符串不能为null。");
        return new String(Base64.decodeBase64(content));
    }

    /**
     * 对输入的byte数组进行Base64解密。
     *
     * @param content 要解密的byte数组。
     * @return 解密后的字符串。
     */
    public static String decode2String(final byte[] content) {
        Objects.requireNonNull(content, "要解密的byte不能为null。");
        return new String(Base64.decodeBase64(content));
    }

}
