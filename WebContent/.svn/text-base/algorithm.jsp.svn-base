<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<jsp:useBean id="project" scope="session" class="Objects.Project" />
<%
project.setCurrentLocation("algorithm.jsp");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
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
<table class='body_table' width="800" align="center">
	<tr>
		<td colspan="3"><h2><font color="navy" , face="verdana">Algorithm and format requirements
		</font></h2><br>
		<a href="#algo">Algorithm</a>
		<ul>
		<li><a href="#light">SDPlight</a>
		<li><a href="#prof">SDPprofile</a>
		<li><a href="#group">SDPgroup</a>
		<li><a href="#tree">SDPclust</a>
		</ul>
		<a href="#format">Format requirements</a>
		</td>
	</tr>
	<tr>
		<td colspan="3">
			<hr color="black">
		</td>
	</tr>
	<tr>
		<td  align="center"  valign="top">
				<a href="index.jsp">Main&nbsp;page</a>
				<br>Algorithm & format&nbsp;requirements
				<br><a href="help.jsp">Help</a>
				<br><a href="mailto:iaa.aka@gmail.com">Contact&nbsp;us</a>
		</td>
		<td  width="1"  bgcolor="black">
		</td>
		<td><font face="verdana" color="navy" size="2">
		SDPfox - the software package for the prediction of functional specificity groups 
		and amino acid residues that determine the specificity using MPA.
		<p>
		Many protein families contain homologous proteins that have a common biological function, 
		but different specificity towards substrates, ligands, effectors, DNA, proteins and other 
		interacting molecules including other monomers of the same protein. All these interactions 
		must be highly specific. Our aim is to find groups of protein with same specificity 
		(specificity group) and amino acid residues, that determine this specificity.
		 <p>
		 Amino acid residues that determine differences in protein functional specificity and
		 account for correct recognition of interaction partners, are usually thought to correspond
		 to those positions of a protein multiple alignment, where the distribution of amino acids
		 is closely associated with grouping of proteins by specificity. SDPfox searches for division
		 into specificity groups and positions that are well conserved within this groups but differ 
		 between them. These positions are called SDPs (specificity-determining positions).
		 <p>
		 SDPfox includes the following interconnected procedures: SDPlight to predict SDPs, SDPprofile
		 to assign specificity to unannotated proteins, SDPgroup to split family into groups of
		 specificity from a training sample (a small number of proteins from the considered family, 
		 for which specificity is known), SDPclust to construct a cluster tree of protein specificity.
		 SDPlight, SDPprofile and SDPgroup is available at web-server SDPfox, all methods of SDPfox
		 is realized as a stand-alone console program <a href="http://storage.bioinf.fbb.msu.ru/~mazin/SDPfox.jar">SDPfox</a>.
			<p><a name="algo"><font size=4><b>Algorithm</b></font></a>
			<p><a name="light"><font size=3><b>SDPlight</b></font></a><p>
			Consider a multiple protein sequence alignment. The proteins are divided into N specificity
			groups, numbered by i=1,...,N. The goal in to identify columns (positions) in the alignment,
		    in which the amino acid distribution is closely associated with the grouping by specificity.
			This association in column p of the alignment is measured by the <i>mutual information</i><br>
			<img src="formula/formula1.gif" align="middle">,<br>
			where <img src="formula/formula2.gif" align="top"> is a residue type, <img src="formula/formula3.gif" align="middle">
			is the ratio of the number of occurrences of residue <img src="formula/formula4.gif" align="middle">
			in group <i>i</i> at position <i>p</i> to the length of the whole alignment column,
			<img src="formula/formula5.gif" align="top"> is the frequency of residue <img src="formula/formula4.gif" align="middle">
			in the whole alignment column, <img src="formula/formula6.gif" align="top"> is the fraction of proteins belonging 
			to group <i>i</i>. The mutual information reflects the statistical association between two discrete random variables 
			<img src="formula/formula4.gif" align="middle"> and <i>i</i>.
			<p>To address the facts that frequencies are calculated based on a small sample, and that substitutions to amino
			acids with similar physical properties should be weakly penalized, the observed amino acid frequencies are modified. 
			Instead of using <img src="formula/formula7.gif" align="top">, where <img src="formula/formula8.gif" align="top"> 
			is the number of occurrences of residue <img src="formula/formula4.gif" align="middle"> in group <i>i</i>, 
			<img src="formula/formula9.gif" align="top"> is the size of group <i>i </i>(here <i>i</i> is a single group or 
			the whole alignment), SDPlight uses <i>smoothed frequencies</i><br>
            <img src="formula/formula10.gif" align="middle">,<br>
 		    where <img src="formula/formula11.gif" align="top"> is the probability of amino acid substitution 
 		    <img src="formula/formula12.gif" align="top"> according to the matrix corresponding to the average identity in group 
 		    <i>i</i>, <img src="formula/formula13.gif" align="top"> is a smoothing parameter.</p>
           <p>To calculate the statistical significance of the obtained values of 
           <i>I<sub>p</sub></i>, average of distribution and dispersion of mutual information  (M(I<sub>p</sub>) 
           and D(I<sub>p</sub>)) are calculated from theoretical knowledge. To offset the background similarity of proteins
            that is higher within groups than between groups, we calculate the <i>expected mutual information for the column p</i> 
            <i>I<sup>exp</sup>=aM(I)+b</i> where <i>a</i> and <i>b</i> do not depend on the position, i.e. 
            are the same for every position of the alignment , so that <br>
              <img src="formula/formula16.gif" align="middle" width="400"><br>
            <i>L</i> is the total length of the alignment, <img src="formula/formula17.gif" align="top"> is the observed mutual information for the <i>i</i>-th column.
           <p>Then, <i>Z-scores</i> are calculated:<br>
              <img src="formula/formula18.gif" align="top"><br>
  			A high value of Z-scores indicates a position, where the amino acid distribution is much closer associated with 
  			grouping by specificity than for an average position of the alignment, and thus, which is likely to be an SDP.</p>
			<p>Given a series of Z-scores corresponding to every position 
			of the multiple alignment, one needs to evaluate the significance of the Z-scores in order to tell whether the 
			observed Z-score is sufficiently high to indicate a SDP. SDPpred uses an automated procedure for setting the 
			thresholds based on the computation of the <i>Bernoulli estimator</i>. The observed Z-scores are oredered by 
			decrease: <img src="formula/formula19.gif" align="middle">. The threshold is defined as:<br>
              <img src="formula/formula20.gif" align="middle"><br>
 			 where <i>n</i> is the total number of considered positions, <img src="formula/formula21.gif" align="middle">, 
 			 <img src="formula/formula22.gif" align="middle">. <img src="formula/formula23.gif" align="bottom"> positions having 
 			 highest Z-scores are designated SDPs, as they are the least probable to constitute a tail of the Gaussian 
 			 distribution, and thus are non-randomly generated positions.<em> p</em>(<em>k*</em>) is 
 			 further referred as <em>p-value</em>.<p>
			<a name="prof"><font size=3><b>SDPprofile</b></font></a>
			<p>To predict specificity of other proteins, we calculate a profile matrix based on SDPs for each group:<br>
			<img alt="" src="formula/formula24.gif"><br>
			is weight of amino acids <img src="formula/formula4.gif" align="middle"> in the position <em>p</em> in the profile for group <em>i</em>.
			<p>Then, for each protein we calculate profile weights for all specificity groups: <img alt="" src="formula/formula25.gif">, </p>
			<p>and select the group providing the maximal weight. Then one can assume that the considered protein has a specificity
			coinciding with the specificity of the maximal weight.			
			<p><a name="group"><font size=3><b>SDPgroup</b></font></a><p>
			SDPgroup is an iterative procedure for splitting family into groups of specificity using training sample:<br>
			<ol>
			<li>Initiation: proteins from the training sample form initial specificity groups.
			<li>Step of iteration: SDPs are identified with SDPlight. For all sequences of the family, profile scores for all specificity groups are calculated using SDPprofile. Sequences are rearranged according to the maximal weight 
			<li>End: The step of iteration does not result in rearramgement of sequences 
			</ol>
			<p><a name="tree"><font size=3><b>SDPclust</b></font></a><p>
			SDPclust is a stochastic procedure to construct specificity cluster tree:<br>
			<ol><li>The family is randomly split into a large number of specificity groups
			<li>Starting from this splitting as a training sample, SDPgroup procedure is performed
			<li>Steps 1-2 were repeated 10000 times. A cluster tree was built based on how often two sequences fall 
			into same specificity group.
			<li>The specificity groups are extracted from the tree so that the weight of worst group is the highest among 
			all possible clustering that emerge from tree. The weight Cw is defined as follows:<br>
			<img src="formula/clust_weight.gif" align="middle" width="450"><br>
			where f<sub>ij</sub><sup>in</sup>  and f<sub>ij</sub><sup>out</sup> - are frequencies of grouping of sequences i and 
			j together or separately, respectively,
			max is taken for all i and j from current cluster, min is taken for all i from current cluster and all j outside of
			current cluster, for cluster, which contain only one sequence weight is accepted equal zero.
			</ol>
			<a name="format"><font size=4><b>Format requirements</b></font><p>
			The only information needed for prediction of SDPs is a multiple alignment of protein sequences divided into 
			specificity groups. The aligned sequences should be in the GDE or fasta format. Amino acids should be indicated by
			small or big characters. Gaps should be indicated by dots or hyphen. Name of each sequence is placed onto a 
			separate line and begins with '%' or '>'.
			The alignment may be manually edited in order to define the specificity groups. They may be 
			separated by lines beginning and ending with the "equals" sign and containing name of the following group, e.g.: 
       		"===Group1===".<br>
       		The alignment should contains from 4 to 2000 sequences, not more than 200 groups and be shorter that 5000 aa.<br>
        Thus the input alignment should look like this:
    
    <table cellpadding="10">
        <tr>
          <td bgcolor="white"><font face="courier new" size="2"><nobr>=== RbsR ===</nobr><br>
                <nobr>%EC_RbsR</nobr><br>
                <nobr>-----matmkdvarlagvststvshvinkdrfvseaitakveaaikE</nobr><br>
                <nobr>lnyapsalarslklnqthtigmlitastn-----pfyselvrgvers</nobr><br>
                <nobr>>Pp_RbsR</nobr><br>
                <nobr>.....MATIKDVAALAGISYTTVSHVLNKTRPVSEQVRLKVEAAIIE</nobr><br>
                <nobr>LDYVPSAVARSLKARSTATIGLLVPNSVN.....PYFAELARGIEDA</nobr><br>
                <nobr>%BS_RbsR</nobr><br>
                <nobr>-----MATIKDVAGAAGVSVATVSRNLNDNGYVHEETRTRVIAAMAK</nobr><br>
                <nobr>LNYYPNEVARSLYKRESRLIGLLLPDITN-----PFFPQLARGAEDE</nobr><br>
                <nobr>=== GalR ===</nobr><br>
                <nobr>%EC_GalR</nobr><br>
                <nobr>-----MATIKDVARLAGVSVATVSRVINNSPKASEASRLAVHSAMES</nobr><br>
                <nobr>LSYHPNANARALAQQTTETVGLVVGDVSD-----PFFGAMVKAVEQV</nobr><br>
                <nobr>>ST_GalR</nobr><br>
                <nobr>MERRRRPTLEMVAALAgvGRGTVSRVINGSDQVSPATREAVKRAIKE</nobr><br>
                <nobr>LGYVPNRAARTLVTRRTDTVALVVSENNQKLFAEPFYAGIVLGVGVA</nobr><br>
          </font> </td>
        </tr>
    </table>
			
			</font>
		</td>
	</tr>
</table>
</body>
</html>