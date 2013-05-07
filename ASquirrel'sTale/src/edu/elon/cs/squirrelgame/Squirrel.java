package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Squirrel {
	private AnimatedSprite animatedSpriteExample; 
	private AnimatedSprite animatedSpriteShown;
	private AnimatedSprite animatedUp;
	private AnimatedSprite animatedDown;
	private AnimatedSprite animatedLeft;
	private AnimatedSprite animatedRight;
		
	protected float x;
	protected float y;
	
	private float currentX;
	private float currentY;
	private float width, height;
	private Bitmap spriteSheetExample;
	//private Bitmap board;
	private int screenHeight, screenWidth;
	private float radiusWidth, radiusHeight;
	private static final int SPEED_VARIABLE = 5;

	private final float FACTOR = 3f;

	public Squirrel(Context context) {
		spriteSheetExample = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.elaine);
		Bitmap spriteSheetUp = BitmapFactory.decodeResource(context.getResources(), R.drawable.up);
		Bitmap spriteSheetDown = BitmapFactory.decodeResource(context.getResources(), R.drawable.down);
		Bitmap spriteSheetLeft = BitmapFactory.decodeResource(context.getResources(), R.drawable.left);
		Bitmap spriteSheetRight = BitmapFactory.decodeResource(context.getResources(), R.drawable.right);
		animatedSpriteExample = new AnimatedSprite(spriteSheetExample, x, y, 30, 47, 5, 5, 1f);
		animatedUp = null; 
		animatedDown = null;
		animatedLeft = null;
		animatedRight = null;
		animatedSpriteShown = animatedSpriteExample; 

		width = animatedSpriteShown.spriteWidth;
		height = animatedSpriteShown.spriteHeight;
		
		radiusWidth = (animatedSpriteShown.spriteWidth / 2);
		System.out.println(radiusWidth + " radiusWidth");
		radiusHeight = (animatedSpriteShown.spriteHeight / 2);
		
		x = animatedSpriteShown.spriteWidth;
		y = animatedSpriteShown.spriteHeight;
		
	}
	
	//Method to switch the sprite sheet looking at based on the direction the squirrel is supposed to be going in 
	private void switchDirection(){
		if(currentY < 0){
			/*direction is up*/
			animatedSpriteShown = animatedUp; 
		}
		else if(currentY > 0){
			/*direction is down*/
			animatedSpriteShown = animatedDown; 
		}
		else if(currentX > 0){
			/*direction is left*/
			animatedSpriteShown = animatedLeft; 
		}
		else if(currentX < 0){
			/*direction is right*/
			animatedSpriteShown = animatedRight; 
		}
	}

	public void doDraw(Canvas canvas) {
		animatedSpriteShown.draw(canvas); 
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}

	public void update(float rollX, float rollY) {
		
		switchDirection(); 
		
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
		animatedSpriteShown.x = currentX;
		animatedSpriteShown.y = currentY; 
		animatedSpriteShown.update(System.currentTimeMillis()); 
	}
}
