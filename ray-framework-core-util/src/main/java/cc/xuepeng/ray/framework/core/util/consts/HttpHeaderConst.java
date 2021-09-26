package cc.xuepeng.ray.framework.core.util.consts;

/**
 * HttpHeader常量。
 *
 * @author xuepeng
 */
public class HttpHeaderConst {

    /**
     * 构造函数。
     */
    private HttpHeaderConst() {
    }

    /**
     * 程序构建号。
     */
    public static final String BUILD_NUMBER = "X-Build-Number";
    /**
     * 程序版本号。
     */
    public static final String APP_VERSION = "X-App-Version";
    /**
     * 设备系统（操作系统类型）。
     */
    public static final String DEVICE_SYSTEM_INFO = "X-Device-System-Info";
    /**
     * 设备类型（浏览器类型）。
     */
    public static final String DEVICE_TYPE = "X-Device-Type";
    /**
     * 请求时间戳（毫秒数）。
     */
    public static final String TIME_STAMP = "X-Time-Stamp";
    /**
     * 用户标识。
     */
    public static final String CUSTOMER_ID = "X-Customer-Id";
    /**
     * 访问令牌。
     */
    public static final String ACCESS_TOKEN = "X-Access-Token";
    /**
     * 防CSRF令牌。
     */
    public static final String XSRF_TOKEN = "X-XSRF-TOKEN";

}
