package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileReader;
import java.util.List;

import javax.swing.*;

import com.opencsv.CSVReader;
public class MainFrame {
	static final int WIDTH = 700;
	static final int HEIGHT = 600;
	JFrame mainFrame;
	public MainFrame() {
		// TODO Auto-generated constructor stub
		mainFrame = new JFrame();
		mainFrame.setTitle("THSS MINER");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH,HEIGHT);
		
		JPanel startPanel =new JPanel();
		
		mainFrame.setContentPane(startPanel);
		JLabel startLabel=new JLabel();
		startLabel.setText("Time to get start!");
		
		JButton startButton=new JButton("import your data");
		
		startButton.addActionListener(new ActionListener()
        {
        	public void actionPerformed(ActionEvent e) 
        	{
        		 try {        			
	        		CSVReader reader = new CSVReader(new FileReader("ExampleLog.csv")); 
	        		 
	        		List<String[]> myEntries = reader.readAll();
	        		String[] headlines = myEntries.remove(0);
	        		String[][] rowData = myEntries.toArray(new String[0][]);
	        		JTable table = new JTable(rowData, headlines);
	        		JScrollPane dataPanel =new JScrollPane(table);
	        		mainFrame.setContentPane(dataPanel);
	        		mainFrame.setVisible(true);
        		 } catch (Exception ex) {
        	            ex.printStackTrace();
        	        }
        	}
        });
		
		
		startPanel.setLayout(new FlowLayout());
		startPanel.add(startLabel);
		startPanel.add(startButton);
		
		mainFrame.setVisible(true);
	}
    public static void main(String[] args)
    {
    	new MainFrame();
    }
}
