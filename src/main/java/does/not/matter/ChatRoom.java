package does.not.matter;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

	private static Long entryCounter = 0L;
	private Long roomID;
	private String roomName;
	private String ownerID;
	private List<String> allowedMembers = new ArrayList<String>();

	public ChatRoom(String roomName, String ownerID) {
		entryCounter++;
		this.roomID = entryCounter;
		this.roomName = roomName;
		this.ownerID = ownerID;
		this.allowedMembers.add(ownerID);
	}

	public Long getRoomID() {
		return this.roomID;
	}

	public String getRoomName() {
		return this.roomName;
	}

	public List<String> getAllowedMembers() {
		return this.allowedMembers;
	}
}
