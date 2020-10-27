package from_olga.activesite;

import java.util.*;
import java.io.*;
import from_olga.conservationscore.Position;
import from_olga.pdbparser.AminoAcid;
import from_olga.pdbparser.partialVolume;
import from_olga.sdppred.SDP;
import from_olga.sdppred.SDPs;



/**
 * <p>Title: activeSite</p>
 *
 * <p>Description: Active site finder that utilizes 3D structure and SDPs</p>
 *
 * <p>Copyright: Copyright (c) 2004</p>
 *
 * <p>Company: </p>
 *
 * @author Olga V. Kalinina
 * @version 1.0
 */
public class finder {

    static final double step = 1.; //in angstroms
    static final double rad = 10.; //in angstroms
    static double lambda = 1.; //0.5; //cost of a CP in terms of SDP
    static final double D = 15.; //influece distance
    static final double R = 5.; //main radius
    static final boolean Ranged = false; //if the weight of each vertex of the graph should be weighted according to its range

    public static void main(String[] args) {
       String[] pars = new String[9];
//       pars[0] = "data\\"; //location of the infiles
//       pars[1] = "PURR_ECOLI"; //reference sequence, RF - 2|12517417|gb|AAG58019.1|
//       pars[2] = "1bdh.pdb"; //pdb
//       pars[3] = "results\\"; // destination of the oufiles
//       pars[4] = "LacI75.gde"; //alignment
//       pars[5] = "LacI75.tre"; //tree
//       pars[6] = "1"; //reference sequence indent
//       pars[7] = "A"; //chain
//       pars[8] = "LacI75.gde"; //reference alignment
//       args = new String[1]; args[0] = "data/PF01725-1b78-A-11.prm";
       System.out.println(args[0]);
       String outName = new String((args[0].split("/"))[1]);
       outName = outName.substring(0, outName.length() - 4);
       pars = getPars(args[0]); //"PF01883-1uwd-A-7.prm");//
       for(int iii = 0; iii < pars.length; iii++)
         System.out.println("" + pars[iii]);
       int refSeqIndent = Integer.parseInt(pars[6]) - 1;
       from_olga.pdbparser.Structure struct = new from_olga.pdbparser.Structure(pars[2]);
       //----check if sructure read
       System.out.println("Structure title: " + struct.title);
       //----end check Structure
       double[] cube = new double[6];
       cube = struct.getCube();
       Point min = new Point(cube[0], cube[1], cube[2]);
       Point max = new Point(cube[3], cube[4], cube[5]);
       //----check if min, max calculated
       System.out.println("Min: " + min.x + "; " + min.y + "; " + min.z);
       System.out.println("Max: " + max.x + "; " + max.y + "; " + max.z);
       //----end check min, max
       from_olga.sdppred.SDPs sdps = new from_olga.sdppred.SDPs();
       sdps = from_olga.treetrimming.trim.trimTree(pars[5], pars[4], pars[3] + outName + ".sdpin", pars[3] + outName + ".sdps", pars[1], pars[8]);
       from_olga.sdppred.SDPpred.outRefSDPs(sdps, pars[3] + outName + ".sdps", refSeqIndent);
       from_olga.sdppred.SDPpred.outRefSDPsFull(sdps, pars[3] + outName + ".sdpsfull", refSeqIndent);
       if((sdps.p < 0) && (sdps.p > -10)) lambda = 1.;
       //----check if SDPs worked
       System.out.println("SDPs for sequence " + sdps.refSequence);
       System.out.println("Alignment position\tPosition in " + sdps.refSequence + "\tMutual information\tZ-probability");
       for(int i = 0; i < sdps.pos.size(); i++) {
         System.out.println("" + ((SDP)sdps.pos.elementAt(i)).ind + "\t" + ((SDP)sdps.pos.elementAt(i)).refInd + "\t" + ((SDP)sdps.pos.elementAt(i)).inf + "\t" + ((SDP)sdps.pos.elementAt(i)).z);
       }
       //----end check SDPs
       Vector cps = new Vector();
       cps.addAll(from_olga.conservationscore.CSmain.getCP(pars[0] + pars[4], pars[1]));
       from_olga.conservationscore.CSmain.outSignScores(cps, pars[3] + outName + ".cps", pars[1], refSeqIndent);
       //----check if CPs worked
       System.out.println("CPs for sequence " + pars[1]);
       System.out.println("Alignment position\tPosition in " + pars[1] + "\tConservation score");
       for(int i = 0; i < cps.size(); i++) {
         System.out.println("" + ((Position)cps.elementAt(i)).num + "\t" + ((Position)cps.elementAt(i)).refSeqNum + "\t" + ((Position)cps.elementAt(i)).consScore);
       }
       //----end check CPs
//----searching in the structure by a sphere
/*       Point curr = new Point();
       Point maxPoint = new Point();
       Vector maxPv = new Vector();
       double maxFrac = 0., currSDPVol, currCPVol, totalVol;
       for(int i = 0; i < (int)((max.x - min.x) / step); i++) {
           curr.x = min.x + i * step;
           for(int j = 0; j < (int)((max.y - min.y) / step); j++) {
               curr.y = min.y + j * step;
               for(int k = 0; k < (int)((max.z - min.z) / step); k++) {
                   curr.z = min.z + k * step;
                   Vector pv = new Vector();
                   pv = struct.findPV(curr.x, curr.y, curr.z, rad);
                   for(int xxx = 0; xxx < pv.size(); xxx++) {
                       //----check if pv ok
//                       System.out.println("" + ((pdbparser.partialVolume)pv.elementAt(xxx)).aa.name + " " + ((pdbparser.partialVolume)pv.elementAt(xxx)).aa.number + " " + ((pdbparser.partialVolume)pv.elementAt(xxx)).volumeFrac);
                       //----end check pv
                       }
//                       System.out.println(i + ":" + j + ":" + k);
                       currSDPVol = findSDPFrac(pv, sdps);
                       currCPVol = findCPFrac(pv, cps);
                       totalVol = getTotalVol(pv);
                       if((currSDPVol + lambda * currCPVol) / totalVol > maxFrac) {
                           maxFrac = (currSDPVol + lambda * currCPVol) / totalVol;
                           maxPoint.copy(curr);
                           maxPv.removeAllElements();
                           maxPv.addAll(pv);
                   }
               }
           }
       }
       for(int xxx = 0; xxx < maxPv.size(); xxx++) {
           //----check if pv ok
                       System.out.println("" + ((pdbparser.partialVolume)maxPv.elementAt(xxx)).aa.name + " " + ((pdbparser.partialVolume)maxPv.elementAt(xxx)).aa.number + " " + ((pdbparser.partialVolume)maxPv.elementAt(xxx)).volumeFrac);
           //----end check pv
           }
*///----end searching by a sphere
//----clustering
      //seeking for clarity, we restrict here only to one protein chain
      Vector edges = new Vector();
      Vector signRes = new Vector();
      for(int i = 0; i < sdps.pos.size(); i++)
        signRes.addElement(new Integer(((SDP)sdps.pos.elementAt(i)).refInd + refSeqIndent));
      for(int i = 0; i < cps.size(); i++)
        signRes.addElement(new Integer(((Position)cps.elementAt(i)).refSeqNum + refSeqIndent));
      int ii = 0;
      while(ii < signRes.size()) {
        int jj = ii + 1;
        while(jj < signRes.size()) {
//          System.out.println(((Integer) signRes.elementAt(ii)).intValue() + "<->" + ((Integer) signRes.elementAt(jj)).intValue());
          if (((Integer) signRes.elementAt(ii)).intValue() ==
              ((Integer) signRes.elementAt(jj)).intValue()) {
            signRes.removeElementAt(jj);
            jj--;
          }
          jj++;
        }
        ii++;
      }

      System.out.println("signRes: " + signRes.toString());
      int it = 0;
      while(it < signRes.size()) {
        if(!struct.isThere(((Integer)signRes.elementAt(it)).intValue(), pars[7]))
          signRes.removeElementAt(it);
        else it++;
      }
//      System.out.println("signRes: " + signRes.toString());
      for(int i = 0; i < signRes.size(); i++)
        for(int j = i + 1; j < signRes.size(); j++) {
//          System.out.println("i = " + i + " j = " + j);
          double weight;
//          System.out.println("Weighting " + ((Integer)signRes.elementAt(i)).intValue() + " and " + ((Integer)signRes.elementAt(j)).intValue());
          if(struct.dist(((Integer)signRes.elementAt(i)).intValue(), ((Integer)signRes.elementAt(j)).intValue(), pars[7]) < D)
            weight = R / struct.dist(((Integer)signRes.elementAt(i)).intValue(), ((Integer)signRes.elementAt(j)).intValue(), pars[7]);
          else weight = 0.;
          edges.addElement(new Edge(weight, ((Integer)signRes.elementAt(i)).intValue(), ((Integer)signRes.elementAt(j)).intValue()));
        }
      //all edges of the graph defined upto this end
      Vector bestCluster = new Vector();
      bestCluster.addAll(cluster(edges, signRes, cps, sdps, refSeqIndent));
      System.out.println("Best cluster: " + bestCluster.toString());
      outBestCluster(bestCluster, pars[3] + pars[1], pars[3] + outName + ".clust");
//----end clustering
//----look up the second best cluster
      Vector signRes2 = new Vector();
      for(int i = 0; i < signRes.size(); i++) {
        boolean isInBest = false;
        for(int j = 0; j < bestCluster.size(); j++)
          if(((Integer)signRes.elementAt(i)).intValue() == ((Integer)bestCluster.elementAt(j)).intValue())
            isInBest = true;
        if(!isInBest) signRes2.addElement(new Integer(((Integer)signRes.elementAt(i)).intValue()));
      }
      Vector edges2 = new Vector();
      for(int i = 0; i < signRes2.size(); i++)
        for(int j = i + 1; j < signRes2.size(); j++) {
          double weight;
          if(struct.dist(((Integer)signRes2.elementAt(i)).intValue(), ((Integer)signRes2.elementAt(j)).intValue(), pars[7]) < D)
            weight = R / struct.dist(((Integer)signRes2.elementAt(i)).intValue(), ((Integer)signRes2.elementAt(j)).intValue(), pars[7]);
          else weight = 0.;
          edges2.addElement(new Edge(weight, ((Integer)signRes2.elementAt(i)).intValue(), ((Integer)signRes2.elementAt(j)).intValue()));
        }
      //all edges of the graph defined upto this end
      Vector bestCluster2 = new Vector();
      bestCluster2.addAll(cluster(edges2, signRes2, cps, sdps, refSeqIndent));
      System.out.println("Second best cluster: " + bestCluster2.toString());
      outBestCluster(bestCluster2, pars[3] + pars[1], pars[3] + outName + ".clust2");
//----end second best
    }

    static Vector cluster(Vector edges, Vector signRes, Vector cps, SDPs sdps, int refSeqIndent) {
      Vector mjus = new Vector();
      Vector clustLayers = new Vector();
      Vector currClust = new Vector();
      Vector result = new Vector();
      currClust.addAll(signRes);
      Collections.sort(currClust);
      while(currClust.size() > 0) {
        clustLayers.addElement(new Vector(currClust));
        double mjuMin = mju(((Integer)currClust.elementAt(0)).intValue(), currClust, edges, cps, sdps, refSeqIndent);
        for(int i = 1; i < currClust.size(); i++)
          if (mju(((Integer) currClust.elementAt(i)).intValue(), currClust, edges, cps, sdps, refSeqIndent) < mjuMin)
            mjuMin = mju(((Integer) currClust.elementAt(i)).intValue(), currClust, edges, cps, sdps, refSeqIndent);
        for(int i = 0; i < currClust.size(); i++)
          if(mju(((Integer) currClust.elementAt(i)).intValue(), currClust, edges, cps, sdps, refSeqIndent) == mjuMin)
            currClust.removeElementAt(i);
        mjus.addElement(new Double(mjuMin));
//        System.out.println("current cluster size is " + currClust.size() + "; mju is " + mjuMin + "; \ncurrent cluster is " + currClust.toString());
      }
      double maxMju = 0.;
      System.out.println("mjus: " + mjus.toString());
      for(int i = 0; i < mjus.size(); i++) {
        if(((Double)mjus.elementAt(i)).doubleValue() > maxMju) {
          result.removeAllElements();
          result.addAll(new Vector((Vector)clustLayers.elementAt(i)));
          maxMju = ((Double)mjus.elementAt(i)).doubleValue();
        }
      }
      return result;
    }

    static double mju(int from, Vector to, Vector dists, Vector cp, SDPs sdps, int refSeqIndent) {
      double result = 0.;
      for(int i = 0; i < to.size(); i++)
        for(int j = 0; j < dists.size(); j++)
          result += ((Edge)dists.elementAt(j)).getWeight(from, ((Integer)to.elementAt(i)).intValue());
      if(inCP(cp, from - refSeqIndent)) {
        result *= lambda;
        if(Ranged) {
          result *= (double) (cp.size() - rangeCP(cp, from - refSeqIndent)) / (double) cp.size();
        } else {}
      } else {
        if(Ranged) {
          result *= (double) (sdps.pos.size() - rangeSDP(sdps, from - refSeqIndent)) /
              (double) sdps.pos.size();
        } else {}
      }
      return result;
    }

    static boolean inCP(Vector cp, int what) {
      for(int i = 0; i < cp.size(); i++)
        if(((Position)cp.elementAt(i)).refSeqNum == what)
          return true;
      return false;
    }

    static int rangeCP(Vector cp, int what) {
      for(int i = 0; i < cp.size(); i++)
        if(((Position)cp.elementAt(i)).refSeqNum == what)
          return i;
      return 0;
    }

    static int rangeSDP(SDPs sdps, int what) {
      for(int i = 0; i < sdps.pos.size(); i++)
        if(((SDP)sdps.pos.elementAt(i)).refInd == what)
          return i;
      return 0;
    }

    static double findSDPFrac(Vector pv, SDPs sdps) {
        double result = 0.;
        for(int i = 0; i < pv.size(); i++)
            if(thereIs(sdps, ((partialVolume)pv.elementAt(i)).aa))
                result += ((partialVolume)pv.elementAt(i)).volumeFrac;
        return result;
    }

    static double findCPFrac(Vector pv, Vector cps) {
        double result = 0.;
        for(int i = 0; i < pv.size(); i++)
            if(thereIs(cps, ((partialVolume)pv.elementAt(i)).aa))
                result += ((partialVolume)pv.elementAt(i)).volumeFrac;
        return result;
    }

    static boolean thereIs(SDPs sdps, AminoAcid aa) {
        for(int i = 0; i < sdps.pos.size(); i++)
            if(((from_olga.sdppred.SDP)sdps.pos.elementAt(i)).refInd == aa.number)
                return true;
        return false;
    }

    static boolean thereIs(Vector cps, AminoAcid aa) {
        for(int i = 0; i < cps.size(); i++)
            if(((Position)cps.elementAt(i)).refSeqNum == aa.number)
                return true;
        return false;
    }

    static double getTotalVol(Vector pv) {
        double result = 0.;
        for(int i = 0; i < pv.size(); i++)
            result += ((partialVolume)pv.elementAt(i)).volumeFrac;
        return result;
    }

    static void outBestCluster(Vector clust, String seqName, String fname) {
      try{
        OutputStream f = new FileOutputStream(fname);
        a_filewriter.Write(f, "Cluster, residue numbering by " + seqName + "\n");
        for(int i = 0; i < clust.size(); i++)
          a_filewriter.Write(f, i + "\t" + ((Integer)clust.elementAt(i)).intValue() + "\n");
        f.close();
      } catch (Exception e) {
        System.out.println("File opening exception in finder");
        System.exit(1);
      }
    }

    public static String[] getPars(String prmName) {
      String[] res = new String[9];
      a_filereader f = new a_filereader(prmName);
      res[0] = ""; //"data/";
      res[3] = "./results/";
      for(int i = 0; i < f.data.size(); i++) {
        String curr = new String((String)f.data.elementAt(i));
        if(curr.split("\\s+")[0].equals("RSQ")) {
          res[1] = curr.split("\\s+")[1];
        } else if(curr.split("\\s+")[0].equals("PDB")) {
          res[2] = curr.split("\\s+")[1];
        } else if(curr.split("\\s+")[0].equals("ALN")) {
          res[4] = curr.split("\\s+")[1];
        } else if(curr.split("\\s+")[0].equals("TRE")) {
          res[5] = curr.split("\\s+")[1];
        } else if(curr.split("\\s+")[0].equals("RSI")) {
          res[6] = curr.split("\\s+")[1];
        } else if(curr.split("\\s+")[0].equals("PDC")) {
          res[7] = curr.split("\\s+")[1];
        } else if(curr.split("\\s+")[0].equals("REF")) {
          res[8] = curr.split("\\s+")[1];
        } else {}
      }
      return res;
    }
}
