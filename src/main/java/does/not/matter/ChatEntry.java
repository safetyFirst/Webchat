package does.not.matter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatEntry {

	private static Long entryCounter = 0L;
	private Long entryID;

	private String room;

	private Long clientID;
	private String clientIP;

	private String content;
	private String alert;
	private boolean msgOutput = false;
	private boolean alertOutput = false;

	private List<Long> allowedRecipients = new ArrayList<Long>();

	public ChatEntry(String ip, String content, String room) {
		super();
		entryCounter++;
		this.entryID = entryCounter;
		this.room = room;
		this.content = content;
		this.clientIP = ip;
		this.clientID = ChatClient.getIdByIP(ip);

		if (this.content.charAt(0) == '/') { // Befehl
			String[] inhalt_split = this.content.split("/");
			String[] command_split = inhalt_split[1].split(" ");
			if (command_split[0].equalsIgnoreCase("nick")) { // z.B. /nick Marco
				if (command_split[1] != null && !command_split[1].trim().equals("")) {
					if (command_split[1].trim().contains(",")) {
						this.alert = "Der Nickname darf kein Komma [,] enthalten.";
					} else {
						String nickname = command_split[1].trim();
						String oldNickname = ChatClient.getNickByID(this.clientID);

						if (ChatClient.setNickByID(nickname, this.clientID)) {
							if (oldNickname != null) {
								this.alert = "'" + oldNickname + "' hat seinen Nickname zu '" + nickname + "' geändert.";
							} else {
								this.alert = "[" + this.clientIP + "]" + " hat seinen Nickname zu '" + nickname + "' geändert.";
							}
						} else {
							this.alert = "Der Nickname '" + nickname + "' ist bereits vergeben.";
						}

						allowedRecipients.add(-1L);
					}
				} else {
					this.alert = "Nickname muss als Parameter angegeben werden";
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("createRoom")) { // z.B. /createRoom Halle
				if (command_split[1] != null && !command_split[1].trim().equals("")) {
					if (ChatRoom.addChatroom(new ChatRoom(command_split[1].trim(), this.clientID))) {
						this.alert = "Der Raum mit dem Namen '" + command_split[1].trim() + "' wurde erfolgreich erstellt.";
					} else {
						this.alert = "Fehler: Der Raum konnte nicht erstellt werden. Raumliste anzeigen mit /listRooms";
					}
				} else {
					this.alert = "Raumname muss als Parameter angegeben werden.";
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("listRooms")) { // listet alle Räume auf
				ArrayList<ChatRoom> rooms = ChatRoom.getRooms();
				if (rooms.size() == 0) {
					this.alert = "Es existieren momentan keine Räume.";
				} else {
					this.alert = "CHATROOMS:\n";
					for (ChatRoom chatRoom : rooms) {
						this.alert += chatRoom.getRoomID().toString() + " - " + chatRoom.getRoomName() + "\n";
					}
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("listNicks")) {
				Collection<String> nicks = ChatClient.nicklist.values();
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
				this.alertOutput = true;
			}
		} else { // Nachricht
			this.msgOutput = true;
			allowedRecipients.add(-1L);
		}
		allowedRecipients.add(this.clientID);
	}

	public Long getClientID() {
		return this.clientID;
	}

	public String getNick() {
		return ChatClient.getNickByID(this.clientID);
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

	public List<Long> getAllowedRecipients() {
		return this.allowedRecipients;
	}
}