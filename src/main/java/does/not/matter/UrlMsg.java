package does.not.matter;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class URL
 */

@WebServlet("/URL")
public class UrlMsg extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UrlMsg() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkURL(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		checkURL(request, response);
	}

	private void checkURL(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletOutputStream out = response.getOutputStream();
		String address = request.getParameter("url").trim();
		if(address.matches("(^http://|^https://|^ftp://|^file:///).*")){
			out.print("OK");
		}
		
		/*URL url = new URL(address);
		InputStream is = null;
		try {
			is = url.openStream();
			out.print("OK");
		} catch (MalformedURLException mue) {
			out.print("MalformedException");
		} catch (IOException ioe) {
			out.print("IOException");
		} finally {
			try {
				if (is != null) {
					is.close();
				}
			} catch (IOException ioe) {

			}
		}*/
	}
}
