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
@WebServlet("/Chat2")
public class ChatServer2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*String clientIP = request.getRemoteAddr();

		ChatClient.addClient(new ChatClient(clientIP));

		ChatClient cc = ChatClient.getClientByIP(clientIP);

		if (request.getParameter("room") != null) {
			if (cc.getSelectedRoom() != Long.parseLong(request.getParameter("room"))) {
				ChatClient.changeSelectedRoom(cc.getClientID(), Long.parseLong(request.getParameter("room")));
			}
		}

		ChatRoom.addChatroom(new ChatRoom("Public Room", 0L));
		ChatRoom cr = ChatRoom.getRoomByID(cc.getSelectedRoom());
		String alertMessage = "";
		if (cr == null) {
			alertMessage = "Der zuletzt gewählte Raum existiert nicht mehr.";
			cr = ChatRoom.getRoomByID(1L);
			ChatClient.changeSelectedRoom(cc.getClientID(), 1L);
		}

		request.getServletContext().setAttribute("alertMessage", alertMessage);
		request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
		request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
		request.getServletContext().setAttribute("client", ChatClient.getClientByIP(clientIP));
		request.getServletContext().setAttribute("roomname", cr.getRoomName());
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		*/
		manageRequests('g', request, response);
	}

	private void manageRequests(char method, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean roomDeletedMsg = false;
		String content = null;
		if (method == 'p') {
			content = request.getParameter("msg");
		}

		if ((method == 'p' && content != null && !content.trim().equals("")) || (method == 'g')) {

			String clientIP = request.getRemoteAddr();
			if (method == 'g') {
				ChatClient.addClient(new ChatClient(clientIP));
			}
			ChatClient cc = ChatClient.getClientByIP(clientIP);

			if (method == 'g') {
				if (request.getParameter("room") != null) {
					if (cc.getSelectedRoom() != Long.parseLong(request.getParameter("room"))) {
						ChatClient.changeSelectedRoom(cc.getClientID(), Long.parseLong(request.getParameter("room")));
					}
				}
				ChatRoom.addChatroom(new ChatRoom("Public Room", 0L));
			}

			ChatRoom cr = ChatRoom.getRoomByID(cc.getSelectedRoom());
			String alertMessage = "";
			if (cr == null) {
				alertMessage += "Der zuletzt gewählte Raum existiert nicht mehr. \\n";
				if (content != null) {
					alertMessage += "Deine letzte Nachricht wurde nicht gesendet: \\n\\n'" + content + "'";
				}
				System.out.println("alertMessage: " + alertMessage);
				roomDeletedMsg = true;
				cr = ChatRoom.getRoomByID(1L);
				ChatClient.changeSelectedRoom(cc.getClientID(), 1L);
			}

			if (method == 'p' && !roomDeletedMsg) {
				ChatEntry ce = new ChatEntry(clientIP, content, cc.getSelectedRoom());

				if (cr.getRoomID() != 1L && ce.getAlertOutput()) {
					if (!ChatRoom.addChatEntryToRoom(cr.getRoomID(), ce)) {
						alertMessage += ce.getAlert();
					}
					if (ce.getNickChange()) {
						if (!ChatRoom.addChatEntryToRoom(1L, ce)) {
							alertMessage += ce.getAlert();
						}
					}
				} else {
					if (!ChatRoom.addChatEntryToRoom(cr.getRoomID(), ce)) {
						alertMessage += ce.getAlert();
					}
				}
			}

			request.getServletContext().setAttribute("alertMessage", alertMessage);
			request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
			request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
			request.getServletContext().setAttribute("client", ChatClient.getClientByIP(clientIP));
			request.getServletContext().setAttribute("clientID", ChatClient.getIdByIP(clientIP));
			request.getServletContext().setAttribute("nickname", ChatClient.getNickByIP(clientIP));
			request.getServletContext().setAttribute("roomname", cr.getRoomName());
			request.getRequestDispatcher("/chatform.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		manageRequests('p', request, response);
		/*String content = request.getParameter("msg");

		if (content != null && !content.trim().equals("")) {

			String clientIP = request.getRemoteAddr();
			ChatClient cc = ChatClient.getClientByIP(clientIP);
			ChatRoom cr = ChatRoom.getRoomByID(cc.getSelectedRoom());
			String alertMessage = "";
			if (cr == null) {
				alertMessage = "Der zuletzt gewählte Raum existiert nicht mehr.";
				cr = ChatRoom.getRoomByID(1L);
				ChatClient.changeSelectedRoom(cc.getClientID(), 1L);
			}

			ChatEntry ce = new ChatEntry(clientIP, content, cc.getSelectedRoom());
			if (cr.getRoomID() != 1L && ce.getAlertOutput()) {
				ChatRoom.addChatEntryToRoom(cr.getRoomID(), ce);
				ChatRoom.addChatEntryToRoom(1L, ce);
			} else {
				ChatRoom.addChatEntryToRoom(cr.getRoomID(), ce);
			}

			request.getServletContext().setAttribute("alertMessage", alertMessage);
			request.getServletContext().setAttribute("chatrooms", ChatRoom.getRooms());
			request.getServletContext().setAttribute("chatverlauf", cr.getChatverlauf());
			request.getServletContext().setAttribute("client", ChatClient.getClientByIP(clientIP));
			request.getServletContext().setAttribute("roomname", cr.getRoomName());
			request.getRequestDispatcher("/index.jsp").forward(request, response);
		}*/
	}
}