package com.legvit.pld.stallum.vo;

import java.util.ArrayList;
import java.util.List;

public class Name {
	
	private final static String WSPACE = " ";
	
	private String name;
        private String originalName;
	private int length;
	private List<Word> tokens;
	private boolean articles = false;
	
	public Name(String name){
		int contador;
		String[] tokens;
		articles = false;
		length = 0;
                originalName = name;
		//this.name = removeArticles(name.toLowerCase());
                this.name = name.toLowerCase();
		tokens = this.name.split(WSPACE);
		this.tokens = new ArrayList<Word>();
		contador = 1;
		for(String token : tokens){
			if(!token.equals("")){
				this.tokens.add(new Word(token, contador));
				length += token.length();
				contador++;
			}
		}
	}
	
	// Junta los artículos con la palabra a la derecha
	public String removeArticles(String name) {
		String result = "";
		String tokens[] = name.split(" ");
		int length = tokens.length;
		result += tokens[length-1];
		for(int i = length-2; i >= 0; i--){
			if(!tokens[i].equalsIgnoreCase("de") &&
			   !tokens[i].equalsIgnoreCase("el") &&
			   !tokens[i].equalsIgnoreCase("los") &&
			   !tokens[i].equalsIgnoreCase("la") &&
			   !tokens[i].equalsIgnoreCase("las") &&
			   !tokens[i].equalsIgnoreCase("un") &&
			   !tokens[i].equalsIgnoreCase("una") &&
			   !tokens[i].equalsIgnoreCase("de") &&
			   !tokens[i].equalsIgnoreCase("del") &&
			   !tokens[i].equalsIgnoreCase("a") &&
			   !tokens[i].equalsIgnoreCase("al") &&
			   !tokens[i].equalsIgnoreCase("un") &&
			   !tokens[i].equalsIgnoreCase("y")
			){
				result = " " + result;
			}
			result = tokens[i] + result;
		}
		if(!name.equalsIgnoreCase(result)){
			articles = true;
		}
		return result;
	}

	public String getName() {
		return name;
	}
        
        public String getOriginalName() {
		return originalName;
	}
	
	public int getLength(){
		return length;
	}

	public List<Word> getTokens() {
		return tokens;
	}
	
	public boolean hasArticles(){
		return articles;
	}
	
	public void setArticles(boolean articles){
		this.articles = articles;
	}

}
