package edu.elon.cs.squirrelgame;


import edu.cs.elon.squirrelstale.R;
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
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BoardView extends SurfaceView implements SurfaceHolder.Callback {
	private SensorManager sensorManager;
	private SurfaceHolder surfaceHolder;
	private BoardViewThread boardViewThread;
	protected float xAccel, yAccel;
	private Squirrel squirrel;
	
	public BoardView(Context context, AttributeSet attrs) {
		super(context, attrs);
		surfaceHolder = getHolder();
		surfaceHolder.addCallback(this);
		boardViewThread = new BoardViewThread(context);
		sensorManager = (SensorManager) context.getSystemService(context.SENSOR_SERVICE);
		sensorManager.registerListener(accelListener, 
				sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), 
				SensorManager.SENSOR_DELAY_GAME);	
		
		
		//create level class object
		
		
		
		
		/*all this junk within a levelUpdate method
			int level = level.getLevel();
			String[] levelProperties = level.getProperties(); 
		*/
		
	}
	
	private SensorEventListener accelListener = new SensorEventListener() {

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			xAccel= event.values[0];
			//System.out.println(xAccel);
			yAccel =  event.values[1];
		}
	};
	

	/* SurfaceHolder.Callback */
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
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
		private Squirrel sqrl;
		private Pedestrian ped;
		private Bitmap gMapBackground;
		private long lastTime;
		private float screenSizeX, screenSizeY; 

		public BoardViewThread(Context context){
			isRunning = false;
			sqrl = new Squirrel(context);
			ped = new Pedestrian(context, R.drawable.freshman_ped, 10);
			gMapBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.levelone_background);
			DisplayMetrics dm = context.getResources().getDisplayMetrics(); 
			screenSizeX = dm.widthPixels;
			screenSizeY = dm.heightPixels; 
		}
		
		

		public void setIsRunning(boolean isRunning) {
			this.isRunning = isRunning;
		}

		private void doDraw(Canvas canvas){
			if(canvas != null){
				//canvas.drawBitmap(board, 0, 0, null);
				canvas.drawBitmap(gMapBackground, null,
						new Rect(0, 0, (int)screenSizeX, (int)screenSizeY),
								null); 
				
				ped.doDraw(canvas);
				sqrl.doDraw(canvas); 
				
				//for each loop to draw 
	
			}
		}
		
		private void update(double elapsed) {
			sqrl.update(yAccel, xAccel);
			ped.update(elapsed, sqrl.x, sqrl.y); 
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

