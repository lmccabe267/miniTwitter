import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserView extends JFrame{
	
	private User user;
	private JTextArea userId, tweetMessage;
	private JButton followUser, postTweet;
	private JList following, newsFeed;
	JLabel updatedLabel;
	private TreeView treeView;
	JScrollPane followScroll, newsScroll;
	JPanel panel1, panel2, panel3, panel4, panel5;
	
	public UserView(User user, TreeView treeView) {
		this.treeView = treeView;
		this.setTitle(user.getId());
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		UserView ref = this;
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent e) {
		        treeView.removeUserView(ref);
		    }
		});
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 2));
		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2));
		panel4 = new JPanel();
		panel4.setLayout(new BorderLayout());
		panel5 = new JPanel();
		panel5.setLayout(new BorderLayout());
		
		
		this.setLayout(new GridLayout(5, 1));
		this.user = user;
		userId = new JTextArea("user id");
		userId.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(userId.getText().equals("user id")) {
					userId.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(userId.getText().equals("")) {
					userId.setText("user id");
				}
			}
			
		});
		panel1.add(userId);
		
		followUser = new JButton("Follow User");
		followUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(userId.getText().equals("user id")) {
					showWarningPopup("Enter a user id");
				}else if(user.followUser(userId.getText())) {
					reloadUserView();
				}else {
					showWarningPopup("Couldn't find user with id: " + userId.getText());
				}
				
			}
			
		});
		panel1.add(followUser);
		
		JLabel followingLabel = new JLabel("Current Following");
		panel2.add(followingLabel, BorderLayout.NORTH);
		
		following = new JList(user.getFollowing().toArray(new String[0]));
		followScroll = new JScrollPane(following);
		panel2.add(followScroll, BorderLayout.CENTER);
		
		tweetMessage = new JTextArea("Tweet Message");
		tweetMessage.addFocusListener(new FocusListener() {
			@Override
			public void focusGained(FocusEvent e) {
				if(tweetMessage.getText().equals("Tweet Message")) {
					tweetMessage.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(tweetMessage.getText().equals("")) {
					tweetMessage.setText("Tweet Message");
				}
			}
			
		});
		panel3.add(tweetMessage);
		postTweet = new JButton("Post Tweet");
		postTweet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String message = tweetMessage.getText();
				if(message.equals("Tweet Message") || message.equals("")) {
					showWarningPopup("Please enter a message");
				}else {
					user.tweetMessage(message);
					treeView.addTweet(message);
					user.updateTimestamp();
					treeView.reloadUserViews();
				}
				
			}
			
		});
		panel3.add(postTweet);
		
		JLabel newsLabel = new JLabel("News Feed");
		panel4.add(newsLabel, BorderLayout.NORTH);
		this.newsFeed = new JList(treeView.getNewsFeed(user).toArray(new String[0]));
		newsScroll = new JScrollPane(newsFeed);
		panel4.add(newsScroll, BorderLayout.CENTER);
		
		JLabel createdLabel = new JLabel("Time Created: " + user.getCreatedTime());
		updatedLabel = new JLabel("Time Updated: " + user.getTimestamp());
		panel5.add(createdLabel, BorderLayout.NORTH);
		panel5.add(updatedLabel, BorderLayout.SOUTH);
		
		this.setSize(new Dimension(500,500));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.add(panel5);
		this.setVisible(true);
	}
	
	public void showWarningPopup(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
	
	public void reloadUserView() {
		System.out.println("Reload");
		userId.setText("user id");
		panel2.remove(followScroll);
		following = new JList(user.getFollowing().toArray(new String[0]));
		followScroll = new JScrollPane(following);
		panel2.add(followScroll);
		
		tweetMessage.setText("Tweet Message");
		panel4.remove(newsScroll);
		newsFeed = new JList(treeView.getNewsFeed(user).toArray(new String[0]));
		newsScroll = new JScrollPane(newsFeed);
		panel4.add(newsScroll);
		
		panel5.remove(updatedLabel);
		updatedLabel = new JLabel("Time Updated: " + user.getTimestamp());
		panel5.add(updatedLabel, BorderLayout.SOUTH);
		this.setVisible(false);
		this.setVisible(true);
	}
	
}
