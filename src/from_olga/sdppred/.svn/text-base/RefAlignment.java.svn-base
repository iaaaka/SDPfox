package from_olga.sdppred;

import java.util.*;

/**
 * <p>Title: activeSite</p>
 * <p>Description: Active site finder that utilizes 3D structure and SDPs</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class RefAlignment {
  Vector seqs;
  int length, thickness;

  public RefAlignment() {
  }

  public RefAlignment(String filename) throws Exception{
    read(filename);
  }

  void read(String filename) throws Exception{
    seqs = new Vector();
    a_filereader f = new a_filereader(filename);
    String current = new String();
    String help = new String();
    boolean last = false;
    int i = 0;
    thickness = 0;
    current = (String)f.data.elementAt(i);
    while(!last) {
      do {
        i++;
        thickness++;
        help = "";
        help += current.substring(1, current.length());
        help += " ";
        current = (String) f.data.elementAt(i);
        while (!current.equals("") && (current.charAt(0) != '%') &&
               (current.charAt(0) != ' ') && (i < f.data.size() - 1)) {
          help += current;
          i++;
          current = (String) f.data.elementAt(i);
        }
        if (i == f.data.size() - 1) {
          last = true;
          help += current;
        }
        seqs.addElement(new Sequence(help));
      } while(!last);
    }
    length = ((Sequence)(seqs.firstElement())).length;
    Vector whole = new Vector();
    for(int x = 0; x < thickness; x++) whole.addElement(new Integer(x));
//----print ot check
//   for(int x = 0; x < thickness; x++) {
//     System.out.println(x + ": " + ((Sequence)seqs.elementAt(x)).aa);
//   }
//----end print to check
  }

  public int findSeq(String name) {
    for(int i = 0; i < thickness; i++)
      if(((Sequence)seqs.elementAt(i)).name.equals(name)) return i;
    return -1;
  }

}
