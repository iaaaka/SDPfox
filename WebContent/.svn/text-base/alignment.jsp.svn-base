<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"   errorPage="fatalError.jsp"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
<%
project.setCurrentLocation("alignment.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<script language="JavaScript" type="text/javascript">
<!--
if(top.id != 'MAIN_PAGE'){
window.location.href="main.jsp";
}
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDPlight</title>
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
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
<!--
function for select.
//-->
<script language="JavaScript" type="text/javascript">
<!--
 var request = false;
   try {
     request = new XMLHttpRequest();
   } catch (err) {
     try {
       request = new ActiveXObject("Msxml2.XMLHTTP");
     } catch (err) {
       try {
         request = new ActiveXObject("Microsoft.XMLHTTP");
       } catch (err) {
         request = false;
       }  
     }
   }

   if (!request){
     alert("Error initializing XMLHttpRequest!");
  }
  
 function sendQuery(seqs,newGr){
 request.open("POST", "Ajax", true);
 request.onreadystatechange = applyResponse;
 request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 request.setRequestHeader("Content-Length", 2);
 request.send("seqs="+seqs+"&newGr="+newGr);
 }
 
 function applyResponse(){
 if (request.readyState == 4){
 	 var lines = request.responseText.split("\n");
 	 top.sdp=lines[0].split(",");
 	 eval("top.sdpIntervals="+lines[1]+";");
 	 top.writeAlignment(window.frames[0].document);
 	}
 } 
 
function addNewGroup(){
	var name = document.getElementById("newGroup").value;
	if(name == ""){
	return;
	}
	request.open("GET", "AddGroup?newGrName="+name, true);
	request.onreadystatechange = function(){
		if (request.readyState == 4){
			if(request.responseText == "bad name"){
				alert("Group "+name+" is already exist!");
			}else{
			top.addGroup(name);
			addGroupInSelect(name,request.responseText-0);
			top.writeAlignment(window.frames[0].document);
			}
		}
	}
	request.send(null);
}

function getGroupArray(grNo){
	return top.groups[grNo-0];
}

function changeGroupOnChange(oListboxID, groupBoxName){
	var gb = document.getElementById(groupBoxName);
	changeGroup(document.getElementById(oListboxID),gb.options[gb.selectedIndex].value);
}

function addOption (oListbox, text, value)
{	
  	var oOption = document.createElement("option");
  	oOption.appendChild(document.createTextNode(text));
  	oOption.setAttribute("value", value);
 	oListbox.appendChild(oOption);
}

function changeGroup(oListbox, grNo){
	top.clearSelect(oListbox);
	var seqNo = getGroupArray(grNo);
	for(var i=0;i<seqNo.length;i++){
	addOption(oListbox,top.seqNames[seqNo[i]],seqNo[i]);
	}
}

function getSelectedIndexes (oListbox)
{
  var arrIndexes = new Array();
  for (var i=0; i < oListbox.options.length; i++)
  {
      if (oListbox.options[i].selected) arrIndexes.push(i);
  }
  return arrIndexes;
};

function getSelectedValues (oListbox)
{
  var arrIndexes = new Array();
  for (var i=0; i < oListbox.options.length; i++)
  {
      if (oListbox.options[i].selected) 
      {
      arrIndexes.push(oListbox.options[i].value);
      }
  }
  return arrIndexes;
};


function moveSequences(oldListboxID, newListboxID, oldGroupBoxName,newGroupBoxName){
	var oldGroupBox = document.getElementById(oldGroupBoxName);
	var newGroupBox = document.getElementById(newGroupBoxName);
	var oListbox = document.getElementById(oldListboxID);
	var seqsNos = getSelectedValues(oListbox);	
	top.moveSequences(getSelectedIndexes(oListbox),seqsNos,oldGroupBox.selectedIndex,newGroupBox.selectedIndex);
	changeGroupOnChange(oldListboxID,oldGroupBoxName);
	changeGroupOnChange(newListboxID,newGroupBoxName);
	var seqs='';
	for(var i=0;i<seqsNos.length;i++){
	seqs += seqsNos[i]+' ';
	}
	sendQuery(seqs,newGroupBox.options[newGroupBox.selectedIndex].value);
}

function addGroupInSelect(name,no){
	var g1 = document.getElementById('group1');
	var g2 = document.getElementById('group2');
	addOption(g1,name,no);
	addOption(g2,name,no);
}

function setState(group1, group2){
	for(var i = 0;i<top.groupNames.length;i++){
		addGroupInSelect(top.groupNames[i],i);
	}
	document.getElementById('group1').options[group1].selected = true;
	document.getElementById('group2').options[group2].selected = true;
	changeGroupOnChange('seq1','group1');
	changeGroupOnChange('seq2','group2');
}
//-->
</script>
</head>
<body bgcolor=#FFFFFF>
<table class='body_table' width="600" align="center">
	<tr>
		<td colspan="3">
		<h2><font color="navy" , face="verdana">Alignment - show
		alignment with SDP </font></h2>
		</td>
	</tr>
	<tr>
		<td colspan="3">
		<hr color="black">
		</td>
	</tr>
	<tr>
		<td align="center"  valign="top"><a href="index.jsp">Main&nbsp;page</a> <br>
		<a href="algorithm.jsp">Algorithm & format requirements</a> <br>
		<a href="help.jsp">Help</a> <br>
		<a href="mailto:iaa.aka@gmail.com">Contact&nbsp;us</a><br>
		<a href="SaveAlignment">Save&nbsp;Alignment</a></td>
		<td width="1" bgcolor="black"></td>
		<td>
		<table>
			<tr>
				<td>
				<table align="center"  width="450">
					<tr>
						<td><h3><a href="statistics.jsp">Statistics</a></h3></td>
						<td><h3>Alignment</h3></td>
						<td><h3><a href="sdp.jsp">SDPs</a></h3></td>
						<td><h3><a href="plot.jsp">Probability&nbsp;plot</a></h3></td>
						<td><h3><a href="sdpgroup.jsp">SDPgroup</a></h3></td>
					</tr>
				</table>
				</td>
		    </tr>
			<tr>
				<td>Alignment divided into specificity groups with marked SDP is represented below. See <a href="help.jsp#algn" target="h">help</a> for detail</td>
			</tr>
			<tr>
				<td>
				<form method="post">
				<table align="center">
					<tr>
						<td  colspan=2 align="left">Add new group <input type="text" id="newGroup"/><input type="button"  value="Add" onclick="javascript:addNewGroup()"/> </td>
					</tr>
					<tr align="center">
						<td>Select group:<br>
						<select name="group1" id="group1" onchange="javascript:changeGroupOnChange('seq1','group1')">
						</select></td>
						<td></td>
						<td>Select group:<br>
						<select name="group2" id="group2"  onchange="javascript:changeGroupOnChange('seq2','group2')">
						</select></td>
					</tr>
					<tr align="center">
						<td>Select sequences:<br>
						<select multiple="multiple" size="10" name="seq1" id="seq1">
						</select></td>
						<td align="center">
						<input name="button1" type="button" value=">>" onclick="javascript:moveSequences('seq1','seq2','group1','group2')"/> 
						<br>
						<input name="button2" type="button"  value="<<" onclick="javascript:moveSequences('seq2','seq1','group2','group1')"/> 
						</td>
						<td>Select sequences:<br>
						<select multiple="multiple" size="10" name="seq2" id="seq2">
						</select></td>
					</tr>
				</table>
				</form>
				</td>
			</tr>
			<tr>
				<td>Alignment (click name for detail): <iframe scrolling="yes"
					height="500" width="600"marginheight="0"
					marginwidth="0"></iframe></td>

			</tr>
		</table>
		</td>
	</tr>
</table>
					<script language="JavaScript" type="text/javascript">
						<!--
							top.writeAlignment(window.frames[0].document);
							setState(0,0);
						//-->
					</script>
</body>
</html>