package as1.rlieu_notes;

import java.util.Date;

public class ClaimItem extends Item {
	
	private ExpenseList expenseList;
	private Date endDate;
	private String status;
	
	public ClaimItem(String name, String startDate, String endDate, String description, 
			String status, ExpenseList expenselist) {
		update(name, startDate, endDate, description, status, expenselist);
	}

	public void update(String name, String startDate, String endDate, String description,
			String status, ExpenseList expenselist) {
		setName(name);
		setStartDate(startDate);
		setEndDate(endDate);
		setDescription(description);
		setStatus(status);
		setExpenselist(expenselist);
	}
	
	public ExpenseList getExpenselist() {
		return expenseList;
	}

	public void setExpenselist(ExpenseList expenselist) {
		this.expenseList = expenselist;
	}

	public String getEndDate() {
		return toString(endDate);
	}
	
	public void setEndDate(String endDate) {
		this.endDate = toDate(endDate);
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void addExp(ExpenseItem expense) {
		expenseList.addItem(expense);
	}

	public void rmExp(ExpenseItem expense) {
		expenseList.rmItem(expense);
	}
	
}
