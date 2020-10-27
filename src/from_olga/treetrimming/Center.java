package from_olga.treetrimming;

/**
 * <p>Title: Tree trimming</p>
 * <p>Description: Different tree trimming approaches in order to identify best grouping</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Center {
  String end1, end2;
  double distToEnd1, distToEnd2, radius;

  public Center() {
    end1 = new String();
    end2 = new String();
    distToEnd1 = 0.;
    distToEnd2 = 0.;
    radius = 0.;
  }

  public Center(String eend1, String eend2, double dist1, double dist2, double rrad) {
    end1 = new String(eend1);
    end2 = new String(eend2);
    distToEnd1 = dist1;
    distToEnd2 = dist2;
    radius = rrad;
  }

  public void copy(Center from) {
    end1 = from.end1;
    end2 = from.end2;
    distToEnd1 = from.distToEnd1;
    distToEnd2 = from.distToEnd2;
    radius = from.radius;
  }

}
