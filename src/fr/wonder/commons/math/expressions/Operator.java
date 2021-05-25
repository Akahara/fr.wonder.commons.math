package fr.wonder.commons.math.expressions;

import java.util.List;

import fr.wonder.commons.math.Mathf;

enum Operator {

	addition('+', 0) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			if(l instanceof ValueExpression && r instanceof ValueExpression) {
				ValueExpression exp = new ValueExpression();
				for(UnknownMonome lm : ((ValueExpression)l).monomes)
					exp.monomes.add(lm);
				for(UnknownMonome rm : ((ValueExpression)r).monomes)
					exp.monomes.add(rm);
				exp.pack();
				return exp;
			}
			return super.evaluate(l, r);
		}
		@Override
		float evaluate(float l, float r) {
			return l+r;
		}
	},
	
	substraction('-', 0, false, true) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			if(l == null)
				l = new ValueExpression(0);
			if(l instanceof ValueExpression && r instanceof ValueExpression) {
				ValueExpression exp = new ValueExpression();
				for(UnknownMonome lm : ((ValueExpression)l).monomes)
					exp.monomes.add(lm);
				for(UnknownMonome rm : ((ValueExpression)r).monomes)
					exp.monomes.add(new UnknownMonome(-rm.realCoeficient, rm.unknowns));
				exp.pack();
				return exp;
			}
			return super.evaluate(l, r);
		}
		@Override
		float evaluate(float l, float r) {
			return l-r;
		}
	},
	
	multiplication('*', 1) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			if(l instanceof ValueExpression && r instanceof ValueExpression) {
				ValueExpression exp = new ValueExpression();
				for(UnknownMonome lu : ((ValueExpression)l).monomes) {
					for(UnknownMonome ru : ((ValueExpression)r).monomes) {
						UnknownMonome monome = new UnknownMonome(lu.realCoeficient*ru.realCoeficient);
						monome.unknowns.addAll(lu.unknowns);
						monome.unknowns.addAll(ru.unknowns);
						monome.pack();
						exp.monomes.add(monome);
					}
				}
				exp.pack();
				return exp;
			}
			return super.evaluate(l, r);
		}
		@Override
		float evaluate(float l, float r) {
			return l*r;
		}
	},
	
	division('/', 1) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			if(l instanceof ValueExpression && r instanceof ValueExpression) {
				List<UnknownMonome> rm = ((ValueExpression) r).monomes;
				if(rm.size() == 1) {
					UnknownMonome monome = rm.get(0);
					UnknownMonome newMonome = new UnknownMonome(1/monome.realCoeficient);
					for(ExpUnknown u : monome.unknowns)
						newMonome.unknowns.add(new ExpUnknown(u.unknown, -u.exponent));
					ValueExpression nr = new ValueExpression();
					nr.monomes.add(newMonome);
					return Operator.multiplication.evaluate(l, nr);
				}
				ValueExpression lv = (ValueExpression) l;
				if(rm.size() == lv.monomes.size()) {
					UnknownMonome monome = lv.getMonome(rm.get(0));
					if(monome == null)
						return super.evaluate(l, r);
					float ratio = monome.realCoeficient/rm.get(0).realCoeficient;
					for(int i = 1; i < rm.size(); i++) {
						if(lv.getMonome(rm.get(i)).realCoeficient/rm.get(i).realCoeficient != ratio)
							return super.evaluate(l, r);
					}
					return new ValueExpression(ratio);
				}
			}
			return super.evaluate(l, r);
		}
		@Override
		float evaluate(float l, float r) {
			if(r == 0)
				throw new ArithmeticException("Cannot perform a division by 0.");
			return l/r;
		}
	},
	
	exponent('^', 2) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			if(l instanceof ValueExpression && r instanceof ValueExpression) {
				List<UnknownMonome> rm = ((ValueExpression) r).monomes;
				List<UnknownMonome> lm = ((ValueExpression) l).monomes;
				if(rm.size() == 1 && lm.size() == 1 && rm.get(0).unknowns.isEmpty()) {
					float exponent = rm.get(0).realCoeficient;
					UnknownMonome baseMonome = lm.get(0);
					UnknownMonome newMonome = new UnknownMonome(Mathf.pow(baseMonome.realCoeficient, exponent));
					for(ExpUnknown u : baseMonome.unknowns)
						newMonome.unknowns.add(new ExpUnknown(u.unknown, u.exponent*exponent));
					ValueExpression value = new ValueExpression();
					value.monomes.add(newMonome);
					return value;
				}
			}
			
			return super.evaluate(l, r);
		}
		@Override
		float evaluate(float l, float r) {
			return Mathf.pow(l, r);
		}
	},
	
	factorial('!', 4, true, false) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			ValueExpression exp = new ValueExpression();
//			float lValue = l.getFloatValue();
//			int lint = (int) lValue;
//			if(lint != lValue)
//				throw new ArithmeticException("Cannot calculate a factorial of a non-integer number.");
//			exp.setFloatValue(Mathf.fact(lint));
			return exp;
		}
		@Override
		float evaluate(float l, float r) {
			int lint = (int) l;
			if(lint != l)
				throw new ArithmeticException("Cannot calculate a factorial of a non-integer number.");
			return Mathf.fact(lint);
		}
	},
	
	modulo('%', 4) {
		@Override
		Expression evaluate(Expression l, Expression r) {
			ValueExpression exp = new ValueExpression();
//			float rValue = r.getFloatValue();
//			if(rValue == 0)
//				throw new ArithmeticException("Cannot perform a division by 0.");
//			exp.setFloatValue(Mathf.mod(l.getFloatValue(), rValue));
			return exp;
		}
		@Override
		float evaluate(float l, float r) {
			if(r == 0)
				throw new ArithmeticException("Cannot perform a division by 0.");
			return Mathf.mod(l, r);
		}
	};
	
	private static final Operator[] operators = values();
	
	final char chr;
	final int priority;
	final boolean needLeftPart, needRightPart;
	
	Operator(char chr, int priority) {
		this(chr, priority, true, true);
	}
	
	Operator(char chr, int priority, boolean needLeftPart, boolean needRightPart) {
		this.chr = chr;
		this.priority = priority;
		this.needLeftPart = needLeftPart;
		this.needRightPart = needRightPart;
	}
	
	Expression evaluate(Expression leftSide, Expression rightSide) throws ArithmeticException {
		return new CalculusExpression(leftSide, rightSide, this);
	}
	
	float evaluate(float l, float r) throws ArithmeticException {
		throw new ArithmeticException("Inexisting operator " + this);
	}

	public static Operator getOperator(char c) {
		for(Operator op : operators) {
			if(c == op.chr)
				return op;
		}
		return null;
	}
	
}
