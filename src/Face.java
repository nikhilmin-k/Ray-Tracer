import java.awt.Color;
import java.awt.Polygon;
import java.util.ArrayList;

public class Face{
	Vector normal;
	Color color;
	public ArrayList<Point> points;
	Face(ArrayList<Point> points){
		this.points = points;
	}
	public Vector getNormal(){//returns a normal vector (NOT A UNIT VECTOR)
		Vector v1 = new Vector(points.get(1).x-points.get(0).x,points.get(1).y-points.get(0).y,points.get(1).z-points.get(0).z);
		Vector v2 = new Vector(points.get(2).x-points.get(1).x,points.get(2).y-points.get(1).y,points.get(2).z-points.get(1).z);
		normal = v1.crossProduct(v2);
		return normal;
	}
	
}
