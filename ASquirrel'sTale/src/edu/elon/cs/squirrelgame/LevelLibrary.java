package edu.elon.cs.squirrelgame;

import java.util.ArrayList;

import android.content.Context;

public class LevelLibrary {

	private Level levelOne, levelTwo, levelThree;
	
	private ArrayList<Level> library;
	private Context context;

	public LevelLibrary(Context context){
		
		this.context = context; 
		
		library = new ArrayList<Level>();
		
//		levelOne = new Level(/*properties*/);
//		library.add(levelOne);
//		levelTwo = new Level(/*properties*/);
//		library.add(levelTwo);
//		levelThree = new Level(/*properties*/);
//		library.add(levelThree);
	}
	
	public ArrayList<Level> getLevelList(){
				
		return library;
		
	}
}
