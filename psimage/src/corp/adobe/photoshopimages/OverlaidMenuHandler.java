package corp.adobe.photoshopimages;

import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

public class OverlaidMenuHandler {
	private ActionBar mActionBar;
	private Button mSyncBtn;
	private boolean isShowing;
	
	public OverlaidMenuHandler(ActionBar actionBar, Button syncBtn) {
		mActionBar = actionBar;
		mSyncBtn = syncBtn;
		
		isShowing = mActionBar.isShowing() ? true : false;
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