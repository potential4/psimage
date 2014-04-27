package corp.adobe.photoshopimages;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class OverlaidMenuHandler {
	private ActionBarActivity mActivity;
	private ActionBar mActionBar;
	private Button mSyncBtn;
	private boolean isShowing;
	private ScreenCaptureHandler mScreenCaptureHandler;
	
	public OverlaidMenuHandler(Context context) {
		mActivity = (ActionBarActivity) context;
		
		mActionBar = mActivity.getSupportActionBar();
		mSyncBtn = (Button) mActivity.findViewById(R.id.syncBtn);
		isShowing = mActionBar.isShowing() ? true : false;
		
		mScreenCaptureHandler = ScreenCaptureHandler.getScreenCaptureHandler(context);
		
		mSyncBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mScreenCaptureHandler.captureScreen();
			}
		});
	}
	
	public void toggle() {
		if (isShowing) {
			mActionBar.hide();
			mSyncBtn.setVisibility(View.INVISIBLE);
			isShowing = false;
		} else {
			mActionBar.show();
			mSyncBtn.setVisibility(View.VISIBLE);
			isShowing = true;
		}
	}
}