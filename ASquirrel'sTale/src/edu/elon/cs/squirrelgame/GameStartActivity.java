/**
 * @author Schuyler Goodwin
 * @author Thomas Robbins
 * @author Matthew McKenzie;
 * @author Rex Renolds 
 * 
 * Activity that models a beginning menu to the game 
 */
package edu.elon.cs.squirrelgame;


import edu.cs.elon.squirrelstale.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class GameStartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_start);
	}
	
	public void intentLevelOne(View v){
		Intent beginLevelOne = new Intent(GameStartActivity.this, GameplayActivity.class);
		startActivity(beginLevelOne);
	}

	public void intentTutorial(View v){
		Intent optionsMenu = new Intent(GameStartActivity.this, TutorialActivity.class);
		startActivity(optionsMenu);
	}
}
