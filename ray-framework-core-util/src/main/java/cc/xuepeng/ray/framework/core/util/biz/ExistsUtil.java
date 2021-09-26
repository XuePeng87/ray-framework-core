package cc.xuepeng.ray.framework.core.util.biz;

import cn.hutool.core.util.ReflectUtil;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 用于验证某个值是否存在。
 * 通常在创建或者修改业务时，判断某个属性的值是否被使用。
 *
 * @author xuepeng
 */
public class ExistsUtil {

    /**
     * 构造函数。
     */
    private ExistsUtil() {
    }

    /**
     * 判断值是否存在。
     *
     * @param list  数据列表。
     * @param value 要验证是否存在的值。
     * @return 是否存在。
     */
    public static <T> boolean exists(List<T> list, String value, String field) {
        return exists(list, value, (l, v) -> {
            if (l.size() > 1) {
                return true;
            } else if (l.isEmpty()) {
                return false;
            } else {
                final String id = ReflectUtil.invoke(l.get(0), "get" + field).toString();
                return StringUtils.isEmpty((CharSequence) v) || !StringUtils.equals(id, (CharSequence) v);
            }
        });
    }

    /**
     * 判断值是否存在。
     *
     * @param list           数据列表。
     * @param value          要验证是否存在的值。
     * @param existsFunction 验证存在的Lambda接口。
     * @return 是否存在。
     */
    public static <T> boolean exists(List<T> list, String value, ExistsFunction<T> existsFunction) {
        return existsFunction.exists(list, value);
    }

}
