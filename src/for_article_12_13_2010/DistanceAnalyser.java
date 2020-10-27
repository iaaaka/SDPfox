package for_article_12_13_2010;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import Math.StaticFunction;

import from_olga.pdbparser.AminoAcidSet;
import from_olga.pdbparser.Distance;
import from_olga.pdbparser.Structure;
import from_olga.pdbparser.mindist;

class DistAnalysisResults{
	double min_dist;
	double pv_min_dist;
	double median;
	double pv_median;
	double sdp_count;
	double sens;
	double fpr;
	double avg_dist;
	String pfam;
	boolean na = false;
	
	public static String getFields() {
		return 	"Family\t"+
				"SDP count\t" +
				"minimum distance to ligand\t" +
				"p-value of minimum distance\t" +
				"median distance to ligand\t" +
				"p-value of median\t" +
				"average distance\t" +
				"sensitivity\t" +
				"false positive rate";
	}
	
	public String toString() {
		if(na)
			return pfam+"\tNA\tNA\tNA\tNA\tNA\tNA\tNA\tNA";
		else
			return pfam+"\t"+sdp_count+"\t"+min_dist+"\t"+pv_min_dist+"\t"+median+"\t"+pv_median+"\t"+avg_dist+"\t"+sens+"\t"+fpr;
				
	}
}

public class DistanceAnalyser {
	public static final String SDP_DIR_RAW = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/method_results/";
	public static final String ALIGNMENTS_DIR = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/";
	public static final String SDP_DIR = SDP_DIR_RAW+"allSDP/";
	public static final String SDP_DIR_MULTY = SDP_DIR+"multyEC/";
	public static final String SDP_DIR_SINGLE = SDP_DIR+"singleEC/";
	public static final String MULTY_PARAM = SDP_DIR+"multy_param";
	public static final String SINGLE_PARAM = SDP_DIR+"single_param";
	private static final String TMP = "./";
	private static final String PDB = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pdb/";
	public static final int EVOL_TRACE = 0;
	public static final int S3DET = 1;
	public static final int MB = 2;
	public static final int S = 3;
	public static final int R4S = 4;
	public static final int SDPCLUST = 5;
	public static final int SDPSITE = 6;
	public static final int PROT_KEYS = 7;
	public static final int SDP_PROFF = 8; //old (about 2009) results
	public static final String[] METHODS = {"Trace Suite II","S3DET-method", "MB-method", "S-method", "rate4site", "SDPclust","SDPsite","Protein keys","SDPproff"};
	public static final String[] EXTENSIONS ={"et","s3det","mb","s","r4s","sdpc","sdps","pk","sdpp"};
	private static boolean suppressWarnings = true;
	private static double dist_thr = 10;
	
	 
	public static void main(String[] args) throws Exception {
		//printQualityStatForAllAligmnents("single",dist_thr);
		//printQualityStatForAllAligmnents("multy",dist_thr);
		printAnalysisResults(dist_thr);
	}
	
	public static List<InputParameters> getInputParameters(String single_multy) throws Exception {
		return  DistanceForLigandStatistics_2009.readInputParameters(DistanceAnalyser.SDP_DIR+single_multy+"_param");
	}
	
	private static double getMinDistance(Structure s,AminoAcidSet ligands,AminoAcidSet residues){ 
		Distance[] res = mindist.calcMinDist(s, residues, ligands);
		double result = res[0].dist;
		for(int i=0;i<res.length;i++){
			result = Math.min(result, res[i].dist);
		}
		//System.out.println(sdp.numbers[sdpN]+"\t"+res[0]+"\t"+res[1]+"\t"+res[2]+"\t"+res[3]+"\t"+res[4]);
		return result;
	}
	
	public static Sdps getSDPs(InputParameters p,String single_multy,int method) throws IOException {
		return new Sdps(SDP_DIR+single_multy+"EC/"+p.pfam_name+"."+DistanceAnalyser.EXTENSIONS[method]);
	}
	
	/**
	 * prints table 4 for article
	 * @param single_multy
	 * @throws Exception 
	 */
	public static void printQualityStatForAllAligmnents(String single_multy,int dist_thr) throws Exception {
		System.out.println(single_multy);
		System.out.println("method\tSensitivity\tfalse positive rate\tAverage distance to the ligand\tMedian distance to the ligand");
		for(int m = 0;m<METHODS.length;m++) {
			System.out.print(METHODS[m]+"\t");
			double[] t = getQualityStatForAllAligmnents(single_multy,dist_thr,m);
			System.out.println(t[0]+"\t"+t[1]+"\t"+t[2]+"\t"+t[3]);
		}
	}
	
	/**
	 * analyzes all pfam alignments together (joins distances to ligand into one list)
	 * @param single_multy
	 * @param dist_thr all residues closer than dist_thr A considered as true SDP
	 * @return sensitivity, false positive rate, average distance, median distance to ligand
	 * @throws Exception 
	 */
	public static double[] getQualityStatForAllAligmnents(String single_multy,int dist_thr,int method) throws Exception {
		List<InputParameters> inpar = getInputParameters(single_multy);
		ArrayList<Double> sdp_dist = new ArrayList<Double>();
		ArrayList<Double> whole_dist = new ArrayList<Double>();
		for(InputParameters i : inpar) {
			Sdps s = getSDPs(i,single_multy,method);
			double[][] dists =  getDistancesBetweenResiduesAndLigand(i,s.sdps);
			sdp_dist.addAll(StaticFunction.asCollection(dists[0]));
			whole_dist.addAll(StaticFunction.asCollection(dists[1]));
		}
		Collections.sort(sdp_dist);
		Collections.sort(whole_dist);
		double[] stat_sdp = getStat(sdp_dist,dist_thr);
		double[] whole_sdp = getStat(whole_dist,dist_thr);
		double[] r = new double[4];
		r[0] = stat_sdp[2]/whole_sdp[2];
		r[1] = (sdp_dist.size()- stat_sdp[2])/(whole_dist.size()-whole_sdp[2]);
		r[2] = stat_sdp[0];
		r[3] = stat_sdp[1];
		return r;
	}
	
	/**
	 * 
	 * @param d should be sorted
	 * @param p
	 * @return average,median, count(value <= q)
	 */
	private static double[] getStat(ArrayList<Double> d,double q) {
		double[] r = new double[3];
		r[1] = d.get(d.size()/2);
		r[2] = Collections.binarySearch(d, q);
		if(r[2] < 0) {
			r[2] = -r[2] - 1;
		}
		for(double dd : d)
			r[0] += dd;
		r[0] /= d.size();
		return r;
	}
	
	public static void printAnalysisResults(double dist_thr) throws Exception {
		for(int m = 0;m<METHODS.length;m++) {
			System.out.println("Method = "+METHODS[m]);
			printAnalysisResults(m,"single",dist_thr);
			printAnalysisResults(m,"multy",dist_thr);
		}
	}
	
	public static void printAnalysisResults(int method,String single_multy,double dist_thr) throws Exception {
		List<InputParameters> in = getInputParameters(single_multy);
		String tmp = "dataset1";
		if(single_multy.equals("multy"))
			tmp = "dataset2";
		System.out.println(tmp);
		System.out.println(DistAnalysisResults.getFields());
		for(InputParameters i : in) {
			System.out.println(analyse(i,getSDPs(i, single_multy, method).sdps,dist_thr));
		}
		
	}
	
	public static DistAnalysisResults analyse(InputParameters p,int[] sdps,double dist_thr) throws Exception {
		sdps = clearSDP(sdps, p.missedresidues, p.pdbFirst, p.pdbLast);
		DistAnalysisResults r = new DistAnalysisResults();
		r.pfam = p.pfam_name;
		if(sdps.length == 0) {
			r.na = true;
			return r;
		}
		double[][] dists = getDistancesBetweenResiduesAndLigand(p,sdps);
		ArrayList<Double> sdp_dist = new ArrayList<Double>(StaticFunction.asCollection(dists[0]));
		ArrayList<Double> whole_dist = new ArrayList<Double>(StaticFunction.asCollection(dists[1]));
		Collections.sort(sdp_dist);
		Collections.sort(whole_dist);
		r.min_dist = sdp_dist.get(0);
		r.sdp_count = sdps.length;
		double[] sdp_stat =  getStat(sdp_dist,dist_thr);
		double[] whole_stat =  getStat(whole_dist,dist_thr);
		r.avg_dist = sdp_stat[0];
		r.median = sdp_stat[1];
		r.sens = sdp_stat[2]/whole_stat[2];
		r.fpr = (sdp_dist.size()- sdp_stat[2])/(whole_dist.size()-whole_stat[2]);
		r.pv_min_dist = DistanceForLigandStatistics_2009.getPvalueForMinValue(dists[1], dists[0], r.min_dist);
		r.pv_median = DistanceForLigandStatistics_2009.getWilcoxonByR(dists[1], dists[0]);
		return r;
	}
	
	/**
	 * 
	 * @param p
	 * @param sdps
	 * @return [0][] - distances for sdp; [1][] - distances for whole residues
	 * @throws Exception
	 */
	protected static double[][] getDistancesBetweenResiduesAndLigand(InputParameters p,int[] sdps)throws Exception {
		double[][] result = new double[2][];
		sdps = clearSDP(sdps, p.missedresidues, p.pdbFirst, p.pdbLast);
		if(sdps.length == 0)
			return new double[2][0];
		saveInputFile(TMP + "tmp", sdps, p.ligands, p.chain1, p.chain2,PDB + p.pdb + ".pdb");
		Object[] obj = mindist.parseInputFile(new String[]{TMP+"tmp",TMP+"result.sdpdistout"});
		AminoAcidSet whole = (AminoAcidSet) obj[0];
		AminoAcidSet sdp = (AminoAcidSet) obj[1];
		AminoAcidSet ligand = (AminoAcidSet) obj[2];
		Structure s = (Structure) obj[3];
		result[0] = new double[sdps.length];
		result[1] = new double[whole.numbers.length];
		if(sdps.length != 0){
			for(int i=0;i<sdp.numbers.length;i++){
				//System.out.println(sdp.numbers[i]);
				result[0][i] = getMinDistance(s,ligand,new AminoAcidSet(sdp.model, sdp.chain, new int[] {sdp.numbers[i]}));
				if(result[0][i] == 10000 && !suppressWarnings){
					throw new Exception("bas sdp: "+sdp.numbers[i]+"\t"+p.pfam_name);
				}
			}
		}
		for(int i=0;i<whole.numbers.length;i++){
			result[1][i] = getMinDistance(s,ligand,new AminoAcidSet(whole.model, whole.chain, new int[] {whole.numbers[i]}));
			if(result[1][i] == 10000 && !suppressWarnings){
				throw new Exception("bas whole: "+sdp.numbers[i]+"\t"+p.pfam_name);
			}
		}
		return result;
	}
	
	private static int[] clearSDP(int[] sdps, int[] missres,int begin,int end){
		Vector<Integer> res = new Vector<Integer>();
		for(int i=0;i<sdps.length;i++){
			if(sdps[i] < begin || sdps[i] > end)
				continue;
			int in = Arrays.binarySearch(missres, sdps[i]);
			if(in < 0)
				res.add(sdps[i]);			
		}
		return StaticFunction.toArray(res);
	}
	
	private static void saveInputFile(String fname, int[] sdps, int[] ligands,
			String chain1, String chain2, String pdbPath) throws Exception {
		PrintWriter pw = new PrintWriter(new File(fname));
		pw.println(pdbPath + "\n0\n" + chain1);
		if(sdps.length == 0)
			pw.println();
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
