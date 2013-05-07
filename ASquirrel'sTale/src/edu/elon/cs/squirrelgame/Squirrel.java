package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Squirrel {
	private AnimatedSprite animatedSprite; 
		
	protected float x;
	protected float y;
	
	private float currentX;
	private float currentY;
	private float width, height;
	private Bitmap spriteSheet;
	//private Bitmap board;
	private int screenHeight, screenWidth;
	private float radiusWidth, radiusHeight;
	private static final int SPEED_VARIABLE = 5;

	private final float FACTOR = 3f;

	public Squirrel(Context context) {
		spriteSheet = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.elaine);
		animatedSprite = new AnimatedSprite(spriteSheet, x, y, 30, 47, 5, 5, 1f);

		width = animatedSprite.spriteWidth;
		height = animatedSprite.spriteHeight;
		
		radiusWidth = (animatedSprite.spriteWidth / 2);
		System.out.println(radiusWidth + " radiusWidth");
		radiusHeight = (animatedSprite.spriteHeight / 2);
		
		x = animatedSprite.spriteWidth;
		y = animatedSprite.spriteHeight;
		
	}

	public void doDraw(Canvas canvas) {
		animatedSprite.draw(canvas); 
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}

	public void update(float rollX, float rollY) {
		
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
		animatedSprite.x = currentX;
		animatedSprite.y = currentY; 
		animatedSprite.update(System.currentTimeMillis()); 
	}
}
