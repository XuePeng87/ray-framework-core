package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.Objects;

/**
 * SHA1工具类。
 *
 * @author xuepeng
 */
public class SHA1Util {

    /**
     * 构造函数。
     */
    private SHA1Util() {
    }

    /**
     * 对输入的字符串进行SHA1加密。
     *
     * @param content 要加密的字符串。
     * @return 加密后的字符串。
     */
    public static String encode(final String content) {
        Objects.requireNonNull(content, "要加密的字符串不能为null。");
        return DigestUtils.sha1Hex(content);
    }

}
