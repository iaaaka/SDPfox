package Objects;

import Math.StaticFunction;
import Util.*;
import java.io.*;
import Exception.*;

public class AlignmentData {
	public final Alignment alignment;
	public final Numerator seqNumerator;
	public Numerator groupNumerator;
	public Grouping grouping;

	
	public AlignmentData(){
		alignment = new Alignment();
		seqNumerator = new Numerator();
		groupNumerator = new Numerator();
		grouping = new Grouping();
	}
	
	public AlignmentData(Alignment a,Numerator s){
		alignment = a;
		seqNumerator = s;
		groupNumerator = new Numerator();
		grouping = new Grouping();
	}
	
	public AlignmentData(String fname)throws Exception{
		this();
		StaticFunction.readAlignment(this.alignment,this.grouping,this.seqNumerator,this.groupNumerator,fname);
	}
	
	public AlignmentData(InputStream is)throws Exception{
		this();
		StaticFunction.readAlignment(this.alignment,this.grouping,this.seqNumerator,this.groupNumerator,is);
	}
	
}
