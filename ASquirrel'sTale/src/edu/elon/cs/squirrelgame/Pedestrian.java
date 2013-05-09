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
	private final int DIFFICULTY_LEVEL = 10;
	private static final float SPEED_VARIABLE = 1;
	
	public Pedestrian(Context context, int imageID, int acornCost){
		this.imageID = imageID;
		image = BitmapFactory.decodeResource(context.getResources(), imageID);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		centerX = width/FACTOR;
		centerY = height/FACTOR;
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
		currentHeroX = sqlrX;
		currentHeroY = sqlrY;
		
		centerX += (float) (SPEED_VARIABLE * angleX);
		centerY += (float) (SPEED_VARIABLE * angleY);
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	   SCREEN BORDER	 |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		if (centerX > screenWidth - width) {
			System.out.println(centerX);
			
			centerX = screenWidth - width;
			randomRHatVector();
			System.out.println("Adjusted for right side?");
			System.out.println("(" + centerX + "," + centerY + ")");
		} else if (centerX < 0) {
			System.out.println(centerX);
			
			centerX = 0;
			randomRHatVector();

			System.out.println("Adjusted for left side?");
			System.out.println("(" + centerX + "," + centerY + ")");
		}
		if (centerY > screenHeight - height) {
			
			System.out.println(centerY);
			
			centerY = screenHeight - height;

			System.out.println("Adjusted for top side?");
			System.out.println("(" + centerX + "," + centerY + ")");
			randomRHatVector();
		} else if (centerY < 0) {
			System.out.println(centerY);
			
			centerY = 0;

			System.out.println("Adjusted for bottom side?");
			System.out.println("(" + centerX + "," + centerY + ")");
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
		if((currentHeroX >= centerX-DIFFICULTY_LEVEL && currentHeroX <= centerX+DIFFICULTY_LEVEL) 
				&& (currentHeroY >= centerY-DIFFICULTY_LEVEL && currentHeroY <= centerY+DIFFICULTY_LEVEL) ){
					die();	 
		}
	}
}
