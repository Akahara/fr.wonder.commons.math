package fr.wonder.commons.math;

public class Vec2 {
	
	/** Float component of the vector  */
	public float x, y;
	
	/** 
	 * Default constructor, xy = xy
	 * @param x the x component of the vector
	 * @param y the y component of the vector
	 */
	public Vec2(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	/** Default constructor, xy = 0 */
	public Vec2() { this(0, 0); }
	/**
	 * Magnitude constructor, xy = mag
	 * @param mag both x and y component of the vector
	 */
	public Vec2(float mag) { this(mag, mag); }
	/**
	 * Copy constructor, xy = v.xy (copied, not pointer)
	 * @param v the vector to copy the components from
	 */
	public Vec2(Vec2 v) { this(v.x, v.y); }
	
	/**
	 * Does not affect the used instance.
	 * @param v the vector to add
	 * @return a new vector that is the sum of the 2 parameters
	 */
	public Vec2 add(Vec2 v) { return new Vec2(x+v.x, y+v.y); }
	public Vec2 add(float x, float y) { return new Vec2(this.x+x, this.y+y); }
	/**
	 * Does not affect the used instance.
	 * @param f the float to multiply the vector coordinates with
	 * @return a new vector that is the product of the 2 parameters
	 */
	public Vec2 multiply(float f) { return new Vec2(x*f, y*f); }
	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the negative of this instance
	 */
	public Vec2 neg() { return new Vec2(-x, -y); }
	
	/**
	 * Calculate the squared length of this vector,
	 * <br>length^2 = x^2 + y^2
	 * @return the squared length of this vector instance
	 */
	public float lengthSquared() { return x*x + y*y; }
	/**
	 * Calculate the length of this vector,
	 * <br>length = sqrt( x^2 + y^2 )
	 * @return the length of this vector instance
	 */
	public float length() { return Mathf.sqrt(lengthSquared()); }
	
	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the normalized version of this vector
	 */
	public Vec2 normalized() {
		float l = length();
		return new Vec2(x/l, y/l);
	}
	
	/**
	 * Returns a new Vec2i which is the rounded version of this instance.
	 * The float components are simply casted to their int version.
	 * @return the rounded vector
	 * @see Mathf#round(Vec2) alternate rounding method
	 */
	public Vec2i round() { return new Vec2i((int) x, (int) y); }
	
	/**
	 * Returns true if the two objects are both Vec2 and each of their
	 * components have the same value. If obj is null this will return
	 * false.
	 * <br>Beware that 1e-30 is not 0 and in some cases, after some
	 * math vectors that you expect to be equal won't.
	 * 
	 * @param obj the other instance to check, may be null
	 * @return true if obj is an instance of Vec2 and xy == obj.xy
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vec2 &&
				x == ((Vec2) obj).x && y == ((Vec2) obj).y;
	}
	
	/**
	 * Returns a string of format [x, y] with xy in scientific
	 * notations with 2 decimals.
	 * @return a string representing this vector
	 */
	@Override
	public String toString() {
		return String.format("[%+.2e %+.2e]", x, y);
	}
	
}
