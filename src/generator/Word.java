package generator;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * <p>
 * Class that provides random words and rules, and methods to build a poem line.
 * </p>
 * <p>
 * Copyright (c) 2014<BR>
 * Created on: 12/05/2014<BR>
 * 
 * @author atabares
 * </p>
 */
public class Word {
	/** Words list. */
	List<String> words = new ArrayList<String>();
	/** Rules list. */
	List<String> rules = new ArrayList<String>();
	
	
	/**
	 * Default constructor.
	 */
	public Word() {
	}
	
	/**
	 * Constructor that initialize words and rules list.
	 * @param line
	 */
	public Word(String line) {
		this.fillWords(line);
		this.fillRules(line);
	}
	
	/**
	 * Fills the words list.
	 * @param line
	 */
	public void fillWords(String line) {
		String st = line.substring(line.indexOf(":") + 1, line.indexOf("<")).trim();
		
		StringTokenizer stt = new StringTokenizer(st, "|", false);
		
		while (stt.hasMoreTokens()) {
			this.words.add(stt.nextToken().toString());
		}
	}
	
	/**
	 * Fills the rules list.
	 * @param line
	 */
	public void fillRules(String line) {
		String st = line.substring(line.indexOf("<"), line.length()).trim();
		
		StringTokenizer stt = new StringTokenizer(st, "|", false);
		String rule;
		
		while (stt.hasMoreTokens()) {
			rule = stt.nextToken().toString();
			
			if (rule.contains("$")) {
				this.rules.add(rule.substring(1));
			} else {
				this.rules.add(rule.replaceAll("<", "").replaceAll(">", ""));
			}
		}
	}
	
	/**
	 * Get a random word from words list.
	 * @return A word.
	 */
	public String getWord() {
		String word = this.words.get((int) Math.floor(Math.random() * this.words.size())).concat(" ");
		
		return word;
	}
	
	/**
	 * Get a random rule from rules list.
	 * @return A rule.
	 */
	public String getRule() {
		String rule = this.rules.get((int) Math.floor(Math.random() * this.rules.size()));
		
		return rule;
	}
}
