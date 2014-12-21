package com.example.mtbf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import com.asus.mtbf.adapter.MTBFAdapter;
import com.asus.mtbf.model.MTBF;
import com.example.beffect.R;

public class MainActivity extends Activity {
	public static final String TAG = "Jackal";
	private static final String BLURRED_IMG_PATH = "blur_sky_image.png";

	public ImageView mBlurredImage;
	public Button mButton;
	public ListView mListView;

	private DrawerLayout layDrawer;
	private ListView lstDrawer;

	private ActionBarDrawerToggle drawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		initActionBar();
		initDrawer();
		initDrawerList();

		final int screenWidth = ImageUtils.getScreenWidth(this);
		Log.d("Jackal", "" + screenWidth);

		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_main);
		// find the view
		mBlurredImage = (ImageView) findViewById(R.id.blur_image);

		try {
			final File blurredImage = new File(getFilesDir() + BLURRED_IMG_PATH);
			new Thread(new Runnable() {
				@Override
				public void run() {
					BitmapFactory.Options options = new BitmapFactory.Options();
					options.inSampleSize = 2;
					Bitmap image = BitmapFactory.decodeResource(getResources(),
							R.drawable.sky);
					Bitmap newImg = Blur.fastblur(MainActivity.this, image, 20);
					ImageUtils.storeImage(newImg, blurredImage);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							//updateView(screenWidth);
						}
					});

				}

			}).start();
		} catch (Exception ee) {
			Log.i("Jackal", "Exception Error " + ee.getMessage().toString());
		}

		String[] MTBFNumberAry = getResources().getStringArray(
				R.array.MTBFStabilityNumber);
		String[] MTBFNameAry = getResources().getStringArray(
				R.array.MTBFStabilityName);

		String[] MTBFSuccessRateAry = getResources().getStringArray(
				R.array.MTBFStabilitySuccessRate);

		ArrayList<MTBF> mtbfList = new ArrayList<MTBF>();

		for (int i = 0; i < MTBFNumberAry.length; i++) {
			MTBF m = new MTBF(MTBFNumberAry[i], MTBFNameAry[i],
					MTBFSuccessRateAry[i] + "%");
			mtbfList.add(m);
		}

		// Create the adapter to convert the array to views
		MTBFAdapter adapter = new MTBFAdapter(this, mtbfList);

		mListView = (ListView) findViewById(R.id.list);
		mListView.setAdapter(adapter);

		

	}

	private void initActionBar() {
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
	}

	private void initDrawer() {
		setContentView(R.layout.activity_main);

		layDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
		lstDrawer = (ListView) findViewById(R.id.list_slidermenu);

		mTitle = mDrawerTitle = getTitle();
		drawerToggle = new ActionBarDrawerToggle(
				this,
				layDrawer,
				R.drawable.ic_launcher,
				R.string.open,
				R.string.close) 
		{
			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getActionBar().setTitle(mTitle);
			}

			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getActionBar().setTitle(mDrawerTitle);
			}
		};
		drawerToggle.syncState();
		layDrawer.setDrawerListener(drawerToggle);
	}

	
	private void initDrawerList(){
        String[] drawer_menu = this.getResources().getStringArray(R.array.MTBFStabilityName);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.drawer_list, drawer_menu);
        lstDrawer.setAdapter(adapter);
    }
	
	
	
	public void RunBlurImage(final int screenWidth) {

	}

	private void updateView(final int screenWidth) {
		Bitmap bmpBlurred = BitmapFactory.decodeFile(getFilesDir()
				+ BLURRED_IMG_PATH);
		bmpBlurred = Bitmap
				.createScaledBitmap(
						bmpBlurred,
						screenWidth,
						(int) (bmpBlurred.getHeight() * ((float) screenWidth) / (float) bmpBlurred
								.getWidth()), false);

		mBlurredImage.setImageBitmap(bmpBlurred);

		// mBlurredImageHeader.setoriginalImage(bmpBlurred);
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
