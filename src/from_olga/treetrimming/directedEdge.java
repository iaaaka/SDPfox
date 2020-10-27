package from_olga.treetrimming;

/**
 * <p>Title: Tree trimming</p>
 * <p>Description: Different tree trimming approaches in order to identify best grouping</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class directedEdge {
  String nodeIn;
  String nodeOut;

  public directedEdge() {
    nodeIn = new String();
    nodeOut = new String();
  }

  public void setEnds(String in, String out) {
    nodeIn = in;
    nodeOut = out;
  }

}
