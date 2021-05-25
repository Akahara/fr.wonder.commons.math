package fr.wonder.commons.math.expressions;

class ExpUnknown {
	
	final char unknown;
	final float exponent;
	
	ExpUnknown(char unknown, float exponant) {
		this.unknown = unknown;
		this.exponent = exponant;
	}
	
	@Override
	public boolean equals(Object o) {
		return o instanceof ExpUnknown && unknown == ((ExpUnknown)o).unknown;
	}
	
	@Override
	public String toString() {
		return "("+unknown +'^'+exponent+')';
	}
}


