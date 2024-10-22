package todo.list.application.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class JsonHelper<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger("JsonHelper");

    public static <T> List<T> asList(String filePath, Class<T> type) {

        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());

        try {
            return objectMapper
                    .readValue(FileUtils.getFile(filePath), objectMapper.getTypeFactory()
                            .constructCollectionType(List.class, type));
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }

        return null;
    }

    public static String getJson(String filePath, Charset charset) {
        try {
            return FileUtils.readFileToString(new File(filePath), charset);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJsonUTF8(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getJsonUTF16(String filePath) {
        try {
            return FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_16);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
