package compiler;

import java.util.ArrayList;
import java.util.List;


public class Parser {
	private final List<Token> tokens;
    private int pos;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
        this.pos = 0;
    }

    // ENTRY POINT: Loops until EOF, collects all top-level statements
    public Program parse() {
        Program program = new Program();
        while (!isAtEnd()) {
            program.statements.add(parseStatement());
        }
        return program;
    }

    // ROUTER: Looks at current token and dispatches to correct parser
    private Statement parseStatement() {
        if (check(Type.print)) return parsePrint();
        if (check(Type.iff)) return parseIf();
        if (check(Type.identifier) && peek(1).type == Type.assign) return parseAssignment();
        
        // Fallback: treat as expression statement (e.g., 5 + 3;)
        Expression expr = parseExpression();
        consume(Type.semicolon);
        return new ExpressionStatement(expr);
    }
 // FIX #1, #6, #10: Dedicated method, reuses parseExpression for condition
    private Statement parseIf() {
        consume(Type.iff);
        consume(Type.lparen);
        Expression condition = parseExpression(); // Reuses expression logic
        consume(Type.rparen);
        consume(Type.lbraket);

        List<Statement> thenBody = new ArrayList<>();
        while (!check(Type.rbraket) && !isAtEnd()) {
            thenBody.add(parseStatement()); // FIX #4, #5: Recursively builds Node tree
        }
        consume(Type.rbraket);

        List<Statement> elseBody = new ArrayList<>();
        if (check(Type.elsee)) {
            consume(Type.elsee);
            consume(Type.lbraket);
            while (!check(Type.rbraket) && !isAtEnd()) {
                elseBody.add(parseStatement());
            }
            consume(Type.rbraket);
        }

        return new If(condition, thenBody, elseBody);
    }
    private Statement parsePrint() {
        consume(Type.print);
        consume(Type.lparen);
        Expression value = parseExpression(); // FIX #7: Parses value as expression, not raw tokens
        consume(Type.rparen);
        consume(Type.semicolon);
        return new Print(value);
    }

    private Statement parseAssignment() {
        Token name = consume(Type.identifier);
        consume(Type.assign);
        Expression value = parseExpression();
        consume(Type.semicolon);
        return new Assignment((String)name.value, value);
    }

    // FIX #4, #5, #12: Nodes contain Nodes. Left-to-right parsing with operator handling
    private Expression parseExpression() {
        Expression left = parsePrimary();
        
        while (check(Type.add) || check(Type.sub) || check(Type.mult) || check(Type.div) ||
               check(Type.bigger) || check(Type.smaller) || check(Type.equal) || check(Type.notequal)) {
            
            Type opType = advance().type;
            String operator = opType.toString(); // e.g., "add", "bigger"
            Expression right = parsePrimary();
            left = new BinaryExpression(left, operator, right); // FIX #5: Captures and nests result
        }
        return left;
    }
 // FIX #6, #9: Handles literals, variables, and parentheses. NO type checking here.
    private Expression parsePrimary() {
        if (check(Type.integer) || check(Type.string)) {
            Token t = advance();
            return new Literal(t.value); // FIX #9: Store raw value, check types later in Interpreter
        }
        if (check(Type.identifier)) {
            return new Variable((String) advance().value);
        }
        if (check(Type.lparen)) {
            consume(Type.lparen);
            Expression expr = parseExpression(); // FIX #6: Recursion handles nesting
            consume(Type.rparen);
            return expr;
        }
        throw new RuntimeException("Expected expression but got: " + peek().type);
    }

    // SAFE ACCESS HELPERS (Fixes #3, bounds errors)
    private boolean isAtEnd() { return peek().type == Type.eof; }
    private Token peek() { return tokens.get(pos); }
    private Token peek(int offset) { return tokens.get(pos + offset); }
    private Token advance() { if (!isAtEnd()) pos++; return tokens.get(pos - 1); }
    private boolean check(Type type) { return !isAtEnd() && peek().type == type; }
    private Token consume(Type type) {
        if (check(type)) return advance();
        throw new RuntimeException("Expected " + type + " but found " + peek().type);
    }
}