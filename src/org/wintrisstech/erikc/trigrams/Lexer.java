package org.wintrisstech.erikc.trigrams;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;

/**
 * A Lexer instance runs through a German text file and converts the file to a
 * sequence of tokens that are put on a queue.
 * 
 * @author ecolban
 * 
 */
public class Lexer implements Runnable {

    private final BlockingQueue<Token> queue;
    private final File file;

    /**
     * Constructor
     * 
     * @param queue
     *            the queue onto which the lexer is to put the tokens
     * @param input
     *            a German text file
     */
    public Lexer(BlockingQueue<Token> queue, File input) {
	this.queue = queue;
	this.file = input;
    }

    /**
     * Opens and reads a text file while generating tokens that are put on a
     * queue.
     */
    @Override
    public void run() {
	BufferedReader reader = null;
	try {
	    //Open the file
	    reader = new BufferedReader(new InputStreamReader(
		    new FileInputStream(file), "UTF-8"));
	    //Read all lines
	    String line = reader.readLine();
	    for (; line != null;) {
		tokenize(line);
		line = reader.readLine();
	    }
	    queue.put(Token.EOF); //insert EOF token at the end
	} catch (FileNotFoundException e) {
	    return;
	} catch (UnsupportedEncodingException e) {
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

    /**
     * translates a string into tokens. Each token is put on the queue
     * @param line a string
     * @throws InterruptedException
     */
    private void tokenize(String line) throws InterruptedException {
	if (line == null || line.isEmpty() || line.matches("\\s*")) {
	    return;
	}
	line = line.trim(); // remove whitespace at start and end
	line.replaceAll("\\s+", " "); // truncate whitespace.
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
