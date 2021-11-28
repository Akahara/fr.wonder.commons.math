package fr.wonder.commons.math.ease;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import fr.wonder.commons.math.Mathf;

/**
 * This class contains generally useful transforms.
 */
public class Transforms {
	
	public static Transform fromFunctionF(Function<Float, Float> u) {
		Objects.requireNonNull(u);
		return x -> u.apply(x);
	}
	
	public static Transform fromFunctionD(Function<Double, Double> u) {
		Objects.requireNonNull(u);
		return x -> (float) (double) u.apply((double) (float) x);
	}
	
	public static Transform fromGenerator(Supplier<Float> u) {
		Objects.requireNonNull(u);
		return x -> u.get();
	}
	
	/** Creates a cubic polynomial: ax^3+bx^2+cx+d */
	public static Transform cubic(float a, float b, float c, float d) {
		return x -> x*x*x*a + x*x*b + x*c + d;
	}

	/** Creates a quadratic polynomial: ax^2+bx+c */
	public static Transform quadratic(float a, float b, float c) {
		return x -> x*x*a + x*b + c;
	}

	/** Creates a linear function: ax+b */
	public static Transform linear(float a, float b) {
		return x -> x*a + b;
	}
	
	public static Transform rescale(float minIn, float maxIn, float minOut, float maxOut) {
		return x -> (x/(maxIn-minIn)-minIn) * (maxOut-minOut) + minOut;
	}
	
	public static Transform smoothstep(float edge0, float edge1) {
		return x -> Mathf.smoothstep(edge0, edge1, x);
	}
	
	public static Transform step(float step) {
		return x -> x < step ? 0 : 1;
	}
	
	public static Transform adaptToFloat(UnaryOperator<Double> function) {
		return x -> (float) (double) function.apply((double) (float) x);
	}
	
	public static Transform cubicBezier(double x1, double y1, double x2, double y2) {
		return adaptToFloat(new Bezier(x1, y1, x2, y2));
	}
	
	public static Transform mod(float m) {
		return x -> x % m;
	}

	public static Transform max(float m) {
		return x -> Math.max(x, m);
	}
	
	public static Transform min(float m) {
		return x -> Math.min(x, m);
	}
	
	public static Transform clamp(float min, float max) {
		return x -> Math.max(Math.min(x, max), min);
	}
	
	/**
	 * Similar to the mod function but inputs exceeding {@code m} or underflowing 0
	 * will 'bounce' in the other direction.
	 * <p>
	 * This may be used to get a infinitely alternating function that goes from 0 to 1
	 * and then from 1 to 0 linearly (using m=1).
	 * <code>bounce_m(x) = |m-(x % (2*m))|</code>
	 */
	public static Transform bounce(float m) {
		return x -> Math.abs(m-(x % (2*m)));
	}
	
}
