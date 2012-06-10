package capstone.android.digitalflashcard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;

/*
 * Downgrade from 4.0.3
 * 
 */



public class DigitalFlashCardActivity extends Activity {
	/** Called when the activity is first created. */
	//EditText module_number = (EditText) findViewById(R.id.get_module_number);
	//use module_number.getText() somewhere to get the module id
	public Module CURRENT_MODULE;
	public int CURRENT_QUESTION, RIGHT;
	public ArrayList<Question> NOTECARDS = new ArrayList<Question>();

	public ProgressBar mProgress;
	private int STATUS = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}//end of onCreate function

	public void mainMenu(View view){
		setContentView(R.layout.main);
		TextView curMod = (TextView)findViewById(R.id.currentModuleLabel);
		curMod.setText("Current module: "+CURRENT_MODULE.name);
	}

	public void showAnswer(View view){

		TextView a = (TextView)findViewById(R.id.answer);
		a.setText(NOTECARDS.get(CURRENT_QUESTION).answer);
	}



	public void nextCard(View view){
		/*
		 * if(right radio button is marked) increment right counter
		 * if(current_question == notecards.size()-1) load stats page
		 * else load next card
		 */
		mProgress = (ProgressBar) findViewById(R.id.progressBar1);
	


			RadioGroup g = (RadioGroup)findViewById(R.id.usersAnswer);
			int selected = g.getCheckedRadioButtonId();
			RadioButton b = (RadioButton)findViewById(selected);
			if(b.getText().equals("Right"))
				RIGHT = RIGHT + 1;
			if(CURRENT_QUESTION == NOTECARDS.size()-1){
				setContentView(R.layout.stats);
				TextView correct = (TextView)findViewById(R.id.numberCorrect);
				correct.setText("Correct: "+RIGHT);
				TextView totalNumberOfCards = (TextView)findViewById(R.id.totalNumberOfCards);
				totalNumberOfCards.setText("Total: "+NOTECARDS.size());
				TextView currentModule = (TextView)findViewById(R.id.curModule);
				currentModule.setText("Current Module: "+CURRENT_MODULE.name);

				//set grade
				double grade = ((double)RIGHT) / NOTECARDS.size();
				Log.i("Grade", "Grade ="+grade);

				TextView score = (TextView)findViewById(R.id.grade);
				if(grade >= .9)
					score.setText("A"); 
				else if(grade >= .8)
					score.setText("B");
				else if(grade >= .7)
					score.setText("C");
				else if(grade >= .6)
					score.setText("D");
				else if(grade <= .6)
					score.setText("F");
				//sets grade
				return;
			}

			g.clearCheck();
			CURRENT_QUESTION++;
			STATUS++;

			TextView q = (TextView)findViewById(R.id.question);
			q.setText(NOTECARDS.get(CURRENT_QUESTION).question);

			TextView a = (TextView)findViewById(R.id.answer);
			a.setText("");

			TextView currentCard = (TextView)findViewById(R.id.currentCard);
			currentCard.setText(""+(CURRENT_QUESTION+1));

			TextView cardTotal = (TextView)findViewById(R.id.cardTotal);
			cardTotal.setText(""+NOTECARDS.size());
			
			mProgress.setProgress(STATUS*100/NOTECARDS.size());
		
	}

	public void soloStartMenu(View view){
		if(CURRENT_MODULE == null){
			showPopUp("Error", "Please choose a module");
			return;
		} 


		setContentView(R.layout.flash_card);
		STATUS = 0;



		Log.i("Creating Question","Starting to create questions....");
		NOTECARDS = createQuestions();
		CURRENT_QUESTION = 0;
		RIGHT = 0;

		Log.i("Questions created", "Questions have been loaded");
		TextView q = (TextView)findViewById(R.id.question);
		q.setText(NOTECARDS.get(CURRENT_QUESTION).question);
		Log.i("Question text", "Question text set");

		TextView a = (TextView)findViewById(R.id.answer);
		a.setText("");
		Log.i("Answer text", "Answer text set");

		TextView currentCard = (TextView)findViewById(R.id.currentCard);
		currentCard.setText(""+(CURRENT_QUESTION+1));


		Log.i("Size of NOTECARDS", "Size of our notecards ="+NOTECARDS.size());
		TextView cardTotal = (TextView)findViewById(R.id.cardTotal);
		cardTotal.setText(""+NOTECARDS.size());

		Log.i("end", "this is the end of the world");

	}//end of soloStart function

	private void showPopUp(String title, String message) {

		AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
		helpBuilder.setTitle(title);
		helpBuilder.setMessage(message);
		helpBuilder.setPositiveButton("Back", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				// Do nothing but close the dialog
			}
		});


		// Remember, create doesn't show the dialog
		AlertDialog helpDialog = helpBuilder.create();
		helpDialog.show();

	}

	public void groupStartMenu(View view){
		showPopUp("In Progress", "Currently under development");
		//setContentView(R.layout.group);
	}//end of groupStart function

	public void chooseModuleMenu(View view){
		setContentView(R.layout.choose_module);
		populateSpinner();
	}

	public void chooseModule(int id){

	}


	public void updateCurrentModuleLabel(View view){
		Spinner s = (Spinner)findViewById(R.id.spinner);
		setContentView(R.layout.main);


		if(!s.getSelectedItem().toString().equals("Select Module")){
			CURRENT_MODULE = createModule(s.getSelectedItem().toString());
			CURRENT_MODULE.name = s.getSelectedItem().toString();
		}
		TextView i = (TextView)findViewById(R.id.currentModuleLabel);
		i.setText("Current Module: "+CURRENT_MODULE.name);

	}

	/*
	 * Function to fill an array list of questions from a module
	 */
	public ArrayList<Question> createQuestions(){
		ArrayList<Question> questions = new ArrayList<Question>();
		Question noteCard;
		int id = 0;
		String question = " ", answer = " ";
		String result = new GetRequest().doInBackground("SELECT * FROM Questions where M_ID = "+CURRENT_MODULE.id+";");
		Log.i("Question result", "Question result: "+result);
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i = 0; i<jArray.length(); i++){
				JSONObject json_data = jArray.getJSONObject(i);
				id = Integer.parseInt(json_data.getString("Q_ID"));
				question = json_data.getString("QUE");
				answer = json_data.getString("ANS");
				noteCard = new Question(question, answer, id);
				questions.add(noteCard);
			}
		}catch(JSONException e){
			Log.e("Log_tag", "error parsing data for questions"+e.toString());
		}
		return questions;
	}

	/*
	 * @temp = name of the module we selected
	 */
	public Module createModule(String temp){
		Module module;
		String name =" ", desc = " ";
		int id = -1, numCards = -1;
		String result = new GetRequest().doInBackground("SELECT * FROM Modules;");
		Log.i("result", "result: "+result);
		try{
			JSONArray jArray = new JSONArray(result);
			for(int i=0;i<jArray.length(); i++){
				JSONObject json_data = jArray.getJSONObject(i);
				name = json_data.getString("NAME");
				id = Integer.parseInt(json_data.getString("M_ID"));
				desc = json_data.getString("DESCRIPTION");
				numCards = Integer.parseInt((json_data.getString("NUM_CARDS")));
				if(name.equals(temp))
					break;
			}
			module = new Module(name, numCards, desc, id);
			return module;
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
		return null;
	}


	public void populateSpinner(){
		String result = new GetRequest().doInBackground("SELECT * FROM Modules;");
		Log.i("result", "result:"+result);
		String array_spinner[];
		try{
			JSONArray jArray = new JSONArray(result);
			array_spinner = new String[jArray.length()+1];
			for(int i=0;i<jArray.length()+1;i++){
				if(i == 0){
					array_spinner[i] = "Select Module";
					continue;
				}
				JSONObject json_data = jArray.getJSONObject(i-1);
				array_spinner[i] = json_data.getString("NAME");
			}//end of the for loop     
			Spinner s = (Spinner)findViewById(R.id.spinner);
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item ,array_spinner);
			s.setAdapter(adapter);

		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data "+e.toString());
		}
	}

	public void quitApplication(View view){
		//old way
		//this.finish();
		
		//bring up the Home application by its corresponding Intent
		Intent intent = new Intent(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_HOME);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

}//end of DigitalFlashCardActivity class