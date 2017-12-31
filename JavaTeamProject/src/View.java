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
		setTitle("���� ���α׷�");
		
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
		result.setPreferredSize(new Dimension(800,340));
		resultDetail.setPreferredSize(new Dimension(780,200));
		
		this.add(explain);
		this.add(standard);
		this.add(func1);
		this.add(func2);
		this.add(showResult);
		this.add(result);
		this.add(resultDetail);
		
		
		JLabel title = new JLabel("��ȭ �м�");
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
		
		JButton[] btn = new JButton[6];
		
		btn[0] = new JButton("���� ��");
		btn[1] = new JButton("�����");
		btn[2] = new JButton("ȿ����");
		btn[3] = new JButton("������");
		btn[4] = new JButton("������");
		btn[5] = new JButton("�帣��");
		
		for(int i=0; i<6; i++)
			btn[i].setFont(new Font("HY����M", 0, 30));
		
		func1.add(btn[0]);
		func1.add(btn[1]);
		func1.add(btn[2]);
		func2.add(btn[3]);
		func2.add(btn[4]);
		func2.add(btn[5]);
		
		JTextArea[] resultText = new JTextArea[4];
		
		for(int i=0; i<4; i++) {
			
			resultText[i] = new JTextArea();
			resultText[i].setPreferredSize(new Dimension(200,340));
			resultText[i].setEditable(false);
			resultText[i].setFont(new Font("", 2, 12));
			result.add(resultText[i]);
			
		} // for		
		
		JLabel[] resultStandard = new JLabel[4];
		
		for(int i=0; i<4; i++) {
			
			resultStandard[i] = new JLabel("");
			resultStandard[i].setFont(new Font("", 1, 18));
			showResult.add(resultStandard[i]);
			
		} // for
		
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
				
				current.setText("������ �м�");
				
				datas = md.sortDirector(monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				System.out.println(ratingChoose.getSelectedItem().toString());
				
				resultStandard[0].setText("������");
				resultStandard[1].setText("��ũ�� ��");
				resultStandard[2].setText("�����");
				resultStandard[3].setText("");
				
				for(Movie m : datas) {
					
					resultText[0].append(m.getDrname() + "\n");
					resultText[1].append(form.format(m.getNumScreen()) + "��\n");
					resultText[2].append(form.format(m.getIncome()) + "��\n");
					
				}
			}			
		});
		
		btn[4].addActionListener(new ActionListener() {
			
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
		
		btn[5].addActionListener(new ActionListener() {
			
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
		
		initButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0; i<4; i++) {
					
					resultText[i].setText("");
					resultStandard[i].setText("");
					
				} // for
				
				current.setText("");
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
