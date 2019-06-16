import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.*;
 
public class LoginForm {
	private JLabel usernameLabel, passwordLabel,errorLabel;
	private JTextField usernameField;
	private JButton loginButton;
	private JPasswordField passwordField;
	private JFrame frame;
	private JButton newUserButton;
	private static Connection conn;
	
	public static void main(String[] args) {
		new LoginForm();
	}
	
	public static Connection getConnection() {
		return conn;
	}

	public LoginForm() {
		//подключение в бд
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
        conn = null;
        try
        {
        	conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/MyApp","postgres","1029");
        }catch (SQLException e)
        {
        	System.out.println("Unable to create the connection");
        	e.printStackTrace();
        	return;
        }
		//Инициализация всех полей
		frame = new JFrame("Login Form");	 
		usernameLabel = new JLabel("Username");
		passwordLabel = new JLabel("Password");
		usernameField = new JTextField();
		passwordField = new JPasswordField();
		loginButton = new JButton("Login");
		
		loginButton.addActionListener(new LoginButtonListener());
		errorLabel= new JLabel("");
		errorLabel.setForeground(Color.red);
		
		newUserButton = new JButton("Create new user");
		newUserButton.addActionListener(new NewUserButtonListener());
		
		//размещение всех элементов на фрейме
		usernameLabel.setBounds(80, 70, 200, 30);//(x,y,length,height)
		passwordLabel.setBounds(80, 110, 200, 30);
		usernameField.setBounds(170, 70, 200, 30);
		passwordField.setBounds(170, 110, 200, 30);
		loginButton.setBounds(270, 160, 100, 30);
		errorLabel.setBounds(170, 135, 230, 30);
		newUserButton.setBounds(80,160,150,30);
		
		//добавление всех элементов на фрейм
		frame.add(usernameLabel);
		frame.add(usernameField);
		frame.add(passwordLabel);
		frame.add(passwordField);
		frame.add(loginButton);
		frame.add(errorLabel);
		frame.add(newUserButton);
		
		//расположение фрейма по центру экрана
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
	}
	
	//внутренний класс обрабатывающий событие нажатия кнопки
	public class LoginButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			
			String uname = usernameField.getText();
			String pass = String.valueOf(passwordField.getPassword());
			System.out.println(pass);
			errorLabel.setVisible(true);
			
			if(uname.equals("s") && pass.equals("s")){
				new Student();
				frame.setVisible(false);
			}else {
				if(uname.equals("t") && pass.equals("t")){
					new Teacher();
					frame.setVisible(false);
				}else {
				usernameField.setText("");
				passwordField.setText("");
				errorLabel.setText("username or password is incorrect");
				}
			}
		}
	}
	
	public class NewUserButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			new RegistrationForm();
			frame.setVisible(false);
		}
	}
}