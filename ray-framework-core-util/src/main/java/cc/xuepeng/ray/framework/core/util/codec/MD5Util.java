package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

/**
 * MD5工具类。
 *
 * @author xuepeng
 */
public class MD5Util {

    private static final Random RANDOM = new Random();

    /**
     * 构造函数。
     */
    private MD5Util() {
    }

    /**
     * 对输入的字符串进行MD5加密。
     *
     * @param content 要加密的字符串。
     * @return 加密后的字符串。
     */
    public static String encode(final String content) {
        Objects.requireNonNull(content, "要加密的字符串不能为null。");
        return DigestUtils.md5Hex(content);
    }

    /**
     * 获取文件的md5。
     *
     * @param file 要加密的文件。
     * @return 加密后的字符串。
     */
    public static String encode(final File file) throws IOException {
        try (FileInputStream in = new FileInputStream(file)) {
            return DigestUtils.md5Hex(in);
        }
    }

    /**
     * 对输入的字符串进行MD5加盐加密。
     *
     * @param content 要加密的字符串。
     * @return 加密后的字符串。
     */
    public static String encodeSalt(final String content) {
        return salt(content);
    }

    /**
     * 验证加密内容。
     *
     * @param content 加密前的字符串。
     * @param md5     加密后的MD5。
     * @return 是否一致。
     */
    public static boolean verify(final String content, final String md5) {
        final char[] cs1 = new char[32];
        final char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5.charAt(i);
            cs1[i / 3 * 2 + 1] = md5.charAt(i + 2);
            cs2[i / 3] = md5.charAt(i + 1);
        }
        final String salt = new String(cs2);
        return DigestUtils.md5Hex(content + salt).equals(new String(cs1));
    }

    /**
     * 生成含有随机盐的字符。
     *
     * @param content 要加密的字符串。
     * @return 生成的加盐的字符。
     */
    private static String salt(final String content) {
        final String salt = get16BitRandom();
        final String result = DigestUtils.md5Hex(content + salt);
        return garble(result, salt);
    }


    /**
     * 生成16位随机数。
     *
     * @return 生成的字符串。
     */
    private static String get16BitRandom() {
        final StringBuilder sb = new StringBuilder(16);
        sb.append(RANDOM.nextInt(99999999)).append(RANDOM.nextInt(99999999));
        final int len = sb.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sb.append("0");
            }
        }
        return sb.toString();
    }

    /**
     * 混淆算法。
     *
     * @param md5Str md5字符串。
     * @param salt   用以加盐混淆的16位字符。
     * @return 混淆后字符
     */
    private static String garble(final String md5Str, final String salt) {
        final char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = md5Str.charAt(i / 3 * 2);
            final char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = md5Str.charAt(i / 3 * 2 + 1);
        }
        return new String(cs);
    }

}
