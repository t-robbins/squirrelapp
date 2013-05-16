package edu.elon.cs.squirrelgame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import edu.cs.elon.squirrelstale.R;

public class HighScoresActivity extends Activity {

	private final String queryScoresURL = "http://66.26.246.5/queryScores.php";
	private final String postURL = "http://66.26.246.5/postScore.php";

	private ArrayList<playerObject> scores;
	protected TextView firstName, firstScore;
	protected TextView secondName, secondScore;
	protected TextView thirdName, thirdScore;
	protected TextView fourthName, fourthScore;
	protected TextView fifthName, fifthScore;

	private String name = "Hello";
	private int score = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_high_scores);

		scores = new ArrayList<playerObject>();

		firstName = (TextView) findViewById(R.id.firstName);
		firstScore = (TextView) findViewById(R.id.firstScore);

		secondName = (TextView) findViewById(R.id.secondName);
		secondScore = (TextView) findViewById(R.id.secondScore);

		thirdName = (TextView) findViewById(R.id.thirdName);
		thirdScore = (TextView) findViewById(R.id.thirdScore);

		fourthName = (TextView) findViewById(R.id.fourthName);
		fourthScore = (TextView) findViewById(R.id.fourthScore);

		fifthName = (TextView) findViewById(R.id.fifthName);
		fifthScore = (TextView) findViewById(R.id.fifthScore);

		new GetHighScores().execute(queryScoresURL);

		//uploadScore();
	}

	@Override
	protected void onResume(){
		super.onResume();

	}

		private class GetHighScores extends AsyncTask<String, Void, String> {

			@Override
			protected String doInBackground(String... queryScoresURL) {

				StringBuilder nameStringBuilder = new StringBuilder();
				HttpClient queryClient = new DefaultHttpClient();

				for (String getQuery : queryScoresURL) {
					HttpGet queryGet = new HttpGet(getQuery);
					try {
						HttpResponse resultResponse = queryClient.execute(queryGet);
						StatusLine webStatus = resultResponse.getStatusLine();

						if (webStatus.getStatusCode() == 200) {
							HttpEntity arrayEntity = resultResponse.getEntity();
							InputStream arrayContent = arrayEntity.getContent();
							InputStreamReader resultInput = new InputStreamReader(arrayContent);
							BufferedReader scoreReader = new BufferedReader(resultInput);
							String lineIn;


							while ((lineIn = scoreReader.readLine()) != null) {
								// add to the string builder
								nameStringBuilder.append(lineIn);
							}
						}
					} catch (IOException ex) {
					}
				}

				return nameStringBuilder.toString();
			}

			@Override
			protected void onPostExecute(String result) {
				try {

					JSONArray resultArray = new JSONArray(result);;

					for(int i = 0; i < resultArray.length(); i+=2){

						System.out.println(resultArray.getString(i) + " " +  resultArray.getInt(i+1));
						scores.add(new playerObject(resultArray.getString(i), resultArray.getInt(i+1)));

					}

					firstName.setText(scores.get(0).name);
					firstScore.setText("\t\t" + scores.get(0).score);

					secondName.setText(scores.get(1).name);
					secondScore.setText("\t\t" + scores.get(1).score);

					thirdName.setText(scores.get(2).name);
					thirdScore.setText("\t\t" + scores.get(2).score);

					fourthName.setText(scores.get(3).name);
					fourthScore.setText("\t\t" + scores.get(3).score);

					fifthName.setText(scores.get(4).name);
					fifthScore.setText("\t\t" + scores.get(4).score);

				} catch (JSONException e) {
					System.out.println("didn't work");
				}
			}

		}

	}

	class playerObject{
		protected String name;
		protected String score;

		public playerObject(String n, int s){
			name = n;
			score = String.valueOf(s);
		}

		@Override
		public String toString(){
			return (name + ": " + score);

		}

	}
