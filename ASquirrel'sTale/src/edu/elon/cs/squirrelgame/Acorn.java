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
	
	
	protected boolean eaten = false;
	
	Random generator;
	public Acorn(Context context){
		this.context = context;
		
		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.acorn);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		
		centerX = 150;
		centerY = 150;
		
		
	}
	public Acorn clone(float xPos, float yPos){
		 Acorn cloned = new Acorn(context); 
		 cloned.centerX = xPos;
		 cloned.centerY = yPos; 
		 return cloned; 
	}
	public Acorn clone(){
		 Acorn cloned = new Acorn(context); 
		 return cloned; 
		
	}
	private void eaten(){
		centerX = screenWidth * -2;
		centerY = screenHeight * -2;  
		eaten = true;
	}
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
		
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}

	public void update(float sqrlX, float sqrlY) {
		



		
		/*
		 * ==================================
		 *      Collision with Squirrel
		 * ==================================
		 */
		
		//System.out.println("S: (" + (int)sqrlX + ","+(int)sqrlY+") and A: (" + (int)centerX + ","+ (int)centerY + ")");
		
		
		
	}
}
