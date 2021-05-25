package fr.wonder.commons.math.expressions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import fr.wonder.commons.math.Mathf;

class UnknownMonome {
	
	static final UnknownMonome realMonome = new UnknownMonome();
	
	final float realCoeficient;
	final List<ExpUnknown> unknowns = new ArrayList<>();
	
	UnknownMonome(float realCoeficient, List<ExpUnknown> unknowns) {
		this.realCoeficient = realCoeficient;
		this.unknowns.addAll(unknowns);
	}
	
	UnknownMonome(float realCoeficient, char unknown) {
		this(realCoeficient, Arrays.asList(new ExpUnknown(unknown, 1)));
	}
	
	UnknownMonome(float realCoeficient) {
		this.realCoeficient = realCoeficient;
	}
	
	UnknownMonome() {
		this(0);
	}
	
	@Override
	public boolean equals(Object o) {
		if(!(o instanceof UnknownMonome))
			return false;
		List<ExpUnknown> other = ((UnknownMonome) o).unknowns;
		if(unknowns.size() != other.size())
			return false;
		for(ExpUnknown u : unknowns)
			if(!other.contains(u))
				return false;
		return true;
	}
	
	@Override
	public String toString() {
		if(unknowns.size() == 0)
			return Mathf.getFloatFormat(realCoeficient);
		StringBuilder sb = new StringBuilder();
		if(realCoeficient == -1)
			sb.append('-');
		else if(realCoeficient != 1)
			sb.append(Mathf.getFloatFormat(realCoeficient));
		for(ExpUnknown exp : unknowns) {
			sb.append(exp.unknown);
			if(exp.exponent != 1) {
				sb.append('^');
				sb.append(Mathf.getFloatFormat(exp.exponent));
			}
		}
		return sb.toString();
	}
	
	public void pack() {
		for(int i = 0; i < unknowns.size(); i++) {
			ExpUnknown u = unknowns.get(i);
			float nexp = u.exponent;
			for(int j = i+1; j < unknowns.size(); ) {
				ExpUnknown n = unknowns.get(j);
				if(n.equals(u)) {
					nexp += n.exponent;
					unknowns.remove(j);
				} else
					j++;
			}
			if(nexp != u.exponent) {
				if(nexp != 0)
					unknowns.set(i, new ExpUnknown(u.unknown, nexp));
				else {
					unknowns.remove(i);
					i--;
				}
			}
		}
	}

	public float evaluate(UnknownValue... us) throws ArithmeticException {
		if(unknowns.size() == 0)
			return realCoeficient;
		float value = 0;
		for(ExpUnknown u : unknowns) {
			boolean found = false;
			for(UnknownValue v : us) {
				if(u.unknown == v.unknown) {
					found = true;
					value += Mathf.pow(v.value, u.exponent);
					break;
				}
			}
			if(!found)
				throw new ArithmeticException("Cannot evaluate an expression missing unknown " + u.unknown + "!");
		}
		return value*realCoeficient;
	}
	
}
