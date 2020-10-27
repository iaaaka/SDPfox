package Run;

import Objects.*;
import Math.*;
import java.util.*;
import Util.*;

public class AlignmentFilter {
	
	public static AlignmentData filterByIdentityWithPriority(AlignmentData data,double thr,int[] seqWithPriority) throws Exception {
		int[] goodSeq = StaticFunction.filterByIdentity(data.alignment.getIdentityMatrix(), thr,seqWithPriority);
		return StaticFunction.createNewAlignmentDataFromSeqsNums(goodSeq, data);
	}

	public static AlignmentData filterByIdentityWithPriorityByName(String inFile,double thr,String pattern) throws Exception {
		AlignmentData data = new AlignmentData(inFile);
		System.out.print("befor "+data.alignment.getAlignmentCount()+" ");
		printVector(statForNames(data.seqNumerator,"PDE"));
		return filterByIdentityWithPriority(data,thr,StaticFunction.filterByName(data, pattern));
	}
	
	
	public static AlignmentData filterByIdentity(AlignmentData data,double thr) throws Exception {
		int[] goodSeq = StaticFunction.filterByIdentity(data.alignment.getIdentityMatrix(), thr);
		return StaticFunction.createNewAlignmentDataFromSeqsNums(goodSeq, data);
	}

	public static AlignmentData filterByIdentity(String inFile,double thr) throws Exception {
		return filterByIdentity(new AlignmentData(inFile),thr);
	}
	
	public static AlignmentData filterByAlignmentCoverage(AlignmentData data,double gapsPartForCons,double partSequentalGaps) throws Exception {
		int[] goodSeq = StaticFunction.filterByAlignmentCoverage(gapsPartForCons, partSequentalGaps, data.alignment);
		return StaticFunction.createNewAlignmentDataFromSeqsNums(goodSeq, data);
	}
	
	public static Vector<String> statForNames(Numerator n,String pattern){
		Vector<String> names = new Vector<String>();
		for(int i=0;i<n.getNumOfNames();i++){
			if(n.getNameForNum(i).indexOf(pattern) != -1){
				names.add(n.getNameForNum(i));
			}
		}
		Collections.sort(names);
		return names;
	}
	
	public static void printVector(Vector v){
		System.out.println(v.size());
		for(int i=0;i<v.size();i++){
			//System.out.println(v.get(i));
		}
	}
	
	public static AlignmentData filterByAlignmentCoverage(String inFile,double gapsPartForCons,double partSequentalGaps) throws Exception {
		return filterByAlignmentCoverage(new AlignmentData(inFile),gapsPartForCons,partSequentalGaps);
	}
	/**
	 * 
	 * @param args infile, outfile, method(ident or cov or both), gapsPartForCons, partSequentalGaps, thr (for identity)
	 */
	public static void main(String[] args) throws Exception {
		StaticFunction.saveAlignment(filterByIdentity("sdp_reg_g.txt",0.95), "ttttt", StaticFunction.GDE_TYPE);
		/*AlignmentData data = new AlignmentData("in/alignment/PF00233_full.txt");
		data = StaticFunction.createNewAlignmentDataFromSeqsNums(StaticFunction.filterByName(data, "PDE"),data);
		System.out.println(data.alignment.getAlignmentCount());
		data = filterByIdentity(data,0.95);
		System.out.println("ident");
		System.out.println(data.alignment.getAlignmentCount());
		data = filterByAlignmentCoverage(data, 0.1, 0.3);
		System.out.println("cov");
		System.out.println(data.alignment.getAlignmentCount());
		StaticFunction.saveAlignment(data, "out/PF00233_onlyPDE_filtered.phy", StaticFunction.PHYLIP_TYPE);
		
		if(args.length==0){
			args = new String[]{"in/alignment/PF00233_full.txt","out/PF00233_filtered.gde","both","0.1","0.3","0.95","PDE"};
		}
		double gapsPartForCons = Double.parseDouble(args[3]);
		double partSequentalGaps = Double.parseDouble(args[4]);
		double thr = Double.parseDouble(args[5]);
		AlignmentData result = null;
		if(args[2].equals("ident")){
			result = filterByIdentity(args[0],thr);
		}
		if(args[2].equals("cov")){
			result = filterByAlignmentCoverage(args[0], gapsPartForCons, partSequentalGaps);
		}
		if(args[2].equals("both")){
			result = filterByIdentityWithPriorityByName(args[0],thr,args[6]);
			System.out.print("ident "+result.alignment.getAlignmentCount()+" ");
			printVector(statForNames(result.seqNumerator,"PDE"));
			result = filterByAlignmentCoverage(result, gapsPartForCons, partSequentalGaps);
			System.out.print("cov "+result.alignment.getAlignmentCount()+" ");
			printVector(statForNames(result.seqNumerator,"PDE"));
		}
		//StaticFunction.saveAlignment(result, args[1], StaticFunction.GDE_TYPE);*/
	}

}
