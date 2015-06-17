package wzc.zcminer.global;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

//case 类
public class EventCase {
	//case属性
	String caseID;
	String activity;
	String startDateString;
	String endDateString;
	String resourseString;
    Date startDate;
	Date endDate;

	public EventCase() {
		// TODO Auto-generated constructor stub
		startDateString = "";
		endDateString = "";
		startDate = new Date();
		endDate = new Date();
	}
	//活动时长
	public long getTime() {
		long hours = 0;
		
		if (endDateString.equals(""))
		{
			hours = 0;
		} else
		{
			hours = (endDate.getTime() - startDate.getTime());
		}
		return hours;
	}

	public Date getStartDate() {
		return startDate;
	}
	
	public Date getEndDate() {
		if (endDateString.equals(""))
		{
			return startDate;
		}else
		{
			return endDate;
		}
	}
	public void setCase(String caseID) {
		this.caseID = caseID;
	}

	public String getCase() {
		return caseID;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivity() {
		return activity;
	}

	public void setResourse(String resourse) {
		this.resourseString = resourse;
	}

	public String getResourse() {
		return resourseString;
	}
	//设置时间，考虑单个或多个时间的情况
	public void setDate(String dateString, String timeString) {
		SimpleDateFormat sdf = new SimpleDateFormat(timeString);  
		if (startDateString.equals("")) {
			this.startDateString = dateString;
			try {
				startDate = sdf.parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			this.endDateString = dateString;
			try {
				endDate = sdf.parse(dateString);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
