package org.wintrisstech.erikc.trigrams;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TrigramTree {

    private int total = 0;
    private static Random random = new Random();

    private class Node {
	private int freq = 1;
	private Token token;

	Node(Token t) {
	    this.token = t;
	}
    }

    private Map<Token, Node> map = new HashMap<Token, Node>();

    public void addThird(Token token) {
	total++;
	Node n = map.get(token);
	if (n != null) {
	    n.freq++;
	} else {
	    map.put(token, new Node(token));
	}
    }

    public Token nextToken() {
	int index = random.nextInt(total);
	for (Node n : map.values()) {
	    if (n.freq > index)
		return n.token;
	    index -= n.freq;
	}
	return null;
    }

}
