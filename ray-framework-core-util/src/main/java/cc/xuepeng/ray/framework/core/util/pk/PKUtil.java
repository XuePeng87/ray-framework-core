package cc.xuepeng.ray.framework.core.util.pk;

import cc.xuepeng.ray.framework.core.util.consts.PunctuationConst;
import org.apache.commons.lang3.StringUtils;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 主键生成工具类。
 *
 * @author xuepeng
 */
public final class PKUtil {

    /**
     * 随机生成器。
     */
    private static final Random RANDOM = new Random();
    /**
     * 线程安全的一个随机数，每次自增+1。
     */
    private static final AtomicInteger NEXT_INC = new AtomicInteger(RANDOM.nextInt());
    /**
     * 机器信息。
     */
    private static final int MACHINE;

    /**
     * 生成机器信息，机器信息 = 机器码 + 进程码。
     */
    static {
        // 机器码
        int machinePiece;
        StringBuilder netInfo = new StringBuilder();
        // 返回机器所有的网络接口，便利这些网络接口后hashcode，取后二位做机器码
        Enumeration<NetworkInterface> enums;
        try {
            enums = NetworkInterface.getNetworkInterfaces();
            while (enums.hasMoreElements()) {
                NetworkInterface ni = enums.nextElement();
                netInfo.append(ni.toString());
            }
            machinePiece = netInfo.toString().hashCode() << 16;
        } catch (SocketException e) {
            // 出现异常随机生成整数，取两位
            machinePiece = (RANDOM.nextInt()) << 16;
        }
        // 进程码 = 进程Id + 对象（加载）Id
        final int processPiece;
        // 进程Id
        int processId = java.lang.management.ManagementFactory.getRuntimeMXBean().getName().hashCode();
        // 加载对象的Id
        ClassLoader loader = PKUtil.class.getClassLoader();
        // 返回对象哈希码，无论是否重写了hashCode方法
        int loaderId = loader != null ? System.identityHashCode(loader) : 0;
        // 进程ID + 对象加载ID，保留前2位
        StringBuilder processSb = new StringBuilder();
        processSb.append(Integer.toHexString(processId));
        processSb.append(Integer.toHexString(loaderId));
        processPiece = processSb.toString().hashCode() & 0xFFFF;
        // 生成机器信息 = 取机器码的后2位和进程码的前2位
        MACHINE = machinePiece | processPiece;
    }

    /**
     * 构造函数。
     */
    private PKUtil() {
    }

    /**
     * 创建ObjectID。
     *
     * @return 创建一个长度为24的字符串。
     */
    public static String createObjectID() {
        ByteBuffer bb = ByteBuffer.wrap(new byte[12]);
        bb.putInt((int) System.currentTimeMillis() / 1000);
        bb.putInt(MACHINE);
        bb.putInt(NEXT_INC.getAndIncrement());
        // 生成24位Id
        StringBuilder buf = new StringBuilder(24);
        for (byte t : bb.array()) {
            // 小于两位左端补0
            int i = t & 0xff;
            if (i < 16) {
                buf.append("0").append(Integer.toHexString(i));
            } else {
                buf.append(Integer.toHexString(i));
            }
        }
        return buf.toString();
    }

    /**
     * @return 获取一个UUID。
     */
    public static String getRandomUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * @return 获取一个32位的UUID（去掉横线）。
     */
    public static String get32UUID() {
        return StringUtils.replace(getRandomUUID(), PunctuationConst.HORIZONTAL_LINE, StringUtils.EMPTY);
    }

    /**
     * @return 获取大写UUID。
     */
    public static String getUpper32UUID() {
        return get32UUID().toUpperCase();
    }

    /**
     * 获取指定数量的32位UUID集合。
     *
     * @param count 指定数量。
     * @return 指定数量的32位UUID集合。
     */
    public static List<String> get32UUIDs(final int count) {
        if (count < 1) {
            throw new IllegalArgumentException("指定数量必须大于0。");
        }
        List<String> uuids = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            uuids.add(get32UUID());
        }
        return uuids;
    }

    /**
     * 获取指定数量的32位大写UUID集合。
     *
     * @param count 指定数量。
     * @return 指定数量的32位大写UUID集合。
     */
    public static List<String> getUpper32UUIDs(final int count) {
        List<String> uuids = get32UUIDs(count);
        return uuids.stream().map(String::toUpperCase).collect(Collectors.toList());
    }

    /**
     * 获得一个订单编号。
     * 订单编号 = yyyyMMdd + 雪花算法。
     *
     * @param snowflakeId 雪花算法。
     * @return 订单编号。
     */
    public static String getOrderIdByDay(final long snowflakeId) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + snowflakeId;
    }

    /**
     * 获得一个订单编号。
     * 订单编号 = yyyyMMddHHmmss + 雪花算法。
     *
     * @param snowflakeId 雪花算法。
     * @return 订单编号。
     */
    public static String getOrderIdBySeconds(final long snowflakeId) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + snowflakeId;
    }

    /**
     * 获得一个订单编号。
     * 订单编号 = yyyyMMddHHmmssSSS + 雪花算法。
     *
     * @param snowflakeId 雪花算法。
     * @return 订单编号。
     */
    public static String getOrderIdByMilliseconds(final long snowflakeId) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS")) + snowflakeId;
    }

}
