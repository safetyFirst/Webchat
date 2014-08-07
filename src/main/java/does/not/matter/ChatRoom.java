package does.not.matter;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

	private static Long roomCounter = 0L;
	private Long roomID;
	private String roomName;
	private Long ownerID;
	private List<Long> allowedMembers = new ArrayList<Long>();

	private static ArrayList<ChatRoom> chatrooms = new ArrayList<ChatRoom>();

	private ArrayList<ChatEntry> chatverlauf;

	public ChatRoom(String roomName, Long ownerID) {
		roomCounter++;
		this.roomID = roomCounter;
		this.roomName = roomName;
		this.ownerID = ownerID;
		this.allowedMembers.add(ownerID);
		chatverlauf = new ArrayList<ChatEntry>();
	}

	public static void addChatEntryToRoom(Long roomID, ChatEntry ce) {
		for (ChatRoom chatRoom : chatrooms) {
			if (chatRoom.getRoomID() == roomID) {
				chatRoom.addChatEntry(ce);
			}
		}
	}

	public void addChatEntry(ChatEntry ce) {
		this.chatverlauf.add(ce);
	}

	public ArrayList<ChatEntry> getChatverlauf() {
		return this.chatverlauf;
	}

	public static ArrayList<ChatRoom> getRooms() {
		return chatrooms;
	}

	public static ChatRoom getRoomByID(Long id) {
		for (ChatRoom chatRoom : chatrooms) {
			if (chatRoom.getRoomID() == id) {
				return chatRoom;
			}
		}
		return null;
	}

	public static ChatRoom getRoomByName(String name) {
		for (ChatRoom chatRoom : chatrooms) {
			if (chatRoom.getRoomName().equals(name)) {
				return chatRoom;
			}
		}
		return null;
	}

	public static boolean addChatroom(ChatRoom cr) {
		for (ChatRoom room : chatrooms) {
			if (room.getRoomName().equalsIgnoreCase(cr.getRoomName())) {
				return false;
			}
		}
		chatrooms.add(cr);
		return true;
	}

	public static boolean deleteChatroom(ChatRoom cr) {
		if (cr != null) {
			if (cr.getRoomID() != 1L) {
				chatrooms.remove(cr);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public Long getRoomID() {
		return this.roomID;
	}

	public String getRoomName() {
		return this.roomName;
	}

	public Long getOwnerID() {
		return this.ownerID;
	}

	public List<Long> getAllowedMembers() {
		return this.allowedMembers;
	}
}
