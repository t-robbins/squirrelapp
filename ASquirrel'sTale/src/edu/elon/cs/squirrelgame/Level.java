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
	private boolean alreadySet = false; 

	private Paint paint, menuBar;
	private int acornTextX, acornTextY, scoreTextX, scoreTextY;
	
	private int hp;
	
	public Level(double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount, ArrayList<Rect> obstacles, Context context, int background, int healthLevel){
		
		DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
		screenSizeX = dm.widthPixels;
		screenSizeY = dm.heightPixels; 
		
		this.scoreScreen = new ScoreScreen(context); 
		this.gameOverScreen = new GameOverScreen(context); 
		this.obstacles = obstacles;
		this.context = context; 
		this.acornSpawnRate = acornSpawnRate;
		
		hp =healthLevel;
		
		this.healthBar = new HealthBar(0, 
				(float) ((screenSizeY / 1.085)), 
				100,
				healthLevel,
				context); 
		
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
		
		scoreTextX = (int) (screenSizeX - context.getResources().getDimensionPixelSize(R.dimen.scoreDistFromRight));
		scoreTextY = (int) ((screenSizeY / 1.085) + (context.getResources().getDimensionPixelSize(R.dimen.myFontSize)/1.072026801)); 
		acornTextX = (int) (screenSizeX - context.getResources().getDimensionPixelSize(R.dimen.acornDistFromRight));
		acornTextY = (int) ((screenSizeY / 1.085) + (context.getResources().getDimensionPixelSize(R.dimen.myFontSize)/1.072026801));

		menuBar = new Paint();
		menuBar.setColor(Color.DKGRAY);
		menuBar.setStyle(Style.FILL);
		menuBar.setAlpha(255);
		
	}
	
	public int getCorns(){
		return acornCount;
	}
	
	
	public int getHealth(){
		return hp;
	}
	
	//populating an array of instantiated pedestrian objects that will exist on the level 
	private void createPedList(){
		
		peds = new ArrayList<Pedestrian>();
	
		for(int f = 0; f < freshCount; f++){
			peds.add(new Pedestrian(context, R.drawable.freshman_ped, 1, obstacles));
		}
		
		for(int so = 0; so < sophCount; so++){ 
			peds.add(new Pedestrian(context, R.drawable.sophomore_ped, 2, obstacles));
		}
		for(int j = 0; j < junCount; j++){
			peds.add(new Pedestrian(context, R.drawable.junior_ped, 3, obstacles));
		}
		for(int se = 0; se < senCount; se++){
			peds.add(new Pedestrian(context, R.drawable.senior_ped, 4, obstacles));
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
	
	protected void doDraw(Canvas canvas){
		if(canvas != null){
			//canvas.drawBitmap(board, 0, 0, null);
			canvas.drawBitmap(gMapBackground, null,
					new Rect(0, 0, (int)screenSizeX, (int)screenSizeY),
							null);
			
			ArrayList<Pedestrian> pedsToRemove = new ArrayList<Pedestrian>();
			ArrayList<Acorn> cornsToRemove = new ArrayList<Acorn>();
			
			
			if(gameOverScreen.display)
				gameOverScreen.doDraw(canvas); 
			else if (scoreScreen.display){
				scoreScreen.doDraw(canvas);  
			}
			else{

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
			
			
			canvas.drawRect(obstacles.get(0), menuBar);
			canvas.drawText("Score: " + String.valueOf(score), scoreTextX, scoreTextY, paint);
			canvas.drawText("Acorns: " + String.valueOf(acornCount), acornTextX, acornTextY, paint);
			
			
			
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
			
//			Paint rect = new Paint(); 
//			paint.setColor(Color.GREEN);
//			paint.setAlpha(255); 
//			
//			
//			for(Rect r : obstacles){
//				canvas.drawRect(r, paint); 
//			}
//			
			squirrel.doDraw(canvas);
			healthBar.doDraw(canvas); 
			}
			
		}
	}
	
	protected void update(double elapsed, float yAccel, float xAccel) {
		//System.out.println("acorns: "+acornCount);
	
		levelTime += elapsed; 
		timeSinceSpawn += elapsed; 
		timeSinceHit += elapsed; 
	
		if(timeSinceSpawn > acornSpawnRate && acorns.size()<6){
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
				score+=100*ped.acornCost;
			}else if(hit > acornCount){
				healthBar.Hit(1);
				hitCount++;
				
				if(score>0)
					score-=2*ped.acornCost;
				if(score<0)
					score=0;
				
			}
		}
		
		squirrel.update(yAccel, xAccel); 
		
		healthBar.update(elapsed); 
		hp = (int) healthBar.getHealth(); 
		if(scoreScreen.display){
			scoreScreen.update(elapsed); 
		}
		
		if(gameOverScreen.display){
			gameOverScreen.update(elapsed); 
		}
		
		//checking to see if the level has been won...
		//our objective is: Peds have been all killed 

		if(peds.size() == 0){
			//displaying the scoring screen 
			if(!alreadySet){
				scoreScreen.setProperties(score, (int) levelTime); 
				alreadySet = true; 
			}

			if (!alreadyDisplayed) {
				scoreScreen.displayScreen();
				alreadyDisplayed = true; 
			}
			if(alreadyDisplayed && !scoreScreen.display){
				//finish the level when scoring screen is done
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
