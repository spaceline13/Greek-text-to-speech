package com.example.androidtts;



public class Abbreviation {
	private String abbreviation, meaning;

	public Abbreviation() {
		abbreviation= "";
		meaning= "";
	}

	public Abbreviation(String abbreviation, String meaning) {
		this.abbreviation= abbreviation;
		this.meaning= meaning;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public void setMeaning(String meaning) {
		this.meaning = meaning;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public String getMeaning() {
		return meaning;
	}
}

