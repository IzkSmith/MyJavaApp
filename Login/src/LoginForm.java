import java.awt.*;
import java.sql.*;

import javax.swing.*;
 
public class LoginForm {
	private JLabel usernameLabel, passwordLabel,errorLabel;
	private JTextField usernameField;
	private JButton loginButton;
	private JPasswordField passwordField;
	private JFrame frame;
	private JButton newUserButton;
	private static Connection connection;
	
	public static void main(String[] args) {
		new LoginForm();
	}
	
	public static Connection getConnection() {
		return connection;
	}

	public LoginForm() {
		DatabaseConnection();
        
		//Adding elements on the frame
		(frame = new JFrame("Login Form")).add(usernameLabel = new JLabel("Username"));
		frame.add(usernameField = new JTextField());
		frame.add(passwordLabel = new JLabel("Password"));
		frame.add(passwordField = new JPasswordField());
		frame.add(errorLabel= new JLabel(""));
		frame.add(loginButton = new JButton("Login"));
		frame.add(newUserButton = new JButton("Create new user"));
		errorLabel.setVisible(true);
		errorLabel.setForeground(Color.red);
		
		//Placing elements on the frame
		usernameLabel.setBounds(80, 70, 200, 30);//(x,y,length,height)
		passwordLabel.setBounds(80, 110, 200, 30);
		usernameField.setBounds(170, 70, 200, 30);
		passwordField.setBounds(170, 110, 200, 30);
		loginButton.setBounds(270, 160, 100, 30);
		errorLabel.setBounds(170, 135, 230, 30);
		newUserButton.setBounds(80,160,150,30);
		
		//frame in a center of the screen
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int sizeWidth = 500;
		int sizeHeight = 300;
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(sizeWidth,sizeHeight);
		frame.setLayout(null);
		frame.setVisible(true);	
         		
		//TO DO select queries
		loginButton.addActionListener(e->
		{	
			String uname = usernameField.getText();
			String pass = String.valueOf(passwordField.getPassword());
			System.out.println(pass);
			errorLabel.setVisible(true);
			
			if(uname.equals("student") && pass.equals("student")){
				new Student();
			}else {
				if(uname.equals("teacher") && pass.equals("teacher")){
					new Teacher();
					frame.setVisible(false);
				}else {
				usernameField.setText("");
				passwordField.setText("");
				errorLabel.setText("username or password is incorrect");
				}
			}
		});
		
		newUserButton.addActionListener(e->
		{
			new RegistrationForm();
			frame.setVisible(false);
		});
	}
	
	public void DatabaseConnection(){
		try
	    {
	    	Class.forName("org.postgresql.Driver");
	    }catch(ClassNotFoundException e)
	    {
	    	System.out.println("Driver not found");
	    	e.printStackTrace();
	    	return;
	    }
	    System.out.println("Driver Registered Successfully...");
	    connection = null;
	    try
	    {
	    	connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/MyApp","postgres","1029");
	    }catch (SQLException e)
	    {
	    	System.out.println("Unable to create the connection");
	    	e.printStackTrace();
	    	return;
	    }
	}
}