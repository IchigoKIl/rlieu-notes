package as1.rlieu_notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class ItemFragment extends Fragment implements OnFocusChangeListener{
	
	private EditText name;
	private EditText startDate;
	private EditText endDate;
	private EditText description;
	private TextView status;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.itemfragment, container, false);
		
		// Initialize
		name = (EditText)view.findViewById(R.id.name);
		startDate = (EditText)view.findViewById(R.id.startdate);
		endDate = (EditText)view.findViewById(R.id.enddate);
		description = (EditText)view.findViewById(R.id.description);
		status = (TextView)view.findViewById(R.id.status);
		
		// Add focus change listeners that will make the hint text blank on focus
		name.setOnFocusChangeListener(this);
		description.setOnFocusChangeListener(this);
		
		return view;
	}
	
	@Override
    public void onFocusChange(View v, boolean hasFocus)
    {	
		// Make the hint text blank
		((EditText) v).setHint(R.string.blank);
    }

	public String getName() {
		return name.getText().toString();
	}
	
	public EditText editName() {
		return name;
	}
	
	public String getStartDate() {
		return startDate.getText().toString();
	}

	public EditText editStartDate() {
		return startDate;
	}
	
	public String getEndDate() {
		return endDate.getText().toString();
	}

	public EditText editEndDate() {
		return endDate;
	}
	
	public String getDescription() {
		return description.getText().toString();
	}
	
	public EditText editDescription() {
		return description;
	}
	
	public String getStatus() {
		return status.getText().toString();
	}
	
	public TextView editStatus() {
		return status;
	}
	
	public boolean testTexts(int nameHintId, int startDateHintId, int endDateHintId, int descriptionHintId) {
		boolean noError = true;
		
		if (name.getText().toString().trim().matches(getString(R.string.blank))) {
			// If name text is white space: reset
			reset(name, nameHintId);
			noError = false;
		}
		
		if (startDate.getText().toString().trim().matches(getString(R.string.blank))) {
			// If startDate text is white space: reset
			reset(startDate, startDateHintId);
			noError = false;
		}
		
		if (endDate.getText().toString().trim().matches(getString(R.string.blank))) {
			// If endDate text is white space: reset
			reset(endDate, endDateHintId);
			noError = false;
		}
		
		if (startDate.getText().toString().compareTo(endDate.getText().toString()) > 0) {
			// If startDate text > endDate text is white space: reset both
			reset(startDate, startDateHintId);
			reset(endDate, endDateHintId);
			noError = false;
		}
		
		if (description.getText().toString().trim().matches(getString(R.string.blank))) {
			// If description text is white space: reset
			reset(description, descriptionHintId);
			noError = false;
		}
		
		return noError;
	}
	
	public boolean testTexts(int nameHintId, int endDateHintId, int descriptionHintId) {
		boolean noError = true;
		
		if (name.getText().toString().trim().matches(getString(R.string.blank))) {
			// If startDate text is white space: reset
			reset(name, nameHintId);
			noError = false;
		}
		
		if (endDate.getText().toString().trim().matches(getString(R.string.blank))) {
			// If endDate text is white space: reset
			reset(endDate, endDateHintId);
			noError = false;
		}
		
		if (description.getText().toString().trim().matches(getString(R.string.blank))) {
			// If description text is white space: reset
			reset(description, descriptionHintId);
			noError = false;
		}
		
		return noError;
	}
	
	public void reset(EditText v, int hintId) {
		// Set v to be blank with a red hint text associated with hintId
		v.setText(R.string.blank);
		v.setHint(hintId);
		v.setHintTextColor(android.graphics.Color.RED);
	}
	
	public void editable(boolean bool) {
		// Make the name and description editable
		name.setFocusableInTouchMode(bool);
		description.setFocusableInTouchMode(bool);
	}
	
}