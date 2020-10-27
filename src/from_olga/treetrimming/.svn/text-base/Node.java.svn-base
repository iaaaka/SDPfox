package from_olga.treetrimming;

/**
 * <p>Title: Tree trimming</p>
 * <p>Description: Different tree trimming approaches in order to identify best grouping</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Node {
  int st, en;
  String name;
  Node left, right, parent;
  double edge; //length of the edge above

  public Node() {
  }

  public Node(String discr, int start) {
    st = start;
    parent = null;
    if(discr.charAt(st) == '(') {
      left = new Node(discr, st + 1);
      left.parent = this;
      right = new Node(discr, left.en + 1);
      right.parent = this;
      name = left.name + "+" + right.name;
      en = right.en + 1;
      if(discr.charAt(en) == ':') {
        while ( (discr.charAt(en) != ',') && (discr.charAt(en) != ')') &&
               (discr.charAt(en) != ';')) en++;
        edge = Double.parseDouble(discr.substring(right.en + 2, en));
        if(edge == 0.) { edge = 0.00001; }
      }
      else if(discr.charAt(en) != ';') {
        Node newLeft = new Node();
        newLeft.name = name;
        newLeft.edge = edge;
        newLeft.left = left;
        newLeft.right = right;
        newLeft.st = st;
        newLeft.en = en;
        newLeft.parent = this;
        left = newLeft;
        right = new Node(discr, en);
        right.parent = this;
        name = left.name + "+" + right.name;
        edge = 0.;
      }
    }
    else {
      name = discr.substring(st, discr.indexOf(':', st));
      en = st;
      while((discr.charAt(en) != ',') && (discr.charAt(en) != ')')) en++;
      edge = Double.parseDouble(discr.substring(discr.indexOf(':', st) + 1, en));
      if(edge == 0.) { edge = 0.00001; }
      left = null;
      right = null;
    }
//    System.out.println("node name " + name + "\tedge length " + edge);
//    System.out.println("st = " + st + " en = " + en);
  }

}
