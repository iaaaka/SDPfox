package Objects;

import java.util.ArrayList;
import Math.StaticFunction;
import java.io.*;

public class Group {
  public String name;
  public ArrayList<Sequence> sequences;

  public Group(String name) {
    this.name = name;
    sequences = new ArrayList<Sequence>();
  }

  public void addSeq(Sequence s) {
    sequences.add(s);
  }

  public void addSeq(String n, String s) {
    sequences.add(new Sequence(n, s));
  }

  public String toString() {
    return name;
  }
  
  public void printGroup(PrintWriter pw){
	  if(!name.equals(StaticFunction.NONGROUPED))
		  pw.println("==="+name+"===");
	  for(int i=0;i<sequences.size();i++){
		  pw.println(">"+sequences.get(i).name);
		  pw.println(sequences.get(i).seq);
	  }
  }
  
  public String getSequenceNames(){
	  StringBuffer result = new StringBuffer();
	  for(int i=0;i<sequences.size();i++){
		  result.append(sequences.get(i).name).append("\t"); 
	  }
	  return result.toString();
  }

  public void sort() {
    StaticFunction.sortObj(sequences);
  }
}
