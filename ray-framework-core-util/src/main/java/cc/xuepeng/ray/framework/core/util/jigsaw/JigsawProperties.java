package cc.xuepeng.ray.framework.core.util.jigsaw;

import lombok.Data;

/**
 * 拼图组件的属性。
 */
@Data
public class JigsawProperties {

    /**
     * 拼图宽度。
     */
    private int width = 50;
    /**
     * 拼图高度。
     */
    private int height = 50;
    /**
     * 拼图凸出部分弧度。
     */
    private int arc = 10;
    /**
     * 拼图阴影宽度。
     */
    private int shadowWidth = 4;
    /**
     * 拼图高亮宽度。
     */
    private int lineHeightWidth = 5;

}
