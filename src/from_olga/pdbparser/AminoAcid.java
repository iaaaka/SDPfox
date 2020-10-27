package from_olga.pdbparser;

import java.util.*;
import java.util.regex.Pattern;

/**
 * <p>Title: PDB parser</p>
 * <p>Description: Parser of PDB files</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class AminoAcid {
  public int number;
  public String name;
  public Vector atoms;

  public AminoAcid(Vector data) {
    atoms = new Vector();
    try {
      number = Integer.parseInt( ( (String) (data.elementAt(0))).substring(22,
          26).trim());
    } catch (NumberFormatException e) {
      System.out.println(( (String) (data.elementAt(0))));
    }
    name = new String(((String)(data.elementAt(0))).substring(16, 20).trim());
    for(int i = 0; i < data.size(); i++) {
      if((Pattern.matches("^ATOM.*", ((String)(data.elementAt(i))))) || (Pattern.matches("^HETATM.*", ((String)(data.elementAt(i)))))) {
        Atom a = new Atom(((String)(data.elementAt(i))));
        atoms.addElement(a);
      }
    }
  }

  public AminoAcid() {
    number = 0;
  }

  public void copy(AminoAcid aa) {
    number = aa.number;
    name = new String(aa.name);
    atoms = new Vector(aa.atoms);
  }

}
