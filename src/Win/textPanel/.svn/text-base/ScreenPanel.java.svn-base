package Win.textPanel;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.util.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

public class ScreenPanel extends JPanel {
  private int scr_w;
  //private int scr_h;
  private Vector map; // ������ ��������, ������ �� ������� ���� ScreenChar[]
  //private Vector foreground; // ������ �������� (Color[])
  //private Vector background; // ������ �������� (Color[])
  //private Font scr_font;  // ����������� ������ ���� ������ ����������������
  private int font_w;
  private int font_h;
  private int font_y_base_shift;
  private char[] one_char_arr = new char[1];
  private BufferedImage bi;
  private BufferedImage tmp_bi;
  private Graphics bi_gr;
  private Graphics tmp_bi_gr;
  private JScrollBar vert_sb;
  private JScrollBar hor_sb;
  private JPanel hor_sb_panel;
  private JPanel sb_coner_panel;
  private JPanel view_panel; // �� ������ ������ ��������� � �������� bi, ������
                             // ��� ��������� �� ������� ������������������� bi.
  //private Dimension canvas_size; // ������ ������ ���� ������� ��������� (����
                                 // ��� ������)
  private Point view_pos;
  private boolean is_scrolls_blocked = false;
  private boolean is_from_outside = false;
  private Rectangle view_rect = null;
  private Rectangle temp_symbol_rect = null;
  private AdjustmentListener horisontalScrollListener;
  private AdjustmentListener verticalScrollListener;
  private static int SCROLL_BAR_THICK = 16;

  public ScreenPanel() {
    this(12);
  }
  public ScreenPanel(int font_size) {
    this(0,0,font_size);
  }
  public ScreenPanel(int w,int h) {
    this(w,h,12);
  }
  public ScreenPanel(int w,int h,int font_size) {
    super.setLayout(new BorderLayout());
    this.view_pos = new Point(0,0);
    this.view_rect = new Rectangle(0,0,0,0);
    this.view_panel = new JPanel() {
      public void paint(Graphics g) {
        view_panel_paint(g);
      }
    };
    this.addComponentListener(new ComponentAdapter() {
      public void componentResized(ComponentEvent e) {
        updateCanvas();
      }
    });
    this.add(this.view_panel,BorderLayout.CENTER);
    this.scr_w = w;
    this.setBackground(Color.white);
    this.setForeground(Color.black);
    this.setFont(new Font("Monospaced",0,font_size));
    FontMetrics fm = this.getFontMetrics(this.getFont());
    this.font_h = fm.getHeight();
    this.font_w = fm.charWidth('W');
    temp_symbol_rect = new Rectangle(0,0,font_w,font_h);
    this.font_y_base_shift = fm.getAscent();
    this.map = new Vector(h);
    for(int i=0;i<h;i++) {
      this.addLine(w);
    }
    //updateCanvas();
    is_from_outside = true;
    sb_coner_panel = new JPanel();
    Dimension dim = new Dimension(SCROLL_BAR_THICK,SCROLL_BAR_THICK);
    sb_coner_panel.setMinimumSize(dim);
    sb_coner_panel.setMaximumSize(dim);
    sb_coner_panel.setPreferredSize(dim);
    this.addMouseWheelListener(new MouseWheelListener(){
    	public void mouseWheelMoved(MouseWheelEvent e){
    		e.getWheelRotation();
    		vert_sb.setValue(vert_sb.getValue()+e.getWheelRotation()*35);
    	}
    });
  }
  public void setBackground(Color bg) {
    super.setBackground(bg);
    if(this.view_panel!=null) this.view_panel.setBackground(bg);
  }
  public void setLayout(LayoutManager mgr) {
    if(is_from_outside)
      throw new RuntimeException("You can't change layout of "+this);
    super.setLayout(mgr);
  }
  protected void addImpl(Component comp, Object constraints, int index) {
    if(is_from_outside)
      throw new RuntimeException("You can't add subcomponents into "+this);
    super.addImpl(comp,constraints,index);
  }
  public void removeAll() {
    if(is_from_outside) {
      //throw new RuntimeException("You can't remove all subcomponents from "+this);
      // ������ ��� ������
      this.map.removeAllElements();
      recalculateMaxWidth();
      this.updateScrolls();
      if(this.isVisible()) {
        this.paint(this.view_rect,true);
      }
      return;
    }
    super.removeAll();
  }
  public void remove(int i) {
    if(is_from_outside)
      throw new RuntimeException("You can't remove subcomponents from "+this);
    super.remove(i);
  }
  private void view_panel_paint(Graphics g) {
    if(bi==null) {
      if((this.view_panel.getWidth()>0)&&(this.view_panel.getHeight()>0))
        updateCanvas();
      return;
    }
    // ����� ������ ������������ �������� �� �����
    g.drawImage(bi,0,0,this.getBackground(),null);
  }
  private void flipTempImageBuffer() {
    if((this.tmp_bi==null)||(this.tmp_bi.getWidth()!=this.bi.getWidth())||
       (this.tmp_bi.getHeight()!=this.bi.getHeight())) {
      this.tmp_bi = new BufferedImage(bi.getWidth(),bi.getHeight(),
                                      BufferedImage.TYPE_INT_RGB);
      this.tmp_bi_gr = this.tmp_bi.getGraphics();
      this.tmp_bi_gr.setFont(this.getFont());
    }
    BufferedImage bi_ = this.bi;
    Graphics bi_gr_ = this.bi_gr;
    this.bi = this.tmp_bi;
    this.bi_gr = this.tmp_bi_gr;
    this.tmp_bi = bi_;
    this.tmp_bi_gr = bi_gr_;
  }
  private void updateViewByScrolls() {
    if(is_scrolls_blocked) return;
    scrollsAreChanged();
    // ���� �������� ��������� �������, �� ��� ����� �������� �������� bi.
    // ���������, ���������� �� ��������� ����
    int new_view_x = (this.hor_sb==null)?0:this.hor_sb.getValue();
    int new_view_y = (this.vert_sb==null)?0:this.vert_sb.getValue();
    if((new_view_x==this.view_pos.x)&&(new_view_y==this.view_pos.y)) return;
    // ����������
    // ���������, ��� ������, ������� ����� �����, �������� �� ������� ������ �
    // �����, � ����� ������������, ���� �� �������
    if((new_view_x==this.view_pos.x)&&(new_view_y>this.view_pos.y)) {
      int shift = new_view_y-this.view_pos.y;
      /*this.bi_gr.drawImage(
          this.bi.getSubimage(0,shift,this.bi.getWidth(),this.bi.getHeight()-shift),
          0,0,null);*/
      this.bi_gr.drawImage(this.bi,
                           0,0,this.bi.getWidth(),this.bi.getHeight()-shift,
                           0,shift,this.bi.getWidth(),this.bi.getHeight(),null);
      this.view_pos.y = new_view_y;
      this.view_rect.y = new_view_y;
      this.rel_paint(0,this.bi.getHeight()-shift,this.bi.getWidth(),shift,false);
      this.view_panel.repaint();
      return;
    }
    if((new_view_x==this.view_pos.x)&&(new_view_y<this.view_pos.y)) {
      int shift = this.view_pos.y-new_view_y;
      this.flipTempImageBuffer();
      /*this.bi_gr.drawImage(
          this.tmp_bi.getSubimage(0,0,this.bi.getWidth(),this.bi.getHeight()-shift),
          0,shift,null);*/
      this.bi_gr.drawImage(this.tmp_bi,
                           0,shift,this.bi.getWidth(),this.bi.getHeight(),
                           0,0,this.bi.getWidth(),this.bi.getHeight()-shift,null);
      this.view_pos.y = new_view_y;
      this.view_rect.y = new_view_y;
      this.rel_paint(0,0,this.bi.getWidth(),shift,false);
      this.view_panel.repaint();
      return;
    }
    if((new_view_x>this.view_pos.x)&&(new_view_y==this.view_pos.y)) {
      int shift = new_view_x-this.view_pos.x;
      /*this.bi_gr.drawImage(
          this.bi.getSubimage(shift,0,this.bi.getWidth()-shift,this.bi.getHeight()),
          0,0,null);*/
      this.bi_gr.drawImage(this.bi,
                           0,0,this.bi.getWidth()-shift,this.bi.getHeight(),
                           shift,0,this.bi.getWidth(),this.bi.getHeight(),null);
      this.view_pos.x = new_view_x;
      this.view_rect.x = new_view_x;
      this.rel_paint(this.bi.getWidth()-shift,0,shift,this.bi.getHeight(),false);
      this.view_panel.repaint();
      return;
    }
    if((new_view_x<this.view_pos.x)&&(new_view_y==this.view_pos.y)) {
      int shift = this.view_pos.x-new_view_x;
      this.flipTempImageBuffer();
      /*if(this.bi.getWidth()-shift>this.tmp_bi.getWidth()) {
        System.out.print("");
      }
      this.bi_gr.drawImage(
          this.tmp_bi.getSubimage(0,0,this.bi.getWidth()-shift,this.bi.getHeight()),
          shift,0,null);*/
      this.bi_gr.drawImage(this.tmp_bi,
                           shift,0,this.bi.getWidth(),this.bi.getHeight(),
                           0,0,this.bi.getWidth()-shift,this.bi.getHeight(),null);
      this.view_pos.x = new_view_x;
      this.view_rect.x = new_view_x;
      this.rel_paint(0,0,shift,this.bi.getHeight(),false);
      this.view_panel.repaint();
      return;
    }
    // �� ��� ������ �� ��������, ��...
    this.rel_paint(0,0,this.bi.getWidth(),this.bi.getHeight(),true);
  }
  private void print(Graphics g,int x,int y) {
    this.temp_symbol_rect.x = x*font_w;
    this.temp_symbol_rect.y = y*font_h;
    Color c = g.getColor();
    Color bck = this.getBackground();
    ScreenChar ch = null;
    if((this.temp_symbol_rect.intersects(this.view_rect))&&
       (y>=0)&&(y<map.size())) {
      Vector line = (Vector)map.get(y);
      if((x>=0)&&(x<line.size())) {
        ch = (ScreenChar)line.get(x);
        bck = ch.getBackground();
      }
    }
    g.setColor(bck);
    g.fillRect(temp_symbol_rect.x-this.view_pos.x,
               temp_symbol_rect.y-this.view_pos.y,font_w,font_h);
    if(ch!=null) {
      g.setColor(ch.getForeground());
      one_char_arr[0] = ch.getChar();
      g.drawChars(one_char_arr,0,1,x*font_w-this.view_pos.x,
                  y*font_h+font_y_base_shift-this.view_pos.y);
    }
    g.setColor(c);
  }
  public Rectangle rect2text(Rectangle r) {
    int x = (r.x)/font_w;
    int y = (r.y)/font_h;
    int w = (r.x+r.width-1)/font_w-x+1;
    int h = (r.y+r.height-1)/font_h-y+1;
    return new Rectangle(x,y,w,h);
  }
  //public void paint(Rectangle r,boolean onScreen) {
  //  inner_paint(
  //}
  private void rel_paint(Rectangle r,boolean onScreen) {
    this.rel_paint(r.x,r.y,r.width,r.height,onScreen);
  }
  private void rel_paint(int x,int y,int w,int h,boolean onScreen) {
    this.paint(this.view_pos.x+x,this.view_pos.y+y,w,h,onScreen);
  }
  private void paint(int x,int y,int w,int h,boolean onScreen) {
    this.paint(new Rectangle(x,y,w,h),onScreen);
  }
  private Rectangle getViewRect() {
    return this.view_rect;
  }
  private void paint(Rectangle r,boolean onScreen) {
    if(this.bi_gr==null) return;
    if(!r.intersects(this.view_rect)) return;
    //this.bi_gr.setClip(x,y,w,h);
    //this.inner_paint(this.bi_gr);
    //this.bi_gr.setClip(null);
    Rectangle text_rect = rect2text(r);
    for(int i=0;i<text_rect.height;i++) {
      for(int j=0;j<text_rect.width;j++) {
        int x = text_rect.x+j;
        int y = text_rect.y+i;
        //if((x<0)||(x>=scr_w)||(y<0)||(y>=scr_h)) continue;
        print(this.bi_gr,x,y);
      }
    }
    if(onScreen)
      this.view_panel.repaint(r.x-this.view_pos.x,r.y-this.view_pos.y,
                              r.width,r.height);
  }
  private void checkLine(int y) {
    if((y<0)||(y>=this.map.size()))
      throw new RuntimeException("Text line "+y+" is out of bounds "+
                                 "(must be from 0 to "+(map.size()-1)+")");
  }
  public ScreenChar getScreenChar(int x,int y) {
    if((x<0)||(x>=this.scr_w))
      throw new RuntimeException("Text position "+x+" is out of bounds "+
                                 "(must be from 0 to "+(this.scr_w-1)+")");
    checkLine(y);
    Vector line = (Vector)map.get(y);
    if((x<0)||(x>=line.size()))
      throw new RuntimeException("Text position "+x+" for line "+y+" is out of "+
                                 "length (must be from 0 to "+(line.size()-1)+")");

    return (ScreenChar)line.get(x);
  }
  public int getLineLength(int y) {
    checkLine(y);
    Vector line = (Vector)map.get(y);
    return line.size();
  }
  public char getChar(int x,int y) {
    return this.getScreenChar(x,y).getChar();
  }
  public Color getForeground(int x,int y) {
    return this.getScreenChar(x,y).getForeground();
  }
  public Color getBackground(int x,int y) {
    return this.getScreenChar(x,y).getBackground();
  }
  public void setChar(char c,int x,int y) {
    this.getScreenChar(x,y).setChar(c);
  }
  public void setForeground(Color for_gr,int x,int y) {
    this.getScreenChar(x,y).setForeground(for_gr);
  }
  public void setBackground(Color bck_gr,int x,int y) {
    this.getScreenChar(x,y).setBackground(bck_gr);
  }
  public int getMaxLineLength() {
    return this.scr_w;
  }
  public int getLineCount() {
    return this.map.size();
  }
  private void recalculateMaxWidth() {
    int max_w = 0;
    for(int i=0;i<this.map.size();i++) {
      Vector line = (Vector)map.get(i);
      if(max_w<line.size()) max_w = line.size();
    }
    this.scr_w = max_w;
  }
  /**
   * ����� ����������, ����� ���������� ��������� �������� ������.
   * ��� ����� ������������� ������ �������, ����������������� �������� �
   * ��������� (���������/�������) ������.
   */
  public void updateCanvas() {
    //new Exception("size="+this.getSize()).printStackTrace();
    if((this.getWidth()<=0)||(this.getHeight()<=0)) return;
    Dimension view_size = this.updateScrolls();
    if((view_size.width<=0)||(view_size.height<=0)) return;
    if((this.bi!=null)&&
       (this.bi.getWidth()==view_size.width)&&
       (this.bi.getHeight()==view_size.height)) return;
    // ������ � ���������
    BufferedImage old_bi = bi;
    this.bi = new BufferedImage(view_size.width,view_size.height,
                                BufferedImage.TYPE_INT_RGB);
    this.view_rect = new Rectangle(this.view_pos,view_size);
    this.bi_gr = bi.getGraphics();
    this.bi_gr.setFont(this.getFont());
    //this.bi_gr.translate(-this.view_pos.x,-this.view_pos.y);
    // ��� �����, ��������, ��� �� ����� - ������
    Dimension common_dim = null;
    if(old_bi!=null) {
      common_dim = new Dimension(
          Math.min(old_bi.getWidth(),bi.getWidth()),
          Math.min(old_bi.getHeight(),bi.getHeight()));
      /*this.bi_gr.drawImage(
          old_bi.getSubimage(0,0,common_dim.width,common_dim.height),0,0,null);*/
      this.bi_gr.drawImage(old_bi,
                           0,0,common_dim.width,common_dim.height,
                           0,0,common_dim.width,common_dim.height,null);
      if(common_dim.height<this.bi.getHeight()) {
        this.rel_paint(0,common_dim.height,common_dim.width,
                       this.bi.getHeight()-common_dim.height,true);
      }
    }
    else {
      common_dim = new Dimension(0,0);
    }
    if(common_dim.width<this.bi.getWidth()) {
      this.rel_paint(common_dim.width,0,this.bi.getWidth()-common_dim.width,
                     this.bi.getHeight(),true);
      //this.inner_paint(this.bi_gr);
      //this.bi_gr.setClip(null);
      //repaint = true;
    }
    //if(repaint) this.view_panel.repaint();
  }
  
  private Dimension updateScrolls() {
    Dimension view_size = this.view_panel.getSize();
    if((view_size.width<=0)||(view_size.height<=0)) return view_size;
    Dimension dim = new Dimension(
        Math.max(this.font_w*scr_w,this.getWidth()-SCROLL_BAR_THICK),
        Math.max(this.font_h*map.size(),this.getHeight()-SCROLL_BAR_THICK));
    boolean need_hor = false;
    boolean need_vert = false;
    boolean someth_changed = false;
    if(((dim.height>this.getHeight())&&(dim.width>this.getWidth()-SCROLL_BAR_THICK))||
       ((dim.height>this.getHeight()-SCROLL_BAR_THICK)&&(dim.width>this.getWidth()))) {
      need_hor = true;
      need_vert = true;
    }
    else {
      if(dim.height>this.getHeight()) need_vert = true;
      if(dim.width>this.getWidth()) need_hor = true;
    }
    if((this.hor_sb==null)&&(need_hor)) {
      is_scrolls_blocked = true;
      this.hor_sb = new JScrollBar(JScrollBar.HORIZONTAL);
      this.hor_sb.setUnitIncrement(5*this.font_w);
      this.hor_sb.addAdjustmentListener(new AdjustmentListener() {
        public void adjustmentValueChanged(AdjustmentEvent e) {
          updateViewByScrolls();
        }
      });
      hor_sb.addAdjustmentListener(horisontalScrollListener);
      is_from_outside = false;
      this.hor_sb_panel = new JPanel(new BorderLayout());
      this.hor_sb_panel.add(this.hor_sb,BorderLayout.CENTER);
      if(this.vert_sb!=null)
        this.hor_sb_panel.add(this.sb_coner_panel,BorderLayout.EAST);
      this.add(this.hor_sb_panel,BorderLayout.SOUTH);
      is_from_outside = true;
      view_size.height -= SCROLL_BAR_THICK;
      someth_changed = true;
    }
    if((this.hor_sb!=null)&&(!need_hor)) {
      is_scrolls_blocked = true;
      is_from_outside = false;
      this.remove(this.hor_sb_panel);
      is_from_outside = true;
      this.hor_sb_panel = null;
      this.hor_sb = null;
      view_size.height += SCROLL_BAR_THICK;
      someth_changed = true;
    }
    if((this.vert_sb==null)&&(need_vert)) {
      is_scrolls_blocked = true;
      this.vert_sb = new JScrollBar(JScrollBar.VERTICAL);
      this.vert_sb.setUnitIncrement(5*this.font_h);
      this.vert_sb.addAdjustmentListener(new AdjustmentListener() {
        public void adjustmentValueChanged(AdjustmentEvent e) {
          updateViewByScrolls();
        }
      });
      vert_sb.addAdjustmentListener(verticalScrollListener);
      is_from_outside = false;
      this.add(this.vert_sb,BorderLayout.EAST);
      if(this.hor_sb_panel!=null)
        this.hor_sb_panel.add(this.sb_coner_panel,BorderLayout.EAST);
      is_from_outside = true;
      view_size.width -= SCROLL_BAR_THICK;
      someth_changed = true;
    }
    if((this.vert_sb!=null)&&(!need_vert)) {
      is_scrolls_blocked = true;
      is_from_outside = false;
      this.remove(this.vert_sb);
      if(this.hor_sb_panel!=null)
        this.hor_sb_panel.remove(this.sb_coner_panel);
      is_from_outside = true;
      this.vert_sb = null;
      view_size.width += SCROLL_BAR_THICK;
      someth_changed = true;
    }
    if(someth_changed) {
      //new Exception("Validating...").printStackTrace();
      this.validate();
      scrollsAreChanged();
    }
    // ������ ���������� � ���������
    is_scrolls_blocked = true;
    if(this.hor_sb!=null) {
      this.hor_sb.setValues(this.view_pos.x,view_size.width,0,dim.width);
      this.hor_sb.setBlockIncrement(view_size.width/2);
    }
    if(this.vert_sb!=null) {
      this.vert_sb.setValues(this.view_pos.y,view_size.height,0,dim.height);
      this.vert_sb.setBlockIncrement(view_size.height/2);
    }
    this.is_scrolls_blocked = false;
    return view_size;
  }

  private Vector createScreenLine(int length) {
    Vector line = new Vector(length);
    for(int i=0;i<length;i++)
      line.add(new ScreenChar(' ',this.getForeground(),this.getBackground()));
    return line;
  }
  private Vector createScreenLine(ScreenChar[] line) {
    return new Vector(Arrays.asList(line));
  }
  private Vector createScreenLine(String text) {
    Vector line = createScreenLine(text.length());
    for(int i=0;i<line.size();i++) {
      ((ScreenChar)line.get(i)).setChar(text.charAt(i));
    }
    return line;
  }
  public int addLine(int length) {
    return addLine(createScreenLine(length));
  }
  public int addLine(ScreenChar[] line) {
    return addLine(createScreenLine(line));
  }
  public int addLine(String text) {
    return addLine(createScreenLine(text));
  }
  private int addLine(Vector line) {
    this.map.add(line);
    if(this.scr_w<line.size()) this.scr_w = line.size();
    this.updateScrolls();
    int y = this.map.size()-1;
    if(this.isVisible()) {
      this.paint(getLineRect(y),true);
      //this.revalidate();
      /*Container par = this.getParent();
      if(par instanceof JViewport) {
        Dimension dim = this.getPreferredSize();
        ((JViewport)par).reshape(0,0,dim.width,dim.height);
      }*/
    }
    return y;
  }
  public Rectangle getLineRect(int y) {
    return new Rectangle(0,y*font_h,getLineLength(y)*font_w,font_h);
  }
  public ScreenChar[] getLine(int y) {
    checkLine(y);
    Vector line = (Vector)map.get(y);
    ScreenChar[] ret = new ScreenChar[line.size()];
    line.copyInto(ret);
    return ret;
  }
  public void setLine(int y,int length) {
    setLine(y,createScreenLine(length));
  }
  public void setLine(int y,ScreenChar[] line) {
    setLine(y,createScreenLine(line));
  }
  public void setLine(int y,String text) {
    setLine(y,createScreenLine(text));
  }
  private void setLine(int y,Vector line) {
    Vector old_line = (Vector)this.map.get(y);
    this.map.set(y,line);
    if(line.size()>=old_line.size()) {
      if(this.scr_w<line.size()) this.scr_w = line.size();
    }
    else {
      recalculateMaxWidth();
    }
    this.updateScrolls();
    if(this.isVisible()) this.paint(getLineRect(y),true);
  }
  //private

  ////////////////////////////////////////////////////////////
  public static void main(String[] args) {
    final ScreenPanel scrn_p = new ScreenPanel(/*80,25*/);
    scrn_p.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
    	  ScreenPanel.addRandomLine(scrn_p,1000);
      }
    });
    /*for(int y=0;y<25;y++) {
      for(int x=0;x<80;x++) {
        scrn_p.setChar((char)(32+x+y),x,y);
        scrn_p.setBackground(arr[x%arr.length],x,y);
        scrn_p.setForeground(arr[y%arr.length],x,y);
      }
    }*/
    //JScrollPane sp = new JScrollPane();
    /*sp.getViewport().setScrollMode(JViewport.BACKINGSTORE_SCROLL_MODE);
    sp.getViewport().add(scrn_p);*/
    JFrame frm = new JFrame();
    frm.getContentPane().add(/*sp);*/ scrn_p);
    frm.setSize(900,700);
    frm.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    GuiUtil.centerWindow(frm);
    GuiUtil.setFrameIcon(frm,"bibi");
    for(int i=0;i<100;i++) {
	  ScreenPanel.addRandomLine(scrn_p,1000);
    }
    frm.setVisible(true);
  }
  /*protected static void addRandomLine(ScreenPanel sp,Color[] carr) {
    // ���� ��������� �����
    addRandomLine(sp,carr,sp.getMaxLineLength()*11/10);
  }*/
  protected static void addRandomLine(ScreenPanel sp,Color[] carr,int length) {
	int shift = sp.getLineCount();
    int y = sp.addLine(length);
    ScreenChar[] line = sp.getLine(y);
    for(int x=0;x<length;x++) {
      line[x].setChar((char)(32+x+y));
      line[x].setBackground(carr[(x+shift)%carr.length]);
      line[x].setForeground(carr[(y+shift)%carr.length]);
    }
    sp.setLine(y,line);
  }
  protected static final Color[] arr = {
	      Color.WHITE,Color.LIGHT_GRAY,Color.GRAY,Color.DARK_GRAY,
	      Color.BLACK,Color.RED,Color.PINK,Color.ORANGE,
	      Color.YELLOW,Color.GREEN,Color.MAGENTA,Color.CYAN,
	      Color.BLUE};
  protected static void addRandomLine(ScreenPanel sp,int len) {
	  if(sp.getLineCount()>0) {
        int max_len = sp.getMaxLineLength()*11/10;
        len = (int)(Math.random()*max_len);
      }
      addRandomLine(sp,arr,len);
  }
  /**
   * ����� p ������ � �����������, ������������ ������� ����������, � ��
   * ���� ����������� �������.
   */
  public int getLineAtPoint(Point p) {
    return (p.y+this.view_pos.y)/font_h;
  }
  /**
   * ����� p ������ � �����������, ������������ ������� ����������, � ��
   * ���� ����������� �������.
   */
  public int getColumnAtPoint(Point p) {
    return (p.x+this.view_pos.x)/font_w;
  }
  public int insertLine(int line_pos,int length) {
    return insertLine(line_pos,createScreenLine(length));
  }
  public int insertLine(int line_pos,ScreenChar[] line) {
    return insertLine(line_pos,createScreenLine(line));
  }
  public int insertLine(int line_pos,String text) {
    return insertLine(line_pos,createScreenLine(text));
  }
  private int insertLine(int line_pos,Vector line) {
    this.map.add(line_pos,line);
    if(this.scr_w<line.size()) this.scr_w = line.size();
    this.updateScrolls();
    if(this.isVisible()) {
      this.paint(this.view_rect,true);
    }
    return line_pos;
  }
  
  public void myRepaint(){
	  this.paint(this.view_rect,true);
  }
  
  public int deleteLine(int line_pos) {
    this.map.remove(line_pos);
    recalculateMaxWidth();
    this.updateScrolls();
    if(this.isVisible()) {
      this.paint(this.view_rect,true);
    }
    return line_pos;
  }
  public int getCharWidth() {
    return this.font_w;
  }
  public int getCharHeight() {
    return this.font_h;
  }
  public JScrollBar getHorizontalScrollBar() {
    return this.hor_sb;
  }
  
  public JScrollBar getVerticalScrollBar(){
	  return vert_sb;
  }
  
  public void addAdjustmentListenerForHorisontalScroll(AdjustmentListener l){
	  if(this.hor_sb != null){
		  horisontalScrollListener = l;
		  hor_sb.addAdjustmentListener(l);
	  }else{
		  horisontalScrollListener = l;
	  }
  }
  
  public void addAdjustmentListenerForVerticalScroll(AdjustmentListener l){
	  if(this.vert_sb != null){
		  verticalScrollListener = l;
		  vert_sb.addAdjustmentListener(l);
	  }else{
		  verticalScrollListener = l;
	  }
  }
  
  public void scrollsAreChanged() {
	  
  }
}
