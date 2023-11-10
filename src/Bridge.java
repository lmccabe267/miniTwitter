import javax.swing.JPanel;
import javax.swing.JTree;

public class Bridge {
	TreeView tree;
	InfoView infoPanel;
	public Bridge(TreeView tree, InfoView infoPanel) {
		this.tree = tree;
		this.infoPanel = infoPanel;
	}
	
	public void update(User user) {
		infoPanel.updatePanel(user);
	}
}
