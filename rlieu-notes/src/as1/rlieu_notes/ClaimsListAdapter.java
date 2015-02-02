package as1.rlieu_notes;

import android.widget.ArrayAdapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ClaimsListAdapter extends ArrayAdapter<ClaimItem> {

	private ClaimItem claim;
	private TextView listName;
	private TextView listDate;
	private TextView listStatus;
	private TextView listTotals;
	
	public ClaimsListAdapter(Context context, ArrayList<ClaimItem> claim) {
		super(context, R.layout.claimslistrow, claim);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater listInflater = LayoutInflater.from(getContext());
		View listRow = listInflater.inflate(R.layout.claimslistrow, parent, false);
		
		// Initialize
		claim = getItem(position);
		listName = (TextView) listRow.findViewById(R.id.listclaimname);
		listDate = (TextView) listRow.findViewById(R.id.listclaimdate);
		listStatus = (TextView) listRow.findViewById(R.id.listclaimstatus);
		listTotals = (TextView) listRow.findViewById(R.id.totals);
        
		// Setup TextViews
		listName.setText(claim.getName());
		listDate.setText(claim.getStartDate());
		listStatus.setText(claim.getStatus());
		listTotals.setText(claim.getExpenselist().getTotals());
		
		return listRow;
	}
	
}
