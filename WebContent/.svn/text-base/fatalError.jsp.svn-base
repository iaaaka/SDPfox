<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page isErrorPage="true" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<h1>Fatal Error</h1>

<br>Fatal error occured in the bean. Error Message is: <%= exception.getMessage() %><br>
Stack Trace is : <pre><font color="red"><% 
 java.io.CharArrayWriter cw = new java.io.CharArrayWriter(); 
 java.io.PrintWriter pw = new java.io.PrintWriter(cw,true); 
 exception.printStackTrace(pw); 
 out.println(cw.toString()); 
 %></font></pre>
<br>
</body>
</html>