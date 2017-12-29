import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.*;

public class View extends JFrame{
	ArrayList<Movie> datas;
	MovieDAO md;
	
	public View() {
		AppMain.getInstance().setView(this); // view 객체를 appmain에 set 
		
		AppMain app = AppMain.getInstance();
		MovieDAO md = new MovieDAO(); // MovieDAO 객체 생성
		datas = new ArrayList<Movie>();
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("관리 프로그램");
		
		this.setLayout(new FlowLayout());
		
		JPanel explain = new JPanel();
		JPanel standard = new JPanel();
		JPanel func1 = new JPanel();
		JPanel func2 = new JPanel();
		JPanel result = new JPanel();
		
		explain.setLayout(new BorderLayout());
		standard.setLayout(new GridLayout(1, 6, 30, 0));
		func1.setLayout(new GridLayout(1, 3, 100, 0));
		func2.setLayout(new GridLayout(1, 3, 100, 0));
		result.setLayout(new FlowLayout());
		
		explain.setPreferredSize(new Dimension(800,70));
		standard.setPreferredSize(new Dimension(800,30));
		func1.setPreferredSize(new Dimension(800,70));
		func2.setPreferredSize(new Dimension(800,70));
		result.setPreferredSize(new Dimension(800,560));
		
		this.add(explain);
		this.add(standard);
		this.add(func1);
		this.add(func2);
		this.add(result);
		
		
		JLabel title = new JLabel("영화 분석");
		title.setFont(new Font("궁서", 0, 30));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel current = new JLabel("");
		current.setFont(new Font("궁서", 0, 20));
		current.setHorizontalAlignment(JLabel.CENTER);
		
		explain.add(title, BorderLayout.NORTH);
		explain.add(current, BorderLayout.SOUTH);
		

		JLabel lMonth = new JLabel("개봉 월 선택");
		JLabel lNation = new JLabel("제작 국가 선택");
		JLabel lRating = new JLabel("관람 기준 선택");
		
		lMonth.setHorizontalAlignment(JLabel.RIGHT);
		lNation.setHorizontalAlignment(JLabel.RIGHT);
		lRating.setHorizontalAlignment(JLabel.RIGHT);
	
		String[] strMonth = {"전체", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] strNation = {"전체", "한국", "외국"};
		String[] strRating = {"전체", "전체관람가", "12세이상관람가", "15세이상관람가", "청소년관람불가"};
		
		JComboBox monthChoose = new JComboBox(strMonth);
		JComboBox nationChoose = new JComboBox(strNation);
		JComboBox ratingChoose = new JComboBox(strRating);
	
		standard.add(lMonth);
		standard.add(monthChoose);
		standard.add(lNation);
		standard.add(nationChoose);
		standard.add(lRating);
		standard.add(ratingChoose);
		
		JButton[] btn = new JButton[6];
		
		btn[0] = new JButton("관객 수");
		btn[1] = new JButton("매출액");
		btn[2] = new JButton("효율성");
		btn[3] = new JButton("감독별");
		btn[4] = new JButton("흥행작");
		btn[5] = new JButton("장르별");
		
		for(int i=0; i<6; i++)
			btn[i].setFont(new Font("굴림", 0, 30));
		
		func1.add(btn[0]);
		func1.add(btn[1]);
		func1.add(btn[2]);
		
		func2.add(btn[3]);
		func2.add(btn[4]);
		func2.add(btn[5]);
		
		JTextArea resultText = new JTextArea();
		resultText.setPreferredSize(new Dimension(780,480));
		result.add(resultText);

		
		btn[0].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				chooseStandard(monthChoose, nationChoose, ratingChoose, current);
				datas = md.sortPNum(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				resultText.setText("");
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				for(Movie m : datas) {
					resultText.append(m.getMvname() + "\t\t"+m.getRating() +"\n");
					
				}
				
			}			
			
		});
			
		setSize(830, 800);
		setVisible(true);
		
	} // view
	
	public void chooseStandard(JComboBox monthChoose, JComboBox nationChoose, JComboBox ratingChoose, JLabel current) {
		
		String selectedMonth = (monthChoose.getSelectedItem().toString().equals("전체"))?
				"" : (monthChoose.getSelectedItem().toString() +"월");
		String selectedNation = (nationChoose.getSelectedItem().toString().equals("전체"))?
				"" : (" " + nationChoose.getSelectedItem().toString());
		String selectedRating = (ratingChoose.getSelectedItem().toString().equals("전체"))?
				"" : (" " + ratingChoose.getSelectedItem().toString());
		
		current.setText(selectedMonth + selectedNation + selectedRating);
		
	}

	
	public static void main(String[] args) {
		
		new View();
	}

}
