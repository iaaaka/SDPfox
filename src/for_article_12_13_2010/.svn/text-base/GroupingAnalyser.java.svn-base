package for_article_12_13_2010;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Set;

import Math.StaticFunction;

public class GroupingAnalyser {
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		//printGenerLaciDist();
		printAllPfam();
		printPfamTogether();
	}
	
	public static void printGenerLaciDist()throws Exception {
		String r = GroupingFormater.grouping_res+"gener+laci/";
		String rg = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/groups_by_ec/gener+lac/";
		printDistances(new String[] {r+"laci",r+"gener1",r+"gener2_mix"},
				new String[] {"gc05","gc06","pk","s3det","s","sdpc"},
				new String[] {rg+"laci",rg+"gener1",rg+"gener2_mix"});
	}
	
	public static void printDistances(String[] bases,String[] ext,String[] rights) throws Exception {
		for(int i= 0;i<bases.length;i++) {
			System.out.print(bases[i].substring(bases[i].lastIndexOf("/")+1)+"\t");
			for(String e : ext) {
				Double d = getDistance(bases[i]+"."+e,rights[i]);
				System.out.print((d==null?"N/A":d)+"\t");
			}
			System.out.println();
		}
	}
	
	public static File[] getGroupingResFiles(final String ex) {
		return new File(GroupingFormater.grouping_res).listFiles(new FilenameFilter() {		
			public boolean accept(File dir, String name) {
				return name.endsWith(ex);
			}
		});
	}
	
	public static SeqGrouping getAll(File[] fs) throws Exception {
		SeqGrouping r = new SeqGrouping();
		for(File f : fs) {
			String pfam = f.getName();
			if(pfam.indexOf('.') != -1)
				pfam = pfam.substring(0,pfam.indexOf('.'));
			SeqGrouping t = new SeqGrouping(f.getAbsolutePath());
			Set<String> sns = t.getSequences();
			for(String sn : sns) {
				r.addSeq(pfam+"_"+sn, pfam+"_"+t.getGroup(sn));
			}
		}
		return r;
	}
	
	public static void printPfamTogether() throws Exception{
		String[] ext = new String[] {"sdps","gc05","gc06","pk","s3det","s","sdpc"};
		File[] ec = new File(ECGroupingMaker.grouping_by_ec).listFiles(new FileFilter() {			
			public boolean accept(File pathname) {
				return !pathname.isDirectory();
			}
		});
		
		SeqGrouping ec_gr = getAll(ec);
		System.out.print("together\t");
		for(String ex : ext) {
			System.out.print(getDistance(getAll(getGroupingResFiles(ex)),ec_gr)+"\t");
		}
	}
	
	public static void printAllPfam() throws Exception {
		String[] ext = new String[] {"sdps","gc05","gc06","pk","s3det","s","sdpc"};
		File[] ec = new File(ECGroupingMaker.grouping_by_ec).listFiles(new FileFilter() {			
			public boolean accept(File pathname) {
				return !pathname.isDirectory();
			}
		});
		String[] rights = new String[ec.length];
		String[] bases = new String[ec.length];
		for(int i=0;i<ec.length;i++) {
			rights[i] = ec[i].getCanonicalPath();
			bases[i] = GroupingFormater.grouping_res+ec[i].getName();
		}
		printDistances(bases,ext,rights);
	}
	
	public static Double getDistance(String g1,String g2) throws Exception {
		if(!new File(g1).exists() || !new File(g2).exists())
			return null;
		return getDistance(new SeqGrouping(g1),new SeqGrouping(g2));
	}
	
	/**
	 * uses only sequences shared in g1 and g2 
	 * @param g1
	 * @param g2
	 * @return
	 */
	public static double getDistance(SeqGrouping g1,SeqGrouping g2) {
		ArrayList<String> snames = new ArrayList<String>(StaticFunction.getIntersection(g1.getSequences(),g2.getSequences()));
		int[] gr1 = new int[snames.size()];
		int[] gr2 = new int[snames.size()];
		for(int i=0;i<snames.size();i++) {
			gr1[i] = g1.getGroup(snames.get(i)).hashCode();
			gr2[i] = g2.getGroup(snames.get(i)).hashCode();
		}
		return StaticFunction.calculateMIdistance(gr1, gr2);
	}
	
	public static boolean equals(Set<String> s1,Set<String> s2) {
		if(s1.size() != s2.size())
			return false;
		for(String s : s1)
			if(!s2.contains(s))
				return false;
		return true;
	}

}
