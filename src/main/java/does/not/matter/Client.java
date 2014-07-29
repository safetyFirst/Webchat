package does.not.matter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import com.oreilly.servlet.HttpMessage;

public class Client {
	private URL url;

	/**
	 * @param url
	 */
	public Client(URL url) {
		super();
		this.url = url;
	}

	public String getGet() {
		String r = "";
		try {
			URLConnection c = url.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(c.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null)
				r += inputLine + "\n";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return r;
	}

	public void sendPost(String nickname, String inhalt) {
		try {
			Properties props = new Properties();
			props.put("nickname", nickname);
			props.put("inhalt", inhalt);

			HttpMessage msg = new HttpMessage(url);
			msg.sendPostMessage(props);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}