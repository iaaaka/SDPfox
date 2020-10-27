package mysqlbase.api;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Hashtable;
import java.util.Vector;

import mysqlbase.objects.OHcluster;

public class Uploader {
	
	public static Vector<String[]> readDescrFile(String f) throws Exception{
		BufferedReader bf = new BufferedReader(new FileReader(f));
		Vector<String[]> result = new Vector<String[]>();
		for(String l = bf.readLine();l != null;l = bf.readLine()){
			result.add(l.split("\t"));
		}
		bf.close();
		return result;
	}
	
	public static String getFileContent(String f) throws Exception {
		BufferedReader bf = new BufferedReader(new FileReader(f));
		StringBuffer result = new StringBuffer();
		for(String l = bf.readLine();l != null;l = bf.readLine()){
			result.append(l).append("\n");
		}
		bf.close();
		return result.toString();
	}
	
	public static void upload(String fndiscr,String aligndir) throws Exception {
		Vector<String[]> ohclusters = readDescrFile(fndiscr);
		for(int i=0;i<ohclusters.size();i++){
			Hashtable<String, String> data = new Hashtable<String, String>();
			data.put(OHcluster.ID, "-1");
			data.put(OHcluster.NAME, ohclusters.get(i)[1]);
			data.put(OHcluster.DESCRIPTION, ohclusters.get(i)[2]);
			data.put(OHcluster.THRESHOLD, ohclusters.get(i)[0].split("#-")[1]);
			data.put(OHcluster.ALIGNMENT, getFileContent(aligndir+ohclusters.get(i)[0]+".afa"));
			data.put(OHcluster.CAMPS_ID, ohclusters.get(i)[0].split("#-")[0]);
			OHcluster ohcl = new OHcluster(data);
			ohcl.save();
		}
	}
	
	public static void main(String[] args) throws Exception {
		MySQLAccess.clean();
		upload("CAMPS/OHCluster_names.txt","CAMPS/");
	}
}
