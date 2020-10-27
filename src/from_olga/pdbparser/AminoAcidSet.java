package from_olga.pdbparser;

/**
 * <p>Title: PDB parser</p>
 * <p>Description: Parser of PDB files</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class AminoAcidSet {
  public int model;
  public char chain;
  public int[] numbers;

  public AminoAcidSet(int mmodel, char cchain, int from, int to) {
    model = mmodel;
    chain = cchain;
    numbers = new int[to - from];
    for(int i = 0; i < to - from; i++) numbers[i] = from + i;
  }

  public AminoAcidSet(int mmodel, char cchain, int[] nnumbers) {
    model = mmodel;
    chain = cchain;
    numbers = new int[nnumbers.length];
    for(int i = 0; i < nnumbers.length; i++) numbers[i] = nnumbers[i];
  }

}
