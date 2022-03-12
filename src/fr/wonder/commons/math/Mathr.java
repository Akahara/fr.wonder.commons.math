package fr.wonder.commons.math;

import static fr.wonder.commons.math.Mathf.*;

public class Mathr {

	public static float rand1(float s) {
		return fract(sin(s*12.9898f) * 43758.5453f);
	}
	public static float rand2(float s) {
		return fract(sin(s*78.233f ) * 43758.5453f);
	}
	
}
