package Run;

import Math.StaticFunction;
import Objects.AlignmentData;
import Util.Numerator;
import java.io.*;

public class GroupingComparator {
	
	public static int[][] getGroupingsFromFiles(String al1,String al2) throws Exception{
		int[][] result = new int[2][];
		AlignmentData ad1 = new AlignmentData(al1);
		AlignmentData ad2 = new AlignmentData(al2);
		result[0] = ad1.grouping.getGroupNoArray();
		result[1] = new int[result[0].length];
		for(int i=0;i<result[0].length;i++){
			result[1][i] = ad2.grouping.getGroupForSeq(ad2.seqNumerator.getNumForName(ad1.seqNumerator.getNameForNum(i)));
		}
		return result;
	}
	
	public static int[] getGroupingFromSplitedFile(String fname, Numerator sn) throws Exception {
		int[] result = new int[sn.getNumOfNames()];
		BufferedReader br = new BufferedReader(new FileReader(fname));
		int currentgr = 0;
		for(String l = br.readLine();l	!=	null;l = br.readLine()){
			if(l.charAt(0) == '='){
				currentgr++;
			}else{
				System.out.println(l);
				result[sn.getNumForName(l)] = currentgr;
			}
		}
		return result;
	}
	
	public static double getDistBeetwenAlignAndSplit(String al,String spl) throws Exception {
		AlignmentData ad = new AlignmentData(al);
		int[] sp = getGroupingFromSplitedFile(spl,ad.seqNumerator);
		return StaticFunction.calculateMIdistance(ad.grouping.getGroupNoArray(),sp);
	}
	
	public static double getDistanceBeetwenGrFromAlign(String al1,String al2) throws Exception {
		int[][] gr = getGroupingsFromFiles(al1,al2);
		return StaticFunction.calculateMIdistance(gr[0], gr[1]);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(getDistBeetwenAlignAndSplit("SDP_reg_new.txt","regGroupedNames"));
	}
}
