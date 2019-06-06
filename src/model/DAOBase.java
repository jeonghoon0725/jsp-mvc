package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class DAOBase implements DAO{
	
	private Connection conn = null;
	public void closeDBResources(ResultSet rs, Statement stmt, PreparedStatement pstmt, Connection conn) {
		//할당된 DB 자원 회수
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(pstmt != null) {
			try {
				pstmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	@Override
	public Connection getConnection() throws SQLException {
	try {
		Class.forName("oracle.jdbc.OracleDriver"); //ojdbc6.jar를 메모리에 적재
	}catch(ClassNotFoundException e) {
		e.printStackTrace();
	}
	try {
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","ja_034", "cometrue");
		return conn;
		} catch (SQLException e) {
		e.printStackTrace();
		}
	return conn;
	}
}

