package processes_tool_v3;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

//Main LoginScreen

public class LoginScreen {
	
	final static String version = "1.03";
	static JFrame frame;
	JTextField tfUserid;
	JPasswordField pfPassword;
	
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
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Registration");	//in progress			
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

		frame.setVisible(true);

	}
	

}
