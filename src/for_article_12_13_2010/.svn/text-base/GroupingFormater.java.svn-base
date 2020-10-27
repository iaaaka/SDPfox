package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;

import Math.StaticFunction;
import Objects.AlignmentData;

/**
 * consist of methods to save grouping results in following format:
 * group name1
 * sequence_name1;sequence_name2;...
 * group name2
 * 
 * files should contain only sequences with EC (accordingly with given file with grouping by EC
 * from ECGroupingMaker.grouping_by_ec folder)
 * @author mazin
 *
 */
public class GroupingFormater {
	public static String grouping_res = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/method_results/grouping/";
	public static String method_res = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/method_results/";
	
	public static void main(String[] args)throws Exception {
		GroupingFormater t = new GroupingFormater();
		t.formatAll();
	}
	
	public static void formatAll() throws Exception {
		GroupingFormater t = new GroupingFormater();
		//laci+gener	
		/*String lg_res = grouping_res+"/gener+laci/";
		
		String laci = "/home/mazin/src/SDPfoxWeb/in/alignment/LacI.gde";
		String g2 = "/home/mazin/src/SDPfoxWeb/in/alignment/gener2_mix.gde";
		String g1 = "/home/mazin/src/SDPfoxWeb/in/alignment/gener1.gde";
		
		String laci_ec = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/groups_by_ec/gener+lac/laci";
		String g2_ec = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/groups_by_ec/gener+lac/gener2_mix";
		String g1_ec = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/pfam_align/groups_by_ec/gener+lac/gener1";
		
		
		t.formatSDPclust(laci,null,laci_ec);
		t.formatSDPclust(g1,null,g1_ec);
		t.formatSDPclust(g2,null,g2_ec);
		//giant comp
		t.formatGiantComp(laci, 0.5, laci_ec,lg_res+"laci.gc05");
		t.formatGiantComp(g1, 0.5, g1_ec,lg_res+"gener1.gc05");
		t.formatGiantComp(g2, 0.5, g2_ec,lg_res+"gener2_mix.gc05");
		
		t.formatGiantComp(laci, 0.6, laci_ec,lg_res+"laci.gc06");
		t.formatGiantComp(g1, 0.6, g1_ec,lg_res+"gener1.gc06");
		t.formatGiantComp(g2, 0.6, g2_ec,lg_res+"gener2_mix.gc06");
		//tree det
		t.formatTreeDet(method_res+"treeDet/gener+laci/laci.xml", laci_ec, lg_res+"laci.s3det", "S3DET");
		t.formatTreeDet(method_res+"treeDet/gener+laci/gener1.xml", g1_ec, lg_res+"gener1.s3det", "S3DET");
		t.formatTreeDet(method_res+"treeDet/gener+laci/gener2_mix.xml", g2_ec, lg_res+"gener2_mix.s3det", "S3DET");
		
		t.formatTreeDet(method_res+"treeDet/gener+laci/laci.xml", laci_ec, lg_res+"laci.s", "S");
		t.formatTreeDet(method_res+"treeDet/gener+laci/gener1.xml", g1_ec, lg_res+"gener1.s", "S");
		t.formatTreeDet(method_res+"treeDet/gener+laci/gener2_mix.xml", g2_ec, lg_res+"gener2_mix.s", "S");
		//protein keys
		t.formatProteinKeys(laci, method_res+"pk/gener+laci/laci.agni", laci_ec, lg_res+"laci.pk");
		t.formatProteinKeys(g1, method_res+"pk/gener+laci/gener1.agni", g1_ec, lg_res+"gener1.pk");
		t.formatProteinKeys(g2, method_res+"pk/gener+laci/gener2_mix.agni", g2_ec, lg_res+"gener2_mix.pk");
		//sdpclust
		t.formatSDPclust(method_res+"sdpclust8.02.2010/gener+laci/laci_10000iter.sdpclust", 
				laci_ec, lg_res+"laci.sdpc");
		t.formatSDPclust(method_res+"sdpclust8.02.2010/gener+laci/gener1_10000iter.sdpclust", 
				g1_ec, lg_res+"gener1.sdpc");
		t.formatSDPclust(method_res+"sdpclust8.02.2010/gener+laci/gener2_mix_10000iter.sdpclust", 
				g2_ec, lg_res+"gener2_mix.sdpc");
		*/
		
		//t.formatSDPclust();
		t.formatSDPsite();
		//t.formatProteinKeys();
		//t.formatTreeDet(DistanceAnalyser.S);
		//t.formatTreeDet(DistanceAnalyser.S3DET);	
		//t.GiantComp(0.6,"gc06");
		//t.GiantComp(0.5,"gc05");
	}
	
	public void formatSDPclust()throws Exception{
		String in = method_res+"sdpclust2009/multyEC/";
		File[] ec_gr = new File(ECGroupingMaker.grouping_by_ec).listFiles();
		for(File f : ec_gr) {
			System.out.println(f.getName());
			formatSDPclust(in+f.getName()+"_10000iter.sdpclust",f.getAbsolutePath(),grouping_res+f.getName()+"."+
					DistanceAnalyser.EXTENSIONS[DistanceAnalyser.SDPCLUST]);
		}
	}
	
	public void formatSDPsite()throws Exception{
		String in = method_res+"sdps/multyEC/";
		File[] ec_gr = new File(ECGroupingMaker.grouping_by_ec).listFiles(new FileFilter() {			
			public boolean accept(File pathname) {
				return !pathname.isDirectory();
			}
		});
		for(File f : ec_gr) {
			System.out.println(f.getName());
			formatSDPclust(in+f.getName()+".sdpin",f.getAbsolutePath(),grouping_res+f.getName()+"."+
					DistanceAnalyser.EXTENSIONS[DistanceAnalyser.SDPSITE]);
		}
	}
	
	public void formatProteinKeys()throws Exception{
		String in = method_res+"pk/multyEC/";
		File[] ec_gr = new File(ECGroupingMaker.grouping_by_ec).listFiles();
		for(File f : ec_gr) {
			System.out.println(f.getName());
			formatProteinKeys(ECGroupingMaker.multy_dir+f.getName()+".fasta",in+f.getName()+".agni",
					f.getAbsolutePath(),grouping_res+f.getName()+"."+
					DistanceAnalyser.EXTENSIONS[DistanceAnalyser.PROT_KEYS]);
		}
	}
	
	/**
	 * 
	 * @param method 'S' or 'S3DET'
	 * @throws Exception
	 */
	public void formatTreeDet(int method)throws Exception{
		String in = method_res+"treeDet/multyEC/";
		File[] ec_gr = new File(ECGroupingMaker.grouping_by_ec).listFiles();
		for(File f : ec_gr) {
			System.out.println(f.getName());
			File td = new File(in+f.getName()+".xml");
			if(td.exists())
				formatTreeDet(in+f.getName()+".xml",f.getAbsolutePath(),grouping_res+f.getName()+"."+
						DistanceAnalyser.EXTENSIONS[method],
						DistanceAnalyser.METHODS[method].substring(0,DistanceAnalyser.METHODS[method].length()-7));
		}
	}
	
	/**
	 * 
	 * @param treedet_res
	 * @param ec_gr
	 * @param out
	 * @param method 'S' or 'S3DET'
	 * @throws Exception
	 */
	public void formatTreeDet(String treedet_res,String ec_gr,String out,String method)throws Exception {
		HashSet<String> seq_with_ec = new SeqGrouping(ec_gr).getSequences();
		SeqGrouping res = new SeqGrouping();
		BufferedReader in = new BufferedReader(new FileReader(treedet_res));
		String grn = null;
		for(String l = in.readLine();l.indexOf("<method name=\""+method+"\">") == -1;l = in.readLine()) {}
		for(String l = in.readLine();l.indexOf("</method>") == -1;l = in.readLine()) {
			
			int inx = l.indexOf("<cluster name=\"");
			if(inx != -1){
				grn = "cluster"+l.substring(inx+15,l.indexOf('\"', inx+15));
			}
			inx = l.indexOf("<protein name=\"");
			if(inx != -1) {
				String name = l.substring(inx+15,l.indexOf('/',inx+15));
				if(seq_with_ec.contains(name)) {
					res.addSeq(name, grn);
				}
			}
			
		}
		in.close();
		res.save(out);
	}
	
	public void GiantComp(double identity_thr,String ext)throws Exception{
		File[] ec_gr = new File(ECGroupingMaker.grouping_by_ec).listFiles(new FileFilter() {			
			public boolean accept(File pathname) {
				return !pathname.isDirectory();
			}
		});
		for(File f : ec_gr) {
			System.out.println(f.getName());
			formatGiantComp(ECGroupingMaker.multy_dir+f.getName()+".fasta",identity_thr,
					f.getAbsolutePath(),grouping_res+f.getName()+"."+ext);
		}
	}
	
	
	public void formatGiantComp(String in_al,double identity_thr,String ec_gr,String out)throws Exception {
		HashSet<String> seq_with_ec = new SeqGrouping(ec_gr).getSequences();
		SeqGrouping res = new SeqGrouping();
		AlignmentData ad = new AlignmentData(in_al);
		StaticFunction.setGroupingFromConnectingComponents(ad, identity_thr);
		int[][] gr = ad.grouping.getGroupingForAllGroup();
		for(int i=0;i<gr.length;i++) {
			String grn = ad.groupNumerator.getNameForNum(i);
			for(int j=1;j<=gr[i][0];j++) {
				String sn = ad.seqNumerator.getNameForNum(gr[i][j]);
				int end = sn.indexOf("/");
				if(end == -1)
					end = sn.length();
				sn = sn.substring(0,end);
				if(seq_with_ec.contains(sn))
					res.addSeq(sn, grn);
			}
		}
		res.save(out);
	}

	
	public void formatProteinKeys(String in_al,String pk_res,String ec_gr,String out)throws Exception {
		ArrayList<String> snames = ECGroupingMaker.getSeqNames(in_al);
		HashSet<String> seq_with_ec = new SeqGrouping(ec_gr).getSequences();
		SeqGrouping res = new SeqGrouping();
		BufferedReader in = new BufferedReader(new FileReader(pk_res));
		String grn = null;
		for(String l = in.readLine();l!=null;l = in.readLine()) {
			int inx = l.indexOf("<cluster id=\"");
			if(inx != -1){
				grn = "cluster"+l.substring(inx+13,l.indexOf('\"', inx+13));
			}
			inx = l.indexOf("<i r=\"");
			if(inx != -1) {
				int n = Integer.parseInt(l.substring(inx+6,l.indexOf('\"',inx+6)));
				String name = snames.get(n);
				if(seq_with_ec.contains(name)) {
					res.addSeq(name, grn);
				}
			}
		}
		in.close();
		res.save(out);
	}

	public void formatSDPclust(String al,String ec_gr,String out) throws Exception {
		//System.out.println(out);
		HashSet<String> seq_with_ec = null;
		if(ec_gr != null)
			seq_with_ec = new SeqGrouping(ec_gr).getSequences();
		SeqGrouping res = new SeqGrouping();
		BufferedReader in = new BufferedReader(new FileReader(al));
		String gr = "nongrouped";
		for(String l = in.readLine();l != null;l = in.readLine()) {
			if(l.charAt(0) == '=') {
				gr = l.replace("=", "");
			}
			if((l.charAt(0) == '>' || l.charAt(0) == '%') && gr != null) {
				int end = l.indexOf("/");
				if(end == -1)
					end = l.length();
				String n = l.substring(1,end);
				if(seq_with_ec == null || seq_with_ec.contains(n)) {
					res.addSeq(n, gr);
				}
			}
		}
		in.close();
		res.save(out);
	}
}
