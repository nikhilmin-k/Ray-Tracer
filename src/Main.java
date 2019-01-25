import javax.swing.JFrame;

public class Main{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame();
		
		frame.setTitle("Ray Tracing");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Canvas canvas = new Canvas();
		frame.setSize(canvas.width, canvas.height);
		frame.add(canvas);
		frame.setVisible(true);
	}
}
