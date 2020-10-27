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

public class Neighborhood {
  String name;
  Vector neighbors;
  Vector dists;
  boolean visited;

  public Neighborhood() {
    name = new String("");
    neighbors = new Vector();
    dists = new Vector();
    visited = false;
  }

  public Neighborhood(Neighborhood from) {
    name = new String(from.name);
    neighbors = new Vector((Vector)from.neighbors.clone());
    dists = new Vector((Vector)from.dists.clone());
    visited = from.visited;
  }

  public void setName(String newName) {
    name = newName;
  }

  public void addNeighbor(String nName, double nDist) {
    neighbors.addElement(new String(nName));
    dists.addElement(new Double(nDist));
  }

  private boolean isInThere(String name) {
    for(int i = 0; i < neighbors.size(); i++)
      if(((String)neighbors.elementAt(i)).equals(name))
        return true;
    return false;
  }

  public void addNeighbors(Vector newNames, Vector newDists) {
    for(int i = 0; i < newNames.size(); i++) {
      if(!isInThere((String)newNames.elementAt(i))) {
        neighbors.addElement(new String((String)newNames.elementAt(i)));
        dists.addElement(new Double(((Double)newDists.elementAt(i)).doubleValue()));
      }
    }
  }

  public int zeroDistIdx() {
    for(int i = 0; i < dists.size(); i++)
      if(((Double)dists.elementAt(i)).doubleValue() == 0.)
        return i;
    return -1;
  }

  public int findItself() {
    for(int i = 0; i < neighbors.size(); i++)
      if(((String)neighbors.elementAt(i)).equals(name))
        return i;
    return -1;
  }

  public void copy(Neighborhood from) {
    name = from.name;
    dists.removeAllElements();
    dists.addAll((Vector)from.dists.clone());
    neighbors.removeAllElements();
    neighbors.addAll((Vector)from.neighbors.clone());
    visited = from.visited;
  }

}
