import java.sql.*;
import java.util.*;

// DB ���� Ŭ���� 
public class MovieDAO {
	
	// db ������ ����ϴ� ���� �� ��ü 
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/test";
	Connection conn;
	
	PreparedStatement pstmt;
	ResultSet rs;
	
	// �޺��ڽ� ������ ������ ���� ���� 
	//Vector<String> items;
	String sql; // sql�� string
	
	ArrayList<Movie> movieList;
	public MovieDAO() {
		// �ʱ�ȭ
		//items = null;
		sql = "";
		movieList = new ArrayList<Movie>();
	}
	
	public void connectDB() {
		try{ 
			// 1. JDBC ����̹� �ε�
			Class.forName(jdbcDriver);
			
			// 2. DB ���� 
			conn = DriverManager.getConnection(jdbcUrl, "root", "rhtjrgh1");
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	} // DB ���� �޼ҵ�
	
	public void closeDB() {
		try{
			pstmt.close();
			conn.close();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
	} // DB ���� ���� �޼ҵ�
	
}
