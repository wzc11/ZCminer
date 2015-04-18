package wzc.zcminer.frame;

import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;


import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;




import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

public class GraphPanel extends JPanel{
	static JSlider pathSlider;
	static JSlider activitySlider;
	static JPanel sliderJPanel;
	mxGraphComponent graphComponent;
	mxGraph graph;
	Object parent;
	public GraphPanel() {
		// TODO Auto-generated constructor stub
		
		setLayout(new GridBagLayout() );
		
		sliderJPanel = new JPanel();
		sliderJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pathSlider = new JSlider(JSlider.VERTICAL);
		pathSlider.setMinimum(0);
		int maxActivityQueFre = MainFrame.graphNet.getMaxActivityQueFre();
		pathSlider.setMaximum(maxActivityQueFre / 10);
		pathSlider.setValue(maxActivityQueFre / 10);
		pathSlider.setExtent(maxActivityQueFre / 100);

		
		pathSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (pathSlider.getValueIsAdjusting() != true){
					paintGraph();
				}
			}
        });
		sliderJPanel.add(pathSlider);
		
		graph = new mxGraph();
		parent = graph.getDefaultParent();
		graphComponent = new mxGraphComponent(graph);
		paintGraph();
		GridBagConstraints grapgBagConstraints = new GridBagConstraints();
		grapgBagConstraints.gridwidth = 3;
		grapgBagConstraints.gridheight = 1;
		grapgBagConstraints.weightx = 0.7;
		grapgBagConstraints.weighty = 1;
		grapgBagConstraints.fill = GridBagConstraints.BOTH;
		add(graphComponent,grapgBagConstraints);
		
		GridBagConstraints sliderBagConstraints = new GridBagConstraints();
		sliderBagConstraints.gridwidth = 1;
		sliderBagConstraints.gridheight = 1;
		sliderBagConstraints.weightx = 0.3;
		sliderBagConstraints.weighty = 1;
		sliderBagConstraints.fill = GridBagConstraints.BOTH;
		add(sliderJPanel, sliderBagConstraints );
		
		
	}
	
	public void paintGraph() {
		graph.getModel().beginUpdate();
		try
		{
			graph.selectAll();
			graph.removeCells();
			Object[] v = new Object[MainFrame.graphNet.activityCount];
			for (int i = 0; i< MainFrame.graphNet.activityCount; i++)
			{
				v[i] = graph.insertVertex(parent, null, MainFrame.graphNet.activityNames[i],
						400, 400, 100, 50);
			}
			
			for (int i = 0; i< MainFrame.graphNet.activityCount; i++)
				for (int j = 0; j< MainFrame.graphNet.activityCount; j++)
				if (MainFrame.graphNet.activityQueFre[i][j] > pathSlider.getValue())
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
		
		graphComponent.validateGraph();
		
	}
		
}
