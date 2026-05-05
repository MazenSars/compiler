package compiler;

public class BinaryExpression extends Expression {
	String right, left, operator;
	public BinaryExpression(String left, String right, String operator) {
		this.left= left;
		this.right= right;
		this.operator= operator;
	}
}