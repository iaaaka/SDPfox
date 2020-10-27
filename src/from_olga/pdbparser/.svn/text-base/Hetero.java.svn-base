package from_olga.pdbparser;

import java.util.*;

/**
 * <p>Title: PDB parser</p>
 * <p>Description: Parser of PDB files</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Hetero extends Molecule{
  public AminoAcid the;
//  Vector atoms;
//  int num;

  public Hetero(Vector data) {
    type = "hetero";
    the = new AminoAcid(data);
/*    atoms = new Vector();
    name = ((String)(data.elementAt(0))).substring(16, 20).trim();
    try {
      num = Integer.parseInt( ( (String) (data.elementAt(0))).substring(22,
          26).trim());
    } catch (NumberFormatException e) {
      System.out.println(( (String) (data.elementAt(0))));
    }
    for(int i = 0; i < data.size(); i++) {
      Atom a = new Atom(((String)(data.elementAt(i))));
      atoms.addElement(a);
    }*/
  }

  public Hetero() {
    the = new AminoAcid();
  }

  public void copy(Hetero h) {
    the = new AminoAcid();
    the.copy(h.the);
  }
}
