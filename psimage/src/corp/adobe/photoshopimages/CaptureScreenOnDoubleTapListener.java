package corp.adobe.photoshopimages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

class CaptureScreenOnDoubleTapListener extends GestureDetector.SimpleOnGestureListener {
	private View mView;
	
	public CaptureScreenOnDoubleTapListener(View view) {
		this.mView = view;
	}
	
	@Override
    public boolean onDoubleTap(MotionEvent event) {
		// Capture screen and save it to bitmap
		View rootView = mView.getRootView();
		rootView.setDrawingCacheEnabled(true);
		Bitmap bitmap = rootView.getDrawingCache();
		
		if (isExternalStorageWritable()) { // Check RW permission
			File file = new File(getAlbumDirFile("TEST"), "test" + ".png");
			try {
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Log.d("naheon", "file not found");
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("naheon", "?");
			} 
		}
		
		return true;
    }
	
	/** Check if external storage is available for read and write */
	private boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	/** Create a directory under Pictures */
	private File getAlbumDirFile(String albumName) {
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumName);
		
		if (!file.isDirectory()) { // If [Pictures]/albumName does not exists
			//Log.d("naheon", albumName + " created");
			file.mkdirs(); // Create a directory
		} else {
			//Log.d("naheon", albumName + " exists");
		}
		
		return file;
	}
}
