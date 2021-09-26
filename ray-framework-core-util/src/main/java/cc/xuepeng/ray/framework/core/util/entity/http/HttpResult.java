package cc.xuepeng.ray.framework.core.util.entity.http;


import org.apache.commons.lang3.StringUtils;

/**
 * Http请求的响应实体类。
 * 若要创建该对象，请使用本对象提供的Builder。
 * 具体用法如下：
 * <p>
 * <code>new HttpResult.Builder(param).build()</code>
 * </p>
 *
 * @param <T> HttpResult的数据对象的类型。
 * @author xuepeng
 */
public class HttpResult<T> {

    /**
     * 构造函数。
     */
    private HttpResult() {
    }

    /**
     * 构造函数。
     *
     * @param builder 建造者。
     */
    private HttpResult(Builder<T> builder) {
        this.code = builder.status.getCode();
        this.desc = builder.status.getDesc();
        this.msg = builder.msg;
        this.data = builder.data;
    }

    /**
     * @return 获得返回代码值。
     */
    public int getCode() {
        return code;
    }

    /**
     * @return 获得返回代码描述。
     */
    public String getDesc() {
        return desc;
    }

    /**
     * @return 获得响应消息。
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @return 获得响应数据。
     */
    public T getData() {
        return data;
    }

    /**
     * @return 重写toString()方法。
     */
    @Override
    public String toString() {
        return "HttpResult{" +
                "code=" + code +
                ", desc='" + desc + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * HttpResult的构造器。
     *
     * @author xuepeng
     */

    public static class Builder<K> {

        /**
         * 构造函数。
         *
         * @param status 返回状态。
         */
        public  Builder(HttpResultStatus status) {
            this.status = status;
        }

        /**
         * 设置返回消息。
         *
         * @param msg 返回消息。
         * @return HttpResult.Builder对象。
         */
        public Builder<K> msg(String msg) {
            this.msg = msg;
            return this;
        }

        /**
         * 设置返回数据。
         *
         * @param data 返回数据。
         * @return HttpResult.Builder对象。
         */
        public Builder<K> data(K data) {
            this.data = data;
            return this;
        }

        /**
         * 创建HttpResultEntity对象。
         *
         * @return HttpResultEntity对象。
         */
        public HttpResult<K> build() {
            return new HttpResult<>(this);
        }

        /**
         * @return 重写toString()方法。
         */
        @Override
        public String toString() {
            return "Builder{" +
                    "status=" + status +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }

        /**
         * 返回状态。
         */
        private final HttpResultStatus status;
        /**
         * 返回消息。
         */
        private String msg;
        /**
         * 返回数据。
         */
        private K data;

    }

    /**
     * 返回代码值。
     */
    private int code;
    /**
     * 返回代码描述。
     */
    private String desc;
    /**
     * 返回消息。
     */
    private String msg = StringUtils.EMPTY;
    /**
     * 返回数据。
     */
    private T data;

}
