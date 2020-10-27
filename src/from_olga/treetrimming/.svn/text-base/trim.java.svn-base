package from_olga.treetrimming;

import java.util.*;
import java.io.*;

import from_olga.sdppred.*;


/**
 * <p>Title: Tree trimming</p>
 * <p>Description: Different tree trimming approaches in order to identify best grouping</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class trim {

  static final double eps = 1e-10; //accuracy of calculations
  static final int sliceNum = 10; //number of slices
  static final double theta = 0.75; //minimum fraction of the initial alignment ot be considered

  static String getDiscr(String fname) {
    a_filereader f = new a_filereader(fname);
    String res = new String();
    for(int i = 0; i < f.data.size(); i++)
      res = res + (String)f.data.elementAt(i);
    return res;
  }

  public static void main(String[] args) {
    args = new String[5];
    args[0] = "ex//1_2_3.tre"; //tree input file name
    args[1] = "ex//1_2_3.gde"; //alignment corresponding to the tree
    args[2] = "ex//1_2_3.sdpin"; //inferred grouping
    args[3] = "ex//1_2_3.sdps";  //inferred best SDPs
    args[4] = "EC_PurR"; //sequence to map onto
    String refAlName = new String(args[1]);
    String treeDiscr = new String(getDiscr(args[0]));
    treeDiscr.replaceAll("\\s", "");
//    System.out.println("" + treeDiscr);
    Node tree = new Node(treeDiscr, 0);
    Vector nodes = new Vector();
    nodes.addAll(getNeighborhood(tree));
    //----cleanind neighbors
    for(int i = 0; i < nodes.size(); i++) {
      int z = ((Neighborhood)nodes.elementAt(i)).zeroDistIdx();
//      System.out.println("zero dist at " + z + " dists: " + ((Neighborhood)nodes.elementAt(i)).dists.elementAt(z).toString() + " name: " + ((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(z).toString());
      if(z != -1) {
        int counter = findCounterpart(nodes, (String)((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(z));
        ((Neighborhood)nodes.elementAt(i)).addNeighbors(((Neighborhood)nodes.elementAt(counter)).neighbors, ((Neighborhood)nodes.elementAt(counter)).dists);
        ((Neighborhood)nodes.elementAt(i)).neighbors.removeElementAt(z); //remove zero distance from neighbor list
        ((Neighborhood)nodes.elementAt(i)).dists.removeElementAt(z);     //--"--
        String counterName = ((Neighborhood)nodes.elementAt(counter)).name;
        nodes.removeElementAt(counter);                                  //remove node that produced zero distance
        for(int j = 0; j < nodes.size(); j++)                            //remove the name of the zero node
          for(int k = 0; k < ((Neighborhood)nodes.elementAt(j)).neighbors.size(); k++)
            if(((String)((Neighborhood)nodes.elementAt(j)).neighbors.elementAt(k)).equals(counterName)) {
              ( (Neighborhood) nodes.elementAt(j)).neighbors.removeElementAt(k);
              ( (Neighborhood) nodes.elementAt(j)).neighbors.addElement(new
                  String( ( (Neighborhood) nodes.elementAt(i)).name));
              double d = ( (Double) ( (Neighborhood) nodes.elementAt(j)).dists.
                          elementAt(k)).doubleValue();
              ( (Neighborhood) nodes.elementAt(j)).dists.removeElementAt(k);
              ( (Neighborhood) nodes.elementAt(j)).dists.addElement(new Double(
                  d));
            }
        int itself = ((Neighborhood)nodes.elementAt(i)).findItself();
        ((Neighborhood)nodes.elementAt(i)).neighbors.removeElementAt(itself);
        ((Neighborhood)nodes.elementAt(i)).dists.removeElementAt(itself);
      }
    }
    //----end cleaning
//----check if neighbors got correctly
//    for(int i = 0; i < nodes.size(); i++) {
//      System.out.println("node " + ((Neighborhood)nodes.elementAt(i)).name);
//      System.out.println("neighbors:");
//      for(int j = 0; j < ((Neighborhood)nodes.elementAt(i)).neighbors.size(); j++)
//        System.out.println("\t" + ((String)((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(j)) + " : " + ((Double)((Neighborhood)nodes.elementAt(i)).dists.elementAt(j)).doubleValue());
//    }
//----end check neighbors
    //----find tree center and radius
    Center cen = new Center();
    cen.copy(getCenter(nodes));
    System.out.println("center between " + cen.end1 + " and " + cen.end2);
    System.out.println("Distances: " + cen.distToEnd1 + " and " + cen.distToEnd2);
    //----get alignment
    Alignment al = new Alignment();
    try{
      al = new Alignment(args[1]);
    }catch(Exception e){
      System.out.println("Alignment not read at trim");
      System.exit(0);
    }
    //----start slices from the center
    Vector bestGroups = new Vector();
    from_olga.sdppred.SDPs bestsdps = new from_olga.sdppred.SDPs();
    double bestScore = 1000.;
    for(int i = 0; i < sliceNum; i++) {
      double currRad = (double)i * cen.radius / (double)sliceNum;
      Vector cutEdges = new Vector();
      cutEdges.addAll(getCutEdges(nodes, cen, currRad));
//----check cut edges
//      for(int ii = 0; ii < cutEdges.size(); ii++)
//        System.out.println("node in: " + ((directedEdge)cutEdges.elementAt(ii)).nodeIn + " node out: " + ((directedEdge)cutEdges.elementAt(ii)).nodeOut);
      Vector groups = new Vector();
      groups.addAll(getGroups(nodes, cutEdges));
      outGrouping(groups, args[2], al);
//      double currScore = getScore(args[2]);
      from_olga.sdppred.SDPs sdps = new from_olga.sdppred.SDPs();
      from_olga.sdppred.RefAlignment alRef = new from_olga.sdppred.RefAlignment();
      try{
        alRef = new RefAlignment(refAlName);
      }catch(Exception e){
        System.out.println("Alignment not read at trim");
        System.exit(0);
      }
      sdps = from_olga.sdppred.SDPpred.getSDPs(args[2], args[4], alRef);
      if(sdps.p < bestScore) {
        bestScore = sdps.p;
        bestGroups.removeAllElements();
        bestGroups.addAll((Vector)groups.clone());
        bestsdps.copy(sdps);
      }
    }
    outGrouping(bestGroups, args[2], al);
//----get best SDPs
    from_olga.sdppred.SDPpred.outRefSDPs(bestsdps, args[3], 0);
  }

  public static from_olga.sdppred.SDPs trimTree(String treeName, String alName, String groupingName, String outSDPsName, String refSeqName, String refAlName) {
    String treeDiscr = new String(getDiscr(treeName));
    treeDiscr.replaceAll("\\s", "");
//    System.out.println("" + treeDiscr);
    Node tree = new Node(treeDiscr, 0);
    Vector nodes = new Vector();
    nodes.addAll(getNeighborhood(tree));
    //----cleanind neighbors
    for(int i = 0; i < nodes.size(); i++) {
      int z = ((Neighborhood)nodes.elementAt(i)).zeroDistIdx();
      if(z != -1) {
//        System.out.println("zero dist at " + z + " dists: " + ((Neighborhood)nodes.elementAt(i)).dists.elementAt(z).toString() + " name: " + ((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(z).toString());
        int counter = findCounterpart(nodes, (String)((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(z));
        ((Neighborhood)nodes.elementAt(i)).addNeighbors(((Neighborhood)nodes.elementAt(counter)).neighbors, ((Neighborhood)nodes.elementAt(counter)).dists);
        ((Neighborhood)nodes.elementAt(i)).neighbors.removeElementAt(z); //remove zero distance from neighbor list
        ((Neighborhood)nodes.elementAt(i)).dists.removeElementAt(z);     //--"--
        String counterName = ((Neighborhood)nodes.elementAt(counter)).name;
        nodes.removeElementAt(counter);                                  //remove node that produced zero distance
        for(int j = 0; j < nodes.size(); j++)                            //remove the name of the zero node
          for(int k = 0; k < ((Neighborhood)nodes.elementAt(j)).neighbors.size(); k++)
            if(((String)((Neighborhood)nodes.elementAt(j)).neighbors.elementAt(k)).equals(counterName)) {
              ( (Neighborhood) nodes.elementAt(j)).neighbors.removeElementAt(k);
              ( (Neighborhood) nodes.elementAt(j)).neighbors.addElement(new
                  String( ( (Neighborhood) nodes.elementAt(i)).name));
              double d = ( (Double) ( (Neighborhood) nodes.elementAt(j)).dists.
                          elementAt(k)).doubleValue();
              ( (Neighborhood) nodes.elementAt(j)).dists.removeElementAt(k);
              ( (Neighborhood) nodes.elementAt(j)).dists.addElement(new Double(
                  d));
            }
        int itself = ((Neighborhood)nodes.elementAt(i)).findItself();
        if(itself != -1) {
          ((Neighborhood) nodes.elementAt(i)).neighbors.removeElementAt(itself);
          ((Neighborhood) nodes.elementAt(i)).dists.removeElementAt(itself);
        }
      }
    }
    //----end cleaning
//----check if neighbors got correctly
//    for(int i = 0; i < nodes.size(); i++) {
//      System.out.println("node " + ((Neighborhood)nodes.elementAt(i)).name);
//      System.out.println("neighbors:");
//      for(int j = 0; j < ((Neighborhood)nodes.elementAt(i)).neighbors.size(); j++)
//        System.out.println("\t" + ((String)((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(j)) + " : " + ((Double)((Neighborhood)nodes.elementAt(i)).dists.elementAt(j)).doubleValue());
//    }
//----end check neighbors
    //----find tree center and radius
    Center cen = new Center();
    cen.copy(getCenter(nodes));
    System.out.println("center between " + cen.end1 + " and " + cen.end2);
    System.out.println("Distances: " + cen.distToEnd1 + " and " + cen.distToEnd2);
    System.out.println("Radius = " + cen.radius);
    //----get alignment
    Alignment al = new Alignment();
    try{
      al = new Alignment(alName);
    }catch(Exception e){
      System.out.println("Alignment not read at trim");
      System.exit(0);
    }
//System.out.println(alName + " Initial alignment thickness: " + al.thickness);    
    from_olga.sdppred.RefAlignment alRef = new from_olga.sdppred.RefAlignment();
    try{
      alRef = new RefAlignment(refAlName);
    }catch(Exception e){
      System.out.println("RefAlignment not read at trim");
      System.exit(0);
    }
    //----start slices from the center
    Vector bestGroups = new Vector();
    from_olga.sdppred.SDPs bestsdps = new from_olga.sdppred.SDPs();
    double bestScore = 1000.;
    for(int i = 0; i < sliceNum; i++) {
      double currRad = (double)i * cen.radius / (double)sliceNum;
      Vector cutEdges = new Vector();
      cutEdges.addAll(getCutEdges(nodes, cen, currRad));
//----check cut edges
//      for(int ii = 0; ii < cutEdges.size(); ii++)
//        System.out.println("node in: " + ((directedEdge)cutEdges.elementAt(ii)).nodeIn + " node out: " + ((directedEdge)cutEdges.elementAt(ii)).nodeOut);
      Vector groups = new Vector();
      groups.addAll(getGroups(nodes, cutEdges));
      int totalGroups = 0;
      for(int x = 0; x < groups.size(); x++)
        totalGroups += ((Vector)groups.elementAt(x)).size();
      System.out.println("Alignment thickness at this step = " + totalGroups);
      if(totalGroups < theta * al.thickness) break;
      if(groups.size() < 2) continue;
      outGrouping(groups, groupingName  + "#" + i, al);
//      double currScore = getScore(args[2]);
      from_olga.sdppred.SDPs sdps = new from_olga.sdppred.SDPs();
//----print out all step results
      sdps = from_olga.sdppred.SDPpred.getSDPs(groupingName + "#" + i, refSeqName, alRef);
      from_olga.sdppred.SDPpred.outRefSDPs(sdps, outSDPsName + "#" + i, 0);
      System.out.println("p at this step = " + sdps.p);
      System.out.println("Best score = " + bestScore);
      if(sdps.p < bestScore) {
        bestScore = sdps.p;
        bestGroups.removeAllElements();
        bestGroups.addAll((Vector)groups.clone());
        bestsdps.copy(sdps);
      }
    }
    outGrouping(bestGroups, groupingName, al);
//----get best SDPs
    from_olga.sdppred.SDPpred.outRefSDPs(bestsdps, outSDPsName, 0);
    return bestsdps;
  }

  static Vector getNeighborhood(Node tree) {
    Vector res = new Vector();
    Neighborhood root = new Neighborhood();
    root.setName(tree.name);
    if(tree.left != null)
      root.addNeighbor(tree.left.name, tree.left.edge);
    if(tree.right != null)
      root.addNeighbor(tree.right.name, tree.right.edge);
    if(tree.parent != null)
      root.addNeighbor(tree.parent.name, tree.edge);
    res.addElement(root);
    if(tree.left != null)
      res.addAll(getNeighborhood(tree.left));
    if(tree.right != null)
      res.addAll(getNeighborhood(tree.right));
    return res;
  }

  static int findCounterpart(Vector nodes, String name) {
    for(int i = 0; i < nodes.size(); i++)
      if(((Neighborhood)nodes.elementAt(i)).name.equals(name))
        return i;
    return -1;
  }

  static Center getCenter(Vector nodes) {
    Path longest = new Path();
    Path longestI = new Path();
    double maxLength = 0.;
    for(int i = 0; i < nodes.size(); i++) {
      if(((Neighborhood)nodes.elementAt(i)).neighbors.size() == 1) {
        longestI = findLongestPath(nodes, i);
        if(longestI.weight > maxLength) {
          maxLength = longestI.weight;
          longest.copy(longestI);
        }
      }
    }
    double rootDist = longest.weight / 2.;
    double d = 0.;
    int inter = 0;
    String curr = new String(longest.source);
    String next = new String((String)longest.internalNodes.elementAt(inter));
    double currEdge = getEdge(nodes, curr, next);
    while(d + currEdge < rootDist) {
      d += currEdge;
      inter++;
      curr = "" + next;
      next = "" + (String)longest.internalNodes.elementAt(inter);
      currEdge = getEdge(nodes, curr, next);
    }

    Center res = new Center(curr, next, rootDist - d, d + currEdge - rootDist, rootDist);
    return res;
  }

  static double getEdge(Vector nodes, String from, String to) {
    int i = 0, j = 0;
    while((i < nodes.size()) && !((Neighborhood)nodes.elementAt(i)).name.equals(from)) i++;
    if(i == nodes.size()) return -1;
    while((j < ((Neighborhood)nodes.elementAt(i)).neighbors.size()) && !((String)((Neighborhood)nodes.elementAt(i)).neighbors.elementAt(j)).equals(to)) j++;
    if(j == ((Neighborhood)nodes.elementAt(i)).neighbors.size()) return -1;
    return ((Double)((Neighborhood)nodes.elementAt(i)).dists.elementAt(j)).doubleValue();
  }

  static Path findLongestPath(Vector nodes, int source) {
    Vector stack = new Vector();
    double dMax = 0., d = 0.;
    String endMax = new String();
    Neighborhood curr = new Neighborhood();
    Vector currIntNodes = new Vector();
    Vector maxIntNodes = new Vector();
//    System.out.println("source: " + ((Neighborhood)nodes.elementAt(source)).name);
    stack.addElement(new Integer(source));
    ((Neighborhood)nodes.elementAt(source)).visited = true;
    while(stack.size() > 0) {
      boolean allVisited = false;
      curr.copy((Neighborhood)nodes.elementAt(((Integer)stack.lastElement()).intValue()));
      allVisited = true;
      int i = 0;
      while ((i < curr.neighbors.size()) && (wasVisited(nodes, (String) curr.neighbors.elementAt(i)))) i++;
      if(i < curr.neighbors.size()) {
        allVisited = false;
        d += getEdge(nodes, curr.name, (String) curr.neighbors.elementAt(i));
//        System.out.println("d = " + d + " from " + curr.name + " to " +
//                           (String) curr.neighbors.elementAt(i));
        currIntNodes.addElement(new String( (String) curr.neighbors.elementAt(i)));
        int idx = findIdx(nodes, (String) curr.neighbors.elementAt(i));
        stack.addElement(new Integer(idx));
        ( (Neighborhood) nodes.elementAt(idx)).visited = true;
      }
      if(allVisited) {
        if(d > dMax) {
          dMax = d;
          endMax = curr.name;
          maxIntNodes.removeAllElements();
          maxIntNodes.addAll((Vector)currIntNodes.clone());
        }
//        System.out.println("d = " + d);
//        System.out.println("" + stack.toString() + " -> " + d);
//        System.out.println("" + ((Neighborhood)nodes.elementAt(((Integer)stack.elementAt(stack.size() - 1)).intValue())).name);
        if(d > eps) {
//        	System.out.println("-1: " + ((Neighborhood)nodes.elementAt(((Integer)stack.elementAt(stack.size() - 1)).intValue())).name + "\n2: " + ((Neighborhood)nodes.elementAt(((Integer)stack.elementAt(stack.size() - 2)).intValue())).name);
        	d -= getEdge(nodes, ((Neighborhood)nodes.elementAt(((Integer)stack.elementAt(stack.size() - 1)).intValue())).name, ((Neighborhood)nodes.elementAt(((Integer)stack.elementAt(stack.size() - 2)).intValue())).name);
        }
        stack.removeElementAt(stack.size() - 1);
        if(currIntNodes.size() > 0) currIntNodes.removeElementAt(currIntNodes.size() - 1);
      }
    }
//    System.out.println("source = " + ((Neighborhood)nodes.elementAt(source)).name + " end = " + endMax + " weight = " + dMax + " internal nodes: " + maxIntNodes.toString());
    for(int i = 0; i < nodes.size(); i++)
      ((Neighborhood)nodes.elementAt(i)).visited = false;
    Path res = new Path(((Neighborhood)nodes.elementAt(source)).name, endMax, dMax, maxIntNodes);
    return res;
  }

  private static boolean wasVisited(Vector nodes, String name) {
    int i = 0;
    while(!((Neighborhood)nodes.elementAt(i)).name.equals(name)) i++;
    return ((Neighborhood)nodes.elementAt(i)).visited;
  }

  private static int findIdx(Vector nodes, String name) {
    int i = 0;
    while(!((Neighborhood)nodes.elementAt(i)).name.equals(name)) i++;
    return i;
  }

  static Vector getCutEdges(Vector nodes, Center cen, double rad) {
    Vector stack = new Vector();
    Vector res = new Vector();
    Neighborhood curr = new Neighborhood();
    int idx = findIdx(nodes, cen.end1);
    int idx2 = findIdx(nodes, cen.end2);
    double d = cen.distToEnd1;
    for(int i = 0; i < nodes.size(); i++)
      ((Neighborhood)nodes.elementAt(i)).visited = false;
    System.out.println("current rad = " + rad);
    if((rad <= cen.distToEnd1) && (rad <= cen.distToEnd2)) {
      directedEdge currEdge = new directedEdge();
      currEdge.setEnds(cen.end1, cen.end2);
      res.addElement(currEdge);
      directedEdge currEdge2 = new directedEdge();
      currEdge2.setEnds(cen.end2, cen.end1);
      res.addElement(currEdge2);
      return res;
    }
    if(rad <= cen.distToEnd1) {
      directedEdge currEdge = new directedEdge();
      currEdge.setEnds(cen.end2, cen.end1);
      res.addElement(currEdge);
    } else {
      idx = findIdx(nodes, cen.end1);
      idx2 = findIdx(nodes, cen.end2);
      ( (Neighborhood) nodes.elementAt(idx)).visited = true;
      ( (Neighborhood) nodes.elementAt(idx2)).visited = true;
      stack.addElement(new Integer(idx));
      while (stack.size() > 0) {
        boolean allVisited = true;
        curr.copy( (Neighborhood) nodes.elementAt( ( (Integer) stack.
            lastElement()).intValue()));
        int i = 0;
        while ( (i < curr.neighbors.size()) &&
               (wasVisited(nodes, (String) curr.neighbors.elementAt(i)))) i++;
        if (i < curr.neighbors.size()) {
          allVisited = false;
          d += getEdge(nodes, curr.name, (String) curr.neighbors.elementAt(i));
//        System.out.println("d = " + d + " from " + curr.name + " to " +
//                           (String) curr.neighbors.elementAt(i));
          int idx1 = findIdx(nodes, (String) curr.neighbors.elementAt(i));
          stack.addElement(new Integer(idx1));
          ( (Neighborhood) nodes.elementAt(idx1)).visited = true;
        }
        if (d >= rad) {
          directedEdge currEdge = new directedEdge();
          if (d > cen.distToEnd1 + eps) currEdge.setEnds( ( (Neighborhood)
              nodes.elementAt( ( (Integer) stack.elementAt(stack.size() - 2)).
                              intValue())).name,
              ( (Neighborhood) nodes.
               elementAt( ( (Integer) stack.elementAt(stack.size() - 1)).
                         intValue())).name);
          else currEdge.setEnds(cen.end2,
                                ( (Neighborhood) nodes.elementAt( ( (Integer)
              stack.elementAt(stack.size() - 1)).intValue())).name);
          res.addElement(currEdge);
          if (d > cen.distToEnd1 + eps) d -=
              getEdge(nodes,
                      ( (Neighborhood)
                       nodes.elementAt( ( (Integer) stack.
                                         elementAt(stack.size() - 1)).intValue())).
                      name,
                      ( (Neighborhood) nodes.elementAt( ( (Integer) stack.
              elementAt(stack.size() - 2)).intValue())).name);
          stack.removeElementAt(stack.size() - 1);
//        stack.removeElementAt(stack.size() - 1);
        }
        else if (allVisited) {
//        System.out.println("d = " + d);
          if (d > cen.distToEnd1 + eps) d -=
              getEdge(nodes,
                      ( (Neighborhood) nodes.elementAt( ( (Integer) stack.
                       elementAt(stack.size() - 2)).intValue())).name,
                      ( (Neighborhood) nodes.elementAt( ( (Integer) stack.
                       elementAt(stack.size() - 1)).intValue())).name);
          stack.removeElementAt(stack.size() - 1);
        }
      }
    }
    idx = findIdx(nodes, cen.end2);
    d = cen.distToEnd2;
    if(rad <= cen.distToEnd2) {
      directedEdge currEdge = new directedEdge();
      currEdge.setEnds(cen.end1, cen.end2);
      res.addElement(currEdge);
    } else {
      idx = findIdx(nodes, cen.end2);
      idx2 = findIdx(nodes, cen.end1);
      ( (Neighborhood) nodes.elementAt(idx)).visited = true;
      ( (Neighborhood) nodes.elementAt(idx2)).visited = true;
      stack.removeAllElements();
      stack.addElement(new Integer(idx));
      while (stack.size() > 0) {
        boolean allVisited = true;
        curr.copy( (Neighborhood) nodes.elementAt( ( (Integer) stack.
            lastElement()).intValue()));
        int i = 0;
        while ( (i < curr.neighbors.size()) &&
               (wasVisited(nodes, (String) curr.neighbors.elementAt(i)))) i++;
        if (i < curr.neighbors.size()) {
          allVisited = false;
          d += getEdge(nodes, curr.name, (String) curr.neighbors.elementAt(i));
//        System.out.println("d = " + d + " from " + curr.name + " to " +
//                           (String) curr.neighbors.elementAt(i));
          int idx1 = findIdx(nodes, (String) curr.neighbors.elementAt(i));
          stack.addElement(new Integer(idx1));
          ( (Neighborhood) nodes.elementAt(idx1)).visited = true;
        }
        if (d >= rad) {
          directedEdge currEdge = new directedEdge();
          if (d > cen.distToEnd2 + eps) currEdge.setEnds( ( (Neighborhood)
              nodes.elementAt( ( (Integer) stack.elementAt(stack.size() - 2)).
                              intValue())).name,
              ( (Neighborhood) nodes.
               elementAt( ( (Integer) stack.elementAt(stack.size() - 1)).
                         intValue())).name);
          else currEdge.setEnds(cen.end1,
                                ( (Neighborhood) nodes.elementAt( ( (Integer)
              stack.elementAt(stack.size() - 1)).intValue())).name);
          res.addElement(currEdge);
          if (d > cen.distToEnd2 + eps) d -=
              getEdge(nodes,
                      ( (Neighborhood)
                       nodes.elementAt( ( (Integer) stack.
                                         elementAt(stack.size() - 2)).intValue())).
                      name,
                      ( (Neighborhood) nodes.elementAt( ( (Integer) stack.
              elementAt(stack.size() - 1)).intValue())).name);
          stack.removeElementAt(stack.size() - 1);
        }
        else if (allVisited) {
//        System.out.println("d = " + d);
          if (d > cen.distToEnd2 + eps) d -=
              getEdge(nodes,
                      ( (Neighborhood)
                       nodes.elementAt( ( (Integer) stack.
                                         elementAt(stack.size() - 1)).intValue())).
                      name,
                      ( (Neighborhood) nodes.elementAt( ( (Integer) stack.
              elementAt(stack.size() - 2)).intValue())).name);
          stack.removeElementAt(stack.size() - 1);
        }
      }
    }
    for(int i = 0; i < nodes.size(); i++)
      ((Neighborhood)nodes.elementAt(i)).visited = false;
    return res;
  }

  static Vector getGroups(Vector nodes, Vector cutEdges) {
    Vector res = new Vector();
    Vector currGroup = new Vector();
    for(int i = 0; i < cutEdges.size(); i++) {
      currGroup.removeAllElements();
      int idx = findIdx(nodes, ((directedEdge)cutEdges.elementAt(i)).nodeOut);
      int idxIn = findIdx(nodes, ((directedEdge)cutEdges.elementAt(i)).nodeIn);
      ((Neighborhood)nodes.elementAt(idx)).visited = true;
      ((Neighborhood)nodes.elementAt(idxIn)).visited = true;
      Vector queue = new Vector();
      queue.addElement(new Integer(idx));
      while(queue.size() > 0) {
        int currIdx = ((Integer)queue.elementAt(0)).intValue();
        queue.removeElementAt(0);
        if(((Neighborhood)nodes.elementAt(currIdx)).neighbors.size() == 1)
          currGroup.addElement(new String(((Neighborhood)nodes.elementAt(currIdx)).name));
        for(int j = 0; j < ((Neighborhood)nodes.elementAt(currIdx)).neighbors.size(); j++) {
          int neiIdx = findIdx(nodes, (String)((Neighborhood)nodes.elementAt(currIdx)).neighbors.elementAt(j));
          if(!((Neighborhood)nodes.elementAt(neiIdx)).visited) {
            ((Neighborhood)nodes.elementAt(neiIdx)).visited = true;
            queue.addElement(new Integer(neiIdx));
          }
        }
      }
      if(currGroup.size() > 2)
        res.addElement(new Vector((Vector)currGroup.clone()));
    }
    return res;
  }

  static void outGrouping(Vector groups, String fname, Alignment al) {
    try{
      System.out.println("grouping name = " + fname + "; #groups = " + groups.size());
      OutputStream f = new FileOutputStream(fname);
//      System.out.println("grouping name = " + fname + "; #groups = " + groups.size());
      for(int i = 0; i < groups.size(); i++) {
//System.out.println("===Group" + i + "===\n");    	  
        a_filewriter.Write(f, "===Group" + i + "===\n");
        for(int j = 0; j < ((Vector)groups.elementAt(i)).size(); j++) {
//System.out.println("name = " + (String)((Vector)groups.elementAt(i)).elementAt(j));
          int idx = findSeqIdx(al, (String)((Vector)groups.elementAt(i)).elementAt(j));
//System.out.println("idx = " + idx);
          a_filewriter.Write(f, "%" + ((Sequence)al.seqs.elementAt(idx)).name + "\n");
          a_filewriter.Write(f, ((Sequence)al.seqs.elementAt(idx)).aa + "\n");
        }
      }
      f.close();
    } catch (Exception e) {
      System.out.println("File opening exception in trim");
      System.exit(1);
    }
  }

  static int findSeqIdx(Alignment al, String name) {
    int i = 0;
    while((i < al.thickness) &&
          (!(namesEqual(((Sequence)al.seqs.elementAt(i)).name, name)))) i++;
    if(i < al.thickness) return i;
    else return -1;
  }

  static boolean namesEqual(String name1, String name2) {
//    System.out.println("name1 = " + name1 + "name2 = " + name2);
    if((name1.length() <= 10) && (name2.length() <= 10)) return name1.equals(name2);
    if(name2.length() <= 10)
      return name1.substring(0, 10).equals(name2);
    if(name1.length() <= 10)
      return name2.substring(0, 10).equals(name1);
    return name1.equals(name2);
  }

  static double getScore(String fname) {
    from_olga.sdppred.SDPs sdps = new from_olga.sdppred.SDPs();
    from_olga.sdppred.RefAlignment alRef = new from_olga.sdppred.RefAlignment();
    sdps = from_olga.sdppred.SDPpred.getSDPs(fname, "", alRef);
    return sdps.p;
  }

}
