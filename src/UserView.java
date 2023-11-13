import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class UserView extends JFrame{
	
	private User user;
	private JTextArea userId, tweetMessage;
	private JButton followUser, postTweet;
	private JList following, newsFeed;
	private TreeView treeView;
	
	public UserView(User user, TreeView treeView) {
		this.treeView = treeView;
		JPanel panel1, panel2, panel3, panel4;
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
		this.userId = new JTextArea("user id");
		panel1.add(userId);
		
		this.followUser = new JButton("Follow User");
		panel1.add(followUser);
		
		JLabel followingLabel = new JLabel("Current Following");
		panel2.add(followingLabel, BorderLayout.NORTH);
		
		this.following = new JList(user.getFollowing().toArray(new String[0]));
		JScrollPane followScroll = new JScrollPane(following);
		panel2.add(followScroll, BorderLayout.CENTER);
		
		this.tweetMessage = new JTextArea("Tweet Mesaage");
		panel3.add(tweetMessage);
		
		this.postTweet = new JButton("Post Tweet");
		panel3.add(postTweet);
		
		JLabel newsLabel = new JLabel("News Feed");
		panel4.add(newsLabel, BorderLayout.NORTH);
		this.newsFeed = new JList(treeView.getNewsFeed(user).toArray(new String[0]));
		JScrollPane newsScroll = new JScrollPane(newsFeed);
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
	
	
	
}
