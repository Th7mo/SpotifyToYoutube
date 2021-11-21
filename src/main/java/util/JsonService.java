package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class JsonService {

    public static String getValueOfKey(String path) {
        InputStream stream = JsonService.class.getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
        String json = reader.lines().collect(Collectors.joining("\n"));
        JsonElement element = JsonParser.parseString(json);
        JsonObject obj = element.getAsJsonObject();

        return obj.get("key").getAsString();
    }
}
