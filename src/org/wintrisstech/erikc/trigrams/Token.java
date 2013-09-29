package org.wintrisstech.erikc.trigrams;

import java.util.HashMap;
import java.util.Map;

public enum Token {

    LOWER_A("a"), LOWER_B("b"), LOWER_C("c"), LOWER_D("d"), LOWER_E("e"), LOWER_F(
	    "f"), LOWER_G("g"), LOWER_H("h"), LOWER_I("i"), LOWER_J("j"), LOWER_K(
	    "k"), LOWER_L("l"), LOWER_M("m"), LOWER_N("n"), LOWER_O("o"), LOWER_P(
	    "p"), LOWER_Q("q"), LOWER_R("r"), LOWER_S("s"), LOWER_T("t"), LOWER_U(
	    "u"), LOWER_V("v"), LOWER_W("w"), LOWER_X("x"), LOWER_Y("y"), LOWER_Z(
	    "z"), //

    UPPER_A("A"), UPPER_B("B"), UPPER_C("C"), UPPER_D("D"), UPPER_E("E"), UPPER_F(
	    "F"), UPPER_G("G"), UPPER_H("H"), UPPER_I("I"), UPPER_J("J"), UPPER_K(
	    "K"), UPPER_L("L"), UPPER_M("M"), UPPER_N("N"), UPPER_O("O"), UPPER_P(
	    "P"), UPPER_Q("Q"), UPPER_R("R"), UPPER_S("S"), UPPER_T("T"), UPPER_U(
	    "U"), UPPER_V("V"), UPPER_W("W"), UPPER_X("X"), UPPER_Y("Y"), UPPER_Z(
	    "Z"), //

    LOWER_CH("ch"), LOWER_CK("ck"), LOWER_EE("ee"), LOWER_GH("gh"), LOWER_GHT(
	    "ght"), LOWER_GU("gu"), LOWER_KN("kn"), LOWER_OO("oo"), LOWER_PH(
	    "ph"), LOWER_QU("qu"), LOWER_QUE("que"), LOWER_SH("sh"), LOWER_TH(
	    "th"), LOWER_WH("wh"), //

    INITIAL_CH("Ch"), INITIAL_EE("Ee"), INITIAL_GH("Gh"), INITIAL_GU("Gu"), INITIAL_KN(
	    "Kn"), INITIAL_OO("Oo"), INITIAL_PH("Ph"), INITIAL_QU("Qu"), INITIAL_QUE(
	    "Que"), INITIAL_SH("Sh"), INITIAL_TH("Th"), INITIAL_WH("Wh"), //

    UPPER_CH("CH"), UPPER_CK("CK"), UPPER_EE("EE"), UPPER_GH("GH"), UPPER_GHT(
	    "GHT"), UPPER_GU("gu"), UPPER_KN("KN"), UPPER_OO("OO"), UPPER_PH(
	    "PH"), UPPER_QU("QU"), UPPER_QUE("QUE"), UPPER_SH("SH"), UPPER_TH(
	    "TH"), UPPER_WH("WH"), //

    SPACE(" "), COMMA(","), PERIOD("."), COLON(":"), SEMI_COLON(";"), QUESTION_MARK(
	    "?"), EXCLAMATION_MARK("!"), QUOTE("\""), APOSTROPHY("'"), OPEN_PARANTHESIS(
	    "("), CLOSE_PARENTHESIS(")"), HYPHEN("-"), SLASH("/"), //

    ONE("1"), TWO("2"), THREE("3"), FOUR("4"), FIVE("5"), SIX("6"), SEVEN("7"), EIGHT(
	    "8"), NINE("9"), ZERO("0"), //

    OTHER("*"), //

    EOF("EOF");

    private static Map<String, Token> tokenMap = new HashMap<String, Token>();

    static {
	for (Token t : Token.values()) {
	    tokenMap.put(t.getTokenString(), t);
	}
    }

    private final String tokenString;

    private Token(String s) {
	this.tokenString = s;
    }

    public String getTokenString() {
	return tokenString;
    }

    public static Token getToken(String s) {
	return tokenMap.get(s);
    }

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
	return token == null ? OTHER : token;
    }

    public static void main(String[] args) {

	String input = "The tough guy thought h'was very clever.";

	System.out.println(Token.values().length);
	int pos = 0;
	while (pos < input.length()) {
	    Token t = Token.nextToken(input, pos);
	    System.out.print(t.getTokenString() + "_");
	    pos += t.getTokenString().length();
	}
	System.out.println(Token.EOF.getTokenString());
    }

}
