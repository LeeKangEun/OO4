import java.util.Date;

public class Movie {
	
	// ��ȭ ���� ������ 
	private int mvcode; // ��ȭ �ڵ�
	private String mvname; // ��ȭ �̸�
	private String drname; // ���� �̸�
	private Date open_date;// ������ string, date ��ɷ� ���� ����
	private String country; // ����
	private int num_screen; // ���� ��ũ�� ��
	private long income; // ����� 
	private int num_people; // ������ ��
	private String genre; // �帣
	private String rating; // ������� 
	// �̹���
	
	private Movie movie; // ��ü 
	
	// ������
	public Movie() {
		
	}
	
	// ������ �����ε� 
	public Movie(int _mvcode, String _mvname, String _drname, Date _open_date, String _country, int _num_screen,
				 long _income, int _num_people, String _genre, String _rating) {
		mvcode = _mvcode;
		mvname = _mvname;
		drname = _drname;
		open_date = _open_date;
		country = _country;
		num_screen = _num_screen;
		income = _income;
		num_people = _num_people;
		genre = _genre;
		rating = _rating;
	}
	
	public Movie(Movie _movie) {
		movie = _movie;
	}
	
	// get, set
	public Movie getMovie() {
		return movie;
	}
	public int getMvcode() {
		return mvcode;
	}
	public String getMvname() {
		return mvname;
	}
	public String getDrname() {
		return drname;
	}
	public Date getOpendate() {
		return open_date;
	}
	public String getCountry() {
		return country;
	}
	public int getNumScreen() {
		return num_screen;
	}
	public long getIncome() {
		return income;
	}
	public int getNumPeople() {
		return num_people;
	}
	public String getGenre() {
		return genre;
	}
	public String getRating() {
		return rating;
	}
	
	public void setMvcode(int _mvcode) {
		mvcode = _mvcode;
	}
	public void setMvname(String _mvname) {
		mvname = _mvname;
	}
	public void setDrname(String _drname) {
		drname = _drname;
	}
	public void setOpendate(Date _open_date) {
		open_date = _open_date;
	}
	public void setCountry(String _country) {
		country = _country;
	}
	public void setNumScreen(int _num_screen) {
		num_screen = _num_screen;
	}
	public void setIncome(long _income) {
		income = _income;
	}
	public void setNumPeople(int _num_people) {
		num_people = _num_people;
	}
	public void setGenre(String _genre) {
		genre = _genre;
	}
	public void setRating(String _rating) {
		rating = _rating;
	}
}