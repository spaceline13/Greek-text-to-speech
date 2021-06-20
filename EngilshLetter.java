
package com.example.androidtts;


public class EnglishLetter {
	private char letter;
	private String toGreekPhoneme;
	private String toGreekLetters;


	public EnglishLetter() {
		letter=' ';
		toGreekPhoneme= "";
		toGreekLetters= "";
	}

	public EnglishLetter(char letter,String toGreekPhoneme,String toGreekLetters) {
		this.letter=letter;
		this.toGreekPhoneme= toGreekPhoneme;
		this.toGreekLetters= toGreekLetters;
	}

	public void setLetter(char letter) {
		this.letter = letter;
	}

	public void setToGreekLetters(String toGreekLetters) {
		this.toGreekLetters = toGreekLetters;
	}

	public void setToGreekPhoneme(String toGreekPhoneme) {
		this.toGreekPhoneme = toGreekPhoneme;
	}

	public char getLetter() {
		return letter;
	}

	public String getToGreekLetters() {
		return toGreekLetters;
	}

	public String getToGreekPhoneme() {
		return toGreekPhoneme;
	}

}





