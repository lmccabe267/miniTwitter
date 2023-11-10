import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InfoView extends JPanel{
	JPanel userInfo, userView, userStats;
	JTextArea userId, groupId;
	JButton addUser, addGroup, openUserView, showUserTotal, showGroupTotal, showTotalMessages, showPositive;
	User user = null;
	UserGroup group = null;
	TreeView treeView;
	public InfoView() {
		this.setLayout(new GridLayout(3,1));
		userInfo = new JPanel();
		userInfo.setLayout(new GridLayout(2,2));
		
		userId = new JTextArea("user id");
		addUser = new JButton("Add User");
		addUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(group != null) {
					if(!userId.getText().equals("")) {
						treeView.addUser(group, userId.getText());						
					}else {
						System.out.println("Enter User Id");
					}
				}else {
					System.out.println("Select Group");
				}
			}
			
		});
		groupId = new JTextArea("group id");
		addGroup = new JButton("Add Group");
		
		userInfo.add(userId);
		userInfo.add(addUser);
		userInfo.add(groupId);
		userInfo.add(addGroup);
		
		
		userView = new JPanel();
		userView.setLayout(new GridLayout(1,1));
		
		openUserView = new JButton("Open User View");
		
		userView.add(openUserView);
		
		userStats = new JPanel();
		userStats.setLayout(new GridLayout(2,2));
		
		showUserTotal = new JButton("Show User Total");
		showGroupTotal = new JButton("Show Group Total");
		showTotalMessages = new JButton("Show Messages Total");
		showPositive = new JButton("Show Positive Percentage");
		
		userStats.add(showUserTotal);
		userStats.add(showGroupTotal);
		userStats.add(showTotalMessages);
		userStats.add(showPositive);
		
		this.add(userInfo);
		this.add(userView);
		this.add(userStats);
	}
	
	public void acceptTree(TreeView treeView) {
		this.treeView = treeView;
	}
	
	public void updatePanel(User user) {
		System.out.println("Selected User");
		this.user = user;
		this.group = null;
	}
	
	public void updatePanel(UserGroup group) {
		System.out.println("Selected Group");
		this.group = group;
		this.user = null;
	}
}
