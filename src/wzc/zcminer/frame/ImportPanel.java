
package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import wzc.zcminer.global.EventCase;

import com.opencsv.CSVReader;

public class ImportPanel extends JPanel{
	static ColumnSelectableJTable table;
	static JPanel radioPanel;
	static JRadioButton removeButton;
	static JRadioButton caseButton;
	static JRadioButton activityButton;
	static JRadioButton timeButton;
	static JRadioButton resourseButton;
	static JRadioButton otherButton;
	static ButtonGroup buttonGroup;
	final static int MAXHEADINDEX = 20;
	int [] headIndex;
	String[][] rowData;
	List<String[]> myEntries;
	public ImportPanel(String dataName) {
		// TODO Auto-generated constructor stub
		try {
			headIndex = new int[MAXHEADINDEX]; 
			
			setLayout(new BorderLayout());
			radioPanel = new JPanel();
			radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			
			removeButton = new JRadioButton("remove");
			removeButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{
	        		headIndex[table.column] = 1;
	        	}
	        });
			
			caseButton = new JRadioButton("case");
			caseButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{
	        		headIndex[table.column] = 2;
	        	}
	        });
			
			activityButton = new JRadioButton("activity");
			activityButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{
	        		headIndex[table.column] = 3;
	        	}
	        });
			
			
			timeButton = new JRadioButton("timestamp");
			timeButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{
	        		headIndex[table.column] = 4;
	        	}
	        });
			
			resourseButton = new JRadioButton("resourse");
			resourseButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{
	        		headIndex[table.column] = 5;
	        	}
	        });
			
			otherButton = new JRadioButton("other");
			otherButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{
	        		headIndex[table.column] = 6;
	        	}
	        });
			
			radioPanel.add(removeButton);
			radioPanel.add(caseButton);
			radioPanel.add(activityButton);
			radioPanel.add(timeButton);
			radioPanel.add(resourseButton);
			radioPanel.add(otherButton);
			
			buttonGroup = new ButtonGroup();
			buttonGroup.add(removeButton);
			buttonGroup.add(caseButton);
			buttonGroup.add(activityButton);
			buttonGroup.add(timeButton);
			buttonGroup.add(resourseButton);
			buttonGroup.add(otherButton);
			
			add(radioPanel, BorderLayout.PAGE_START);
			
			CSVReader reader = new CSVReader(new FileReader(dataName));  
			myEntries = reader.readAll();
			String[] headlines = myEntries.remove(0);
			rowData = myEntries.toArray(new String[0][]);
			table = new ColumnSelectableJTable(rowData, headlines);
			JScrollPane dataPanel =new JScrollPane(table);
			add(dataPanel,BorderLayout.CENTER);
			reader.close();
			
			JButton okButton=new JButton("开始分析");
			okButton.addActionListener(new ActionListener()
	        {
	        	public void actionPerformed(ActionEvent e) 
	        	{	
	        		
	        		for (int i = 0; i < myEntries.size(); i++)
	        		{
	        			EventCase eventCase = new EventCase();
	        			for (int j = 0 ; j< rowData[i].length; j++)
	        			{
	        				switch (headIndex[j]) {
							case 2:
								eventCase.setCase(rowData[i][j]);
								break;
							case 3:
								eventCase.setActivity(rowData[i][j]);
								if (! MainFrame.graphNet.activityExist(rowData[i][j]))
								{
									MainFrame.graphNet.addActivity(rowData[i][j]);
								}
								break;
							case 4:
								eventCase.setDate(rowData[i][j]);
								break;
							case 5:
								eventCase.setResourse(rowData[i][j]);
								break;
							default:
								break;
							}
	        			}
	        			MainFrame.caseCollection.addCase(eventCase);
	        		}
	        		
	        		MainFrame.graphNet.setMemory();
	        		
	        	}
	        });
			
			add(okButton,BorderLayout.PAGE_END);
			
			
		 } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	}

}
