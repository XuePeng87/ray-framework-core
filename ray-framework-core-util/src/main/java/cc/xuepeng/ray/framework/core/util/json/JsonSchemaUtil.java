package cc.xuepeng.ray.framework.core.util.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jackson.JsonLoader;
import com.github.fge.jackson.JsonNodeReader;
import com.github.fge.jsonschema.core.report.LogLevel;
import com.github.fge.jsonschema.core.report.ProcessingMessage;
import com.github.fge.jsonschema.core.report.ProcessingReport;
import com.github.fge.jsonschema.main.JsonSchemaFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

/**
 * JSON格式验证器。
 *
 * @author xuepeng
 */
public class JsonSchemaUtil {

    /**
     * 构造函数。
     */
    private JsonSchemaUtil() {
    }

    /**
     * 验证JSON格式。
     *
     * @param jsonNode   JSON数据。
     * @param schemaNode JSON校验配置。
     * @return 是否通过校验。
     */
    public static boolean validateJsonByFgeByJsonNode(JsonNode jsonNode, JsonNode schemaNode) {
        final ProcessingReport report = JsonSchemaFactory
                .byDefault()
                .getValidator()
                .validateUnchecked(schemaNode, jsonNode);
        if (report.isSuccess()) {
            return true;
        } else {
            Iterator<ProcessingMessage> it = report.iterator();
            StringBuilder msg = new StringBuilder();
            while (it.hasNext()) {
                ProcessingMessage pm = it.next();
                if (!LogLevel.WARNING.equals(pm.getLogLevel())) {
                    msg.append(pm);
                }
            }
            throw new IllegalArgumentException(msg.toString());
        }
    }

    /**
     * 字符串转JsonNode。
     *
     * @param jsonStr 字符串。
     * @return JsonNode。
     */
    public static JsonNode getJsonNodeFromString(String jsonStr) throws IOException {
        return JsonLoader.fromString(jsonStr);
    }

    /**
     * 文件转JsonNode。
     *
     * @param filePath 文件路径。
     * @return JsonNode。
     */
    public static JsonNode getJsonNodeFromFile(String filePath) throws IOException {
        return new JsonNodeReader().fromReader(new FileReader(filePath));
    }

}
