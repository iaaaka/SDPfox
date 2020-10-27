package for_article_12_13_2010;

import java.io.*;

import Objects.*;

import java.security.Permission;
import java.util.*;
import Math.StaticFunction;
import from_olga.pdbparser.*;
import java.lang.reflect.Method;
import java.net.*;

class SDPDistStatForDiffMethods {
	public final String FAMILY;
	public static final int TS_METHOD = 0;
	public static final int FASS_METHOD = 1;
	public static final int MB_METHOD = 2;
	public static final int S_METHOD = 3;
	public static final int r4s_METHOD = 4;
	public static final int SDPproff_METHOD = 5;
	public static final int SDPsite_METHOD = 6;
	public static final String[] METHODS = {"Trace Suite II","FASS-method", "MB-method", "S-method", "rate4site", "SDPproff","SDPsite" };
	public static final String[] EXTENSIONS ={"distet","distfass","distmb","dists","distr4s","","sdps"};
	private double[] medians = new double[METHODS.length];
	private double[] median_pvalues = new double[METHODS.length];
	private double[] mindist = new double[METHODS.length];
	private double[] mindist_pvalues = new double[METHODS.length];
	private double[] sensitivity = new double[METHODS.length];
	private double[] fpr = new double[METHODS.length];
	private double[] averagedist = new double[METHODS.length];
	private int[] sdpCount = new int[METHODS.length];
	private final static String[] fields = {"Min ditance", "pvalue(mindist)","median","pvalue(median)","SDP count","Sensitivity","False positive rate","Average dist"};
	private boolean[] notAvailable = new boolean[METHODS.length];
	private int positinCount;

	public SDPDistStatForDiffMethods(String f,int positinCount){
		FAMILY = f;
		Arrays.fill(notAvailable, true);
		this.positinCount = positinCount;
	}
	
	private String getResultClass(int method){
		double t1 = 0.05;
		double t2 = 0.01;
		double minpv = mindist_pvalues[method];
		double medpv = median_pvalues[method];
		//class 0 - NA
		if(notAvailable[method])
			return "NA";
		//class 1
		if(minpv <= t1 && medpv <= t1)
			return ""+1;
		//class 2
		if(minpv <= t1 || medpv <= t1)
			return ""+2;
		//class 3
		if(minpv <= t2 && medpv <= t2)
			return ""+3;
		//class 4
		if(minpv <= t2 || medpv <= t2)
			return ""+4;
		return ""+5;
	}
	
	public static void printFieldsForClass(PrintStream ps){
		ps.print("Family \t");
		for(int i=0;i<METHODS.length;i++){
			ps.print(METHODS[i]+"\t");
		}
		ps.println();
	}
	
	public void printClass(PrintStream ps){
		ps.print(FAMILY+"\t");
		for(int i=0;i<METHODS.length;i++){
			ps.print(getResultClass(i)+"\t");
		}
		ps.println();
	}

	
	public void setValues(double med, double medpv,double mind,double mindpv, int method,int sdpCount, double sen, double fpr, double avrgd){
		medians[method] = med;
		median_pvalues[method] = medpv;
		mindist[method] = mind;
		mindist_pvalues[method] = mindpv;
		notAvailable[method] = false;
		this.sdpCount[method] = sdpCount;
		this.averagedist[method] = avrgd;
		this.sensitivity[method] = sen;
		this.fpr[method] = fpr;
	}
	
	private String getString(double p, int method){
		if(notAvailable[method]){
			return "NA";
		}
		return "" + p;
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer();
		result.append(FAMILY).append("\t").append(positinCount).append("\t");
		for(int i=0;i<METHODS.length;i++){
			result.append(getString(mindist[i],i)).append("\t").append(getString(mindist_pvalues[i],i)).append(" \t").
			append(getString(medians[i],i)).append("\t").append(getString(median_pvalues[i],i)).append("\t").append(sdpCount[i]).
			append("\t").append(getString(sensitivity[i],i)).append("\t").append(getString(fpr[i],i)).append("\t").append(getString(averagedist[i],i)).append("\t");
		}
		return result.toString();
	}
	
	public static void printFields(PrintStream ps){
		ps.print("Family\tPosition count\t");
		for(int i=0;i<METHODS.length;i++){
			ps.print(METHODS[i]+"\t\t\t\t\t\t\t\t");
		}
		ps.print("\n\t\t");
		for(int i=0;i<METHODS.length;i++){
			for(int j=0;j<fields.length;j++){
				ps.print(fields[j]+"\t");
			}
		}
		ps.println();
	}
}

class Results {
	// values
	public double minDist;

	public double averageDist;

	public double diameter;

	public double averagePairwiseDist;

	public double partWithinMinDist;

	public double partWithinAverageDist;

	public double partWithinMaxDist;

	// p-values
	private double p_minDist = 0;

	private double p_averageDist = 0;

	private double p_diameter = 0;

	private double p_averagePairwiseDist = 0;

	private double p_partWithinMinDist = 0;

	private double p_partWithinAverageDist = 0;

	private double p_partWithinMaxDist = 0;

	private int numberOfIteration;

	public String toString() {
		return minDist + "\t" + getPvalueMinDist() + "\t" + averageDist + "\t"
				+ getPvalueAverageDist() + "\t" + diameter + "\t" + getPvalueDiameter() + "\t"
				+ averagePairwiseDist + "\t" + getPvalueAveragePairwiseDist() + "\t"
				+ partWithinMinDist + "\t" + getPvaluePartWithinMinDist() + "\t"
				+ partWithinAverageDist + "\t" + getPvaluePartWithinAverageDist() + "\t"
				+ partWithinMaxDist+"\t"+getPvaluePartWithinMaxDist();
	}

	public static String getFields() {
		return "min dist\tp-value\taverage dist\tp-value\tdiameter\tp-value\taverage pairwise dist\tp-value\tpart within min dist"
				+ "\tp-value\tpart within average dist\tp-value\tpart within max dist\tp-value";
	}

	public Results(String fname) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(fname
				+ ".sdpdistout"));
		br.readLine();
		minDist = Double.parseDouble(br.readLine());
		averageDist = Double.parseDouble(br.readLine());
		diameter = Double.parseDouble(br.readLine());
		averagePairwiseDist = Double.parseDouble(br.readLine());
		br.close();
		br = new BufferedReader(new FileReader(fname + ".sdpdistoutsign"));
		br.readLine();
		partWithinMinDist = Double.parseDouble(br.readLine());
		partWithinMaxDist = Double.parseDouble(br.readLine());
		partWithinAverageDist = Double.parseDouble(br.readLine());
		br.close();
	}

	public void addMonteCarloIteration(Results r) {
		numberOfIteration++;
		if (minDist >= r.minDist)
			p_minDist++;
		if (averageDist >= r.averageDist)
			p_averageDist++;
		if (diameter >= r.diameter)
			p_diameter++;
		if (averagePairwiseDist >= r.averagePairwiseDist)
			p_averagePairwiseDist++;
		if (partWithinMinDist >= r.partWithinMinDist)
			p_partWithinMinDist++;
		if (partWithinAverageDist >= r.partWithinAverageDist)
			p_partWithinAverageDist++;
		if (partWithinMaxDist >= r.partWithinMaxDist)
			p_partWithinMaxDist++;
	}

	public double getPvalueMinDist() {
		return this.p_minDist / this.numberOfIteration;
	}

	public double getPvalueAverageDist() {
		return this.p_averageDist / this.numberOfIteration;
	}

	public double getPvalueDiameter() {
		return this.p_diameter / this.numberOfIteration;
	}

	public double getPvalueAveragePairwiseDist() {
		return this.p_averagePairwiseDist / this.numberOfIteration;
	}

	public double getPvaluePartWithinMinDist() {
		return this.p_partWithinMinDist / this.numberOfIteration;
	}

	public double getPvaluePartWithinAverageDist() {
		return this.p_partWithinAverageDist / this.numberOfIteration;
	}

	public double getPvaluePartWithinMaxDist() {
		return this.p_partWithinMaxDist / this.numberOfIteration;
	}
}

public class DistanceForLigandStatistics_2009 {
	final static PrintStream out = System.out;
	final static PrintStream empty = new PrintStream(new OutputStream(){
		@Override
		public void write(int b) throws IOException {}
	});
	public static  String WORKDIR = "SDPdistance/singleEC/";
	public static final double THRDIST = 10;
	public static String INPUT = WORKDIR + "input/";
	public static String SDPPROF_RES_BASE = ".alnSDPproff_10000iter.al";
	public static String OUTPUT = WORKDIR + "output/";
	public static String TMP = WORKDIR + "tmp/";
	public static String ALIGNMENTS = INPUT + "alignment/";
	public static String PDB = INPUT + "pdb/";
	public static final String PDB_SUF = "_mod.pdb";
	public static final String OTHER_METHODS = "SDPdistance/otherMethods/";


	public static void setEC(char e){
		if(e == 's'){
			WORKDIR = "SDPdistance/singleEC/";
			SDPPROF_RES_BASE = ".alnSDPproff_10000iter.al";
		}else if(e == 'm'){
			WORKDIR = "SDPdistance/multyEC/";
			SDPPROF_RES_BASE = ".aln_SDPproff_10000iter.al";
		}
		INPUT = WORKDIR + "input/";
		OUTPUT = WORKDIR + "output/";
		TMP = WORKDIR + "tmp/";
		ALIGNMENTS = INPUT + "alignment/";
		PDB = INPUT + "pdb/";
	}
	
	public static void main(String[] args) throws Exception {
		setEC('m');
		Object[] ob1 = getDistsForComulPvalue();
		//setEC('m');
		//Object[] ob2 = getDistsForComulPvalue();
		double[][][] sdpdist1 = (double[][][])ob1[0];
		//double[][][] sdpdist2 = (double[][][])ob2[0];
		double[][] alldist1 = (double[][])ob1[1];
		//double[][] alldist2 = (double[][])ob2[1];
		double[][][] sdpdist = new double[sdpdist1.length/*+sdpdist2.length*/][][];
		double[][] alldist = new double[alldist1.length/*+alldist2.length*/][];
		for(int i=0;i<sdpdist1.length;i++){
			alldist[i] = alldist1[i];
			sdpdist[i] = sdpdist1[i];
		}
		/*for(int i=0;i<sdpdist2.length;i++){
			alldist[i+sdpdist1.length] = alldist2[i];
			sdpdist[i+sdpdist1.length] = sdpdist2[i];
		}*/
		
		System.out.println("\tmindist\tpvalue(mindist)\tmedian\tpvalue(median)\t");
		for(int m=0;m<SDPDistStatForDiffMethods.METHODS.length;m++){
			Vector<Double> all = new Vector<Double>();
			Vector<Double> sdp = new Vector<Double>();
			for(int i=0;i<sdpdist.length;i++){
				if(sdpdist[i][m] != null){
					addArray(sdp,sdpdist[i][m]);
					addArray(all,alldist[i]);
				}
			}
			double[] alla = toArray(all);
			double[] sdpa = toArray(sdp);
			double[] mm = getMinAndMedian(sdpa);			
			System.out.println(SDPDistStatForDiffMethods.METHODS[m] + "\t" +mm[0]+"\t"+getPvalueForMinValue(alla, sdpa, mm[0])+"\t"+mm[1]+"\t"+getWilcoxonByR(alla, sdpa)+"\t");
		}
	}
	
	private static Object[] getDistsForComulPvalue() throws Exception {
		Vector<InputParameters> pars = readInputParameters(INPUT + "input");
		double pvaluethr = 0.05;
		double[][][] sdpdist = new double[pars.size()][SDPDistStatForDiffMethods.METHODS.length][];
		double[][] alldist = new double[pars.size()][];
		for(int i=0;i<pars.size();i++){
			System.out.println(pars.get(i).pfam_name);
			alldist[i] = getDistancesForAllPositions(pars.get(i));
			for(int m=0;m<SDPDistStatForDiffMethods.METHODS.length;m++){
				sdpdist[i][m] = null;
				try{
					sdpdist[i][m] = getDistancesForSDP(pars.get(i),m);
				}catch(IOException e){
					
				}
			}	
		}
		return new Object[]{sdpdist,alldist};
	}
	
	private static double[] toArray(Vector<Double> data){
		double[] r = new double[data.size()];
		for(int i=0;i<data.size();i++)
			r[i] = data.get(i);
		return r;
	}
	
	private static void addArray(Vector<Double> to, double[] from){
		for(int i=0;i<from.length;i++){
			to.add(from[i]);
		}
	}
	
	//methods for comparing with others - START
	private static SDPDistStatForDiffMethods getResults(InputParameters p) throws Exception{
		double[] allDist = getDistancesForAllPositions(p);
		SDPDistStatForDiffMethods result = new SDPDistStatForDiffMethods(p.pfam_name,allDist.length);
		for(int i=0;i<result.METHODS.length;i++){
			//System.out.println(p.name+" "+SDPDistStatForDiffMethods.METHODS[i]);
			double[] sdpdist = null;
			try{
				sdpdist = getDistancesForSDP(p,i);
			}catch(IOException e){
				//System.out.println("Ex in jar: "+p.name+" "+SDPDistStatForDiffMethods.METHODS[i]);
				//e.printStackTrace();
			}
			if(sdpdist == null){
				//System.out.println("SDP == null: "+p.name+" "+SDPDistStatForDiffMethods.METHODS[i]);
				continue;
			}
			int tp=0,fp=0,pos=0,neg=0;
			for(int j=0;j<sdpdist.length;j++){
				if(sdpdist[j]<=THRDIST){
					tp++;
				}else{
					fp++;
				}
			}
			for(int j=0;j<allDist.length;j++){
				if(allDist[j]<=THRDIST){
					pos++;
				}else{
					neg++;
				}
			}
			double sen = -1;
			if(pos != 0)
				sen = (double)tp/pos;
			double fpr = -1;
			if(neg != 0)
				fpr = (double)fp/neg;
			double[] mm = getMinAndMedian(sdpdist);
			double w = getWilcoxonByR(allDist,sdpdist);
			double mpv = getPvalueForMinValue(allDist,sdpdist,mm[0]);
			result.setValues(mm[1], w, mm[0], mpv, i,sdpdist.length,sen,fpr,StaticFunction.average(sdpdist));
		}
		return result;
	}
	
	/**
	 * @param d
	 * @return minimum value and value of median
	 */
	private static double[] getMinAndMedian(double[] d){
		double[] myd = (double[])d.clone();
		Arrays.sort(myd);
		return new double[]{myd[0], myd[myd.length/2]};
	}
	
	protected static double getPvalueForMinValue(double[] allDist,double[] sdpDist,double minSDPdist){
		double result = 1;
		int betterSDP = 0;
		for(int i=0;i<allDist.length;i++){
			if(allDist[i] <= minSDPdist)
				betterSDP++;
		}
		for(int i=allDist.length-betterSDP+1;i<=allDist.length;i++){
			result *= (double)(i - sdpDist.length)/i;
		}
		
		return (1 - result);
	}
	
	protected static double getWilcoxonByR(double[] allDist, double[] sdpDist)throws Exception{
		return getWilcoxonByR(sdpDist,allDist, 'l');
	}
	
	/**
	 * 
	 * @param first
	 * @param second
	 * @param alternative l,g,t
	 * @return if first < second and alternative l - result must be small
	 * @throws Exception
	 */
	private static double getWilcoxonByR(double[] first,double[] second, char alternative)throws Exception{
		if(alternative != 'l' && alternative != 't' && alternative != 'g')
			alternative = 't';
		Process p = Runtime.getRuntime().exec("R --no-save --quiet");
		Thread t = copyStreamNT(p.getErrorStream(), new OutputStream(){
			public void write(int b) throws IOException {
				//throw new IOException("R does not work!");
			}
		});
		final StringBuffer result = new StringBuffer();
		Thread t_ = copyStreamNT(p.getInputStream(), new OutputStream(){
			public void write(int b) throws IOException {
				result.append((char)b);
			}
		});
		BufferedWriter in = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
		double[][] d = {first,second};
		in.write("sdp<-c(");
		for(int i=0;i<d[0].length;i++){
			in.write(""+d[0][i]);
			if(i!=d[0].length-1)
				in.write(',');
			else
				in.write(")\n");
		}
		in.write("whole<-c(");
		for(int i=0;i<d[1].length;i++){
			in.write(""+d[1][i]);
			if(i!=d[1].length-1)
				in.write(',');
			else
				in.write(")\n");
		}
		in.write("wilcox.test(sdp,whole,alternative=\""+alternative+"\")$p.value\n");
		in.write("q()\n");
		in.flush();
		t.join();
		t_.join();		
		p.waitFor();
		StringTokenizer st = new StringTokenizer(result.toString(),"\n");
		for(;st.hasMoreTokens();){
			String l = st.nextToken();
			if(l.indexOf("[1] ") != -1){
				return Double.parseDouble(l.substring(4));
			}
		}
		throw new Exception("R does not work!");
	}
	
	private static int[] getSDPFromFileFromSDPsite(String fname)throws IOException{
		BufferedReader bf = new BufferedReader(new FileReader(fname));
		bf.readLine();
		bf.readLine();
		bf.readLine();
		String line = bf.readLine();
		Vector<Integer> r = new Vector<Integer>();
		do{	
			StringTokenizer st = new StringTokenizer(line,"\t");
			st.nextToken();
			r.add(Integer.parseInt(st.nextToken()));
			line = bf.readLine();
		}while(line != null);
		bf.close();
		if(r.size() == 0)
			throw new IOException();
		int[] result = new int[r.size()];
		for(int i=0;i<r.size();i++){
			result[i] = r.get(i);
		}
		return result;
	}

	private static int[] getSDPFromInputFile(String fname) throws IOException{
		//System.out.println(fname);
		if(fname.indexOf(SDPDistStatForDiffMethods.EXTENSIONS[SDPDistStatForDiffMethods.SDPsite_METHOD]) != -1){
			return getSDPFromFileFromSDPsite(fname);
		}
		BufferedReader bf = new BufferedReader(new FileReader(fname));
		bf.readLine();
		bf.readLine();
		bf.readLine();
		String line = bf.readLine();
		Vector<Integer> r = new Vector<Integer>();
		do{	
			if(line.equals(""))
				throw new IOException(fname);
			r.add(Integer.parseInt(line));
			line = bf.readLine();
		}while(!line.equals("//"));
		bf.close();
		if(r.size() == 0)
			throw new IOException();
		int[] result = new int[r.size()];
		for(int i=0;i<r.size();i++){
			result[i] = r.get(i);
		}
		return result;
	}
	
	private static int[] clearSDP(int[] sdps, int[] missres,int begin,int end){
		if(missres == null)
			return sdps;
		Vector<Integer> res = new Vector<Integer>();
		for(int i=0;i<sdps.length;i++){
			if(sdps[i] < begin || sdps[i] > end)
				continue;
			int in = Arrays.binarySearch(missres, sdps[i]);
			//System.out.println(sdps[i]+"\t" +in);
			if(in < 0 || missres[in] != sdps[i])
				res.add(sdps[i]);			
		}
		int[] result = new int[res.size()];
		for(int i=0;i<result.length;i++){
			result[i] = res.get(i);
		}
		return result;
	}
	
	private static double[] getDistancesForSDP(InputParameters p, int method) throws Exception {
		if(method == SDPDistStatForDiffMethods.SDPproff_METHOD){
			double[][] res = getDistancesBetweenResiduesAndLigand(p);
			if(res == null)
				return null;
			return res[0];
		}
		
		int[] sdps = null;
		if(p.pfam_name.equals("PF00132") && p.seqName.equals("THGA_ECOLI/95-112")){
			sdps = getSDPFromInputFile(OTHER_METHODS + p.pfam_name + "-1." +SDPDistStatForDiffMethods.EXTENSIONS[method]);
		}else{
			sdps = getSDPFromInputFile(OTHER_METHODS + p.pfam_name + "." +SDPDistStatForDiffMethods.EXTENSIONS[method]);
		}
		sdps = clearSDP(sdps, p.missedresidues,p.pdbFirst,p.pdbLast);
		if(sdps.length == 0){
			return null;
		}
		return getDistancesBetweenResiduesAndLigand(p,sdps)[0];
	}
	
	private static double[] getDistancesForAllPositions(InputParameters p) throws Exception {
		return getDistancesBetweenResiduesAndLigand(p,new int[]{})[1];
	}
	
	//methods for comparing with others - END
	
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
	
	private static void printForCheckWilcoxByR(Vector<InputParameters> pars,BufferedWriter bf) throws Exception {
		bf.write("pvalueT<-c()\n");
		bf.write("pvalueL<-c()\n");
		bf.write("pvalueG<-c()\n");
		for(int i=0;i<pars.size();i++){
			double[][] d = getDistancesBetweenResiduesAndLigand(pars.get(i));
			if(d != null){
				printDistancesForCheckWilcoxByR(d,bf);
				//System.out.println(pars.get(i).name);
			}
		}
		bf.write("pvalueT\n");
		bf.write("pvalueL\n");
		bf.write("pvalueG\n");
	}
	
	private static void printDistancesForCheckWilcoxByR(double[][] d, BufferedWriter bf) throws Exception {
		if(d == null)
			return;
		bf.write("sdp<-c(");
		for(int i=0;i<d[0].length;i++){
			bf.write(""+d[0][i]);
			if(i!=d[0].length-1)
				bf.write(',');
			else
				bf.write(")\n");
		}
		bf.write("whole<-c(");
		for(int i=0;i<d[1].length;i++){
			bf.write(""+d[1][i]);
			if(i!=d[1].length-1)
				bf.write(',');
			else
				bf.write(")\n");
		}
		bf.write("pvalueT<-c(pvalueT,wilcox.test(sdp,whole,alternative=\"t\")$p.value)\n");
		bf.write("pvalueL<-c(pvalueL,wilcox.test(sdp,whole,alternative=\"l\")$p.value)\n");
		bf.write("pvalueG<-c(pvalueG,wilcox.test(sdp,whole,alternative=\"g\")$p.value)\n");
	}
	
	/**
	 * returns two-dimensional array contained 2 arrays: first is array of distances between 
	 * ligand and sdp and  second array contain distances between all residues and ligand.
	 * @param p
	 * @return
	 * @throws Exception
	 */
	private static double[][] getDistancesBetweenResiduesAndLigand(InputParameters p)throws Exception {
		setSecuriryManagerWithoutExit();
		int[] sdps = getSDPforSeq(ALIGNMENTS + p.pfam_name
					+ SDPPROF_RES_BASE, p.seqName, p.startindex, p.pdbLast,p.pdbFirst,p.missedresidues);
		if(sdps==null || sdps.length== 0){
			return null;
		}
		return getDistancesBetweenResiduesAndLigand(p, sdps);
	}
	
	private static double[][] getDistancesBetweenResiduesAndLigand(InputParameters p,int[] sdps)throws Exception {
		setSecuriryManagerWithoutExit();
		double[][] tmp = new double[2][];
		double[][] result = new double[2][];
		saveInputFile(TMP + "tmp", sdps, p.ligands, p.chain1, p.chain2,PDB + p.pdb + PDB_SUF);
		//saveInputFile(TMP + "tmp", sdps, p.ligands, p.chain1, p.chain2, p.pdb);
		Object[] obj = mindist.parseInputFile(new String[]{TMP+"tmp",TMP+"result.sdpdistout"});
		AminoAcidSet whole = (AminoAcidSet) obj[0];
		AminoAcidSet sdp = (AminoAcidSet) obj[1];
		AminoAcidSet ligand = (AminoAcidSet) obj[2];
		Structure s = (Structure) obj[3];
		tmp[0] = new double[sdps.length];
		tmp[1] = new double[whole.numbers.length];
		int badd = 0;
		if(sdps.length != 0){
			for(int i=0;i<sdp.numbers.length;i++){
				tmp[0][i] = getMinDistance(s,ligand,sdp,i);
				if(tmp[0][i] == 10000){
					badd++;
				}
				if(tmp[0][i] == -1){
					System.out.println("ttt "+p.pfam_name);
					return null;
				}
			}
		}
		if(badd != 0){
			int ind = 0;
			result[0] = new double[tmp[0].length-badd];
			for(int i=0;i<tmp[0].length;i++){
				if(tmp[0][i] != 10000){
					result[0][i-ind] = tmp[0][i];
				}else{
					ind++;
				}
			}
		}else{
			result[0] = tmp[0];
		}
		badd = 0;
		for(int i=0;i<whole.numbers.length;i++){
			tmp[1][i] = getMinDistance(s,ligand,whole,i);
			if(tmp[1][i] == 10000){
				badd++;
			}
			if(tmp[1][i] == -1){
				System.out.println("ttt "+p.pfam_name);
				return null;
			}
		}
		if(badd != 0){
			int ind = 0;
			result[1] = new double[tmp[1].length-badd];
			for(int i=0;i<tmp[1].length;i++){
				if(tmp[1][i] != 10000){
					result[1][i-ind] = tmp[1][i];
				}else{
					ind++;
				}
			}
		}else{
			result[1] = tmp[1];
		}
		return result;
	}
	
	private static double getMinDistance(Structure s,AminoAcidSet ligand,AminoAcidSet sdp, int sdpN){ 
		AminoAcidSet tmp = new AminoAcidSet(sdp.model,sdp.chain, new int[]{sdp.numbers[sdpN]});
		Distance[] res;
		try{
			res = mindist.calcMinDist(s, tmp, ligand);
		}catch(Exception e){
			System.out.println("res "+tmp.numbers[0]+"\t"+ligand.numbers[0]+"\t"+ligand.numbers[1]);
			e.printStackTrace();
			System.exit(0);
			return -1;//на случай ошибок в олином скрипте.
		}
		double result = res[0].dist;
		for(int i=0;i<res.length;i++){
			if(res[i].dist<result)
				result = res[i].dist;
		}
		//System.out.println(sdp.numbers[sdpN]+"\t"+res[0]+"\t"+res[1]+"\t"+res[2]+"\t"+res[3]+"\t"+res[4]);
		return result;
	}
	
	private static void setSecuriryManagerWithoutExit(){
		final SecurityManager securityManager = new SecurityManager() {
			public void checkPermission(Permission permission) {
				// This Prevents the shutting down of JVM.(in case of
				// System.exit())
				if ("exitVM".equals(permission.getName())) {
					throw new SecurityException(
							"System.exit attempted and blocked.");
				}
			}
			@Override
			public void checkExit(int status) {
				throw new SecurityException(
				"System.exit attempted and blocked.");
				}			
		};
		System.setSecurityManager(securityManager);	
	}
	
	private static void makePvalueByMonteCarlo(int monteCarloIteration) throws Exception {
		setSecuriryManagerWithoutExit();
		PrintWriter pw = new PrintWriter(new File(OUTPUT + "results"));
		Vector<InputParameters> pars = readInputParameters(INPUT + "input");
		pw.println("family\t" + Results.getFields());
		for (int i = 0; i < pars.size(); i++) {
			int[] sdps = getSDPforSeq(ALIGNMENTS + pars.get(i).pfam_name
					+ ".alnSDPproff_10000iter.al", pars.get(i).seqName, pars
					.get(i).startindex, pars.get(i).pdbLast,pars.get(i).pdbFirst,pars.get(i).missedresidues);
			System.out.println(pars.get(i).pfam_name);
			if(sdps == null){
				pw.println(pars.get(i).pfam_name + "_" + pars.get(i).startindex + "\tsdp don`t exist");
				continue;
			}
			//makeInputFile(pars.get(i));
			Results result = getResults(sdps, pars.get(i).ligands,
					pars.get(i).chain1, pars.get(i).chain2, PDB
							+ pars.get(i).pdb + PDB_SUF);
			for (int j = 0; j < monteCarloIteration; j++) {
				sdps = generateRandomSDP(pars.get(i).pdbFirst,
						pars.get(i).pdbLast, sdps.length,pars.get(i).missedresidues);
				try{
				result.addMonteCarloIteration(getResults(sdps,
						pars.get(i).ligands, pars.get(i).chain1,
						pars.get(i).chain2, PDB + pars.get(i).pdb + PDB_SUF));
				}catch(SecurityException e){
					j--;
					System.setOut(out);
					System.out.println("Bad sdps");
				}
				if((j+1)%100 == 0)
					System.out.print("\r"+(j+1));
			}
			pw.println(pars.get(i).pfam_name + "_" + pars.get(i).startindex + "\t"
					+ result);
			pw.flush();
		}
		pw.close();
	}

	private static void makeInputFile(InputParameters p) throws Exception {
		int[] sdps = getSDPforSeq(ALIGNMENTS + p.pfam_name
				+ ".aln_SDPproff_10000iter.al", p.seqName, p.startindex,
				p.pdbLast,p.pdbFirst,p.missedresidues);
		saveInputFile(OUTPUT + p.pfam_name + "_" + p.startindex, sdps, p.ligands,
				p.chain1, p.chain2, "../pdb/" + p.pdb + "_mod.pdb");
	}

	private static Results getResults(int[] sdps, int[] ligands, String chain1,
			String chain2, String pdb) throws Exception {
		saveInputFile(TMP + "tmp", sdps, ligands, chain1, chain2, pdb);
		System.setOut(empty);
		mindist.main(new String[]{TMP+"tmp",TMP+"result.sdpdistout"});
		System.setOut(out);
		return new Results(TMP + "result");
	}

	public static Vector<InputParameters> readInputParameters(String fname)
			throws Exception {
		BufferedReader bf = new BufferedReader(new FileReader(fname));
		Vector<InputParameters> result = new Vector<InputParameters>();	
		for (String line = bf.readLine();line != null && line != "";line = bf.readLine()) {
			if(line.startsWith("#"))
				continue;
			//System.out.println(line);
			StringTokenizer st = new StringTokenizer(line, "\t");
			Vector<String> d = new Vector<String>();
			for (; st.hasMoreTokens();) {
				d.add(st.nextToken());
			}
			result.add(new InputParameters(d.get(0), d.get(13), d.get(16), d
					.get(17), d.get(9), d.get(8), d.get(5), d.get(11), d
					.get(12),d.get(19)));
			
		}
		return result;
	}

	private static int[] getSDPforSeq(String fname, String seqName,
			int startIndex, int lastPDBindex,int firstPDBindex,int[] missinRes) throws Exception {
		Project p = new Project();
		p.setAlignmentData(fname);
		int[] ref = p.getReference(p.getSequenceNo(seqName));
		int[] sdps = p.getSortedForAlignmentPositionSDP();
		if (sdps == null || sdps.length == 0)
			return null;
		Vector<Integer> result = new Vector<Integer>();
		for (int i = 0; i < sdps.length; i++) {
			if (ref[sdps[i]] != -1) {
				if(missinRes != null && Arrays.binarySearch(missinRes, ref[sdps[i]] + startIndex - 1) >= 0)
					continue;
				 if(ref[sdps[i]] +startIndex-1 < firstPDBindex)
					 continue;
				 if(ref[sdps[i]] +startIndex-1 > lastPDBindex)
					 break;
				result.add(ref[sdps[i]] + startIndex - 1);
			}
		}
		int[] res = new int[result.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = result.get(i);
		}
		return res;
	}
	
	private static int[] generateRandomSDP(int first, int last, int count) {
		int[] t = new int[last - first + 1];
		for (int i = first; i <= last; i++) {
			t[i - first] = i;
		}
		StaticFunction.shuffleIntArray(t);
		int result[] = new int[count];
		for (int i = 0; i < count; i++) {
			result[i] = t[i];
		}
		return result;
	}

	private static int[] generateRandomSDP(int first, int last, int count,int[] missedresidues) {
		if(missedresidues == null || missedresidues.length == 0){
			return generateRandomSDP(first,last,count);
		}
		int missIndex=0;
		int[] t = new int[last - first + 1-missedresidues.length];
		for (int i = first; i <= last; i++) {
			if(missIndex<missedresidues.length && missedresidues[missIndex] == i ){
				missIndex++;
				continue;
			}
			//System.out.println(t.length+"\t"+i+"\t"+first+"\t"+missIndex);
			t[i - first-missIndex] = i;
		}
		StaticFunction.shuffleIntArray(t);
		int result[] = new int[count];
		for (int i = 0; i < count; i++) {
			result[i] = t[i];
		}
		return result;
	}

	private static void saveInputFile(String fname, int[] sdps, int[] ligands,
			String chain1, String chain2, String pdbPath) throws Exception {
		PrintWriter pw = new PrintWriter(new File(fname));
		pw.println(pdbPath + "\n0\n" + chain1);
		//if(sdps.length == 0)
			//pw.println();
		for (int i = 0; i < sdps.length; i++) {
			pw.println(sdps[i]);
		}
		pw.println("//\n0\n" + chain2);
		for (int i = 0; i < ligands.length; i++) {
			pw.println(ligands[i]);
		}
		pw.println("//");
		pw.close();
	}

}
