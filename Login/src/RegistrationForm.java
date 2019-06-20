import java.awt.*;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class RegistrationForm {
	private JButton createButton,studentButton,teacherButton;
	private JTextField firstNameField,lastNameField,emailField,addressField,usernameField,passwordField,schoolField,birthdateField,gradeField,disciplineField;
	private JLabel firstNameLabel,lastNameLabel,emailLabel,addressLabel,usernameLabel,passwordLabel,schoolLabel,birthdateLabel,gradeLabel,disciplineLabel;
	private JFrame frame;
	private boolean table;//Decides which of the two tables to use in insert query
	
	public RegistrationForm(){
		
		//Adding elements on the frame
		(frame = new JFrame ("Registration Form")).add(firstNameField = new JTextField());
		frame.add(lastNameField = new JTextField());
		frame.add(usernameField = new JTextField()); 
		frame.add(passwordField = new JTextField()); 
		frame.add(addressField = new JTextField()); 
		frame.add(emailField = new JTextField());
		frame.add(birthdateField = new JTextField()); 
		frame.add(schoolField = new JTextField());
		frame.add(disciplineField = new JTextField()); 
		frame.add(gradeField = new JTextField());
		
		frame.add(firstNameLabel= new JLabel("First name :")); 
		frame.add(lastNameLabel = new JLabel("Last name :"));
		frame.add(usernameLabel = new JLabel("Username :")); 
		frame.add(passwordLabel = new JLabel("password :")); 
		frame.add(addressLabel = new JLabel("address :")); 
		frame.add(emailLabel = new JLabel("email :"));
		frame.add(birthdateLabel= new JLabel("birthdate :")); 
		frame.add(schoolLabel = new JLabel("school :"));
		frame.add(disciplineLabel = new JLabel("discipline :")); 
		frame.add(gradeLabel = new JLabel("degree :"));
		
		frame.add(studentButton = new JButton ("Student"));
		frame.add(teacherButton = new JButton ("Teacher"));
		frame.add(createButton = new JButton ("Create user"));
		
		List<JTextField> fields = Arrays.asList(firstNameField,lastNameField,emailField,addressField,usernameField,passwordField,schoolField,birthdateField,gradeField,disciplineField);
		List<JLabel> labels = Arrays.asList(firstNameLabel,lastNameLabel,emailLabel,addressLabel,usernameLabel,passwordLabel,schoolLabel,birthdateLabel,gradeLabel,disciplineLabel);
		
		for(JTextField i : fields)
		i.setVisible(false);	
		
		for(JLabel i : labels)
		i.setVisible(false);
		
		//Placing elements on the frame
		createButton.setBounds(320,600,120,30);
		studentButton.setBounds(100, 20, 100, 30);
		teacherButton.setBounds(300, 20, 100, 30);
		
		int x=220,y=70,width=200,height=30;
		for(JTextField i: fields) {
			i.setBounds(x, y, width, height);
			y+=50;
		}
		
		x=100;
		y=70;
		for(JLabel i:labels) {
			i.setBounds(x,y,width,height);
			y+=50;
		}
		
		disciplineField.setBounds(220,470,200,30);
		disciplineLabel.setBounds(100,470,200,30);
			
		//frame in a center of the screen
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

		studentButton.addActionListener(event -> 
		{
			for(JTextField i : fields)
			i.setVisible(true);	
			
			for(JLabel i : labels)
			i.setVisible(true);
			
			disciplineField.setVisible(false);
			disciplineLabel.setVisible(false);
			table=(true);
		});
		 
		teacherButton.addActionListener(event->
		{
			for(JTextField i : fields)
			i.setVisible(true);	
			
			for(JLabel i : labels)
			i.setVisible(true);
			
			gradeField.setVisible(false);
			gradeLabel.setVisible(false);
			table=(false);
		});	
		
		//Insert into table
		createButton.addActionListener(event ->
		{
			Connection conn=LoginForm.getConnection();	
			if(conn !=null)
	        {
	        	try 
	        	{
	        		if(table) {
	        			conn.createStatement().executeUpdate("INSERT INTO public.student(school_id, birthdate, first_name, last_name, email, address_id, active, username, password, degree)\r\n" + 
	      					"	VALUES ('"+schoolField.getText()+"','"+birthdateField.getText()+"',"+firstNameField.getText()+"','"+lastNameField.getText()+"','"+emailField.getText()+"',"+addressField.getText()+",true,'"+usernameField.getText()+"','"+passwordField.getText()+"',"+gradeField.getText()+");");			        		
	        		}
		      		else {
	        			conn.createStatement().executeUpdate("INSERT INTO public.teacher(first_name, last_name, email, address_id, active, username, password, school_id, birthdate, discipline_id)\r\n" + 
	        				"	VALUES ('"+firstNameField.getText()+"','"+lastNameField.getText()+"','"+emailField.getText()+"',"+addressField.getText()+",true,'"+usernameField.getText()+"','"+passwordField.getText()+"',"+schoolField.getText()+",'"+birthdateField.getText()+"',"+disciplineField.getText()+");");
			        }
			  	}catch(SQLException ex)
		      	{
	        		ex.printStackTrace();			    			         
			    }
	        }
			else
			{
				System.out.println("Unable to connect");
			}        
		});
	}
}
