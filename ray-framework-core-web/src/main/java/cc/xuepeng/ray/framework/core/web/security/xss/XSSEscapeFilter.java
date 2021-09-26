package cc.xuepeng.ray.framework.core.web.security.xss;

import cc.xuepeng.ray.framework.core.util.jsoup.JsoupUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 处理XSS的过滤器。
 *
 * @author xuepeng
 */
public class XSSEscapeFilter implements Filter {

    /**
     * 创建一个XSS过滤器。
     * 使用框架默认提供的JsoupUtil进行处理。
     */
    public XSSEscapeFilter() {
        this.jsoupUtil = new JsoupUtil();
    }

    /**
     * 创建一个XSS过滤器。
     *
     * @param jsoupUtil 提供一个JsoupUtil进行处理。
     */
    public XSSEscapeFilter(JsoupUtil jsoupUtil) {
        this.jsoupUtil = jsoupUtil;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Do nothing
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new XSSHttpServletRequestWrapper((HttpServletRequest) request, jsoupUtil), response);
    }

    @Override
    public void destroy() {
        // Do nothing
    }

    /**
     * JsoupUtil类。
     */
    private final JsoupUtil jsoupUtil;

}
