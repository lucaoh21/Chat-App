package Client;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.PrintWriter;

import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class ClientJFrame {
	
	private static boolean isRunning = true; //whether the Client runs or not
	JFrame frame = new JFrame("Chat");
	JTextField textField = new JTextField(50); //for user input
	JTextArea messageField = new JTextArea(10, 50); //for messages from other users
	
	public boolean getisRunning() {
		return isRunning;
	}
	
	public ClientJFrame (PrintWriter out) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container contentPane = frame.getContentPane();
	
		//allows user to enter messages, but not change conversation history
		textField.setEditable(true);
		messageField.setEditable(false);
		
		//if enter key is pressed in textField, the input is sent to server
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textField.getText();
				if(!message.isEmpty()) {
					out.println(message);
					textField.setText("");
				}
			}
		});
		
		contentPane.add(textField, "North");
		contentPane.add(new JScrollPane(messageField), "Center");
		
		//when window is closed, the Client is shut down
		frame.addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e) {
				System.out.println("The frame is closing.");
				isRunning = false;
			}
		});
		
		frame.setBounds(50, 50, 600, 300);
		frame.setVisible(true);
		
	}
}