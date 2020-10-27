package mysqlbase.basedescr;

import java.util.*;

public class Table {
	
	private String name;
	private String[] fieldnames;
	private Hashtable<Table,Connection> connections;
	private int idfield;
	
	public Table(String name,String[] fields,int idf){
		this.name = name;
		this.fieldnames = fields;
		idfield = idf;
		connections = new Hashtable<Table,Connection>();
	}
	
	public String getName(){
		return name;
	}
	
	public int getFieldCount(){
		return fieldnames.length;
	}
	
	public String getFieldName(int f){
		return fieldnames[f];
	}
	
	public int getIDfield(){
		return idfield;
	}
	
	public int getConnectionCount(){
		return connections.size();
	}
	
	public void addConnection(Connection c){
		connections.put(c.getSTable(),c);
	}
	
	public Connection getConnection(Table t){
		return connections.get(t);
	}

}
