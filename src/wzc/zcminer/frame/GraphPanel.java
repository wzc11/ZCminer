package wzc.zcminer.frame;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;




import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphPanel extends JPanel{
	public GraphPanel() {
		// TODO Auto-generated constructor stub
		
		setLayout(new GridBagLayout() );
		
		mxGraph graph = new mxGraph();
		Object parent = graph.getDefaultParent();
		
		graph.getModel().beginUpdate();
		try
		{
		/*	Object v1 = graph.insertVertex(parent, null, "Hello", 20, 20, 80,
					30);
			Object v2 = graph.insertVertex(parent, null, "World!", 240, 150,
					80, 30);
			graph.insertEdge(parent, null, "Edge", v1, v2);  */
			Object[] v = new Object[MainFrame.graphNet.activityCount];
			for (int i = 0; i< MainFrame.graphNet.activityCount; i++)
			{
				v[i] = graph.insertVertex(parent, null, MainFrame.graphNet.activityNames[i],
						400, 400, 100, 50);
			}
			
			for (int i = 0; i< MainFrame.graphNet.activityCount; i++)
				for (int j = 0; j< MainFrame.graphNet.activityCount; j++)
				if (MainFrame.graphNet.activityQueFre[i][j] > 50)
				{
					graph.insertEdge(parent, null, MainFrame.graphNet.activityQueFre[i][j] , v[i], v[j]);
				}
			
			
			new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
			new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());
		}
		finally
		{
			graph.getModel().endUpdate();
		}

		mxGraphComponent graphComponent = new mxGraphComponent(graph);
		add(graphComponent,new GridBagConstraints());
	}
	
		
}
