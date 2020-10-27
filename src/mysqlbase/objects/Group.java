package mysqlbase.objects;


import java.util.Hashtable;
import java.util.Vector;

import mysqlbase.api.MySQLAccess;
import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.basedescr.Table;

public class Group extends AbstrastObject{
	private int id;
	private int oh_cluster_id;
	private Double weight;
	private int isnongrouped;
	public static final String ID = "id";
	public static final String OH_CLUSTER_ID = "oh_cluster_id";
	public static final String WEIGHT = "weight";
	public static final String ISNONGROUPED = "isnongrouped";
	
	public Group(Hashtable<String, String> f){
		super(f);
		this.id = Integer.parseInt(f.get(ID));
		this.oh_cluster_id = Integer.parseInt(f.get(OH_CLUSTER_ID));
		if(f.containsKey(WEIGHT))
			this.weight = Double.parseDouble(f.get(WEIGHT));
		this.isnongrouped = Integer.parseInt(f.get(ISNONGROUPED));
	}
	
	public int getOHclusterID(){
		return oh_cluster_id;
	}
		
	public double getWeight(){
		return weight;
	}
	
	public Vector<Sequence> getSequences(){
		try{
			return MySQLAccess.loadAllSequences(this);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
}