import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;

import javax.swing.*;

public class View extends JFrame{
	ArrayList<Movie> datas;
	MovieDAO md;
	
	public View() {
		
		AppMain.getInstance().setView(this); // view ��ü�� appmain�� set 
		
		AppMain app = AppMain.getInstance();
		MovieDAO md = new MovieDAO(); // MovieDAO ��ü ����
		datas = new ArrayList<Movie>();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("��ȭ �м� ���α׷�");
		
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
		
		JLabel title = new JLabel("��ȭ�� �ñ���~");
		title.setFont(new Font("�ü�", 0, 35));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel current = new JLabel("");
		current.setFont(new Font("��������ڵ�", 0, 23));
		current.setHorizontalAlignment(JLabel.CENTER);
		
		explain.add(title, BorderLayout.NORTH);
		explain.add(current, BorderLayout.SOUTH);
		
		JLabel lMonth = new JLabel("���� �� ����");
		JLabel lNation = new JLabel("���� ���� ����");
		JLabel lRating = new JLabel("���� ���� ����");
		
		JButton initButton = new JButton("�ʱ�ȭ");
		initButton.setBackground(Color.white);
		initButton.setFont(new Font("HY�ٴ�M", 0, 17));
		
		lMonth.setHorizontalAlignment(JLabel.RIGHT);
		lNation.setHorizontalAlignment(JLabel.RIGHT);
		lRating.setHorizontalAlignment(JLabel.RIGHT);
	
		String[] strMonth = {"��ü", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] strNation = {"��ü", "�ѱ�", "�ܱ�"};
		String[] strRating = {"��ü", "��ü������", "12���̻������", "15���̻������", "û�ҳ�����Ұ�"};
		
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
		
		btn[0] = new JButton("���� ��");
		btn[1] = new JButton("�����");
		btn[2] = new JButton("ȿ����");
		btn[3] = new JButton("������");
		btn[4] = new JButton("�帣��");
		btn[5] = new JButton("�ܾ");
		btn[6] = new JButton("������");
		btn[7] = new JButton("?");
		
		for(int i=0; i<8; i++)
			btn[i].setFont(new Font("HY����M", 0, 27));
		
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
		
		JDialog inputDr = new JDialog(this, "���� �̸� �Է�");
		inputDr.setLayout(new FlowLayout());
		inputDr.setSize(280,80);
		inputDr.setLocation(200,200);
		inputDr.setVisible(false); // ó������ ������ ����
		
		JTextField inputTextDr = new JTextField(12);
		JButton inputDrButton = new JButton("�Է�");
		JButton closeDrButton = new JButton("���");
		
		inputDr.add(inputTextDr);
		inputDr.add(inputDrButton);
		inputDr.add(closeDrButton);
		
		JDialog inputWord = new JDialog(this, "�ܾ� �Է�");
		inputWord.setLayout(new FlowLayout());
		inputWord.setSize(280,80);
		inputWord.setLocation(200,200);
		inputWord.setVisible(false); // ó������ ������ ����
		
		JTextField inputTextWord = new JTextField(12);
		JButton inputWordButton = new JButton("�Է�");
		JButton closeWordButton = new JButton("���");
		
		inputWord.add(inputTextWord);
		inputWord.add(inputWordButton);
		inputWord.add(closeWordButton);
		
		
		DecimalFormat form = new DecimalFormat("#,###");
		
		btn[0].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(monthChoose, nationChoose, ratingChoose) + " ���� �� �м�");
				
				datas = md.sortPNum(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("��ȭ ����");
				resultStandard[1].setText("���� ��¥");
				resultStandard[2].setText("������");
				resultStandard[3].setText("���� �ο�");
				
				for(Movie m : datas) {
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getOpendate() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getNumPeople()) + "��\n");
					
				}
			}			
		});
		
		btn[1].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(monthChoose, nationChoose, ratingChoose) + " ����� �м�");
				
				datas = md.sortIncome(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());

				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("��ȭ ����");
				resultStandard[1].setText("����");
				resultStandard[2].setText("������");
				resultStandard[3].setText("�����");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getDrname() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getIncome()) + "��\n");
					
				}
			}			
		});
		
		btn[2].addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(monthChoose, nationChoose, ratingChoose) + " ȿ���� �м�");

				datas = md.sortEffeciency(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("��ȭ ����");
				resultStandard[1].setText("�帣");
				resultStandard[2].setText("������");
				resultStandard[3].setText("��ũ���� ����");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getGenre() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getIncome() / m.getNumScreen()) + "��\n");
					
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
				
				current.setText("�帣�� �м�");
				datas = md.sortTheme();
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("�帣");
				resultStandard[1].setText("��ȭ ����");
				resultStandard[2].setText("��� �����");
				resultStandard[3].setText("");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getGenre() + "\n");
					resultText[1].append(m.getNumScreen() + "��\n");
					resultText[2].append(form.format(m.getFResult()) + "��\n");
					
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
				
				current.setText("������ �м�");
				
				datas = md.sortSuccess(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("��ȭ ����");
				resultStandard[1].setText("�����");
				resultStandard[2].setText("��ũ�� ��");
				resultStandard[3].setText("������ ��");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(form.format(m.getIncome()) + "��\n");
					resultText[2].append(form.format(m.getNumScreen()) + "��\n");
					resultText[3].append(form.format(m.getNumPeople()) + "��\n");
					
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
				current.setText("������ �м�");
				
				datas = md.sortDirector(inputTextDr.getText().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("������");
				resultStandard[1].setText("��ȭ ����");
				resultStandard[2].setText("��ũ�� ��");
				resultStandard[3].setText("�����");
				
				// System.out.println(datas.get(0).getDrname());
				

				for(Movie m : datas) {
					
					resultText[0].append(m.getDrname() + "\n");
					resultText[1].append(m.getMvname() + "\n");
					resultText[2].append(form.format(m.getNumScreen()) + "��\n");
					resultText[3].append(form.format(m.getIncome()) + "��\n");
						
					
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
				current.setText("�ܾ �м�");
				
				datas = md.sortWord(inputTextWord.getText().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("��ȭ ����");
				resultStandard[1].setText("������");
				resultStandard[2].setText("�帣");
				resultStandard[3].setText("���� ���");

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
		
		String selectedMonth = (monthChoose.getSelectedItem().toString().equals("��ü"))?
				"" : (monthChoose.getSelectedItem().toString() +"��");
		
		String selectedNation = (nationChoose.getSelectedItem().toString().equals("��ü"))?
				"" : (" " + nationChoose.getSelectedItem().toString());
		
		String selectedRating = (ratingChoose.getSelectedItem().toString().equals("��ü"))?
				"" : (" " + ratingChoose.getSelectedItem().toString());
		
		// current.setText(selectedMonth + selectedNation + selectedRating);
		
		return selectedMonth + selectedNation + selectedRating;
		
	} // chooseStandard

	
	public static void main(String[] args) {
		
		new View();
	}

}
