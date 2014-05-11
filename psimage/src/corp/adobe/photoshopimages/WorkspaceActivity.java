package corp.adobe.photoshopimages;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class WorkspaceActivity extends Activity {
	private ArrayAdapter<String> mAdapter;
	private SharedPreferences storage;
	private int testCount = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workspace_view);
		
		storage = getPreferences(Activity.MODE_PRIVATE);

		TextView newWorkspace = (TextView) findViewById(R.id.add_workspace);
		newWorkspace.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// Update SharedPreferences storage.
				SharedPreferences.Editor editor = storage.edit();
				String strKey = new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
				String strValue = testCount++ + "";
				editor.putString(strKey, strValue);
				editor.commit();
				
				// Refresh the workspace list.
				mAdapter.add(strKey + ", " + strValue);
			}
		});
		
		initListView(getListFromStorage());
	}
	
	private List<String> getListFromStorage() {
		List<String> list = new ArrayList<String>(10);

		Map<String, String> savedMap = (HashMap<String, String>) storage.getAll();
		for (String key : savedMap.keySet()) {
			list.add(key + ", " + savedMap.get(key));
		}

		return list;
	}
	
	private void initListView(List<String> list) {
		mAdapter = new ArrayAdapter<String>(this, R.layout.workspace_item, list);

		ListView listView = (ListView) findViewById(R.id.workspace_list);
		listView.setAdapter(mAdapter);
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	MenuInflater inflater = getMenuInflater();
    	inflater.inflate(R.menu.menu2, menu);
    	return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch (item.getItemId()) {
    		case R.id.sync:
    			Intent intent = new Intent(this, MainActivity.class);
    			startActivity(intent);
    			finish();
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);    
    	}
    }
    
}
