import java.awt.Color;

public class Matte extends Material{
	
	Matte(Color color) {
		super(color);
	}

	public Ray newRay(Ray oldRay) {
		return null;
	}

	public Color vectColor(Ray oldRay, Point lightLocation) {//implementation of diffuse shader is here
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
