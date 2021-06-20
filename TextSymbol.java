
package com.example.androidtts;


public class TextSymbol {
	private char symbol;
	private String symbolToWords;
	private String mathMeaning;

	public TextSymbol() {
		symbol=' ';
		symbolToWords= new String("empty");
		mathMeaning= new String("empty");
	}

	public TextSymbol(char symbol, String symbolToWords, String mathMeaning) {
		this.symbol=symbol;
		this.symbolToWords= new String(symbolToWords);
		this.mathMeaning= new String(mathMeaning);
	}


	public void setSymbol(char symbol) {
		this.symbol = symbol;
	}

	public void setSymbolToWords(String symbolToWords) {
		this.symbolToWords = symbolToWords;
	}

	public void setMathMeaning(String mathMeaning) {
		this.mathMeaning = mathMeaning;
	}

	public char getSymbol() {
		return symbol;
	}

	public String getSymbolToWords() {
		return symbolToWords;
	}

	public String getMathMeaning() {
		return mathMeaning;
	}


}

