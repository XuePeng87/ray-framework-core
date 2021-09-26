package cc.xuepeng.ray.framework.core.util.entity.http;

/**
 * Http请求的响应状态枚举。
 * Framework提供的默认HttpResult对象。
 *
 * @author xuepeng
 */
public enum DefaultHttpResultStatus implements HttpResultStatus {

    /**
     * 成功。
     */
    SUCCESS(20000, "成功"),
    /**
     * 超时。
     */
    TIMEOUT(30000, "超时"),
    /**
     * 参数非法。
     */
    PARAM(40000, "参数错误"),
    /**
     * 失败。
     */
    FAIL(50000, "操作失败"),
    /**
     * 授权失败。
     */
    PERMISSIONS(60000, "授权失败"),
    /**
     * 系统异常。
     */
    ERROR(90000, "系统异常");

    /**
     * 构造函数。
     *
     * @param code 状态编码。
     * @param desc 状态描述。
     */
    DefaultHttpResultStatus(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * @return 获得状态编码。
     */
    @Override
    public int getCode() {
        return code;
    }

    /**
     * @return 获得状态描述。
     */
    @Override
    public String getDesc() {
        return desc;
    }

    /**
     * 状态编码。
     */
    private final int code;
    /**
     * 状态描述。
     */
    private final String desc;

}
