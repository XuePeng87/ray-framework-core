package cc.xuepeng.ray.framework.core.util.exception;

/**
 * 加载.properties文件的异常，继承自BaseException。
 * 继承了BaseException，加载.properties失败时，抛出该异常。
 *
 * @author xuepeng
 */
public class LoadPropertiesException extends BaseException {


    /**
     * 构造函数。
     *
     * @param msg 异常信息。
     */
    public LoadPropertiesException(String msg) {
        super(msg);
    }

    /**
     * 构造函数。
     *
     * @param msg   异常信息。
     * @param cause 异常原因。
     */
    public LoadPropertiesException(String msg, Throwable cause) {
        super(msg, cause);
    }

}
