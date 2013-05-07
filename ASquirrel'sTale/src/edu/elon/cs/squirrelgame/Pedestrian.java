package edu.elon.cs.squirrelgame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Pedestrian {
	protected float centerX;
	protected float centerY;
	private float width, height;
	private Bitmap image;
	private float currentX;
	private float currentY;
	private float currentHeroX;
	private float currentHeroY;
	private int screenWidth;
	private int screenHeight;
	private Context context; 
	private int acornCost; 
	private int imageID; 
	boolean called = true;
	private double angleX, angleY;
	
	
	private final float FACTOR = 4f;
	private final int DIFFICULTY_LEVEL = 10;
	private static final float SPEED_VARIABLE = 1;
	
	public Pedestrian(Context context, int imageID, int acornCost){
		this.imageID = imageID;
		image = BitmapFactory.decodeResource(context.getResources(), imageID);
		width = image.getWidth()/FACTOR;
		height = image.getHeight()/FACTOR;
		centerX = width/FACTOR+60;
		centerY = height/FACTOR+60;
		this.context = context; 
		this.acornCost = acornCost; 
	}
	
	public Pedestrian clone(float xPos, float yPos){
		 Pedestrian cloned = new Pedestrian(context, imageID, acornCost); 
		 cloned.currentX = xPos;
		 cloned.currentY = yPos; 
		 return cloned; 
	}
	
	public Pedestrian clone(){
		 Pedestrian cloned = new Pedestrian(context, imageID, acornCost); 
		 return cloned; 
		
	}
	
	public void die(){
		currentX = screenWidth * -2;
		currentY = screenHeight * -2;  
	}
	
	public void doDraw(Canvas canvas){
		canvas.drawBitmap(image, null, 
				new Rect((int)(centerX-width/FACTOR),(int)(centerY-height/FACTOR),
				(int)(centerX+width/FACTOR),(int)(centerY+height/FACTOR)),null);
		
		screenHeight = canvas.getHeight();
		screenWidth = canvas.getWidth();
	}
	
	private void translateAngle(){
		
		Random generator = new Random();
		
		double angle = generator.nextDouble() * 360;
		
		/*0 and 360*/if (angle > 348.75 && angle <= 11.25){angleX = 1; angleY = 0;}
		/*22.5*/else if(angle >11.25 && angle <=33.75){angleX = Math.cos(22.5); angleY = Math.sin(22.5);}
		/*45*/else if(angle >33.75 && angle <=56.25){angleX = Math.cos(45); angleY = Math.sin(45);}
		/*67.5*/else if(angle >56.25 && angle <=78.75){angleX = Math.cos(67.5); angleY = Math.sin(67.5);}
		/*90*/else if(angle >78.75 && angle <=101.25){angleX = 0; angleY = 1;}
		/*112.5*/else if(angle >101.25 && angle <=123.75){angleX = Math.cos(112.5); angleY = Math.sin(112.5);}
		/*135*/else if(angle >123.75 && angle <=146.25){angleX = Math.cos(135); angleY = Math.sin(135);}
		/*157.5*/else if(angle >146.25 && angle <=168.75){angleX = Math.cos(157.5); angleY = Math.sin(157.5);}
		/*180*/else if(angle >168.75 && angle <=191.25){angleX = -1; angleY = 0;}
		/*202.5*/else if(angle >191.25 && angle <=213.75){angleX = Math.cos(202.5); angleY = Math.sin(202.5);}
		/*255*/else if(angle >213.75 && angle <=236.25){angleX = Math.cos(255); angleY = Math.sin(255);}
		/*247.5*/else if(angle >236.25 && angle <=258.75){angleX = Math.cos(247.5); angleY = Math.sin(247.5);}
		/*270*/else if(angle >258.75 && angle <=281.25){angleX = 0; angleY = -1;}
		/*292.5*/else if(angle >281.25 && angle <=303.75){angleX = Math.cos(292.5); angleY = Math.sin(292.5);}
		/*315*/else if(angle >303.75 && angle <=326.25){angleX = Math.cos(315); angleY = Math.sin(315);}
		/*337.5*/else if(angle >326.25 && angle <=348.75){angleX = Math.cos(337.5); angleY = Math.sin(337.5);}
		
				
	}
	
	public void update(double elapsed, float sqlrX, float sqlrY){
		currentHeroX = sqlrX;
		currentHeroY = sqlrY;
		
		
		/* how to handle zeroes */
		if (called){
			translateAngle();
			System.out.println("(" + angleX + ", " + angleY + ")");
			called = false;
			}
		
		
		centerX += (float) (SPEED_VARIABLE * angleX);
		centerY += (float) (SPEED_VARIABLE * angleY);
		
		
		
		//decides if hero is 'on top' of them 
		if((currentHeroX >= centerX-DIFFICULTY_LEVEL && currentHeroX <= centerX+DIFFICULTY_LEVEL) 
				&& (currentHeroY >= centerY-DIFFICULTY_LEVEL && currentHeroY <= centerY+DIFFICULTY_LEVEL) ){
					die();	 
		}
	}
}
