import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class View extends JFrame{
	
	public View() {
		
		AppMain.getInstance().setView(this);;
		
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
		String[] strRating = {"전체", "전체 관람가", "12세 이상 관람가", "15세 이상 관람가", "청소년 관람 불가"};
		
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
		// resultText.add(new JScrollPane());
		
		btn[0].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				String selectedMonth = (monthChoose.getSelectedItem().toString().equals("전체"))?
						"" : (monthChoose.getSelectedItem().toString() +"월");
				String selectedNation = (nationChoose.getSelectedItem().toString().equals("전체"))?
						"" : (" " + nationChoose.getSelectedItem().toString());
				String selectedRating = (ratingChoose.getSelectedItem().toString().equals("전체"))?
						"" : (" " + ratingChoose.getSelectedItem().toString());
				
				current.setText(selectedMonth + selectedNation + selectedRating);
				
			}
			
		});
			
		setSize(830, 800);
		setVisible(true);
		
	} // view

	public static void main(String[] args) {
		
		new View();

	}

}
