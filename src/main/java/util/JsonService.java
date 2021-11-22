package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JsonService {

    public static String getJsonFromResource(final String fileName) {
        InputStream inputStream = getFileAsInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }

    private static InputStream getFileAsInputStream(final String fileName) {
        return JsonService.class.getClassLoader().getResourceAsStream(fileName);
    }

    public static String getValueOfKey(final String key, final String json) {
        JsonElement element = JsonParser.parseString(json);
        JsonObject jsonObject = element.getAsJsonObject();

        return jsonObject.get(key).getAsString();
    }
}
