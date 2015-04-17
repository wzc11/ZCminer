package wzc.zcminer.global;

import java.util.HashMap;


public class GraphNet {
  HashMap<String,Integer> activityIDMap;
  public String[] activityNames;
  public int[][] activityQueFre;
  public int[] activityFre;
  public int activityCount;
  
  public GraphNet() {
	// TODO Auto-generated constructor stub
	  activityIDMap =  new HashMap<String,Integer>();
	  activityCount = 0;
  }
  
  public boolean activityExist(String activityName) {
	  return activityIDMap.containsKey(activityName);
	
  }
  
  public void addActivityFre(int pos) {
	activityFre[pos]++;
  }
  
  public void addActivityQueFre(int parent, int children) {
	activityQueFre[parent][children]++;
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
  
  public void setMemory() {
	  activityNames = new String[activityCount];
	  activityQueFre = new int[activityCount][activityCount];
	  activityFre = new int[activityCount];
	
  }
  
}

