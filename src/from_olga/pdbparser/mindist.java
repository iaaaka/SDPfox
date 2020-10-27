package from_olga.pdbparser;

import java.io.*;
import java.util.*;

/**
 * <p>Title: PDB parser</p>
 * <p>Description: Parser of PDB files</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class mindist {
  public mindist() {
  }
  
 /**
  * 
  * @param fname arguments as for main method of this class. 
  * @return AminoAcidSet whole, AminoAcidSet sdp, AminoAcidSet ligand, Structure s
  */
  public static Object[] parseInputFile(String[] args){
//    args = new String[1];
	    int mod = 0, from = 0, to = 0, line = 0;
	    char chain;
	    AminoAcidSet set1, set2, whole;
//	    args[0] = "in";

//	 input file format:
//	 first line: PDB file name
//	 second line: number of the model of the first set
//	 third line: name of the chain of the first set (if any, otherwise nothing) (if no chain it's hetero atom!)
//	 next lines: either residue numbers in format "from - to (including to!!)" or one by one each in separate line ended with "//" in a separate line
//	 the same for set2

	    a_filereader f = new a_filereader(args[0]);
	    Structure struct = new Structure((String)(f.data.elementAt(line)));
	    line++;
	 //========read structure
	    if(struct == null) {
	      System.err.println("structure not read");
	      System.exit(0);
	    }
//	=========read set1
	    try {
	      mod = Integer.parseInt((String)(f.data.elementAt(line))); line++;
	    } catch (NumberFormatException e) {
	      System.out.println("Wrong first set model number format: " + (String)(f.data.elementAt(line)));
	    }
	    if(((String)(f.data.elementAt(line))).length() > 0) chain = ((String)(f.data.elementAt(line))).charAt(0);
	    else chain = ' ';
	    line++;
	    String[] from_to = ((String)(f.data.elementAt(line))).split("\\s");
	    if((from_to.length > 1) && (from_to[1].equals("-"))) {
	      try {
	        from = Integer.parseInt(from_to[0]);
	        to = Integer.parseInt(from_to[2]);
	      }
	      catch (NumberFormatException e) {
	        System.out.println("Wrong from or to number format: " +
	                           (String) (f.data.elementAt(line)));
	      }
	      set1 = new AminoAcidSet(mod, chain, from, to + 1);
	      line++;
	    } else {
	      int setSize = 0;
	      while(!((String)(f.data.elementAt(line + setSize))).equals("//")) setSize++;
	      int[] aas = new int [setSize];
	      for(int i = 0; i < setSize; i++)
	        try {
	          aas[i] = Integer.parseInt((String)(f.data.elementAt(line + i)));
	        } catch (NumberFormatException e) {
	          System.out.println("Wrong first set aa number format: " + (String)(f.data.elementAt(line + i)));
	        }
	      set1 = new AminoAcidSet(mod, chain, aas);
	      line += (setSize + 1);
	    }
//	============read whole chain (to calculate significance afterwards)    
	    int iter = 0;
	    while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
	        model))).molecules).elementAt(iter))).type.equals("chain")) &&
	         ( (Chain) ( ( ( (Model) (struct.models.elementAt(set1.
	        model))).molecules).elementAt(iter))).name.equals(chain +
	        ""))) iter++;
	    int[] all = new int [((Chain) ((( (Model) (struct.models.
	            elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.size()];
	    for(int i = 0; i < all.length; i++) {
	    	all[i] = ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	                elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
	                elementAt(i))).number;
	    }
	    whole = new AminoAcidSet(0, chain, all);
//	============read set2
	    try {
	      mod = Integer.parseInt((String)(f.data.elementAt(line))); line++;
	    } catch (NumberFormatException e) {
	      System.out.println("Wrong second set model number format: " + (String)(f.data.elementAt(line)));
	    }
	    if(((String)(f.data.elementAt(line))).length() > 0) chain = ((String)(f.data.elementAt(line))).charAt(0);
	    else chain = ' ';
	    line++;
	    from_to = ((String)(f.data.elementAt(line))).split("\\s");
	    if((from_to.length > 1) && (from_to[1].equals("-"))) {
	      try {
	        from = Integer.parseInt(from_to[0]);
	        to = Integer.parseInt(from_to[2]);
	      }
	      catch (NumberFormatException e) {
	        System.out.println("Wrong from or to number format: " +
	                           (String) (f.data.elementAt(line)));
	      }
	      set2 = new AminoAcidSet(mod, chain, from, to + 1);
	      line++;
	    } else {
	      int setSize = 0;
	      while(!((String)(f.data.elementAt(line + setSize))).equals("//")) setSize++;
	      int[] aas = new int [setSize];
	      for(int i = 0; i < setSize; i++)
	        try {
	          aas[i] = Integer.parseInt((String)(f.data.elementAt(line + i)));
	        } catch (NumberFormatException e) {
	          System.out.println("Wrong first set aa number format: " + (String)(f.data.elementAt(line + i)));
	        }
	      set2 = new AminoAcidSet(mod, chain, aas);
	      line += (setSize + 1);
	    }
	    return new Object[]{whole,set1,set2,struct};
  }
  
  public static void main(String[] args) {
//    args = new String[1];
    int mod = 0, from = 0, to = 0, line = 0;
    char chain;
    AminoAcidSet set1, set2, whole;
//    args[0] = "in";

// input file format:
// first line: PDB file name
// second line: number of the model of the first set
// third line: name of the chain of the first set (if any, otherwise nothing) (if no chain it's hetero atom!)
// next lines: either residue numbers in format "from - to (including to!!)" or one by one each in separate line ended with "//" in a separate line
// the same for set2

    a_filereader f = new a_filereader(args[0]);
    Structure struct = new Structure((String)(f.data.elementAt(line)));
    line++;
 //========read structure
    if(struct == null) {
      System.err.println("structure not read");
      System.exit(0);
    }
//=========read set1
    try {
      mod = Integer.parseInt((String)(f.data.elementAt(line))); line++;
    } catch (NumberFormatException e) {
      System.out.println("Wrong first set model number format: " + (String)(f.data.elementAt(line)));
    }
    if(((String)(f.data.elementAt(line))).length() > 0) chain = ((String)(f.data.elementAt(line))).charAt(0);
    else chain = ' ';
    line++;
    String[] from_to = ((String)(f.data.elementAt(line))).split("\\s");
    if((from_to.length > 1) && (from_to[1].equals("-"))) {
      try {
        from = Integer.parseInt(from_to[0]);
        to = Integer.parseInt(from_to[2]);
      }
      catch (NumberFormatException e) {
        System.out.println("Wrong from or to number format: " +
                           (String) (f.data.elementAt(line)));
      }
      set1 = new AminoAcidSet(mod, chain, from, to + 1);
      line++;
    } else {
      int setSize = 0;
      while(!((String)(f.data.elementAt(line + setSize))).equals("//")) setSize++;
      int[] aas = new int [setSize];
      for(int i = 0; i < setSize; i++)
        try {
          aas[i] = Integer.parseInt((String)(f.data.elementAt(line + i)));
        } catch (NumberFormatException e) {
          System.out.println("Wrong first set aa number format: " + (String)(f.data.elementAt(line + i)));
        }
      set1 = new AminoAcidSet(mod, chain, aas);
      line += (setSize + 1);
    }
//============read whole chain (to calculate significance afterwards)    
    int iter = 0;
    while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
        model))).molecules).elementAt(iter))).type.equals("chain")) &&
         ( (Chain) ( ( ( (Model) (struct.models.elementAt(set1.
        model))).molecules).elementAt(iter))).name.equals(chain +
        ""))) iter++;
    int[] all = new int [((Chain) ((( (Model) (struct.models.
            elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.size()];
    for(int i = 0; i < all.length; i++) {
    	all[i] = ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
                elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
                elementAt(i))).number;
    }
    whole = new AminoAcidSet(0, chain, all);
//============read set2
    try {
      mod = Integer.parseInt((String)(f.data.elementAt(line))); line++;
    } catch (NumberFormatException e) {
      System.out.println("Wrong second set model number format: " + (String)(f.data.elementAt(line)));
    }
    if(((String)(f.data.elementAt(line))).length() > 0) chain = ((String)(f.data.elementAt(line))).charAt(0);
    else chain = ' ';
    line++;
    from_to = ((String)(f.data.elementAt(line))).split("\\s");
    if((from_to.length > 1) && (from_to[1].equals("-"))) {
      try {
        from = Integer.parseInt(from_to[0]);
        to = Integer.parseInt(from_to[2]);
      }
      catch (NumberFormatException e) {
        System.out.println("Wrong from or to number format: " +
                           (String) (f.data.elementAt(line)));
      }
      set2 = new AminoAcidSet(mod, chain, from, to + 1);
      line++;
    } else {
      int setSize = 0;
      while(!((String)(f.data.elementAt(line + setSize))).equals("//")) setSize++;
      int[] aas = new int [setSize];
      for(int i = 0; i < setSize; i++)
        try {
          aas[i] = Integer.parseInt((String)(f.data.elementAt(line + i)));
        } catch (NumberFormatException e) {
          System.out.println("Wrong first set aa number format: " + (String)(f.data.elementAt(line + i)));
        }
      set2 = new AminoAcidSet(mod, chain, aas);
      line += (setSize + 1);
    }
//============test reading
    System.out.println("set1 model " + set1.model);
    System.out.println("set2 model " + set2.model);
    System.out.println("set1 chain " + set1.chain);
    System.out.println("set2 chain " + set2.chain);
    for(int i = 0; i < set1.numbers.length; i++) System.out.print(" " + set1.numbers[i]);
    System.out.println("");
    for(int i = 0; i < set2.numbers.length; i++) System.out.print(" " + set2.numbers[i]);
    System.out.println("");

//============working
//    Distance[] dist = new Distance[set1.numbers.length * set2.numbers.length];
//    dist = calcMinDist(struct, set1, set2);
//    out(args[1], struct.title, set1, set2, dist);
    double[] features = calcDistFeatures(struct, set1, set2);
    outFeatures(args[1], struct.title, features);
    double[] sign = calcSignificance(struct, whole, set2, features[0], features[4], features[1]);
    outSign(args[1], struct.title, sign);

//    System.out.println("title = " + struct.title);
//    System.out.println("consists of " + struct.models.size() + " models");
//    System.out.println("model 1 consists of " + ((Model)(struct.models.elementAt(0))).molecules.size() + " different molecules");
//    System.out.println("Water in the model 1:");
//    for(int i = 0; i < ((Model)(struct.models.elementAt(0))).molecules.size(); i++)
//      if(((Molecule)((Model)(struct.models.elementAt(0))).molecules.elementAt(i)).name.equals("HOH"))
//        System.out.println("mol number = " + ((Hetero)((Model)(struct.models.elementAt(0))).molecules.elementAt(i)).num);
//    System.out.println("the second atom has coordinates:");
//    System.out.println("   x = " + ((Atom)(((AminoAcid)(((Chain)(((Model)(struct.models.elementAt(0))).molecules.elementAt(0))).aminoacids.elementAt(0))).atoms.elementAt(1))).x);
//    System.out.println("   y = " + ((Atom)(((AminoAcid)(((Chain)(((Model)(struct.models.elementAt(0))).molecules.elementAt(0))).aminoacids.elementAt(0))).atoms.elementAt(1))).y);
//    System.out.println("   z = " + ((Atom)(((AminoAcid)(((Chain)(((Model)(struct.models.elementAt(0))).molecules.elementAt(0))).aminoacids.elementAt(0))).atoms.elementAt(1))).z);
//    System.out.println("   temperature factor = " + ((Atom)(((AminoAcid)(((Chain)(((Model)(struct.models.elementAt(0))).molecules.elementAt(0))).aminoacids.elementAt(0))).atoms.elementAt(1))).temp);
//    System.out.println("   nomenclature = " + ((Atom)(((AminoAcid)(((Chain)(((Model)(struct.models.elementAt(0))).molecules.elementAt(0))).aminoacids.elementAt(0))).atoms.elementAt(1))).nomen);
//    System.out.println("   and identity = " + ((Atom)(((AminoAcid)(((Chain)(((Model)(struct.models.elementAt(0))).molecules.elementAt(0))).aminoacids.elementAt(0))).atoms.elementAt(1))).identity);
  }

  public static Distance[] calcMinDist(Structure struct, AminoAcidSet set1, AminoAcidSet set2) {
	    Distance[] result = new Distance[set1.numbers.length * set2.numbers.length];
	    for(int i = 0; i < set1.numbers.length * set2.numbers.length; i++) result[i] = new Distance();
	    double min;
	    String atom1_min = new String();
	    String atom2_min = new String();
	    for(int i = 0; i < set1.numbers.length; i++)
	      for(int j = 0; j < set2.numbers.length; j++) {
//	    	  int set2i = set2.numbers[i];
	        min = 10000;
	        atom1_min = "";
	        atom2_min = "";
	        AminoAcid first = new AminoAcid();
	        if (set1.chain != ' ') {
	          int iter = 0;
	          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules).elementAt(iter))).type.equals("chain")) &&
	               ( (Chain) ( ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules).elementAt(iter))).name.equals(set1.chain +
	              ""))) iter++;
	          int iter2 = 0;
	          while ((iter2 < ( (Chain) ( ( ( (Model) (struct.models.
	                  elementAt(set1.model))).molecules).elementAt(iter))).
	                  aminoacids.size()) && ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
	                                 elementAt(iter2))).number != set1.numbers[i])) {
	        	  iter2++;
	          }    
	          if(iter2 == ( (Chain) ( ( ( (Model) (struct.models.
	                  elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.size()) { first.number = -1; }
	          else {
	        	  first.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	        			  elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
	                                      elementAt(iter2)));
	          }
	        }
	        else {
	          int iter = 0;
	          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
	                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.model))).
	                              molecules.elementAt(iter))).the.number ==
	                 set1.numbers[i]))) iter++;
	          first.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules.elementAt(iter))).the);
	        }
	        AminoAcid second = new AminoAcid();
	        if (set2.chain != ' ') {
	          int iter = 0;
	          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set2.
	              model))).molecules).elementAt(iter))).type.equals("chain")) &&
	               ( (Chain) ( ( ( (Model) (struct.models.elementAt(set2.
	              model))).molecules).elementAt(iter))).name.equals(set2.chain +
	              ""))) iter++;
	          int iter2 = 0;
	          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(set2.model))).molecules).elementAt(iter))).aminoacids.
	                                 elementAt(iter2))).number != set2.numbers[j])
	              iter2++;
	          second.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.
	              models.elementAt(set2.model))).molecules).elementAt(iter))).
	                                       aminoacids.elementAt(iter2)));
	        }
	        else {
	          int iter = 0;
	          //System.out.println(set2.numbers[j]);
	          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(set2.model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
	                     ( ( (Hetero) (( ( (Model) (struct.models.elementAt(set2.model))).
	                                  molecules).elementAt(iter))).the.number ==
	                     set2.numbers[j]))) {
	        	  iter++;
	        	 // System.out.println(( (Hetero) (( ( (Model) (struct.models.elementAt(set2.model))).
	                     //             molecules).elementAt(iter))).the.number);
	        	  //System.out.println(iter);
	          }
	              second.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(set2.
	                  model))).molecules.elementAt(iter))).the);
	        }
	        if(first.number >= 0) {
	        	for (int ii = 0; ii < first.atoms.size(); ii++)
	        		for(int jj = 0; jj < second.atoms.size(); jj++) {
	        			double d = dist(((Atom)(first.atoms.elementAt(ii))), ((Atom)(second.atoms.elementAt(jj))));
	        			if(d < min) {
	        			min = d;
	        			atom1_min = ((Atom)(first.atoms.elementAt(ii))).nomen;
	        			atom2_min = ((Atom)(second.atoms.elementAt(jj))).nomen;
	        		}
	        	}
	        	result[j * set1.numbers.length + i].dist = min;
	        	result[j * set1.numbers.length + i].atom1 = atom1_min;
	        	result[j * set1.numbers.length + i].atom2 = atom2_min;
	        } else {
	        	result[j * set1.numbers.length + i].dist = 10000;
	        }
	    }
	    return result;
	  }

  public static double[] calcDistFeatures(Structure struct, AminoAcidSet set1, AminoAcidSet set2) {
	    double min = 10000.;
	    double average = 0.; int avCount = 0;
	    double diam = 0.;
	    double avPair = 0.; int avPairCount = 0;
	    double maxDist = 0.;
	    double[] res = new double[5];
	    for(int i = 0; i < set1.numbers.length; i++) {
          double pairMin = 10000.;
	      for(int j = 0; j < set2.numbers.length; j++) {
	        AminoAcid first = new AminoAcid();
	        if (set1.chain != ' ') {
	          int iter = 0;
	          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules).elementAt(iter))).type.equals("chain")) &&
	               ( (Chain) ( ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules).elementAt(iter))).name.equals(set1.chain +
	              ""))) iter++;
	          int iter2 = 0;
	          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
	                                 elementAt(iter2))).number != set1.numbers[i]) {
	              iter2++;
	              if(iter2 >= ( (Chain) ( ( ( (Model) (struct.models.
	    	              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.size()) {
	            	  System.out.println("Abortion at " + set1.numbers[i]); System.exit(0);
	              }
	          }    
	          first.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
	                                      elementAt(iter2)));
	        }
	        else {
	          int iter = 0;
	          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
	                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.model))).
	                              molecules.elementAt(iter))).the.number ==
	                 set1.numbers[i]))) iter++;
	          first.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.
	              model))).molecules.elementAt(iter))).the);
	        }
	        AminoAcid second = new AminoAcid();
	        if (set2.chain != ' ') {
	          int iter = 0;
	          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set2.
	              model))).molecules).elementAt(iter))).type.equals("chain")) &&
	               ( (Chain) ( ( ( (Model) (struct.models.elementAt(set2.
	              model))).molecules).elementAt(iter))).name.equals(set2.chain +
	              ""))) iter++;
	          int iter2 = 0;
	          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(set2.model))).molecules).elementAt(iter))).aminoacids.
	                                 elementAt(iter2))).number != set2.numbers[j])
	              iter2++;
	          second.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.
	              models.elementAt(set2.model))).molecules).elementAt(iter))).
	                                       aminoacids.elementAt(iter2)));
	        }
	        else {
	          int iter = 0;
	          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(set2.
	              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
	                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(set2.model))).
	                              molecules.elementAt(iter))).the.number ==
	                 set2.numbers[j]))) iter++;
	          second.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(set2.
	              model))).molecules.elementAt(iter))).the);
	        }
	        for (int ii = 0; ii < first.atoms.size(); ii++)
	          for(int jj = 0; jj < second.atoms.size(); jj++) {
	            double d = dist(((Atom)(first.atoms.elementAt(ii))), ((Atom)(second.atoms.elementAt(jj))));
	            if(d < min) {
	              min = d;
	            }
	            if(d < pairMin) {
		              pairMin = d;
		            }
	            if(d > maxDist) {
	            	maxDist = d;
	            }
//System.out.println("pairMin = " + pairMin + " d = " + d + " first = " + first.name + "" + first.number + " second = " + second.name + "" + second.number);	        
	          }
	    }
          average += pairMin;
          avCount++;
	    }
	    for(int i = 0; i < set1.numbers.length; i++)
		      for(int j = 0; j < set1.numbers.length; j++) {
		        AminoAcid first = new AminoAcid();
		        double pairMin = 10000.;
		        if (set1.chain != ' ') {
		          int iter = 0;
		          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules).elementAt(iter))).type.equals("chain")) &&
		               ( (Chain) ( ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules).elementAt(iter))).name.equals(set1.chain +
		              ""))) iter++;
		          int iter2 = 0;
		          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
		              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
		                                 elementAt(iter2))).number != set1.numbers[i])
		              iter2++;
		          first.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
		              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
		                                      elementAt(iter2)));
		        }
		        else {
		          int iter = 0;
		          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
		                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.model))).
		                              molecules.elementAt(iter))).the.number ==
		                 set1.numbers[i]))) iter++;
		          first.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules.elementAt(iter))).the);
		        }
		        AminoAcid second = new AminoAcid();
		        if (set1.chain != ' ') {
		          int iter = 0;
		          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules).elementAt(iter))).type.equals("chain")) &&
		               ( (Chain) ( ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules).elementAt(iter))).name.equals(set1.chain +
		              ""))) iter++;
		          int iter2 = 0;
		          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
		              elementAt(set1.model))).molecules).elementAt(iter))).aminoacids.
		                                 elementAt(iter2))).number != set1.numbers[j])
		              iter2++;
		          second.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.
		              models.elementAt(set1.model))).molecules).elementAt(iter))).
		                                       aminoacids.elementAt(iter2)));
		        }
		        else {
		          int iter = 0;
		          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
		                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.model))).
		                              molecules.elementAt(iter))).the.number ==
		                 set1.numbers[i]))) iter++;
		          second.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(set1.
		              model))).molecules.elementAt(iter))).the);
		        }
		        for (int ii = 0; ii < first.atoms.size(); ii++)
		          for(int jj = 0; jj < second.atoms.size(); jj++) {
		            double d = dist(((Atom)(first.atoms.elementAt(ii))), ((Atom)(second.atoms.elementAt(jj))));
		            if(d > diam) {
		              diam = d;
		            }
		            if(d < pairMin) {
		            	pairMin = d;
		            }
		          }
	            avPair += pairMin;
	            if(pairMin > 0) avPairCount++;
		    }
	    res[0] = min;
	    res[1] = average / avCount;
	    res[2] = diam;
	    res[3] = avPair / avPairCount;
	    res[4] = maxDist;
	    return res;
	  }
  
  public static double[] calcSignificance(Structure struct, AminoAcidSet whole, AminoAcidSet ligand, double minDist, double maxDist, double avDist) {
	    double[] res = new double[3];
	    int minCounter = 0, maxCounter = 0, allCounter = 0, avCounter = 0;
	    for(int i = 0; i < whole.numbers.length; i++)
	      for(int j = 0; j < ligand.numbers.length; j++) {
	        AminoAcid first = new AminoAcid();
	        if (whole.chain != ' ') {
	          int iter = 0;
	          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(whole.
	              model))).molecules).elementAt(iter))).type.equals("chain")) &&
	               ( (Chain) ( ( ( (Model) (struct.models.elementAt(whole.
	              model))).molecules).elementAt(iter))).name.equals(whole.chain +
	              ""))) iter++;
	          int iter2 = 0;
	          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(whole.model))).molecules).elementAt(iter))).aminoacids.
	                                 elementAt(iter2))).number != whole.numbers[i]) {
	              iter2++;
	              if(iter2 >= ( (Chain) ( ( ( (Model) (struct.models.
	    	              elementAt(whole.model))).molecules).elementAt(iter))).aminoacids.size()) {
	            	  System.out.println("Abortion at " + whole.numbers[i]); System.exit(0);
	              }
	          }    
	          first.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(whole.model))).molecules).elementAt(iter))).aminoacids.
	                                      elementAt(iter2)));
	        }
	        else {
	          int iter = 0;
	          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(whole.
	              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
	                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(whole.model))).
	                              molecules.elementAt(iter))).the.number ==
	                            	  whole.numbers[i]))) iter++;
	          first.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(whole.
	              model))).molecules.elementAt(iter))).the);
	        }
	        AminoAcid second = new AminoAcid();
	        if (ligand.chain != ' ') {
	          int iter = 0;
	          while (!((((Molecule)( ( ( (Model) (struct.models.elementAt(ligand.
	              model))).molecules).elementAt(iter))).type.equals("chain")) &&
	               ( (Chain) ( ( ( (Model) (struct.models.elementAt(ligand.
	              model))).molecules).elementAt(iter))).name.equals(ligand.chain +
	              ""))) iter++;
	          int iter2 = 0;
	          while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.models.
	              elementAt(ligand.model))).molecules).elementAt(iter))).aminoacids.
	                                 elementAt(iter2))).number != ligand.numbers[j])
	              iter2++;
	          second.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (struct.
	              models.elementAt(ligand.model))).molecules).elementAt(iter))).
	                                       aminoacids.elementAt(iter2)));
	        }
	        else {
	          int iter = 0;
	          while (!(((((Molecule)( ( ( (Model) (struct.models.elementAt(ligand.
	              model))).molecules).elementAt(iter))).type.equals("hetero"))) &&
	                 ( ( (Hetero) ( ( (Model) (struct.models.elementAt(ligand.model))).
	                              molecules.elementAt(iter))).the.number ==
	                            	  ligand.numbers[j]))) iter++;
	          second.copy( ( (Hetero) ( ( (Model) (struct.models.elementAt(ligand.
	              model))).molecules.elementAt(iter))).the);
	        }
	        for (int ii = 0; ii < first.atoms.size(); ii++)
	          for(int jj = 0; jj < second.atoms.size(); jj++) {
	            double d = dist(((Atom)(first.atoms.elementAt(ii))), ((Atom)(second.atoms.elementAt(jj))));
//System.out.println("d = " + d + " minDist = " + minDist + " maxDist = " + maxDist);	            
	            if(d < minDist) {
	              minCounter++;
	            }
	            if(d < maxDist) {
		          maxCounter++;
		        }
	            if(d < avDist) {
			          avCounter++;
			        }
	            allCounter++;
//System.out.println("minCounter = " + minCounter + " maxCounter = " + maxCounter + " allCounter = " + allCounter);	            
	          }
	    }
        res[0] = (double)minCounter / (double)allCounter;
        res[1] = (double)maxCounter / (double)allCounter;
        res[2] = (double)avCounter / (double)allCounter;
	    return res;
	  }

  private static double dist(Atom a1, Atom a2) {
    return Math.sqrt((a1.x - a2.x) * (a1.x - a2.x) + (a1.y - a2.y) * (a1.y - a2.y) + (a1.z - a2.z) * (a1.z - a2.z));
  }

  public static void out(String outName, String structTitle, AminoAcidSet set1, AminoAcidSet set2, Distance[] dist) {
    try{
      OutputStream f = new FileOutputStream(outName);
      a_filewriter.Write(f, structTitle + "\n");
      a_filewriter.Write(f, "Set 1: model " + set1.model + ", chain " + set1.chain + "\n");
      a_filewriter.Write(f, "Set 2: model " + set2.model + ", chain " + set2.chain + "\n");
      a_filewriter.Write(f, "Distances:\n");
      a_filewriter.Write(f, "\t");
//      for(int i = 0; i < set1.numbers.length; i++) a_filewriter.Write(f, set1.numbers[i] + "\t\t");
//      a_filewriter.Write(f, "\n");
//      for(int j = 0; j < set2.numbers.length; j++) {
//        a_filewriter.Write(f, set2.numbers[j] + "\t");
//        for(int i = 0; i < set1.numbers.length; i++) a_filewriter.Write(f, dist[j * set1.numbers.length + i].dist + "\t" + "(" + dist[j * set1.numbers.length + i].atom1 + "-" + dist[j * set1.numbers.length + i].atom2 + ")\t");
//        a_filewriter.Write(f, "\n");
//      }
      a_filewriter.Write(f, "\n");
      a_filewriter.Write(f, "Minimals:\n");
      for(int j = 0; j < set2.numbers.length; j++) {
        a_filewriter.Write(f, set2.numbers[j] + "\t");
        double min = 10000;
        String atm1 = new String();
        String atm2 = new String();
        int set1num = 0;
        for(int i = 0; i < set1.numbers.length; i++)
          if(dist[j * set1.numbers.length + i].dist < min) {
            min = dist[j * set1.numbers.length + i].dist;
            atm1 = dist[j * set1.numbers.length + i].atom1;
            atm2 = dist[j * set1.numbers.length + i].atom2;
            set1num = set1.numbers[i];
          }
        a_filewriter.Write(f, min + "\t" + "(" + atm1 + "-" + atm2 + ")\t" + set1num + "\n");
      }
    } catch (Exception e) {
      System.out.println("Result not written to file");
    }
  }

  private static void outFeatures(String outName, String structTitle, double[] features) {
	    try{
	      OutputStream f = new FileOutputStream(outName);
	      a_filewriter.Write(f, structTitle + "\n");
	      a_filewriter.Write(f, features[0] + "\n");
	      a_filewriter.Write(f, features[1] + "\n");
	      a_filewriter.Write(f, features[2] + "\n");
	      a_filewriter.Write(f, features[3] + "\n");
	    } catch (Exception e) {
	      System.out.println("Result not written to file");
	    }
	  }

private static void outSign(String outName, String structTitle, double[] sign) {
    try{
      OutputStream f = new FileOutputStream(outName + "sign");
      a_filewriter.Write(f, structTitle + "\n");
      a_filewriter.Write(f, sign[0] + "\n");
      a_filewriter.Write(f, sign[1] + "\n");
      a_filewriter.Write(f, sign[2] + "\n");
    } catch (Exception e) {
      System.out.println("Result not written to file");
    }
  }

}
