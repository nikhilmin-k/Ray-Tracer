import java.util.ArrayList;

public class Ray {
	Point init;
	Vector vect; //vector it points towards
	Point intersection;
	Shape3D interShape; //Shape this Ray intersected with
	Face interFace;
	Ray reflectionRay;  //reflection off surface
	Ray shadowRay;		//ray to light source from intersection point 
	Vector normalVect;		//normal vector on surface intersection
	Shape3D lightSource;   //light source
	double distanceTravelled;
	public Ray(Point init, Vector vect){
		this.init = init;
		this.vect = vect;
	}
	
	public Point findIntersection(ArrayList<Shape3D> shapes)
	{
		ArrayList<Point> interList = new ArrayList<Point>(); //list of points intersected
		ArrayList<Shape3D> interShapes = new ArrayList<Shape3D>(); //list of shapes intersected
		ArrayList<Face> interFaces = new ArrayList<Face>(); //list of faces intersected (in case of polyshape intersection)
		for(Shape3D shape: shapes)
		{
			if(shape instanceof PolyShape)//if this is a polygon
			{
				PolyShape poly = (PolyShape)shape;
				for(Face f: poly.faces)
				{	//defines equation for intersection of Point and plane in terms of t <- independent variable for parameterized equation of line
					double numerator = (f.points.get(0).subtract(init)).getVector().dotProduct(f.getNormal());
					double denominator = vect.dotProduct(f.getNormal());
					double interNum = numerator/denominator;
					Point planePoint = (init.getVector()).add(vect.scalarMulti(interNum));
					//determine whether said point is in polygon
					if(isInPolygon(f,planePoint))
					{
						interShapes.add(shape);
						interList.add(planePoint);
						interFaces.add(f);
					}
				}
			}
			
			if(shape instanceof Sphere) //if this is a sphere
			{
				Sphere sphere = (Sphere)shape;
				Vector toCenter = (init.getVector()).subtract(sphere.center.getVector());
				double toBeSqrt = Math.pow(vect.dotProduct(toCenter),2)-Math.pow(toCenter.magnitude(),2)+Math.pow(sphere.radius,2);
				//System.out.println(toBeSqrt);
				if(toBeSqrt>0)//checks if this does intersect the sphere
				{
					interShapes.add(shape);
					interShapes.add(shape);//adds shape twice because of two intersection points
					double interNum1 = -vect.dotProduct(toCenter)+Math.sqrt(toBeSqrt);//finding both intersections
					double interNum2 = -vect.dotProduct(toCenter)-Math.sqrt(toBeSqrt);
					Point spherePoint1 = (this.init).add(vect.scalarMulti(interNum1));
					Point spherePoint2 = (this.init).add(vect.scalarMulti(interNum2));
					interList.add(spherePoint1);
					interList.add(spherePoint2);
					interFaces.add(null);//adds null twice to keep indexing consistent with other lists.  
					interFaces.add(null);
				}
			}
		}
		
		if(interList.size()==0)//returns null if there is no intersection
		{
			return null;
		}
		
		//returns the first point at which the ray intersects a shape
		Shape3D closestShape = interShapes.get(0);
		Face closestFace = interFaces.get(0);
		Point closest = interList.get(0);
		double closestDist = closest.subtract(init).getVector().magnitude();//initialize distance to start of ray
		for(int i = 0; i<interList.size(); i++)
		{
			double pdist = interList.get(i).subtract(init).getVector().magnitude();//distance from p to start of ray
			if(pdist<closestDist)//if the new distance is closer
			{
				closestShape = interShapes.get(i);
				closestFace = interFaces.get(i);
				closest = interList.get(i);
				closestDist = pdist;
			}
		}
		intersection = closest;
		distanceTravelled = closestDist;
		interShape = closestShape;
		interFace = closestFace;
		this.setBranchingRays(closest);
		return closest;
	}
	
	public void setBranchingRays(Point p){//sets up the rays that start at this point
		Vector refVect = null;
		if(interShape instanceof Sphere)
		{
			Sphere sphere = (Sphere)interShape;
			normalVect = p.subtract(sphere.center).getVector(); //normal is set here
			normalVect = normalVect.scalarMulti(1/normalVect.magnitude()); //set as unit vector
		}
		if(interShape instanceof PolyShape)
		{
			PolyShape poly = (PolyShape)interShape;
			normalVect = interFace.getNormal(); //normal is set here
			normalVect = normalVect.scalarMulti(1/normalVect.magnitude());//set as unit vector
		}
		refVect = vect.add(vect.projectionOn(normalVect).scalarMulti(-2)); //finds the reflection by adding -2 * its projection on the normal
		refVect = refVect.scalarMulti(1/refVect.magnitude()); //resets refVect to a unit vector
		Point adjustedPoint  = p.add(normalVect); //makes sure reflection ray is not directly touching shape it bounces off of
		reflectionRay = new Ray(adjustedPoint, refVect);
	}
	
	public boolean isInPolygon(Face f, Point p)//takes a point on the plane of the face and determines if it is within the polygon
	{
		for(int i = 0; i<f.points.size()-1; i++)
		{
			Vector edgeVector = ((f.points.get(i+1)).subtract(f.points.get(i))).getVector(); //cross these two vectors to determine side of line
			Vector pointVector = p.subtract(f.points.get(i)).getVector();
			if(!f.normal.isInSameDirAs(edgeVector.crossProduct(pointVector)))//if the point is on the right side of the line
			{
				return false;
			}
		}
		//final edge is between last point and first point
		Vector edgeVector = ((f.points.get(0)).subtract(f.points.get(f.points.size()-1))).getVector(); //cross these two vectors to determine side of line
		Vector pointVector = p.subtract(f.points.get(f.points.size()-1)).getVector();
		if(!f.normal.isInSameDirAs(edgeVector.crossProduct(pointVector)))//if the point is on the right side of the line
		{
			return false;
		}
		return true;
	}
	public String toString(){
		return "point: "+init.toString()+" vector: "+vect.toString();
	}
}
