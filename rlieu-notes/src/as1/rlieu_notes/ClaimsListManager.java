package as1.rlieu_notes;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class ClaimsListManager {
	
	private static final String FILENAME = "file.sav";
	private static Context context;
	
	public static void init(Context context) {
		ClaimsListManager.context = context;
	}
	
	public static Context getContext() {
		if (context == null) {
			throw new RuntimeException("Init has not been called");
		}
		return context;
	}
	
	// Based on http://stackoverflow.com/questions/12910503/read-file-as-string
	public static String convertStreamToString(InputStream is) throws Exception {
	    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	    StringBuilder sb = new StringBuilder();
	    String line = null;
	    
	    // Build sb by reading lines from reader into line
	    while ((line = reader.readLine()) != null) {
	      sb.append(line).append("\n");
	    }
	    
	    reader.close();
	    
	    return sb.toString();
	}
	
	// Based on http://www.survivingwithandroid.com/2013/10/android-json-tutorial-create-and-parse.html
	public static void saveJSON() throws JSONException, IOException {
		//Write current ClaimsList to FILENAME
		FileOutputStream fos = getContext().openFileOutput(FILENAME, 0);
		OutputStreamWriter osw = new OutputStreamWriter(fos);
		
		// Create JSONArray to serialize current ClaimsList
		JSONArray claimsList = new JSONArray();
		for (ClaimItem claim : ClaimsListController.getList().getList()) {
			JSONObject claimItem = new JSONObject();
			claimItem.put("name", claim.getName());
			claimItem.put("startDate", claim.getStartDate());
			claimItem.put("endDate", claim.getEndDate());
			claimItem.put("description", claim.getDescription());
			claimItem.put("status", claim.getStatus());

			JSONArray expenseList = new JSONArray();
			for (ExpenseItem expense : claim.getExpenselist().getList()) {
				JSONObject expenseItem = new JSONObject();
				expenseItem.put("name", expense.getName());
				expenseItem.put("date", expense.getStartDate());
				expenseItem.put("description", expense.getDescription());
				expenseItem.put("category", expense.getCategory());
				expenseItem.put("amount", expense.getAmount());
				expenseItem.put("currency", expense.getCurrency());
				expenseList.put(expenseItem);
			}
			claimItem.put("expenseList", expenseList);
			claimsList.put(claimItem);
		}
		
		osw.write(claimsList.toString());
		osw.flush();
		osw.close();
	}
	
	// Based on http://www.survivingwithandroid.com/2013/10/android-json-tutorial-create-and-parse.html
	public static void loadJSON() throws Exception {
		// Read JSONArray from FILENAME
		FileInputStream fis = getContext().openFileInput(FILENAME);
		String json = convertStreamToString(fis);
		
		// Populate ClaimsList with JSONArray's data
		JSONArray claimsList = new JSONArray(json);
		for (int i = 0; i < claimsList.length(); i++) {
			JSONObject claim = claimsList.getJSONObject(i);
			String claimName = claim.getString("name");
			String claimStartDate = claim.getString("startDate");
			String claimEndDate = claim.getString("endDate");
			String claimDescription = claim.getString("description");
			String claimStatus = claim.getString("status");
			
			ExpenseList expList = new ExpenseList();
			JSONArray expenseList = claim.getJSONArray("expenseList");
			for (int j = 0; j < expenseList.length(); j++) {
				JSONObject expense = expenseList.getJSONObject(j);
				String expenseName = expense.getString("name");
				String expenseDate = expense.getString("date");
				String expenseDescription = expense.getString("description");
				String expenseCategory = expense.getString("category");
				String expenseAmount = expense.getString("amount");
				String expenseCurrency = expense.getString("currency");
				
				ExpenseItem expenseItem = new ExpenseItem(expenseName, expenseDate, expenseDescription, 
						expenseCategory, expenseAmount, expenseCurrency);
				expList.addItem(expenseItem);
			}
			
			ClaimItem claimItem = new ClaimItem(claimName, claimStartDate, claimEndDate,
					claimDescription, claimStatus, expList);
			ClaimsListController.listaddclaim(claimItem);
		}
		
		fis.close();
	}
	
}
