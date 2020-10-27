package Exception;

public class NumeratorException extends Exception {
	private String name;
	private String type;
	
	public NumeratorException(String name){
		this.name = name;
	}
	
	public void setType(String t){
		type = t;
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public String getMessage(){
		return toString();
	}
	
	public String toString(){
		return name+" is already exist";
	}

}
