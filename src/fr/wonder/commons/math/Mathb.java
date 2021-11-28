package fr.wonder.commons.math;

public class Mathb {

	/**
	 * Returns the number of bits set to 1 in the binary representation of an integer.
	 * 
	 * @param x the number to count the bits from
	 * @return the number of bits set to 1 in {@code x}
	 */
	public static int count1bits(int x) {
		int c;
		for(c = 0; x != 0; c++)
			x = x & (x-1);
		return c;
	}
	
	/**
	 * Extracts the value of the least significant bit in an integer.
	 * <p>
	 * ie: lsb(6) = lsb(0b0110) = 0b0010 = 2
	 * 
	 * 
	 * @param x the number to extract a bit from
	 * @return the value of the least significant 1 bit in {@code x}
	 */
	public static int leastSignificantBit(int x) {
		return x & (-x);
	}

	/**
	 * Returns the position of the least significant bit in an integer.
	 * <p>
	 * ie: plsb(6) = plsb(0b0000_0110) = 1
	 * <p>
	 * If {@code x} is 0 (has no 1 bit), -1 is returned.
	 * 
	 * @param x the integer from which the bits are read
	 * @return the position of the least significant bit (0 indexed)
	 */
	public static int positionOfLeastSignificantBit(int x) {
		if(x == 0)
			return -1;
		int p;
		for(p = 0; (x&1) == 0; p++)
			x >>= 1;
		return p;
	}

}
