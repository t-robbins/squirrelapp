/**
 * @author Schuyler Goodwin
 * @author Thomas Robbins
 * @author Matthew McKenzie; 
 * @author Rex Renolds
 * 
 * Modified Code given by Joel Hollingsworth
 * 
 * A class that is the basis for all the levels in the game.
 * A game loop (thread) is used to update and draw the level. 
 * The levels are accessed through LevelLibrary 
 * which holds all the levels that exist in the game 
 */
package edu.elon.cs.squirrelgame;


import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import edu.cs.elon.squirrelstale.R;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {
	private SensorManager sensorManager;
	private SurfaceHolder surfaceHolder;
	private BoardViewThread boardViewThread;
	private Context context; 
	protected float xAccel, yAccel;
	private Squirrel squirrel;
	
	
	private int seconds;
	private TimerTask task;
	private Timer myTimer;
	
	LevelLibrary levelIterator; 
	
	private Handler handler;
	
	private boolean objectivesMet = false;
	//private int levelCount = 0;
	
	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context; 
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		
		levelIterator = new LevelLibrary(context);

		
		//---------------------THREAD CREATED HERE-----------------------------------
		boardViewThread = new BoardViewThread(context);
		
		
		sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
		sensorManager.registerListener(accelListener, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_GAME);	
		
	}
	
	private SensorEventListener accelListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			xAccel= event.values[0];
			yAccel =  event.values[1];
		}
	};
	

	/* SurfaceHolder.Callback */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if(boardViewThread.getState() == Thread.State.TERMINATED) {
			boardViewThread = new BoardViewThread(context); 
		}
		//start the thread 
		boardViewThread.setIsRunning(true);
		boardViewThread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		boardViewThread.setIsRunning(false);
		while(retry){
			try{
				boardViewThread.join();
				retry = false;
			}
			catch (InterruptedException ex){}
		}
	}
	
	private class BoardViewThread extends Thread{
		private boolean isRunning;
		private long lastTime;
		private ArrayList<Level> levels; 
		private WinScreen winScreen; 
		private int currentLevel = 0;  

		

		public BoardViewThread(Context context){
			this.winScreen = new WinScreen(context); 
			isRunning = false;
			levels = levelIterator.getLevelList(); 
			
			System.out.println("current level" + levels.get(currentLevel).name); 
		}
		
		
		public void setIsRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}

		private void doDraw(Canvas canvas){ 
			if(canvas != null){
				//gets the current level from levelLibrary 
				levels.get(currentLevel).doDraw(canvas);
				
				if(winScreen.display){
					winScreen.doDraw(canvas); 
				}
			}
		}
		
		private void update(double elapsed) {
			
			if(winScreen.display){
				winScreen.update(elapsed);  
			}

			if (!levels.get(currentLevel).levelFinished) {
				//if the level isn't done, update
				levels.get(currentLevel).update(elapsed, yAccel, xAccel);
				
			} else {
				if (currentLevel < levelIterator.getNumLevels()-1) {
					// the level has ended
					// increment what level looking at					
					currentLevel++;					
				} else {
					//Game is finished
					winScreen.displayScreen(); 
				}
			}
		}
		
		
		@Override
		public void run(){
			lastTime = System.currentTimeMillis() + 100;
			while(isRunning){
				Canvas canvas = null;
				try{
					canvas = surfaceHolder.lockCanvas();
					if(canvas == null){
						isRunning = false;
						continue;
					}
					synchronized (surfaceHolder) { 
						long now = System.currentTimeMillis();
						double elapsed = (now - lastTime)/1000.0;
						lastTime = now;
						update(elapsed);
						doDraw(canvas);
						
						
						}
					} finally {
						if(canvas != null){
							surfaceHolder.unlockCanvasAndPost(canvas);
						}
					}
				}
			}
		}
	}

