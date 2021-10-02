package fr.wonder.commons.math.vectors;

import fr.wonder.commons.math.Mathf;

public class Vec4i {

	/** Float component of the vector */
	public int x, y, z, w;
	
	/**
	 * Default constructor, xyz = xyz
	 * @param x x component of the vector
	 * @param y y component of the vector
	 * @param z z component of the vector
	 * @param w w component of the vector
	 */
	public Vec4i(int x, int y, int z, int w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/** Default constructor, xyzw = 0 */
	public Vec4i() { this(0, 0, 0, 0); }
	/** 
	 * Magnitude constructor, xyzw = mag
	 * @param mag the 4i components of the vector
	 */
	public Vec4i(int mag) { this(mag, mag, mag, mag); }
	/**
	 * Copy constructor, xyz = v.xyz (copied, not pointer)
	 * @param v the vector to copy the components from
	 */
	public Vec4i(Vec4i v) { this(v.x, v.y, v.z, v.w); }
	
	/**
	 * Does not affect the used instance.
	 * @param v the vector to add
	 * @return a new vector that is the sum of the 2 parameters
	 */
	public Vec4i add(Vec4i v) { return new Vec4i(x+v.x, y+v.y, z+v.z, z+v.z); }
	public Vec4i add(int x, int y, int z, int w) { return new Vec4i(this.x+x, this.y+y, this.z+z, this.w+w); }
	/**
	 * Does not affect the used instance.
	 * @param f the int to multiply the vector coordinates with
	 * @return a new vector that is the product of the 2 parameters
	 */
	public Vec4i multiply(int f) { return new Vec4i(x*f, y*f, z*f, w*f); }
	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the negative of this instance
	 */
	public Vec4i neg() { return new Vec4i(-x, -y, -z, -w); }
	
	/**
	 * Calculate the squared length of this vector,
	 * <br>length^2 = x^2 + y^2 + z^2 + w^2
	 * @return the squared length of this vector instance
	 */
	public int lengthSquared() { return x*x + y*y + z*z + w*w; }
	/**
	 * Calculate the length of this vector,
	 * <br>length = sqrt( x^2 + y^2 + z^2 + w^2 )
	 * @return the length of this vector instance
	 */
	public float length() { return Mathf.sqrt(lengthSquared()); }
	
	/**
	 * Returns true if the two objects are both Vec4i and each of their
	 * components have the same value. If obj is null this will return
	 * false.
	 * <br>Beware that 1e-30 is not 0 and in some cases, after some
	 * math vectors that you expect to be equal won't.
	 * 
	 * @param obj the other instance to check, may be null
	 * @return true if obj is an instance of Vec2 and xyzw == obj.xyzw
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vec4i &&
				x == ((Vec4i) obj).x && y == ((Vec4i) obj).y &&
				z == ((Vec4i) obj).z && w == ((Vec4i) obj).w;
	}
	
	/**
	 * Returns a string of format [x, y, z, w] with xyzw in scientific
	 * notations with 2 decimals.
	 * @return a string representing this vector
	 */
	@Override
	public String toString() {
		return String.format("[%+.2e %+.2e %+.2e %+.2e]", x, y, z, w);
	}
	
}
