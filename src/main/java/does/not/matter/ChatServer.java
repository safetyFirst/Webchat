package does.not.matter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ChatServer
 */
@WebServlet("/ChatServer")
public class ChatServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private static ArrayList<ChatEntry> chatverlauf = new ArrayList<ChatEntry>();
	private static ArrayList<ChatRoom> chatrooms = new ArrayList<ChatRoom>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatServer() {
		super();
		chatverlauf = new ArrayList<ChatEntry>();
		chatrooms = new ArrayList<ChatRoom>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		request.setAttribute("chatverlauf", chatverlauf);
		request.getServletContext().setAttribute("chatverlauf", chatverlauf);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("msg");
		System.out.println("Content: " + content);
		if (content != null && !content.trim().equals("")) {

			String clientIP = request.getRemoteAddr();

			request.getServletContext().setAttribute("chatverlauf", chatverlauf);
			ChatEntry ce = new ChatEntry(clientIP, content);
			chatverlauf.add(ce);
			System.out.println("add chat entry");
			//		request.setAttribute("chatverlauf", chatverlauf);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}

	public static ArrayList<ChatRoom> getRooms(){
		return chatrooms;
	}
	
	public static boolean addChatroom(ChatRoom cr) {
		try {
			for (ChatRoom room : chatrooms) {
				if(room.getRoomName().equalsIgnoreCase(cr.getRoomName())){
					return false;
				}
			}
			chatrooms.add(cr);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}