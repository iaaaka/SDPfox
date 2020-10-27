package Math;

import java.io.*;
import java.util.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

import painter.ColorFrame;
import Exception.AlignmentException;
import Objects.*;
import Util.*;
import Tree.*;
import painter.Graph;
import Exception.*;

public class StaticFunction {

	private static final MandDInfMatrix MATRIX = new MandDInfMatrix();

	public static final int GDE_TYPE = 0;
	public static String phylip_protdist = "phylip protdist";
	public static String phylip_neigbor = "phylip neighbor";
	public static final int PHYLIP_TYPE = 1;

	private static final BlosumMatrix BLOSUM62 = new BlosumMatrix(62);

	public static final String NONGROUPED = "nongrouped";

	public static final int MINIMAL_GROUP_SIZE = 2;

	public static final double MAX_PART_BEFORE_THRESHOLD = 0.5;

	// public static long[] test = new long[10];
	public static final double[] LOGs;

	public static final double LOG2PI = Math.log(2 * Math.PI);

	public static final double[] LOGs_OF_FACTORIAL = new double[1000];

	public static final double[][] LOGs_OF_NUMBER_OF_COMBINATION = new double[1000][1000];

	static {
		LOGs = new double[5000];
		for (int i = 1; i < LOGs.length; i++) {
			LOGs[i] = Math.log(i);
		}

		for (int i = 0; i < LOGs_OF_FACTORIAL.length; i++) {
			LOGs_OF_FACTORIAL[i] = lnFactorial(1, i);
		}

		for (int i = 0; i < LOGs_OF_NUMBER_OF_COMBINATION.length; i++) {
			for (int j = 0; j <= i; j++) {
				LOGs_OF_NUMBER_OF_COMBINATION[i][j] = calclnNumberOfCombination(
						i, j);
			}
		}
	}

	/**
	 * 
	 * @param column
	 *            Column of alignment in int
	 * @param gr
	 *            grouping by Grouping.getGroupingForAllGroup()
	 * @return [groupNo, last - overall alignment][acidNo, last - gap] number of
	 *         this acid in this group. For group with grounSize <
	 *         MINIMAL_GROUP_SIZE return null; If part of ga� > MAX_GAP_PART or
	 *         good group < 2 return null;
	 */
	private static int[][] calculateAcidFreq(int[] column, int[][] gr,
			double max_gap_part) {
		int grNo = gr.length;
		int[][] result = new int[grNo][21];
		int goodGr = 0;
		for (int i = 1; i < grNo; i++) {
			if (gr[i][0] < MINIMAL_GROUP_SIZE) {
				result[i - 1] = null;
			} else {
				for (int j = 1; j <= gr[i][0]; j++) {
					result[i - 1][column[gr[i][j]]]++;
					result[grNo - 1][column[gr[i][j]]]++;
				}
				if (gr[i][0] - result[i - 1][20] < MINIMAL_GROUP_SIZE) {
					result[i - 1] = null;
				} else {
					goodGr++;
				}
			}
		}
		if (goodGr < 2
				|| (column.length - gr[0][0]) * max_gap_part < result[grNo - 1][20]) {
			return null;
		}
		return result;
	}

	public static void saveFile(String data, String fname) {
		try {
			BufferedWriter bf = new BufferedWriter(new FileWriter(fname));
			bf.write(data);
			bf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���������� ��� ������� ������������� �� �������� Z-score. �� ��������
	 * ������� - �������� ��������������� ������ (�.�. ���� ��� �������� �����
	 * 1, �� ��� SDP ������� ������������� 0 � 1 ���� �������)
	 * 
	 * @param is
	 *            ����� � ������� �������� ������������ � gde-�������
	 * @return
	 * @throws AlignmentException
	 *             ������ ������������
	 * @throws IOException
	 * @throws NumeratorException
	 *             ��� ������������������ ��� ��� ������ ����� ���� ���
	 */
	public static int[] calculateSDP(InputStream is, double max_gap_part)
			throws Exception{
		AlignmentData ad = readAlignment(is);
		Positions p = calculateZ_scoreForAll(ad, max_gap_part);
		if (p == null)
			return null;
		return p.getSortedForZscorePositions();
	}
	
	public static int[] toArray(List<Integer> t) {
		int[] r = new int[t.size()];
		for(int i=0;i<r.length;i++)
			r[i] = t.get(i);
		return r;
	}
	
	public static Node getUPGMATreeByPhylip(AlignmentData ad) throws Exception{
		(new File("outfile")).delete();
		StaticFunction.saveAlignment(ad, "tmp.phy", StaticFunction.PHYLIP_TYPE);
		Process p = Runtime.getRuntime().exec(phylip_protdist);
		//distance
		Thread t = copyStreamNT(p.getErrorStream(), new OutputStream(){
			public void write(int b) throws IOException {
				//System.out.print((char)b);
			}
		});

		Thread t_ = copyStreamNT(p.getInputStream(), new OutputStream(){
			public void write(int b) throws IOException {
				//System.out.print((char)b);
			}
		});
		PrintWriter in = new PrintWriter(new OutputStreamWriter(p.getOutputStream()));
		in.println("tmp.phy");
		in.println("y");
		in.println();
		in.flush();
		t.join();
		t_.join();		
		p.waitFor();
		in.close();
		(new File("tmp.phy")).delete();	
		(new File("outfile")).renameTo(new File("tmp.dst"));	
		//tree
		p = Runtime.getRuntime().exec(phylip_neigbor);
		t = copyStreamNT(p.getErrorStream(), new OutputStream(){
			public void write(int b) throws IOException {
				//throw new IOException("R does not work!");
			}
		});

		t_ = copyStreamNT(p.getInputStream(), new OutputStream(){
			public void write(int b) throws IOException {
				//System.out.print((char)b);
			}
		});
		in = new PrintWriter(new OutputStreamWriter(p.getOutputStream()));
		in.println("tmp.dst");
		in.println("n");
		in.println("y");
		in.println();
		in.flush();
		t.join();
		t_.join();		
		p.waitFor();
		in.close();
		(new File("tmp.dst")).delete();
		(new File("outfile")).delete();
		BufferedReader bf = new BufferedReader(new FileReader("outtree"));
		String tree = new String();
		for(String l = bf.readLine();l != null;l = bf.readLine())
			tree += l;
		bf.close();
		(new File("outtree")).delete();	
		return TreeMaker.makeTreeFromParentheses(tree);
	}
	
	public static Thread copyStreamNT(final InputStream is, final OutputStream os){
		Thread t = new Thread(){
			public void run(){
				try{
					copyStream(is,os);
					if(os!=null) 
						os.close();
					is.close();
				}catch(Exception e){e.printStackTrace();}
			}
		};
		t.start();
		return t;
	}
	
	public static  void  copyStream(InputStream is, OutputStream os) throws Exception{
		byte[] buf = new byte[10000];
		for(int len;(len=is.read(buf))>0;){
			if(os!=null)os.write(buf,0,len);
		}
	}
	
	public static AlignmentData getAlignmentDataWithBestCut(AlignmentData ad, Node root,double max_gap_part){
		Double min_pv = Double.MAX_VALUE;
		AlignmentData r = null;
		HashSet<Double> thrs = findAllPossibleThr(root);
		for(Double d: thrs){
			//System.out.println(d);			
			Vector<Node> clusters = root.cutAtLevel(d);
			AlignmentData tmp = getAlignmentDataForCut(ad,clusters);
			Positions p = calculateZ_scoreForAll(tmp, max_gap_part);
			double pv = -1;
			if(p!= null){
				pv = p.getBernThresholdValue();
				if(pv < min_pv){
					r = tmp;
					min_pv = pv;
				}
			}
			//System.out.println("\t"+tmp.grouping.getRealGroupCount()+"\t"+pv);
		}
		return r;
	}
	
	private static AlignmentData getAlignmentDataForCut(AlignmentData ad,Vector<Node> clusters){
		AlignmentData r = new AlignmentData(ad.alignment,ad.seqNumerator);
		try{
			r.groupNumerator.addName(NONGROUPED);
			int gr_no = 1;
			int[] gr_nos = new int[r.alignment.getAlignmentCount()];
			for(Node n : clusters){
				Vector<Node> leafs = n.getAllLeafs();
				if(leafs.size() > 1){
					r.groupNumerator.addName("cluster" + gr_no);
					for(Node l : leafs)
						gr_nos[l.getNumber()] = gr_no;
					gr_no++;
				}			
			}	
			r.grouping = new Grouping(gr_nos);
		}catch(NumeratorException e){
			e.printStackTrace();
		}	
		return r;
	}
	
	private static HashSet<Double> findAllPossibleThr(Node root){
		HashSet<Double> r = new HashSet<Double>();
		for(int i=0;i<root.getChildNum();i++)
			findAllPossibleThr(root.getChild(i),0,r);
		return r;
	}
	
	private static void findAllPossibleThr(Node n,double curr_l,HashSet<Double> r){
		if(n.getChildNum() > 0)
			r.add(curr_l+n.getLength());
		for(int i=0;i<n.getChildNum();i++)
			findAllPossibleThr(n.getChild(i),curr_l+n.getLength(),r);
	}
	
	public static void hyphenByWord(String w,PrintStream ps,int min_l) {
		int curr_p = 0;
		for(;curr_p + min_l< w.length();) {
			int next_p = w.indexOf(' ', curr_p+min_l);
			if(next_p == -1)
				next_p = w.length();
			ps.println(w.substring(curr_p,next_p));
			curr_p = next_p+1;
		}
		if(curr_p <= w.length())
		ps.println(w.substring(curr_p));
	}
	
	public static void copyStream(BufferedReader bf, PrintStream ps,int line_length)throws IOException {
		for (String line = bf.readLine(); line != null; line = bf.readLine()) {
			hyphenByWord(line,ps,line_length);
		}
	}

	public static void copyStream(BufferedReader bf, PrintStream ps)
			throws IOException {
		for (String line = bf.readLine(); line != null; line = bf.readLine()) {
			ps.println(line);
		}
	}

	public static void printFileIntoPrintStream(String fname, PrintStream ps)
			throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(new File(fname)));
		for (String line = bf.readLine(); line != null; line = bf.readLine()) {
			ps.println(line);
		}
	}
	
	public static double average(double[] a){
		double result = 0;
		for(double d : a){
			result += d;
		}
		return (result/a.length);
	}
	
	public static Set<String> getIntersection(Set<String> s1,Set<String> s2){
		Set<String> r = new HashSet<String>();
		for(String s : s1)
			if(s2.contains(s))
				r.add(s);
		return r;
	}
	
	/**
	 * 
	 * @param a splitting 1
	 * @param b splitting 2
	 * @return 1-MI(a,b)/H(a,b)
	 */
	public static double calculateMIdistance(int[] a,int[] b){
		Hashtable<Integer, Integer> groupnoa = new Hashtable<Integer, Integer>();
		Hashtable<Integer, Integer> groupnob = new Hashtable<Integer, Integer>();
		for(int i=0;i<a.length;i++){
			if(!groupnoa.containsKey(a[i])){
				groupnoa.put(a[i], groupnoa.size());
			}
			if(!groupnob.containsKey(b[i])){
				groupnob.put(b[i], groupnob.size());
			}
		}
		double[] grfa = new double[groupnoa.size()];
		double[] grfb = new double[groupnob.size()];
		
		double[][] pairf = new double[groupnoa.size()][groupnob.size()];
		for(int i=0;i<a.length;i++){
			grfa[groupnoa.get(a[i])] += 1;
			grfb[groupnob.get(b[i])] += 1;
			pairf[groupnoa.get(a[i])][groupnob.get(b[i])] += 1;
		}
		double mi = 0, s = 0;
		for(int i=0;i<pairf.length;i++){
			for(int j=0;j<pairf[i].length;j++){
				if(pairf[i][j] == 0)
					continue;
				s += -pairf[i][j]*Math.log(pairf[i][j]/a.length)/a.length;
				mi += pairf[i][j]*Math.log(pairf[i][j]/(grfa[i]*grfb[j])*a.length)/a.length;
			}
		}
		if(s == 0)
			return 0;
		return (1-mi/s);		
	}

	public static void print2Array(int[][] data) {
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(data[i][j] + "\t");
			}
			System.out.println();
		}
	}

	/**
	 * 
	 * @param d
	 *            ������� ���������
	 * @param g
	 *            �����������
	 * @return ������ ������� ���������� �� �� ������� ��� � grouping, ���� �
	 *         ������ 0 ��������� - ������� ���������� 0. ���� ���� ������� ��
	 *         1.
	 */
	public static double[] getAvarageIdentityForGroups(double[][] d, Grouping g) {
		int[][] grouping = g.getGroupingForAllGroup();
		double[] result = new double[grouping.length];
		for (int i = 0; i < grouping.length; i++) {
			for (int j1 = 1; j1 <= grouping[i][0]; j1++) {
				if (grouping[i][0] == 1)
					result[i] = 1;
				for (int j2 = j1 + 1; j2 <= grouping[i][0]; j2++) {
					result[i] += d[grouping[i][j1]][grouping[i][j2]];
				}
			}
			if (grouping[i][0] <= 1)
				result[i] = grouping[i][0];
			else {
				result[i] /= (grouping[i][0] * (grouping[i][0] - 1) / 2);
			}
		}
		return result;
	}

	public static int findMaxIndex(int[] a) {
		if (a.length == 0)
			return -1;
		int index = 0;
		for (int i = 1; i < a.length; i++) {
			if (a[i] > a[index]) {
				index = i;
			}
		}
		return index;
	}

	/**
	 * выдает массив с номерами последовательностей. В них остаются
	 * последовательности такие что id между ними меньше thr. Работает так:
	 * определяет для каждой последовательности с каким числом
	 * последовательностей у нее id больше порога. Потом выбрасывает ту
	 * последовательность у которой это число больше. Так продолжается пока все
	 * id не окажутся ниже порога.
	 * 
	 * @param identMatrix
	 * @param thr
	 * @return
	 */
	public static int[] filterByIdentity(double[][] identMatrix, double thr) {
		return filterByIdentity(identMatrix,thr,new int[]{});
	}
	
	public static int[] filterByIdentity(double[][] identMatrix, double thr,int[] seqsWithPriority) {
		int[] nums = new int[identMatrix.length];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i;
		}
		int size = nums.length;
		do {
			int r = findVertexWithMaxConnections(identMatrix, thr, nums,seqsWithPriority);
			if (r == -1)
				break;
			nums[r] = -1;
			size--;
		} while (true);
		int[] result = new int[size];
		int ind = 0;
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != -1) {
				result[ind] = nums[i];
				ind++;
			}
		}
		return result;
	}
	
	
	/**
	 * Если это возможно выдает узел не из seqsWithPriority
	 * @param identMatrix
	 * @param thr
	 * @param nums
	 * @param seqsWithPriority
	 * @return
	 */
	private static int findVertexWithMaxConnections(double[][] identMatrix,
			double thr, int[] nums,int[] seqsWithPriority) {
		int[] connCount = new int[nums.length];
		for (int i = 0; i < nums.length; i++) {
			if (nums[i] != -1) {
				for (int j = i + 1; j < nums.length; j++) {
					if (identMatrix[i][j] > thr && nums[j] != -1) {
						connCount[i]++;
						connCount[j]++;
					}
				}
			}
		}
		int result = findMaxIndex(connCount);
		if (connCount[result] == 0) {
			return -1;
		}
		if(Arrays.binarySearch(seqsWithPriority, result)>=0){
			for(int i=0;i<connCount.length;i++){
				if(nums[i] != -1 && connCount[result] == connCount[i] && Arrays.binarySearch(seqsWithPriority, i) < 0)
					return i;
			}
		}
		return result;
	}

	
	public static int[] filterByName(AlignmentData old, String pattern){
		Vector<Integer> seqs = new Vector<Integer>();
		for(int i=0;i<old.seqNumerator.getNumOfNames();i++){
			if(old.seqNumerator.getNameForNum(i).indexOf(pattern) != -1){
				seqs.add(i);
			}
		}
		int[] seqsInt = new int[seqs.size()];
		for(int i=0;i<seqs.size();i++){
			seqsInt[i] = seqs.get(i);
		}
		return seqsInt;
	}
	
	public static AlignmentData createNewAlignmentDataFromSeqsNums(int[] seqs, AlignmentData old){
		AlignmentData result = new AlignmentData();
		try{
		result.groupNumerator.addName(StaticFunction.NONGROUPED);
		result.grouping = new Grouping(seqs.length,1);
		for(int i=0;i<seqs.length;i++){
			result.alignment.addSeq(old.alignment.getSequenceInString(seqs[i]), old.seqNumerator.getNameForNum(seqs[i]));
			result.grouping.addSeq(0);
			result.seqNumerator.addName(old.seqNumerator.getNameForNum(seqs[i]));
		}
		result.alignment.calculateIntMatrix(result.seqNumerator);
		}catch(Exception e){
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * Возвращает массив последовательностей удовлетворяющих следующему
	 * свойству: 1) Столбцы где доля гэпов ниже выше gapsPartForCons
	 * обозначаются "консервативными" 2) Последовательности в которых в более
	 * partSequentalGaps последовательных "консервативных" позициях заняты
	 * гэпами выкидываются. Это делается итеративно до схождения.
	 * 
	 * @param gapsPartForCons
	 * @param partSequentalGaps
	 * @return
	 */
	public static int[] filterByAlignmentCoverage(double gapsPartForCons,double partSequentalGaps,Alignment a) {
		int[] seqs = new int[a.getAlignmentCount()];
		for(int i=0;i<seqs.length;i++){
			seqs[i] = i;
		}
		return filterByAlignmentCoverage(gapsPartForCons,partSequentalGaps,a,seqs);
	}

	/**
	 * Возвращает массив последовательностей удовлетворяющих следующему
	 * свойству: 1) Столбцы где доля гэпов ниже выше gapsPartForCons
	 * обозначаются "консервативными" 2) Последовательности в которых в более
	 * partSequentalGaps последовательных "консервативных" позициях заняты
	 * гэпами выкидываются. Это делается итеративно до схождения.
	 * 
	 * @param gapsPartForCons
	 * @param partSequentalGaps
	 * @return
	 */
	private static int[] filterByAlignmentCoverage(double gapsPartForCons,double partSequentalGaps,Alignment a,int[] seqs) {
		int consSize =0;
		double[] partOfGaps = new double[a.getAlignmetnLength()];
		for(int i=0;i<partOfGaps.length;i++){
			partOfGaps[i] = calculateGapPartInPositionForSeqs(a.getColumnInInt(i),seqs);
			if(partOfGaps[i] < gapsPartForCons)
				consSize++;
		}
		int[] consPos = new int[consSize];
		for(int i=0;i<partOfGaps.length;i++){
			if(partOfGaps[i] < gapsPartForCons){
				consPos[consPos.length-consSize] = i;
				consSize--;
			}
		}
		int seqSize=0;
		for(int i=0;i<seqs.length;i++){
			if(isBadByAlignmentCoverage(partSequentalGaps,consPos,a.getSeqInInt(seqs[i]))){
				seqs[i] = -1;
			}else{
				seqSize++;
			}
		}
		if(seqSize == seqs.length)
			return seqs;
		int[] newSeqs = new int[seqSize];
		for(int i=0;i<seqs.length;i++){
			if(seqs[i] != -1){
				newSeqs[newSeqs.length-seqSize] = i;
				seqSize--;
			}
		}
		return filterByAlignmentCoverage(gapsPartForCons,partSequentalGaps,a,newSeqs);
	}

	private static boolean isBadByAlignmentCoverage(double partSequentalGaps,int[] consPos, int[] seq) {
		double l = 0;
		for (int i = 0; i < consPos.length; i++) {
			if (seq[consPos[i]] == 20) {
				l++;
			} else {
				l = 0;
			}
			if (l / consPos.length >= partSequentalGaps)
				return true;
		}
		return false;
	}

	private static double calculateGapPartInPositionForSeqs(int[] p, int[] seqs) {
		double gapPart = 0;
		int size = 0;
		for (int i = 0; i < seqs.length; i++) {
			if (seqs[i] != -1) {
				size++;
				if (p[seqs[i]] == 20) {
					gapPart++;
				}
			}
		}
		return gapPart / p.length;
	}

	public static void setGroupingFromConnectingComponents(AlignmentData a,
			double thr) {
		try {
			a.grouping = new Grouping(a.alignment.getAlignmentCount());
			a.groupNumerator = new Numerator();
			int grNo = 0;
			int[] groupNo = searchConnectedComponents(a.alignment
					.getIdentityMatrix(), thr);
			for (int i = 0; i < groupNo.length; i++) {
				a.grouping.addSeq(groupNo[i]);
				grNo = Math.max(grNo, groupNo[i]);
			}
			a.grouping.setRealGroupCount(grNo + 1);
			a.groupNumerator.addName("nongrouped");
			for (int i = 0; i < grNo; i++) {
				a.groupNumerator.addName("group" + i);
			}
		} catch (NumeratorException e) {
			System.out.println(e);
		}
	}

	public static double[] getAvarageIdentityForGroups(AlignmentData a) {
		return getAvarageIdentityForGroups(a.alignment.getIdentityMatrix(),
				a.grouping);
	}

	/**
	 * ���� ������� ���������� �� ������ thr
	 * 
	 * @param identity
	 *            ������� �������������
	 * @param thr
	 *            �����. ���� ������������ ������ thr �� ������� ��������
	 * @return ������ [����� �������] - ����� �������� ������� � 1.
	 */
	public static int[] searchConnectedComponents(double[][] identity,
			double thr) {
		int currentCluster = 1;
		int[] result = new int[identity.length];
		do {
			int start = -1;
			for (int i = 0; i < result.length; i++) {
				if (result[i] == 0) {
					start = i;
					break;
				}
			}
			if (start == -1)
				break;
			breadthFirstSearch(identity, thr, result, start, currentCluster);
			currentCluster++;
		} while (true);
		return result;
	}

	private static void breadthFirstSearch(double[][] identity, double thr,
			int[] clusterNo, int start, int cluster) {
		clusterNo[start] = cluster;
		for (int i = 0; i < identity.length; i++) {
			if (identity[start][i] >= thr && clusterNo[i] != cluster) {
				breadthFirstSearch(identity, thr, clusterNo, i, cluster);
			}
		}
	}

	public static double getAvarageIdentityForAllAlignment(double[][] d) {
		double result = 0;
		for (int i = 0; i < d.length; i++) {
			for (int j = i + 1; j < d.length; j++) {
				result += d[i][j];
			}
		}
		return result / (d.length * (d.length - 1) / 2);
	}

	public static double[][] getIdentityMatrix(Alignment a) {
		double[][] result = new double[a.getAlignmentCount()][a
				.getAlignmentCount()];
		for (int i = 0; i < result.length; i++) {
			result[i][i] = 1;
			for (int j = i + 1; j < result.length; j++) {
				result[i][j] = getIdentityBetweenSequence(a.getSeqInInt(i), a
						.getSeqInInt(j));
				result[j][i] = result[i][j];
			}
		}
		return result;
	}

	public static String getColorForIdentityTable(String identity) {
		double d = Double.parseDouble((new StringTokenizer(identity,",. \n")).nextToken());
		String result = "#";
		int ci = (int) (15 * d / 100);
		result += getHTMLColor(ci) + getHTMLColor(15 - ci);
		return (result + "00");
	}

	private static String getHTMLColor(int i) {
		switch (i) {
		case 0:
			return "00";
		case 1:
			return "11";
		case 2:
			return "22";
		case 3:
			return "33";
		case 4:
			return "44";
		case 5:
			return "55";
		case 6:
			return "66";
		case 7:
			return "77";
		case 8:
			return "88";
		case 9:
			return "99";
		case 10:
			return "AA";
		case 11:
			return "BB";
		case 12:
			return "CC";
		case 13:
			return "DD";
		case 14:
			return "EE";
		case 15:
			return "FF";
		}
		return "";
	}

	public static double getIdentityBetweenSequence(int[] a, int[] b) {
		double result = 0;
		int size = 0;
		for (int i = 0; i < a.length; i++) {
			if (a[i] == b[i] && a[i] != 20) {
				size++;
				result++;
			}
			if (a[i] != b[i]) {
				size++;
			}
		}
		return result / size;
	}

	public static void removeGroup(int gr, int newGr, AlignmentData ad) {
		if (ad.groupNumerator.removeName(gr)) {
			ad.grouping.removeGroup(gr, newGr);
		}
	}

	public static void printArray(double[] data, int decCount, PrintStream out) {
		String df = "###############0.";
		String zero = "00000000000000000000";
		DecimalFormat f = new DecimalFormat(df + zero.substring(0, decCount));

		for (int i = 0; i < data.length; i++) {
			out.println(f.format(data[i]));
		}
	}

	public static void print2Array(double[][] data) {
		DecimalFormat f = new DecimalFormat(" ##0.000");
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				System.out.print(f.format(data[i][j]) + "\t");
			}
			System.out.println();
		}
	}

	public static double calculateEntropy(double[] f, double[] g) {
		double result = 0;
		for (int i = 0; i < f.length; i++) {
			if (f[i] != 0)
				result += f[i] * Math.log(f[i] / g[i]);
		}
		return result;
	}

	public static double[][] calculateAcidFreqForPositions(Alignment a) {
		double[][] result = new double[a.getAlignmetnLength()][20];
		for (int i = 0; i < a.getAlignmetnLength(); i++) {
			int nonGap = 0;
			for (int j = 0; j < a.getAlignmentCount(); j++) {
				if (a.getAcidInInt(j, i) != 20) {
					result[i][a.getAcidInInt(j, i)]++;
					nonGap++;
				}
			}
			for (int j = 0; j < 20; j++) {
				result[i][j] /= nonGap;
			}
		}
		return result;
	}

	public static double[] getGapsCountForPositions(Alignment a) {
		double[] result = new double[a.getAlignmetnLength()];
		for (int i = 0; i < a.getAlignmetnLength(); i++) {
			for (int j = 0; j < a.getAlignmentCount(); j++) {
				if (a.getAcidInInt(j, i) == 20) {
					result[i]++;
				}
			}
		}
		return result;
	}

	/**
	 * ����� �� ������������� ������� ������ �� ����������� ����� �����
	 * 
	 * @param a
	 * @return
	 */
	public static Positions getSortedForNonGapsAndNonConservationPositions(
			Alignment a) {
		double[][] acidFreq = calculateAcidFreqForPositions(a);
		double[] gaps = getGapsCountForPositions(a);
		double[] infs = new double[acidFreq.length];
		double mInf = 0;
		double mGaps = 0;
		for (int i = 0; i < acidFreq.length; i++) {
			infs[i] = calculateEntropy(acidFreq[i], a.acidFreqs);
			mInf += infs[i] / acidFreq.length;
			mGaps += gaps[i] / acidFreq.length;
		}
		for (int i = 0; i < acidFreq.length; i++) {
			// gaps[i] /= (mInf/mGaps);
		}
		Positions result = new Positions(a.getAlignmetnLength());
		for (int i = 0; i < a.getAlignmetnLength(); i++) {
			result.positions[i] = new Position(i);
			result.positions[i].z_score = gaps[i];
		}
		Arrays.sort(result.positions, new Comparator<Position>() {
			public int compare(Position a, Position b) {
				if (a.z_score == b.z_score)
					return Double.compare(b.aligNo, a.aligNo);
				else
					return Double.compare(a.z_score, b.z_score);
			}
		});
		return result;
	}

	private static Position calculatePosition(int[] column, int[][] gr,
			int aligPos, int goodGroupCount, double max_gap_part) {
		Position result = new Position(aligPos);
		int[][] freq = calculateAcidFreq(column, gr, max_gap_part);
		if (freq == null) {
			result.inf = -999999;
			result.z_score = -999999;
			return result;
		}
		int grNum = freq.length - 1;
		double[] smoothFreqForAllColumn = new double[20];
		for (int i = 0; i < 20; i++) {
			smoothFreqForAllColumn[i] = calculateSmooth(freq[grNum], i,
					column.length);
			if (freq[grNum][i] > 0) {
				result.inf_sh += MATRIX.getMInf(goodGroupCount, freq[grNum][i]);
				result.sigma += MATRIX.getDInf(goodGroupCount, freq[grNum][i]);
			}
		}
		result.sigma = Math.sqrt(result.sigma);
		for (int i = 0; i < grNum; i++) {
			if (!(freq[i] == null)) {
				for (int j = 0; j < 20; j++) {
					double freqSmooth = calculateSmooth(freq[i], j,
							column.length);
					double groupPart = (double) column.length / gr[i + 1][0];
					result.inf += freqSmooth
							* Math.log(freqSmooth / smoothFreqForAllColumn[j]
									* groupPart);
				}
			}
		}
		return result;
	}

	public static OutputStream getStreamForStringBuffer(final StringBuffer to) {
		return new OutputStream() {
			public void write(int b) throws IOException {
				to.append((char) b);
			}
		};
	}

	public static double calculateSmooth(int[] freq, int acidNo, int size) {
		double result = 0;
		for (int i = 0; i < 20; i++) {
			result += BLOSUM62.matrix[i][acidNo] * freq[i];
		}
		result = (0.5 * result / Math.sqrt((double) size) + (double) freq[acidNo])
				/ (1 + 0.5 / Math.sqrt((double) size));
		return result;
	}

	public static double calculateSmoothWithPseudocount(int[] freq, int acidNo,
			int size, double[] pseudocount) {
		double result = 0;
		for (int i = 0; i < 20; i++) {
			result += BLOSUM62.matrix[i][acidNo]
					* ((double) freq[i] + pseudocount[i]);
		}
		result = (0.5 * result / Math.sqrt((double) size + 1) + ((double) freq[acidNo] + pseudocount[acidNo]))
				/ (1 + 0.5 / Math.sqrt((double) size + 1));
		return result;
	}
	
	public static String join(int[] d,String s) {
		StringBuffer result = new StringBuffer(1000);
		for (int i = 0; i < d.length; i++) {
			result.append(d[i]);
			if(i != d.length-1)
				result.append(s);
		}

		return result.toString();
	}

	public static String toStringIntArray(int[] data) {
		String result = "";
		for (int i = 0; i < data.length; i++) {
			result += data[i] + " ";
		}

		return result;
	}

	public static int[] getSampleWithReplacement(int[] data, int size) {
		int[] result = new int[size];
		for (int i = 0; i < size; i++) {
			int index = (int) (data.length * Math.random());
			result[i] = data[index];
		}
		return result;
	}

	public static void shuffleIntArray(int begin, int end, int[] data) {
		for (int i = begin; i < end; i++) {
			int indx = (int) (Math.random() * (end - begin));
			int t = data[begin + indx];
			data[begin + indx] = data[i];
			data[i] = t;
		}
	}

	public static void shuffleIntArray(int[] data) {
		shuffleIntArray(0, data.length, data);
	}

	public static void generateGroupingMatrixes(AlignmentData ad,
			int numOfIter, int numOfGroup, int numOfSeqInGr,
			GroupingMatrixGeneratorListener listener, double max_gap_part) {
		// test[0] = -System.currentTimeMillis();
		Grouping backupGrouping = ad.grouping;
		Numerator bacupGroupNumerator = ad.groupNumerator;
		for (int i = 0; i < numOfIter; i++) {
			// test[1] -= System.currentTimeMillis();
			randomizeGrouping(numOfGroup, numOfSeqInGr, ad);
			// test[1] += System.currentTimeMillis();
			Grouping gr = predictGroupingFromLearningSample(ad, max_gap_part);
			Positions p = StaticFunction.calculateZ_scoreForAll(ad, max_gap_part);
			int[] result = null;
			if (gr != null)
				result = gr.getGroupNoArray();
			if (result == null)
				i--;
			else {
				// test[6] -= System.currentTimeMillis();
				listener.matrixComplete(result);
				// test[6] += System.currentTimeMillis();
			}
		}
		ad.grouping = backupGrouping;
		ad.groupNumerator = bacupGroupNumerator;
		// test[0] += System.currentTimeMillis();
		// System.out.println("times:");
		// for(int i=0;i<test.length;i++){
		// System.out.println(test[i]);
		// }
	}

	public static int[][] getGroupingMatrixes(AlignmentData ad, int numOfIter,
			int numOfGroup, int numOfSeqInGr, double max_gap_part) {
		final int[][] result = new int[numOfIter][];
		final int[] i = { 0 };
		generateGroupingMatrixes(ad, numOfIter, numOfGroup, numOfSeqInGr,
				new GroupingMatrixGeneratorListener() {
					public void matrixComplete(int[] matrix) {
						result[i[0]] = matrix;
						i[0]++;
					}
				}, max_gap_part);
		return result;
	}

	public static Grouping predictGroupingFromLearningSample(AlignmentData ad,
			double max_gap_part) {
		// test[2] -= System.currentTimeMillis();
		Grouping result = ad.grouping;
		Grouping temp;
		int limit = 0;
		do {
			// test[3] -= System.currentTimeMillis();
			Positions pos = StaticFunction.calculateZ_scoreForAll(ad.alignment,
					result, max_gap_part);
			// test[3] += System.currentTimeMillis();
			if (pos == null) {
				return null;
			}
			// test[4] -= System.currentTimeMillis();
			Profiles prof = new Profiles(pos.getSDP(), result
					.getGroupingForAllGroup(), ad.alignment);
			// test[4] += System.currentTimeMillis();
			temp = result;
			// test[5] -= System.currentTimeMillis();
			result = getNewGroupingForProfile(ad, prof);
			// test[5] += System.currentTimeMillis();
			limit++;
			if (limit >= 20){
				break;
			}
		} while (!temp.equals(result));
		// test[2] += System.currentTimeMillis();
		return result;
	}

	public static void randomizeGrouping(int noGr, int noSeqInGr,
			AlignmentData ad) {
		if (noSeqInGr < MINIMAL_GROUP_SIZE
				|| noGr * noSeqInGr > ad.alignment.getAlignmentCount())
			return;
		ad.groupNumerator = new Numerator();
		ad.grouping = new Grouping(ad.alignment.getAlignmentCount(), noGr + 1);
		try {
			ad.groupNumerator.addName("nongrouped");
			for (int i = 0; i < ad.alignment.getAlignmentCount() - noGr
					* noSeqInGr; i++) {
				ad.grouping.addSeq(0);
			}
			for (int i = 0; i < noGr; i++) {
				ad.groupNumerator.addName("group" + i);
				for (int j = 0; j < noSeqInGr; j++) {
					ad.grouping.addSeq(i + 1);
				}
			}
			ad.grouping.shuffleGrouping();
		} catch (NumeratorException e) {
			System.out.println(e);
		}
	}

	public static Positions calculateZ_scoreForAll(Alignment alig, Grouping gr,
			double max_gap_part) {
		if (gr.getNumberOfGoodGroup() < 2)
			return null;
		Positions result = new Positions(alig.getAlignmetnLength());
		int[][] grouping = gr.getGroupingForAllGroup();
		// test[7] -= System.currentTimeMillis();
		for (int i = 0; i < alig.getAlignmetnLength(); i++) {
			result.positions[i] = calculatePosition(alig.getColumnInInt(i),
					grouping, i, gr.getNumberOfGoodGroup(), max_gap_part);
		}
		// test[7] += System.currentTimeMillis();
		// test[8] -= System.currentTimeMillis();
		result.setRegression();
		// test[8] += System.currentTimeMillis();
		// test[9] -= System.currentTimeMillis();
		result.setThreshold();
		// test[9] += System.currentTimeMillis();
		return result;
	}

	public static Positions calculateZ_scoreForAll(AlignmentData ad,
			double max_gap_part) {
		return calculateZ_scoreForAll(ad.alignment, ad.grouping, max_gap_part);
	}

	public static Profiles calcProfiles(AlignmentData ad, Positions p) {
		return new Profiles(p.getSDP(), ad.grouping.getGroupingForAllGroup(),
				ad.alignment);
	}

	public static double[][] getWeightsForAllSeqForAllGroup(AlignmentData ad,
			Profiles p) {
		double[][] result = new double[ad.alignment.getAlignmentCount()][];
		for (int i = 0; i < result.length; i++) {
			result[i] = p.getWeightsForAllGroup(ad.alignment.getSeqInInt(i));
		}
		return result;
	}

	public static int getMaximumIndx(double[] data) {
		int mi = 0;
		double m = data[0];
		for (int i = 1; i < data.length; i++) {
			if (data[i] > m) {
				mi = i;
				m = data[i];
			}
		}
		return mi;
	}

	/**
	 * 
	 * @param ad
	 * @param p
	 * @return [sequences][groups]
	 */
	public static Grouping getNewGroupingForProfile(AlignmentData ad, Profiles p) {
		Grouping result = new Grouping(ad.alignment.getAlignmentCount(),
				ad.groupNumerator.getNumOfNames());
		double[][] weights = getWeightsForAllSeqForAllGroup(ad, p);
		for (int i = 0; i < weights.length; i++) {
			result.addSeq(getMaximumIndx(weights[i]));
		}
		return result;
	}

	public static char getAcidForIndex(int i) {
		String asids = new String("ARNDCQEGHILKMFPSTWYV-");
		return (asids.charAt(i));
	}

	public static char[] getAcids() {
		return ("ARNDCQEGHILKMFPSTWYV").toCharArray();
	}

	public static int getIndexForAcid(char g) {
		if (g == '.' || g == 'x' || g == 'X') {
			return 20;
		}
		String asids = new String("ARNDCQEGHILKMFPSTWYV-");
		String asids1 = new String("arndcqeghilkmfpstwyv-");
		int r = asids.indexOf(g);
		if (r != -1) {
			return r;
		} else {
			return asids1.indexOf(g);
		}
	}

	public static double calculateCumulativeEntropy(Alignment a, int[] sdps,
			int[] seqs) {
		double result = 0;
		for (int i = 0; i < sdps.length; i++) {
			double[] groupFreq = new double[20];
			double[] positionFreq = new double[20];
			int[] positionCount = a.getAcidCountsForPosition(sdps[i]);
			for (int j = 1; j <= seqs[0]; j++) {
				int t = a.getAcidInInt(seqs[j], sdps[i]);
				if (t != 20) {
					groupFreq[t] += (double) 1 / seqs[0];
				}
			}
			for (int j = 0; j < 20; j++) {
				positionFreq[j] = (double) positionCount[j]
						/ a.getAlignmentCount();
			}
			result += calculateEntropy(groupFreq, positionFreq);
		}
		result /= sdps.length;
		return result;
	}

	public static double getLogProb(double lnProb, int lenght, int no) {
		if (lnProb == 0) {
			return 0;
		}
		double prob = 0;
		double q = Math.log(1 - Math.exp(lnProb));
		for (int i = no; i <= lenght; i++) {
			prob += Math.exp(i * lnProb
					+ lnNumberOfCombinationByStirling(lenght, i) + (lenght - i)
					* q);
		}
		if (prob == 0) {
			return (no * lnProb + lnNumberOfCombinationByStirling(lenght, no) + (lenght - no)
					* q);
		}
		return Math.log(prob);
	}

	public static int getThreshold(Positions p) {
		int localMin = -1;
		double localMinProb = 10;
		for (int i = 1; i < p.positions.length * MAX_PART_BEFORE_THRESHOLD; i++) {
			if (p.positions[i - 1].positionProb > p.positions[i].positionProb
					&& p.positions[i + 1].positionProb > p.positions[i].positionProb
					&& p.positions[i].positionProb < localMinProb) {
				localMin = i;
				localMinProb = p.positions[i].positionProb;
			}
		}
		if (localMin == -1) {
			return (int) (p.positions.length * MAX_PART_BEFORE_THRESHOLD);
		}
		return localMin;
	}

	/**
	 * 
	 * @param n
	 *            общая толщина колонки
	 * @param k
	 *            объем группы
	 * @param a
	 *            кол-во остатков данного типа во все колонке
	 * @param i
	 *            кол-во остатков данного типа в группе
	 * @return
	 */
	private static double calculateProbabilityForMForEstimateGroup(int n,
			int k, int a, int i) {
		return Math.exp(lnNumberOfCombination(n - a, k - i)
				+ lnNumberOfCombination(a, i) - lnNumberOfCombination(n, k));
	}

	public static double calculateAverageOfDistributionEntropyForAlignment(
			Alignment a, int groupSize) {
		double result = 0;
		int alignWidth = a.getAlignmentCount();
		for (int i = 0; i < a.getAlignmetnLength(); i++) {
			int[] acidCounts = a.getAcidCountsForPosition(i);
			for (int j = 0; j < 20; j++) {
				result += calculateAverageOfDistributionEntropyForAcidForPosition(
						alignWidth, groupSize, acidCounts[j]);
			}
		}
		result /= a.getAlignmetnLength();
		return result;
	}

	/**
	 * 
	 * @param n
	 *            толшина выравнивания
	 * @param k
	 *            объем группы
	 * @param i
	 *            колво данного остатка в первой позиции
	 * @param j
	 *            колво другого данного остатка во второй позиции
	 * @param a1a2
	 *            колво последовательностей в которых в первой позиции и во
	 *            второй позиции нужные кислоты
	 * @param na1a2
	 *            колво последовательностей в которых в первой позиции не та
	 *            кислота а во второй - та
	 * @param inj
	 *            колво последовательностей в которых в первой позиции та
	 *            кислота а во второй - не та
	 * @return
	 */
	public static double calculateProbabilityForCovariationBetweenPositions(
			int n, int k, int a1, int a2, int a1a2, int na1a2, int a1na2) {
		double result = 0;
		for (int i1 = Math.max(0, na1a2 + k - n); i1 <= Math.min(na1a2, a2); i1++) {
			int i2 = a2 - i1;
			if (i2 > a1a2) {
				continue;
			}
			int i3 = a1 - i2;
			if (i3 > a1na2) {
				return result;
			}
			if (i3 >= 0 && (k - i1 - i2 - i3) >= 0
					&& (n - a1a2 - na1a2 - a1na2) >= (k - i1 - i2 - i3)) {
				result += Math.exp(lnNumberOfCombination(na1a2, i1)
						+ lnNumberOfCombination(a1a2, i2)
						+ lnNumberOfCombination(a1na2, i3)
						+ lnNumberOfCombination(n - a1a2 - na1a2 - a1na2, k
								- i1 - i2 - i3) - lnNumberOfCombination(n, k));
			}
		}
		return result;

	}

	/**
	 * вычисляет дисперсию при случайном выборе сдп с возвращением
	 * 
	 * @param a
	 * @param groupSize
	 * @return {M,Sigma}
	 */
	public static double[] calculateSigmaAndAverageOfDistributionEntropyForAlignment(
			Alignment a, int groupSize, int sdpCount) {
		double[] result = { 0, 0 };
		double m[] = new double[a.getAlignmetnLength()];
		double d[] = new double[a.getAlignmetnLength()];
		int alignWidth = a.getAlignmentCount();
		for (int p1 = 0; p1 < a.getAlignmetnLength(); p1++) {
			int[] acidCounts1 = a.getAcidCountsForPosition(p1);
			for (int a1 = 0; a1 < 20; a1++) {
				m[p1] += calculateAverageOfDistributionEntropyForAcidForPosition(
						alignWidth, groupSize, acidCounts1[a1]);
				d[p1] += calculateDispersionEntropyForAcidForPosition(
						alignWidth, groupSize, acidCounts1[a1]);
				for (int b1 = 0; b1 < a1; b1++) {
					d[p1] += 2 * calculateCovariationEntropyForPositionsAndPairAcids(
							alignWidth, groupSize, acidCounts1[a1],
							acidCounts1[b1]);
				}
			}
			result[0] += m[p1];
			result[1] += d[p1] - m[p1] * m[p1];
		}
		result[0] /= a.getAlignmetnLength();
		result[1] /= a.getAlignmetnLength();
		double dm = 0;
		for (int i = 0; i < m.length; i++) {
			dm += (m[i] - result[0]) * (m[i] - result[0]);
		}
		result[1] += dm / m.length;// это дисперсия для одной позиции
		// считаем ковариации
		double cov = 0;
		if (sdpCount > 1)
			for (int p1 = 0; p1 < a.getAlignmetnLength(); p1++) {
				int[] acidCounts1 = a.getAcidCountsForPosition(p1);
				int[] col1 = a.getColumnInInt(p1);
				for (int p2 = p1 + 1; p2 < a.getAlignmetnLength(); p2++) {
					int[][] pairCount = new int[20][20];
					int[] acidCounts2 = a.getAcidCountsForPosition(p2);
					int[] col2 = a.getColumnInInt(p2);
					for (int i = 0; i < col1.length; i++) {
						if (col1[i] != 20 && col2[i] != 20)
							pairCount[col1[i]][col2[i]]++;
					}
					for (int a1 = 0; a1 < 20; a1++) {
						for (int a2 = 0; a2 < 20; a2++) {
							double pp = 0;
							for (int i = 0; i <= Math.min(acidCounts1[a1],
									groupSize); i++) {
								for (int j = 0; j <= Math.min(acidCounts2[a2],
										groupSize); j++) {
									double p = calculateProbabilityForCovariationBetweenPositions(
											alignWidth, groupSize, i, j,
											pairCount[a1][a2], acidCounts2[a2]
													- pairCount[a1][a2],
											acidCounts1[a1] - pairCount[a1][a2]);
									if (p != 0) {
										pp += p;
										cov += 2
												* p
												* mutualEntropy(alignWidth,
														groupSize,
														acidCounts1[a1], i)
												* mutualEntropy(alignWidth,
														groupSize,
														acidCounts2[a2], j);
									}
								}
							}
						}
					}
					cov -= 2 * m[p1] * m[p2];
				}
				cov += d[p1] - m[p1] * m[p1];
			}
		cov /= a.getAlignmetnLength() * a.getAlignmetnLength();
		cov = cov * (sdpCount - 1) / sdpCount;
		result[1] /= sdpCount;
		result[1] += cov;
		result[1] = Math.sqrt(result[1]);
		return result;
	}

	public static long factorial(int i) {
		if (i <= 1)
			return 1;
		return i * factorial(i - 1);
	}

	public static int NumberOfCombination(int n, int k) {
		if (k > n)
			return 0;
		return (int) Math.round((double) factorial(n) / factorial(k)
				/ factorial(n - k));
	}

	/**
	 * Вычисляет дисперсию взаимной энтропии для данной позиций и данной пары
	 * аминокислот - для вычисления Z-score для вычисления качестве групп.
	 * 
	 * @param n
	 *            общая толщина колонки
	 * @param k
	 *            объем группы
	 * @param a
	 *            кол-во остатка а
	 * @param b
	 *            кол-во остатка b
	 * @return
	 */
	private static double calculateCovariationEntropyForPositionsAndPairAcids(
			int n, int k, int a, int b) {
		double result = 0;
		if (a == 0 || b == 0)
			return 0;
		for (int i1 = Math.max(0, a + k - n); i1 <= Math.min(a, k); i1++) {
			for (int j1 = Math.max(0, b + a + k - n - i1); j1 <= Math.min(b, k
					- i1); j1++) {
				double p1 = calculateProbabilityForDForEstimateGroup(n, k, a,
						b, i1, j1);
				result += p1 * mutualEntropy(n, k, a, i1)
						* mutualEntropy(n, k, b, j1);
			}
		}
		return result;
	}

	/**
	 * Вычисляет дисперсию взаимной энтропии для данной пары позиций и данной
	 * пары аминокислот - для вычисления Z-score для вычисления качестве групп.
	 * 
	 * @param n
	 *            общая толщина колонки
	 * @param k
	 *            объем группы
	 * @param a1
	 *            кол-во остатка а в 1 позиции
	 * @param a2
	 *            кол-во остатка a в 2 позиции
	 * @param b1
	 *            кол-во остатка b в 1 позиции
	 * @param b2
	 *            кол-во остатка b в 2 позиции
	 * @return
	 */
	private static double calculateDispersionEntropyForPairPositionsAndPairAcids(
			int n, int k, int a1, int a2, int b1, int b2) {
		double result = 0;
		if (a1 == 0 || a2 == 0 || b1 == 0 || b2 == 0)
			return 0;
		for (int i1 = Math.max(1, a1 + k - n); i1 <= Math.min(a1, k); i1++) {
			for (int j1 = Math.max(1, b1 + a1 + k - n - i1); j1 <= Math.min(b1,
					k - i1); j1++) {
				double p1 = calculateProbabilityForDForEstimateGroup(n, k, a1,
						b1, i1, j1);
				for (int i2 = Math.max(1, a2 + k - n); i2 <= Math.min(a2, k); i2++) {
					for (int j2 = Math.max(1, b2 + a2 + k - n - i2); j2 <= Math
							.min(b2, k - i2); j2++) {
						double p2 = calculateProbabilityForDForEstimateGroup(n,
								k, a2, b2, i2, j2);
						result += p1 * p2 * mutualEntropy(n, k, a1, i1)
								* mutualEntropy(n, k, b1, j1)
								* mutualEntropy(n, k, a2, i2)
								* mutualEntropy(n, k, b2, j2);
					}
				}
			}
		}
		return result;
	}

	private static double mutualEntropy(int n, int k, int a, int i) {
		if (a == 0 || i == 0)
			return 0;
		return ((double) i / k * (LOGs[i] + LOGs[n] - LOGs[a] - LOGs[k]));
	}

	/**
	 * 
	 * @param n
	 *            общая толщина колонки
	 * @param k
	 *            объем группы
	 * @param a
	 *            кол-во остатков данного типа во все колонке (соответсвует i)
	 * @param b
	 *            кол-во остатков данного типа во все колонке (соответсвует j)
	 * @param i
	 *            кол-во остатков данного типа в группе (a)
	 * @param j
	 *            кол-во остатков данного типа в группе (b)
	 * @return
	 */
	private static double calculateProbabilityForDForEstimateGroup(int n,
			int k, int a, int b, int i, int j) {
		if (a + b > n || i + j > k)
			return 0;
		return Math.exp(lnNumberOfCombination(n - a - b, k - i - j)
				+ lnNumberOfCombination(a, i) + lnNumberOfCombination(b, j)
				- lnNumberOfCombination(n, k));
	}
	
	/**
	 * 
	 * @param true_negatives
	 * @param to_test
	 * @param positives
	 * @return |intersection(to_test,positives)|/positives.size(), 
	 * (to_test.size()-|intersection(to_test,positives)|)/true_negatives
	 */
	public static double[] getSensAndFalsePositivesRate(int true_negatives,
			HashSet<Integer> to_test,HashSet<Integer> positives) {
		double inter = 0;
		for(Integer t : to_test)
			if(positives.contains(t))
				inter++;
		return new double[] {inter/positives.size(),(to_test.size() - inter)/true_negatives};
	}
	
	public static Collection<Double> asCollection(double[] d){
		ArrayList<Double> r = new ArrayList<Double>();
		for(double dd : d)
			r.add(dd);
		return r;
	}

	/**
	 * Вычисляет дисперсию взаимной энтропии для данной позиции и данной кислоты -
	 * для вычисления Z-score для вычисления качестве групп.
	 * 
	 * @param n
	 *            общая толщина колонки
	 * @param k
	 *            объем группы
	 * @param a
	 *            колво остатков данного типа во все колонке
	 * @return
	 */
	private static double calculateDispersionEntropyForAcidForPosition(int n,
			int k, int a) {
		double result = 0;
		if (a == 0)
			return 0;
		for (int i = Math.max(0, a + k - n); i <= Math.min(a, k); i++) {
			double p1 = calculateProbabilityForMForEstimateGroup(n, k, a, i);
			double m = mutualEntropy(n, k, a, i);
			result += p1 * m * m;
		}
		return result;
	}

	/**
	 * Вычисляет матожидание взаимной энтропии для данной позиции и данной
	 * кислоты - для вычисления Z-score для вычисления качестве групп.
	 * 
	 * @param n
	 *            общая толщина колонки
	 * @param k
	 *            объем группы
	 * @param a
	 *            колво остатков данного типа во все колонке
	 * @return
	 */
	private static double calculateAverageOfDistributionEntropyForAcidForPosition(
			int n, int k, int a) {
		double result = 0;
		if (a == 0)
			return 0;
		for (int i = Math.max(0, a + k - n); i <= Math.min(a, k); i++) {
			double p1 = calculateProbabilityForMForEstimateGroup(n, k, a, i);
			result += p1 * mutualEntropy(n, k, a, i);
		}
		return result;
	}

	public static double lnFactorial(int begin, int end) {
		if (begin <= 0 || end < begin) {
			return 0;
		}
		return LOGs[begin] + lnFactorial(begin + 1, end);
	}

	public static double lnNumberOfCombination(int n, int k) {
		return LOGs_OF_NUMBER_OF_COMBINATION[n][k];
	}

	public static double calclnNumberOfCombination(int n, int k) {
		return LOGs_OF_FACTORIAL[n] - LOGs_OF_FACTORIAL[k]
				- LOGs_OF_FACTORIAL[n - k];
	}

	public static double lnNumberOfCombinationByStirling(int n, int k) {
		if (n == k | k == 0 | n == 0) {
			return 0;
		}
		return (double) ((n + 0.5) * LOGs[n] - (k + 0.5) * LOGs[k]
				- (n - k + 0.5) * LOGs[n - k] - 0.5 * LOG2PI);
	}

	public static String getAminoAcidInSDPinGropup(int pos, int[] seqs,
			Alignment a) {
		StringBuffer result = new StringBuffer(seqs.length);
		for (int i = 0; i < seqs.length; i++) {
			result.append(a.getSequenceInString(seqs[i])
					.substring(pos, pos + 1));
		}
		return result.toString();
	}

	public static String generateColoredSequence(Positions pos, String seq) {
		if (pos == null)
			return seq;
		int[] sdp = new int[pos.bernThreshold + 1];
		for (int i = 0; i <= pos.bernThreshold; i++) {
			sdp[i] = pos.positions[i].aligNo;
		}
		Arrays.sort(sdp);
		StringBuffer r = new StringBuffer();
		r.append(seq.subSequence(0, sdp[0]));
		for (int i = 0; i < pos.bernThreshold; i++) {
			r.append("<font style=\"background-color: #ff0000;\">");
			r.append(seq.subSequence(sdp[i], sdp[i] + 1));
			r.append("</font>");
			r.append(seq.subSequence(sdp[i] + 1, sdp[i + 1]));
		}
		r.append(seq.subSequence(sdp[sdp.length - 1] + 1, seq.length()));
		return r.toString();
	}

	public static void printGroupingForMatrixFile(String fname, int matrix_type) {
		GroupingMatrixes groupingMatrix = new GroupingMatrixes(fname,
				matrix_type);
		Node root = TreeMaker.makeUPGMATreeFromDistantMatrix(groupingMatrix
				.getDistanceMatrix(), groupingMatrix.getNumerator());
		Vector<Node> clusters = new Vector<Node>();
		root.getBestCut(clusters);
		System.out.println(root);
		for (int i = 0; i < clusters.size(); i++) {
			System.out.println("cluster" + i);
			System.out.println(clusters.get(i).getNamesOfAllLeafs());
		}
	}

	public static void setGroupingFromGroupingMatrix(AlignmentData data,
			String fname_matrix, int matrix_type) throws Exception {
		setGroupingFromGroupingMatrix(data, new GroupingMatrixes(fname_matrix,
				matrix_type));
	}

	public static void setGroupingFromGroupingMatrix(AlignmentData data,
			GroupingMatrixes groupingMatrix) throws Exception {
		String e = data.seqNumerator.equalsWithNumerator(groupingMatrix
				.getNumerator());
		if (!e.equals(""))
			throw new Exception(
					"Bad order of sequence names (or names) in matrix. " + e);
		Node root = TreeMaker.makeUPGMATreeFromDistantMatrix(groupingMatrix
				.getDistanceMatrix(), groupingMatrix.getNumerator());
		Vector<Node> clusters = new Vector<Node>();
		root.getBestCut(clusters);
		data.groupNumerator = new Numerator();
		data.groupNumerator.addName(StaticFunction.NONGROUPED);
		data.grouping = new Grouping(data.alignment.getAlignmentCount(),
				clusters.size());
		int[] groupNoarray = new int[data.alignment.getAlignmentCount()];
		int grno = 1;
		for (int i = 0; i < clusters.size(); i++) {
			//System.out.println(i+"\t"+clusters.get(i).getWeight());
			Vector<Node> seqs = clusters.get(i).getAllLeafs();
			if (seqs.size() > 1) {
				if(grno < 200)
					data.groupNumerator.addName("cluster" + grno);
				for (int j = 0; j < seqs.size(); j++) {
					groupNoarray[seqs.get(j).getNumber()] = grno;
				}
				grno++;
			}
		}
		if (grno > 200) {
			System.out.println("too much group predicted, group count = "+grno+", all groups after 200 moved to ungrouped");
			for (int i = 0; i < groupNoarray.length; i++) {
				if(groupNoarray[i] >= 200)
					groupNoarray[i] = 0;
			}
		}
		data.grouping = new Grouping(groupNoarray);
	}

	public static Graph getProbabilityGraphForPositions(Positions p) {
		double data[][] = new double[2][p.positions.length + 1];
		data[0][0] = 0;
		data[1][0] = 0;
		for (int i = 1; i < data[0].length; i++) {
			data[0][i] = i;
			data[1][i] = p.positions[i - 1].positionProb;
		}
		return new Graph(data);
	}

	/**
	 * ���� ��������������������� (�.�. ������ ������ �����) �� ������� �������
	 * 
	 * @param data
	 * @return ������ ������� �������� �� 2-� ��������� - ������ � �����
	 *         ��������� (����� - �� ������ � ��������)
	 */
	public static Vector<int[]> calculateSequentialIntervals(int[] data) {
		Vector<int[]> result = new Vector<int[]>();
		for (int tmp = 0; tmp < data.length;) {
			int end = searchSequentialIntervals(data, tmp);
			result.add(new int[] { data[tmp], data[end] + 1 });
			tmp = end + 1;
		}
		return result;
	}

	private static int searchSequentialIntervals(int[] data, int startIndex) {
		int result = startIndex;
		if (startIndex >= data.length)
			return startIndex;
		for (; result < (data.length - 1)
				&& data[result + 1] == data[result] + 1; result++) {
		}
		return result;
	}
	
	
	public static int[] toIntArray(String v, String del) {
		if(v.length() == 0)
			return new int[0];
		String[] tmp = v.split(del);
		int[] r = new int[tmp.length];
		for(int i=0;i<tmp.length;i++) {
			r[i] = Integer.parseInt(tmp[i]);
		}
		return r;
	}


	public static int[] parseStringToIntArray(String s) {
		Vector<Integer> t = new Vector<Integer>();
		StringTokenizer st = new StringTokenizer(s);
		int[] result;
		for (; st.hasMoreTokens();) {
			t.add(new Integer(st.nextToken()));
		}
		result = new int[t.size()];
		for (int i = 0; i < t.size(); i++) {
			result[i] = t.get(i).intValue();
		}
		return result;
	}

	/**
	 * 
	 * @param pos
	 *            ����� �������
	 * @param grouping ��
	 *            ������ ������� ���-�� ������������������� � ������, �����
	 *            ������ �������������������
	 * @param a
	 *            ������������
	 * @param noAsidForReturn
	 *            ���������� ������� ������ ����������� ������� ���� �������
	 * @return ������ � noAsidForReturn ����������� ������� ����� ������������� �
	 *         ���� ������ � ���� �������
	 */
	public static String getFrequentAcidsInPositionInGroup(int pos,
			int[] grouping, Alignment a, int noAsidForReturn) {
		int[][] acidFreq = new int[20][2];
		for (int i = 0; i < 20; i++) {
			acidFreq[i][0] = getAcidForIndex(i);
		}
		for (int i = 1; i <= grouping[0]; i++) {
			int ac = a.getAcidInInt(grouping[i], pos);
			if (ac != 20)
				acidFreq[ac][1]++;
		}
		Arrays.sort(acidFreq, new Comparator<int[]>() {
			public int compare(int[] a, int[] b) {
				return b[1] - a[1];
			}
		});
		String result = "";
		for (int i = 0; i < noAsidForReturn; i++) {
			if (acidFreq[i][1] == 0)
				break;
			result += (char) acidFreq[i][0];
		}
		return result;
	}

	public static String generateAligmentNumberString(int length, int part,
			String spacer) {
		StringBuffer result = new StringBuffer();
		StringBuffer del = new StringBuffer(6 * (part - 3));
		for (int i = 0; i < part - 4; i++) {
			del.append(spacer);
		}
		DecimalFormat f = new DecimalFormat("0000");
		for (int j = 0; j * part < length; j++) {
			String tmp = f.format(j * part);
			StringBuffer r = new StringBuffer(4);
			for (int i = 0;; i++) {
				if (tmp.charAt(i) != '0' || i == tmp.length() - 1) {
					for (int a = i; a < tmp.length(); a++) {
						r.append(tmp.charAt(a));
					}
					for (int a = 0; a < i; a++) {
						r.append(spacer);
					}
					break;
				}
			}
			result.append(r).append(del);
		}
		return result.toString();
	}

	public static String generateAligmentNumberString(int length, int part) {
		return generateAligmentNumberString(length, part, "&nbsp;");
	}

	public static AlignmentData readAlignment(InputStream is)
			throws Exception {
		AlignmentData ad = new AlignmentData();
		readAlignment(ad.alignment, ad.grouping, ad.seqNumerator,
				ad.groupNumerator, is);
		return ad;
	}

	public static void readAlignment(Alignment alig, Grouping grouping,
			Numerator seqNumerator, Numerator groupNumerator, InputStream is)
			throws Exception{
		String line;
		StringBuffer seq = new StringBuffer(2000);
		String seqName;
		String groupName = new String("nongrouped");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		line = br.readLine();
		if (line.charAt(0) == '(') {
			line = br.readLine();
		}
		for (; line != null;) {
			if(line.indexOf("ALIGNMENT END") != -1)
				break;
			if (line.substring(0, 1).equals("=")) {
				try {
					groupNumerator.addName(groupName);
				} catch (NumeratorException e) {
					e.setType("group");
					throw e;
				}
				groupName = new String((new StringTokenizer(line, "= \t"))
						.nextToken());
				line = br.readLine();
			} else if (line.substring(0, 1).equals("%")
					|| line.substring(0, 1).equals(">")) {
				seqName = (new StringTokenizer(line, ">%\n")).nextToken();
				for (line = br.readLine(); line != null && !line.equals("")
						&& !line.substring(0, 1).equals("=")
						&& !line.substring(0, 1).equals(">")
						&& !line.substring(0, 1).equals("%"); line = br
						.readLine()) {
					if(line.indexOf("ALIGNMENT END") != -1)
						break;
					StringTokenizer st = new StringTokenizer(line);
					for (; st.hasMoreTokens();)
						seq.append(st.nextToken());
				}
				try {
					seqNumerator.addName(seqName);
				} catch (NumeratorException e) {
					e.setType("sequence");
					throw e;
				}
				if(groupNumerator.getNumOfNames() > 200)
					throw new Exception("Count of groups more than 200!");
				grouping.addSeq(groupNumerator.getNumOfNames());
				alig.addSeq(seq.toString(), seqName);
				if(seq.toString().length() > 5000)
					throw new AlignmentException("Length of sequences more than 5000!");
				seq = new StringBuffer(2000);
			} else
				throw new AlignmentException(
						"Incorrect alignment format!\nLast delivered line: "
								+ line, true);
			if(seqNumerator.getNumOfNames() >= 2000)
				throw new AlignmentException("Count of sequences more than 2000!");
		}
		groupNumerator.addName(groupName);
		alig.calculateIntMatrix(seqNumerator);
		grouping.setRealGroupCount(groupNumerator.getNumOfNames());
		br.close();
		if (seqNumerator.getNumOfNames() < 4)
			throw new AlignmentException(
					"Incorrect alignment size!\nAlignment must contain more then 4 sequence!",
					true);
	}

	public static void readAlignment(Alignment alig, Grouping grouping,
			Numerator seqNumerator, Numerator groupNumerator, String fname)
			throws Exception{
		readAlignment(alig, grouping, seqNumerator, groupNumerator,
				new FileInputStream(fname));
	}

	/**
	 * 
	 * @param grNumerator
	 *            ������ ��������� �����, � ������� ��������� ����� �������
	 *            �����
	 * @param clusters
	 *            ������ ���������
	 * @return ���������� � ������������ � ����������. ���� ������
	 *         ������������������ ���� � �������� ��� �������� � ungrouped.
	 */

	public static Grouping calculateGroupingFromTreeClasters(
			Numerator grNumerator, Vector<Node> clusters) {
		int[][] gr = new int[clusters.size()][];
		int seqCount = 0;
		int grNo = 1;
		try {
			grNumerator.addName("ungrouped");
			for (int i = 0; i < gr.length; i++) {
				gr[i] = clusters.get(i).getSortedLeafNumberArray();
				if (gr[i].length >= MINIMAL_GROUP_SIZE)
					grNumerator.addName("group"
							+ (grNumerator.getNumOfNames() - 1));
				seqCount += gr[i].length;
			}
		} catch (NumeratorException e) {
			System.out.println(e);
		}
		Grouping grouping = new Grouping(seqCount, grNumerator.getNumOfNames());
		int[] tempGrArray = new int[seqCount];
		for (int i = 0; i < gr.length; i++) {
			if (gr[i].length < MINIMAL_GROUP_SIZE) {
				for (int j = 0; j < gr[i].length; j++) {
					tempGrArray[gr[i][j]] = 0;
				}
			} else {
				for (int j = 0; j < gr[i].length; j++) {
					tempGrArray[gr[i][j]] = grNo;
				}
				grNo++;
			}
		}
		for (int i = 0; i < tempGrArray.length; i++) {
			grouping.addSeq(tempGrArray[i]);
		}
		return grouping;
	}

	public static void saveAlignment(AlignmentData data, String fname,
			int alignment_format_type) {
		switch (alignment_format_type) {
		case 0:
			saveAlignmentInGDE(transformInGroup(data), fname);
			break;
		case 1:
			saveAlignmentInPhylip(data, fname);
			break;
		}
	}

	public static Numerator getDefaultNumerator(int grno) {
		Numerator result = new Numerator();
		try {
			result.addName("nongrouped");
			for (int i = 0; i < grno - 1; i++) {
				result.addName("group" + i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private static String getSequenceNameForPhylip(String name) {
		int nameLength = 10;
		if (name.length() == nameLength)
			return name;
		if (name.length() < nameLength) {
			String space = "          ";
			return name + space.substring(0, nameLength - name.length());
		} else
			return name.substring(0, nameLength);
	}

	private static String replaceDotToDash(String s) {
		StringBuffer result = new StringBuffer(s.length());
		int old = 0;
		for (int i = s.indexOf('.'); i != -1;) {
			result.append(s.substring(old, i)).append('-');
			old = i + 1;
			i = s.indexOf('.', old);
		}
		result.append(s.substring(old, s.length()));
		return result.toString();
	}

	public static void saveAlignmentInPhylip(AlignmentData data, String fname) {
		try {
			PrintWriter pw = new PrintWriter(fname);
			pw.println(data.alignment.getAlignmentCount() + " "
					+ data.alignment.getAlignmetnLength());
			for (int i = 0; i < data.seqNumerator.getNumOfNames(); i++) {
				pw.print(getSequenceNameForPhylip(data.seqNumerator
						.getNameForNum(i)));
				pw.println(replaceDotToDash(data.alignment
						.getSequenceInString(i)));
			}
			pw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveAlignmentInGDE(ArrayList<Group> groups, String fname) {
		BufferedWriter bw;
		try {
			bw = new BufferedWriter(new FileWriter(fname));
			for (int i = 0; i < groups.size(); i++) {				
				String grName = (groups.get(i)).name;
				if (!grName.equals(StaticFunction.NONGROUPED)) {
					bw.write("===" + grName + "===\n");
				}
				Group group = groups.get(i);
				for (int j = 0; j < group.sequences.size(); j++) {
					Sequence seq = (Sequence) group.sequences.get(j);
					bw.write(">" + seq.name + "\n" + seq.seq + "\n");
				}
			}
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sortObj(ArrayList l) {
		Collections.sort(l, new Comparator() {
			public int compare(Object o1, Object o2) {
				return o1.toString().compareTo(o2.toString());
			}
		});
	}

	public static ArrayList<Group> transformInGroup(AlignmentData ad) {
		return transformInGroup(ad.grouping, ad.groupNumerator,
				ad.seqNumerator, ad.alignment);
	}

	public static ArrayList<Group> transformInGroup(Grouping gr,
			Numerator groupNumerator, Numerator seqNumerator, Alignment al) {
		ArrayList<Group> ret = new ArrayList<Group>(groupNumerator
				.getNumOfNames());
		for (int i = 0; i < groupNumerator.getNumOfNames(); i++) {
			ret.add(new Group(groupNumerator.getNameForNum(i)));
		}
		for (int i = 0; i < seqNumerator.getNumOfNames(); i++) {
			int grNo = gr.getGroupForSeq(i);
			Group t = ret.get(grNo);
			t.addSeq(seqNumerator.getNameForNum(i), al.getSequenceInString(i));
		}
		for (int i = 0; i < ret.size(); i++) {
			if (!(ret.get(i) == null)) {
				(ret.get(i)).sort();
			} else {
				ret.remove(i);
				i--;
			}
		}
		return ret;
	}
}
