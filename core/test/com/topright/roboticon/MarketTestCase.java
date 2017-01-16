package com.topright.roboticon;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.*;
import mockit.integration.junit4.*;

@RunWith(JMockit.class)
public class MarketTestCase {
	/**
	 * Test case for the Market class
	 * @author Ben
	 *
	 */
	
	
	@Mocked MarketInventory inventory;

	/**
	 * Runs before every test. Creates a (mocked) market inventory object for the market.
	 */
	@Before
	public void setup(){
		inventory = new MarketInventory(5,5,6);
		Market.getInstance().setInventory(inventory);		
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that when 3 energy is bought from the market and it has more than 3 energy in its inventory, 3 energy is removed from its inventory
	 */
	@Test
	public void testBuyThreeEnergyRemovedFromInventory(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result=6;
		}};
		Market.getInstance().buyEnergyFromMarket(3);
		new Verifications(){{
			inventory.decreaseEnergyQuantity(3); times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that when 1 energy is bought from the market and it has more than 1 energy in its inventory, 1 energy is removed from its inventory
	 */
	@Test
	public void testBuyOneEnergyRemovedFromInventory(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result=6;
		}};
		Market.getInstance().buyEnergyFromMarket(1);
		new Verifications(){{
			inventory.decreaseEnergyQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that it is possible to buy 2 energy from the market when the market has exactly 2 energy in its inventory.
	 */
	@Test
	public void testBuyTwoEnergyRemovedFromInventoryMarketOnlyHasTwoEnergy(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result=2;
		}};
		Market.getInstance().buyEnergyFromMarket(2);
		new Verifications(){{
			inventory.decreaseEnergyQuantity(2); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that it is possible to buy 1 energy from the market when the market has exactly 1 energy in its inventory.
	 */
	@Test
	public void testBuyOneEnergyRemovedFromInventoryMarketOnlyHasOneEnergy(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result=1;
		}};
		Market.getInstance().buyEnergyFromMarket(1);
		new Verifications(){{
			inventory.decreaseEnergyQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that an exception is thrown if someone tries to buy more energy than is available.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyEnergyExceptionThrownBuyTooMuchEnergy(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result = 2;
		}};
		Market.getInstance().buyEnergyFromMarket(3);
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that an exception is thrown if someone tries to buy a negative quantity of energy.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyEnergyNegativeQuantityExceptionThrown(){
		Market.getInstance().buyEnergyFromMarket(-1);
	}
	
	/**
	 * Tests {@link Market#buyEnergyFromMarket}. Ensures that no energy is removed from the market when someone attempts to buy 0 energy from the market
	 */
	@Test
	public void testBuyZeroEnergy(){
		Market.getInstance().buyEnergyFromMarket(0);
		new Verifications(){{
			inventory.decreaseEnergyQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that when three energy is sold to the market 3 energy is added to the markets inventory
	 */
	@Test
	public void testSellThreeEnergyEnergyStoredIncreasedByThree(){
		Market.getInstance().sellEnergyToMarket(3);
		new Verifications(){{
			inventory.increaseEnergyQuantity(3); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that when six energy is sold to the market 6 energy is added to the markets inventory
	 */
	@Test
	public void testSellSixEnergyEnergyStoredIncreasedBySix(){
		Market.getInstance().sellEnergyToMarket(6);
		new Verifications(){{
			inventory.increaseEnergyQuantity(6); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that no energy is added to the market when someone attempts to sell 0 energy to the market
	 */
	@Test
	public void testSellZeroEnergy(){
		Market.getInstance().sellEnergyToMarket(0);
		new Verifications(){{
			inventory.increaseEnergyQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Market#sellEnergyToMarket}. Ensures that an exception is thrown if someone attempts to sell a negative amount of energy
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSellNegativeQuantityEnergyExceptionThrown(){
		Market.getInstance().sellEnergyToMarket(-1);
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that when 3 ore is bought from the market and it has more than 3 ore in its inventory, 3 ore is removed from its inventory
	 */
	@Test
	public void testBuyThreeOreRemovedFromInventory(){
		new Expectations(){{
			inventory.getOreQuantity(); result=6;
		}};
		Market.getInstance().buyOreFromMarket(3);
		new Verifications(){{
			inventory.decreaseOreQuantity(3); times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that when 1 ore is bought from the market and it has more than 1 ore in its inventory, 1 ore is removed from its inventory
	 */
	@Test
	public void testBuyOneOreRemovedFromInventory(){
		new Expectations(){{
			inventory.getOreQuantity(); result=6;
		}};
		Market.getInstance().buyOreFromMarket(1);
		new Verifications(){{
			inventory.decreaseOreQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that it is possible to buy 2 ore from the market when the market has exactly 2 ore in its inventory.
	 */
	@Test
	public void testBuyTwoOreRemovedFromInventoryMarketOnlyHasTwoOre(){
		new Expectations(){{
			inventory.getOreQuantity(); result=2;
		}};
		Market.getInstance().buyOreFromMarket(2);
		new Verifications(){{
			inventory.decreaseOreQuantity(2); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that it is possible to buy 1 ore from the market when the market has exactly 1 ore in its inventory.
	 */
	@Test
	public void testBuyOneOreRemovedFromInventoryMarketOnlyHasOneOre(){
		new Expectations(){{
			inventory.getOreQuantity(); result=1;
		}};
		Market.getInstance().buyOreFromMarket(1);
		new Verifications(){{
			inventory.decreaseOreQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that an exception is thrown if someone tries to buy more ore than is available.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyOreExceptionThrownBuyTooMuchOre(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 2;
		}};
		Market.getInstance().buyOreFromMarket(3);
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that an exception is thrown if someone tries to buy a negative quantity of ore.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyOreNegativeQuantityExceptionThrown(){
		Market.getInstance().buyOreFromMarket(-1);
	}
	
	/**
	 * Tests {@link Market#buyOreFromMarket}. Ensures that no ore is removed from the market when someone attempts to buy 0 ore from the market
	 */
	@Test
	public void testBuyZeroOre(){
		Market.getInstance().buyOreFromMarket(0);
		new Verifications(){{
			inventory.decreaseOreQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that when three ore is sold to the market 3 ore is added to the markets inventory
	 */
	@Test
	public void testSellThreeOreOreStoredIncreasedByThree(){
		Market.getInstance().sellOreToMarket(3);
		new Verifications(){{
			inventory.increaseOreQuantity(3); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that when six ore is sold to the market 6 ore is added to the markets inventory
	 */
	@Test
	public void testSellSixOreOreStoredIncreasedBySix(){
		Market.getInstance().sellOreToMarket(6);
		new Verifications(){{
			inventory.increaseOreQuantity(6); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that no ore is added to the market when someone attempts to sell 0 ore to the market
	 */
	@Test
	public void testSellZeroOre(){
		Market.getInstance().sellOreToMarket(0);
		new Verifications(){{
			inventory.increaseOreQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Market#sellOreToMarket}. Ensures that an exception is thrown if someone attempts to sell a negative amount of ore
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testSellNegativeQuantityOreExceptionThrown(){
		Market.getInstance().sellOreToMarket(-1);
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that when 3 roboticons are bought from the market and it has more than 3 roboticons in its inventory, 3 roboticons are removed from its inventory
	 */
	@Test
	public void testBuyThreeRoboticonsRemovedFromInventory(){
		new Expectations(){{
			inventory.getRoboticonQuantity(); result=6;
		}};
		Market.getInstance().buyRoboticonsFromMarket(3);
		new Verifications(){{
			inventory.decreaseRoboticonQuantity(3); times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that when 1 roboticon is bought from the market and it has more than 1 roboticon in its inventory, 1 roboticon is removed from its inventory
	 */
	@Test
	public void testBuyOneRoboticonRemovedFromInventory(){
		new Expectations(){{
			inventory.getRoboticonQuantity(); result=6;
		}};
		Market.getInstance().buyRoboticonsFromMarket(1);
		new Verifications(){{
			inventory.decreaseRoboticonQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that it is possible to buy 2 roboticons from the market when the market has exactly 2 roboticons in its inventory.
	 */
	@Test
	public void testBuyTwoRoboticonsRemovedFromInventoryMarketOnlyHasTwoRoboticons(){
		new Expectations(){{
			inventory.getRoboticonQuantity(); result=2;
		}};
		Market.getInstance().buyRoboticonsFromMarket(2);
		new Verifications(){{
			inventory.decreaseRoboticonQuantity(2); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that it is possible to buy 1 roboticon from the market when the market has exactly 1 roboticon in its inventory.
	 */
	@Test
	public void testBuyOneRoboticonRemovedFromInventoryMarketOnlyHasOneRoboticon(){
		new Expectations(){{
			inventory.getRoboticonQuantity(); result=1;
		}};
		Market.getInstance().buyRoboticonsFromMarket(1);
		new Verifications(){{
			inventory.decreaseRoboticonQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that an exception is thrown if someone tries to buy more roboticons than are available.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyRoboticonsExceptionThrownBuyTooManyRoboticons(){
		new Expectations(){{
			inventory.getRoboticonQuantity(); result = 2;
		}};
		Market.getInstance().buyRoboticonsFromMarket(3);
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that an exception is thrown if someone tries to buy a negative number of roboticons.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testBuyRoboticonsNegativeQuantityExceptionThrown(){
		Market.getInstance().buyRoboticonsFromMarket(-1);
	}
	
	/**
	 * Tests {@link Market#buyRoboticonsFromMarket}. Ensures that no roboticons are removed from the market when someone attempts to buy 0 roboticons from the market
	 */
	@Test
	public void testBuyZeroRoboticons(){
		Market.getInstance().buyRoboticonsFromMarket(0);
		new Verifications(){{
			inventory.decreaseRoboticonQuantity(anyInt); times=0;
		}};
	}
	

	/**
	 * Tests {@link Market#getCostEnergy}. Ensures that 0 energy costs 0 money
	 */
	@Test
	public void testEnergyCostZero(){
		assertEquals(0, Market.getInstance().getCostEnergy(0));
	}
	
	/**
	 * Tests {@link Market#getCostOre}. Ensures that 0 ore costs 0 money
	 */
	@Test
	public void testOreCostZero(){
		assertEquals(0, Market.getInstance().getCostOre(0));
	}
	
	/**
	 * Tests {@link Market#getCostRoboticons}. Ensures that 0 roboticons cost 0 money
	 */
	@Test
	public void testRoboticonCostZero(){
		assertEquals(0, Market.getInstance().getCostRoboticons(0));
	}
	
	/**
	 * Tests {@link Market#getCostEnergy}. Ensures that 1 energy costs more than 0 money
	 */
	@Test
	public void testEnergyCostOne(){
		assertTrue(0 < Market.getInstance().getCostEnergy(1));
	}
	
	/**
	 * Tests {@link Market#getCostOre}. Ensures that 1 ore costs more than 0 money
	 */
	@Test
	public void testOreCostOne(){
		assertTrue(0 < Market.getInstance().getCostOre(1));
	}
	
	/**
	 * Tests {@link Market#getCostRoboticons}. Ensures that 1 roboticon costs more than 0 money
	 */
	@Test
	public void testRoboticonCostOne(){
		assertTrue(0 < Market.getInstance().getCostRoboticons(1));
	}
	
	/**
	 * Tests {@link Market#getCostEnergy}. Ensures that 2 energy costs twice as much as one energy
	 */
	@Test
	public void testEnergyCostTwo(){
		assertEquals(2 * Market.getInstance().getCostEnergy(1), Market.getInstance().getCostEnergy(2));
	}
	
	/**
	 * Tests {@link Market#getCostOre}. Ensures that 2 ore costs twice as much as 1 ore
	 */
	@Test
	public void testOreCostTwo(){
		assertEquals(2 * Market.getInstance().getCostOre(1), Market.getInstance().getCostOre(2));
	}
	
	/**
	 * Tests {@link Market#getCostRoboticons}. Ensures that 2 roboticons cost twice as much as 1 roboticon
	 */
	@Test
	public void testRoboticonCostTwo(){
		assertEquals(2 * Market.getInstance().getCostRoboticons(1), Market.getInstance().getCostRoboticons(2));
	}
	
	/**
	 * Tests {@link Market#getCostEnergy}. Ensures that an exception is thrown if someone tries to find the cost of a negative amount of energy
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testEnergyCostNegative(){
		Market.getInstance().getCostEnergy(-1);
	}
	
	/**
	 * Tests {@link Market#getCostOre}. Ensures that an exception is thrown if someone tries to find the cost of a negative amount of ore
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testOreCostNegative(){
		Market.getInstance().getCostOre(-1);
	}
	
	/**
	 * Tests {@link Market#getCostRoboticons}. Ensures that an exception is thrown if someone tries to find the cost of a negative number of roboticons
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testRoboticonCostNegative(){
		Market.getInstance().getCostRoboticons(-1);
	}

	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that it returns true when called if the market has more than enough ore to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveMoreThanEnoughOreReturnTrue(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate()+99999; // More than enough ore
		}};
		assertTrue(Market.getInstance().attemptToProduceRoboticon());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that a roboticon is added to the markets inventory when it is called if the market has more than enough ore to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveMoreThanEnoughOreRoboticonAdded(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate()+99999; // More than enough ore
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.increaseRoboticonQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that the correct amount of ore is removed from the markets inventory when it is called if the market has more than enough ore to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveMoreThanEnoughOreOreReduced(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate()+99999; // More than enough ore
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.decreaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that it returns true when called if the market has the exact amount of ore needed to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveExactlyEnoughOreReturnTrue(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate(); // Exactly enough ore
		}};
		assertTrue(Market.getInstance().attemptToProduceRoboticon());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that a roboticon is added to the markets inventory when it is called if the market has the exact amount of ore needed to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveExactlyEnoughOreRoboticonAdded(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate(); // Exactly enough ore
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.increaseRoboticonQuantity(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that the correct amount of ore is removed from the markets inventory when it is called if the market has the exact amount of ore needed to produce a roboticon
	 */
	@Test
	public void testAttemptToProduceRoboticonsHaveExactlyEnoughOreOreReduced(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate(); // Exactly enough ore
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.decreaseOreQuantity(Market.getInstance().getRoboticonOreConversionRate()); times=1;
		}};
	}

	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures that it returns false when the market does not have enough ore to produce a roboticon
	 */
	@Test
	public void testProduceRoboticonsNotEnoughOreReturnFalse(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate() - 1; // Not enough ore
		}};
		assertFalse(Market.getInstance().attemptToProduceRoboticon());
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures no ore is removed from the markets inventory when the market does not have enough ore to produce a roboticon
	 */
	@Test
	public void testProduceRoboticonsNotEnoughOreNoOreRemoved(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate() - 1; // Not enough ore
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.decreaseOreQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Market#attemptToProduceRoboticon}. Ensures no roboticons are added to the markets inventory when the market does not have enough ore to produce a roboticon
	 */
	@Test
	public void testProduceRoboticonsNotEnoughOreNoRoboticonsAdded(){
		new Expectations(){{
			inventory.getOreQuantity(); result = Market.getInstance().getRoboticonOreConversionRate() - 1; // Not enough ore
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.increaseRoboticonQuantity(anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link Market#getCostRoboticonCustomisation}. Ensures that a non-zero value is returned for an energy customisation.
	 */
	@Test
	public void testGetCostRoboticonEnergy(){
		assertNotEquals(0,Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Market#getCostRoboticonCustomisation}. Ensures that a non-zero value is returned for an ore customisation.
	 */
	@Test
	public void testGetCostRoboticonOre(){
		assertNotEquals(0,Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Market#getCostRoboticonCustomisation}. Ensures that an exception is thrown if RoboticionCustomisation.UNCUSTOMISED is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testGetCostRoboticonExceptionUnustomised(){
		Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.UNCUSTOMISED);
	}
}
