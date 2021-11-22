package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class JsonService {

    public static String getValueOfKey(final String key, final String fileName) {
        InputStream stream = getFileAsInputStream(fileName);
        InputStreamReader inputStreamReader = new InputStreamReader(stream);
        BufferedReader reader = new BufferedReader(inputStreamReader);
        String json = reader.lines().collect(Collectors.joining("\n"));
        JsonElement element = JsonParser.parseString(json);
        JsonObject obj = element.getAsJsonObject();

        return obj.get(key).getAsString();
    }

    private static InputStream getFileAsInputStream(final String fileName) {
        return JsonService.class.getClassLoader().getResourceAsStream(fileName);
    }
}
