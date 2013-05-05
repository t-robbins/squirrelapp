package edu.elon.cs.squirrelgame;


import java.util.ArrayList;

import edu.cs.elon.squirrelstale.R;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Level {

	int pedCount;
	double acornSpawnRate;
	int freshCount= 0, sophCount = 0, junCount = 0, senCount = 0;
	String mapSrc;
	ArrayList<Rect> obstacles;
	ArrayList<Pedestrian> peds;
	
	private Pedestrian freshman; 
	private Pedestrian sophomore;
	private Pedestrian junior;
	private Pedestrian senior;
	
	
	public Level(int pedCount, double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount,
			String mapSrc, ArrayList<Rect> obstacles, Context context){
		
		this.obstacles = obstacles;
		
		freshman = new Pedestrian(context, R.drawable.freshman_ped, 5); 
		sophomore = new Pedestrian(context, R.drawable.sophomore_ped, 10);
		junior = new Pedestrian(context, R.drawable.junior_ped, 15);
		senior = new Pedestrian(context, R.drawable.senior_ped, 20);
		
		pedCount = freshCount + sophCount + junCount + senCount;
	}
	
	//populating an array of instantated pedsetiran objects that will exist on the level 
	private void createPedList(){
		peds = new ArrayList<Pedestrian>();
		
		for(int f = 0; f <= freshCount; f++){
			peds.add(freshman.clone());
		}
		
		for(int so = 0; so <= sophCount; so++){
			peds.add(sophomore.clone()); 
		}
		for(int j = 0; j <= junCount; j++){
			peds.add(junior.clone());
		}
		for(int se = 0; se <= senCount; se++){
			peds.add(senior.clone());
		}
	}
}
