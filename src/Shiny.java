import java.awt.Color;

public class Shiny extends Material{

	Shiny(Color color) {
		super(color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Ray newRay(Ray oldRay) {
		return oldRay.reflectionRay;
	}

	@Override
	public Color vectColor(Ray oldRay, Point lightLocation) { //applies diffuse along with the reflection
		Vector normal = oldRay.normalVect;
		Vector lightVect = lightLocation.subtract(oldRay.intersection).getVector();
		double diffuseScalar = (normal.dotProduct(lightVect))/(normal.magnitude()*lightVect.magnitude());
		if(diffuseScalar<0)
			return Color.BLACK;
		//System.out.println(diffuseScalar);
		Color newColor = new Color((int)(materialColor.getRed()*diffuseScalar),(int)(materialColor.getGreen()*diffuseScalar),(int)(materialColor.getBlue()*diffuseScalar));
		return newColor;
	}
}
