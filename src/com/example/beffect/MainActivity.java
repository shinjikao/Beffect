package com.example.beffect;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;



import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class MainActivity extends Activity{
	private static final String BLURRED_IMG_PATH = "blur_sky_image.png";

	public ImageView mBlurredImage;
	public Button mButton;
	private ListView mList;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		
		final int screenWidth = ImageUtils.getScreenWidth(this);
		Log.d("Jackal", ""+screenWidth);
		
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		//find the view
		mBlurredImage =(ImageView)findViewById(R.id.blur_image);
		mList = (ListView) findViewById(R.id.list);
				
		try{
			final File blurredImage = new File(getFilesDir()+ BLURRED_IMG_PATH);
			new Thread(new Runnable()
			{
				@Override
				public void run(){
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2 ;
					Bitmap image = BitmapFactory.decodeResource(getResources(),R.drawable.sky);
					Bitmap newImg = Blur.fastblur(MainActivity.this, image,20);
					ImageUtils.storeImage(newImg, blurredImage);
					runOnUiThread(new Runnable(){
						@Override
						public void run(){
							updateView(screenWidth);	
						}
					});
					
					
				}
				
			}).start();
		}catch(Exception ee)
		{
			Log.i("Jackal", "Exception Error " +ee.getMessage().toString());
		}	
		
		
		
		String[] strings = getResources().getStringArray(R.array.list_content);
		
		mList.setAdapter(new ArrayAdapter<String>(this, R.layout.list_item, strings));
		mList.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {

			}

			/**
			 * Listen to the list scroll. This is where magic happens ;)
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				// Calculate the ratio between the scroll amount and the list
				// header weight to determinate the top picture alpha
				

			}
		});
		
		
		
		//RunBlurImage(screenWidth);
		
		
	}
	
	public void RunBlurImage(final int screenWidth)
	{
	
		
	}
	private void updateView(final int screenWidth) {
		Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir()+ BLURRED_IMG_PATH);
		bmpBlurred = Bitmap.createScaledBitmap(bmpBlurred, screenWidth, (int) (bmpBlurred.getHeight()
				* ((float) screenWidth) / (float) bmpBlurred.getWidth()), false);

		mBlurredImage.setImageBitmap(bmpBlurred);

		//mBlurredImageHeader.setoriginalImage(bmpBlurred);
	}
	
	 
	private static void copyFileUsingFileStreams(File source, File dest)
			throws IOException {
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
			input.close();
			output.close();
		}
	}

}
