package DAO;

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

public class Auth {

	public static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
	public static final JsonFactory JSON_FACTORY = new JacksonFactory();
	private static final String CREDENTIALS_DIRECTORY = ".oauth-credentials";

	public static Credential authorize(List<String> scopes, String credentialDatastore) throws IOException {
		InputStream inputStream = Auth.class.getResourceAsStream("/client_secrets.json");
		Reader clientSecretReader = new InputStreamReader(inputStream);
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, clientSecretReader);
		File oathFile = new File(System.getProperty("user.home") + "/" + CREDENTIALS_DIRECTORY);
		FileDataStoreFactory fileDataStoreFactory = new FileDataStoreFactory(oathFile);
		DataStore<StoredCredential> datastore = fileDataStoreFactory.getDataStore(credentialDatastore);
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
				HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, scopes).setCredentialDataStore(datastore)
				.build();
		LocalServerReceiver localReceiver = new LocalServerReceiver.Builder().setPort(8080).build();

		return new AuthorizationCodeInstalledApp(flow, localReceiver).authorize("user");
	}
}
