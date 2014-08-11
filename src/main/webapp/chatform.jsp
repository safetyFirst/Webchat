<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" href="style.css" type="text/css">
<script src='http://code.jquery.com/jquery-2.1.1.min.js'></script>
<title>Webchat</title>
</head>
<body style='margin: 0; padding: 0;'>

	<img src='dbad_transparent.gif' id='dbad-logo' />
	
	<% 
	String nickname = (String) application.getAttribute("nickname");
	%>

	<form action='Chat2' method='post' name='sndMsg' id='formMsg'>
		<textarea placeholder=" Als '<%out.print(nickname);%>' Nachricht versenden..." autofocus rows='3' cols='100' name='msg' id='textarea'></textarea>
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
	});
</script>
</html>