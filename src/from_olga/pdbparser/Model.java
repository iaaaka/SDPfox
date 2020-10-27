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

public class Model {
  public Vector molecules;

  public Model(Vector data) {
    String current = new String();
    molecules = new Vector();
    int lineno = 0;
    while(lineno < data.size()) {
      current = (String) (data.elementAt(lineno));
      if(current.charAt(21) != ' ') {                                           //it's a chain - the position is occupied by chain id
        int counter = 0;
        char chainName = current.charAt(21);
        Vector chainData = new Vector();
//        System.out.println("Chain name = " + chainName);
        while((lineno + counter < data.size()) && (((String)(data.elementAt(counter + lineno))).charAt(21) == chainName) && (((String)(data.elementAt(counter + lineno))).charAt(0) != 'T')) {
//          System.out.println("" + ((String)(data.elementAt(counter + lineno))));
          if (((String)(data.elementAt(counter + lineno))).charAt(0) != 'H') chainData.addElement(new String((String)(data.elementAt(counter + lineno))));
          counter++;
        }
//        for(int i = lineno; i < lineno + counter; i++) chainData.addElement(new String((String)(data.elementAt(i))));
        for(int i = lineno + counter; i < data.size(); i++) {
            if ((((String) (data.elementAt(i))).charAt(21) == chainName) &&
                (((String) (data.elementAt(i))).charAt(0) != 'T'))
                chainData.addElement(new String((String) (data.elementAt(i))));
        }
        Chain c;
        if(chainData.size() > 0) {
          c = new Chain(chainData);
          molecules.addElement(c);
        }
        lineno += (counter + 1);
      }
      else {                                                                    //it's not a chain atom - add separately
        int molNum = 0;
        try {
          molNum = Integer.parseInt(current.substring(22, 26).trim());
        } catch (NumberFormatException e) {
          System.out.println(current);
        }
        int counter = 0;
        while((counter + lineno < data.size()) && (Integer.parseInt(((String)(data.elementAt(counter + lineno))).substring(22, 26).trim()) == molNum)) counter++;
        Vector heteroData = new Vector();
        for(int i = lineno; i < lineno + counter; i++) heteroData.addElement(new String((String)(data.elementAt(i))));
        Hetero h = new Hetero(heteroData);
        molecules.addElement(h);
        lineno += counter;
      }
    }
//    System.out.println("# of chains: " + molecules.size());
  }

  public Hetero get(int number) {
    for(int i = 0; i < molecules.size(); i++)
      if((((Molecule)(molecules.elementAt(i))).type.equals("hetero")) && (((Hetero)(molecules.elementAt(i))).the.number == number))
        return (Hetero)(molecules.elementAt(i));
    return null;
  }
}
