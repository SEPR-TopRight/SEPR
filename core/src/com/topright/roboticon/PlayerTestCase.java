package com.topright.roboticon;
import static org.junit.Assert.*;
import java.util.EnumMap;
import org.junit.*;	
import mockit.*;
/**
 * Test case for {@link Player}
 * @author jcn509
 */

public class PlayerTestCase {
	
	private Player player;
	@Mocked private PlayerInventory playerInventory;
	
	@Mocked private Market market;

	/**
	 * Runs before every test, creates a new market object and an empty mapping from RoboticonCustomisation to Integer.
	 */
	@Before
	public void setup(){
		playerInventory = new PlayerInventory(5,4,new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class),1000);
		market = new Market(10,10,10);
		player = new Player(playerInventory);
		
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when used correctly.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(anyInt); result = 10;
			playerInventory.getMoneyQuantity();result=100000;
		}};
		
		assertTrue(player.attemptToBuyEnergy(market,6));
		new Verifications(){{
			market.getCostEnergy(6);times=1;
			market.buyEnergy(anyInt);times=1;
			playerInventory.increaseEnergyQuantity(6);times=1;
			playerInventory.decreaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyNotEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		assertFalse(player.attemptToBuyEnergy(market,1000000));
		new Verifications(){{
			market.getCostEnergy(1000000);times=1;
			market.buyEnergy(anyInt);times=0;
			playerInventory.increaseEnergyQuantity(anyInt);times=0;
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when used correctly.
	 */
	@Test
	public void testReturnTrueBuyOreHaveEnoughMoney(){
		new Expectations(){{
			market.getCostOre(anyInt);result=10;
			playerInventory.getMoneyQuantity();result=10;
		}};
		assertTrue(player.attemptToBuyOre(market,6));
		new Verifications(){{
			market.getCostOre(6);times=1;
			market.buyOre(6);times=1;
			playerInventory.increaseOreQuantity(6);times=1;
			playerInventory.decreaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyOreNotEnoughMoney(){
		new Expectations(){{
			market.getCostOre(anyInt);result=100000;
			playerInventory.getMoneyQuantity();result=0;
		}};
		assertFalse(player.attemptToBuyOre(market,1000000));
		new Verifications(){{
			market.getCostOre(1000000);times=1;
			market.buyOre(anyInt);times=0;
			playerInventory.increaseOreQuantity(anyInt);times=0;
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that returns true when used correctly.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(anyInt);result=10;
			playerInventory.getMoneyQuantity();result=10000;
		}};
		
		assertTrue(player.attemptToBuyRoboticons(market,6));
		new Verifications(){{
			market.getCostRoboticons(6);times=1;
			market.buyRoboticons(6);times=1;
			playerInventory.decreaseMoneyQuantity(10);times=1;
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsNotEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(anyInt);result=99999999;
			playerInventory.getMoneyQuantity();result=0;
		}};
		assertFalse(player.attemptToBuyRoboticons(market,1000000));
		new Verifications(){{
			market.getCostRoboticons(1000000);times=1;
			market.buyRoboticons(anyInt);times=0;
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,anyInt);times=0;
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it returns false when the player does not have enough ore.
	 */
	@Test
	public void testSellOreNotEnoughOre(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=0;
		}};
		assertFalse(player.attemptToSellOre(market,1000000));
		new Verifications(){{
			market.getCostOre(1000000);times=0;
			market.sellOre(anyInt);times=0;
			playerInventory.decreaseOreQuantity(anyInt);times=0;
			playerInventory.increaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it returns true when used as intended.
	 */
	@Test
	public void testSellOreEnoughOreReturnTrue(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=10;
			market.getCostOre(anyInt);result=100;
		}};
		assertTrue(player.attemptToSellOre(market,10));
		new Verifications(){{
			market.getCostOre(10);times=1;
			market.sellOre(10);times=1;
			playerInventory.decreaseOreQuantity(10);times=1;
			playerInventory.increaseMoneyQuantity(100);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellenergy} ensures that it returns false when the player does not have enough energy.
	 */
	@Test
	public void testSellEnergyNotEnoughEnergy(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=0;
		}};
		assertFalse(player.attemptToSellEnergy(market,1000000));
		new Verifications(){{
			market.getCostEnergy(anyInt);times=0;
			market.sellEnergy(anyInt);times=0;
			playerInventory.decreaseEnergyQuantity(anyInt);times=0;
			playerInventory.increaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it returns true when used as intended.
	 */
	@Test
	public void testSellEnergyEnoughEnergyReturnTrue(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=10;
			market.getCostEnergy(anyInt);result=100;
		}};
		assertTrue(player.attemptToSellEnergy(market,10));
		new Verifications(){{
			market.getCostEnergy(10);times=1;
			market.sellEnergy(10);times=1;
			playerInventory.decreaseEnergyQuantity(10);times=1;
			playerInventory.increaseMoneyQuantity(100);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when used properly.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonsHaveEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10000;
		}};
		assertTrue(player.attemptToCustomiseRoboticon(market,RoboticonCustomisation.ORE));
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
			playerInventory.decreaseMoneyQuantity(anyInt);times=1;
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, anyInt);times=1;
		}};
	}
	
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory.
	 */
	@Test
	public void testNotEnoughMoneyCustomiseRoboticons(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=20000000;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE));
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=0;
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have any uncustomised roboticons in their inventory.
	 */
	@Test
	public void testNoUncustomisedRoboticonsCustomiseRoboticons(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE));
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, anyInt);times=0;
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, anyInt);times=0;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);times=0;
		}};
	}	
	
}
