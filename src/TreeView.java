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
	
	public TreeView(InfoView infoView) {
		
		this.infoView = infoView;
		this.infoView.acceptTree(this);
		this.root = createGroupHierarchy();
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
	
	private static UserGroup createGroupHierarchy() {
        // Create a sample hierarchy
        User user1 = new User("Alice");
        User user2 = new User("Bob");
        User user3 = new User("Charlie");
        
        user1.followUser("Bob");
        user1.followUser("Charlie");
        
        user2.tweetMessage("hello there");
        user3.tweetMessage("goodybye");
        
        
        UserGroup rootGroup = new UserGroup("Root Group");
        UserGroup group1 = new UserGroup("Group 1");
        UserGroup group2 = new UserGroup("Group 2");
        UserGroup subgroup1 = new UserGroup("Subgroup 1");
        UserGroup subgroup2 = new UserGroup("Subgroup 2");

        group1.addUser(user1);
        group1.addUser(user2);

        subgroup1.addUser(user3);
        group2.addSubgroup(subgroup1);

        subgroup2.addSubgroup(new UserGroup("Subgroup 2.1")); // Creating a subgroup within subgroup 2
        group2.addSubgroup(subgroup2);

        rootGroup.addSubgroup(group1);
        rootGroup.addSubgroup(group2);

        return rootGroup;
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
			parentGroup.addUser(new User(userId));
			reloadTree(root);			
		}
	}
	public void addGroup(UserGroup parentGroup, String groupId) {
		if(groupId.equals("group id")) {
			infoView.showWarningPopup("Enter Group Id");
		}else {
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
	
}
