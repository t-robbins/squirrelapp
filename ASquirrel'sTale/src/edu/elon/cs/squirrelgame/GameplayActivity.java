/**
 * @author Schuyler Goodwin
 * @author Thomas Robbins
 * @author Matthew McKenzie 
 * @author Rex Renolds
 * 
 * Activity that is the gameplay of the level
 */
package edu.elon.cs.squirrelgame;

import edu.cs.elon.squirrelstale.R;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.WindowManager;

public class GameplayActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gameplay);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); 
	}
	
	protected void onFinish(){
		
	}
}
