<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<style type="text/css">
<!--
.b {font-family: Courier; font-size: 6pt}
-->
</style>
<title>All SDP table</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
</head>
<body>
<h1>All SDP table</h1>
<table  class='body_table' width="600" align="center" border="1" class="b">
<tr>
	<td>Alignment position</td>
	<%for(int i=0;i<project.getGroupCount();i++){%>
	<td><%=project.getGroupName(i) %></td>
<%} %>
</tr>
	<%
	String[][] sdpAcids = project.getColumnInStringForAllSDPandAllGroup();
	int[] sdp = project.getSortedForZscoreSDP();
	for(int i=0;i<sdpAcids.length;i++){
	%>
<tr>
	<td><%=sdp[i] %></td>
	<%for(int j=0;j<project.getGroupCount();j++){%>
	<td><%=sdpAcids[i][j] %></td>
<%} %>
</tr>
<%} %>
</table>
</body>
</html>