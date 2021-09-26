package cc.xuepeng.ray.framework.core.web.security.cors;

import lombok.Data;
import org.apache.commons.lang3.time.DateUtils;

/**
 * 允许跨域的配置。
 *
 * @author xuepeng
 */
@Data
public class CORSAllowedProperties {

    /**
     * 配置允许跨域的路由。
     */
    private String mapping = "/**";
    /**
     * 配置允许跨域的Header。
     */
    private String headers = "*";
    /**
     * 配置允许跨域的请求源。
     */
    private String origins = "*";
    /**
     * 配置允许跨域的Method。
     */
    private String methods = "*";
    /**
     * 配置通过后无需在验证的时间。
     */
    private long age = DateUtils.MILLIS_PER_HOUR / 1000;
    /**
     * 配置是否允许传递Cookie。
     */
    private boolean credentials = true;

}
