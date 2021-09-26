package cc.xuepeng.ray.framework.core.util.exception;

/**
 * 框架提供的PermissionsException异常对象。
 * 继承了BaseException，当权限不够时，抛出该异常。
 *
 * @author xuepeng
 */
public class PermissionsException extends BaseException {

    /**
     * 构造函数。
     */
    public PermissionsException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public PermissionsException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public PermissionsException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public PermissionsException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
