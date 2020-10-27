package from_olga.activesite;

import java.util.Vector;

import from_olga.conservationscore.Position;
import from_olga.sdppred.SDP;

public class NoStruct {
    public static void main(String[] args) {
       String[] pars = new String[9];
       System.out.println(args[0]);
       pars = getPars(args[0]); //"PF01883-1uwd-A-7.prm");//
       String outName = pars[0].substring(0, pars[0].length() - 4);
       for(int iii = 0; iii < pars.length; iii++)
         System.out.println("" + pars[iii]);
       from_olga.sdppred.SDPs sdps = new from_olga.sdppred.SDPs();
       sdps = from_olga.treetrimming.trim.trimTree(pars[1], pars[0],  outName + ".sdpin", outName + ".sdps", pars[2], pars[0]);
       from_olga.sdppred.SDPpred.outRefSDPs(sdps, outName + ".sdps", 0);
       from_olga.sdppred.SDPpred.outRefSDPsFull(sdps, outName + ".sdpsfull", 0);
       //----check if SDPs worked
       System.out.println("SDPs for sequence " + sdps.refSequence);
       System.out.println("Alignment position\tPosition in " + sdps.refSequence + "\tMutual information\tZ-probability");
       for(int i = 0; i < sdps.pos.size(); i++) {
         System.out.println("" + ((SDP)sdps.pos.elementAt(i)).ind + "\t" + ((SDP)sdps.pos.elementAt(i)).refInd + "\t" + ((SDP)sdps.pos.elementAt(i)).inf + "\t" + ((SDP)sdps.pos.elementAt(i)).z);
       }
       //----end check SDPs
       Vector cps = new Vector();
       cps.addAll(from_olga.conservationscore.CSmain.getCP(pars[0], pars[2]));
       from_olga.conservationscore.CSmain.outSignScores(cps, outName + ".cps", pars[1], 0);
       //----check if CPs worked
       System.out.println("CPs for sequence " + pars[1]);
       System.out.println("Alignment position\tPosition in " + pars[1] + "\tConservation score");
       for(int i = 0; i < cps.size(); i++) {
         System.out.println("" + ((Position)cps.elementAt(i)).num + "\t" + ((Position)cps.elementAt(i)).refSeqNum + "\t" + ((Position)cps.elementAt(i)).consScore);
       }
       //----end check CPs
    }

    public static String[] getPars(String prmName) {
        String[] res = new String[4];
        a_filereader f = new a_filereader(prmName);
        res[0] = ""; //"data/";
        res[3] = "./results/";
        for(int i = 0; i < f.data.size(); i++) {
          String curr = new String((String)f.data.elementAt(i));
          if(curr.split("\\s+")[0].equals("RSQ")) {
              res[2] = curr.split("\\s+")[1];
          } else if(curr.split("\\s+")[0].equals("ALN")) {
            res[0] = curr.split("\\s+")[1];
          } else if(curr.split("\\s+")[0].equals("TRE")) {
            res[1] = curr.split("\\s+")[1];
          } else {}
        }
        return res;
      }

}
