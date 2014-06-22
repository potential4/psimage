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
	private Button mCaptureBtn, mFitWidthBtn;
	private boolean isShowing;
	private ScreenCaptureHandler mScreenCaptureHandler;
	private ChangeSizeHandler mChangeSizeHandler;
	
	public OverlaidMenuHandler(Context context) {
		mActivity = (ActionBarActivity) context;
		
		mActionBar = mActivity.getSupportActionBar();
		mCaptureBtn = (Button) mActivity.findViewById(R.id.screenCaptureBtn);
		mFitWidthBtn = (Button) mActivity.findViewById(R.id.fitToWidthBtn);
		isShowing = mActionBar.isShowing() ? true : false;
		
		mScreenCaptureHandler = ScreenCaptureHandler.getScreenCaptureHandler(context);
		mCaptureBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mScreenCaptureHandler.captureScreen();
			}
		});
		
		mChangeSizeHandler = ChangeSizeHandler.getChangeSizeHandler(context);
		mFitWidthBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mChangeSizeHandler.fitToWidth();
			}
		});
	}
	
	public void toggle() {
		if (isShowing) {
			mActionBar.hide();
			mCaptureBtn.setVisibility(View.INVISIBLE);
			mFitWidthBtn.setVisibility(View.INVISIBLE);
			isShowing = false;
		} else {
			mActionBar.show();
			mCaptureBtn.setVisibility(View.VISIBLE);
			mFitWidthBtn.setVisibility(View.VISIBLE);
			isShowing = true;
		}
	}
	
	public void setTitle(String str) {
		mActionBar.setTitle(str);
	}
}