package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.RomanAdder;


public class TestRomanAdder {

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testOne() {
		RomanAdder adder = new RomanAdder();
		
		List<RomanTestVector> vectors = new ArrayList<RomanTestVector>();
		
		vectors.add( new RomanTestVector("LXLVIII" , "I", "IC" ));
		vectors.add( new RomanTestVector("IC" , "I", "C" ));
		vectors.add( new RomanTestVector("MIM" , "I", "MM" ));
		vectors.add( new RomanTestVector("MCMLIV" , "V", "MCMLIX" ));
		vectors.add( new RomanTestVector("MCMLIV" , "C", "MMLIV" ));
		vectors.add( new RomanTestVector("MCMLIV" , "CC", "MMCLIV" ));
//		vectors.add( new RomanTestVector("MCMLIV" , "CVC", "MMCIL" ));
//        vectors.add( new RomanTestVector("IV" , "IV", "VIII" ));
//        vectors.add( new RomanTestVector("IX" , "IX", "XVIII" ));

        
		for( RomanTestVector vector : vectors ) {
			
			// Add & check the answers
			String sum = adder.add( vector.getOne() , vector.getTwo() );
			assertEquals( vector.getSum(), sum  );
						
			// Reverse the order.  The order should not matter.
			// The sum should remain the same.
			sum = adder.add( vector.getTwo() , vector.getOne() );
			assertEquals( vector.getSum(), sum  );
		}
	}
	
	@After
	public void tearDown() {

	}
}
