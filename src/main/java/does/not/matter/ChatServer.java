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
		String clientIP = request.getRemoteAddr();
		ChatClient.addClient(new ChatClient(clientIP));

		ChatClient cc = ChatClient.getClientByIP(clientIP);

		if (request.getParameter("room") != null) {
			if (ChatClient.getClientByIP(clientIP).getSelectedRoom() != Long.parseLong(request.getParameter("room"))) {
				ChatClient.changeSelectedRoom(ChatClient.getIdByIP(clientIP), Long.parseLong(request.getParameter("room")));
			}
		}

		ChatRoom.addChatroom(new ChatRoom("Public Room", 0L));
		ChatRoom cr = ChatRoom.getRoomByID(cc.getSelectedRoom());

		request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
		request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
		request.getServletContext().setAttribute("client", ChatClient.getClientByIP(clientIP));
		request.getServletContext().setAttribute("roomname", cr.getRoomName());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("msg");
		System.out.println("Content: " + content);
		if (content != null && !content.trim().equals("")) {

			String clientIP = request.getRemoteAddr();
			ChatClient cc = ChatClient.getClientByIP(clientIP);
			ChatRoom cr = ChatRoom.getRoomByID(cc.getSelectedRoom());

			ChatEntry ce = new ChatEntry(clientIP, content, cc.getSelectedRoom());

			ChatRoom.addChatEntryToRoom(cr.getRoomID(), ce);

			request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
			request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
			request.getServletContext().setAttribute("client", ChatClient.getClientByIP(clientIP));
			request.getServletContext().setAttribute("roomname", cr.getRoomName());
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}
	}
}