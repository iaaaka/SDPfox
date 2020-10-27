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

public class SDP {
  public double inf, z, e, sigma;
  public int ind, refInd;
  public char refAA;
  public Vector groups = new Vector();
  public Vector curGroup = new Vector();

  public SDP(double iinf, double zz, double ee, double ssigma, int iind, Partition part, char[] column) {
    get(iinf, zz, ee, ssigma, iind, part, column);
    refAA = ' ';
  }

  public void get(double iinf, double zz, double ee, double ssigma, int iind, Partition part, char[] column) {
    inf = iinf;
    z = zz;
    ind = iind;
    e = ee;
    sigma = ssigma;
    groups.removeAllElements();
    for(int i = 0; i < part.groups.size(); i++) {
        curGroup.removeAllElements();
        for (int j = 0; j < ((Vector) (part.groups.elementAt(i))).size(); j++)
            curGroup.addElement(new Character(column[((Integer) ((Vector) (part.
                    groups.elementAt(i))).elementAt(j)).intValue()]));
        groups.addElement(new Vector(curGroup));
    }
  }
}
