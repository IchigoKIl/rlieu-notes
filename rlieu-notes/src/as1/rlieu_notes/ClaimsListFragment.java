package as1.rlieu_notes;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;

import android.content.Intent;
import android.graphics.Color;
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

public class ClaimsListFragment extends Fragment {
	
	private ArrayList<ClaimItem> claimsList;
	private ListView listView;
	private ArrayAdapter<ClaimItem> claimsListAdapter;
	private Button add;
	private TextView header;
	private boolean email;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.listfragment, container, false);
		
		// Initialize
		add = (Button)view.findViewById(R.id.add);
		header = (TextView)view.findViewById(R.id.header);
		
		// Setup texts
		add.setText(R.string.add_claim);
		header.setText(R.string.claims);

		// Email mode default off
		email = false;
		
		// Set up claimsList
		claimsList = ClaimsListController.getList().getList();
		listView = (ListView)view.findViewById(R.id.list);
		claimsListAdapter = new ClaimsListAdapter(getActivity(), claimsList);
        listView.setAdapter(claimsListAdapter);
        
        // Add listeners
        ClaimsListController.getList().addListener(new Listener() {			
			@Override
			public void update() {
				claimsListAdapter.notifyDataSetChanged();
				
				// Whenever listeners are notified to update views, save data via ClaimsListManager
				try {
					ClaimsListManager.saveJSON();
				} catch (JSONException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
        
        listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				if (!email) {
					ClaimsListController.setSelectedItem(ClaimsListController.getList().getList().get(pos));
					Intent myintent = new Intent(getActivity(), ClaimActivity.class);
					startActivity(myintent);
				} else {
					setSelectedItem(ClaimsListController.getList().getList().get(pos));
				}
			}
        });
        
		return view;
	}

	public String getAdd() {
		return add.getText().toString();
	}
	
	public Button editAdd() {
		return add;
	}
	
	public ClaimItem getSelectedItem() {
		return ClaimsListController.getSelectedItem();
	}

	public void setSelectedItem(ClaimItem selectedItem) {
		if (getSelectedItem() != null) {
			listView.getChildAt(claimsList.indexOf(getSelectedItem())).setBackgroundColor(android.graphics.Color.TRANSPARENT);
		}
		
		if (selectedItem != null) {
			if (getSelectedItem() == selectedItem) {
				listView.getChildAt(claimsList.indexOf(selectedItem)).setBackgroundColor(android.graphics.Color.TRANSPARENT);
				selectedItem = null;
			} else {
				listView.getChildAt(claimsList.indexOf(selectedItem)).setBackgroundColor(Color.parseColor("#FFA54F"));
			}
		}
		
		ClaimsListController.setSelectedItem(selectedItem);
	}

	public boolean getEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}
	
}