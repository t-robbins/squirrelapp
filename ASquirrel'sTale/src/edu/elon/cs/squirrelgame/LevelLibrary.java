package edu.elon.cs.squirrelgame;

import java.util.ArrayList;

import edu.cs.elon.squirrelstale.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class LevelLibrary {

	private Level levelOne, levelTwo, levelThree;
	
	private ArrayList<Level> library;
	private Context context;
	
	private Bitmap bmp1, bmp2, bmp3;
	private float w1, h1, w2, h2, w3, w4;
	private ArrayList<Rect> obstacles1, obstacles2, obstacles3;
	
	private float screenSizeX, screenSizeY;
	public LevelLibrary(Context context){
		
		this.context = context; 
		
		library = new ArrayList<Level>();
		
		
		int background1 = R.drawable.levelone;
		//int background1 = R.drawable.leveltwo;
		//int background1 = R.drawable.levelthree;
		
		obstacles1 = new ArrayList<Rect>();

		DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		screenSizeX = dm.widthPixels;
		screenSizeY = dm.heightPixels; 
		
		/*
		 * =======================================
		 * |									 |
		 * |	         Level One     		 	 |
		 * =======================================
		 */
		Bitmap bmp1 = BitmapFactory.decodeResource(context.getResources(), background1);
		w1 = bmp1.getWidth();
		h1 = bmp1.getHeight();
		
		float resize_factorW = w1/screenSizeX;
		
		System.out.println(resize_factorW);
		float resize_factorH = h1/screenSizeY;
		System.out.println(resize_factorH);
		
		//sX = 480 sY = 320
		Rect lakesideBottom = new Rect(
				/*LEFT*/(int)(screenSizeX / 3.310344828), 
				/*TOP*/(int)(screenSizeY / 2.935779817), 
				/*RIGHT*/(int)(screenSizeX / 2.042553191), 
				/*BOTTOM*/(int)(screenSizeY / 1.425389755));
		obstacles1.add(lakesideBottom);
		Rect lakeMainDining = new Rect(
				/*LEFT*/(int)(screenSizeX / 4.137931034), 
				/*TOP*/(int)(screenSizeY / 1.431767338), 
				/*RIGHT*/(int)(screenSizeX / 1.791044776), 
				/*BOTTOM*/(int)(screenSizeY / 1));
		obstacles1.add(lakeMainDining);
		
		levelOne = new Level(10, 2, 2, 2, 2, obstacles1, context, background1);
		library.add(levelOne);
		System.out.println("levelOne added!"); 
		System.out.println("at index 0: "+library.get(0).name); 
		
		
//		levelTwo = new Level(/*properties*/);
//		library.add(levelTwo);
//		levelThree = new Level(/*properties*/);
//		library.add(levelThree);
	}
	
	public ArrayList<Level> getLevelList(){
		System.out.println("level list gotten"); 	
		return library;
		
	}

}
