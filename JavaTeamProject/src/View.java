import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;


/*
 * 18.01.07 최종본 
 */

public class View extends JFrame{
	
	ArrayList<Movie> datas; // DB에서 받아온 영화 정보를 저장 할 변수 
	MovieDAO md; // MovieDAO 객체 선언
	
	public View() { // 생성자
		
		AppMain.getInstance().setView(this); // view 객체를 AppMain에 set 
		
		AppMain app = AppMain.getInstance(); // 싱글톤 패턴 적용
		MovieDAO md = new MovieDAO(); // MovieDAO 객체 생성
		datas = new ArrayList<Movie>(); // Movie클래스의 ArrayList 객체 생성
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("영화 분석 프로그램");
		
		this.setLayout(new FlowLayout()); // 전체 레이아웃 설정
		
		JPanel explain = new JPanel(); // 콤보 박스를 통해 어떤 기준을 선택했는지 보여줌(각 기능들을 각각의 패널로 구현)
		JPanel standard = new JPanel(); // 기준을 선택할 콤보 박스를 보여줌
		JPanel func1 = new JPanel(); // 기능 1
		JPanel func2 = new JPanel(); // 기능 2
		JPanel showResult = new JPanel(); // 분석 지표를 보여주기 위한 패널
		JPanel result = new JPanel(); // 결과를 보여주기 위한 패널 
		
		explain.setLayout(new BorderLayout()); // 각각의 패널들의 레이아웃 설정
		standard.setLayout(new GridLayout(1, 6, 30, 0));
		func1.setLayout(new GridLayout(1, 3, 100, 0));
		func2.setLayout(new GridLayout(1, 3, 100, 0));
		showResult.setLayout(new GridLayout(1, 4));
		result.setLayout(new GridLayout(1, 4));
		
		explain.setPreferredSize(new Dimension(800,70)); // 각각의 패널들의 크기 설정
		standard.setPreferredSize(new Dimension(800,30));
		func1.setPreferredSize(new Dimension(800,70));
		func2.setPreferredSize(new Dimension(800,70));
		showResult.setPreferredSize(new Dimension(800,20));
		result.setPreferredSize(new Dimension(800,460));
		
		this.add(explain); // 각각의 페널들을 화면에 추가
		this.add(standard);
		this.add(func1);
		this.add(func2);
		this.add(showResult);
		this.add(result);
		
		JLabel title = new JLabel("영화 데이터 분석"); // 가장 위에서 프로그램의 제목을 보여줌
		title.setFont(new Font("나눔스퀘어라운드 ExtraBold", 0, 37)); // 폰트 설정
		title.setHorizontalAlignment(JLabel.CENTER); // 중앙 정렬
		
		JLabel current = new JLabel(""); // 현재 설정한 기준을 보여줌(월, 국적, 관람가)
		current.setFont(new Font("나눔고딕", 0, 23)); // 폰트 설정
		current.setHorizontalAlignment(JLabel.CENTER); // 중앙 정렬
		
		explain.add(title, BorderLayout.NORTH); // 패널에 add해줌
		explain.add(current, BorderLayout.SOUTH);
		
		JLabel lYear = new JLabel("개봉 년도"); // 무슨 무슨 기준이 있는지 보여주기 위함
		JLabel lMonth = new JLabel("개봉 월");
		JLabel lNation = new JLabel("제작 국가");
		JLabel lRating = new JLabel("관람 기준");
		
		lYear.setFont(new Font("나눔스퀘어라운드 Bold", 0, 18));
		lMonth.setFont(new Font("나눔스퀘어라운드 Bold", 0, 18));
		lNation.setFont(new Font("나눔스퀘어라운드 Bold", 0, 18));
		lRating.setFont(new Font("나눔스퀘어라운드 Bold", 0, 18));
		
		lYear.setHorizontalAlignment(JLabel.RIGHT); // 라벨들 정렬
		lMonth.setHorizontalAlignment(JLabel.RIGHT);
		lNation.setHorizontalAlignment(JLabel.RIGHT);
		lRating.setHorizontalAlignment(JLabel.RIGHT);
	
		String[] strYear = {"전체", "2016", "2017"}; // 콤보 박스 문자열들 선언
		String[] strMonth = {"전체", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] strNation = {"전체", "한국", "외국"};
		String[] strRating = {"전체", "전체관람가", "12세이상관람가", "15세이상관람가", "청소년관람불가"};
		
		JComboBox yearChoose = new JComboBox(strYear); // 콤보박스 생성
		JComboBox monthChoose = new JComboBox(strMonth);
		JComboBox nationChoose = new JComboBox(strNation);
		JComboBox ratingChoose = new JComboBox(strRating);
		
		yearChoose.setFont(new Font("나눔스퀘어라운드 Regular", 0, 15));
		monthChoose.setFont(new Font("나눔스퀘어라운드 Regular", 0, 15));
		nationChoose.setFont(new Font("나눔스퀘어라운드 Regular", 0, 15));
		ratingChoose.setFont(new Font("나눔스퀘어라운드 Regular", 0, 15));
	
		standard.add(lYear); // 기준을 선택하기 위한 요소들을 패널에 추가해줌
		standard.add(yearChoose);
		standard.add(lMonth);
		standard.add(monthChoose);
		standard.add(lNation);
		standard.add(nationChoose);
		standard.add(lRating);
		standard.add(ratingChoose);
		
		JButton[] btn = new JButton[8]; // 각각의 기능을 위한 버튼 선언
		
		btn[0] = new JButton("관객수"); // 각각의 버튼 생성
		btn[1] = new JButton("매출액");
		btn[2] = new JButton("효율성");
		btn[3] = new JButton("감독별");
		btn[4] = new JButton("장르별");
		btn[5] = new JButton("단어별");
		btn[6] = new JButton("흥행작");
		btn[7] = new JButton("초기화");
		
		for(int i=0; i<8; i++) { // 버튼들의 속성 설정
			
			btn[i].setForeground(Color.BLACK);
			btn[i].setFont(new Font("나눔바른펜", 0, 27));
			
			btn[i].setBorderPainted(false);
			btn[i].setContentAreaFilled(false);
			btn[i].setFocusPainted(false);
			
		} // for
		
		btn[7].setForeground(Color.WHITE); // 마지막 버튼은 초기화 버튼이므로 따로 설정
		
		func1.add(btn[0]); // 각각의 버튼들을 패널에 추가해줌
		func1.add(btn[1]);
		func1.add(btn[2]);
		func1.add(btn[3]);
		
		func2.add(btn[4]);
		func2.add(btn[5]);
		func2.add(btn[6]);
		func2.add(btn[7]);
		
		JTextArea[] resultText = new JTextArea[4]; // 분석 결과를 보여주기 위한 TextArea 선언
		
		for(int i=0; i<4; i++) { // 생성, 크기와 속성과 폰트 설정, 추가
			
			resultText[i] = new JTextArea();
			resultText[i].setPreferredSize(new Dimension(200,340));
			resultText[i].setEditable(false);
			resultText[i].setFont(new Font("", 0, 12));
			result.add(resultText[i]);
			
		} // for
		
		JLabel[] resultStandard = new JLabel[4]; // 분석 지표를 보여주기 위한 라벨들 선언
		
		for(int i=0; i<4; i++) { // 라벨들 생성 및 폰트 설정후 패널에 추가
			
			resultStandard[i] = new JLabel("");
			resultStandard[i].setFont(new Font("나눔고딕", 1, 18));
			showResult.add(resultStandard[i]);
			
		} // for
		
		JDialog inputDr = new JDialog(this, "감독 이름 입력"); // 감독 이름 입력을 위한 다이얼로그 창 선언 및 생성
		inputDr.setLayout(new FlowLayout()); // 레이아웃 설정
		inputDr.setSize(280,80); // 크기 설정
		inputDr.setLocation(200,200); // 위치 설정
		inputDr.setVisible(false); // 처음에는 보이지 않음
		
		JTextField inputTextDr = new JTextField(12); // 입력 창 선언 및 생성
		JButton inputDrButton = new JButton("입력"); // 각각의 버튼 선언 및 생성
		JButton closeDrButton = new JButton("취소");
		
		inputDr.add(inputTextDr); // 다이얼로그 창에 추가
		inputDr.add(inputDrButton);
		inputDr.add(closeDrButton);
		
		JDialog inputWord = new JDialog(this, "단어 입력"); // 단어 입력을 위한 다이얼로그 선언 및 생성
		inputWord.setLayout(new FlowLayout()); // 레이아웃 설정
		inputWord.setSize(280,80); // 크기 설정
		inputWord.setLocation(200,200); // 위치 설정
		inputWord.setVisible(false); // 처음에는 보이지 않음
		
		JTextField inputTextWord = new JTextField(12); // 입력 창 선언 및 생성
		JButton inputWordButton = new JButton("입력"); // 각각의 버튼 선언 및 생성
		JButton closeWordButton = new JButton("취소");
		
		inputWord.add(inputTextWord); // 다이얼로그 창에 추가
		inputWord.add(inputWordButton);
		inputWord.add(closeWordButton);
		
		
		DecimalFormat form = new DecimalFormat("#,###"); // 천의 자리마다 쉼표를 찍기 위한 객체 선언 및 생성
		
		btn[0].addActionListener(new ActionListener() { // 관객 수 기준 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(yearChoose, monthChoose, nationChoose, ratingChoose) + " 관객 수 분석"); // 콤보 박스로 선택한 기준 적용후 표시
				datas = md.sortPNum(yearChoose.getSelectedItem().toString(), monthChoose.getSelectedItem().toString(), // 분석한 결과를 받아서 저장
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++) // 분석 결과를 보여주기 위한 TextArea를 초기화
					resultText[i].setText("");
				
				resultStandard[0].setText("영화 제목"); // 분석 지표 명시
				resultStandard[1].setText("개봉 날짜");
				resultStandard[2].setText("관람가");
				resultStandard[3].setText("관람 인원");
				
				for(Movie m : datas) { // 분석 결과 표시
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getOpendate() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getNumPeople()) + "명\n");
				}
			}			
		});
		
		btn[1].addActionListener(new ActionListener() { // 매출액 기준 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(yearChoose, monthChoose, nationChoose, ratingChoose) + " 매출액 분석");
				datas = md.sortIncome(yearChoose.getSelectedItem().toString(), monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());

				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
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
		
		btn[2].addActionListener(new ActionListener() { // 효율성(매출액 / 스크린 수) 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(yearChoose, monthChoose, nationChoose, ratingChoose) + " 효율성 분석");
				datas = md.sortEffeciency(yearChoose.getSelectedItem().toString(), monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
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
		
		btn[3].addActionListener(new ActionListener() { // 감독별 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(true); // 감독 이름 입력을 위한 창을 보이게 함
				
			}			
		});
		
		btn[4].addActionListener(new ActionListener() { // 장르별 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText("장르별 분석");
				datas = md.sortTheme(); // 장르별로 분석한 결과를 저장(콤보 박스 미사용)
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
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
		
		btn[5].addActionListener(new ActionListener() { // 단어별 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(true); // 단어 입력을 위한 창을 보이게 함
				
			}			
		});
		
		btn[6].addActionListener(new ActionListener() { // 흥행작 분석
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText("흥행작 분석");
				
				datas = md.sortSuccess();
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
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
		
		btn[7].addActionListener(new ActionListener() { // 초기화
			
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0; i<4; i++) { // 각각의 TextArea와 Label을 초기화 
					
					resultText[i].setText("");
					resultStandard[i].setText("");
					
				} // for
				
				current.setText("");
			}
		});
		
		inputDrButton.addActionListener(new ActionListener() { // 감독 이름 입력 창에서 입력 선택 시
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(false); // 입력 창을 보이지 않게 함
				current.setText("감독별 분석"); // 분석 기준 변경
				
				datas = md.sortDirector(inputTextDr.getText().toString()); // 분석 결과를 받아서 저장
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				resultStandard[0].setText("감독명"); // 분석 지표 명시
				resultStandard[1].setText("영화 제목");
				resultStandard[2].setText("스크린 수");
				resultStandard[3].setText("매출액");				

				for(Movie m : datas) { // 분석 결과를 보여줌
					resultText[0].append(m.getDrname() + "\n");
					resultText[1].append(m.getMvname() + "\n");
					resultText[2].append(form.format(m.getNumScreen()) + "개\n");
					resultText[3].append(form.format(m.getIncome()) + "원\n");
				}
				
				inputTextDr.setText("");
			}		
		});
		
		closeDrButton.addActionListener(new ActionListener() { // 감독 이름 입력 창에서 취소 선택 시
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(false); // 입력 창을 보이지 않게 함
				inputTextDr.setText(""); // 텍스트 창 비움
				inputDr.setSize(280,80); // 크기 재설정
				inputDr.setLocation(200,200); // 위치 재설정
				
			}			
		});
		
		inputWordButton.addActionListener(new ActionListener() { // 단어 입력 창에서 입력 선택 시
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(false); // 창을 보이지 않게 함
				current.setText("단어별 분석");
				
				datas = md.sortWord(inputTextWord.getText().toString()); // 분석 결과를 저장
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
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
		
		closeWordButton.addActionListener(new ActionListener() { // 단어 분석 창에서 취소 클릭 시
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(false);
				inputTextWord.setText("");
				inputWord.setSize(280,80);
				inputWord.setLocation(200,200);
				
			}			
		});
			
		setSize(830, 800); // GUI 크기 설정
		setVisible(true);
		
	} // view
	
	public String chooseStandard(JComboBox yearChoose, JComboBox monthChoose, JComboBox nationChoose, JComboBox ratingChoose) { // 콤보 박스에서 선택한 값들에 대한 조합
		
		String selectedYear = (yearChoose.getSelectedItem().toString().equals("전체"))? // 전체를 선택하면 반환 X
				"" : (yearChoose.getSelectedItem().toString() +"년"); // 그 이외에 년도를 선택하면 문자열을 붙인 후 반환
		
		String selectedMonth = (monthChoose.getSelectedItem().toString().equals("전체"))? // 전체를 선택하면 반환 X
				"" : (" " + monthChoose.getSelectedItem().toString() +"월"); // 그 이외에 달을 선택하면 문자열을 붙인 후 반환
		
		String selectedNation = (nationChoose.getSelectedItem().toString().equals("전체"))? // 전체 선택시 반환 X
				"" : (" " + nationChoose.getSelectedItem().toString()); // 그 이외에 선택한 국적 반환
		
		String selectedRating = (ratingChoose.getSelectedItem().toString().equals("전체"))?
				"" : (" " + ratingChoose.getSelectedItem().toString()); // 선택한 관람가 반환
		
		return selectedYear + selectedMonth + selectedNation + selectedRating; // 문자열 조합 후 반환
		
	} // chooseStandard

	
	public static void main(String[] args) {
		
		new View();
	}

}
