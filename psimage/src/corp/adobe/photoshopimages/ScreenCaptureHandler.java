package corp.adobe.photoshopimages;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;

public class ScreenCaptureHandler {
	private static ScreenCaptureHandler mHandler;
	private View mImagesView;
	private Activity mActivity;
	
	private final static String FOLDER_NAME = "PSIMAGE";
	
	private ScreenCaptureHandler(Context context) {
		mActivity = (Activity) context;
		mImagesView = (ImagesView) ((Activity)context).findViewById(R.id.imagesView);
	}
	
	public static ScreenCaptureHandler getScreenCaptureHandler(Context context) {
		if (mHandler == null) {
			mHandler = new ScreenCaptureHandler(context);
		}
		
		return mHandler;
	}

	public void captureScreen() {
		mImagesView.buildDrawingCache();
		Bitmap bitmap = mImagesView.getDrawingCache();
		
		if (isExternalStorageWritable()) {
			File file = new File(getAlbumDirFile(FOLDER_NAME), getWorkspaceStr() + "_" + getTimeFlag() + ".png");
			
			try {
				FileOutputStream out = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
				
				Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
				Uri contentUri = Uri.fromFile(file);
				mediaScanIntent.setData(contentUri);
				mActivity.sendBroadcast(mediaScanIntent);
				
				mImagesView.destroyDrawingCache();
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
		return new SimpleDateFormat("yyMMdd_HHmmss").format(new Date());
	}
	
	private String getWorkspaceStr() {
		return "WS";
	}
}
