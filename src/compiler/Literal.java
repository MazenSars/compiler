package compiler;

public class Literal extends Statement {
	
	String string;
	int value; 
	char character;
	
	public Literal(Object value) {
		if (value instanceof String) {
			string= (String) value;
		}
		if (value instanceof Integer) {
			this.value= (int) value;
		}
		if (value instanceof Character) {
			character= (char) this.value;
		}
	}
}
