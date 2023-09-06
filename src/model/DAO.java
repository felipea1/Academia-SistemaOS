package model;

import java.sql.Connection;
import java.sql.DriverManager;

public class DAO {

	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbsistema";
	private String user = "root";
	private String password = "";
	
	private Connection con;

	public Connection conectar() {
		try {
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
}
