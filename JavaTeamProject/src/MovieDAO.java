import java.sql.*;
import java.util.*;

public class MovieDAO {
	
	String jdbcDriver = "com.mysql.jdbc.Driver"; // JDBC 드라이버 설정
	String jdbcUrl = "jdbc:mysql://localhost/javadb"; // JDBC 연결
	Connection conn; // Connection 클래스 인스턴스 래퍼런스 얻기
	
	PreparedStatement pstmt; // SQL문을 미리 만들어 놓고 변수를 따로 입력
	ResultSet rs; // 데이터 결과 처리를 위한 ResultSet 객체
	
	Vector<String> items = null; // 제품 명단을 보기 위함
	String sql; // 실행 할 문장
	
	public void connectDB() { // DB와 연결
		
		try {
			
			Class.forName(jdbcDriver); // 드라이버 로드
			
			conn = DriverManager.getConnection(jdbcUrl, "root", "ssk13lke"); // 데이터베이스 연결
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} // catch
		
	} // connectDB
	
	public void closeDB() { // DB와 연결 끊기
		
		try {
			
			pstmt.close(); // 연결 해제
			// rs.close(); // rs가 모든 기능에서 쓰이지 않으므로 여기서 쓰면 안됨(데이터를 가져오는 경우에만 사용)
			conn.close();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		} // catch
		
	} // closeDB
	
	public ArrayList<Product> getAll() { // 모든 상품 정보를 가져옴
		
		connectDB();
		sql = "select * from product"; // 실행 할 문장
		
		ArrayList<Product> datas = new ArrayList<Product>(); // 반환을 위한 ArrayList 선언 및 생성
		
		items = new Vector<String>();
		items.add("전체");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // 내용잊 존재 할 때까지
				
				Product p = new Product(); // Product 클래스의 객체 p 생성
				
				p.setPrcode(rs.getInt("prcode")); // p의 상품 번호 설정
				p.setPrname(rs.getString("prname")); // p의 상품 이름 설정
				p.setPrice(rs.getInt("price")); // p의 상품 가격 설정
				p.setManufacture(rs.getString("manufacture")); // p의 상품 제조사 설정
				
				datas.add(p); // p를 datas(ArrayList)에 더함 
				items.add(String.valueOf(rs.getInt("prcode")));
				
			} // while
			
			rs.close();
			
		} catch(Exception e) { }
		
		closeDB();
		
		return datas;
		
	} // getAll
	
	public Product getProduct(int prcode) { // 1개의 Product 객체 반환
		
		sql = "select * from product where prcode = ?"; // 특정 prcode를 가지는 Product 클래스의 객체 반환
		
		Product p = null; // p 초기화
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql); // 실행할 문장 설정
			pstmt.setInt(1, prcode); // ?를 인자로 받아온 prcode로 설정 
			rs = pstmt.executeQuery();
			
			rs.next();
			p = new Product(); // 반환 할 객체의 선언 및 정보 설정
			p.setPrcode(rs.getInt("prcode")); // p의 상품 번호 설정
			p.setPrname(rs.getString("prname")); // p의 상품 이름 설정
			p.setPrice(rs.getInt("price")); // p의 상품 가격 설정
			p.setManufacture(rs.getString("manufacture")); // p의 상품 제조사 설정
			
			rs.close();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} // catch
		
		closeDB();
		
		return p;
		
	} // getProduct
	
	public boolean newProduct(Product product) { // DB에 제품 삽입
		
		String sql = "insert into product values(?, ?, ?, ?)";
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product.getPrcode()); // 제품 정보 입력
			pstmt.setString(2, product.getPrname());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getManufacture());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return false;
			
		} // catch
		
		closeDB();
		
		return true;
		
	} // newProduct
	
	public boolean delProduct(int prcode) { // DB에서 제품 삭제
		
		String sql = "delete from product where prcode = ?";
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, prcode); // 특정 prcode를 가지는 제품을 DB에서 삭제
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return false;
			
		} // catch
		
		closeDB();
		
		return true;
		
	} // delProduct
	
	public boolean updateProduct(Product product) { // DB에서 제품 정보 변경
		
		String sql = "update product SET prcode = ?, prname = ?, price = ?, manufacture = ? where prcode = ?";
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product.getPrcode()); // 수정할 제품의 정보 입력
			pstmt.setString(2, product.getPrname());
			pstmt.setInt(3, product.getPrice());
			pstmt.setString(4, product.getManufacture());
			pstmt.setInt(5, product.getPrcode());
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return false;
			
		} // catch
		
		closeDB();
		
		return true;
		
	} // updateProduct
	
	public Vector<String> getItems() { // 제품 명단을 반환
		
		return items;
		
	} // getItems

}
