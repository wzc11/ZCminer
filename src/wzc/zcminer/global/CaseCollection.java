package wzc.zcminer.global;

import java.util.ArrayList;
import java.util.List;
//case集合类
public class CaseCollection {
	List<EventCase> cases;

	public CaseCollection() {
		// TODO Auto-generated constructor stub
		cases = new ArrayList<EventCase>();
	}

	public void addCase(EventCase c) {
		cases.add(c);
	}
	
	public int getSize(){
		return cases.size();
	}
	
	public EventCase getCase(int pos) {
		return cases.get(pos);
	}
}
