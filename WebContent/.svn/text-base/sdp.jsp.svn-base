<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"   errorPage="fatalError.jsp"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
	<%
project.setCurrentLocation("sdp.jsp");
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
<script language="JavaScript" type="text/javascript">
<!--
if(top.id != 'MAIN_PAGE'){
window.location.href="main.jsp";
}
//-->
</script>
<%
	if (!project.withAlignment()) {
%>
<script language="JavaScript" type="text/javascript">
<!--
window.location.href="index.jsp"
//-->
</script>
<%
	return;
	}%>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDPlight</title>
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
</head>
<body bgcolor=#FFFFFF>
<table class='body_table' width="600" align="center">
	<tr>
		<td colspan="3"><h2><font color="navy" , face="verdana">SDPs
		</font></h2>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr color="black">
		</td>
	</tr>
	<tr>
		<td align="center"  valign="top">
				<a href="index.jsp">Main&nbsp;page</a>
				<br><a href="algorithm.jsp">Algorithm & format&nbsp;requirements</a>
				<br><a href="help.jsp">Help</a>
				<br><a href="mailto:iaa.aka@gmail.com">Contact&nbsp;us</a>
		</td>
		<td  width="1"  bgcolor="black">
		</td>
		<td>
			<table>
			<tr>
					<td>
				<table align="left"  width="450">
					<tr>
						<td><h3><a href="statistics.jsp">Statistics</a></h3></td>
						<td><h3><a href="alignment.jsp">Alignment</a></h3></td>
						<td><h3>SDPs</h3></td>
						<td><h3><a href="plot.jsp">Probability&nbsp;plot</a></h3></td>
						<td><h3><a href="sdpgroup.jsp">SDPgroup</a></h3></td>
					</tr>
				</table>
					</td>
			</tr>
			<tr>
					<td>
					The most frequent amino acids in this specificity group and position is represented in table below.
					</td>
			</tr>
			<tr>
				<td>
				<a href="javascript:top.openWindow('allSDP.jsp','allSDP')">View full sdp table</a><br>
				Click position number for detail
				<table   class='body_table' width="600" align="center" border="1" class="b">
				<tr>
					<td>No</td>
					<td>Alignment position</td>
					<%for(int i=0;i<project.getGroupCount();i++){ %>
					<td><%=project.getGroupName(i) %></td>
					<%} %>
					<td>Z-score</td>
					<td>P-value</td>
				</tr>
				<%
				String[][] acids = project.get3FrequentAcidsForAllSDPAndAllGroup();
				String[] zscore = project.getZscoreForSDP();
				String[] pvalue = project.getPvalueForSDP();
				int[] sdp = project.getSortedForZscoreSDP();
				for(int i=0;i<acids.length;i++){ %>
				<tr>
					<td><%=i+1 %></td>
					<td><a href="javascript:top.posDetail(<%=sdp[i] %>)"><%=sdp[i] %></a></td>
					<%for(int j=0;j<acids[i].length;j++){ %>
					<td><%=acids[i][j] %></td>
					<%} %>
					<td><%=zscore[i] %></td>
					<td><%=pvalue[i] %></td>
				</tr>
				<%} %>
				</table>
				</td>
			</tr>
		 </table>
		</td>
	</tr>
</table>
</body>
</html>