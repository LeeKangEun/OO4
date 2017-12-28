
public class AppMain {
	
	private static AppMain s_instance;
	
	private View m_view;
	
	public static AppMain getInstance() {
		
		if(s_instance == null)
			s_instance = new AppMain();
		
		return s_instance;
	}
	
	public void setView(View _view) {
		
		m_view = _view;
		
	} // setView
	
	public View getView() {
		
		return m_view;
		
	}

	public static void main(String[] args) {
		
		
	}

}
