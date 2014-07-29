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

	ArrayList<ChatEintrag> chatverlauf = new ArrayList<ChatEintrag>();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChatServer() {
		super();
		chatverlauf = new ArrayList<ChatEintrag>();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//		request.setAttribute("chatverlauf", chatverlauf);
		request.getServletContext().setAttribute("chatverlauf", chatverlauf);
		request.getRequestDispatcher("/index.jsp").forward(request, response);
		//getChat(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String content = request.getParameter("msg");
		System.out.println("Content: " + content);
		if (!content.equals("") || content != null) {

			String clientIP = request.getRemoteAddr();

			request.getServletContext().setAttribute("chatverlauf", chatverlauf);
			ChatEintrag ce = new ChatEintrag(clientIP, content);
			chatverlauf.add(ce);
			System.out.println("add chat entry");
			//		request.setAttribute("chatverlauf", chatverlauf);
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			//getChat(request, response);
		}
	}

	private void getChat(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		out.println("<html style='margin:0; padding:0;'><head><title>Webchat</title><script src='http://code.jquery.com/jquery-1.11.1.min.js'></script></head><body style='margin:0; padding:0;'>");
		out.println("<div id='verlauf' style='overflow:auto; height:78%; width:96%; padding:0% 2% 2% 2%; background:black; color: white; font-family: Consolas, sans-serif;'>");
		for (ChatEintrag ce : chatverlauf) {
			if (ce.getMsgOutput()) {
				out.println("<p>" + ce.getNick() + " | " + ce.getInhalt() + "</p>");
			}
			if (ce.getAlertOutput()) {
				out.println("<p>" + ce.getNick() + " | " + ce.getAlert() + "</p>");
			}
		}
		out.println("</div>");

		out.println("<form style='margin:0; position=fixed; clear:both; height: 6%; width=96%; padding:2%; font-family: Consolas, sans-serif; background: darkgrey; color; white;' action='/Chat/ChatServer' method='post' name='sndMsg' id='formMsg'>");
		out.println("<textarea rows='3' cols='100' name='msg' id='textarea'></textarea>");
		out.println("<input id='sndMsgBtn' type='submit' value='Senden' name='send' />");
		out.println("</form>");

		out.println("</body></html>");
		out.println("<script>$('#textarea').keypress(function( event ) {if ( event.which == 13 ) { event.preventDefault();   }</script>");
		out.close();
	}

}