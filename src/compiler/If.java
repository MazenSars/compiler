package compiler;

import java.util.ArrayList;

public class If extends Statement {
	ArrayList<Statement> statements= new ArrayList<>();
	ArrayList<Expression> expressions= new ArrayList<>();
	
	public If(ArrayList<Statement> statements, ArrayList<Expression> expressions ) {
		this.statements= statements;
		this.expressions= expressions;
	}
}
