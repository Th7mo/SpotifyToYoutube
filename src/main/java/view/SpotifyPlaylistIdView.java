package view;

import java.util.Scanner;

public class SpotifyPlaylistIdView {

    public static String getUserInput() {
        System.out.print("> Please enter your Spotify playlist id: ");

        return new Scanner(System.in).nextLine();
    }
}
