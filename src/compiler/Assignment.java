package compiler;

public class Assignment extends Statement {
	String literal;
	Object value;
	public Assignment(String literal, Object value) {
		this.literal= literal;
		this.value= value;
	}
}
