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

public class Structure {
  public String title;
  public Vector models;

  public Structure(String fname) {
    a_filereader f = new a_filereader(fname);
    title = new String();
    String current = new String();
    models = new Vector();
    int lineno = 0;
    while(lineno < f.data.size()) {
      current = (String)(f.data.elementAt(lineno));
      if(current.startsWith("TITLE")) {
        title += current.substring(10);
        lineno++;
      } else
      if(current.startsWith("MODEL")) {
        int counter = 0;
        while(!((String)(f.data.elementAt(lineno + counter))).startsWith("ENDMDL")) counter++;
        Vector modelData = new Vector();
        for(int i = lineno + 1; i < lineno + counter; i++) modelData.addElement(new String((String)(f.data.elementAt(i))));
//        for(int i = 0; i < modelData.size(); i++) System.out.println("" + modelData.elementAt(i));
        Model m = new Model(modelData);
        models.addElement(m);
        lineno += (counter + 1);
      } else
      if(current.startsWith("ATOM")) {
        int counter = 0;
        while(!((String)(f.data.elementAt(lineno + counter))).startsWith("CONECT") && !((String)(f.data.elementAt(lineno + counter))).startsWith("MASTER") && !((String)(f.data.elementAt(lineno + counter))).startsWith("END")) counter++;
        Vector modelData = new Vector();
        for(int i = lineno; i < lineno + counter; i++) modelData.addElement(new String((String)(f.data.elementAt(i))));
//        for(int i = 0; i < modelData.size(); i++) System.out.println("" + modelData.elementAt(i));
        Model m = new Model(modelData);
        models.addElement(m);
        lineno = f.data.size(); //one model - go to end
      }
      else lineno++;
    }
  }

  public double[] getCube() {
      double [] result = new double[6];
      double minX, minY, minZ, maxX, maxY, maxZ;
      minX = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(0)).molecules.elementAt(0)).aminoacids.elementAt(0)).atoms.elementAt(0)).x;
      minY = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(0)).molecules.elementAt(0)).aminoacids.elementAt(0)).atoms.elementAt(0)).y;
      minZ = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(0)).molecules.elementAt(0)).aminoacids.elementAt(0)).atoms.elementAt(0)).z;
      maxX = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(0)).molecules.elementAt(0)).aminoacids.elementAt(0)).atoms.elementAt(0)).x;
      maxY = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(0)).molecules.elementAt(0)).aminoacids.elementAt(0)).atoms.elementAt(0)).y;
      maxZ = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(0)).molecules.elementAt(0)).aminoacids.elementAt(0)).atoms.elementAt(0)).z;
      for(int i = 0; i < models.size(); i++)
        for(int j = 0; j < ((Model)models.elementAt(i)).molecules.size(); j++)
          if(((Molecule)((Model)models.elementAt(i)).molecules.elementAt(j)).type.equals("chain")) {
              for(int k = 0; k < ((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.size(); k++) {
                  for(int m = 0; m < ((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.size(); m++) {
                      if(((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).x < minX)
                          minX = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).x;
                      if(((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).y < minY)
                          minY = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).y;
                      if(((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).z < minZ)
                          minZ = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).z;
                      if(((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).x > maxX) {
                          maxX = ((Atom) ((AminoAcid) ((Chain) ((Model) models.
                                  elementAt(i)).molecules.elementAt(j)).
                                          aminoacids.elementAt(k)).atoms.
                                  elementAt(m)).x;
/*                          if(maxX == 0.0) {
                              System.out.println("" +
                                                 ((Atom) ((AminoAcid) ((
                                      Chain) ((Model) models.elementAt(i)).
                                      molecules.elementAt(j)).aminoacids.
                                      elementAt(k)).atoms.elementAt(m)).number +
                                                 "; " +
                                                 ((Atom) ((AminoAcid) ((
                                      Chain) ((Model) models.elementAt(i)).
                                      molecules.elementAt(j)).aminoacids.
                                      elementAt(k)).atoms.elementAt(m)).
                                                 identity);
                              System.out.println("" + i  + " " + j + " " + k + " " + m);
                              System.out.println("" + ((AminoAcid) ((Chain) ((Model) models.
                                  elementAt(i)).molecules.elementAt(j)).
                                          aminoacids.elementAt(k)).name);
                          }*/
                      }
                      if(((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).y > maxY)
                          maxY = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).y;
                      if(((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).z > maxZ)
                          maxZ = ((Atom)((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.elementAt(m)).z;
                  }
              }
          } else {
              for(int m = 0; m < ((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.size(); m++) {
                  if(((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).x < minX)
                      minX = ((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).x;
                  if(((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).y < minY)
                      minY = ((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).y;
                  if(((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).z < minZ)
                      minZ = ((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).z;
                  if(((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).x > maxX)
                      maxX = ((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).x;
                  if(((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).y > maxY)
                      maxY = ((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).y;
                  if(((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).z > maxZ)
                      maxZ = ((Atom)((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).z;
              }
          }
    result[0] = minX;
    result[1] = minY;
    result[2] = minZ;
    result[3] = maxX;
    result[4] = maxY;
    result[5] = maxZ;
    return result;
  }

  public Vector findPV(double centerX, double centerY, double centerZ, double rad) {
    Vector result = new Vector();
      for(int i = 0; i < models.size(); i++)
        for(int j = 0; j < ((Model)models.elementAt(i)).molecules.size(); j++)
          if(((Molecule)((Model)models.elementAt(i)).molecules.elementAt(j)).type.equals("chain")) {
              for(int k = 0; k < ((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.size(); k++) {
                  double aaSize = ((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.size();
                  double occSize = 0;
                  for(int m = 0; m < ((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k)).atoms.size(); m++) {
                      if (((Atom) ((AminoAcid) ((Chain) ((Model) models.
                              elementAt(i)).molecules.elementAt(j)).aminoacids.
                                   elementAt(k)).atoms.elementAt(m)).within(
                              centerX, centerY, centerZ, rad)) occSize++;
                  }
                  if(occSize > 0) {
                      partialVolume pv = new partialVolume((AminoAcid)((Chain)((Model)models.elementAt(i)).molecules.elementAt(j)).aminoacids.elementAt(k), (double)(occSize / aaSize));
                      result.addElement(pv);
                  }
              }
          } else {
                  double aaSize = ((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.size();
                  double occSize = 0;
                  for(int m = 0; m < ((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the).atoms.size(); m++) {
                      if (((Atom) ((AminoAcid) ((Hetero) ((Model) models.
                              elementAt(i)).molecules.elementAt(j)).the).atoms.elementAt(m)).within(
                              centerX, centerY, centerZ, rad)) occSize++;
                  }
                  if(occSize > 0) {
                      partialVolume pv = new partialVolume((AminoAcid)((Hetero)((Model)models.elementAt(i)).molecules.elementAt(j)).the, (double)(occSize / aaSize));
                      result.addElement(pv);
                  }
          }
    return result;
  }

  public boolean isThere(int num, String chain) {
    int iter = 0;
//    System.out.println("num = " + num);
    while (!((((Molecule)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).type.equals("chain")) &&
           ((Chain)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).name.equals(chain))) {
      iter++;
    }
    int iter2 = 0;
    while (iter2 < ( (Chain) ( ( ( (Model) (models.
      elementAt(0))).molecules).elementAt(iter))).aminoacids.size()) {
      if( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (models.
        elementAt(0))).molecules).elementAt(iter))).aminoacids.elementAt(iter2))).number == num)
        return true;
      iter2++;
    }
    return false;
  }

  public double dist(int aa1, int aa2, String chain) {
    double min = 10000;
    AminoAcid first = new AminoAcid();
    int iter = 0;
    //0 means the first model in the structure, A - the chain A is considered
//    System.out.println("chain # = " + iter + "chain name = " + ((Chain)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).name);
    while (!((((Molecule)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).type.equals("chain")) &&
           ((Chain)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).name.equals(chain))) {
     iter++;
//     System.out.println("chain # = " + iter + "chain name = " + ((Chain)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).name);
   }
    int iter2 = 0;
    while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (models.
      elementAt(0))).molecules).elementAt(iter))).aminoacids.
                         elementAt(iter2))).number != aa1) {
      iter2++;
//      System.out.println("aa1 = " + aa1 + " chain # = " + iter + " amino acid at place " + iter2 + " is " + ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (models.
//      elementAt(0))).molecules).elementAt(iter))).aminoacids.
//                         elementAt(iter2))).number);
    }
    first.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (models.
      elementAt(0))).molecules).elementAt(iter))).aminoacids.
                              elementAt(iter2)));
    AminoAcid second = new AminoAcid();
    iter = 0;
    while (!((((Molecule)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).type.equals("chain")) &&
           ((Chain)( ( ( (Model) (models.elementAt(0))).molecules).elementAt(iter))).name.equals(chain)))
      iter++;
    iter2 = 0;
    while ( ( (AminoAcid) ( ( (Chain) ( ( ( (Model) (models.
      elementAt(0))).molecules).elementAt(iter))).aminoacids.
                         elementAt(iter2))).number != aa2)
      iter2++;
    second.copy( (AminoAcid) ( ( (Chain) ( ( ( (Model) (models.
        elementAt(0))).molecules).elementAt(iter))).
                               aminoacids.elementAt(iter2)));
    for (int ii = 0; ii < first.atoms.size(); ii++)
      for(int jj = 0; jj < second.atoms.size(); jj++) {
        double d = dist(((Atom)(first.atoms.elementAt(ii))), ((Atom)(second.atoms.elementAt(jj))));
        if(d < min)
          min = d;
      }
    return min;
  }

  private static double dist(Atom a1, Atom a2) {
    return Math.sqrt((a1.x - a2.x) * (a1.x - a2.x) + (a1.y - a2.y) * (a1.y - a2.y) + (a1.z - a2.z) * (a1.z - a2.z));
  }
}
