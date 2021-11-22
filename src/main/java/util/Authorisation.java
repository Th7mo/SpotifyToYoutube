package util;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;

import java.io.*;
import java.util.List;

public class Authorisation {

    public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
    public static final JsonFactory JSON_FACTORY = new JacksonFactory();
    private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";
    private static GoogleClientSecrets clientSecrets;
    private static List<String> scopes;
    private static DataStore<StoredCredential> dataStore;

    public static Credential authorize(List<String> scopes,
                                       String credentialDatastore) throws IOException {
        Authorisation.scopes = scopes;
        loadCredentials();

        return makeAuthorization(credentialDatastore);
    }

    private static void loadCredentials() throws IOException {
        InputStream inputStream = getClientSecretInputStream();
        Reader clientSecretReader = new InputStreamReader(inputStream);
        clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
    }

    private static InputStream getClientSecretInputStream() {
        return Authorisation.class.getClassLoader().getResourceAsStream("youtube_credentials.json");
    }

    private static Credential makeAuthorization(String credentialDatastore)
            throws IOException {
        File oathFile = new File(getCredentialsDirectory());
        FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(oathFile);
        dataStore = fileDataStoreFactory.getDataStore(credentialDatastore);
        GoogleAuthorizationCodeFlow flow = getCodeFlow();
        LocalServerReceiver localReceiver = getLocalServerReceiver();

        return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
    }

    private static String getCredentialsDirectory() {
        return System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY;
    }

    private static GoogleAuthorizationCodeFlow getCodeFlow() {
        return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes)
                .setCredentialDataStore(dataStore)
                .build();
    }

    private static LocalServerReceiver getLocalServerReceiver() {
        return new LocalServerReceiver.Builder().setPort(8080).build();
    }
}
