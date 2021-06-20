
package com.example.androidtts;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.content.res.AssetManager;

public class TtsSoundEngine {

	private static final String tonismenaDir="tonismena";
	private static final String no_tonismenaDir="no_tonismena";
	private AssetManager assetManager;


	public TtsSoundEngine(Context appContext) {
		assetManager=appContext.getAssets();
	}


    public String[] makeFilenames(String sentence){
    	String[] diphs=splitSyllables(sentence);
    	int k= diphs.length;
    	String[] filenames=new String[k];

    	for (int i=0;i<diphs.length;i++){
    		if(diphs[i].equals(".")) filenames[i]=tonismenaDir+"/DOT.wav";

    		else if (Character.isUpperCase(diphs[i].charAt(diphs[i].length()-1)))
	    		filenames[i]=tonismenaDir+"/"+diphs[i]+".wav";
			else
			    filenames[i]=no_tonismenaDir+"/"+diphs[i]+".wav";
    	}

    	return filenames;
    }


    public String[] splitSyllables(String str){
		ArrayList<String> wordsList= new ArrayList<String>();

		for(String s:str.split("-")){
			if(!s.isEmpty()) wordsList.add(s);
		}

		return wordsList.toArray(new String[wordsList.size()]);
	}




    public InputStream makeAudioStream (String sentence) throws IOException {

    		//if the sentence ends with !, replace it with . and make a simple sentence with mergeWavFilesStream
    	    if(sentence.endsWith("!")) sentence=sentence.substring(0,sentence.length()-1)+".";
    	    String[] filenames=makeFilenames(sentence);
    	    boolean isQuestion=false;

    	    if(filenames.length==1 && filenames[0].isEmpty()) return null;


    	    if(filenames[filenames.length-1].endsWith(";.wav")){

    	    	if(filenames.length==1) filenames[0]="tonismena/DOT.wav";

    	    	else if(filenames.length>1 && !filenames[filenames.length-2].endsWith("SPACE.wav")
    	    			&& filenames[filenames.length-2].substring(0,filenames[filenames.length-2].length()-4).matches(".*[aeiouAEIOU]$")) {
    	    		filenames[filenames.length-2]=filenames[filenames.length-2].substring(0,filenames[filenames.length-2].length()-4)+";.wav";
    				filenames=Arrays.copyOfRange(filenames,0,filenames.length-1);
    				isQuestion=true;
    	    	}

    	    	else if(filenames.length>2 && !filenames[filenames.length-3].endsWith("SPACE.wav")
    	    			&& filenames[filenames.length-3].substring(0,filenames[filenames.length-3].length()-4).matches(".*[aeiouAEIOU]$")) {
    	    			filenames[filenames.length-3]=filenames[filenames.length-3].substring(0,filenames[filenames.length-3].length()-4)+";.wav";
        	    		filenames=Arrays.copyOfRange(filenames,0,filenames.length-1);
        	    		isQuestion=true;
        	    }

    	    	else filenames[filenames.length-1]="tonismena/DOT.wav";
    	    }


			if (isQuestion)  return createQuestionStream(filenames);

			return mergeWavFilesStream(filenames);
    }



	public InputStream splitAudio( String filename, float sizeToSplit) throws IOException{
		//check the sizeToSplit
		if(!(sizeToSplit>0 && sizeToSplit<=1.0)) {System.out.println("File "+filename+" not splitted. Wrong sizeToSplit value."); return null;}


		int BUFFER_LENGTH=256;
		int headerLength=44;
		int RECORDER_BPP=16; //8,16,32..etc
		int RECORDER_SAMPLERATE=44100; //8000,11025,16000,32000,48000,96000,44100..et
		int channels = 2;  //mono=1,stereo=2
		long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;

	    InputStream inputStream= assetManager.open(filename);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] abBuffer = new byte[BUFFER_LENGTH];
        int nBytesRead;

        //cut clipping from the end of file
        boolean stopLoop=false;
        byte lastByte=0;
        while ( (nBytesRead = inputStream.read(abBuffer)) != -1) {
        	if(lastByte=='<' &&  abBuffer[0]=='?') break;

        	for(int i=0;i<abBuffer.length-1;i++) {
        		if(abBuffer[i]=='<' && abBuffer[i+1]=='?') stopLoop=true;
        	}

        	if(stopLoop) break;
        	lastByte=abBuffer[abBuffer.length-1];
            baos.write(abBuffer, 0, nBytesRead);
        }

        inputStream.close();
        byte[] allAudioData = baos.toByteArray();



        //find the data size of the split audio, create the array and add the header
        int splitFileLength=(int) (allAudioData.length*sizeToSplit);
        byte[] splittedAudio = new byte[splitFileLength];

        long dataSize=splitFileLength;
		long totalDataLen = dataSize +36;

		//create the header
        byte[] header=createWavFileHeader(dataSize, totalDataLen, RECORDER_SAMPLERATE, channels, byteRate, RECORDER_BPP);

        //write the data into the split audio byte array
        for (int i =0; i < headerLength; i++) splittedAudio[i] = header[i];
        for (int i =headerLength; i < splitFileLength; i++) splittedAudio[i] = allAudioData[i];


        //create inputStream with the splittedAudio data
        InputStream  in = new ByteArrayInputStream(splittedAudio);
        in.close();
        return in;
	}




	public InputStream mergeWavFilesStream(String[] files){
		InputStream finalStream=null;
		 try {
			 int headerLength=44;
			 int RECORDER_BPP=16; //8,16,32..etc
	    	 int RECORDER_SAMPLERATE=44100; //8000,11025,16000,32000,48000,96000,44100..etc
	    	 int channels = 2;  //mono=1,stereo=2
	    	 long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;
			 int BUFFER_LENGTH=256;

			 int filesCount=files.length;
			 byte[] header=null;
			 byte[] finalData=null;
			 //ArrayList<byte[]> allByteDataArrays= new ArrayList<byte[]>();
			 byte [][] allByteDataArrays= new byte[filesCount][];

			 int finalDataSize=headerLength;
			//create all byte arrays for all inputStreams
			 for(int i=0;i<filesCount;i++){
				 	InputStream inStream= assetManager.open(files[i]);
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        byte[] abBuffer = new byte[BUFFER_LENGTH];
			        int nBytesRead;

			        //cut clipping from the end of file
			        boolean stopLoop=false;
			        byte lastByte=0;
			        while ( (nBytesRead =inStream.read(abBuffer)) != -1) {
			        	if(lastByte=='<' &&  abBuffer[0]=='?') break;
			        	for(int j=0;j<abBuffer.length-1;j++) {
			        		if(abBuffer[j]=='<' && abBuffer[j+1]=='?') stopLoop=true;
			        	}

			        	if(stopLoop) break;
			        	lastByte=abBuffer[abBuffer.length-1];
			            baos.write(abBuffer, 0, nBytesRead);
			        }

			        allByteDataArrays[i]=baos.toByteArray();
			        finalDataSize+=allByteDataArrays[i].length-headerLength;
			        inStream.close();
			 }

			 long totalDataLen = finalDataSize + 36;

			 //create header
			 header=createWavFileHeader(finalDataSize, totalDataLen, RECORDER_SAMPLERATE, channels, byteRate, RECORDER_BPP);

			 finalData= new byte[finalDataSize];

			 //copy the header to the final data
			 for(int i=0;i<headerLength;i++) finalData[i]=header[i];

			 int x=headerLength;

			 for(int i=0;i<filesCount;i++){
				 for(int j=headerLength; j<allByteDataArrays[i].length;j++){
					 finalData[x]=allByteDataArrays[i][j];
					 x++;
				 }
			 }

	        //create the final input stream
			 finalStream=new  ByteArrayInputStream(finalData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return finalStream;
	}



	public InputStream mergeWavFilesStream(InputStream [] streams){
		InputStream finalStream=null;
		 try {
			 int headerLength=44;
			 int RECORDER_BPP=16; //8,16,32..etc
	    	 int RECORDER_SAMPLERATE=44100; //8000,11025,16000,32000,48000,96000,44100..etc
	    	 int channels = 2;  //mono=1,stereo=2
	    	 long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;
			 int BUFFER_LENGTH=256;

			 int filesCount=streams.length;
			 byte[] header=null;
			 byte[] finalData=null;
			 //ArrayList<byte[]> allByteDataArrays= new ArrayList<byte[]>();
			 byte [][] allByteDataArrays= new byte[filesCount][];

			 int finalDataSize=headerLength;
			//create all byte arrays for all inputStreams
			 for(int i=0;i<filesCount;i++){
				 	//InputStream inStream=streams[i];
			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
			        byte[] abBuffer = new byte[BUFFER_LENGTH];
			        int nBytesRead;

			        //cut clipping from the end of file
			        boolean stopLoop=false;
			        byte lastByte=0;
			        while ( (nBytesRead =streams[i].read(abBuffer)) != -1) {
			        	if(lastByte=='<' &&  abBuffer[0]=='?') break;
			        	for(int j=0;j<abBuffer.length-1;j++) {
			        		if(abBuffer[j]=='<' && abBuffer[j+1]=='?') stopLoop=true;
			        	}

			        	if(stopLoop) break;
			        	lastByte=abBuffer[abBuffer.length-1];
			            baos.write(abBuffer, 0, nBytesRead);
			        }

			        allByteDataArrays[i]=baos.toByteArray();
			        finalDataSize+=allByteDataArrays[i].length-headerLength;
			        //inStream.close();
			 }

			 long totalDataLen = finalDataSize + 36;

			 //create header
			 header=createWavFileHeader(finalDataSize, totalDataLen, RECORDER_SAMPLERATE, channels, byteRate, RECORDER_BPP);

			 finalData= new byte[finalDataSize];

			 //copy the header to the final data
			 for(int i=0;i<headerLength;i++) finalData[i]=header[i];

			 int x=headerLength;

			 for(int i=0;i<filesCount;i++){
				 for(int j=headerLength; j<allByteDataArrays[i].length;j++){
					 finalData[x]=allByteDataArrays[i][j];
					 x++;
				 }
			 }

	        //create the final input stream
			 finalStream=new  ByteArrayInputStream(finalData);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		 return finalStream;
	}




	public InputStream createQuestionStream(String[] filenames) throws IOException{
		 int numberOfDiphs=filenames.length;
		 boolean lastDiph=false;
		 String qDiph=null;

		 InputStream qAudio = null;
 	     InputStream[] forMerge=new InputStream[2];


 	     if (filenames[numberOfDiphs-1].endsWith(";.wav")){
 	    	   lastDiph=true;
 	    	   qDiph=filenames[numberOfDiphs-1];
 	     }
 	     else if (numberOfDiphs>1 && filenames[numberOfDiphs-2].endsWith(";.wav")) {
 		      qDiph=filenames[numberOfDiphs-2];
 	     }


 	     if(qDiph!=null){
 	    	 //if the diphone has only one letter, then the file is already ready to be loaded and we save it in qAudio
 	    	 if(qDiph.charAt(qDiph.length()-7)=='/')  qAudio= assetManager.open(qDiph);

 	    	 //else we have to split it
 	    	 else{
	 	    	 String firstDiph=qDiph.substring(0,qDiph.length()-5)+".wav";
	 	    	 String secondDiph=(Character.isUpperCase(qDiph.charAt(qDiph.length()-6))?"":"no_")+"tonismena/"+qDiph.substring(qDiph.length()-6);

	 	    	 forMerge[0]=splitAudio(firstDiph,0.5f);
			     forMerge[1]=assetManager.open(secondDiph);
			     qAudio=mergeWavFilesStream(forMerge);
 	    	 }
 	     }


		 InputStream finalStream=null;

		 int headerLength=44;
		 int RECORDER_BPP=16; //8,16,32..etc
    	 int RECORDER_SAMPLERATE=44100; //8000,11025,16000,32000,48000,96000,44100..etc
    	 int channels = 2;  //mono=1,stereo=2
    	 long byteRate = RECORDER_BPP * RECORDER_SAMPLERATE * channels / 8;
		 int BUFFER_LENGTH=256;

		 int filesCount=numberOfDiphs;
		 byte[] header=null;
		 byte[] finalData=null;
		 //ArrayList<byte[]> allByteDataArrays= new ArrayList<byte[]>();
		 byte [][] allByteDataArrays= new byte[filesCount][];

		 int finalDataSize=headerLength;
		//create all byte arrays for all inputStreams
		 for(int i=0;i<filesCount;i++){
			 	InputStream inStream;
			 	if(lastDiph && i==filesCount-1 && qAudio!=null) inStream=qAudio;
			 	else if(!lastDiph && i==filesCount-2 && qAudio!=null) inStream=qAudio;
			 	else inStream= assetManager.open(filenames[i]);

		        ByteArrayOutputStream baos = new ByteArrayOutputStream();
		        byte[] abBuffer = new byte[BUFFER_LENGTH];
		        int nBytesRead;

		        //cut clipping from the end of file
		        boolean stopLoop=false;
		        byte lastByte=0;
		        while ( (nBytesRead =inStream.read(abBuffer)) != -1) {
		        	if(lastByte=='<' &&  abBuffer[0]=='?') break;
		        	for(int j=0;j<abBuffer.length-1;j++) {
		        		if(abBuffer[j]=='<' && abBuffer[j+1]=='?') stopLoop=true;
		        	}

		        	if(stopLoop) break;
		        	lastByte=abBuffer[abBuffer.length-1];
		            baos.write(abBuffer, 0, nBytesRead);
		        }

		        allByteDataArrays[i]=baos.toByteArray();
		        finalDataSize+=allByteDataArrays[i].length-headerLength;
		        inStream.close();
		 }

		 long totalDataLen = finalDataSize + 36;

		 //create header
		 header=createWavFileHeader(finalDataSize, totalDataLen, RECORDER_SAMPLERATE, channels, byteRate, RECORDER_BPP);

		 finalData= new byte[finalDataSize];

		 //copy the header to the final data
		 for(int i=0;i<headerLength;i++) finalData[i]=header[i];

		 int x=headerLength;

		 for(int i=0;i<filesCount;i++){
			 for(int j=headerLength; j<allByteDataArrays[i].length;j++){
				 finalData[x]=allByteDataArrays[i][j];
				 x++;
			 }
		 }

        //create the final input stream
		 finalStream=new  ByteArrayInputStream(finalData);


		 return finalStream;
	}


	private static byte[]  createWavFileHeader(long totalAudioLen,long totalDataLen, long longSampleRate, int channels, long byteRate, int RECORDER_BPP)  {

		         byte[] header = new byte[44];

		         header[0] = 'R';
		         header[1] = 'I';
		         header[2] = 'F';
		         header[3] = 'F';
		         header[4] = (byte)(totalDataLen & 0xff);
		         header[5] = (byte)((totalDataLen >> 8) & 0xff);
		         header[6] = (byte)((totalDataLen >> 16) & 0xff);
		         header[7] = (byte)((totalDataLen >> 24) & 0xff);
		         header[8] = 'W';
		         header[9] = 'A';
		         header[10] = 'V';
		         header[11] = 'E';
		         header[12] = 'f';
		         header[13] = 'm';
		         header[14] = 't';
		         header[15] = ' ';
		         header[16] = 16;
		         header[17] = 0;
		         header[18] = 0;
		         header[19] = 0;
		         header[20] = 1;
		         header[21] = 0;
		         header[22] = (byte) channels;
		         header[23] = 0;
		         header[24] = (byte)(longSampleRate & 0xff);
		         header[25] = (byte)((longSampleRate >> 8) & 0xff);
		         header[26] = (byte)((longSampleRate >> 16) & 0xff);
		         header[27] = (byte)((longSampleRate >> 24) & 0xff);
		         header[28] = (byte)(byteRate & 0xff);
		         header[29] = (byte)((byteRate >> 8) & 0xff);
		         header[30] = (byte)((byteRate >> 16) & 0xff);
		         header[31] = (byte)((byteRate >> 24) & 0xff);
		         header[32] = (byte)(2 * 16 / 8);
		         header[33] = 0;
		         header[34] = (byte) RECORDER_BPP;
		         header[35] = 0;
		         header[36] = 'd';
		         header[37] = 'a';
		         header[38] = 't';
		         header[39] = 'a';
		         header[40] = (byte)(totalAudioLen & 0xff);
		         header[41] = (byte)((totalAudioLen >> 8) & 0xff);
		         header[42] = (byte)((totalAudioLen >> 16) & 0xff);
		         header[43] = (byte)((totalAudioLen >> 24) & 0xff);

		         return header;
		     }



}
