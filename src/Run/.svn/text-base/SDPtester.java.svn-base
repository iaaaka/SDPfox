package Run;

import java.io.*;

import java.util.*;
import Exception.AlignmentException;
import Exception.NumeratorException;
import Math.StaticFunction;
import Objects.AlignmentData;
import Objects.Positions;
import Objects.Project;

public class SDPtester {
		
	public static void main(String[] args)throws Exception {
		String work = "/home/mazin/src/SDPfoxWeb/in/fromVRamensky/";
		File[] als = (new File(work+"out")).listFiles();
		Arrays.sort(als);
		String out = work+"out/";
		for(int i=0;i<als.length;i++){
			if(als[i].getName().indexOf("sdp") != -1)
				continue;
			String sn = als[i].getName().substring(0, als[i].getName().length()-7);
			String outgr = out+sn+".groups";
			int ind = sn.lastIndexOf('_');
			String name = sn.substring(0,ind)+"/"+sn.substring(ind+1);
			System.out.println(name);
			try{
				SDPfoxRun.main(new String[]{"-i",outgr,"-m","sdplight","-o",out+sn+".sdp","-ref",name});
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
