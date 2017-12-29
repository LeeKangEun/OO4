import java.sql.*;
import java.util.*;

// DB 연동 클래스 
public class MovieDAO {
	
	// db 연동에 사용하는 변수 및 객체 
	String jdbcDriver = "com.mysql.jdbc.Driver";
	String jdbcUrl = "jdbc:mysql://localhost/test";
	Connection conn;
	
	PreparedStatement pstmt;
	ResultSet rs;
	
	// 콤보박스 아이템 관리를 위한 벡터 
	//Vector<String> items;
	String sql; // sql문 string
	
	ArrayList<Movie> movieList;
	public MovieDAO() {
		// 초기화
		//items = null;
		sql = "";
		movieList = new ArrayList<Movie>();
	}
	
	public void connectDB() {
		try{ 
			// 1. JDBC 드라이버 로드
			Class.forName(jdbcDriver);
			
			// 2. DB 연결 
			conn = DriverManager.getConnection(jdbcUrl, "root", "rhtjrgh1");
		} catch(Exception e1) {
			e1.printStackTrace();
		}
	} // DB 연결 메소드
	
	public void closeDB() {
		try{
			pstmt.close();
			conn.close();
		}catch(Exception e2) {
			e2.printStackTrace();
		}
	} // DB 연결 종료 메소드
	
}
