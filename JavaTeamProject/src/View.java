import java.awt.*;
import java.awt.event.*;
import java.text.*;
import java.util.*;
import javax.swing.*;

public class View extends JFrame{
	
	ArrayList<Movie> datas; // DB���� �޾ƿ� ��ȭ ����� ���� �� ���� 
	MovieDAO md; // MovieDAO ��ü ����
	
	public View() { // ����
		
		AppMain.getInstance().setView(this); // view ��ü�� AppMain�� set 
		
		AppMain app = AppMain.getInstance(); // �̱��� ���� ���
		MovieDAO md = new MovieDAO(); // MovieDAO ��ü ��
		datas = new ArrayList<Movie>(); // MovieŬ������ ArrayList ��ü ��
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("��ȭ �м� ��α׷�");
		
		this.setLayout(new FlowLayout()); // ��ü ���̾ƿ� ���
		
		JPanel explain = new JPanel(); // �޺� �ڽ��� ���� � ����� �����ߴ��� ������(�� ��ɵ�� ������ �гη� ����)
		JPanel standard = new JPanel(); // ����� ������ �޺� �ڽ��� ������
		JPanel func1 = new JPanel(); // ��� 1
		JPanel func2 = new JPanel(); // ��� 2
		JPanel showResult = new JPanel(); // �м� ��ǥ�� �����ֱ� ��� �г�
		JPanel result = new JPanel(); // ��� �����ֱ� ��� �г� 
		
		explain.setLayout(new BorderLayout()); // ������ �гε��� ���̾ƿ� ���
		standard.setLayout(new GridLayout(1, 6, 30, 0));
		func1.setLayout(new GridLayout(1, 3, 100, 0));
		func2.setLayout(new GridLayout(1, 3, 100, 0));
		showResult.setLayout(new GridLayout(1, 4));
		result.setLayout(new GridLayout(1, 4));
		
		explain.setPreferredSize(new Dimension(800,70)); // ������ �гε��� ũ�� ���
		standard.setPreferredSize(new Dimension(800,30));
		func1.setPreferredSize(new Dimension(800,70));
		func2.setPreferredSize(new Dimension(800,70));
		showResult.setPreferredSize(new Dimension(800,20));
		result.setPreferredSize(new Dimension(800,460));
		
		this.add(explain); // ������ ��ε�� ȭ�鿡 �߰�
		this.add(standard);
		this.add(func1);
		this.add(func2);
		this.add(showResult);
		this.add(result);
		
		JLabel title = new JLabel("��ȭ ������ �м�"); // ���� ����� ��α׷��� ���� ������
		title.setFont(new Font("������������ ExtraBold", 0, 37)); // ��Ʈ ���
		title.setHorizontalAlignment(JLabel.CENTER); // �߾� ���
		
		JLabel current = new JLabel(""); // ���� ����� ����� ������(��, ����, ���)
		current.setFont(new Font("�������", 0, 23)); // ��Ʈ ���
		current.setHorizontalAlignment(JLabel.CENTER); // �߾� ���
		
		explain.add(title, BorderLayout.NORTH); // �гο� add����
		explain.add(current, BorderLayout.SOUTH);
		
		JLabel lYear = new JLabel("���� �⵵"); // ���� ���� ������ �ִ��� �����ֱ� ���
		JLabel lMonth = new JLabel("���� ��");
		JLabel lNation = new JLabel("��� ����");
		JLabel lRating = new JLabel("��� ����");
		
		lYear.setFont(new Font("������������ Bold", 0, 18));
		lMonth.setFont(new Font("������������ Bold", 0, 18));
		lNation.setFont(new Font("������������ Bold", 0, 18));
		lRating.setFont(new Font("������������ Bold", 0, 18));
		
		lYear.setHorizontalAlignment(JLabel.RIGHT); // �󺧵� ���
		lMonth.setHorizontalAlignment(JLabel.RIGHT);
		lNation.setHorizontalAlignment(JLabel.RIGHT);
		lRating.setHorizontalAlignment(JLabel.RIGHT);
	
		String[] strYear = {"��ü", "2016", "2017"}; // �޺� �ڽ� ���ڿ��� ����
		String[] strMonth = {"��ü", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] strNation = {"��ü", "�ѱ�", "�ܱ�"};
		String[] strRating = {"��ü", "��ü���", "12���̻���", "15���̻���", "û�ҳ���Ұ�"};
		
		JComboBox yearChoose = new JComboBox(strYear); // �޺��ڽ� ��
		JComboBox monthChoose = new JComboBox(strMonth);
		JComboBox nationChoose = new JComboBox(strNation);
		JComboBox ratingChoose = new JComboBox(strRating);
		
		yearChoose.setFont(new Font("������������ Regular", 0, 15));
		monthChoose.setFont(new Font("������������ Regular", 0, 15));
		nationChoose.setFont(new Font("������������ Regular", 0, 15));
		ratingChoose.setFont(new Font("������������ Regular", 0, 15));
	
		standard.add(lYear); // ����� �����ϱ� ��� ��ҵ�� �гο� �߰�����
		standard.add(yearChoose);
		standard.add(lMonth);
		standard.add(monthChoose);
		standard.add(lNation);
		standard.add(nationChoose);
		standard.add(lRating);
		standard.add(ratingChoose);
		
		JButton[] btn = new JButton[8]; // ������ ���� ��� ��ư ����
		
		btn[0] = new JButton("���"); // ������ ��ư ��
		btn[1] = new JButton("�����");
		btn[2] = new JButton("ȿ���");
		btn[3] = new JButton("������");
		btn[4] = new JButton("�帣��");
		btn[5] = new JButton("�ܾ");
		btn[6] = new JButton("������");
		btn[7] = new JButton("�ʱ�ȭ");
		
		for(int i=0; i<8; i++) { // ��ư���� �Ӽ� ���
			
			btn[i].setForeground(Color.BLACK);
			btn[i].setFont(new Font("�����ٸ���", 0, 27));
			
			btn[i].setBorderPainted(false);
			btn[i].setContentAreaFilled(false);
			btn[i].setFocusPainted(false);
			
		} // for
		
		btn[7].setForeground(Color.WHITE); // ���� ��ư� �ʱ�ȭ ��ư�̹Ƿ� ��� ���
		
		func1.add(btn[0]); // ������ ��ư��� �гο� �߰�����
		func1.add(btn[1]);
		func1.add(btn[2]);
		func1.add(btn[3]);
		
		func2.add(btn[4]);
		func2.add(btn[5]);
		func2.add(btn[6]);
		func2.add(btn[7]);
		
		JTextArea[] resultText = new JTextArea[4]; // �м� ��� �����ֱ� ��� TextArea ����
		
		for(int i=0; i<4; i++) { // ��, ũ��� �Ӽ��� ��Ʈ ���, �߰�
			
			resultText[i] = new JTextArea();
			resultText[i].setPreferredSize(new Dimension(200,340));
			resultText[i].setEditable(false);
			resultText[i].setFont(new Font("", 0, 12));
			result.add(resultText[i]);
			
		} // for
		
		JLabel[] resultStandard = new JLabel[4]; // �м� ��ǥ�� �����ֱ� ��� �󺧵� ����
		
		for(int i=0; i<4; i++) { // �󺧵� �� �� ��Ʈ ����� �гο� �߰�
			
			resultStandard[i] = new JLabel("");
			resultStandard[i].setFont(new Font("�������", 1, 18));
			showResult.add(resultStandard[i]);
			
		} // for
		
		JDialog inputDr = new JDialog(this, "���� �̸� �Է�"); // ���� �̸� �Է�� ��� ���̾�α� â ���� �� ��
		inputDr.setLayout(new FlowLayout()); // ���̾ƿ� ���
		inputDr.setSize(280,80); // ũ�� ���
		inputDr.setLocation(200,200); // �ġ ���
		inputDr.setVisible(false); // ó����� ������ ���
		
		JTextField inputTextDr = new JTextField(12); // �Է� â ���� �� ��
		JButton inputDrButton = new JButton("�Է�"); // ������ ��ư ���� �� ��
		JButton closeDrButton = new JButton("���");
		
		inputDr.add(inputTextDr); // ���̾�α� â�� �߰�
		inputDr.add(inputDrButton);
		inputDr.add(closeDrButton);
		
		JDialog inputWord = new JDialog(this, "�ܾ� �Է�"); // �ܾ� �Է�� ��� ���̾�α� ���� �� ��
		inputWord.setLayout(new FlowLayout()); // ���̾ƿ� ���
		inputWord.setSize(280,80); // ũ�� ���
		inputWord.setLocation(200,200); // �ġ ���
		inputWord.setVisible(false); // ó����� ������ ���
		
		JTextField inputTextWord = new JTextField(12); // �Է� â ���� �� ��
		JButton inputWordButton = new JButton("�Է�"); // ������ ��ư ���� �� ��
		JButton closeWordButton = new JButton("���");
		
		inputWord.add(inputTextWord); // ���̾�α� â�� �߰�
		inputWord.add(inputWordButton);
		inputWord.add(closeWordButton);
		
		
		DecimalFormat form = new DecimalFormat("#,###"); // õ�� �ڸ����� ��ǥ�� ��� ��� ��ü ���� �� ��
		
		btn[0].addActionListener(new ActionListener() { // �� �� ���� �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(yearChoose, monthChoose, nationChoose, ratingChoose) + " �� �� �м�"); // �޺� �ڽ��� ������ ���� ����� ǥ��
				datas = md.sortPNum(yearChoose.getSelectedItem().toString(), monthChoose.getSelectedItem().toString(), // �м��� ��� �޾Ƽ� ����
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++) // �м� ��� �����ֱ� ��� TextArea�� �ʱ�ȭ
					resultText[i].setText("");
				
				resultStandard[0].setText("��ȭ ���"); // �м� ��ǥ ���
				resultStandard[1].setText("���� ��¥");
				resultStandard[2].setText("���");
				resultStandard[3].setText("��� �ο�");
				
				for(Movie m : datas) { // �м� ��� ǥ��
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getOpendate() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getNumPeople()) + "��\n");
				}
			}			
		});
		
		btn[1].addActionListener(new ActionListener() { // ����� ���� �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(yearChoose, monthChoose, nationChoose, ratingChoose) + " ����� �м�");
				datas = md.sortIncome(yearChoose.getSelectedItem().toString(), monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());

				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				resultStandard[0].setText("��ȭ ���");
				resultStandard[1].setText("����");
				resultStandard[2].setText("���");
				resultStandard[3].setText("�����");
				
				for(Movie m : datas) {
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getDrname() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getIncome()) + "��\n");
				}
			}			
		});
		
		btn[2].addActionListener(new ActionListener() { // ȿ���(����� / ��ũ�� ��) �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText(chooseStandard(yearChoose, monthChoose, nationChoose, ratingChoose) + " ȿ��� �м�");
				datas = md.sortEffeciency(yearChoose.getSelectedItem().toString(), monthChoose.getSelectedItem().toString(), 
									nationChoose.getSelectedItem().toString(), ratingChoose.getSelectedItem().toString());
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				resultStandard[0].setText("��ȭ ���");
				resultStandard[1].setText("�帣");
				resultStandard[2].setText("���");
				resultStandard[3].setText("��ũ���� ����");
				
				for(Movie m : datas) {
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getGenre() + "\n");
					resultText[2].append(m.getRating() + "\n");
					resultText[3].append(form.format(m.getIncome() / m.getNumScreen()) + "��\n");
				}
			}			
		});
		
		btn[3].addActionListener(new ActionListener() { // ������ �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(true); // ���� �̸� �Է�� ��� â� ���̰� ��
				
			}			
		});
		
		btn[4].addActionListener(new ActionListener() { // �帣�� �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText("�帣�� �м�");
				datas = md.sortTheme(); // �帣���� �м��� ��� ����(�޺� �ڽ� �̻��)
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
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
		
		btn[5].addActionListener(new ActionListener() { // �ܾ �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(true); // �ܾ� �Է�� ��� â� ���̰� ��
				
			}			
		});
		
		btn[6].addActionListener(new ActionListener() { // ������ �м�
			
			public void actionPerformed(ActionEvent arg0) {
				
				current.setText("������ �м�");
				
				datas = md.sortSuccess();
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				resultStandard[0].setText("��ȭ ���");
				resultStandard[1].setText("�����");
				resultStandard[2].setText("��ũ�� ��");
				resultStandard[3].setText("��� ��");
				
				for(Movie m : datas) {
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(form.format(m.getIncome()) + "��\n");
					resultText[2].append(form.format(m.getNumScreen()) + "��\n");
					resultText[3].append(form.format(m.getNumPeople()) + "��\n");
				}
			}			
		});
		
		btn[7].addActionListener(new ActionListener() { // �ʱ�ȭ
			
			public void actionPerformed(ActionEvent arg0) {
				
				for(int i=0; i<4; i++) { // ������ TextArea�� Label� �ʱ�ȭ 
					
					resultText[i].setText("");
					resultStandard[i].setText("");
					
				} // for
				
				current.setText("");
			}
		});
		
		inputDrButton.addActionListener(new ActionListener() { // ���� �̸� �Է� â���� �Է� ���� ��
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(false); // �Է� â� ������ �ʰ� ��
				current.setText("������ �м�"); // �м� ���� ����
				
				datas = md.sortDirector(inputTextDr.getText().toString()); // �м� ��� �޾Ƽ� ����
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				resultStandard[0].setText("������"); // �м� ��ǥ ���
				resultStandard[1].setText("��ȭ ���");
				resultStandard[2].setText("��ũ�� ��");
				resultStandard[3].setText("�����");				

				for(Movie m : datas) { // �м� ��� ������
					resultText[0].append(m.getDrname() + "\n");
					resultText[1].append(m.getMvname() + "\n");
					resultText[2].append(form.format(m.getNumScreen()) + "��\n");
					resultText[3].append(form.format(m.getIncome()) + "��\n");
				}
				
				inputTextDr.setText("");
			}		
		});
		
		closeDrButton.addActionListener(new ActionListener() { // ���� �̸� �Է� â���� ��� ���� ��
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputDr.setVisible(false); // �Է� â� ������ �ʰ� ��
				inputTextDr.setText(""); // �ؽ�Ʈ â ���
				inputDr.setSize(280,80); // ũ�� �缳�
				inputDr.setLocation(200,200); // �ġ �缳�
				
			}			
		});
		
		inputWordButton.addActionListener(new ActionListener() { // �ܾ� �Է� â���� �Է� ���� ��
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(false); // â� ������ �ʰ� ��
				current.setText("�ܾ �м�");
				
				datas = md.sortWord(inputTextWord.getText().toString()); // �м� ��� ����
				
				for(int i=0; i<4; i++)
					resultText[i].setText("");
				
				resultStandard[0].setText("��ȭ ���");
				resultStandard[1].setText("������");
				resultStandard[2].setText("�帣");
				resultStandard[3].setText("��� ���");

				for(Movie m : datas) {
					resultText[0].append(m.getMvname() + "\n");
					resultText[1].append(m.getOpendate() + "\n");
					resultText[2].append(m.getGenre() + "\n");
					resultText[3].append(m.getRating() + "\n");
				}
				
				inputTextWord.setText("");
			}			
		});
		
		closeWordButton.addActionListener(new ActionListener() { // �ܾ� �м� â���� ��� Ŭ�� ��
			
			public void actionPerformed(ActionEvent arg0) {
				
				inputWord.setVisible(false);
				inputTextWord.setText("");
				inputWord.setSize(280,80);
				inputWord.setLocation(200,200);
				
			}			
		});
			
		setSize(830, 800); // GUI ũ�� ���
		setVisible(true);
		
	} // view
	
	public String chooseStandard(JComboBox yearChoose, JComboBox monthChoose, JComboBox nationChoose, JComboBox ratingChoose) { // �޺� �ڽ����� ������ ���鿡 ���� ���
		
		String selectedYear = (yearChoose.getSelectedItem().toString().equals("��ü"))? // ��ü�� �����ϸ� ��ȯ X
				"" : (yearChoose.getSelectedItem().toString() +"��"); // �� �̿ܿ� �⵵�� �����ϸ� ���ڿ�� ���� �� ��ȯ
		
		String selectedMonth = (monthChoose.getSelectedItem().toString().equals("��ü"))? // ��ü�� �����ϸ� ��ȯ X
				"" : (" " + monthChoose.getSelectedItem().toString() +"��"); // �� �̿ܿ� ��� �����ϸ� ���ڿ�� ���� �� ��ȯ
		
		String selectedNation = (nationChoose.getSelectedItem().toString().equals("��ü"))? // ��ü ���ý� ��ȯ X
				"" : (" " + nationChoose.getSelectedItem().toString()); // �� �̿ܿ� ������ ���� ��ȯ
		
		String selectedRating = (ratingChoose.getSelectedItem().toString().equals("��ü"))?
				"" : (" " + ratingChoose.getSelectedItem().toString()); // ������ ��� ��ȯ
		
		return selectedYear + selectedMonth + selectedNation + selectedRating; // ���ڿ� ��� �� ��ȯ
		
	} // chooseStandard

	
	public static void main(String[] args) {
		
		new View();
	}

}
