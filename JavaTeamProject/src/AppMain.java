
/*
 * 18.01.07 최종본 
 */

public class AppMain {
	
	private static AppMain s_instance; // 싱글톤 패턴을 적용하기 위함
	
	private View m_view; // 각각의 클래스의 변수 선언
	private MovieDAO m_movieDAO;
	// private Movie movie;
	
	private AppMain() {
		
	}

	public static AppMain getInstance() { // AppMain 클래스를 사용하기 위함
		
		if(s_instance == null) {
			s_instance = new AppMain();
		}
		return s_instance;
	}
	
	// get / set 메소드
	public void setView(View _view) {
		m_view = _view;
	} // setView
	
	public View getView() {
		return m_view;
	}
	
	public void setMovieDAO(MovieDAO _movieDAO) {
		m_movieDAO = _movieDAO;	
	}
	
	public MovieDAO getMovieDAO() {
		return m_movieDAO;
	}
}