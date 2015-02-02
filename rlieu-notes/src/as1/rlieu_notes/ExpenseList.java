package as1.rlieu_notes;

import java.util.ArrayList;
import java.util.Locale;
import java.util.TreeMap;

public class ExpenseList extends List<ExpenseItem> {
	
	public ExpenseList() {
		list = new ArrayList<ExpenseItem>();
		listeners = new ArrayList<Listener>();
	}
	
	@Override
	public int Compare(ExpenseItem item1, ExpenseItem item2) {
		if (item1.getStartDate().compareTo(item2.getStartDate()) != 0) {
			// Compare by startDates
			return item1.getStartDate().compareTo(item2.getStartDate());
		} else if (item1.getName().compareTo(item2.getName()) != 0) {
			// startDates are the same, compare by names
			return item1.getName().compareTo(item2.getName());
		} else if (item1.getCurrency().compareTo(item2.getCurrency()) != 0) {
			// names are the same, compare by currencies
			return item1.getCurrency().compareTo(item2.getCurrency());
		} else if (item1.getAmount().compareTo(item2.getAmount()) != 0) {
			// currencies are the same, compare by amounts
			return item1.getAmount().compareTo(item2.getAmount());
		} else if (item1.getCategory().compareTo(item2.getCategory()) != 0) {
			// amounts are the same, compare by categories
			return item1.getCategory().compareTo(item2.getCategory());
		} else {
			// categories are the same, compare by description
			return item1.getDescription().compareTo(item2.getDescription());
		}
	}
	
	public String getTotals() {
		// Make TreeMap of currency to summed amounts
		TreeMap<String, Double> map = new TreeMap<String, Double>();
		String totals = new String();
		
		for (ExpenseItem expense: getList()) {
			Double originalValue = 0.0;
			
			if (map.containsKey(expense.getCurrency())) {
				// Retrieve originalValue if it exists
				originalValue += map.get(expense.getCurrency());
			}
			
			// update map
			map.put(expense.getCurrency(), originalValue + Double.parseDouble(expense.getAmount()));
		}
		
		// Create string of totals (currency : total)
		for (String key : map.keySet()) {
			totals += key + " : " + String.format(Locale.getDefault(), "%.2f", map.get(key)) + "\n";
		}
		
		return totals;
	}
	
}
