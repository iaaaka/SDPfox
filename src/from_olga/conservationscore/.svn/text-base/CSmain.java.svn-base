package from_olga.conservationscore;

import java.util.*;
import java.io.*;

/**
 * <p>Title: Conservation scores calculation</p>
 * <p>Description: Calculate conservation scores for each position of a given alignment. Select a number of most conserved positions</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class CSmain {

  Alignment al = new Alignment();

  static void outScores(Alignment al, Vector consScores, String fname) {
    try{
      OutputStream f = new FileOutputStream(fname);
      for(int i = 0; i < al.goodColumnNum; i++) {
        a_filewriter.Write(f, i + "\t" + ((Integer)al.goodColumnIdx.elementAt(i)).intValue() + "\t");
        for(int x = 0; x < al.thickness; x++) a_filewriter.Write(f, "" + al.getColumn(((Integer)al.goodColumnIdx.elementAt(i)).intValue())[x]);
        a_filewriter.Write(f, "\t " + ((Double)consScores.elementAt(i)).doubleValue() + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }
  }

  public static void outSignScores(Vector signScores, String fname, String refSeq, int indent) {
    try{
      OutputStream f = new FileOutputStream(fname);
      a_filewriter.Write(f, "Conserved positions for sequence " + refSeq + "\n");
      for(int i = 0; i < signScores.size(); i++) {
        a_filewriter.Write(f, i + "\t" + ((Position)signScores.elementAt(i)).num + "\t");
        a_filewriter.Write(f, (((Position)signScores.elementAt(i)).refSeqNum + indent) + "\t");
        a_filewriter.Write(f, ((Position)signScores.elementAt(i)).refSeqAA + "\t");
        for(int x = 0; x < (((Position)signScores.elementAt(i)).col).length; x++) a_filewriter.Write(f, "" + (((Position)signScores.elementAt(i)).col)[x]);
        a_filewriter.Write(f, "\t " + ((Position)signScores.elementAt(i)).consScore + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }
  }

  static void outScoreDistribution(Vector consScores, String fname) {
    Collections.sort(consScores);
//    System.out.println("" + consScores.toString());
    int catNum = 1000;
    double max = 490; //((Double)consScores.lastElement()).doubleValue();
    double min = -10; //((Double)consScores.firstElement()).doubleValue();
    double step = (max - min) / catNum;
    int bin = 0;
    int j = 0;
    try{
      OutputStream f = new FileOutputStream(fname);
      for(int i = 0; i <= catNum; i++) {
        bin = 0;
        double upperBound = min + (i + 1) * step;
        double lowerBound = min + i * step;
        for(j = 0; j < consScores.size(); j++)
          if((((Double)consScores.elementAt(j)).doubleValue() < upperBound)
             && ((((Double)consScores.elementAt(j)).doubleValue() > lowerBound)))
            bin++;
        a_filewriter.Write(f, lowerBound + "\t" + bin + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }
  }

  public static void main(String[] args) {
    Alignment al = new Alignment();
//    args = new String[7];
    char[] column;
    long beginTime = System.currentTimeMillis();
    Vector consScores;
    String refSeq = new String();
    if(args.length == 2) { refSeq = ""; }
    else { refSeq = args[2]; }
//    args[0] = "data\\3RAB.gde"; //alignment
//    args[1] = "data\\3RAB"; //common part of the name of all output files
//    args[2] = "rab4_human"; //reference sequence name
    Calculations.normMatr = false;
    Calculations.getMatrices();
    try{
      al = new Alignment(args[0]);
    }catch(Exception e){
      System.out.println("Alignment not read");
      System.exit(0);
    }
    System.out.println("Alignment read at " + (System.currentTimeMillis() - beginTime));
    consScores = new Vector(al.goodColumnNum);
    column = new char[al.thickness];
    System.out.println("GCN = " + al.goodColumnNum);
    for(int i = 0; i < al.goodColumnNum; i++) {
      System.out.println(i + "");
      column = al.getColumn(((Integer)al.goodColumnIdx.elementAt(i)).intValue());
      consScores.addElement(new Double(Calculations.score(column, al.dist)));
    }
//    System.out.println("Conservation scores calculated at " + (System.currentTimeMillis() - beginTime));
    Vector backgroundScores = new Vector();
    backgroundScores.addAll(Calculations.background(al, consScores));
    Vector signScores = new Vector();
    signScores.addAll(Calculations.cutoff(backgroundScores, al));
    Calculations.project(al, refSeq, signScores);
    outScores(al, consScores, args[1]+".con");
//    Vector normalScores = new Vector();
//    normalScores.addAll(Calculations.normalize(consScores));
    outScoreDistribution(consScores, args[1]+".distr");
//    outScoreDistribution(normalScores, args[1]+".norm");
//    Vector backgroundScores = new Vector();
//    backgroundScores.addAll(Calculations.background(al, consScores));
    outScoreDistribution(backgroundScores, args[1]+".bkgr");
//    Vector signScores = new Vector();
//    signScores.addAll(Calculations.cutoff(backgroundScores, al));
//    Calculations.project(al, refSeq, signScores);
    outSignScores(signScores, args[1]+".sign", refSeq, 0);
//    outScores(al, backgroundScores, args[1] + "#");
//----check scores
//    for(int i = 0; i < al.goodColumnNum; i++) {
//      System.out.println(i + ") " + ((Integer)al.goodColumnIdx.elementAt(i)).intValue() + ": ");
//      for(int x = 0; x < column.length; x++) System.out.print("" + al.getColumn(i)[x]);
//      System.out.println(" = " + ((Double)consScores.elementAt(i)).doubleValue());
//    }
//----end check scores
  }

  public static Vector getCP(String alName, String refSeqName) {
    Alignment al = new Alignment();
    char[] column;
    Vector consScores;
    Calculations.normMatr = false;
    Calculations.getMatrices();
    try{
      al = new Alignment(alName);
    }catch(Exception e){
      System.out.println("Alignment not read in CS");
      System.exit(0);
    }
    consScores = new Vector(al.goodColumnNum);
    column = new char[al.thickness];
    for(int i = 0; i < al.goodColumnNum; i++) {
      column = al.getColumn(((Integer)al.goodColumnIdx.elementAt(i)).intValue());
      consScores.addElement(new Double(Calculations.score(column, al.dist)));
//for(int cc = 0; cc < column.length; cc++) {System.out.print(column[cc]);}
//System.out.println("; score = " + Calculations.score(column, al.dist));
    }
    Vector backgroundScores = new Vector();
    backgroundScores.addAll(Calculations.background(al, consScores));
    Vector signScores = new Vector();
    signScores.addAll(Calculations.cutoff(backgroundScores, al));
    Calculations.project(al, refSeqName, signScores);
    return signScores;
  }

}
