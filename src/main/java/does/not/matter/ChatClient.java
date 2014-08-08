package does.not.matter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatClient {

	private static Long clientCounter = 0L;
	private Long clientID;
	private String clientIP;
	private String nickname;
	private Long selectedRoom;

	private static ArrayList<ChatClient> clients = new ArrayList<ChatClient>();

	public ChatClient(String clientIP) {
		if (ChatClient.getIdByIP(clientIP) == null) {
			this.clientIP = clientIP;
			clientCounter++;
			this.clientID = clientCounter;
			this.nickname = "Guest" + this.clientID;
			this.selectedRoom = 1L;
		}
	}

	public static ArrayList<String> getNicknames() {
		ArrayList<String> names = new ArrayList<String>();
		for (ChatClient chatClient : clients) {
			names.add(chatClient.getNickname());
		}
		return names;
	}

	public static String getNickByID(Long id) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getClientID().equals(id)) {
				return chatClient.getNickname();
			}
		}
		return null;
	}

	public static boolean setNickByID(String nick, Long id) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getClientID().equals(id)) {
				chatClient.setNickname(nick);
				return true;
			}
		}
		return false;
	}

	public static String getNickByIP(String ip) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getClientIP().equals(ip)) {
				return chatClient.getNickname();
			}
		}
		return null;
	}

	public static Long getIdByIP(String ip) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getClientIP().equals(ip)) {
				return chatClient.getClientID();
			}
		}
		return null;
	}

	public static ChatClient getClientByName(String name) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getNickname().equals(name)) {
				return chatClient;
			}
		}
		return null;
	}

	public static ChatClient getClientByIP(String ip) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getClientIP().equals(ip)) {
				return chatClient;
			}
		}
		return null;
	}

	public static boolean addClient(ChatClient cc) {
		if (cc.getClientID() != null) {
			clients.add(cc);
			return true;
		} else {
			return false;
		}
	}

	public static ArrayList<ChatClient> getClients() {
		return clients;
	}

	public String getClientIP() {
		return this.clientIP;
	}

	public Long getClientID() {
		return this.clientID;
	}

	public String getNickname() {
		return this.nickname;
	}

	private void setNickname(String name) {
		this.nickname = name;
	}

	public Long getSelectedRoom() {
		return this.selectedRoom;
	}

	private void setSelectedRoom(Long id) {
		this.selectedRoom = id;
	}

	public static void changeSelectedRoom(Long clientID, Long sRoomID) {
		for (ChatClient chatClient : clients) {
			if (chatClient.getClientID() == clientID) {
				chatClient.setSelectedRoom(sRoomID);
			}
		}
	}
}