package compiler;

public class Interpreter {
	int countStatement, countExpression=0;
	Program program;
	public Interpreter(Program program) {
		this.program= program;
	}
	public String Execute() {
		while (countStatement<program.statements.size()) {
			if (program.statements.get(countStatement).getClass()==Print.class) {
				Print print= (Print) program.statements.get(countStatement);
				System.out.println(print.value);
			}
			if (program.statements.get(countStatement).getClass()==If.class) {
				If iff= (If) program.statements.get(countStatement);
				int statement = 0, expression=0;
				while (statement<iff.statements.size()) {
					if (iff.statements.get(statement).getClass()==Print.class) {
						Print print= (Print) program.statements.get(countStatement);
						System.out.println(print.value);
					}
			}
				while (expression<iff.expressions.size()) {
					if (iff.expressions.get(expression).getClass()== BinaryExpression.class) {
						BinaryExpression be= (BinaryExpression) program.expressions.get(expression);
						if (be.operator.equals("+")) {
							return (be.left+be.right);
						}
							if (be.operator.equals("*")) {

								return (""+ Integer.parseInt(be.left)*Integer.parseInt(be.right));
							}
							if (be.operator.equals("/")) {

								return (""+ Integer.parseInt(be.left)/Integer.parseInt(be.right));
							}
							if (be.operator.equals("-")) {

								return (""+ (Integer.parseInt(be.left)-Integer.parseInt(be.right)));
							}
					}
				}
			if (program.statements.get(countStatement).getClass()==Assignment.class) {
				Assignment assignment= (Assignment) program.statements.get(countStatement);
			}
			countStatement++;
		}
		while (countExpression<program.expressions.size()) {
			if (program.expressions.get(countExpression).getClass()== BinaryExpression.class) {
				BinaryExpression be= (BinaryExpression) program.expressions.get(countExpression);
				if (be.operator=="*") {

					return (""+ Integer.parseInt(be.left)*Integer.parseInt(be.right));
				}
				if (be.operator=="/") {

					return (""+ Integer.parseInt(be.left)/Integer.parseInt(be.right));
				}
				if (be.operator=="-") {

					return (""+ (Integer.parseInt(be.left)-Integer.parseInt(be.right)));
				}
			}
		}
	}
	return ("completed");
	}
}