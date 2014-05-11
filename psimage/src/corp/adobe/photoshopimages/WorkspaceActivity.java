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
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

public class WorkspaceActivity extends Activity {
	public final static String WORKSPACE = "workspace";
	private ArrayAdapter<String> mAdapter;
	private SharedPreferences storage;
	private String selectedWorkspaceStr = "noname";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.workspace_view);
		
		storage = getPreferences(Activity.MODE_PRIVATE);
		initListView(getListFromStorage());

		EditText newWorkspace = (EditText) findViewById(R.id.add_workspace);
		newWorkspace.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				boolean handled = false;
				if (actionId == EditorInfo.IME_ACTION_SEND) {
					updateList(v.getText().toString());
					// TODO: Reset text
					// TODO: Keyboard control
					handled = true;
				}
				return handled;
			}
		});
	}
	
	private void updateList(String nuName) {
		// Update SharedPreferences storage.
		SharedPreferences.Editor editor = storage.edit();
		String strKey = new SimpleDateFormat("yyMMdd_HH:mm:ss").format(new Date());
		editor.putString(strKey, nuName);
		editor.commit();
		
		// Refresh the workspace list.
		mAdapter.add(nuName + "_" + strKey);
	}
	
	private List<String> getListFromStorage() {
		List<String> list = new ArrayList<String>();

		HashMap<String, String> savedMap = (HashMap<String, String>) storage.getAll();
		for (String key : savedMap.keySet()) {
			list.add(savedMap.get(key) + "_" + key);
		}

		return list;
	}
	
	private void initListView(List<String> list) {
		mAdapter = new ArrayAdapter<String>(this, R.layout.workspace_item, list);
		OnItemClickListener itemClickListener = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				TextView textView = (TextView)view.findViewById(R.id.workspace_item);
				selectedWorkspaceStr = parseWorkspaceStr(textView.getText().toString());
			}
		};
		
		ListView listView = (ListView) findViewById(R.id.workspace_list);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(itemClickListener);
	}
	
	private String parseWorkspaceStr(String str) {
		String ws = str.substring(0, str.indexOf('_'));
		return ws;
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
    			intent.putExtra(WORKSPACE, selectedWorkspaceStr);
    			startActivity(intent);
    			finish();
    			return true;
    		default:
    			return super.onOptionsItemSelected(item);    
    	}
    }
    
}
