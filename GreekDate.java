package com.example.androidtts;

import android.annotation.SuppressLint;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;



public class GreekDate {
	
	private static final String[] formats={				
			"dd/MM/yy",		//days, months, years
			"HH:mm:ss",		//hours, minutes, seconds
			"HH:mm"			//hours, minutes
	};
	
	private static final String[] days={
			"μηδέν",
			"μία",
			"δύο",
			"τρείς",
			"τέσσερις",
			"πέντε",
			"έξι",
			"εφτά",
			"οχτώ",
		    "εννιά",
		    "δέκα",
		    "έντεκα",
		    "δώδεκα",
		    "δεκατρείς",
		    "δεκατέσσερις",
		    "δεκαπέντε",
		    "δεκαέξι",
		    "δεκαεφτά",
		    "δεκαοχτώ",
		    "δεκαεννιά",
		    "είκοσι",
		    "είκοσι μία",
		    "είκοσι δύο",
		    "είκοσι τρείς",
		    "είκοσι τέσσερις",
		    "είκοσι πέντε",
		    "είκοσι έξι",
		    "είκοσι εφτά",
		    "είκοσι οχτώ",
		    "είκοσι εννιά",
		    "τριάντα",
		    "τριάντα μία"
	};
	
	private static final String[] months={
			"",
			"Ιανουαρίου",
			"Φεβρουαρίου",
			"Μαρτίου",
			"Απριλίου",
			"Μαΐου",
			"Ιουνίου",
			"Ιουλίου",
			"Αυγούστου",
			"Σεπτεμβρίου",
			"Οκτωβρίου",
			"Νοεμβρίου",
			"Δεκεμβρίου",
	};

	private static final String[] hours={
			"μηδέν",
			"μία",
			"δύο",
			"τρείς",
			"τέσσερεις",
			"πέντε",
			"έξι",
			"εφτά",
			"οχτώ",
		    "εννιά",
		    "δέκα",
		    "έντεκα",
		    "δώδεκα",
		    "δεκατρείς",
		    "δεκατέσσερις",
		    "δεκαπέντε",
		    "δεκαέξι",
		    "δεκαεφτά",
		    "δεκαοχτώ",
		    "δεκαεννιά",
		    "είκοσι",
		    "είκοσι μία",
		    "είκοσι δύο",
		    "είκοσι τρείς"
	};
	
	

	
	/*
	 * Parsing a date with a given format
	 * If the conversion does not succeed, the function will return an empty String ""
	 */
	@SuppressLint("SimpleDateFormat") public static String parse(String date, String format){
		try {
			SimpleDateFormat localFormatter= new SimpleDateFormat(format);
			localFormatter.setLenient(false);
			String formattedDate=localFormatter.parse(date).toString();
			
			//days, months, years
			if(format.equals(formats[0])) return convertDate(formattedDate);
			
			//hours, minutes, seconds
			else if(format.equals(formats[1])) return convertHoursMinsSecs(formattedDate);	
			
			//hours, minutes
			else if(format.equals(formats[2])) return convertHoursMins(formattedDate);	
			
			return "";
		
		} catch (ParseException e) {
			//e.printStackTrace();
			return "";
		}
	}
	
	
	
	/*	
	 * This function splits all words in a string and returns an array of strings with the words
 	 * All spaces are being erased.
	 */
	public static String[] splitWords(String str){
		ArrayList<String> wordsList= new ArrayList<String>();
		
		for(String s:str.split(" ")){
			if(!s.isEmpty()) wordsList.add(s);	
		}
				
		return wordsList.toArray(new String[wordsList.size()]);
	}
	

	
	/*
	 * This function gets a String, checks if it is a date or time using the supported date formats, and returns the date in Greek words if found
	 * If it is not a date, the function returns an empty String ""
	 */	
	public static String dateToGreekWords(String date){
		String format=checkFormat(date);
		if(format.isEmpty())	return "";		
		
		return parse(date,format);		
	}
	
	
	//CONVERTION FUNCTIONS
	
	/*
	 * This function gets the formatted date, as created by a SimpleDateFormat object
	 * and converts it into Greek words in the form of day, month, year
	 * An example is "μία Φεβρουαρίου του 2014"
	 */
	private static String convertDate(String formattedDate){
		String[] words= splitWords(formattedDate);
		String day="",month="",year="";
		if(words.length!=6) return "";
		
		day=days[Integer.parseInt(words[2])];
		

		if(words[1].equals("Jan")) { month=months[1]; }
		else if(words[1].equals("Feb")) { month=months[2]; }
		else if(words[1].equals("Mar")) { month=months[3]; }
		else if(words[1].equals("Apr"))	{ month=months[4]; }
		else if(words[1].equals("May")) { month=months[5]; }
		else if(words[1].equals("Jun")) { month=months[6]; }
		else if(words[1].equals("Jul")) { month=months[7]; }
		else if(words[1].equals("Aug")) { month=months[8]; }
		else if(words[1].equals("Sep")) { month=months[9]; }
		else if(words[1].equals("Oct")) { month=months[10]; }
		else if(words[1].equals("Nov")) { month=months[11]; }
		else if(words[1].equals("Dec")) { month=months[12]; }
		else { month="μήνας"; }

		
		year=NumberToWordsConverter.numberStringToGreekWords(words[5]);
		
		return day+' '+month+" του "+year;
	}
	
	
	/*
	 * This function gets the formatted date, as created by a SimpleDateFormat object
	 * and converts it into Greek words in the form of hours and minutes
	 * An example is "τρείς και σαράντα πέντε λεπτά"
	 */	
	private static String convertHoursMins(String formattedDate){	
			String[] words= splitWords(formattedDate);
			if(words.length!=6) return "";
			String time=words[3];
			time=time.replaceAll(":", " ");
			String[] numbers= splitWords(time);		
			int hour=Integer.parseInt(numbers[0]);
			String minute=numbers[1];

			return hours[hour]+" και "+NumberToWordsConverter.numberStringToGreekWords(minute)+" λεπτά";
	}
	
	
	/*
	 * This function gets the formatted date, as created by a SimpleDateFormat object
	 * and converts it into Greek words in the form of hours, minutes and seconds
	 * An example is "δύο και τριάντα λεπτά και δώδεκα δευτερόλεπτα"
	 */	
	private static String convertHoursMinsSecs(String formattedDate){
		String[] words= splitWords(formattedDate);
		if(words.length!=6) return "";
		String time=words[3];
		time=time.replaceAll(":", " ");
		String[] numbers= splitWords(time);		
		int hour=Integer.parseInt(numbers[0]);
		String minute=numbers[1];
		String second=numbers[2];

		return hours[hour]+" και "+NumberToWordsConverter.numberStringToGreekWords(minute)+" λεπτά και "+NumberToWordsConverter.numberStringToGreekWords(second)+" δευτερόλεπτα";
	}
	
	
	/*
	 * This function checks if the date has any of the supported formats
	 * If it succeds, it returns the format or else it returns an empty String ""
	 * The formats are dd/MM/yy  or  HH:mm:ss  or  HH:mm
	 */
	private static String checkFormat(String date){
		if(date.length()==0) return "";
		int dateChars=0,timeChars=0;
		char currenChar;
		
		//check if the first and the last character of the string is a number, it must always be
		if(!Character.isDigit(date.charAt(0))) return "";
		if(!Character.isDigit(date.charAt((date.length()-1)))) return "";
		
		
		for(int i=1; i<date.length()-1;i++){
			currenChar=date.charAt(i);
			
			//check if every character is / or : or number		
			if(!(Character.isDigit(currenChar) || currenChar==':' || currenChar=='/')) return "";
			//check if there is a number between // or ::, if not it returns false
			if((currenChar==':' || currenChar=='/') &&  (date.charAt(i+1)==':' || date.charAt(i+1)=='/')) return "";
			
			//count the / and :
			if(currenChar==':') timeChars++; 
			else if(currenChar=='/') dateChars++;
		}
		
		//if it is time with one  :
		if(timeChars==1  && dateChars==0){
			//check if one of the numbers has more than 2 digits, then it is invalid
			int index=0;
			for(String s:splitWords(date.replaceAll(":", " "))){
				if( index==0 && s.length()!=1 && s.length()!=2) return "";
				else if(index>0 && s.length()!=2 ) return "";
				index++;
			}

			return formats[2];
		}
		
		//if it is time with two :
		else if(timeChars==2 && dateChars==0){
			//check if one of the numbers has more than 2 digits, then it is invalid
			int index=0;
			for(String s:splitWords(date.replaceAll(":", " "))){
				if( index==0 && s.length()!=1 && s.length()!=2) return "";
				else if(index>0 && s.length()!=2 ) return "";
				index++;
			}

			return formats[1];
		}
		
		//if it is a date with two /
		else if(dateChars==2 && timeChars==0){
			//check if one of the numbers have the right amount of digits
			String[] numbers= splitWords(date.replaceAll("/", " "));
			if(numbers[0].length()>2) return "";
			if(numbers[1].length()>2) return "";
			if(numbers[2].length()>4) return "";
			
			return formats[0];
		}
		
		else return "";		
	}
	
}



