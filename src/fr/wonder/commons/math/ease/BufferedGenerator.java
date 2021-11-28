package fr.wonder.commons.math.ease;

/**
 * Simply a generator that can cache the current transformed value.
 * <p>
 * Prefer using this instead of a Generator is it the instance's transformed
 * value will be queried multiple times without change to its base value.
 */
public class BufferedGenerator extends Generator {
	
	protected boolean validCache;
	protected float cached;
	
	public BufferedGenerator() {
		
	}
	
	public float get() {
		boolean c = validCache;
		validCache = true;
		return c ? cached : (cached = super.get());
	}
	
	public Generator setCurrent(float x) {
		this.validCache &= x == currentX;
		this.currentX = x;
		return this;
	}
	
}
