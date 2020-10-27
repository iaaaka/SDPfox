package mysqlbase.basedescr;

public class Connection {
	public static final int ONEONE_TYPE = 0;
	public static final int ONEMORE_TYPE = 1;
	public static final int MOREMORE_TYPE = 2;
	
	private int type;
	private Table ftable;
	private Table stable;
	private int ffield;
	private int sfield;
	private Table connection = null;
	
	public Connection(int type,Table ft,Table st,int ffield,int sfield,Table con){
		if(type >=ONEONE_TYPE && type <= MOREMORE_TYPE)
			this.type = type;
		else
			throw new RuntimeException("Bad connaction type!");
		if(type == MOREMORE_TYPE)
			connection = con;
		ftable = ft;
		stable = st;
	}
	
	public int getType(){
		return type;
	}
	
	public Table getFTable(){
		return ftable;
	}
	
	public Table getConnectionTable(){
		return connection;
	}
	
	public Table getSTable(){
		return stable;
	}
}
