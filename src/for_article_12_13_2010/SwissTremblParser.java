package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class SwissTremblParser {
	private static String name_field = "ID";
	private static String pfam_field = "DR   Pfam; ";
	private static String sprot = "/mnt/mysql/mazin_data/uniprot_sprot";
	private static String trembl = "/mnt/mysql/mazin_data/uniprot_trembl.dat";
	
	
	public static void printPfamRes(HashSet<String> seq_names,PrintStream out) throws Exception {
		HashMap<String, HashSet<String>> names_to_pfam = new HashMap<String, HashSet<String>>();
		getPfam(trembl,seq_names,names_to_pfam);
		getPfam(sprot,seq_names,names_to_pfam);
		for(String n : seq_names) {
			HashSet<String> pfam = names_to_pfam.get(n);
			String print;
			if(pfam == null)
				print = "not found";
			else
				print = toString(pfam);
			out.println(n+"\t"+print);
		}
	}
	
	private static String toString(HashSet<String> s) {
		if(s.size() == 0)
			return "no pfam";
		ArrayList<String> t = new ArrayList<String>(s);
		Collections.sort(t);
		String r = "";
		for(String tt : t)
			r += tt +"; "; 
		return r;
	}
	
	public static void getPfam(String fname_db,HashSet<String> seg_names,HashMap<String, HashSet<String>> to) throws Exception{
		BufferedReader br = new BufferedReader(new FileReader(fname_db));
		String cur_name = null;
		int li = 0;
		for(String l = br.readLine();l!=null;l=br.readLine()) {
			if(li % 10000000 == 0)
				System.out.println(li);
			li++;
			if(l.startsWith(name_field)) {
				cur_name = l.split("[\\s\\t]+")[1];
				if(!seg_names.contains(cur_name))
					cur_name = null;
				else if(!to.containsKey(cur_name))
					to.put(cur_name, new HashSet<String>());
				continue;
			}
			if(cur_name == null)
				continue;
			else {
				if(l.contains(pfam_field)) {
					String pfam = l.substring(pfam_field.length(),l.indexOf(';', pfam_field.length()));
					HashSet<String> o = to.get(cur_name);
					o.add(pfam);
					to.put(cur_name, o);
				}
			}
			
		}
		br.close();
	}
}
