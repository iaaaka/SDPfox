package Objects;

import Tree.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.*;
import Exception.*;
import Math.StaticFunction;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;

import javax.imageio.ImageIO;

import painter.ColorPanel;
import painter.Graph;
import painter.LogoPainter;
import Util.*;

public class Project {
	private static final String fp = "00.000000000000000000";
	private AlignmentData data;
	private Positions positions;
	private GroupingMatrixes groupingMatrixes;
	private Node root;
	private String currentLocation ="index.jsp";
	private Profiles profiles;
	private double maxGapPart = 0.3;
	private Node phylogeneticTree;

	public Project() {
	}

	public GroupingMatrixes getGroupingMatrixes() {
		return groupingMatrixes;
	}

	public char getChar(int seq, int pos) {
		return data.alignment.getAcidInChar(seq, pos);
	}
	
	public void setPhylogeneticTree() throws Exception {
		Numerator seqn = new Numerator();
		for(int i=0; i<data.seqNumerator.getNumOfNames();i++)
			seqn.addName(""+i);
		AlignmentData ad = new AlignmentData(data.alignment,seqn);
		phylogeneticTree = StaticFunction.getUPGMATreeByPhylip(ad);
		Vector<Node> leafs = phylogeneticTree.getAllLeafs();
		for(Node n : leafs){
			n.setNumber(Integer.parseInt(n.name));
			n.name = data.seqNumerator.getNameForNum(n.getNumber());
		}
	}
	
	public void setPhylogeneticTree(String tree) throws Exception {
		phylogeneticTree = TreeMaker.makeTreeFromParentheses(tree);
		Vector<Node> leafs = phylogeneticTree.getAllLeafs();
		for(Node n : leafs){
			n.setNumber(data.seqNumerator.getNumForName(n.name));
			if(data.seqNumerator.getNumForName(n.name) == -1)
				throw new Exception("Incorrect sequence name in tree. Sequence '"+n.name+"' is not exist in alignment!");
		}
	}
	
	public void setAlignmentDataWithBestCut(){
		AlignmentData t = StaticFunction.getAlignmentDataWithBestCut(data, phylogeneticTree,maxGapPart);
		if(t != null){
			data = t;
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
			profiles = null;
		}else{
			data.groupNumerator = null;
			data.grouping = null;
			positions = null;
			profiles = null;
		}
	}

	public void setAlignmentData(String file) throws Exception {
		data = new AlignmentData(file);
		positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		profiles = null;
	}
	
	public double getPvalue(){
		return positions.getBernThresholdValue();
	}
	

	public int addNewGroup(String name) throws NumeratorException {
		int i = data.groupNumerator.addName(name);
		data.grouping.setRealGroupCount(i + 1);
		return i;
	}
	
	
	public int[] getPositionsForAlign(int seqno, int[] seqPos){
		int[] result = new int[seqPos.length];
		for(int i=0;i<result.length;i++){
			result[i] = this.data.alignment.getAlignmentPositionForSeqPosition(seqno, seqPos[i]);
		}
		return result;
	}
	
	public int[] getPositionsForSeq(int seqno, int[] alignPos){
		int[] result = new int[alignPos.length];
		for(int i=0;i<result.length;i++){
			result[i] = this.getReference(seqno)[alignPos[i]];
		}
		return result;
	}

	public void setMaxGapPart(String m) {
		setMaxGapPart(Double.parseDouble(m));
	}

	public void setMaxGapPart(double m) {
		if (m < 0) {
			maxGapPart = 0;
		} else if (m > 1) {
			maxGapPart = 1;
		} else {
			maxGapPart = m;
		}
		positions = null;
	}

	public double getMaxGapPart() {
		return maxGapPart;
	}

	public String generateAligmentNumberString(int part) {
		return StaticFunction.generateAligmentNumberString(data.alignment
				.getAlignmetnLength(), part);
	}

	public String generateColoredSequence(int seqNo) {
		return StaticFunction.generateColoredSequence(positions, data.alignment
				.getSequenceInString(seqNo));
	}

	public String[][] get3FrequentAcidsForAllSDPAndAllGroup() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return new String[][] {};
		}
		int[][] grouping = data.grouping.getGroupingForAllGroup();
		int[] sdp = positions.getSDP();
		String[][] result = new String[sdp.length][grouping.length];
		for (int i = 0; i < result.length; i++) {
			for (int j = 0; j < result[i].length; j++) {
				result[i][j] = StaticFunction
						.getFrequentAcidsInPositionInGroup(sdp[i], grouping[j],
								data.alignment, 3);
			}
		}
		return result;
	}

	public int getAlinmentLength() {
		return data.alignment.getAlignmetnLength();
	}

	public String[] getAvarageIdentityForGroups() {
		return getAvarageIdentityForGroups(2);
	}

	public String[] getAvarageIdentityForGroups(int dec) {
		DecimalFormat f = new DecimalFormat(fp.substring(0,
				Math.min(dec, 18) + 3));
		double[] id = StaticFunction.getAvarageIdentityForGroups(data);
		String[] result = new String[id.length];
		for (int i = 0; i < id.length; i++) {
			result[i] = f.format(100 * id[i]);
		}
		return result;
	}

	public String getAvarageIdentityForWholeAlignment() {
		return getAvarageIdentityForWholeAlignment(2);
	}

	public void setGroupingMatrixes(GroupingMatrixes gm) throws Exception {
		String e = this.data.seqNumerator.equalsWithNumerator(gm.getNumerator());
		if (e != "") {
			throw new Exception(e);
		} else
			groupingMatrixes = gm;
	}
	
	public double[] getWeightForGroupsByShuffling(int randNo, int sdpSelNo){
		int[] posNos = new int[this.getAlinmentLength()];
		for(int i=0;i<posNos.length;i++){
			posNos[i]=i;
		}
		double[] s = new double[this.getGroupCount()];
		double[] m = new double[this.getGroupCount()];
		double[] d = new double[this.getGroupCount()];
		int[][] grp = this.getGroupng();
		for(int i=0;i<grp.length;i++){
			s[i] = StaticFunction.calculateCumulativeEntropy(data.alignment, this.positions.getSDP(), grp[i]);
		}
		int size =  this.positions.getSDP().length;
		double[] result = new double[this.getGroupCount()];
		Grouping backup = this.data.grouping;
		int[] grNoArray = backup.getGroupNoArray().clone();
		for(int i=0;i<randNo;i++){
			System.out.println("iter "+i);
			StaticFunction.shuffleIntArray(grNoArray);
			this.data.grouping = new Grouping(grNoArray);
			for(int j=0;j<sdpSelNo;j++){
				int[] sdp = StaticFunction.getSampleWithReplacement(posNos, size);
				grp = this.getGroupng();
				for(int g=0;g<grp.length;g++){
					double ss = StaticFunction.calculateCumulativeEntropy(data.alignment, sdp, grp[g]);
					m[g] += ss;
					d[g] += ss*ss;
				}
			}
		}
		System.out.println("shufl");
		for(int i=0;i<s.length;i++){
			m[i] /= (randNo*sdpSelNo);
			d[i] /= (randNo*sdpSelNo);
			d[i] = Math.sqrt(d[i] - m[i]*m[i]);
			System.out.println(s[i]+"\t"+m[i]+"\t"+d[i]);
			result[i] = (s[i] - m[i])/d[i];
		}
		this.data.grouping = backup;
		return result;
	}
	
	
	public double getZscoreOfCumulativeEntropyForGroup(int grno){
		int[] group = this.data.grouping.getGroupingForGroup(grno);
		double s = StaticFunction.calculateCumulativeEntropy(data.alignment, this.positions.getSDP(), group);
		double[] sm = StaticFunction.calculateSigmaAndAverageOfDistributionEntropyForAlignment(data.alignment, group[0],this.positions.getSDP().length);
		return (s-sm[0])/sm[1];
	}

	public void setGroupingMatrixes(String fname, int matrix_type)
			throws Exception {
		GroupingMatrixes gm = new GroupingMatrixes(fname, matrix_type);
		setGroupingMatrixes(gm);
	}

	public String getAvarageIdentityForWholeAlignment(int dec) {
		DecimalFormat f = new DecimalFormat(fp.substring(0,
				Math.min(dec, 18) + 3));
		return f.format(100 * data.alignment
				.getAvarageIdentityForWholeAlignment());
	}

	public String[] getColumnInStringForAllGroupForSPosition(int pos) {
		String[] result = new String[this.getGroupCount()];
		int[][] grouping = data.grouping.getGroupingForAllGroup();
		for (int i = 0; i < result.length; i++) {
			result[i] = this.getColumnInStringForGroup(pos, grouping[i]);
		}
		return result;
	}

	/**
	 * 
	 * @return ������������ � ��� ��� ���� �����. [����� ���][����� ������]
	 */
	public String[][] getColumnInStringForAllSDPandAllGroup() {
		if(getGroupCount() <3 ){
			return new String[0][0];
		}
		int[] sdp = positions.getSDP();
		String[][] result = new String[sdp.length][this.getGroupCount()];
		int[][] grouping = data.grouping.getGroupingForAllGroup();
		for (int i = 0; i < sdp.length; i++) {
			for (int j = 0; j < this.getGroupCount(); j++) {
				result[i][j] = getColumnInStringForGroup(sdp[i], grouping[j]);
			}
		}
		return result;
	}

	/**
	 * 
	 * @param group
	 *            ����� ������
	 * @param pos
	 *            ����� �������
	 * @param seqs
	 *            ������ � �������� ������������������ � �����. �� �������
	 *            ������� - ���-�� ������������������ � ������.
	 * @return ������ �� ����� �������������� � ������ ������ � ������ �������
	 */
	private String getColumnInStringForGroup(int pos, int[] seqs) {
		String result = "";
		for (int i = 1; i <= seqs[0]; i++) {
			result += (char) data.alignment.getAcidInChar(seqs[i], pos);
		}
		return result;
	}

	private String getGroupCompoung(int[] gr) {
		if (gr[0] == 0)
			return "[]";
		StringBuffer result = new StringBuffer();
		result.append('[');
		for (int i = 1; i < gr[0]; i++) {
			result.append(gr[i]).append(',');
		}
		result.append(gr[gr[0]]).append(']');
		return result.toString();
	}
	
	public boolean whithPositions(){
		return positions != null;
	}

	public int getGroupCount() {
		return data.groupNumerator.getNumOfNames();
	}

	public int getGroupForSequence(int seqNo) {
		return data.grouping.getGroupForSeq(seqNo);
	}

	public String getGroupName(int i) {
		return data.groupNumerator.getNameForNum(i);
	}

	public int[][] getGroupng() {
		return data.grouping.getGroupingForAllGroup();
	}
	
	public int[] getSDP(){
		return positions.getSDP();
	}
	
	public String getGroups() {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < data.groupNumerator.getNumOfNames(); i++) {
			if(i == data.groupNumerator.getNumOfNames()-1)
				result.append(data.groupNumerator.getNameForNum(i));
				else
					result.append(data.groupNumerator.getNameForNum(i) + "\t");
		}
		return result.toString();
	}

	/**
	 * ������ ������ ��� ������������ javascript � �������� ������������������� �
	 * ���� ������. ���� [s1,s2,..]
	 * 
	 * @return ������ ����� �������������� �������.
	 */
	public String[] getGroupsCompound() {
		int[][] gr = data.grouping.getGroupingForAllGroup();
		String[] result = new String[gr.length];
		for (int i = 0; i < result.length; i++) {
			result[i] = getGroupCompoung(gr[i]);
		}
		return result;
	}

	public int getGroupSize(int grNo) {
		return data.grouping.getGroupSize(grNo);
	}

	/**
	 * 
	 * @param dec
	 *            ���������� ����� ����� �������
	 * @return � ��������� � 100 �� ���������. ������ ������� - �����
	 *         �������������������
	 */
	public String[][] getIdentityMatrixForGroup(int grNo) {
		return getIdentityMatrixForGroup(grNo, 2);
	}

	/**
	 * 
	 * @param grNo
	 *            ����� ������
	 * @param dec
	 *            ���������� ����� ����� �������
	 * @return � ��������� � 100 �� ���������. ������ ������� - �����
	 *         �������������������
	 */
	public String[][] getIdentityMatrixForGroup(int grNo, int dec) {
		int[] grouping = data.grouping.getGroupingForGroup(grNo);
		DecimalFormat f = new DecimalFormat(fp.substring(0,
				Math.min(dec, 18) + 3));
		String[][] result = new String[grouping[0]][grouping[0] + 1];
		for (int i = 0; i < grouping[0]; i++) {
			result[i][0] = data.seqNumerator.getNameForNum(grouping[i + 1]);
			for (int j = 1; j <= grouping[0]; j++) {
				result[i][j] = f.format(100 * data.alignment.getIdentity(
						grouping[i + 1], grouping[j]));
			}
		}
		return result;
	}

	/**
	 * 
	 * @return � ��������� � 100 �� ���������. ������ ������� - �����
	 *         �������������������
	 */
	public String[][] getIdentityMatrixForWholeAlignment() {
		return getIdentityMatrixForWholeAlignment(2);
	}
	
	public double[][] getIDMAtrix(){
		return data.alignment.getIdentityMatrix();
	}

	/**
	 * 
	 * @param dec
	 *            ���������� ������ ����� �������
	 * @return � ��������� � 100 �� ���������. ������ ������� - �����
	 *         �������������������
	 */
	public String[][] getIdentityMatrixForWholeAlignment(int dec) {
		String[][] result = new String[data.alignment.getAlignmentCount()][data.alignment
				.getAlignmentCount() + 1];
		DecimalFormat f = new DecimalFormat(fp.substring(0,
				Math.min(dec, 18) + 3));
		
		for (int i = 0; i < result.length; i++) {
			result[i][0] = data.seqNumerator.getNameForNum(i);
			for (int j = 1; j < result[i].length; j++) {
				result[i][j] = f.format(100 * data.alignment.getIdentity(i,
						j - 1));
			}
		}
		return result;
	}

	public Graph getProbabilityGraph() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return null;
		}
		return StaticFunction.getProbabilityGraphForPositions(positions);
	}

	public int getThresholdPositionNamber() {
		return positions.getThresholdPositionNamber();
	}

	public int getThreshold() {
		return positions.bernThreshold+1;
	}

	public void printWeights(PrintWriter pw) {
		this.getProfiles();
		pw.print("Seq names\t");
		for (int i = 0; i < data.groupNumerator.getNumOfNames(); i++) {
			pw.print(data.groupNumerator.getNameForNum(i) + "\t");
		}
		pw.println();
		for (int i = 0; i < data.seqNumerator.getNumOfNames(); i++) {
			pw.print(data.seqNumerator.getNameForNum(i) + "\t");
			double[] w = profiles.getWeightsForAllGroup(data.alignment
					.getSeqInInt(i));
			for (int j = 0; j < data.groupNumerator.getNumOfNames(); j++) {
				pw.print(w[j] + "\t");
			}
			pw.println();
		}
	}

	public void printPositions(PrintWriter pw, int ref_seq) {
		int[] ref = data.alignment.getReference(ref_seq);
		for (int i = 0; i < positions.positions.length; i++) {
			pw.println(positions.positions[i].aligNo + "\t"
					+ ref[positions.positions[i].aligNo] + "\t"
					+ data.alignment.getSequenceInString(ref_seq).charAt(positions.positions[i].aligNo)+"\t"
					+ positions.positions[i].z_score);
		}
	}

	public int getSequenceNo(String name) {
		return data.seqNumerator.getNumForName(name);
	}

	public int getGroupNo(String name) {
		return data.groupNumerator.getNumForName(name);
	}

	public GroupingMatrixes sdpTree(final int iter, int gr_no, int seq_no,
			PrintWriter out, final PrintStream log,boolean with_sdpgroup) {
		return sdpTree(iter, gr_no, seq_no, out, log, null,with_sdpgroup);
	}

	/**
	 * 
	 * @param iter
	 *            ����� ��������
	 * @param gr_no
	 *            ����� ����� �� ������� �������� �����������
	 * @param seq_no
	 *            ����� ������������������ � ������
	 * @param out
	 *            ���� ������� ������ �������������� � �������� �� ������
	 *            ������������ ������������
	 * @param log
	 *            ���������
	 * @param sm
	 *            ������� �����������
	 * @return
	 */
	public GroupingMatrixes sdpTree(final int iter, int gr_no, int seq_no,
			PrintWriter out, final PrintStream log, final PrintWriter sm,boolean with_sdpgroup) {
		final int[][] mtx = new int[iter][];
		StaticFunction.generateGroupingMatrixes(data, iter, gr_no, seq_no,
				new GroupingMatrixGeneratorListener() {
					double part = 0.05;
					int itr = 0;
					public void matrixComplete(int[] matrix) {
						if (sm != null) {
							for (int i = 0; i < matrix.length; i++) {
								sm.print(matrix[i] + "\t");
							}
							sm.println();
						}
						mtx[itr] = matrix;
						itr++;
						if ((double) itr / iter >= part) {
							if(log != null)
								log.print("\r" + (int) (part * 100) + "% completed");
							part += 0.05;
							if (sm != null)
								sm.flush();
						}
					}
				}, maxGapPart);
		if(log != null)
			log.println("\r100% complete");
		GroupingMatrixes grm = new GroupingMatrixes(mtx);		
		grm.setNumerator(data.seqNumerator);
		root = TreeMaker.makeUPGMATreeFromDistantMatrix(grm
				.getDistanceMatrix(), data.seqNumerator);
		if(out != null)
			out.println(root);
		try{
			this.setGroupingMatrixes(grm);
		}catch(Exception e){
			e.printStackTrace();
		}
		setGroupingFromGroupingMatirx(with_sdpgroup);
		ArrayList<Group> gr = StaticFunction.transformInGroup(data);
		if(out != null){
			for (int i = 0; i < gr.size(); i++) {
				gr.get(i).printGroup(out);
			}
		}
		return grm;
	}
	
	public Node getRoot(){
		return root;
	}
	
	public int[] getSeqIdForGroup(int gr_id) {
		return data.grouping.getSequencesForGr(gr_id);
	}
	
	public void saveGroupingMatrixImage(GroupingMatrixes grm,String fname,int size){
		painter.ColorPanel.saveImageJPG(fname, size, grm.getFrequencesMatrix());
	}

	public void printPositions(PrintWriter pw) {
		for (int i = 0; i < positions.positions.length; i++) {
			pw.println(positions.positions[i].aligNo + "\t"
					+ positions.positions[i].z_score);
		}
	}

	public void printLogo(int grNo, OutputStream os) throws IOException {
		int width, height = 300;
		width = getSortedForAlignmentPositionSDP().length * 60;
		BufferedImage img = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_RGB);
		Graphics gr = img.getGraphics();
		gr.setColor(Color.WHITE);
		gr.fillRect(0, 0, width, height);
		LogoPainter painter = new LogoPainter(StaticFunction.getAcids(),
				getProfiles().getInformations(grNo), getProfiles()
						.getFrequencies(grNo), getSortedForZscoreSDP(), width,
				height, gr);
		painter.paintComponent(gr);
		ImageIO.write(img, "png", os);
	}

	public Profiles getProfiles() {
		if (profiles == null) {
			if (positions == null) {
				positions = StaticFunction.calculateZ_scoreForAll(data,
						maxGapPart);
			}
			if (positions == null) {
				return null;
			}
			profiles = new Profiles(positions.getSDP(), data.grouping
					.getGroupingForAllGroup(), data.alignment);
		}
		return profiles;
	}

	/**
	 * 
	 * @param seqNo
	 *            ����� ������������������
	 * @return ������ � ������� � ������ ��� ������ ������� ���������������
	 *         ����� ����� �� ������ ������������������. ���� ��� �� -1.
	 */
	public int[] getReference(int seqNo) {
		return data.alignment.getReference(seqNo);
	}

	public String[] getRowForSmallProfileTable(int grNo, int seqNo) {
		DecimalFormat df = new DecimalFormat("##0.00");
		Profiles p = this.getProfiles();
		String[] result = new String[5];
		double[] weights = p.getWeightsForAllGroup(data.alignment
				.getSeqInInt(seqNo));
		int mx = StaticFunction.getMaximumIndx(weights);
		result[0] = data.seqNumerator.getNameForNum(seqNo);
		result[1] = data.groupNumerator.getNameForNum(grNo);
		result[2] = df.format(weights[grNo]);
		result[3] = data.groupNumerator.getNameForNum(mx);
		result[4] = df.format(weights[mx]);
		return result;
	}

	public String getSDPIntervals() {
		StringBuffer result = new StringBuffer();
		result.append('[');
		Vector<int[]> data = StaticFunction.calculateSequentialIntervals(this
				.getSortedForAlignmentPositionSDP());
		for (int i = 0; i < data.size() - 1; i++) {
			int[] t = data.get(i);
			result.append('[').append(t[0]).append(',').append(t[1]).append(
					"],");
		}
		if (data.size() > 0) {
			int[] t = data.lastElement();
			result.append('[').append(t[0]).append(',').append(t[1]).append(
					"]]");
		} else {
			result.append(']');
		}
		return result.toString();
	}

	public String getSDPs() {
		int[] sdp = getSortedForAlignmentPositionSDP();
		StringBuffer result = new StringBuffer(sdp.length * 5);
		for (int i = 0; i < sdp.length - 1; i++) {
			result.append(sdp[i]).append(',');
		}
		if (sdp.length > 0)
			result.append(sdp[sdp.length - 1]);
		return result.toString();
	}

	public int getSeqCount() {
		return data.seqNumerator.getNumOfNames();
	}

	public String getSeqName(int i) {
		return data.seqNumerator.getNameForNum(i);
	}

	public String getSequence(int i) {
		return data.alignment.getSequenceInString(i);
	}

	public int getSequenceGroup(int seqNo) {
		return data.grouping.getGroupForSeq(seqNo);
	}

	public Numerator getSequenceNumerator() {
		return data.seqNumerator;
	}

	public char[] getSortedForAlignmentPOsitionAcidsForAllSDPforSeq(int seqNo) {
		int[] sdp = this.getSortedForAlignmentPositionSDP();
		char[] result = new char[sdp.length];
		for (int i = 0; i < sdp.length; i++) {
			result[i] = data.alignment.getAcidInChar(seqNo, sdp[i]);
		}
		return result;
	}
	
	public Positions setGroupingFromGroupingMatirx(boolean with_sdpgroup){
		if(groupingMatrixes == null){
			return this.positions;
		}
		try{
			StaticFunction.setGroupingFromGroupingMatrix(data, groupingMatrixes);
		}catch(Exception e){
			e.printStackTrace();
		}
		if(!with_sdpgroup) {
			positions = StaticFunction.calculateZ_scoreForAll(data,this.maxGapPart);
		}else {
			Grouping gr = StaticFunction.predictGroupingFromLearningSample(data, this.maxGapPart);
			if(gr == null){
				return null;
			}else
				data.grouping = gr;
			int[][] grouping = data.grouping.getGroupingForAllGroup();
			int[] grnoArray = new int[data.seqNumerator.getNumOfNames()];
			int grno=1;
			data.groupNumerator = new Numerator();
			try{
			data.groupNumerator.addName(StaticFunction.NONGROUPED);
			for(int i=1;i<grouping.length;i++){
				if(grouping[i][0] !=0){
					data.groupNumerator.addName("cluster"+grno);
					for(int j=1;j<=grouping[i][0];j++){
						grnoArray[grouping[i][j]] = grno;
					}
					grno++;
				}
			}
			}catch(NumeratorException e){
				e.printStackTrace();
			}
			data.grouping = new Grouping(grnoArray);
			positions = StaticFunction.calculateZ_scoreForAll(data,this.maxGapPart);
		}
		return positions;
	}

	public Positions setGroupingFromGroupingMatirx(int iter_no) {
		data.groupNumerator = new Numerator();
		data.grouping = new Grouping(this.groupingMatrixes
				.getGroupingMatrix(iter_no));
		try {
			data.groupNumerator.addName(StaticFunction.NONGROUPED);
			for (int i = 0; i < data.grouping.getRealGroupCount() - 1; i++) {
				data.groupNumerator.addName("group" + i);
			}
		} catch (NumeratorException e) {
			e.printStackTrace();
		}
		positions = StaticFunction
				.calculateZ_scoreForAll(data, this.maxGapPart);
		return positions;
	}

	public double[] setWeightsForGroupingMatrixes() {
		if (groupingMatrixes == null) {
			return new double[0];
		}
		Numerator grN = data.groupNumerator;
		Grouping gr = data.grouping;
		double[] r = new double[groupingMatrixes.getIterationCount()];
		for (int i = 0; i < r.length; i++) {
			r[i] = setGroupingFromGroupingMatirx(i).getBernThresholdValue();
		}
		data.groupNumerator = grN;
		data.grouping = gr;
		groupingMatrixes.setWeights(r);
		return r;
	}

	public String[] getSortedForAlignmentPositionColumnsInStringForAllSDPForGroup(
			int group) {
		int[] sdp = this.getSortedForAlignmentPositionSDP();
		String[] result = new String[sdp.length];
		int[] grouping = data.grouping.getGroupingForGroup(group);
		for (int i = 0; i < sdp.length; i++) {
			result[i] = getColumnInStringForGroup(sdp[i], grouping);
		}
		return result;
	}

	public int[] getSortedForAlignmentPositionSDP() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return new int[] {};
		}
		int[] result = positions.getSDP();
		Arrays.sort(result);
		return result;
	}

	public int[] getSortedForZscoreSDP() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return new int[] {};
		}
		if (positions == null) {
			return new int[] {};
		}
		int[] result = positions.getSDP();
		return result;
	}

	public String[][] getWeightsForSeq(int seqNo) {
		getProfiles();
		String[][] result = new String[this.getGroupCount()][2];
		DecimalFormat df = new DecimalFormat("##0.00");
		double[] weights = profiles.getWeightsForAllGroup(data.alignment
				.getSeqInInt(seqNo));
		for (int i = 0; i < this.getGroupCount(); i++) {
			result[i][0] = this.getGroupName(i);
			result[i][1] = df.format(weights[i]);
		}
		return result;
	}

	public double[] getAllZscoreSortedForAlignmentPosition() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return null;
		}
		return positions.getZscores();
	}
	
	public double[][] getSDPandZscore(){
		double[][] result = new double[positions.bernThreshold + 1][2];
		for(int i=0;i<result.length;i++){
			result[i][0] = positions.positions[i].aligNo;
			result[i][1] = positions.positions[i].z_score;
		}
		return result;
	}

	public String[] getZscoreForSDP() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return new String[] {};
		}
		String[] result = new String[positions.bernThreshold + 1];
		DecimalFormat df = new DecimalFormat("###0.00");
		for (int i = 0; i < result.length; i++) {
			if (positions.positions[i].z_score == -999999) {
				result[i] = "none";
			} else {
				result[i] = df.format(positions.positions[i].z_score);
			}
		}
		return result;
	}
	
	public double getThresholdPvalue(){
		return positions.getBernThresholdValue();
	}
	
	public String[] getPvalueForSDP() {
		if (positions == null) {
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		}
		if (positions == null) {
			return new String[] {};
		}
		String[] result = new String[positions.bernThreshold + 1];
		DecimalFormat df = new DecimalFormat("###0.00");
		for (int i = 0; i < result.length; i++) {
			if (positions.positions[i].positionProb == -999999) {
				result[i] = "none";
			} else {
				result[i] = df.format(positions.positions[i].positionProb);
			}
		}
		return result;
	}

	public void moveSequenceForBestWeight() {
		if(getGroupCount() < 3){
			return;
		}
		data.grouping = StaticFunction.getNewGroupingForProfile(data, this
				.getProfiles());
		positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
		profiles = null;
	}

	public void moveSequences(int newGr, int[] seqsNo) {
		profiles = null;
		data.grouping.moveSeqs(seqsNo, newGr);
		positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
	}

	public void moveSequences(int newGr, String seqs) {
		moveSequences(newGr, StaticFunction.parseStringToIntArray(seqs));
	}

	public void printAlignment(PrintWriter pw) {
		ArrayList<Group> g = StaticFunction.transformInGroup(data);
		for (int i = 0; i < g.size(); i++) {
			g.get(i).printGroup(pw);
		}
	}

	public void randomizeGroup(int grCount, int seqCount) {
		profiles = null;
		StaticFunction.randomizeGrouping(grCount, seqCount, data);
		positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
	}

	public void sdpGroup() {
		if(getGroupCount() < 3){
			return;
		}
		profiles = null;
		data.grouping = StaticFunction.predictGroupingFromLearningSample(data,
				maxGapPart);
		positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
	}
	
	public boolean setAlignmentData(AlignmentData a) throws Exception {
			data = a;
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
			profiles = null;
			groupingMatrixes = null;
			root = null;
			return true;
	}
	
	public String getProfileToString(int gr_n){
		return profiles.getProfileToString(gr_n);
	}

	public boolean setAlignmentData(InputStream is) throws Exception {
		if (is != null) {
			try {
				data = new AlignmentData(is);
			} catch (Exception e) {
				data = null;
				throw e;
			}
			positions = StaticFunction.calculateZ_scoreForAll(data, maxGapPart);
			profiles = null;
			groupingMatrixes = null;
			root = null;
			return true;
		} else {
			return false;
		}

	}

	public void setThreshold(int t) {
		profiles = null;
		t = Math.min(t, data.alignment.getAlignmetnLength());
		if (t < -1)
			t = -1;
		positions.bernThreshold = t;
	}

	public boolean withAlignment() {
		return data != null;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}
}
