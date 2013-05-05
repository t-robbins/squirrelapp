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
	private Bitmap image;
	private float currentX;
	private float currentY;
	private float currentHeroX;
	private float currentHeroY;
	private int screenWidth;
	private int screenHeight;
	private Context context; 
	private int acornCost; 
	private int imageID; 
	
	private final float FACTOR = 4f;
	private final int DIFFICULTY_LEVEL = 10;
	
	public Pedestrian(Context context, int imageID, int acornCost){
		this.imageID = imageID;
		image = BitmapFactory.decodeResource(context.getResources(), imageID);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		centerX = width/FACTOR+60;
		centerY = height/FACTOR+60;
		this.context = context; 
		this.acornCost = acornCost; 
	}
	
	public Pedestrian clone(float xPos, float yPos){
		 Pedestrian cloned = new Pedestrian(context, imageID, acornCost); 
		 cloned.currentX = xPos;
		 cloned.currentY = yPos; 
		 return cloned; 
	}
	
	public Pedestrian clone(){
		 Pedestrian cloned = new Pedestrian(context, imageID, acornCost); 
		 return cloned; 
		
	}
	
	public void die(){
		currentX = screenWidth * -2;
		currentY = screenHeight * -2;  
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
		
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}
	
	public void update(double elapsed, float ballX, float ballY){
		currentHeroX = ballX;
		currentHeroY = ballY;
				
		//decides if hero is 'on top' of them 
		if((currentHeroX >= centerX-DIFFICULTY_LEVEL && currentHeroX <= centerX+DIFFICULTY_LEVEL) 
				&& (currentHeroY >= centerY-DIFFICULTY_LEVEL && currentHeroY <= centerY+DIFFICULTY_LEVEL) ){
			currentX =Math.abs((float) Math.random() * ((screenWidth - width) - width) + width);
			centerX = currentX;
			currentY = Math.abs((float) Math.random() * ((screenHeight - height) - height) + height);
			centerY = currentY;
		}
	}
}
