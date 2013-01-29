package com.lehmoine.romantwo;

public class RomanAdder {

	/*
	 * Take two strings.
	 * Add them together.
	 * Return the result to the caller.
	 */
	public String add( String one, String two ) {
		RomanComplexNumber complexOne = new RomanComplexNumber(one);
		RomanComplexNumber complexTwo = new RomanComplexNumber(two);
		
		RomanComplexNumber complexSum = complexOne.add(complexTwo);
		
		return complexSum.format();
	}
}
