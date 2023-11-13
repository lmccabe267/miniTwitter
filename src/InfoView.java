import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class InfoView extends JPanel{
	JPanel userInfo, userViewPanel, userStats;
	JTextArea userId, groupId;
	JButton addUser, addGroup, openUserView, showUserTotal, showGroupTotal, showTotalMessages, showPositive;
	User user = null;
	UserGroup group = null;
	TreeView treeView;
	List<UserView> openViews;
	
	public InfoView() {
		openViews = new ArrayList<UserView>();
		this.setLayout(new GridLayout(3,1));
		userInfo = new JPanel();
		userInfo.setLayout(new GridLayout(2,2));
		
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
		addUser = new JButton("Add User");
		addUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(group != null) {
					if(!userId.getText().equals("") && !userId.getText().equals("user id")) {
						treeView.addUser(group, userId.getText());						
					}else {
						showWarningPopup("Please Enter User Id");
					}
				}else {
					showWarningPopup("Please Select a Group");
				}
			}
			
		});
		groupId = new JTextArea("group id");
		groupId.addFocusListener(new FocusListener() {

			@Override
			public void focusGained(FocusEvent e) {
				if(groupId.getText().equals("group id")) {
					groupId.setText("");
				}
			}

			@Override
			public void focusLost(FocusEvent e) {
				if(groupId.getText().equals("")) {
					groupId.setText("group id");
				}
			}
			
		});
		
		addGroup = new JButton("Add Group");
		addGroup.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(group != null) {
					if(!groupId.getText().equals("") && !groupId.getText().equals("user id")) {
						treeView.addGroup(group, groupId.getText());						
					}else {
						showWarningPopup("Please Enter Group Id");
					}
				}else {
					showWarningPopup("Please Select a Group");
				}
			}
			
		});
		
		userInfo.add(userId);
		userInfo.add(addUser);
		userInfo.add(groupId);
		userInfo.add(addGroup);
		
		
		userViewPanel = new JPanel();
		userViewPanel.setLayout(new GridLayout(1,1));
		
		openUserView = new JButton("Open User View");
		openUserView.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(user != null) {
					openViews.add(new UserView(user, treeView));
				}
			}
			
		});
		
		userViewPanel.add(openUserView);
		
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
		this.add(userViewPanel);
		this.add(userStats);
	}
	
	public void acceptTree(TreeView treeView) {
		this.treeView = treeView;
	}
	
	public void updatePanel(User user) {
		this.user = user;
		this.group = null;
	}
	
	public void updatePanel(UserGroup group) {
		this.group = group;
		this.user = null;
	}
	
	public void showWarningPopup(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }
	
	public void reloadUserViews() {
		for(UserView view: openViews) {
			view.reloadUserView();
		}
	}
}
