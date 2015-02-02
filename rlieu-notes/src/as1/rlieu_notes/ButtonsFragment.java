package as1.rlieu_notes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ButtonsFragment extends Fragment {
	
	private Button top;
	private Button left;
	private Button right;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.buttonsfragment, container, false);
		
		// Initialize
		top = (Button)view.findViewById(R.id.top);
		left = (Button)view.findViewById(R.id.left);
		right = (Button)view.findViewById(R.id.right);
		
		return view;
	}

	public Button getTop() {
		return top;
	}

	public Button getLeft() {
		return left;
	}

	public Button getRight() {
		return right;
	}
	
}
