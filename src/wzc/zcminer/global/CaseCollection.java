package wzc.zcminer.global;

import java.util.ArrayList;
import java.util.List;

public class CaseCollection {
	int caseCount;
	List<EventCase> cases;
	public CaseCollection() {
		// TODO Auto-generated constructor stub
		caseCount = 0;
		cases = new ArrayList<EventCase>();
	}
	public void addCase(EventCase c) {
		cases.add(c);
	}
}
