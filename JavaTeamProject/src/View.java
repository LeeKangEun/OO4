import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class View extends JFrame{
	
	public View() {
		
		AppMain.getInstance().setView(this);;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("���� ���α׷�");
		
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
		
		
		JLabel title = new JLabel("��ȭ �м�");
		title.setFont(new Font("�ü�", 0, 30));
		title.setHorizontalAlignment(JLabel.CENTER);
		
		JLabel current = new JLabel("");
		current.setFont(new Font("�ü�", 0, 20));
		current.setHorizontalAlignment(JLabel.CENTER);
		
		explain.add(title, BorderLayout.NORTH);
		explain.add(current, BorderLayout.SOUTH);
		

		JLabel lMonth = new JLabel("���� �� ����");
		JLabel lNation = new JLabel("���� ���� ����");
		JLabel lRating = new JLabel("���� ���� ����");
		
		lMonth.setHorizontalAlignment(JLabel.RIGHT);
		lNation.setHorizontalAlignment(JLabel.RIGHT);
		lRating.setHorizontalAlignment(JLabel.RIGHT);
	
		String[] strMonth = {"��ü", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
		String[] strNation = {"��ü", "�ѱ�", "�ܱ�"};
		String[] strRating = {"��ü", "��ü ������", "12�� �̻� ������", "15�� �̻� ������", "û�ҳ� ���� �Ұ�"};
		
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
		
		btn[0] = new JButton("���� ��");
		btn[1] = new JButton("�����");
		btn[2] = new JButton("ȿ����");
		btn[3] = new JButton("������");
		btn[4] = new JButton("������");
		btn[5] = new JButton("�帣��");
		
		for(int i=0; i<6; i++)
			btn[i].setFont(new Font("����", 0, 30));
		
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
				
				String selectedMonth = (monthChoose.getSelectedItem().toString().equals("��ü"))?
						"" : (monthChoose.getSelectedItem().toString() +"��");
				String selectedNation = (nationChoose.getSelectedItem().toString().equals("��ü"))?
						"" : (" " + nationChoose.getSelectedItem().toString());
				String selectedRating = (ratingChoose.getSelectedItem().toString().equals("��ü"))?
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
