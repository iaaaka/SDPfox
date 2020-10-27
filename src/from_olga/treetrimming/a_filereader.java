package from_olga.treetrimming;
import java.lang.*;
import java.util.*;
import java.awt.*;
import java.io.*;

/**
 * <p>Title: </p>
 * <p>Description: Find Palindrome Inside Promoter</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: IG</p>
 * @author unascribed
 * @version 1.0
 */

public class a_filereader
{
  java.util.Vector data=new java.util.Vector();
  public a_filereader(String s)
  {
    try
    {
      BufferedReader in
        = new BufferedReader(new FileReader(s));

      while(in.ready())
      {
        data.add(in.readLine());
      }
      in.close();
    }
    catch (IOException e)
    {
      System.out.print("File ");
      System.out.print(s);
      System.out.print(" cannot be opened.\n");
      System.exit(1);
    }
  }
}
