package fr.wonder.commons.math.expressions;

public interface Expression {
	
	@Override
	public abstract String toString();
	
	public String toCalculusString();
	
	public Expression evaluate();
	
	public float evaluate(UnknownValue... unknowns) throws ArithmeticException;
	
}
