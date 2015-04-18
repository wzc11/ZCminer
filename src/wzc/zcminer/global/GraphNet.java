package wzc.zcminer.global;

import java.util.HashMap;


public class GraphNet {
  HashMap<String,Integer> activityIDMap;
  public String[] activityNames;
  public int[][] activityQueFre;
  public int[] activityFre;
  public int activityCount;
  public int maxActivityFre;
  public int maxActivityQuiFre;
  
  public GraphNet() {
	// TODO Auto-generated constructor stub
	  activityIDMap =  new HashMap<String,Integer>();
	  activityCount = 2;
	  maxActivityFre = 0;
	  maxActivityQuiFre = 0;
  }
  
  public boolean activityExist(String activityName) {
	  return activityIDMap.containsKey(activityName);
	
  }
  
  public void addActivityFre(int pos) {
	activityFre[pos]++;
	if (activityFre[pos] > maxActivityFre)
	{
		maxActivityFre = activityFre[pos];
	}
  }
  
  public void addActivityQueFre(int parent, int children) {
	activityQueFre[parent][children]++;
	if (activityQueFre[parent][children] > maxActivityQuiFre)
	{
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
  
  public void setMemory() {
	  activityNames = new String[activityCount];
	  activityQueFre = new int[activityCount][activityCount];
	  activityFre = new int[activityCount];
	  activityNames[0] = "begin";
	  activityNames[1] = "end";
	
  }
  
  public int getMaxActivityFre() {
	 return maxActivityFre;
  }
  
  public int getMaxActivityQueFre() {
	return maxActivityQuiFre;
  }
  
}

