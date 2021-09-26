package cc.xuepeng.ray.framework.core.util.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.util.Objects;

/**
 * Hex工具类。
 *
 * @author xuepeng
 */
public class HexUtil {

    /**
     * 构造函数。
     */
    private HexUtil() {
    }

    /**
     * 对输入的字符串进行Hex加密。
     *
     * @param content 要加密的byte数组。
     * @return 加密后的字符串。
     */
    public static String encode(final byte[] content) {
        Objects.requireNonNull(content, "要加密的byte不能为null。");
        return Hex.encodeHexString(content);
    }

    /**
     * 对输入的字符串进行Hex解密。
     *
     * @param content 要解密的字符串。
     * @return 解密后的byte。
     */
    public static byte[] decode(final String content) {
        Objects.requireNonNull(content, "要解密的字符串不能为null。");
        byte[] context;
        try {
            context = Hex.decodeHex(content.toCharArray());
        } catch (DecoderException e) {
            throw new IllegalArgumentException("解码失败，密文为：" + content);
        }
        return context;
    }

}
