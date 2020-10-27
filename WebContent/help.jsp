<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
	<%project.setCurrentLocation("statistics.jsp");%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%
project.setCurrentLocation("help.jsp");
%>
<%@ page import="Run.SDPfoxRun"%>
<html>
<head>
<link rel='stylesheet' type='text/css' href='css/styles.css' />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SDPlight</title>
<META name="Author" content="Mazin Pavel, 20.12.2007">
<META name="Keywords"
	content="SDP, specificity determining positions, proteins, family, multiple alignment">
</head>
<body bgcolor=#FFFFFF>
<table class='body_table' width="600" align="center">
	<tr>
		<td colspan="3"><h2><font color="navy" , face="verdana">Help
		</font></h2>
		<ul>
		<li><a href="#sdpfox">Using stand-alone SDPfox</a>
		<li><a href="#algn">Alignment</a>
		<li><a href="#grp">SDPgroup</a>
		</ul>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr color="black">
		</td>
	</tr>
	<tr>
		<td align="center" valign="top">
				<a href="index.jsp">Main&nbsp;page</a>
				<br><a href="algorithm.jsp">Algorithm & format&nbsp;requirements</a>
				<br><a href="help.jsp">Help</a>
				<br><a href="mailto:iaa.aka@gmail.com">Contact&nbsp;us</a>
		</td>
		<td  width="1"  bgcolor="black">
		</td>
		<td>
			<table width="500">
			<tr>
					<td>
					<font face="verdana" color="navy" size="2">
					<p><a name="sdpfox"><font size=3><b>Using stand-alone SDPfox</b></font></a><p>
					From our site you can download <a href="http://storage.bioinf.fbb.msu.ru/~mazin/SDPfox.jar">stand-alone SDPfox</a>, 
					and <a href="http://storage.bioinf.fbb.msu.ru/~mazin/laci.fasta">example alignment</a>.<br>
					To get help, run "java -jar SDPfox.jar".  Java version 1.6.0_17 or later is required.
					</p>
					<p><font size=3><b>Web interface</b></font><p>
					<p><a name="algn"><font size=3><b>Alignment</b></font></a><p>
					To add a new specificity group, type in the group name and press "Add"<br>
					<img src="formula/add.gif"><br>
					To change composition of  specificity groups, select two groups: first, from which  to remove 
					sequences, and second, to which to add  sequences. Select sequences and press one of the arrow 
					buttons:<br>
					<img src="formula/move.gif"><br>
					Click name of a group or a sequence to get  detailed information on a position (SDP) 
					<p><a name="grp"><font size=3><b>SDPgroup</b></font></a><p>
					For a constructed grouping, it is possible to run SDPgroup, re-randomize grouping or move 
					sequences according to the best weights (see algorithm) under the link 'SDPgroup'. 
					There, the user is requested to select a method and press "GO".
					To randomize grouping, set number of specificity groups and number of sequences into each group:<br>
					<img src="formula/group.gif"><br>
					After the randomization is done, PSSM group weights are calculated for each sequence and those 
					that have a higher weight for a group other than the initial group are marked red. These sequences 
					are likely to be re-grouped after SDPgroup is performed.
					</font>
					</td>
			</tr>
		 </table>
		</td>
	</tr>
</table>
</body>
</html>