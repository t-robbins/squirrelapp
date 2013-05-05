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
	protected float centerX;
	protected float centerY;
	private float width, height;
	private Bitmap ped;
	private float currentX;
	private float currentY;
	private float currentBallX;
	private float currentBallY;
	private int screenWidth;
	private int screenHeight;
	private Context context; 
	
	private final float FACTOR = 2.7f;
	private final int DIFFICULTY_LEVEL = 10;
	
	public Pedestrian(Context context /*, appearance, acornCost*/){
		ped = BitmapFactory.decodeResource(context.getResources(), R.drawable.checkered);
		width = ped.getWidth()/FACTOR;
		height = ped.getHeight()/FACTOR;
		centerX = width/FACTOR+60;
		centerY = height/FACTOR+60;
		this.context = context; 
		
	}
	
	public Pedestrian clone(float xPos, float yPos){
		 Pedestrian cloned = new Pedestrian(context); 
		 cloned.currentX = xPos;
		 cloned.currentY = yPos; 
		 return cloned; 
		
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(ped, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
		
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}
	
	public void update(double elapsed, float ballX, float ballY){
		currentBallX = ballX;
		currentBallY = ballY;
				
		if((currentBallX >= centerX-DIFFICULTY_LEVEL && currentBallX <= centerX+DIFFICULTY_LEVEL) 
				&& (currentBallY >= centerY-DIFFICULTY_LEVEL && currentBallY <= centerY+DIFFICULTY_LEVEL) ){
			currentX =Math.abs((float) Math.random() * ((screenWidth - width) - width) + width);
			centerX = currentX;
			currentY = Math.abs((float) Math.random() * ((screenHeight - height) - height) + height);
			centerY = currentY;
		}
	}
}
