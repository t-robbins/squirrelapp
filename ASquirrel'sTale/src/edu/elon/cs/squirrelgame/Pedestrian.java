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
	private int acornCost; 
	boolean called = true;
	private double angleX, angleY;
	protected boolean dead = false;
	private final float FACTOR = 6f;
	private Random generator;
	
	//make speed dependent on resolution
	private static final float SPEED_VARIABLE = 5;
	private ArrayList<Rect> obs;
	
	public Pedestrian(Context context, int imageID, int acornCost, ArrayList<Rect> obstacles){
		
		image = BitmapFactory.decodeResource(context.getResources(), imageID);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		
		generator = new Random();
		centerX = generator.nextFloat()*(Math.abs(screenWidth - width))+ 50;
		centerY = generator.nextFloat()*(Math.abs(screenHeight - height))+ 50;
		System.out.println("START X:" + centerX + " and Y: " + centerY);
		
		obs = obstacles;
		this.context = context; 
		this.acornCost = acornCost; 
		
		angleX = Math.cos((generator.nextInt()*360)*(Math.PI/180.0));
		angleY = Math.sin((generator.nextInt()*360)*(Math.PI/180.0));
		
		
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
			generator = new Random();
			centerX = generator.nextFloat()*(Math.abs(screenWidth - width));
			centerY = generator.nextFloat()*(Math.abs(screenHeight - height));
		}
		
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
	}
	
	private void billiardsBounce(boolean x, boolean y){
		if (x && !y) angleX = -angleX;
		if (!x && y) angleY = -angleY;
	}

	public void update(float sX, float sY){
		
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
			
		} else if (centerX < 0) {
			centerX = 0 + (width/FACTOR)/8;
			billiardsBounce(true, false);
		}
		if (centerY > screenHeight - (height/FACTOR)) {

			centerY = screenHeight - (height/FACTOR);
			billiardsBounce(false, true);
		} else if (centerY < 0) {
			centerY = 0 + (height/FACTOR)/8;
			billiardsBounce(false, true);
		}
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	     OBSTACLES   	 |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		
		for(Rect r : obs){
			//left side of the building
			if(centerX > r.left - width && centerX < r.centerX() && centerY > r.top && centerY < r.bottom){
				centerX = r.left - width;
				billiardsBounce(true, false);
				}
			
			//right side of the building
			if(centerX < r.right + width && centerX > r.centerX() && centerY > r.top && centerY < r.bottom){
				//System.out.println("centerX: " + centerX  + "r.right: " + r.right);
				centerX = r.right + width;
				billiardsBounce(true, false);
				}
			
			//top side of the building
			if(centerY > r.top - height && centerY < r.centerY() && centerX > r.left && centerX < r.right){
				centerY = r.top - height;
				billiardsBounce(false, true);
				}
			
			if(centerY < r.bottom + height && centerY > r.centerY() && centerX > r.left && centerX < r.right){
				centerY = r.bottom + height;
				billiardsBounce(false, true);
				}
		}
		
		
		
		
		/*~~~~~~~~~~~~~~~~~~~~~~~~
		 *|	      SQUIRREL       |
		 *~~~~~~~~~~~~~~~~~~~~~~~~
		 */
		
		if((sX >= centerX-20 && sX <= centerX+20) 
				&& (sY >= centerY-20 && sY <= centerY+20))
			die();
			
	
	}
}
