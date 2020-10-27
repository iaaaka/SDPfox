package from_olga.treetrimming;

import java.util.*;

/**
 * <p>Title: Tree trimming</p>
 * <p>Description: Different tree trimming approaches in order to identify best grouping</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Path {
  String source;
  String end;
  double weight;
  Vector internalNodes;

  public Path() {
    source = new String();
    end = new String();
    weight = 0.;
    internalNodes = new Vector();
  }

  public Path(String ssource, String eend, double wweight, Vector iinternal) {
    source = ssource;
    end = eend;
    weight = wweight;
    internalNodes = new Vector();
    internalNodes.removeAllElements();
//    System.out.println("int nodes in path: " + iinternal);
    internalNodes.addAll((Vector)iinternal.clone());
  }

  public void copy(Path from) {
    source = from.source;
    end = from.end;
    weight = from.weight;
    internalNodes.removeAllElements();
    internalNodes.addAll((Vector)from.internalNodes.clone());
  }

}
