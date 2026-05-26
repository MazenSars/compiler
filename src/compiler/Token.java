package compiler;

import compiler.Token;

public class Token {
	Object value;
	Type type;
	public Token (Object value,Type type) {
		this.value= value;
		this.type= type;
	}
}
