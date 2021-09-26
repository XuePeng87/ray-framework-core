package cc.xuepeng.ray.framework.core.util.exception;

/**
 * 框架提供的TimeoutException异常对象。
 * 继承了BaseException，当发生超时时，抛出该异常。
 *
 * @author xuepeng
 */
public class TimeoutException extends BaseException {

    /**
     * 构造函数。
     */
    public TimeoutException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public TimeoutException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public TimeoutException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public TimeoutException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
