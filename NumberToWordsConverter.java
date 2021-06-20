
package com.example.androidtts;

import java.text.DecimalFormat;

public class NumberToWordsConverter {


  private static final String[] tensNames = {
    "",
    " δέκα",
    " είκοσι",
    " τριάντα",
    " σαράντα",
    " πενήντα",
    " εξήντα",
    " εβδομήντα",
    " ογδόντα",
    " ενενήντα"
  };

  private static final String[] numNamesNeutral = {
    "",
    " ένα",
    " δύο",
    " τρία",
    " τέσσερα",
    " πέντε",
    " έξι",
    " εφτά",
    " οχτώ",
    " εννιά",
    " δέκα",
    " έντεκα",
    " δώδεκα",
    " δεκατρία",
    " δεκατέσσερα",
    " δεκαπέντε",
    " δεκαέξι",
    " δεκαεφτά",
    " δεκαοχτώ",
    " δεκαεννιά"
  };


  private static final String[] numNamesFemale = {
    "",
    " μία",
    " δύο",
    " τρείς",
    " τέσσερις",
    " πέντε",
    " έξι",
    " εφτά",
    " οχτώ",
    " εννιά",
    " δέκα",
    " έντεκα",
    " δώδεκα",
    " δεκατρείς",
    " δεκατέσσερις",
    " δεκαπέντε",
    " δεκαέξι",
    " δεκαεφτά",
    " δεκαοχτώ",
    " δεκαεννιά"
  };


  private static final String[] numNamesMale = {
    "",
    " ένας",
    " δύο",
    " τρείς",
    " τέσσερις",
    " πέντε",
    " έξι",
    " εφτά",
    " οχτώ",
    " εννιά",
    " δέκα",
    " έντεκα",
    " δώδεκα",
    " δεκατρείς",
    " δεκατέσσερις",
    " δεκαπέντε",
    " δεκαέξι",
    " δεκαεφτά",
    " δεκαοχτώ",
    " δεκαεννιά"
  };


  private static final String[] hundredsNamesNeutral = {
	    "",
	    " εκατόν",
	    " διακόσια",
	    " τριακόσια",
	    " τετρακόσια",
	    " πεντακόσια",
	    " εξακόσια",
	    " επτακόσια",
	    " οκτακόσια",
	    " εννιακόσια"
	  };


  private static final String[] hundredsNamesFemale = {
	    "",
	    " εκατόν",
	    " διακόσιες",
	    " τριακόσιες",
	    " τετρακόσιες",
	    " πεντακόσιες",
	    " εξακόσιες",
	    " επτακόσιες",
	    " οκτακόσιες",
	    " εννιακόσιες"
	  };

  private static final String[] hundredsNamesMale = {
	    "",
	    " εκατόν",
	    " διακόσιοι",
	    " τριακόσιοι",
	    " τετρακόσιοι",
	    " πεντακόσιοι",
	    " εξακόσιοι",
	    " επτακόσιοι",
	    " οκτακόσιοι",
	    " εννιακόσιοι"
	  };



  /*
   * This function converts a number that is up to 999 into Greek words
   * The first argument is the number and the second is the gender of the suffix of the words
   * gender==0 neutral , gender==1 female ,  gender==2 male or else gender is neutral
   * An example is "τριακόσιες πενήντα" or "τριακόσια πενήντα"
   */
  private static String convertLessThanOneThousand(int number,int gender) {
    String soFar;
    if(gender<0 || gender >2) gender=0;
    String hundreds= "";

    if(gender==0){
		    if (number % 100 < 20){
		      soFar = numNamesNeutral[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = numNamesNeutral[number % 10];
		      number /= 10;

		      soFar = tensNames[number % 10] + soFar;
		      number /= 10;
		    }

		    hundreds= hundredsNamesNeutral[number];
    }

    else if (gender==1){
    	if (number % 100 < 20){
		      soFar = numNamesFemale[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = numNamesFemale[number % 10];
		      number /= 10;

		      soFar = tensNames[number % 10] + soFar;
		      number /= 10;
		    }
		    hundreds=hundredsNamesFemale[number];
    }

    else{
    	if (number % 100 < 20){
		      soFar = numNamesMale[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = numNamesMale[number % 10];
		      number /= 10;

		      soFar = tensNames[number % 10] + soFar;
		      number /= 10;
		    }
		    hundreds= hundredsNamesMale[number];
    }

    if (number == 0) return soFar;
    else if(number==1 && soFar=="") return "εκατό";
    return hundreds + ' ' + soFar;

  }



/*
 * This function converts numbers from 0 to 999 999 999 999 (or up to billions) to greek words
*/
  public static String longToGreekWords(long number) {
    if (number == 0) { return "μηδέν"; }

    String snumber = Long.toString(number);

    // pad with "0"
    String mask = "000000000000";
    DecimalFormat df = new DecimalFormat(mask);
    snumber = df.format(number);

    // XXXnnnnnnnnn
    int billions = Integer.parseInt(snumber.substring(0,3));
    // nnnXXXnnnnnn
    int millions  = Integer.parseInt(snumber.substring(3,6));
    // nnnnnnXXXnnn
    int hundredThousands = Integer.parseInt(snumber.substring(6,9));
    // nnnnnnnnnXXX
    int thousands = Integer.parseInt(snumber.substring(9,12));

    String tradBillions,tradMillions,tradHundredThousands,tradThousand;

    switch (billions) {
    case 0:
	      tradBillions = "";
	      break;
	    case 1 :
	      tradBillions = "ένα δισεκατομμύριο ";
	      break;
	    default :
	      tradBillions = convertLessThanOneThousand(billions,0)  + " δισεκατομμύρια ";
    }

    switch (millions) {
	    case 0:
	      tradMillions = "";
	      break;
	    case 1 :
	      tradMillions = " ένα εκατομμύριο ";
	      break;
	    default :
	      tradMillions = convertLessThanOneThousand(millions,0) + " εκατομμύρια ";
    }

    switch (hundredThousands) {
	    case 0:
	      tradHundredThousands = "";
	      break;
	    case 1 :
	      tradHundredThousands = " χίλια ";
	      break;
	    default :
	      tradHundredThousands = convertLessThanOneThousand(hundredThousands,1) + " χιλιάδες ";
    }

    tradThousand = convertLessThanOneThousand(thousands,0);
    String result = tradBillions + tradMillions +tradHundredThousands+ tradThousand;

    //Remove any extra spaces
    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
  }


  //This function gets a number as a String and converts it to Greek Words.
  //It words up to trillion, and if the number is bigger, it says the numbers one by one
  public static String  numberStringToGreekWords(String number){
	  //check if the string is empty
	  if(number.isEmpty()) return "";

	  int length=number.length();

	  //check if the string has only numbers in it
	  for(int i=0;i<length;i++){
		  if(!Character.isDigit(number.charAt(i))) return "";
	  }

	  //if the number is in the billion scale
	  if(length<=12) return longToGreekWords(Long.parseLong(number));

	  //if the number is in the trillion scale
	  else if(length<=15){

		  if(Integer.parseInt(number.substring(0,length-12))>1) {
			  String trillion= longToGreekWords(Long.parseLong(number.substring(0,length-12)))+" τρισεκατομμύρια";
			  String billion= longToGreekWords(Long.parseLong(number.substring(length-12,length)));
			  return trillion+' '+billion;
		  }
		  else if(Integer.parseInt(number.substring(0,length-12))==1) {
			  String trillion= "ένα τρισεκατομμύριο";
			  String billion= longToGreekWords(Long.parseLong(number.substring(length-12,length)));
			  return trillion+' '+billion;
		  }
		  else{
			  return longToGreekWords(Long.parseLong(number.substring(length-12,length)));
		  }
	  }

	  //if the number is even bigger, say the numbers one by one
	  else{
		  String numbersString="";
		  for(int i=0;i<length;i++){
			  numbersString=numbersString+CharactersContainer.numberToGreekWord(number.charAt(i))+' ';
		  }

		  return numbersString.substring(0,numbersString.length()-1);
	  }

  }


}//end of class
