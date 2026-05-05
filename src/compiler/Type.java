package compiler;

public enum Type {
		/*keywords*/ integer, string, func, print, iff, elsee, var,
		/*operators*/ add, sub, mult, div, bigger, smaller, equal, notequal, assign,
		/*linking*/ lparen, rparen, semicolon, identifier, lbraket, rbraket,
		/*end of file*/ eof;
}
