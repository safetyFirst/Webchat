package does.not.matter;

import java.net.MalformedURLException;
import java.net.URL;

public class YoutubeChatClient {
	private View view;
	private Client model;

	public YoutubeChatClient() throws MalformedURLException {
		model = new Client(new URL("http://localhost:8080/Chat/ChatServer"));
		view = new View(model);
		view.getFrame().setVisible(true);
	}

	public static void main(String[] args) throws MalformedURLException {
		new YoutubeChatClient();
	}

}