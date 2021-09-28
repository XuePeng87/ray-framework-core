package cc.xuepeng.ray.framework.core.web.protocol;

import javax.servlet.http.HttpServletRequest;

/**
 * HTTP协议处理接口。
 * 用于检查HTTP请求是否符合标准。
 *
 * @author xuepeng
 */
public interface HttpRequestHandler {

    /**
     * 检查协议。
     *
     * @param request checkHttpRequest。
     * @return 是否通过检查。
     */
    boolean checkHttpRequest(HttpServletRequest request);

    /**
     * 检查协议。
     *
     * @param request checkHttpRequest。
     * @param message 返回的消息信息。
     * @return 是否通过检查。
     */
    boolean checkHttpRequest(HttpServletRequest request, StringBuilder message);

}
