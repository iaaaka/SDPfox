<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"  errorPage="fatalError.jsp"%>
<%@ page isErrorPage="true" %>
<%@ page import="Exception.*"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Exception</title>
</head>
<body>
<%
if(exception instanceof NumeratorException){
	NumeratorException e = (NumeratorException)exception;
	if(e.getType().equals("group")){
		out.print("Bad group names.<br>Group "+e.getName()+" is duplicated.<br><a href=\"main.jsp\">main page</a>");
	}else if(e.getType().equals("sequence")){
		out.print("Bad sequence names.<br>sequence "+e.getName()+" is duplicated.<br><a href=\"main.jsp\">main page</a>");
	}
}else if(exception instanceof AlignmentException){
	AlignmentException e = (AlignmentException)exception;
	out.print("<pre>"+e.toString()+"</pre><br><a href=\"main.jsp\">main page</a>");
	}else{
	throw (Exception)exception;
}
%>
</body>
</html>