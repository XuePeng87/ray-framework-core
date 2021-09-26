package cc.xuepeng.ray.framework.core.util.jigsaw;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 框架提供的拼图生成器。
 *
 * @author xuepeng
 */
@NoArgsConstructor
@AllArgsConstructor
public class DefaultJigsawGenerator implements JigsawGenerator {

    /**
     * 生成拼图。
     *
     * @param pathName 文件路径。
     * @return 拼图信息。
     */
    @SneakyThrows
    @Override
    public Jigsaw generate(final String pathName) {
        return this.generate(new File(pathName));
    }

    /**
     * 生成拼图。
     *
     * @param file 原图文件。
     * @return 拼图信息。
     */
    @SneakyThrows
    @Override
    public Jigsaw generate(final File file) {
        final BufferedImage sourcePicture = ImageIO.read(file);

        // 1.随机生成拼图的坐标x，y，x轴距离右端width以上，Y轴距离底部height以上
        final int x = RandomUtils.nextInt(0, sourcePicture.getWidth() - jigsawProperties.getWidth() * 2) + jigsawProperties.getWidth();
        final int y = RandomUtils.nextInt(0, sourcePicture.getHeight() - jigsawProperties.getHeight());
        final BufferedImage smallPicture = createSmallPicture(file, x, y);

        // 2.生成拼图方向角
        final List<Shape> shapes = createSmallShape();
        final Shape area = shapes.get(0);
        final Shape bigarea = shapes.get(1);

        // 3.创建图层用于处理小图的阴影
        final BufferedImage smallBfm = new BufferedImage(
                jigsawProperties.getWidth(),
                jigsawProperties.getHeight(),
                BufferedImage.TYPE_INT_ARGB
        );

        // 4.创建图层用于处理大图的凹槽
        final BufferedImage bigBfm = new BufferedImage(
                jigsawProperties.getWidth(),
                jigsawProperties.getHeight(),
                BufferedImage.TYPE_INT_ARGB);

        // 5.设置阴影与凹槽
        for (int i = 0; i < jigsawProperties.getWidth(); i++) {
            for (int j = 0; j < jigsawProperties.getHeight(); j++) {
                if (area.contains(i, j)) {
                    smallBfm.setRGB(i, j, smallPicture.getRGB(i, j));
                }
                if (bigarea.contains(i, j)) {
                    bigBfm.setRGB(i, j, Color.black.getRGB());
                }
            }
        }

        // 6.处理图片的边缘高亮及其阴影效果
        BufferedImage smallPictureBuffered = dealLightAndShadow(smallBfm, area);

        // 7.创建返回值
        Jigsaw result = new Jigsaw();

        // 8.生成拼图
        try (ByteArrayOutputStream smallPictureBytes = new ByteArrayOutputStream()) {
            ImageIO.write(smallPictureBuffered, "png", smallPictureBytes);
            result.setCkImageBytes(smallPictureBytes.toByteArray());
        }

        // 9.生成背景图
        try (ByteArrayOutputStream bigPictureBytes = new ByteArrayOutputStream()) {
            BufferedImage bigPictureBuffered = addWatermark(file, bigBfm, x, y);
            ImageIO.write(bigPictureBuffered, "png", bigPictureBytes);
            result.setBgImageBytes(bigPictureBytes.toByteArray());
        }

        // 10. 设置拼图坐标
        result.setX(x);
        result.setY(y);

        return result;
    }

    /**
     * 创建拼图方块。
     *
     * @param file 原图对象。
     * @param x    拼图x坐标。
     * @param y    拼图y坐标。
     * @return 拼图方块。
     * @throws IOException IO异常。
     */
    private BufferedImage createSmallPicture(final File file, final int x, final int y) throws IOException {
        final Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName("png");
        final ImageReader render = iterator.next();
        try (ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(file))) {
            render.setInput(in, true);
            final ImageReadParam param = render.getDefaultReadParam();
            final Rectangle rect = new Rectangle(x, y, jigsawProperties.getWidth(), jigsawProperties.getHeight());
            param.setSourceRegion(rect);
            return render.read(0, param);
        }
    }

    /**
     * @return 创建小图方向角。
     */
    private List<Shape> createSmallShape() {
        final int face1 = RandomUtils.nextInt(0, 3);
        int face2;
        do {
            face2 = RandomUtils.nextInt(0, 3);
        } while (face2 == face1);
        final int width = jigsawProperties.getWidth();
        final int height = jigsawProperties.getHeight();
        final int arc = jigsawProperties.getArc();
        final int shadowWidth = jigsawProperties.getShadowWidth();
        // 生成随机区域值， （10-20）之间
        final int position1 = RandomUtils.nextInt(0, (width - arc * 2) / 2) + (width - arc * 2) / 2;
        final Shape shape1 = createShape(face1, 0, position1);
        final Shape bigshape1 = createShape(face1, 2, position1);
        // 生成中间正方体Shape, (具体边界+弧半径 = x坐标位)
        final int position2 = RandomUtils.nextInt(0, (width - arc * 2) / 2) + (height - arc * 2) / 2;
        final Shape shape2 = createShape(face2, 0, position2);
        final Shape centre = new Rectangle2D.Float(arc, arc, (float) width - 2 * 10, (float) height - 2 * 10);
        //因为后边图形需要生成阴影， 所以生成的小图shape + 阴影宽度 = 灰度化的背景小图shape（即大图上的凹槽）
        final Shape bigshape2 = createShape(face2, (float) shadowWidth / 2, position2);
        final Shape bigcentre = new Rectangle2D.Float(
                10 - (float) shadowWidth / 2,
                10 - (float) shadowWidth / 2,
                30 + (float) shadowWidth,
                30 + (float) shadowWidth
        );
        //合并Shape
        final Area area = new Area(centre);
        area.add(new Area(shape1));
        area.add(new Area(shape2));
        final Area bigarea = new Area(bigcentre);
        bigarea.add(new Area(bigshape1));
        bigarea.add(new Area(bigshape2));
        final List<Shape> list = new ArrayList<>();
        list.add(area);
        list.add(bigarea);
        return list;
    }

    /**
     * 创建半径为5的圆形区域。
     *
     * @param type     类型：上方，1：右方 2：下方，3：左方
     * @param size     大小。
     * @param position 位置。
     * @return 半径为5的圆形区域。
     */
    private Shape createShape(final int type, final float size, final int position) {
        final Arc2D.Float d;
        if (type == 0) {
            //上
            d = new Arc2D.Float(position, 5, 10 + size, 10 + size, 0, 190, Arc2D.CHORD);
        } else if (type == 1) {
            //右
            d = new Arc2D.Float(35, position, 10 + size, 10 + size, 270, 190, Arc2D.CHORD);
        } else if (type == 2) {
            //下
            d = new Arc2D.Float(position, 35, 10 + size, 10 + size, 180, 190, Arc2D.CHORD);
        } else if (type == 3) {
            //左
            d = new Arc2D.Float(5, position, 10 + size, 10 + size, 90, 190, Arc2D.CHORD);
        } else {
            d = new Arc2D.Float(5, position, 10 + size, 10 + size, 90, 190, Arc2D.CHORD);
        }
        return d;
    }

    /**
     * 设置拼图边缘的高亮与阴影。
     *
     * @param smallBfm 拼图底图。
     * @param shape    拼图的方向角。
     * @return 带有高亮、阴影和方向角的拼图。
     */
    private BufferedImage dealLightAndShadow(final BufferedImage smallBfm, final Shape shape) {
        // 创建新的透明图层，该图层用于边缘化阴影，将生成的小图合并到该图上
        final BufferedImage buffimg = ((Graphics2D) smallBfm.getGraphics())
                .getDeviceConfiguration()
                .createCompatibleImage(jigsawProperties.getWidth(), jigsawProperties.getHeight(), Transparency.TRANSLUCENT);
        final Graphics2D graphics2D = buffimg.createGraphics();
        final Graphics2D g2 = (Graphics2D) smallBfm.getGraphics();
        // 原有拼图，边缘亮色处理
        paintBorderGlow(g2, jigsawProperties.getLineHeightWidth(), shape);
        // 新图层添加阴影
        paintBorderShadow(graphics2D, shape);
        graphics2D.drawImage(smallBfm, 0, 0, null);
        return buffimg;
    }

    /**
     * 拼图描边。
     *
     * @param g2        拼图操作对象。
     * @param glowWidth 边缘宽度。
     * @param clipShape 边缘形状。
     */
    public void paintBorderGlow(final Graphics2D g2, final int glowWidth, final Shape clipShape) {
        final int gw = glowWidth * 2;
        for (int i = gw; i >= 2; i -= 2) {
            final float pct = (float) (gw - i) / (gw - 1);
            final Color mixHi = getMixedColor(COLOR_GLOW_INNER_HIGHT, pct, COLOR_GLOW_OUTER_HIGHT, 1.0f - pct);
            final Color mixLo = getMixedColor(COLOR_GLOW_INNER_LOWER, pct, COLOR_GLOW_OUTER_LOWER, 1.0f - pct);
            g2.setPaint(new GradientPaint(0.0f, 35 * 0.25f, mixHi, 0.0f, 35, mixLo));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }

    /**
     * 拼图阴影化。
     *
     * @param g2        拼图操作对象。
     * @param clipShape 边缘形状。
     */
    private void paintBorderShadow(final Graphics2D g2, final Shape clipShape) {
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        final int sw = jigsawProperties.getShadowWidth() * 2;
        for (int i = sw; i >= 2; i -= 2) {
            final float pct = (float) (sw - i) / (sw - 1);
            //pct<03. 用于去掉阴影边缘白边，  pct>0.8用于去掉过深的色彩， 如果使用Color.lightGray. 可去掉pct>0.8
            if (pct < 0.3 || pct > 0.8) {
                continue;
            }
            g2.setColor(getMixedColor(new Color(54, 54, 54), pct, Color.WHITE, 1.0f - pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }

    /**
     * 获取边缘颜色。
     *
     * @param c1   前景色。
     * @param pct1 饱和度。
     * @param c2   后景色。
     * @param pct2 色差。
     * @return 边缘颜色。
     */
    private Color getMixedColor(final Color c1, final float pct1, final Color c2, final float pct2) {
        final float[] clr1 = c1.getComponents(null);
        final float[] clr2 = c2.getComponents(null);
        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }
        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }

    /**
     * 添加背景图的水印。
     *
     * @param file   原图文件。
     * @param bigBfm 原图底图。
     * @param x      水印x坐标。
     * @param y      水印y坐标。
     * @return 背景图。
     * @throws IOException IO异常。
     */
    private BufferedImage addWatermark(File file, BufferedImage bigBfm, int x, int y) throws IOException {
        BufferedImage source = ImageIO.read(file);
        Graphics2D graphics2D = source.createGraphics();
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.5F));
        graphics2D.drawImage(bigBfm, x, y, null);
        graphics2D.dispose(); //释放
        return source;
    }


    /**
     * 内边发光最高值。
     */
    private static final Color COLOR_GLOW_INNER_HIGHT = new Color(253, 239, 175, 148);
    /**
     * 内边发光最低值。
     */
    private static final Color COLOR_GLOW_INNER_LOWER = new Color(255, 209, 0);
    /**
     * 外边发光最高值。
     */
    private static final Color COLOR_GLOW_OUTER_HIGHT = new Color(253, 239, 175, 124);
    /**
     * 外边发光最低值。
     */
    private static final Color COLOR_GLOW_OUTER_LOWER = new Color(255, 179, 0);
    /**
     * 拼图生成器参数。
     */
    private JigsawProperties jigsawProperties;

}
