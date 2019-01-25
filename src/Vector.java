
public class Vector extends Point{
	
	Vector(double x, double y, double z){
		super(x,y,z);
	}
	public double dotProduct(Vector vect){
		return (vect.x*this.x)+(vect.y*this.y)+(vect.z*this.z);
	}
	public Vector scalarMulti(double num)//returns scalar multiple of original vector
	{
		Vector v = new Vector(this.x*num,this.y*num,this.z*num);
		return v;
	}
	public Vector add(Vector vect)
	{
		Vector v = new Vector(this.x+vect.x,this.y+vect.y,this.z+vect.z);
		return v;
	}
	public Vector subtract(Vector vect)
	{
		return this.add(vect.scalarMulti(-1));
	}
	public Vector crossProduct(Vector vect){
		double term1 = this.y*vect.z-this.z*vect.y;
		double term2 = this.z*vect.x-this.x*vect.z;
		double term3 = this.x*vect.y-this.y*vect.x;
		//System.out.println(term1+","+term2+","+term3);
		return new Vector(term1, term2, term3);
	}
	public double magnitude(){
		return Math.sqrt(this.dotProduct(this)); 
	}
	public double angleBetween(Vector vect){ //returns angle in radians
		double a = this.dotProduct(vect)/(this.magnitude()*vect.magnitude());
		return Math.acos(a);
	}
	public Vector projectionOn(Vector vect){//returns projection of one vector on another
		return vect.scalarMulti(this.dotProduct(vect)/(Math.pow(vect.magnitude(),2)));
	}
	public boolean isInSameDirAs(Vector vect)  //method only used to determine direction of vectors that are scalars of one another
	{
		if(this.x*vect.x>0||this.y*vect.y>0||this.z*vect.z>0)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	public String toString()
	{
		return this.x+","+this.y+","+this.z;
	}
}
