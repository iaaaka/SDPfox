package from_olga.conservationscore;
import java.lang.*;
import java.util.*;
/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class cmp implements Comparator {
    cmp() {;}
    public int compare(Object o1, Object o2) {
      if(((Position)o1).consScore < ((Position)o2).consScore) return 1;
      if(((Position)o1).consScore > ((Position)o2).consScore) return -1;
      return 0;
    }
  }
