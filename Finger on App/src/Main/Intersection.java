package Main;

import java.awt.Rectangle;

public class Intersection extends Rectangle{
	Intersection(int x, int y ){
		super(x,y,1,1);
		
	}
	
	public void move(int clickX,int clickY) {
		
		x = clickX;
		y = clickY;

		
	}
}
