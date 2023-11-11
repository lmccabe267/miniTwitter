import java.awt.Dimension;

import javax.swing.JFrame;

public class UserView extends JFrame{
	
	User user;
	public UserView(User user) {
		this.user = user;
		
		this.setSize(new Dimension(500,500));
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		
	}
	
	
}
