import java.awt.*;
import java.awt.event.*;
import java.text.*;
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
		setTitle("영화 분석 프로그램");
		
		this.setLayout(new FlowLayout());
		
		JPanel explain = new JPanel();
		JPanel standard = new JPanel();
		JPanel func1 = new JPanel();
		JPanel func2 = new JPanel();
		JPanel showResult = new JPanel();
		JPanel result = new JPanel();
		JPanel resultDetail = new JPanel();
		
		explain.setLayout(new BorderLayout());
		standard.setLayout(new GridLayout(1, 7, 30, 0));
		func1.setLayout(new GridLayout(1, 3, 100, 0));
		func2.setLayout(new GridLayout(1, 3, 100, 0));
		showResult.setLayout(new GridLayout(1, 4));
		result.setLayout(new GridLayout(1, 4));
		resultDetail.setLayout(new GridLayout(1,2));
		
		explain.setPreferredSize(new Dimension(800,70));
		standard.setPreferredSize(new Dimension(800,30));
		func1.setPreferredSize(new Dimension(800,70));
		func2.setPreferredSize(new Dimension(800,70));
		showResult.setPreferredSize(new Dimension(800,20));
		result.setPreferredSize(new Dimension(800,380));
		resultDetail.setPreferredSize(new Dimension(780,160));
		
		this.add(explain);
		this.add(standard);
		this.add(func1);
		this.add(func2);
		this.add(showResult);
		this.add(result);
		this.add(resultDetail);
		
		JLabel title = new JLabel("영화가 궁금해~");
		title.setFont(new Font("궁서", 0, 35));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel current = new JLabel("");
		current.setFont(new Font("나눔고딕코딩", 0, 23));
		current.setHorizontalAlignment(JLabel.CENTER);
		
		explain.add(title, BorderLayout.NORTH);
		explain.add(current, BorderLayout.SOUTH);
		
		JLabel lMonth = new JLabel("개봉 월 선택");
		JLabel lNation = new JLabel("제작 국가 선택");
		JLabel lRating = new JLabel("관람 기준 선택");
		
		JButton initButton = new JButton("초기화");
		initButton.setBackground(Color.white);
		initButton.setFont(new Font("HY바다M", 0, 17));
		
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
		standard.add(initButton);
		
		JButton[] btn = new JButton[8];
		
		btn[0] = new JButton("관객 수");
		btn[1] = new JButton("매출액");
		btn[2] = new JButton("효율성");
		btn[3] = new JButton("감독별");
		btn[4] = new JButton("장르별");
		btn[5] = new JButton("단어별");
		btn[6] = new JButton("흥행작");
		btn[7] = new JButton("?");
		
		for(int i=0; i<8; i++)
			btn[i].setFont(new Font("HY동녘M", 0, 27));
		
		func1.add(btn[0]);
		func1.add(btn[1]);
		func1.add(btn[2]);
		func1.add(btn[3]);
		
		func2.add(btn[4]);
		func2.add(btn[5]);
		func2.add(btn[6]);
		func2.add(btn[7]);
		
		JTextArea[] resultText = new JTextArea[4];
		
		for(int i=0; i<4; i++) {
			
			resultText[i] = new JTextArea();
			resultText[i].setPreferredSize(new Dimension(200,340));
			resultText[i].setEditable(false);
			resultText[i].setFont(new Font("", 0, 12));
			result.add(resultText[i]);
			
		} // for		
		
		JLabel[] resultStandard = new JLabel[4];
		
		for(int i=0; i<4; i++) {
			
			resultStandard[i] = new JLabel("");
			resultStandard[i].setFont(new Font("", 1, 18));
			showResult.add(resultStandard[i]);
			
		} // for
		
		JDialog inputDr = new JDialog(this, "감독 이름 입력");
		inputDr.setLayout(new FlowLayout());
		inputDr.setSize(280,80);
		inputDr.setLocation(200,200);
		inputDr.setVisible(false); // 처음에는 보이지 않음
		
		JTextField inputTextDr = new JTextField(12);
		JButton inputDrButton = new JButton("입력");
		JButton closeDrButton = new JButton("취소");
		
		inputDr.add(inputTextDr);
		inputDr.add(inputDrButton);
		inputDr.add(closeDrButton);
		
		JDialog inputWord = new JDialog(this, "단어 입력");
		inputWord.setLayout(new FlowLayout());
		inputWord.setSize(280,80);
		inputWord.setLocation(200,200);
		inputWord.setVisible(false); // 처음에는 보이지 않음
		
		JTextField inputTextWord = new JTextField(12);
		JButton inputWordButton = new JButton("입력");
		JButton closeWordButton = new JButton("취소");
		
		inputWord.add(inputTextWord);
		inputWord.add(inputWordButton);
		inputWord.add(closeWordButton);
		
		
		DecimalFormat form = new DecimalFormat("#,###");
		
		btn[0].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(monthChoose, nationChoose, ratingChoose) + " 관객 수 분석");
				
				datas = md.sortPNum(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("영화 제목");
				resultStandard[1].setText("개봉 날짜");
				resultStandard[2].setText("관람가");
				resultStandard[3].setText("관람 인원");
				
				for(Movie m : datas) {
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getOpendate() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getNumPeople()) + "명\n");
					
				}
			}			
		});
		
		btn[1].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(monthChoose, nationChoose, ratingChoose) + " 매출액 분석");
				
				datas = md.sortIncome(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());

				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("영화 제목");
				resultStandard[1].setText("감독");
				resultStandard[2].setText("관람가");
				resultStandard[3].setText("매출액");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getDrname() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getIncome()) + "원\n");
					
				}
			}			
		});
		
		btn[2].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(monthChoose, nationChoose, ratingChoose) + " 효율성 분석");

				datas = md.sortEffeciency(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("영화 제목");
				resultStandard[1].setText("장르");
				resultStandard[2].setText("관람가");
				resultStandard[3].setText("스크린당 수입");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getGenre() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getIncome() / m.getNumScreen()) + "원\n");
					
				}
			}			
		});
		
		btn[3].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(true);
				
			}			
		});
		
		btn[4].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText("장르별 분석");
				datas = md.sortTheme();
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("장르");
				resultStandard[1].setText("영화 개수");
				resultStandard[2].setText("평균 매출액");
				resultStandard[3].setText("");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getGenre() + "\n");
					resultText[1].append(m.getNumScreen() + "개\n");
					resultText[2].append(form.format(m.getFResult()) + "원\n");
					
				}
			}			
		});
		
		btn[5].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(true);
				
			}			
		});
		
		btn[6].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText("흥행작 분석");
				
				datas = md.sortSuccess(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("영화 제목");
				resultStandard[1].setText("매출액");
				resultStandard[2].setText("스크린 수");
				resultStandard[3].setText("관람객 수");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(form.format(m.getIncome()) + "원\n");
					resultText[2].append(form.format(m.getNumScreen()) + "개\n");
					resultText[3].append(form.format(m.getNumPeople()) + "명\n");
					
				}
			}			
		});
		
		initButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0; i<4; i++) {
					
					resultText[i].setText("");
					resultStandard[i].setText("");
					
				} // for
				
				current.setText("");
			}			
		});
		
		inputDrButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(false);
				current.setText("감독별 분석");
				
				datas = md.sortDirector(inputTextDr.getText().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("감독명");
				resultStandard[1].setText("영화 제목");
				resultStandard[2].setText("스크린 수");
				resultStandard[3].setText("매출액");
				
				// System.out.println(datas.get(0).getDrname());
				

				for(Movie m : datas) {
					
					resultText[0].append(m.getDrname() + "\n");
					resultText[1].append(m.getMvname() + "\n");
					resultText[2].append(form.format(m.getNumScreen()) + "개\n");
					resultText[3].append(form.format(m.getIncome()) + "원\n");
						
					
				}
				
				inputTextDr.setText("");
				
			}			
		});
		
		closeDrButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(false);
				inputTextDr.setText("");
				inputDr.setSize(280,80);
				inputDr.setLocation(200,200);
				
			}			
		});
		
		inputWordButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(false);
				current.setText("단어별 분석");
				
				datas = md.sortWord(inputTextWord.getText().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("영화 제목");
				resultStandard[1].setText("개봉일");
				resultStandard[2].setText("장르");
				resultStandard[3].setText("관람 등급");

				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getOpendate() + "\n");
					resultText[2].append(m.getGenre() + "\n");
					resultText[3].append(m.getRating() + "\n");
						
					
				}
				
				inputTextWord.setText("");
				
			}			
		});
		
		closeWordButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(false);
				inputTextWord.setText("");
				inputWord.setSize(280,80);
				inputWord.setLocation(200,200);
				
			}			
		});
			
		setSize(830, 800);
		setVisible(true);
		
	} // view
	
	public String chooseStandard(JComboBox monthChoose, JComboBox nationChoose, JComboBox ratingChoose) {
		
		String selectedMonth = (monthChoose.getSelectedItem().toString().equals("전체"))?
				"" : (monthChoose.getSelectedItem().toString() +"월");
		
		String selectedNation = (nationChoose.getSelectedItem().toString().equals("전체"))?
				"" : (" " + nationChoose.getSelectedItem().toString());
		
		String selectedRating = (ratingChoose.getSelectedItem().toString().equals("전체"))?
				"" : (" " + ratingChoose.getSelectedItem().toString());
		
		// current.setText(selectedMonth + selectedNation + selectedRating);
		
		return selectedMonth + selectedNation + selectedRating;
		
	} // chooseStandard

	
	public static void main(String[] args) {
		
		new View();
	}

}
