package edu.elon.cs.squirrelgame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import edu.cs.elon.squirrelstale.R;

public class Squirrel {
	private AnimatedSprite animatedSpriteExample; 
	private AnimatedSprite animatedSpriteShown;
	private AnimatedSprite animatedUp;
	private AnimatedSprite animatedDown;
	private AnimatedSprite animatedLeft;
	private AnimatedSprite animatedRight;
		
	protected float spriteWidth;
	protected float spriteHeight;
	
	protected float currentX;
	protected float currentY;
	protected float width, height;
	private Bitmap spriteSheetExample;
	//private Bitmap board;
	private int screenHeight, screenWidth;
	private float radiusWidth, radiusHeight;
	private static final int SPEED_VARIABLE = 2;

	private final float FACTOR = .7f;
	private ArrayList<Rect> obs;
	
	public Squirrel(Context context, ArrayList<Rect> obstacles) {
		spriteSheetExample = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.elaine);
		Bitmap spriteSheetUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.up);
		Bitmap spriteSheetDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.down);
		Bitmap spriteSheetLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.left);
		Bitmap spriteSheetRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.right);
		animatedSpriteExample = new AnimatedSprite(spriteSheetExample, spriteWidth, spriteHeight, 30, 47, 5, 5);
		animatedUp = new AnimatedSprite(spriteSheetUp, spriteWidth, spriteHeight, 30, 16, 5, 5);
		animatedDown = new AnimatedSprite(spriteSheetDown, spriteWidth, spriteHeight, 30, 18, 5, 5);
		animatedLeft = new AnimatedSprite(spriteSheetLeft, spriteWidth, spriteHeight, 30, 14, 5, 5);
		animatedRight = new AnimatedSprite(spriteSheetRight, spriteWidth, spriteHeight, 30, 14, 5, 5);
		animatedSpriteShown = animatedDown; 

		width = animatedSpriteShown.spriteWidth / FACTOR;
		height = animatedSpriteShown.spriteHeight / FACTOR;
		
		radiusWidth = (animatedSpriteShown.spriteWidth / 2);
		//System.out.println(radiusWidth + " radiusWidth");
		radiusHeight = (animatedSpriteShown.spriteHeight / 2);
		
		spriteWidth = animatedSpriteShown.spriteWidth;
		spriteHeight = animatedSpriteShown.spriteHeight;
		obs = obstacles;
		
		currentX = 50;
		currentY = 50;
	}
	
	//Method to switch the sprite sheet looking at based on the direction the squirrel is supposed to be going in 
	private void switchDirection(float accelX, float accelY){
		if(accelY < 0 && accelY < accelX){
			/*direction is up*/
			animatedSpriteShown = animatedUp; 
		}
		if(accelY > 0 && accelY > accelX){
			/*direction is down*/
			animatedSpriteShown = animatedDown; 
		}
		if(accelX > 0 && accelX > accelY){
			/*direction is left*/
			animatedSpriteShown = animatedRight; 
		}
		if(accelX < 0 && accelX < accelY){
			/*direction is right*/
			animatedSpriteShown = animatedLeft; 
		}
	}

	public void doDraw(Canvas canvas) {
		animatedSpriteShown.draw(canvas, FACTOR); 
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}
	
	public void update(float rollX, float rollY) {
		
		switchDirection(rollX, rollY); 
		
		currentX += rollX * SPEED_VARIABLE;
		currentY += rollY * SPEED_VARIABLE;

		if (currentX > screenWidth - width) {
			currentX = screenWidth - width;
		} else if (currentX < 0) {
			currentX = 0;
		}
		if (currentY > screenHeight - height) {
			currentY = screenHeight - height;
		} else if (currentY < 0) {
			currentY = 0;
		}
		
		for(Rect r : obs){
			//left side of the building
			if(currentX > r.left - width && currentX < r.centerX() && currentY > r.top && currentY < r.bottom)
				currentX = r.left - width;
			
			//right side of the building
			if(currentX < r.right + width && currentX > r.centerX() && currentY > r.top && currentY < r.bottom){
				//System.out.println("currentX: " + currentX  + "r.right: " + r.right);
				currentX = r.right + width;
			}
			
			//top side of the building
			if(currentY > r.top - height && currentY < r.centerY() && currentX > r.left && currentX < r.right)
				currentY = r.top - height;
			
			if(currentY < r.bottom + height && currentY > r.centerY() && currentX > r.left && currentX < r.right)
				currentY = r.bottom + height;
		}
		
		
		
		animatedSpriteShown.x = currentX;
		animatedSpriteShown.y = currentY; 
		animatedSpriteShown.update(System.currentTimeMillis()); 
		
	}
}
