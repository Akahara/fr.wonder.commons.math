# fr.wonder.commons.math

This librairy contains usefull math functions and types.\
It is somewhat similar to `java.lang.Math` but uses mainly floats and ints instead of doubles
and longs, it also contains methods that are way faster at the cost of some precision.

## speed-oriented methods

See the `Mathf` class for the full list of accessible methods, here are the most important ones:

* `sin`, `cos`
  - using a sine table
* `exp`, `ln`
  - exp with 2 methods, see `Mathf.exp` and `Mathf.expf`
  - ln with a bits manipulation method
* `invSqrt`
  - using the Quake 3 algorithm

## Types

There are multiple vector types in this library:
```java
class Vec2  { float x, y;       ... }
class Vec2i { int   x, y;       ... }
class Vec3  { float x, y, z;    ... }
class Vec4  { float x, y, z, w; ... }
class Vec4i { int   x, y, z, w; ... }
```
Vector operations in these classes return *new instances* unless specified otherwise,
they alsooverride `Object.hashCode()` so they are safe to use in hash maps.

## Vector manipulations & random

The `Mathf` class contains a large number of utility methods to manipulate vectors (`max`, `clamp`,
`abs`, `mod`...) and methods to generate random values and vectors:
```java
/**
randomx() methods return vectors of size x composed of random values between 0 and 1 (exclusive) such that randomx().length() = 1.
*/
VecX randomX() {}
/**
randomx(float radius) methods return vectors of size x with size equal to the given radius.
*/
VecX randomX(float radius) {}
/**
randomX(float min, float max) methods return vectors of size x with composants randomly distributed between the given min and max values. 
*/
VecX randomX(float min, float max) {}
```
There are also simpler methods to generate random floats and ints:
```java
float random() {}
float randomGaussian() {}
int randomInRange(int min, int max) {}
float randomInRange(float min, float max) {}
```

## More & about

This library is currently built using java 9+, it should work with java 8 for the most part, you can safely use it as long as you include the project's licence in yours.
You can find more utilities in my common libraries ([commons](https://github.com/Akahara/fr.wonder.commons) and [systems](https://github.com/Akahara/fr.wonder.commons.systems)).
