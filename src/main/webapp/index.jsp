<%@page import="does.not.matter.ChatEintrag"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css" type="text/css">
<title>Webchat</title>
<script src='http://code.jquery.com/jquery-2.1.1.min.js'></script>
</head>
<body style='margin: 0; padding: 0;'>

	<img src='dbad_transparent.gif' id='dbad-logo' />
	<div id='menu'></div>
	<div id='verlauf'>
		<%
			ArrayList<ChatEintrag> chatverlauf = (ArrayList<ChatEintrag>) application.getAttribute("chatverlauf");
			
			if(chatverlauf != null){				
				for (ChatEintrag ce : chatverlauf) {
					ArrayList<String> allowedRecipients = new ArrayList<String>(ce.getAllowedRecipients());
					if((allowedRecipients.get(0) != null && allowedRecipients.get(0).equals("*") ) || allowedRecipients.contains(request.getRemoteAddr())){					
						if (ce.getMsgOutput()) {
							String ownMsg = "";
							if(ce.getClientID().equalsIgnoreCase(request.getRemoteAddr())){
								ownMsg = "class='ownMsg'";
							}
							
							String[] contentLines = ce.getInhalt().split("\n");
							int length = contentLines.length;
														
							out.println("<table id='msgOutput' border='0'>");				
							if(length > 1){
								out.println("<tr><th rowspan='"+ length +"'>" + ce.getNick() + "</th><td " + ownMsg + " >" + contentLines[0] + "</td></tr>");
								for(int i = 1; i < length; i++){
									out.println("<tr><td " + ownMsg + " >" + contentLines[i] + "</td></tr>");
								}
							}else {
								out.println("<tr><th>" + ce.getNick() + "</th><td " + ownMsg + " >" + contentLines[0] + "</td></tr>");
							}
							out.println("</table>");
						}
						if (ce.getAlertOutput()) {
							String[] alertLines = ce.getAlert().split("\n");
							int length = alertLines.length;
							for(int i = 0; i < length; i++){
								out.println("<p style='color:red; font-weight: bold;'>System | <span style='color: lightgreen;'>" + alertLines[i] + "</span></p>");
							}							
						}
					}			
				}
			}
		%>
	</div>

	<form action='ChatServer' method='post' name='sndMsg' id='formMsg'>
		<textarea autofocus rows='3' cols='100' name='msg' id='textarea'></textarea>
		<input id='sndMsgBtn' type='submit' value='Senden' name='send' />
	</form>

</body>
<script>
	$(document).ready(function() {
		$('textarea#textarea').keypress(function(e) {
			if (e.which == 13 && e.shiftKey) {
			} else if (e.which == 13) {
				if ($.trim($(this).val()) != "") {
					$('form#formMsg').submit();
				}
			}
		});

		$('div#verlauf').scrollTop($('div#verlauf')[0].scrollHeight);

		$("div#verlauf td").each(function() {
			var p = $(this);

			$.ajax({
				type : "POST",
				url : "URL",
				data : {
					entry : p[0].innerHTML
				}
			}).done(function(data) {
				/*if (data == "OK") {
					p.html("<a target='_blank' href='" + p[0].innerHTML + "'>" + p[0].innerHTML + "</a>");					
				}*/

				if (data != "") {
					var urls = data.split("|");
					for (var i = 0; i < urls.length; i++) {
						var wwwCheck = urls[i].substring(0,4); // www.
						if (wwwCheck == "www.") {
							alert(wwwCheck);
							p.html(p[0].innerHTML.replace(urls[i], "<a target='_blank' href='http://" + urls[i] + "'>" + urls[i] + "</a>"));
						} else {
							p.html(p[0].innerHTML.replace(urls[i], "<a target='_blank' href='" + urls[i] + "'>" + urls[i] + "</a>"));
						}
					}
				}
			});
		});
	});
</script>

<style>
</style>
</html>