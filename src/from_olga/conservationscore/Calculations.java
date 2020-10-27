package from_olga.conservationscore;

import java.util.*;

/**
 * <p>Title: Conservation scores calculation</p>
 * <p>Description: Calculate conservation scores for each position of a given alignment. Select a number of most conserved positions</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Calculations {
  //Static methods for calculations
  static double [] matrix;
  static final int shuffleTimes = 10000;
  static boolean normMatr;

  public static void getMatrices() {
    a_filereader f;
    String s = new String();
    String[] splitted;
    //blosum62
    f = new a_filereader("matrices/blosum62");
    matrix = new double[400];
    s = (String)f.data.elementAt(0);
    for(int i = 0; i < 20; i++) {
      s = (String)f.data.elementAt(i + 3);
      splitted = s.split("\\s");
      for(int j = 0; j < 20; j++) {
        Double v = new Double(splitted[j + 1]);
        matrix[i * 20 + j] = v.doubleValue();
      }
    }
//----matrix "normalization"
    if(normMatr)
      for(int i = 0; i < 20; i++)
        for(int j = 0; j < 20; j++)
          matrix[i * 20 + j] = matrix[i * 20 + j] / Math.sqrt(matrix[i * 20 + i] * matrix[j * 20 + j]);
    //m40
    //m50
    //m60
    //m70
    //m80
    //m90
  }

  static double score(char[] col, double[] dist) {
    double res = 0.;
    double total = 0.;
    for(int i = 0; i < col.length; i++)
      for(int j = i + 1; j < col.length; j++) {
        if((col[i] != '-') && (col[j] != '-')) {
          if((AminoAcids.index(col[i]) != -1) && (AminoAcids.index(col[j]) != -1)) {
            res += dist[i * col.length +
                j] * matrix[AminoAcids.index(col[i]) * 20 +
                AminoAcids.index(col[j])];
            total += dist[i * col.length + j];
          } else {res = -1.; total = 1.; } //non canonical symbols in the sequence
//          if(col[i] == col[j]) res++;
//          total++;
        }
//        System.out.println(AminoAcids.index(col[i]) + " " + AminoAcids.index(col[j]));
//        System.out.println(col[i] + " " + col[j]);
      }
    return res / total;
  }

  static Vector normalize(Vector scores) {
    double mean = 0., sigma = 0.;
    Vector res = new Vector();
    for(int i = 0; i < scores.size(); i++)
      mean += ((Double)scores.elementAt(i)).doubleValue();
    mean /= scores.size();
    for(int i = 0; i < scores.size(); i++)
      sigma += (((Double)scores.elementAt(i)).doubleValue() - mean) * (((Double)scores.elementAt(i)).doubleValue() - mean);
    sigma /= scores.size() - 1;
    sigma = Math.sqrt(sigma);
    for(int i = 0; i < scores.size(); i++)
      res.addElement(new Double((((Double)scores.elementAt(i)).doubleValue() - mean) / sigma));
    return res;
  }

  static char[] randCol(Alignment al) {
    char[] res = new char[al.thickness];
    for(int i = 0; i < al.thickness; i++) {
      System.out.println(al.goodColumnIdx.size() + "\n");
      res[i] = ((Sequence)al.seqs.elementAt(i)).aa(((Integer)al.goodColumnIdx.elementAt((int)(Math.random() * al.goodColumnNum))).intValue());
    }
    return res;
  }

  static Vector background(Alignment al, Vector scores) {
    double mean, sigma;
    double[] bkSc = new double[scores.size()];
    Vector res = new Vector();
    double[] sc = new double[shuffleTimes];
    mean = 0.; sigma = 0.;
    for(int j = 0; j < shuffleTimes; j++) {
      char[] col = new char[al.thickness];
      col = randCol(al);
//      for(int x = 0; x < col.length; x++) System.out.print("" + col[x]);
//      System.out.println("");
      sc[j] = score(col, al.dist);
//      System.out.println("score j " + sc[j]);
      mean += sc[j];
    }
    mean /= shuffleTimes;
    for(int j = 0; j < shuffleTimes; j++)
      sigma += (sc[j] - mean) * (sc[j] - mean);
    sigma = Math.sqrt(sigma / (shuffleTimes - 1));
//    System.out.println("mean = " + mean + "; sigma = " + sigma);
    for(int i = 0; i < scores.size(); i++)
      bkSc[i] = (((Double)scores.elementAt(i)).doubleValue() - mean) / sigma;
    double bkMean = 0.;
    for(int i = 0; i < scores.size(); i++)
      bkMean += bkSc[i];
    bkMean /= bkSc.length;
    for(int i = 0; i < scores.size(); i++)
      res.addElement(new Double(bkSc[i] - bkMean));
    return res;
  }

  static double myLog(double arg) {
    if(arg < 1e-323) return 0.;
    return Math.log(arg);
  }

  static double logC(int n, int k) {
    double s = 0.;
    int i = n;
    if(k > n - k) k = n - k;
    for(int j = 1; j <= k; j++) {
      s += (Math.log((double)i) - Math.log((double)j));
      i--;
    }
    return s;
  }


  static Vector cutoff(Vector sc, Alignment al) {
    Vector res = new Vector();
    double p, q, prod, min = 0.;
    int minIdx = 0;
    Vector items = new Vector();
    Vector prods = new Vector();
    Vector ps = new Vector();
    Vector poss = new Vector();
    for(int i = 0; i < sc.size(); i++) {
      char[] ccol = new char[al.thickness];
      for(int x = 0; x < al.thickness; x++) ccol[x] = al.getColumn(((Integer)al.goodColumnIdx.elementAt(i)).intValue())[x];
      poss.addElement(new Position(((Double)sc.elementAt(i)).doubleValue(), ccol, ((Integer)al.goodColumnIdx.elementAt(i)).intValue()));
    }
    Collections.sort(poss, new  cmp());  //some sorting of z
    if(Normal.probGrater(0, 1, ((Position)poss.elementAt(0)).consScore) > 0.) {
      min = 1.;
      for (int i = 0; i < poss.size(); i++) {
        p = Normal.probGrater(0, 1, ((Position)poss.elementAt(i)).consScore);
        ps.addElement(new Double(p));
        q = 1. - p;
        items.removeAllElements();
        for (int j = 0; j <= poss.size() - i; j++) {
          prod = 1.;
          for (int n = 1; n <= poss.size() - j; n++) prod *= (p * (n + j) / n);
          for (int n = 1; n <= j; n++) prod *= q;
          items.addElement(new Double(prod));
        }
        Collections.sort(items); //some sorting of items
        prod = 0.;
        for (int j = 0; j < items.size(); j++) prod +=
          ( (Double) items.elementAt(j)).doubleValue();
        //System.out.println("prod = " + prod);
        prods.addElement(new Double(prod));
        if (prod < min) {
          min = prod;
          minIdx = i;
          if (minIdx == poss.size() - 1) {
            System.out.println("Moderate scores, prods: " + prods.toString());
            System.out.println(" scores:" + sc.toString());
            System.out.println(" probabilities" + ps.toString());
          }
        }
      }
      //System.out.println("k = " + i + " prod = " + prod);
      if(min > 0.) min = myLog(min);
    }
    if((min == 0.) || (Normal.probGrater(0, 1, ((Position)poss.elementAt(0)).consScore) == 0.)){
      min = 0.;
      for (int i = 0; i < poss.size(); i++) {
        p = Normal.probGraterApprox(((Position)poss.elementAt(i)).consScore);
        if(i > 0) {
          prod = logC(poss.size(), poss.size() - i + 1);
          prod  += (i - 1) * p;
        }
        else prod = 0.;
        if (prod < min) {
          min = prod;
          minIdx = i;
          if (minIdx == poss.size() - 1)
            System.out.println("MinIdx = n - 1 in log part");
        }
      }
    }
    for(int i = 0; i < minIdx; i++)
      res.addElement(new Position(((Position)poss.elementAt(i)).consScore, ((Position)poss.elementAt(i)).col, ((Position)poss.elementAt(i)).num));
    return res;
  }

  static int findCol(Vector sc, double val) {
    for(int i = 0; i < sc.size(); i++)
      if(((Double)sc.elementAt(i)).doubleValue() == val)
        return i;
    return -1;
  }

  static void project(Alignment al, String seqName, Vector poss) {
    if(!seqName.equals("")) {
      int seqNum = al.findSeq(seqName);
      if(seqNum == -1) {
        System.out.println("Error in reference sequence name");
        System.exit(0);
      }
      for (int i = 0; i < poss.size(); i++) {
//        System.out.println(i + ": " + ((Position) poss.elementAt(i)).num);
        ((Position) poss.elementAt(i)).refSeqAA = ((Sequence) al.seqs.elementAt(seqNum)).
                                                  aa(((Position) poss.elementAt(i)).num);
        ((Position) poss.elementAt(i)).refSeqNum = ((Sequence) al.seqs.elementAt(seqNum)).
                                        seqInd(((Position) poss.elementAt(i)).num);
//        System.out.println("refAA: " + ((Position) poss.elementAt(i)).refSeqAA + "; refSeqNum: " + ((Position) poss.elementAt(i)).refSeqNum);
      }
    }
  }

}
