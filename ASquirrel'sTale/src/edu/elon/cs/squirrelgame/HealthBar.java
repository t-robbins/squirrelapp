package edu.elon.cs.squirrelgame;

import android.graphics.*;
import android.graphics.Paint.Style;

public class HealthBar {
	
	private double maxHealth; 
	private double currentHealth; 
	private float locationX, locationY;
	private double healthScale; 
	private double healthBarWidth = 300; 
	protected boolean empty = false; 
	
	public HealthBar(float x, float y, int maximum){
		this.maxHealth = maximum;
		this.currentHealth = maxHealth; 
		//this.currentHealth = 2;
		this.locationX = x;
		this.locationY = y; 
	}
	
	public void Hit(double decreaseBy){
		currentHealth = currentHealth - decreaseBy; 
	}
	
	public void regainHealth(double increaseBy){
		currentHealth += increaseBy; 
	}
	
	public double getHealth(){
		return  currentHealth; 
	}
	
	public void doDraw(Canvas canvas){
		//System.out.println("health scale - draw: "+healthScale);
		Rect bar = new Rect((int)locationX, (int)locationY, (int) (locationX + (healthScale*healthBarWidth)), (int)(locationY + 50)); 
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
			empty = true; 
		}
	}
}
