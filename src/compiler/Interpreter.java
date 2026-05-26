package compiler;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Interpreter {
	// Environment: Stores variable names and their current values
    private Map<String, Object> environment = new HashMap<>();

    // ENTRY POINT: Executes all top-level statements in order
    public void interpret(Program program) {
        for (Statement stmt : program.statements) {
            execute(stmt);
        }
    }

    // STATEMENT DISPATCHER: Routes to the correct execution logic
    private void execute(Statement stmt) {
        if (stmt instanceof Print) executePrint((Print) stmt);
        else if (stmt instanceof If) executeIf((If) stmt);
        else if (stmt instanceof Assignment) executeAssignment((Assignment) stmt);
        else if (stmt instanceof ExpressionStatement) evaluate(((ExpressionStatement) stmt).expr);
        else throw new RuntimeError("Unknown statement type: " + stmt.getClass().getSimpleName());
    }

    // PRINT: Evaluates expression, converts to string, prints to console
    private void executePrint(Print node) {
        Object value = evaluate(node.value);
        System.out.println(value.toString());
    }

    // IF: Evaluates condition, must be boolean, then executes correct branch
    private void executeIf(If node) {
        Object condition = evaluate(node.condition);
        if (!(condition instanceof Boolean)) {
            throw new RuntimeError("Condition must evaluate to true/false, got: " + condition.getClass());
        }
        boolean isTrue = (Boolean) condition;
        List<Statement> target = isTrue ? node.thenBody : node.elseBody;
        for (Statement stmt : target) {
            execute(stmt); // Recursive execution for nested statements
        }
    }

    // ASSIGNMENT: Evaluates right side, stores in environment map
    private void executeAssignment(Assignment node) {
        Object value = evaluate(node.value);
        environment.put(node.varName, value);
    }

    // EXPRESSION EVALUATOR (Recursive): Computes value of an expression tree
    private Object evaluate(Expression expr) {
        if (expr instanceof Literal) {
            return ((Literal) expr).value; // Base case: return raw value
        }
        if (expr instanceof Variable) {
            String name = ((Variable) expr).name;
            if (!environment.containsKey(name)) {
                throw new RuntimeError("Undefined variable: " + name);
            }
            return environment.get(name); // Lookup in environment
        }
        if (expr instanceof BinaryExpression) {
            return evaluateBinary((BinaryExpression) expr);
        }
        throw new RuntimeError("Unknown expression type: " + expr.getClass().getSimpleName());
    }

    // BINARY OPERATOR HANDLER: Applies operator to evaluated left/right sides
    private Object evaluateBinary(BinaryExpression node) {
        Object left = evaluate(node.left);
        Object right = evaluate(node.right);

        switch (node.operator) { // Matches enum.toString() from Parser
            case "add":
            	if (left instanceof Integer && right instanceof Integer)
                    return (Integer) left + (Integer) right;
                return left.toString() + right.toString(); // String concatenation fallback
            case "sub": return checkInt(left) - checkInt(right);
            case "mult": return checkInt(left) * checkInt(right);
            case "div": return checkInt(left) / checkInt(right);
            case "bigger": return checkInt(left) > checkInt(right);
            case "smaller": return checkInt(left)<checkInt(right);
            case "equal": return left.equals(right);
            case "notequal": return !left.equals(right);
            default: throw new RuntimeError("Unknown operator: " + node.operator);
        }
    }

    // HELPER: Ensures operands are integers for math/comparison
    private int checkInt(Object obj) {
        if (!(obj instanceof Integer)) {
            throw new RuntimeError("Expected integer, got: " + obj.getClass().getSimpleName());
        }
        return (Integer) obj;
    }
}