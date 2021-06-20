
package com.example.androidtts;


public class GreekPhoneme {
	private String phoneme;
	private char simplePhoneme;

	public GreekPhoneme() {
		phoneme= "";
		simplePhoneme=' ';
	}

	public GreekPhoneme(String phoneme, char simplePhoneme ) {
		this.phoneme=phoneme;
		this.simplePhoneme= simplePhoneme;
	}

	public void setPhoneme(String phoneme) {
		this.phoneme = phoneme;
	}

	public void setSimplePhoneme(char simplePhoneme) {
		this.simplePhoneme = simplePhoneme;
	}

	public String getPhoneme() {
		return phoneme;
	}

	public char getSimplePhoneme() {
		return simplePhoneme;
	}

}











