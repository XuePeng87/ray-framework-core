package cc.xuepeng.ray.framework.core.util.jigsaw;

import java.io.File;

/**
 * 拼图生成器。
 *
 * @author xuepeng
 */
public interface JigsawGenerator {

    /**
     * 生成拼图。
     *
     * @param pathName 原图文件路径。
     * @return 拼图信息。
     */
    Jigsaw generate(final String pathName);

    /**
     * 生成拼图。
     *
     * @param file 原图文件。
     * @return 拼图信息。
     */
    Jigsaw generate(final File file);

}
