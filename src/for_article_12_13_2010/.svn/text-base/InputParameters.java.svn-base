package for_article_12_13_2010;

import java.util.StringTokenizer;
import java.util.Vector;

import Math.StaticFunction;

public class InputParameters {
	public String pfam_name;
	public int[] missedresidues = null;
	public int[] ligands;
	public String chain1;
	public String chain2;
	public int startindex;
	public String seqName;
	public String pdb;
	public int pdbFirst;
	public int pdbLast;
	
	public InputParameters(String n, String lig, String ch1, String ch2,
			String sti, String sN, String pdb, String pdbF, String pdbS,String missRes) {
		pfam_name = n;
		StringTokenizer st = new StringTokenizer(lig, " \n,");
		Vector<Integer> ligs = new Vector<Integer>();
		for (; st.hasMoreTokens();) {
			ligs.add(Integer.parseInt(st.nextToken()));
		}
		ligands = new int[ligs.size()];
		for (int i = 0; i < ligands.length; i++) {
			ligands[i] = ligs.get(i);
		}
		chain1 = ch1.trim();
		chain2 = ch2.trim();
		startindex = Integer.parseInt(sti);
		seqName = sN.trim();
		this.pdb = pdb.trim();
		pdbFirst = Integer.parseInt(pdbF);
		pdbLast = Integer.parseInt(pdbS);
		missedresidues = StaticFunction.parseStringToIntArray(missRes);
	}
}
