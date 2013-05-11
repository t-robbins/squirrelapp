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
	private float currentX, currentY;
	private float centerX, centerY;
	private Bitmap image;
	
	protected int screenWidth, screenHeight;
	
	protected float width, height;
	private final float FACTOR = 4f;
	
	private float randX, randY;
	
	private long timeNow, timeBefore;
	
	private ArrayList<Acorn> acornsOnScreen;
	
	Random generator;
	public Acorn(Context context){
		this.context = context;
		
		image = BitmapFactory.decodeResource(context.getResources(), R.drawable.acorn);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		
		centerX = width/2;
		centerY = height/2;
		
		
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
	
	
	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
		
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}

	public void update(float sqlrX, float sqrlY) {
		currentX = 100;
		currentY = 100;
	}
}
