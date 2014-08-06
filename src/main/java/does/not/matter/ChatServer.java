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
@WebServlet("/Chat")
public class ChatServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChatRoom.addChatroom(new ChatRoom("Public Room", 0L));
		ChatRoom cr = ChatRoom.getRoomByName("Public Room");

		String clientIP = request.getRemoteAddr();
		ChatClient.addClient(new ChatClient(clientIP));

		request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
		request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
		request.getServletContext().setAttribute("nickname", ChatClient.getNickByIP(clientIP));
		request.getServletContext().setAttribute("clientID", ChatClient.getIdByIP(clientIP));
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("msg");
		System.out.println("Content: " + content);
		if (content != null && !content.trim().equals("")) {

			String clientIP = request.getRemoteAddr();
			String roomName = request.getParameter("room");

			ChatEntry ce = new ChatEntry(clientIP, content, roomName);
			ChatRoom cr = ChatRoom.getRoomByName(roomName);
			ChatRoom.addChatEntryToRoom(cr.getRoomID(), ce);

			request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
			request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
			request.getServletContext().setAttribute("nickname", ChatClient.getNickByIP(clientIP));
			request.getServletContext().setAttribute("clientID", ChatClient.getIdByIP(clientIP));
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}
}