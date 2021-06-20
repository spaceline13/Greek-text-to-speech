
package com.example.androidtts;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator") public class GreekTextToSpeech implements Parcelable  {



	private GreekTextAnalyzer textAnalyzer;
	private TtsSoundEngine soundEngine;
	private boolean speakPaused=false;
	private SpeakThread speakThread;
	Context myContext;

	public GreekTextToSpeech(Context appContext) {
		myContext=appContext;
		textAnalyzer= new GreekTextAnalyzer(myContext);
		soundEngine= new TtsSoundEngine(myContext);
		speakThread= null;
	}



	public boolean isAudioPaused(){
		return speakPaused;
	}


	public  void speak(String str){

		if(str.isEmpty()) return;

		if(speakThread!=null && speakThread.isAlive()){
			if(speakPaused) resumeAudio();
			else return;
		}
		else{
			speakThread= new SpeakThread(str);
			speakThread.start();
		}
    }



	public void stopAudio(){
		resetPlayer();
	}


	public void resetPlayer(){
		pauseAudio();
		speakThread=null;
	}



	//Pause the audio, in fact use the code that does that in the speak function
	public void pauseAudio(){
		if(speakThread!=null) speakPaused = true;
	}


	//Resume the audio, in fact use the code that does that in the speak function
	public void resumeAudio(){
		speakPaused = false;
		if(speakThread!=null){
			synchronized(speakThread){
				speakThread.notify();
			}
		}
	}




	/*
	 * This Runnable analyzes all sentences and plays the sound for each one
	 * This procedure is done for every paragraph separately
	 */
	private class SpeakThread extends Thread{
		private String defaultString;
		private int sentIndex=0,parIndex=0;
		InputStream currAudioData,nextAudioData;
		int minBufferSize ;
		private  String[] paragraphs;
		private  String[] sentences;
		private  String analyzedSentence;
		private boolean nextAudioExists;
		NextSoundThread nextSoundThread;
		AudioTrack audioTrack;

		int BUFFER_LENGTH=512;
		byte[] buffer = new byte[BUFFER_LENGTH];


		public SpeakThread(String strToSpeak) {
			defaultString=strToSpeak;
			nextSoundThread=null;
			nextAudioExists=false;
			minBufferSize = AudioTrack.getMinBufferSize(44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT);

		}


		public void run() {
			speakPaused=false;
			nextAudioExists=false;

			try{
				paragraphs=defaultString.split("\n");

				for(parIndex=0;parIndex<paragraphs.length;parIndex++){

					//check for empty Strings, because split creates them sometimes
					if(!paragraphs[parIndex].isEmpty()){

						sentences=textAnalyzer.splitSentences(paragraphs[parIndex]);

						for(sentIndex=0;sentIndex<sentences.length;sentIndex++){

							audioTrack= new AudioTrack(AudioManager.STREAM_MUSIC, 44100, AudioFormat.CHANNEL_OUT_STEREO, AudioFormat.ENCODING_PCM_16BIT,  minBufferSize, AudioTrack.MODE_STREAM);

							if(nextAudioExists){
								currAudioData=nextAudioData;
								nextAudioExists=false;
							}
							else {
								analyzedSentence=textAnalyzer.analyzeSentToDiph(sentences[sentIndex]);
								currAudioData=soundEngine.makeAudioStream(analyzedSentence);
							}


							//start the nextSoundThread to make the sound of the next sentence, if there is one.
							//I do this only just after the sound of the current sentence has started playing
							if(sentIndex<sentences.length-1) {
								nextSoundThread= new NextSoundThread(sentIndex+1);
								nextSoundThread.start();
								nextAudioExists=true;
							}

							if(currAudioData!=null){
								audioTrack.play();

								int tempdata=0;
								while((tempdata = currAudioData.read(buffer)) != -1){

									//I synchronize this code to check if the speakSuspended has been changed from pauseAudio
									synchronized(this) {
										if(speakPaused){
											wait();
										}
									}

									audioTrack.write(buffer,0, tempdata);
								}

								audioTrack.stop();
							}
							//join the nextSoundThread so I will be sure that it has ended its work
							if(nextAudioExists) {
								nextSoundThread.join();
							}

						}// end of for j
					}//end of if is empty
				}// end of for i

			} catch (InterruptedException e) {
				//System.out.println("InterruptedException in PlayThread");
				//e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		};


		private class NextSoundThread extends Thread{
			int nextSentIndex;

			public NextSoundThread(int nextSent) {
				nextSentIndex=nextSent;
			}

			@Override
			public void run() {
				analyzedSentence=textAnalyzer.analyzeSentToDiph(sentences[nextSentIndex]);
				try {
					nextAudioData=soundEngine.makeAudioStream(analyzedSentence);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Next Sentence Audio Stream made!");

				super.run();
			}
		}


	}//end of SpeakThread



	public void createWavFileSd(String text, String fileName) throws IOException {
    	if (text.isEmpty()) return;

		int sentIndex=0,parIndex=0;
		String[] paragraphs;
		String[] sentences;
		String analyzedSentence;

		int BUFFER_LENGTH=512;
		byte[] buffer = new byte[BUFFER_LENGTH];
		ArrayList<InputStream> streamsList= new ArrayList<InputStream>();
		OutputStream out;

		paragraphs=text.split("\n");

		for(parIndex=0;parIndex<paragraphs.length;parIndex++){

			//check for empty Strings, because split creates them sometimes
			if(!paragraphs[parIndex].isEmpty()){
				sentences=textAnalyzer.splitSentences(paragraphs[parIndex]);

				for(sentIndex=0;sentIndex<sentences.length;sentIndex++){
					if(!sentences[sentIndex].isEmpty()){
						analyzedSentence=textAnalyzer.analyzeSentToDiph(sentences[sentIndex]);
						streamsList.add(soundEngine.makeAudioStream(analyzedSentence));
					}
				}// end of for j
			}//end of if is empty
		}// end of for i


		//if there are InputStreams created, create and write them into the wav file
		if(!streamsList.isEmpty()){
			out=new FileOutputStream(fileName);
			int tempdata=0;
			for(int i=0;i<streamsList.size();i++){
				while((tempdata = streamsList.get(i).read(buffer)) != -1) out.write(buffer,0, tempdata);
			}
			out.close();
		}

	}

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub

	}



}


}
