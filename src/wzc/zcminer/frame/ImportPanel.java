package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.io.FileReader;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import com.opencsv.CSVReader;

public class ImportPanel extends JPanel{
	static JTable table;
	public ImportPanel(String dataName) {
		// TODO Auto-generated constructor stub
		try {
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
