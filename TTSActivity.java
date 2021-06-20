package com.example.androidtts;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.jsoup.Jsoup;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.webkit.URLUtil;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Scroller;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;


public class TTSActivity extends Activity implements OnClickListener{
	
	private final String externalDir=Environment.getExternalStorageDirectory().toString() +"/"+"GreekTTS";	
	private final String wavFileName=externalDir+"/sound.wav";
	private final String aboutFile="text_files/about.txt";
	private final String instrFile="text_files/instructions.txt";
	private String about="";
	private String instructions="";
	
 	private GreekTextToSpeech greekTTS;
 	private AudioManager audioManager;
 	private int maxVolume;
 	private int originalVolume;
 	
 	private TextView inputText;
 	private ImageButton playButton;
 	private ImageButton pauseButton;
 	private ImageButton fileButton;
 	private ImageButton stopButton;
 	private ImageButton clearButton;
 	private ImageButton urlButton;
 	private SeekBar volBar;
 	private RelativeLayout mainLayout;
 	private int backImage=R.drawable.blue_notrans;
 	
 	
 	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tts);

		//read about.txt and instructions.txt
		try {
			String line;
			InputStream aboutStream = getApplicationContext().getAssets().open(aboutFile);
			BufferedReader aboutReader= new BufferedReader(new InputStreamReader(aboutStream));		
			while((line = aboutReader.readLine()) != null) about+=line+'\n';
			about=about.substring(0,about.length()-1);
			aboutReader.close();			
			
			InputStream instrStream = getApplicationContext().getAssets().open(instrFile);
			BufferedReader instrReader= new BufferedReader(new InputStreamReader(instrStream));
			while((line = instrReader.readLine()) != null) instructions+=line+'\n';
			instructions=instructions.substring(0,instructions.length()-1);
			instrReader.close();			
		} catch (IOException e) {
			System.out.println("Could not read about or instructions file!");
			e.printStackTrace();
		}
	    
	    	    		
		
		audioManager= (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		maxVolume=audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		originalVolume=audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
		mainLayout = (RelativeLayout) findViewById(R.id.mainlayout); 
		inputText = (TextView) findViewById(R.id.editText);			
		inputText.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				runOnUiThread(new Runnable() {
					public void run() {
						playFromCursor();
					}
				});
				
			}
		});
		inputText.setScroller(new Scroller(getApplicationContext())); 
		inputText.setVerticalScrollBarEnabled(true); 
		inputText.setMovementMethod(new ScrollingMovementMethod());
		
		checkSdFolder();

		//if the orientation has changed, get the instance of greekTTS that is saved in onSaveInstanceState
		if ((savedInstanceState != null) && (savedInstanceState.getParcelable("GreekTextToSpeech") != null)) 
			greekTTS = (GreekTextToSpeech) savedInstanceState.getParcelable("GreekTextToSpeech");
		else 	
			greekTTS= new GreekTextToSpeech(getApplicationContext());
		
		if ((savedInstanceState != null) && (savedInstanceState.getInt("background") != backImage)) 
			changeTheme();
		 

		playButton = (ImageButton) findViewById(R.id.playButton);
		playButton.setOnClickListener(this);
		
		playButton.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				runOnUiThread(new Runnable() {
					public void run() {
						stopAndPlayFromCursor();
					}
				});
				return false;
			}
		});
		
		pauseButton = (ImageButton) findViewById(R.id.pauseButton);
		pauseButton.setOnClickListener(this);
		stopButton = (ImageButton) findViewById(R.id.stopButton);
		stopButton.setOnClickListener(this);
		fileButton = (ImageButton) findViewById(R.id.fileButton);
		fileButton.setOnClickListener(this);
		urlButton = (ImageButton) findViewById(R.id.urlButton);
		urlButton.setOnClickListener(this);
		clearButton = (ImageButton) findViewById(R.id.clearButton);
		clearButton.setOnClickListener(this);
		
		volBar = (SeekBar) findViewById(R.id.volSeekBar);
		volBar.setOnClickListener(this);
		
		volBar.getProgressDrawable().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);

		volBar.setMax(maxVolume);
		volBar.setProgress(originalVolume);
		volBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() { 

		        @Override 
		        public void onStopTrackingTouch(SeekBar seekBar) { 
		        } 

		        @Override 
		        public void onStartTrackingTouch(SeekBar seekBar) { 
		        } 

		        @Override 
		        public void onProgressChanged(SeekBar seekBar, int progress,boolean fromUser) { 
		        	audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0 );
		        } 
		    });
		
	}
	
	

	//if the audio is laying is stops it and is plays it again from the position of the cursor on the edittext
	public void playFromCursor(){
		if(greekTTS.isAudioPlaying()){
			String text=inputText.getText().toString();
			if(text.isEmpty()) return;
			
			int textSize=text.length();
			int cursorPos=inputText.getSelectionStart();
			//check if the cursor is at the end of the edittext, so do nothing
			if(textSize<=cursorPos) return;
			
			//everything is valid so speak the text from the start of the word chosen
			greekTTS.resetPlayer();
			
			int startFrom;
			for(startFrom=cursorPos;startFrom>0;startFrom--){
				if(text.charAt(startFrom)==' ') break;
			}
			
					
			greekTTS.speak(text.substring(startFrom, text.length()));
		}
		
	}
	
	
	
	//stop the audio playing and plays it again from the position of the cursor on the edittext
	public void stopAndPlayFromCursor(){

			greekTTS.resetPlayer();
			String text=inputText.getText().toString();
			if(text.isEmpty()) return;
			
			int textSize=text.length();
			int cursorPos=inputText.getSelectionStart();
			//check if the cursor is at the end of the edittext, so do nothing
			if(textSize<=cursorPos) return;
			
			//everything is valid so speak the text from the start of the word chosen			
			int startFrom;
			for(startFrom=cursorPos;startFrom>0;startFrom--){
				if(text.charAt(startFrom)==' ') break;
			}
			
					
			greekTTS.speak(text.substring(startFrom, text.length()));

	}

	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.menu, menu);
		menu.add("About");
		menu.add("Change Theme");
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		if (item.getTitle().equals("About")) {	    	
		    new AlertDialog.Builder(this).setMessage(about).show();
		}
		else if (item.getTitle().equals("Change Theme")) {	  
			changeTheme();
		}
		return super.onOptionsItemSelected(item);
	}
	

	public void changeTheme(){
		if (backImage==R.drawable.blue_notrans){
			mainLayout.setBackground(getResources().getDrawable(R.drawable.white_vertical));
			inputText.setBackground(getResources().getDrawable(R.drawable.white_edittext));
			backImage=R.drawable.white_vertical;
		} else {
			mainLayout.setBackground(getResources().getDrawable(R.drawable.blue_notrans));
			inputText.setBackground(getResources().getDrawable(R.drawable.edittext));
			backImage=R.drawable.blue_notrans;
		}
	}
	//onClick for all the buttons
	@Override
	public void onClick(View v) {
		if (v==playButton){
			stopButton.setEnabled(false);
			pauseButton.setEnabled(false);
			String text=inputText.getText().toString();
		  	greekTTS.speak(text);
		  	pauseButton.setEnabled(true);
		  	stopButton.setEnabled(true);
		} 
		
		else if (v==pauseButton) {
			playButton.setEnabled(false);
			stopButton.setEnabled(false);
			greekTTS.pauseAudio();
			playButton.setEnabled(true);
			stopButton.setEnabled(true);			
		} 
		
		else if(v==stopButton) {
			playButton.setEnabled(false);
			pauseButton.setEnabled(false);
			greekTTS.stopAudio();	
			pauseButton.setEnabled(true);
			playButton.setEnabled(true);
		} 
		
		else if(v==clearButton) {			
			playButton.setEnabled(false);
			pauseButton.setEnabled(false);
			stopButton.setEnabled(false);
			greekTTS.stopAudio();
			inputText.setText("");	
			playButton.setEnabled(true);
			pauseButton.setEnabled(true);			
			stopButton.setEnabled(true);
				
		}
		
		else if (v==fileButton) {
			
			String text=inputText.getText().toString();
			
			if(text.isEmpty()) {
				Toast.makeText(this, "No text written", Toast.LENGTH_SHORT).show();
				return;
			}			
			else if(text.length()>50) {
				Toast.makeText(this, "Text should contain up to 50 characters", Toast.LENGTH_SHORT).show();
				return;
			}
			
			fileButton.setEnabled(false);
			try {
				greekTTS.createWavFileSd(text,wavFileName);
				Toast.makeText(this, "Wav file created", Toast.LENGTH_SHORT).show();
			} catch (IOException e) {
				Toast.makeText(this, "Wav file could not be created", Toast.LENGTH_SHORT).show();
				e.printStackTrace();
			}
			fileButton.setEnabled(true);
		} 
		
		else if (v==urlButton) {			
			String urlText=inputText.getText().toString();
			if(urlText.isEmpty()) {
				Toast.makeText(this, "No text written", Toast.LENGTH_SHORT).show();
				return;
			}
			
			urlText = urlText.trim();
			
			if(!URLUtil.isValidUrl(urlText)) { 
				Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show();
				return;
			}
			
			urlButton.setEnabled(false);
			Toast.makeText(this, "Downloading text, please wait..", Toast.LENGTH_SHORT).show();
			UrlThread urlT= new UrlThread(urlText);
			urlT.start();
			try {
				urlT.join();
			} catch (InterruptedException e) {
				Toast.makeText(this, "Failed to run URL thread", Toast.LENGTH_SHORT).show();
				//e.printStackTrace();
			}
			
			urlButton.setEnabled(true);
		}
		
	}

	


    @Override
    protected void onSaveInstanceState (Bundle outState) {
       super.onSaveInstanceState(outState);
       outState.putParcelable("GreekTextToSpeech", greekTTS);
       outState.putInt("background", backImage);
    }
    

   
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    switch (keyCode) {
		    case KeyEvent.KEYCODE_VOLUME_UP:
		        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		                AudioManager.ADJUST_RAISE, AudioManager.FLAG_SHOW_UI);
		        volBar.setProgress(volBar.getProgress()+1);
		        return true;
		    case KeyEvent.KEYCODE_VOLUME_DOWN:
		    	audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC,
		                AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
		    	volBar.setProgress(volBar.getProgress()-1);
		        return true;
		    case KeyEvent.KEYCODE_BACK:
		    	greekTTS.pauseAudio();
		    	moveTaskToBack(true);
	            return true;
		    default:
		        return false;
		    }
	}
	
	

	
	 private class AsyncTaskforURL extends AsyncTask<String, String, String> {

	    @Override
	    protected String doInBackground(String... params) {
	      final String text=params[0];
	      final String finalString = Jsoup.parse(text).text();
	      runOnUiThread(new Runnable() {
	                   @Override
	                   public void run() {
	                    inputText.setText(finalString);
	                   }
	               });
	     return null;
	    }
	 }
	 
	 
 
	 
   public void checkSdFolder(){
    	File ttsDir=new File(externalDir);
        if(!ttsDir.exists()){
        	ttsDir.mkdirs();
        }
    }
   
   
   
   private class UrlThread extends Thread{
	   String urlText;
	   
	   public UrlThread( String url) {
		   urlText=url;
	   }
	   
	   @Override
	   public void run() {
		   HttpParams httpParameters = new BasicHttpParams();
           HttpConnectionParams.setConnectionTimeout(httpParameters,3000); // 3s max for connection
           HttpConnectionParams.setSoTimeout(httpParameters, 4000); // 4s max to get data
           HttpClient httpclient = new DefaultHttpClient(httpParameters);
           HttpGet httpget = new HttpGet(urlText); // Set the action you want to do
           HttpResponse response;
           try {
        	    response = httpclient.execute(httpget);
        	    HttpEntity entity = response.getEntity(); 
                InputStream is = entity.getContent(); // Create an InputStream with the response
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"), 8);
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) // Read line by line
                    sb.append(line + "\n");

                String resString = sb.toString(); // Result is here
                AsyncTaskforURL runner = new AsyncTaskforURL();
                      runner.execute(resString);
                is.close(); // Close the stream
	     } catch (ClientProtocolException e1) {
	      // TODO Auto-generated catch block
	      e1.printStackTrace();
	     } catch (IOException e1) {
	      // TODO Auto-generated catch block
	      e1.printStackTrace();
	     } 
		super.run();
	   }
   }
     

}
