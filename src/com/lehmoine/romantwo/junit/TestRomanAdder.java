package com.lehmoine.romantwo.junit;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.RomanAdder;
import com.lehmoine.romantwo.RomanCoder;


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
		vectors.add( new RomanTestVector("MCMLIV" , "V", "MLMIX" ));
		vectors.add( new RomanTestVector("MCMLIV" , "C", "MMLIV" ));
		vectors.add( new RomanTestVector("MCMLIV" , "CC", "MMCLIV" ));
		vectors.add( new RomanTestVector("MCMLIV" , "CVC", "MMCIL" ));
		vectors.add( new RomanTestVector("IV" , "IV", "VIII" ));
        vectors.add( new RomanTestVector("IX" , "IX", "XVIII" ));

        
		for( RomanTestVector vector : vectors ) {

		    System.out.printf( "adding %s and %s\n", vector.getOne(), vector.getTwo());
		    
			// Add & check the answers
			String sum = adder.add( vector.getOne() , vector.getTwo() );
			assertEquals( vector.getSum(), sum  );
						
			// Reverse the order.  The order should not matter.
			// The sum should remain the same.
			sum = adder.add( vector.getTwo() , vector.getOne() );
			assertEquals( vector.getSum(), sum  );
		}
	}
	
    //@Test
	public void testTwo() {
	    RomanAdder adder = new RomanAdder();
	    RomanCoder coder = new RomanCoder();
	    
	    int i;
	    int j;
	    
	    for( i = 1; i <= 2000; i++ ) {
	        for( j=i; j <= 2000; j++ ) {
	            
	            String str1 = coder.fromInt(i);
	            String str2 = coder.fromInt(j);
	            int intSum = i+j;

                System.out.printf("%d + %d = %d\n", i, j, intSum);
	            
	            String expectedSum = coder.fromInt(intSum);
	            
	            String sum = adder.add(str1, str2);
	            
	            System.out.printf(" --- %s + %s = %s (%s)\n", str1, str2, sum, expectedSum);
	            assertEquals(expectedSum, sum);	            	            	           
	        }
	    }
	}
	
	@After
	public void tearDown() {

	}
}
