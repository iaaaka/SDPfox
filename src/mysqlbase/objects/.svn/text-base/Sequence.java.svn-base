package mysqlbase.objects;


import java.util.Hashtable;

import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.basedescr.Table;

public class Sequence extends AbstrastObject {
	private int id;
	private String name;
	private int group_id;
	private boolean removed;
	public static final String ID = "id";
	public static final String  NAME = "name";
	public static final String  GROUP_ID = "group_id";
	public static final String  REMOVED = "removed";
	
	public Sequence(Hashtable<String, String> f){
		super(f);
		this.id = Integer.parseInt(f.get(ID));
		this.group_id = Integer.parseInt(f.get(GROUP_ID));
		this.name = f.get(NAME);
		if(f.get(REMOVED).equals("0"))
			removed = false;
		if(f.get(REMOVED).equals("1"))
			removed = true;
	}

	public int getGroupID(){
		return group_id;
	}
		
	public String getName(){
		return name;
	}
	
}