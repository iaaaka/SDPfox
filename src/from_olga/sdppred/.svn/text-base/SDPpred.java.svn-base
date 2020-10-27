package from_olga.sdppred;

import java.util.*;
import java.io.*;

/**
 * <p>Title: SDPpred</p>
 *
 * <p>Description: Prediction of SDPs given an alignment and a grouping</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Olga V. Kalinina
 * @version 1.0
 */
public class SDPpred {

  static void out1(SDPs sdp, Alignment al, String fname) {
    try{
      OutputStream f = new FileOutputStream(fname);
      a_filewriter.Write(f, "Minimum probability = " + al.given.p + "\n");
      a_filewriter.Write(f, "Average alignment id " + al.given.id + "\n");
      a_filewriter.Write(f, "a = " + sdp.a + " b = " + sdp.b + "\n");
      if(sdp.log) a_filewriter.Write(f, "Log transformation of probabilities\n");
      else a_filewriter.Write(f, "No log transformation of probabilities\n");
      a_filewriter.Write(f, "A = " + sdp.A + "\n");
      a_filewriter.Write(f, "B = " + sdp.B + "\n");
      a_filewriter.Write(f, "C = " + sdp.C + "\n");
      a_filewriter.Write(f, "D = " + sdp.D + "\n");
      a_filewriter.Write(f, "L = " + sdp.L + "\n");
//      a_filewriter.Write(f, "Good columns = \n");
//      for (int i = 0; i < al.given.goodColumnNum; i++) {
//        char[] h = (al.getColumn( ( (Integer) (al.given.goodColumnIdx.elementAt(i))).intValue()));
//        for(int j = 0; j < al.thickness; j++)
//          a_filewriter.Write(f, h[j] + "");
//        a_filewriter.Write(f, "\n");
//      }
//      a_filewriter.Write(f, "I = \n");
//      for (int i = 0; i < (sdp.I).size(); i++)
//        a_filewriter.Write(f, (sdp.I).elementAt(i) + "\n");
//      a_filewriter.Write(f, "I^sh_mean = \n");
//      for (int i = 0; i < (sdp.I_sh).size(); i++)
//        a_filewriter.Write(f, (sdp.I_sh).elementAt(i) + "\n");
//      a_filewriter.Write(f, "I^sh_sigma = \n");
//      for (int i = 0; i < (sdp.I_sh_sigma).size(); i++)
//        a_filewriter.Write(f, (sdp.I_sh_sigma).elementAt(i) + "\n");
      a_filewriter.Write(f, "Partitioning:\n");
      for(int i = 0; i < al.given.groups.size(); i++)
        a_filewriter.Write(f, " group " + i + ":" + ((Vector)al.given.groups.elementAt(i)).toString() + ", average id " + ((Double)al.given.groupID.elementAt(i)).doubleValue() + "\n");
      for(int i = 0; i < sdp.pos.size(); i++) {
        a_filewriter.Write(f, i + ". Alignment position " + ((SDP)sdp.pos.elementAt(i)).ind + "\n");
        a_filewriter.Write(f, "I_n       = " + ((SDP)sdp.pos.elementAt(i)).inf + "\n");
        a_filewriter.Write(f, "Z-score   = " + ((SDP)sdp.pos.elementAt(i)).z + "\n");
        a_filewriter.Write(f, "E^exp     = " + ((SDP)sdp.pos.elementAt(i)).e + "\n");
        a_filewriter.Write(f, "sigma^exp = " + ((SDP)sdp.pos.elementAt(i)).sigma + "\n");
        for(int j = 0; j < al.given.groups.size(); j++)
          a_filewriter.Write(f, "Amino acids in group " + j + ": " + ((SDP)sdp.pos.elementAt(i)).groups.elementAt(j).toString() + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }
  }

  public static void outResProbs(Vector resProbs, String fname) {
    try{
      OutputStream f = new FileOutputStream(fname);
      a_filewriter.Write(f, "Probability plot\n");
      for(int i = 0; i < resProbs.size(); i++) {
        a_filewriter.Write(f, "" + ((Double)resProbs.elementAt(i)).doubleValue() + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }

  }

  public static void outRefSDPs(SDPs sdp, String fname, int indent) {
    try{
      OutputStream f = new FileOutputStream(fname);
      a_filewriter.Write(f, "SDPs for sequence" + sdp.refSequence + "\n");
      a_filewriter.Write(f, "Probability is " + sdp.p + "\n");
      a_filewriter.Write(f, "Alignment position\tPosition in " + sdp.refSequence + "\tMutual information\tZ-probability\n");
      for(int i = 0; i < sdp.pos.size(); i++) {
        a_filewriter.Write(f, "" + ((SDP)sdp.pos.elementAt(i)).ind + "\t" + (((SDP)sdp.pos.elementAt(i)).refInd + indent) + "\t" + ((SDP)sdp.pos.elementAt(i)).refAA + "\t" + ((SDP)sdp.pos.elementAt(i)).inf + "\t" + ((SDP)sdp.pos.elementAt(i)).z + "\n");
//        for(int j = 0; j < ((SDP)sdp.pos.elementAt(i)).groups.size(); j++)
//          a_filewriter.Write(f, "\t\tgroup " + j + ": " + ((SDP)sdp.pos.elementAt(i)).groups.elementAt(j).toString() + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }

  }

  public static void outRefSDPsFull(SDPs sdp, String fname, int indent) {
    try{
      OutputStream f = new FileOutputStream(fname);
      a_filewriter.Write(f, "SDPs for sequence" + sdp.refSequence + "\n");
      a_filewriter.Write(f, "Probability is " + sdp.p + "\n");
      a_filewriter.Write(f, "Alignment position\tPosition in " + sdp.refSequence + "\tMutual information\tZ-probability\n");
      for(int i = 0; i < sdp.pos.size(); i++) {
        a_filewriter.Write(f, "" + ((SDP)sdp.pos.elementAt(i)).ind + "\t" + (((SDP)sdp.pos.elementAt(i)).refInd + indent) + "\t" + ((SDP)sdp.pos.elementAt(i)).refAA + "\t" + ((SDP)sdp.pos.elementAt(i)).inf + "\t" + ((SDP)sdp.pos.elementAt(i)).z + "\n");
        for(int j = 0; j < ((SDP)sdp.pos.elementAt(i)).groups.size(); j++)
          a_filewriter.Write(f, "\t\tgroup " + j + ": " + ((SDP)sdp.pos.elementAt(i)).groups.elementAt(j).toString() + "\n");
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception");
      System.exit(1);
    }

  }

  public static SDPs getSDPs(String sdpin, String refSeq, RefAlignment alRef) {
      Alignment al = new Alignment();
      Vector inf = new Vector();
      SDPs sdp = new SDPs();
      Calculations.getMatrices();
      try{
        al = new Alignment(sdpin);
      }catch(Exception e){
      System.out.println("Alignment not read at SDPpred");
      System.exit(0);
    }
//    System.out.println("Alignment read at " + (System.currentTimeMillis() - beginTime));
    Vector I_sh = new Vector();
    I_sh.addAll(al.myShuffle());
//    System.out.println("Shuffling done at " + (System.currentTimeMillis() - beginTime));
    {
      int i = 0;
      double info;
      while (i < al.given.goodColumnNum) {
        char[] column = new char[al.thickness];
        column = al.getColumn(((Integer) al.given.goodColumnIdx.elementAt(i)).
                              intValue());
        info = Calculations.getInf(column, al.thickness, al.given);
//        for(int cc = 0; cc < column.length; cc++) {System.out.print(column[cc]);}
//        System.out.println("; inf = " + info);          
        if (info <= 0.) {
          al.given.goodColumnNum--;
          al.given.goodColumnIdx.removeElementAt(i);
          for(int ii = 0; ii < I_sh.size(); ii++)
            ((Vector)I_sh.elementAt(ii)).removeElementAt(i);
        } else {
          i++;
          inf.addElement(new Double(info));
        }
      }
    }
    Vector I_sh_mean = new Vector();
    I_sh_mean.addAll(Calculations.meanShuffle(I_sh));
    double A = 0., B = 0., C = 0., D = 0.;
    for(int i = 0; i < al.given.goodColumnNum; i++) {
      A += (((Double)I_sh_mean.elementAt(i)).doubleValue()) * (((Double)inf.elementAt(i)).doubleValue());
      B += ((Double)inf.elementAt(i)).doubleValue();
      C += ((Double)I_sh_mean.elementAt(i)).doubleValue();
      D += (((Double)I_sh_mean.elementAt(i)).doubleValue()) * (((Double)I_sh_mean.elementAt(i)).doubleValue());
//System.out.println("A = " + A + "; B = " + B + "; C = " + C + "; D = " + D + "; I_sh_mean[i] = " + (((Double)I_sh_mean.elementAt(i)).doubleValue()) + "; inf[i] = " + (((Double)inf.elementAt(i)).doubleValue()));
    }
    double a = (A * al.given.goodColumnNum - B * C) / (D * al.given.goodColumnNum - C * C);
    double b = (B * D - C * A) / (D * al.given.goodColumnNum - C * C);
    Vector I_sh_sigma = new Vector();
    I_sh_sigma.addAll(Calculations.sigmaShuffle(I_sh));
//    System.out.println("sigma(I^exp) calculated at " + (System.currentTimeMillis() - beginTime));
    double[] e_exp = new double[al.given.goodColumnNum];
    double[] sigma_exp = new double[al.given.goodColumnNum];
    for(int i = 0; i < al.given.goodColumnNum; i++) {
      e_exp[i] = a * ((Double)I_sh_mean.elementAt(i)).doubleValue() + b;
      sigma_exp[i] = Math.abs(a) * ((Double)I_sh_sigma.elementAt(i)).doubleValue();
    }
    System.out.println("a = " + a + " b = " + b);
    al.given.getP_i(inf, I_sh, e_exp, sigma_exp);
    Vector pp_p = new Vector();
    pp_p = Calculations.p_p(al.given.p_i, al.given.log);
//    System.out.println("p calculated at " + (System.currentTimeMillis() - beginTime));
    al.given.p = ((Double)pp_p.elementAt(0)).doubleValue();
    outResProbs((Vector)pp_p.elementAt(2), "pPlot");
    sdp.get(al, inf, I_sh_mean, I_sh_sigma, e_exp, sigma_exp, al.given, D, C, A, B, al.given.goodColumnNum, ((Integer)pp_p.elementAt(1)).intValue());
    if(!refSeq.equals("")) sdp.project(alRef, refSeq);
    return sdp;
  }

  public static void main(String[] args) {
      Alignment al = new Alignment();
      Vector inf = new Vector();
      Vector z = new Vector();
      Vector zIdx = new Vector();
      SDPs sdp = new SDPs();
      args = new String[3];
      long beginTime = System.currentTimeMillis();
      args[0] = "data\\idh_NAD_vs_NADP.sdpin";
      args[1] = "data\\idh_NAD_vs_NADP.sdpout";
      args[2] = "P08200|IDH_ECOLI";
      Calculations.getMatrices();
      try{
        al = new Alignment(args[0]);
      }catch(Exception e){
        System.out.println("Alignment not read");
        System.exit(0);
      }
      System.out.println("Alignment read at " + (System.currentTimeMillis() - beginTime));
      Vector I_sh = new Vector();
      I_sh.addAll(al.myShuffle());
      System.out.println("Shuffling done at " + (System.currentTimeMillis() - beginTime));
      {
        int i = 0;
        double info;
        while (i < al.given.goodColumnNum) {
          char[] column = new char[al.thickness];
          column = al.getColumn(((Integer) al.given.goodColumnIdx.elementAt(i)).
                                intValue());
          info = Calculations.getInf(column, al.thickness, al.given);
          if (info <= 0.) {
            al.given.goodColumnNum--;
            al.given.goodColumnIdx.removeElementAt(i);
            for(int ii = 0; ii < I_sh.size(); ii++)
             ((Vector)I_sh.elementAt(ii)).removeElementAt(i);
          } else {
            i++;
            inf.addElement(new Double(info));
          }
        }
      }
      System.out.println("MI calculated at " + (System.currentTimeMillis() - beginTime));
      Vector I_sh_mean = new Vector();
      I_sh_mean.addAll(Calculations.meanShuffle(I_sh));
      System.out.println("<I^exp> calculated at " + (System.currentTimeMillis() - beginTime));
      double A = 0., B = 0., C = 0., D = 0.;
      for(int i = 0; i < al.given.goodColumnNum; i++) {
        A += (((Double)I_sh_mean.elementAt(i)).doubleValue()) * (((Double)inf.elementAt(i)).doubleValue());
        B += ((Double)inf.elementAt(i)).doubleValue();
        C += ((Double)I_sh_mean.elementAt(i)).doubleValue();
        D += (((Double)I_sh_mean.elementAt(i)).doubleValue()) * (((Double)I_sh_mean.elementAt(i)).doubleValue());
      }
      double a = (A * al.given.goodColumnNum - B * C) / (D * al.given.goodColumnNum - C * C);
      double b = (B * D - C * A) / (D * al.given.goodColumnNum - C * C);
      Vector I_sh_sigma = new Vector();
      I_sh_sigma.addAll(Calculations.sigmaShuffle(I_sh));
      System.out.println("sigma(I^exp) calculated at " + (System.currentTimeMillis() - beginTime));
      double[] e_exp = new double[al.given.goodColumnNum];
      double[] sigma_exp = new double[al.given.goodColumnNum];
      for(int i = 0; i < al.given.goodColumnNum; i++) {
        e_exp[i] = a * ((Double)I_sh_mean.elementAt(i)).doubleValue() + b;
        sigma_exp[i] = Math.abs(a) * ((Double)I_sh_sigma.elementAt(i)).doubleValue();
      }
      al.given.getP_i(inf, I_sh, e_exp, sigma_exp);
      Vector pp_p = new Vector();
      pp_p = Calculations.p_p(al.given.p_i, al.given.log);
      System.out.println("p calculated at " + (System.currentTimeMillis() - beginTime));
      al.given.p = ((Double)pp_p.elementAt(0)).doubleValue();
      sdp.get(al, inf, I_sh_mean, I_sh_sigma, e_exp, sigma_exp, al.given, D, C, A, B, al.given.goodColumnNum, ((Integer)pp_p.elementAt(1)).intValue());
      out1(sdp, al, args[1]);
      outResProbs((Vector)pp_p.elementAt(2), "data\\pPlot");
      if(!args[2].equals("")) {
        sdp.project(al, args[2]);
        outRefSDPs(sdp, args[1] + ".ref", 0);
      }
    }
}
