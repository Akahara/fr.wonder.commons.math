/**
 * Code adapted from https://github.com/gre/bezier-easing:
 * 
 * 
 * Copyright (c) 2014 Gaëtan Renaudeau
 * 
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE
 */

package fr.wonder.commons.math.ease;

import java.util.function.UnaryOperator;

public class Bezier implements UnaryOperator<Double> {

	private static final int NEWTON_ITERATIONS = 4;
	private static final double NEWTON_MIN_SLOPE = 0.001;
	private static final double SUBDIVISION_PRECISION = 0.0000001;
	private static final int SUBDIVISION_MAX_ITERATIONS = 10;

	private static final int kSplineTableSize = 11;
	private static final double kSampleStepSize = 1.0 / (kSplineTableSize - 1.0);

	private static double A(double a1, double a2) {
		return 1.0 - 3.0 * a2 + 3.0 * a1;
	}

	private static double B(double a1, double a2) {
		return 3.0 * a2 - 6.0 * a1;
	}

	private static double C(double a1) {
		return 3.0 * a1;
	}

	private static double calcBezier(double at, double a1, double a2) {
		return ((A(a1, a2) * at + B(a1, a2)) * at + C(a1)) * at;
	}

	private static double getSlope(double at, double a1, double a2) {
		return 3.0 * A(a1, a2) * at * at + 2.0 * B(a1, a2) * at + C(a1);
	}

	private static double binarySubdivide(double x, double a, double b, double x1, double x2) {
		double currentX, currentT;
		int i = 0;
		do {
			currentT = a + (b - a) / 2.0;
			currentX = calcBezier(currentT, x1, x2) - x;
			if (currentX > 0.0) {
				b = currentT;
			} else {
				a = currentT;
			}
		} while (Math.abs(currentX) > SUBDIVISION_PRECISION && ++i < SUBDIVISION_MAX_ITERATIONS);
		return currentT;
	}

	private static double newtonRaphsonIterate(double x, double guessT, double x1, double x2) {
		for (var i = 0; i < NEWTON_ITERATIONS; ++i) {
			var currentSlope = getSlope(guessT, x1, x2);
			if (currentSlope == 0.0) {
				return guessT;
			}
			var currentX = calcBezier(guessT, x1, x2) - x;
			guessT -= currentX / currentSlope;
		}
		return guessT;
	}

	private final double x1, x2, y1, y2;
	private final double[] sampleValues;

	public Bezier(double x1, double y1, double x2, double y2) {
		if (!(0 <= x1 && x1 <= 1 && 0 <= x2 && x2 <= 1))
			throw new IllegalArgumentException("bezier x values must be in [0, 1] range");
		this.x1 = x1;
		this.x2 = x2;
		this.y1 = y1;
		this.y2 = y2;
		this.sampleValues = new double[kSplineTableSize];
		for (int i = 0; i < kSplineTableSize; ++i)
			sampleValues[i] = calcBezier(i * kSampleStepSize, x1, x2);
	}

	private double getTForX(double x) {
		double intervalStart = 0.0;
		int currentSample = 1;
		int lastSample = kSplineTableSize - 1;

		for (; currentSample != lastSample && sampleValues[currentSample] <= x; ++currentSample) {
			intervalStart += kSampleStepSize;
		}
		--currentSample;

		var dist = (x - sampleValues[currentSample]) / (sampleValues[currentSample + 1] - sampleValues[currentSample]);
		var guessForT = intervalStart + dist * kSampleStepSize;

		var initialSlope = getSlope(guessForT, x1, x2);
		if (initialSlope >= NEWTON_MIN_SLOPE) {
			return newtonRaphsonIterate(x, guessForT, x1, x2);
		} else if (initialSlope == 0.0) {
			return guessForT;
		} else {
			return binarySubdivide(x, intervalStart, intervalStart + kSampleStepSize, x1, x2);
		}
	}

	@Override
	public Double apply(Double x) {
		return x == 0 || x == 1 ? x : calcBezier(getTForX(x), y1, y2);
	}

}
