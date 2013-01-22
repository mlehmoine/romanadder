package com.lehmoine.romantwo;

public class Main {

	/**
	 * Command line driver.
	 * Get numbers from the command line and add them together.
	 * Print the result.
	 */
	public static void main(String[] args) {

		String valueOne = args[0];
		String valueTwo = args[1];
		try {
			RomanAdder adder = new RomanAdder();
			String sum = adder.add( valueOne, valueTwo);
			
			System.out.println( String.format("%s + %s = %s", valueOne, valueTwo, sum));			
		}
		catch(Exception e) {
			System.out.println( "There was an error: " + e.getMessage() );
		}
		
	}

}
