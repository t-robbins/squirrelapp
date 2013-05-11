package edu.elon.cs.squirrelgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

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
	boolean called = true;
	private double angleX, angleY;
	
	
	private final float FACTOR = 4f;
	
	//make speed dependent on resolution
	private static final float SPEED_VARIABLE = 5;
	
	public Pedestrian(Context context, int imageID, int acornCost){
		this.imageID = imageID;
		image = BitmapFactory.decodeResource(context.getResources(), imageID);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		centerX = width/2;
		centerY = height/2;
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
	
	
	//computes random angle for pedestrian to 'face'
	private void randomRHatVector(){
		
		Random generator = new Random();
		
		double angle = generator.nextDouble() * 360;
		
		angleX = Math.cos(angle);
		angleY = Math.sin(angle);
	}
	
	public void update(double elapsed, float sqlrX, float sqlrY){
		
		centerX += (float) (SPEED_VARIABLE * angleX);
		centerY += (float) (SPEED_VARIABLE * angleY);
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	   SCREEN BORDER	 |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		
		
		//done
		if (centerX > screenWidth - width/FACTOR) {
			
			
			centerX = screenWidth - width/FACTOR;
			randomRHatVector();
			
		} else if (centerX < 0) {
			centerX = 0 + (width/FACTOR)/8;
			
			randomRHatVector();
		}
		if (centerY > screenHeight - (height/FACTOR)) {

			centerY = screenHeight - (height/FACTOR);

			randomRHatVector();
		} else if (centerY < 0) {
			centerY = 0 + (height/FACTOR)/8;
			randomRHatVector();
		}
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	     OBSTACLES   	 |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		
		
		
		
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	      SQUIRREL       |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		if((currentHeroX >= centerX && currentHeroX <= centerX) 
				&& (currentHeroY >= centerY && currentHeroY <= centerY) ){
					die();	 
		}
	}
}
