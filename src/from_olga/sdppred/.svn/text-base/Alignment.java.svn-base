package from_olga.sdppred;
import java.io.*;
import java.util.*;

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
  int length, thickness;//, goodColumnNum;
  double ID;
  Partition given = new Partition();

  public Alignment() {
  }

  public Alignment(String filename) throws Exception{
    read(filename);
  }

  void read(String filename) throws Exception{
    seqs = new Vector();
    a_filereader f = new a_filereader(filename);
    String current = new String();
    String help = new String();
    boolean last = false;
    int i = 0;
    thickness = 0;
    Vector curGroup = new Vector();
    current = (String)f.data.elementAt(i);
    while(!last) {
      while(current.charAt(0) != '=') {
        i++;
        current = (String)f.data.elementAt(i);
      }
      curGroup.removeAllElements();
      do {
        curGroup.addElement(new Integer(thickness));
        if(current.charAt(0) == '=') {
            i++;
            current = (String) f.data.elementAt(i);
        }
        i++;
        thickness++;
        help = "";
        help += current.substring(1, current.length());
        help += " ";
        current = (String) f.data.elementAt(i);
        while (!current.equals("") && (current.charAt(0) != '%') && (current.charAt(0) != '=') &&
               (current.charAt(0) != ' ') && (i < f.data.size() - 1)) {
          //System.out.println(current + " " + i + " " + thickness);
          help += current;
          i++;
          current = (String) f.data.elementAt(i);
        }
        if (i == f.data.size() - 1) {
          last = true;
          help += current;
        }
        seqs.addElement(new Sequence(help));
      } while((current.charAt(0) != '=') && (!last));
      given.groups.addElement(new Vector(curGroup));
//      System.out.println(((Sequence)(seqs.lastElement())).aa);
    }
    length = ((Sequence)(seqs.firstElement())).length;
    Vector whole = new Vector();
    for(int x = 0; x < thickness; x++) whole.addElement(new Integer(x));
    ID = id(whole);
    for(int k = 0; k < given.groups.size(); k++)
      given.groupID.addElement(new Double(id((Vector)given.groups.elementAt(k))));
//----print to check
//    for(int k = 0; k < given.groups.size(); k++) {
//      System.out.println("Group " + k + ":");
//      for (int j = 0; j < ((Vector) (given.groups.elementAt(k))).size(); j++) {
//                System.out.print(" " + ((Integer)((Vector) (given.groups.elementAt(k))).elementAt(j)).intValue());
//            }
//            System.out.println("");
//   }
//----find good columns
    given.goodColumnNum = 0;
    given.goodColumnIdx.removeAllElements();
    for(int k = 0; k < length; k++)
      if(goodColumn(k) && !fullyConserved(k) /*&& !nonCanonicalAA(k)*/) {
        given.goodColumnNum++;
        given.goodColumnIdx.addElement(new Integer(k));
      }
System.out.println("GCN = " + given.goodColumnNum);    
    given.avGroupSize = (double)thickness / given.groups.size();
    int min = thickness;
    double sum = 0.;
    for(int x = 0; x < given.groups.size(); x++) {
      if (((Vector) given.groups.elementAt(x)).size() < min)
        min = ((Vector) given.groups.elementAt(x)).size();
      sum += ((Double)given.groupID.elementAt(x)).doubleValue();
    }
    given.smallestGroupSize = min;
    given.avGroupID = sum / (double)given.groups.size();
    System.out.println("sm group size = " + given.smallestGroupSize);
    System.out.println("av group size = " + given.avGroupSize);
  }

  char[] getColumn(int pos) {
    char[] help = new char[thickness];
    for(int i = 0; i < thickness; i++) help[i] = ((Sequence)(seqs.elementAt(i))).aa(pos);
    return help;
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

  public boolean goodColumn(int pos) {
    double counter = 0;
    for(int i = 0; i < thickness; i++)
      if(((((Sequence)(seqs.elementAt(i))).aa(pos) >= 'a') && (((Sequence)(seqs.elementAt(i))).aa(pos) <= 'z')) || (((Sequence)(seqs.elementAt(i))).aa(pos) == '.'))
        return false;
    for(int i = 0; i < thickness; i++) if(((Sequence)(seqs.elementAt(i))).aa(pos) == '-') counter++;
    if(counter / (double)thickness < Calculations.gapPrecentage) return true;
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
      System.out.println("File opening exception");
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


  public Vector myShuffle() {
    Vector result = new Vector();
    Vector randomGroups = new Vector();
    Vector randomID= new Vector();
    Vector whole = new Vector();
    char[] column = new char[thickness];
    double n_a, n_ak, secTerm, inf;
    for(int i = 0; i < thickness; i++) whole.addElement(new Integer(i));
    double wholeID = id(whole);
    for (int k = 0; k < given.groups.size(); k++)
      randomID.addElement(new Double(wholeID));
    for(int j = 0; j < Calculations.iteration; j++) {
      Vector I_j = new Vector();
      if(j % 10 == 0)
        System.out.print(" " + j);
      //----making random groups
      {
        randomGroups.removeAllElements();
        Vector allSeq = new Vector();
        Vector curGroup = new Vector();
        for (int i = 0; i < thickness; i++) allSeq.addElement(new Integer(i));
        Collections.shuffle(allSeq);
//        System.out.println("" + allSeq.toString());
        int k = 0;
        for (int i = 0; i < given.groups.size(); i++) {
          curGroup.removeAllElements();
          for (int jj = 0; jj < ((Vector) (given.groups.elementAt(i))).size();
                        jj++) {
            curGroup.addElement(new Integer(((Integer) (allSeq.elementAt(k))).
                                            intValue()));
            k++;
          }
          randomGroups.addElement(new Vector(curGroup));
        }
      }
      //----random groups made
//        //----print to check
//        for(int k = 0; k < given.groups.size(); k++) {
//        System.out.println("Group " + k + ":");
//        for (int jj = 0; jj < ((Vector) (randomGroups.elementAt(k))).size(); jj++) {
//                  System.out.print(" " + ((Integer)((Vector) (randomGroups.elementAt(k))).elementAt(jj)).intValue());
//              }
//              System.out.println("");
//        }
//        //----end check
      for(int i = 0; i < given.goodColumnNum; i++) {
        column = getColumn(((Integer)(given.goodColumnIdx.elementAt(i))).intValue());
//        System.out.print(".");
//        if(i % 100 == 1) System.out.println("");
        secTerm = 0.;
        inf = 0.;
        if(!Calculations.gapGroups(column, randomGroups)){
          for (int a = 0; a < AminoAcids.alphLen; a++) {
            n_a = Calculations.Pseudocount(column, whole, thickness, wholeID,
                                           a);
            for (int k = 0; k < given.groups.size(); k++) {
              n_ak = Calculations.Pseudocount(column,
                                              ((Vector) (randomGroups.
                  elementAt(k))),
                                              thickness,
                                              ((Double) (randomID.
                  elementAt(k))).doubleValue(), a);
              inf += n_ak * Calculations.myLog(n_ak / n_a);
            }
          }
          if (inf != 0) {
            inf /=
                (double) (thickness +
                          Calculations.kappa * Math.sqrt((double) thickness));
            for (int k = 0; k < randomGroups.size(); k++)
              secTerm += ((Vector) (randomGroups.elementAt(k))).size() *
                  Calculations.myLog(((Vector) (randomGroups.elementAt(k))).
                                     size());
            inf +=
                ( -secTerm / (double) thickness + Calculations.myLog(thickness));
          }
        }
        I_j.addElement(new Double(inf));
      }
      result.addElement(new Vector(I_j));
    }
    System.out.println("");
    return result;
  }

  public int findSeq(String name) {
    for(int i = 0; i < thickness; i++)
      if(((Sequence)seqs.elementAt(i)).name.equals(name)) return i;
    return -1;
  }

}
