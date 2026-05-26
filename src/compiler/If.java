package compiler;

import java.util.ArrayList;
import java.util.List;

public class If extends Statement {
	public Expression condition;
    public List<Statement> thenBody = new ArrayList<>();
    public List<Statement> elseBody = new ArrayList<>();
    public If(Expression condition, List<Statement> thenBody, List<Statement> elseBody) {
        this.condition = condition;
        this.thenBody = thenBody;
        this.elseBody = elseBody;
    }
}
