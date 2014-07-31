package does.not.matter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatEintrag {

	private static Long entryCounter = 0L;
	private Long entryID;

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
			if (command_split[0].equalsIgnoreCase("nick")) { // z.B. /nick Marco
				if (command_split[1] != null && !command_split[1].trim().equals("")) {
					if (command_split[1].trim().contains(",")) {
						this.alert = "Der Nickname darf kein Komma [,] enthalten.";
					} else {
						this.nickname = command_split[1].trim();
						setNickToMap(this.clientID, this.nickname);
						this.alert = "Nickname wurde ge�ndert zu '" + this.nickname + "'.";
					}
				} else {
					this.alert = "Nickname muss als Parameter angegeben werden";
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("createRoom")) { // z.B. /createRoom Halle
				if (command_split[1] != null && !command_split[1].trim().equals("")) {
					if (ChatServer.addChatroom(new ChatRoom(command_split[1].trim(), this.clientID))) {
						this.alert = "Der Raum mit dem Namen '" + command_split[1].trim() + "' wurde erfolgreich erstellt.";
					} else {
						this.alert = "Fehler: Der Raum konnte nicht erstellt werden. Raumliste anzeigen mit /listRooms";
					}
				} else {
					this.alert = "Raumname muss als Parameter angegeben werden.";
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("listRooms")) { // listet alle R�ume auf
				ArrayList<ChatRoom> rooms = ChatServer.getRooms();
				if (rooms.size() == 0) {
					this.alert = "Es existieren momentan keine R�ume.";
				} else {
					this.alert = "CHATROOMS:\n";
					for (ChatRoom chatRoom : rooms) {
						this.alert += chatRoom.getRoomID().toString() + " - " + chatRoom.getRoomName() + "\n";
					}
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("listNicks")) {
				Collection<String> nicks = nicklist.values();
				if (nicks.isEmpty()) {
					this.alert = "Es existieren momentan keine Nicknames.";
				} else {
					ArrayList<String> nicknames = new ArrayList<String>(nicks);
					this.alert = "NICKNAMES:\n";
					for (int i = 0; i < nicknames.size(); i++) {
						this.alert += nicknames.get(i);
						if (i < (nicknames.size() - 1)) {
							this.alert += ", ";
						}
					}
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("help")) {
				this.alert = "COMMANDS:\n";
				this.alert += "/nick\n";
				this.alert += "/listNicks\n";
				this.alert += "/createRoom\n";
				this.alert += "/listRooms\n";
				this.alert += "/help\n";
				this.alertOutput = true;
			} else {
				this.alert = "Unbekannter Befehl: " + this.content;
				this.nickname = "[" + this.ip + "]";
				this.alertOutput = true;
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
		allowedRecipients.add(this.clientID);
	}
	
	public String getClientID(){
		return this.clientID;
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

	public List<String> getAllowedRecipients() {
		return this.allowedRecipients;
	}
}