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
		
		for (int i=0; i<lineRules.size()-1; i++) {
			try {
//				String sentencePartName = Character.toUpperCase(((String) lineRules.get(i).toLowerCase()).charAt(0))
//						+ lineRules.get(i).toLowerCase().substring(1);
				String sentencePartName = lineRules.get(i);
				
//				Class<?> wordClass = Class.forName("sentence." + sentencePartName);
				Class<?> wordClass = Class.forName("generator.Word");
				Object wordInstance = null;
				
				if (sentencePartName.equalsIgnoreCase("ADJECTIVE")) {
					wordInstance = wordClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {adjective});
				} else if (sentencePartName.equalsIgnoreCase("NOUN")) {
					wordInstance = wordClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {noun});
				} else if (sentencePartName.equalsIgnoreCase("PRONOUN")) {
					wordInstance = wordClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {pronoun});
				} else if (sentencePartName.equalsIgnoreCase("VERB")) {
					wordInstance = wordClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {verb});
				} else if (sentencePartName.equalsIgnoreCase("PREPOSITION")) {
					wordInstance = wordClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {preposition});
				}
				
				Method wordMethod = wordClass.getMethod("getWord", null);
				line = line.concat((String) wordMethod.invoke(wordInstance, null));
				
				Method ruleMethod = wordClass.getMethod("getRule", null);
				String rule = "";
				rule = (String) ruleMethod.invoke(wordInstance, null);
				
				while (!rule.equalsIgnoreCase("END")) {
//					String recursiveSentencePartName = Character.toUpperCase((rule.toLowerCase()).charAt(0))
//							+ rule.toLowerCase().substring(1);
					String recursiveSentencePartName = rule;
		
//					Class<?> ruleClass = Class.forName("sentence." + recursiveSentencePartName);
					Class<?> ruleClass = Class.forName("generator.Word");
					Object recursiveWordInstance = null;
					
					if (recursiveSentencePartName.equalsIgnoreCase("ADJECTIVE")) {
						recursiveWordInstance = ruleClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {adjective});
					} else if (recursiveSentencePartName.equalsIgnoreCase("NOUN")) {
						recursiveWordInstance = ruleClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {noun});
					} else if (recursiveSentencePartName.equalsIgnoreCase("PRONOUN")) {
						recursiveWordInstance = ruleClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {pronoun});
					} else if (recursiveSentencePartName.equalsIgnoreCase("VERB")) {
						recursiveWordInstance = ruleClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {verb});
					} else if (recursiveSentencePartName.equalsIgnoreCase("PREPOSITION")) {
						recursiveWordInstance = ruleClass.getConstructor(new Class[] {String.class}).newInstance(new Object[] {preposition});
					}
					
					Method recursiveWordMethod = ruleClass.getMethod("getWord", null);
					line = line.concat((String) recursiveWordMethod.invoke(recursiveWordInstance, null));
					
					Method recursiveRuleMethod = ruleClass.getMethod("getRule", null);
					rule = (String) recursiveRuleMethod.invoke(recursiveWordInstance, null);
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println(e.getCause());
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		line = line.concat("\n");
		
		return line;
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
