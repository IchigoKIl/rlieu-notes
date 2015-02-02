package as1.rlieu_notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	private ClaimsListFragment claimsListFragment;
	private Button email;
	private Toast noSelectedItem;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		
		// Initialize
		claimsListFragment = (ClaimsListFragment)getSupportFragmentManager().findFragmentById(R.id.claimslistfragment);
		email = (Button)findViewById(R.id.email);
		
		// Try to load data via ClaimsListManager onCreate
		ClaimsListManager.init(getApplicationContext());
		try {
			ClaimsListManager.loadJSON();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Setup Toasts
		noSelectedItem = Toast.makeText(this, "Please select a claim to send", Toast.LENGTH_SHORT);
		noSelectedItem.show();
		noSelectedItem.cancel();
	}

	public void cancelEmail() {
		email.setText(R.string.email);
		claimsListFragment.editAdd().setText(R.string.add_claim);
		claimsListFragment.setSelectedItem(null);
		claimsListFragment.setEmail(false);
	}
	
	public void email(View v) {
		if (email.getText().toString() == getString(R.string.email)) {
			// Set to email mode
			email.setText(R.string.send);
			claimsListFragment.editAdd().setText(R.string.done);
			claimsListFragment.setEmail(true);
			ClaimsListController.setSelectedItem(null);
		} else {
			// Email mode
			createEmail();
		}
	}
	
	// Based on https://www.youtube.com/watch?v=Z7lgmFF2WP8
	public void createEmail() {
		if (claimsListFragment.getSelectedItem() != null) {
			// There is a selectedClaim
			Intent emailIntent = new Intent(Intent.ACTION_SEND);

			emailIntent.putExtra(Intent.EXTRA_SUBJECT, "rlieu-notes");
			
			// Add ClaimItem data to message
			String message = getString(R.string.claim_name) + ": " + 
					claimsListFragment.getSelectedItem().getName() + "\n" + 
					getString(R.string.status) + ": " + 
					claimsListFragment.getSelectedItem().getStatus() + "\n" +
					getString(R.string.date) + ": " + 
					claimsListFragment.getSelectedItem().getStartDate() + " -> " +
					claimsListFragment.getSelectedItem().getEndDate() + "\n" + 
					getString(R.string.description) + ": " + 
					claimsListFragment.getSelectedItem().getDescription() + "\n\n" +
					getString(R.string.expense_items) + "\n";
			
			// Add ExpenseList data to message
			for (ExpenseItem expense : claimsListFragment.getSelectedItem().getExpenselist().getList()) {
				message += getString(R.string.expense_name) + ": " + 
						expense.getName() + "\n" +
						getString(R.string.date) + ": " + 
						expense.getStartDate() + "\n" +
						getString(R.string.description) + ": " + 
						expense.getDescription() + "\n" +
						getString(R.string.category) + ": " + 
						expense.getCategory() + "\n" +
						getString(R.string.amount) + ": " + 
						expense.getAmount() + " " + 
						expense.getCurrency() + "\n\n";
			}
			
			// Add totals to message
			message += "Totals\n" + claimsListFragment.getSelectedItem().getExpenselist().getTotals();
			
			emailIntent.putExtra(Intent.EXTRA_TEXT, message);
			emailIntent.setType("message/rfc822");
			
			claimsListFragment.setSelectedItem(null);
			noSelectedItem.cancel();
			startActivity(Intent.createChooser(emailIntent, "E-mail"));
		} else {
			// No selectedClaim
			noSelectedItem.show();
		}
	}
	
	public void addItem(View v) {
		if (claimsListFragment.getAdd() == getString(R.string.add_claim)) {
			// Add mode: Add new ClaimItem via ClaimActivity
			noSelectedItem.cancel();
			ClaimsListController.setSelectedItem(null);
			Intent myintent = new Intent(this, ClaimActivity.class);
			startActivity(myintent);
		} else {
			// Get out of email mode
			cancelEmail();
		}
	}
	
}