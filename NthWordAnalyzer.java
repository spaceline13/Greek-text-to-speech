
package com.example.androidtts;

import java.text.DecimalFormat;

public class NthWordAnalyzer {


	/*
	  private static final String[] wordEndings = {
		    "ο", "ό",
		    "η", "ή",
		    "ος","ός",
		    "α", "ά",
		    "ες","ές",
		    "οι","οί",
		    "ου","ού",
		    "ης","ής",
		    "ων","ών",
		    "ους","ούς",
		  };
	 */

	  private static final String[] tenthNames = {
	    "",
	    " δέκατ",
	    " εικοστ",
	    " τριακοστ",
	    " τεσσαρακοστ",
	    " πεντηκοστ",
	    " εξηκοστ",
	    " εβδομηκοστ",
	    " ογδοηκοστ",
	    " ενενηκοστ"
	  };


	  private static final String[] nthNames = {
		    "",
		    " πρώτ",
		    " δεύτερ",
		    " τρίτ",
		    " τέταρτ",
		    " πέμπτ",
		    " έκτ",
		    " εύδομ",
		    " όγδο",
		    " ένατ",
		    " δέκατ",
		    " ενδέκατ",
		    " δωδέκατ",
		  };


	  private static final String[] hundredthNames = {
		    "",
		    " εκατοστ",
		    " διακοσιοστ",
		    " τριακοσιοστ",
		    " τετρακοσιοστ",
		    " πεντακοσιοστ",
		    " εξακοσιοστ",
		    " επτακοσιοστ",
		    " οκτακοσιοστ",
		    " εννιακοσιοστ"
		  };



	  private static String[] nthNeutral;
	  private static String[] tenthNeutral;
	  private static String[] hundredthNeutral;

	  private static String[] nthFemale;
	  private static String[] tenthFemale;
	  private static String[] hundredthFemale;

	  private static String[] nthMale;
	  private static String[] tenthMale;
	  private static String[] hundredthMale;

	  private static String[] nthNeutralPl;
	  private static String[] tenthNeutralPl;
	  private static String[] hundredthNeutralPl;

	  private static String[] nthFemalePl;
	  private static String[] tenthFemalePl;
	  private static String[] hundredthFemalePl;

	  private static String[] nthMalePl;
	  private static String[] tenthMalePl;
	  private static String[] hundredthMalePl;

	  private static String[] nthOy;
	  private static String[] tenthOy;
	  private static String[] hundredthOy;

	  private static String[] nthHs;
	  private static String[] tenthHs;
	  private static String[] hundredthHs;

	  private static String[] nthOn;
	  private static String[] tenthOn;
	  private static String[] hundredthOn;

	  private static String[] nthOus;
	  private static String[] tenthOus;
	  private static String[] hundredthOus;



	  /*
	   * Initializing all the arrays
	   */
	  static{

		  int nthLength=nthNames.length;
		  int hundredthLength=hundredthNames.length;
		  int tenthLength=tenthNames.length;

		  nthNeutral= new String[nthLength]; nthNeutral[0]="";
		  tenthNeutral= new String[tenthLength]; tenthNeutral[0]="";
		  hundredthNeutral= new String[hundredthLength]; hundredthNeutral[0]="";
		  nthFemale= new String[nthLength]; nthFemale[0]="";
		  tenthFemale= new String[tenthLength]; tenthFemale[0]="";
		  hundredthFemale= new String[hundredthLength]; hundredthFemale[0]="";
		  nthMale= new String[nthLength]; nthMale[0]="";
		  tenthMale= new String[tenthLength]; tenthMale[0]="";
		  hundredthMale= new String[hundredthLength]; hundredthMale[0]="";
		  nthNeutralPl= new String[nthLength]; nthNeutralPl[0]="";
		  tenthNeutralPl= new String[tenthLength]; tenthNeutralPl[0]="";
		  hundredthNeutralPl= new String[hundredthLength]; hundredthNeutralPl[0]="";
		  nthFemalePl= new String[nthLength]; nthFemalePl[0]="";
		  tenthFemalePl= new String[tenthLength]; tenthFemalePl[0]="";
		  hundredthFemalePl= new String[hundredthLength]; hundredthFemalePl[0]="";
		  nthMalePl= new String[nthLength]; nthMalePl[0]="";
		  tenthMalePl= new String[tenthLength]; tenthMalePl[0]="";
		  hundredthMalePl= new String[hundredthLength]; hundredthMalePl[0]="";
		  nthOy= new String[nthLength]; nthOy[0]="";
		  tenthOy= new String[tenthLength]; tenthOy[0]="";
		  hundredthOy= new String[hundredthLength]; hundredthOy[0]="";
		  nthHs= new String[nthLength]; nthHs[0]="";
		  tenthHs= new String[tenthLength]; tenthHs[0]="";
		  hundredthHs= new String[hundredthLength]; hundredthHs[0]="";
		  nthOn= new String[nthLength]; nthOn[0]="";
		  tenthOn= new String[tenthLength]; tenthOn[0]="";
		  hundredthOn= new String[hundredthLength]; hundredthOn[0]="";
		  nthOus= new String[nthLength]; nthOus[0]="";
		  tenthOus= new String[tenthLength]; tenthOus[0]="";
		  hundredthOus= new String[hundredthLength]; hundredthOus[0]="";


		  for(int i=1;i<nthLength;i++){
			  nthNeutral[i]=nthNames[i]+"ο";
			  nthFemale[i]=nthNames[i]+"η";
			  nthMale[i]=nthNames[i]+"ος";
			  nthNeutralPl[i]=nthNames[i]+"α";
			  nthFemalePl[i]=nthNames[i]+"ες";
			  nthMalePl[i]=nthNames[i]+"οι";
			  nthOy[i]=nthNames[i]+"ου";
			  nthHs[i]=nthNames[i]+"ης";
			  nthOn[i]=nthNames[i]+"ων";
			  nthOus[i]=nthNames[i]+"ους";
		  }

		  tenthNeutral[1]=tenthNames[1]+"ο";
		  tenthFemale[1]=tenthNames[1]+"η";
		  tenthMale[1]=tenthNames[1]+"ος";
		  tenthNeutralPl[1]=tenthNames[1]+"α";
		  tenthFemalePl[1]=tenthNames[1]+"ες";
		  tenthMalePl[1]=tenthNames[1]+"οι";
		  tenthOy[1]=tenthNames[1]+"ου";
		  tenthHs[1]=tenthNames[1]+"ης";
		  tenthOn[1]=tenthNames[1]+"ων";
		  tenthOus[1]=tenthNames[1]+"ους";

		  for(int i=2;i<tenthLength;i++){
			  tenthNeutral[i]=tenthNames[i]+"ό";
			  tenthFemale[i]=tenthNames[i]+"ή";
			  tenthMale[i]=tenthNames[i]+"ός";
			  tenthNeutralPl[i]=tenthNames[i]+"ά";
			  tenthFemalePl[i]=tenthNames[i]+"ές";
			  tenthMalePl[i]=tenthNames[i]+"οί";
			  tenthOy[i]=tenthNames[i]+"ού";
			  tenthHs[i]=tenthNames[i]+"ής";
			  tenthOn[i]=tenthNames[i]+"ών";
			  tenthOus[i]=tenthNames[i]+"ούς";
		  }

		  for(int i=1;i<hundredthLength;i++){
			  hundredthNeutral[i]=hundredthNames[i]+"ό";
			  hundredthFemale[i]=hundredthNames[i]+"ή";
			  hundredthMale[i]=hundredthNames[i]+"ός";
			  hundredthNeutralPl[i]=hundredthNames[i]+"ά";
			  hundredthFemalePl[i]=hundredthNames[i]+"ές";
			  hundredthMalePl[i]=hundredthNames[i]+"οί";
			  hundredthOy[i]=hundredthNames[i]+"ού";
			  hundredthHs[i]=hundredthNames[i]+"ής";
			  hundredthOn[i]=hundredthNames[i]+"ών";
			  hundredthOus[i]=hundredthNames[i]+"ούς";
		  }


	  }


	  public static void main(String[] args) {
		  String ending=nthToGreekWords("999999999999η");
		  System.out.println(ending);

	  }


	  /*
	   * This function converts a number that is up to 999 into Greek words
	   * The first argument is the number and the second is the gender of the suffix of the words
	   * gender==0 neutral , gender==1 female ,  gender==2 male or else gender is neutral
	   * An example is "τριακόσιες πενήντα" or "τριακόσια πενήντα"
	   */
	  private static String convertLessThanOneThousand(int number,String gender) {
	    if(gender.isEmpty()) return "";
	    String soFar="";
	    String hundreds= "";


	    if(gender.equals("ο") || gender.equals("ό") ){
			    if (number % 100 < 13){
			      soFar = nthNeutral[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthNeutral[number % 10];
			      number /= 10;
			      soFar = tenthNeutral[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthNeutral[number];
	    }

	    else if(gender.equals("η") || gender.equals("ή") ){
		        if (number % 100 < 13){
			      soFar = nthFemale[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthFemale[number % 10];
			      number /= 10;
			      soFar = tenthFemale[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthFemale[number];
	    }

	    else if(gender.equals("ος") || gender.equals("ός") ){
		        if (number % 100 < 13){
			      soFar = nthMale[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthMale[number % 10];
			      number /= 10;
			      soFar = tenthMale[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthMale[number];
	    }

	    else if(gender.equals("α") || gender.equals("ά") ){
		        if (number % 100 < 13){
			      soFar = nthNeutralPl[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthNeutralPl[number % 10];
			      number /= 10;
			      soFar = tenthNeutralPl[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthNeutralPl[number];
	    }

	    else if(gender.equals("ες") || gender.equals("ές") ){
		        if (number % 100 < 13){
			      soFar = nthFemalePl[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthFemalePl[number % 10];
			      number /= 10;
			      soFar = tenthFemalePl[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthFemalePl[number];
	    }

	    else if(gender.equals("οι") || gender.equals("οί") ){
		        if (number % 100 < 13){
			      soFar = nthMalePl[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthMalePl[number % 10];
			      number /= 10;
			      soFar = tenthMalePl[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthMalePl[number];
	    }

	    else if(gender.equals("ου") || gender.equals("ού") ){
		        if (number % 100 < 13){
			      soFar = nthOy[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthOy[number % 10];
			      number /= 10;
			      soFar = tenthOy[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthOy[number];
	    }

		else if(gender.equals("ης") || gender.equals("ής") ){
		        if (number % 100 < 13){
			      soFar = nthHs[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthHs[number % 10];
			      number /= 10;
			      soFar = tenthHs[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthHs[number];
		}
		else if(gender.equals("ων") || gender.equals("ών") ){

		        if (number % 100 < 13){
			      soFar = nthOn[number % 100];
			      number /= 100;
			    }
			    else {
			      soFar = nthOn[number % 10];
			      number /= 10;
			      soFar = tenthOn[number % 10] + soFar;
			      number /= 10;
			    }
			    hundreds= hundredthOn[number];
		}
		else if(gender.equals("ους") || gender.equals("ούς") ){

	        if (number % 100 < 13){
		      soFar = nthOus[number % 100];
		      number /= 100;
		    }
		    else {
		      soFar = nthOus[number % 10];
		      number /= 10;
		      soFar = tenthOus[number % 10] + soFar;
		      number /= 10;
		    }
		    hundreds= hundredthOus[number];
		}

		else return "";


	    if (number == 0) return soFar;
	    return hundreds + ' ' + soFar;
	  }



	/*
	 * This function converts numbers from 0 to 999 999 999 999 (or up to billions) to greek words
	*/
	  public static String longNthToGreekWords(long number,String gender) {
		  String billionth,millionth,thousandth;

	      if(gender.equals("ο") || gender.equals("ό") ){
		    	thousandth=" χιλιοστό";
		    	millionth=" εκατομμυριοστό";
		    	billionth=" δισεκατομμυριοστό";
	      }
	      else if(gender.equals("η") || gender.equals("ή") ){
		    	thousandth=" χιλιοστή";
		    	millionth=" εκατομμυριοστή";
		    	billionth=" δισεκατομμυριοστή";
	      }
	      else if(gender.equals("ος") || gender.equals("ός") ){
		    	thousandth=" χιλιοστός";
		    	millionth=" εκατομμυριοστός";
		    	billionth=" δισεκατομμυριοστός";
	      }
	      else if(gender.equals("α") || gender.equals("ά") ){
		    	thousandth=" χιλιοστά";
		    	millionth=" εκατομμυριοστά";
		    	billionth=" δισεκατομμυριοστά";
	      }
	      else if(gender.equals("ες") || gender.equals("ές") ){
		    	thousandth=" χιλιοστές";
		    	millionth=" εκατομμυριοστές";
		    	billionth=" δισεκατομμυριοστές";
	      }
		 else if(gender.equals("οι") || gender.equals("οί") ){
		    	thousandth=" χιλιοστοί";
		    	millionth=" εκατομμυριοστοί";
		    	billionth=" δισεκατομμυριοστοί";
		 }
		 else if(gender.equals("ου") || gender.equals("ού") ){
		    	thousandth=" χιλιοστού";
		    	millionth=" εκατομμυριοστού";
		    	billionth=" δισεκατομμυριοστού";
		 }
		 else if(gender.equals("ης") || gender.equals("ής") ){
		    	thousandth=" χιλιοστής";
		    	millionth=" εκατομμυριοστής";
		    	billionth=" δισεκατομμυριοστής";
		 }
		 else if(gender.equals("ων") || gender.equals("ών") ){
		    	thousandth=" χιλιοστών";
		    	millionth=" εκατομμυριοστών";
		    	billionth=" δισεκατομμυριοστών";
		 }
		 else if(gender.equals("ους") || gender.equals("ούς") ){
		    	thousandth=" χιλιοστούς";
		    	millionth=" εκατομμυριοστούς";
		    	billionth=" δισεκατομμυριοστούς";
		 }

		 else return "";



	    if (number == 0) { return "μηδενικ"+gender; }

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
	    	case 1:
			    tradBillions = billionth;
			    break;
		    default :
		    	tradBillions = convertLessThanOneThousand(billions,gender) + billionth;
	    }

	    switch (millions) {
		    case 0:
		    	tradMillions = "";
		    	break;
		    case 1:
		    	tradMillions = millionth;
		    	break;
		    default :
		    	tradMillions = convertLessThanOneThousand(millions,gender)  + millionth;
	    }

	    switch (hundredThousands) {
		    case 0:
		      tradHundredThousands = "";
		      break;
		    case 1:
			  tradHundredThousands = thousandth;
			  break;
		    default :
		      tradHundredThousands = convertLessThanOneThousand(hundredThousands,gender) + thousandth;
	    }

	    tradThousand = convertLessThanOneThousand(thousands,gender);
	    String result = tradBillions + tradMillions +tradHundredThousands+ tradThousand;

	    //Remove any extra spaces
	    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	 }




	  /*
	 * This function returns true if the expression is in the form of 1ος 13οι 7ου etc
	 */
	private static String getNthEnding(String str){

		if( str.endsWith("ους") || str.endsWith("ούς") ){
			 return str.substring(str.length()-3,str.length());
		}

		else if( str.endsWith("ος") || str.endsWith("ός") || str.endsWith("οι") || str.endsWith("οί")
			|| str.endsWith("ες") || str.endsWith("ές") || str.endsWith("ου") || str.endsWith("ού")
			|| str.endsWith("ης") || str.endsWith("ής") || str.endsWith("ων") || str.endsWith("ών")){

			 return str.substring(str.length()-2,str.length());
		 }

		else if(str.endsWith("ο")  || str.endsWith("ό")  || str.endsWith("η")  || str.endsWith("ή")  || str.endsWith("α")  || str.endsWith("ά")){

			 return str.substring(str.length()-1,str.length());
		}

		return "";
	}



	public static String nthToGreekWords(String str){
		if(str.length()<2) return "";

		String ending=getNthEnding(str);
		if(ending.isEmpty())  return "";

		String number=str.substring(0,str.length()-ending.length());

		if(number.charAt(0)=='0' || number.length()>12) return WordAnalyzer.simpleNumericExprToWords(number)+' '+ending;

		//check if the number has only numbers in it, because . or , or any other character is not accepted
		for(int i=0;i<number.length();i++) {
			if(!Character.isDigit(number.charAt(i))) return WordAnalyzer.simpleNumericExprToWords(number)+' '+ending;
		}

		//Finally, everything is ok, so I can analyze the expression with longNthToGreekWords
		return longNthToGreekWords(Long.parseLong(number),ending);
	}

}


