package as1.rlieu_notes;

public class ClaimsListController {
	
	private static ClaimsList list;
	private static ClaimItem selectedItem;
	
	public static ClaimsList getList() {
		if (list == null) {
			startNewList();
		}
		return list;
	}
	
	public static void startNewList() {
		list = new ClaimsList();
		selectedItem = null;
	}
	
	public static ClaimItem getSelectedItem() {
		return selectedItem;
	}
	
	public static void setSelectedItem(ClaimItem selectedItem) {
		ClaimsListController.selectedItem = selectedItem;
	}

	public static void updateSelectedItem(String name, String startDate, String endDate, String description, 
			String status, ExpenseList expenselist) {
		selectedItem.update(name, startDate, endDate, description, status, expenselist);
		getList().sort();
	}
	
	public static void listaddclaim(ClaimItem claim) {
		getList().addItem(claim);
		selectedItem = null;
	}

	public static void listrmclaim(ClaimItem claim) {
		getList().rmItem(claim);
		selectedItem = null;
	}
	
}