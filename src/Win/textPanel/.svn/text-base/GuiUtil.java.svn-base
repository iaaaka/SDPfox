package Win.textPanel;

import java.io.*;
import java.net.*;
import java.awt.*;
import javax.swing.*;
import java.util.*;
import java.util.regex.*;
import javax.swing.border.*;

/**
 * <p>Title: Datagy Transformation Tools</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: Diasoft, Datagy</p>
 * @author unascribed
 * @version 1.0
 */

public class GuiUtil {
  private static final String DOT_JAR_FRAGMENT = ".jar!";

  public static String getIconPath(String icon_name) {
    String datagy_icon_path = null;
    File f = new File("img\\"+icon_name+".gif");
    if(f.exists()) {
      datagy_icon_path = "file:img/"+icon_name+".gif";
    }
    else {
      try {
        URL url = GuiUtil.class.getResource(icon_name+".gif");
        //System.out.println("url: "+url);
        datagy_icon_path = correctURLExclamations(url.toString());
      }catch(Exception ex) {
        ex.printStackTrace();
      }
    }
    return datagy_icon_path;
  }

  public static void setFrameIcon(Frame fr,String icon_name) {
    try {
      String icon_url_path = getIconPath(icon_name);
      if(icon_url_path!=null) {
        URL url = new URL(icon_url_path);
        fr.setIconImage(new ImageIcon(url).getImage());
      }
    }catch(Exception ex) {
      ex.printStackTrace();
    }
  }

  public static void showUIDefaults() {
    showHashtableSortedByKey(javax.swing.UIManager.getDefaults());
  }

  public static void showSystemProperties() {
    showHashtableSortedByKey(System.getProperties());
  }

  public static void showHashtableSortedByKey(Hashtable tbl) {
    Vector keys = new Vector(Collections.list(tbl.keys()));
    Collections.sort(keys,new Comparator() {
      public int compare(Object o,Object o_) {
        if((o==null)||(o_==null))
          throw new RuntimeException("Встречены null ключи");
        return o.toString().compareTo(o_.toString());
      }
    });
    Iterator it = keys.iterator();
    while(it.hasNext()) {
      Object key = it.next();
      System.out.println(""+key+": "+tbl.get(key));
    }
  }

  public static void main(String[] args) {
    // Для тестирования
    //showSystemProperties();
    showUIDefaults();
  }

  /**
   * Если хочется задать ширину столбца в точках нужно задать ее как есть, если в виде долей, то
   * нужно задать количество долей со знаком минус. Если столбцы справа не заданы, то для них
   * выставляется 10 пропорциональных долей.
   */
  public static boolean setTableColumnsWidthForAutoresizing(JTable tbl,int[] widths) {
    if(tbl.getColumnCount()<widths.length)return false;
    if(tbl.getColumnCount()>widths.length) {
      // Дополним колонки пропорциональными десяточками
      int[] real_w = new int[tbl.getColumnCount()];
      for(int i=0;i<real_w.length;i++) {
        if(i<widths.length)real_w[i] = widths[i];
        else real_w[i] = -10;
      }
      widths = real_w;
    }
    int tbl_w = tbl.getWidth();
    int already_set_w = 0;
    int scaled_parts = 0;
    for(int i=0;i<widths.length;i++) {
      if(widths[i]>=0)already_set_w += widths[i];
      else scaled_parts -= widths[i];
    }
    if(already_set_w+scaled_parts>tbl_w)return false;
    // Теперь мы можем рассчитать ширины столбцов
    tbl.setAutoResizeMode(tbl.AUTO_RESIZE_OFF);
    for(int i=0;i<widths.length;i++) {
      int col_w = (widths[i]>=0)?widths[i]:((tbl_w-already_set_w)*(-widths[i])/scaled_parts);
      tbl.getColumnModel().getColumn(i).setPreferredWidth(col_w);
    }
    tbl.setAutoResizeMode(tbl.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    return true;
  }


  public static void centerWindow(Window win) {
    Dimension winSize = win.getSize();
    centerWindow(win,winSize.width,winSize.height);
  }
  public static void centerWindow(Window win,int w,int h) {
    Dimension scrSize = Toolkit.getDefaultToolkit().getScreenSize();
    if(w>scrSize.width)w = scrSize.width;
    if(h>scrSize.height)h = scrSize.height;
    win.setBounds((scrSize.width-w)/2,(scrSize.height-h)/2,w,h);
  }
  public static boolean checkURL(URL url) {
    try {
      InputStream is = url.openStream();
      is.close();
      return true;
    }catch(Exception ex) {
    }
    return false;
  }
  public static JPanel createBorderEtchedLine() {
    JPanel ret = new JPanel(new BorderLayout());
    ret.setBorder(BorderFactory.createEtchedBorder());
    Dimension dim = new Dimension(2,2);
    ret.setMinimumSize(dim);
    ret.setPreferredSize(dim);
    ret.setMaximumSize(dim);
    return ret;
  }
  public static boolean askQuestion(Window wnd,String question) {
    return JOptionPane.YES_OPTION==JOptionPane.showOptionDialog(
        wnd,question,"Внимание",JOptionPane.YES_NO_OPTION,
        JOptionPane.WARNING_MESSAGE,null,new String[] {"Да", "Нет"}, "Да");
  }

  public static void noticeTeamDevelopmentInconsistency(String message,boolean throw_exception) {
    if(throw_exception)throw new RuntimeException(message);
  }
  public static void showJVMThreads() {
    ThreadGroup tg = Thread.currentThread().getThreadGroup();
    for(;tg.getParent()!=null;tg = tg.getParent());
    Thread[] tlist = new Thread[tg.activeCount()];
    tg.enumerate(tlist,true);
    System.out.println("Threads in JVM:");
    for(int i=0;i<tlist.length;i++) {
      Thread t = tlist[i];
      if(t==null) {
        System.out.println(""+(i+1)+". -");
        continue;
      }
      System.out.println(""+(i+1)+". Thread '"+t.getName()+"', "+
                         "group '"+t.getThreadGroup().getName()+"', "+
                         "priority="+t.getPriority()+
                         (t.isDaemon()?" [deamon]":""));
    }
  }




///////////////////////////// Взято из util.DatagyUtil ////////////////////////////////////




  public static void invokeAndWait(Runnable r) {
    if(SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      try {
        SwingUtilities.invokeAndWait(r);
      }catch(Exception ex) {}
    }
  }

  /*public static void invokeAndWait(Runnable r,ProcessMonitor pm) {
    if(SwingUtilities.isEventDispatchThread()) {
      r.run();
    }
    else {
      try {
        SwingUtilities.invokeAndWait(r);
      }catch(Exception ex) { pm.finish(ex,ex.getClass().getName()); }
    }
  }*/

  /*public static void showAndWait(Window w) {
    invokeAndWait(new SwingCaller(w) {
      public void run() {
        ((Window)this.getObject()).setVisible(true);
      }
    });
  }*/

  public static JPanel wrapWithJPanelAndBorder(JComponent jc,Border b)
  { JPanel jp=new JPanel(new BorderLayout(0,0));
    jp.add(jc);
    jp.setBorder(b);
    return jp; }


  public static String replaceExclamationMark(String str) {
    return str.replaceAll("!","%"+Integer.toHexString('!'));
  }
  public static String correctURLExclamations(String url_path) {
    int dot_jar_pos = url_path.indexOf(DOT_JAR_FRAGMENT);
    if(dot_jar_pos>=0) {
      String prefix = url_path.substring(0,dot_jar_pos);
      String suffix = url_path.substring(dot_jar_pos+DOT_JAR_FRAGMENT.length());
      url_path = replaceExclamationMark(prefix)+DOT_JAR_FRAGMENT+replaceExclamationMark(suffix);
    }
    return url_path;
  }
  public static URL correctURLExclamations(URL url) {
    try {
      return new URL(correctURLExclamations(url.toString()));
    }catch(Exception ex) {
      return url;
    }
  }
  public static void prepareSystemGraphicsStyle() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    }catch (Exception exc) {
      System.err.println("Error loading L&F: " + exc);
    }
    //try {
    //  UIManager.put("SplitPaneDivider.border",BorderFactory.createMatteBorder(0, 0, 0, 0, Color.white));
    //  UIManager.put("TabbedPane.contentBorderInsets", new Insets( 0, 0, 0, 0) );
    //}catch(Exception ex) {
    //  ex.printStackTrace();
    //}
    JFrame.setDefaultLookAndFeelDecorated( true );
    JDialog.setDefaultLookAndFeelDecorated( true );
  }
}
