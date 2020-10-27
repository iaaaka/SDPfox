<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
	<%
project.setCurrentLocation("index.jsp");
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
</head>
<body bgcolor=#FFFFFF>
<table class='body_table' width=600" align="center">
	<tr>
		<td colspan="3">
		<h2><font color="navy" , face="verdana">SDPfox - the
		fast tool for the prediction of functional specificity groups and
		amino acid residues that determine the specificity using MPA </font></h2>
		</td>
	</tr>
	<tr>
		<td colspan="3">
		<hr color="black">
		</td>
	</tr>
	<tr>
		<td align="center"  valign="top">Main&nbsp;page<br>
		<a href="algorithm.jsp">Algorithm & format&nbsp;requirements</a> <br>
		<a href="help.jsp">Help</a> <br>
		<a href="mailto:iaa.aka@gmail.com">Contact&nbsp;us</a><br>
		<a href="http://storage.bioinf.fbb.msu.ru/~mazin/SDPfox.jar">Download SDPfox</a>
		</td>
		<td width="2" bgcolor="black"></td>
		<td>
		<table>
			<tr>
				<td>
				<p><font face="verdana" color="navy" size="2"> 
				SDPfox provides a novel phylogeny-independent method for prediction of specificity-determining positions 
				(SDPs) and grouping sequences into functional sub-groups. You may use the form below to conduct a training 
				set-driven analysis (i.e. predict protein specificity based on a set of known specificities) or download 
				<a href="http://storage.bioinf.fbb.msu.ru/~mazin/SDPfox.jar">a JAR package</a>  that allows for ab initio 
				specificity prediction.
				 </font></p>
				<form method="post" action="statistics.jsp"
					enctype="multipart/form-data">
				<p><font color="navy" face="verdana" size=2> <br>
				Paste your alignment into the form below (fasta and gde formats, see <a target= "laci" href="http://storage.bioinf.fbb.msu.ru/~mazin/laci.fasta">example</a>):</font></p>
				<textarea name="text" cols=70 rows=10></textarea>
				<p><font color="navy" face="verdana" size=2> Or load it
				from file:</font></p>
				<input type="file" name="file"> <br>
							Select the maximum allowed percent of gaps in a group in each column:
				<select name="maxGapPart">
					 <option value="0">0%</option>
					 <option value="0.1">10%</option>
					 <option value="0.2">20%</option>
					 <option value="0.3" selected="selected">30%</option>
					 <option value="0.4">40%</option>
					 <option value="0.5">50%</option>
					 <option value="0.6">60%</option>
					 <option value="0.7">70%</option>
					 <option value="0.8">80%</option>
					 <option value="0.9">90%</option>
					 <option value="1">100%</option>
				</select><br>
				<input type="submit" name="Submit" value="Submit"> <input
					type="reset" value="Reset"></form>
					<br>
				<font size=1>
					To get help, run java -jar SDPfox.jar.<br>
					When using. please cite: Mazin PV et al. "An Automated Stochastic Approach to the Identification of the 
					Protein Specificity Determinants and Functional Subfamilies" (2010)
				</font>
				</td>
			</tr> 
		</table>
		</td>
	</tr>
</table>
</body>
</html>