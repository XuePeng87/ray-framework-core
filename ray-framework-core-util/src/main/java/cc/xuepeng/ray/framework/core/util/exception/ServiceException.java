package cc.xuepeng.ray.framework.core.util.exception;

/**
 * 框架提供的ServiceException异常对象。
 * 继承了BaseException，当微服务发生异常时，抛出该异常。
 *
 * @author xuepeng
 */
public class ServiceException extends BaseException {

    /**
     * 构造函数。
     */
    public ServiceException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public ServiceException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public ServiceException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
