package com.legvit.pld.stallum.service;

@SuppressWarnings("rawtypes")
public class Match implements Comparable{
	
	private String position;
	private String percentage;
	private String word;
	
	public Match(String position, String percentage, String word){
		this.position = position;
		this.percentage = percentage;
		this.word = word;
	}
	
	public Match(){
		
	}
	
	@Override
	public int compareTo(Object o){
		Match match = (Match)o;
		int a = (int) Double.parseDouble(this.percentage);
		int b = (int) Double.parseDouble(match.percentage);
		return a - b;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getPercentage() {
		return percentage;
	}

	public void setPercentage(String percentage) {
		this.percentage = percentage;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

}
