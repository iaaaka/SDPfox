package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class SeqGrouping {
	private HashMap<String, String> name_to_group;
	
	public SeqGrouping() {
		name_to_group = new HashMap<String, String>();
	}
	
	public SeqGrouping(String fname) throws Exception {
		this();
		BufferedReader in = new BufferedReader(new FileReader(fname));
		for(String gr=in.readLine();gr!=null;gr=in.readLine()) {
			String[] names = in.readLine().split(";");
			for(String n : names) {
				name_to_group.put(n, gr);
			}
		}
		in.close();		
	}
	
	public void addSeq(String sn,String gn) {
		name_to_group.put(sn, gn);
	}
	
	public HashSet<String> getSequences(){
		return new HashSet<String>(name_to_group.keySet());
	}
	
	public HashSet<String> getGroups(){
		return new HashSet<String>(name_to_group.values());
	}
	
	public String getGroup(String sn) {
		return name_to_group.get(sn);
	}
	
	public String toString() {
		String res = "";
		for(String n : name_to_group.keySet()) {
			res+= n+"\t"+name_to_group.get(n)+"\n";
		}
		return res;
	}
	
	public void save(String fname) throws Exception {
		PrintStream out = new PrintStream(fname);
		HashSet<String> grs = new HashSet<String>(name_to_group.values());
		for(String gr : grs) {
			if(gr.equals("nongrouped"))
				System.out.println("nongr: "+fname.substring(fname.lastIndexOf("/")+1));
			out.println(gr);
			String r = "";
			for(String n : name_to_group.keySet()) {
				if(name_to_group.get(n).equals(gr)) {
					r+=n +";";
				}
			};
			r = r.substring(0,r.length()-1);
			out.println(r);
		}
		out.close();
	}
}
