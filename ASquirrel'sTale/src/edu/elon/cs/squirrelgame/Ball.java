package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Ball {
	protected float x;
	protected float y;
	private float currentX;
	private float currentY;
	private float width, height;
	private Bitmap ff;
	private Bitmap board;
	private int canvasHeight, canvasWidth;
	private float radiusWidth, radiusHeight;
	private static final int SPEED_VARIABLE = 5;

	private final float FACTOR = 3f;

	public Ball(Context context) {
		ff = BitmapFactory.decodeResource(context.getResources(),
				R.drawable.ball);

		width = ff.getWidth() / FACTOR;
		height = ff.getHeight() / FACTOR;
		radiusWidth = (width / 2);
		// System.out.println(radiusWidth + " radiusWidth");
		radiusHeight = (height / 2);
		x = width / FACTOR;
		y = height / FACTOR;
	}

	public void doDraw(Canvas canvas) {
		canvas.drawBitmap(ff, null, new Rect((int) (x - width / FACTOR),
				(int) (y - height / FACTOR), (int) (x + width / FACTOR),
				(int) (y + height / FACTOR)), null);
		canvasHeight = canvas.getHeight();
		canvasWidth = canvas.getWidth();
	}

	public void update(float rollX, float rollY) {
		currentX += rollX * SPEED_VARIABLE;
		currentY += rollY * SPEED_VARIABLE;

		if (currentX > canvasWidth) {
			currentX = canvasWidth;
		} else if (currentX < 0) {
			currentX = 0;
		}
		x = currentX;
		System.out.println(x + " X");
		if (x > canvasWidth - radiusWidth+28) {
			x = canvasWidth - radiusWidth+28;
		} else if (x < radiusWidth-27) {
			x = radiusWidth-27;
		}
		if (currentY > canvasHeight) {
			currentY = canvasHeight;
		} else if (currentY < 0) {
			currentY = 0;
		}
		y = currentY;
		System.out.println(y + " Y");
		if (y > canvasHeight - radiusHeight+28) {
			y = canvasHeight - radiusHeight+28;
		} else if (y < radiusHeight-27) {
			y = radiusHeight-27;
		}

	}
}
