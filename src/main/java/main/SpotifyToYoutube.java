package main;

import controller.ApplicationController;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;
import util.JsonService;

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

    @Option(
            names = {"-i", "--client-id"},
            description = "sets the clientId for the Youtube API"
    )
    private String clientId;

    @Option(
            names = {"-s", "--client-secret"},
            description = "The client secret used to insert Youtube playlist items"
    )
    private String clientSecret;

    @Parameters(
            paramLabel = "<playlistId>",
            description = "The id of the Spotify Playlist that will be converted"
    )
    private String playlistId;

    public SpotifyToYoutube() {}

    @Override
    public void run() {
        try {
            setCredentials();
            new ApplicationController().convert(playlistId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setCredentials() throws IOException {
        System.out.println(clientId + " : " + clientSecret);
        JsonService.setValueOfKeyYoutubeCredentials("client_id", clientId, "./youtube_credentials.json");
        JsonService.setValueOfKeyYoutubeCredentials("client_secret", clientSecret, "./youtube_credentials.json");
        JsonService.setValueOfKey("key", apiKey, "./youtube_api_key.json");
    }
}
