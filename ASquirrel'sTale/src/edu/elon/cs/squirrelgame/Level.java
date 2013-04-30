package edu.elon.cs.squirrelgame;


import java.util.ArrayList;

import android.graphics.Rect;

public class Level {

	int pedCount;
	double acornSpawnRate;
	int freshCount= 0, sophCount = 0, junCount = 0, senCount = 0;
	String mapSrc;
	ArrayList<Rect> obstacles;
	ArrayList<Pedestrian> peds;
	
	public Level(int pedCount, double acornSpawnRate,
			int freshCount, int sophCount, int junCount, int senCount,
			String mapSrc, ArrayList<Rect> obstacles){
		
		this.obstacles = obstacles;
		
		
		pedCount = freshCount + sophCount + junCount + senCount;
	}
	
	
	private void createPedList(){
		peds = new ArrayList<Pedestrian>();
		
		for(int f = 0; f <= freshCount; f++){
			//create ped object
			//add ped object to arraylist
		}
		
		for(int so = 0; so <= sophCount; so++){
			//create ped object
			//add ped object to arraylist
		}
		for(int j = 0; j <= junCount; j++){
			//create ped object
			//add ped object to arraylist
		}
		for(int se = 0; se <= senCount; se++){
			//create ped object
			//add ped object to arraylist
		}
	}
}
