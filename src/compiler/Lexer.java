package compiler;

import java.util.ArrayList;
import javax.xml.stream.events.EndDocument;

public class Lexer {
	int pos;
	ArrayList<Token> tokens= new ArrayList<Token>();
	StringBuilder builder;
	Parser parse;
	public Lexer(String code) {
		while (pos<code.length()) {
		char ch= code.charAt(pos);
		if (Character.isWhitespace(ch)) {
			pos++;
			continue;
		}
		switch (ch) {
		case '+': tokens.add(new Token(ch, Type.add)); pos++; break;
		case '-': tokens.add(new Token(ch, Type.sub)); pos++; break;
		case '*': tokens.add(new Token(ch, Type.mult)); pos++; break;
		case '/': tokens.add(new Token(ch, Type.div)); pos++; break;
		case '(': tokens.add(new Token(ch, Type.lparen)); pos++; break;
		case ')': tokens.add(new Token(ch, Type.rparen)); pos++; break;
		case '{': tokens.add(new Token(ch, Type.lbraket)); pos++; break;
		case '}': tokens.add(new Token(ch, Type.rbraket)); pos++; break;
		case ';': tokens.add(new Token(ch, Type.semicolon)); pos++; break;
		case '=': if (code.charAt(pos+1)=='=') {
			tokens.add(new Token("==", Type.equal));
			pos+=2;
		}
		else {
			tokens.add(new Token(ch,Type.equal));
			pos++;
		}
		case '!': if (code.charAt(pos+1)=='=') {
			tokens.add(new Token(ch, Type.notequal)); pos+=2; break;
		}
		}
		if (Character.isDigit(ch)) {
			builder= new StringBuilder();
			while (Character.isDigit(ch)) {
				builder.append(ch);
				pos++;
			}
			tokens.add(new Token(builder.toString(), Type.integer));
			break;
		}
		if (Character.isLetter(ch)) {
			builder= new StringBuilder();
			String str;
			while (Character.isLetter(ch)) {
				builder.append(ch);
				pos++;
			}
			str= builder.toString();
			switch (str) {
			case "if": tokens.add(new Token(str, Type.iff));
			case "print": tokens.add(new Token(str, Type.print));
			case "func": tokens.add(new Token(str, Type.func));
			case "else": tokens.add(new Token(str, Type.elsee));
			case "var": tokens.add(new Token(str, Type.var));
			case "int": tokens.add(new Token(str, Type.integer));
			case "str": tokens.add(new Token(str, Type.string));
			}
			break;
		}
		}
		tokens.add(new Token(null, Type.eof));
		parse= new Parser(tokens);
	}
}
