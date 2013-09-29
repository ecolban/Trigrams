package org.wintrisstech.erikc.trigrams;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class TrigramCounter {

    private final BlockingQueue<Token> queue = new ArrayBlockingQueue<Token>(
	    100);;
    private final String fileName = "TheStory.txt";
    private Lexer lexer;
    private TrigramTree[][] digrams;

    public TrigramCounter() {
	URL url = TrigramCounter.class.getResource("textfiles/" + fileName);
	try {
	    File input = new File(url.toURI());
	    lexer = new Lexer(input, queue);
	    new Thread(lexer).start();
	} catch (URISyntaxException e) {
	}
    }

    public static void main(String[] args) {
    
        try {
            TrigramCounter tc = new TrigramCounter();
    
            tc.buildTrigrams();
            tc.generateText(1000);
        } catch (InterruptedException e) {
        }
    
    }

    private void buildTrigrams() throws InterruptedException {
	int numTokens = Token.values().length;
	digrams = new TrigramTree[numTokens][numTokens];
	Token first = queue.take();
	Token second = queue.take();
	Token third = queue.take();
	while (!third.equals(Token.EOF)) {
	    int i = first.ordinal();
	    int j = second.ordinal();
	    if (digrams[i][j] == null) {
		digrams[i][j] = new TrigramTree();
	    }
	    digrams[i][j].addThird(third);
	    first = second;
	    second = third;
	    third = queue.take();
	}
    }

    private void generateText(int len) {
	int i = Token.getToken("O").ordinal();
	int j = Token.getToken("n").ordinal();
	StringBuilder sb = new StringBuilder("On");
	int lineLength = 3;
	for (int numTokens = 2; numTokens < len; numTokens++) {
	    Token t = digrams[i][j].nextToken();
	    i = j;
	    j = t.ordinal();
	    if(t == Token.SPACE && lineLength > 70) {
		System.out.println(sb);
		sb.setLength(0);
		lineLength = 0;
	    } else {
		sb.append(t.getTokenString());
		lineLength++;
	    }
	}
	System.out.println(sb);

    }

}
