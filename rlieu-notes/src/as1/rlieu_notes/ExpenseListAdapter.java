package as1.rlieu_notes;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

class ExpenseListAdapter extends ArrayAdapter<ExpenseItem> {

	private ExpenseItem expense;
	private TextView listName;
	private TextView listDate;
	private TextView listAmount;
	private TextView listCurrency;
	
	public ExpenseListAdapter(Context context, ArrayList<ExpenseItem> expense) {
		super(context, R.layout.expenselistrow, expense);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater listInflater = LayoutInflater.from(getContext());
		View listRow = listInflater.inflate(R.layout.expenselistrow, parent, false);
		
		// Initialize
		expense = getItem(position);
		listName = (TextView) listRow.findViewById(R.id.listexpensename);
		listDate = (TextView) listRow.findViewById(R.id.listexpensedate);
		listAmount = (TextView) listRow.findViewById(R.id.listexpenseamount);
		listCurrency = (TextView) listRow.findViewById(R.id.listexpensecurrency);
		
		// Setup TextViews
		listName.setText(expense.getName());
		listDate.setText(expense.getStartDate());
		listAmount.setText(expense.getAmount());
		listCurrency.setText(expense.getCurrency());
		return listRow;
	}
	
}
