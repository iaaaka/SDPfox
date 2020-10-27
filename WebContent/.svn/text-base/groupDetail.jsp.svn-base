<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
<%int grNo = Integer.parseInt(request.getParameter("grno"));
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<style type="text/css">
<!--
.b {font-family: Courier; font-size: 10pt}
-->
</style>
<title>SDP detail for group <%=project.getGroupName(grNo)%></title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
</head>
<body>
<h1><%=project.getGroupName(grNo) %></h1>
<table  class='body_table' align="center" border="1" class="b">
<tr><td>Alignment position</td><td>Amino acids</td></tr>
<%
String[] columns= project.getSortedForAlignmentPositionColumnsInStringForAllSDPForGroup(grNo);
int[] sdp = project.getSortedForAlignmentPositionSDP();
for(int i=0;i< sdp.length;i++){ %>
<tr><td><%=sdp[i] %></td><td><%=columns[i] %></td></tr>
<%} %>
</table>
</body>
</html>