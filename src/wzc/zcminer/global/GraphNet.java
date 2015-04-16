package wzc.zcminer.global;

import java.util.HashMap;

public class GraphNet {
  HashMap<String,Integer> activityIDMap;
  HashMap<Integer, String> activityNameMap;
  static final int ACTIVITYMAXCOUNTS = 100; 
  int activityMap[][];
  public GraphNet() {
	// TODO Auto-generated constructor stub
	  activityIDMap =  new HashMap<String,Integer>();
	  activityNameMap = new HashMap<Integer, String>();
	  activityMap = new int[ACTIVITYMAXCOUNTS][ACTIVITYMAXCOUNTS];
  }
  
  
}
