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
		/* Create bundle?  or put extra*/
		/* Put things in it */
		startActivity(beginLevelOne);
	}
	
	public void intentContinue(View v){
		Intent continueGame = new Intent(GameStartActivity.this, LevelSelectionActivity.class);
		/* Create bundle? or put extra*/
		/* Put things in it */
		startActivity(continueGame);
		
	}

	public void intentOptions(View v){
		Intent optionsMenu = new Intent(GameStartActivity.this, OptionsActivity.class);
		/* Create bundle? or put extra*/
		/* Put things in it */
		startActivity(optionsMenu);
	}
	
	public void intentHighScores(View v){
		Intent viewHighScores = new Intent(GameStartActivity.this, HighScoresActivity.class);
		/* Create bundle? or put extra*/
		/* Put things in it */
		startActivity(viewHighScores);
	}
}
