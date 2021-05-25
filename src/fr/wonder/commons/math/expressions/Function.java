package fr.wonder.commons.math.expressions;

abstract class Function {
	
	final String name;
	
	Function(String name) {
		this.name = name;
	}
	
	abstract float evaluate(float[] args);
	
}
