package as1.rlieu_notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class AmountFragment extends Fragment implements OnFocusChangeListener {
	
	private EditText amount;
	private Spinner currency;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.amountfragment, container, false);
		
		// Initialize 
		amount = (EditText)view.findViewById(R.id.amount);
		currency = (Spinner)view.findViewById(R.id.currency);
		
		// Setup currency spinner
		ArrayAdapter<String> adapter = new SpinnerAdapter(getActivity(), getResources().getStringArray(R.array.currency));
		currency.setAdapter(adapter);
		
		// Add focus change listeners that will make the hint text blank on focus
		amount.setOnFocusChangeListener(this);
		currency.setOnFocusChangeListener(this);
		
		return view;
	}
	
	@Override
    public void onFocusChange(View v, boolean hasFocus)
    {	
		// Make the hint text blank
		((EditText) v).setHint(R.string.blank);
    }

	public String getAmount() {
		return amount.getText().toString();
	}
	
	public EditText editAmount() {
		return amount;
	}
	
	public String getCurrency() {
		return currency.getSelectedItem().toString();
	}
	
	public Spinner editCurrency() {
		return currency;
	}
	
	public boolean testTexts(int amountHintId) {
		boolean noError = true;
		
		if (amount.getText().toString().trim().matches(getString(R.string.blank))) {
			// If amount text is white space: reset
			reset(amount, amountHintId);
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
		// Make the amount and currency editable
		amount.setFocusableInTouchMode(bool);
		currency.setEnabled(bool);
	}
}
