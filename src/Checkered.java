import java.awt.Color;

public class Checkered extends Material{// only works for flooring surfaces (y must be zero)
	double checkerSize;
	Checkered(Color color, double checkerSize) {
		super(color);
		this.checkerSize = checkerSize;
		// TODO Auto-generated constructor stub
	}

	public Ray newRay(Ray oldRay) {
		return null;
	}

	public Color vectColor(Ray oldRay, Point lightLocation) {
		Vector normal = oldRay.normalVect;
		Vector lightVect = lightLocation.subtract(oldRay.intersection).getVector();
		double diffuseScalar = (normal.dotProduct(lightVect))/(normal.magnitude()*lightVect.magnitude());
		if(diffuseScalar<0)
			return Color.BLACK;
		//System.out.println(diffuseScalar);
		materialColor = gridLocationColor(oldRay.intersection);
		Color newColor = new Color((int)(materialColor.getRed()*diffuseScalar),(int)(materialColor.getGreen()*diffuseScalar),(int)(materialColor.getBlue()*diffuseScalar));
		return newColor;
	}
	
	public Color gridLocationColor(Point pt){ //used to create the checkerboard pattern on the floor of the world
		int i;
		int j;
		if(pt.x<0)
		{
			i = (int)Math.ceil(pt.x/checkerSize);
		}
		else
		{
			i = (int)Math.ceil(pt.x/checkerSize);
		}
		if(pt.y<0)
		{
			j = (int)Math.ceil(pt.z/checkerSize);
		}
		else
		{
			j = (int)Math.ceil(pt.z/checkerSize);
		}
		
		if (i % 2 == 1){
            if (j % 2 ==1){
            	return Color.white;
            }
            if (j % 2 ==0){
                return Color.gray;
            }
        }

        if (i % 2 == 0){
            if (j % 2 ==1){ 
                return Color.gray;
            }
            if (j % 2 ==0){
                return Color.white;
            }
        }
		//System.out.println("for point: "+pt.toString());
		/*if ( gridNum % 2 == 0 ) {
			return Color.white;
		} 
		else {
			return Color.gray;
		}	*/
		return Color.gray;  
	}
}
