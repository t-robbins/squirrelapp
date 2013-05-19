package edu.elon.cs.squirrelgame;


import java.util.ArrayList;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.cs.elon.squirrelstale.R;

public class Acorn {
	private Context context; 
	private float centerX, centerY;
	private Bitmap image;

	protected int screenWidth, screenHeight;

	protected float width, height;
	private final float FACTOR = 4f;

	ArrayList<Rect> obs;
	protected boolean eaten = false;
	protected float randX, randY;
	Random generator;
	public Acorn(Context context, ArrayList<Rect> obstacles){
		this.context = context;

		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.acorn);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;

	
		obs = obstacles;
		generator = new Random();
		
		randomLocation();

		

	}

	private void randomLocation(){

		centerX = generator.nextFloat()*(screenHeight - (height*2));
		centerY = generator.nextFloat()*((screenWidth) - (width*2));
		
			
	}

	private void eaten(){
		centerX = screenWidth * -2;
		centerY = screenHeight * -2;  
		eaten = true;
	}
	public void doDraw(Canvas canvas) {
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
		
		if(centerX < 0.0 && centerY < 0.0){
			generator = new Random();
			centerX = generator.nextFloat()*(Math.abs(screenWidth - width));
			centerY = generator.nextFloat()*(Math.abs(screenHeight - height));
		}
		
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
						(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);

		
	}

	public void update(float sX, float sY) {	

		/*
		 * ==================================
		 *      Collision with Squirrel
		 * ==================================
		 */

		if((sX >= centerX-20 && sX <= centerX+20) 
				&& (sY >= centerY-20 && sY <= centerY+20))
			eaten();

		//DONT SPAWN ON BILLDINGS
				for(Rect r : obs){
					//left side of the building
					if(centerX > r.left - width && centerX < r.centerX() && centerY > r.top && centerY < r.bottom){
						centerX = r.left - width;
					}

					//right side of the building
					if(centerX < r.right + width && centerX > r.centerX() && centerY > r.top && centerY < r.bottom){

						centerX = r.right + width;
					}

					//top side of the building
					if(centerY > r.top - height && centerY < r.centerY() && centerX > r.left && centerX < r.right){
						centerY = r.top - height;
					}

					if(centerY < r.bottom + height && centerY > r.centerY() && centerX > r.left && centerX < r.right){
						centerY = r.bottom + height;
					}
				}



	}
}
