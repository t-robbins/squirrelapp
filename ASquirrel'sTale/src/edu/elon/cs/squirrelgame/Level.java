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
	
	private Pedestrian freshman; 
	private Pedestrian sophomore;
	private Pedestrian junior;
	private Pedestrian senior;
	
	private Squirrel squirrel; 
	private Acorn acorn;
	private HealthBar healthBar; 
	
	private Context context; 
	
	protected double levelTime = 0;
	private int pedKilledCount = 0;
	private int acornCount = 0; 
	private double timeSinceSpawn = 0;
	private double timeSinceHit = 0; 
	protected float randX, randY;
	protected Random generator;
	protected String score = ""; 
	private ScoreScreen scoreScreen; 
	private GameOverScreen gameOverScreen; 
	
	private boolean alreadyDisplayed = false; 
	
	private int min,sec;
	public Level(double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount, ArrayList<Rect> obstacles, Context context, int background){
		
		this.scoreScreen = new ScoreScreen(context); 
		this.gameOverScreen = new GameOverScreen(context); 
		this.obstacles = obstacles;
		this.context = context; 
		this.acornSpawnRate = acornSpawnRate; 
		this.healthBar = new HealthBar(0, 0, 100); 
		
		gMapBackground = BitmapFactory.decodeResource(context.getResources(), background);
		
		squirrel = new Squirrel(context);
		acorn = new Acorn(context);
		
		freshman = new Pedestrian(context, R.drawable.freshman_ped, 1); 
		sophomore = new Pedestrian(context, R.drawable.sophomore_ped, 2);
		junior = new Pedestrian(context, R.drawable.junior_ped, 3);
		senior = new Pedestrian(context, R.drawable.senior_ped, 4);
		
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
	
//	private void registerHit(Pedestrian ped, Canvas canvas){
//		if(ped.dead && acornCount < ped.acornCost){
//			//squirrel gets hit instead of killing ped
//			//health gets decreased
//			healthBar.Hit(1); 
//			//hit count incremented 
//			hitCount++; 
//			ped.dead = false; 
//			ped.doDraw(canvas); 
//		} else if (ped.dead && acornCount >= ped.acornCost){
//			pedKilledCount++;
//			pedsToRemove.add(ped); 
//			acornCount = acornCount - ped.acornCost; 
//		}
//	}
//	
	
	protected void doDraw(Canvas canvas){
		if(canvas != null){
			//canvas.drawBitmap(board, 0, 0, null);
			canvas.drawBitmap(gMapBackground, null,
					new Rect(0, 0, (int)screenSizeX, (int)screenSizeY),
							null);
			
			ArrayList<Pedestrian> pedsToRemove = new ArrayList<Pedestrian>();
			ArrayList<Acorn> cornsToRemove = new ArrayList<Acorn>();
			
			for(Pedestrian ped : peds){
//				//here will also happen the logic for if
//				//player doesn't have enough acorns to kill,
//				//peds will not die, but squirrel's health will decrease. 
//				if(ped.dead){
//					pedKilledCount++; 
//					pedsToRemove.add(ped);
//				}
//				else
//					ped.doDraw(canvas);  
//				if(ped.dead && acornCount < ped.acornCost){
//					//squirrel gets hit instead of killing ped
//					//health gets decreased
//					healthBar.Hit(1); 
//					//hit count incremented 
//					hitCount++; 
//					ped.dead = false; 
//					ped.doDraw(canvas); 
//				} else if (ped.dead && acornCount >= ped.acornCost){
//					pedKilledCount++;
//					pedsToRemove.add(ped); 
//					acornCount = acornCount - ped.acornCost; 
//				}
				if(ped.dead){
					System.out.println("draw - ped should be removed");
					pedsToRemove.add(ped); 
				} else {
					ped.doDraw(canvas); 
				}
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
			
			squirrel.doDraw(canvas);
			
			healthBar.doDraw(canvas); 
			
			if(scoreScreen.display){
				scoreScreen.doDraw(canvas);  
			}
			
			if(gameOverScreen.display){
				gameOverScreen.doDraw(canvas); 
			}
	
			
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
		System.out.println("acorns: "+acornCount);
	
		levelTime += elapsed; 
		timeSinceSpawn += elapsed; 
		timeSinceHit += elapsed; 
	
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
		
//		ArrayList<Pedestrian> pedsToRemove = new ArrayList<Pedestrian>();
		for(Pedestrian ped : peds){
			ped.update(sX, sY); 
			
			if(ped.dead && acornCount < ped.acornCost){
				//squirrel gets hit instead of killing ped
				//health gets decreased
				healthBar.Hit(1); 
				//hit count incremented 
				hitCount++; 
				//ped is not marked as dead, since not enough acorns 
				ped.dead = false;  
			} else if (ped.dead && acornCount >= ped.acornCost){
				pedKilledCount++; 
				acornCount = acornCount - ped.acornCost; 
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
		if(peds.size() == 0){
			//could put some sort of dialog or screen that pops up with the score of this level. Then set the boolean
			//when they click okay. 
//			System.out.println("There are no peds left! Finish the level!\n Count is: "+peds.size());
 
			score = calculateScore(levelTime); 
			scoreScreen.setScore(score); 
			if (!alreadyDisplayed) {
				scoreScreen.displayScreen();
				alreadyDisplayed = true; 
			}
			if(alreadyDisplayed && !scoreScreen.display){
				//alreadyDisplayed = false; 
				finishLevel(); 
			}
			 
		}
		
		if(healthBar.empty){
			//also end the level with game over screen 
			//user ends game with back button 
			gameOverScreen.displayScreen(); 
		}

		
	}
}
