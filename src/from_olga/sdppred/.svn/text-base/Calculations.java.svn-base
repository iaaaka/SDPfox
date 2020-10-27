package from_olga.sdppred;
import java.util.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Calculations {
    //Static functions for calculations

    static double[] m30, m40, m50, m60, m70, m80, m90;
    static double kappa = 1.; //.5;
    static int iteration = 3000; //100;
    static double mju = .1;
    static double gapPrecentage = .3;

    static double myLog(double arg) {
//      System.out.println("Been in myLog!");
      if(arg < 1e-323) return 0.;
      return Math.log(arg);
    }

    public static void getMatrices() {
      a_filereader f;
      String s = new String();
      String[] splitted;
      //m30
      f = new a_filereader("matrices/m30.lp");
      m30 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m30[i * 20 + j] = v.doubleValue();
        }
      }
      //m40
      f = new a_filereader("matrices/m40.lp");
      m40 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m40[i * 20 + j] = v.doubleValue();
        }
      }
      //m50
      f = new a_filereader("matrices/m50.lp");
      m50 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m50[i * 20 + j] = v.doubleValue();
        }
      }
      //m60
      f = new a_filereader("matrices/m60.lp");
      m60 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m60[i * 20 + j] = v.doubleValue();
        }
      }
      //m70
      f = new a_filereader("matrices/m70.lp");
      m70 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m70[i * 20 + j] = v.doubleValue();
        }
      }
      //m80
      f = new a_filereader("matrices/m80.lp");
      m80 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m80[i * 20 + j] = v.doubleValue();
        }
      }
      //m90
      f = new a_filereader("matrices/m90.lp");
      m90 = new double[400];
      s = (String)f.data.elementAt(0);
      for(int i = 0; i < 20; i++) {
        s = (String)f.data.elementAt(i + 3);
        splitted = s.split("\\s");
        for(int j = 0; j < 20; j++) {
          Double v = new Double(splitted[j + 1]);
          m90[i * 20 + j] = v.doubleValue();
        }
      }
    }

    static int[] getRandom(int n, int m) {
      int[] result = new int[m];
      int k;
      boolean flag;
      //System.out.println("getRandon entered");
      for(int i = 0; i < m; i++) {
        do {
          flag = true;
          k = (int)(Math.random() * n);
          for(int j = 0; j <= i - 1; j++)
            if(result[j] == k) flag = false;
        } while(!flag);
        result[i] = k;
      }
      return result;
    }

    static double matrix(double id, int alpha, int beta) {
      if(id > 0.9) return m90[beta * AminoAcids.alphLen + alpha];
      if(id > 0.8) return m80[beta * AminoAcids.alphLen + alpha];
      if(id > 0.7) return m70[beta * AminoAcids.alphLen + alpha];
      if(id > 0.6) return m60[beta * AminoAcids.alphLen + alpha];
      if(id > 0.5) return m50[beta * AminoAcids.alphLen + alpha];
      if(id > 0.4) return m40[beta * AminoAcids.alphLen + alpha];
      return m30[beta * AminoAcids.alphLen + alpha];
    }

    static double Pseudocount(char[] column, Vector seqs, int N, double id, int aa) {
      int[] counter = new int[AminoAcids.alphLen];
      int a;
      double s = 0;
      for(int i = 0; i < seqs.size(); i++) {
        a = AminoAcids.index(column[((Integer)(seqs.elementAt(i))).intValue()]);
        if(a >= 0) counter[a]++;
      }
      for(int i = 0; i < AminoAcids.alphLen; i++)
        s += counter[i] * matrix(id, aa, i);
      return (double)counter[aa] + kappa * s / Math.sqrt((double)N);
    }

    static boolean gapGroups(char[] column, Partition part) {
      int gapCounter;
      for(int i = 0; i < part.groups.size(); i++) {
        gapCounter = 0;
        for (int j = 0; j < ((Vector) (part.groups.elementAt(i))).size(); j++)
          if (column[((Integer) (((Vector) (part.groups.elementAt(i))).elementAt(
              j))).intValue()] == '-')
            gapCounter++;
        if ((double) gapCounter /
            (double) ((Vector) (part.groups.elementAt(i))).size() > gapPrecentage) return true;
      }
      return false;
    }

    static boolean gapGroups(char[] column, Vector groups) {
      int gapCounter;
      for(int i = 0; i < groups.size(); i++) {
        gapCounter = 0;
        for (int j = 0; j < ((Vector) (groups.elementAt(i))).size(); j++)
          if (column[((Integer) (((Vector) (groups.elementAt(i))).elementAt(
              j))).intValue()] == '-')
            gapCounter++;
        if ((double) gapCounter /
            (double) ((Vector) (groups.elementAt(i))).size() > gapPrecentage) return true;
      }
      return false;
    }

    static double getInf(char[] column, int colLen, Partition part) {
      Vector whole = new Vector();
      double n_a, n_ai, secTerm = 0., inf = 0.;
      for(int i = 0; i < colLen; i++) whole.addElement(new Integer(i));
      if(gapGroups(column, part)) return -1.;
//      if(nonCanonicalAA(column)) return -2;
      for(int a = 0; a < AminoAcids.alphLen; a++) {
        n_a = Pseudocount(column, whole, colLen, part.id, a);
        for(int i = 0; i < part.groups.size(); i++) {
          n_ai = Pseudocount(column, ((Vector) (part.groups.elementAt(i))),
                             colLen, ((Double)(part.groupID.elementAt(i))).doubleValue(), a);
          inf += n_ai *myLog(n_ai / n_a);
        }
      }
      if(inf == 0) return 0;
      inf /= (double)(colLen + kappa * Math.sqrt((double)colLen));
      for(int i = 0; i < part.groups.size(); i++)
        secTerm += ((Vector)(part.groups.elementAt(i))).size() * myLog(((Vector)(part.groups.elementAt(i))).size());
      inf += (-secTerm / (double)colLen + myLog(colLen));
      return inf;
    }
    
    static boolean nonCanonicalAA(char[] column) {
    	boolean res = false;
    	for(int i = 0; i < column.length; i++) {
    		if(AminoAcids.index(column[i]) < 0) {
    			res = true;
    		}
    	}
    	return res;
    }

    public static Vector mi(Alignment al) {
      Vector result = new Vector();
      Vector resInf = new Vector();
      Vector resIdx = new Vector();
      int num = 0;
      double inf;
      char[] column = new char[al.thickness];
      resInf.removeAllElements();
      resIdx.removeAllElements();
      result.removeAllElements();
      for(int i = 0; i < al.length; i++) {
        column = al.getColumn(i);
        if((al.goodColumn(i))&&(!fullyConserved(column, al.thickness))) {
          inf = getInf(column, al.thickness, al.given);
          if(inf >= 0.) {
            num++;
            resIdx.addElement(new Integer(i));
            resInf.addElement(new Double(inf));
          }
        }
      }
      result.addElement(resInf);
      result.addElement(resIdx);
      result.addElement(new Integer(num));
      return result;
    }

    public static Vector zs(Vector inf, Vector goodColumnIdx, double[] e, double[] sigma) {
      Vector result = new Vector();
      Vector zRes = new Vector();
      Vector idxRes = new Vector();
      double z;
      result.removeAllElements();
      for(int i = 0; i < inf.size(); i++) {
        if((sigma[i] > .01)&&(e[i] > 0.001)) {
          z = ( ( (Double) inf.elementAt(i)).doubleValue() - e[i]) / sigma[i];
          zRes.addElement(new Double(z));
          idxRes.addElement(new Integer(((Integer)(goodColumnIdx.elementAt(i))).intValue()));
        }
      }
      result.addElement(zRes);
      result.addElement(idxRes);
      return result;
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

    public static double expDistr(double x) {
      if(x <= -1.) return 1.;
      return Math.exp(-x - 1);
    }

    public static double[] p(Vector z) {
      double p, q, prod, min = 0.;
      int minIdx = 0;
      double[] result = new double[2];
      Vector items = new Vector();
      Vector prods = new Vector();
      Vector ps = new Vector();
      Collections.sort(z, new  cmp());  //some sorting of z
      if(Normal.probGrater(0, 1, ((Double)z.elementAt(0)).doubleValue()) > 0.) {
        min = 1.;
        for (int i = 0; i < z.size(); i++) {
          p = Normal.probGrater(0, 1, ( (Double) z.elementAt(i)).doubleValue());
          ps.addElement(new Double(p));
          q = 1. - p;
          items.removeAllElements();
          for (int j = 0; j <= z.size() - i; j++) {
            prod = 1.;
            for (int n = 1; n <= z.size() - j; n++) prod *= (p * (n + j) / n);
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
            if (minIdx == z.size() - 1) {
              System.out.println("Moderate scores, prods: " + prods.toString());
              System.out.println(" scores:" + z.toString());
              System.out.println(" probabilities" + ps.toString());
            }
          }
        }
        //System.out.println("k = " + i + " prod = " + prod);
        if(min > 0.) min = myLog(min);
      }
      if((min == 0.) || (Normal.probGrater(0, 1, ((Double)z.elementAt(0)).doubleValue()) == 0.)){
        min = 0.;
        for (int i = 0; i < z.size(); i++) {
          p = Normal.probGraterApprox(((Double)z.elementAt(i)).doubleValue());
          if(i > 0) {
            prod = logC(z.size(), z.size() - i + 1);
            prod  += (i - 1) * p;
          }
          else prod = 0.;
          if (prod < min) {
            min = prod;
            minIdx = i;
            if (minIdx == z.size() - 1)
              System.out.println("MinIdx = n - 1 in log part");
          }
        }
      }
      result[0] = min;
      result[1] = (double)minIdx;
      if(minIdx == z.size() - 1) System.out.println("Prob:" + min + " Z-scores: " + z.toString());
      return result;
    }

    public static Vector p_p(Vector pp_i, boolean log) {
      double p, q, prod, min = 0.;
      int minIdx = 0;
      Vector result = new Vector();
      Vector resProbs = new Vector();
      Vector items = new Vector();
      Vector p_i = new Vector();
      p_i.addAll(pp_i);
      Collections.sort(p_i, new cmp());  //some sorting of z
//      System.out.println("" + p_i.toString());
      if(!log) {
        min = 1.;
        for (int i = 0; i < p_i.size(); i++) {
          p = ( (Double) p_i.elementAt(i)).doubleValue();
          q = 1. - p;
          items.removeAllElements();
          for (int j = 0; j <= p_i.size() - i; j++) {
            prod = 1.;
            for (int n = 1; n <= p_i.size() - j; n++) prod *= (p * (n + j) / n);
            for (int n = 1; n <= j; n++) prod *= q;
            items.addElement(new Double(prod));
          }
          Collections.sort(items); //some sorting of items
          prod = 0.;
//          System.out.println("" + items.toString());
          for (int j = 0; j < items.size(); j++) prod +=
              ( (Double) items.elementAt(j)).doubleValue();
          //System.out.println("prod = " + prod);
          resProbs.addElement(new Double(prod));
          if ((prod < min) && (i < (double)p_i.size() * .25)) {
            min = prod;
            minIdx = i;
//            System.out.println("We're in bidder part!");
//            System.out.println("min = " + min + " minIdx = " + minIdx);
          }
        }
        if(min > 0.) {
          min = myLog(min);
//          System.out.println("min to log");
//          System.out.println("Resulting probabilities: "+ resProbs.toString());
        }
      }
      if((min == 0.) || (log)){
        min = 0.;
        for (int i = 0; i < p_i.size(); i++) {
          p = ((Double)p_i.elementAt(i)).doubleValue();
          if(i > 0) {
            prod = logC(p_i.size(), p_i.size() - i + 1);
            if(log) prod  += (i - 1) * p;
            else prod += (i - 1) * myLog(p);
          }
          else prod = 0.;
          resProbs.addElement(new Double(prod));
          if (prod < min) {
            min = prod;
            minIdx = i;
//            System.out.println("min = " + min + " minIdx = " + minIdx);
            if (minIdx == p_i.size() - 1)
              System.out.println("MinIdx = n - 1 in log part");
          }
        }
      }
      result.addElement(new Double(min));
      result.addElement(new Integer(minIdx));
      result.addElement(new Vector(resProbs));
      if(minIdx == p_i.size() - 1) System.out.println("Prob:" + min + " probs: " + p_i.toString());
      return result;
    }

    static boolean isInRandom(int[] random, int size, int val) {
      for(int i = 0; i < size; i++)
        if(random[i] == val)
          return true;
      return false;
    }

    static boolean fullyConserved(char[] column, int len) {
      for(int i = 1; i < len; i++) if(column[i] != column[0]) return false;
      return true;
    }

    static Vector meanShuffle(Vector I_sh) {
      Vector result = new Vector();
      for(int i = 0; i < ((Vector)I_sh.elementAt(0)).size(); i++) {
        double sum = 0;
        for(int j = 0; j < I_sh.size(); j++)
          sum += ((Double)((Vector)I_sh.elementAt(j)).elementAt(i)).doubleValue();
        result.addElement(new Double(sum / I_sh.size()));
      }
      return result;
    }

    static Vector sigmaShuffle(Vector I_sh) {
      Vector result = new Vector();
      for(int i = 0; i < ((Vector)I_sh.elementAt(0)).size(); i++) {
        double sum = 0;
        for(int j = 0; j < I_sh.size(); j++)
          sum += ((Double)((Vector)I_sh.elementAt(j)).elementAt(i)).doubleValue();
        double ave = sum / I_sh.size();
        sum = 0.;
        for(int j = 0; j < I_sh.size(); j++)
          sum += (((Double)((Vector)I_sh.elementAt(j)).elementAt(i)).doubleValue() - ave) * (((Double)((Vector)I_sh.elementAt(j)).elementAt(i)).doubleValue() - ave);
        result.addElement(new Double(Math.sqrt(sum / (I_sh.size() - 1))));
      }
      return result;
    }

}
