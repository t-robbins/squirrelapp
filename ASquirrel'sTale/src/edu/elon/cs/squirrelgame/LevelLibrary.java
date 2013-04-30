package edu.elon.cs.squirrelgame;

import java.util.ArrayList;

public class LevelLibrary {

	Level levelOne, levelTwo, levelThree;
	
	ArrayList<Level> library;

	public LevelLibrary(){
		
		library = new ArrayList<Level>();
		
		levelOne = new Level(/*properties*/);
		library.add(levelOne);
		levelTwo = new Level(/*properties*/);
		library.add(levelTwo);
		levelThree = new Level(/*properties*/);
		library.add(levelThree);
	}
	
	public ArrayList<Level> getLevelList(){
				
		return library;
		
	}
}
