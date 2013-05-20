/**
 * @author Schuyler Goodwin
 * 
 * A class that displays a game over message to the screen
 */
package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameOverScreen {
	
	private Bitmap gameOver; 
	private double time = 0; 
	protected boolean display = false; 
	
	public GameOverScreen(Context context){
		this.gameOver = BitmapFactory.decodeResource(context.getResources(), R.drawable.gameover); 
	}
	
	public void displayScreen(){
		display = true; 
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(gameOver, null, new Rect(0,0,canvas.getWidth(),canvas.getHeight()), null);  
	}
	
	public void update(double elapsed){
		time += elapsed; 
	}

}
