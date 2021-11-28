package fr.wonder.commons.math.ease;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

/**
 * Transforms are applications x->f(x) over the floats.
 * <p>
 * Transforms are meant to be composed and applied, they can be very handy for
 * animations and such (see the {@link Generator} utility if you are interested
 * in animations).
 * <p>
 * Collections of useful transforms can be found in {@link Transforms} and
 * {@link EaseTransforms}.
 * <p>
 * Most transformations have their input and output in range [0,1]. Use
 * {@link #normalizeLinear(float, float)} to normalize inputs if needed and
 * {@link #scaleLinear(float, float)} to scale back outputs.
 */
@FunctionalInterface
public interface Transform {

	public static Transform identity() {
		return x -> x;
	}


	public float apply(float x);


	public default Transform compose(Transform before) {
		Objects.requireNonNull(before);
		return x -> apply(before.apply(x));
	}

	public default Transform composeF(Function<Float, Float> func) {
		return compose(Transforms.fromFunctionF(func));
	}
	
	public default Transform composeD(Function<Double, Double> func) {
		return compose(Transforms.fromFunctionD(func));
	}

	public default Transform andThen(Transform after) {
		Objects.requireNonNull(after);
		return x -> after.apply(apply(x));
	}

	public default UnaryOperator<Float> toUnaryOperator() {
		return x -> apply(x);
	}

	public default Transform normalizeLinear(float min, float max) {
		return andThen(Transforms.linear(1 / (max - min), -min / (max - min)));
	}

	public default Transform scaleLinear(float min, float max) {
		return andThen(Transforms.linear(max - min, min));
	}

}
