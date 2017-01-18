package com.topright.roboticon;
import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.*;	
import mockit.*;
import mockit.integration.junit4.*;
import org.junit.runner.RunWith;


/**
	 * Test case for the Plot class
	 * @author Ben
	 *
	 */

@RunWith(JMockit.class)
public class PlotTestCase {
	
	private Plot plot;
	@Mocked private Player player;
	@Mocked private PlayerInventory playerInventory; // Required by player

	/**
	 * Runs before every test, creates a new plot that specialises in producing ore and a mocked player object
	 */
	@Before
	public void setup(){
		plot = new Plot(PlotSpecialism.ORE);
		
		// The player inventory does not need to store anything but it is required by the player class
		playerInventory = new PlayerInventory(0,0, new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class),0); 
		player = new Player(playerInventory);
	}
	
	/**
	 * Tests {@link Plot#Plot(PlotSpecialism)} and ensures that a newly created plot does not have an associated player (i.e. no player owns it)
	 */
	@Test
	public void testCreatePlotNoPlayer(){
		assertEquals(null,plot.getPlayer()); // New plot created in the setup method
	}
	
	/**
	 * Tests {@link Plot#Plot(PlotSpecialism)} and ensures that a newly created plot has not yet been acquired
	 */
	@Test
	public void testCreatePlotNotAcquired(){
		assertFalse(plot.hasBeenAcquired()); // New plot created in the setup method
	}

	/**
	 * Tests {@link Plot#Plot(PlotSpecialism)} and ensures that a newly created plot that is supposed to specialise in ore production does in fact specialise in ore production
	 */
	@Test
	public void testCreatePlotOreSpecialismCorrect(){
		assertEquals(PlotSpecialism.ORE, plot.getSpecialism()); // Ore plot created in setup
	}
	
	/**
	 * Tests {@link Plot#Plot(PlotSpecialism)} and ensures that a newly created plot that is supposed to specialise in energy production does in fact specialise in energy production
	 */
	@Test
	public void testCreatePlotEnergySecialismCorrect(){
		plot = new Plot(PlotSpecialism.ENERGY);
		assertEquals(PlotSpecialism.ENERGY, plot.getSpecialism());
	}

	/**
	 * Tests {@link Plot#Plot(PlotSpecialism)} and ensures that a newly created plot does not yet have a roboticon on it, by calling its getRoboticon method and ensuring that it returns null
	 */
	@Test
	public void testCreatePlotNullRoboticon(){
		assertEquals(null,plot.getRoboticon());
	}
	
	/**
	 * Tests {@link Plot#Plot(PlotSpecialism)} and ensures that a newly created plot does not yet have a roboticon on it
	 */
	@Test
	public void testCreatePlotNoRoboticon(){
		assertFalse(plot.hasRoboticon());
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} and ensures that a newly created plot does not yet have a roboticon on it
	 */
	@Test
	public void testSetPlayer(){
		assertFalse(plot.hasRoboticon());
	}
	
}
