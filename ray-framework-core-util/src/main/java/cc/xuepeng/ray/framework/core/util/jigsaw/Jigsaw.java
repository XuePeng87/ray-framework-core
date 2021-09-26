package cc.xuepeng.ray.framework.core.util.jigsaw;

import lombok.Data;

/**
 * 拼图信息。
 *
 * @author xuepeng
 */
@Data
public class Jigsaw {

    /**
     * 背景图字节数组。
     */
    private byte[] bgImageBytes;
    /**
     * 背景图字符串。
     */
    private String byImageBase64;
    /**
     * 拼图。
     */
    private byte[] ckImageBytes;
    /**
     * 拼图字符串。
     */
    private String ckImageBase64;
    /**
     * x坐标。
     */
    private int x;
    /**
     * y坐标。
     */
    private int y;
    /**
     * 令牌。
     */
    private String token;

}
