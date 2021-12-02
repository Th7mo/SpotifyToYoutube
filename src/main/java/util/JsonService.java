package util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.stream.Collectors;

public class JsonService {

    public static String getJsonFromResource(final String fileName) throws FileNotFoundException {
        System.out.println("FILENAME: " + fileName  + "\n\n\n\n\n\n");
        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);

        return bufferedReader.lines().collect(Collectors.joining("\n"));
    }

    public static String getValueOfKey(final String key, final String json) {
        System.out.println("///////" + json);
        JsonElement element = JsonParser.parseString(json);
        JsonObject jsonObject = element.getAsJsonObject();

        return jsonObject.get(key).getAsString();
    }

    public static void setValueOfKey(final String key, final String value,
                                     final String fileName) throws FileNotFoundException {
        String json = getJsonFromResource(fileName);
        JSONObject jsonObject = new JSONObject(json);
        jsonObject.put(key, value);
        writeJSONObject(jsonObject, fileName);
    }

    public static void setValueOfKeyYoutubeCredentials(final String key,
                                                       final String value,
                                                       final String fileName) throws FileNotFoundException {
        String json = getJsonFromResource(fileName);
        JSONObject jsonObject = new JSONObject(json);
        JSONObject installed = jsonObject.getJSONObject("installed");
        installed.put(key, value);
        jsonObject.put("installed", installed);
        writeJSONObject(jsonObject, fileName);

//        System.out.println("JSON SET: " + getValueOfKey("installed", getJsonFromResource(fileName)));
    }

    private static void writeJSONObject(final JSONObject jsonObject,
                                 final String fileName) {
        System.out.println("WRITING: " + jsonObject.toString());

        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(jsonObject);
            writer.close();

            System.out.println("TO FILE: " + fileName + "\n\n\n" + getJsonFromResource(fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
