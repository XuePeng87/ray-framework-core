package cc.xuepeng.ray.framework.core.util.exception;

/**
 * 框架提供的ViewModelRequiredException异常对象。
 * 继承了ParameterException，当接收VO时，其属性为Null，抛出该异常。
 *
 * @author xuepeng
 */
public class ViewModelRequiredException extends BaseException {

    /**
     * 构造函数。
     */
    public ViewModelRequiredException() {
    }

    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public ViewModelRequiredException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param cause 异常原因。
     */
    public ViewModelRequiredException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public ViewModelRequiredException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
