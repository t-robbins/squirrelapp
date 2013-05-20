/**
 * @author Schuyler Goodwin
 * 
 * A class that displays a win message to the screen
 */
package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class WinScreen {
	
	private Bitmap winner; 
	private double time = 0; 
	protected boolean display = false; 
	
	public WinScreen(Context context){
		this.winner = BitmapFactory.decodeResource(context.getResources(), R.drawable.win); 
	}
	
	public void displayScreen(){
		display = true; 
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(winner, null, new Rect(0,0,canvas.getWidth(),canvas.getHeight()), null);  
	}
	
	public void update(double elapsed){
		time += elapsed; 
	}

}
