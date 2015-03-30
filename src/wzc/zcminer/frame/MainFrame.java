package wzc.zcminer.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
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
        		JPanel dataPanel =new JPanel();
        		JLabel dataLabel=new JLabel();
        		dataLabel.setText("this is your data");
        		dataPanel.add(dataLabel);
        		mainFrame.setContentPane(dataPanel);
        		mainFrame.setVisible(true);
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
