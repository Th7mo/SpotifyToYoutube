package nl.th7mo.convert;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;

@Command(
        name = "spotifytoyoutube",
        mixinStandardHelpOptions = true
)
public class SpotifyToYoutube implements Runnable {

    @Option(
            names = {"-a", "--api-key"},
            description = "The api-key used to search for Youtube ids"
    )
    private String apiKey;

    @Parameters(
            paramLabel = "<playlistId>",
            description = "The id of the Spotify Playlist that will be converted"
    )
    private String playlistId;

    @Override
    public void run() {
        try {
            new ApplicationController().convert(playlistId, apiKey);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
