<%@page import="does.not.matter.ChatEintrag"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Webchat</title>
<script src='http://code.jquery.com/jquery-2.1.1.min.js'></script>
</head>
<body style='margin: 0; padding: 0;'>

	<div id='verlauf' style='overflow: auto; height: 78%; width: 96%; padding: 2% 2% 2% 2%; background: black; color: white; font-family: Consolas, sans-serif;'>
		<%
			ArrayList<ChatEintrag> chatverlauf = (ArrayList<ChatEintrag>) application.getAttribute("chatverlauf");

			if(chatverlauf != null){
				for (ChatEintrag ce : chatverlauf) {
					ArrayList<String> allowedRecipients = new ArrayList<String>(ce.getAllowedRecipients());
					if(allowedRecipients.get(0).equals("*") || allowedRecipients.contains(request.getRemoteAddr())){					
						if (ce.getMsgOutput()) {
							
							String[] contentLines = ce.getInhalt().split("\n");
							int lines = contentLines.length;
							
							out.println("<table id='msgOutput' border='0'>");				
							if(lines > 1){
								out.println("<tr><th rowspan='"+ lines +"'>" + ce.getNick() + "</th><td>" + contentLines[0] + "</td></tr>");
								for(int i = 1; i < lines; i++){
									out.println("<tr><td>" + contentLines[i] + "</td></tr>");
								}
							}else {
								out.println("<tr><th>" + ce.getNick() + "</th><td>" + contentLines[0] + "</td></tr>");
							}
							out.println("</table>");
						}
						if (ce.getAlertOutput()) {
							out.println("<p style='color:red; font-weight: bold;'>System | " + ce.getAlert() + "</p>");
						}
					}
				}
			}
		%>
	</div>

	<form style='margin: 0; position: fixed; clear: both; height: 6%; width: 96%; padding: 2%; font-family: Consolas, sans-serif; background: darkgrey;' action='ChatServer' method='post' name='sndMsg' id='formMsg'>
		<textarea autofocus rows='3' cols='100' name='msg' id='textarea' style='resize: none;'></textarea>
		<input id='sndMsgBtn' type='submit' value='Senden' name='send' />
	</form>

</body>
<script>
	$(document).ready(function() {
		$('textarea#textarea').keypress(function(e) {
			if (e.keyCode == 13 && e.shiftKey) {

			} else if (e.which == 13) {
				alert(trim($(this).val()));
				/*if(trim($(this).val()) != ""){					
					$('form#formMsg').submit();
				}*/				
			}
		});
	});
</script>

<style>
table#msgOutput {
	margin-top: 20px
}

table#msgOutput th {
	vertical-align: top;
	width: 200px;
	max-width: 200px;
}

table#msgOutput td {
	padding-left: 50px;
}
</style>
</html>