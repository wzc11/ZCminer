package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;


import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;




import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;

public class GraphPanel extends JPanel implements ComponentListener{
	static JSlider pathSlider;
	static JSlider activitySlider;
	static JPanel sliderJPanel;
	mxGraphComponent graphComponent;
	mxGraph graph;
	Object parent;
	public GraphPanel() {
		// TODO Auto-generated constructor stub
		
		setLayout(new BorderLayout());
		
		sliderJPanel = new JPanel();
		sliderJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		pathSlider = new JSlider(JSlider.VERTICAL);
		pathSlider.setMinimum(0);
		int maxActivityQueFre = MainFrame.graphNet.getMaxActivityQueFre();
		pathSlider.setMaximum(maxActivityQueFre / 10);
		pathSlider.setValue(maxActivityQueFre / 10);
		pathSlider.setExtent(maxActivityQueFre / 300);

		
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
/*		GridBagConstraints grapgBagConstraints = new GridBagConstraints();
		grapgBagConstraints.gridwidth = 3;
		grapgBagConstraints.gridheight = 1;
		grapgBagConstraints.weightx = 0.7;
		grapgBagConstraints.weighty = 1;
		grapgBagConstraints.fill = GridBagConstraints.NONE;   */
		add(graphComponent,BorderLayout.CENTER);  
		
	/*	GridBagConstraints sliderBagConstraints = new GridBagConstraints();
		sliderBagConstraints.gridwidth = 1;
		sliderBagConstraints.gridheight = 1;
		sliderBagConstraints.weightx = 0.3;
		sliderBagConstraints.weighty = 1;
		sliderBagConstraints.fill = GridBagConstraints.BOTH;  */
		add(sliderJPanel, BorderLayout.EAST);
		graphComponent.addComponentListener(this);
		
	}

	
	
	public void paintGraph() {
		graph.getModel().beginUpdate();
		try
		{
			graph.selectAll();
			graph.removeCells();
			Object[] v = new Object[MainFrame.graphNet.activityCount];
			v[0] = graph.insertVertex(parent, null, MainFrame.graphNet.activityNames[0],
					400, 400, 50, 20);
			v[1] = graph.insertVertex(parent, null, MainFrame.graphNet.activityNames[1],
					400, 400, 50, 20);
			
			for (int i = 2; i< MainFrame.graphNet.activityCount; i++)
			{
				v[i] = graph.insertVertex(parent, null, MainFrame.graphNet.activityNames[i],
						400, 400, 80, 40);
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
		ZoomtoFit();
		ZoomtoCenter();
	}
	
	public void ZoomtoFit() {
		
		double newScale = 1;
		mxRectangle graphSize = graph.getView().getGraphBounds();
        Dimension viewPortSize = graphComponent.getViewport().getSize();

        int gw = (int) graphSize.getWidth();
        int gh = (int) graphSize.getHeight();

        if (gw > 0 && gh > 0) {
            int w = (int) viewPortSize.getWidth();
            int h = (int) viewPortSize.getHeight();

            newScale = Math.min((double) w / gw, (double) h / gh);
        }
        if (newScale != 0){
        	graphComponent.zoom(newScale);
        }
	}
	
	public void ZoomtoCenter() {
	       mxRectangle graphSize = graph.getView().getGraphBounds();
           Dimension viewPortSize = graphComponent.getViewport().getSize();
           int x = (int)graphSize.getWidth()/2 - viewPortSize.width/2;
           int y = (int)graphSize.getHeight()/2 - viewPortSize.height/2;

           graph.getView().setTranslate(new mxPoint(-x,-y));
	}
	@Override
	public void componentMoved(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void componentShown(ComponentEvent e) {
		// TODO Auto-generated method stub
		ZoomtoFit();
		ZoomtoCenter();
	}
	@Override
	public void componentHidden(ComponentEvent e) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void componentResized(ComponentEvent e) {
		// TODO Auto-generated method stub
		ZoomtoFit();
		ZoomtoCenter();
	}
	
		
}
