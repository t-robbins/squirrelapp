package edu.elon.cs.squirrelgame;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import edu.cs.elon.squirrelstale.R;

public class HighScoresActivity extends Activity {

	
	private final String URL = "http://66.26.246.5/getscores.php";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);
	}

	private String getServerData(String returnString) {
	    
		   InputStream is = null;
		    
		   String result = "";
		    //the year data to send

		    //http post
		    try{
		            HttpClient httpclient = new DefaultHttpClient();
		            HttpPost httppost = new HttpPost(URL);
		            httppost.setEntity(null);
		            HttpResponse response = httpclient.execute(httppost);
		            HttpEntity entity = response.getEntity();
		            is = entity.getContent();


		    }catch(Exception e){
		            Log.e("log_tag", "Error in http connection "+e.toString());
		    }


		    //convert response to string
		    try{
		            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
		            StringBuilder sb = new StringBuilder();
		            String line = null;
		            while ((line = reader.readLine()) != null) {
		                    sb.append(line + "\n");
		            }
		            is.close();
		            result=sb.toString();
		    }catch(Exception e){
		            Log.e("log_tag", "Error converting result "+e.toString());
		    }
		    //parse json data
		    try{
		    	
		    		String name[] = new String[4];
		    		int score[] = new int[4];
		    		
		            JSONArray jArray = new JSONArray(result);
		            for(int i=0; i < 4; i++){
		                    JSONObject json_data = jArray.getJSONObject(i);
		                    
		                    //name[i] = json_data.get(name);
		                    //score[i] = json_data.getInt(name);
		                    
		                    
		                    //Get an output to the screen
		                    returnString += "\n\t" + jArray.getJSONObject(i); 
		            }
		    }catch(JSONException e){
		            Log.e("log_tag", "Error parsing data "+e.toString());
		    }
		    return returnString; 
		}    
		    
	
}
