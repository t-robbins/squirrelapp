package edu.elon.cs.squirrelgame;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.os.Handler;
import android.util.DisplayMetrics;
import edu.cs.elon.squirrelstale.R;

public class Level {

	protected String name = "one"; 
	private int pedCount; 
	private double acornSpawnRate;
	private int freshCount= 0, sophCount = 0, junCount = 0, senCount = 0;
	private String mapSrc;
	private ArrayList<Rect> obstacles;
	private ArrayList<Pedestrian> peds;
	private ArrayList<Acorn> acorns;
	private float screenSizeX, screenSizeY; 
	
	private Bitmap gMapBackground;
	
	private Pedestrian freshman; 
	private Pedestrian sophomore;
	private Pedestrian junior;
	private Pedestrian senior;
	
	private Squirrel squirrel; 
	private Acorn acorn;
//	private HealthBar healthBar; 
	
	private Context context; 
	
	protected double levelTime = 0;
	private int pedKilledCount = 0;
	private int acornCount = 0; 
	private double timeSinceSpawn = 0;
	protected float randX, randY;
	protected Random generator;
	
	private int min,sec;
	public Level(double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount, ArrayList<Rect> obstacles, Context context, int background){
		
		this.obstacles = obstacles;
		this.context = context; 
		this.acornSpawnRate = acornSpawnRate; 
//		this.healthBar = new HealthBar(0, 0, 10); 
		
		gMapBackground = BitmapFactory.decodeResource(context.getResources(), background);
		
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
		
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		screenSizeX = dm.widthPixels;
		screenSizeY = dm.heightPixels; 
		
	
		
		acorns = new ArrayList<Acorn>();
		acorns.add(acorn);
	}
	
	//populating an array of instantiated pedestrian objects that will exist on the level 
	private void createPedList(){
		
		peds = new ArrayList<Pedestrian>();

		generator = new Random();
		
		
		
		for(int f = 0; f < freshCount; f++){
			randY = generator.nextFloat()*(screenSizeY - freshman.height);
			randX = generator.nextFloat()*(screenSizeX - freshman.width);

			peds.add(freshman.clone(randX, randY));
		}
		
		for(int so = 0; so < sophCount; so++){
			randY = generator.nextFloat()*(screenSizeY -sophomore.height);
			randX = generator.nextFloat()*(screenSizeX - sophomore.width);

			peds.add(sophomore.clone(randX, randY)); 
		}
		for(int j = 0; j < junCount; j++){
			randY = generator.nextFloat()*(screenSizeY - junior.height);
			randX = generator.nextFloat()*(screenSizeX - junior.width);

			peds.add(junior.clone(randX, randY));
		}
		for(int se = 0; se < senCount; se++){
			randY = generator.nextFloat()*(screenSizeY - senior.height);
			randX = generator.nextFloat()*(screenSizeX - senior.width);

			peds.add(senior.clone(randX, randY));
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
			
			ArrayList<Pedestrian> pedsToRemove = new ArrayList<Pedestrian>();
			ArrayList<Acorn> cornsToRemove = new ArrayList<Acorn>();
			
			for(Pedestrian ped : peds){
				//here will also happen the logic for if
				//player doesn't have enough acorns to kill,
				//peds will not die, but squirrel's health will decrease. 
				if(ped.dead){
					pedKilledCount++; 
					pedsToRemove.add(ped);
				}
				else
					ped.doDraw(canvas);  
			}
			
			for (Pedestrian rmP : pedsToRemove){
				peds.remove(rmP);
			}
			pedsToRemove = null;
			
			for(Acorn corn : acorns){
				if(corn.eaten){
					acornCount++;
					cornsToRemove.add(corn);
				}
				else
					corn.doDraw(canvas);
			}
			
			
			for (Acorn rmA : cornsToRemove){
				acorns.remove(rmA);
			}
			cornsToRemove = null;
			
	
			
			Paint paint = new Paint();
			paint.setStyle(Style.FILL);
			paint.setAlpha(255);
			//draw obstacles
			

			
			System.out.println(obstacles.get(0).flattenToString());
			for(Rect obs : obstacles){
				canvas.drawRect(obs, paint);
			}
			
			
			squirrel.doDraw(canvas); 
			
//			healthBar.doDraw(canvas); 
		}
	}
	
	protected void update(double elapsed, float yAccel, float xAccel) {
	
		levelTime += elapsed; 
		timeSinceSpawn += elapsed; 
	
		if(timeSinceSpawn > acornSpawnRate){
			//System.out.println((System.currentTimeMillis()/1000));
			
			
			
			float randY = generator.nextFloat()*(screenSizeY - acorn.height);
			float randX = generator.nextFloat()*(screenSizeX - acorn.width);
			
			
			Acorn clone = acorn.clone(randX, randY);
			acorns.add(clone);
			timeSinceSpawn = 0; 
		}		
		
		float squirrelHeight = squirrel.height;
		float squirrelWidth = squirrel.width;
		
		//top left of the squirrel
		float squirrelLocationX = squirrel.currentX;  
		float squirrelLocationY = squirrel.currentY;
		
		
		float sX = squirrelLocationX + (squirrelWidth/2);
		float sY = squirrelLocationY + (squirrelHeight/2);
		
		
		
		for(Acorn corn : acorns){
			corn.update(sX, sY);	
		
		}
		for(Pedestrian ped : peds){
			ped.update(sX, sY); 
		}		
		squirrel.update(yAccel, xAccel); 
		
//		healthBar.update(elapsed); 

		
	}
}
