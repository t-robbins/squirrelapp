package edu.elon.cs.squirrelgame;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Rect;

public class LevelLibrary {

	private Level levelOne, levelTwo, levelThree;
	
	private ArrayList<Level> library;
	private Context context;

	public LevelLibrary(Context context){
		
		this.context = context; 
		
		library = new ArrayList<Level>();
		
		
		
		levelOne = new Level(2, 2, 0, 0, 0, null, context);
		library.add(levelOne);
		System.out.println("levelOne added!"); 
		System.out.println("at index 0: "+library.get(0).name); 
		
		
//		levelTwo = new Level(/*properties*/);
//		library.add(levelTwo);
//		levelThree = new Level(/*properties*/);
//		library.add(levelThree);
	}
	
	public ArrayList<Level> getLevelList(){
		System.out.println("level list gotten"); 	
		return library;
		
	}

}
