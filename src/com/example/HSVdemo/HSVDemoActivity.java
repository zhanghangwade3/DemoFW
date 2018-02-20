package com.example.HSVdemo;

import java.io.File;

import com.example.galleydemo.R;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.Toast;
/**
 *  HorizontalScrollView in place of the Type Gallery
 * @author Administrator
 *
 */
public class HSVDemoActivity extends Activity {
	
	private static final String tag="GH";
	private static final String path="/mnt/sdcard";
	private LinearLayout myGallery;
	private LinearLayout yourGallery;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hsv_demo);
        
        myGallery=(LinearLayout) findViewById(R.id.mygallery);
        yourGallery=(LinearLayout) findViewById(R.id.yourgallery);
        
        //pic in the drawable
        Integer[] images = { R.drawable.img0001, R.drawable.img0030,
                R.drawable.img0100, R.drawable.img0130, R.drawable.img0200,
                R.drawable.img0230, R.drawable.img0300, R.drawable.img0330,
                R.drawable.img0354 };
        
        for(Integer id:images){
        	myGallery.addView(insertImage(id));
        }
        

        //pic in the sdcard
        String targetPath=path+"/test/";
        Log.d(tag,targetPath);
        Toast.makeText(getApplicationContext(), targetPath,Toast.LENGTH_LONG).show();
        
        File targetDirector=new File(targetPath);
        File[] files=targetDirector.listFiles();
        Log.d(tag,files.length+"");
        for(File file:files){
        	yourGallery.addView(insertPhoto(file.getAbsolutePath()));
        }
        
    }

	private View insertImage(Integer id) {
		LinearLayout layout=new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(320,320));
		layout.setGravity(Gravity.CENTER);
		
		ImageView imageView=new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(300,300));
		imageView.setBackgroundResource(id);
		
		layout.addView(imageView);
		return layout;
	}


	private View insertPhoto(String absolutePath) {
		// TODO Auto-generated method stub
		Bitmap bm=decodeSampleBitmapFromUri(absolutePath,200,200);
		LinearLayout layout=new LinearLayout(getApplicationContext());
		layout.setLayoutParams(new LayoutParams(250,250));
		layout.setGravity(Gravity.CENTER);
		
		ImageView imageView=new ImageView(getApplicationContext());
		imageView.setLayoutParams(new LayoutParams(220,220));
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);
		
		layout.addView(imageView);
		return layout;
	}


	private Bitmap decodeSampleBitmapFromUri(String absolutePath, int reqWidth, int reqHeight) {
		// TODO Auto-generated method stub
		Bitmap bm=null;
		
		// First decode with inJustDecodeBounds=true to check dimensions
		final Options options=new Options();
		options.inJustDecodeBounds=true;
		BitmapFactory.decodeFile(absolutePath,options);
		
		// Calculate inSampleSize
		options.inSampleSize=calculateInSampleSize(options,reqWidth,reqHeight);
		
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds=false;
		bm=BitmapFactory.decodeFile(absolutePath,options);
		return bm;
	}


	private int calculateInSampleSize(Options options, int reqWidth,
			int reqHeight) {
		// TODO Auto-generated method stub
		// Raw height and width of image
		final int height=options.outHeight;
		final int width=options.outWidth;
		int inSampleSize=1;
		
		if(height>reqHeight||width>reqWidth){
			if(width>height){
				inSampleSize=Math.round((float)height/(float)reqHeight);
			}else{
				inSampleSize = Math.round((float)width / (float)reqWidth);
			}
		}
		return inSampleSize;
	}

}
