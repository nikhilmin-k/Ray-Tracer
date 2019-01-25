import java.awt.Color;

public abstract class Material {
	Color materialColor;
	Material(Color color)
	{
		materialColor = color;
	}
	public abstract Ray newRay(Ray oldRay); //returns new ray that goes to search for other colors
	public abstract Color vectColor(Ray oldRay, Point lightLocation); //returns color of the given object at the intersection point
}
