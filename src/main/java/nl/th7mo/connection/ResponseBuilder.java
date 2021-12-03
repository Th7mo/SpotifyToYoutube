/*
    Keep it simple - Th7mo
*/

package nl.th7mo.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

public final class ResponseBuilder {

    public static String getResponse(HttpURLConnection connection) throws IOException {
        BufferedReader reader = getBufferedReader(connection);

        return buildResponseString(reader);
    }

    private static BufferedReader getBufferedReader(HttpURLConnection connection)
            throws IOException {
        InputStream inputStream = connection.getInputStream();
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

        return new BufferedReader(inputStreamReader);
    }

    private static String buildResponseString(BufferedReader reader) {
        return reader.lines().collect(Collectors.joining());
    }
}
