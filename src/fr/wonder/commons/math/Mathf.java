package fr.wonder.commons.math;

import java.util.Random;

import fr.wonder.commons.math.vectors.Vec2;
import fr.wonder.commons.math.vectors.Vec2i;
import fr.wonder.commons.math.vectors.Vec3;
import fr.wonder.commons.math.vectors.Vec4;
import fr.wonder.commons.math.vectors.Vec4i;

public class Mathf {
	
	/** Approximated value of PI */
	public static final float PI = (float) Math.PI;
	public static final float TWOPI = 2*PI;
	public static final float GOLDEN_RATIO_CONJ = .61803398f;
	
	private static class SinTableHolder {
		/**
		 * The float table used to calculate cosines and sines, initialized
		 * with 360 values and a maximal error of .018
		 * @see #cos(float)
		 * @see #sin(float)
		 */
		private static float[] sinTable = sinTable(360);
	}
	
	private static class RandomHolder {
		private static final Random random = new Random();
	}
	
	/**
	 * Creates a new sin table used by both sin and cos functions.
	 * <p>
	 * A sin table is a number of pre-calculated sines which can be looked up to
	 * avoid calculating actual sines a big number of times. This comes with a
	 * margin of error, the bigger the table the lower the error and the greater the
	 * first calculation cost.
	 * <p>
	 * This method uses an approximation of the sin function. For a size of 360 the
	 * maximum error is .018<br>
	 * By default the class is loaded with a sin table of size 360.<br>
	 * The table size is rounded to the upper multiple of two if not already one.
	 * 
	 * @param tableSize the size of the sine table
	 */
	public static float[] sinTable(int tableSize) {
		tableSize = (tableSize+1)/2*2;
		float[] table = new float[tableSize];
		// initialize the sine table
		for(int i = 0; i < tableSize/2; i++) {
			for(int n = 0; n < 7; n++) {
				table[i] += pow(-1, n%2)*pow(i/(tableSize/2f)*PI, 2*n+1)/fact(2*n+1);
			}
			table[i+tableSize/2] = - table[i];
		}
		// correct absurd values to be in-bounds, these values can come
		// from the approximation of the sin function
		table[0] = 0f;
		table[tableSize/4] = 1f;
		table[tableSize/2] = 0f;
		table[tableSize*3/4] = -1f;
		return table;
	}
	
	public static int roundToZero(float f) {
		return (int) (f+.5f);
	}
	
	/**
	 * Rounds a Vec2 to a Vec2i using {@link #roundFloat(float)}.
	 * @param v the vector to round
	 * @return the rounded vector
	 * @see Vec2#round() alternate rounding method
	 */
	public static Vec2i round(Vec2 v) { return new Vec2i(roundToZero(v.x), roundToZero(v.y)); }
	
	/**
	 * Returns the factorial of a number, which is defined as :
	 * <blockquote> {@code fact(0) = 1}
	 * <br>{@code fact(n) = n*fact(n-1)} </blockquote>
	 * @param i the value to calculate the factorial
	 * @return the factorial of i
	 */
	public static int fact(int i) {
		int l = 1;
		for(int j = 2; j <= i; j++)
			l *= j;
		return l;
	}
	
	/**
	 * Returns the number of binomial combinations for 'k in n elements'.
	 * @param n the number of elements in the set
	 * @param k the size of the arrangement
	 * @return the number of binomial combinations
	 */
	public static int binomialCombinations(int n, int k) {
		return fact(n)/(fact(k)*fact(n-k));
	}
	
	/**
	 * Returns the number of binomial arrangements for 'k in n elements'.
	 * @param n the number of elements in the set
	 * @param k the size of the arrangement
	 * @return the number of binomial arrangements
	 */
	public static int binomialArrangements(int n, int k) {
		return fact(n)/fact(n-k);
	}
	
	/**
	 * Returns the base raised to the exponent, defaults to the java.lang.Math
	 * implementation and converts the result to a float.
	 * @param base the base
	 * @param exp the exponent
	 * @return base^exp
	 */
	public static float pow(float base, float exp) {
		return (float) Math.pow(base, exp);
	}
	
	/**
	 * Unfortunately there are yet no better implementation of sqrt is this project
	 * than redirecting to {@link Math#sqrt(double)}, though {@link #invSqrt(float)}
	 * may be used instead of dividing by the result of this function.
	 * @param f the float to calculate the float of
	 * @return the square root of f
	 */
	public static float sqrt(float f) {
		return (float) Math.sqrt(f);
	}
	
	/**
	 * Fast inverse square root, quake 3 algorithm
	 * @see <a href='https://en.wikipedia.org/wiki/Fast_inverse_square_root'>The source for this function</a>
	 * @param f a float
	 * @return 1/sqrt(f)
	 */
	public static float invSqrt(float f) {
	    float f2 = 0.5f * f;
	    int i = Float.floatToIntBits(f);
	    i = 0x5f3759df - (i >> 1);
	    f = Float.intBitsToFloat(i);
	    f *= (1.5f - f2 * f * f);
	    return f;
	}
	
	/**
	 * Returns the exponential of a number.
	 * <p>This method uses an approximation that can be found
	 * <a href=https://martin.ankerl.com/2007/02/11/optimized-exponential-functions-for-java>
	 * here</a>. This is slower than {@link #expf(double)} but more precise (around 1.5e-5).
	 * @param x the exponent
	 * @return e^x
	 */
	public static float exp(float x) {
	    long tmp = (long) (1512775 * x + 1072632447);
	    return (float)Double.longBitsToDouble(tmp << 32);
	}
	
	/**
	 * Returns the exponential of a value, this method uses an approximation and although it is
	 * quite fast, it has a bigger error margin than {@link #exp(float)} (around 7.2e-4).
	 * @param x the exponent
	 * @return e^x
	 */
	public static float expf(double x) {
		x = 1d + x / 256d;
		x *= x; x *= x; x *= x; x *= x;
		x *= x; x *= x; x *= x; x *= x;
		return (float)x;
	}
	
	/**
	 * Returns the natural logarithm of a number.
	 * <p>This method uses an approximation that can be found
	 * <a href=https://martin.ankerl.com/2007/02/11/optimized-exponential-functions-for-java>
	 * here</a>.
	 * @param f the float value
	 * @return the natural logarithm of f
	 */
	public static float ln(float f) {
		double x = (Double.doubleToLongBits(f) >> 32);
	    return (float) ((x - 1072632447) / 1512775);
	}
	
	/**
	 * Returns the logarithm in a specific base of a number.
	 * <p>The logarithm for non-e base can be easily calculated as <code>log(f,b) = ln(f)/ln(b)</code>
	 * @param f the float value
	 * @param base the base of the logarithm
	 * @return the logarithm of <code>f</code> in base <code>base</code>
	 */
	public static float log(float f, float base) {
		return ln(f)/ln(base);
	}
	
	/**
	 * Applies the sigmoid (or logistic) function.
	 * <p>The expression of the sigmoid function is : <code>s(x) = 1/(1+e^(-x))</code>
	 * @param x the float value
	 * @return the image of x by the sigmoid function
	 */
	public static float sigmoid(float x) {
		return 1f/(1f+exp(-x));
	}

	/**
	 * Smoothstep function, as defined <a href="https://en.wikipedia.org/wiki/Smoothstep">here</a>.
	 * @param edge0 first edge
	 * @param edge1 second edge
	 * @param x the float to evaluate the smoothstep function at
	 * @return the evaluation of the smoothstep function at x, in range [0,1]
	 */
	public static float smoothstep(float edge0, float edge1, float x) {
		x = clamp((x - edge0) / (edge1 - edge0), 0, 1); 
		return x * x * (3 - 2 * x);
	}
	
	/**
	 * Step function.
	 * <p>When evaluated at {@code edge}, this method returns 1.
	 * @param edge the step edge
	 * @param x the application value
	 * @return 0 if x < edge, 1 otherwise
	 */
	public static float step(float edge, float x) {
		return x < edge ? 0 : 1;
	}
	
	/**
	 * Applies the primitive of the sigmoid function.
	 * The expression of the sigmoid prime is : <code>s'(x) = s(x)*(1-s(x))</code>
	 * @param y the float value
	 * @return the image of x by the sigmoid prime function
	 */
	public static float sigmoidPrime(float y) {
		return sigmoid(y)*(1f-sigmoid(y));
	}
	
	/**
	 * Calculates a cosine using a pre-calculated sine table, precision is not
	 * perfect but the maximum delta between this method and Math.cos is around .018
	 * <br>This method is quite faster than using Math.cos though.
	 * @param rad the angle in radians
	 * @return the cosine of the angle
	 */
	public static float cos(float rad) {
		return SinTableHolder.sinTable[(int) mod(rad/PI*180f+90f, 360)];
	}
	
	/**
	 * Calculates a sine using a pre-calculated sine table, precision is not
	 * perfect but the maximum delta between this method and Math.sin is around .018
	 * <br>This method is quite faster than using Math.sin though.
	 * @param rad the angle in radians
	 * @return the sine of the angle
	 */
	public static float sin(float rad) {
		return SinTableHolder.sinTable[(int) mod(rad/PI*180f, 360)];
	}
	
	/**
	 * Returns the tangent of a value. The tangent is easily calculated
	 * as <code>tan(x) = sin(x)/cos(x)</code>
	 * @param rad the value in radians
	 * @return the tangent of <code>rad</code>
	 */
	public static float tan(float rad) {
		return sin(rad)/cos(rad);
	}
	
	/**
	 * Returns the polar angle of a point in Cartesian coordinates.
	 * @param x first coordinate of the point
	 * @param y second coordinate of the point
	 * @return the angle theta of the representation of the given point in polar coordinates
	 */
	public static float atan2(float x, float y) {
		return (float) Math.atan2(y, x);
	}
	
	/**
	 * Calculate the modulo of 2 numbers, which is the only value x between
	 * 0 and m which is such that <i>f = m*k + x</i> with k a signed integer.
	 * <p>This method is different of the % operator in that it will never return
	 * a negative number : <i>-4%3 = -1</i> however <i>mod(-4, 3) = +2</i>
	 * @param f the base
	 * @param m the modulo
	 * @return the result of the mod operation
	 */
	public static int mod(int f, int m) {
		f %= m;
		if(f < 0)
			f += m;
		return f;
	}
	
	/**
	 * Calculate the modulo of 2 numbers, which is the only value x between
	 * 0 and m which is such that <i>f = m*k + x</i> with k a signed integer.
	 * <p>This method is different of the % operator in that it will never return
	 * a negative number : <i>-4%3 = -1</i> however <i>mod(-4, 3) = +2</i>
	 * @param f the base
	 * @param m the modulo
	 * @return the result of the modulo operation
	 */
	public static float mod(float f, float m) {
		f %= m;
		if(f < 0)
			f += m;
		return f;
	}
	
	/**
	 * Returns whether a string represents a float. The float may be written
	 * using digits 0 to 9, a single decimal dot and a sign.
	 * @param s the string to parse
	 * @return whether or not the string can be parsed as a float
	 */
	public static boolean isFloatString(String s) {
		return s.matches("[+-]?(\\d+(\\.\\d*)?|\\.\\d+)");
	}
	
	/**
	 * Returns the float value a string represents. This calls Float#parseFloat
	 * and silently ignores errors, returning -1 instead.
	 * @param s the string to parse
	 * @return the float value corresponding, or -1 if it could not be parsed
	 */
	public static float parseFloat(String s) {
		try {
			return Float.parseFloat(s);
		} catch(NumberFormatException x) {
			return -1;
		}
	}
	
	/**
	 * Overall same as {@link Math#random()} but using {@link Random#nextFloat()} to
	 * get a float and not a double.
	 * @return a random float in range [0,1[
	 */
	public static float random() {
		return RandomHolder.random.nextFloat();
	}
	
	/**
	 * Return the linear interpolation at t between x and y.
	 * <p>
	 * The formula used is <code>(1-t)x+ty</code>
	 * @param x first value to interpolate with
	 * @param y second value to interpolate with
	 * @param t the fraction to interpolate with
	 * @return the interpolated value
	 */
	public static float lerp(float x, float y, float t) {
		return (1-t)*x+t*y;
	}

	public static Vec2 lerp(Vec2 a, Vec2 b, float x) { return new Vec2((b.x-a.x)*x+a.x, (b.y-a.y)*x+a.y); }
	public static Vec3 lerp(Vec3 a, Vec3 b, float x) { return new Vec3((b.x-a.x)*x+a.x, (b.y-a.y)*x+a.y, (b.z-a.z)*x+a.z); }
	public static Vec4 lerp(Vec4 a, Vec4 b, float x) { return new Vec4((b.x-a.x)*x+a.x, (b.y-a.y)*x+a.y, (b.z-a.z)*x+a.z, (b.w-a.w)*x+a.w); }
	
	public static Vec2i mod(Vec2i v, Vec2i m) { return new Vec2i(mod(v.x, m.x), mod(v.y, m.y)); }
	public static Vec2i mod(Vec2i v, int m) { return new Vec2i(mod(v.x, m), mod(v.y, m)); }
	public static Vec2 mod(Vec2 v, Vec2 m) { return new Vec2(mod(v.x, m.x), mod(v.y, m.y)); }
	public static Vec2 mod(Vec2 v, float m) { return new Vec2(mod(v.x, m), mod(v.y, m)); }
	public static Vec3 mod(Vec3 v, Vec3 m) { return new Vec3(mod(v.x, m.x), mod(v.y, m.y), mod(v.z, m.z)); }
	public static Vec3 mod(Vec3 v, float m) { return new Vec3(mod(v.x, m), mod(v.y, m), mod(v.z, m)); }
	public static Vec4 mod(Vec4 v, Vec4 m) { return new Vec4(mod(v.x, m.x), mod(v.y, m.y), mod(v.z, m.z), mod(v.w, m.w)); }
	public static Vec4 mod(Vec4 v, float m) { return new Vec4(mod(v.x, m), mod(v.y, m), mod(v.z, m), mod(v.w, m)); }
	public static Vec4i mod(Vec4i v, Vec4i m) { return new Vec4i(mod(v.x, m.x), mod(v.y, m.y), mod(v.z, m.z), mod(v.w, m.w)); }
	public static Vec4i mod(Vec4i v, int m) { return new Vec4i(mod(v.x, m), mod(v.y, m), mod(v.z, m), mod(v.w, m)); }
	
	public static int abs(int a) { return (a < 0) ? -a : a; }
	public static float abs(float a) { return (a < 0) ? -a : a; }
	
	public static Vec2i abs(Vec2i v) { return new Vec2i(abs(v.x), abs(v.y)); }
	public static Vec2 abs(Vec2 v) { return new Vec2(abs(v.x), abs(v.y)); }
	public static Vec3 abs(Vec3 v) { return new Vec3(abs(v.x), abs(v.y), abs(v.z)); }
	public static Vec4 abs(Vec4 v) { return new Vec4(abs(v.x), abs(v.y), abs(v.z), abs(v.w)); }
	public static Vec4i abs(Vec4i v) { return new Vec4i(abs(v.x), abs(v.y), abs(v.z), abs(v.w)); }
	
	public static int min(int a, int b) { return a < b ? a : b; }
	public static float min(float a, float b) { return a < b ? a : b; }
	
	public static int min(int[] ints) {
		if(ints == null || ints.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		int m = ints[0];
		for(int i : ints)
			if(i < m)
				m = i;
		return m;
	}
	
	public static float min(float[] floats) {
		if(floats == null || floats.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		float m = floats[0];
		for(float f : floats)
			if(f < m)
				m = f;
		return m;
	}
	
	public static int minIndex(int... ints) {
		if(ints == null || ints.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		int i = 0, m = ints[0];
		for(int j = 0; j < ints.length; j++)
			if(ints[j] < m)
				m = ints[i = j];
		return i;
	}
	
	public static float minIndex(float... floats) {
		if(floats == null || floats.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		int i = 0;
		float m = floats[0];
		for(int j = 0; j < floats.length; j++)
			if(floats[j] < m)
				m = floats[i = j];
		return i;
	}
	
	public static Vec2i min(Vec2i v, Vec2i m) { return new Vec2i(min(v.x, m.x), min(v.y, m.y)); }
	public static Vec2i min(Vec2i v, int m) { return new Vec2i(min(v.x, m), min(v.y, m)); }
	public static Vec2 min(Vec2 v, Vec2 m) { return new Vec2(min(v.x, m.x), min(v.y, m.y)); }
	public static Vec2 min(Vec2 v, float m) { return new Vec2(min(v.x, m), min(v.y, m)); }
	public static Vec3 min(Vec3 v, Vec3 m) { return new Vec3(min(v.x, m.x), min(v.y, m.y), min(v.z, m.z)); }
	public static Vec3 min(Vec3 v, float m) { return new Vec3(min(v.x, m), min(v.y, m), min(v.z, m)); }
	public static Vec4 min(Vec4 v, Vec4 m) { return new Vec4(min(v.x, m.x), min(v.y, m.y), min(v.z, m.z), min(v.w, m.w)); }
	public static Vec4 min(Vec4 v, float m) { return new Vec4(min(v.x, m), min(v.y, m), min(v.z, m), min(v.w, m)); }
	public static Vec4i min(Vec4i v, Vec4i m) { return new Vec4i(min(v.x, m.x), min(v.y, m.y), min(v.z, m.z), min(v.w, m.w)); }
	public static Vec4i min(Vec4i v, int m) { return new Vec4i(min(v.x, m), min(v.y, m), min(v.z, m), min(v.w, m)); }
	public static int min(Vec2i v) { return min(v.x, v.y); }
	public static float min(Vec2 v) { return min(v.x, v.y); }
	public static float min(Vec3 v) { return min(v.x, min(v.y, v.z)); }
	public static float min(Vec4 v) { return min(v.x, min(v.y, min(v.z, v.w))); }
	public static int min(Vec4i v) { return min(v.x, min(v.y, min(v.z, v.w))); }
	
	public static int max(int a, int b) { return a > b ? a : b; }
	public static float max(float a, float b) { return a > b ? a : b; }

	public static int max(int... ints) {
		if(ints == null || ints.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		int m = ints[0];
		for(int i : ints)
			if(i > m)
				m = i;
		return m;
	}
	
	public static float max(float... floats) {
		if(floats == null || floats.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		float m = floats[0];
		for(float f : floats)
			if(f > m)
				m = f;
		return m;
	}
	
	public static int maxIndex(int... ints) {
		if(ints == null || ints.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		int i = 0, m = ints[0];
		for(int j = 0; j < ints.length; j++)
			if(ints[j] > m)
				m = ints[i = j];
		return i;
	}
	
	public static float maxIndex(float... floats) {
		if(floats == null || floats.length == 0)
			throw new IllegalArgumentException("Null or empty array given.");
		int i = 0;
		float m = floats[0];
		for(int j = 0; j < floats.length; j++)
			if(floats[j] > m)
				m = floats[i = j];
		return i;
	}
	
	public static Vec2i max(Vec2i v, Vec2i m) { return new Vec2i(max(v.x, m.x), max(v.y, m.y)); }
	public static Vec2i max(Vec2i v, int m) { return new Vec2i(max(v.x, m), max(v.y, m)); }
	public static Vec2 max(Vec2 v, Vec2 m) { return new Vec2(max(v.x, m.x), max(v.y, m.y)); }
	public static Vec2 max(Vec2 v, float m) { return new Vec2(max(v.x, m), max(v.y, m)); }
	public static Vec3 max(Vec3 v, Vec3 m) { return new Vec3(max(v.x, m.x), max(v.y, m.y), max(v.z, m.z)); }
	public static Vec3 max(Vec3 v, float m) { return new Vec3(max(v.x, m), max(v.y, m), max(v.z, m)); }
	public static Vec4 max(Vec4 v, Vec4 m) { return new Vec4(max(v.x, m.x), max(v.y, m.y), max(v.z, m.z), max(v.w, m.z)); }
	public static Vec4 max(Vec4 v, float m) { return new Vec4(max(v.x, m), max(v.y, m), max(v.z, m), max(v.w, m)); }
	public static Vec4i max(Vec4i v, Vec4i m) { return new Vec4i(max(v.x, m.x), max(v.y, m.y), max(v.z, m.z), max(v.w, m.z)); }
	public static Vec4i max(Vec4i v, int m) { return new Vec4i(max(v.x, m), max(v.y, m), max(v.z, m), max(v.w, m)); }
	public static int max(Vec2i v) { return max(v.x, v.y); }
	public static float max(Vec2 v) { return max(v.x, v.y); }
	public static float max(Vec3 v) { return max(v.x, max(v.y, v.z)); }
	public static float max(Vec4 v) { return max(v.x, max(v.y, max(v.z, v.w))); }
	public static int max(Vec4i v) { return max(v.x, max(v.y, max(v.z, v.w))); }

	public static int clamp(int x, int min, int max) { return min(max, max(min, x)); }
	public static float clamp(float x, float min, float max) { return min(max, max(min, x)); }
	
	public static Vec2i clamp(Vec2i v, Vec2i min, Vec2i max) { return new Vec2i(clamp(v.x, min.x, max.x), clamp(v.y, min.y, max.y)); }
	public static Vec2i clamp(Vec2i v, int min, int max) { return new Vec2i(clamp(v.x, min, max), clamp(v.y, min, max)); }
	public static Vec2 clamp(Vec2 v, Vec2 min, Vec2 max) { return new Vec2(clamp(v.x, min.x, max.y), clamp(v.y, min.y, max.y)); }
	public static Vec2 clamp(Vec2 v, float min, float max) { return new Vec2(clamp(v.x, min, max), clamp(v.y, min, max)); }
	public static Vec3 clamp(Vec3 v, Vec3 min, Vec3 max) { return new Vec3(clamp(v.x, min.x, max.x), clamp(v.y, min.y, max.y), clamp(v.z, min.z, max.z)); }
	public static Vec3 clamp(Vec3 v, float min, float max) { return new Vec3(clamp(v.x, min, max), clamp(v.y, min, max), clamp(v.z, min, max)); }
	public static Vec4 clamp(Vec4 v, Vec4 min, Vec4 max) { return new Vec4(clamp(v.x, min.x, max.x), clamp(v.y, min.y, max.y), clamp(v.z, min.z, max.z), clamp(v.w, min.w, max.w)); }
	public static Vec4 clamp(Vec4 v, float min, float max) { return new Vec4(clamp(v.x, min, max), clamp(v.y, min, max), clamp(v.z, min, max), clamp(v.w, min, max)); }
	public static Vec4i clamp(Vec4i v, Vec4i min, Vec4i max) { return new Vec4i(clamp(v.x, min.x, max.x), clamp(v.y, min.y, max.y), clamp(v.z, min.z, max.z), clamp(v.w, min.w, max.w)); }
	public static Vec4i clamp(Vec4i v, int min, int max) { return new Vec4i(clamp(v.x, min, max), clamp(v.y, min, max), clamp(v.z, min, max), clamp(v.w, min, max)); }

	public static int ceil(float f) {
		return (int) Math.ceil(f);
	}

	public static int floor(float f) {
		return (int) f;
	}
	
	public static float fract(float f) {
		return mod(f, 1);
	}
	
	public static Vec2i random2i(int min, int max) {
		return new Vec2i((int) (random()*(max-min)+min), (int) (random()*(max-min)+min));
	}
	public static Vec2 random2() {
		return new Vec2(random(), random()).normalized();
	}
	public static Vec2 random2(float radius) {
		return random2().multiply(radius);
	}
	public static Vec2 random2(float min, float max) {
		return new Vec2(random()*(max-min)+min, random()*(max-min)+min);
	}
	public static Vec3 random3() {
		return new Vec3(random(), random(), random()).normalized();
	}
	public static Vec3 random3(float radius) {
		return random3().multiply(radius);
	}
	public static Vec3 random3(float min, float max) {
		return new Vec3(random()*(max-min)+min, random()*(max-min)+min, random()*(max-min)+min);
	}
	public static Vec4 random4() {
		return new Vec4(random(), random(), random(), random()).normalized();
	}
	public static Vec4 random4(float radius) {
		return random4().multiply(radius);
	}
	public static Vec4 random4(float min, float max) {
		return new Vec4(random()*(max-min)+min, random()*(max-min)+min,
						random()*(max-min)+min, random()*(max-min)+min);
	}
		
	public static int sum(int... ints) {
		int s = 0;
		for(int i : ints)
			s += i;
		return s;
	}
	
	public static float sum(float... floats) {
		float s = 0;
		for(float f : floats)
			s += f;
		return s;
	}
	
}
