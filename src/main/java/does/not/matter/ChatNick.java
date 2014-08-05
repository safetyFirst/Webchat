package does.not.matter;

import java.util.HashMap;
import java.util.Map;

public class ChatNick {

	public static Map<String, String> nicklist = new HashMap<String, String>();
			
	public static String getNickFromMap(String id) {
		return nicklist.get(id);
	}

	public static void setNickToMap(String id, String nick) {
		nicklist.put(id, nick);
	}
}
