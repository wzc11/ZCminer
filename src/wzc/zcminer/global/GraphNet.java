package wzc.zcminer.global;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.LongPredicate;

import com.sun.org.apache.xml.internal.resolver.helpers.PublicId;

public class GraphNet {
	HashMap<String, Integer> activityIDMap;
	public String[] activityNames;
	public int[][] activityQueFre;
	public int[] activityFre;
	public long[] activityTime;
	public long[][] activityQueTime; 
	public long[] maxActivityTime;
	public long[][] maxActivityQueTime;
	public long[] minActivityTime;
	public long[][] minActivityQueTime;
	public int activityCount;
	public int maxActivityFre;
	public int maxActivityQuiFre;
	public long beginTime;
	public long endTime;
	public ArrayList<Integer> activityQueFreSort;  
	

	public GraphNet() {
		// TODO Auto-generated constructor stub
		activityIDMap = new HashMap<String, Integer>();
		activityQueFreSort = new ArrayList<Integer>();
		activityCount = 2;
		maxActivityFre = 0;
		maxActivityQuiFre = 0;
		beginTime = 0;
		endTime = 0;
	}

	public boolean activityExist(String activityName) {
		return activityIDMap.containsKey(activityName);

	}

	public void addActivityFre(int pos) {
		activityFre[pos]++;
		if (activityFre[pos] > maxActivityFre) {
			maxActivityFre = activityFre[pos];
		}
	}

	public void addActivityQueFre(int parent, int children) {
		activityQueFre[parent][children]++;
		if (activityQueFre[parent][children] > maxActivityQuiFre) {
			maxActivityQuiFre = activityQueFre[parent][children];
		}
	}

	public void setActivityName(int id, String name) {
		activityNames[id] = name;
	}

	public int getActivityId(String activityString) {
		return activityIDMap.get(activityString);
	}

	public void addActivity(String activityName) {
		activityIDMap.put(activityName, activityCount);
		activityCount++;
	}

	public void addActivityTime(int id, long time) {
		activityTime[id] += time;
	}
	
	public void addActivityQueTime(int parent, int children, long time) {
		activityQueTime[parent][children] += time;
	}
	
	public void setMemory() {
		activityNames = new String[activityCount];
		activityQueFre = new int[activityCount][activityCount];
		activityFre = new int[activityCount];
		activityNames[0] = "begin";
		activityNames[1] = "end";
		activityTime = new long[activityCount];
		activityQueTime = new long[activityCount][activityCount];
		maxActivityTime = new long[activityCount];
		maxActivityQueTime = new long[activityCount][activityCount];
		minActivityTime = new long[activityCount];
		minActivityQueTime = new long[activityCount][activityCount];

	}
	public void setTime(int id, long time) {
		if (maxActivityTime[id] < time){
			 maxActivityTime[id] = time;
		}
		if (minActivityTime[id] == 0 || minActivityTime[id] > time)
		{
			minActivityTime[id] = time;
		}	
	}
	
	public void setQueTime(int parent, int children, long time) {
		if (maxActivityQueTime[parent][children] < time){
			maxActivityQueTime[parent][children]  = time;
		}
		if (minActivityQueTime[parent][children] == 0 || minActivityQueTime[parent][children] > time){
			minActivityQueTime[parent][children] = time;
		}
	}
	
	public void setBeginTime(long time){
		if (time < beginTime || beginTime == 0 ){
			beginTime = time;
		}
	}
	
	public void setEndTime(long time){
		if (time > endTime){
			endTime = time;
		}
	}
	
	public int getMaxActivityFre() {
		return maxActivityFre;
	}

	public int getMaxActivityQueFre() {
		return maxActivityQuiFre;
	}
	
	

}
