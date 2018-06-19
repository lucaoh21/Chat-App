package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ServerTest {
	//list of all the output streams
	static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
	
	public static void main(String[] args) {
		String ipAddress;
		int port = 0;
		
		/* dialog box used to get port */
		JTextField portText = new JTextField();
		
		//gives option to enter the standard port #8080
		JButton standard = new JButton("Use standard port");
		standard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				portText.setText("8080");
			}
		});
		
		Object[] connect = {"Port:", portText, standard};
	
		int answer = JOptionPane.showConfirmDialog(
				null, connect, null, JOptionPane.OK_CANCEL_OPTION);
		if(answer == JOptionPane.OK_OPTION) {
			port = Integer.parseInt(portText.getText());
		} else {
			System.out.println("Port not given");
			System.exit(1);
		}
		
		
		try {
			//get ip address to display it in server window
			ipAddress = InetAddress.getLocalHost().getHostAddress();
			
			//create the servers socket and a server window
			ServerSocket socket = new ServerSocket(port);
			System.out.println("New server socket was made");
			ServerJFrame window = new ServerJFrame(ipAddress, port);
			
			//runs until server is shut down (by closing server window)
			while(window.getisRunning()) {
				//new thread is started to handle new client
				new ServerThread(socket.accept()).start();
				System.out.println("New server thread was made");
			}
			socket.close();
			
		} catch (IOException e) {
			System.out.println("Could not create new server thread.");
			System.exit(1);
		} finally {
			System.out.println("System exit");
			System.exit(0);
		}

	}

}
