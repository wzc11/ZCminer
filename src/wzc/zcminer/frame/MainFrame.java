package wzc.zcminer.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;




import javax.swing.*;

import wzc.zcminer.global.CaseCollection;
import wzc.zcminer.global.GraphNet;


public class MainFrame {
	static final int WIDTH = 1000;
	static final int HEIGHT = 600;
	static CaseCollection caseCollection;
	static GraphNet graphNet;
	
	static JFrame mainFrame;
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
        		ImportPanel importPanel = new ImportPanel("ExampleLog.csv");
	        	mainFrame.getContentPane().removeAll();
	       		mainFrame.setContentPane(importPanel);
	       		mainFrame.setVisible(true);
        		
        	}
        });
		
		
		caseCollection = new CaseCollection();
		graphNet = new GraphNet();
		
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

