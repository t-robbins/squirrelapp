/**
 * @author Schuyler Goodwin
 * @author Thomas Robbins 
 * @author Matthew McKenzie
 * @author Rex Renolds
 * 
 * Activity that displays the tutorial
 *  
 */
package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.os.Build;

public class TutorialActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
	}



}
