package fr.wonder.commons.math.expressions;

class FunctionExpression implements Expression {
	
	final Function function;
	final Expression[] arguments;
	
	FunctionExpression(Function function, Expression... arguments) {
		this.function = function;
		this.arguments = arguments;
	}
	
	@Override
	public String toCalculusString() {
		StringBuilder sb = new StringBuilder();
		sb.append(function.name);
		sb.append('(');
		for(int i = 0; i < arguments.length; i++) {
			sb.append(arguments[i].toCalculusString());
			if(i != arguments.length-1)
				sb.append(", ");
		}
		sb.append(')');
		return sb.toString();
	}
	
	@Override
	public Expression evaluate() {
		Expression[] args = new Expression[arguments.length];
		for(int i = 0; i < args.length; i++)
			args[i] = arguments[i].evaluate();
		return new FunctionExpression(function, args);
	}
	
	@Override
	public String toString() {
		return toCalculusString();
	}

	@Override
	public float evaluate(UnknownValue... unknowns) throws ArithmeticException {
		float[] args = new float[arguments.length];
		for(int i = 0; i < args.length; i++)
			args[i] = arguments[i].evaluate(unknowns);
		return function.evaluate(args);
	}
	
}
