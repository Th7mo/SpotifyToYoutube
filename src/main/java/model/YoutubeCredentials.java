package model;

public class YoutubeCredentials {

    private Installed installed;
    private String apiKey;

    public Installed getInstalled() {
        return installed;
    }

    public void setInstalled(Installed installed) {
        this.installed = installed;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
