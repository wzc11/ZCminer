package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Arrays;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import wzc.zcminer.global.EventCase;
import jdk.internal.org.objectweb.asm.tree.analysis.Value;

import com.mxgraph.layout.mxParallelEdgeLayout;
import com.mxgraph.layout.hierarchical.mxHierarchicalLayout;
import com.mxgraph.layout.orthogonal.mxOrthogonalLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.util.mxConstants;
import com.mxgraph.util.mxPoint;
import com.mxgraph.util.mxRectangle;
import com.mxgraph.view.mxGraph;
import com.sun.media.jfxmedia.events.NewFrameEvent;

public class GraphPanel extends JPanel implements ComponentListener {
	static JSlider pathSlider;
	static JSlider activitySlider;
	static JComboBox<String> modelType;
	static JPanel sliderJPanel;
	static JPanel labelPanel;
	static JPanel centerPanel;
	static JPanel controlPanel;
	static JButton animationButton;
	mxGraphComponent graphComponent;
	mxGraph graph;
	Object parent;
	Timer timer;
	int timeFlag;
	long currentTime;
	Object[]  v;
	public GraphPanel() {
		// TODO Auto-generated constructor stub

		setLayout(new BorderLayout());

		controlPanel = new JPanel();
		controlPanel.setLayout(new BorderLayout());
		
		centerPanel = new JPanel(new BorderLayout());
		
		sliderJPanel = new JPanel();
		sliderJPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		labelPanel.add(new JLabel("Path"));
		labelPanel.add(new JLabel("Activity"));
		
		String[] boxString = {"Absolute Frequence", "Total Duration", "Mean Duration", "Max Duration"
				, "Min Duration"};
		modelType = new JComboBox<String>(boxString);
		modelType.setSelectedItem("Absolute Frequence");
		modelType.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent evt) {
				if(evt.getStateChange() == ItemEvent.SELECTED){
					paintGraph();
				} 
			}
		});

		
		pathSlider = new JSlider(JSlider.VERTICAL);
		pathSlider.setMinimum(0);
		int activityCount = MainFrame.graphNet.activityCount - 1;
		pathSlider.setMaximum(activityCount * activityCount);
		pathSlider.setValue(activityCount * activityCount);
		pathSlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (pathSlider.getValueIsAdjusting() != true) {
					paintGraph();
				}
			}
		});
		sliderJPanel.add(pathSlider);

		activitySlider = new JSlider(JSlider.VERTICAL);
		activitySlider.setMinimum(0);
		activitySlider.setMaximum(MainFrame.graphNet.activityCount);
		activitySlider.setValue(0);
		activitySlider.setExtent(1);
		activitySlider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				// TODO Auto-generated method stub
				if (activitySlider.getValueIsAdjusting() != true) {
					paintGraph();
				}
			}
		});
		sliderJPanel.add(activitySlider);

		centerPanel.add(sliderJPanel,BorderLayout.CENTER);
		
		timeFlag = 0;
		animationButton = new JButton("播放动画");
		animationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (timeFlag == 0){
					timeFlag = 1;
					animationButton.setText("结束播放");
					paintAnimation();
				} else{
					animationButton.setText("播放动画");
					timeFlag = 0;
					paintGraph();
					timer.cancel();
				}
			}
		});
		centerPanel.add(animationButton,BorderLayout.SOUTH);
		
		
		controlPanel.add(labelPanel, BorderLayout.NORTH);
		controlPanel.add(centerPanel, BorderLayout.CENTER);
		controlPanel.add(modelType, BorderLayout.SOUTH);
		
		graph = new mxGraph();
		parent = graph.getDefaultParent();
		graphComponent = new mxGraphComponent(graph);
		paintGraph();
		/*
		 * GridBagConstraints grapgBagConstraints = new GridBagConstraints();
		 * grapgBagConstraints.gridwidth = 3; grapgBagConstraints.gridheight =
		 * 1; grapgBagConstraints.weightx = 0.7; grapgBagConstraints.weighty =
		 * 1; grapgBagConstraints.fill = GridBagConstraints.NONE;
		 */
		add(graphComponent, BorderLayout.CENTER);

		/*
		 * GridBagConstraints sliderBagConstraints = new GridBagConstraints();
		 * sliderBagConstraints.gridwidth = 1; sliderBagConstraints.gridheight =
		 * 1; sliderBagConstraints.weightx = 0.3; sliderBagConstraints.weighty =
		 * 1; sliderBagConstraints.fill = GridBagConstraints.BOTH;
		 */
		add(controlPanel, BorderLayout.EAST);
		graphComponent.addComponentListener(this);

	}
	
	public void paintAnimation(){
		timer = new Timer();  
		currentTime = MainFrame.graphNet.beginTime;
		System.out.println(MainFrame.caseCollection.getSize());
		
        timer.scheduleAtFixedRate(new TimerTask() {  
            public void run() {  
            	currentTime++;
            	System.out.println(currentTime);
            	if (currentTime > MainFrame.graphNet.endTime){
            		cancel();
            	}
            	
            	int[] activityEvent = new int[MainFrame.graphNet.activityCount];
            	int[][] activityEventEdge = new int[MainFrame.graphNet.activityCount][MainFrame.graphNet.activityCount];
            	
            	int lastActivityId = -1;
            	String lastCase = "";
            	Date lastDate = new Date();
            	int[] temp = MainFrame.graphNet.activityFre.clone();
    			Arrays.sort(temp);
    			
            	for (int i = 0 ; i < MainFrame.caseCollection.getSize(); i++){
            		
            		EventCase eventCase = MainFrame.caseCollection
							.getCase(i);
            		String caseName = eventCase.getCase();
            		String activityName = eventCase.getActivity();
            		int activityId = MainFrame.graphNet
							.getActivityId(activityName);
        			if (MainFrame.graphNet.activityFre[activityId] < temp[activitySlider
        			                      						.getValue()]) {
        				continue;
        			}
            		if (eventCase.getStartDate().getTime()/ (1000*60*60) <= currentTime && 
            				eventCase.getEndDate().getTime()/ (1000*60*60) >= currentTime){
            			//graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{v[activityId]}); 
            			activityEvent[activityId]++;
            		}
            		
            		if (caseName.equals(lastCase)){
            			if (eventCase.getStartDate().getTime()/ (1000*60*60) >= currentTime && 
                				lastDate.getTime()/ (1000*60*60) <= currentTime){
            				activityEventEdge[lastActivityId][activityId]++;
            			}
            		}
            		
            		lastCase = caseName;
            		lastDate = eventCase.getEndDate();
            		lastActivityId = activityId;
            	}
            	
            	for (int i = 2; i< MainFrame.graphNet.activityCount; i++){
            		if (activityEvent[i] > 0){
            			graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "yellow", new Object[]{v[i]}); 
            		} else{
            			graph.setCellStyles(mxConstants.STYLE_FILLCOLOR, "#BFEFFF",new Object[]{v[i]}); 
            		}
            	}
	            	
            	for (int i = 2; i< MainFrame.graphNet.activityCount;i++)
            	if (MainFrame.graphNet.activityFre[i] >= temp[activitySlider
     			               								.getValue()]){
            		for (int j = 2; j< MainFrame.graphNet.activityCount; j++){
            			if ( MainFrame.graphNet.activityFre[j] >= temp[activitySlider
            			               								.getValue()]
            			               								&& MainFrame.graphNet.activityQueFre[i][j] >= MainFrame.graphNet.activityQueFreSort.
            			               								get(pathSlider.getValue()) ){ 
            				if(   activityEventEdge[i][j] > 0 ){
            					graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "red",graph.getEdgesBetween(v[i], v[j]));
            			
            				} else{
            					graph.setCellStyles(mxConstants.STYLE_STROKECOLOR, "#515151",graph.getEdgesBetween(v[i], v[j]));
            				}
            			}
            		}
            	}
            	
            	
        		graphComponent.refresh();
            }
        }, 0, 500);
	}
	public void paintGraph() { 
		graph.getModel().beginUpdate();
		try {
			int[] temp = MainFrame.graphNet.activityFre.clone();
			Arrays.sort(temp);
			
			graph.selectAll();
			graph.removeCells();
			v = new Object[MainFrame.graphNet.activityCount];
			v[0] = graph.insertVertex(parent, null,
					MainFrame.graphNet.activityNames[0], 400, 400, 50, 20,"fillColor=#BFEFFF");
			v[1] = graph.insertVertex(parent, null,
					MainFrame.graphNet.activityNames[1], 400, 400, 50, 20,"fillColor=#BFEFFF");
			for (int i = 2; i < MainFrame.graphNet.activityCount; i++)
				if (MainFrame.graphNet.activityFre[i] >= temp[activitySlider
						.getValue()]) {
					
					String value = MainFrame.graphNet.activityNames[i] + "\n"+
					MainFrame.graphNet.activityFre[i];
					
					switch (modelType.getSelectedIndex()) {
					case 1:
						if (MainFrame.graphNet.activityTime[i] != 0)
						{
							value = MainFrame.graphNet.activityNames[i] + "\n"+
									(MainFrame.graphNet.activityTime[i] / (1000*60*60)) + "hours";
						}else{
							value = MainFrame.graphNet.activityNames[i];
						}
						break;
					case 2:
						if (MainFrame.graphNet.activityTime[i] != 0)
						{
							value = MainFrame.graphNet.activityNames[i] + "\n"+
									(MainFrame.graphNet.activityTime[i] / 
											(1000*60*MainFrame.graphNet.activityFre[i])) + "mins";
						}else{
							value = MainFrame.graphNet.activityNames[i];
						}
						break;
					case 3:
						if (MainFrame.graphNet.maxActivityTime[i] != 0)
						{
							value = MainFrame.graphNet.activityNames[i] + "\n"+
									(MainFrame.graphNet.maxActivityTime[i] / 
											(1000*60*60)) + "hours";
						}else{
							value = MainFrame.graphNet.activityNames[i];
						}
						break;
					case 4:
						if (MainFrame.graphNet.minActivityTime[i] != 0)
						{
							value = MainFrame.graphNet.activityNames[i] + "\n"+
									(MainFrame.graphNet.minActivityTime[i] / 
											(1000*60)) + "mins";
						}else{
							value = MainFrame.graphNet.activityNames[i];
						}
						break;
					default:
						break;
					}
							
					v[i] = graph.insertVertex(parent, null,
							value, 400, 400, 80,
							40, "fillColor=#BFEFFF");
				}

			for (int i = 0; i < MainFrame.graphNet.activityCount; i++)
				if (i < 2
						|| MainFrame.graphNet.activityFre[i] >= temp[activitySlider
								.getValue()])
					for (int j = 0; j < MainFrame.graphNet.activityCount; j++)
						if ((j < 2 || MainFrame.graphNet.activityFre[j] >= temp[activitySlider
								.getValue()])
								&& MainFrame.graphNet.activityQueFre[i][j] >= MainFrame.graphNet.activityQueFreSort.
								get(pathSlider.getValue())) {
							
							String value = MainFrame.graphNet.activityQueFre[i][j]+"";
							
							switch (modelType.getSelectedIndex()) {
							case 1:
								if (MainFrame.graphNet.activityQueTime[i][j] != 0)
								{
									value =  (MainFrame.graphNet.activityQueTime[i][j] / (1000*60*60) )+" hours";
								} else
								{
									value = "";
								}
								break;
							case 2:
								if (MainFrame.graphNet.activityQueTime[i][j] != 0)
								{
									value =  (MainFrame.graphNet.activityQueTime[i][j] /
											(1000*60*MainFrame.graphNet.activityQueFre[i][j]) )+" mins";
								} else
								{
									value = "";
								}
								break;
							case 3:
								if (MainFrame.graphNet.maxActivityQueTime[i][j] != 0)
								{
									value =  (MainFrame.graphNet.maxActivityQueTime[i][j] / (1000*60*60) )+" hours";
								} else
								{
									value = "";
								}
								break;
							case 4:
								if (MainFrame.graphNet.minActivityQueTime[i][j] != 0)
								{
									value =  (MainFrame.graphNet.minActivityQueTime[i][j] / (1000*60) )+" mins";
								} else
								{
									value = "";
								}
								break;
							default:
								break;
							}
							
							graph.insertEdge(parent, null,
									value,
									v[i], v[j], "strokeColor=#515151");
						}

			new mxHierarchicalLayout(graph).execute(graph.getDefaultParent());
			new mxParallelEdgeLayout(graph).execute(graph.getDefaultParent());
		} finally {
			graph.getModel().endUpdate();
		}
		ZoomtoFit();
		ZoomtoCenter();
		graphComponent.validateGraph();
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

			newScale = Math.min((double) w * 0.9 / gw, (double) h * 0.9 / gh);
		}
		if (newScale != 0 && newScale > 0.2) {
			graphComponent.zoom(newScale);
		}
	}

	public void ZoomtoCenter() {
		mxRectangle graphSize = graph.getView().getGraphBounds();
		Dimension viewPortSize = graphComponent.getViewport().getSize();
		int x = (int) graphSize.getWidth() / 2 - viewPortSize.width / 2;
		int y = (int) graphSize.getHeight() / 2 - viewPortSize.height / 2;
		graphComponent.getGraphControl().setTranslate(
				new Point((int) (-graphSize.getX() - x), (int) (-graphSize
						.getY() - y)));
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
