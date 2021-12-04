package nl.th7mo.convert;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;

import java.util.stream.Collectors;

public class JsonService {

    public static String getJsonFromResource(final String fileName) throws FileNotFoundException {
        FileReader reader = new FileReader(fileName);
        BufferedReader bufferedReader = new BufferedReader(reader);

        return bufferedReader.lines().collect(Collectors.joining("\n"));
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
    }

    private static void writeJSONObject(final JSONObject jsonObject,
                                 final String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName);
            writer.println(jsonObject);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
