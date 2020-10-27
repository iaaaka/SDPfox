package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Hashtable;

import Math.StaticFunction;


public class Sdps {
	public final String method;
	public final String seq_name;
	public final String pdb;
	public final String pfam;
	public final int[] sdps;
	
	public Sdps(String method,String seq_name,String pdb,String pfam,int[] sdps) {
		this.method = method;
		this.seq_name = seq_name;
		this.pdb = pdb;
		this.pfam = pfam;
		this.sdps = sdps;
	}
	
	public Sdps(String fname) throws IOException {
		BufferedReader bf = new BufferedReader(new FileReader(fname));
		Hashtable<String,String> p = new Hashtable<String,String>();
		int[] tmp = null;
		for(String l = bf.readLine();l!=null;l = bf.readLine()) {
			if(l.startsWith("//"))
				continue;
			if(l.startsWith("#")) {
				String[] t = l.substring(1).split("=");
				p.put(t[0], t[1]);
			}else {
				tmp = StaticFunction.toIntArray(l, ",");
			}
		}
		bf.close();
		sdps = tmp;
		method = p.get("method");
		seq_name = p.get("seq_name");
		pdb = p.get("pdb");
		pfam = p.get("pfam");
	}
	
	public void save(String fname)throws IOException {
		PrintStream ps = new PrintStream(fname);
		ps.println("//SDPs for PDB analysis. Numbering by pdb.");
		ps.println("#method="+method);
		ps.println("#seq_name="+seq_name);
		ps.println("#pdb="+pdb);
		ps.println("#pfam="+pfam);
		ps.println(StaticFunction.join(sdps, ","));
		ps.close();
	}
}
