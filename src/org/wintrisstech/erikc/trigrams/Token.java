package org.wintrisstech.erikc.trigrams;

import java.util.HashMap;
import java.util.Map;

/**
 * Enumeration of all the tokens. Some tokens represent character combinations
 * such as "sch"
 * 
 * @author Erik Colban &copy; 2013
 */
public enum Token {

    // Lower case letters
    LOWER_A("a"), LOWER_A_UMLAUT("\u00e4"), LOWER_B("b"), LOWER_C("c"), LOWER_D(
	    "d"), LOWER_E("e"), LOWER_F("f"), LOWER_G("g"), LOWER_H("h"), LOWER_I(
	    "i"), LOWER_J("j"), LOWER_K("k"), LOWER_L("l"), LOWER_M("m"), LOWER_N(
	    "n"), LOWER_O("o"), LOWER_O_UMLAUT("\u00f6"), LOWER_P("p"), LOWER_Q(
	    "q"), LOWER_R("r"), LOWER_S("s"), LOWER_ESZETT("\u00df"), LOWER_T(
	    "t"), LOWER_U("u"), LOWER_U_UMLAUT("\u00fc"), LOWER_V("v"), LOWER_W(
	    "w"), LOWER_X("x"), LOWER_Y("y"), LOWER_Z("z"), //

    // Upper case letters
    UPPER_A("A"), UPPER_A_UMLAUT("\u00c4"), UPPER_B("B"), UPPER_C("C"), UPPER_D(
	    "D"), UPPER_E("E"), UPPER_F("F"), UPPER_G("G"), UPPER_H("H"), UPPER_I(
	    "I"), UPPER_J("J"), UPPER_K("K"), UPPER_L("L"), UPPER_M("M"), UPPER_N(
	    "N"), UPPER_O("O"), UPPER_O_UMLAUT("\u00d6"), UPPER_P("P"), UPPER_Q(
	    "Q"), UPPER_R("R"), UPPER_S("S"), UPPER_ESZETT("\u1e9e"), UPPER_T(
	    "T"), UPPER_U("U"), UPPER_U_UMLAUT("\u00dc"), UPPER_V("V"), UPPER_W(
	    "W"), UPPER_X("X"), UPPER_Y("Y"), UPPER_Z("Z"), //

    // Special character combinations.
    LOWER_CH("ch"), LOWER_SC("sc"), LOWER_SCH("sch"), LOWER_SS("ss"), LOWER_ST("st"), //
    UPPER_CH("CH"), UPPER_SC("SC"), UPPER_SCH("SCH"), UPPER_SS("SS"), UPPER_ST("ST"),//
    INITIAL_CH("Ch"), INITIAL_SC("Sc"), INITIAL_SCH("Sch"), INITIAL_SS("Ss"), INITIAL_ST("St"),//

    // Delimiters
    SPACE(" "), COMMA(","), PERIOD("."), COLON(":"), SEMI_COLON(";"), QUESTION_MARK(
	    "?"), EXCLAMATION_MARK("!"), QUOTE("\""), APOSTROPHY("'"), OPEN_PARANTHESIS(
	    "("), CLOSE_PARENTHESIS(")"), HYPHEN("-"), SLASH("/"), UNDERSCORE(
	    "_"), //

    // Digits
    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT(
	    "8"), NINE("9"), ZERO("0"), //

    // A catch-all token
    OTHER("*"), //

    // End of file marker
    EOF("EOF");

    /*
     * A map that maps strings to tokens. Used to look up which token represents
     * a character or string of characters.
     */
    private static Map<String, Token> tokenMap = new HashMap<String, Token>();

    // Build the map.
    static {
	for (Token t : Token.values()) {
	    tokenMap.put(t.getTokenString(), t);
	}
    }
    /*
     * The string that the token represents
     */
    private final String tokenString;

    /**
     * A private constructor
     * 
     * @param s
     *            the string that the token represents. A string should be
     *            represented by at most one token.
     */
    private Token(String s) {
	this.tokenString = s;
    }

    /**
     * Gets the string that the token represents.
     * 
     * @return
     */
    public String getTokenString() {
	return tokenString;
    }

    /**
     * Gets the token representing a given string, or null if there is no such
     * token.
     * 
     * @param s
     *            a string
     * @return the token matching s
     */
    public static Token getToken(String s) {
	return tokenMap.get(s);
    }

    /**
     * Gets the token representing a substring of input starting at the position
     * pos. If there is no such token, null is returned.
     * 
     * @param input
     *            the input string
     * @param pos
     *            the position where the token string starts.
     * @return a token
     */
    public static Token nextToken(String input, int pos) {
	if (pos < 0 || input.length() <= pos) {
	    throw new IllegalArgumentException(
		    "pos must be greater or equal to zero and less than input.length()");
	}
	boolean done = false;
	StringBuilder sb = new StringBuilder();
	Token token = null;
	for (int i = pos; i < input.length() && !done; i++) {
	    sb.append(input.charAt(i));
	    Token lookup = getToken(sb.toString());
	    if (lookup == null) {
		done = true;
	    } else {
		token = lookup;
	    }
	}
	if (token == null) {
	    // System.out.println(sb);
	    return OTHER;
	} else {
	    return token;
	}
    }

    /**
     * A method to test this class.
     * @param args
     */
    public static void main(String[] args) {

	System.out.println("The number of tokens = " + Token.values().length);
	String input = "Geistes und seiner Einsamkeit und wurde dessen zehn Jahr nicht müde.";
	System.out.println("in:  " + input);

	int pos = 0;
	StringBuilder sb = new StringBuilder();
	while (pos < input.length()) {
	    Token t = Token.nextToken(input, pos);
	    sb.append(t.getTokenString());
	    pos += t.getTokenString().length();
	}
	
	System.out.println("out: " + sb);
    }

}
