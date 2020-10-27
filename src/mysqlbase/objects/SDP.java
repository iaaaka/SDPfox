package mysqlbase.objects;


import java.util.Hashtable;

import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.basedescr.Table;

public class SDP extends AbstrastObject {
	private int id;
	private int oh_cluster_id;
	private int position;
	private double zscore;
	public static final String ID = "id";
	public static final String OH_CLUSTER_ID = "oh_cluster_id";
	public static final String ZSCORE = "zscore";
	public static final String POSITION = "position";
	
	public SDP(Hashtable<String, String> f){
		super(f);
		this.id = Integer.parseInt(f.get(ID));
		this.oh_cluster_id = Integer.parseInt(f.get(OH_CLUSTER_ID));;
		this.position =Integer.parseInt(f.get(POSITION));;
		this.zscore = Double.parseDouble(f.get(ZSCORE));
	}
	
	public int getOHclusterID(){
		return oh_cluster_id;
	}
	
	public int getPosition(){
		return position;
	}
		
	public double getZscore(){
		return zscore;
	}
	
}