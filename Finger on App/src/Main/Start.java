package Main;

import java.awt.Color;

import javax.swing.JFrame;

public class Start extends JFrame{
	
	
	Start(){
		
		this.add(new Panel());
		this.setTitle("Finger on App");
		this.setResizable(false);
		this.setBackground(new Color(205,205,55));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		Start start = new Start();
	}
}
