
public class Sphere extends Shape3D{
	public double radius;
	public Point center;
	public Sphere(double radius, Point center, Material material)
	{
		this.center = center;
		this.radius  = radius;
		this.material = material;
	}
}
