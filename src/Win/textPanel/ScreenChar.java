package Win.textPanel;

import java.awt.Color;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ScreenChar {
  private char c;
  private Color for_gr;
  private Color bck_gr;
  private int outline_mask;
  //
  public static final int OUTLINE_LEFT_PART = 1;
  public static final int OUTLINE_RIGHT_PART = 2;
  public static final int OUTLINE_TOP_PART = 4;
  public static final int OUTLINE_BOTTOM_PART = 8;
  public static final int OUTLINE_ALL =
      OUTLINE_LEFT_PART|OUTLINE_RIGHT_PART|OUTLINE_TOP_PART|OUTLINE_BOTTOM_PART;

  public ScreenChar(char c,Color for_gr,Color bck_gr) {
    this(c,for_gr,bck_gr,0);
  }
  public ScreenChar(char c,Color for_gr,Color bck_gr,int outline_mask) {
    this.c = c;
    this.for_gr = for_gr;
    this.bck_gr = bck_gr;
    this.outline_mask = outline_mask;
  }
  public char getChar() {
    return this.c;
  }
  public Color getForeground() {
    return this.for_gr;
  }
  public Color getBackground() {
    return this.bck_gr;
  }
  public void setChar(char c) {
    this.c = c;
  }
  public void setForeground(Color for_gr) {
    this.for_gr = for_gr;
  }
  public void setBackground(Color bck_gr) {
    this.bck_gr = bck_gr;
  }
  public boolean isOutlineLeftPart() {
    return (this.outline_mask&this.OUTLINE_LEFT_PART)!=0;
  }
  public boolean isOutlineRightPart() {
    return (this.outline_mask&this.OUTLINE_RIGHT_PART)!=0;
  }
  public boolean isOutlineTopPart() {
    return (this.outline_mask&this.OUTLINE_TOP_PART)!=0;
  }
  public boolean isOutlineBottomPart() {
    return (this.outline_mask&this.OUTLINE_BOTTOM_PART)!=0;
  }
  public void switchOutlineParts(int mask_bits,boolean on) {
    if(on) this.outline_mask |= mask_bits;
    else this.outline_mask &= (this.OUTLINE_ALL-mask_bits);
  }
  public void switchOutlineLeftPart(boolean on) {
    switchOutlineParts(this.OUTLINE_LEFT_PART,on);
  }
  public void switchOutlineRightPart(boolean on) {
    switchOutlineParts(this.OUTLINE_RIGHT_PART,on);
  }
  public void switchOutlineTopPart(boolean on) {
    switchOutlineParts(this.OUTLINE_TOP_PART,on);
  }
  public void switchOutlineBottomPart(boolean on) {
    switchOutlineParts(this.OUTLINE_BOTTOM_PART,on);
  }
}