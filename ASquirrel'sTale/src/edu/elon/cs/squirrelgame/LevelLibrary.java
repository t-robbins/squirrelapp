/**
 * @author Schuyler Goodwin
 * @author Thomas Robbins
 * @author Matthew McKenzie
 * 
 * Class that works like a library for all the levels.
 * All the obstacles are created here for each level 
 * along with the creation of each level and adding to
 * an array list of all levels. 
 */
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
	
	private ArrayList<Rect> obstacles1, obstacles2, obstacles3;
	
	private float screenSizeX, screenSizeY;
	
	private int healthLevel = 100;
	private int background1, background2, background3;
	
	private int numLevels = 3;
	public LevelLibrary(Context context){
		
		this.context = context; 
		
		library = new ArrayList<Level>();
		
		
		background1 = R.drawable.levelone;
		background2 = R.drawable.leveltwo;
		background3 = R.drawable.levelthree;
		
		obstacles1 = new ArrayList<Rect>();
		obstacles2 = new ArrayList<Rect>();
		obstacles3 = new ArrayList<Rect>();

		DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		screenSizeX = dm.widthPixels;
		screenSizeY = dm.heightPixels; 
		
		
		/*
		 * =======================================
		 * |									 |
		 * |	         Level One     		 	 |
		 * =======================================
		 */
		//sX = 480 sY = 320
		Rect menuBar = new Rect(
				/*LEFT*/(int)(0), 
				/*TOP*/(int)(screenSizeY / 1.072026801), 
				/*RIGHT*/(int)(screenSizeX), 
				/*BOTTOM*/(int)(screenSizeY));
		obstacles1.add(menuBar);
				
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
				/*BOTTOM*/(int)(screenSizeY / 1.0));
		obstacles1.add(lakeMainDining);
		Rect lakesideMainDiningLeft = new Rect(
				/*LEFT*/(int)(screenSizeX / 5.026178010), 
				/*TOP*/(int)(screenSizeY / 1.358811040), 
				/*RIGHT*/(int)(screenSizeX / 3.918367347), 
				/*BOTTOM*/(int)(screenSizeY / 1.0));
		obstacles1.add(lakesideMainDiningLeft);
		Rect lakesideMainDiningRight = new Rect(
				/*LEFT*/(int)(screenSizeX / 1.794392523), 
				/*TOP*/(int)(screenSizeY / 1.379310344), 
				/*RIGHT*/(int)(screenSizeX / 1.5), 
				/*BOTTOM*/(int)(screenSizeY / 1.0));
		obstacles1.add(lakesideMainDiningRight);

		levelOne = new Level(4, 2,1, 0, 0, obstacles1, context, background1, healthLevel);
		library.add(levelOne);

		/*
		 * =======================================
		 * |									 |
		 * |	         Level Two    		 	 |
		 * =======================================
		 */
		//sX = 480 sY = 320
		Rect menuBar2 = new Rect(
				/*LEFT*/(int)(0), 
				/*TOP*/(int)(screenSizeY / 1.072026801), 
				/*RIGHT*/(int)(screenSizeX), 
				/*BOTTOM*/(int)(screenSizeY));
		obstacles2.add(menuBar2);
				
		Rect oaksC = new Rect(
				/*LEFT*/(int)(screenSizeX / 9.320388350), 
				/*TOP*/(int)(screenSizeY / 3.832335329), 
				/*RIGHT*/(int)(screenSizeX / 2.637362637), 
				/*BOTTOM*/(int)(screenSizeY / 2.539682540));
		obstacles2.add(oaksC);
		Rect oaksD = new Rect(
				/*LEFT*/(int)(screenSizeX / 3.764705882), 
				/*TOP*/(int)(screenSizeY / 1.588089330), 
				/*RIGHT*/(int)(screenSizeX / 1.357850071), 
				/*BOTTOM*/(int)(screenSizeY / 1.316872428));
		obstacles2.add(oaksD);
		Rect oaksE = new Rect(
				/*LEFT*/(int)(screenSizeX / 1.807909605), 
				/*TOP*/(int)(screenSizeY / 3.832335329), 
				/*RIGHT*/(int)(screenSizeX / 1.145584726), 
				/*BOTTOM*/(int)(screenSizeY /2.539682540));
		obstacles2.add(oaksE);
		
		levelTwo = new Level(4, 1,1,1, 0, obstacles2, context, background2, healthLevel);
		library.add(levelTwo);
		/*
		 * =======================================
		 * |									 |
		 * |	         Level Three    		 |
		 * =======================================
		 */
		//sX = 480 sY = 320
		Rect menuBar3 = new Rect(
				/*LEFT*/(int)(0), 
				/*TOP*/(int)(screenSizeY / 1.072026801), 
				/*RIGHT*/(int)(screenSizeX), 
				/*BOTTOM*/(int)(screenSizeY));
		obstacles3.add(menuBar3);
		
		Rect belkPavRight = new Rect(
				/*LEFT*/(int)(screenSizeX / 5.245901639), 
				/*TOP*/(int)(screenSizeY / 5.663716814), 
				/*RIGHT*/(int)(screenSizeX / 3.157894737), 
				/*BOTTOM*/(int)(screenSizeY / 3.047619048));
		obstacles3.add(belkPavRight);
		
		Rect spencePavRight = new Rect(
				/*LEFT*/(int)(screenSizeX / 2.696629213), 
				/*TOP*/(int)(screenSizeY / 5.765765766), 
				/*RIGHT*/(int)(screenSizeX / 2.016806723), 
				/*BOTTOM*/(int)(screenSizeY / 3.047619048));
		obstacles3.add(spencePavRight);
				
		Rect kenanPavRight = new Rect(
				/*LEFT*/(int)(screenSizeX / 1.801125704), 
				/*TOP*/(int)(screenSizeY / 5.765765766), 
				/*RIGHT*/(int)(screenSizeX / 1.479198767), 
				/*BOTTOM*/(int)(screenSizeY /3.333333333));
		obstacles3.add(kenanPavRight);
		
		Rect lindnerBuilding = new Rect(
				/*LEFT*/(int)(screenSizeX / 1.346423562), 
				/*TOP*/(int)(screenSizeY / 3.368421053), 
				/*RIGHT*/(int)(screenSizeX / 1.153846154), 
				/*BOTTOM*/(int)(screenSizeY / 1.415929204));
		obstacles3.add(lindnerBuilding);

		
		Rect isabellaPavLeft = new Rect(
				/*LEFT*/(int)(screenSizeX / 1.777777778), 
				/*TOP*/(int)(screenSizeY / 1.441441441), 
				/*RIGHT*/(int)(screenSizeX / 1.461187215), 
				/*BOTTOM*/(int)(screenSizeY / 1.185185185));
		obstacles3.add(isabellaPavLeft);
		
		Rect grayPavRight = new Rect(
				/*LEFT*/(int)(screenSizeX / 2.644628099), 
				/*TOP*/(int)(screenSizeY / 1.438202247), 
				/*RIGHT*/(int)(screenSizeX / 1.979381443), 
				/*BOTTOM*/(int)(screenSizeY /1.182994455));
		obstacles3.add(grayPavRight);

		
		levelThree = new Level(3, 1,1, 1, 1, obstacles3, context, background3, healthLevel);
		library.add(levelThree);
	}
	
	public ArrayList<Level> getLevelList(){
		System.out.println("level list gotten"); 	
		return library;
		
	}
	
	public int getNumLevels(){
		return numLevels;
	}
}
