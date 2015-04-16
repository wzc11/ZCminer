package wzc.zcminer.global;

public class EventCase {
	String caseID;
	String activity;
	String startDateString;
	String endDateString;
	String resourseString;
	public EventCase() {
		// TODO Auto-generated constructor stub
		startDateString = "";
	}
	public void setCase(String caseID) {
		this.caseID = caseID;
	}
	public void setActivity(String activity) {
		this.activity = activity;
	}
	public void setResourse(String resourse){
		this.resourseString = resourse;
	}
	
	public void setDate(String dateString) {
		if (startDateString.equals(""))
		{
			this.startDateString = dateString;
		} else
		{
			this.endDateString = dateString;
		}
	}
}
