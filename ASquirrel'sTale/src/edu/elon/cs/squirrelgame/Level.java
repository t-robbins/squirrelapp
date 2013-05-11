package edu.elon.cs.squirrelgame;


import java.util.ArrayList;

import edu.cs.elon.squirrelstale.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.DisplayMetrics;

public class Level {

	protected String name = "one"; 
	int pedCount;
	double acornSpawnRate;
	int freshCount= 0, sophCount = 0, junCount = 0, senCount = 0;
	String mapSrc;
	ArrayList<Rect> obstacles;
	ArrayList<Pedestrian> peds;
	
	private float screenSizeX, screenSizeY; 
	
	private Bitmap gMapBackground;
	
	private Pedestrian freshman; 
	private Pedestrian sophomore;
	private Pedestrian junior;
	private Pedestrian senior;
	
	private Squirrel squirrel; 
	
	private Context context; 
	
	public Level(double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount, ArrayList<Rect> obstacles, Context context){
		
		this.obstacles = obstacles;
		
		this.context = context; 
		
		this.squirrel = new Squirrel(context);
		
		gMapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.levelone_background);
		
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
			//draw obsacles
			squirrel.doDraw(canvas); 
		}
	}
	
	protected void update(double elapsed, float yAccel, float xAccel) {
		squirrel.update(yAccel, xAccel); 
		for(Pedestrian ped : peds){
			ped.update(elapsed, squirrel.currentX, squirrel.currentY); 
		}
		
	}
}
