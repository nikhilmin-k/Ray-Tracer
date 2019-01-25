public class Point {
	double x ,y ,z, xinit, y1, zinit;
	Point(double x, double y, double z){
		this.xinit = x;
		this.zinit = z;
		this.y1 = y;
		this.x = xinit;  //init values never change to serve as a baseline to prevent rotation error building up over time
		this.y = y1;
		this.z = zinit;
	}
	Point(double x, double y)
	{
		this.x = x;
		this.y = y;
	}

	public void translate(double xAmt, double yAmt, double zAmt)
	{
		this.x += xAmt;
		this.y += yAmt;
		this.z += zAmt;
	}
	
	public void rotateX(double theta){
		y = y*Math.cos(theta)-z*Math.sin(theta);
		z = y*Math.sin(theta)+z*Math.cos(theta);
	}
	public void rotateY(double theta){
		x = x*Math.cos(theta)+z*Math.sin(theta);
		z = -x*Math.sin(theta)+z*Math.cos(theta);
	}
	public void rotateZ(double theta){
		x = x*Math.cos(theta)-y*Math.sin(theta);
		y = x*Math.sin(theta)+y*Math.cos(theta);
	}
	
	/*public void rotate(double thetaX, double thetaY, double thetaZ) //need to implement rotate to include translated rotations
	{
		
		double z2,y2,x2;
		y2 = (y1*Math.cos(thetaX))-(zinit*Math.sin(thetaX));
		z2 = (y1*Math.sin(thetaX))+(zinit*Math.cos(thetaX));
		
		x2 = (xinit*Math.cos(thetaY))+(z2*Math.sin(thetaY));
		z = xinit*-Math.sin(thetaY)+(z2*Math.cos(thetaY));
		
		x = (x2*Math.cos(thetaZ))-(y2*Math.sin(thetaZ));
		y = (x2*Math.sin(thetaZ))+(y2*Math.cos(thetaZ));
		
	}*/
	public String toString()
	{
		return "("+x+", "+y+", "+z+")";
	}
	public void rotateArbitrary(Vector vect, double theta)
	{
		
		double a = vect.x/vect.magnitude();
		double b = vect.y/vect.magnitude();
		double c = vect.z/vect.magnitude();
		double d = Math.sqrt(b*b+c*c);
		double z2,y2,x2,z3,y3,x3,z4;
		
		y2 = y1*(c/d)-zinit*(b/d);
		z2 = y1*(b/d)+zinit*(c/d);
		
		x2 = xinit*d+z2*(-a);
		z3 = xinit*a+z2*d;
		
		x3 = (x2*Math.cos(theta))-(y2*Math.sin(theta));
		y3 = (x2*Math.sin(theta))+(y2*Math.cos(theta));
		
		x = x3*d+z3*(a);
		z4 = x3*-a+z3*d;
		
		y = y3*(c/d)+z4*(b/d);
		z = -y3*(b/d)+z4*(c/d);
	}
	
	public Vector getVector(){
		return new Vector(this.x,this.y,this.z);
	}
	
	public double distToCenter()
	{
		return Math.sqrt((x*x)+(y*y)+(z*z));
	}
	
	public Point scalarMulti(double num)//returns scalar multiple of original Point
	{
		Point p = new Point(this.x*num,this.y*num,this.z*num);
		return p;
	}
	public Point add(Point point)
	{
		Point p = new Point(this.x+point.x,this.y+point.y,this.z+point.z);
		return p;
	}
	public Point subtract(Point p)
	{
		return this.add(p.scalarMulti(-1));
	}
}
