package nl.th7mo.convert;

import picocli.CommandLine;

public class Main {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new SpotifyToYoutube()).execute(args);
        System.exit(exitCode);
    }
}
