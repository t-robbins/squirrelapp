package edu.elon.cs.squirrelgame;

import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pedestrian {
	protected float centerX;
	protected float centerY;
	protected float width, height;
	private Bitmap image;
	protected int screenWidth;
	protected int screenHeight;
	private Context context; 

	protected int acornCost; 
	private int imageID; 

	boolean called = true;
	private double angleX, angleY;
	protected boolean dead = false;
	private final float FACTOR = 4.5f;
	private Random generator;
	
	//make speed dependent on resolution
	private static final float SPEED_VARIABLE = 5;
	private ArrayList<Rect> obs;
	
	
	public Pedestrian(Context context, int imageID, int acornCost, ArrayList<Rect> obstacles){
		
		image = BitmapFactory.decodeResource(context.getResources(), imageID);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		
		generator = new Random(System.currentTimeMillis());
		centerX = generator.nextFloat()*(Math.abs(screenWidth - width))+ 50;
		centerY = generator.nextFloat()*(Math.abs(screenHeight - height))+ 50;
		System.out.println("START X:" + centerX + " and Y: " + centerY);
		
		obs = obstacles;
		this.context = context; 
		this.acornCost = acornCost; 
		
		angleX = Math.cos((generator.nextInt()*360)*(Math.PI/180.0));
		angleY = Math.sin((generator.nextInt()*360)*(Math.PI/180.0));
		
		centerX = 1000;
		centerY = 1000;
		System.out.println("FORCED X:" + centerX + " and Y: " + centerY);
		
	} 
	
	public void die(){
		centerX = screenWidth * -2;
		centerY = screenHeight * -2;  
		dead = true;
	}
	
	public void doDraw(Canvas canvas){
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		
		//initiates starting position
		if(centerX < 0.0 && centerY < 0.0){
			generator = new Random(System.currentTimeMillis());
			centerX = generator.nextFloat()*(Math.abs(screenWidth - width) + (screenWidth/2));
			centerY = generator.nextFloat()*(Math.abs(screenHeight - height)+ (screenHeight/2));
			
			
		}
		

		//System.out.println("AFTER DO DRAW X:" + centerX + " and Y: " + centerY);
		
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
	}
	
	private void billiardsBounce(boolean x, boolean y){
		if (x && !y) angleX = -angleX;
		if (!x && y) angleY = -angleY;
	}

	public int update(float sX, float sY, int acornCount){
		
		centerX += (float) (SPEED_VARIABLE * angleX);
		centerY += (float) (SPEED_VARIABLE * angleY);
		
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	   SCREEN BORDER	 |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		//done
		if (centerX > screenWidth - width/FACTOR) {
			centerX = screenWidth - width/FACTOR;
			billiardsBounce(true, false);
			
		} else if (centerX < 0+(width/FACTOR)) {
			centerX = 0 + (width/FACTOR);
			billiardsBounce(true, false);
		}
		if (centerY > screenHeight - (height/FACTOR)) {

			centerY = screenHeight - (height/FACTOR);
			billiardsBounce(false, true);
			
		} else if (centerY < 0+(height/FACTOR)) {
			centerY = 0 + (height/FACTOR);
			billiardsBounce(false, true);
		}
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	     OBSTACLES   	 |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		
		for(Rect r : obs){
			//left side of the building
			if(centerX > r.left - (width/FACTOR) && centerX < r.centerX() && centerY > r.top && centerY < r.bottom){
				centerX = r.left - (width/FACTOR);
				billiardsBounce(true, false);
				}
			
			//right side of the building
			if(centerX < r.right + (width/FACTOR) && centerX > r.centerX() && centerY > r.top && centerY < r.bottom){
				//System.out.println("centerX: " + centerX  + "r.right: " + r.right);
				centerX = r.right + (width/FACTOR);
				billiardsBounce(true, false);
				}
			
			//top side of the building
			if(centerY > r.top-(height/FACTOR) && centerY < r.centerY() && centerX > r.left && centerX < r.right){
				centerY = r.top - (height/FACTOR);
				billiardsBounce(false, true);
				}
			//bottom of building
			if(centerY < r.bottom + (height/FACTOR) && centerY > r.centerY() && centerX > r.left && centerX < r.right){
				centerY = r.bottom + (height/FACTOR);
				billiardsBounce(false, true);
				}
		}
		
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	      SQUIRREL       |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		if((sX >= centerX-40 && sX <= centerX+40) 
				&& (sY >= centerY-40 && sY <= centerY+40)){
			
			if(acornCount >= acornCost){
				die();
			}
			else{
				acornCount++;
			}
		
		}
			
		
		return acornCount;
	
	}
}
