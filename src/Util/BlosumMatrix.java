package Util;

import java.io.*;
import java.util.StringTokenizer;

public class BlosumMatrix {
  public double[][] matrix = new double[20][20];
  BufferedReader br;
  String line;
  StringTokenizer st;

  public BlosumMatrix(int no) {
      InputStream is = BlosumMatrix.class.getResourceAsStream("/blosum/blosum" + no + ".qij"); 	
      br = new BufferedReader(new InputStreamReader(is));
    for (int i = 0; i != -1; i++) {
      try {
        line = br.readLine();
      }
      catch (IOException e) {
        System.out.println("Error, bad matrix");
      }
      if (line == null) {
        break;
      }
      st = new StringTokenizer(line);
      if (st.nextToken().equalsIgnoreCase("A")) {
        break;
      }
    }

    for (int i = 0; i < 20; i++) {
      try {
        line = br.readLine();
      }
      catch (IOException e) {
        System.out.println("Error, bad matrix");
      }
      if (line == null) {
        break;
      }
      st = new StringTokenizer(line);
      for (int j = 0; j <= i; j++) {
        matrix[i][j] = Double.parseDouble(st.nextToken());
        matrix[j][i] = matrix[i][j];
      }
    }
    try {
      br.close();
    }
    catch (IOException e) {
      System.out.println("Error, bad matrix");
    }
  }

}
