<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"   errorPage="fatalError.jsp"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
	<%
project.setCurrentLocation("sdpgroup.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="Math.StaticFunction"%>
<%@page import="Objects.Profiles;"%>
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
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
<script language="JavaScript" type="text/javascript">
<!--
if(top.id != 'MAIN_PAGE'){
window.location.href="main.jsp";
}

<%

if(request.getParameter("go") != null && request.getParameter("go").equals("GO!")){
	int method = Integer.parseInt(request.getParameter("method"));
	switch(method){
	case 1:
		project.sdpGroup();
		break;
	case 2:
		project.moveSequenceForBestWeight();
		break;
	case 3:
		project.randomizeGroup(Integer.parseInt(request.getParameter("groupscount")),Integer.parseInt(request.getParameter("seqscount")));
		System.out.println("'"+project.getGroups()+"'");
		%>		
				var tmp = '<%=project.getGroups()%>';
				top.groupNames = tmp.split("\t");
		<%
		break;
		}
	String[] grouping = project.getGroupsCompound();%>
	top.sdp = [<%=project.getSDPs() %>];
	top.sdpIntervals = <%=project.getSDPIntervals() %>;
	top.groups = new Array();
	<%for(int i=0;i<grouping.length;i++){%>
		top.groups[<%=i %>] = <%=grouping[i] %>;
	<%}}%>

function setStart(){
var groupCountSel = document.getElementById("groupscount");
for(var i=2;i<=top.sequences.length/2;i++){
	addOption(groupCountSel,i,i);
}
setSequencesCount();
}

function addOption (oListbox, text, value)
{	
  	var oOption = document.createElement("option");
  	oOption.appendChild(document.createTextNode(text));
  	oOption.setAttribute("value", value);
 	oListbox.appendChild(oOption);
}

function setSequencesCount(){
if(!top.sequences || top.sequences.length == 0){
	top.reloadData();
}
	var sequencesCountSel = document.getElementById("seqscount");
	var groupCountSel = document.getElementById("groupscount");
	top.clearSelect(sequencesCountSel);
	for(var i=2;i<=top.sequences.length/groupCountSel.options[groupCountSel.selectedIndex].value;i++){
	addOption(sequencesCountSel,i,i);}
}

function setDisabled(disabled){
			document.getElementById("groupscount").disabled = disabled;
			document.getElementById("seqscount").disabled = disabled;			
}

//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDPlight</title>
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
</head>
<body bgcolor="#FFFFFF">
<table class='body_table' width="600" align="center">
	<tr>
		<td colspan="3"><h2><font color="navy" , face="verdana">SDPGroup
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
				<table align="center"  width="450">
					<tr>
						<td><h3><a href="statistics.jsp">Statistics</a></h3></td>
						<td><h3><a href="alignment.jsp">Alignment</a></h3></td>
						<td><h3><a href="sdp.jsp">SDPs</a></h3></td>
						<td><h3><a href="plot.jsp">Probability&nbsp;plot</a></h3></td>
						<td><h3>SDPgroup</h3></td>
					</tr>
				</table>
					</td>
			</tr>
			<tr>
					<td>
					See <a href="help.jsp#grp" target="h">help</a>.
					</td>
			</tr>
			<tr>
				<td>
					<form method="POST">
						<table>
							<tr>
								<td>
									<input type="radio" value="1" name="method" onclick="javascript:setDisabled(true);" checked> SDPGroup<br>
									<input type="radio" value="2" name="method" onclick="javascript:setDisabled(true);"> Move sequences according to the best weight<br>
									<input type="radio" value="3" name="method" onclick="javascript:setDisabled(false);"> Randomize<br>
									Set the number of the groups: <select name="groupscount" id="groupscount"  disabled="disabled" onchange="javascript:setSequencesCount()"></select><br>
									Set the number of the sequences: <select name="seqscount" id="seqscount"  disabled="disabled"></select><br>
									<input type="submit" value="GO!" name="go">
								</td>
							</tr>
						</table>
					</form>
				 </td>
			</tr>
			<%
			Profiles p = project.getProfiles();
			if(p==null){%>
				</table>
			</td>
		</tr>
	</table>
	<script language="JavaScript" type="text/javascript">
	<!--
		setStart();
	//-->
	</script>
	</body>
	</html>
			<%
			return;}
			%>
			<tr><td>sequences that probably do not belong to respective groups are marked red</tr></td>
			<%int[][] grouping = project.getGroupng();
			for(int i=0;i<grouping.length;i++){%>
			<tr>
				<td>
					<h2><%=project.getGroupName(i) %></h2>
					<a href="javascript:top.openLogo(<%=i %>)">Logo</a>
					<table border=1>
						<tr>
							<td>Sequence Name</td>
							<td>Current group</td>
							<td>Weight for current group</td>
							<td>Group with max weight</td>
							<td>Max weight</td>
						</tr>
						<%
						for(int j=1;j<=grouping[i][0];j++){
							String[] row = project.getRowForSmallProfileTable(i,grouping[i][j]);
							if(!row[1].equals(row[3])){%>
								<tr bgcolor="#ff0000">
							<%}else{
							%>
						<tr><%} %>
							<td><a href="javascript:top.openWindow('seqProfileDetail.jsp?seqNo=<%=grouping[i][j] %>','seqPrD')"><%=row[0] %></a></td>
							<td><%=row[1] %></td>
							<td><%=row[2] %></td>
							<td><%=row[3] %></td>
							<td><%=row[4] %></td>
						</tr>
						<%}%>
					</table>
				</td>
			</tr>
			<%} %>
		 </table>
		</td>
	</tr>
</table>
<script language="JavaScript" type="text/javascript">
<!--
setStart();
//-->
</script>
</body>
</html>