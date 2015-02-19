import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import com.mysql.jdbc.PreparedStatement;

public class GUI extends JFrame{
	private Container content;
	public GUI(){
		content = this.getContentPane();
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		content.setVisible(true);
		switchPane(new LoginPanel());
		this.pack();
		this.setLocationRelativeTo(null);
	}
	private void switchPane(JPanel nextPanel){
		if(content.getComponentCount() == 1)
			content.remove(0);
		content.add(nextPanel);
		content.validate();
		this.repaint();
		this.pack();
	}
	private void rePack(){
		this.repaint();
		this.pack();
	}
	class LoginPanel extends JPanel{
		private JLabel labelUsername = new JLabel("Enter Email: ");
		private JLabel labelPassword = new JLabel("Enter Password: ");
		private JLabel ifWrong = new JLabel("");
		private JTextField textUsername = new JTextField(20);
		private JPasswordField fieldPassword = new JPasswordField(20);
		private JButton buttonLogin = new JButton("Login");
		private JButton buttonRegister = new JButton("Register");
		
		public LoginPanel(){
			this.setVisible(true);
			this.setLayout(new GridBagLayout());
	         
	        GridBagConstraints constraints = new GridBagConstraints();
	        constraints.anchor = GridBagConstraints.WEST;
	        constraints.insets = new Insets(10, 10, 10, 10);
	        
	        constraints.gridx = 0;
	        constraints.gridy = 0;     
	        this.add(labelUsername, constraints);
	 
	        constraints.gridx = 1;
	        this.add(textUsername, constraints);
	         
	        constraints.gridx = 0;
	        constraints.gridy = 1;     
	        this.add(labelPassword, constraints);
	         
	        constraints.gridx = 1;
	        this.add(fieldPassword, constraints);
	         
	        constraints.gridx = 0;
	        constraints.gridy = 2;
	        constraints.gridwidth = 2;
	        constraints.anchor = GridBagConstraints.LAST_LINE_START;
	        this.add(buttonLogin, constraints);
	        
	        constraints.gridx = 0;
	        constraints.gridy = 2;
	        constraints.gridwidth = 2;
	        constraints.anchor = GridBagConstraints.LAST_LINE_END;
	        this.add(buttonRegister, constraints);
	        
	        constraints.gridx = 0;
	        constraints.gridy = 3;
	        this.add(ifWrong,constraints);
	         
	        // set border for the panel
	        this.setBorder(BorderFactory.createTitledBorder(
	                BorderFactory.createEtchedBorder(), "Login Panel"));
	        buttonRegister.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GUI.this.switchPane(new RegisterPanel());
				}
			});
	        buttonLogin.addActionListener(new ActionListener(){

				@Override
				public void actionPerformed(ActionEvent e) {
					MysqlHelper mysql = new MysqlHelper();
					mysql.setConnection("localhost", "my_first_forum", "root", "0411");
					ArrayList<String> email = new ArrayList<String>();
					ArrayList<String> password = new ArrayList<String>();
					boolean check = false;
					try{
						String sql = "SELECT email, password FROM user";
						PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
						ResultSet resultSet = statement.executeQuery();
						while(resultSet.next()){
							email.add(resultSet.getString("email"));
							password.add(resultSet.getString("password"));
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{
						mysql.closeConnection();
						for(int x = 0; x < email.size(); x++){
							if(email.get(x).equals(textUsername.getText()) && password.get(x).equals(fieldPassword.getText()))
								check = true;
							else{
								ifWrong.setText("Try again");
								GUI.this.rePack();
							}
						}
					}
					if(check)
						GUI.this.switchPane(new ForumMain(GUI.this, textUsername.getText()));
				}
	        	
	        });
		}
	}
	class RegisterPanel extends JPanel{
		private JLabel fName = new JLabel("First Name: ");
		private JLabel lName = new JLabel("Last Name: ");
		private JLabel ageBirth = new JLabel("DateOfBirth: ");
		private JLabel passOne = new JLabel("Password: ");
		private JLabel passTwo = new JLabel("Confirm Password: ");
		private JLabel email = new JLabel("Email: ");
		private JLabel fieldIncorrect = new JLabel("<html>***************************<br>Fill in all fields<br>***************************</html>", SwingConstants.CENTER);
		private JTextField textFName = new JTextField(20);
		private JTextField textLName = new JTextField(20);
		private JSpinner ageSpinner; 
		private JTextField textPassOne = new JTextField(20);
		private JTextField textPassTwo = new JTextField(20);
		private JTextField textEmail = new JTextField(20);
		private JButton confirm = new JButton("Confirm");
		private JButton cancel = new JButton("Cancel");
		private StringBuilder fieldCheck;
		
		public RegisterPanel(){   
			this.setVisible(true);
			this.setLayout(new GridBagLayout());
			GridBagConstraints constraints = new GridBagConstraints();
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(10,10,10,10);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			this.add(fName, constraints);
			
			constraints.gridx = 1;
			this.add(textFName, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 1;
			this.add(lName, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 1;
			this.add(textLName, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			this.add(ageBirth, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			SpinnerModel model =
			        new SpinnerNumberModel(2015, //initial value
			                               2015 - 100, //min
			                               2015 + 100, //max
			                               1);
			ageSpinner = new JSpinner(model);
			this.add(ageSpinner, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 3;
			this.add(passOne, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 3;
			this.add(textPassOne, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 4;
			this.add(passTwo, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 4;
			this.add(textPassTwo, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 5;
			this.add(email, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 5;
			this.add(textEmail, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 6;
			this.add(confirm, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 6;
			this.add(cancel, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 7;
			this.add(fieldIncorrect, constraints);
			confirm.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					MysqlHelper mysql = new MysqlHelper();
					mysql.setConnection("localhost", "my_first_forum", "root", "0411");
					ArrayList<String> data = null;
					try{
						String sql = "SELECT email FROM user";
						PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
						ResultSet resultSet = statement.executeQuery();
						data = new ArrayList<String>();
						while(resultSet.next()){
							data.add(resultSet.getString("email"));
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						mysql.closeConnection();
					}
					//check if everything is ok....
					fieldCheck = new StringBuilder();
					fieldCheck.append("<html>");
					if(textEmail.getText().equals(""))
						fieldCheck.append("<br>*Email missing\n");
					if(textFName.getText().equals(""))
						fieldCheck.append("<br>*First Name missing\n");
					if(textLName.getText().equals(""))
						fieldCheck.append("<br>*Last Name missing\n");
					if(textPassOne.getText().equals(""))
						fieldCheck.append("<br>*Password missing\n");
					else if(textPassTwo.getText().equals(""))
						fieldCheck.append("<br>*Password missing\n");
					else if(!textPassOne.getText().equals(textPassTwo.getText()))
						fieldCheck.append("<br>*Password not matching\n");
					for(String dataGet : data){
						if(dataGet.equals(textEmail.getText()))
							fieldCheck.append("<br>*email exists");
					}
					fieldCheck.append("</html>");
					fieldIncorrect.setText(fieldCheck.toString());
					GUI.this.rePack();
					//then send it to the server....
					String temp = fieldCheck.toString();
					boolean check = false;
					for(int x = 0; x < temp.length(); x++){
						if(temp.charAt(x) == '*'){
							check = true;
							break;
						}
					}
					if(check == false){
						mysql = new MysqlHelper();
						mysql.setConnection("localhost", "my_first_forum", "root", "0411");
						// create the mysql insert preparedstatement
						try{
							String query = "INSERT INTO user(first_name,last_name,email,birth_date,password) VALUES(?,?,?,?,?)";
							PreparedStatement preparedStmt = (PreparedStatement) mysql.getConnection().prepareStatement(query);
							preparedStmt.setString (1, textFName.getText());
							preparedStmt.setString (2, textLName.getText());
							preparedStmt.setString (3, textEmail.getText());
							preparedStmt.setInt (4, (Integer)ageSpinner.getValue());
							preparedStmt.setString(5, textPassOne.getText());
							preparedStmt.execute();
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally{
							mysql.closeConnection();
						}
					}
				}
			});
			cancel.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					GUI.this.switchPane(new LoginPanel());					
				}
			});
		}
	}
}
