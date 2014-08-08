package does.not.matter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class ChatEntry {

	private static Long entryCounter = 0L;
	private Long entryID;

	private Long room;

	private Long clientID;
	private String clientIP;

	private Date time;
	private String content;
	private String alert;
	private boolean msgOutput = false;
	private boolean alertOutput = false;
	private boolean nickChange = false;

	private List<Long> allowedRecipients = new ArrayList<Long>();

	public ChatEntry(String ip, String content, Long room) {
		super();
		entryCounter++;
		this.entryID = entryCounter;
		this.room = room;
		this.time = new Date();
		this.content = content;
		this.clientIP = ip;
		this.clientID = ChatClient.getIdByIP(ip);

		if (this.content.charAt(0) == '/') { // Befehl
			String[] inhalt_split = this.content.split("/");
			String[] command_split = inhalt_split[1].split(" ");
			if (command_split[0].equalsIgnoreCase("nick")) { // z.B. /nick Marco
				if (command_split.length > 1 && command_split[1] != null && !command_split[1].trim().equals("")) {
					if (command_split[1].trim().contains(",")) {
						this.alert = "[nick] Der Nickname darf kein Komma [,] enthalten.";
					} else {
						String nickname = command_split[1].trim();
						String oldNickname = ChatClient.getNickByID(this.clientID);

						if (ChatClient.setNickByID(nickname, this.clientID)) {
							if (oldNickname != null) {
								this.alert = "[nick] '" + oldNickname + "' hat seinen Nickname zu '" + nickname + "' ge�ndert.";
							} else {
								this.alert = "[nick] [" + this.clientIP + "]" + " hat seinen Nickname zu '" + nickname + "' ge�ndert.";
							}
						} else {
							this.alert = "[nick] Der Nickname '" + nickname + "' ist bereits vergeben.";
						}

						allowedRecipients.add(-1L);
					}
				} else {
					this.alert = "[nick] Nickname muss als Parameter angegeben werden";
				}
				this.alertOutput = true;
				this.nickChange = true;
			} else if (command_split[0].equalsIgnoreCase("createRoom")) { // z.B. /createRoom Halle
				if (command_split.length > 1 && command_split[1] != null && !command_split[1].trim().equals("")) {
					if (ChatRoom.addChatroom(new ChatRoom(command_split[1].trim(), this.clientID))) {
						this.alert = "[createRoom] Der Raum mit dem Namen '" + command_split[1].trim() + "' wurde erstellt.";
					} else {
						this.alert = "[createRoom] Fehler: Der Raum konnte nicht erstellt werden. Raumliste anzeigen mit /listRooms";
					}
				} else {
					this.alert = "[createRoom] Raumname muss als Parameter angegeben werden.";
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("deleteRoom")) {
				try {
					if (command_split.length > 1 && command_split[1] != null && !command_split[1].trim().equals("")) {
						Long param = Long.parseLong(command_split[1].trim());
						ChatRoom roomToDelete = ChatRoom.getRoomByID(param);
						if (ChatRoom.deleteChatroom(roomToDelete)) {
							this.alert = "[deleteRoom] Der Raum mit dem Namen '" + roomToDelete.getRoomName() + "' (" + roomToDelete.getRoomID() + ") wurde erfolgreich gel�scht.";
						} else {
							this.alert = "[deleteRoom] Fehler: Der Raum konnte nicht gel�scht werden. Raumliste anzeigen mit /listRooms";
						}
					} else {
						this.alert = "[deleteRoom] Es muss die Raum-ID als Parameter angegeben werden.";
					}
				} catch (NumberFormatException nfe) {
					this.alert = "[deleteRoom] Es muss eine Ganzzahl f�r die Raum-ID angegeben werden.";
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("listRooms")) { // listet alle R�ume auf
				ArrayList<ChatRoom> rooms = ChatRoom.getRooms();
				if (rooms.size() == 0) {
					this.alert = "[listRooms] Es existieren momentan keine R�ume.";
				} else {
					this.alert = "CHATROOMS:\n";
					for (ChatRoom chatRoom : rooms) {
						this.alert += chatRoom.getRoomID().toString() + " - " + chatRoom.getRoomName() + "\n";
					}
				}
				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("listNicks")) {
				ArrayList<String> names = ChatClient.getNicknames();
				ArrayList<ChatClient> clients = ChatClient.getClients();
				if (clients.size() == 0) {
					this.alert = "[listNicks] Es existieren momentan keine Nicknamen.";
				} else {
					this.alert = "NICKNAMES:\n";
					for (ChatClient chatClient : clients) {
						this.alert += chatClient.getClientID().toString() + " - " + chatClient.getNickname() + "\n";
					}
				}

				this.alertOutput = true;
			} else if (command_split[0].equalsIgnoreCase("help")) {
				this.alert = "COMMANDS:\n";
				this.alert += "/nick &lt;nickName&gt;\n";
				this.alert += "/listNicks\n";
				this.alert += "/createRoom &lt;roomName&gt;\n";
				this.alert += "/deleteRoom &lt;roomID&gt;\n";
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

	public Date getTime() {
		return this.time;
	}

	public String getContent() {
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

	public boolean getNickChange() {
		return this.nickChange;
	}

	public List<Long> getAllowedRecipients() {
		return this.allowedRecipients;
	}
}