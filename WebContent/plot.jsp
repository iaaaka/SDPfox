<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"   errorPage="fatalError.jsp"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
	<%
	project.setCurrentLocation("plot.jsp");
	%>
<%@ page import="servlets.Plot,painter.Graph"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<style type="text/css"><!--
.plotThr {
position: absolute;
width: 2px;
background: #f00;
top: 0;
left: 0;
height: 100;
}
--></style>
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
	}
%>
<script language="JavaScript" type="text/javascript">
<!--
if(top.id != 'MAIN_PAGE'){
window.location.href="main.jsp";
}

function mousePageXY(e)
{
  var x = 0, y = 0;

  if (!e) e = window.event;

  if (e.pageX || e.pageY)
  {
    x = e.pageX;
    y = e.pageY;
  }
  else if (e.clientX || e.clientY)
  {
    x = e.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft) - document.documentElement.clientLeft;
    y = e.clientY + (document.documentElement.scrollTop || document.body.scrollTop) - document.documentElement.clientTop;
  }

  return {"x":x, "y":y};
}

function getElementPosition(elemId)
{
    var elem = document.getElementById(elemId);
	
    var w = elem.offsetWidth;
    var h = elem.offsetHeight;
	
    var l = 0;
    var t = 0;
	
    while (elem)
    {
        l += elem.offsetLeft;
        t += elem.offsetTop;
        elem = elem.offsetParent;
    }
    return {"left":l, "top":t, "width": w, "height":h};
}

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
  
  function applyResponse(){
 if (request.readyState == 4){
 	 if(request.responseText == 'no'){
 		 top.sdp = new Array();
 		 top.sdpIntervals = new Array();
 	 }
 	 else{
 	 	var lines = request.responseText.split("\n");
 	 	top.sdp=lines[0].split(",");
 		eval("top.sdpIntervals="+lines[1]+";");
 	 }
 	 repaintThreshold();
 	}
 }
  
 function sendClickQuery(e){
 request.open("POST", "Plot", true);
 request.onreadystatechange = applyResponse;
 request.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
 request.setRequestHeader("Content-Length", 1);
 var pos = getElementPosition('plot');
 var clk = mousePageXY(e);
 var position = (clk.x - pos.left)/pos.width;
 request.send("position="+position);
 }
 
 
function repaintThreshold(){
if(!top.sequences || top.sequences.length == 0){
	top.reloadData();
}
	document.getElementById("sdpcutoff").innerHTML = "The currently selected cutoff is "+top.sdp.length+" SDPs.";
	var relativePos = ((<%=Plot.width-Graph.YAXES_DIST %>)*top.sdp.length/top.sequences[0].length+<%=Graph.YAXES_DIST %>)/<%=Plot.width %>;
	var d = document.getElementById('pltthr');
	var pos = getElementPosition('plot');
	d.style.top = pos.top+'px';
	d.style.height = pos.height+'px';
	d.style.left = Math.abs(pos.left+(relativePos)*pos.width-1)+'px';
}
//-->
</script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDPlight</title>
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
</head>
<body bgcolor=#FFFFFF>
<table class='body_table' width="600" align="center">
	<tr>
		<td colspan="3"><h2><font color="navy" , face="verdana">Probability plot
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
						<td><h3>Probability&nbsp;plot</h3></td>
						<td><h3><a href="sdpgroup.jsp">SDPgroup</a></h3></td>
					</tr>
				</table>
					</td>
			</tr>
			<tr>
					<td>
					Threshold is marked by red line. Click graph to change threshold. See <a href="algorithm.jsp#light" target="h">algorithm</a> for detail.
					</td>
			</tr>
			<tr>
					<td>
					<div id=sdpcutoff></div>
					<img id="plot" src="Plot?id=<%=Math.random() %>"  onclick="javascript:sendClickQuery()" onload="javascript:repaintThreshold()">
					</td>				
			</tr>
		 </table>
		</td>
	</tr>
</table>
	<div id="pltthr" class="plotThr">
	</div>
	<script language="JavaScript" type="text/javascript"><!--
	document.getElementById("plot").onclick = function(e){
 		sendClickQuery(e);
	};
//--></script>
</body>
</html>