package as1.rlieu_notes;

import java.util.ArrayList;

public class ClaimsList extends List<ClaimItem> {

	public ClaimsList() {
		list = new ArrayList<ClaimItem>();
		listeners = new ArrayList<Listener>();
	}
	
	@Override
	public int Compare(ClaimItem item1, ClaimItem item2) {
		if (item1.getStartDate().compareTo(item2.getStartDate()) != 0) {
			// Compare by startDates
			return item1.getStartDate().compareTo(item2.getStartDate());
		} else if (item1.getEndDate().compareTo(item2.getEndDate()) != 0) {
			// startDates are the same, compare by endDates
			return item1.getEndDate().compareTo(item2.getEndDate());
		} else if (item1.getName().compareTo(item2.getName()) != 0){
			// endDates are the same as well, compare by names
			return item1.getName().compareTo(item2.getName());
		} else {
			// names are the same as well, compare by Description
			return item1.getDescription().compareTo(item2.getDescription());
		}
	}
	
}
