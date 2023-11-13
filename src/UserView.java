import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

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
	private TreeView treeView;
	JScrollPane followScroll, newsScroll;
	JPanel panel1, panel2, panel3, panel4;
	
	public UserView(User user, TreeView treeView) {
		this.treeView = treeView;
		
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(1, 2));
		panel2 = new JPanel();
		panel2.setLayout(new BorderLayout());
		panel3 = new JPanel();
		panel3.setLayout(new GridLayout(1, 2));
		panel4 = new JPanel();
		panel4.setLayout(new BorderLayout());
		
		
		this.setLayout(new GridLayout(4, 1));
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
		
		this.postTweet = new JButton("Post Tweet");
		panel3.add(postTweet);
		
		JLabel newsLabel = new JLabel("News Feed");
		panel4.add(newsLabel, BorderLayout.NORTH);
		this.newsFeed = new JList(treeView.getNewsFeed(user).toArray(new String[0]));
		newsScroll = new JScrollPane(newsFeed);
		panel4.add(newsScroll, BorderLayout.CENTER);
		
		this.setSize(new Dimension(500,500));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		
		this.add(panel1);
		this.add(panel2);
		this.add(panel3);
		this.add(panel4);
		this.setVisible(true);
	}
	
	public void showWarningPopup(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
	
	public void reloadUserView() {
		panel2.remove(followScroll);
		following = new JList(user.getFollowing().toArray(new String[0]));
		followScroll = new JScrollPane(following);
		panel2.add(followScroll);
		
		panel4.remove(newsScroll);
		newsFeed = new JList(treeView.getNewsFeed(user).toArray(new String[0]));
		newsScroll = new JScrollPane(newsFeed);
		panel4.add(newsScroll);
		
		this.setVisible(false);
		this.setVisible(true);
	}
	
}
