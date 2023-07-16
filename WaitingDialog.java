package processes_tool_v3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class WaitingDialog implements Runnable{
	
	JLabel ltext;
	Thread thread;
	Thread threadcheck;
	JDialog loadingDialog;
	
	public WaitingDialog() {

		//Place the dialog in the middle of a main Screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();		
		double screenWidth = screenSize.getWidth();
		double screenHeight = screenSize.getHeight();
		
		int dialogWidth = 250;
		int dialogHeight = 100;
		
		int xposition= (int) (screenWidth-dialogWidth)/2;	//horizontal middle
		int yposition= (int) (((screenHeight-dialogHeight)/2)*0.65);//vertical 65% up from middle
		
		loadingDialog = new JDialog ();
		loadingDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		loadingDialog.setLayout(null);
		loadingDialog.setSize(dialogWidth,dialogHeight);
		loadingDialog.setResizable(false);
		loadingDialog.getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLUE, 3, false));
		loadingDialog.setLocation(new Point(xposition,yposition));
		loadingDialog.getContentPane().setBackground(Color.white);
		loadingDialog.setUndecorated(true);
		loadingDialog.setAlwaysOnTop(true);
		
		ltext = new JLabel("Loading");
		ltext.setHorizontalAlignment(SwingConstants.LEFT);
		ltext.setFont(new Font("Georgia",Font.BOLD,20));
		ltext.setBounds(70, 0, 125, 100);

		loadingDialog.getContentPane().add(ltext);
	
		//Thread: display dialog and update
		thread = new Thread(this,"LoadingScreen");
		thread.start();
		//next Thread (check to close dialog when finished==true)
		threadcheck = new Thread(this,"LoadingCheck");
		threadcheck.start();
		
		loadingDialog.setVisible(true);
	}
	
	public void run() {
		if (Thread.currentThread().getName().equals("LoadingScreen")) {
			int x = 1;
			while (RegisterScreen.finished==false) {
				try {
					loadingDialog.update(loadingDialog.getGraphics());
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}	
				switch (x) {
					case 1 : 
						ltext.setText("Loading.");
						x++;
						break;
					case 2 : 
						ltext.setText("Loading..");
						x++;
						break;
					case 3 : 
						ltext.setText("Loading...");
						x++;
						break;
					case 4 : 
						ltext.setText("Loading....");
						x=1;
						break;
				}
			}	
		}
		if (Thread.currentThread().getName().equals("LoadingCheck")) {
			while (RegisterScreen.finished==false) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			loadingDialog.dispose();
		}
	}
}
