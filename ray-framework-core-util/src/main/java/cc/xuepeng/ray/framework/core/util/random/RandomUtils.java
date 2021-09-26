package cc.xuepeng.ray.framework.core.util.random;

import java.util.Random;

/**
 * 随机数工具类。
 *
 * @author xuepeng
 */
public class RandomUtils {

    /**
     * 构造函数。
     */
    private RandomUtils() {
    }

    /**
     * 随机数类。
     */
    private static final Random random = new Random();

    /**
     * 生成六位数字型字符串。
     *
     * @return 六位数字型字符串。
     */
    public static String generateSixDigitsString() {
        int code = random.nextInt(899999) + 100000;
        return String.valueOf(code);
    }

}
