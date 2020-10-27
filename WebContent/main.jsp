<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <jsp:useBean id="project" scope="session" class="Objects.Project" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script language="JavaScript" type="text/javascript">
<!--
var sequences = new Array();
var seqNames = new Array();
var id = 'MAIN_PAGE';
var groupNames = new Array();
var groups = new Array();
var delimiter;
var sdp = new Array();
var sdpIntervals = new Array();
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
  
function reloadData(){
	request.open("GET", "JavaScriptDataLoader", true);
 	request.onreadystatechange = loadData;
 	request.send('');
}

function loadData(){
 if (request.readyState == 4){
 	 eval(request.responseText);
 	 //writeAlignment(window.frames[0].frames[0].document);
 	 }
 } 

function allNew(){
	sequences = new Array();
	seqNames = new Array();
	groupNames = new Array();
	groups = new Array();
	sdp = new Array();
	sdpIntervals = new Array();
}

function addGroup(grName){
	groupNames.push(grName);
	groups.push(new Array());
}

function moveSequences(seqsNoInGroup, seqIndexes,oldGroup,newGroup){
	groups[oldGroup] = deleteElements(seqsNoInGroup,groups[oldGroup]);
	for(var i=0;i<seqIndexes.length;i++){
		groups[newGroup].push(seqIndexes[i]);
	}
}

function getColorSequence(seqNo){
	var seq = sequences[seqNo];
	var result='&nbsp;&nbsp;';
	if(sdpIntervals.length <= 0) return seq;
	result += seq.substring(0,sdpIntervals[0][0]);
	for(var i=0;i<sdpIntervals.length-1;i++){
		result +=  '<font class="clr">'+seq.substring(sdpIntervals[i][0],sdpIntervals[i][1])+'</font>'+seq.substring(sdpIntervals[i][1],sdpIntervals[i+1][0]);
	}
		result +=  '<font class="clr">'+seq.substring(sdpIntervals[sdpIntervals.length-1][0],sdpIntervals[sdpIntervals.length-1][1])+'</font>'+seq.substring(sdpIntervals[sdpIntervals.length-1][1],seq.length);
	return result;
}

 function clearSelect(oListbox)
{
  for (var i=oListbox.options.length-1; i >= 0; i--)
  {
      oListbox.remove(i);
  }
};

function deleteElements(indexes,array){
				 var newArray = new Array();
				 var index = 0;
				 for(var i=0;i<array.length;i++){
				 			if(i != indexes[index]){
						 			 newArray.push(array[i]);
							}
							else {
									 index++;
							}
				 }
				 return newArray;
}

function getBodyScrollLeft(frameFor)
{
  return frameFor.pageXOffset || (frameFor.document.documentElement && frameFor.document.documentElement.scrollLeft) || (frameFor.document.body && frameFor.document.body.scrollLeft);
}

function moveNames(frameFor){
var obj = frameFor.document.getElementById("names1");
obj.style.left = getBodyScrollLeft(frameFor);
}

function addSequence(name, seq, groupNo){
	sequences[sequences.length] = seq;
	seqNames[seqNames.length] = name;
	groups[groupNo].push(sequences.length-1);
}

function posDetail(address){
window.open('positionDetail.jsp?posno='+address,'pos', 'width=650,height=400,screenX=100,screenY=0,left=100,top=0,toolbar=0,scrollbars=1,menubar=0,resizable=1');
}

function openLogo(grNo){
var w = window.open('','logo'+grNo, 'width=650,height=400,screenX=100,screenY=0,left=100,top=0,toolbar=0,scrollbars=1,menubar=0,resizable=1');
w.document.open();
w.document.writeln('<html><head><title>Logo '+groupNames[grNo]+'</title></head><body>');
w.document.writeln('<h3>'+groupNames[grNo]+'</h3>');
w.document.writeln('<img src="Logo?grNo='+grNo+'&id='+Math.random()+'" height=200></img>');
w.document.writeln("</body></html>");
w.document.close();
}

function openWindow(address,target){
window.open(address,target, 'width=650,height=400,screenX=100,screenY=0,left=100,top=0,toolbar=0,scrollbars=1,menubar=0,resizable=1');
}

function seqDetail(address){
window.open('seqDetail.jsp?seqno='+address,'seqDet', 'width=650,height=400,screenX=100,screenY=0,left=100,top=0,toolbar=0,scrollbars=1,menubar=0,resizable=1');
}

function groupDetail(address){
window.open('groupDetail.jsp?grno='+address,'grDet', 'width=650,height=400,screenX=100,screenY=0,left=100,top=0,toolbar=0,scrollbars=1,menubar=0,resizable=1');
}

function writeAlignment(doc){
	if(!sequences || sequences.length == 0){
		reloadData();
		return;
	}
	doc.open();
	doc.writeln('<html><head>');
	doc.writeln('<style type="text/css"><!--');
	doc.writeln('p{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}');
	doc.writeln('tr{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}');
	doc.writeln('td{margin: 0px 0px 0px 0px;padding: 0px 0px 0px 0px;}');
	doc.writeln('.nms {position: absolute;left: 0;top: 0;background: #fff;}');
	doc.writeln('.b {font-family: monospace; background: #ffffff; font-size: 9pt}');
	doc.writeln('.clr {	background: #f00;}--></style>');
	doc.writeln('</head><body onScroll="javascript:top.moveNames(window)" topmargin="0" leftmargin="0">');
	doc.writeln('<table class="b">');
			for(var i=0;i< groups.length;i++){
				doc.writeln('<tr>'+
									'<td>'+groupNames[i]+'</td>'+
									'<td>&nbsp;&nbsp;'+delimiter+'</td>'+
								 '</tr>');
				for(var j=0;j<top.groups[i].length;j++){
					var s = getColorSequence(top.groups[i][j]);
					doc.writeln('<tr>'+
										'<td>&nbsp;&nbsp;'+seqNames[groups[i][j]]+'</td>'+
										'<td>'+s+'</td>'+
									'</tr>');
				}
			}
	doc.writeln('</table><div class="nms" id="names1"><table class="b">');
			for(var i=0;i<groups.length;i++){
				doc.writeln('<tr>'+
								    '<td><a href="javascript:top.groupDetail('+i+')">'+groupNames[i]+'</a></td>'+
								 '</tr>');
				for(var j=0;j<groups[i].length;j++){
					doc.writeln('<tr>'+
										'<td>&nbsp;&nbsp;<a href="javascript:top.seqDetail('+groups[i][j]+')">'+seqNames[groups[i][j]]+'</a></td>'+
									'</tr>');
				}
			}
	doc.writeln('</table></div></body></html>');
	doc.close();
}

<%
if(project.withAlignment()){%>
reloadData();	
<%}
%>
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
<title>SDPfox</title>

</head>
<frameset>
<frame src="<%=project.getCurrentLocation() %>">
</frameset>
</html>