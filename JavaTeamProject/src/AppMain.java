
/*
 * 18.01.07 ������ 
 */

public class AppMain {
	
	private static AppMain s_instance; // �̱��� ������ �����ϱ� ����
	
	private View m_view; // ������ Ŭ������ ���� ����
	private MovieDAO m_movieDAO;
	// private Movie movie;
	
	private AppMain() {
		
	}

	public static AppMain getInstance() { // AppMain Ŭ������ ����ϱ� ����
		
		if(s_instance == null) {
			s_instance = new AppMain();
		}
		return s_instance;
	}
	
	// get / set �޼ҵ�
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