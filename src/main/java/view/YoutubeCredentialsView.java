package view;

import java.util.Scanner;

public class YoutubeCredentialsView {

    private static final Scanner scanner = new Scanner(System.in);

    public static String getClientId() {
        return getUserInput("Please enter your client id: ");
    }

    public static String getClientSecret() {
        return getUserInput("Please enter your client secret: ");
    }

    public static String getApiKey() {
        return getUserInput("Please enter your Youtube API Key: ");
    }

    private static String getUserInput(String message) {
        System.out.print("> " + message);

        return scanner.nextLine();
    }
}
