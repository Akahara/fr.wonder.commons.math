package fr.wonder.commons.math.expressions;

import java.util.ArrayList;
import java.util.List;

import fr.wonder.commons.math.Mathf;

class ValueExpression implements Expression {
	
	static final float PI_CONSTANT = 3.141592f;
	static final float E_CONSTANT = 2.72f;

	final List<UnknownMonome> monomes = new ArrayList<>();
	
	ValueExpression(String s, int start, int end, char... unknowns) throws ArithmeticException {
		// constants
		String valueString = s.substring(start, end);
		if(valueString.equals("pi")) {
			monomes.add(new UnknownMonome(PI_CONSTANT));
			return;
		} else if(valueString.equals("e")) {
			monomes.add(new UnknownMonome(E_CONSTANT));
			return;
		}
		
		// unknowns
		char chr = s.charAt(start);
		for(char u : unknowns) {
			if(chr == u) {
				monomes.add(new UnknownMonome(1, u));
				return;
			}
		}
		// actual decimal value
		if(Mathf.isFloatString(valueString)) {
			monomes.add(new UnknownMonome(Mathf.parseFloat(valueString)));
			return;
		}
		
		throw new ArithmeticException("Cannot parse a value!" + Expressions.getErrorString(s, start));
	}
	
	public ValueExpression(float realValue) {
		monomes.add(new UnknownMonome(realValue));
	}
	
	ValueExpression() {}

	void pack() {
		for(int i = 0; i < monomes.size(); i++) {
			UnknownMonome monome = monomes.get(i);
			float ncoef = monome.realCoeficient;
			for(int j = i+1; j < monomes.size(); ) {
				if(monomes.get(j).equals(monome)) {
					ncoef += monomes.get(j).realCoeficient;
					monomes.remove(j);
				} else
					j++;
			}
			if(ncoef != monome.realCoeficient) {
				if(ncoef != 0)
					monomes.set(i, new UnknownMonome(ncoef, monome.unknowns));
				else {
					monomes.remove(i);
					i--;
				}
			}
		}
	}
	
	public UnknownMonome getMonome(UnknownMonome monome) {
		for(UnknownMonome m : monomes) {
			if(m.equals(monome))
				return m;
		}
		return null;
	}

	@Override
	public float evaluate(UnknownValue... unknowns) throws ArithmeticException {
		float value = 0;
		for(UnknownMonome m : monomes)
			value += m.evaluate(unknowns);
		return value;
	}
	
	@Override
	public ValueExpression evaluate() {
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		if(monomes.size() > 1)
			sb.append('(');
		for(int i = 0; i < monomes.size(); i++) {
			sb.append(monomes.get(i));
			if(i != monomes.size()-1 && monomes.get(i+1).realCoeficient>0)
				sb.append("+");
		}
		if(monomes.size() > 1)
			sb.append(')');
		return sb.toString();
	}
	
	@Override
	public String toCalculusString() {
		return toString();
	}
	
}
