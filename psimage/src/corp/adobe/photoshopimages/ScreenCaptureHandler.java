package corp.adobe.photoshopimages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.text.format.Time;
import android.util.Log;
import android.view.View;

public class ScreenCaptureHandler {
	private static ScreenCaptureHandler mHandler;
	private View mImagesView;
	private Time mTime;
	
	private final static String FOLDER_NAME = "PSIMAGE";
	
	private ScreenCaptureHandler(Context context) {
		mImagesView = (ImagesView) ((Activity)context).findViewById(R.id.imagesView);
		mImagesView.setDrawingCacheEnabled(true);
		mTime = new Time();
	}
	
	public static ScreenCaptureHandler getScreenCaptureHandler(Context context) {
		if (mHandler == null) {
			mHandler = new ScreenCaptureHandler(context);
		}
		
		return mHandler;
	}

	public void captureScreen() {
		Bitmap bitmap = mImagesView.getDrawingCache();
		
		if (isExternalStorageWritable()) {
			File file = new File(getAlbumDirFile(FOLDER_NAME), getTimeFlag() + ".png");
			
			try {
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				//Log.d("naheon", "created");
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Log.d("naheon", "file not found");
			} catch (IOException e) {
				e.printStackTrace();
				Log.d("naheon", "?");
			} 
		}
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
	
	private String getTimeFlag() {
		mTime.setToNow();
		return mTime.format2445();
	}
}
