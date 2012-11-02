#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.ui.helper;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class RangeTest {

	@Test
	public void testParseRangeNormal() {
		assertEquals(new Range(10,20,null), Range.fromRequestHeader("items=10-20"));
		assertEquals(new Range(10,20,null), Range.fromRequestHeader("items = 10 - 20"));
	}

	@Test
	public void testConstructor(){
		assertEquals(new Range(10,20,30L), new Range(10,20,30L));
		assertEquals(new Range(10,29,30L), new Range(10,40,30L));
	}

	@Test
	public void testParseRangeNull() {
		String input = null;

		Range hasil = Range.fromRequestHeader(input);
		assertEquals(new Range(0,19,null), hasil);
	}

	@Test
	public void testParseRangeInvalidString() {
		assertEquals(new Range(0,19,null), Range.fromRequestHeader("halo=10-20"));
		assertEquals(new Range(0,19,null), Range.fromRequestHeader("halo1020"));
	}

	@Test
	public void testParseRangeInvalidNumber() {
		String input = "items=10-2a";

		Range hasil = Range.fromRequestHeader(input);
		assertEquals(new Range(0,19,null), hasil);
	}

	@Test
	public void testToResponseHeader(){
		Range r = new Range(11,30,100L);
		assertEquals("items 11-30/100", r.toResponseHeader());
	}
}
