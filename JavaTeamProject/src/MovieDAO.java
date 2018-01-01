import java.sql.*;
import java.util.*;

public class MovieDAO {
	
	// DB ������ ����ϴ� ���� �� ��ü 
	private String jdbcDriver = "com.mysql.jdbc.Driver";
	private String jdbcUrl = "jdbc:mysql://localhost/javadb";
	private Connection conn;
	   
	private PreparedStatement pstmt;
	private ResultSet rs;
	   
	private View v; 
	   
	private String sql; // sql�� string
	   
	ArrayList<Movie> movieList; // db���� ������ ��ȭ ����Ʈ�� ������ ����Ʈ
	
	public MovieDAO() {
	   // �ʱ�ȭ
		AppMain app = AppMain.getInstance(); // appmain Ŭ������ �޾ƿ�
		app.setMovieDAO(this); // �ڽ��� ��ü�� set
	    sql = "";
	    movieList = new ArrayList<Movie>();
	    v = app.getView(); // view ��ü �޾ƿ� 
	}
	   
	public void connectDB() {
	    try{ 
	        // 1. JDBC ����̹� �ε�
	        Class.forName(jdbcDriver);
	         
	        // 2. DB ���� 
	        conn = DriverManager.getConnection(jdbcUrl, "root", "ssk13lke");
	    } catch(Exception e1) {
	        e1.printStackTrace();
	    }
	} // DB ���� �޼ҵ�
	   
	public void closeDB() {
	    try {
	    	pstmt.close();
	        conn.close();
	    } catch(Exception e2) {
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
	      
	    sql += " order by num_people desc limit 15"; // �߰� �˻����� ���� 
	      
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
	   } catch(Exception e3) {
	        e3.printStackTrace();
	   }
	      
	   closeDB(); // DB ���� ���� 
	   
	   return datas;
	} // sortPNum(), DB���� ���ǿ� �´� �����͸� ������
	
	// ���غ� ��ȭ ����� �� top 5
	public ArrayList<Movie> sortIncome(String _month, String _nation, String _rating) {
		
	    connectDB(); // DB ����
	    sql = "select * from movie";  // �⺻ 
	    
	    // �˻��� �����͸� �޴� ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    sql += searchReq(_month, _nation, _rating); // �˻� ���ǿ� �´� sql�� �ۼ� 
	    sql += " order by income desc limit 15"; // �߰� �˻����� ���� 
	      
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
	   } catch(Exception e4) {
	        e4.printStackTrace();
	   }
	      
	   closeDB(); // DB ���� ���� 
	   
	   return datas;
	} // sortIncome(), DB���� ���ǿ� �´� �����͸� ������
   
	// ���غ� ȿ���� ��ȭ top 5
	public ArrayList<Movie> sortEffeciency(String _month, String _nation, String _rating) {
		
	    connectDB(); // DB ����
	    sql = "select *, income/num_screen as 'effeciency' from movie";  // �⺻ sql�� 
	    sql += searchReq(_month, _nation, _rating); // �˻� ���ǿ� �´� sql�� �ۼ� 
	    sql += " order by income/num_screen desc limit 15"; // �߰� �˻����� ����
	    
	    // �˻��� �����͸� �޴� ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
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
	            m.setFResult(rs.getFloat("effeciency")); // ȿ���� 
	            datas.add(m); // �����͸� arrayList�� ����
	         
	        } // while, ������ ������ ���� 

	        rs.close(); // close 
	   } catch(Exception e5) {
	        e5.printStackTrace();
	   }
	      
	   closeDB(); // DB ���� ���� 
	   
	   return datas;
	} // sortEffeciency(), DB���� ���ǿ� �´� �����͸� ������
	
	// ���غ� ���� ���� top 5
	// �޺��ڽ� 3�� ���� ���� �� �� ���� 
	// ��ũ�� �� ��� ��ȭ ����, ����� ��� ���� ����� set�� 
	public ArrayList<Movie> sortDirector(String _drname) {
		
	    connectDB(); // DB ����
	    
	    sql = "select * from movie";  // �⺻
	    sql += " where drname like " + "'%"+ _drname + "%'";
	    sql += " order by drname"; // �߰� �˻����� ���� 
	    
	    // �˻��� �����͸� �޴� ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    try{
	    	// �ʿ�� pstmt �׳� tmt�� �ٲٱ� 
	        pstmt = conn.prepareStatement(sql); // sql�� ����
	        rs = pstmt.executeQuery(); // �Է¹��� sql�� ����, ��� �����͸� ���� 
	        
	        while(rs.next()) {

        		Movie m = new Movie();

	            //m.setMvcode(rs.getInt("mvcode")); // ��ȭ ��ȣ 
	            m.setMvname(rs.getString("mvname")); // ��ȭ ���� 
	            m.setDrname(rs.getString("drname")); // ���� �̸� 
	            //m.setOpendate(rs.getDate("open_date")); // ������
	            //m.setCountry(rs.getString("country")); // ����
	            m.setNumScreen(rs.getInt("num_screen")); // ��ũ�� �� 
	            m.setIncome(rs.getLong("income")); // ����� 
	            //m.setNumPeople(rs.getInt("num_people")); // ���� ��
	            //m.setGenre(rs.getString("genre")); // �帣
	            //m.setRating(rs.getString("rating")); // ���� ��� 
	            datas.add(m); // �����͸� arrayList�� ����
	         
	        } // while, ������ ������ ���� 

	        rs.close(); // close
	        
	   }catch(Exception e6) {
	        e6.printStackTrace();
	   }
	      
	   closeDB(); // DB ���� ���� 
	   
	   return datas;
	} // sortDirector(), DB���� ���ǿ� �´� �����͸� ������
	
	// ���غ� ������ top 5 < ���� ���� �� 100�� �̻�)
	// �޺��ڽ� 3�� ���� �ϴ� �� 

	public ArrayList<Movie> sortSuccess() {

	    connectDB(); // DB ����
	    sql = "select * from movie where num_people > 1000000 order by num_people desc;";  // �⺻ 

	    // �˻��� �����͸� �޴� ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();

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
	            m.setNumScreen(rs.getInt("num_screen")); // ��ũ�� �� ��� 
	            m.setIncome(rs.getLong("income")); // ����� ��� ���� ����� 
	            m.setNumPeople(rs.getInt("num_people")); // ���� ��
	            m.setGenre(rs.getString("genre")); // �帣
	            m.setRating(rs.getString("rating")); // ���� ��� 
	            datas.add(m); // �����͸� arrayList�� ����
	            
	        } // while, ������ ������ ���� 
	        
	        rs.close(); // close 

	   }catch(Exception e7) {
	        e7.printStackTrace();
	   }

	   closeDB(); // DB ���� ���� 

	   return datas;

	} // sortSuccess(), DB���� ���ǿ� �´� �����͸� ������
	
   // ��ũ�� �� ��� ��ȭ ���� �߰�
   public ArrayList<Movie> sortTheme() {
	   
       connectDB(); // DB ����
       
       sql = "select genre, avg(income) as 'avg_income', count(*) as 'num_movie' from movie group by genre order by count(*) desc";  // �⺻ 
       
       // �˻��� �����͸� �޴� ArrayList
       ArrayList<Movie> datas = new ArrayList<Movie>();
       
       try{
          // �ʿ�� pstmt �׳� tmt�� �ٲٱ� 
           pstmt = conn.prepareStatement(sql); // sql�� ����
           rs = pstmt.executeQuery(); // �Է¹��� sql�� ����, ��� �����͸� ���� 
           
           while(rs.next()) {
              
               Movie m = new Movie();

               //m.setMvcode(rs.getInt("mvcode")); // ��ȭ ��ȣ 
               //m.setMvname(rs.getString("mvname")); // ��ȭ ���� 
               //m.setDrname(rs.getString("drname")); // ���� �̸� 
               //m.setOpendate(rs.getDate("open_date")); // ������
               //m.setCountry(rs.getString("country")); // ����
               m.setNumScreen(rs.getInt("num_movie")); // ��ũ�� �� ��� ��ȭ ���� 
               //m.setIncome(rs.getLong("income")); // �����
               //m.setNumPeople(rs.getInt("num_people")); // ���� ��
               m.setGenre(rs.getString("genre")); // �帣
               //m.setRating(rs.getString("rating")); // ���� ��� 
               m.setFResult(rs.getFloat("avg_income")); // ��� �����    
               datas.add(m); // �����͸� arrayList�� ����
            
           } // while, ������ ������ ���� 

           rs.close(); // close
           
      }catch(Exception e8) {
           e8.printStackTrace();
      }
         
      closeDB(); // DB ���� ���� 
      
      return datas;
   } // sortTheme(), DB���� ���ǿ� �´� �����͸� ������
   
   public ArrayList<Movie> sortWord(String word) {
		
	    connectDB(); // DB ����
	    
	    sql = "select * from movie";  // �⺻
	    sql += " where mvname like " + "'%"+ word + "%'";
	    sql += " order by mvname"; // �߰� �˻����� ����  
	    
	    // �˻��� �����͸� �޴� ArrayList
	    ArrayList<Movie> datas = new ArrayList<Movie>();
	      
	    try{
	    	// �ʿ�� pstmt �׳� tmt�� �ٲٱ� 
	        pstmt = conn.prepareStatement(sql); // sql�� ����
	        rs = pstmt.executeQuery(); // �Է¹��� sql�� ����, ��� �����͸� ���� 
	        
	        while(rs.next()) {

       		Movie m = new Movie();

	            //m.setMvcode(rs.getInt("mvcode")); // ��ȭ ��ȣ 
	            m.setMvname(rs.getString("mvname")); // ��ȭ ���� 
	            //m.setDrname(rs.getString("drname")); // ���� �̸� 
	            m.setOpendate(rs.getDate("open_date")); // ������
	            //m.setCountry(rs.getString("country")); // ����
	            //m.setNumScreen(rs.getInt("num_screen")); // ��ũ�� �� 
	            //m.setIncome(rs.getLong("income")); // ����� 
	            //m.setNumPeople(rs.getInt("num_people")); // ���� ��
	            m.setGenre(rs.getString("genre")); // �帣
	            m.setRating(rs.getString("rating")); // ���� ��� 
	            datas.add(m); // �����͸� arrayList�� ����
	         
	        } // while, ������ ������ ���� 

	        rs.close(); // close
	        
	   }catch(Exception e9) {
	        e9.printStackTrace();
	   }
	      
	   closeDB(); // DB ���� ����
	   
	   return datas;
	   
	} // sortWord(), DB���� ���ǿ� �´� �����͸� ������
	   
	
	// �˻� ���ǿ� �´� sql���� �ۼ��ϴ� �޼ҵ� 
	private String searchReq(String _month, String _nation, String _rating) {
		String sql = "";
	    // ���� �˻� ���� üũ, ��ü �˻��� ���� ���� ���  
	    if (!(_month.equals("��ü"))) {
	    	// ��ü�� �ƴϸ� ���� �Ⱓ �˻��� �߰� 
	        sql+= " where DATE(open_date) between '2017-" +_month 
	               +"-01' and last_day('2017-"+_month +"-01')";
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
	            sql += " and rating like " + "'"+ _rating + "%'"; 
	    	}
	    	// �� ������ �Ѵ� ��ü�� ��� (���� ���)
	    	else {
	    		sql += " where rating like " + "'"+ _rating + "%'";
	    	}   
	    }
	    // ������ ��ü�� ��� �� �ʿ� x 
	      
	    return sql;
	      
	} // searchReq()
}