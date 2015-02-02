package as1.rlieu_notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

public class ClaimActivity extends FragmentActivity {
	
	private ClaimItem claim;
	private ItemFragment itemFragment;
	private ExpenseListFragment expenseListFragment;
	private ButtonsFragment buttonsFragment;
	private TextView totals;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.claimlayout);
		
		// Initialize
		itemFragment = (ItemFragment) getSupportFragmentManager().findFragmentById(R.id.claimitemfragment);
		expenseListFragment = (ExpenseListFragment) getSupportFragmentManager().findFragmentById(R.id.expenselistfragment);
		buttonsFragment = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.claimbuttons);
		totals = (TextView) findViewById(R.id.claimtotals);
		
		if (ClaimsListController.getSelectedItem() == null) {
			// New ClaimItem
			// Set up itemFragment 
			itemFragment.editName().setHint(R.string.claim_name);
			itemFragment.editStatus().setText(R.string.in_progress);
			
			// Set up buttonsFragment
			buttonsFragment.getLeft().setText(R.string.submit);
			buttonsFragment.getRight().setText(R.string.cancel);
		} else {
			// Selected ClaimItem
			claim = ClaimsListController.getSelectedItem();
			
			// Set up itemFragment
			itemFragment.editable(false);
			itemFragment.editName().setText(claim.getName());
			itemFragment.editStartDate().setText(claim.getStartDate());
			itemFragment.editEndDate().setText(claim.getEndDate());
			itemFragment.editDescription().setText(claim.getDescription());
			itemFragment.editStatus().setText(claim.getStatus());
			
			// Set up expenseListFragment
			// Use of new ExpenseList that needs to be populated each time for easy canceling of edits
			expenseListFragment.editable(false);
			for (ExpenseItem item : ClaimsListController.getSelectedItem().getExpenselist().getList()) {
				ExpenseListController.getList().addItem(item.copy());
			}
		
			// Set up buttonsFragment based on claim's status
			if (claim.getStatus().matches(getString(R.string.submitted))) {
				// Submitted status: Approve or return claim
				buttonsFragment.getLeft().setText(R.string.approve);
				buttonsFragment.getRight().setText(R.string.return_item);
			} else if (claim.getStatus().matches(getString(R.string.approved))) {
				// Approved status: Close via okay or delete claim
				buttonsFragment.getLeft().setText(R.string.ok);
				buttonsFragment.getRight().setText(R.string.delete_claim);
			} else {
				// Returned or In Progress status: Submit, edit or delete claim
				buttonsFragment.getTop().setVisibility(View.VISIBLE);
				buttonsFragment.getTop().setText(R.string.edit_claim);
				buttonsFragment.getLeft().setText(R.string.submit);
				buttonsFragment.getRight().setText(R.string.cancel);
			}	
		}
		
		// Set up totals 
		String totalsString = ExpenseListController.getList().getTotals();
		totals.setText(totalsString);
		
		// Set up listeners
        ExpenseListController.getList().addListener(new Listener() {			
			@Override
			public void update() {
				String totalsString = ExpenseListController.getList().getTotals();
				totals.setText(totalsString);
			}
		});
	}
	
	public void addItem(View v) {
		// Add new ExpenseItem via ExpenseActivity
		ExpenseListController.setSelectedItem(null);
		Intent myintent = new Intent(this, ExpenseActivity.class);
		startActivity(myintent);
	 }
	
	public void top(View v) {
		if (buttonsFragment.getTop().getText().toString().matches(getString(R.string.edit_claim))) {
			// Edit mode
			// Set claim status to In Progress for editing
			ClaimsListController.updateSelectedItem(claim.getName(), claim.getStartDate(), claim.getEndDate(), 
					claim.getDescription(), getString(R.string.in_progress), claim.getExpenselist());
			
			// Set to delete mode
			buttonsFragment.getTop().setText(R.string.delete_claim);
			
			// Make views editable
			itemFragment.editable(true);
			expenseListFragment.editable(true);
		} else {
			if (buttonsFragment.getTop().getText().toString().matches(getString(R.string.delete_claim))) {
				// Delete mode
				ClaimsListController.getList().rmItem(ClaimsListController.getSelectedItem());
			}
			
			this.finish();
		}
	}
	
	public void left(View v) {
		// Lose focus from views
		findViewById(R.id.claimdummy).requestFocus();
		
		if (itemFragment.getStatus().matches(getString(R.string.submitted))) {
			// Submitted status: Approve claim
			ClaimsListController.updateSelectedItem(claim.getName(), claim.getStartDate(), claim.getEndDate(), 
					claim.getDescription(), getString(R.string.approved), claim.getExpenselist());
			
			this.finish();
		} else if (itemFragment.getStatus().matches(getString(R.string.approved))) {
			// Approved status: Close details
			this.finish();
		} else {
			// Returned or In Progress status: Submit claim
			if (itemFragment.testTexts(R.string.claim_name, R.string.start_date, R.string.end_date, R.string.description)) {
				// Input tests pass
				ExpenseList expList = ExpenseListController.getList();
				
				if (claim == null) {
					// New ClaimItem
					if (expList.size() != 0) {
						// Expenses: Create new claim with Submitted status
						ClaimItem claim = new ClaimItem(itemFragment.getName(),
								itemFragment.getStartDate(),
								itemFragment.getEndDate(),
								itemFragment.getDescription(),
								getString(R.string.submitted), expList);
						ClaimsListController.listaddclaim(claim);
					} else {
						// No expenses: Create new claim with In Progress status
						ClaimItem claim = new ClaimItem(itemFragment.getName(),
								itemFragment.getStartDate(),
								itemFragment.getEndDate(),
								itemFragment.getDescription(),
								getString(R.string.in_progress), expList);
						ClaimsListController.listaddclaim(claim);
					}
				} else {
					// Update selected ClaimItem
					if (expList.size() != 0) {
						// Expenses: Update claim with Submitted status
						ClaimsListController.updateSelectedItem(
								itemFragment.getName(),
								itemFragment.getStartDate(),
								itemFragment.getEndDate(),
								itemFragment.getDescription(),
								getString(R.string.submitted), expList);
					} else {
						// No expenses: Update claim with In Progress status
						ClaimsListController.updateSelectedItem(
								itemFragment.getName(),
								itemFragment.getStartDate(),
								itemFragment.getEndDate(),
								itemFragment.getDescription(),
								getString(R.string.in_progress), expList);
					}
				}

				this.finish();
			}
		}
	}
	
	public void right(View v) {
		if (itemFragment.getStatus().matches(getString(R.string.submitted))) {
			// Submitted status: Return claim
			ClaimsListController.updateSelectedItem(claim.getName(), claim.getStartDate(), claim.getEndDate(), 
					claim.getDescription(), getString(R.string.returned), claim.getExpenselist());
		} else if (itemFragment.getStatus().matches(getString(R.string.approved))) {
			// Approved status: Delete claim
			ClaimsListController.getList().rmItem(ClaimsListController.getSelectedItem());
		}
		
		this.finish();
	}
	
	public void startDate(View v) {
		if (claim == null || claim.getStatus().matches(getString(R.string.in_progress))) {
			// In Progress status: Allow edits to startDate via DatePickerActivity
			Intent myintent = new Intent(this, DatePickerActivity.class);
			
			if (!itemFragment.getStartDate().trim().matches(getString(R.string.blank))) {
				// startDate exists, provide to DatePickerActivity as default date
				myintent.putExtra("data", itemFragment.getStartDate());
			}
			
			// Wait for requestCode: 0
			startActivityForResult(myintent, 0);
		}
	}
	
	public void endDate(View v) {
		if (claim == null || claim.getStatus().matches(getString(R.string.in_progress))) {
			// In Progress status: Allow edits to endDate via DatePickerActivity
			Intent myintent = new Intent(this, DatePickerActivity.class);
			
			if (!itemFragment.getEndDate().trim().matches(getString(R.string.blank))) {
				// endDate exists, provide to DatePickerActivity as default date
				myintent.putExtra("data", itemFragment.getEndDate());
			}
			
			// Wait for requestCode: 1
			startActivityForResult(myintent, 1);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if (resultCode == 1) {
	    	// Date was provided via DatePickerActivity
	        if (requestCode == 0) {
	        	// startDate requestCode, set startDate to date provided in data
	        	itemFragment.editStartDate().setText(data.getExtras().getString("date"));
	        } else {
	        	// endDate requestCode, set endDate to date provided in data
	        	itemFragment.editEndDate().setText(data.getExtras().getString("date"));
	        }
	    }
	}
	
}
