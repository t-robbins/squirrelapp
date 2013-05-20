package edu.elon.cs.squirrelgame;
import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;


public class ScoreScreen {
	private Bitmap background; 
	private double time = 0; 
	protected boolean display = false; 
	private final int DISPLAY_TIME = 5;  
	private int score = 0; 
	private int levelTime = 0; 
	
	private Paint mainPaint;
	private int textMainX, textMainY;
	private Paint outputPaint; 
	private int textOutputX, textOutputY;
	private int textOutput2X, textOutput2Y;
	private Paint waitPaint; 
	private int textWaitX, textWaitY;
	
	public ScoreScreen(Context context){
		this.background = BitmapFactory.decodeResource(context.getResources(), R.drawable.black);
		
		mainPaint = new Paint(); 
		mainPaint.setAlpha(255); 
		mainPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		mainPaint.setStrokeWidth(1);
		mainPaint.setColor(Color.GREEN);
		mainPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.scoreFontSize)); 
		
		textMainX = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenMainDistFromLeft); 
		textMainY = context.getResources().getDimensionPixelSize(R.dimen.scoreDistFromTop); 
		
		outputPaint = new Paint(); 
		outputPaint.setAlpha(255); 
		outputPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		outputPaint.setStrokeWidth(1);
		outputPaint.setColor(Color.CYAN);
		outputPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.scoreFontSize));
		
		textOutputX = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenOutputDistFromLeft);
		textOutputY = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenOutputDistTop);
		textOutput2X = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenOutput2DistFromLeft);
		textOutput2Y = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenOutput2DistTop);
		
		waitPaint = new Paint(); 
		waitPaint = new Paint(); 
		waitPaint.setAlpha(255); 
		waitPaint.setStyle(Paint.Style.FILL_AND_STROKE);
		waitPaint.setStrokeWidth(1);
		waitPaint.setColor(Color.YELLOW);
		waitPaint.setTextSize(context.getResources().getDimensionPixelSize(R.dimen.scoreFontSize));
		
		textWaitX = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenWaitDistFromLeft);
		textWaitY = context.getResources().getDimensionPixelSize(R.dimen.scoreScreenWaitDistTop);
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(background, null, new Rect(0,0,canvas.getWidth(),canvas.getHeight()), null);  
		String sucess = "Success!!"; 
		String scoreStr = "Your Score is: "+score; 
		String timeStr = "Time for level: "+ levelTime+" seconds"; 
		//String output = "Your Score is: "+score+"\nThe time it took to complete the level: "+ levelTime;
		String wait = "Please wait "+DISPLAY_TIME+" seconds for the next level to load"; 
		
		canvas.drawText(sucess, textMainX, textMainY, mainPaint); 
		//canvas.drawText(output, textOutputX, textOutputY, outputPaint);
		canvas.drawText(scoreStr, textOutputX, textOutputY, outputPaint); 
		canvas.drawText(timeStr, textOutput2X, textOutput2Y, outputPaint); 
		canvas.drawText(wait, textWaitX, textWaitY, waitPaint); 
		
	}
	
	public void displayScreen(){
		display = true; 
	}
	
	public void setProperties(int score, int levelTime){
		this.score = score; 
		this.levelTime = levelTime; 
		
	}
	
	public void update(double elapsed){
		time += elapsed; 
		if( time >= DISPLAY_TIME){
			display = false; 
			//finished 
		}
	}

}
