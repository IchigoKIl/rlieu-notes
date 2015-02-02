package as1.rlieu_notes;

public class ExpenseListController {

	private static ExpenseList list;
	private static ExpenseItem selectedItem;
	
	public static ExpenseList getList() {
		if (list == null) {
			startNewList();
		}
		return list;
	}
	
	public static void setList(ExpenseList list) {
		ExpenseListController.list = list;
	}
	
	public static void startNewList() {
		list = new ExpenseList();
		selectedItem = null;
	}

	public static ExpenseItem getSelectedItem() {
		return selectedItem;
	}
	
	public static void setSelectedItem(ExpenseItem selectedItem) {
		ExpenseListController.selectedItem = selectedItem;
	}
	
	public static void updateSelectedItem(String name, String date,
			String description, String category, String amount, String currency) {
		selectedItem.update(name, date, description, category, amount, currency);	
		getList().sort();
	}
	
	public static void listaddexp(ExpenseItem exp) {
		getList().addItem(exp);
		selectedItem = null;
	}
	
	public static void listrmexp(ExpenseItem exp) {
		getList().rmItem(exp);
		selectedItem = null;
	}

}
