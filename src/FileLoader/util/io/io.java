package FileLoader.util.io;

import java.io.*;
import java.net.*;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2005</p>
 * <p>Company: </p>
 * @author not attributable
 * @version 1.0
 */

public class io {
  private io() { }

  public static void copyFile(File src_file,File dst_file) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(src_file));
    OutputStream os = new BufferedOutputStream(new FileOutputStream(dst_file));
    copyStream(is,os);
    is.close();
    os.close();
  }
  public static void copyStream(InputStream is, OutputStream os) throws IOException {
    byte[] buffer = new byte[10000];
    for(;;) {
      int len = is.read(buffer);
      if(len<=0) break;
      os.write(buffer,0,len);
    }
    os.flush();
  }

  public static String readTextStream(InputStream is) throws IOException {
    return new String(readStream(is));
  }
  public static byte[] readStream(InputStream is) throws IOException {
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    copyStream(is,baos);
    return baos.toByteArray();
  }
  public static String readTextFile(File file) throws IOException {
    InputStream is = new BufferedInputStream(new FileInputStream(file));
    String ret = readTextStream(is);
    is.close();
    return ret;
  }
  public static void writeTextFile(File f,String text) throws IOException {
    Writer w = new BufferedWriter(new FileWriter(f));
    w.write(text);
    w.close();
  }

  public static String getFileNameExtention(String file_name) {
    int dot_pos = file_name.lastIndexOf('.');
    if(dot_pos<0)return "";
    return file_name.substring(dot_pos+1);
  }
  public static String getFileNameWithoutExtention(String file_name) {
    int dot_pos = file_name.lastIndexOf('.');
    if(dot_pos<0)return file_name;
    return file_name.substring(0,dot_pos);
  }

  public static String rebuildPath(String filepath)
  { char c=File.separatorChar;
    if (c=='/')
      return filepath.replace('\\',c);
    else
      return filepath.replace('/',c);
  }

  public static String getFileExtention(File f) {
    String file_name = f.getName();
    int dot_pos = file_name.lastIndexOf('.');
    if(dot_pos<0)return "";
    return file_name.substring(dot_pos+1);
  }

  public static String removeBackDirCases(String dir) {
    //System.out.println("removeBackDirCases: dir="+dir);
    File f = new File(dir);
    File par_f = f.getParentFile();
    if((par_f==null)||(par_f.compareTo(f)==0))return dir;
    while((par_f!=null)&&(par_f.compareTo(f)!=0)&&(par_f.getName().equals(".")))par_f = par_f.getParentFile();
    if((par_f==null)||(par_f.compareTo(f)==0))return dir;
    String par_dir = removeBackDirCases(par_f.getAbsolutePath());
    String name = f.getName();
    //System.out.println("rbs: par="+par_dir+", name="+name);
    if(!name.equals("..")) {
      if(!par_dir.endsWith(File.separator))par_dir += File.separator;
      return par_dir+name;
    }
    return new File(par_dir).getParent();
  }

  /*
  public static String correctSlashes(String str) {
    str = StringUtil.replaceSubString(str,"\\",File.separator);
    str = StringUtil.replaceSubString(str,"/",File.separator);
    return str;
  }
  */

  public static String fileContentToString(String fname) throws Exception{
    return fileContentToString(new File(fname));
  }
  public static String fileContentToString(File f) throws Exception{
    Reader is=new BufferedReader(new FileReader(f));
    StringBuffer sb=new StringBuffer();
    char[] b=new char[8192];
    int n;
    while ((n=is.read(b))>0) sb.append(b,0,n);
    return sb.toString();
  }

  public static byte[] fileContent2bytes(String fname) throws Exception {
    return fileContent2bytes(new File(fname));
  }
  public static byte[] fileContent2bytes(File f) throws Exception {
    return stream2bytes(new FileInputStream(f));
  }
  public static byte[] stream2bytes(InputStream is) throws Exception {
    BufferedInputStream bis = new BufferedInputStream(is);
    ByteArrayOutputStream ret = new ByteArrayOutputStream();
    BufferedOutputStream bos = new BufferedOutputStream(ret);
    byte[] buf = new byte[10000];
    int len = -1;
    while((len = bis.read(buf,0,buf.length))>0) bos.write(buf,0,len);
    bos.close();
    bis.close();
    return ret.toByteArray();
  }

  public static void stringTofile(String text,String fname) throws Exception {
    stringToFile(text,new File(fname));
  }
  public static void stringToFile(String text,File f) throws Exception {
    Writer w = new BufferedWriter(new FileWriter(f));
    w.write(text);
    w.close();
  }

  public static void copyFile(String in_file,String out_path) throws Exception {
    byte[] buf = new byte[10000];
    File f = new File(out_path);
    String out_file = out_path;
    if((f.exists())&&(f.isDirectory())) {
      out_file = new File(out_path,new File(in_file).getName()).getAbsolutePath();
    }
    InputStream is = new BufferedInputStream(new FileInputStream(in_file));
    OutputStream os = new BufferedOutputStream(new FileOutputStream(out_file));
    for(;;) {
      int len = is.read(buf);
      if(len<=0)break;
      os.write(buf,0,len);
      if(len<buf.length)break;
    }
    os.close();
    is.close();
  }

  public static String loadURL(String url_path) throws Exception {
    URL url = new URL(url_path);
    InputStream is = url.openStream();
    String ret = io.readTextStream(is);
    is.close();
    return ret;
  }
}
