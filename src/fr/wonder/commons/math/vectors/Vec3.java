package fr.wonder.commons.math.vectors;

import fr.wonder.commons.math.Mathf;

public class Vec3 {
	
	/** Float component of the vector */
	public float x, y, z;
	
	/**
	 * Default constructor, xyz = xyz
	 * @param x x component of the vector
	 * @param y y component of the vector
	 * @param z z component of the vector
	 */
	public Vec3(float x, float y, float z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/** Default constructor, xyz = 0 */
	public Vec3() { this(0, 0, 0); }
	/** 
	 * Magnitude constructor, xyz = mag
	 * @param mag the 3 components of the vector
	 */
	public Vec3(float mag) { this(mag, mag, mag); }
	/**
	 * Copy constructor, xyz = v.xyz (copied, not pointer)
	 * @param v the vector to copy the components from
	 */
	public Vec3(Vec3 v) { this(v.x, v.y, v.z); }
	/**
	 * Extension constructor, xy = v.xy, z = z
	 * @param v the vector to copy the x and y components from
	 * @param z the new z component
	 */
	public Vec3(Vec2 v, float z) { this(v.x, v.y, z); }

	/**
	 * Creates a new Vector2 taking only the x and y components of this instance
	 * @return the x and y components of this vector
	 */
	public Vec2 xy() { return new Vec2(x, y); }
	/**
	 * Creates a new Vector2 taking only the x and z components of this instance
	 * @return the y and z components of this vector
	 */
	public Vec2 xz() { return new Vec2(x, z); }
	/**
	 * Creates a new Vector2 taking only the y and z components of this instance
	 * @return the y and z components of this vector
	 */
	public Vec2 yz() { return new Vec2(y, z); }
	
	/**
	 * Does not affect the used instance.
	 * @param v the vector to add
	 * @return a new vector that is the sum of the 2 parameters
	 */
	public Vec3 add(Vec3 v) { return new Vec3(x+v.x, y+v.y, z+v.z); }
	public Vec3 add(float x, float y, float z) { return new Vec3(this.x+x, this.y+y, this.z+z); }
	/**
	 * Does not affect the used instance.
	 * @param f the float to multiply the vector coordinates with
	 * @return a new vector that is the product of the 2 parameters
	 */
	public Vec3 multiply(float f) { return new Vec3(x*f, y*f, z*f); }
	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the negative of this instance
	 */
	public Vec3 neg() { return new Vec3(-x, -y, -z); }
	
	/**
	 * Calculate the squared length of this vector,
	 * <br>length^2 = x^2 + y^2 + z^2
	 * @return the squared length of this vector instance
	 */
	public float lengthSquared() { return x*x + y*y + z*z; }
	/**
	 * Calculate the length of this vector,
	 * <br>length = sqrt( x^2 + y^2 + z^2 )
	 * @return the length of this vector instance
	 */
	public float length() { return Mathf.sqrt(lengthSquared()); }

	/**
	 * Does not affect the used instance.
	 * @return a new vector that is the normalized version of this vector
	 */
	public Vec3 normalized() {
		float l = length();
		return new Vec3(x/l, y/l, z/l);
	}
	
	/**
	 * Returns true if the two objects are both Vec3 and each of their
	 * components have the same value. If obj is null this will return
	 * false.
	 * <br>Beware that 1e-30 is not 0 and in some cases, after some
	 * math vectors that you expect to be equal won't.
	 * 
	 * @param obj the other instance to check, may be null
	 * @return true if obj is an instance of Vec2 and xyz == obj.xyz
	 */
	@Override
	public boolean equals(Object obj) {
		return obj != null && obj instanceof Vec3 &&
				x == ((Vec3) obj).x && y == ((Vec3) obj).y && z == ((Vec3) obj).z;
	}
	
	/**
	 * Returns a string of format [x, y, z] with xyz in scientific
	 * notations with 2 decimals.
	 * @return a string representing this vector
	 */
	@Override
	public String toString() {
		return String.format("[%+.2e %+.2e %+.2e]", x, y, z);
	}
	
}
