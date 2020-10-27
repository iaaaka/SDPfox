package from_olga.sdppred;
import java.lang.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class SDPs {
  public double p;
  public double a, b;
  public double A, B, C, D;
  public int L;
  public boolean log;
  public String refSequence = new String();
  public Vector pos = new Vector();
  public Vector I = new Vector();
  public Vector I_sh = new Vector();
  public Vector I_sh_sigma = new Vector();

  private int search(Vector where, double what) {
    for(int i = 0; i < where.size(); i++)
      if(((Double)where.elementAt(i)).doubleValue() == what) return i;
    return -1;
  }

  private int searchInt(Vector where, int what) {
    for(int i = 0; i < where.size(); i++)
      if(((Integer)where.elementAt(i)).intValue() == what) return i;
    return -1;
  }

  public SDPs(Alignment al, Vector z, Vector inf_sh, Vector inf_sh_sigma, double[] e, double[] sigma, Partition part, double alpha, double beta, double A, double B, int LL, int quant) {
    get(al, z, inf_sh, inf_sh_sigma, e, sigma, part, alpha, beta, A, B, LL, quant);
  }

  public void get(Alignment al, Vector inf, Vector inf_sh, Vector inf_sh_sigma, double[] e, double[] sigma, Partition part, double alpha, double beta, double AA, double BB, int LL, int quant) {
    //System.out.println(z.toString());
    Vector sorted = (Vector)part.p_i.clone();
    Collections.sort(sorted, new cmp());
    pos.removeAllElements();
    //System.out.println(sorted.toString());
    //System.out.println(z.toString());
    //System.out.println(al.goodColumnIdx.toString());
    char[] column = new char[al.thickness];
    int idx, idx1, idx2;
    I.removeAllElements();
    I_sh.removeAllElements();
    I_sh_sigma.removeAllElements();
    I.addAll(inf);
    I_sh.addAll(inf_sh);
    I_sh_sigma.addAll(inf_sh_sigma);
    log = part.log;
    L = LL;
    p = part.p;
    a = part.a;
    b = part.b;
    A = AA;
    B = alpha;
    C = BB;
    D = beta;
    for(int i = 0; i < quant; i++) {
      idx = search(part.p_i, ((Double)sorted.elementAt(i)).doubleValue());
//      System.out.println("z = " + ((Double)sorted.elementAt(i)).doubleValue());
//      System.out.println("z.length = " + z.size());
//      System.out.println("GoodColumnNum = " + part.goodColumnNum);
//      System.out.println("what = " + ((Double)sorted.elementAt(i)).doubleValue());
      idx1 = ((Integer)part.goodColumnIdx.elementAt(idx)).intValue();
//      idx1 = ((Integer)(zIdx.elementAt(idx))).intValue();
//      idx2 = searchInt(part.goodColumnIdx, idx1);
//      System.out.println("idx1 = " + idx1);
      column = al.getColumn(idx1);
      pos.addElement(new SDP(((Double)inf.elementAt(idx)).doubleValue(), ((Double)part.p_i.elementAt(idx)).doubleValue(), e[idx], sigma[idx], idx1, part, column));
    }
  }

  public SDPs() {
    p = 0.;
  }

  public void copy(SDPs sdps) {
    p = sdps.p;
    pos = (Vector)(sdps.pos).clone();
    refSequence = sdps.refSequence;
  }

  public void project(RefAlignment al, String seqName) {
    refSequence = seqName;
    if(!seqName.equals("")) {
      int seqNum = al.findSeq(seqName);
      if(seqNum == -1) {
        System.out.println("Error in reference sequence name");
//        System.exit(0);
      } else {
        for (int i = 0; i < pos.size(); i++) {
          ( (SDP) pos.elementAt(i)).refAA = ( (Sequence) al.seqs.elementAt(
              seqNum)).aa( ( (SDP) pos.elementAt(i)).ind);
          ( (SDP) pos.elementAt(i)).refInd = ( (Sequence) al.seqs.elementAt(
              seqNum)).
              seqInd( ( (SDP) pos.elementAt(i)).ind);
        }
      }
    }
  }

  public void project(Alignment al, String seqName) {
    refSequence = seqName;
    if(!seqName.equals("")) {
      int seqNum = al.findSeq(seqName);
      if(seqNum == -1) {
        System.out.println("Error in reference sequence name");
//        System.exit(0);
      } else {
        for (int i = 0; i < pos.size(); i++) {
//          System.out.println(i + ": " +  ( (SDP) pos.elementAt(i)).ind + " in sequence  " + ( (Sequence) al.seqs.elementAt(seqNum)));
          ( (SDP) pos.elementAt(i)).refAA = ( (Sequence) al.seqs.elementAt(
              seqNum)).aa( ( (SDP) pos.elementAt(i)).ind);
          ( (SDP) pos.elementAt(i)).refInd = ( (Sequence) al.seqs.elementAt(
              seqNum)).
              seqInd( ( (SDP) pos.elementAt(i)).ind);
        }
      }
    }
  }

}
