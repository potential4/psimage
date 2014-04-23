package corp.adobe.photoshopimages;

import android.support.v7.app.ActionBar;
import android.widget.Button;

public class OverlaidMenuHandler {
	private ActionBar mActionBar;
	private ImagesView mMainView;
	private Button mSyncBtn;
	private boolean isShowing;
	
	public OverlaidMenuHandler(ImagesView imagesView, ActionBar actionBar) {
		mMainView = imagesView;
		mActionBar = actionBar;
		
		isShowing = mActionBar.isShowing() ? true : false;
		mSyncBtn = imagesView.getSyncButton();
	}
	
	public void toggle() {
		if (isShowing) {
			mActionBar.hide();
			showSyncButton(false);
			isShowing = false;
		} else {
			mActionBar.show();
			showSyncButton(true);
			isShowing = true;
		}
	}
	
	private void showSyncButton(boolean show) {
	}
}