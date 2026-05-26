package compiler;

public class Assignment extends Statement {
    public String varName;
    public Expression value;
    public Assignment(String varName, Expression value) {
        this.varName = varName;
        this.value = value;
    }
}
