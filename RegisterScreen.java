package processes_tool_v3;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Formatter;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import petr_hausdor.login_list_0_1.Login_List;
import petr_hausdor.registered_data_0_1.Registered_data;

public class RegisterScreen {
	
	String [][] userList;		//two dimensional field for list of workers
	JLabel lExist;
	JTextField tfUserid;
	JTextField tfFirstname;		
	JTextField tfSecondname;	
	JPasswordField pfPassword;	
	JButton bRegister;			
	JDialog dialog;

	static boolean finished;	//for loading dialog
	
	RegisterScreen()  {
		finished=false;
		Thread thread = new Thread(new Runnable() {	//Waiting dialog
			@Override
			public void run() {
				new WaitingDialog();
			}
		});
		thread.start();

		//start Talend Job
		Login_List usersListTalend = new Login_List();		
		usersListTalend.runJob(new String[] {});;

		//Array for list of workers
		ArrayList<String[]> array= new ArrayList<String[]>();
		for (int i = 0;i < usersListTalend.globalBuffer.size(); i++) {
			String[] pole = usersListTalend.globalBuffer.get(i);
			array.add(pole);
		}
		//Two dimensional fields to transform the array
		userList = new String [array.size()][5]; //userid, first_name, second_name, password, tool
		userList = array.toArray(userList);	
		
		//Check, that any data was return
		if(usersListTalend.globalBuffer.size()==0) {
			finished=true;
			JOptionPane.showMessageDialog(null, "It is not possible to join to a database. \r\n"
							+ "Check your NET/VPN connection. Alternatively inform the administrator");
			Thread.currentThread().interrupt();
		}
		if (!Thread.currentThread().isInterrupted()) {
			//Main dialog window
			dialog = new JDialog();
			dialog.setTitle("Register screen");
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setSize(500,700);
			dialog.setLayout(null);
			dialog.setResizable(false);
			dialog.setLocationRelativeTo(null);
			dialog.getContentPane().setBackground(Color.WHITE);
			dialog.addWindowListener(new WindowListener() {
	
				public void windowOpened(WindowEvent e) {}
				public void windowClosing(WindowEvent e) {			}
				public void windowClosed(WindowEvent e) {
					if (LoginScreen.frame!=null) LoginScreen.frame.setVisible(true);
				}
				public void windowIconified(WindowEvent e) {}
				public void windowDeiconified(WindowEvent e) {}
				public void windowActivated(WindowEvent e) {}
				public void windowDeactivated(WindowEvent e) {}	
			});
			
			//location a dimension of Labels
			int xlabel= 50;//x lokace label
			int ylabel=170;//x lokace label
			int wlabel=140;//šířka label
			int hlabel=30;//výška label
			int range = 70;
			
			JLabel lTitle = new JLabel ("Register");
			lTitle.setFont(new Font("Goudy Old Style",Font.BOLD,45));
			lTitle.setForeground(Color.BLUE);
			lTitle.setBounds(175, 50, 150, 50);	
			
			JLabel lUserid = new JLabel("Fill your user ID:");
			lUserid.setFont(new Font("Georgia",Font.PLAIN,18));
			lUserid.setBounds(xlabel, ylabel, wlabel, hlabel);	
			
			JLabel lFirstname = new JLabel("First name:");
			lFirstname.setFont(new Font("Georgia",Font.PLAIN,18));
			lFirstname.setBounds(xlabel, ylabel+range, wlabel, hlabel);
			
			JLabel lSesondname = new JLabel("Second name:");
			lSesondname.setFont(new Font("Georgia",Font.PLAIN,18));
			lSesondname.setBounds(xlabel, ylabel+range*2, wlabel, hlabel);
			
			JLabel lPassword = new JLabel("Your password:");
			lPassword.setFont(new Font("Georgia",Font.PLAIN,18));
			lPassword.setBounds(xlabel, ylabel+range*3, wlabel, hlabel);
				
			//location a dimension of TextFields	
			int ttext= 210;//x lokace label
			int ytext=170;//x lokace label
			int wtext=200;//šířka label
			int htext=30;//výška label
			
			tfUserid = new JTextField();
			tfUserid.setFont(new Font("Georgia",Font.PLAIN,18));
			tfUserid.setBounds(ttext, ytext, wtext, htext);
			tfUserid.addKeyListener(new KeyListener() {
				public void keyTyped(KeyEvent e) {}
				public void keyPressed(KeyEvent e) {}
				@Override
				public void keyReleased(KeyEvent e) {
	
					for (int i=0; i<userList.length;i++) {
						//set default format
						if (!tfFirstname.getText().isEmpty()) tfFirstname.setText("");
						if (!tfSecondname.getText().isEmpty()) tfSecondname.setText("");
						if (pfPassword.isEnabled())pfPassword.setEnabled(false);
						if (bRegister.isEnabled())bRegister.setEnabled(false);	
						if (lExist.isVisible()==true) lExist.setVisible(false);
						
						tfFirstname.setBackground(new Color (255,171,171));			
						tfSecondname.setBackground(new Color (255,171,171));
						pfPassword.setBackground(new Color (255,171,171));
						
						//Check if the worker (Userid) was found in database. Fields are enabled if true
						if (userList[i][0].equals(tfUserid.getText()) && userList[i][4]==null) {
							//Fill the first/second name and enable password field+Button
							tfFirstname.setText(userList[i][1]);
							tfSecondname.setText(userList[i][2]);
							pfPassword.setEnabled(true);
							bRegister.setEnabled(true);
							
							tfFirstname.setBackground(null);
							tfSecondname.setBackground(null);
							pfPassword.setBackground(null);
							break;
						}
						if (userList[i][0].equals(tfUserid.getText()) && userList[i][4]!=null) {
							lExist.setVisible(true);
							break;
						}
					}	
				}
			});
									
			tfFirstname = new JTextField();
			tfFirstname.setFont(new Font("Georgia",Font.PLAIN,18));
			tfFirstname.setBounds(ttext, ytext+range, wtext, htext);
			tfFirstname.setBackground(new Color (255,171,171));
			tfFirstname.setEnabled(false);
			
			tfSecondname = new JTextField();
			tfSecondname.setFont(new Font("Georgia",Font.PLAIN,18));
			tfSecondname.setBounds(ttext, ytext+range*2, wtext, htext);
			tfSecondname.setBackground(new Color (255,171,171));
			tfSecondname.setEnabled(false);
			
			pfPassword = new JPasswordField();
			pfPassword.setFont(new Font("Georgia",Font.PLAIN,18));
			pfPassword.setBounds(ttext, ytext+range*3, wtext, htext);
			pfPassword.setBackground(new Color (255,171,171));
			pfPassword.setEnabled(false);
	
			bRegister = new JButton("Register");
			bRegister.setFont(new Font("Georgia",Font.PLAIN,18));
			
			bRegister.setBounds(175, 500, 150, 40);
			bRegister.setFocusable(false);
			bRegister.setEnabled(false);
			bRegister.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					@SuppressWarnings("deprecation")
					String [] pole = new String [] {tfUserid.getText(),tfSecondname.getText(),tfSecondname.getText(),pfPassword.getText()};
					boolean check = true;
					for (int i = 0; i<pole.length;i++) {
						if (pole[i].isEmpty()) {
							check = false;	//check all fields are fill
							break;
						}
					}
					if (check==false) {
						JOptionPane.showMessageDialog(dialog, "Missing entry data/Fill all text fields", "Error", JOptionPane.ERROR_MESSAGE, null);
					}else {
						finished=false;
						Thread thread2 = new Thread(new Runnable() {//Waiting dialog
							public void run() {
								new WaitingDialog();
							}		
						});
						thread2.start();
						
						//Current date
						Calendar date = Calendar.getInstance();
						Formatter format = new Formatter();
						format.format("%1$td" +"."+"%1$tm" +"."+"%1$tY" +" "+"%1$tH" +":"+ "%1$tM" +":" +"%1$tS",date.getTime());
						
						Registered_data data = new Registered_data();
						String [] context = new String []
													{"--context_param Userid="+pole[0]
													,"--context_param First_name="+pole[1]
													,"--context_param Second_name="+pole[2]
													,"--context_param Password="+pole[3]
													,"--context_param Tool="+"Processes"
													,"--context_param Version="+LoginScreen.version
													,"--context_param Date_="+format.toString()
													};
						format.close();
						data.runJob(context);
						
						finished=true;
						dialog.dispose();
						LoginScreen.frame.setVisible(true);
						
					}
				}
			});
			
			lExist = new JLabel();
			lExist.setText("User is already registred!");
			lExist.setFont(new Font("Georgia",Font.PLAIN,15));
			lExist.setBounds(ttext, ytext+htext, wtext, htext-10);
			lExist.setForeground(Color.red);
			lExist.setVisible(false);
			
			//Add all components	
			dialog.getContentPane().add(lTitle);
			dialog.getContentPane().add(lUserid);
			dialog.getContentPane().add(lFirstname);
			dialog.getContentPane().add(lSesondname);
			dialog.getContentPane().add(lPassword);
			dialog.getContentPane().add(tfUserid);
			dialog.getContentPane().add(tfFirstname);
			dialog.getContentPane().add(tfSecondname);
			dialog.getContentPane().add(pfPassword);
			dialog.getContentPane().add(bRegister);
			dialog.getContentPane().add(lExist);
	
			finished=true;
			if(LoginScreen.frame!=null && LoginScreen.frame.isVisible()==true) {
				LoginScreen.frame.setVisible(false);
			}
			dialog.setVisible(true);
		}
	}
}