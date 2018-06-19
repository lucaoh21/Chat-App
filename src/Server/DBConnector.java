package Server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {
	private static String locate = "com.mysql.cj.jdbc.Driver";
	private static String link = "jdbc:mysql://localhost:3306/chatLogin?autoReconnect=true&useSSL=false&useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=EST";
	private static String user = "root";
	private static String pass = "esintern";
	
	
	/*public static void main(String[] args) {
		boolean result = correctLogin("smith", "13245");
		if(result) {
			System.out.println("user exists");
		} else {
			System.out.println("no user");
		}
		
		boolean createdUser = newUser("Jane", "Doe", "janedoe3", "1234", "testemail.com");
		
		if(createdUser) {
			System.out.println("new user was made");
		} else {
			System.out.println("new user was not created");
		}
		
		try {
			
			Class.forName(locate);
			Connection connect = DriverManager.getConnection(link, user, pass);
			
			PreparedStatement statement = connect.prepareStatement("select * from login");
			ResultSet result = statement.executeQuery();
			
			while(result.next()) {
				System.out.println(result.getString(1) + " " + result.getString(2) + 
						" " + result.getString(3) + " " + result.getString(4));
				
			}
					
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}*/
	
	public static boolean newUser(String firstName, String lastName, String username, String pword, String email) {
		try {
			Class.forName(locate);
			Connection connect = DriverManager.getConnection(link, user, pass);
			
			//checks to make sure username has not been taken, returns false is username is taken
			PreparedStatement statement = connect.prepareStatement("SELECT * FROM login WHERE Username=?");
			statement.setString(1, username);
			ResultSet data = statement.executeQuery();
			if(data.next()) {
				return false;
			}
	
			//inserts user information into the table and returns true
			statement = connect.prepareStatement("INSERT INTO login(FirstName,LastName,Username,Pword,Email) VALUES (?,?,?,?,?)");
			statement.setString(1, firstName);
			statement.setString(2, lastName);
			statement.setString(3, username);
			statement.setString(4, pword);
			statement.setString(5, email);
			statement.executeUpdate();
			return true;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("The database could not be found.");
			
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		return false;
	}
	
	public static boolean correctLogin(String username, String password) {
		try {
			Class.forName(locate);
			Connection connect = DriverManager.getConnection(link, user, pass);
			
			//checks to make sure user exists, and login information is correct
			PreparedStatement statement = connect.prepareStatement("SELECT * FROM login WHERE Username=? AND Pword=?");
			statement.setString(1, username);
			System.out.println(password.toString());
			statement.setString(2, password);
			ResultSet data = statement.executeQuery();
			if(data.next()) {
				System.out.println("login correct");
				return true;
			}
			
			return false;
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println("The database could not be found.");
			
		} catch (SQLException e) {
			System.out.println("SQL Exception");
			e.printStackTrace();
		}
		return false;
	}
	
}
