package from_olga.conservationscore;
import java.io.*;
import java.util.*;

import from_olga.sdppred.AminoAcids;


/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Alignment {
  Vector seqs;
  int length, thickness;
  double ID;
  int goodColumnNum;
  Vector goodColumnIdx = new Vector();
  static double gapPrecentage = 0.5;
  double[] dist;

  public Alignment() {
  }

  public Alignment(String filename) throws Exception{
    read(filename);
  }

  void read(String filename) throws Exception{
    seqs = new Vector();
    a_filereader f = new a_filereader(filename);
//    System.out.println("File opened");
    String current = new String();
    String help = new String();
    boolean last = false;
    int i = 0;
    thickness = 0;
    current = (String)f.data.elementAt(i);
    while(!last) {
      do {
System.out.println(current);    	  
        i++;
        thickness++;
        help = "";
        help += current.substring(1, current.length());
        help += " ";
        current = (String) f.data.elementAt(i);
        while (!current.equals("") && (current.charAt(0) != '%') &&
               (current.charAt(0) != ' ') && (i < f.data.size() - 1)) {
          help += current;
          i++;
          current = (String) f.data.elementAt(i);
        }
        if (i == f.data.size() - 1) {
          last = true;
          help += current;
        }
        seqs.addElement(new Sequence(help));
      } while(!last);
    }
    length = ((Sequence)(seqs.firstElement())).length;
    Vector whole = new Vector();
    for(int x = 0; x < thickness; x++) whole.addElement(new Integer(x));
    ID = id(whole);
//----find good columns
    goodColumnNum = 0;
    goodColumnIdx.removeAllElements();
    for(int k = 0; k < length; k++)
      if(goodColumn(k)) {
    	  System.out.println(k+"");
//      if(goodColumn(k) && !nonCanonicalAA(k)) {
        goodColumnNum++;
        goodColumnIdx.addElement(new Integer(k));
      }
 //----print ot check
   for(int x = 0; x < thickness; x++) {
     System.out.println(x + ": " + ((Sequence)seqs.elementAt(x)).aa);
   }
    for(int x = 0; x < goodColumnNum; x++) {
    	char[] c = getColumn(((Integer)goodColumnIdx.elementAt(x)).intValue());
    	System.out.print("x: " + x + "; idx" + ((Integer)goodColumnIdx.elementAt(x)).intValue() + "; c: ");
    	for(int xx = 0; xx < c.length; xx++) {
    		System.out.print(c[xx]);
    	}
    	System.out.println();
    }
//----end print to check
   dist = new double[thickness * thickness];
   getDist();
  }

  boolean nonCanonicalAA(int pos) {
	  	boolean res = false;
	  	char[] column = getColumn(pos);
	  	for(int i = 0; i < column.length; i++) {
	  		if(AminoAcids.index(column[i]) < 0) {
	  			res = true;
	  		}
	  	}
	  	return res;
	  }

  void getDist() {
    for(int i = 0; i < thickness; i++)
      for(int j = i; j < thickness; j++)
        if(i == j) dist[i * thickness + i] = 0;
        else {
          dist[i * thickness + j] = dist[j * thickness + i] = 1. - id(i, j);
        }
  }

  double id(int i, int j) {
    int match = 0, total = 0;
    for (int k = 0; k < length; k++)
      if((AminoAcids.isAminoAcid(((Sequence)seqs.elementAt(i)).aa(k))) ||
        (AminoAcids.isAminoAcid(((Sequence)seqs.elementAt(j)).aa(k)))) {
          total++;
          if(((Sequence)seqs.elementAt(i)).aa(k) == ((Sequence)seqs.elementAt(j)).aa(k)) match++;
        }
    return (double)match / (double)total;
  }

  char[] getColumn(int pos) {
    char[] help = new char[thickness];
    for(int i = 0; i < thickness; i++) help[i] = ((Sequence)(seqs.elementAt(i))).aa(pos);
    return help;
  }

  public boolean goodColumn(int pos) {
    double counter = 0;
//    for(int i = 0; i < thickness; i++)
//      if(((((Sequence)(seqs.elementAt(i))).aa(pos) >= 'A') && (((Sequence)(seqs.elementAt(i))).aa(pos) <= 'Z')) || (((Sequence)(seqs.elementAt(i))).aa(pos) == '.'))
//        return false;
    for(int i = 0; i < thickness; i++) if(((Sequence)(seqs.elementAt(i))).aa(pos) == '-') counter++;
    if(counter / (double)thickness <= gapPrecentage) return true;
    return false;
  }

  boolean fullyConserved(int pos) {
    for(int i = 1; i < thickness; i++) if(((Sequence)(seqs.elementAt(i))).aa(pos) != ((Sequence)(seqs.elementAt(0))).aa(pos)) return false;
    return true;
  }

  public void outFreq(String name, double[] freq) throws Exception{
    try{
      OutputStream f = new FileOutputStream(name);
      for(int i = 0; i < freq.length; i++)
        a_filewriter.Write(f, freq[i] + "\n");
      f.close();
    } catch (IOException IOE) {
      System.out.println("File opening exception in Alignment conservation score");
      System.exit(1);
    }
  }

  public double id(Vector set) {
    double match = 0., total = 0.;
    for(int i = 0; i < set.size(); i++)
      for(int j = i + 1; j < set.size(); j++) {
        for (int k = 0; k < length; k++) {
          if((AminoAcids.isAminoAcid(((Sequence)(seqs.elementAt(((Integer)set.elementAt(i)).intValue()))).aa(k))) ||
             (AminoAcids.isAminoAcid(((Sequence)(seqs.elementAt(((Integer)set.elementAt(j)).intValue()))).aa(k)))) {
            total++;
            if ( ( (Sequence) (seqs.elementAt(((Integer)set.elementAt(i)).intValue()))).aa(k) ==
                ( (Sequence) (seqs.elementAt(((Integer)set.elementAt(j)).intValue()))).aa(k)) match++;
          }
        }
      }
    return (match / total);
  }

  public int findSeq(String name) {
    for(int i = 0; i < thickness; i++)
      if(((Sequence)seqs.elementAt(i)).name.equals(name)) return i;
    return -1;
  }

}
