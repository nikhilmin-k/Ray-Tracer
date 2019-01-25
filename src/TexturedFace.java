import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class TexturedFace extends PolyShape{
	BufferedImage image; //not used at the moment
	double faceLength, faceWidth; 
	TexturedFace(double faceLength, double faceWidth, Material material) {
		this.material = material;
		this.faceLength = faceLength;
		this.faceWidth = faceWidth;
		Point p1 = new Point(0,0,0);
		Point p2 = new Point(faceWidth,0,0);
		Point p3 = new Point(faceWidth,0,faceLength);
		Point p4 = new Point(0,0,faceLength);
		ArrayList <Point> facePoints = new ArrayList<Point>();
		facePoints.add(p4);
		facePoints.add(p3);
		facePoints.add(p2);
		facePoints.add(p1);
		faces = new ArrayList<Face>();
		faces.add(new Face(facePoints));
		
		this.getToCenter();
		
		
		this.image = image;
		// TODO Auto-generated constructor stub
	}
	
	
}
