/**
 * @author Schuyler Goodwin
 * @author Thomas Robbins; 
 * 
 * Class that models a health bar, 
 * with the abilities to increase and decrease health 
 */
package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.Style;

public class HealthBar {
	
	private double maxHealth; 
	private double currentHealth; 
	private float locationX, locationY;
	private double healthScale; 
	private double healthBarWidth, height; 
	protected boolean empty = false; 
	
	public HealthBar(float x, float y, int maximum, int current, Context context){
		this.maxHealth = maximum;
		this.currentHealth = current; 
		//this.currentHealth = 2;
		this.locationX = x;
		this.locationY = y; 
		
		healthBarWidth = context.getResources().getDimensionPixelSize(R.dimen.barWidth);
		height = context.getResources().getDimensionPixelSize(R.dimen.barHeight);
		
	}
	
	/*
	 * Method to modify health based on a hit taken
	 */
	public void Hit(double decreaseBy){
		currentHealth = currentHealth - decreaseBy; 
	}
	
	/*
	 * Getter for the health amount 
	 */
	public double getHealth(){
		return  currentHealth; 
	}
	
	public void doDraw(Canvas canvas){
		Rect bar = new Rect((int)locationX, (int)locationY, (int) (locationX + (healthScale*healthBarWidth)), (int)(locationY + height)); 
		Paint paint = new Paint(); 
		
		//if the health drops below 25% then the health bar color is changed from green to red
		if(currentHealth > (maxHealth/4)){
			paint.setColor(Color.GREEN); 
		} else {
			paint.setColor(Color.RED);
		}
		paint.setStyle(Style.FILL); 
		paint.setAlpha(255); 
		canvas.drawRect(bar, paint); 
	}
	
	public void update(double elapsed){
		healthScale = currentHealth / maxHealth; 
		
		if(currentHealth == 0.0){
			System.out.println("EMPTYYYYYYYYY"); 
			empty = true; 
		}
	}
}
