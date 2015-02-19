import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class ForumMain extends JPanel{
	private JLabel home = new JLabel("Home");
	private JLabel forum = new JLabel("Forum");
	private JLabel user = new JLabel("User");
	private JButton buttonBack = new JButton();
	private GridBagConstraints constraints;
	private JPanel panel;
	private JPanel panel2;
	private final JFrame frame;
	private String userEmail;
	
	public ForumMain(final JFrame frame, String userEmail){
		this.userEmail = userEmail;
		this.frame = frame;
		panel = this;
		panel2 = new JPanel();
		panel2.setVisible(true);
		panel2.setLayout(new GridBagLayout());
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setVisible(true);
		constraints = new GridBagConstraints();
		constraints.anchor = GridBagConstraints.WEST;
		constraints.insets = new Insets(10,10,10,10);
		
		ImageIcon icon = new ImageIcon("/home/root255/Desktop/sprites/Programpictures/back_arrow.png");
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(40, 20,  java.awt.Image.SCALE_SMOOTH);
		icon = new ImageIcon(newimg);
		buttonBack.setIcon(icon);
		constraints.gridx = 0;
		constraints.gridy = 0;
		panel2.add(buttonBack,constraints);
		
		constraints.gridx = 1;
		constraints.gridy = 0;
		panel2.add(home, constraints);
		
		constraints.gridx = 2;
		constraints.gridy = 0;
		panel2.add(forum, constraints);
		
		constraints.gridx = 3;
		constraints.gridy = 0;
		panel2.add(user, constraints);
		
		this.add(panel2);
		this.add(new HomeLink());
		
		home.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(new HomeLink());
			}
		});
		forum.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(new ForumLink());
			}
		});
		user.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				changePanel(new UserLink());
			}
		});
	}
	public String getUser(){
		return userEmail;
	}
	public void changePanel(JPanel newPanel){
		panel.remove(panel.getComponentCount()-1);
		constraints.gridx = 0;
		constraints.gridy = 1;
		panel.add(newPanel, constraints);
		panel.validate();
		panel.repaint();
		frame.pack();
	}
	public void packFrame(){
		panel.validate();
		panel.repaint();
		frame.pack();
	}
	//add newest threads or posts
	class HomeLink extends JPanel{
		private JList list;
		private DefaultListModel model;
		private ArrayList<Integer> threadID = new ArrayList<Integer>();
		private ArrayList<String> threadName = new ArrayList<String>();
		public HomeLink(){
			this.setVisible(true);
			this.setBackground(Color.WHITE);
			model = new DefaultListModel();
			
			MysqlHelper mysql = new MysqlHelper();
			mysql = new MysqlHelper();
			mysql.setConnection("localhost", "my_first_forum", "root", "0411");
			try{
				String sql = "SELECT id, name FROM thread";
				PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()){
					threadID.add(resultSet.getInt("id"));
					threadName.add(resultSet.getString("name"));
					
				}			
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}finally{
					mysql.closeConnection();
				}
				int counter = 10;
				for(int x = threadName.size()-1; x > 0; x--){
					counter--;
					if(counter == 0)
						break;
					model.addElement(threadName.get(x));
			}
			
			list = new JList(model);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.add(list);
			list.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					ForumMain.this.changePanel(new PostViewPanel(threadID.get(list.locationToIndex(arg0.getPoint()))));
				}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseExited(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
				}
				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
		}
	}
	// subforums threads
	class ForumLink extends JPanel{
		
		private JLabel casual = new JLabel("Casual");
		private JLabel casualYesterday = new JLabel("Yesterday");
		private JLabel casualTomorrow = new JLabel("Tomorrow");
		private JLabel casualToday = new JLabel("Today");
		
		private JLabel programming = new JLabel("Programming");
		private JLabel programmingJava = new JLabel("Java");
		private JLabel programmingCPP = new JLabel("C++");
		private JLabel programmingPascal = new JLabel("Pascal");
		
		private JLabel random = new JLabel("Random");
		private JLabel randomOne = new JLabel("One");
		private JLabel randomFive = new JLabel("Five");
		private JLabel randomSeven = new JLabel("Seven");
		
		private GridBagConstraints constraints;
		public ForumLink(){
			this.setVisible(true);
			this.setBackground(Color.WHITE);
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(5,-5,3,-5);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			this.add(casual, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 1;
			this.add(casualYesterday, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			this.add(casualTomorrow, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 3;
			this.add(casualToday, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 4;
			this.add(programming, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 5;
			this.add(programmingJava, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 6;
			this.add(programmingCPP, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 7;
			this.add(programmingPascal, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 8;
			this.add(random, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 9;
			this.add(randomOne, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 10;
			this.add(randomFive, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 11;
			this.add(randomSeven, constraints);
			
			casualYesterday.addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {	
				}
				@Override
				public void mouseExited(MouseEvent e) {
				}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					ForumMain.this.changePanel(new ThreadListPanel());
				}
			});	
		}
	}
	//edit user options
	class UserLink extends JPanel{
		private JLabel fName = new JLabel("First Name: ");
		private JLabel lName = new JLabel("Last Name: ");
		private JLabel email = new JLabel("Email: ");
		private JTextField textFName = new JTextField(20);
		private JTextField textLName = new JTextField(20);
		private JTextField textEmail = new JTextField(20);
		private JButton update = new JButton("Update");
		private GridBagConstraints constraints;
		
		public UserLink(){
			this.setVisible(true);
			this.setBackground(Color.WHITE);
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(10,10,10,10);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			this.add(fName, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			this.add(textFName, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 1;
			this.add(lName, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 1;
			this.add(textLName, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			this.add(email, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			this.add(textEmail, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 3;
			this.add(update, constraints);
			update.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					MysqlHelper mysql = new MysqlHelper();
					mysql = new MysqlHelper();
					mysql.setConnection("localhost", "my_first_forum", "root", "0411");
					try{
					    // create the java mysql update preparedstatement
					    String query = "update user set first_name = ?, last_name = ?, email = ? where email = ?";
					    PreparedStatement preparedStmt = (PreparedStatement) mysql.getConnection().prepareStatement(query);
					    preparedStmt.setString(1, textFName.getText());
					    preparedStmt.setString(2, textLName.getText());
					    preparedStmt.setString(3, textEmail.getText());
					    preparedStmt.setString(4, ForumMain.this.getUser());
					 
					    // execute the java preparedstatement
					    preparedStmt.executeUpdate();
					} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
					}finally{
							mysql.closeConnection();
					}
					}
			});
			
		}
	}
	class ThreadListPanel extends JPanel{

		private JList list;
		private DefaultListModel model;
		private JButton threadButton = new JButton("New Thread");
		private ArrayList<Integer> threadID = new ArrayList<Integer>();
		private ArrayList<String> threadName = new ArrayList<String>();
		public ThreadListPanel(){
			model = new DefaultListModel();
			this.setVisible(true);
			this.setBackground(Color.WHITE);
			MysqlHelper mysql = new MysqlHelper();
			mysql = new MysqlHelper();
			mysql.setConnection("localhost", "my_first_forum", "root", "0411");
			try{
				String sql = "SELECT id, name FROM thread";
				PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()){
					threadID.add(resultSet.getInt("id"));
					threadName.add(resultSet.getString("name"));
					
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				mysql.closeConnection();
			}
			for(int x = 0; x < threadName.size(); x++){
				model.addElement(threadName.get(x));
			}

			list = new JList(model);
			list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			this.add(list);
			this.add(threadButton, BorderLayout.SOUTH);
			list.addMouseListener(new MouseListener(){
				@Override
				public void mouseClicked(MouseEvent arg0) {
					ForumMain.this.changePanel(new PostViewPanel(threadID.get(list.locationToIndex(arg0.getPoint()))));
				}
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				@Override
				public void mouseExited(MouseEvent e) {
				}
				@Override
				public void mousePressed(MouseEvent e) {
				}
				@Override
				public void mouseReleased(MouseEvent e) {
				}
			});
			threadButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					ForumMain.this.changePanel(new NewThreadPanel());
					
				}
			});
		}
	}
	class NewThreadPanel extends JPanel{
		private JLabel threadName = new JLabel("Thread Name: ");
		private JTextField textName = new JTextField(20);
		private JTextArea textPost = new JTextArea();
		private JButton buttonPost = new JButton("Post");
		private JButton buttonCancel = new JButton("Cancel");
		private GridBagConstraints constraints;
		
		public NewThreadPanel(){
			this.setVisible(true);
			this.setBackground(Color.WHITE);
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(10,10,10,10);
			
			constraints.gridx = 0;
			constraints.gridy = 0;
			this.add(threadName, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 0;
			this.add(textName, constraints);
			
			textPost.append("Write post here\n");
			constraints.gridx = 0;
			constraints.gridy = 1;
			this.add(textPost, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = 2;
			this.add(buttonPost, constraints);
			
			constraints.gridx = 1;
			constraints.gridy = 2;
			this.add(buttonCancel, constraints);
			
			textPost.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {
				}	
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER)
						ForumMain.this.packFrame();
				}
			});
			buttonPost.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MysqlHelper mysql = new MysqlHelper();
					mysql = new MysqlHelper();
					mysql.setConnection("localhost", "my_first_forum", "root", "0411");
					try{
						String sql = "SELECT id FROM user WHERE email='"+ForumMain.this.getUser()+"'";
						PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
						ResultSet resultSet = statement.executeQuery();
						int userID = 0;
						while(resultSet.next()){
							userID = resultSet.getInt("id");
						}
						if(userID > 0){
							String query = "INSERT INTO thread(user_id, name) VALUES(?,?)";
							PreparedStatement preparedStmt = (PreparedStatement) mysql.getConnection().prepareStatement(query);
							preparedStmt.setInt(1, userID);
							preparedStmt.setString(2, textName.getText());
							preparedStmt.execute();
							
							query = "SELECT last_insert_id() FROM thread";
							preparedStmt = (PreparedStatement) mysql.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
							int threadID = 0;
							ResultSet rs = preparedStmt.executeQuery();
							if (rs.next()){
							    threadID = rs.getInt(1);
							}
							query = "INSERT INTO post(user_id, thread_id, post) VALUES(?,?,?)";
							preparedStmt = (PreparedStatement) mysql.getConnection().prepareStatement(query);
							preparedStmt.setInt(1, userID);
							preparedStmt.setInt(2, threadID);
							preparedStmt.setString(3, textPost.getText());
							preparedStmt.execute();
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{
						mysql.closeConnection();
					}					
				}
			});
		}
	}
	class PostViewPanel extends JPanel{
		private JLabel[] user;
		private JTextArea[] displayPost;
		private JTextArea addPost;
		private GridBagConstraints constraints;
		private ArrayList<String> posts = new ArrayList<String>();
		private ArrayList<Integer> userID = new ArrayList<Integer>();
		private ArrayList<String> userName = new ArrayList<String>();
		private JButton postBtn = new JButton("Post");
		public PostViewPanel(final int threadID){
			this.setVisible(true);
			this.setBackground(Color.WHITE);
			this.setLayout(new GridBagLayout());
			constraints = new GridBagConstraints();
			constraints.anchor = GridBagConstraints.WEST;
			constraints.insets = new Insets(10,10,10,10);
			int counter = 0;
			MysqlHelper mysql = new MysqlHelper();
			mysql = new MysqlHelper();
			mysql.setConnection("localhost", "my_first_forum", "root", "0411");
			try{
				String sql = "SELECT user_id, post FROM post WHERE thread_id = "+threadID;
				PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
				ResultSet resultSet = statement.executeQuery();
				while(resultSet.next()){
					userID.add(resultSet.getInt("user_id"));
					posts.add(resultSet.getString("post"));
					counter++;
				}
				for(int x = 0; x < userID.size();  x++){
					sql = "SELECT first_name FROM user WHERE id="+userID.get(x);
					statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
					resultSet = statement.executeQuery();
					while(resultSet.next()){
						userName.add(resultSet.getString("first_name"));
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}finally{
				mysql.closeConnection();
			}
			
			
			displayPost = new JTextArea[counter];
			user = new JLabel[counter];
			addPost = new JTextArea();
			addPost.isEditable();
			addPost.append("Write your post\n");
			addPost.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
				}
				@Override
				public void keyReleased(KeyEvent e) {
				}	
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_ENTER)
						ForumMain.this.packFrame();
				}
			});
			
			post();
			constraints.gridx = 0;
			constraints.gridy = counter+1;
			this.add(addPost, constraints);
			
			constraints.gridx = 0;
			constraints.gridy = counter+2;
			this.add(postBtn, constraints);
			postBtn.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					MysqlHelper mysql = new MysqlHelper();
					mysql = new MysqlHelper();
					mysql.setConnection("localhost", "my_first_forum", "root", "0411");
					try{		
					
						String sql = "SELECT id FROM user WHERE email='"+ForumMain.this.getUser()+"'";
						PreparedStatement statement = (PreparedStatement) mysql.getConnection().prepareStatement(sql);
						ResultSet resultSet = statement.executeQuery();
						int userID = 0;
						while(resultSet.next()){
							userID = resultSet.getInt("id");
						}
					
						String query = "INSERT INTO post(user_id, thread_id, post) VALUES(?,?,?)";
						PreparedStatement preparedStmt = (PreparedStatement) mysql.getConnection().prepareStatement(query);
						preparedStmt.setInt(1, userID);
						preparedStmt.setInt(2, threadID);
						preparedStmt.setString(3, addPost.getText());
						preparedStmt.execute();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}finally{
						mysql.closeConnection();
					}
				}
			});
		}

		private void post(){
			MysqlHelper mysql = new MysqlHelper();
			mysql = new MysqlHelper();
			mysql.setConnection("localhost", "my_first_forum", "root", "0411");

			for(int x = 0; x < displayPost.length; x++){
				user[x] = new JLabel();
				user[x].setText(userName.get(x));
				constraints.gridx = 0;
				constraints.gridy = x;
				this.add(user[x], constraints);
				
				displayPost[x] = new JTextArea();
				displayPost[x].setEditable(false);
				
				displayPost[x].append(posts.get(x));
				constraints.gridx = 1;
				constraints.gridy = x;
				this.add(displayPost[x], constraints);
			}
		}
	}
}
