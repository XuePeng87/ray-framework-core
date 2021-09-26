package cc.xuepeng.ray.framework.core.web.security.xss;

import cc.xuepeng.ray.framework.core.util.jsoup.JsoupUtil;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 对Request进行XSS的处理类。
 *
 * @author xuepeng
 */
public class XSSHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * 构造函数。
     *
     * @param request   request。
     * @param jsoupUtil jsoup通用类。
     */
    XSSHttpServletRequestWrapper(HttpServletRequest request, JsoupUtil jsoupUtil) {
        super(request);
        this.originRequest = request;
        this.jsoupUtil = jsoupUtil;
    }

    /**
     * 覆盖ajax post方法。
     *
     * @return 返回经过XSS处理的Request。
     * @throws IOException 当获取流失败时，抛出该异常。
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(originRequest.getInputStream(), StandardCharsets.UTF_8.displayName()))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            String result = sb.toString();
            if (StringUtils.isNotBlank(result)) {
                result = jsoupUtil.clean(result);
            }
            return new WrappedServletInputStream(new ByteArrayInputStream(result.getBytes(StandardCharsets.UTF_8.displayName())));
        }
    }

    /**
     * 覆盖getParameter方法。
     *
     * @param name name。
     * @return 过滤了XSS的数据。
     */
    @Override
    public String getParameter(String name) {
        if (StringUtils.equals("content", name) || StringUtils.endsWith("WithHtml", name)) {
            return super.getParameter(name);
        }
        name = jsoupUtil.clean(name);
        String value = super.getParameter(name);
        if (StringUtils.isNotBlank(value)) {
            value = jsoupUtil.clean(value);
        }
        return value;
    }

    /**
     * 覆盖getParameterValues方法。
     *
     * @param name name。
     * @return 过滤了XSS的数据。
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = jsoupUtil.clean(arr[i]);
            }
        }
        return arr;
    }

    /**
     * 覆盖getParameterMap方法。
     *
     * @return 过滤了XSS的数据。
     */
    @Override
    public Map<String, String[]> getParameterMap() {
        Map<String, String[]> map = super.getParameterMap();
        Map<String, String[]> returnMap = new HashMap<>();
        Iterator<Map.Entry<String, String[]>> entries = map.entrySet().iterator();
        String value = StringUtils.EMPTY;
        while (entries.hasNext()) {
            Map.Entry<String, String[]> entry = entries.next();
            String name = entry.getKey();
            Object valueObj = entry.getValue();
            if (null == valueObj) {
                value = StringUtils.EMPTY;
            } else {
                String[] values = (String[]) valueObj;
                for (String s : values) {
                    value = s + ",";
                }
                value = value.substring(0, value.length() - 1);
            }
            returnMap.put(name, new String[]{jsoupUtil.clean(value).trim()});
        }
        return returnMap;
    }

    /**
     * 覆盖getHeader方法。
     *
     * @param name name。
     * @return 过滤了XSS的数据。
     */
    @Override
    public String getHeader(String name) {
        String value = super.getHeader(jsoupUtil.clean(name));
        if (value != null) {
            value = jsoupUtil.clean(value);
        }
        return value;
    }

    /**
     * @return 获得原始的Request的静态方法。
     */
    public static HttpServletRequest getOriginRequest(HttpServletRequest req) {
        if (req instanceof XSSHttpServletRequestWrapper) {
            return ((XSSHttpServletRequestWrapper) req).getOriginRequest();
        }
        return req;
    }

    /**
     * @return 获得原始的Request。
     */
    public HttpServletRequest getOriginRequest() {
        return this.originRequest;
    }

    /**
     * 原始的Request。
     */
    private final HttpServletRequest originRequest;

    /**
     * WrappedServletInputStream对象。
     * 获取了ServletInputStream后，需要反写回Request，该类就用于反写。
     *
     * @author xuepeng
     */
    private static class WrappedServletInputStream extends ServletInputStream {

        public void setStream(InputStream stream) {
            this.stream = stream;
        }

        private InputStream stream;

        WrappedServletInputStream(InputStream stream) {
            this.stream = stream;
        }

        @Override
        public int read() throws IOException {
            return stream.read();
        }

        @Override
        public boolean isFinished() {
            return true;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {
            throw new UnsupportedOperationException();
        }

    }

    /**
     * Jsoup的通用类。
     */
    private final JsoupUtil jsoupUtil;

}
