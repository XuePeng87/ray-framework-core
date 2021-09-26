package cc.xuepeng.ray.framework.core.util.exception;

/**
 * 框架提供的BaseException异常对象。
 * 继承了RuntimeException，便于抛出非受检异常。
 *
 * @author xuepeng
 */
public class BaseException extends RuntimeException {

    /**
     * 构造函数。
     */
    public BaseException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public BaseException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public BaseException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
