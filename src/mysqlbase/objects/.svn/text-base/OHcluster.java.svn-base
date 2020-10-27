package mysqlbase.objects;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Vector;

import mysqlbase.api.MySQLAccess;
import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.basedescr.Table;

public class OHcluster extends AbstrastObject {
	private int id;
	private String name;
	private String alignment;
	private String description;
	private Double sdp_pvalue;
	private double threshold;
	private int camps_id;
	private String sp_tree;
	public static final String ID = "id";
	public static final String NAME = "name";
	public static final String DESCRIPTION = "description";
	public static final String THRESHOLD = "threshold";
	public static final String ALIGNMENT = "alignment";
	public static final String SDP_PVALUE = "sdp_pvalue";
	public static final String CAMPS_ID = "camps_id";
	public static final String SP_TREE = "sp_tree";
	
	public OHcluster(Hashtable<String, String> f){
		super(f);
		this.id = Integer.parseInt(f.get(ID));
		this.name = f.get(NAME);
		this.alignment = f.get(ALIGNMENT);
		this.description = f.get(DESCRIPTION);
		if(f.containsKey(SDP_PVALUE))
			this.sdp_pvalue = Double.parseDouble(f.get(SDP_PVALUE));
		this.threshold = Double.parseDouble(f.get(THRESHOLD));
		camps_id = Integer.parseInt(f.get(CAMPS_ID));
		if(f.containsKey(SP_TREE))
			this.sp_tree = f.get(SP_TREE);
	}
			
	public int getCampsID(){
		return this.camps_id;
	}
	
	public String getSPtree(){
		return this.sp_tree;
	}
	
	public void setSDPpvalue(double pv){
		super.setField(SDP_PVALUE, ""+pv);
		this.sdp_pvalue = pv;
	}
	
	public void setSPtree(String t){
		super.setField(SP_TREE, t);
		this.sp_tree = sp_tree;
	}
	
	public String getName(){
		return name;
	}
	
	public String getAlignment(){
		return alignment;
	}
	
	public InputStream getInputStreamForAlignment(){
		return new ByteArrayInputStream(alignment.getBytes());
	}
	
	public String getDescription(){
		return description;
	}
	
	public double getSDPpvalue(){
		return sdp_pvalue;
	}
	
	public double getThreshold(){
		return threshold;
	}
	
	public Vector<Sequence> getSequences(){
		Vector<Sequence> result = new Vector<Sequence>();
		Vector<Group> groups = getGroups();
		for(Group g : groups){
			result.addAll(g.getSequences());
		}
		return result;
	}
	
	public Vector<SDP> getSDPs(){
		try{
			return MySQLAccess.loadAllSDPs(this);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public Vector<Group> getGroups(){
		try{
			return MySQLAccess.loadAllGroups(this);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
