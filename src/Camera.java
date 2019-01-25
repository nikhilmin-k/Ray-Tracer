import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Camera {
	double focal, rotation; //focal is distance from camera to hypothetical screen through which rays are created
	//rotation of camera not implemented yet
	public Point pos;	//position of Camera
	public Vector dir; //direction of Camera
	public Vector angleVect; //used to determine the y axis direction on the camera's pixel screen
	public Vector light; //light source vector, placeholder for later lighting model
	public Point lightLocation;
	public ArrayList<Shape3D> shapes;
	public Ray testingRay;
	public int numRecursions; //number of maximum recursions of reflections
	Camera(double xpos, double ypos, double zpos, double focal, double xvect, double yvect, double zvect)
	{
		numRecursions = 15;
		shapes = new ArrayList<Shape3D>();
		pos = new Point(xpos, ypos, zpos);
		dir = new Vector(xvect, yvect, zvect);
		this.focal = focal;
		lightLocation = new Point(0,1000,1000);
	}
	
	/*public void draw(Graphics2D g2d, Cube cube){
		for(Face f:cube.faces){
			Vector viewVector = new Vector(f.points.get(0).x-pos.x,f.points.get(0).y-pos.y,f.points.get(0).z-pos.z);
			if(f.getNormal().angleBetween(viewVector)>90*(Math.PI/180)||f.getNormal().angleBetween(viewVector)<-90*(Math.PI/180))
			{
				drawFace(f,g2d);
			}
		}
	}*/
	public void traceEverything(int height,int width, BufferedImage image){
		for(int i = 0; i<width; i++)
		{
			for(int j = 0; j<height; j++)
			{
				Ray ray = getInitRayV2(i,j, width, height);
				Color pixelColor = traceRay(ray);
				image.setRGB(i, j, pixelColor.getRGB());
			}
		}
	}
	
	public Color traceRay(Ray cameraRay){
		Point interPoint = cameraRay.findIntersection(shapes);
		if(interPoint == null)//if the ray hits nothing
		{
			return Color.black;
		}
		Color vectColor = cameraRay.interShape.material.vectColor(cameraRay, lightLocation);
		//if(cameraRay.interShape.material instanceof Matte||cameraRay.interShape.material instanceof Shiny||camer)//if the material has a diffuse layer and can have a shadow cast on it
		//{
			//System.out.println(lightLocation.y+", "+cameraRay.intersection.y);
			Vector shadowVect = lightLocation.subtract(cameraRay.intersection).getVector(); 
			shadowVect = shadowVect.scalarMulti(1/shadowVect.magnitude());
			cameraRay.shadowRay = new Ray(cameraRay.intersection,shadowVect); //creates ray from point to light source
			ArrayList<Shape3D> shadowBlockers = (ArrayList<Shape3D>)shapes.clone();
			shadowBlockers.remove(cameraRay.interShape); //removing the shape the shadowRay is supposed to start from
			cameraRay.shadowRay.findIntersection(shadowBlockers);
			if(cameraRay.interShape.material instanceof Matte|| cameraRay.interShape.material instanceof Checkered) //if this is a non-reflective surface
			{
				if(cameraRay.shadowRay.intersection==null)//if there is no shadow
				{
					return vectColor;
				}
				else//if there is a shadow
				{
					return vectColor.darker().darker();
				}
			}
			if(cameraRay.interShape.material instanceof Shiny)
			{
				if(numRecursions==0)
				{
					numRecursions = 15;
					return vectColor;
				}
				numRecursions--;
				
				//System.out.println(cameraRay.reflectionRay.toString());
				Color reflectionColor = traceRay(cameraRay.reflectionRay);
				//System.out.println(UtilityFunctions.colorToString(reflectionColor));
				Color mixedColor = UtilityFunctions.blend(vectColor,reflectionColor);
				return mixedColor;
			}
		//}
		//rulesets for different materials with no return having hit it
		if(cameraRay.interShape instanceof PolyShape)//returns green for polyshape and nothing shows up
			return Color.green;
		return Color.gray; //returns gray if nothing shows up
	}
	
	public void drawWire(Graphics2D g2d, PolyShape shape)
	{
		for(Face f:shape.faces){
			for(int i = 0; i<f.points.size()-1; i++)//minus 1 is because we are drawing edges, not vertexes
			{
				Point p1 = translatePoint(f.points.get(i));
				Point p2 = translatePoint(f.points.get(i+1));
				g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
			}
			Point p1 = translatePoint(f.points.get(3));
			Point p2 = translatePoint(f.points.get(0));
			g2d.drawLine((int)p1.x, (int)p1.y, (int)p2.x, (int)p2.y);
		}
	}
	
	public void drawTexturedFace(){
		
	}
	
	/*public void drawFace(Face f, Graphics2D g2d){
		int vertexNum = f.points.size();
		int[] Xints = new int[vertexNum];
		int[] Yints = new int[vertexNum];
		for(int i = 0; i<vertexNum; i++)
		{
			Point p = translatePoint(f.points.get(i));
			Xints[i] = (int)p.x;
			Yints[i] = (int)p.y;
		}
		double shadeRatio = f.getNormal().angleBetween(light)/Math.PI;
		g2d.setColor(new Color((int)(50+205*shadeRatio),0,0));
		g2d.fillPolygon(Xints, Yints, 4);
	}*/
	
	public Point translatePoint(Point p){
		Vector imageVect = new Vector(p.x-pos.x, p.y-pos.y, p.z-pos.z); //creating vector from camera to point
		double theta = imageVect.angleBetween(dir); //viewing angle of point
		if(theta==0)
		{
			return new Point(0,0);  //case that a point is right in the camera's direction
		}
		System.out.println("viewing angle: "+theta*(180/Math.PI));
		Vector inclinationVect = imageVect.crossProduct(dir); //vector used to determine the inclination of point (angle for polar graphing)
		double a = inclinationVect.angleBetween(angleVect);
		double phi = 0;
		if(angleVect.crossProduct(inclinationVect).isInSameDirAs(dir))//determines whether angle phi is positive or negative
		{
			phi = a;
		}
		else
		{
			phi = 2*Math.PI-a;
		}
		System.out.println("inclination angle:"+phi*(180/Math.PI));
		double r = Math.tan(theta)*focal; //r value for polar graphing onto 2 dimensional surface
		double finalX = r*Math.cos(phi);
		double finalY = r*Math.sin(phi);
		return new Point(finalX, finalY);
	}
	
	public Ray getInitRayV2(int pixelX, int pixelY, int width, int height)
	{
		Vector perpVect = new Vector(1,0,0); //vector that is straight sideways
		if(dir.y==0&&dir.z==0)//if the camera is in teh same direction as the sideways vector
			perpVect = new Vector(0,0,1);
		if(perpVect.crossProduct(dir).y>0)
			angleVect = perpVect.crossProduct(dir); //crossing gets vector in direction of screen y axis
		else
			angleVect = dir.crossProduct(perpVect);//case if crossing results in opposite direction
		Vector focalVect = dir.scalarMulti(1/dir.magnitude()).scalarMulti(this.focal);  //creating dir with size of focal length(distance from camera to hypothetical screen)
		Vector YUnit = angleVect.scalarMulti(1/angleVect.magnitude()); //creates unit vector in the 3d world coordinate system that is aligned with the y axis of the screen's coordinate system
		Vector XUnit = dir.crossProduct(YUnit); //do the same for the x axis
		XUnit = XUnit.scalarMulti(1/XUnit.magnitude());
		Vector pixelVect = focalVect.add(XUnit.scalarMulti(pixelX-width/2)).add(YUnit.scalarMulti(pixelY-height/2));//scale the unit vectors accordingly and adds to vector pointing straight
		//use of -1/2 of screen dimensions and negative y unit vector is necessary to compensate for BufferedImage drawRGB method
		pixelVect = pixelVect.scalarMulti(1/pixelVect.magnitude());
		if(pixelX==800&&pixelY==100)
		{
			System.out.println(pixelVect.toString());
			testingRay = new Ray(this.pos, pixelVect);
		}
		return new Ray(this.pos, pixelVect);
	}
}
