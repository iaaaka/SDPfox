package for_article_12_13_2010;

import java.util.ArrayList;
import java.util.HashSet;

import Math.StaticFunction;
import Objects.AlignmentData;

public class LacIAnalyser {
	private static final String laci_tp_4_15 = "143,214,307,367,73,183,244,251,270,279,281,304,311,319,320,329,369,153,224,128,133,135,136,137,138,147,150,160,161,162,184,187,188,191,198,201,202,203,204,205,206,207,211,213,225,229,255,258,259,268,271,272,273,274,276,278,282,285,286,296,300,301,302,303,316,318,321,323,328,332,333,336,370,247,248,249,252,280,283,284,287,288,310,312,313,314,315,317,8,9,10,11,21,22,23,24,26,27,30,31,37,61,62,64,65,69,13,14,15,17,18,20,25,28,29,38,39,42,46,49,50,53,55,66,87,89,90,93,139,141,163,210,212,221,246,275,277,305,306,308,325,71,79,80,88,92,94,95,98,102,107,109,110,111,112,124,164,176,177,178,180,72,74,75,76,77,78,91,97,101,106,108,113,142,56,57,58,59,60,63,67,126,127,129,130,131,132";
	private static final int laci_alignment_length = 384;
	private static final HashSet<Integer> laci_tp_4_15_set = parse(laci_tp_4_15);
	private static final int true_negatives = laci_alignment_length - laci_tp_4_15_set.size();
	static InputParameters laci_param = new InputParameters("","699,700,701,702,703,704,705,706,707,708,709,710,711,712,713,714,715,599",
			"A"," ","0","","1BDH","3","340","");
	private static String laci_align = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/gener+laci/lac+1BDH.aln";
	private static String bdh1_seq_name = "1BDH_A|PDBID|CHAIN|SEQUENCE"; 
	
	public static void main(String[] args)throws Exception {
		//alignment positions
		String sdpc = "12,22,23,27,29,32,37,63,65,112,126,138,139,160,161,163,177,179,182,314,324,361";
		String pk = "37,123,126,32,283,179,177,314,163,22,139,160,361,176,56,324,161,247,107,164,112,142,67,332,115,65,147,23,148,138,60,313,33,78,8,100,211,63,104,12,321,338,307,129,66,140";
		//printSensAndFPR(pk);
		System.out.println(getMedianPV(pk));
	}
	
	private static double getMedianPV(String sdps) throws Exception {
		int[] sdps_1bdh = to1BDH(sdps);
		double[][] dists = DistanceAnalyser.getDistancesBetweenResiduesAndLigand(laci_param,sdps_1bdh);
		return DistanceForLigandStatistics_2009.getWilcoxonByR(dists[1], dists[0]);
	}
	
	private static int[] to1BDH(String sdps) throws Exception {
		AlignmentData ad = new AlignmentData(laci_align);
		HashSet<Integer> sdps_al = parse(sdps);
		ArrayList<Integer> r_tmp = new ArrayList<Integer>();
		int ref = ad.seqNumerator.getNumForName(bdh1_seq_name);
		for(int ap : sdps_al)
			if(ad.alignment.getReference(ref)[ap] != -1)
				r_tmp.add(ad.alignment.getReference(ref)[ap]);
		return StaticFunction.toArray(r_tmp);
	}
	
	private static void printSensAndFPR(String sdp) {
		double[] r = StaticFunction.getSensAndFalsePositivesRate(true_negatives, parse(sdp), laci_tp_4_15_set);
		System.out.println("Sensitivity = "+r[0]+"\nFalse positives rate = "+r[1]);
	}
	
	private static HashSet<Integer> parse(String positions){
		String[] p = positions.split(",");
		HashSet<Integer> r = new HashSet<Integer>();
		for(String pp : p)
			r.add(Integer.parseInt(pp));
		return r;
	}
}
