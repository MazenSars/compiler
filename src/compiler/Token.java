package compiler;

import compiler.Token;

public class Token {
	Object token;
	Type type;
	public Token (Object token,Type type) {
		this.token= token;
		this.type= type;
	}
}
