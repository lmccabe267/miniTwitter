import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class TreeView extends JPanel {
	private UserGroup root;
	private JTree tree;
	private DefaultTreeModel treeModel;
	private InfoView infoView;
	
	public TreeView(InfoView infoView) {
		
		this.infoView = infoView;
		
		this.root = createGroupHierarchy();
		treeModel = new DefaultTreeModel(createTreeNode(root));
		this.tree = new JTree(treeModel);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

                if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
                    User selectedUser = (User) selectedNode.getUserObject();
                    handleUserSelection(selectedUser);
                }
            }
        });
		
		this.setLayout(new BorderLayout());
		this.add(tree);
		this.setVisible(true);
	}
	
	public void reload() {
		
	}
	
	private static UserGroup createGroupHierarchy() {
        // Create a sample hierarchy
        User user1 = new User("1", "Alice", "Smith");
        User user2 = new User("2", "Bob", "Jones");
        User user3 = new User("3", "Charlie", "Brown");

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
        DefaultMutableTreeNode node = new DefaultMutableTreeNode(group.getId());

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
	}
	
	private void handleUserSelection(User user) {
        // Perform actions based on the selected user
        infoView.updatePanel(user);
        // Add your custom logic here
    }
}
