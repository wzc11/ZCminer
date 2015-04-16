package wzc.zcminer.global;

import java.util.HashMap;


public class GraphNet {
  HashMap<String,Integer> activityIDMap;
  String[] activityNames;
  int[][] activityQueFre;
  int[] activityFre;
  int activityCount;
  
  public GraphNet() {
	// TODO Auto-generated constructor stub
	  activityIDMap =  new HashMap<String,Integer>();
	  activityCount = 0;
  }
  
  public boolean activityExist(String activityName) {
	  return activityIDMap.containsKey(activityName);
	
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
