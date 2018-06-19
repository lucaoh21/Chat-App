package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ServerThread extends Thread {
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private String name;
	
	public ServerThread(Socket socket) {
		this.socket = socket;
	}
	
	//runs when a new thread is created by the server
	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			//adds the current output stream to the list of all writers
			ServerTest.writers.add(out);
			
			/* dialog box used to get a unique name from the user */
			JTextField username = new JTextField();
			JPasswordField password = new JPasswordField();
			
			//allows user to access the create new user page
			JButton createAccount = new JButton("Create an account");
			createAccount.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					JTextField firstName = new JTextField();
					JTextField lastName = new JTextField();
					JTextField user = new JTextField();
					JTextField email = new JTextField();
					JTextField pword = new JTextField();
					Object[] info = {"First Name*:", firstName, "Last Name*:", lastName,
							"Email:", email, "Username*:", user, "Password*:", pword};
					
					int answer = JOptionPane.showConfirmDialog(
							null, info, null, JOptionPane.OK_CANCEL_OPTION);
					if(answer == JOptionPane.OK_OPTION) {
						if(DBConnector.newUser(firstName.getText(), lastName.getText(), user.getText(),
								pword.getText(), email.getText())) {
							JOptionPane.showMessageDialog(null, "Your account has been created. Please login.",
									"New Account", JOptionPane.INFORMATION_MESSAGE);
						} else {
							JOptionPane.showMessageDialog(null, "Your account could not be created. Please try again.",
									"Failed Account", JOptionPane.INFORMATION_MESSAGE);
							System.out.println("Could not create new user");
						}
					} 
				}
			});
			
			Object[] loginInfo = {"Username:", username, "Password:", password, createAccount};
			
			while(true) {
				int answer = JOptionPane.showConfirmDialog(
						null, loginInfo, null, JOptionPane.OK_CANCEL_OPTION);
				if(answer == JOptionPane.OK_OPTION) {
					name = username.getText();
					
					if(DBConnector.correctLogin(name, new String(password.getPassword()))) {
						break;
					} else {
						System.out.println("Name is used");
						username.setText("Name already used. Try again.");
					}
				} else {
					System.out.println("User did not enter a name");
					System.exit(1);
				}
			}
			
			/*runs until null input is received from input stream,
			  happens when server removes input stream from list of writers */
			while(true) {
				String input = in.readLine();
				if(input == null) {
					ServerTest.writers.remove(out);
					return;
				}
				
				//send message to all other writers
				for(PrintWriter writer : ServerTest.writers) {
					writer.println(name + ": " + input);
				}
			}
			
		} catch (IOException e) {
			System.out.println("IO connection error.");
			System.exit(1);
		}
	}
}
