package com.example.androidtts;

import java.util.ArrayList;

import android.content.Context;


/*
 * This is the class that does all the text analysis of the TTS system.
 * It uses other class objects to do many jobs like searching arrays, converting expressions etc.
 * The function that outputs the final form of the analyzed text is analyzeStringToSyllables.
 */
public class GreekTextAnalyzer {

	private String[] sentencesArray;
	private String[] analyzedSentences;
	private String[] exprSentences;
	private String[] grammarSentences;
	private String[] phonemesSentences;
	private String[] syllablesSentences;

	private SyllablesManager sylManager;

	//Default Constructor, just initializing all variables
	public GreekTextAnalyzer(Context myContext) {
			sylManager= new SyllablesManager(myContext);
			sentencesArray= new String[1];
			analyzedSentences= new String[1];
			exprSentences= new String[1];
			grammarSentences= new String[1];
			phonemesSentences= new String[1];
			syllablesSentences= new String[1];

	}



	// MAIN!
	public static void main(String[] args) {
		//GreekTextAnalyzer analyzer= new GreekTextAnalyzer();
		//String testString="Η ARM είναι μια αρχιτεκτονική συνόλου εντολών RISC των 32-bit, η οποία έχει αναπτυχθεί από την ARM Holdings. ";
		//System.out.println(testString);
		//System.out.println(analyzer.analyzeTextToSyllables(testString)[0]);

	}



	/*
	 * This function accepts a sentence as a String and separates all the expressions inside it
	 * It returns a String which is the sentence with all the expressions separated by space
	 * Also the , character is going to have spaces around if it used as regular comma
	 * An example is "a234" which is going to be "a 234" and "hello, how are you;" that transforms into "hello , how are you;"
	 * The math expressions are staying in the string only if they are done between numbers
	 * Every character that is not in a math expression is going to be alone.
	 */
	public String splitExpressions(String str){
		int stringSize= str.length();
		int index,prevIndex,nextIndex;
		char currentChar,nextChar,prevChar,lastChar;

		if(stringSize==0) return "";

		//add a space at the start of the string, so I will be able to use the prevIndex and prevChar without checking is the index>0
		str=' '+str;
		stringSize++;

		//check the last character of the sentence and if it is not . or ; or ! add a . at the end of the sentence
		lastChar=str.charAt(stringSize-1);
		if(lastChar!='.' && lastChar!=';' && lastChar!='!'){
			lastChar='.';
			str=str+".";
			stringSize++;
		}


		//The last character is always an ending character so it is not counted
		for(index=1;index<stringSize-1;index++){
			prevIndex=index-1;
			nextIndex=index+1;
			currentChar=str.charAt(index);
			nextChar=str.charAt(nextIndex);
			prevChar=str.charAt(prevIndex);

			//if it is space or a letter do nothing
			//I check this first for optimization and less searches from CharactersContainer
			if(currentChar==' ' || CharactersContainer.isLetter(currentChar)){
					//do nothing!
			}

			//if it is a number
			else if(Character.isDigit(currentChar)){
				//if the previous character is a letter (English or Greek), add a space before it
				if(CharactersContainer.isLetter(prevChar)){
						//add a space before it
						str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
						stringSize++;
						index++;
						nextIndex++;
						prevIndex++;
						currentChar=str.charAt(index);
						nextChar=str.charAt(nextIndex);
						prevChar=str.charAt(prevIndex);
				}

				//the cases that I want to keep the expression 1ο or 5η
				if(nextIndex<stringSize-1 && !CharactersContainer.isGreekLetter(str.charAt(nextIndex+1))
						&& (nextChar=='ο' || nextChar=='ό' || nextChar=='η' || nextChar=='ή' || nextChar=='α'|| nextChar=='ά')){
					//if there is a character right of it and space left of it, add a space after the comma

					//add space after it
					if(str.charAt(nextIndex+1)!=' ') {
						str = str.substring(0, nextIndex+1) + ' ' + str.substring(nextIndex+1, stringSize);
						stringSize++;
					}
					//skip the next 2 characters
					index+=2;
				}

				else if(nextIndex<stringSize-3 && !CharactersContainer.isGreekLetter(str.charAt(nextIndex+3))
						&& (str.substring(nextIndex, nextIndex+3).equals("ους") ||  str.substring(nextIndex, nextIndex+3).equals("ούς"))){
					//add space after the next 3 letters
					if(str.charAt(nextIndex+3)!=' ') {
						str = str.substring(0, nextIndex+3) + ' ' + str.substring(nextIndex+3, stringSize);
						stringSize++;
					}
					//skip the next 3 characters
					index+=4;
				}


				//the cases that I want to keep the expression 1ος or 5ες or 16οι
				else if(nextIndex<stringSize-2 && !CharactersContainer.isGreekLetter(str.charAt(nextIndex+2))
						&& ( str.substring(nextIndex, nextIndex+2).equals("ος") || str.substring(nextIndex, nextIndex+2).equals("ός")
								||   str.substring(nextIndex, nextIndex+2).equals("οι") ||  str.substring(nextIndex, nextIndex+2).equals("οί")
								||   str.substring(nextIndex, nextIndex+2).equals("ες") ||  str.substring(nextIndex, nextIndex+2).equals("ές")
								||   str.substring(nextIndex, nextIndex+2).equals("ου") ||  str.substring(nextIndex, nextIndex+2).equals("ού")
								||   str.substring(nextIndex, nextIndex+2).equals("ων") ||  str.substring(nextIndex, nextIndex+2).equals("ών")
								||   str.substring(nextIndex, nextIndex+2).equals("ης") ||  str.substring(nextIndex, nextIndex+2).equals("ής"))){

					//add space after the next 2 letters
					if(str.charAt(nextIndex+2)!=' ') {
						str = str.substring(0, nextIndex+2) + ' ' + str.substring(nextIndex+2, stringSize);
						stringSize++;
					}
					//skip the next 3 characters
					index+=3;
				}



				//if the next character is a letter, add a space after it
				else if(CharactersContainer.isLetter(nextChar)){
					str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
					stringSize++;
				}
			}

			//if it is another character
			else{
					switch(currentChar){

					//comma
					//Leave it as it is if it is between numbers else separate it from the left and right word if they exist
					case ',':{

								//if it has not a number right and left of it we have to do some things..
								if(!(Character.isDigit(prevChar) && Character.isDigit(nextChar))){
									 if(prevChar!=' '){
											//add a space before it
											str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
											stringSize++;
										}
										//if there is a character right of it and space left of it, add a space after the comma
										if(nextChar!=' ') {
											//add space after it
											str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
											stringSize++;
										}
								}
								break;
					}

					//if the next character is not a number or a letter, I replace the next character with space
					case '.':{

							//if it is not between letters, replace it with space
							if( CharactersContainer.isLetter(nextChar)){
								if(!CharactersContainer.isLetter(prevChar)){
										//replace with space
										str = str.substring(0, index) + " " + str.substring(nextIndex, stringSize);
									}
							}

							//if it is not between numbers, add space after it
							else if( Character.isDigit(nextChar) ){
								if( !Character.isDigit(prevChar) ){
									if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
								}
							}
							//if the next character is anything else but space
							else if(nextChar!=' '){
								//add space after it
								str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
								stringSize++;
								index++;
							}

							break;
					}


					//The character -
					//I keep after a number or before a number
					//or else I separate it from the left expression
					case '-':{

							//if the next character is number
							 if(Character.isDigit(nextChar)){
								 	if(prevChar!=' '){
										//add a space before it
										str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
										stringSize++;
										index++;
									}

							  }

							 else if(CharactersContainer.isLetter(prevChar) || (CharactersContainer.isLetter(nextChar))){
								 	//replace with space
									str = str.substring(0, index) + " " + str.substring(nextIndex, stringSize);
							 }

							 //else leave it alone (add spaces around it)
							 else{
								//add space before it
								 if(prevChar!=' '){
										//add a space before it
										str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
										stringSize++;
									}
									//add space after it
									if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
							}
						break;
					}

					//I keep it if it is before a number
					//or else I separate it from all sides (add spaces around it)
					case '!':case '+':case '#':case '√':{

							//if the next character is number
							 if(Character.isDigit(nextChar)){
								//add a space before it
									str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
									stringSize++;
									index++;
							  }

							 //else leave it alone (add spaces around it)
							 else{
								 if(prevChar!=' '){
										//add a space before it
										str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
										stringSize++;
									}
									//if there is a character right of it and space left of it, add a space after the comma
									if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
							 }
						break;
					}


					//characters that I don't want to pronounce and I replace them with space
					//I could not write this code because of the default in this switch but it is good for performance
					case '·':case ';':case '"':case '\'':case '`':case '_':case ')':case ']':case '}':case '(':case '{':case '[':{
							//replace with space
							str = str.substring(0, index) + " " + str.substring(nextIndex, stringSize);
					}


					//the character =
					//separate it from the left part only if the previous character is not < or > or space
					//also add a space after it
					case '=':{

							//add a space before and after it if the previous character is not > or <
							if(prevChar!='>' && prevChar!='<'  && prevChar!=' '){
								//add a space before it
								str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
								stringSize++;
								index++;
							}
							if(nextChar!=' ') {
								//add space after it
								str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
								stringSize++;
							}
						break;
					}


					//I keep them as they are if they are between numbers, or else I add spaces around them
					case '*':case '/':case '\\':case '^':case ':':case '≪':case '≫':{

								//if it is not between numbers
								if(!(Character.isDigit(prevChar) && Character.isDigit(nextChar))){
										//leave it alone
										if(prevChar!=' '){
											//add a space before it
											str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
											stringSize++;
										}

										if(nextChar!=' ') {
											//add space after it
											str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
											stringSize++;
										}
									}
							break;
					}



					//characters $ € £
					//they have spaces around them unless they are in an expression like this
					//$12 £34 17245€
					case '€':case '$':case '£':{

							//if the next character is number
							 if(Character.isDigit(nextChar)){
								//add a space before it
									str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
									stringSize++;
									index++;
							  }

							//if the previous character is number
							 else if(Character.isDigit(prevChar)){
								 if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
							  }

							 //else leave it alone
							 else{
								 if(prevChar!=' '){
										//add a space before it
										str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
										stringSize++;
									}
									//if there is a character right of it and space left of it, add a space after the comma
									if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
							 }
						break;
					}


					//characters < and >
					//I keep them as they are if they are between numbers and if they are before the character =
					//I also add a space before the character and after the =, so it can be used as an abbreviation
					case '<': case'>':{

							//if it is not between numbers
							if(!(Character.isDigit(prevChar) && Character.isDigit(nextChar))){

								if(nextChar=='='){
										if(prevChar!=' '){
											//add a space before it
											str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
											stringSize++;
											index++;
											nextIndex++;
										}
										//add space after the =
										if(str.charAt(nextIndex+1)!=' '){
											str = str.substring(0, nextIndex+1) + " " + str.substring(nextIndex+1, stringSize);
											stringSize++;
											index+=2;
										}

								}
								//else leave it alone (add spaces around it)
								else {
									if(prevChar!=' '){
										//add a space before it
										str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
										stringSize++;
									}

									if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
								}
							}
							break;
					}


					//These characters have to be alone with spaces around them because the way they will be pronounced is standard
					case '?':case '%':case '×':case '÷':case '&':case '@':case '~':case '|':case '∞':
					case '≤':case '≥':case '±':case'∓':case '∫':case'≠':case '∑':case 'ƒ':case '؋':case '₫':case 'ლ':case '₵':
					case '¥':case '₪':case '₩':case '₡':case '₭':case 'ރ':case '₮':case '৲':case '৳':
					case '₦':case '₴':case '₲':case '₤':case '฿':case '₱':case '₣':case '圓':{

									//if there is a character left of it and space right of it, add a space before or after it
									if(prevChar!=' '){
										//add a space before it
										str = str.substring(0, index) + ' ' + str.substring(index, stringSize);
										stringSize++;
									}
									//if there is a character right of it and space left of it, add a space after the comma
									if(nextChar!=' ') {
										//add space after it
										str = str.substring(0, nextIndex) + " " + str.substring(nextIndex, stringSize);
										stringSize++;
									}
							break;
					}



					//any other character that I do not want to use
					default: {
									//replace with space
									str = str.substring(0, index) + " " + str.substring(nextIndex, stringSize);
								break;
					}//end of default

				}//end of switch
			}//end of else it is character
		}//end of for

		//the first character is going to a a space, so I remove it
		return str.substring(1,str.length());
	}


	/*	This function splits all words in a string and returns an array of strings with the words
 	 *  All spaces are being erased.
	 */
	public String[] splitWords(String str){
		ArrayList<String> wordsList= new ArrayList<String>();

		for(String s:str.split(" ")){
			if(!s.isEmpty()) wordsList.add(s);
		}

		return wordsList.toArray(new String[wordsList.size()]);
	}




	/*
	 * This function converts all the expressions of the sentence to a form in words only.
	 * The expression types are the word expression, the numerical expression, the characters and the abbreviations
	 */
	public String analyzeWords(String str){
		String outputString="";
		String analyzedWord="";
		String[] words;
		int defaultLength= str.length();
		char lastChar,currentChar;

		if(defaultLength==0) return "";

		/*
		* save the ending character of the sentence and add it back when the algorithm is done
		* if it is not . or ; or !, the lastChar is . and is added at the end of the sentence
		* if it is ; or !, I replace it with . so the program can analyze an abbreviation if it is at the end of the sentence,
		* like κ.α! which becomes και άλλα !
		*/
		lastChar=str.charAt(defaultLength-1);
		if(lastChar==';' || lastChar=='!'){
			str=str.substring(0,defaultLength-1)+'.';
		}
		else if(lastChar!='.'){
			lastChar='.';
			str=str+lastChar;
			defaultLength++;
		}

		words= splitWords(str);

		//I check all the words except the last one, because it will always be the ending character
		for(String word:words){

			//the analyzedWord gets initialized to "" and if it doesn't change, nothing or "" is being added to the outputString
			analyzedWord=new String("");

			//if the word is a single character or a single character with a . after it (if the sentence end with a single character)
			if(word.length()==1 || (word.length()==2 && word.endsWith("."))){

				currentChar=word.charAt(0);
				//check if it a comma first (it has better chance)
				if(currentChar==','){ analyzedWord=",";}

				//if it a number
				else if(Character.isDigit(currentChar)) analyzedWord=CharactersContainer.numberToGreekWord(currentChar);

				//if it is a letter, just save it into analyzedWord
				else if(CharactersContainer.isGreekLetter(currentChar) ){
					switch(currentChar){
						case 'β': case 'Β': analyzedWord="βήτα"; break;
						case 'γ': case 'Γ': analyzedWord="γάμα"; break;
						case 'δ': case 'Δ': analyzedWord="δέλτα"; break;
						case 'ζ': case 'Ζ': analyzedWord="ζήτα"; break;
						case 'θ': case 'Θ': analyzedWord="θήτα"; break;
						case 'κ': case 'Κ': analyzedWord="κάπα"; break;
						case 'λ': case 'Λ': analyzedWord="λάμδα"; break;
						case 'μ': case 'Μ': analyzedWord="μί"; break;
						case 'ν': case 'Ν': analyzedWord="νί"; break;
						case 'ξ': case 'Ξ': analyzedWord="ξί"; break;
						case 'π': case 'Π': analyzedWord="πί"; break;
						case 'ρ': case 'Ρ': analyzedWord="ρώ"; break;
						case 'σ': case 'Σ': analyzedWord="σίγμα"; break;
						case 'τ': case 'Τ': analyzedWord="τάφ"; break;
						case 'φ': case 'Φ': analyzedWord="φί"; break;
						case 'χ': case 'Χ': analyzedWord="χί"; break;
						case 'ψ': case 'Ψ': analyzedWord="ψί"; break;
						case 'ς': analyzedWord="σίγμα τελικό"; break;
						default:  analyzedWord=String.valueOf(currentChar); break;
					}
				}

				//if it is English letter
				else if(CharactersContainer.isEnglishLetter(currentChar)) analyzedWord= CharactersContainer.engLetterToGreekWord(currentChar);

				//if it is any other character
				else if(currentChar!='.') analyzedWord= CharactersContainer.symbolToWords(currentChar);

			}

			//if it has more than one characters
			else
			{
				//check if it is an abbreviation first
				//if it is it will be saved in the analyzedWord or else it will return ""
				analyzedWord=WordAnalyzer.analyzeAbbreviation(word);

				//if it is not an abbreviation, check for word and numeric expression
				if(analyzedWord.isEmpty()){
					/*
					 * if there is a . at the end of the word, remove it
					 * this is the case that the sentence ends with . and this character stays with the word
					 * If the sentence ended with ; or !, this character is removed from the start of this function.
					 * The character . just stays for the case that the sentence ends with an abbreviation
					*/
					if(word.charAt(word.length()-1)=='.') word=word.substring(0, word.length()-1);

					//if it is a word expression or a numeric expression, analyze it properly
					if(WordAnalyzer.isNumericExpression(word)) analyzedWord=WordAnalyzer.analyzeNumericExpression(word);
					//else, it is a word expression
					else analyzedWord=WordAnalyzer.analyzeWordExpression(word);

				}

			}//end of else
			outputString+=analyzedWord+' ';
		}//end of for(String s:words)

		//add the ending character I have saved from the start of the function
		return outputString+lastChar;
	}




	/*
	 * This function takes as input a string and, separates all the sentences inside it and creates an array of strings with all these sentences
	 * A sentence in Greek language is considered a part of text that ends with . or ! or ; and has space or a capital letter right after it
	 * or the last part of a text that ends with none of these characters, so this function puts a . just in  this case
	 * I also separate the sentences that end with : or · and if they have , after them like in this case
	 * "Παράδειγμα: Που πας;, ρώτησε εκείνη. Έξω!, απάντησε αυτός." Here we have 5 sentences
	 * If the input String is "", the output ArrayList will have just one empty String ""
	 */
	public String[] splitSentences(String str) {

		ArrayList<String> sentencesList = new ArrayList<String>();
		int index = 0,nextIndex=0, sentenceStartIndex = 0;
		int stringSize;
		char nextChar,currentChar,lastChar;

		//Replace all \n and \r from the string with space
		str = str.replaceAll("(\\r|\\n|\\r\\n)", " ");
		stringSize=str.length();


		// if the string str is empty, return an array of Strings with the first element ""
		if (stringSize == 0) {
			String []outputArray = new String[1];
			outputArray[0]=  new String("");
			return outputArray;
		}

		// If the text does not end with . or ; or ! add . so it becomes a regular sentence
		lastChar=str.charAt(stringSize - 1);
		if (lastChar != '.' && lastChar != ';' && lastChar != '!' && lastChar != '·' && lastChar != ':') {
				lastChar='.';
				str+=lastChar;
				stringSize++;
		}

		for(index=0;index<stringSize;index++) {
			currentChar=str.charAt(index);
			if(currentChar=='.' || currentChar==';' || currentChar=='!' || currentChar==':' || currentChar=='·'){

				// Check if we reached the end of the string
				if (index == stringSize - 1)  sentencesList.add(str.substring(sentenceStartIndex, stringSize));

				else{
					nextIndex=index+1;
					nextChar=str.charAt(nextIndex);

					// Check if after an ending character there is a , or . or ! or ; or : or · and delete it because it is not necessary
					// In this way this algorithm becomes smarter and it separates sentences even in occasions like this
					// "Παράδειγμα: Που πας;, ρώτησε εκείνη. Έξω!, απάντησε αυτός."
					if (nextChar==',' || nextChar=='.' || nextChar==';' || nextChar=='!' || nextChar=='·' || nextChar==':') {
							//replace the next of the i with space
							str = str.substring(0, nextIndex) + ' ' + str.substring(nextIndex+1);
							stringSize=str.length();
					}

					switch(nextChar){
						// Save the sentence in the ArrayList if the next character of
						// the index is space or capital letter
						case ' ':  {sentencesList.add(str.substring(sentenceStartIndex, index + 1));
									sentenceStartIndex = index + 2;
									break;}

						case 'Α':case 'Β':case 'Γ':case 'Δ':case 'Ε':case 'Ζ':case 'Η':case 'Θ':case 'Ι':case 'Κ':case 'Λ':case 'Μ':
						case 'Ν':case 'Ξ':case 'Ο':case 'Π':case 'Ρ':case 'Σ':case 'Τ':case 'Υ':case 'Φ':case 'Χ':case 'Ψ':case 'Ω':
						case 'Ά':case 'Έ':case 'Ή':case 'Ί':case 'Ό':case 'Ύ':case 'Ώ': case 'Ϋ': case 'Ϊ':
						case 'A':case 'B':case 'C':case 'D':case 'E':case 'F':case 'G':case 'H':case 'I':case 'J':case 'K':case 'L':
						case 'M':case 'N':case 'O':case 'P':case 'Q':case 'R':case 'S':case 'T':case 'U':case 'V':case 'W':case 'X':
						case 'Y':case 'Z':{
										sentencesList.add(str.substring(sentenceStartIndex, nextIndex));
										sentenceStartIndex = nextIndex;
										break;}

						default: break;

					}//end of switch
				}//end of else
			}//end of if
		}//end of for


		//Now we clear the sentences from useless characters and the list from the single characters
		int sentenceLength;
		for(int i=0;i<sentencesList.size();i++){
				sentenceLength=sentencesList.get(i).length();
				// Remove the spaces from the start of every sentence if they exist
				while (sentencesList.get(i).charAt(0) == ' ') {
					sentencesList.set(i,sentencesList.get(i).substring(1,sentenceLength));
					sentenceLength--;
				}

				// Remove the spaces from the end of every sentence if they exist
				while (sentencesList.get(i).charAt(sentenceLength-1) == ' ') {
					sentenceLength--;
					sentencesList.set(i,sentencesList.get(i).substring(0, sentenceLength));
				}

				if(sentenceLength>1){
						//if the sentence ends with : or · replace it with .
						if((sentencesList.get(i).charAt(sentenceLength-1)==':' || sentencesList.get(i).charAt(sentenceLength-1)=='·')){
							sentencesList.set(i,sentencesList.get(i).substring(0,sentenceLength-1)+'.');
						}
				}

				//if the sentence is a single character, remove it
				else{   sentencesList.remove(i);
						i--;
					}
		}//end of for


		//we are done! Convert the ArrayList to array and return it!
		return sentencesList.toArray(new String[sentencesList.size()]);

	}//end of splitSentences!




	//This function manages all the characters that are not pronounced as they are written
	//in Greek language the character υ is the only one that needs to be checked
	public String convertSpecialLetters(String str){
		int length=str.length();
		int prevIndex=0,nextIndex=0;
		char prevChar,nextChar,currChar;;
		for(int i=1;i<length-1;i++){
			prevIndex=i-1;
			nextIndex=i+1;
			currChar=str.charAt(i);
			prevChar=str.charAt(prevIndex);
			nextChar=str.charAt(nextIndex);

			if(currChar=='ύ' || currChar=='Ύ' || currChar=='υ' || currChar=='Υ'){
				//do this only if the previous character is one of those above
				if((prevChar=='ε' || prevChar=='α' || prevChar=='Α' || prevChar=='Ε')){
					//tone the previous character
					if(str.charAt(i)=='ύ' || str.charAt(i)=='Ύ'){
						str=str.substring(0, prevIndex) +CharactersContainer.toneTheGreekLetter(prevChar)+str.substring(i,length);
					}

					//replace with φ or β
					switch(nextChar){
						case 'θ':case 'κ':case 'ξ':case 'π':case 'σ':case 'τ':case 'φ':case 'χ':case 'ψ':
						case 'Θ':case 'Κ':case 'Ξ':case 'Π':case 'Σ':case 'Τ':case 'Φ':case 'Χ':case 'Ψ':case ' ':
						{ str=str.substring(0, i) +'φ'+str.substring(nextIndex); break;}

						default:{str=str.substring(0, i) +'β'+str.substring(nextIndex); break;}
					}
				}
			}
		}//end of if
		return str;

	}




	//This function converts all the phonemes of the sentence it accepts as input, to their simple form.
	public String convertLettersToPhonemes(String str){
		int length=str.length();
		if(length==0) return "";

		String outputString= "";
		int index,nextIndex;
		char currChar,nextChar,simplePhoneme;
		char lastChar=str.charAt(length-1);

		for(index=0;index<length-2;index++){
			simplePhoneme=' ';
			nextIndex=index+1;
			currChar=str.charAt(index);
			nextChar=str.charAt(nextIndex);

			//if the current character I am checking is space, I don't have to do anything
			if(currChar!=' ' && currChar!=','){
				//check for double letters first and if not found, the simplePhoneme variable will still be ' '
				if(nextChar!=' ') 	simplePhoneme=CharactersContainer.convertToSimplePhoneme(String.valueOf(currChar)+String.valueOf(nextChar));

				//if the double letter phoneme is found,simplePhoneme variable is not going to be ' ', so skip the next character
				if(simplePhoneme!=' ')  index++;
				//if the simplePhoneme variable is ' ', which means that the double letter phoneme was not found, check for single letter phoneme
				else simplePhoneme=CharactersContainer.convertToSimplePhoneme(String.valueOf(currChar));
			}

			//don't forget the character comma!
			else if(currChar==',') simplePhoneme=',';

			//add it into the outputString
			outputString+=simplePhoneme;
		}

		return outputString+lastChar;
	}




	/*
	 * This functions takes as input a sentence, splits the syllables of the words and adds a _ between them
	 * The sentence must be in the form that the tts system can understand, using the right characters.
	 */
	public String splitSyllables(String str){
		//initialize all variables
		int maxSyllableLength=4;
		String[] words;
		String space="SPACE";
		int wordLength;
		String outputString="";
		String syllable,splitWord,tempString;
		int strLength=str.length();
		int index;

		//save the character that is between the syllables
		char splitCharacter='-';

		if(strLength==0) return "";

		//save the last character and check if it is one of those we use . ! ;
		//if not, add a . at the end of the sentence
		char lastChar=str.charAt(strLength-1);
		if(lastChar=='.' || lastChar=='!' || lastChar==';') {
			str=str.substring(0,str.length()-1);
			strLength--;
		}
		else {
			lastChar='.';
		}

		words=splitWords(str);

		//Now we split the syllables of every word with the splitCharacter
		//the last word is always the ending character, so we don't use it
		for(int i=0;i<words.length;i++){
			wordLength=words[i].length();
			splitWord="";

			//if we find a ,
			if(words[i].equals(",")){
				if(outputString.length()>0) outputString=outputString.substring(0,outputString.length()-space.length())+',';
				 else outputString=","+splitCharacter;
			}

			//the only other case is a regular word
			else{
				for(index=0;index<wordLength;index++){
					//initialize the syllable and the indexes
					syllable="";

					for(int counter=maxSyllableLength;counter>0;counter--){
						if(index+counter<=wordLength) {
							tempString=words[i].substring(index,index+counter);
							if(sylManager.isSyllable(tempString)){
								syllable=tempString+splitCharacter;
								index+=counter-1;
								counter=-1;
							}
						}//end of for
					}// end of for

					//add it into the outputString
					splitWord+=syllable;
				}//end of for
				outputString+=splitCharacter+splitWord+space;
			}
		}//end of for

		//if the outputString length is>1, it means that there is a word in it and a splitCharacter at the start of it,
		//also a space at the end of it, which must be removed
		if(outputString.length()>1) return outputString.substring(1,outputString.length()-space.length())+lastChar;
		else return String.valueOf(lastChar);
	}




	/*
	 * This function is the final step in the test analysis of the TTS system.
	 * It takes as input the default text that must be analyzed, and outputs an array of sentences in a form that the audio program understands.
	 * It uses all the steps of the analysis and exports every sentences with the syllables split
	 */
	public String[] analyzeTextToDiph(String str){
		if(str.isEmpty()) {
			String outputStr[]= new String[1];
			outputStr[0]="";
			return outputStr;
		}

		int sentencesCount;

		sentencesArray= splitSentences(str);
		sentencesCount=sentencesArray.length;
		exprSentences= new String[sentencesCount];
		analyzedSentences= new String[sentencesCount];
		grammarSentences= new String[sentencesCount];
		phonemesSentences= new String[sentencesCount];
		syllablesSentences= new String[sentencesCount];

		for(int i=0;i<sentencesCount;i++){
			exprSentences[i]= splitExpressions(sentencesArray[i]);
			analyzedSentences[i]=analyzeWords(exprSentences[i]);
			grammarSentences[i]= convertSpecialLetters(analyzedSentences[i]);
			phonemesSentences[i]=convertLettersToPhonemes(grammarSentences[i]);
			syllablesSentences[i]=splitSyllables(phonemesSentences[i]);
		}
		return syllablesSentences;
	}


	/*
	 * This function gets a single sentence, it analyzes using all the levels of analysis
	 * and returns a sentence with all the syllables split
	 */
	public String analyzeSentToDiph(String str){
		if(str.isEmpty()) return "";

		String exprSentence=splitExpressions(str);
		String analyzedSentence=analyzeWords(exprSentence);
		String grammarSentence= convertSpecialLetters(analyzedSentence);
		String phonemesSentence=convertLettersToPhonemes(grammarSentence);
		String syllablesSentence=splitSyllables(phonemesSentence);

		return syllablesSentence;
	}


}//end of class!











