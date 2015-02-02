package as1.rlieu_notes;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.DatePicker;

public class DatePickerActivity extends FragmentActivity {

	private DatePicker datePicker;
	private ButtonsFragment buttonsFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.datepickerlayout);
		
		// Initialize
		datePicker = (DatePicker) findViewById(R.id.datepicker);
		buttonsFragment = (ButtonsFragment) getSupportFragmentManager().findFragmentById(R.id.datepickerbuttons);
		
		// Setup buttonsFragment
		buttonsFragment.getLeft().setText(R.string.confirm);
		buttonsFragment.getRight().setText(R.string.cancel);
		
		try {
			// Try to retrieve data and update date on datePicker
			String[] data = getIntent().getExtras().getString("data").split("/");
			int year = Integer.parseInt(data[0]);
			int month = Integer.parseInt(data[1]) - 1;
			int day = Integer.parseInt(data[2]);
			datePicker.updateDate(year, month, day);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void left(View v) {
		// Date chosen: set resultCode to 1 and provide date with intent
		getIntent().putExtra("date", String.valueOf(datePicker.getYear()) + "/" + 
				String.valueOf(datePicker.getMonth() + 1) + "/" +
				String.valueOf(datePicker.getDayOfMonth()));
		this.setResult(1, getIntent());
		this.finish();
	}
	
	public void right(View v) {
		// Date not chosen: set resultCode to 0
		this.setResult(0);
		this.finish();
	}
	
}