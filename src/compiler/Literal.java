package compiler;

public class Literal extends Expression {
    public Object value; // Integer or String
    public Literal(Object value) { this.value = value; }
}
