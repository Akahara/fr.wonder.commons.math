package fr.wonder.commons.math.vectors;

import fr.wonder.commons.math.Mathf;

public class Vec4 {

	/** Float component of the vector */
	public float x, y, z, w;
	
	/**
	 * Default constructor, xyz = xyz
	 * @param x x component of the vector
	 * @param y y component of the vector
	 * @param z z component of the vector
	 * @param w w component of the vector
	 */
	public Vec4(float x, float y, float z, float w) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}
	
	/** Default constructor, xyzw = 0 */
	public Vec4() { this(0, 0, 0, 0); }
	/** 
	 * Magnitude constructor, xyzw = mag
	 * @param mag the 4 components of the vector
	 */
	public Vec4(float mag) { this(mag, mag, mag, mag); }
	/**
	 * Copy constructor, xyz = v.xyz (copied, not pointer)
	 * @param v the vector to copy the components from
	 */
	public Vec4(Vec4 v) { this(v.x, v.y, v.z, v.w); }
	/**
	 * Extension constructor, xyz = v.xyz, w = w
	 * @param v the vector to copy the x, y and z components from
	 * @param w the new w component
	 */
	public Vec4(Vec3 v, float w) { this(v.x, v.y, v.z, w); }
	
	/**
	 * Does not affect the used instance.
	 * @param v the vector to add
	 * @return a new vector that is the sum of the 2 parameters
	 */
	public Vec4 add(Vec4 v) { return new Vec4(x+v.x, y+v.y, z+v.z, z+v.z); }
	public Vec4 add(float x, float y, float z, float w) { return new Vec4(this.x+x, this.y+y, this.z+z, this.w+w); }
	/**
	 * Does not affect the used instance.
	 * @param f the float to multiply the vector coordinates with
	 * @return a new vector that is the product of the 2 parameters
	 */
	public Vec4 multiply(float f) { return new Vec4(x*f, y*f, z*f, w*f); }
	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the negative of this instance
	 */
	public Vec4 neg() { return new Vec4(-x, -y, -z, -w); }
	
	/**
	 * Calculate the squared length of this vector,
	 * <br>length^2 = x^2 + y^2 + z^2 + w^2
	 * @return the squared length of this vector instance
	 */
	public float lengthSquared() { return x*x + y*y + z*z + w*w; }
	/**
	 * Calculate the length of this vector,
	 * <br>length = sqrt( x^2 + y^2 + z^2 + w^2 )
	 * @return the length of this vector instance
	 */
	public float length() { return Mathf.sqrt(lengthSquared()); }

	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the normalized version of this vector
	 */
	public Vec4 normalized() {
		float l = length();
		return new Vec4(x/l, y/l, z/l, w/l);
	}
	
	/**
	 * Returns true if the two objects are both Vec4 and each of their
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
		return obj != null && obj instanceof Vec4 &&
				x == ((Vec4) obj).x && y == ((Vec4) obj).y &&
				z == ((Vec4) obj).z && w == ((Vec4) obj).w;
	}
	
	@Override
	public int hashCode() {
		return (((31+Float.floatToIntBits(x))*31+Float.floatToIntBits(y))*31
				+Float.floatToIntBits(z))*31+Float.floatToIntBits(w);
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
