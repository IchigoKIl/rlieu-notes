package as1.rlieu_notes;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class ExpenseListFragment extends Fragment {
	
	private ArrayList<ExpenseItem> expenseList;
	private ListView listView;
    private ArrayAdapter<ExpenseItem> expenseListAdapter;
	private Button add;
	private TextView header;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listfragment, container, false);
		
		// Initialize
		add = (Button)view.findViewById(R.id.add);
		header = (TextView)view.findViewById(R.id.header);
		
		// Setup Views
		add.setText(R.string.add_expense_item);
		header.setText(R.string.expense_items);
		
		// Start new ExpenseList
		// Use of new ExpenseList that needs to be populated each time for easy canceling of edits
		ExpenseListController.startNewList();
		
		// Setup expenseList
		expenseList = ExpenseListController.getList().getList();
		listView = (ListView)view.findViewById(R.id.list);
        expenseListAdapter = new ExpenseListAdapter(getActivity(), expenseList);
        listView.setAdapter(expenseListAdapter);
        
        // Add listeners
        ExpenseListController.getList().addListener(new Listener() {			
			@Override
			public void update() {
				expenseListAdapter.notifyDataSetChanged();
			}
		});
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				ExpenseListController.setSelectedItem(ExpenseListController.getList().getList().get(pos));
				Intent myintent = new Intent(getActivity(), ExpenseActivity.class);
				startActivity(myintent);
			}
        });
		
		return view;
	}

	public void editable(boolean bool) {
		// Make add visible if boolean is true, else make add gone
		if (bool) {
			add.setVisibility(View.VISIBLE);
		} else {
			add.setVisibility(View.GONE);
		}
	}
		
}
