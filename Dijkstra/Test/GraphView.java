import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.bwvaachen.graph.gui.input.VisualGraph;
import de.bwvaachen.graph.logic.Graph;


public class GraphView extends GUIElementTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			Graph g=Graph.load("test1223.txt");
			JFrame frame=getFrame();
			frame.setLayout(new BorderLayout());
			frame.add(new VisualGraph(g), BorderLayout.CENTER);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

}
