package builder;

import java.io.IOException;

public interface HttpURLConnectionBuilder {

	void openConnection()  throws IOException;
	void setRequestMethod(String requestMethod) throws IOException;
	void setOutput(boolean isOutputConnection);
	void setRequestProperty(String key, String value) throws IOException;
}
