<%@page import="does.not.matter.ChatRoom"%>
<%@page import="does.not.matter.ChatEntry"%>
<%@page import="does.not.matter.ChatClient"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css" type="text/css">
<link rel="stylesheet" href="jquery.mCustomScrollbar.css" />
<script src='http://code.jquery.com/jquery-2.1.1.min.js'></script>
<script src="jquery.mCustomScrollbar.concat.min.js"></script>
<title>Webchat</title>
</head>
<body style='margin: 0; padding: 0;'>

	<img src='dbad_transparent.gif' id='dbad-logo' />
	<div id='menu'>
		<%
			String actRoom = "Public Room";
			ArrayList<ChatRoom> chatrooms = (ArrayList<ChatRoom>) application.getAttribute("chatrooms");
			String roomname = (String) application.getAttribute("roomname");
			for(ChatRoom cr : chatrooms){
				String classSelected = "";
				if(cr.getRoomName().equals(roomname)){
					classSelected = "selected";					
				}
				out.print("<p><a class='" + classSelected + "' href='?room=" + cr.getRoomID() + "'>" + cr.getRoomName() + "</a></p>");
			}
		%>
	</div>
	<div id='verlauf' room='Public Room'>
		<%
			String alertMessage = (String) application.getAttribute("alertMessage");				
			ArrayList<ChatEntry> chatverlauf = (ArrayList<ChatEntry>) application.getAttribute("chatverlauf");				
			ChatClient client =  (ChatClient) application.getAttribute("client");
			String nickname = client.getNickname();
			Long clientID = client.getClientID();
		%>
		<div id="roomname">
			<p>
				<%
					out.print(roomname);
				%>
			</p>
		</div>
		<%
			if(chatverlauf != null){				
				for (ChatEntry ce : chatverlauf) {
					ArrayList<Long> allowedRecipients = new ArrayList<Long>(ce.getAllowedRecipients());
					if((allowedRecipients.get(0) != null && allowedRecipients.get(0).equals(-1L) ) || allowedRecipients.contains(clientID)){	
						Date datetime = ce.getTime();
						SimpleDateFormat dt = new SimpleDateFormat("dd.MM.YY");
						SimpleDateFormat tm = new SimpleDateFormat("HH:mm:ss");
						String timestamp = "<date>" + dt.format(datetime) + "</date>";
						timestamp += "<br/><time>" + tm.format(datetime) + "</time>";
						if (ce.getMsgOutput()) {
							String ownMsg = "";
							if(ce.getClientID() == clientID){
								ownMsg = "class='ownMsg'";
							}
							
							String[] contentLines = ce.getContent().split("\n");
							int length = contentLines.length;
														
							out.println("<table id='msgOutput' border='0'>");				
							if(length > 1){
								out.println("<tr><td id='timestamp' rowspan='" + length + "'>" + timestamp + "</td><th rowspan='"+ length +"'>" + ce.getNick() + "</th><td " + ownMsg + " >" + contentLines[0] + "</td></tr>");
								for(int i = 1; i < length; i++){
									out.println("<tr><td " + ownMsg + " >" + contentLines[i] + "</td></tr>");
								}
							}else {
								out.println("<tr><td id='timestamp'>" + timestamp + "</td><th>" + ce.getNick() + "</th><td " + ownMsg + " >" + contentLines[0] + "</td></tr>");
							}
							out.println("</table>");
						}
						if (ce.getAlertOutput()) {
							String[] alertLines = ce.getAlert().split("\n");
							int length = alertLines.length;
							for(int i = 0; i < length; i++){
								out.println("<p id='alertOutput'>System | <span>" + alertLines[i] + "</span></p>");
							}							
						}
					}			
				}
			}
		%>
	</div>

	<form action='Chat' method='post' name='sndMsg' id='formMsg'>
		<textarea placeholder=" Als '<%out.print(nickname);%>' Nachricht versenden..." autofocus rows='3' cols='100' name='msg' id='textarea'></textarea>
		<input id='sndMsgBtn' type='submit' value='Senden' name='send' />
	</form>
</body>
<script>
	$(document).ready(function() {

		$("div#verlauf div#roomname").mouseover(function() {
			$("div#verlauf div#roomname p").css("opacity", "0.0");
		}).mouseout(function() {
			$("div#verlauf div#roomname p").css("opacity", "1.0");
		});

		var roomnameWidth = $("div#verlauf div#roomname").width();
		$("div#verlauf div#roomname").css("margin-left", "-" + (roomnameWidth / 2) + "px");

		$("div#verlauf").mCustomScrollbar({
			axis : "y",
			theme : "light-thick",
			scrollbarPosition : "inside",
			scrollInertia : 450,
			scrollButtons : {
				enable : true,
				scrollType : "stepless"
			},
			keyboard : {
				enable : true,
				scrollType : "stepless"
			},
			snapOffset : 10,
			setTop : $('div#verlauf')[0].scrollHeight + "px",
			setWidth : "80%"
		});

		$('textarea#textarea').keypress(function(e) {
			if (e.which == 13 && e.shiftKey) {
			} else if (e.which == 13) {
				if ($.trim($(this).val()) != "") {
					$('form#formMsg').submit();
				}
			}
		});

		//$('div#verlauf').scrollTop($('div#verlauf')[0].scrollHeight);

		$("div#verlauf td").each(function() {
			var p = $(this);

			$.ajax({
				type : "POST",
				url : "URL",
				data : {
					entry : p[0].innerHTML
				}
			}).done(function(data) {
				if (data != "") {
					var urls = data.split("|");
					for (var i = 0; i < urls.length; i++) {
						var wwwCheck = urls[i].substring(0, 4); // www.
						if (wwwCheck == "www.") {
							p.html(p[0].innerHTML.replace(urls[i], "<a target='_blank' href='http://" + urls[i] + "'>" + urls[i] + "</a>"));
						} else {
							p.html(p[0].innerHTML.replace(urls[i], "<a target='_blank' href='" + urls[i] + "'>" + urls[i] + "</a>"));
						}
					}
				}
			});
		});
<%if(alertMessage != ""){
			out.print("alert('" + alertMessage + "');");
		}%>
	});
</script>
</html>