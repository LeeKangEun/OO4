import java.sql.*;
import java.util.*;

public class MovieDAO {
	
	String jdbcDriver = "com.mysql.jdbc.Driver"; // JDBC ����̹� ����
	String jdbcUrl = "jdbc:mysql://localhost/javadb"; // JDBC ����
	Connection conn; // Connection Ŭ���� �ν��Ͻ� ���۷��� ���
	
	PreparedStatement pstmt; // SQL���� �̸� ����� ���� ������ ���� �Է�
	ResultSet rs; // ������ ��� ó���� ���� ResultSet ��ü
	
	Vector<String> items = null; // ��ǰ ����� ���� ����
	String sql; // ���� �� ����
	
	public void connectDB() { // DB�� ����
		
		try {
			
			Class.forName(jdbcDriver); // ����̹� �ε�
			
			conn = DriverManager.getConnection(jdbcUrl, "root", "ssk13lke"); // �����ͺ��̽� ����
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} // catch
		
	} // connectDB
	
	public void closeDB() { // DB�� ���� ����
		
		try {
			
			pstmt.close(); // ���� ����
			// rs.close(); // rs�� ��� ��ɿ��� ������ �����Ƿ� ���⼭ ���� �ȵ�(�����͸� �������� ��쿡�� ���)
			conn.close();
			
		} catch(SQLException e) {
			
			e.printStackTrace();
			
		} // catch
		
	} // closeDB
	
	public ArrayList<Product> getAll() { // ��� ��ǰ ������ ������
		
		connectDB();
		sql = "select * from product"; // ���� �� ����
		
		ArrayList<Product> datas = new ArrayList<Product>(); // ��ȯ�� ���� ArrayList ���� �� ����
		
		items = new Vector<String>();
		items.add("��ü");
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) { // ������ ���� �� ������
				
				Product p = new Product(); // Product Ŭ������ ��ü p ����
				
				p.setPrcode(rs.getInt("prcode")); // p�� ��ǰ ��ȣ ����
				p.setPrname(rs.getString("prname")); // p�� ��ǰ �̸� ����
				p.setPrice(rs.getInt("price")); // p�� ��ǰ ���� ����
				p.setManufacture(rs.getString("manufacture")); // p�� ��ǰ ������ ����
				
				datas.add(p); // p�� datas(ArrayList)�� ���� 
				items.add(String.valueOf(rs.getInt("prcode")));
				
			} // while
			
			rs.close();
			
		} catch(Exception e) { }
		
		closeDB();
		
		return datas;
		
	} // getAll
	
	public Product getProduct(int prcode) { // 1���� Product ��ü ��ȯ
		
		sql = "select * from product where prcode = ?"; // Ư�� prcode�� ������ Product Ŭ������ ��ü ��ȯ
		
		Product p = null; // p �ʱ�ȭ
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql); // ������ ���� ����
			pstmt.setInt(1, prcode); // ?�� ���ڷ� �޾ƿ� prcode�� ���� 
			rs = pstmt.executeQuery();
			
			rs.next();
			p = new Product(); // ��ȯ �� ��ü�� ���� �� ���� ����
			p.setPrcode(rs.getInt("prcode")); // p�� ��ǰ ��ȣ ����
			p.setPrname(rs.getString("prname")); // p�� ��ǰ �̸� ����
			p.setPrice(rs.getInt("price")); // p�� ��ǰ ���� ����
			p.setManufacture(rs.getString("manufacture")); // p�� ��ǰ ������ ����
			
			rs.close();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			
		} // catch
		
		closeDB();
		
		return p;
		
	} // getProduct
	
	public boolean newProduct(Product product) { // DB�� ��ǰ ����
		
		String sql = "insert into product values(?, ?, ?, ?)";
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product.getPrcode()); // ��ǰ ���� �Է�
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
	
	public boolean delProduct(int prcode) { // DB���� ��ǰ ����
		
		String sql = "delete from product where prcode = ?";
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, prcode); // Ư�� prcode�� ������ ��ǰ�� DB���� ����
			
			pstmt.executeUpdate();
			
		} catch(Exception e) {
			
			e.printStackTrace();
			return false;
			
		} // catch
		
		closeDB();
		
		return true;
		
	} // delProduct
	
	public boolean updateProduct(Product product) { // DB���� ��ǰ ���� ����
		
		String sql = "update product SET prcode = ?, prname = ?, price = ?, manufacture = ? where prcode = ?";
		
		connectDB();
		
		try {
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, product.getPrcode()); // ������ ��ǰ�� ���� �Է�
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
	
	public Vector<String> getItems() { // ��ǰ ����� ��ȯ
		
		return items;
		
	} // getItems

}
