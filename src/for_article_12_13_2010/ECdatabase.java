package for_article_12_13_2010;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;


public class ECdatabase {
	private HashMap<String, ArrayList<String>> database;
	private HashMap<String, String> primary_to_name;
	private static String db_fname = "/home/mazin/all/laba/SDP/article/24.02.2010/benchmarking/ec numbers/enzyme.dat";
	
	public static void main(String[] args)throws IOException {
		ECdatabase db = new ECdatabase(db_fname);
		db.check();
	}
	
	private void check(){
		System.out.println("Number of unic primary keys: "+primary_to_name.size());
		HashSet<String> names = new HashSet<String>(primary_to_name.values());
		System.out.println("Number of unic names: "+names.size());
		System.out.println("EC count statistics");
		HashMap<Integer, Integer> stat = new HashMap<Integer, Integer>();
		for(ArrayList<String> t : database.values()){
			int s = t.size();
			Integer old = stat.get(s);
			if(old == null){
				old = 0;
			}
			stat.put(s, old+1);	
		}
		int sum = 0;
		for(int n : stat.keySet()){
			System.out.println(n+"\t"+stat.get(n));
			sum += stat.get(n);
		}
		System.out.println("Sum "+sum);
	}
	
	private String[] parseEntry(String e){
		e = e.replaceAll("\\s", "");
		return e.split(",");
	}
	
	public ArrayList<String> getECs(String n){
		ArrayList<String> r = database.get(n);
		if(r == null)
			r = database.get(primary_to_name.get(n));
		if(r == null)
			r = database.get(primary_to_name.get(n.split("_")[0]));
		return r;
	}
	
	public String getEC(String full,int level) {
		level = Math.min(level, 4);
		if(level == 4)
			return full;
		int inx = 0;
		for(int i=0;i<level;i++) {
			inx = full.indexOf('.',inx+1);
		}
		return full.substring(0,inx);
	}
	
	/**
	 * 
	 * @param n
	 * @param level
	 * @param filter first part of EC. null if all
	 * @return
	 */
	public Set<String> getECs(String n,int level,String filter){
		HashSet<String> r = new HashSet<String>();
		ArrayList<String> ecs = getECs(n);
		if(ecs == null)
			return r;
		for(String ec : ecs) {
			if(filter == null || ec.startsWith(filter))
				r.add(getEC(ec,level));
		}
		return r;
	}
	
	public ECdatabase()throws IOException{
		this(db_fname);
	}
	
	public ECdatabase(String fname)throws IOException{
		database = new HashMap<String, ArrayList<String>>();
		primary_to_name = new HashMap<String, String>();
		BufferedReader r = new BufferedReader(new FileReader(fname));
		String id = null;
		for(String l = r.readLine();l != null;l = r.readLine()){
			if(l.startsWith("ID")){
				id = l.split("\\s+")[1];
			}
			if(l.startsWith("DR")){
				l = l.substring(5);
				String[] prots = l.split(";");
				for(String p : prots){
					String[] acc = parseEntry(p);
					String old = primary_to_name.put(acc[0], acc[1]);
					if(old != null && !old.equals(acc[1]))
							throw new RuntimeException("Primary key has more than one names!: "+acc[0]+" ;names: "+old+","+acc[1]);
					if(!database.containsKey(acc[1]))
						database.put(acc[1], new ArrayList<String>());
					database.get(acc[1]).add(id);			
				}
			}
		}
		r.close();
	}
}
