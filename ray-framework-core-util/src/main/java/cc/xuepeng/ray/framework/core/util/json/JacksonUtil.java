package cc.xuepeng.ray.framework.core.util.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * Jackson的工具类。
 *
 * @author xuepeng
 */
public class JacksonUtil {

    private static ObjectMapper objectMapper = null;

    private JacksonUtil() {
    }

    public static ObjectMapper getInstance() {
        if (Objects.isNull(objectMapper)) {
            synchronized (JacksonUtil.class) {
                if (Objects.isNull(objectMapper)) {
                    objectMapper = new ObjectMapper();
                    objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                    JavaTimeModule timeModule = new JavaTimeModule();
                    timeModule.addSerializer(LocalDateTime.class, new JsonSerializer<LocalDateTime>() {
                        @Override
                        public void serialize(LocalDateTime localDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                            jsonGenerator.writeNumber(localDateTime.toInstant(OffsetDateTime.now().getOffset()).toEpochMilli());
                        }
                    });
                    timeModule.addDeserializer(LocalDateTime.class, new JsonDeserializer<LocalDateTime>() {
                        @Override
                        public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                            final long timestamp = jsonParser.getLongValue();
                            return LocalDateTime.ofEpochSecond(timestamp / 1000, 0, OffsetDateTime.now().getOffset());
                        }
                    });
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    objectMapper.registerModule(timeModule);
                }
            }
        }
        return objectMapper;
    }

}
