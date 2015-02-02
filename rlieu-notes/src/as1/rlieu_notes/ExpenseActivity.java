package as1.rlieu_notes;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class ExpenseActivity extends FragmentActivity {
	
	private ExpenseItem expense;
	private ItemFragment itemFragment;
	private AmountFragment amountFragment;
	private ButtonsFragment buttonsFragment;
	private Spinner category;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.expenselayout);
		
		// Initialize
		itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.expenseitemfragment);
		amountFragment = (AmountFragment) getSupportFragmentManager().findFragmentById(R.id.expenseamountfragment);
		buttonsFragment = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.expensebuttons);
		category = (Spinner) findViewById(R.id.expensecategory);
		
		// Setup category spinner
		ArrayAdapter<String> adapter = new SpinnerAdapter(this, getResources().getStringArray(R.array.category));
		category.setAdapter(adapter);
		
		// Setup itemFragment
		itemFragment.editStartDate().setVisibility(View.GONE);
		itemFragment.editEndDate().setHint(R.string.date);
		itemFragment.editStatus().setVisibility(View.GONE);
		
		if (ExpenseListController.getSelectedItem() == null) {
			// New ExpenseItem
			//Setup fragments
			itemFragment.editName().setHint(R.string.expense_name);
			buttonsFragment.getLeft().setText(R.string.confirm);
			buttonsFragment.getRight().setText(R.string.cancel);
		} else {
			// Selected ExpenseItem
			expense = ExpenseListController.getSelectedItem();
			
			// Setup itemFragment
			itemFragment.editable(false);
			itemFragment.editName().setText(expense.getName());
			itemFragment.editEndDate().setText(expense.getStartDate());
			itemFragment.editDescription().setText(expense.getDescription());
			
			// Setup category spinner
			category.setEnabled(false);
			category.setSelection(new ArrayList<String>(
					Arrays.asList(getResources().getStringArray(R.array.category)))
					.indexOf(expense.getCategory()));
			
			// Setup amount Fragment
			amountFragment.editable(false);
			amountFragment.editAmount().setText(expense.getAmount());
			amountFragment.editCurrency().setSelection(new ArrayList<String>(
					Arrays.asList(getResources().getStringArray(R.array.currency)))
					.indexOf(expense.getCurrency()));
			
			// Setup buttonsFragment based on selectedClaim
			buttonsFragment.getTop().setVisibility(View.VISIBLE);
			if (ClaimsListController.getSelectedItem() == null || 
					ClaimsListController.getSelectedItem().getStatus().matches(getString(R.string.in_progress))) {
				// New ClaimItem or In Progress status: expense editable
				buttonsFragment.getTop().setText(R.string.edit_expense);
				buttonsFragment.getLeft().setText(R.string.ok);
				buttonsFragment.getRight().setText(R.string.cancel);
			} else {
				// Not editable claim status 
				buttonsFragment.getTop().setText(R.string.close);
				buttonsFragment.getLeft().setVisibility(View.GONE);
				buttonsFragment.getRight().setVisibility(View.GONE);
			}
		}
	}
	
	public String getCategory() {
		return category.getSelectedItem().toString();
	}
	
	public void top(View v) {
		if (buttonsFragment.getTop().getText().toString().matches(getString(R.string.edit_expense))) {
			// Edit mode
			// Make views editable
			itemFragment.editable(true);
			category.setEnabled(true);
			amountFragment.editable(true);
			
			// Set to delete mode
			buttonsFragment.getTop().setText(R.string.delete_expense);
			buttonsFragment.getLeft().setText(R.string.save);
			buttonsFragment.getRight().setText(R.string.cancel);
		} else {
			if (buttonsFragment.getTop().getText().toString() == getString(R.string.delete_expense)) {
				// Delete mode
				ExpenseListController.getList().rmItem(ExpenseListController.getSelectedItem());
			}
			
			this.finish();
		}
	}
	
	public void left(View v) {
		// Lose focus from views
		findViewById(R.id.expensedummy).requestFocus();
		
		if (itemFragment.testTexts(R.string.expense_name, R.string.date, R.string.description) &
				amountFragment.testTexts(R.string.amount)) {
			// Input tests passed
			if (expense == null) {
				// New ExpenseItem
				ExpenseItem exp = new ExpenseItem(itemFragment.getName(), itemFragment.getEndDate(), 
						itemFragment.getDescription(), getCategory(), 
						amountFragment.getAmount(), amountFragment.getCurrency());
				ExpenseListController.listaddexp(exp);
			} else {
				// Update selected ExpenseItem
				ExpenseListController.updateSelectedItem(itemFragment.getName(), itemFragment.getEndDate(), 
						itemFragment.getDescription(), getCategory(), 
						amountFragment.getAmount(), amountFragment.getCurrency());
			}
			
			this.finish();
		}
	}
	
	public void right(View v) {
		this.finish();
	}

	public void endDate(View v) {
		if (ExpenseListController.getSelectedItem() == null || 
				buttonsFragment.getTop().getText().toString().matches(getString(R.string.delete_expense))) {
			// New ExpenseItem or in edit mode:  Allow edits to endDate via DatePickerActivity 
			Intent myintent = new Intent(this, DatePickerActivity.class);
			
			if (!itemFragment.getEndDate().trim().matches(getString(R.string.blank))) {
				// endDate exists, provide to DatePickerActivity as default date
				myintent.putExtra("data", itemFragment.getEndDate());
			}
			
			startActivityForResult(myintent, 1);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == 1) {
	    	// Date was provided via DatePickerActivity
	        if (requestCode == 1) {
	        	// endDate requestCode, set endDate to date provided in data
	        	itemFragment.editEndDate().setText(data.getExtras().getString("date"));
	        } 
	    }
	}
	
}