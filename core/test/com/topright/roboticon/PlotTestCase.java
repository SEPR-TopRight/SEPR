package com.topright.roboticon;
import static org.junit.Assert.*;
import org.junit.*;	


/**
	 * Test case for the Plot class
	 * @author Ben
	 *
	 */
public class PlotTestCase {
	
	private Plot plot;

	@Before
	public void setup(){
		plot = new Plot(PlotSpecialism.ORE);		
	}
	
	@Test
	public void testCreatePlotNoPlayer(){
		assertEquals(null,plot.getPlayer());
	}

	@Test
	public void testCreatePlotOreSpecialismCorrect(){
		assertEquals(PlotSpecialism.ORE, plot.getSpecialism());
	}
	
	@Test
	public void testCreatePlotEnergySecialismCorrect(){
		plot = new Plot(PlotSpecialism.ENERGY);
		assertEquals(PlotSpecialism.ENERGY, plot.getSpecialism());
	}

	@Test
	public void testCreatePlotNoRoboticon(){
		assertEquals(RoboticonCustomisation.UNCUSTOMISED,plot.getRoboticon());
	}
	
}
