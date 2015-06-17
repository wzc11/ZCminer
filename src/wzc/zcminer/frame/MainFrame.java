package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import wzc.zcminer.global.CaseCollection;
import wzc.zcminer.global.GraphNet;
//主框架
public class MainFrame {
	static final int WIDTH = 1000;
	static final int HEIGHT = 600;
	static CaseCollection caseCollection;
	static GraphNet graphNet;

	static JFrame mainFrame;

	public MainFrame() {
		// TODO Auto-generated constructor stub
		mainFrame = new JFrame();
		mainFrame.setTitle("THUMiner");
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setSize(WIDTH, HEIGHT);

		JPanel startPanel = new JPanel();

		mainFrame.setContentPane(startPanel);

		JButton startButton = new JButton("import your data");
		//导入数据文件
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fd = new JFileChooser();
				fd.setCurrentDirectory(new File("."));
				fd.setAcceptAllFileFilterUsed(false);
				final String[][] fileENames = { { ".csv", "csv数据 文件(*.csv)" } };
				fd.addChoosableFileFilter(new FileFilter() {
					public boolean accept(File file) {
						return true;
					}

					public String getDescription() {
						return "所有文件(*.*)";
					}
				});
				//文件操作
				for (final String[] fileEName : fileENames) {

					fd.setFileFilter(new javax.swing.filechooser.FileFilter() {

						public boolean accept(File file) {

							if (file.getName().endsWith(fileEName[0])
									|| file.isDirectory()) {

								return true;
							}

							return false;
						}

						public String getDescription() {

							return fileEName[1];
						}

					});
				}
				fd.showDialog(new JLabel(), "选择");
				File file = fd.getSelectedFile();
				if (file!=null){
					ImportPanel importPanel = new ImportPanel(file
						.getAbsolutePath());
					mainFrame.getContentPane().removeAll();
					mainFrame.setContentPane(importPanel);
					mainFrame.setVisible(true);
				}

			}
		});

		caseCollection = new CaseCollection();
		graphNet = new GraphNet();

		startPanel.setLayout(new BorderLayout());

		JPanel filePanel = new JPanel();
		filePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20 ));
		filePanel.add(new JLabel("choose csv file"));
		filePanel.add(startButton);
		startPanel.add(filePanel, BorderLayout.CENTER);
		
		mainFrame.setVisible(true);
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
