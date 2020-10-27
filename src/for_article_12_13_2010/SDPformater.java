package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Math.StaticFunction;
import Objects.Alignment;
import Objects.AlignmentData;
import Objects.Project;

public class SDPformater {
	
	public static void main(String[] args) throws Exception {
		transformFromOlgaFormat();
		saveSDPclustRes("sdpclust8.02.2010",DistanceAnalyser.EXTENSIONS[DistanceAnalyser.SDPCLUST]);
		saveSDPclustRes("sdpclust2009",DistanceAnalyser.EXTENSIONS[DistanceAnalyser.SDP_PROFF]);
		transformFromProtKeys(0.12);
		transformFromS3DET();
	}
	
	public static int[] getSDPsFromOlgaFiles(String fname) throws Exception{
		BufferedReader b = new BufferedReader(new FileReader(fname));
		b.readLine();
		b.readLine();
		b.readLine();
		ArrayList<Integer> t = new ArrayList<Integer>();
		for(String l = b.readLine();b!=null;l=b.readLine()){
			if(l.startsWith("//"))
				break;
			if(l.length() == 0)
				continue;
			t.add(Integer.parseInt(l));
		}
		b.close();
		return StaticFunction.toArray(t);
	}
	
	public static void transformFromOlgaFormat()  throws Exception  {
		int[] methods = new int[] {DistanceAnalyser.S,DistanceAnalyser.EVOL_TRACE,
				DistanceAnalyser.MB,DistanceAnalyser.R4S};
		String[] sm = new String[] {"single","multy"};
		for(String t : sm) {
			transformSDPsite(t);
			for(int m : methods)
				transformFromOlgaFormat(m,t);
		}
		//correct PF01014
		changeRefSeq(DistanceAnalyser.SDP_DIR+"singleEC/PF01014.et",DistanceAnalyser.ALIGNMENTS_DIR+"singleEC/PF01014.fasta",
				"URIC_ASPFL/5-134","URIC_ASPFL/141-290",5,141);
		changeRefSeq(DistanceAnalyser.SDP_DIR+"singleEC/PF01014.mb",DistanceAnalyser.ALIGNMENTS_DIR+"singleEC/PF01014.fasta",
				"URIC_ASPFL/5-134","URIC_ASPFL/141-290",5,141);
		changeRefSeq(DistanceAnalyser.SDP_DIR+"singleEC/PF01014.r4s",DistanceAnalyser.ALIGNMENTS_DIR+"singleEC/PF01014.fasta",
				"URIC_ASPFL/5-134","URIC_ASPFL/141-290",5,141);
		changeRefSeq(DistanceAnalyser.SDP_DIR+"singleEC/PF01014.s",DistanceAnalyser.ALIGNMENTS_DIR+"singleEC/PF01014.fasta",
				"URIC_ASPFL/5-134","URIC_ASPFL/141-290",5,141);
		changeRefSeq(DistanceAnalyser.SDP_DIR+"singleEC/PF01014.sdps",DistanceAnalyser.ALIGNMENTS_DIR+"singleEC/PF01014.fasta",
				"URIC_ASPFL/5-134","URIC_ASPFL/141-290",5,141);
	}
	
	
	
	/**
	 * input files contains sequence positions by pdb
	 * @param single_multy
	 * @throws Exception
	 */
	public static void transformSDPsite(String single_multy) throws Exception {
		String ext = DistanceAnalyser.EXTENSIONS[DistanceAnalyser.SDPSITE];
		String dir_from = DistanceAnalyser.SDP_DIR_RAW+ext+"/"+single_multy+"EC/";
		String dir_to = DistanceAnalyser.SDP_DIR+single_multy+"EC/";
		List<InputParameters> inpars = DistanceForLigandStatistics_2009.readInputParameters(DistanceAnalyser.SDP_DIR+single_multy+"_param");
		for(InputParameters p : inpars) {
			try {
				System.out.println(dir_from+p.pfam_name);
				int[] sdps = getSDPsFromSDPsite(dir_from+p.pfam_name,p.seqName);
				Sdps s = new Sdps(DistanceAnalyser.METHODS[DistanceAnalyser.SDPSITE], p.seqName, p.pdb, p.pfam_name, sdps);
				s.save(dir_to+p.pfam_name+"."+ext);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * changes position numbers from one reference sequence to another
	 * @param old_pos
	 * @param old_ref
	 * @param new_ref
	 * @param ad
	 * @return
	 */
	private static int[] changeRefSeq(int[] old_pos,String old_ref,String new_ref,AlignmentData ad,int old_pdb_start,int new_pdb_start) {
		ArrayList<Integer> t = new ArrayList<Integer>();
		int or = ad.seqNumerator.getNumForName(old_ref);
		int nr = ad.seqNumerator.getNumForName(new_ref);
		for(int i=0;i<old_pos.length;i++) {
			int tmp = ad.alignment.getAlignmentPositionForSeqPosition(or, old_pos[i]-old_pdb_start+1);
			if(tmp != -1) {
				tmp = ad.alignment.getReference(nr)[tmp];
				if(tmp != -1)
					t.add(tmp+new_pdb_start-1);
			}
		}
		return StaticFunction.toArray(t);
	}
	
	public static void saveSDPclustRes(String dir,String extension) throws Exception {
		String[] sm = new String[] {"single","multy"};
		for(String single_multy : sm) {
			List<InputParameters> inpars = DistanceForLigandStatistics_2009.readInputParameters(DistanceAnalyser.SDP_DIR+single_multy+"_param");
			for(InputParameters i : inpars) {
				saveSDPclustRes(i,single_multy,dir,extension);
			}
		}
	}
	
	private static void saveSDPclustRes(InputParameters p,String single_multy,String dir,String extension) throws Exception {
		String outfile = DistanceAnalyser.SDP_DIR+single_multy+"EC/"+p.pfam_name+"."+extension;
		Project pr = new Project();
		System.out.println(DistanceAnalyser.SDP_DIR_RAW+
				dir+"/"+single_multy+"EC/"+p.pfam_name+"_10000iter.sdpclust");
		pr.setAlignmentData(DistanceAnalyser.SDP_DIR_RAW+
				dir+"/"+single_multy+"EC/"+p.pfam_name+"_10000iter.sdpclust");
		int[] sdps_a = null;
		if(pr.getGroupCount() >= 3)
			sdps_a = pr.getSDP();
		else
			sdps_a = new int[0];
		Arrays.sort(sdps_a);
		int ref = pr.getSequenceNo(p.seqName);
		ArrayList<Integer> t = new ArrayList<Integer>();
		for(int i=0;i<sdps_a.length;i++) {
			int tmp = pr.getReference(ref)[sdps_a[i]];
			if(tmp != -1)
				t.add(tmp+p.pdbFirst-1);
		}
		new Sdps(DistanceAnalyser.METHODS[DistanceAnalyser.SDPCLUST],
				p.seqName,p.pdb,p.pfam_name,StaticFunction.toArray(t)).save(outfile);
	}
	
	/**
	 * changes position numbers from one reference sequence to another
	 */
	public static void changeRefSeq(String sdps_fname,String align_fname,String old_ref,String new_ref,int old_pdb_start,int new_pdb_start)throws Exception {
		Sdps so = new Sdps(sdps_fname);
		AlignmentData ad = new AlignmentData(align_fname);
		int[] ns = changeRefSeq(so.sdps,old_ref,new_ref,ad, old_pdb_start, new_pdb_start);
		new Sdps(so.method,so.seq_name,so.pdb,so.pfam,ns).save(sdps_fname);
	}
	
	public static int[] getSDPsFromSDPsite(String fname,String seq_name) throws Exception{
		BufferedReader b = new BufferedReader(new FileReader(fname));
		String l = b.readLine();
		if(!l.endsWith(seq_name) && !seq_name.equals("URIC_ASPFL/141-290"))
			throw new Exception("Incorrect sequence name. "+fname+"\nexpected: "+seq_name+"\nobserved: "+l);
		HashSet<Integer> t = new HashSet<Integer>();
		b.readLine();
		b.readLine();
		for(l = b.readLine();l!=null;l = b.readLine()) {
			t.add(Integer.parseInt(l.split("\t")[1]));
		}
		b.close();
		ArrayList<Integer> tmp = new ArrayList<Integer>(t);
		return StaticFunction.toArray(tmp);
	}
	
	/**
	 * 
	 * @param i
	 * @param single_multy
	 * @param percent which part of top consider as SDP. from 0 to 1. 
	 * @throws Exception
	 */
	public static void transformFromProtKeys(InputParameters i,String single_multy,double percent) throws Exception {
		String inf = DistanceAnalyser.SDP_DIR_RAW+DistanceAnalyser.EXTENSIONS[DistanceAnalyser.PROT_KEYS]+"/"+single_multy+"EC/"+i.pfam_name+".csv";
		System.out.println(inf);
		String outf = DistanceAnalyser.SDP_DIR+single_multy+"EC/"+i.pfam_name+"."+DistanceAnalyser.EXTENSIONS[DistanceAnalyser.PROT_KEYS];
		AlignmentData ad = new AlignmentData(DistanceAnalyser.ALIGNMENTS_DIR+single_multy+"EC/"+i.pfam_name+".fasta");
		int ref = ad.seqNumerator.getNumForName(i.seqName);
		BufferedReader b = new BufferedReader(new FileReader(inf));
		ArrayList<Integer> all_pos = new ArrayList<Integer>();
		for(String l = b.readLine(); l!= null && l.length() != 0; l = b.readLine()) {
			all_pos.add(Integer.parseInt(l.split(",")[1]));
		}
		ArrayList<Integer> sdps = new ArrayList<Integer>();
		for(int j=0;j<all_pos.size()*percent;j++) {
			int tmp = ad.alignment.getReference(ref)[all_pos.get(j)];
			if(tmp!=-1)
				sdps.add(tmp+i.pdbFirst-1);
		}
		b.close();
		new Sdps(DistanceAnalyser.METHODS[DistanceAnalyser.PROT_KEYS],
				i.seqName,i.pdb,i.pfam_name,StaticFunction.toArray(sdps)).save(outf);
	}
	
	public static void transformFromS3DET(InputParameters i,String single_multy) throws Exception {
		String inf = DistanceAnalyser.SDP_DIR_RAW+"treeDet/"+single_multy+"EC/"+i.pfam_name+".xml";
		System.out.println(inf);
		String outf = DistanceAnalyser.SDP_DIR+single_multy+"EC/"+i.pfam_name+"."+DistanceAnalyser.EXTENSIONS[DistanceAnalyser.S3DET];
		AlignmentData ad = new AlignmentData(DistanceAnalyser.ALIGNMENTS_DIR+single_multy+"EC/"+i.pfam_name+".fasta");
		int ref = ad.seqNumerator.getNumForName(i.seqName);
		File infile = new File(inf);
		ArrayList<Integer> sdps = new ArrayList<Integer>();
		if(infile.exists()) {
			BufferedReader b = new BufferedReader(new FileReader(inf));
			for(String l = b.readLine();l != null && !l.contains("<method name=\"S3DET\">");l = b.readLine()) {}
			for(String l = b.readLine();l != null && !l.contains("<residue_list>");l = b.readLine()) {}
			for(String l = b.readLine();l != null && !l.contains("</residue_list>");l = b.readLine()) {
				int tmp = Integer.parseInt(l.substring(l.indexOf("\"")+1,l.indexOf("\"", l.indexOf("\"")+1)))-1;
				tmp = ad.alignment.getReference(ref)[tmp];
				if(tmp != -1)
					sdps.add(tmp + i.pdbFirst-1);
			}
			b.close();
		}
		new Sdps(DistanceAnalyser.METHODS[DistanceAnalyser.S3DET],
				i.seqName,i.pdb,i.pfam_name,StaticFunction.toArray(sdps)).save(outf);
	}
	
	public static void transformFromS3DET() throws Exception {
		String[] sm = new String[] {"single","multy"};
		for(String single_multy : sm) {
			List<InputParameters> inpars = DistanceForLigandStatistics_2009.readInputParameters(DistanceAnalyser.SDP_DIR+single_multy+"_param");
			for(InputParameters i : inpars) {
				transformFromS3DET(i,single_multy);
			}
		}
	}
	
	public static void transformFromProtKeys(double percent) throws Exception {
		String[] sm = new String[] {"single","multy"};
		for(String single_multy : sm) {
			List<InputParameters> inpars = DistanceForLigandStatistics_2009.readInputParameters(DistanceAnalyser.SDP_DIR+single_multy+"_param");
			for(InputParameters i : inpars) {
				transformFromProtKeys(i,single_multy,percent);
			}
		}
	}
	
	/**
	 * input files contains sequence positions by pdb
	 * @param method
	 * @param single_multy
	 * @throws Exception
	 */
	public static void transformFromOlgaFormat(int method,String single_multy) throws Exception {
		String dir_from = DistanceAnalyser.SDP_DIR_RAW+DistanceAnalyser.EXTENSIONS[method]+"/"+single_multy+"EC/";
		String dir_to = DistanceAnalyser.SDP_DIR+single_multy+"EC/";
		List<InputParameters> inpars = DistanceForLigandStatistics_2009.readInputParameters(DistanceAnalyser.SDP_DIR+single_multy+"_param");
		for(InputParameters p : inpars) {
			System.out.println(dir_from+p.pfam_name);
			int[] sdps = getSDPsFromOlgaFiles(dir_from+p.pfam_name);
			Sdps s = new Sdps(DistanceAnalyser.METHODS[method], p.seqName, p.pdb, p.pfam_name, sdps);
			s.save(dir_to+p.pfam_name+"."+DistanceAnalyser.EXTENSIONS[method]);
		}
	}
	
}
