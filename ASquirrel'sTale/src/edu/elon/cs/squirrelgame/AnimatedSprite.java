/**
 * @author Schuyler Goodwin 
 * 
 * A class that models an sprite that can flip through different image
 * so that the sprite looks animated. 
 */
package edu.elon.cs.squirrelgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class AnimatedSprite {
	
	private Bitmap spriteSheet;
	private Rect sourceRect; 
	private int frameNum; 
	private int currentFrame;
	private long frameTicker; 
	private int framePeriod;
	
	protected int spriteWidth; 
	protected int spriteHeight; 
	
	protected float x;
	protected float y; 
	
	public AnimatedSprite(Bitmap bitmap, float x, float y, int width, int height, int fps, int frameCount){
		this.spriteSheet = bitmap;
		this.x = x;
		this.y = y;
		currentFrame = 0;
		frameNum = frameCount;
		spriteWidth = spriteSheet.getWidth() / frameCount;
		spriteHeight = spriteSheet.getHeight();
		sourceRect = new Rect(0, 0, (int)(spriteWidth), spriteHeight);
		framePeriod = 1000 / fps; 
		frameTicker = 01; 
	}
	
	/*
	 * update method 
	 */
	public void update(long gameTime) {
		if (gameTime > frameTicker + framePeriod){
			frameTicker = gameTime; 
			//increment the frame
			currentFrame++; 
			if (currentFrame >= frameNum){
				//reset the frame 
				currentFrame = 0; 
			}
		}
		this.sourceRect.left = currentFrame * spriteWidth; 
		this.sourceRect.right = this.sourceRect.left + spriteWidth; 
	}
	
	/*
	 * draw method 
	 */
	public void draw(Canvas canvas, float factor){
		//where to draw the sprite 
		Rect destRect = new Rect((int)x,(int)y,(int)(x + spriteWidth / factor),(int)( y + spriteHeight / factor)); 
		canvas.drawBitmap(spriteSheet, sourceRect, destRect, null);
		}
}
