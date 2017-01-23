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
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has not yet been acquired (does not yet have a player) and this method is called the plots player attribute is set correctly
	 */
	@Test
	public void testSetPlayerCurrentlyNoPlayerCorrectPlayer(){
		plot.setPlayer(player);
		assertEquals(player,plot.getPlayer());
		
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has not yet been acquired (does not yet have a player) and this method is called that afterwards the plot will register as being acquired
	 */
	@Test
	public void testSetPlayerCurrentlyNoPlayerHasBeenAcquired(){
		plot.setPlayer(player);
		assertTrue(plot.hasBeenAcquired());
		
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has been acquired and this method is called and a new player tries to acquire the plot an exception is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlayerAlreadyAcquiredNewPlayer(){
		plot.setPlayer(player); // Acquire by the current player
		
		// Try and acquire by a new player
		plot.setPlayer(new Player(new PlayerInventory(0,0,new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class),0)));
		
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has been acquired and this method is called and the same player tries to acquire the plot again an exception is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlayerAlreadyAcquiredSamePlayer(){
		plot.setPlayer(player); // Acquire by the current player
		
		// Try and acquire again
		plot.setPlayer(player);
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in ore) when the plot hasn't got a roboticon on it, it wont produce any resources for the player
	 */
	@Test
	public void testProduceNoRoboticonOrePlot(){
		plot = new Plot(PlotSpecialism.ORE);
		plot.setPlayer(player); // Acquire by player
		plot.produce();
		new Verifications(){{
			player.increaseOreQuantity(anyInt); times=0;
			player.increaseEnergyQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in energy) when the plot hasn't got a roboticon on it, it wont produce any resources for the player
	 */
	@Test
	public void testProduceNoRoboticonEnergyPlot(){
		plot = new Plot(PlotSpecialism.ENERGY);
		plot.setPlayer(player); // Acquire by player
		plot.produce();
		new Verifications(){{
			player.increaseOreQuantity(anyInt); times=0;
			player.increaseEnergyQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in energy) when the plot hasn't got a player on it, an exception won't be thrown
	 */
	@Test
	public void testProduceNoPlayerEnergyPlot(){
		plot = new Plot(PlotSpecialism.ENERGY);
		
		plot.produce(); // Ensure no exceptions thrown
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in ore) when the plot hasn't got a player on it, an exception won't be thrown
	 */
	@Test
	public void testProduceNoPlayerOrePlot(){
		plot = new Plot(PlotSpecialism.ORE);
		
		plot.produce(); // Ensure no exceptions thrown
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production when the plot has an energy roboticon on it, it will produce 2 energy for its player
	 */
	@Test
	public void testProduceEnergyPlotEnergyRoboticonProduce2Energy(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		plot.produce();
		new Verifications(){{
			player.increaseEnergyQuantity(2); times=1; // Produce 2 energy
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production when the plot has an energy roboticon on it, it will produce 0 ore for its player
	 */
	@Test
	public void testProduceEnergyPlotEnergyRoboticonProduceNoOre(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		plot.produce();
		new Verifications(){{
			player.increaseOreQuantity(anyInt); times=0; // Produce no ore
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production when the plot has an ore roboticon on it, it will produce 1 ore for its player
	 */
	@Test
	public void testProduceEnergyPlotOreRoboticonProduce1Ore(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		plot.produce();
		new Verifications(){{
			player.increaseOreQuantity(1); times=1; // Produce 1 ore
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production when the plot has an ore roboticon on it, it will produce 0 energy for its player
	 */
	@Test
	public void testProduceEnergyPlotOreRoboticonProduceNoEnergy(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		plot.produce();
		new Verifications(){{
			player.increaseEnergyQuantity(anyInt); times=0; // Produce no energy
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore production when the plot has an ore roboticon on it, it will produce 2 ore for its player
	 */
	@Test
	public void testProduceOrePlotOreRoboticonProduce2Ore(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		plot.produce();
		new Verifications(){{
			player.increaseOreQuantity(2); times=1;  // Produce 2 ore
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore when the plot has an ore roboticon on it, it will produce 0 energy for its player
	 */
	@Test
	public void testProduceOrePlotOreRoboticonProduceNoEnergy(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		plot.produce();
		new Verifications(){{
			player.increaseEnergyQuantity(anyInt); times=0; // Produce no energy
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore when the plot has an energy roboticon on it, it will produce 1 energy for its player
	 */
	@Test
	public void testProduceOrePlotEnergyRoboticonProduce1Energy(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		plot.produce();
		new Verifications(){{
			player.increaseEnergyQuantity(1); times=1; // Produce 1 energy
		}};
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore when the plot has an energy roboticon on it, it will produce 0 ore for its player
	 */
	@Test
	public void testProduceOrePlotEnergyRoboticonProduceNoOre(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		plot.produce();
		new Verifications(){{
			player.increaseOreQuantity(anyInt); times=0; // Produce no ore
		}};
	}
	
}
