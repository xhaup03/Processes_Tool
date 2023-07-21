package processes_tool_v3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneLayout;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import petr_hausdor.a165006_item_info_dc_0_1.A165006_Item_Info_DC;
import petr_hausdor.abc_2_0.ABC;
import petr_hausdor.abc_fresh_2_0.ABC_Fresh;
import petr_hausdor.dotaz_10_0_1.Dotaz_10;
import petr_hausdor.dotaz_11_0_1.Dotaz_11;
import petr_hausdor.dotaz_12_0_1.Dotaz_12;
import petr_hausdor.dotaz_1_0_1.Dotaz_1;
import petr_hausdor.dotaz_2_0_1.Dotaz_2;
import petr_hausdor.dotaz_3_0_1.Dotaz_3;
import petr_hausdor.dotaz_4_0_1.Dotaz_4;
import petr_hausdor.dotaz_5_0_1.Dotaz_5;
import petr_hausdor.dotaz_6_0_1.Dotaz_6;
import petr_hausdor.dotaz_7_0_1.Dotaz_7;
import petr_hausdor.dotaz_8_0_1.Dotaz_8;
import petr_hausdor.dotaz_9_0_1.Dotaz_9;
import petr_hausdor.occupancy_ambient_0_1.Occupancy_ambient;
import petr_hausdor.occupancy_chilled_0_1.Occupancy_Chilled;
import petr_hausdor.occupancy_frozen_0_1.Occupancy_frozen;
import petr_hausdor.occupancy_meat_0_1.Occupancy_meat;
import petr_hausdor.pla_tool_0_1.PLA_Tool;

public class TaskScreen {
	
	boolean ManualPath=false;
	int ThreadCount=0;	//MAX three Thread active in time
	
	public TaskScreen() {
		JFrame frame = new JFrame("Task Screen");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(700,520);
		frame.setLocationRelativeTo(null);
		frame.setBackground(new Color(0xF2F2F2));
			
		//Icon frame
		URL urlIcon = LoginScreen.class.getResource("/MakroLogo.png");
		ImageIcon iiIcondc = new ImageIcon(urlIcon);
		frame.setIconImage(iiIcondc.getImage());
		
		//Panel for PLA Tool
		JPanel pPLA = new JPanel();
		pPLA.setLayout(new BorderLayout(0,40));
		pPLA.setBackground(new Color(0xF2F2F2));
					
			//content of PLA Panel
			JLabel lPLA = new JLabel("PLA Tool");
			lPLA.setHorizontalAlignment(JLabel.CENTER);
			lPLA.setFont(new Font("Georgia",Font.PLAIN,35));
				
			JPanel pPLACenter = new JPanel();
			pPLACenter.setLayout(new FlowLayout());
			pPLACenter.setBackground(new Color(0xF2F2F2));
						
			JButton bPLA = new JButton ("PLA Tool");
			bPLA.setFont(new Font("Georgia",Font.PLAIN,18));
			bPLA.setFocusable(false);
			bPLA.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadPLA = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bPLA.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								PLA_Tool PLA_Tool = new PLA_Tool();
								PLA_Tool.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									PLA_Tool PLA_Tool = new PLA_Tool();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										PLA_Tool.runJob(context);
								}
							}
							ThreadCount--;
							bPLA.setEnabled(true);
						}
					});
					threadPLA.start();
					}
				});
				pPLACenter.add(bPLA);

		pPLA.add(pPLACenter,BorderLayout.CENTER);
		pPLA.add(lPLA,BorderLayout.NORTH);
	
		
		//Panel for Occupancy
		JPanel pOccupancy = new JPanel();
		pOccupancy.setLayout(new BorderLayout(0,40));
		pOccupancy.setBackground(new Color(0xEEFEEC));
		
			//content of occupancy Panel
			JLabel lOccupancy = new JLabel("Occupancy");
			lOccupancy.setHorizontalAlignment(JLabel.CENTER);
			lOccupancy.setFont(new Font("Georgia",Font.PLAIN,35));
			
			JPanel pOccupancyCenter = new JPanel();
			pOccupancyCenter.setLayout(new FlowLayout());
			pOccupancyCenter.setBackground(new Color(0xEEFEEC));
		
				JButton bOccupancyAMB = new JButton ("Ambient");
				bOccupancyAMB.setFont(new Font("Georgia",Font.PLAIN,18));
				bOccupancyAMB.setFocusable(false);
				bOccupancyAMB.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadoccupancyAMB = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bOccupancyAMB.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Occupancy_ambient Occupancy_AMB = new Occupancy_ambient();
								Occupancy_AMB.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Occupancy_ambient Occupancy_AMB = new Occupancy_ambient();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										Occupancy_AMB.runJob(context);
								}
							}
							ThreadCount--;
							bOccupancyAMB.setEnabled(true);
						}
					});
					threadoccupancyAMB.start();
					}
				});
				pOccupancyCenter.add(bOccupancyAMB);
				
				JButton bOccupancyChilled = new JButton ("Chilled");
				bOccupancyChilled.setFont(new Font("Georgia",Font.PLAIN,18));
				bOccupancyChilled.setFocusable(false);
				bOccupancyChilled.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadoccupancyChilled = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bOccupancyChilled.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Occupancy_Chilled Occupancy_Chilled = new Occupancy_Chilled();
								Occupancy_Chilled.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Occupancy_Chilled Occupancy_Chilled = new Occupancy_Chilled();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										Occupancy_Chilled.runJob(context);
								}
							}
							ThreadCount--;
							bOccupancyChilled.setEnabled(true);
						}
					});
					threadoccupancyChilled.start();
					}
				});
				pOccupancyCenter.add(bOccupancyChilled);
				
				JButton bOccupancyMeat = new JButton ("Meat");
				bOccupancyMeat.setFont(new Font("Georgia",Font.PLAIN,18));
				bOccupancyMeat.setFocusable(false);
				bOccupancyMeat.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadoccupancyMeat = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bOccupancyMeat.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Occupancy_meat Occupancy_Meat = new Occupancy_meat();
								Occupancy_Meat.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Occupancy_meat Occupancy_Meat = new Occupancy_meat();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										Occupancy_Meat.runJob(context);
								}
							}
							ThreadCount--;
							bOccupancyMeat.setEnabled(true);
						}
					});
					threadoccupancyMeat.start();
					}
				});
				pOccupancyCenter.add(bOccupancyMeat);
				
				JButton bOccupancyFrozen = new JButton ("Frozen");
				bOccupancyFrozen.setFont(new Font("Georgia",Font.PLAIN,18));
				bOccupancyFrozen.setFocusable(false);
				bOccupancyFrozen.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadoccupancyFrozen = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bOccupancyFrozen.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Occupancy_frozen Occupancy_Frozen = new Occupancy_frozen();
								Occupancy_Frozen.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Occupancy_frozen Occupancy_Frozen = new Occupancy_frozen();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										Occupancy_Frozen.runJob(context);
								}
							}
							ThreadCount--;
							bOccupancyFrozen.setEnabled(true);
						}
					});
					threadoccupancyFrozen.start();
					}
				});
				pOccupancyCenter.add(bOccupancyFrozen);
					
		pOccupancy.add(lOccupancy,BorderLayout.NORTH);
		pOccupancy.add(pOccupancyCenter,BorderLayout.CENTER);
		
		//Panel for ABC
		JPanel pABC = new JPanel();
		pABC.setLayout(new BorderLayout(0,40));
		pABC.setBackground(new Color(0xFFD1D1));
			
			//content of ABC Panel
			JLabel lABC = new JLabel("ABC analysis");
			lABC.setHorizontalAlignment(JLabel.CENTER);
			lABC.setFont(new Font("Georgia",Font.PLAIN,35));
			
			JPanel pABCCenter = new JPanel();
			pABCCenter.setLayout(new FlowLayout());
			pABCCenter.setBackground(new Color(0xFFD1D1));
				
				JButton bABCAMB = new JButton ("Ambient");
				bABCAMB.setFont(new Font("Georgia",Font.PLAIN,18));
				bABCAMB.setFocusable(false);
				bABCAMB.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadABCAMB = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bABCAMB.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								ABC ABC_AMB = new ABC();
								ABC_AMB.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									ABC ABC_AMB = new ABC();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										ABC_AMB.runJob(context);
								}
							}
							ThreadCount--;
							bABCAMB.setEnabled(true);
						}
					});
					threadABCAMB.start();
					}
				});
				pABCCenter.add(bABCAMB);
				
				JButton bFreshChilled = new JButton ("Chilled");
				bFreshChilled.setFont(new Font("Georgia",Font.PLAIN,18));
				bFreshChilled.setFocusable(false);
				bFreshChilled.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadABCChilled = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bFreshChilled.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								ABC_Fresh ABC_Chilled = new ABC_Fresh();
								ABC_Chilled.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									ABC_Fresh ABC_Chilled = new ABC_Fresh();
										String [] context = new String []
												{"--context_param Path="+path	
												};
										ABC_Chilled.runJob(context);
								}
							}
							ThreadCount--;
							bFreshChilled.setEnabled(true);
						}
					});
					threadABCChilled.start();
					}
				});
				pABCCenter.add(bFreshChilled);
				
				pABC.add(lABC,BorderLayout.NORTH);
				pABC.add(pABCCenter,BorderLayout.CENTER);

		//Panel for MD Tool
		JPanel pMD = new JPanel();
		pMD.setLayout(new BorderLayout(0,40));
		pMD.setBackground(new Color(0xFFF2CC));
					
			//content of ABC Panel
			JLabel lMD = new JLabel("MD Check");
			lMD.setHorizontalAlignment(JLabel.CENTER);
			lMD.setFont(new Font("Georgia",Font.PLAIN,35));
			
			JPanel pMDCenter = new JPanel();
			pMDCenter.setLayout(new BoxLayout(pMDCenter,BoxLayout.Y_AXIS));
			pMDCenter.setBackground(new Color(0xFFF2CC));
		
				JButton bMD1 = new JButton ("Objem SB je větší, než objem BB");
				bMD1.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD1.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD1.setFocusable(false);
				bMD1.setToolTipText("Objem SB je větší, než objem BB. Obsah (balení) SB je větší, než obash BB");
				bMD1.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD01 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD1.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_1 dotaz1 = new Dotaz_1();
								dotaz1.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_1 dotaz1 = new Dotaz_1();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz1.runJob(context);
								}
							}
							ThreadCount--;
							bMD1.setEnabled(true);
						}
					});
					threadMD01.start();
					}
				});
				pMDCenter.add(bMD1);

				JButton bMD2 = new JButton ("Dummy hodnoty u MD");
				bMD2.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD2.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD2.setFocusable(false);
				bMD2.setToolTipText("Dummy hodnoty u MD tj., výška, šířka, délka, BB PAL, BB Layer je null, 0 nebo 1");
				bMD2.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD02 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD2.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_2 dotaz2 = new Dotaz_2();
								dotaz2.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_2 dotaz2 = new Dotaz_2();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz2.runJob(context);
								}
							}
							ThreadCount--;
							bMD2.setEnabled(true);
						}
					});
					threadMD02.start();
					}
				});
				pMDCenter.add(bMD2);
				
				JButton bMD3 = new JButton ("Kv. ve FR>OG24");
				bMD3.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD3.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD3.setFocusable(false);
				bMD3.setToolTipText("Nejmenší kalkulovaná kvantita ve FR (mínus 2) je větší, než hodnota v OG24");
				bMD3.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD03 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD3.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_3 dotaz3 = new Dotaz_3();
								dotaz3.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_3 dotaz3 = new Dotaz_3();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz3.runJob(context);
								}
							}
							ThreadCount--;
							bMD3.setEnabled(true);
						}
					});
					threadMD03.start();
					}
				});
				pMDCenter.add(bMD3);
				
				JButton bMD4 = new JButton ("Reálná vs. MD váha v totu");
				bMD4.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD4.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD4.setFocusable(false);
				bMD4.setToolTipText("Váha reálného totu se neshoduje s váhou totu podle MD");
				bMD4.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD04 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD4.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_4 dotaz4 = new Dotaz_4();
								dotaz4.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_4 dotaz4 = new Dotaz_4();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz4.runJob(context);
								}
							}
							ThreadCount--;
							bMD4.setEnabled(true);
						}
					});
					threadMD04.start();
					}
				});
				pMDCenter.add(bMD4);
				
				JButton bMD5 = new JButton ("Odlišná MD v rodině");
				bMD5.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD5.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD5.setFocusable(false);
				bMD5.setToolTipText("V rámci jedné rodiny jsou odlišná MD");
				bMD5.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD05 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD5.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_5 dotaz5 = new Dotaz_5();
								dotaz5.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_5 dotaz5 = new Dotaz_5();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz5.runJob(context);
								}
							}
							ThreadCount--;
							bMD5.setEnabled(true);
						}
					});
					threadMD05.start();
					}
				});
				pMDCenter.add(bMD5);
				
				JButton bMD6 = new JButton ("Váha totu v miniloadu nad 31kg");
				bMD6.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD6.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD5.setFocusable(false);
				bMD6.setToolTipText("Reálná váha totu v miniloadu je podle MD nad 31kg");
				bMD6.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD06 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD6.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_6 dotaz6 = new Dotaz_6();
								dotaz6.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_6 dotaz6 = new Dotaz_6();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz6.runJob(context);
								}
							}
							ThreadCount--;
							bMD6.setEnabled(true);
						}
					});
					threadMD06.start();
					}
				});
				pMDCenter.add(bMD6);
				
				JButton bMD7 = new JButton ("Využití totu podle MD nad 100%");
				bMD7.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD7.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD7.setFocusable(false);
				bMD7.setToolTipText("Objemové vytížení totuje podle MD větší jak 100%");
				bMD7.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD07 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD7.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_7 dotaz7 = new Dotaz_7();
								dotaz7.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_7 dotaz7 = new Dotaz_7();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz7.runJob(context);
								}
							}
							ThreadCount--;
							bMD7.setEnabled(true);
						}
					});
					threadMD07.start();
					}
				});
				pMDCenter.add(bMD7);
				
				JButton bMD8 = new JButton ("Váha palety nad 1000kg");
				bMD8.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD8.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD8.setFocusable(false);
				bMD8.setToolTipText("Váha plné palety je podle MD více jak 1000kg");
				bMD8.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD08 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD8.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_8 dotaz8 = new Dotaz_8();
								dotaz8.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_8 dotaz8 = new Dotaz_8();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz8.runJob(context);
								}
							}
							ThreadCount--;
							bMD8.setEnabled(true);
						}
					});
					threadMD08.start();
					}
				});
				pMDCenter.add(bMD8);
				
				JButton bMD9 = new JButton ("Více bundelu ve Wamasu");
				bMD9.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD9.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD9.setFocusable(false);
				bMD9.setToolTipText("Více aktivních balení ve Wamasu/chybný Bundle Change");
				bMD9.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD09 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD9.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_9 dotaz9 = new Dotaz_9();
								dotaz9.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_9 dotaz9 = new Dotaz_9();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz9.runJob(context);
								}
							}
							ThreadCount--;
							bMD9.setEnabled(true);
						}
					});
					threadMD09.start();
					}
				});
				pMDCenter.add(bMD9);
				
				JButton bMD10 = new JButton ("WQU per tote odlišný v rodině");
				bMD10.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD10.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD10.setFocusable(false);
				bMD10.setToolTipText("Existuje více hodnotu: \" WQU per tote \" v rámci jedné rodiny");
				bMD10.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD10 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD10.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_10 dotaz10 = new Dotaz_10();
								dotaz10.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_10 dotaz10 = new Dotaz_10();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz10.runJob(context);
								}
							}
							ThreadCount--;
							bMD10.setEnabled(true);
						}
					});
					threadMD10.start();
					}
				});
				pMDCenter.add(bMD10);
				
				JButton bMD11 = new JButton ("Chybné množství na paletě");
				bMD11.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD11.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD11.setFocusable(false);
				bMD11.setToolTipText("Množství na paletě není dělitelné vrstvou nebo paleta je méně jak 7 totů");
				bMD11.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD11 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD11.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_11 dotaz11 = new Dotaz_11();
								dotaz11.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_11 dotaz11 = new Dotaz_11();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz11.runJob(context);
								}
							}
							ThreadCount--;
							bMD11.setEnabled(true);
						}
					});
					threadMD11.start();
					}
				});
				pMDCenter.add(bMD11);
				
				JButton bMD12 = new JButton ("Nereálný objem palety");
				bMD12.setFont(new Font("Georgia",Font.PLAIN,16));
				bMD12.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				bMD12.setFocusable(false);
				bMD12.setToolTipText("Objem expedované palety je větší na 3m3 nebo její váha je větší bež 1050kg");
				bMD12.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadMD12 = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bMD12.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								Dotaz_12 dotaz12 = new Dotaz_12();
								dotaz12.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									Dotaz_12 dotaz12 = new Dotaz_12();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									dotaz12.runJob(context);
								}
							}
							ThreadCount--;
							bMD12.setEnabled(true);
						}
					});
					threadMD12.start();
					}
				});
				pMDCenter.add(bMD12);
				
				JScrollPane spMDCenter = new JScrollPane(pMDCenter,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				spMDCenter.setAlignmentX(JComponent.CENTER_ALIGNMENT);
				
				pMD.add(lMD,BorderLayout.NORTH);
				pMD.add(spMDCenter,BorderLayout.CENTER);
				
		//Panel for PLA Tool
		JPanel pOther = new JPanel();
		pOther.setLayout(new BorderLayout(0,40));
		pOther.setBackground(new Color(0xDCC5ED));
			
			//content of PLA Panel
			JLabel lItem = new JLabel("Others");
			lItem.setHorizontalAlignment(JLabel.CENTER);
			lItem.setFont(new Font("Georgia",Font.PLAIN,35));
			
			JPanel pItemCenter = new JPanel();
			pItemCenter.setLayout(new FlowLayout());
			pItemCenter.setBackground(new Color(0xDCC5ED));
								
				JButton bItem = new JButton ("Item info");
				bItem.setFont(new Font("Georgia",Font.PLAIN,18));
				bItem.setFocusable(false);
				bItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {	
					Thread threadItem = new Thread (new Runnable() {

						@Override
						public void run() {
							ThreadCount++;
							bItem.setEnabled(false);
							if (ThreadCount>3) {
								JOptionPane.showInternalMessageDialog(null, "To many active jobs", "Jobs", JOptionPane.WARNING_MESSAGE, null);
							}
							else if (ManualPath==false) {
								A165006_Item_Info_DC Item_Info = new A165006_Item_Info_DC();
								Item_Info.runJob(new String[] {});
							}else {
								JFileChooser filePath = new JFileChooser();
								filePath.setDialogTitle("Set the path");
								filePath.setMultiSelectionEnabled(false);
								filePath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
							
								int x = filePath.showSaveDialog(pPLACenter);			
								if (x!=JFileChooser.SAVE_DIALOG) {
									
									String path = filePath.getSelectedFile().getAbsolutePath().replace('\\', '/');
									
									A165006_Item_Info_DC Item_Info = new A165006_Item_Info_DC();
										String [] context = new String []
												{"--context_param Path="+path	
												};
									Item_Info.runJob(context);
								}
							}
							ThreadCount--;
							bItem.setEnabled(true);
						}
					});
					threadItem.start();
					}
				});
				pItemCenter.add(bItem);
			
		pOther.add(pItemCenter,BorderLayout.CENTER);
		pOther.add(lItem,BorderLayout.NORTH);	
			
		JTabbedPane tp = new JTabbedPane(SwingConstants.LEFT);
		tp.setBackground(new Color(0xF2F2F2));
		tp.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tp.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tp.addTab("PLA Tool", pPLA);
		tp.addTab("Occupancy", pOccupancy);
		tp.addTab("ABC", pABC);
		tp.addTab("MD check", pMD);
		tp.addTab("Other", pOther);
		frame.setContentPane(tp);
		
		//Menu bar
		JMenuBar menu = new JMenuBar();
		
			JMenu mSystem = new JMenu("System");
			JMenu mEdit = new JMenu("Edit");
				
				JMenuItem miLogout = new JMenuItem("Logout");
				JMenuItem miExit = new JMenuItem("Exit");
				JCheckBoxMenuItem cbmiManualPath = new JCheckBoxMenuItem("Manual Path",false);
				
				mSystem.add(miLogout);
				mSystem.add(miExit);
				mEdit.add(cbmiManualPath);
				
			menu.add(mSystem);
			menu.add(mEdit);
		
		frame.setJMenuBar(menu);
		
		miLogout.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new LoginScreen();
			}	
		});
		
		miExit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}	
		});
		
		cbmiManualPath.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ManualPath=cbmiManualPath.getState();		
			}
			
		});
		
		frame.setVisible(true);		
	}	
}
