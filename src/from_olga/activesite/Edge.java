package from_olga.activesite;

/**
 * <p>Title: activeSite</p>
 * <p>Description: Active site finder that utilizes 3D structure and SDPs</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Edge {
  double weight;
  int end1, end2;

  public Edge() {
  }

  public Edge(double wweight, int eend1, int eend2) {
    weight = wweight;
    end1 = eend1;
    end2 = eend2;
  }

  public double getWeight(int origin, int end) {
    if(((end1 == origin) && (end2 == end)) || ((end2 == origin) && (end1 == end)))
      return weight;
    return 0;
  }

}
