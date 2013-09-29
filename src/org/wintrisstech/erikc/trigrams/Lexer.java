package org.wintrisstech.erikc.trigrams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;

public class Lexer implements Runnable {

    private final File input;
    private final BlockingQueue<Token> queue;

    public Lexer(File input, BlockingQueue<Token> queue) {
	this.input = input;
	this.queue = queue;
    }

    @Override
    public void run() {
	if (!input.exists()) {
	    return;
	}
	BufferedReader reader = null;
	try {
	    reader = new BufferedReader(new FileReader(input));
	    String line = reader.readLine();
	    while (line != null) {
		tokenize(line);
		line = reader.readLine();
	    }
	    queue.put(Token.EOF);
	} catch (FileNotFoundException e) {
	    return;
	} catch (IOException e) {
	    return;
	} catch (InterruptedException e) {
	    return;
	} finally {
	    if (reader != null) {
		try {
		    reader.close();
		} catch (IOException e) {
		}
	    }
	}

    }

    private void tokenize(String line) throws InterruptedException {
	if (line == null || line.isEmpty() || line.matches("\\s*")) {
	    return;
	}
	line = line.trim();
	line.replaceAll("\\s+", " ");
	int pos = 0;
	Token t = null;
	while (pos < line.length()) {
	    t = Token.nextToken(line, pos);
	    queue.put(t);
	    pos += t.getTokenString().length();
	}
	queue.put(Token.SPACE);
    }

}
