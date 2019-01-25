import java.util.ArrayList;

public class PolyShape extends Shape3D{
//contains methods pertaining to shapes consisting of polygons and vertices\
	ArrayList<Face> faces; //all faces must all be defined counterclockwise from outside the shape in contructors
	public void rotate(double thetaX, double thetaY, double thetaZ){
		for(Face f: faces)
		{
			for(Point p: f.points)
			{
				p.rotateX(thetaX);
				p.rotateY(thetaY);
				p.rotateZ(thetaZ);
			}
		}
	}
	public void rotateArbitrary(Vector v, double theta)
	{
		for(Face f: faces)
		{
			for(Point p: f.points)
			{
				p.rotateArbitrary(v, theta);
			}
		}
	}
	
	public void translate(double xAmt, double yAmt, double zAmt)
	{
		for(Face f: faces)
		{
			for(Point p: f.points)
			{
				p.translate(xAmt, yAmt, zAmt);
			}
		}
	}
	
	public void getToCenter(){ //method to simply center the PolyShape
		double xTotal = 0;
		double yTotal = 0;
		double zTotal = 0;
		double totalPoints = 0;
		
		for(Face f: faces)
		{
			for(Point p: f.points)
			{
				totalPoints++;
				xTotal += p.x;
				yTotal += p.y;
				zTotal += p.z;
			}
		}
		double xAmt = -xTotal/totalPoints;
		double yAmt = -yTotal/totalPoints;
		double zAmt = -zTotal/totalPoints;
		this.translate(xAmt, yAmt, zAmt);
	}
}
