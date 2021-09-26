package cc.xuepeng.ray.framework.core.web.protocol;

import cc.xuepeng.ray.framework.core.util.consts.HttpHeaderConst;
import cc.xuepeng.ray.framework.core.util.consts.PunctuationConst;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 默认提供的HTTP协议处理类。
 * 用于检查HTTP请求是否符合Yesway的标准。
 *
 * @author xuepeng
 */
public class DefaultHttpRequestHandler implements HttpRequestHandler {

    /**
     * 检查协议。
     * 判断是否缺失HTTPHeader信息。
     *
     * @param request checkHttpRequest。
     * @return 是否通过检查。
     */
    @Override
    public boolean checkHttpRequest(HttpServletRequest request) {
        return checkHttpRequest(request, new StringBuilder());
    }

    /**
     * 检查协议。
     * 判断是否缺失HTTPHeader信息。
     *
     * @param request checkHttpRequest。
     * @param message 返回的消息信息。
     * @return 是否通过检查。
     */
    @Override
    public boolean checkHttpRequest(HttpServletRequest request, StringBuilder message) {
        boolean passed = true;
        if (!StringUtils.isNotBlank(request.getHeader(HttpHeaderConst.BUILD_NUMBER))) {
            passed = false;
            message.append(HttpHeaderConst.BUILD_NUMBER).append(PunctuationConst.COMMA);
        }
        if (!StringUtils.isNotBlank(request.getHeader(HttpHeaderConst.APP_VERSION))) {
            passed = false;
            message.append(HttpHeaderConst.APP_VERSION).append(PunctuationConst.COMMA);
        }
        if (!StringUtils.isNotBlank(request.getHeader(HttpHeaderConst.DEVICE_SYSTEM_INFO))) {
            passed = false;
            message.append(HttpHeaderConst.DEVICE_SYSTEM_INFO).append(PunctuationConst.COMMA);
        }
        if (!StringUtils.isNotBlank(request.getHeader(HttpHeaderConst.DEVICE_TYPE))) {
            passed = false;
            message.append(HttpHeaderConst.DEVICE_TYPE).append(PunctuationConst.COMMA);
        }
        if (!StringUtils.isNotBlank(request.getHeader(HttpHeaderConst.TIME_STAMP))) {
            passed = false;
            message.append(HttpHeaderConst.TIME_STAMP).append(PunctuationConst.COMMA);
        }
        return passed;
    }

}
