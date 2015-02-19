public class MysqlHelper{
	//SERVER ***********************************************
	private String server, database, user, pass;
	private MysqlDataSource ds;
	private Connection con;
	public MysqlHelper(){
		
	}
	public void setConnection(String server, String database, String user, String pass){
			this.server = server;
			this.database = database;
			this.user = user;
			this.pass = pass;
			ds = new MysqlDataSource();
			ds.setServerName(server);
			ds.setDatabaseName(database);
		try{
			con = (Connection) ds.getConnection(user, pass);
			System.out.println("Online");
		}catch(Exception e){
			System.out.println("Error: "+e.toString());
		}
	}
	public Connection getConnection(){
		return con;
	}
	public void closeConnection(){
		try {
			con.close();
			System.out.println("Connection Closed");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
