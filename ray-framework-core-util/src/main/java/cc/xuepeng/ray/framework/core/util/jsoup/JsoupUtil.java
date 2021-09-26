package cc.xuepeng.ray.framework.core.util.jsoup;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

/**
 * Jsoup的通用类。
 *
 * @author xuepeng
 */
public class JsoupUtil {

    /**
     * 创建一个Jsoup的通用类。
     * 白名单的默认级别为Relaxed。
     * 对Relaxed级别下的所有Tag开发style属性。
     */
    public JsoupUtil() {
        this.whitelist = Whitelist.relaxed();
        this.whitelist.addAttributes(":all", "style");
    }

    /**
     * 创建一个Jsoup的通用类。
     *
     * @param whitelist 白名单。
     */
    public JsoupUtil(Whitelist whitelist) {
        this.whitelist = whitelist;
    }

    /**
     * 对文本进行过滤。
     *
     * @param content 要过滤的文本。
     * @return 过滤后的文本。
     */
    public String clean(String content) {
        if (StringUtils.isNotEmpty(content)) {
            return Jsoup.clean(content, "", whitelist, outputSettings);
        }
        return content;
    }

    /**
     * 过滤XSS的白名单，级别为relaxed。
     */
    private final Whitelist whitelist;
    /**
     * 配置过滤化参数, 不对代码进行格式化
     */
    private static final Document.OutputSettings outputSettings = new Document.OutputSettings().prettyPrint(false);


}
