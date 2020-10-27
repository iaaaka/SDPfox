package Run;

import java.io.*;
import java.util.Hashtable;
import Tree.*;
import java.util.*;

import from_olga.activesite.finder;
import Math.StaticFunction;
import Objects.*;
import Util.*;
import painter.*;

public class Tester {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		fasta2pfam("/home/mazin/all/laba/article/24.02.2010/benchmarking/gener+laci/", "/home/mazin/all/laba/article/24.02.2010/benchmarking/gener+laci/pfam_format/");
		//fasta2pfam("/home/mazin/all/laba/article/24.02.2010/benchmarking/pfam_align/singleEC", "/home/mazin/all/laba/article/24.02.2010/benchmarking/pfam_align/pfam_format/singleEC/");
	}
	
	private static void fasta2pfam(String in_d,String out_d)throws Exception{
		for(File f : new File(in_d).listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.endsWith("fasta");
			}
		}))
			fasta2pfam(f,out_d);
	}
	
	private static void fasta2pfam(File f,String out_d) throws Exception {
		System.out.println(f);
		BufferedReader r = new BufferedReader(new FileReader(f));
		ArrayList<String> p = new ArrayList<String>();
		for(String l = r.readLine();l!=null;l = r.readLine()) {
			if(!l.startsWith(">"))
				continue;
			String t = l.substring(1)+"\t   \t"+r.readLine();
			p.add(t);
		}
		r.close();
		PrintStream o = new PrintStream(out_d + f.getName().substring(0,f.getName().length()-6)+".txt");
		for(int i=0;i<p.size();i++) {
			o.print(p.get(i));
			if(i!=p.size()-1)
				o.println();
		}
		o.close();
	}

}
