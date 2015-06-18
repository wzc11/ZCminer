package wzc.zcminer.frame;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import sun.applet.Main;

import wzc.zcminer.global.CaseCollection;
import wzc.zcminer.global.EventCase;

import com.opencsv.CSVReader;
//数据导入面板
public class ImportPanel extends JPanel {
	static ColumnSelectableJTable table;
	static JPanel radioPanel;
	static JRadioButton removeButton;
	static JRadioButton caseButton;
	static JRadioButton activityButton;
	static JRadioButton timeButton;
	static JRadioButton resourseButton;
	static JRadioButton otherButton;
	static ButtonGroup buttonGroup;
	static JTextField timeText; //时间模式输入
	final static int MAXHEADINDEX = 20;
	int[] headIndex;
	String[][] rowData;
	List<String[]> myEntries;

	public ImportPanel(String dataName) {
		// TODO Auto-generated constructor stub
		try {
			headIndex = new int[MAXHEADINDEX];
			//按钮组的设置
			setLayout(new BorderLayout());
			radioPanel = new JPanel();
			radioPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

			removeButton = new JRadioButton("remove");
			removeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headIndex[table.column] = 1;
				}
			});

			caseButton = new JRadioButton("case");
			caseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headIndex[table.column] = 2;
				}
			});

			activityButton = new JRadioButton("activity");
			activityButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headIndex[table.column] = 3;
				}
			});

			timeButton = new JRadioButton("timestamp");
			timeButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headIndex[table.column] = 4;
				}
			});
			//时间模板输入
			timeText = new JTextField();
			timeText.setText("dd.MM.yy HH:mm");

			resourseButton = new JRadioButton("resourse");
			resourseButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headIndex[table.column] = 5;
				}
			});

			otherButton = new JRadioButton("other");
			otherButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					headIndex[table.column] = 6;
				}
			});

			radioPanel.add(removeButton);
			radioPanel.add(caseButton);
			radioPanel.add(activityButton);
			radioPanel.add(timeButton);
			radioPanel.add(timeText);
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
			//csv数据导入
			CSVReader reader = new CSVReader(new FileReader(dataName));
			myEntries = reader.readAll();
			String[] headlines = myEntries.remove(0);
			rowData = myEntries.toArray(new String[0][]);
			int maxLine = 1000;
			if (myEntries.size() < 1000){
				maxLine = myEntries.size();
			}
			String [][] tableData = myEntries.subList(0, maxLine).toArray(new String[0][]);
			
			table = new ColumnSelectableJTable(tableData, headlines);

			table.addMouseListener(new MouseAdapter() {
				public void mouseReleased(MouseEvent e) {
					int column = table.getSelectedColumn();
					table.column = column;
					switch (headIndex[column]) {
					case 1:
						buttonGroup.setSelected(removeButton.getModel(), true);
						break;
					case 2:
						buttonGroup.setSelected(caseButton.getModel(), true);
						break;
					case 3:
						buttonGroup.setSelected(activityButton.getModel(), true);
						break;
					case 4:
						buttonGroup.setSelected(timeButton.getModel(), true);
						break;
					case 5:
						buttonGroup.setSelected(resourseButton.getModel(), true);
						break;
					case 6:
						buttonGroup.setSelected(otherButton.getModel(), true);
						break;
					default:
						buttonGroup.clearSelection();
						break;
					}
				}
			});

			JScrollPane dataPanel = new JScrollPane(table);
			add(dataPanel, BorderLayout.CENTER);
			reader.close();
			//逻辑分析代码，得出所有活动数
			JButton okButton = new JButton("开始分析");
			okButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {

					for (int i = 0; i < myEntries.size(); i++) {
						EventCase eventCase = new EventCase();
						for (int j = 0; j < rowData[i].length; j++) {
							switch (headIndex[j]) {
							case 2:
								eventCase.setCase(rowData[i][j]);
								break;
							case 3:
								eventCase.setActivity(rowData[i][j]);
								if (!MainFrame.graphNet
										.activityExist(rowData[i][j])) {
									MainFrame.graphNet
											.addActivity(rowData[i][j]);
								}
								break;
							case 4:
								eventCase.setDate(rowData[i][j], timeText.getText());
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
					int[] temp = new int[MainFrame.graphNet.activityCount]; 
					int[][] tempQue = new int[MainFrame.graphNet.activityCount][MainFrame.graphNet.activityCount]; 
					int lastActivityId = -1;
					String lastCase = "";
					Date lastDate = new Date();
					//按顺序遍历得出case，并运算各个参数
					for (int i = 0; i < MainFrame.caseCollection.getSize(); i++) {
						EventCase eventCase = MainFrame.caseCollection
								.getCase(i);
						String activityName = eventCase.getActivity();
						String caseName = eventCase.getCase();
						long time = eventCase.getTime();
						int activityId = MainFrame.graphNet
								.getActivityId(activityName);
						MainFrame.graphNet.setActivityName(activityId,
								activityName);
						MainFrame.graphNet.addActivityTime(activityId, time);
						MainFrame.graphNet.setTime(activityId, time);
						MainFrame.graphNet.setBeginTime(eventCase.getStartDate().getTime());
						MainFrame.graphNet.setEndTime(eventCase.getEndDate().getTime());
						
						if (caseName.equals(lastCase)) {
							MainFrame.graphNet.addActivityFre(activityId);
							MainFrame.graphNet.addActivityQueFre(
									lastActivityId, activityId);
							long queTime = (eventCase.getStartDate().getTime()-lastDate.getTime());
							MainFrame.graphNet.addActivityQueTime(lastActivityId, activityId, queTime);
							MainFrame.graphNet.setQueTime(lastActivityId, activityId, queTime);
							temp[activityId]++;
							tempQue[lastActivityId][activityId]++;
							lastActivityId = activityId;
							lastDate = eventCase.getEndDate();
						} else {
							if (lastActivityId != -1) {
								MainFrame.graphNet.addActivityQueFre(
										lastActivityId, 1);
								MainFrame.graphNet.addActivityQueFre(0,
										activityId);
								tempQue[lastActivityId][1]++;
							} else{
								MainFrame.graphNet.addActivityQueFre(0,
										activityId);
							}
							for (int j=0; j< MainFrame.graphNet.activityCount; j++){
								if (temp[j] > MainFrame.graphNet.maxActivityRep[j]){
									MainFrame.graphNet.maxActivityRep[j] = temp[j];
								}
								if (temp[j] > 0){
									MainFrame.graphNet.activityCaseFre[j]++;
								}
								temp[j] = 0;
								for (int k = 0; k< MainFrame.graphNet.activityCount; k++){
									if (tempQue[j][k] >0){
										MainFrame.graphNet.activityCaseQueFre[j][k]++;
									}
									tempQue[j][k] = 0;
								}
							}
							tempQue[0][activityId]++;
							temp[activityId]++;
							lastCase = caseName;
							lastActivityId = activityId;
							lastDate = eventCase.getEndDate();
							MainFrame.graphNet.addActivityFre(lastActivityId);
						}
					}
					tempQue[lastActivityId][1]++;
					for (int j=0; j< MainFrame.graphNet.activityCount; j++){
						if (temp[j] > MainFrame.graphNet.maxActivityRep[j]){
							MainFrame.graphNet.maxActivityRep[j] = temp[j];
						}
						if (temp[j] > 0){
							MainFrame.graphNet.activityCaseFre[j]++;
						}
						for (int k = 0; k< MainFrame.graphNet.activityCount; k++){
							if (tempQue[j][k] >0){
								MainFrame.graphNet.activityCaseQueFre[j][k]++;
							}
						}
					}
					MainFrame.graphNet.addActivityQueFre(
							lastActivityId, 1);

					MainFrame.graphNet.beginTime /= 1000*60*60;
					MainFrame.graphNet.endTime /= 1000*60*60;
					
					for (int i = 0; i < MainFrame.graphNet.activityCount; i++)
						for (int j = 0; j < MainFrame.graphNet.activityCount; j++)
						{
							MainFrame.graphNet.activityQueFreSort.add(MainFrame.graphNet.activityQueFre[i][j]);
						}
					Collections.sort(MainFrame.graphNet.activityQueFreSort);
					GraphPanel graphPanel = new GraphPanel();
					MainFrame.mainFrame.getContentPane().removeAll();
					MainFrame.mainFrame.setContentPane(graphPanel);
					MainFrame.mainFrame.setVisible(true);

				}
			});

			add(okButton, BorderLayout.PAGE_END);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
