package mysqlbase.objects;

import java.util.Hashtable;

import mysqlbase.api.MySQLAccess;
import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.basedescr.Table;

public class AbstrastObject {
	private Hashtable<String, String> fields;
	
	public AbstrastObject(Hashtable<String, String> f){
		fields = f;
	}
	
	public void setField(String fname,String fvalue){
		fields.put(fname, fvalue);
	}
	
	public static Table getTable(Class c){
		return SDP_CAMPSbase.getTable(c);
	}	
	
	public Table getTable(){
		return getTable(this.getClass());
	}
	
	public String getFieldValue(String f){
		return fields.get(f);
	}
	
	public int save() throws Exception {
		return MySQLAccess.save(this);
	}
	
	public String getFieldValue(int f){
		return fields.get(getTable(this.getClass()).getFieldName(f));
	}
	
	public int getID(){
		return Integer.parseInt(fields.get(getTable().getFieldName(getTable().getIDfield())));
	}
	 
}
