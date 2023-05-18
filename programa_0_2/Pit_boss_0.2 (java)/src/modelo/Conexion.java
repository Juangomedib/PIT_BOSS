package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Conexion {
	public static String url;
	public static String login="root";
	public static String pass="";
	public static String host="localhost:3306";
	public static String bd="pit_boss_0_2";
	public static Connection con=null;
	
	public static void open() {
		try {
			if(Conexion.con==null || Conexion.con.isClosed()) {
				url="jdbc:mysql://"+host+"/"+bd+"?useSSL=false";
				Conexion.con=DriverManager.getConnection(url,login,pass);
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void close() {
		try {
			if(!con.isClosed()) {
				con.close();
			}
		}catch(SQLException e) {
			System.out.println(e.getMessage());
		}
	}
}
