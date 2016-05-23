package program;

import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JanelaControle extends Frame{
	
	public JanelaControle(){
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(640, 480);
		this.setLocation((java.awt.Toolkit.getDefaultToolkit().getScreenSize().width - 640)/2, (java.awt.Toolkit.getDefaultToolkit().getScreenSize().height - 480)/2);
		this.setTitle("Academia LBD");
		this.setVisible(true);
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				JanelaPrincipal jP = new JanelaPrincipal();
				dispose();
			}
		});
	}
	
}
