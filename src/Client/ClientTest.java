package Client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ClientTest {
	private static Socket socket;
	private static BufferedReader in;
	private static PrintWriter out;
	
	
	public static void main(String[] args) {
		String ipAddress = null; 
		int port = 0;
		
		/* dialog box used to get IP Address and port from the user */
		JTextField ipText = new JTextField();
		JTextField portText = new JTextField();
		
		//gives option to enter the standard ip and port (for testing)
		JButton standard = new JButton("Use standard ip and port");
		standard.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				ipText.setText("");
				portText.setText("8080");
			}
		});
		
		Object[] connect = {"IP Address:", ipText, "Port:", portText, standard};
		
		int answer = JOptionPane.showConfirmDialog(
				null, connect, null, JOptionPane.OK_CANCEL_OPTION);
		if(answer == JOptionPane.OK_OPTION) {
			ipAddress = ipText.getText();
			port = Integer.parseInt(portText.getText());
		} else {
			System.out.println("User did not enter information");
			System.exit(1);
		}
		
		try {
			socket = new Socket(ipAddress, port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			ClientJFrame window = new ClientJFrame(out);
			
			//runs until chat window is closed by the user
			while(window.getisRunning()) {
				String message = in.readLine();
				
				//message only empty if its from the server (signal to quit)
				if(message.isEmpty()) {
					window.frame.dispose();
					break;
				}
				window.messageField.append(message + "\n");
			}
			
		} catch (UnknownHostException e) {
			System.out.println(ipAddress + " is unknown.");
			System.exit(1);
		} catch (IOException e) {
			System.out.println("IO connection to " + ipAddress + " did not work.");
			System.exit(1);
		}
		
	}
}
