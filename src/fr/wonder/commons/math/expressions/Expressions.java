package fr.wonder.commons.math.expressions;

import java.util.ArrayList;
import java.util.List;

import fr.wonder.commons.math.Mathf;
import fr.wonder.commons.math.Vec2i;

public class Expressions {
	
	//TODO allow for writings such as 3xy^2
	
	public static UnknownValue fixValue(char c, float value) {
		return new UnknownValue(c, value);
	}
	
	public static Expression evaluate(String s, char... unknowns) throws ArithmeticException {
		String clearedString = s.replaceAll(" ", "").replaceAll("\t", "");
		return evaluate(clearedString, 0, clearedString.length(), unknowns);
	}
	
	public static Vec2i trimParanthesis(String s, int start, int end) throws ArithmeticException {
		Vec2i scope = new Vec2i(start, end);
		Vec2i subScope;
		while(true) {
			subScope = getHighestParenthesis(s, scope.x, scope.y);
			if(subScope.x == scope.x && subScope.y == scope.y-1) {
				scope.x++;
				scope.y--;
			} else
				break;
		}
		return scope;
	}
	
	private static Expression evaluate(String s, int start, int end, char... unknowns) throws ArithmeticException {
		Vec2i trimPos = trimParanthesis(s, start, end);
		if(trimPos.x == trimPos.y)
			throw new ArithmeticException("An empty body has been encountered!" + getErrorString(s, trimPos.x));
		
		List<Vec2i> groups = new ArrayList<>();
		Vec2i paranthesis = new Vec2i(trimPos.x-1);
		while((paranthesis = getHighestParenthesis(s, paranthesis.y+1, trimPos.y)).x != -1)
			groups.add(paranthesis);
		
		int lastGroup = 0;
		int lowestOperatorPos = -1;
		Operator lowestOperator = null;
		
		for(int i = trimPos.x; i < trimPos.y; i++) {
			for(int group = lastGroup; group < groups.size(); group++) {
				if(groups.get(group).x == i) {
					i = groups.get(group).y;
					break;
				}
			}
			Operator op = Operator.getOperator(s.charAt(i));
			if(op != null && (!op.needLeftPart || operatorHasLeftSide(s, i)) && (lowestOperator == null
					|| op.priority <= lowestOperator.priority)) {
				lowestOperator = op;
				lowestOperatorPos = i;
			}
		}
		
		if(lowestOperator != null) {
			return new CalculusExpression(
					evaluate(s, trimPos.x, lowestOperatorPos, unknowns),
					evaluate(s, lowestOperatorPos+1, trimPos.y, unknowns),
					lowestOperator);
		}
		
		paranthesis = getHighestParenthesis(s, trimPos.x, trimPos.y);
		if(paranthesis.x != paranthesis.y) {
			if(paranthesis.y-paranthesis.x == 1)
				throw new ArithmeticException("Empty function parameter!" + getErrorString(s, paranthesis.x));
			Function function = Functions.getFunction(s, trimPos.x, paranthesis.x);
			int lastArg = paranthesis.x;
			int argCount = 1;
			paranthesis.x++;
			for(int i = paranthesis.x; i < paranthesis.y; i++) {
				if(s.charAt(i) == ',') {
					if(i == lastArg+1 || i == paranthesis.y-1)
						throw new ArithmeticException("Empty function parameter!" + getErrorString(s, i));
					lastArg = i;
					argCount++;
				}
			}
			
			Expression[] arguments = new Expression[argCount];
			argCount = 0;
			lastArg = paranthesis.x;
			for(int i = paranthesis.x; i < paranthesis.y; i++) {
				if(s.charAt(i) == ',') {
					arguments[argCount] = evaluate(s, lastArg, i, unknowns);
					argCount++;
					lastArg = i+1;
				}
			}
			arguments[argCount] = evaluate(s, lastArg, paranthesis.y, unknowns);
			
			return new FunctionExpression(function, arguments);
		}
		
		return new ValueExpression(s, trimPos.x, trimPos.y, unknowns);
	}
	
	private static boolean operatorHasLeftSide(String s, int operatorPos) {
		return operatorPos > 0 && s.charAt(operatorPos-1) != '(';
	}
	
	public static Vec2i getHighestParenthesis(String s, int start, int end) throws ArithmeticException {
		Vec2i positions = new Vec2i(-1);
		int openedParanthesis = 0;
		
		for(int i = start; i < end; i++) {
			if(s.charAt(i) == '(') {
				if(positions.x == -1)
					positions.x = i;
				openedParanthesis++;
			} else if(s.charAt(i) == ')') {
				openedParanthesis--;
				if(openedParanthesis < 0)
					throw new ArithmeticException("Unopened parenthesis :" + getErrorString(s, i));
				
				if(openedParanthesis == 0) {
					positions.y = i;
					return positions;
				}
			}
		}
		
		if(positions.y == -1 && positions.x != -1)
			throw new ArithmeticException("Unclosed parenthesis :" + getErrorString(s, positions.x));
		
		return positions;
	}

	public static String getErrorString(String s, int errIndex) {
		int substringBegin = Mathf.max(0, errIndex-2);
		int baseBegin = substringBegin;
		for(int i = substringBegin-1; i >= 0; i--) {
			char c = s.charAt(i);
			if(Operator.getOperator(c) != null || c == ')')
				break;
			substringBegin--;
		}
		String token = s.substring(substringBegin, Mathf.min(s.length(), baseBegin+6)) + " ...";
		return "\n\t" + token + "\n\t" + " ".repeat(errIndex-substringBegin) + "^";
	}
}
