package fr.wonder.commons.math.ease;

/**
 * Generators are wrappers on transforms.
 * <p>
 * A generator holds a base value {@code x} that can be changed and a
 * transformation that will be applied to x when using the {@code get} method.
 * <p>
 * Generators can be very handy when dealing with animations, with x being
 * interpreted as a time/duration.
 */
public class Generator {

	protected float currentX;
	protected Transform transform = Transform.identity();

	public Generator() {

	}

	public Generator then(Transform after) {
		this.transform = transform.andThen(after);
		return this;
	}

	public float getCurrent() {
		return currentX;
	}

	public float get() {
		return transform.apply(currentX);
	}

	public Generator setCurrent(float x) {
		this.currentX = x;
		return this;
	}

	public Generator advance(float delta) {
		return setCurrent(currentX + delta);
	}

	public float next(float delta) {
		return advance(delta).get();
	}

}
