package corp.adobe.photoshopimages;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;

public class ConnectActivity extends Activity {
	public static final String CONNECT_IP = "ip";
	public static final String CONNECT_PASSWORD = "password";
	
	private Activity mThis;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.login);
    	
    	mThis = this;

    	findViewById(R.id.connectBtn).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String mIP = ((EditText)mThis.findViewById(R.id.ip)).getText().toString();
				String mPassword = ((EditText)mThis.findViewById(R.id.password)).getText().toString();
				
				// Check IP and password are valid
				if (!isValidIP(mIP) || !isValidPassword(mPassword)) {
					connectToast();
					return;
				}
				
				// Save IP address and password to intent
				Intent intent = new Intent(mThis, MainActivity.class);
				intent.putExtra(CONNECT_IP, mIP);
				intent.putExtra(CONNECT_PASSWORD, mPassword);
				
				// Start MainActivity
				startActivity(intent);
				finish();
			}
		});
    }
	
	private boolean isValidIP(String ip) {
		// TODO: ip format check
		return !(ip.equals("") || ip == null);
	}
	
	private boolean isValidPassword(String pw) {
		return !(pw.equals("") || pw == null);
	}
	
	private void connectToast() {
		Context context = getApplicationContext();
		CharSequence text = "Put IP address and password to connect.";
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}