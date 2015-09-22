import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnect {
	
	static String dbServerAddr = "jdbc:mysql://220.67.115.33:3306/";
	static String dbName = "hjkim_1512";
	static String user = "hjkim_1512";
	static String pswd = "mosquito";
	
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch(ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	public static Connection getConnection(String dbName) throws SQLException {
		return DriverManager.getConnection(dbServerAddr + dbName, user, pswd);
	}
	
	public static void CloseConnection(Connection conn) throws SQLException {
		if(conn != null) {
			conn.close();
		}
	}

}
