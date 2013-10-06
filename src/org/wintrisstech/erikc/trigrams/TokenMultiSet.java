package org.wintrisstech.erikc.trigrams;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Each instance of this class represents a multiset of tokens.
 * 
 * @author Erik Colban &copy; 2013
 * 
 */
public class TokenMultiSet {

    private int total = 0;
    private static Random random = new Random(123456L);

    private Map<Token, Integer> map = new HashMap<Token, Integer>();

    /**
     * Adds a token to the multiset.
     * 
     * @param token
     *            the token to add
     */
    public void add(Token token) {
	total++;
	Integer n = map.get(token);
	if (n == null) {
	    map.put(token, Integer.valueOf(1));
	} else {
	    map.put(token, Integer.valueOf(n.intValue() + 1));
	}

    }

    /**
     * Gets a random token from the set such that the probability of getting
     * each token is proportional to the token's multiplicity.
     * 
     * @return a token
     */
    public Token nextToken() {
	int index = random.nextInt(total);
	for (Token t : map.keySet()) {
	    int n = map.get(t).intValue();
	    if (n < index) {
		index -= n;
	    } else {
		return t;
	    }
	}
	return null;
    }

}
