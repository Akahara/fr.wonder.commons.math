package fr.wonder.commons.math.vectors;

import fr.wonder.commons.math.Mathf;

public class Vec2i {
	
	/** Integer component of the vector */
	public int x, y;
	
	/** 
	 * Default constructor, xy = xy
	 * @param x the x component of the vector
	 * @param y the y component of the vector
	 */
	public Vec2i(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/** Default constructor, xy = 0 */
	public Vec2i() { this(0, 0); }
	/**
	 * Magnitude constructor, xy = mag
	 * @param mag both x and y component of the vector
	 */
	public Vec2i(int mag) { this(mag, mag); }
	/**
	 * Copy constructor, xy = v.xy (copied, not pointer)
	 * @param v the vector to copy the components from
	 */
	public Vec2i(Vec2i v) { this(v.x, v.y); }
	
	/**
	 * Does not affect the used instance.
	 * @param v the vector to add
	 * @return a new vector that is the sum of the 2 parameters
	 */
	public Vec2i add(Vec2i v) { return new Vec2i(x+v.x, y+v.y); }
	public Vec2i add(int x, int y) { return new Vec2i(this.x+x, this.y+y); }
	/**
	 * Does not affect the used instance.
	 * @param v the vector to subtract
	 * @return a new vector that is the difference of the 2 parameters
	 */
	public Vec2i sub(Vec2i v) { return new Vec2i(x-v.x, y-v.y); }
	public Vec2i sub(int x, int y) { return new Vec2i(this.x-x, this.y-y); }
	/**
	 * Does not affect the used instance.
	 * @param f the float to multiply the vector coordinates with
	 * @return a new vector that is the product of the 2 parameters
	 */
	public Vec2i multiply(int i) { return new Vec2i(x*i, y*i); }
	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the negative of this instance
	 */
	public Vec2i neg() { return new Vec2i(-x, -y); }
	/**
	 * Does not affect the used instance.
	 * @return a new vector that components are the absolute of this instance coords
	 */
	public Vec2i abs() { return new Vec2i(Mathf.abs(x), Mathf.abs(y)); }
	/**
	 * Calculate the squared length of this vector,
	 * <br><code>length^2 = x^2 + y^2</code>
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
	 * Two Vec2i equal each other if and only if each of their
	 * components share the same value.
	 * <blockquote>	<code>(3, 2).equals(3, 2)</code> is true even if the instances are different
	 * <br>			<code>(1, 2).equals(3, 2)</code> is false
	 * </blockquote>
	 * @param obj the object to compare to
	 * @return true if the two instances are equal
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vec2i &&
				x == ((Vec2i) obj).x && y == ((Vec2i) obj).y;
	}
	
	@Override
	public int hashCode() {
		return (31+x)*31+y;
	}
	
	/**
	 * Returns a string of format [x, y] with xy in scientific
	 * notations with 2 decimals.
	 * @return a string representing this vector
	 */
	@Override
	public String toString() {
		return String.format("[%+.2e %+.2e]", (float)x, (float)y);
	}
	
}
