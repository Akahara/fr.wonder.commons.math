package fr.wonder.commons.math.expressions;

class CalculusExpression implements Expression {
	
	final Expression leftSide, rightSide;
	final Operator operator;
	
	CalculusExpression(Expression leftSide, Expression rightSide, Operator operator) throws ArithmeticException {
		this.leftSide = leftSide;
		this.rightSide = rightSide;
		this.operator = operator;
		
		if(leftSide == null && operator.needLeftPart)
			throw new ArithmeticException("No left-hand part for operator " + operator.chr);
		if(rightSide == null && operator.needRightPart)
			throw new ArithmeticException("No right-hand part for operator " + operator.chr);
	}
	
	@Override
	public Expression evaluate() {
		Expression lexp = leftSide != null ? leftSide.evaluate() : null;
		Expression rexp = rightSide != null ? rightSide.evaluate() : null;
		return operator.evaluate(lexp, rexp);
	}
	
	@Override
	public String toString() {
		if(rightSide == null)
			return operator + "(" + leftSide + ")";
		return operator + "(" + leftSide + ", " + rightSide + ")";
	}
	
	@Override
	public String toCalculusString() {
		if(rightSide == null)
			return "(" + leftSide.toCalculusString() + operator.chr + ")";
		return "(" + leftSide.toCalculusString() + operator.chr + rightSide.toCalculusString() + ")";
	}

	@Override
	public float evaluate(UnknownValue... unknowns) throws ArithmeticException {
		float lfloat = leftSide != null ? leftSide.evaluate(unknowns) : 0;
		float rfloat = rightSide != null ? rightSide.evaluate(unknowns) : 0;
		return operator.evaluate(lfloat, rfloat);
	}
}
