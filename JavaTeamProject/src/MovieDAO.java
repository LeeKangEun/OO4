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
	
	String sql; // sql문 string
	
	ArrayList<Movie> movieList; // db에서 가져온 영화 리스트를 저장할 리스트 
	
	public static void main(String args[]){
		new MovieDAO().sortPNum("1", "전체", "전체");
	} // test 
	
	public MovieDAO() {
		// 초기화
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
	
	// 기준 별 관람객 수 top 5 
	public ArrayList<Movie> sortPNum(String _month, String _nation, String _rating) {
		connectDB(); // DB 연결
		sql = "select * from movie";  // 기본 
		
		// 검색한 데이터를 받는 ArrayList
		ArrayList<Movie> datas = new ArrayList<Movie>();
		
		sql += searchReq(_month, _nation, _rating); // 검색 조건에 맞는 sql문 작성 
		
		sql += " order by num_people desc limit 5"; // 추가 검색조건 삽입 
	
		try{
			// 필요시 pstmt 그냥 tmt로 바꾸기 
			pstmt = conn.prepareStatement(sql); // sql문 생성
			rs = pstmt.executeQuery(); // 입력받은 sql문 전송, 결과 데이터를 받음 
			
			while(rs.next()) {

				Movie m = new Movie();

				m.setMvcode(rs.getInt("mvcode")); // 영화 번호 
				m.setMvname(rs.getString("mvname")); // 영화 제목 
				m.setDrname(rs.getString("drname")); // 감독 이름 
				m.setOpendate(rs.getDate("open_date")); // 개봉일
				m.setCountry(rs.getString("country")); // 국적
				m.setNumScreen(rs.getInt("num_screen")); // 스크린 수
				m.setIncome(rs.getLong("income")); // 매출액
				m.setNumPeople(rs.getInt("num_people")); // 관객 수
				m.setGenre(rs.getString("genre")); // 장르
				m.setRating(rs.getString("rating")); // 관람 등급 
				datas.add(m); // 데이터를 arrayList에 저장
			
			} // while, 가져온 데이터 저장 

			rs.close(); // close 
		}catch(Exception e3) {
			e3.printStackTrace();
		}
		
		closeDB(); // DB 연결 종료 
		return datas;
	} // sortPNum(), DB에서 조건에 맞는 데이터를 가져옴
	
	// 검색 조건에 맞는 sql문 작성 메소드 
	private String searchReq(String _month, String _nation, String _rating) {
		String sql = "";
		// 월별 검색 조건 체크, 전체 검색을 하지 않은 경우  
		if (!(_month.equals("전체"))) {
			// 전체가 아니면 월별 기간 검색문 추가 
			sql+= " where DATE(open_date) between '2017-" +_month 
					+"-01' and last_day(2017-"+_month +"-01)";
		}
		// 전체로 검색한 경우 할 필요 x
		
		// 국가 검색 조건 체크, 한국인 경우 
		if ((_nation.equals("한국"))) {
			// 월별 검색 조건이 전체인 경우
			if (_month.equals("전체")) {
				sql += " where country = '한국'";				
			}
			// 월별 검색 조건이 전체가 아닌 경우
			else {
				sql += " and country = '한국'";	
			}			
		} 
		// 국가 검색 조건이 외국인 경우 
		else if ((_nation.equals("외국"))) {
			// 월별 검색 조건이 전체인 경우
			if (_month.equals("전체")) {
				sql += " where country != '한국'";
			}
			// 월별 검색 조건이 전체가 아닌 경우
			else {
				sql += " and country != '한국'";
			}
		}
		
		// 관람등급 검색 조건 체크, 관람등급 검색을 전체로 하지 않은 경우
		if (!(_rating.equals("전체"))) {
			if (!(_month.equals("전체")) || !(_nation.equals("전체"))) {
				// 위 조건이 하나라도 있는 경우 
				sql += " and rating = " + _rating; 
			}
			// 위 조건이 둘다 전체인 경우 (없는 경우)
			else {
				sql += " where rating = " + _rating;
			}	
		}
		// 조건이 전체인 경우 할 필요 x 
		
		return sql;
	} // searchReq(), 
}
