<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="project" scope="session" class="Objects.Project" />
    <%
    int seqNo = Integer.parseInt(request.getParameter("seqNo"));
    %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=project.getSeqName(seqNo) %></title>
</head>
<body>
<h3><%=project.getSeqName(seqNo) %></h3>
<table class='body_table' width="300" align="center" border="1">
	<tr>
		<td>Group name</td>
		<td>weight</td>
	</tr>
	<%
	int grNo = project.getGroupForSequence(seqNo);
	String[][] data = project.getWeightsForSeq(seqNo);
	for(int i=0;i<project.getGroupCount();i++){
	if(i==grNo){%>
	<tr  bgcolor="#ff0000">
	<%}else{%>
		<tr><%} %>
		<td><%=data[i][0] %></td>
		<td><%=data[i][1] %></td>
		</tr>		
	<%} %>
</table> 
</body>
</html>