package Exception;

import Util.Numerator;

public class AlignmentException extends Exception {
	private static final long serialVersionUID = -1878388665010257919L;
	private int acidNo = -1;
	private String name;
	private String message;
	private boolean format;
	
	public AlignmentException(String m,boolean format){
		if(format){
			this.message = m;
			this.format = format;
		}
	}
	

	public AlignmentException(String n) {
		name = n;
	}

	public AlignmentException(String n, int acdN) {
		name = n;
		acidNo = acdN;
	}
	
	public String getMessage(){
		return toString();
	}

	public String toString() {
		if(format)
			return message;
		if (acidNo == -1) {
			return ("Bad sequence lenght\nSequence : " + name);
		} else {
			return ("Unknow amino acid\nSequence : " + name
					+ "\nAcid position: " + acidNo);
		}
	}
}
