package from_olga.sdppred;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: </p>
 * @author Olga V. Kalinina
 * @version 1.0
 */

public class ESigma {
    double[] arr;
    int length, height;

    ESigma(int i_length, int i_height) {
      length = i_length;
      height = i_height;
      arr = new double [length * height];
    }

    void put(int thickness, int pos, double val) {
      arr[thickness * length + pos] = val;
    }

    double get(int thickness, int pos) {
      return arr[thickness * length + pos];
    }

  public ESigma() {
  }

}
