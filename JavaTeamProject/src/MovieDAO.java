import java.sql.*;
import java.util.*;

public class MovieDAO {
	
	// DB 연동에 사용하는 변수 및 객체 
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private String jdbcUrl = "jdbc:mysql://localhost/javadb";
	private Connection conn;
	   
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private View v; 
	   
	private String sql; // sql문 string
	   
	ArrayList<Movie> movieList; // db에서 가져온 영화 리스트를 저장할 리스트
	
	public MovieDAO() {
	   // 초기화
		AppMain app = AppMain.getInstance(); // appmain 클래스를 받아옴
		app.setMovieDAO(this); // 자신의 객체를 set
	    sql = "";
	    movieList = new ArrayList<Movie>();
	    v = app.getView(); // view 객체 받아옴 
	}
	   
	public void connectDB() {
	    try{ 
	        // 1. JDBC 드라이버 로드
	        Class.forName(jdbcDriver);
	         
	        // 2. DB 연결 
	        conn = DriverManager.getConnection(jdbcUrl, "root", "ssk13lke");
	    } catch(Exception e1) {
	        e1.printStackTrace();
	    }
	} // DB 연결 메소드
	   
	public void closeDB() {
	    try {
	    	pstmt.close();
	        conn.close();
	    } catch(Exception e2) {
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
	      
	    sql += " order by num_people desc limit 15"; // 추가 검색조건 삽입 
	      
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
	   } catch(Exception e3) {
	        e3.printStackTrace();
	   }
	      
	   closeDB(); // DB 연결 종료 
	   
	   return datas;
	} // sortPNum(), DB에서 조건에 맞는 데이터를 가져옴
	
	// 기준별 영화 매출액 수 top 5
	public ArrayList<Movie> sortIncome(String _month, String _nation, String _rating) {
		
	    connectDB(); // DB 연결
	    sql = "select * from movie";  // 기본 
	    
	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    sql += searchReq(_month, _nation, _rating); // 검색 조건에 맞는 sql문 작성 
	    sql += " order by income desc limit 15"; // 추가 검색조건 삽입 
	      
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
	   } catch(Exception e4) {
	        e4.printStackTrace();
	   }
	      
	   closeDB(); // DB 연결 종료 
	   
	   return datas;
	} // sortIncome(), DB에서 조건에 맞는 데이터를 가져옴
   
	// 기준별 효율성 영화 top 5
	public ArrayList<Movie> sortEffeciency(String _month, String _nation, String _rating) {
		
	    connectDB(); // DB 연결
	    sql = "select *, income/num_screen as 'effeciency' from movie";  // 기본 sql문 
	    sql += searchReq(_month, _nation, _rating); // 검색 조건에 맞는 sql문 작성 
	    sql += " order by income/num_screen desc limit 15"; // 추가 검색조건 삽입
	    
	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
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
	            m.setFResult(rs.getFloat("effeciency")); // 효율성 
	            datas.add(m); // 데이터를 arrayList에 저장
	         
	        } // while, 가져온 데이터 저장 

	        rs.close(); // close 
	   } catch(Exception e5) {
	        e5.printStackTrace();
	   }
	      
	   closeDB(); // DB 연결 종료 
	   
	   return datas;
	} // sortEffeciency(), DB에서 조건에 맞는 데이터를 가져옴
	
	// 기준별 감독 기준 top 5
	// 콤보박스 3개 조건 뺴도 될 것 같음 
	// 스크린 수 대신 영화 갯수, 매출액 대신 누적 매출액 set함 
	public ArrayList<Movie> sortDirector(String _drname) {
		
	    connectDB(); // DB 연결
	    
	    sql = "select * from movie";  // 기본
	    sql += " where drname like " + "'%"+ _drname + "%'";
	    sql += " order by drname"; // 추가 검색조건 삽입 
	    
	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    try{
	    	// 필요시 pstmt 그냥 tmt로 바꾸기 
	        pstmt = conn.prepareStatement(sql); // sql문 생성
	        rs = pstmt.executeQuery(); // 입력받은 sql문 전송, 결과 데이터를 받음 
	        
	        while(rs.next()) {

        		Movie m = new Movie();

	            //m.setMvcode(rs.getInt("mvcode")); // 영화 번호 
	            m.setMvname(rs.getString("mvname")); // 영화 제목 
	            m.setDrname(rs.getString("drname")); // 감독 이름 
	            //m.setOpendate(rs.getDate("open_date")); // 개봉일
	            //m.setCountry(rs.getString("country")); // 국적
	            m.setNumScreen(rs.getInt("num_screen")); // 스크린 수 
	            m.setIncome(rs.getLong("income")); // 매출액 
	            //m.setNumPeople(rs.getInt("num_people")); // 관객 수
	            //m.setGenre(rs.getString("genre")); // 장르
	            //m.setRating(rs.getString("rating")); // 관람 등급 
	            datas.add(m); // 데이터를 arrayList에 저장
	         
	        } // while, 가져온 데이터 저장 

	        rs.close(); // close
	        
	   }catch(Exception e6) {
	        e6.printStackTrace();
	   }
	      
	   closeDB(); // DB 연결 종료 
	   
	   return datas;
	} // sortDirector(), DB에서 조건에 맞는 데이터를 가져옴
	
	// 기준별 흥행작 top 5 < 누적 관객 수 100만 이상)
	// 콤보박스 3개 조건 일단 뺌 

	public ArrayList<Movie> sortSuccess() {

	    connectDB(); // DB 연결
	    sql = "select * from movie where num_people > 1000000 order by num_people desc;";  // 기본 

	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();

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
	            m.setNumScreen(rs.getInt("num_screen")); // 스크린 수 대신 
	            m.setIncome(rs.getLong("income")); // 매출액 대신 누적 매출액 
	            m.setNumPeople(rs.getInt("num_people")); // 관객 수
	            m.setGenre(rs.getString("genre")); // 장르
	            m.setRating(rs.getString("rating")); // 관람 등급 
	            datas.add(m); // 데이터를 arrayList에 저장
	            
	        } // while, 가져온 데이터 저장 
	        
	        rs.close(); // close 

	   }catch(Exception e7) {
	        e7.printStackTrace();
	   }

	   closeDB(); // DB 연결 종료 

	   return datas;

	} // sortSuccess(), DB에서 조건에 맞는 데이터를 가져옴
	
   // 스크린 수 대신 영화 갯수 추가
   public ArrayList<Movie> sortTheme() {
	   
       connectDB(); // DB 연결
       
       sql = "select genre, avg(income) as 'avg_income', count(*) as 'num_movie' from movie group by genre order by count(*) desc";  // 기본 
       
       // 검색한 데이터를 받는 ArrayList
       ArrayList<Movie> datas = new ArrayList<Movie>();
       
       try{
          // 필요시 pstmt 그냥 tmt로 바꾸기 
           pstmt = conn.prepareStatement(sql); // sql문 생성
           rs = pstmt.executeQuery(); // 입력받은 sql문 전송, 결과 데이터를 받음 
           
           while(rs.next()) {
              
               Movie m = new Movie();

               //m.setMvcode(rs.getInt("mvcode")); // 영화 번호 
               //m.setMvname(rs.getString("mvname")); // 영화 제목 
               //m.setDrname(rs.getString("drname")); // 감독 이름 
               //m.setOpendate(rs.getDate("open_date")); // 개봉일
               //m.setCountry(rs.getString("country")); // 국적
               m.setNumScreen(rs.getInt("num_movie")); // 스크린 수 대신 영화 개수 
               //m.setIncome(rs.getLong("income")); // 매출액
               //m.setNumPeople(rs.getInt("num_people")); // 관객 수
               m.setGenre(rs.getString("genre")); // 장르
               //m.setRating(rs.getString("rating")); // 관람 등급 
               m.setFResult(rs.getFloat("avg_income")); // 평균 매출액    
               datas.add(m); // 데이터를 arrayList에 저장
            
           } // while, 가져온 데이터 저장 

           rs.close(); // close
           
      }catch(Exception e8) {
           e8.printStackTrace();
      }
         
      closeDB(); // DB 연결 종료 
      
      return datas;
   } // sortTheme(), DB에서 조건에 맞는 데이터를 가져옴
   
   public ArrayList<Movie> sortWord(String word) {
		
	    connectDB(); // DB 연결
	    
	    sql = "select * from movie";  // 기본
	    sql += " where mvname like " + "'%"+ word + "%'";
	    sql += " order by mvname"; // 추가 검색조건 삽입  
	    
	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    try{
	    	// 필요시 pstmt 그냥 tmt로 바꾸기 
	        pstmt = conn.prepareStatement(sql); // sql문 생성
	        rs = pstmt.executeQuery(); // 입력받은 sql문 전송, 결과 데이터를 받음 
	        
	        while(rs.next()) {

       		Movie m = new Movie();

	            //m.setMvcode(rs.getInt("mvcode")); // 영화 번호 
	            m.setMvname(rs.getString("mvname")); // 영화 제목 
	            //m.setDrname(rs.getString("drname")); // 감독 이름 
	            m.setOpendate(rs.getDate("open_date")); // 개봉일
	            //m.setCountry(rs.getString("country")); // 국적
	            //m.setNumScreen(rs.getInt("num_screen")); // 스크린 수 
	            //m.setIncome(rs.getLong("income")); // 매출액 
	            //m.setNumPeople(rs.getInt("num_people")); // 관객 수
	            m.setGenre(rs.getString("genre")); // 장르
	            m.setRating(rs.getString("rating")); // 관람 등급 
	            datas.add(m); // 데이터를 arrayList에 저장
	         
	        } // while, 가져온 데이터 저장 

	        rs.close(); // close
	        
	   }catch(Exception e9) {
	        e9.printStackTrace();
	   }
	      
	   closeDB(); // DB 연결 종료
	   
	   return datas;
	   
	} // sortWord(), DB에서 조건에 맞는 데이터를 가져옴
	   
	
	// 검색 조건에 맞는 sql문을 작성하는 메소드 
	private String searchReq(String _month, String _nation, String _rating) {
		String sql = "";
	    // 월별 검색 조건 체크, 전체 검색을 하지 않은 경우  
	    if (!(_month.equals("전체"))) {
	    	// 전체가 아니면 월별 기간 검색문 추가 
	        sql+= " where DATE(open_date) between '2017-" +_month 
	               +"-01' and last_day('2017-"+_month +"-01')";
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
	            sql += " and rating like " + "'"+ _rating + "%'"; 
	    	}
	    	// 위 조건이 둘다 전체인 경우 (없는 경우)
	    	else {
	    		sql += " where rating like " + "'"+ _rating + "%'";
	    	}   
	    }
	    // 조건이 전체인 경우 할 필요 x 
	      
	    return sql;
	      
	} // searchReq()
}