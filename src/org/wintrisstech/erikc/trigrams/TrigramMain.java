package org.wintrisstech.erikc.trigrams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * This class is the Mainclass in a project that generates random
 * "German looking" text based on trigram frequencies encountered in two large
 * German texts. A trigram is a sequence of three consecutive tokens, where each
 * token is a character of a character combinations such a "sch".
 * 
 * @author Erik Colban &copy; 2013
 * 
 */
public class TrigramMain {

    private final BlockingQueue<Token> queue;
    private Lexer lexer;
    private TokenMultiSet[][] tokenSets;
    private Token start1; // first token in the input file
    private Token start2; // second token in the input file

    /**
     * Constructor
     * 
     * @throws URISyntaxException
     */
    public TrigramMain() throws URISyntaxException {
	URL inputUrl = TrigramMain.class.getResource("textfiles/faustzara.txt");
	File input = new File(inputUrl.toURI());
	queue = new ArrayBlockingQueue<Token>(100);
	lexer = new Lexer(queue, input);
    }

    /**
     * Starrts the program by starting the lexer and then starting counting
     * trigrams.
     * 
     * @param args
     *            ignored
     */
    public static void main(String[] args) {

	try {
	    TrigramMain tc = new TrigramMain();
	    new Thread(tc.lexer).start();
	    tc.countTrigrams();
	    tc.generateText(2000);
	} catch (InterruptedException e) {
	    // Exit immediately
	} catch (URISyntaxException e) {
	    // Exit immediately
	}

    }

    /**
     * Gets all the tokens off the queue and adds each to a structure that keeps
     * count of the trigram frequencies.
     * 
     * @throws InterruptedException
     */
    private void countTrigrams() throws InterruptedException {
	int numTokens = Token.values().length;
	tokenSets = new TokenMultiSet[numTokens][numTokens];
	Token first = start1 = queue.take();
	Token second = start2 = queue.take();
	Token third = queue.take();
	while (!third.equals(Token.EOF)) {
	    int i = first.ordinal();
	    int j = second.ordinal();
	    if (tokenSets[i][j] == null) {
		tokenSets[i][j] = new TokenMultiSet();
	    }
	    tokenSets[i][j].add(third);
	    first = second;
	    second = third;
	    third = queue.take();
	}
    }

    /**
     * Generates a text of length len (or shorter if not possible to continue).
     * The output is divided into lines of approximately 70+ characters.
     * @param len the length of the output in number of tokens.
     */
    private void generateText(int len) {

	int i = start1.ordinal();
	int j = start2.ordinal();
	StringBuilder sb = new StringBuilder();
	sb.append(start1.getTokenString());
	sb.append(start2.getTokenString());
	int lineLength = 2;
	File output = new File(System.getProperty("user.home")
		+ "/Desktop/output.txt");
	PrintStream out = null;
	try {
	    PrintStream os = new PrintStream(output);
	    out = new PrintStream(os, true, "UTF-8");
	    for (int numTokens = 2; numTokens < len; numTokens++) {
		TokenMultiSet tSet = tokenSets[i][j];
		if (tSet == null) {
		    break;
		}
		Token t = tSet.nextToken();
		i = j;
		j = t.ordinal();
		if (t == Token.SPACE && lineLength > 70) {
		    out.println(sb);
		    sb.setLength(0);
		    lineLength = 0;
		} else {
		    String s = t.getTokenString();
		    sb.append(s);
		    lineLength += s.length();
		}
	    }
	    out.println(sb);
	} catch (UnsupportedEncodingException e) {
	} catch (FileNotFoundException e) {
	}
    }

}
