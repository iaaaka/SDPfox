package from_olga.conservationscore;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Sequence {
  String name;
  int length;
  String aa;

  public Sequence() {
    name = new String();
    length = 0;
    aa = new String();
  }

  public Sequence(String source) {
    make(source);
  }

  void make(String source) {
    String[] splitted = source.split("\\s+");
    name = new String(splitted[0]);
    aa = new String(splitted[1]);
    length = aa.length();
  }

  public char aa(int pos) {
    return aa.charAt(pos);
  }

  public int seqInd(int pos) {
    int ind = 0;
    for(int i = 0; i <= pos; i++)
      if((aa.charAt(i) != '-') && (aa.charAt(i) != '.')) ind++;
    return ind;
  }
}

