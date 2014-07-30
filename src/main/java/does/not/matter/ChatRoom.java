package does.not.matter;

import java.util.ArrayList;
import java.util.List;

public class ChatRoom {

	private static Long entryCounter = 0L;
	private Long roomID;
	private String roomName;
	private String ownerID;
	private List<Long> allowedMembers = new ArrayList<Long>();

	public ChatRoom(String roomName, String ownerID) {
		entryCounter++;
		this.roomID = entryCounter;
		this.roomName = roomName;
		this.ownerID = ownerID;
	}

	public Long getRoomID() {
		return this.roomID;
	}

	public String getRoomName() {
		return this.roomName;
	}

	public List<Long> getAllowedMembers() {
		return this.allowedMembers;
	}
}
