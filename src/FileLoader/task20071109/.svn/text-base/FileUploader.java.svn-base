package FileLoader.task20071109;

import java.io.*;
import java.util.*;
import FileLoader.util.html.*;

public class FileUploader {

	public static InputStream getDataStream(InputStream is, String[] param,String[] otherParam,String[] otherParamValue)
			throws Exception {
		MultipartHeaderParser mhp = new MultipartHeaderParser(is);
		Hashtable<String, MultipartParam> pr = mhp.getHash();
		MultipartParam file;
		for(int i=0;i<otherParam.length;i++){
			file =  pr.get(otherParam[i]);
			if(file != null)
			otherParamValue[i] = pr.get(otherParam[i]).value;
		}
		for (int i = 0; i < param.length; i++) {
			file = pr.get(param[i]);
			if (file != null) {
				if (file.file) {
					if((new String(file.data).trim().length() != 0))
					return new ByteArrayInputStream(file.data);
				} else {
					if(file.value.trim().length() !=0)
					return new ByteArrayInputStream(file.value.getBytes());
				}
			}
		}
		return null;
	}

	public static String loadFile(InputStream is) throws Exception {
		MultipartHeaderParser mhp = new MultipartHeaderParser(is);
		Hashtable<String, MultipartParam> pr = mhp.getHash();
		MultipartParam file = pr.get("filepath");
		String out_file = "c:\\" + file.value;
		OutputStream os = new BufferedOutputStream(new FileOutputStream(
				out_file));
		os.write(file.data);
		os.close();
		return out_file;
	}

}
