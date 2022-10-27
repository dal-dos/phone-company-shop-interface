import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;

public class AS3DalveerDosanjh extends JFrame {

	private JPanel contentPane;
	private JList<Object> lstLeft;
	private JList<Object> lstRight;
	private JButton btnNewButton;
	private JScrollPane scrollPane;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	DefaultListModel<Object> dlmRight = new DefaultListModel<>();//so I can access dlmRight anywhere
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AS3DalveerDosanjh frame = new AS3DalveerDosanjh();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public void setList() {//to set the lstLeft and sort it just incase accdb changes codes
		DefaultListModel<Object> dlmLeft = new DefaultListModel<>();
		ArrayList<String>code=new ArrayList<>();
		String driver = "jdbc:ucanaccess://c:\\temp\\1275AS3W20.accdb";
		try
		{
			Connection conn = DriverManager.getConnection(driver);
			Statement sql = conn.createStatement();
			ResultSet rs = sql.executeQuery("Select * from phoneplans");
			while(rs.next()) {
				code.add(rs.getString(1));
			}
			rs.close();
			Collections.sort(code);
			for(int i = 0;i < code.size();i++) {
				dlmLeft.addElement(code.get(i)); 
			}
			lstLeft.setModel(dlmLeft);
			}
		catch(Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	public void createEvents() {
		setList();
		btnNewButton.addActionListener(new ActionListener() {//show result clicked
			public void actionPerformed(ActionEvent arg0) {
				if(!dlmRight.isEmpty()) {
				double total = 0;
				double subtotal = 0;
				double upTotal = 0;
				String fmt = "%-15s %15s %15s %20s %20s %20s \n";
				System.out.printf(fmt, "Phone Plan","Phone","Upfront Cost","Monthly Amt","Duration","Plan Amt");
				NumberFormat numC = NumberFormat.getCurrencyInstance();
				String driver = "jdbc:ucanaccess://c:\\temp\\1275AS3W20.accdb";
				try
				{
					Connection conn = DriverManager.getConnection(driver);
					Statement sql = conn.createStatement();
					for(int i = 0;i < dlmRight.getSize();i++) {
					String code = dlmRight.elementAt(i).toString();
					ResultSet rs = sql.executeQuery("Select * from phoneplans where code = '" + code + "'"); //to find the months
					while(rs.next()) {
						CellPlans myPlan;
						myPlan = CellPlans.valueOf(code);
						upTotal = upTotal + myPlan.getCost();//get the upfront total
						subtotal =myPlan.getMonths()*Double.parseDouble(rs.getString(2)); //2 is the second col
						System.out.printf(fmt,myPlan.getName(),myPlan.getPhone(),numC.format(myPlan.getCost()),numC.format(Double.parseDouble(rs.getString(2))),myPlan.getMonths()+" months", numC.format(subtotal));
						total =  total + subtotal;
					}
					rs.close();
					}
					System.out.println();
					System.out.printf(fmt, "Total Amt:","",numC.format(upTotal),"","",numC.format(total));
					System.out.println("Total # of mobile plans ordered: "+dlmRight.getSize());//get the #of plans by size
					System.out.println("Program coded by Dalveer Dosanjh");
				}
				catch(Exception ex) {
					System.out.println(ex.getMessage());
				}
				}else {
					JOptionPane.showMessageDialog(null, "Choose a plan");
				}
			}
		});
		lstLeft.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if(dlmRight.size() < 5) {
					if(lstLeft.getSelectedIndex() != -1) { //to switch from left to right and if max is hit
						dlmRight.addElement(lstLeft.getSelectedValue());
						lstRight.setModel(dlmRight);
					}
				}else {
					JOptionPane.showMessageDialog(null, "You can't choose more than 5 plans");
				}
			}
		});
	}
	
	public AS3DalveerDosanjh() {
		setUP();
		createEvents();
	}
	
	public void setUP() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		scrollPane = new JScrollPane();
		
		lblNewLabel = new JLabel("Select your plans");
		
		lstRight = new JList<Object>();
		
		lblNewLabel_1 = new JLabel("Your selected plans");
		
		btnNewButton = new JButton("Show Result");

		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 186, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(lstRight, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1)
						.addComponent(btnNewButton))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap(18, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel)
						.addComponent(btnNewButton))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lstRight, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		
		lstLeft = new JList<Object>();


		lstLeft.setModel(new AbstractListModel<Object>() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(lstLeft);
		contentPane.setLayout(gl_contentPane);
	}
}
