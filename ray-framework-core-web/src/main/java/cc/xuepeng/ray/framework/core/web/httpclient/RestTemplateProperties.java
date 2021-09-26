package cc.xuepeng.ray.framework.core.web.httpclient;

import lombok.Data;

/**
 * RestTemplate属性对象。
 *
 * @author xuepeng
 */
@Data
public class RestTemplateProperties {

    /**
     * 连接超时时间。
     */
    private int connectionTimeout = 3000;
    /**
     * 执行超时时间。
     */
    private int readTimeout = 5000;
    /**
     * 代理对象。
     */
    private Proxy proxy = new Proxy();
    
    /**
     * 代理对象。
     */
    @Data
    public static class Proxy {

        /**
         * 是否启用代理。
         */
        private boolean enable = false;
        /**
         * 代理服务器地址。
         */
        private String host = "localhost";
        /**
         * 代理服务器端口。
         */
        private int port = 8080;

    }

}
