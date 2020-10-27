<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
<%@ page import="Math.*"%>
<%
	int grNo;
	try {
		grNo = Integer.parseInt(request.getParameter("grno"));
	} catch (Exception e) {
%>
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<script language="JavaScript">window.location.href="index.jsp"</script>
</head>
</html>
<%
	return;
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<%
	if (!project.withAlignment()) {
%>
<script language="JavaScript">window.location.href="index.jsp"</script>
<%
	return;
	}
%>
<%
	String[][] data;
	String name;
	if (grNo == -1) {
		data = project.getIdentityMatrixForWholeAlignment();
		name = "Whole alignment";
	} else {
		data = project.getIdentityMatrixForGroup(grNo);
		name = project.getGroupName(grNo);
	}
%>
<title>SDPlight: <%=name%></title>
</head>
<body>
<h2><%=name%></h2>
<table class='body_table' width="600" align="center" border="1">
	<%
		for (int i = 0; i < data.length; i++) {
	%>
	<tr align="center">
		<%
			for (int j = 0; j < data[i].length; j++) {
		%>
		<td <%if(j!=0){%>bgcolor = <%=StaticFunction.getColorForIdentityTable(data[i][j]) %><%} %>><%=data[i][j]%></td>
		<%
			}
		%>
	</tr>
	<%
		}
	%>
</table>
</body>
</html>