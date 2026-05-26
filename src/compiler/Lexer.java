package compiler;

import java.util.ArrayList;
import javax.xml.stream.events.EndDocument;

public class Lexer {
	Parser parser;
	public Lexer(String code) {
		ArrayList<Token> tokens= new ArrayList<Token>();
		StringBuilder builder;
		int pos=0;
		while (pos<code.length()-1) {
		if (Character.isWhitespace(code.charAt(pos))) {
			System.out.println("in whitespace");
			pos++;
			continue;
		}
		switch (code.charAt(pos)) {
		case '+': tokens.add(new Token(code.charAt(pos), Type.add)); pos++; break;
		case '-': tokens.add(new Token(code.charAt(pos), Type.sub)); pos++; break;
		case '*': tokens.add(new Token(code.charAt(pos), Type.mult)); pos++; break;
		case '/': tokens.add(new Token(code.charAt(pos), Type.div)); pos++; break;
		case '(': tokens.add(new Token(code.charAt(pos), Type.lparen)); pos++; break;
		case ')': tokens.add(new Token(code.charAt(pos), Type.rparen)); pos++; break;
		case '{': tokens.add(new Token(code.charAt(pos), Type.lbraket)); pos++; break;
		case '}': tokens.add(new Token(code.charAt(pos), Type.rbraket)); pos++; break;
		case ';': tokens.add(new Token(code.charAt(pos), Type.semicolon)); pos++; break;
		case '=': if (code.charAt(pos+1)=='=') {
			tokens.add(new Token("==", Type.equal));
			pos+=2;
		}
		else {
			tokens.add(new Token(code.charAt(pos),Type.equal));
			pos++;
		}
		case '!': if (code.charAt(pos+1)=='=') {
			tokens.add(new Token(code.charAt(pos), Type.notequal)); pos+=2; break;
		}
		case '>': tokens.add(new Token(code.charAt(pos), Type.bigger)); pos++; break;
		case '<': System.out.println(pos); tokens.add(new Token(code.charAt(pos), Type.smaller)); pos++; break;
		default: pos++; break;
		}
		if (Character.isDigit(code.charAt(pos))) {
			System.out.println("in digit");
			builder= new StringBuilder();
			while (Character.isDigit(code.charAt(pos))) {
				builder.append(code.charAt(pos));
				pos++;
			}
			tokens.add(new Token(builder.toString(), Type.integer));
		}
		
		if (Character.isLetter(code.charAt(pos))) {
			System.out.println("in letter");
			builder= new StringBuilder();
			String str;
			while (Character.isLetter(code.charAt(pos))) {
				builder.append(code.charAt(pos));
				pos++;
			}
			str= builder.toString();
			System.out.println(str);
			System.out.println(pos);
			switch (str) {
			case "if": tokens.add(new Token(str, Type.iff)); break;
			case "print": tokens.add(new Token(str, Type.print)); break;
			case "func": tokens.add(new Token(str, Type.func)); break;
			case "else": tokens.add(new Token(str, Type.elsee)); break;
			case "var": tokens.add(new Token(str, Type.var)); break;
			case "int": tokens.add(new Token(str, Type.integer)); break;
			case "str": tokens.add(new Token(str, Type.string));break;
			}
		}
		}
		tokens.add(new Token(null, Type.eof));
		parser= new Parser(tokens);
	}
}
