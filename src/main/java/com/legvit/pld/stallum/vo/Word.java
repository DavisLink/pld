package com.legvit.pld.stallum.vo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.legvit.pld.stallum.service.Match;

public class Word {
	
	private String word;
	private boolean found100;
	private boolean inverse;
	private int position;
	private double maxPercentage;
	private double realPercentage;
	private double score;
	private String temp;
	private List<Match> matchList;
	private int matchPosition;
	private int matchPointer;
	
	public Word(String word, int position) {
		this.word = word.toLowerCase();
		this.position = position;
		found100 = false;
		inverse = false;
		score = 0d;
		maxPercentage = 0d;
		realPercentage = 0d;
		matchList = new ArrayList<Match>();
		matchList.add(new Match("0", "0", ""));
	}
	
	public Word(Word token){
		word = token.getWord().toLowerCase();
		position = token.getPosition();
		found100 = false;
		inverse = false;
		score = 0d;
		maxPercentage = 0d;
		realPercentage = 0d;
	}
	
	public void reverse(int position){
		StringBuffer str = new StringBuffer(word.substring(position));
		temp = word;
		word = str.reverse().toString();
		inverse = true;
	}
	
	public void reverse(){
		StringBuffer str = new StringBuffer(word);
		temp = word;
		word = str.reverse().toString();
		inverse = true;
	}
	
	public void returnToNormal(){
		if(inverse){
			word = temp;
			inverse = false;
		}
	}
	
	public void addMatch(Match match){
		matchList.add(match);
	}
	
	@SuppressWarnings("unchecked")
	public void sortMatchList(){
		Collections.sort(matchList);
		matchPointer = matchList.size() - 1;
		// Imprime la lista
		/*System.out.println("---------------" + word + "(" + position + ")---------------------");
		for(int i=0;i<matchList.size();i++) {
            System.out.println(matchList.get(i).getPercentage() + ", " + matchList.get(i).getPosition());
        }*/
	}
	
	public void decreaseMatchPonter(){
		//System.out.println("Decrementa el matchPointer de " + word + "(" + position + "):" + matchPosition + ", " + maxPercentage);
		matchPointer--;
                
		matchPosition = Integer.parseInt(matchList.get(matchPointer).getPosition());
		maxPercentage = Double.parseDouble(matchList.get(matchPointer).getPercentage());
		//System.out.println("queda en: " + matchPosition + ", " + maxPercentage);
	}

	public String getWord() {
		return word;
	}

	public boolean isFound100() {
		return found100;
	}

	public void setFound100(boolean found100) {
		this.found100 = found100;
	}

	public int getPosition() {
		return position;
	}
	
	public int getLength(){
		return word.length();
	}
	
	public void setMaxPercentage(double percentage, int matchPosition){
		if(percentage >= this.maxPercentage){
			this.maxPercentage = percentage;
			setMatchPosition(matchPosition);
		}
		this.realPercentage = percentage;
	}
	
	public double getMaxPercentage(){
		return maxPercentage;
	}
	
	public double getRealPercentage(){
		return realPercentage;
	}
	
	public void setScore(double score){
		if(score > this.score)
			this.score = score;
	}
	
	public double getScore(){
		return score;
	}
	
	public void setMatchPosition(int matchPosition){
		this.matchPosition = matchPosition;
	}
	
	public int getMatchPosition(){
		return matchPosition;
	}
}
