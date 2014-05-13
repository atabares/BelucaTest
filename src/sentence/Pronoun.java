package sentence;

import generator.Word;


/**
 * <p>
 * Noun class that extends Word functionalities.
 * </p>
 * <p>
 * Copyright (c) 2014<BR>
 * Created on: 12/05/2014<BR>
 * 
 * @author atabares
 * </p>
 */
public class Pronoun extends Word {
	
	public Pronoun() {
	}
	
	public Pronoun(String line) {
		this.fillWords(line);
		this.fillRules(line);
	}
}
