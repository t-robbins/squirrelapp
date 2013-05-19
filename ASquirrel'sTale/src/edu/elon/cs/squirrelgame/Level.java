package edu.elon.cs.squirrelgame;


import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import edu.cs.elon.squirrelstale.R;

public class Level {

	protected String name = "one"; 
	private int pedCount; 
	private int hitCount = 0; 
	private double acornSpawnRate;
	private int freshCount= 0, sophCount = 0, junCount = 0, senCount = 0;
	private String mapSrc;
	private ArrayList<Rect> obstacles;
	private ArrayList<Pedestrian> peds;
	private ArrayList<Acorn> acorns;
	private float screenSizeX, screenSizeY; 
	protected boolean levelFinished = false; 
	
	private Bitmap gMapBackground;

	private Squirrel squirrel; 
	private Acorn acorn;
	private HealthBar healthBar; 
	
	private Context context; 
	
	protected double levelTime = 0;
	private int pedKilledCount = 0;
	public int acornCount = 0; 
	private double timeSinceSpawn = 0;
	private double timeSinceHit = 0; 
	protected float randX, randY;
	protected Random generator;
	protected int score = 0; 
	
	private ScoreScreen scoreScreen; 
	private GameOverScreen gameOverScreen; 
	
	private boolean alreadyDisplayed = false; 

	private Paint paint;
	private int textX, textY;
	
	public Level(double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount, ArrayList<Rect> obstacles, Context context, int background){
		
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		screenSizeX = dm.widthPixels;
		screenSizeY = dm.heightPixels; 
		
		this.scoreScreen = new ScoreScreen(context); 
		this.gameOverScreen = new GameOverScreen(context); 
		this.obstacles = obstacles;
		this.context = context; 
		this.acornSpawnRate = acornSpawnRate; 
		this.healthBar = new HealthBar(0, 0, 100, context); 
		
		gMapBackground = BitmapFactory.decodeResource(context.getResources(), background);
		

		generator = new Random();
		

		acorn = new Acorn(context, obstacles);
		squirrel = new Squirrel(context, obstacles);

		this.freshCount = freshCount; 
		this.sophCount = sophCount; 
		this.junCount = junCount;
		this.senCount = senCount; 
		
		pedCount = freshCount + sophCount + junCount + senCount;
		createPedList(); 
	
		
		acorns = new ArrayList<Acorn>();
		
		acorns.add(acorn);
		
		paint = new Paint();
		paint.setAlpha(255);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setStrokeWidth(1);
		paint.setColor(Color.CYAN);
		
		paint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.myFontSize));
		
		textX = (int) (screenSizeX - context.getResources().getDimensionPixelSize(R.dimen.scoreDistFromRight));
		textY = context.getResources().getDimensionPixelSize(R.dimen.scoreDistFromTop); 
	}
	
	public int getCorns(){
		return acornCount;
	}
	
	//populating an array of instantiated pedestrian objects that will exist on the level 
	private void createPedList(){
		
		peds = new ArrayList<Pedestrian>();
	
		for(int f = 0; f < freshCount; f++){
			System.out.println("Creating freshman number :" + f);
			peds.add(new Pedestrian(context, R.drawable.freshman_ped, 1, obstacles));
		}
		
		for(int so = 0; so < sophCount; so++){ 
			peds.add(new Pedestrian(context, R.drawable.sophomore_ped, 1, obstacles));
		}
		for(int j = 0; j < junCount; j++){
			peds.add(new Pedestrian(context, R.drawable.junior_ped, 1, obstacles));
		}
		for(int se = 0; se < senCount; se++){
			peds.add(new Pedestrian(context, R.drawable.senior_ped, 1, obstacles));
		}
	}
	
	public ArrayList<Pedestrian> getPedList(){
		return peds;
	}
	
	public String getMapSrc(){
		return mapSrc;
	}
	
	public void finishLevel(){
		this.levelFinished = true; 
	}
	
	public String calculateScore(double time){
		//score is based on time taken to complete the level
		//if the squirrel gets "hit" (didn't have enough acorns to kill a ped)
		//it counts as 5 secs 
		double score = time + (hitCount*5); 
		String scoreStr = "";
		if(score <= 60){
			scoreStr = "Gold"; 
		}
		if(score > 60 && score <=120){
			scoreStr = "Silver";
		}
		if(score > 120){
			scoreStr = "Bronze";
		}
		return scoreStr; 
	}
	
	protected void doDraw(Canvas canvas){
		if(canvas != null){
			//canvas.drawBitmap(board, 0, 0, null);
			canvas.drawBitmap(gMapBackground, null,
					new Rect(0, 0, (int)screenSizeX, (int)screenSizeY),
							null);
			
			ArrayList<Pedestrian> pedsToRemove = new ArrayList<Pedestrian>();
			ArrayList<Acorn> cornsToRemove = new ArrayList<Acorn>();
			
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
			
			for(Pedestrian ped : peds){

				if(ped.dead){
					System.out.println("draw - ped should be removed");
					pedsToRemove.add(ped); 
				} else {
					ped.doDraw(canvas); 
				}
			}
			
			for (Pedestrian rmP : pedsToRemove){
				peds.remove(rmP);
				System.out.println("size of peds is : " + peds.size());
			}
			pedsToRemove = null;
			
			
			squirrel.doDraw(canvas);
			healthBar.doDraw(canvas); 
			
			canvas.drawText(String.valueOf(score), textX, textY, paint);
			
			
			if(scoreScreen.display){
				scoreScreen.doDraw(canvas);  
			}
			  
			if(gameOverScreen.display)
				gameOverScreen.doDraw(canvas); 
			
	

//			Paint paint = new Paint();
//			paint.setStyle(Style.FILL);
//			paint.setAlpha(255);
//			//draw obstacles
//			
//			System.out.println(obstacles.get(0).flattenToString());
//			for(Rect obs : obstacles){
//				canvas.drawRect(obs, paint);
//			}
			

		}
	}
	
	protected void update(double elapsed, float yAccel, float xAccel) {
		//System.out.println("acorns: "+acornCount);
	
		levelTime += elapsed; 
		timeSinceSpawn += elapsed; 
		timeSinceHit += elapsed; 
	
		if(timeSinceSpawn > acornSpawnRate){
			//System.out.println((System.currentTimeMillis()/1000));
			
			
			acorns.add(new Acorn(context, obstacles));
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
		
//		ArrayList<Pedestrian> pedsToRemove = new ArrayList<Pedestrian>();
		for(Pedestrian ped : peds){
			int hit = ped.update(sX, sY, acornCount); 
			
			if(ped.dead){
				acornCount = acornCount - ped.acornCost;
				pedKilledCount++;
				score+=100;
			}else if(hit > acornCount){
				healthBar.Hit(1);
				hitCount++;
				if(score>0)
					score-=2;
				
			}
		}
		
		squirrel.update(yAccel, xAccel); 
		
		healthBar.update(elapsed); 
		
		if(scoreScreen.display){
			scoreScreen.update(elapsed); 
		}
		
		if(gameOverScreen.display){
			gameOverScreen.update(elapsed); 
		}
		
		//checking to see if the level has been won...
		//our objective is: Peds have been all killed 

		
//		if(peds.size() == 0){
// 
//			score = calculateScore(levelTime); 
//			scoreScreen.setScore(score); 
//			if (!alreadyDisplayed) {
//				scoreScreen.displayScreen();
//				alreadyDisplayed = true; 
//			}
//			if(alreadyDisplayed && !scoreScreen.display){
//				//alreadyDisplayed = false; 
//				finishLevel(); 
//			}
//			 
//		}

		if(peds.size() == 0){
			//could put some sort of dialog or screen that pops up with the score of this level. Then set the boolean
			//when they click okay. 
//			System.out.println("There are no peds left! Finish the level!\n Count is: "+peds.size());
 
//			score = calculateScore(levelTime); 
//			scoreScreen.setScore(String.valueOf(score)); 
//			if (!alreadyDisplayed) {
//				scoreScreen.displayScreen();
//				alreadyDisplayed = true; 
//			}
//			if(alreadyDisplayed && !scoreScreen.display){
//				//alreadyDisplayed = false; 
//				finishLevel(); 
//			}
			 
		}
		
		if(healthBar.empty){
			//also end the level with game over screen 
			//user ends game with back button 
			gameOverScreen.displayScreen(); 
		}

		
	}
	
}
