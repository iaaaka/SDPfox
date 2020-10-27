package mysqlbase.api;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.Vector;

import com.mysql.jdbc.Connection;

import mysqlbase.basedescr.SDP_CAMPSbase;
import mysqlbase.basedescr.Table;
import mysqlbase.objects.AbstrastObject;
import mysqlbase.objects.Group;
import mysqlbase.objects.OHcluster;
import mysqlbase.objects.SDP;
import mysqlbase.objects.Sequence;

public class MySQLAccess {
	
	static{
		try{
			Connector.setParameters("storage", "sdp_camps", "mazin", "Vectyacs5");
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
	
	public static OHcluster getOHcluster(int id) throws Exception {
		return new OHcluster(loadObject(AbstrastObject.getTable(OHcluster.class),id));
	}
	
	public static SDP getSDP(int id) throws Exception {
		return new SDP(loadObject(AbstrastObject.getTable(SDP.class),id));
	}
	
	public static Group getGroup(int id) throws Exception {
		return new Group(loadObject(AbstrastObject.getTable(Group.class),id));
	}
	
	public static Sequence getSequence(int id) throws Exception {
		return new Sequence(loadObject(AbstrastObject.getTable(Sequence.class),id));
	}
	
	public static void clean(String table) throws Exception {
		Statement st = Connector.getStatement();
		st.execute("TRUNCATE TABLE "+table+";");
		st.close();
	}
	
	public static Vector<Sequence> loadAllSequences(Group g) throws Exception {
		Vector<Hashtable<String,String>> data = loadObjects(Sequence.getTable(Sequence.class),Sequence.GROUP_ID,""+g.getID());
		Vector<Sequence> result = new Vector<Sequence>();
		for(Hashtable<String,String> o : data){
			result.add(new Sequence(o));
		}
		return result;
	}
	
	public static Vector<SDP> loadAllSDPs(OHcluster c) throws Exception {
		Vector<Hashtable<String,String>> data = loadObjects(SDP.getTable(SDP.class),SDP.OH_CLUSTER_ID,""+c.getID());
		Vector<SDP> result = new Vector<SDP>();
		for(Hashtable<String,String> o : data){
			result.add(new SDP(o));
		}
		return result;
	}
	
	public static Vector<Group> loadAllGroups(OHcluster c) throws Exception {
		Vector<Hashtable<String,String>> data = loadObjects(Group.getTable(Group.class),Group.OH_CLUSTER_ID,""+c.getID());
		Vector<Group> result = new Vector<Group>();
		for(Hashtable<String,String> o : data){
			result.add(new Group(o));
		}
		return result;
	}
	
	private static Vector<Hashtable<String,String>> loadObjects(Table t, String field,String value) throws Exception {
		Vector<Hashtable<String,String>> result = new Vector<Hashtable<String,String>>();
		Statement st = Connector.getStatement();
		st.execute("SELECT * FROM "+t.getName()+" WHERE "+field+" = '"+value+"';");
		ResultSet rs = st.getResultSet();
		for(;rs.next();){
			Hashtable<String,String> tmp = new Hashtable<String, String>();
			for(int i=0;i<t.getFieldCount();i++){
				tmp.put(t.getFieldName(i), rs.getString(t.getFieldName(i)));
			}
			result.add(tmp);
		}
		rs.close();
		st.close();
		return result;
	}
		
	public static void clean() throws Exception{
		String[] t = SDP_CAMPSbase.getTablesNames();
		for(int i=0;i<t.length;i++)
			clean(t[i]);
	}
	
	private static Hashtable<String, String> loadObject(Table t, int id) throws Exception {
		StringBuffer query = new StringBuffer("SELECT * FROM ");
		query.append(t.getName()).append(" WHERE ");
		query.append(t.getFieldName(t.getIDfield())).append(" = ").append(id).append(";");
		Statement st = Connector.getStatement();
		st.execute(query.toString());
		ResultSet rs = st.getResultSet();
		Hashtable<String, String> result = new Hashtable<String, String>();
		rs.next();
		for(int i=0;i<t.getFieldCount();i++){
			result.put(t.getFieldName(i), rs.getString(t.getFieldName(i)));
		}
		rs.close();
		st.close();
		return result;
	}
	
	/**
	 * update object if id != -1 and insert in opposite case
	 * @param obj
	 * @return id value
	 * @throws Exception
	 */
	public static int save(AbstrastObject obj) throws Exception{
		int id = obj.getID();
		if(id == -1)
			id = insert(obj);
		StringBuffer query = new StringBuffer("UPDATE ");
		query.append(obj.getTable().getName()).append(" SET ");
		boolean notfrst = false;
		for(int i=0;i<obj.getTable().getFieldCount();i++){
			if(i != obj.getTable().getIDfield() && obj.getFieldValue(i) != null){
				if(notfrst)
					query.append(", ");
				notfrst = true;
				query.append(obj.getTable().getFieldName(i)).append(" = '").append(obj.getFieldValue(i)).append("'");
			}
		}
		query.append(" WHERE ").append(obj.getTable().getFieldName(obj.getTable().getIDfield()));
		query.append(" = ").append(id).append(";");
		Statement st = Connector.getStatement();
		st.executeUpdate(query.toString());
		st.close();
		return id;
	}
	
	public static Vector<Integer> getAllID(Table t) throws Exception {
		Vector<Integer> result = new Vector<Integer>();
		Statement st = Connector.getStatement();
		st.execute("SELECT "+t.getFieldName(t.getIDfield())+" FROM "+t.getName()+";");
		ResultSet rs = st.getResultSet();	
		for(;rs.next();){
			result.add(rs.getInt(1));
		}
		rs.close();
		st.close();
		return result;
	}
	
	private static int insert(AbstrastObject obj) throws Exception{
		StringBuffer query = new StringBuffer("INSERT INTO ");
		query.append(obj.getTable().getName()+" (id) VALUES (DEFAULT);");
		Statement st = Connector.getStatement();
		st.executeUpdate(query.toString(),1);
		ResultSet rs = st.getGeneratedKeys();
		int result = rs.getInt(1);
		rs.close();
		st.close();
		rs.next();
		return result;
	}
}
