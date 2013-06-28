import java.awt.BorderLayout;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.JFrame;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import de.bwvaachen.graph.gui.input.DijkstraVisualisation;
import de.bwvaachen.graph.logic.Graph;
import de.bwvaachen.graph.logic.Node;


public class GraphView extends GUIElementTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			
			Graph g=Graph.load("BigTest.txt");
			JFrame frame=getFrame();
			frame.setLayout(new BorderLayout());
			//frame.add(new VisualGraph(g), BorderLayout.CENTER);
			Iterator<Node> iterator = g.getNodes().iterator();
			iterator.next();
			Node node=iterator.next();
			System.out.println("StartNode "+ node);
				
			frame.add(new DijkstraVisualisation(g,node), BorderLayout.CENTER);
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		

	}

}
