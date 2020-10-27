package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ECGroupingMaker {
	ECdatabase ecs;
	HashMap<String, String> pfam_to_ec_filter;
	public static String grouping_by_ec = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/groups_by_ec/";
	public static String multy_dir = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/multyEC/";
	//private static String single_dir = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/singleEC/";

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ECGroupingMaker t = new ECGroupingMaker(true);
		//t.saveECGrouping(multy_dir+"PF00351.fasta", System.out);
		t.saveGroupingByEc(t.getMulti2Ec4Seq(),grouping_by_ec);
		//t.testPfamsWithSwissTrembl(t.getMulti2Ec4Seq(),
		//		new PrintStream("/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/ec numbers/seg_names_to_pfam"));
	}
	
	private void saveGroupingByEc(File[] in, String dir_out) throws FileNotFoundException, Exception {
		for(File f : in) {
			SeqGrouping t =  getECGrouping(f.getAbsolutePath());
			t.save(dir_out+f.getName().substring(0,f.getName().length()-6));
		}
	}
	
	/**
	 * 
	 * @return alignments from multi set with more than 1 EC and more than 3 sequences with EC
 	 */
	private File[] getMulti2Ec4Seq() {
		File[] r =  new File(multy_dir).listFiles(new FileFilter() {		
			public boolean accept(File f) {
				if(!f.getName().endsWith(".fasta"))
					return false;
				//if (true) return true;
				SeqGrouping g = null;
				try {
					g = getECGrouping(f.getAbsolutePath());
				} catch (Exception e) {
					e.printStackTrace();
				}
				int sum_ec = g.getSequences().size();
				int ec_count = g.getGroups().size();			
				System.out.println(f.getName()+"\t"+ec_count+"\t"+sum_ec);
				return (sum_ec >=4) && (ec_count >= 2);
			}
		});
		Arrays.sort(r);
		return r;
	}
	
	private SeqGrouping getECGrouping(String al) throws Exception {
		String pfam = al.substring(al.lastIndexOf("/")+1,al.lastIndexOf("."));
		ArrayList<String> names = getSeqNames(al);
		HashMap<String, String> names_to_ec = new HashMap<String, String>();
		for(String n : names) {
			Set<String> ec = ecs.getECs(n,4,pfam_to_ec_filter.get(pfam));
			if(ec.size() == 1) {
				names_to_ec.put(n, ec.iterator().next());
			}
		}
		SeqGrouping res = new SeqGrouping();
		HashSet<String> all_pos_ecs = new HashSet<String>(names_to_ec.values());
		for(String ec : all_pos_ecs) {
			for(String n : names_to_ec.keySet()) {
				if(names_to_ec.get(n).equals(ec)) {
					res.addSeq(n, "EC="+ec);
				}
			};
		}
		return res;
	}
	
	
	private void checkEC(File[] als,int ec_level) throws IOException{
		for(File f : als) {
			System.out.print("\n"+f.getName());
			printFirstEC(f.getAbsolutePath(),ec_level);
		}
	}
	
	public void testPfamsWithSwissTrembl(File[] als,PrintStream out) throws Exception {
		HashSet<String> seq_names = new HashSet<String>();
		for(File f : als) {
			System.out.println(f.getName());
			seq_names.addAll(getSeqNames(f.getAbsolutePath()));
		}
		SwissTremblParser.printPfamRes(seq_names,out);
	}
	
	public HashMap<String, Integer> getECstat(String al_fname,int ec_level) throws IOException {
		String pfam = al_fname.substring(al_fname.lastIndexOf("/")+1,al_fname.lastIndexOf("."));
		ArrayList<String> names = getSeqNames(al_fname);
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		for(String n : names) {
			Set<String> ec = ecs.getECs(n,ec_level,pfam_to_ec_filter.get(pfam));
			for(String e : ec) {
				Integer v = res.get(e);
				if(v == null)
					v = 0;
				res.put(e, v+1);
			}
			if(ec.size() == 0) {
				Integer v = res.get("none");
				if(v == null)
					v = 0;
				res.put("none", v+1);
			}				
		}
		return res;
	}
	
	public void printFirstEC(String al_fname,int ec_level) throws IOException {
		String pfam = al_fname.substring(al_fname.lastIndexOf("/")+1,al_fname.lastIndexOf("."));
		ArrayList<String> names = getSeqNames(al_fname);
		HashMap<String, Integer> res = new HashMap<String, Integer>();
		for(String n : names) {
			Set<String> ec = ecs.getECs(n,ec_level,pfam_to_ec_filter.get(pfam));
			for(String e : ec) {
				Integer v = res.get(e);
				if(v == null)
					v = 0;
				res.put(e, v+1);
			}
			if(ec.size() == 0) {
				Integer v = res.get("none");
				if(v == null)
					v = 0;
				res.put("none", v+1);
			}
			/* to print protein with several EC
			 if(ec.size() > 1) {
				String r = "";
				for(String e : ec)
					r += e+"; ";
				System.out.print("\n"+n+"\t"+r);
			}*/			
		}
		print(res);
	}
	
	private void print(HashMap<String,Integer> m) {
		ArrayList<String> keys = new ArrayList<String>(m.keySet());
		Collections.sort(keys);
		int sum_ec = 0;
		for(String k : keys) {
			System.out.println("\t"+k+"\t"+m.get(k));
			if(!k.equals("none"))
				sum_ec += m.get(k);
		}
		//System.out.print("\tECs = "+(keys.size()-(keys.contains("none")?1:0))+"; with_ec = "+ sum_ec);
	}
	
	public ECGroupingMaker(boolean with_filter_by_ec) throws IOException {
		ecs = new ECdatabase();
		pfam_to_ec_filter = new HashMap<String, String>();
		if(with_filter_by_ec) {
			BufferedReader r = new BufferedReader(new InputStreamReader(ECGroupingMaker.class.getResourceAsStream("pfam_to_ec")));
			for(String l = r.readLine();l!=null;l = r.readLine()) {
				if(l.charAt(0) == '#')
					continue;
				String[] t = l.split("\t");
				pfam_to_ec_filter.put(t[0], t[1]);
			}
			r.close();
		}
	}
	
	public static ArrayList<String> getSeqNames(String al_fname) throws IOException{
		ArrayList<String> r = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader(al_fname));
		for(String l = br.readLine();l!=null;l = br.readLine()) {
			if(l.charAt(0) == '>' || l.charAt(0) == '%') {
				int end = l.indexOf("/");
				if(end == -1)
					end = l.length();
				r.add(l.substring(1,end));
			}
		}
		br.close();
		return r;
	}

}
