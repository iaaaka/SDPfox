package mysqlbase.basedescr;


import java.util.Hashtable;

import mysqlbase.objects.Group;
import mysqlbase.objects.OHcluster;
import mysqlbase.objects.SDP;
import mysqlbase.objects.Sequence;

public class SDP_CAMPSbase {
	private static Hashtable<String, Table> tables;
	public static final String OH_CLUSTER_TABLE = "oh_cluster";
	public static final String SDP_TABLE = "sdp";
	public static final String GROUP_TABLE = "sp_group";
	public static final String SEQUENCE_TABLE = "sequence";
	private static Hashtable<Class, Table> tablesByClass;
	
	static{
		Table oh = new Table(OH_CLUSTER_TABLE,new String[]{OHcluster.ID,OHcluster.NAME,OHcluster.DESCRIPTION,OHcluster.THRESHOLD,OHcluster.SDP_PVALUE,OHcluster.ALIGNMENT,OHcluster.CAMPS_ID,OHcluster.SP_TREE},0);
		Table sdp = new Table(SDP_TABLE,new String[]{SDP.ID,SDP.OH_CLUSTER_ID,SDP.ZSCORE,SDP.POSITION},0);
		Table group = new Table(GROUP_TABLE,new String[]{Group.ID,Group.OH_CLUSTER_ID,Group.WEIGHT,Group.ISNONGROUPED},0);
		Table seq = new Table(SEQUENCE_TABLE, new String[]{Sequence.ID,Sequence.NAME,Sequence.GROUP_ID,Sequence.REMOVED},0);
		Connection oh_sdp = new Connection(Connection.ONEMORE_TYPE,oh,sdp,0,1,null);
		Connection oh_groups = new Connection(Connection.ONEMORE_TYPE,oh,group,0,1,null);
		Connection groups_seq = new Connection(Connection.ONEMORE_TYPE,group,seq,0,2,null);
		oh.addConnection(oh_sdp);
		sdp.addConnection(oh_sdp);
		oh.addConnection(oh_groups);
		group.addConnection(oh_groups);
		group.addConnection(groups_seq);
		seq.addConnection(groups_seq);
		
		tables = new Hashtable<String, Table>();
		
		tables.put(oh.getName(), oh);
		tables.put(sdp.getName(), sdp);
		tables.put(group.getName(), group);
		tables.put(seq.getName(), seq);
		
		tablesByClass = new Hashtable<Class, Table>();
		
		tablesByClass.put(SDP.class, sdp);
		tablesByClass.put(OHcluster.class, oh);
		tablesByClass.put(Group.class, group);
		tablesByClass.put(Sequence.class, seq);
	}
	
	public static Table getTable(Class c){
		return tablesByClass.get(c);
	}
	
	public static String[] getTablesNames(){
		return tables.keySet().toArray(new String[]{});
	}
	
	public static Table getTable(String t){
		return tables.get(t);
	}
}
