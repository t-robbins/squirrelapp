package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

public class Pedestrian {
	protected float x;
	protected float y;
	private float width, height;
	private Bitmap ss;
	private float currentX;
	private float currentY;
	private float currentBallX;
	private float currentBallY;
	private int canvasWidth;
	private int canvasHeight;
	
	private final float FACTOR = 2.7f;
	private final int DIFFICULTY_LEVEL = 10;
	
	public Pedestrian(Context context /*, appearance, acornCost*/){
		ss = BitmapFactory.decodeResource(context.getResources(), R.drawable.checkered);
		width = ss.getWidth()/FACTOR;
		height = ss.getHeight()/FACTOR;
		x = width/FACTOR+60;
		y = height/FACTOR+60;
		
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(ss, null, 
				new Rect((int)(x-width/FACTOR),(int)(y-height/FACTOR),
				(int)(x+width/FACTOR),(int)(y+height/FACTOR)),null);
		
		canvasHeight = canvas.getHeight();
		canvasWidth = canvas.getWidth();
	}
	
	public void update(double elapsed, float ballX, float ballY){
		currentBallX = ballX;
		currentBallY = ballY;
				
		if((currentBallX >= x-DIFFICULTY_LEVEL && currentBallX <= x+DIFFICULTY_LEVEL) 
				&& (currentBallY >= y-DIFFICULTY_LEVEL && currentBallY <= y+DIFFICULTY_LEVEL) ){
			currentX =Math.abs((float) Math.random() * ((canvasWidth - width) - width) + width);
			x = currentX;
			currentY = Math.abs((float) Math.random() * ((canvasHeight - height) - height) + height);
			y = currentY;
		}
	}
}
