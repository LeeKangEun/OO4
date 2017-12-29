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
	
	String sql; // sql�� string
	
	ArrayList<Movie> movieList; // db���� ������ ��ȭ ����Ʈ�� ������ ����Ʈ 
	
	public static void main(String args[]){
		new MovieDAO().sortPNum("1", "��ü", "��ü");
	} // test 
	
	public MovieDAO() {
		// �ʱ�ȭ
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
	
	// ���� �� ������ �� top 5 
	public ArrayList<Movie> sortPNum(String _month, String _nation, String _rating) {
		connectDB(); // DB ����
		sql = "select * from movie";  // �⺻ 
		
		// �˻��� �����͸� �޴� ArrayList
		ArrayList<Movie> datas = new ArrayList<Movie>();
		
		sql += searchReq(_month, _nation, _rating); // �˻� ���ǿ� �´� sql�� �ۼ� 
		
		sql += " order by num_people desc limit 5"; // �߰� �˻����� ���� 
	
		try{
			// �ʿ�� pstmt �׳� tmt�� �ٲٱ� 
			pstmt = conn.prepareStatement(sql); // sql�� ����
			rs = pstmt.executeQuery(); // �Է¹��� sql�� ����, ��� �����͸� ���� 
			
			while(rs.next()) {

				Movie m = new Movie();

				m.setMvcode(rs.getInt("mvcode")); // ��ȭ ��ȣ 
				m.setMvname(rs.getString("mvname")); // ��ȭ ���� 
				m.setDrname(rs.getString("drname")); // ���� �̸� 
				m.setOpendate(rs.getDate("open_date")); // ������
				m.setCountry(rs.getString("country")); // ����
				m.setNumScreen(rs.getInt("num_screen")); // ��ũ�� ��
				m.setIncome(rs.getLong("income")); // �����
				m.setNumPeople(rs.getInt("num_people")); // ���� ��
				m.setGenre(rs.getString("genre")); // �帣
				m.setRating(rs.getString("rating")); // ���� ��� 
				datas.add(m); // �����͸� arrayList�� ����
			
			} // while, ������ ������ ���� 

			rs.close(); // close 
		}catch(Exception e3) {
			e3.printStackTrace();
		}
		
		closeDB(); // DB ���� ���� 
		return datas;
	} // sortPNum(), DB���� ���ǿ� �´� �����͸� ������
	
	// �˻� ���ǿ� �´� sql�� �ۼ� �޼ҵ� 
	private String searchReq(String _month, String _nation, String _rating) {
		String sql = "";
		// ���� �˻� ���� üũ, ��ü �˻��� ���� ���� ���  
		if (!(_month.equals("��ü"))) {
			// ��ü�� �ƴϸ� ���� �Ⱓ �˻��� �߰� 
			sql+= " where DATE(open_date) between '2017-" +_month 
					+"-01' and last_day(2017-"+_month +"-01)";
		}
		// ��ü�� �˻��� ��� �� �ʿ� x
		
		// ���� �˻� ���� üũ, �ѱ��� ��� 
		if ((_nation.equals("�ѱ�"))) {
			// ���� �˻� ������ ��ü�� ���
			if (_month.equals("��ü")) {
				sql += " where country = '�ѱ�'";				
			}
			// ���� �˻� ������ ��ü�� �ƴ� ���
			else {
				sql += " and country = '�ѱ�'";	
			}			
		} 
		// ���� �˻� ������ �ܱ��� ��� 
		else if ((_nation.equals("�ܱ�"))) {
			// ���� �˻� ������ ��ü�� ���
			if (_month.equals("��ü")) {
				sql += " where country != '�ѱ�'";
			}
			// ���� �˻� ������ ��ü�� �ƴ� ���
			else {
				sql += " and country != '�ѱ�'";
			}
		}
		
		// ������� �˻� ���� üũ, ������� �˻��� ��ü�� ���� ���� ���
		if (!(_rating.equals("��ü"))) {
			if (!(_month.equals("��ü")) || !(_nation.equals("��ü"))) {
				// �� ������ �ϳ��� �ִ� ��� 
				sql += " and rating = " + _rating; 
			}
			// �� ������ �Ѵ� ��ü�� ��� (���� ���)
			else {
				sql += " where rating = " + _rating;
			}	
		}
		// ������ ��ü�� ��� �� �ʿ� x 
		
		return sql;
	} // searchReq(), 
}
