package processes_tool_v3;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import petr_hausdor.loginscreenusers_0_1.LoginScreenUsers;

//Main LoginScreen

public class LoginScreen {
	
	final static String version = "1.03";
	static JFrame frame;
	JTextField tfUserid;
	JPasswordField pfPassword;
	static String loginUserID;
	
	LoginScreen() {

		frame = new JFrame("Login screen");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(700, 400);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		
		//Icon frame
		URL urlIcon = LoginScreen.class.getResource("/MakroLogo.png");
		ImageIcon iiIcondc = new ImageIcon(urlIcon);
		frame.setIconImage(iiIcondc.getImage());
		
		//Version number
		JLabel lVersion = new JLabel(version);
		lVersion.setBounds(5,5,50,20);
		
			
		//background picture
		JPanel cPanel = (JPanel) frame.getContentPane();
		cPanel.setLayout(new FlowLayout());
		URL urlDcfoto = LoginScreen.class.getResource("/DC_Photo.png");
		ImageIcon iiPhoto = new ImageIcon(urlDcfoto);
		JLabel lPhoto = new JLabel(iiPhoto);
		cPanel.add(lPhoto);
		
		//layered panel
		JLayeredPane panel = frame.getLayeredPane();
		panel.setOpaque(true);
		
		//Labels
		JLabel lProcesses = new JLabel();
		lProcesses.setText("Processes");
		lProcesses.setForeground(Color.black);
		lProcesses.setFont(new Font("ITALIC",Font.BOLD,35));
		lProcesses.setBounds(250, 30, 250,45);
			
		JLabel lUserid = new JLabel("User ID");
		lUserid.setFont(new Font("Georgia",Font.BOLD,20));
		lUserid.setForeground(Color.BLACK);
		lUserid.setBounds(110, 120, 100, 30);		
		
		JLabel lPassword = new JLabel("Password");
		lPassword.setFont(new Font("Georgia",Font.BOLD,20));
		lPassword.setForeground(Color.BLACK);
		lPassword.setBounds(110, 180, 120, 30);
		
		//TextFields
		tfUserid = new JTextField();
		tfUserid.setFont(new Font("Georgia",Font.PLAIN,18));
		tfUserid.setBounds(250, 120, 200, 30);
		
		pfPassword = new JPasswordField();
		pfPassword.setFont(new Font("Georgia",Font.PLAIN,18));
		pfPassword.setBounds(250, 180, 200, 30);
		
		//Buttons
		JButton bLogin = new JButton("Login");
		bLogin.setFont(new Font("Georgia",Font.PLAIN,18));
		bLogin.setBounds(200, 270, 130, 30);
		bLogin.setFocusable(false);
		frame.getRootPane().setDefaultButton(bLogin);
		bLogin.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginScreenUsers usersListTalend = new LoginScreenUsers();		
				usersListTalend.runJob(new String[] {});;

				//Array for list of logged workers
				ArrayList<String[]> array= new ArrayList<String[]>();
				for (int i = 0;i < usersListTalend.globalBuffer.size(); i++) {
					array.add(usersListTalend.globalBuffer.get(i));	
				}
				//Two dimensional fields to transform the array
				String [][] userList = new String [array.size()][6]; // userid,first_name,second_name,password,version ,tool,Date_
				userList = array.toArray(userList);
				boolean checkID =false;
				for(String[] userid:userList ) {
					if (userid[0].equals(tfUserid.getText())) {	//check userid
						checkID=true;
						if(userid[3].equals(pfPassword.getText())) { //check password
							if(userid[4].equals(version)) {
								new TaskScreen();
								frame.dispose();
							}else {
								JOptionPane.showMessageDialog(frame, "Wrong version, download newest version \"" +userid[4] + "\" ASAP!!!", "WrongVersion", JOptionPane.WARNING_MESSAGE, null);
								new TaskScreen();
								frame.dispose();
							}
						}else {
							JOptionPane.showMessageDialog(frame, "Wrong Password", "WrongPassword", JOptionPane.WARNING_MESSAGE, null);
						}
					}	
				}
				if (checkID==false) {	//wasn´t found any equal userID record
					JOptionPane.showMessageDialog(frame, "Wrong User ID", "WrongUserID", JOptionPane.WARNING_MESSAGE, null);
				};		
			}			
		});
		
		JButton bReset = new JButton("Reset");
		bReset.setFont(new Font("Georgia",Font.PLAIN,18));
		bReset.setBounds(340, 270, 130, 30);
		bReset.setFocusable(false);
		bReset.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!tfUserid.getText().isEmpty()) {tfUserid.setText("");}
				if (!pfPassword.getText().isEmpty()) {pfPassword.setText("");}
				tfUserid.requestFocus();
			}
		});
		
		JButton bRegister = new JButton("Register");
		bRegister.setFont(new Font("Georgia",Font.PLAIN,14));
		bRegister.setBounds(580, 10, 100, 20);
		bRegister.setFocusable(false);
		bRegister.addActionListener(new ActionListener() {
			@Override 
			public void actionPerformed(ActionEvent e) {
				new RegisterScreen();
			}
		});
		
		panel.add(lUserid, Integer.valueOf(1));
		panel.add(tfUserid, Integer.valueOf(1));
		panel.add(lPassword, Integer.valueOf(1));
		panel.add(pfPassword, Integer.valueOf(1));
		panel.add(bLogin, Integer.valueOf(1));
		panel.add(bReset, Integer.valueOf(1));
		panel.add(bRegister, Integer.valueOf(1));
		panel.add(lProcesses,Integer.valueOf(1));
		panel.add(lVersion,Integer.valueOf(1));
		

		frame.setVisible(true);

	}
}
