package cc.xuepeng.ray.framework.core.util.bean;

import cc.xuepeng.ray.framework.core.util.entity.page.PageResult;
import cc.xuepeng.ray.framework.core.util.json.JacksonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Bean的公共处理类，用于复制Bean。
 * 常用的情况有VO和Entity的转换等。
 *
 * @author maye
 */
public class BeanUtil {

    /**
     * 私有构造函数。
     */
    private BeanUtil() {
    }

    /**
     * 将t1和k1的公有字段转化为k1对象,注意：此方法会舍弃掉非公有字段
     *
     * @param t1  原始对象
     * @param k1  转换后对象
     * @param <T> 原始类型
     * @param <K> 转换后类型
     * @return 转换后对象
     * @throws IllegalArgumentException 转换异常
     */
    public static <T, K> K objToObj(T t1, Class<K> k1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            final String json = getObjToStr(t1, mapper);
            return mapper.readValue(json, k1);
        } catch (IOException e) {
            throw new IllegalArgumentException("objToObj对象转换异常", e);
        }
    }

    /**
     * 将t1转为map
     *
     * @param <T> 原始类型
     * @param t1  原始对象
     * @return 转换后对象
     * @throws IllegalArgumentException 转换异常
     */
    public static <T> Map<String, Object> objToMap(T t1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            String json = getObjToStr(t1, mapper);
            return mapper.readValue(json, new TypeReference<Map<String, Object>>() {
            });
        } catch (IOException e) {
            throw new IllegalArgumentException("objToMap对象转换异常", e);
        }
    }

    /**
     * 将map转为t1
     *
     * @param <T> 原始类型
     * @param t1  原始对象
     * @return 转换后对象
     * @throws IllegalArgumentException 转换异常
     */
    public static <T> T mapToObj(Map<Object, Object> map, Class<T> t1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            String json = getObjToStr(map, mapper);
            return mapper.readValue(json, t1);
        } catch (IOException e) {
            throw new IllegalArgumentException("mapToObj对象转换异常", e);
        }
    }

    /**
     * 将t1转为list<k1>
     *
     * @param <T> 原始类型
     * @param t1  原始对象
     * @return 转换后对象
     * @throws IllegalArgumentException 转换异常
     */
    public static <T, K> List<K> objToList(T t1, Class<K> k1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            String json = getObjToStr(t1, mapper);
            JavaType javaType = getCollectionType(mapper, k1);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("objToList对象转换异常", e);
        }
    }

    /**
     * @param elementClasses 集合元素类
     * @return javaType
     */
    private static JavaType getCollectionType(ObjectMapper mapper, Class<?>... elementClasses) {
        return mapper.getTypeFactory().constructParametricType(ArrayList.class, elementClasses);
    }


    /**
     * 将Page<T> 转为Page<K> ,注意：此方法会舍弃掉非公有字段
     *
     * @param t1  Page<T>
     * @param k1  转换后对象
     * @param <T> 原始类型
     * @param <K> 转换后类型
     * @return 转换后对象
     * @throws IllegalArgumentException 转换异常
     */
    public static <T, K> PageResult<K> pageObjToPageObj(PageResult<T> t1, Class<K> k1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            if (t1 == null) {
                return null;
            }
            final List<T> list = t1.getRecord();
            final List<K> resList = new ArrayList<>();
            String json;
            for (T t : list) {
                json = mapper.writeValueAsString(t);
                resList.add(mapper.readValue(json, k1));
            }
            return new PageResult<>(t1.getCurrentPage(), t1.getPageSize(), t1.getTotalCount(), resList);
        } catch (IOException e) {
            throw new IllegalArgumentException("PageObj对象转换异常", e);
        }
    }

    /**
     * 将List<T> 转为List<K> ,注意：此方法会舍弃掉非公有字段
     *
     * @param t1  List<T>
     * @param k1  转换后对象
     * @param <T> 原始类型
     * @param <K> 转换后类型
     * @return 转换后对象
     * @throws IllegalArgumentException 转换异常
     */
    public static <T, K> List<K> listObjToListObj(List<T> t1, Class<K> k1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            String json = mapper.writeValueAsString(t1);
            JavaType javaType = getCollectionType(mapper, k1);
            return mapper.readValue(json, javaType);
        } catch (IOException e) {
            throw new IllegalArgumentException("listObjToListObj对象转换异常" + k1, e);
        }
    }

    /**
     * 字符串转对象。
     *
     * @param jsonString json字符串。
     * @param t1         待转换的对象。
     * @param <T>        转换后的类型。
     * @return 转换后的对象。
     */
    public static <T> T getStrToObj(String jsonString, Class<T> t1) {
        final ObjectMapper mapper = JacksonUtil.getInstance();
        try {
            return mapper.readValue(jsonString, t1);
        } catch (IOException e) {
            throw new IllegalArgumentException("字符串转对象转换异常", e);
        }
    }

    /**
     * 对象转字符串
     *
     * @param t1 t1
     * @return 转换后字符串
     */
    public static <T> String getObjToStr(T t1) {
        final ObjectMapper mapper = JacksonUtil.getInstance();
        String json;
        try {
            if (!(t1 instanceof String)) {
                json = mapper.writeValueAsString(t1);
            } else {
                json = String.valueOf(t1);
            }
        } catch (IOException e) {
            throw new IllegalArgumentException("getObjToStr对象转换异常" + t1, e);
        }
        return json;
    }

    /**
     * 对象转字符串
     *
     * @param t1     t1
     * @param mapper ObjectMapper
     * @return 转换后字符串
     */
    private static <T> String getObjToStr(T t1, ObjectMapper mapper) throws JsonProcessingException {
        String json;
        if (!(t1 instanceof String)) {
            json = mapper.writeValueAsString(t1);
        } else {
            json = String.valueOf(t1);
        }
        return json;
    }

    /**
     * 嵌套对象转换
     *
     * @param obj 转换对象
     * @param t1  转换后类型
     * @return 转换后字符串
     */
    public static <T> T objToNestingObj(Object obj, TypeReference<T> t1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            String json = getObjToStr(obj, mapper);
            return mapper.readValue(json, t1);
        } catch (IOException e) {
            throw new IllegalArgumentException("ObjToNestingObj对象转换异常", e);
        }
    }

    /**
     * 嵌套对象转换
     *
     * @param obj 转换对象
     * @param t1  转换后类型
     * @return 转换后字符串
     */
    public static <T> T objToNestingObj(String obj, TypeReference<T> t1) {
        try {
            final ObjectMapper mapper = JacksonUtil.getInstance();
            return mapper.readValue(obj, t1);
        } catch (IOException e) {
            throw new IllegalArgumentException("ObjToNestingObj对象转换异常", e);
        }
    }

    /**
     * list套对象转分隔字符串
     *
     * @param list           list
     * @param splitAttribute 对象属性(返回值是该属性的值)
     * @param splitStr       分隔的字符串
     * @param <T>            T
     * @return 分隔后的字符串
     */
    public static <T> String listObjToSplitStr(List<T> list, String splitAttribute, String splitStr) {
        try {
            final StringBuilder stringBuilder = new StringBuilder();
            for (T t : list) {
                String invoke = String.valueOf(t.getClass().getMethod("get" + StringUtils.capitalize(splitAttribute)).invoke(t));
                stringBuilder.append(splitStr).append(invoke);
            }
            stringBuilder.deleteCharAt(0);
            return stringBuilder.toString();
        } catch (Exception e) {
            throw new IllegalArgumentException("listObjToSplitStr对象转换异常", e);
        }
    }

    /**
     * @param obj 规范强转
     * @param <T> 强转类型
     * @return 转换结果
     */
    @SuppressWarnings("unchecked")
    private static <T> T cast(Object obj) {
        return (T) obj;
    }


}
