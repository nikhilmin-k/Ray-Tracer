import java.awt.Color;
import java.util.ArrayList;

public class Cube extends PolyShape{
	
	public double sideLength;
	Cube(double sideLength, Material material) 
	{
		this.material = material;
		this.sideLength = sideLength;
		Point p1 = new Point(0,0,0); //all points on a cube
		Point p2 = new Point(0,1*sideLength,0);
		Point p3 = new Point(1*sideLength,1*sideLength,0);
		Point p4 = new Point(1*sideLength,0,0);
		Point p5 = new Point(0,0,1*sideLength);
		Point p6 = new Point(0,1*sideLength,1*sideLength);
		Point p7 = new Point(1*sideLength,1*sideLength,1*sideLength);
		Point p8 = new Point(1*sideLength,0,1*sideLength);
		
		
		//all faces of a cube with corresponding points placed into lists counterclockwise rotation
		ArrayList<Point> points1 = new ArrayList<Point>();
		points1.add(p1); points1.add(p2); points1.add(p3); points1.add(p4);
		Face f1 = new Face(points1);
		
		ArrayList<Point> points2 = new ArrayList<Point>();
		points2.add(p5); points2.add(p6); points2.add(p2); points2.add(p1);
		Face f2 = new Face(points2);
		
		ArrayList<Point> points3 = new ArrayList<Point>();
		points3.add(p8); points3.add(p7); points3.add(p6); points3.add(p5);
		Face f3 = new Face(points3);
		
		ArrayList<Point> points4 = new ArrayList<Point>();
		points4.add(p4); points4.add(p3); points4.add(p7); points4.add(p8);
		Face f4 = new Face(points4);
		
		ArrayList<Point> points5 = new ArrayList<Point>();
		points5.add(p5); points5.add(p1); points5.add(p4); points5.add(p8);
		Face f5 = new Face(points5);
		
		ArrayList<Point> points6 = new ArrayList<Point>();
		points6.add(p7); points6.add(p3); points6.add(p2); points6.add(p6);
		Face f6 = new Face(points6);
		
		ArrayList<Face> faces = new ArrayList<Face>();
		faces.add(f1); faces.add(f2); faces.add(f3); faces.add(f4); faces.add(f5); faces.add(f6);
		this.faces = faces;
		this.getToCenter();
	}
}
