import java.awt.Color;

public class UtilityFunctions {
	public static Color blend(Color c0, Color c1)//blends colors with 50% weights for each
	{
		int red = (int)(c0.getRed()*0.5+c1.getRed()*0.5);
		int green = (int)(c0.getGreen()*0.5+c1.getGreen()*0.5);
		int blue = (int)(c0.getBlue()*0.5+c1.getBlue()*0.5);
		return new Color(red,green,blue);
	}
	public static String colorToString(Color c)
	{
		return "red: "+c.getRed()+" green: "+c.getGreen()+" blue: "+c.getBlue();
	}
}
