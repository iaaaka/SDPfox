package from_olga.pdbparser;

/**
 * <p>Title: PDB parser</p>
 * <p>Description: Parser of PDB files</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Atom {
  public int number;
  public String nomen;
  public double x, y, z, temp;
  public char identity;

  public Atom(String data) {
    x = ReadPDBCoord(data, 30, 38);
    y = ReadPDBCoord(data, 38, 46);
    z = ReadPDBCoord(data, 46, 54);
    temp = ReadPDBCoord(data, 60, 66);
    number = Integer.parseInt(data.substring(6, 11).trim());
    nomen = data.substring(12, 16).trim();
    identity = data.charAt(77);
  }

/*  private double ReadPDBCoord( String Record, int offset)
  {
    if(Record.length() <= offset)
      return 0;
    int len = Math.min(8,Record.length() - offset);
    long result = (long)(Double.valueOf(Record.substring(offset,offset + len).trim()).doubleValue());

    if(false)
    {
      System.err.println("Record: " + Record.substring(offset,offset+len)
                         + " result " + result);
      System.err.println(Record.length() + " offset " + offset + " len " + len);
    }
    return result;
  }*/

  public Atom() {
    number = 0;
    nomen = "";
    x = 0;
    y = 0;
    z = 0;
    temp = 0;
    identity = ' ';
  }

  private double ReadPDBCoord(String data, int from, int to) {
    try {
      double v = Double.parseDouble(data.substring(from, to));
      return v;
    } catch (NumberFormatException e) {
        return 0;
    }
  }

  public void copy(Atom a) {
    number = a.number;
    nomen = new String(a.nomen);
    identity = a.identity;
    x = a.x;
    y = a.y;
    z = a.z;
    temp = a.temp;
  }

  public boolean within(double cx, double cy, double cz, double rad) {
      if (Math.sqrt((x - cx) * (x - cx) + (y - cy) * (y - cy) +
                    (z - cz) * (z - cz)) < rad)return true;
      return false;
  }
}
