package Main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Panel extends JPanel implements Runnable, ActionListener{
	
	private final static int GAME_WIDTH = 500;
	private final static int GAME_HEIGHT = 800;
	private final static Dimension SCREEN_SIZE = new Dimension (GAME_WIDTH,GAME_HEIGHT);
	
	// makes the games general frame rate
	final private int ns = 1000000000/60;
	private static long lastFPSCheck = 0;
	private static long currentFPS = 0;
	private static long totalFrames =0;
	
	boolean started = false;
	boolean showCircle = false;
	boolean failed = false;
	int money = 0;
	int countdown = 5;
	
	String message = "Current earnings £";
	
	
	Thread gameThread;
	Graphics graphics;
	Image image;
	Timer genMoney;
	Timer circleLife;
	Font font;
	Random ran;
	Intersection inter;
	
	Circle circle;
	
	BufferedImage grid;
	
	
	
	Panel(){
		
		ran = new Random();
		newGrid();
		newIntersection();
		newCircle();
		
		font = new Font("Comic sans MS", Font.BOLD,60);
		
		genMoney = new Timer(1500, this);
		circleLife = new Timer(1000,this);
		
		// creates the screen size and add the Action listener
		this.setFocusable(true);
		this.addMouseListener(new ML());
		this.setPreferredSize(SCREEN_SIZE);
						
		// starts game thread
		gameThread = new Thread(this);
		gameThread.start();
		
	}
	void newCircle() {
		int x = 100+ran.nextInt(4)*100;
		int y = 100+ran.nextInt(6)*100;
		
		countdown = 4;
		circle = new Circle(x, y, 100, 100);
		
		
		
	}void newIntersection(){
		inter = new Intersection(0,0);
	}
	void newGrid() {
		try {
			grid = ImageIO.read(getClass().getResource("Grid.PNG"));
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void paint(Graphics g) {
		// puts everything on the screen all at once
		image = createImage(getWidth(),getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
	}
	
	void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setFont(font);
		
		g2d.setColor(new Color(123,51,230));
		g2d.fillRect(0, 0, 500, 100);
		
		g2d.setColor(new Color(0,0,0));
		if(!started) {
			g2d.drawString("Click left mouse button to play!", 10, 75);

		}else {
			
			g2d.drawString(message + Integer.toString(money), 10, 75);
		}
		if(showCircle) {
			circle.draw(g2d, countdown);
		}
		g2d.drawImage(grid, 0, 100, null);
	}
	
	void hitBoxes() {
		if(inter.intersects(circle)) {
			showCircle = false;
		}
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true) {

			//FPS
			totalFrames ++;
			if (System.nanoTime() > lastFPSCheck + ns) {
				lastFPSCheck = System.nanoTime();
				currentFPS = totalFrames/10000;
				totalFrames = 0;
				
				// updates the game's panel and movements
				repaint();
				hitBoxes();
			}
				
		}
	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == genMoney) {
			money += 100;
		}
		
		if(e.getSource() == circleLife) {
			countdown -= 1;
			
			if(countdown == 0) {
				
				if(showCircle) {
					showCircle = false;
					font =new Font("Comic sans MS", Font.BOLD, 30);
					message = "You lost but you earned £";
					genMoney.stop();
					circleLife.stop();
					
					failed = true;
					
				}else {
					newCircle();
					showCircle = true;
				}
					
				
				
			}
		}
	}
	
	public class ML implements MouseListener {
		
		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub
			
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton() == MouseEvent.BUTTON1 && !failed) {
				font =new Font("Comic sans MS", Font.BOLD, 40);
				started = true;
				genMoney.start();
				circleLife.start();
				
			}
			if(e.getButton() == MouseEvent.BUTTON3) {
				inter.move(e.getX(), e.getY());

			}
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getButton() == MouseEvent.BUTTON1) {
				font =new Font("Comic sans MS", Font.BOLD, 30);
				message = "You lost but you earned £";
				
				genMoney.stop();
				circleLife.stop();
				failed =true;

			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
		
	}

	
}
