package from_olga.sdppred;

/**
 * <p>Title: </p>
 * <p>Description: Class for work with amino acids abbreviations etc.</p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class AminoAcids {
  static int alphLen = 20;
  static public int index(char abbr) {
    if((abbr == 'A') || (abbr == 'a')) return 0;
    if((abbr == 'C') || (abbr == 'c')) return 1;
    if((abbr == 'D') || (abbr == 'd')) return 2;
    if((abbr == 'E') || (abbr == 'e')) return 3;
    if((abbr == 'F') || (abbr == 'f')) return 4;
    if((abbr == 'G') || (abbr == 'g')) return 5;
    if((abbr == 'H') || (abbr == 'h')) return 6;
    if((abbr == 'I') || (abbr == 'i')) return 7;
    if((abbr == 'K') || (abbr == 'k')) return 8;
    if((abbr == 'L') || (abbr == 'l')) return 9;
    if((abbr == 'M') || (abbr == 'm')) return 10;
    if((abbr == 'N') || (abbr == 'n')) return 11;
    if((abbr == 'P') || (abbr == 'p')) return 12;
    if((abbr == 'Q') || (abbr == 'q')) return 13;
    if((abbr == 'R') || (abbr == 'r')) return 14;
    if((abbr == 'S') || (abbr == 's')) return 15;
    if((abbr == 'T') || (abbr == 't')) return 16;
    if((abbr == 'V') || (abbr == 'v')) return 17;
    if((abbr == 'W') || (abbr == 'w')) return 18;
    if((abbr == 'Y') || (abbr == 'y')) return 19;
    return -1;
  }

  static public char abbr(int index) {
    if(index == 0) return 'A';
    if(index == 1) return 'C';
    if(index == 2) return 'D';
    if(index == 3) return 'E';
    if(index == 4) return 'F';
    if(index == 5) return 'G';
    if(index == 6) return 'H';
    if(index == 7) return 'I';
    if(index == 8) return 'K';
    if(index == 9) return 'L';
    if(index == 10) return 'M';
    if(index == 11) return 'N';
    if(index == 12) return 'P';
    if(index == 13) return 'Q';
    if(index == 14) return 'R';
    if(index == 15) return 'S';
    if(index == 16) return 'T';
    if(index == 17) return 'V';
    if(index == 18) return 'W';
    if(index == 19) return 'Y';
    return '#';
  }

  static public boolean isAminoAcid(char c) {
    return index(c) >= 0;
  }

  static public boolean isAminoAcid(char q, char t) {
    return q == t;
  }

  static public boolean isAminoAcid(char q, int t) {
    return index(q) == t;
  }

  public AminoAcids() {
  }

}
