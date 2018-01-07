import java.sql.*;
import java.util.*;


/*
 * 18.01.07 최종본 
 */
public class MovieDAO {
	
	// DB 연동에 사용하는 변수 및 객체 
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private String jdbcUrl = "jdbc:mysql://localhost/test";
	private Connection conn;
	   
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private View v; // View 클래스 객체 선언
	   
	private String sql; // sql문 string
	   
	ArrayList<Movie> movieList; // db에서 가져온 영화 리스트를 저장할 리스트
	
	public MovieDAO() { // 생성자
	   // 초기화
		AppMain app = AppMain.getInstance(); // appmain 클래스를 받아옴
		app.setMovieDAO(this); // 자신의 객체를 set
	    sql = "";
	    movieList = new ArrayList<Movie>();
	    v = app.getView(); // view 객체 받아옴 
	}
	   
	public void connectDB() {
	    try{ 

	        Class.forName(jdbcDriver); // 1. JDBC 드라이버 로드
	         
	        conn = DriverManager.getConnection(jdbcUrl, "root", "rhtjrgh1"); // 2. DB 연결
	        
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
	public ArrayList<Movie> sortPNum(String _year, String _month, String _nation, String _rating) {
		
	    connectDB(); // DB 연결
	    sql = "select * from movie";  // 기본 
	    
	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    sql += searchReq(_year, _month, _nation, _rating); // 검색 조건에 맞는 sql문 작성 

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
	public ArrayList<Movie> sortIncome(String _year, String _month, String _nation, String _rating) {
		
	    connectDB(); // DB 연결
	    sql = "select * from movie";  // 기본 
	    
	    // 검색한 데이터를 받는 ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    sql += searchReq(_year, _month, _nation, _rating); // 검색 조건에 맞는 sql문 작성 
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
	public ArrayList<Movie> sortEffeciency(String _year, String _month, String _nation, String _rating) {
		
	    connectDB(); // DB 연결
	    
	    sql = "select *, income/num_screen as 'effeciency' from movie";  // 기본 sql문 
	    sql += searchReq(_year, _month, _nation, _rating); // 검색 조건에 맞는 sql문 작성 
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
	    sql += " where drname like " + "'%"+ _drname + "%'"; // 감독이름 지정
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
	    sql += " where mvname like " + "'%"+ word + "%'"; // 단어 포함여부 설정
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
	private String searchReq(String _year, String _month, String _nation, String _rating) {
		
		String sql = "";
		
	    // 년도별 및 월별 검색 조건 체크, 월별 전체 검색을 하지 않은 경우  
	    if (!(_month.equals("전체"))) {
	    	// 선택 월이 전체가 아니면 월별 및 연도별 기간 검색문 추가 
	    	
	    	if ((_year.equals("전체"))) { // 년도는 전체고, 특정 월을 지정한 경우(16년도 혹은 17년도 둘중에 하나만 가능해도 출력이므로 or 사용)
	    		
	    		sql+= " where (DATE(open_date) between '2016-" +_month +"-01' and last_day('2016-"+_month +"-01')" // 16년도에서 해당 월의 영화 선택
	    		+ " or DATE(open_date) between '2017-" +_month +"-01' and last_day('2017-"+_month +"-01'))"; // 17년도에서 해당 월의 영화 선택
	    		
	    	} // if
	    	
	    	else if ((_year.equals("2017"))) { // 17년도 선택 후 특정 월 선택한 경우
	    	
	    		sql+= " where DATE(open_date) between '2017-" +_month 
	               +"-01' and last_day('2017-"+_month +"-01')";
	        
	    	} // else if
	    	
	    	else if ((_year.equals("2016"))) { // 16년도 선택 후 특정 월 선택한 경우
		    	
	    		sql+= " where DATE(open_date) between '2016-" +_month 
	               +"-01' and last_day('2016-"+_month +"-01')";
	        
	    	} // else if
	        	      
	    }
	    
	    
	    else { // 전체 월을 선택한 경우
	    	
	    	if ((_year.equals("2017"))) { // 날짜를 17년 1월부터 12월까지로 설정
		    	
	    		sql+= " where DATE(open_date) between '2017-1-01'"
	    				+ "and last_day('2017-"+ "12" +"-01')";
	        
	    	} // if
	    	
	    	else if ((_year.equals("2016"))) { // 날짜를 16년 1월부터 12월까지로 설정
		    	
	    		sql+= " where DATE(open_date) between '2016-1-01'"
	    				+ "and last_day('2016-12-01')";
	        
	    	} // else if
	    	
	    	else if ((_year.equals("전체"))) { // 년도, 월 모두 전체인 경우 아무 조건도 추가하지 않음
	    	
	    	} // else if
	    	
	    } // else
	    
	    
	    // 국가 검색 조건 체크, 한국인 경우 
	    if ((_nation.equals("한국"))) {
	    	// 월별 검색 조건이 전체인 경우
	    	
	    	if (_month.equals("전체") && _year.equals("전체")) { // 연도와 월이 모두 전체였으면 처음으로 사용되는 조건문일 것이므로 where 사용
	    		sql += " where country = '한국'";            
	    	}
	         // 연도별 및 월별 검색 조건이 전체가 아닌 경우
	         else { // 위에서 where를 사용했으므로 and로 조건 추가
	            sql += " and country = '한국'";   
	         }         
	    } 
	    
	    // 국가 검색 조건이 외국인 경우 
	    else if ((_nation.equals("외국"))) {
	    	// 월별 검색 조건이 전체인 경우
	    	
	    	if (_month.equals("전체") && _year.equals("전체")) {
	    		sql += " where country != '한국'";
	        }	
	    	// 월별 검색 조건이 전체가 아닌 경우
	    	else {
	    		sql += " and country != '한국'";
	    	}
	    	
	    } // else if
	      
	    
	    // 관람등급 검색 조건 체크, 관람등급 검색을 전체로 하지 않은 경우
	    if (!(_rating.equals("전체"))) {
	    	
	    	if ((_month.equals("전체")) && (_nation.equals("전체")) && (_year.equals("전체"))) {
	    		// 위 조건이 모두 전체인 경우 처음으로 사용되는 조건이므로 where 사용
	            sql += " where rating like " + "'"+ _rating + "%'"; 
	    	}
	    	// 하나라도 특정 조건이 설정된 경우
	    	else { // 추가 조건으로 사용하면 되므로 and 사용
	    		sql += " and rating like " + "'"+ _rating + "%'";
	    	}   
	    }
	    // 조건이 전체인 경우 할 필요 x 
	      
	    return sql; // 작성한 sql문 반환
	      
	} // searchReq()
}