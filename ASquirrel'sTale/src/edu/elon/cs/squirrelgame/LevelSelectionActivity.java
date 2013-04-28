package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class LevelSelectionActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_level_selection);
	}

	public void intentLevelOne(View v){
		Intent beginLevelOne = new Intent(LevelSelectionActivity.this, LevelOne.class);
		/* Create bundle?  or put extra*/
		/* Put things in it */
		startActivity(beginLevelOne);
	}
}
