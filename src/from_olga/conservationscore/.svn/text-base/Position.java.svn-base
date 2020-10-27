package from_olga.conservationscore;

/**
 * <p>Title: Conservation scores calculation</p>
 * <p>Description: Calculate conservation scores for each position of a given alignment. Select a number of most conserved positions</p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class Position {
  public double consScore;
  public char[] col;
  public int num;
  public int refSeqNum;
  public char refSeqAA;

  public Position() {
  }

  public Position(double sscore, char[] ccol, int nnum) {
    consScore = sscore;
    col = new char[ccol.length];
    for(int i = 0; i < ccol.length; i++)
      col[i] = ccol[i];
    num = nnum;
    refSeqNum = 0;
    refSeqAA = ' ';
  }

}
