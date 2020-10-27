package from_olga.sdppred;
import java.lang.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Partition {
    Vector groups = new Vector();
    Vector curGroup = new Vector();
    Vector p_i = new Vector();
    boolean log;
    double p, id, a, b;
    double avGroupSize, avGroupID;
    double smallestGroupSize;
    Vector groupID = new Vector();
    int goodColumnNum;
    Vector goodColumnIdx = new Vector();

    Partition() {
      goodColumnNum = 0;
      goodColumnIdx.removeAllElements();
    }

    Partition(Vector indices) {
    }

//    Partition(int[] ind, int height) {
//      get(ind, height);
//    }
//
//    public void get(int[] ind, int height) {
//      for(int i = 0; i < height; i++) {
//        if(ind[i] == 1) group1.addElement(new Integer(i));
//        else group2.addElement(new Integer(i));
//      }
//    }

    public void get(Partition part) {
      groups.removeAllElements();
      for(int i = 0; i < part.groups.size(); i++)
        groups.addElement(new Vector((Vector)(part.groups.elementAt(i))));
      p = part.p;
      groupID.addAll(new Vector(part.groupID));
      id = part.id;
      a = part.a;
      b = part.b;
      goodColumnNum = part.goodColumnNum;
      goodColumnIdx.removeAllElements();
      goodColumnIdx.addAll(part.goodColumnIdx);
      p_i.removeAllElements();
      p_i.addAll(part.p_i);
    }

    Partition(int ind) {

    }

    public void get(int ind) {

    }

    public void getP_i(Vector inf, Vector I_sh, double[] e, double[] sigma) {
      double f;
      double z = 0;
      int greater;
      log = false;
      Vector zs = new Vector();
      Vector is = new Vector();
      p_i.removeAllElements();
//      System.out.println("inf: " + inf.toString());
//      System.out.print("e: ");
//      for(int i = 0; i < e.length; i++) System.out.print(e[i] + ", ");
//      System.out.println("");
//      System.out.print("sigma: ");
//      for(int i = 0; i < sigma.length; i++) System.out.print(sigma[i] + ", ");
//      System.out.println("");
      for(int i = 0; i < goodColumnNum; i++) {
  //        int col = ((Integer)(goodColumnIdx.elementAt(i))).intValue();
        Vector I_sh_i = new Vector();
        for (int k = 0; k < I_sh.size(); k++)
          I_sh_i.addElement(new Double(((Double) ((Vector) I_sh.elementAt(k)).
                                        elementAt(i)).doubleValue()));
//          if(uniform(I_sh_i)) {
        greater = 0;
        for (int j = 0; j < Calculations.iteration; j++) {
          if (((Double) (inf.elementAt(i))).doubleValue() <=
              ((Double) (I_sh_i.elementAt(j))).doubleValue())
            greater++;
            }
        if (greater > Calculations.mju * Calculations.iteration) {
          f = (double) greater / (double) Calculations.iteration;
        } else {
          z = (((Double) inf.elementAt(i)).doubleValue() - e[i]) / sigma[i];
//          z /= avGroupID;
          z /= Math.log(avGroupSize);
          zs.addElement(new Double(z));
          is.addElement(new Integer(i));
//System.out.println(z);          
          if (Normal.probGrater(0, 1, z) == 0.) {
            log = true;
            break;
          } else f = Normal.probGrater(0, 1, z);
        }
//        if (f < 1e-20) {
//          System.out.println("z = " + z + "; f = " + f + "; inf = " +
//                             ((Double) inf.elementAt(i)).doubleValue() +
//                             "; e = " + e[i] + "; sigma = " + sigma[i]);
//          System.out.println("column idx " +
//                             ((Integer) goodColumnIdx.elementAt(i)).intValue());
//        }
        p_i.addElement(new Double(f));
      }
/*      if(!log) {
        Vector ppp_i = new Vector();
        double meanZ = 0.;
        double sigmaZ = 0.;
//        System.out.println("Zs: " + zs.toString());
        for(int i = 0; i < zs.size(); i++) meanZ += ((Double)zs.elementAt(i)).doubleValue();
        meanZ /= zs.size();
        for(int i = 0; i < zs.size(); i++) sigmaZ += (((Double)zs.elementAt(i)).doubleValue() - meanZ) * (((Double)zs.elementAt(i)).doubleValue() - meanZ);
        sigmaZ = Math.sqrt(sigmaZ / (zs.size() - 1));
        for(int i = 0; i < p_i.size(); i++) {
          int isZ = -1;
          for(int j = 0; j < is.size(); j++)
            if(((Integer)is.elementAt(j)).intValue() == i) isZ = j;
          if(isZ == -1) {
            ppp_i.addElement(new Double(((Double)p_i.elementAt(i)).doubleValue()));
          } else {
            ppp_i.addElement(new Double(Normal.probGrater(0, 1, ((((Double)zs.elementAt(isZ)).doubleValue()) - meanZ))));
          }
        }
        p_i.removeAllElements();
        p_i.addAll((Vector)ppp_i.clone());
        System.out.println("Done the nasty thing! meanZ: " + meanZ + "; sigmaZ: " + sigmaZ);
      }*/
      if(log) {
        p_i.removeAllElements();
        for(int i = 0; i < goodColumnNum; i++) {
          greater = 0;
//          int col = ((Integer)(goodColumnIdx.elementAt(i))).intValue();
          Vector I_sh_i = new Vector();
          for(int k = 0; k < I_sh.size(); k++)
            I_sh_i.addElement(new Double(((Double)((Vector)I_sh.elementAt(k)).elementAt(i)).doubleValue()));
            for (int j = 0; j < Calculations.iteration; j++) {
              if (((Double) (inf.elementAt(i))).doubleValue() <=
                  ((Double) (I_sh_i.elementAt(j))).doubleValue())
                greater++;
            }
          if(greater > Calculations.mju * Calculations.iteration) f = Math.log((double) greater / (double) Calculations.iteration);
          else {
            z = ( ( (Double) inf.elementAt(i)).doubleValue() - e[i]) / sigma[i];
//            z /= avGroupID;
            z /= Math.log(avGroupSize);
            f = Normal.probGraterApprox(z);
          }
          p_i.addElement(new Double(f));
        }
      }
//      System.out.println("p_i: " + p_i.toString());
    }

    boolean uniform(Vector vals) {
      int occNum, minOccNum = vals.size();
      boolean [] wasThere = new boolean[vals.size()];
      for(int i = 0; i < vals.size(); i++) wasThere[i] = false;
      for(int i = 0; i < vals.size(); i++) {
        if(!wasThere[i]) {
          occNum = 0;
          for(int j = 0; j < vals.size(); j++)
            if(Math.abs(((Double)(vals.elementAt(j))).doubleValue() - ((Double)(vals.elementAt(i))).doubleValue()) < 0.001) {
              occNum++;
              wasThere[j] = true;
            }
          if(occNum < minOccNum) minOccNum = occNum;
        }
      }
      if(minOccNum > Calculations.mju * Calculations.iteration) return true;
      else return false;
    }

    public double frac(int i) {
      int total = 0;
      for(int j = 0; j < groups.size(); j++) total += ((Vector)(groups.elementAt(j))).size();
      return ((Vector)(groups.elementAt(i))).size() / total;
    }

    public void ab(double alpha, double beta, double A, double B) {
      a = (B * beta - A * goodColumnNum) / (beta * beta - alpha * goodColumnNum);
      b = (A * beta - B * alpha) / (beta * beta - alpha * goodColumnNum);
    }

    public boolean inGroup(int idx, int i) {
      for(int j = 0; j < ((Vector)(groups.elementAt(i))).size(); j++)
        if(((Integer)((Vector)(groups.elementAt(j))).elementAt(j)).intValue() == idx) return true;
      return false;
    }

}
