package compiler;

import java.util.ArrayList;

import com.sun.org.apache.bcel.internal.util.BCELifier;

public class Interpreter {
	ArrayList<Statement> statements= new ArrayList<>();
	ArrayList<Expression> expressions= new ArrayList<>();
	int countStatement, countExpression=0;
	
	public String Ececute() {
		while (countStatement<statements.size()) {
			if (statements.get(countStatement).getClass()==Print.class) {
				Print print= (Print) statements.get(countStatement);
				System.out.println(print.value);
			}
			if (statements.get(countStatement).getClass()==If.class) {
				If iff= (If) statements.get(countStatement);
				int statement = 0, expression=0;
				while (statement<iff.statements.size()) {
					if (statements.get(statement).getClass()==Print.class) {
						Print print= (Print) statements.get(countStatement);
						System.out.println(print.value);
					}
			}
				while (expression<iff.expressions.size()) {
					if (expressions.get(expression).getClass()== BinaryExpression.class) {
						BinaryExpression be= (BinaryExpression) expressions.get(expression);
						if (be.operator=="+") {
							return (be.left+be.right);
						}
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
			if (statements.get(countStatement).getClass()==Assignment.class) {
				Assignment assignment= (Assignment) statements.get(countStatement);
			}
			countStatement++;
		}
		while (countExpression<expressions.size()) {
			if (expressions.get(countExpression).getClass()== BinaryExpression.class) {
				BinaryExpression be= (BinaryExpression) expressions.get(countExpression);
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
	}
}
