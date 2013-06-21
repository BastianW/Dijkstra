import javax.swing.JFrame;


public abstract class GUIElementTester {
	public static JFrame getFrame()
	{
		JFrame frame=new JFrame("Test Adjazenzmatrix");
		frame.setSize(500,500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		return frame;
	}
}
