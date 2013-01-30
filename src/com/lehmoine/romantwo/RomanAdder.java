package com.lehmoine.romantwo;

public class RomanAdder {

	/*
	 * Take two strings.
	 * Add them together.
	 * Return the result to the caller.
	 */
	public String add( String one, String two ) {
		RomanNumber complexOne = new RomanNumber(one);
		RomanNumber complexTwo = new RomanNumber(two);
		
		RomanNumber complexSum = complexOne.add(complexTwo);
		
		return complexSum.format();
	}
}
