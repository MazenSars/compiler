package compiler;

import java.util.ArrayList;

public class If extends Statement {
	ArrayList<Statement> statements= new ArrayList<>();
	ArrayList<Expression> expressions= new ArrayList<>();
	ArrayList<Literal> literals;
	
	public If(ArrayList<Statement> statements, ArrayList<Expression> expressions, ArrayList<Literal> literals) {
		this.statements= statements;
		this.expressions= expressions;
		this.literals= literals;
	}
}
