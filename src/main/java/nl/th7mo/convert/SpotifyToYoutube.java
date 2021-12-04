package nl.th7mo.convert;

import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;

import java.io.IOException;

@Command(
        name = "SpotifyToYoutube",
        mixinStandardHelpOptions = true
)
public class SpotifyToYoutube implements Runnable {

    @Parameters(
            paramLabel = "<playlistId>",
            description = "The id of the Spotify Playlist that will be converted"
    )
    private String playlistId;

    @Override
    public void run() {
        try {
            new ApplicationController().convert(playlistId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
