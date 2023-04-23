package Main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Circle extends Rectangle{
	
	Circle(int x, int y, int width, int height){
		super(x,y,width,height);
	}
	
	void draw(Graphics2D g2d, int num){
		g2d.setColor(Color.red);
		g2d.fillOval(x, y, width, height);
		
		g2d.setColor(Color.green);
		g2d.drawString(Integer.toString(num), x+35, y+63);
	}
}
