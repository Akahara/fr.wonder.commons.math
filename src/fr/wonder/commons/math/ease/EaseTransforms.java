package fr.wonder.commons.math.ease;

import static fr.wonder.commons.math.ease.Transforms.cubicBezier;

/**
 * Collection of ease functions.
 * 
 * <p>
 * All of these are defined as Bezier curves, when using Bezier curves
 * prefer using cached values such as the ones in this class instead of
 * creating multiple instances of identical curves.
 * 
 * <p>
 * See <a href="https://easings.net/en#">easings.net</a> for Bezier parameters.
 */
public class EaseTransforms {

	public static final Transform EASEIN_SINE = cubicBezier(0.12, 0, 0.39, 0);
	public static final Transform EASEINOUT_SINE = cubicBezier(0.37, 0, 0.63, 1);
	
	public static final Transform EASEINOUT_CUBIC = cubicBezier(0.65, 0, 0.35, 1);
	public static final Transform EASEIN_CUBIC = cubicBezier(0.32, 0, 0.67, 0);
	
	public static final Transform EASEINOUT_CIRC = cubicBezier(0.85, 0, 0.15, 1);
	public static final Transform EASEIN_CIRC = cubicBezier(0.55, 0, 1, 0.45);
	
	public static final Transform EASEINOUT_QUAD = cubicBezier(0.45, 0, 0.55, 1);
	
}
