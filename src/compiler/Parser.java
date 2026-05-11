package compiler;

import java.util.ArrayList;


public class Parser {
	Program program= new Program();
	int item=0;
	ArrayList<Token> tokens;
	
	public Parser(ArrayList<Token> tokens) {
		this.tokens= tokens;
		Evalif();
		Evalbinary();
		Evalprint();
		Evalassignment();
	}
	
	public If Evalif() {
		If iff = null;
		ArrayList<Statement> statement = new ArrayList<Statement>();
		ArrayList<Expression> expression = new ArrayList<Expression>();
		ArrayList<Literal> literals= new ArrayList<>();
		Literal literal;
		String operator;
		if (tokens.get(item).type==Type.iff) {
			item++;
			if (tokens.get(item).type==Type.lparen) {
				item++;
				while (tokens.get(item).type!=Type.rparen) {
					if (tokens.get(item).type!=Type.literal) {
						System.out.println("put condition first");
						break;
					}
					else {
						item++;
						literal= new Literal(tokens.get(item).token);
						literals.add(literal);
						if (tokens.get(item).type==Type.bigger || tokens.get(item).type==Type.smaller || tokens.get(item).type==Type.equal || tokens.get(item).type==Type.notequal) {
							operator= (String) tokens.get(item).token;
							item++;
							if ((tokens.get(item-2).token instanceof String)!=(tokens.get(item).token instanceof String) || (tokens.get(item-2).token instanceof Integer)!=(tokens.get(item).token instanceof Integer)) {
								System.out.println("must compare same type");
								break;
							}
							else {
								literal= new Literal(tokens.get(item).token);
								literals.add(literal);
								item++;
							}
						}
						else {
							System.out.println("must add condition");
							break;
						}
					}
					if (tokens.get(item).type==Type.eof) {
						System.out.println("must close parenthesis");
						break;
					}
					if (tokens.get(item).type==Type.print) {
						statement.add(Evalprint());
			}
					if (tokens.get(item).type==Type.string || tokens.get(item).type==Type.integer) {
						expression.add(Evalbinary());
					} 
					item++;
				}
			}
			iff= new If(statement, expression, literals);
		}
		else {
			System.out.println("wrong if");
		}
		program.statements.add(iff);
		return iff;
	}
	public BinaryExpression Evalbinary() {
		BinaryExpression be = null;
		String operator = null;
		String left = null;
		String right = null;
		if (tokens.get(item).type==Type.integer) {
			left= tokens.get(item).toString();
			item++;
			if (tokens.get(item).type==Type.add || tokens.get(item).type==Type.sub || tokens.get(item).type==Type.mult || tokens.get(item).type==Type.div) {
				operator= tokens.get(item).toString();
				item++;
				if (tokens.get(item).type==Type.integer) {
					right= tokens.get(item).toString();
					item++;
					if (tokens.get(item).type==Type.semicolon) {
						item++;
					}
					else {
						Evalbinary();
					}
				}
				else {
					System.out.println("wrong types");
				}
			}
			else {
				System.out.println("wrong operator");
			}
		}
		
		if (tokens.get(item).type==Type.string) {
			item++;
			right= tokens.get(item).toString();
			if (tokens.get(item).type==Type.add) {
				operator= tokens.get(item).toString();
				item++;
				if (tokens.get(item).type==Type.string) {
					item++;
					left=tokens.get(item).toString();
					if (tokens.get(item).type==Type.semicolon) {
						item++;
					}
				}
				else {
				}
			}
			else {
			}
		}
		if (Integer.parseInt(right)==(int)Integer.parseInt(right) && Integer.parseInt(left)==(int)Integer.parseInt(left) || right== (String)right && left== (String)left) {
			be= new BinaryExpression(left, right, operator);
		}
		program.expressions.add(be);
		return be;
	}
	public Print Evalprint() {
		Print print = null;
		if (tokens.get(item).type==Type.print) {
			StringBuilder builder= new StringBuilder();
			String value = null;
			while (tokens.get(item).type!=Type.rparen) {
				if (tokens.get(item).type!=Type.string && tokens.get(item).type!=Type.rparen) {
					System.out.println("must print string");
					break;
				}
				else {
					if (tokens.get(item).type!= Type.rparen) {
						
						builder.append(tokens.get(item).toString());
					}
					else {
						value= builder.toString();
					}
				}
				item++;
			}
			if (item<tokens.size() && tokens.get(item+1).type!=Type.semicolon) {
				System.out.println("expected end of print statement");
			}
			print= new Print(value);
	}
		program.statements.add(print);
		return print;
}
	public Statement Evalassignment() {
		String literal;
		Object value;
		Assignment assignment = null;
		if (tokens.get(item).type==Type.var) {
			item++;
			if (tokens.get(item).type==Type.string) {
				literal= tokens.get(item).toString();
				item++;
				if (tokens.get(item).type==Type.semicolon) {
					Literal lit= new Literal(literal);
				}
				else {
					if (tokens.get(item).type==Type.assign) {
						item++;
						if (tokens.get(item).type==Type.string || tokens.get(item).type==Type.integer) {
							item++;
							value= tokens.get(item).toString();
							if (tokens.get(item).type==Type.semicolon) {
								assignment= new Assignment(literal, value);
							}
						}
					}
				}
			}
		}
		program.statements.add(assignment);
		return assignment;
	}
	Interpreter interpreter= new Interpreter(program);
}