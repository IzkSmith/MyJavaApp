import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class RegistrationForm {
	private JButton createButton,studentButton,teacherButton;
	private JTextField firstNameField,lastNameField,usernameField,passwordField,addressField,schoolField,disciplineField,degreeField,emailField,birthdateField;
	private JLabel firstNameLabel,lastNameLabel,usernameLabel,passwordLabel,addressLabel,schoolLabel,disciplineLabel,degreeLabel,emailLabel,birthdateLabel;
	private JFrame frame;
	private boolean table;

	
	public RegistrationForm(){
		
		frame = new JFrame ("Registration Form");
		createButton = new JButton ("Create user");
		createButton.addActionListener(new CreateButtonListener());
		
		studentButton = new JButton ("Student");
		studentButton.addActionListener(new StudentButtonListener());
		
		teacherButton = new JButton ("Teacher");
		teacherButton.addActionListener(new TeacherButtonListener());
		
		firstNameField = new JTextField();
		lastNameField = new JTextField();
		usernameField = new JTextField();
		passwordField = new JTextField();
		addressField = new JTextField();
		schoolField = new JTextField();	
		birthdateField = new JTextField();	
		emailField = new JTextField();	
		degreeField = new JTextField();	
		disciplineField = new JTextField();	
		
		firstNameLabel= new JLabel("First name :");
		lastNameLabel = new JLabel("Last name :");
		usernameLabel = new JLabel("Username :");
		passwordLabel = new JLabel("password :");
		addressLabel = new JLabel("address :");
		schoolLabel = new JLabel("school :");
		disciplineLabel = new JLabel("discipline :");
		degreeLabel = new JLabel("degree :");
		emailLabel = new JLabel("email :");
		birthdateLabel= new JLabel("birthdate :");
		
		
		createButton.setBounds(320,600,120,30);
		studentButton.setBounds(100, 20, 100, 30);
		teacherButton.setBounds(300, 20, 100, 30);
		
	    firstNameField.setBounds(220,70,200,30);
		lastNameField.setBounds(220,120,200,30);
		usernameField.setBounds(220,170,200,30);
		passwordField.setBounds(220,220,200,30);
		addressField.setBounds(220,270,200,30); 
		schoolField.setBounds(220,320,200,30);
		birthdateField.setBounds(220,370,200,30); 
		emailField.setBounds(220,420,200,30);
		disciplineField.setBounds(220,470,200,30);
		degreeField.setBounds(220,470,200,30);
		
		firstNameLabel.setBounds(100,70,200,30);
		lastNameLabel.setBounds(100,120,200,30);
		usernameLabel.setBounds(100,170,200,30);
		passwordLabel.setBounds(100,220,200,30);
		addressLabel.setBounds(100,270,200,30); 
		schoolLabel.setBounds(100,320,200,30);
		birthdateLabel.setBounds(100,370,200,30); 
		emailLabel.setBounds(100,420,200,30);
		disciplineLabel.setBounds(100,470,200,30);
		degreeLabel.setBounds(100,470,200,30);
		
		
		frame.add(studentButton);
		frame.add(teacherButton);
		frame.add(createButton);
		
		frame.add(firstNameField); 
		frame.add(lastNameField);
		frame.add(usernameField); 
		frame.add(passwordField); 
		frame.add(addressField); 
		frame.add(emailField);
		frame.add(birthdateField); 
		frame.add(schoolField);
		frame.add(disciplineField); 
		frame.add(degreeField);
		
		frame.add(firstNameLabel); 
		frame.add(lastNameLabel);
		frame.add(usernameLabel); 
		frame.add(passwordLabel); 
		frame.add(addressLabel); 
		frame.add(emailLabel);
		frame.add(birthdateLabel); 
		frame.add(schoolLabel);
		frame.add(disciplineLabel); 
		frame.add(degreeLabel);
		
		firstNameField.setVisible(false);
		lastNameField.setVisible(false);
		usernameField.setVisible(false);
		passwordField.setVisible(false);
		disciplineField.setVisible(false);
		birthdateField.setVisible(false);
		emailField.setVisible(false);
		schoolField.setVisible(false);
		addressField.setVisible(false);
		degreeField.setVisible(false);
		
		firstNameLabel.setVisible(false);
		lastNameLabel.setVisible(false);
		usernameLabel.setVisible(false);
		passwordLabel.setVisible(false);
		disciplineLabel.setVisible(false);
		birthdateLabel.setVisible(false);
		emailLabel.setVisible(false);
		schoolLabel.setVisible(false);
		addressLabel.setVisible(false);
		degreeLabel.setVisible(false);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int sizeWidth = 500;
		int sizeHeight = 700;
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		frame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setSize(sizeWidth,sizeHeight);
		frame.setLayout(null);
		frame.setVisible(true);	
	}
	
	public class StudentButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			firstNameField.setVisible(true);
			lastNameField.setVisible(true);
			usernameField.setVisible(true);
			passwordField.setVisible(true);
			disciplineField.setVisible(false);
			birthdateField.setVisible(true);
			emailField.setVisible(true);
			schoolField.setVisible(true);
			addressField.setVisible(true);
			degreeField.setVisible(true);
			
			firstNameLabel.setVisible(true);
			lastNameLabel.setVisible(true);
			usernameLabel.setVisible(true);
			passwordLabel.setVisible(true);
			disciplineLabel.setVisible(false);
			birthdateLabel.setVisible(true);
			emailLabel.setVisible(true);
			schoolLabel.setVisible(true);
			addressLabel.setVisible(true);
			degreeLabel.setVisible(true);
			table=(true);
		}
	}
	
	public class TeacherButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			firstNameField.setVisible(true);
			lastNameField.setVisible(true);
			usernameField.setVisible(true);
			passwordField.setVisible(true);
			disciplineField.setVisible(true);
			birthdateField.setVisible(true);
			emailField.setVisible(true);
			schoolField.setVisible(true);
			addressField.setVisible(true);
			degreeField.setVisible(false);
			
			firstNameLabel.setVisible(true);
			lastNameLabel.setVisible(true);
			usernameLabel.setVisible(true);
			passwordLabel.setVisible(true);
			disciplineLabel.setVisible(true);
			birthdateLabel.setVisible(true);
			emailLabel.setVisible(true);
			schoolLabel.setVisible(true);
			addressLabel.setVisible(true);
			degreeLabel.setVisible(false);
			table=(false);
		}
	}
	
	public class CreateButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			Connection conn=LoginForm.getConnection();
			if (table) {
			        if(conn !=null)
			        {
			        	try 
			        	{
			        		conn.createStatement().executeUpdate("INSERT INTO public.student(\r\n" + 
			        				"	school_id, birthdate, degree, first_name, last_name, email, address_id, active, username, password)\r\n" + 
			        				"	VALUES ('"+schoolField.getText()+"','"+birthdateField.getText()+"',"+degreeField.getText()+","+firstNameField.getText()+"','"+lastNameField.getText()+"','"+emailField.getText()+"',"+addressField.getText()+",true,'"+usernameField.getText()+"','"+passwordField.getText()+"');");			        		
			        	}catch(SQLException e)
			        	{
			        		e.printStackTrace();
			        	}				         
			        }
			        else
			        {
			        	System.out.println("Unable to connect");
			       	}	
			}
			else {	
			        if(conn !=null)
			        {
			        	try 
			        	{
			        		conn.createStatement().executeUpdate("INSERT INTO public.teacher(\r\n" + 
			        				"	first_name, last_name, email, address_id, active, username, password, school_id, discipline_id, birthdate)\r\n" + 
			        				"	VALUES ('"+firstNameField.getText()+"','"+lastNameField.getText()+"','"+emailField.getText()+"',"+addressField.getText()+",true,'"+usernameField.getText()+"','"+passwordField.getText()+"',"+schoolField.getText()+","+disciplineField.getText()+",'"+birthdateField.getText()+"');");
			        	}catch(SQLException e)
			        	{
			        		e.printStackTrace();
			        	}				         
			        }
			        else
			        {
			        	System.out.println("Unable to connect");
			       	}	
			}
		}
	}
}
