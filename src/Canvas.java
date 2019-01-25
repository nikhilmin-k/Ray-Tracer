import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Canvas extends JPanel implements KeyListener{
	public int height, width;
	Camera camera;
	int diffuseNum;
	BufferedImage image;
	Cube cube;
	public Canvas()
	{
		image = new BufferedImage(1280,800,BufferedImage.TYPE_INT_RGB);
		height = 800;
		width = 1280;
		camera = new Camera(1000,300,1000,600,-1,-0.3,-1); //edit these values for camera position and orientation
		setPreferredSize(new Dimension(width,height));
		setBackground(Color.WHITE);
		//cube = new Cube(400,new Matte(Color.blue)); //unfinished cube 
		//cube.translate(-50, 500, 300);
		//cube.rotate(20*(180/Math.PI),0,0);
		camera.shapes.add(cube);
		TexturedFace floor = new TexturedFace(10000,10000,new Checkered(Color.GRAY,50));
		camera.shapes.add(new Sphere(200,new Point(-500,200,0),new Shiny(Color.blue)));
		camera.shapes.add(new Sphere(200,new Point(0,200,0),new Shiny(Color.green)));
		camera.shapes.add(new Sphere(200,new Point(500,200,0),new Shiny(Color.red)));
		camera.shapes.add(floor);
		addKeyListener(this);
	}
	public void paintComponent(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		super.paintComponent(g2d);
		g2d.translate(getWidth()/2, getHeight()/2);
		g2d.scale(1, -1);
		camera.traceEverything(height, width, image);
		g2d.drawImage(image, null, -width/2, -height/2);
		try {
		    File outputfile = new File("rayscene.png");
		    ImageIO.write(image, "png", outputfile);
		} catch (IOException e) {
		    System.out.println("image failed to write to output file: ");
		    System.out.println(e);
		}
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		if(arg0.getKeyCode()==KeyEvent.VK_UP)
		{
			System.out.println("cube rotated");
			cube.rotate(0, 10*(Math.PI/180), 0);
			repaint();
		}
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
