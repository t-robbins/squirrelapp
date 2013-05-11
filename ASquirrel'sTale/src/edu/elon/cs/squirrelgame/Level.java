package edu.elon.cs.squirrelgame;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import edu.cs.elon.squirrelstale.R;

public class Level {

	protected String name = "one"; 
	int pedCount;
	double acornSpawnRate;
	int freshCount= 0, sophCount = 0, junCount = 0, senCount = 0;
	String mapSrc;
	ArrayList<Rect> obstacles;
	ArrayList<Pedestrian> peds;
	ArrayList<Acorn> acorns;
	private float screenSizeX, screenSizeY; 
	
	private Bitmap gMapBackground;
	
	private Pedestrian freshman; 
	private Pedestrian sophomore;
	private Pedestrian junior;
	private Pedestrian senior;
	
	private Squirrel squirrel; 
	private Acorn acorn;
	
	private Context context; 
	
	private Handler handler = new Handler();
	private double startTime = 0;
	private TimerTask task;
	private Timer myTimer;
	
	private boolean acornSpawnAvailable;
	
	private int min,sec;
	public Level(double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount, ArrayList<Rect> obstacles, Context context){
		
		this.obstacles = obstacles;
		this.context = context; 
		
		gMapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.levelone_background);
		
		squirrel = new Squirrel(context);
		acorn = new Acorn(context);
		
		freshman = new Pedestrian(context, R.drawable.freshman_ped, 5); 
		sophomore = new Pedestrian(context, R.drawable.sophomore_ped, 10);
		junior = new Pedestrian(context, R.drawable.junior_ped, 15);
		senior = new Pedestrian(context, R.drawable.senior_ped, 20);
		
		this.freshCount = freshCount; 
		this.sophCount = sophCount; 
		this.junCount = junCount;
		this.senCount = senCount; 
		
		pedCount = freshCount + sophCount + junCount + senCount;
		
		createPedList(); 
		
		this.mapSrc = mapSrc;
		
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		screenSizeX = dm.widthPixels;
		screenSizeY = dm.heightPixels; 
		
	
		myTimer = new Timer();
		//myTimer.cancel();
		task = new TimerTask(){
			@Override
			public void run(){
				updateTimer();
			}
		};
		
		myTimer.schedule(task, 0, 1000);
		
		
		
		acorns = new ArrayList<Acorn>();
		acorns.add(acorn);
	}
	
	//populating an array of instantated pedsetiran objects that will exist on the level 
	private void createPedList(){
		
		System.out.println("create ped list"); 
		
		peds = new ArrayList<Pedestrian>();
		
		System.out.println("f is "+0+", count is: "+freshCount);
		
		for(int f = 0; f < freshCount; f++){
			System.out.println("f is "+f+", count is: "+freshCount); 
			peds.add(freshman.clone());
		}
		
		for(int so = 0; so < sophCount; so++){
			peds.add(sophomore.clone()); 
		}
		for(int j = 0; j < junCount; j++){
			peds.add(junior.clone());
		}
		for(int se = 0; se < senCount; se++){
			peds.add(senior.clone());
		}
	}
	
	public ArrayList<Pedestrian> getPedList(){
		return peds;
	}
	
	public String getMapSrc(){
		return mapSrc;
	}
	
	protected void doDraw(Canvas canvas){
		if(canvas != null){
			//canvas.drawBitmap(board, 0, 0, null);
			canvas.drawBitmap(gMapBackground, null,
					new Rect(0, 0, (int)screenSizeX, (int)screenSizeY),
							null);
			for(Pedestrian ped : peds){
				ped.doDraw(canvas);  
			}
			
			for(Acorn corn : acorns){
				corn.doDraw(canvas);
			}
			
			//draw obstacles

			squirrel.doDraw(canvas); 
		}
	}
	
	protected void update(double elapsed, float yAccel, float xAccel) {
	
		
		if(acornSpawnAvailable && (startTime) % 10 == 0){
			System.out.println((System.currentTimeMillis()/1000));
			Random generator = new Random();
			
			
			float randY = generator.nextFloat()*(acorn.screenHeight - acorn.height);
			float randX = generator.nextFloat()*((acorn.screenWidth) - acorn.width);
			System.out.println("rX: " + randX + "  rY: " + randY);
			System.out.println("sH: " + acorn.screenHeight + " sW: " + acorn.screenWidth);
			
			
			Acorn clone = acorn.clone(randX, randY);
			acorns.add(clone);
			acornSpawnAvailable = false;
		}
		
		for(Acorn corn : acorns){
			corn.update(squirrel.currentX, squirrel.currentY);			
		}
		for(Pedestrian ped : peds){
			ped.update(elapsed, squirrel.currentX, squirrel.currentY); 
		}
		
		squirrel.update(yAccel, xAccel); 
		
		
		
	}
	
	
	//Move timer to BoardView, and remove "elapsed" from boardView, replace with startTime
	 private void updateTimer() {
		  /* Updates timer*/
	      startTime++;
	      acornSpawnAvailable = true;
	      handler.post(timeRunner);
	   }
	 final Runnable timeRunner = new Runnable() {
		  /*Creates runnable for the start timer */
	      public void run() {
	    	  /*executes the start timer */
	    	   min = (int) startTime / 60;
	    	   sec = (int) startTime - (min * 60);
	    	  
	    	  System.out.println(min + ":" + modifyDigits(sec));
	      }
	   };
	   
	   private String modifyDigits(int number) {
			
			String num = "" + number;
			if (num.length() == 1) {
				num = "0" + num;
			}
			return num;
		}
}
