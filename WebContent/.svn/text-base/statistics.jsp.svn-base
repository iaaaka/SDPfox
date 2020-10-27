<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"  errorPage="error.jsp"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
	<%
project.setCurrentLocation("statistics.jsp");
%>
<%@ page import="FileLoader.task20071109.FileUploader,java.io.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<script language="JavaScript" type="text/javascript">
<!--
if(top.id != 'MAIN_PAGE'){
window.location.href="main.jsp";
}
function detail(address){
window.open('statisticDetails.jsp?grno='+address,'stat', 'width=650,height=400,screenX=100,screenY=0,left=100,top=0,toolbar=0,scrollbars=1,menubar=0,resizable=1');}
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDPlight</title>
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
<%
	String[] maxGap = {""};
	if (!project.setAlignmentData(FileUploader.getDataStream(request
			.getInputStream(), new String[] { "file", "text"},new String[] { "maxGapPart"},maxGap))) {
		if(!project.withAlignment()){
%>
<script language="JavaScript">window.location.href="index.jsp"</script>

<%
	return;}
	}
	else{
	project.setMaxGapPart(maxGap[0]);%>
	<script language="JavaScript">
		top.reloadData();
		<%if(project.getGroupCount() < 3){%>
		alert("There is less than 3 groups. SDPlight and SDPgroup algorithms is inapplicable");
		<%}%>
	</script>
	<%}%>

</head>
<body bgcolor=#FFFFFF>
<table class='body_table' width="600" align="center">
	<tr>
		<td colspan="3">
		<h2><font color="navy" face="verdana">Statistics </font></h2>
		</td>
	</tr>
	<tr>
		<td colspan="3">
		<hr color="black">
		</td>
	</tr>
	<tr>
		<td align="center"  valign="top"><a href="index.jsp">Main&nbsp;page</a> <br>
		<a href="algorithm.jsp">Algorithm & format&nbsp;requirements</a> <br>
		<a href="help.jsp">Help</a> <br>
		<a href="mailto:iaa.aka@gmail.com">Contact&nbsp;us</a></td>
		<td width="1" bgcolor="black"></td>
		<td>
		<table>
			<tr>
				<td>
				<table align="center"  width="450">
					<tr>
						<td><h3>Statistics</h3></td>
						<td><h3><a href="alignment.jsp">Alignment</a></h3></td>
						<td><h3><a href="sdp.jsp">SDPs</a></h3></td>
						<td><h3><a href="plot.jsp">Probability&nbsp;plot</a></h3></td>
						<td><h3><a href="sdpgroup.jsp">SDPgroup</a></h3></td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td><font color="navy" face="verdana" size="2">
				<h3><a href="javascript:detail('-1')">Alignment:</a></h3>
				Length: <b><%=project.getAlinmentLength()%></b> amino acids.<br>
				<b><%=project.getSeqCount()%></b> sequences, average identity <b><%=project.getAvarageIdentityForWholeAlignment()%></b><br>
				<br>
				<b><%=project.getGroupCount()%></b> groups (click group name for detail):<br>
				<br>
				<ul>
					<%
						String[] id = project.getAvarageIdentityForGroups();
					%>
					<%
						for (int i = 0; i < project.getGroupCount(); i++) {
					%>
					Group
					<a href="javascript:detail('<%=i %>')")><b><%=project.getGroupName(i)%></b></a>
					:
					<b><%=project.getGroupSize(i)%></b>
					sequences, average identity
					<b><%=id[i]%>%</b>
					<br>
					<%
						}
					%>
				</ul>
				</font></td>
			</tr>
		</table>
		</td>
	</tr>
</table>
</body>
</html>