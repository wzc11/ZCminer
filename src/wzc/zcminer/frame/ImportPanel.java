package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.FileReader;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.opencsv.CSVReader;

public class ImportPanel extends JPanel{
	static JTable table;
	static JPanel radioPanel;
	static JRadioButton removeButton;
	static JRadioButton caseButton;
	static JRadioButton activityButton;
	static JRadioButton timeButton;
	static JRadioButton resourseButton;
	static JRadioButton otherButton;
	static ButtonGroup buttonGroup;
	public ImportPanel(String dataName) {
		// TODO Auto-generated constructor stub
		try {
			
			setLayout(new BorderLayout());
			radioPanel = new JPanel();
			radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			removeButton = new JRadioButton("remove");
			caseButton = new JRadioButton("case");
			activityButton = new JRadioButton("activity");
			timeButton = new JRadioButton("timestamp");
			resourseButton = new JRadioButton("resourse");
			otherButton = new JRadioButton("other");
			
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
			List<String[]> myEntries = reader.readAll();
			String[] headlines = myEntries.remove(0);
			String[][] rowData = myEntries.toArray(new String[0][]);
			table = new JTable(rowData, headlines);
			JScrollPane dataPanel =new JScrollPane(table);
			add(dataPanel,BorderLayout.CENTER);
			reader.close();
			
			
		 } catch (Exception ex) {
	            ex.printStackTrace();
	        }
	}

}
