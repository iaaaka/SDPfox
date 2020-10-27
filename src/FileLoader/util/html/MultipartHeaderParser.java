package FileLoader.util.html;

import java.io.*;
import java.util.*;
import FileLoader.util.io.*;

public class MultipartHeaderParser {
  private static String goodPrefix = "content-disposition: form-data; name=\"";
  private static String goodPrefix2 = "\"; filename=\"";
  private static String badPrefix = "content-type:";
  private static boolean debug = false; //false;
  private Hashtable<String,MultipartParam> hash;

  public MultipartHeaderParser(InputStream is) throws Exception {
	  byte[] buf = new byte[10000];
	  Hashtable<String,MultipartParam> ret = new Hashtable<String,MultipartParam>();
	  BufferedInputStream bi = new BufferedInputStream(is);
	  byte[] separator = readByteLine(bi);
	  if(debug) System.out.println("MultipartHeaderParser: separator = "+
			  "["+new String(separator)+"]");
	  //String eol = System.getProperty("line.separator");
	  //if(eol==null) eol = "\r\n";
	  for(;;) {
		  String line = readLine(bi);
		  if(line==null) break;
		  if(line.trim().length()==0) continue;
		  if(debug) System.out.println("MultipartHeaderParser: good = "+line);
		  if(!line.toLowerCase().startsWith(goodPrefix)) throw new Exception();
		  line = line.substring(goodPrefix.length());
		  String name = line.substring(0,line.indexOf('\"'));
		  if(debug) System.out.println("MultipartHeaderParser: name = "+name);
		  line = line.substring(name.length());
		  String file_name = null;
		  if(line.startsWith(goodPrefix2)) {
			  line = line.substring(goodPrefix2.length());
			  file_name = line.substring(0,line.indexOf('\"'));
			  if(debug) System.out.println("MultipartHeaderParser: filename = "+file_name);			  
		  }
		  for(;;) {
			  line = readLine(bi);
			  if(!line.toLowerCase().startsWith(badPrefix)) break;
			  if(debug) System.out.println("MultipartHeaderParser: bad = "+line);
		  }
		  if(debug) System.out.println("MultipartHeaderParser: empty = "+line);
		  // Тут в line находится пустая строка
		  ByteArrayOutputStream baos = new ByteArrayOutputStream();
		  int buf_qnt = 0;
		  //int last_carret = -1;
		  for(int i=0;;i++) {
			  int ch = bi.read();
			  if(ch<0) break;
			  if(buf_qnt==buf.length) {
				  baos.write(buf,0,buf.length-1000);
				  System.arraycopy(buf,buf.length-1000,buf,0,1000);
				  buf_qnt = 1000;
			  }
			  if(ch=='\n') {
				  // Тут проверим, не загрузили ли мы separator
				  if(buf_qnt>=separator.length) {
					  boolean is_sep = true;
					  int cur_pos = buf_qnt-1;
					  for(int k=0;k<10;k++) {
						  if((buf[cur_pos]=='-')||(buf[cur_pos]=='\r')) {
							  cur_pos--;
						  }
					  }
					  for(int k=0;k<separator.length;k++) {
						  if(buf[cur_pos-separator.length+1+k]!=separator[k]) {
							  is_sep = false;
							  break;
						  }
					  }
					  if(is_sep) {
						  buf_qnt = cur_pos-separator.length+1-2;
						  break;
					  }
				  }
				  //last_carret = buf_qnt; 
			  }
			  buf[buf_qnt] = (byte)ch;
			  buf_qnt++;
		  }
		  if(buf_qnt>0) {
			  baos.write(buf,0,buf_qnt);
		  }
		  byte[] data = baos.toByteArray();
		  if(debug) System.out.println("MultipartHeaderParser: val("+name+").length = "+data.length);
		  MultipartParam val = null;
		  if(file_name!=null) {
			  val = new MultipartParam(name,file_name,data,true); 
		  }
		  else {
			  val = new MultipartParam(name,new String(data),null,false);
		  }
		  ret.put(name,val);
	  }
	  bi.close();
	  this.hash = ret;
  }

  /*public static Properties parseMultipartHeader(InputStream is) throws Exception {
    Properties ret = new Properties();
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String separator = br.readLine();
    if(debug) System.out.println("MultipartHeaderParser: separator = "+separator);
    String eol = System.getProperty("line.separator");
    if(eol==null) eol = "\r\n";
    for(;;) {
      String line = br.readLine();
      if(line==null) break;
      if(line.trim().length()==0) continue;
      if(debug) System.out.println("MultipartHeaderParser: good = "+line);
      if(!line.toLowerCase().startsWith(goodPrefix)) throw new Exception();
      line = line.substring(goodPrefix.length());
      String name = line.substring(0,line.indexOf('\"'));
      if(debug) System.out.println("MultipartHeaderParser: name = "+name);
      for(;;) {
        line = br.readLine();
        if(!line.toLowerCase().startsWith(badPrefix)) break;
        if(debug) System.out.println("MultipartHeaderParser: bad = "+line);
      }
      if(debug) System.out.println("MultipartHeaderParser: empty = "+line);
      // Тут в line находится пустая строка
      StringBuffer sb = new StringBuffer();
      for(int i=0;;i++) {
        line = br.readLine();
        if(line.startsWith(separator)) break;
        if(debug) System.out.println("MultipartHeaderParser: value = "+line);
        if(i==1) sb.append(eol);
        sb.append(line);
        if(i>0) sb.append(eol);
      }
      String val = sb.toString();
      if(debug) System.out.println("MultipartHeaderParser: val("+name+").length = "+val.length());
      ret.put(name,val);
    }
    return ret;
  }*/

  public Hashtable<String,MultipartParam> getHash() {
	  return hash;
  }
  public MultipartParam getParamValue(String name) {
	  return hash.get(name);
  }
  private static byte[] readByteLine(InputStream is) throws Exception {
	  ByteArrayOutputStream baos = new ByteArrayOutputStream();
	  boolean has_data = false;
	  for(;;) {
		  int ch = is.read();
		  if(ch<0) break;
		  has_data = true;
		  if(ch=='\n') break;
		  if(ch=='\r') continue;
		  baos.write(ch);
	  }
	  if(!has_data) return null;
	  return baos.toByteArray();
  }
  private static String readLine(InputStream is) throws Exception {
	  byte[] arr = readByteLine(is);
	  if(arr==null) return null;
	  return new String(arr);
  }

}
