package Server;
import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ServerJFrame {
	private static boolean isRunning = true;
	JFrame frame = new JFrame("Server");
	
	public boolean getisRunning() {
		return isRunning;
	}
	
	public ServerJFrame (String ipAddress, int portNum) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
		JLabel ip = new JLabel("Server IP Address is " + ipAddress);
		JLabel port = new JLabel("Server is using port " + portNum);
		System.out.println("Made it");
		//JButton closeButton = new JButton("Close");
		contentPane.add(ip,"North");
		contentPane.add(port, "Center");
		System.out.println("Made it");
		
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("The server frame is closing.");
				
				/* sends blank message to each input stream,
				   this tells client the server socket is going down */
				for(PrintWriter writer : ServerTest.writers) {
					writer.println();
				}
				//if window is closed server socket is shut down
				isRunning = false;
			}
		});
		frame.pack();
		//frame.setBounds(50, 50, 600, 300);
		frame.setVisible(true);
	}
	
	
}
