package cc.xuepeng.ray.framework.core.util.entity.http;

/**
 * Http请求的响应状态接口。
 *
 * @author xuepeng
 */

public interface HttpResultStatus {

    /**
     * @return 获得状态编码。
     */
    int getCode();

    /**
     * @return 获得状态描述。
     */
    String getDesc();

}
