package as1.rlieu_notes;

import java.util.Locale;

public class ExpenseItem extends Item{
	
	private String category;
	private Double amount;
	private String currency;
	
	public ExpenseItem(String name, String date, String description, 
			String category, String amount, String currency) {
		update(name, date, description, category, amount, currency);
	}

	public void update(String name, String date, String description,
			String category, String amount, String currency) {
		setName(name);
		setStartDate(date);
		setDescription(description);
		setCategory(category);
		setAmount(amount);
		setCurrency(currency);
	}

	public ExpenseItem copy() {
		return new ExpenseItem(getName(), getStartDate(), getDescription(), getCategory(), getAmount(), getCurrency());
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAmount() {
		return String.format(Locale.getDefault(), "%.2f", amount);
	}

	public void setAmount(String amount) {
		this.amount = Double.parseDouble(amount);
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}