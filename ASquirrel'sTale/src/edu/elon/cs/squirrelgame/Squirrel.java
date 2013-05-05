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
	private Bitmap board;
	private int screenHeight, screenWidth;
	private float radiusWidth, radiusHeight;
	private static final int SPEED_VARIABLE = 5;

	private final float FACTOR = 3f;

	public Squirrel(Context context) {
		spriteSheet = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.elaine);
		animatedSprite = new AnimatedSprite(spriteSheet, x, y, 30, 47, 5, 5);

		width = animatedSprite.spriteWidth / FACTOR;
		height = animatedSprite.spriteHeight / FACTOR;
		
		radiusWidth = (width / 2);
		System.out.println(radiusWidth + " radiusWidth");
		radiusHeight = (height / 2);
		x = width / FACTOR;
		y = height / FACTOR;
	}

	public void doDraw(Canvas canvas) {
//		canvas.drawBitmap(bitmap, null, new Rect((int) (x - width / FACTOR),
//				(int) (y - height / FACTOR), (int) (x + width / FACTOR),
//				(int) (y + height / FACTOR)), null);
		animatedSprite.draw(canvas, (int)x,(int)y); 
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}

	public void update(float rollX, float rollY) {
		animatedSprite.update(System.currentTimeMillis()); 
		
		currentX += rollX * SPEED_VARIABLE;
		currentY += rollY * SPEED_VARIABLE;

		if (currentX > screenWidth) {
			currentX = screenWidth;
		} else if (currentX < 0) {
			currentX = 0;
		}
		x = currentX;
		//System.out.println(x + " X");
		if (x > screenWidth - radiusWidth+28) {
			x = screenWidth - radiusWidth+28;
		} else if (x < radiusWidth-27) {
			x = radiusWidth-27;
		}
		if (currentY > screenHeight) {
			currentY = screenHeight;
		} else if (currentY < 0) {
			currentY = 0;
		}
		y = currentY;
		//System.out.println(y + " Y");
		if (y > screenHeight - radiusHeight+28) {
			y = screenHeight - radiusHeight+28;
		} else if (y < radiusHeight-27) {
			y = radiusHeight-27;
		}
	}
}
