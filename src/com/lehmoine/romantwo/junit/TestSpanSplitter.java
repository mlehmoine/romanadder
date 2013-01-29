package com.lehmoine.romantwo.junit;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.lehmoine.romantwo.SpanSplitter;

/*
 * Unit test for the span splitter.
 */


public class TestSpanSplitter {

	@Before
	public void setUp() {
		
	}
	
	@Test
	public void testOne() {
		List<Character> list = new ArrayList<Character>();
		
		list.add( new Character('A'));
		list.add( new Character('B'));
		list.add( new Character('C'));
	
		SpanSplitter<Character> splitter = new SpanSplitter<Character>();
		List<SpanSplitter.SpanInfo<Character>> result = splitter.split(list);
		
		assertEquals( result.size(), 3);
		
		assertEquals( result.get(0).getElements().size(), 1);
		assertEquals( result.get(0).getElements().get(0), new Character('A'));
		
		assertEquals( result.get(1).getElements().size(), 1);
		assertEquals( result.get(1).getElements().get(0), new Character('B'));

		assertEquals( result.get(2).getElements().size(), 1);
		assertEquals( result.get(2).getElements().get(0), new Character('C'));
	}

	@Test
	public void testTwo() {
		List<Character> list = new ArrayList<Character>();
		
		list.add( new Character('A'));
		list.add( new Character('B'));
		list.add( new Character('B'));
		list.add( new Character('B'));
		list.add( new Character('C'));
	
		SpanSplitter<Character> splitter = new SpanSplitter<Character>();
		List<SpanSplitter.SpanInfo<Character>> result = splitter.split(list);

		assertEquals( result.size(), 3);

		assertEquals( result.get(0).getElements().size(), 1);
		assertEquals( result.get(0).getElements().get(0), new Character('A'));
		
		assertEquals( result.get(1).getElements().size(), 3);
		assertEquals( result.get(1).getElements().get(0), new Character('B'));

		assertEquals( result.get(2).getElements().size(), 1);
		assertEquals( result.get(2).getElements().get(0), new Character('C'));

	}

	@Test
	public void testThree() {
		List<Character> list = new ArrayList<Character>();
		
		list.add( new Character('A'));
		list.add( new Character('B'));
		list.add( new Character('B'));
		list.add( new Character('B'));
		list.add( new Character('C'));
		list.add( new Character('C'));
		list.add( new Character('C'));
	
		SpanSplitter<Character> splitter = new SpanSplitter<Character>();
		List<SpanSplitter.SpanInfo<Character>> result = splitter.split(list);

		assertEquals( result.size(), 3);

		assertEquals( result.get(0).getElements().size(), 1);
		assertEquals( result.get(0).getElements().get(0), new Character('A'));
		
		assertEquals( result.get(1).getElements().size(), 3);
		assertEquals( result.get(1).getElements().get(0), new Character('B'));

		assertEquals( result.get(2).getElements().size(), 3);
		assertEquals( result.get(2).getElements().get(0), new Character('C'));
	}

	@Test
	public void testFour() {
		List<Character> list = new ArrayList<Character>();
		
		list.add( new Character('A'));
	
		SpanSplitter<Character> splitter = new SpanSplitter<Character>();
		List<SpanSplitter.SpanInfo<Character>> result = splitter.split(list);

		assertEquals( result.size(), 1);

		assertEquals( result.get(0).getElements().size(), 1);
		assertEquals( result.get(0).getElements().get(0), new Character('A'));
	}
	
	
	@After
	public void tearDown() {

	}
}
