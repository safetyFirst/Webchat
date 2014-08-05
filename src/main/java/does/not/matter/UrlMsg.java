package does.not.matter;

import java.io.IOException;

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
		String entry = request.getParameter("entry").trim();
		if (entry.matches(".*http://.*") || entry.matches(".*https://.*") || entry.matches(".*ftp://.*") || entry.matches(".*file:///.*") || entry.matches(".*www..*")) {

			String[] address_split = entry.split(" ");
			String urls = "";

			for (int i = 0; i < address_split.length; i++) {
				if (address_split[i].matches("(^http://|^https://|^ftp://|^file:///|^www.).*")) {
					if (urls.equals("")) {
						urls += address_split[i];
					} else {
						urls += "|" + address_split[i];
					}
				}
			}
			out.print(urls);
		}

		/*File f = new File(address.replace("file:///", ""));
		System.out.println("Exists: " + f.exists());

		HttpClient client = new HttpClient();
		
		HttpMethod method = new GetMethod(address);	
		client.setTimeout(1000);
		client.executeMethod(method);
		System.out.println(method.getStatusCode());		

		URL url = new URL(address);
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
