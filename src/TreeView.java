import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeView extends JPanel {
	private static final long serialVersionUID = 1L;
	private UserGroup root;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private InfoView infoView;
	private int userCount, groupCount, messagesCount, positiveMessages;
	public TreeView(InfoView infoView) {
		userCount = groupCount = messagesCount = positiveMessages = 0;
		this.infoView = infoView;
		this.infoView.acceptTree(this);
		this.root = new UserGroup("Root");
		treeModel = new DefaultTreeModel(createTreeNode(root));
		this.tree = new JTree(treeModel);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode != null) {
                    Object selectedObject = selectedNode.getUserObject();

                    if (selectedObject != null) {
                        if (selectedObject instanceof User) {
                            User selectedUser = (User) selectedObject;
                            handleUserSelection(selectedUser);
                        } else {
                            UserGroup selectedGroup = (UserGroup) selectedObject;
                            handleGroupSelection(selectedGroup);
                        }
                    }
                }
            }
        });
		
		this.setLayout(new BorderLayout());
		this.add(tree);
		reloadTree(root);
		this.setVisible(true);
	}
	
	private DefaultMutableTreeNode createTreeNode(UserGroup group) {
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(group);

        for (User user : group.getUsers()) {
            node.add(new DefaultMutableTreeNode(user));
        }

        for (UserGroup subgroup : group.getSubgroups()) {
            node.add(createTreeNode(subgroup));
        }

        return node;
    }
	
	private void reloadTree(UserGroup rootGroup) {
	    treeModel.setRoot(createTreeNode(rootGroup));
	    treeModel.reload();
	    for(int i = 0; i < tree.getRowCount(); i++) {
	    	tree.expandRow(i);
	    }
	}
	
	private void handleUserSelection(User user) {
        // Perform actions based on the selected user
        infoView.updatePanel(user);
        // Add your custom logic here
    }
	
	private void handleGroupSelection(UserGroup userGroup) {
        // Perform actions based on the selected user
        infoView.updatePanel(userGroup);
        // Add your custom logic here
    }
	
	public void addUser(UserGroup parentGroup, String userId) {
		if(userId.equals("user id")) {
			infoView.showWarningPopup("Enter User Id");
		}else {
			parentGroup.addUser(new User(userId, this));
			++userCount;
			reloadTree(root);			
		}
	}
	public void addGroup(UserGroup parentGroup, String groupId) {
		if(groupId.equals("group id")) {
			infoView.showWarningPopup("Enter Group Id");
		}else {
			++groupCount;
			parentGroup.addSubgroup(new UserGroup(groupId));
			reloadTree(root);			
		}
	}
	
	public User searchUser(UserGroup group, String userId) {
        for (User user : group.getUsers()) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        for (UserGroup subgroup : group.getSubgroups()) {
            User foundUser = searchUser(subgroup, userId);
            if (foundUser != null) {
                return foundUser;
            }
        }

        return null;
    }
	
	public User searchUserFromRoot(String userId) {
        for (User user : root.getUsers()) {
            if (user.getId().equals(userId)) {
                return user;
            }
        }

        for (UserGroup subgroup : root.getSubgroups()) {
            User foundUser = searchUser(subgroup, userId);
            if (foundUser != null) {
                return foundUser;
            }
        }

        return null;
    }
	
	public List<String> getNewsFeed(User user) {
		List<String> following = user.getFollowing();
		List<String> newsFeed = new ArrayList<String>();
		for(String userId: following) {
			User followedUser = searchUser(root, userId);
			for(String tweet: followedUser.getTweets()) {
				newsFeed.add("-  " + followedUser.getId() + ": " + tweet);
			}
		}
		return newsFeed;
	}
	
	public void reloadUserViews() {
		infoView.reloadUserViews();
	}
	
	public int getUserCount() {
		return userCount;
	}
	
	public int getGroupCount() {
		return groupCount;
	}
	
	public void addTweet(String message) {
		if(message.contains("good") || message.contains("great") || message.contains("excellent")) {
			++positiveMessages;
		}
		++messagesCount;
	}
	
	public int getMessagesCount() {
		return messagesCount;
	}
	
	public int getPositiveMessages() {
		return positiveMessages;
	}
	
	public void removeUserView(UserView userView) {
		infoView.removeUserView(userView);
	}
	
}
