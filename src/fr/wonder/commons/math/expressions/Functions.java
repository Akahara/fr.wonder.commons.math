package fr.wonder.commons.math.expressions;

import java.util.ArrayList;
import java.util.List;

import fr.wonder.commons.math.Mathf;

class Functions {
	
	static final List<Function> functions = new ArrayList<>();
	
	static {
		functions.add(new Function("cos") {
			@Override
			float evaluate(float[] args) throws ArithmeticException {
				validateArgCount(this, args, 1);
				return Mathf.cos(args[0]);
			}
		});
		functions.add(new Function("sin") {
			@Override
			float evaluate(float[] args) throws ArithmeticException {
				validateArgCount(this, args, 1);
				return Mathf.sin(args[0]);
			}
		});
		functions.add(new Function("tan") {
			@Override
			float evaluate(float[] args) throws ArithmeticException {
				validateArgCount(this, args, 1);
				float cos = Mathf.cos(args[0]);
				if(cos == 0)
					throw new ArithmeticException(valueErrString(this, args[0]));
				return Mathf.sin(args[0])/cos;
			}
		});
		functions.add(new Function("ln") {
			@Override
			float evaluate(float[] args) throws ArithmeticException {
				validateArgCount(this, args, 1);
				if(args[0] <= 0)
					throw new ArithmeticException(valueErrString(this, args[0]));
				return Mathf.ln(args[0]);
			}
		});
		functions.add(new Function("log") {
			@Override
			float evaluate(float[] args) throws ArithmeticException {
				validateArgCount(this, args, 2);
				if(args[0] <= 0)
					throw new ArithmeticException(valueErrString(this, args[0]));
				if(args[1] <= 0)
					throw new ArithmeticException(valueErrString(this, args[1]));
				return Mathf.log(args[0], args[1]);
			}
		});
		
	}
	
	private static void validateArgCount(Function f, float[] args, int argCount) throws ArithmeticException {
		if(args.length != argCount)
			throw new ArithmeticException(argErrString(f, argCount));
	}
	private static String argErrString(Function f, int arg) {
		return f.name + " function takes " + arg + " argument(s)!";
	}
	private static String valueErrString(Function f, float value) {
		return "Cannot evaluate " + f.name + " for invalid argument " + value + " !";
	}
	
	static Function getFunction(String s, int start, int end) throws ArithmeticException {
		String funcName = s.substring(start, end);
		for(Function f : functions) {
			if(f.name.contentEquals(funcName)) {
				return f;
			}
		}
		throw new ArithmeticException("Unknown function name!" + Expressions.getErrorString(s, start));
	}
	
}
