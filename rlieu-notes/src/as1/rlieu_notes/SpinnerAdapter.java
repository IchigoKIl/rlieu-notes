package as1.rlieu_notes;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SpinnerAdapter extends ArrayAdapter<String> {
	
	private String text;
	private TextView item;
	
	public SpinnerAdapter(Context context, String[] strings) {
		super(context, R.layout.spinnerlistrow, strings);
	}

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater listInflater = LayoutInflater.from(getContext());
		View listRow = listInflater.inflate(R.layout.spinnerlistrow, parent, false);
		
		// Initialize
		text = getItem(position);
		item = (TextView) listRow.findViewById(R.id.spinneritem);
		
		// Setup TextViews
		item.setText(text);
		
        return listRow;
    }
    
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
    	LayoutInflater listInflater = LayoutInflater.from(getContext());
		View listRow = listInflater.inflate(R.layout.spinnerlistrow, parent, false);
		
		// Initialize
		text = getItem(position);
		item = (TextView) listRow.findViewById(R.id.spinneritem);
		
		// Setup TextViews
		item.setText(text);
		item.setTextColor(android.graphics.Color.BLACK);
		
        return listRow;
    }
	
}
