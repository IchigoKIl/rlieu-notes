package as1.rlieu_notes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public abstract class Item {
	
	protected static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
	protected String name;
	protected Date startDate;
	protected String description;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getStartDate() {
		return toString(startDate);
	}
	
	public void setStartDate(String startDate) {
		this.startDate = toDate(startDate);
	}
	
	public String toString(Date date) {
		return dateFormat.format(date);
	}
	
	public static Date toDate(String date) {
		Date Date = null;
		try {
			// Try to parse date string
			Date = dateFormat.parse(date);
		} catch (Exception e){
			e.printStackTrace();
		}
		
		return Date;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
}
