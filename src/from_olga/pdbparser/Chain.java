package from_olga.pdbparser;

import java.util.*;
import java.util.regex.*;

/**
 * <p>Title: PDB parser</p>
 * <p>Description: Parser of PDB files</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Chain extends Molecule{
  public Vector aminoacids;

  public Chain(Vector data) {
    String current = new String();
    aminoacids = new Vector();
    type = "chain";
    name = ((String)(data.elementAt(0))).charAt(21) + "";
    for(int lineno = 0; lineno < data.size(); lineno++) {
      current = (String)(data.elementAt(lineno));
      if((Pattern.matches("^ATOM.*", current)) || (Pattern.matches("^HETATM.*", current))) {
//System.out.println(current);    	  
        int aaNum;
        try {
          aaNum = Integer.parseInt(current.substring(22, 26).trim());
        } catch (NumberFormatException e) {
          System.out.println(current);
          aaNum = 0;
        }
        String aaName = current.substring(16, 20).trim();
        if(unique(aaNum, aaName)) {
          Vector aaData = new Vector();
          for(int i = 0; i < data.size(); i++) {
            try {
              int curAaNum = Integer.parseInt( ( (String) (data.elementAt(i))).
                                              substring(22, 26).trim());
              String curAaName = ( (String) (data.elementAt(i))).substring(16, 20).
                  trim();
              if ( (aaNum == curAaNum) && (aaName.equals(curAaName))) {
                String aaLine = new String( (String) (data.elementAt(i)));
                aaData.addElement(aaLine);
              }
            } catch (NumberFormatException e) {;
//            System.out.println("making aa " + ( (String) (data.elementAt(i))));
            }
          }
          AminoAcid aa = new AminoAcid(aaData);
          aminoacids.addElement(aa);
        }
      }
    }
  }

  private boolean unique(int num, String name) {
    for(int i = 0; i < aminoacids.size(); i++) {
      if((((AminoAcid)(aminoacids.elementAt(i))).name.equals(name)) && (((AminoAcid)(aminoacids.elementAt(i))).number == num)) return false;
    }
    return true;
  }

  public AminoAcid get(int number) {
    for(int i = 0; i < aminoacids.size(); i++)
      if(((AminoAcid)(aminoacids.elementAt(i))).number == number)
        return (AminoAcid)(aminoacids.elementAt(i));
    return null;
  }

}
