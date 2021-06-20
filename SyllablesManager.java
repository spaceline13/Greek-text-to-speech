
package com.example.androidtts;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;


/*
 * This class reads the text files that contain the syllables, sorts them and does all the necessary functions that the program needs.
 */
public class SyllablesManager {
	private  String[] syllablesArray;
	private  int syllablesArraySize;
	private final String syllablesFile="text_files/syllables.txt";


	/*
	 * opens the text files with the syllables, reads the data into them
	 * and sorts them, so we can use binary search method to search them.
	 */
    SyllablesManager(Context myContext){
		ArrayList<String> syllablesList= new ArrayList<String>();
		String line;

		//open the files and save the data into the ArrayLists
		try {
			//FileReader syllablesFile=new FileReader(syllablesFileName);

			InputStream textStream=myContext.getAssets().open(syllablesFile);
		    BufferedReader syllablesReader= new BufferedReader(new InputStreamReader(textStream));

			while((line = syllablesReader.readLine()) != null) syllablesList.add(line);
			syllablesReader.close();

			//Convert the list to array, so it can be used by the StringsComparator to get sorted
			syllablesArray = new String[syllablesList.size()];
			syllablesList.toArray(syllablesArray);

			//sort the array
			Arrays.sort(syllablesArray);
			syllablesArraySize=syllablesArray.length;


		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found!");
			e.printStackTrace();
		}
		catch (IOException e) {
			System.out.println("ERROR: Could not read file!");
			e.printStackTrace();
		}
	}



    //Find if the input String is a syllable, using binary search in the syllables array
    public  boolean isSyllable(String str){
    	int first = 0;
	    int last  = syllablesArraySize;
	    int mid;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (str.compareTo(syllablesArray[mid]) < 0) {
	            last = mid;
	        } else if (str.compareTo(syllablesArray[mid]) > 0) {
	            first = mid + 1;
	        } else {
	            return true;
	        }
	    }
	    return false;
    }



	public  String[] getSyllablesArray() {
		return syllablesArray;
	}



	public  int getSyllablesArraySize() {
		return syllablesArraySize;
	}


}












