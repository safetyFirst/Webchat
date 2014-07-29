package does.not.matter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatEintrag {
	
	private static long entryCounter = 0; 
	private long entryID;
	
	private String clientID;
	private static List<String> clientIDs = new ArrayList<String>();

	private String ip;
	private int port;

	private String nickname;
	private static Map<String, String> nicklist = new HashMap<String, String>();

	private String content;
	private String alert;
	private boolean msgOutput = false;
	private boolean alertOutput = false;
	
	private List<String> allowedRecipients = new ArrayList<String>();

	public ChatEintrag(String ip, String content) {
		super();
		entryCounter++;
		this.entryID = entryCounter;
		this.ip = ip;
		this.clientID = this.ip;
		this.content = content;

		boolean idExists = false;
		for (String id : clientIDs) {
			if (id.equals(this.clientID)) {
				idExists = true;
			}
		}
		if (!idExists) {
			clientIDs.add(this.clientID);
		}

		if (this.content.charAt(0) == '/') { // Befehl
			String[] inhalt_split = this.content.split("/");
			String[] command_split = inhalt_split[1].split(" ");
			if (command_split[0].equalsIgnoreCase("nick")) {
				this.nickname = command_split[1];
				setNickToMap(this.clientID, this.nickname);
				this.alert = "Nickname wurde geändert zu '" + this.nickname + "'.";
				this.alertOutput = true;
				allowedRecipients.add(this.clientID);
			} else {
				this.alert = "Unbekannter Befehl: " + this.content;
				this.nickname = "[" + this.ip + "]";
				this.alertOutput = true;
				allowedRecipients.add(this.clientID);
			}
		} else { // Nachricht
			if (getNickFromMap(this.clientID) != null) {
				this.nickname = getNickFromMap(this.clientID);
				this.msgOutput = true;
				allowedRecipients.add("*");
			} else {
				this.nickname = "[" + this.ip + "]";
				this.msgOutput = true;
				allowedRecipients.add("*");
			}
		}
	}

	public String getNick() {
		return this.nickname;
	}

	private String getNickFromMap(String id) {
		return nicklist.get(id);
	}

	private void setNickToMap(String id, String nick) {
		nicklist.put(id, nick);
	}

	public String getInhalt() {
		return this.content;
	}

	public String getAlert() {
		return this.alert;
	}

	public boolean getMsgOutput() {
		return this.msgOutput;
	}

	public boolean getAlertOutput() {
		return this.alertOutput;
	}
	
	public List<String> getAllowedRecipients(){
		return this.allowedRecipients;
	}
}