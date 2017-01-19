package com.topright.roboticon;
import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.*;	


	/**
	 * Test case for the Plot class
	 * @author jcn509
	 *
	 */

public class PlotIntegrationTests {
	
	private Plot plot;
	private Player player;
	private PlayerInventory playerInventory; // Required by player

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
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has not yet been acquired 
	 * (does not yet have a player) and this method is called the plot's player attribute is set correctly
	 */
	@Test
	public void testSetPlayerCurrentlyNoPlayerCorrectPlayer(){
		plot.setPlayer(player);
		assertEquals(player,plot.getPlayer());
		
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has not yet been acquired 
	 * (does not yet have a player) and this method is called that afterwards the plot 
	 * will register as being acquired
	 */
	@Test
	public void testSetPlayerCurrentlyNoPlayerHasBeenAcquired(){
		plot.setPlayer(player);
		assertTrue(plot.hasBeenAcquired());
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has been acquired and 
	 * this method is called and a new player tries to acquire the plot an exception is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlayerAlreadyAcquiredNewPlayer(){
		plot.setPlayer(player); // Acquire by the current player
		
		// Try and acquire by a new player
		plot.setPlayer(new Player(new PlayerInventory(0,0,new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class),0)));
		
	}
	
	/**
	 * Tests {@link Plot#setPlayer(Player)} ensures that when a plot has been acquired
	 * and this method is called and the same player tries to acquire the plot again an exception is thrown
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testSetPlayerAlreadyAcquiredSamePlayer(){
		plot.setPlayer(player); // Acquire by the current player
		
		// Try and acquire again
		plot.setPlayer(player);
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in ore) when the
	 * plot hasn't got a roboticon on it, it wont produce any energy for the player
	 */
	@Test
	public void testProduceNoRoboticonOrePlotProduceEnergy(){
		plot = new Plot(PlotSpecialism.ORE);
		plot.setPlayer(player); // Acquire by player
		int energyBefore = playerInventory.getEnergyQuantity();
		plot.produce();
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in ore) when the
	 * plot hasn't got a roboticon on it, it wont produce any ore for the player
	 */
	@Test
	public void testProduceNoRoboticonOrePlotProduceOre(){
		plot = new Plot(PlotSpecialism.ORE);
		plot.setPlayer(player); // Acquire by player
		int oreBefore = playerInventory.getOreQuantity();
		plot.produce();
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in energy) when the
	 * plot hasn't got a roboticon on it, it wont produce any energy for the player
	 */
	@Test
	public void testProduceNoRoboticonEnergyPlotProduceEnergy(){
		plot = new Plot(PlotSpecialism.ENERGY);
		plot.setPlayer(player); // Acquire by player
		int energyBefore = playerInventory.getEnergyQuantity();
		plot.produce();
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in energy) when the
	 * plot hasn't got a roboticon on it, it wont produce any ore for the player
	 */
	@Test
	public void testProduceNoRoboticonEnergyPlotProduceOre(){
		plot = new Plot(PlotSpecialism.ENERGY);
		plot.setPlayer(player); // Acquire by player
		int oreBefore = playerInventory.getOreQuantity();
		plot.produce();
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in energy) 
	 * when the plot hasn't got a player on it, an exception won't be thrown
	 */
	@Test
	public void testProduceNoPlayerEnergyPlot(){
		plot = new Plot(PlotSpecialism.ENERGY);
		
		plot.produce(); // Ensure no exceptions thrown
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that (for a plot specialising in ore) 
	 * when the plot hasn't got a player on it, an exception won't be thrown
	 */
	@Test
	public void testProduceNoPlayerOrePlot(){
		plot = new Plot(PlotSpecialism.ORE);
		
		plot.produce(); // Ensure no exceptions thrown
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production 
	 * when the plot has an energy roboticon on it, it will produce 2 energy for its player
	 */
	@Test
	public void testProduceEnergyPlotEnergyRoboticonProduce2Energy(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		
		int energyBefore = playerInventory.getEnergyQuantity();
		plot.produce();
		assertEquals(energyBefore+2,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production 
	 * when the plot has an energy roboticon on it, it will produce 0 ore for its player
	 */
	@Test
	public void testProduceEnergyPlotEnergyRoboticonProduceNoOre(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		
		int oreBefore = playerInventory.getOreQuantity();
		plot.produce();
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production 
	 * when the plot has an ore roboticon on it, it will produce 1 ore for its player
	 */
	@Test
	public void testProduceEnergyPlotOreRoboticonProduce1Ore(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		
		int oreBefore = playerInventory.getOreQuantity();
		plot.produce();
		assertEquals(oreBefore+1,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in energy production 
	 * when the plot has an ore roboticon on it, it will produce 0 energy for its player
	 */
	@Test
	public void testProduceEnergyPlotOreRoboticonProduceNoEnergy(){
		plot = new Plot(PlotSpecialism.ENERGY); // Energy plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		
		int energyBefore = playerInventory.getEnergyQuantity();
		plot.produce();
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore production 
	 * when the plot has an ore roboticon on it, it will produce 2 ore for its player
	 */
	@Test
	public void testProduceOrePlotOreRoboticonProduce2Ore(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon

		int oreBefore = playerInventory.getOreQuantity();
		plot.produce();
		assertEquals(oreBefore+2,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore 
	 * when the plot has an ore roboticon on it, it will produce 0 energy for its player
	 */
	@Test
	public void testProduceOrePlotOreRoboticonProduceNoEnergy(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ORE); // Ore roboticon
		
		int energyBefore = playerInventory.getEnergyQuantity();
		plot.produce();
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore 
	 * when the plot has an energy roboticon on it, it will produce 1 energy for its player
	 */
	@Test
	public void testProduceOrePlotEnergyRoboticonProduce1Energy(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon

		int energyBefore = playerInventory.getEnergyQuantity();
		plot.produce();
		assertEquals(energyBefore+1,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Plot#produce()} ensure that for a plot specialising in ore when the plot has an energy roboticon on it,
	 * it will produce 0 ore for its player
	 */
	@Test
	public void testProduceOrePlotEnergyRoboticonProduceNoOre(){
		plot = new Plot(PlotSpecialism.ORE); // Ore plot
		plot.setPlayer(player); // Acquire by player
		plot.placeRoboticon(RoboticonCustomisation.ENERGY); // Energy roboticon
		
		int oreBefore = playerInventory.getOreQuantity();
		plot.produce();
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}
	
}
