package com.example.androidtts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


/*
 * This class has all the functions that convert an expression into its verbal form
 * The expressions are: the abbreviation, the normal word, the numeric expression and the single character
 */
public class WordAnalyzer {
	private static Abbreviation[] abbreviationsArray;
	private static int abbreviationsArraySize;



	/*
	 * This is the comparator that is used to sort the abbreviationsArray
	 * It gets sorted by the abbreviation name
	 */
   public static Comparator<Abbreviation> AbbreviationsComparator = new Comparator<Abbreviation>() {

        @Override
        public int compare(Abbreviation a1, Abbreviation a2) {
        	return a1.getAbbreviation().compareTo(a2.getAbbreviation());
        }
    };




	/*
	 * STATIC CONSTRUCTOR
	 * Initializing the abbreviationsArray and sorting it with AbbreviationsComparator
	 */
	static {

		abbreviationsArray= new Abbreviation[]{
				new Abbreviation("οκ","όκέι"),new Abbreviation("Οκ","όκέι"),new Abbreviation("ΟΚ","όκέι"),
				new Abbreviation("ok","όκέι"),new Abbreviation("Ok","όκέι"),new Abbreviation("OK","όκέι"),
				new Abbreviation("οκ.","όκέι"),new Abbreviation("Οκ.","όκέι"),new Abbreviation("ΟΚ.","όκέι"),
				new Abbreviation("ok.","όκέι"),new Abbreviation("Ok.","όκέι"),new Abbreviation("OK.","όκέι"),
				new Abbreviation("α.α.","αντ αυτού"),new Abbreviation("α/α","αύξων αριθμός"),new Abbreviation("ά.α.","άνευ αντικειμένου"),
				new Abbreviation("αριθ.","αριθμός"),new Abbreviation("βλ.","βλέπε"),
				new Abbreviation("δηλ","δηλαδή"),new Abbreviation("δηλ.","δηλαδή"),
				new Abbreviation("δισ.","δισεκατομμύρια"),new Abbreviation("δισεκατ.","δισεκατομμύρια"),
				new Abbreviation("τρισ.","τρισεκατομμύρια"),new Abbreviation("τρισεκατ.","τρισεκατομμύρια"),
				new Abbreviation("εκατ.","εκατομμύρια"),new Abbreviation("εκατομ.","εκατομμύρια"),
				new Abbreviation("Η/Υ","ηλεκτρονικός υπολογιστής"),new Abbreviation("Η/Υ.","ηλεκτρονικός υπολογιστής"),
				new Abbreviation("κα","κυρία"),new Abbreviation("Κα","κυρία"),new Abbreviation("κα.","κυρία"),new Abbreviation("Κα.","κυρία"),
				new Abbreviation("κος","κύριος"),new Abbreviation("Κος","κύριος"),new Abbreviation("κος.","κύριος"),new Abbreviation("Κος.","κύριος"),
				new Abbreviation("μτφ.","μεταφορικά"),new Abbreviation("μεταφ.","μεταφορικά"),
				new Abbreviation("αρσ.","αρσενικό"),new Abbreviation("θηλ.","θηλυκό"),
				new Abbreviation("μτφρ.","μετάφραση"),new Abbreviation("ουσ.","ουσιαστικό"),
				new Abbreviation("επίρρ.","επίρρημα"),new Abbreviation("μτχ.","μετοχή"),new Abbreviation("μτχ","μετοχή"),
				new Abbreviation("κ.α.","και άλλα"),new Abbreviation("ουδ.","ουδέτερο"),
				new Abbreviation("κ.λπ.","και λοιπά"),new Abbreviation("κλπ.","και λοιπά"),new Abbreviation("κλπ","και λοιπά"),
				new Abbreviation("ΒΑ","βορειοανατολικά"),new Abbreviation("ΒΑ.","βορειοανατολικά"),
				new Abbreviation("ΒΔ","βορειοδυτικά"),new Abbreviation("ΒΔ.","βορειοδυτικά"),
				new Abbreviation("ΝΑ","νοτειοανατολικά"),new Abbreviation("ΝΑ.","νοτειοανατολικά"),
				new Abbreviation("ΝΔ","νοτειοδυτικά"),new Abbreviation("ΝΔ.","νοτειοδυτικά"),
				new Abbreviation("κ.ο.κ.","και ούτω καθεξής"),new Abbreviation("κ.τ.ό.","και τα όμοια"),new Abbreviation("κ.τ.τ.","και τα τοιαύτα"),
				new Abbreviation("κ.τ.λ.","και τα λοιπά"),new Abbreviation("κτλ.","και τα λοιπά"),new Abbreviation("κτλ","και τα λοιπά"),
				new Abbreviation("λχ","λόγου χάρη"),new Abbreviation("λ.χ.","λόγου χάρη"),new Abbreviation("λχ.","λόγου χάρη"),
				new Abbreviation("μ.δ.","μη διαθέσιμα"),new Abbreviation("μ.Χ.","μετά Χριστόν"),new Abbreviation("π.Χ.","προ Χριστού"),
				new Abbreviation("μ.μ.","μετά μεσημβρίας"),new Abbreviation("π.μ.","προ μεσημβρίας"),new Abbreviation("ό.π.","όπου παραπάνω"),
				new Abbreviation("π.δ.α.α.","που δεν αναφέρονται αλλού"),new Abbreviation("π.δ.κ.α.","που δεν κατατάσσονται αλλού"),
				new Abbreviation("π.χ.","παραδείγματος χάριν"),new Abbreviation("πχ.","παραδείγματος χάριν"),new Abbreviation("πχ","παραδείγματος χάριν"),
				new Abbreviation("τ.μ.","τετραγωνικά μέτρα"),new Abbreviation("τμ.","τετραγωνικά μέτρα"),new Abbreviation("τμ","τετραγωνικά μέτρα"),
				new Abbreviation("PC","πί σί"),new Abbreviation("pc","πί σί"),new Abbreviation("Pc","πί σί"),
				new Abbreviation("PC.","πί σί"),new Abbreviation("pc.","πί σί"),new Abbreviation("Pc.","πί σί"),
				new Abbreviation("kg","κιλά"),new Abbreviation("kg.","κιλά"),new Abbreviation("κυβ.","κυβικά"),
				new Abbreviation("χλμ.","χιλιόμετρα"),new Abbreviation("m","μέτρα"),new Abbreviation("m.","μέτρα"),
				new Abbreviation("cm","εκατοστά"),new Abbreviation("cm.","εκατοστά"),
				new Abbreviation("mm","χιλιοστά"),new Abbreviation("mm.","χιλιοστά"),
				new Abbreviation("nm","νανόμετρα"),new Abbreviation("nm.","νανόμετρα"),
				new Abbreviation("a.m.","έι έμ"),new Abbreviation("p.m.","πί έμ"),
				new Abbreviation("BC","προ Χριστού"),new Abbreviation("BC.","προ Χριστού"),
				new Abbreviation("km","χιλιόμετρα"),new Abbreviation("km.","χιλιόμετρα"),
				new Abbreviation("kph","χιλιόμετρα ανά ώρα"),new Abbreviation("kph.","χιλιόμετρα ανά ώρα"),
				new Abbreviation("mph","μίλια ανά ώρα"),new Abbreviation("mph.","μίλια ανά ώρα"),
				new Abbreviation(">=","μεγαλύτερο ή ίσο του"),new Abbreviation("<=","μικρότερο ή ίσο του"),

		};


		Arrays.sort(abbreviationsArray,AbbreviationsComparator);

		abbreviationsArraySize=abbreviationsArray.length;
	}




    /*
     * This function gets as input a String, searches it the array with the abbreviations,
     * and if it finds it, it returns the meaning of the abbreviation in words
     * Binary search is used.
     */
	public static String analyzeAbbreviation(String str){
		if(str.isEmpty()) 	return "";

		int first = 0;
	    int last  = abbreviationsArraySize;
	    int mid,index=0;
	    while (first < last) {
	        mid = first + ((last - first) / 2);
	        if (str.compareTo(abbreviationsArray[mid].getAbbreviation()) < 0) {
	            last = mid;
	        } else if (str.compareTo(abbreviationsArray[mid].getAbbreviation()) > 0) {
	            first = mid + 1;
	        } else {
	        	index= mid;
	            return  abbreviationsArray[index].getMeaning();
	        }
	    }
	    return "";
	}




	/*
	 * This function takes a word as input and returns a string which is the word with just letters
	 * English characters are converted to their word form, like the letter "a" gets converted into "έι"
	 */
	public static String analyzeWordExpression(String str){
		String outputString="";

		for(int i=0;i<str.length();i++){
			//check if it is a Greek letter
			if(CharactersContainer.isGreekLetter(str.charAt(i))) outputString+=str.charAt(i);
			//else if it finds it is an English letter it will return the word of it and if it doesn't, it will return ""
			else outputString+=' '+CharactersContainer.engLetterToGreekWord(str.charAt(i))+' ';
		}

		return outputString;
	}




	/*
	 * This function returns true if the input string is a word expression
	 * A word expression has to have at least one letter and not have any numbers inside it
	 */
	public static boolean isWordExpression(String str){
		//check if it has numbers first
		if(hasNumber(str)) return false;

		for(int i=0;i<str.length();i++){
			if(CharactersContainer.isLetter(str.charAt(i))) return true;
		}

		return false;
	}



	/*
	 * This function returns true if the input string is a numeric expression
	 * A numeric expression has to have at least one number inside it
	 */

	public static boolean isNumericExpression(String str){
		for(int i=0;i<str.length();i++){
			if(Character.isDigit(str.charAt(i))) return true;
		}
		return false;
	}


	/*
	 * This function returns true if the input String has a least one number
	 */
	public static boolean hasNumber(String str){
		for(int i=0;i<str.length();i++){
			if(Character.isDigit(str.charAt(i))) return true;
		}
		return false;
	}


	/*
	 * This function gets a numeric expression as argument, and converts it to Greek words
	 * The numeric expression might be a simple one with numbers and symbols, a date or time or a nth expression
	 */
	public static String analyzeNumericExpression(String str){
		String analyzedWord;

		//if the string is empty, return ""
		if(str.length()==0) return "";

		//if the length of the string is 1, it is a single number, so I just return the word expression of it
		else if(str.length()==1) return CharactersContainer.numberToGreekWord(str.charAt(0));

		//check if it is a date first, if it is not, it will be an empty String ""
		analyzedWord=GreekDate.dateToGreekWords(str);
		if(!analyzedWord.isEmpty()) return analyzedWord;

		//then check if it is nth expression
		analyzedWord=NthWordAnalyzer.nthToGreekWords(str);
		if(!analyzedWord.isEmpty()) return analyzedWord;

		//then check if it is currency expression
		analyzedWord=currencyToGreekWords(str);
		if(!analyzedWord.isEmpty()) return analyzedWord;

		//if it is none of the above, analyze it as a regular numeric expression and return it
		return simpleNumericExprToWords(str);

	}



	/*	This function splits all words in a string and returns an array of strings with the words
 	 *  All spaces are being erased.
	 */
	public static String[] splitWords(String str){
		ArrayList<String> wordsList= new ArrayList<String>();

		for(String s:str.split(" ")){
			if(!s.isEmpty()) wordsList.add(s);
		}

		return wordsList.toArray(new String[wordsList.size()]);
	}




	/*
	 * This function checks if the input String is a currency
	 */
	private static boolean isCurrency(String str){
		int length=str.length();
		if(length<2) return false;
		boolean  atFirstChar=str.charAt(0)=='$' || str.charAt(0)=='€' || str.charAt(0)=='£';
		boolean  atLastChar=str.charAt(length-1)=='$' || str.charAt(length-1)=='€' || str.charAt(length-1)=='£';

		if(atFirstChar ^ atLastChar) return true;

		return false;
	}



	/*
	 * This function converts the input String, if it a currency, to its verbal form
	 * If it is not a currency, it returns an empry String ""
	 */
	private static String currencyToGreekWords(String str){
		if(isCurrency(str)){
			if(str.charAt(0)=='$') return "δολλάρια " + simpleNumericExprToWords(str.substring(1,str.length()));
			else if(str.charAt(0)=='€' ) return "ευρώ " + simpleNumericExprToWords(str.substring(1,str.length()));
			else if(str.charAt(0)=='£') return "λίρες" + simpleNumericExprToWords(str.substring(1,str.length()));
			else if(str.endsWith("$")) return simpleNumericExprToWords(str.substring(0,str.length()-1)) + " δολλάρια";
			else if(str.endsWith("€")) return simpleNumericExprToWords(str.substring(0,str.length()-1)) + " ευρώ";
			else if(str.endsWith("£")) return simpleNumericExprToWords(str.substring(0,str.length()-1)) + " λίρες";
		}

		return "";
	}




	/*
	 * This function converts a numeric expression with numbers and symbols in it, in Greek words
	 */
	public static String simpleNumericExprToWords(String str){
		String outputString= new String("");
		String charsToWordsString= new String("");
		String words[];
		int defaultLength=str.length();


		//if the string is empty, return ""
		if(defaultLength==0) return "";

		//if the length of the string is 1, it is a single number, so I just return the word expression of it
		else if(defaultLength==1) return CharactersContainer.numberToGreekWord(str.charAt(0));


		//else if it's length is bigger than 1
		for(int i=0;i<defaultLength;i++){
			if(Character.isDigit(str.charAt(i)) || str.charAt(i)=='.' )
				charsToWordsString+=str.charAt(i);
			else
				charsToWordsString+=' '+CharactersContainer.symbolToMathMeaning(str.charAt(i))+' ';
		}

		words=splitWords(charsToWordsString);
		for(String s:words) {
			if(Character.isDigit(s.charAt(0))) outputString+=numberToWords(s)+' ';
			else outputString+=s+' ';
		}

		//remove the last character that is always a space
		return outputString.substring(0,outputString.length()-1);
	}



	/*
	 * This function gets a number as String, which might have dots in it (no commas), and converts it in Greek words
	 */
	private static String numberToWords(String number){
		int length=number.length();
		ArrayList<Integer> dotsIndexes = new ArrayList<Integer>();
		String finalNumber="";

		if(length==0) return "";

		//if the number starts with 0 or with no number, say the numbers and the dots one by one and return the result
		if(number.charAt(0)=='0' || !Character.isDigit(number.charAt(0))){
			String result="";
			String tempString="";
			for(int i=0;i<length;i++){
				tempString=CharactersContainer.numberToGreekWord(number.charAt(i));
				if(tempString.isEmpty() && number.charAt(i)=='.'){
					tempString="τελεία";
				}
				result+=tempString+' ';
			}
			return result.substring(0,result.length()-1);
		}


		//find all the indexes of the dots, and add them in the list
		for(int i=0;i<length;i++){ if(number.charAt(i)=='.') dotsIndexes.add(i);}

		//if there are no dots in the number, just convert it and return it
		if(dotsIndexes.size()==0) return NumberToWordsConverter.numberStringToGreekWords(number);

		//if there is only one dot, check if it is integer or float
		else if (dotsIndexes.size()==1){
			int index=dotsIndexes.get(0);

			//check if it is an integer
			if(index>=1 && index<=3 && length-index==4){
				finalNumber=number.substring(0,index)+number.substring(index+1,length);
				return NumberToWordsConverter.numberStringToGreekWords(finalNumber);
			}

			//check if it a valid float
			else if(index>0 && index<length-1){
				String leftSide=number.substring(0,index);
				String rightSide=number.substring(index+1,length);
				String leftSideWords="",rightSideWords="";

				//convert the left side of the number
				leftSideWords=NumberToWordsConverter.numberStringToGreekWords(leftSide);
				rightSideWords=numberToWords(rightSide);

				return leftSideWords+" κόμμα "+rightSideWords;
			}

			else {
				finalNumber = number.replace(".", "");
				return NumberToWordsConverter.numberStringToGreekWords(finalNumber);
			}

		}

		//if the number has more than two dots
		else{
			int firstIndex=dotsIndexes.get(0);
			int lastIndex=dotsIndexes.get(dotsIndexes.size()-1);
			boolean dotsOK=true;

			//check if the dots in the middle of the number have 4 letters distance
			for(int i=1;i<dotsIndexes.size();i++){
				if(dotsIndexes.get(i)-dotsIndexes.get(i-1)!=4) {
					dotsOK=false;
					break;
				}
			}

			//check if it is an integer
			if(firstIndex>=1 && firstIndex<=3 && length-lastIndex==4 && dotsOK){
				finalNumber = number.replace(".", "");
				return NumberToWordsConverter.numberStringToGreekWords(finalNumber);
			}

			//check if it is a float
			else if (firstIndex>=1 && firstIndex<=3 && length-lastIndex!=4 && dotsOK){
				String leftSide=number.substring(0,lastIndex);
				leftSide= leftSide.replace(".", "");
				String rightSide=number.substring(lastIndex+1,length);
				String leftSideWords="",rightSideWords="";

				//convert the left side of the number
				leftSideWords=NumberToWordsConverter.numberStringToGreekWords(leftSide);
				rightSideWords=numberToWords(rightSide);


				return leftSideWords+" κόμμα "+rightSideWords;
			}

			//if it is a bad expression, replace the dots with spaces and convert each number alone
			else{
					String[] numbers=splitWords(number.replace(".", " "));
					String outputString="";
					for(String s:numbers) outputString+=NumberToWordsConverter.numberStringToGreekWords(s)+' ';
					return outputString.substring(0, outputString.length()-1);
			}

		}
	}


}//end of class!
