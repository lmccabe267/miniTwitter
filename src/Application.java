import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JFrame;

public class Application {
	private static Application instance;
	
	TreeView treeView;
	InfoView infoView;
	
	public Application() {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(1280, 720));
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new GridLayout(1, 2));
		frame.setTitle("Mini Twitter");
		
		infoView = new InfoView();
		treeView = new TreeView(infoView);
		
		frame.add(treeView);
		frame.add(infoView);
		frame.setVisible(true);
	}
	
	public static Application getInstance() {
		if(instance == null) {
			instance = new Application();
		}
		return instance;
	}
	
}
