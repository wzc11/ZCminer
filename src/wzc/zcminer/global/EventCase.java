package wzc.zcminer.global;

public class EventCase {
	String caseID;
	String activity;
	String startDateString;
	String endDateString;
	public EventCase(String caseID) {
		// TODO Auto-generated constructor stub
		this.caseID = caseID;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public void setStartDate(String startDateString) {
		this.startDateString = startDateString;
	}
	public void setEndDate(String endDateString) {
		this.endDateString = endDateString;
	}
}
