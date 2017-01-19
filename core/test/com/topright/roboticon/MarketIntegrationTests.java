package com.topright.roboticon;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;

/**
 * Integration tests for the {@link Market} and {@link MarketInventory} classes
 * @author jcn509
 *
 */
public class MarketIntegrationTests {
	
	
	
	MarketInventory inventory;

	/**
	 * Runs before every test. Creates a (mocked) market inventory object for the market.
	 */
	@Before
	public void setup(){
		inventory = new MarketInventory(5,5,6);
		Market.getInstance().setInventory(inventory);		
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that when 3 energy 
	 * is bought from the market and it has more than 
	 * 3 energy in its inventory, 3 energy is removed from its inventory
	 */
	@Test
	public void testBuyThreeEnergyRemovedFromInventory(){
		inventory.increaseEnergyQuantity(4);
		int energyBefore = inventory.getEnergyQuantity();
		Market.getInstance().buyEnergyFromMarket(3);
		assertEquals(energyBefore-3, inventory.getEnergyQuantity());	
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that when 1 energy 
	 * is bought from the market and it has more than 
	 * 1 energy in its inventory, 1 energy is removed from its inventory
	 */
	@Test
	public void testBuyOneEnergyRemovedFromInventory(){
		inventory.increaseEnergyQuantity(2);
		int energyBefore = inventory.getEnergyQuantity();
		Market.getInstance().buyEnergyFromMarket(1);
		assertEquals(energyBefore-1, inventory.getEnergyQuantity());	
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that it is possible to buy 2 energy from the market when the market has exactly 2 energy in its inventory.
	 */
	@Test
	public void testBuyTwoEnergyRemovedFromInventoryMarketOnlyHasTwoEnergy(){
		inventory.decreaseEnergyQuantity(inventory.getEnergyQuantity()); // Left with no energy
		inventory.increaseEnergyQuantity(2);// Left with 2 energy
		Market.getInstance().buyEnergyFromMarket(2);
		assertEquals(0, inventory.getEnergyQuantity()); // Should be left with no energy
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that it is possible to buy 1 energy from the market when the market has exactly 1 energy in its inventory.
	 */
	@Test
	public void testBuyOneEnergyRemovedFromInventoryMarketOnlyHasOneEnergy(){
		inventory.decreaseEnergyQuantity(inventory.getEnergyQuantity()); // Left with no energy
		inventory.increaseEnergyQuantity(1);// Left with 1 energy
		Market.getInstance().buyEnergyFromMarket(1);
		assertEquals(0, inventory.getEnergyQuantity()); // Should be left with no energy
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that an exception is thrown if someone tries to buy more energy than is available.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyEnergyExceptionThrownBuyTooMuchEnergy(){
		inventory.decreaseEnergyQuantity(inventory.getEnergyQuantity()); // Left with no energy
		inventory.increaseEnergyQuantity(2);// Left with 2 energy
		Market.getInstance().buyEnergyFromMarket(3);
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that no energy is removed from the market when someone attempts to buy 0 energy from the market
	 */
	@Test
	public void testBuyZeroEnergy(){
		int energyBefore = inventory.getEnergyQuantity();
		Market.getInstance().buyEnergyFromMarket(0);
		assertEquals(energyBefore,inventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that when three energy is sold to the market 3 energy is added to the market's inventory
	 */
	@Test
	public void testSellThreeEnergyEnergyStoredIncreasedByThree(){
		int energyBefore = inventory.getEnergyQuantity();
		Market.getInstance().sellEnergyToMarket(3);
		assertEquals(energyBefore+3,inventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that when six energy is sold to the market 6 energy is added to the market's inventory
	 */
	@Test
	public void testSellSixEnergyEnergyStoredIncreasedBySix(){
		int energyBefore = inventory.getEnergyQuantity();
		Market.getInstance().sellEnergyToMarket(6);
		assertEquals(energyBefore+6,inventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that no energy is added to the market when someone attempts to sell 0 energy to the market
	 */
	@Test
	public void testSellZeroEnergy(){
		int energyBefore = inventory.getEnergyQuantity();
		Market.getInstance().sellEnergyToMarket(0);
		assertEquals(energyBefore,inventory.getEnergyQuantity());
	}
	
	/**
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that when 3 ore 
	 * is bought from the market and it has more than 
	 * 3 ore in its inventory, 3 ore is removed from its inventory
	 */
	@Test
	public void testBuyThreeOreRemovedFromInventory(){
		inventory.increaseOreQuantity(4);
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().buyOreFromMarket(3);
		assertEquals(oreBefore-3, inventory.getOreQuantity());	
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that when 1 ore 
	 * is bought from the market and it has more than 
	 * 1 ore in its inventory, 1 ore is removed from its inventory
	 */
	@Test
	public void testBuyOneOreRemovedFromInventory(){
		inventory.increaseOreQuantity(2);
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().buyOreFromMarket(1);
		assertEquals(oreBefore-1, inventory.getOreQuantity());	
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that it is possible to buy 2 ore from the market when the market has exactly 2 ore in its inventory.
	 */
	@Test
	public void testBuyTwoOreRemovedFromInventoryMarketOnlyHasTwoOre(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		inventory.increaseOreQuantity(2);// Left with 2 ore
		Market.getInstance().buyOreFromMarket(2);
		assertEquals(0, inventory.getOreQuantity()); // Should be left with no ore
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that it is possible to buy 1 ore from the market when the market has exactly 1 ore in its inventory.
	 */
	@Test
	public void testBuyOneOreRemovedFromInventoryMarketOnlyHasOneOre(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		inventory.increaseOreQuantity(1);// Left with 1 ore
		Market.getInstance().buyOreFromMarket(1);
		assertEquals(0, inventory.getOreQuantity()); // Should be left with no ore
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that an exception is thrown if someone tries to buy more ore than is available.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyOreExceptionThrownBuyTooMuchOre(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		inventory.increaseOreQuantity(2);// Left with 2 ore
		Market.getInstance().buyOreFromMarket(3);
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that no ore is removed from the market when someone attempts to buy 0 ore from the market
	 */
	@Test
	public void testBuyZeroOre(){
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().buyOreFromMarket(0);
		assertEquals(oreBefore,inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that when three ore is sold to the market 3 ore is added to the market's inventory
	 */
	@Test
	public void testSellThreeOreOreStoredIncreasedByThree(){
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().sellOreToMarket(3);
		assertEquals(oreBefore+3,inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that when six ore is sold to the market 6 ore is added to the market's inventory
	 */
	@Test
	public void testSellSixOreOreStoredIncreasedBySix(){
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().sellOreToMarket(6);
		assertEquals(oreBefore+6,inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that no ore is added to the market when someone attempts to sell 0 ore to the market
	 */
	@Test
	public void testSellZeroOre(){
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().sellOreToMarket(0);
		assertEquals(oreBefore,inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that when 3 roboticons are bought from the market 
	 * and it has more than 3 roboticons in its inventory, 3 roboticons are removed from its inventory
	 */
	@Test
	public void testBuyThreeRoboticonsRemovedFromInventory(){
		inventory.increaseRoboticonQuantity(4); // More than 3 roboticons
		int roboticonsBefore = inventory.getRoboticonQuantity();
		Market.getInstance().buyRoboticonsFromMarket(3);
		assertEquals(roboticonsBefore-3,inventory.getRoboticonQuantity());
		
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that when 1 roboticon is bought from the 
	 * market and it has more than 1 roboticon in its inventory, 1 roboticon is removed from its inventory
	 */
	@Test
	public void testBuyOneRoboticonRemovedFromInventory(){
		inventory.increaseRoboticonQuantity(2); // More than 1 roboticons
		int roboticonsBefore = inventory.getRoboticonQuantity();
		Market.getInstance().buyRoboticonsFromMarket(1);
		assertEquals(roboticonsBefore-1,inventory.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that it is possible to buy 
	 * 2 roboticons from the market when the market has exactly 2 roboticons in its inventory.
	 */
	@Test
	public void testBuyTwoRoboticonsRemovedFromInventoryMarketOnlyHasTwoRoboticons(){
		inventory.decreaseRoboticonQuantity(inventory.getRoboticonQuantity()); // Left with no roboticons
		inventory.increaseRoboticonQuantity(2); // Left with exactly 2 roboticons
		
		Market.getInstance().buyRoboticonsFromMarket(2);
		assertEquals(0,inventory.getRoboticonQuantity()); // Should be left with no roboticons
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that it is possible to buy 1 
	 * roboticon from the market when the market has exactly 1 roboticon in its inventory.
	 */
	@Test
	public void testBuyOneRoboticonRemovedFromInventoryMarketOnlyHasOneRoboticon(){
		inventory.decreaseRoboticonQuantity(inventory.getRoboticonQuantity()); // Left with no roboticons
		inventory.increaseRoboticonQuantity(1); // Left with exactly 1 roboticon
	
		Market.getInstance().buyRoboticonsFromMarket(1);
		assertEquals(0,inventory.getRoboticonQuantity()); // Should be left with no roboticons
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that an exception is thrown if 
	 * someone tries to buy more roboticons than are available.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyRoboticonsExceptionThrownBuyTooManyRoboticons(){
		inventory.decreaseRoboticonQuantity(inventory.getRoboticonQuantity()); // Left with no roboticons
		inventory.increaseRoboticonQuantity(2); // Left with exactly 2 roboticons
		Market.getInstance().buyRoboticonsFromMarket(3);
	}
	
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that no roboticons are 
	 * removed from the market when someone attempts to buy 0 roboticons from the market
	 */
	@Test
	public void testBuyZeroRoboticons(){
		int roboticonsBefore = inventory.getRoboticonQuantity();
		Market.getInstance().buyRoboticonsFromMarket(0);
		assertEquals(roboticonsBefore,inventory.getRoboticonQuantity());
	}
	

	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that it returns true when called 
	 * if the market has more than enough ore to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveMoreThanEnoughOreReturnTrue(){
		// Left with more than enough ore
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()+1); 
		assertTrue(Market.getInstance().attemptToProduceRoboticon());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that a roboticon is added to the market's 
	 * inventory when it is called if the market has more than enough ore to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveMoreThanEnoughOreRoboticonAdded(){
		// Left with more than enough ore	
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()+1); 
		
		int roboticonsBefore = inventory.getRoboticonQuantity();
		Market.getInstance().attemptToProduceRoboticon();
		assertEquals(roboticonsBefore+1,inventory.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that the correct amount of ore is removed from the 
	 * market's inventory when it is called if the market has more than enough ore to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveMoreThanEnoughOreOreReduced(){
		// Left with more than enough ore	
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()+1); 
		
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().attemptToProduceRoboticon();
		assertEquals(oreBefore - Market.getInstance().getRoboticonOreConversionRate(), inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that it returns true when called 
	 * if the market has the exact amount of ore needed to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveExactlyEnoughOreReturnTrue(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		
		// Left with the exact quantity of ore that is required
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate());
		
		assertTrue(Market.getInstance().attemptToProduceRoboticon());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that a roboticon is added to the market's inventory 
	 * when it is called if the market has the exact amount of ore needed to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveExactlyEnoughOreRoboticonAdded(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		
		// Left with the exact quantity of ore that is required
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate());
		
		int roboticonsBefore = inventory.getRoboticonQuantity();
		Market.getInstance().attemptToProduceRoboticon();
		assertEquals(roboticonsBefore+1,inventory.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that the correct amount of ore is 
	 * removed from the market's inventory when it is called if the market has the exact 
	 * amount of ore needed to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveExactlyEnoughOreOreReduced(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		
		// Left with the exact quantity of ore that is required
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate());
		
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().attemptToProduceRoboticon();
		assertEquals(oreBefore-Market.getInstance().getRoboticonOreConversionRate(),inventory.getOreQuantity());
	}

	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that it returns false 
	 * when the market does not have enough ore to produce a roboticon
	 */
	@Test
	public void testProduceRoboticonsNotEnoughOreReturnFalse(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		
		// Left with 1 less ore than is required
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()-1);
		
		assertFalse(Market.getInstance().attemptToProduceRoboticon());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures no ore is removed from the market's 
	 * inventory when the market does not have enough ore to produce a roboticon
	 */
	@Test
	public void testProduceRoboticonsNotEnoughOreNoOreRemoved(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		
		// Left with 1 less ore than is required
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()-1);
		
		int oreBefore = inventory.getOreQuantity();
		Market.getInstance().attemptToProduceRoboticon();
		assertEquals(oreBefore,inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures no roboticons are added to 
	 * the market's inventory when the market does not have enough ore to produce a roboticon
	 */
	@Test
	public void testProduceRoboticonsNotEnoughOreNoRoboticonsAdded(){
		inventory.decreaseOreQuantity(inventory.getOreQuantity()); // Left with no ore
		
		// Left with 1 less ore than is required
		inventory.increaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()-1);
		
		int roboticonsBefore = inventory.getRoboticonQuantity();
		Market.getInstance().attemptToProduceRoboticon();
		assertEquals(roboticonsBefore, inventory.getRoboticonQuantity());
	}

}
