package generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 * <p>
 * Class that read poem rules from txt file and then generate a random poem.
 * </p>
 * <p>
 * Copyright (c) 2014<BR>
 * Created on: 12/05/2014<BR>
 * 
 * @author atabares
 * </p>
 */
public class Poem {
	
	/** Adjective definition line. */
	static String adjective;
	/** Noun definition line. */
	static String noun;
	/** Pronoun definition line. */
	static String pronoun;
	/** Verb definition line. */
	static String verb;
	/** Preposition definition line. */
	static String preposition;
	
	/** The poem. */
	static String poem = new String();
	/** Lines counter. */
	static int linesCounter = 0;
	/** Line rules. */
	static List<String> lineRules = new ArrayList<String>();
	
	
	/**
	 * Generate a poem based on given grammatical rules.
	 * @return The poem.
	 */
	private static String generatePoem () {
		for (int i=0; i<linesCounter; i++) {
			poem = poem.concat(generateLine()).concat("\n");
		}
		
		return poem;
	}
	
	/**
	 * Generate a poem line based on given grammatical rules.
	 * @return A poem line.
	 */
	private static String generateLine () {
		String line = "";
		String rule = "";
		
		for (int i=0; i<lineRules.size()-1; i++) {
			try {	
				String sentencePartName = lineRules.get(i);
				
				Word wordClass = instClass(sentencePartName);
				line = line.concat((String) ((Word) wordClass).getWord());
				
				rule = (String) wordClass.getRule();
				
				while (!rule.equalsIgnoreCase("END")) {
					String recursiveSentencePartName = rule;
		
					Word ruleClass = instClass(recursiveSentencePartName);
					line = line.concat((String) ((Word) ruleClass).getWord());
					
					rule = (String) ruleClass.getRule();
				}
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		line = line.concat("\n");
		
		return line;
	}
	
	/**
	 * Create a new Word object.
	 * @param sentencePartName
	 * @return A new Word object.
	 */
	public static Word instClass (String sentencePartName) {
		Word wordClass = null;
		
		if (sentencePartName.equalsIgnoreCase("ADJECTIVE")) {
			wordClass = new Word(adjective);
		} else if (sentencePartName.equalsIgnoreCase("NOUN")) {
			wordClass = new Word(noun);
		} else if (sentencePartName.equalsIgnoreCase("PRONOUN")) {
			wordClass = new Word(pronoun);
		} else if (sentencePartName.equalsIgnoreCase("VERB")) {
			wordClass = new Word(verb);
		} else if (sentencePartName.equalsIgnoreCase("PREPOSITION")) {
			wordClass = new Word(preposition);
		}
		
		return wordClass;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File poemFile = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {
			poemFile = new File("E:\\PoemRules.txt");
			fr = new FileReader(poemFile);
			br = new BufferedReader(fr);
			String line;
			
			while ((line = br.readLine()) != null) {
				if (line.toUpperCase().startsWith(new String("POEM"))) {
					String st = line.substring(line.indexOf(":") + 1).trim();
					
					StringTokenizer stt = new StringTokenizer(st, "> <", false);
					linesCounter = stt.countTokens();
				} else if (line.toUpperCase().startsWith(new String("LINE"))) {
					String st = line.substring(line.indexOf(":") + 1).trim();
					
					StringTokenizer stt = new StringTokenizer(st, "|", false);
					String rule;
					
					while (stt.hasMoreTokens()) {
						rule = stt.nextToken().toString();
						
						if (rule.contains("$")) {
							lineRules.add(rule.substring(1));
						} else {
							lineRules.add(rule.replaceAll("<", "").replaceAll(">", ""));
						}
					}
				} else if (line.toUpperCase().startsWith(new String("ADJECTIVE"))) {
					adjective = line;
				} else if (line.toUpperCase().startsWith(new String("NOUN"))) {
					noun = line;
				} else if (line.toUpperCase().startsWith(new String("PRONOUN"))) {
					pronoun = line;
				} else if (line.toUpperCase().startsWith(new String("VERB"))) {
					verb = line;
				} else if (line.toUpperCase().startsWith(new String("PREPOSITION"))) {
					preposition = line;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (null != fr) {
					fr.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		System.out.println("THE POEM:\n");
		System.out.println(generatePoem());
	}
}
