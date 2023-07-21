package processes_tool_v3;
import javax.swing.SwingUtilities;

public class Processes_tool_v3 {
	
	public static void main(String[] args) {
			
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {				
				new LoginScreen();
			//	new WaitingDialog();
			//	new RegisterScreen();
			//	new TaskScreen();
			};
		});
	}
}
