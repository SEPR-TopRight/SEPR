package com.topright.roboticon;
import static org.junit.Assert.*;
import java.util.EnumMap;
import org.junit.*;	
import mockit.*;
import mockit.integration.junit4.*;
import org.junit.runner.RunWith;

/**
 * Test case for {@link Player}
 * @author jcn509
 */

@RunWith(JMockit.class)
public class PlayerTestCase {
	
	private Player player;
	@Mocked private PlayerInventory playerInventory;
	@Mocked private Market market;
	@Mocked private Plot plot;

	/**
	 * Runs before every test, creates PlayerInventory and Player objects
	 */
	@Before
	public void setup(){
		playerInventory = new PlayerInventory(5,4,new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class),1000);
		player = new Player(playerInventory);
		market = Market.getInstance();
		market.setInventory(new MarketInventory(10,10,10));
		plot = new Plot(PlotSpecialism.ORE);
	}
	
	///////////////////////////////////////////////////////////////// increaseOreQuantity
	
	/**
	 * Tests {@link Player#increaseOreQuantity} ensures that it calls the increaseOreQuantity method from the playerInventory class (only needs one test as it is just a simple wrapper method)
	 */
	@Test
	public void testIncreaseOreQuantity(){
		player.increaseOreQuantity(5);
		new Verifications(){{
			playerInventory.increaseOreQuantity(5);
		}};
	}
	
	///////////////////////////////////////////////////////////////// increaseEnergyQuantity
	/**
	 * Tests {@link Player#increaseEnergyQuantity} ensures that it calls the increaseEnergyQuantity method from the playerInventory class (only needs one test as it is just a simple wrapper method)
	 */
	@Test
	public void testIncreaseEnergyQuantity(){
		player.increaseEnergyQuantity(5);
		new Verifications(){{
			playerInventory.increaseEnergyQuantity(5);
		}};
	}
	
	
	
	
	///////////////////////////////////////////////////////////////// attempttoBuyEnergy
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostEnergy(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		assertTrue(player.attemptToBuyEnergy(6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it increases the quantity of energy stored in the inventory by 6 when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostEnergy(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};

		player.attemptToBuyEnergy(6);
		new Verifications(){{
			playerInventory.increaseEnergyQuantity(6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostEnergy(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyEnergy(6);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that player calls the market when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testCallMarketBuyEnergyHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostEnergy(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyEnergy(6);
		new Verifications(){{
			market.buyEnergyFromMarket(6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostEnergy(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		assertTrue(player.attemptToBuyEnergy(1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it increases the quantity of energy stored in the inventory by 1 when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostEnergy(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyEnergy(1);
		new Verifications(){{
			playerInventory.increaseEnergyQuantity(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostEnergy(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyEnergy(1);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(10);times=1; // 10 = the cost of 1 energy as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that player calls the market when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testCallMarketBuyEnergyHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostEnergy(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyEnergy(1);
		new Verifications(){{
			market.buyEnergyFromMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it returns true when the player tries to buy 0 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyZero(){
	
		assertTrue(player.attemptToBuyEnergy(0));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that no energy is added to the inventory when the player tries to buy 0 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyZero(){
		
		player.attemptToBuyEnergy(0);
		new Verifications(){{
			playerInventory.increaseEnergyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that no money is removed from the inventory when the player tries to buy 0 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyZero(){
			
		player.attemptToBuyEnergy(0);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when the player has exactly enough money to buy 3 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		assertTrue(player.attemptToBuyEnergy(3));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it increases the quantity of energy stored in the inventory by 3 when the player has more than enough money and tries to buy 3 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		player.attemptToBuyEnergy(3);
		new Verifications(){{
			market.buyEnergyFromMarket(3);times=1;
			playerInventory.increaseEnergyQuantity(3);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		player.attemptToBuyEnergy(3);
		new Verifications(){{
			market.buyEnergyFromMarket(3);times=1;
			playerInventory.decreaseMoneyQuantity(10);times=1; // 10 = the cost of 3 energy as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it returns false if a negative value is passed to it.
	 */
	@Test
	public void testReturnFalseBuyEnergyNegativeQuantity(){
		assertFalse(player.attemptToBuyEnergy(-1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no money is taken from the inventory when the player attempts to buy a negative quantity.
	 */
	@Test
	public void testBuyEnergyDecreaseMoneyNegativeQuantity(){
		
		player.attemptToBuyEnergy(-1);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no energy is added to the inventory when the player attempts to buy a negative quantity
	 */
	@Test
	public void testBuyEnergyIncreaseEnergyNegativeQuantity(){
			
		player.attemptToBuyEnergy(-1);
		new Verifications(){{
			playerInventory.increaseEnergyQuantity(anyInt);times=0;
		}};
	}	
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyReturnFalseNotEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		assertFalse(player.attemptToBuyEnergy(1000000));
		
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no money is taken from the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyDecreaseMoneyNotEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		player.attemptToBuyEnergy(1000000);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no energy is added to the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyIncreaseEnergyNotEnoughMoney(){
		new Expectations(){{
			market.getCostEnergy(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		player.attemptToBuyEnergy(1000000);
		new Verifications(){{
			playerInventory.increaseEnergyQuantity(anyInt);times=0;
		}};
	}	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////// attempttoBuyOre
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testReturnTrueBuyOreHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostOre(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		assertTrue(player.attemptToBuyOre(6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it increases the quantity of ore stored in the inventory by 6 when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostOre(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};

		player.attemptToBuyOre(6);
		new Verifications(){{
			playerInventory.increaseOreQuantity(6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostOre(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyOre(6);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that player calls the market when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testCallMarketBuyOreHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostOre(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyOre(6);
		new Verifications(){{
			market.buyOreFromMarket(6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testReturnTrueBuyOreHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostOre(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		assertTrue(player.attemptToBuyOre(1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it increases the quantity of ore stored in the inventory by 1 when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostOre(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyOre(1);
		new Verifications(){{
			playerInventory.increaseOreQuantity(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostOre(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyOre(1);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(10);times=1; // 10 = the cost of 1 ore as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that player calls the market when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testCallMarketBuyOreHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostOre(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyOre(1);
		new Verifications(){{
			market.buyOreFromMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it returns true when the player tries to buy 0 ore.
	 */
	@Test
	public void testReturnTrueBuyOreZero(){
	
		assertTrue(player.attemptToBuyOre(0));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that no ore is added to the inventory when the player tries to buy 0 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreZero(){
		
		player.attemptToBuyOre(0);
		new Verifications(){{
			playerInventory.increaseOreQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that no money is removed from the inventory when the player tries to buy 0 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreZero(){
			
		player.attemptToBuyOre(0);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when the player has exactly enough money to buy 3 ore.
	 */
	@Test
	public void testReturnTrueBuyOreHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostOre(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		assertTrue(player.attemptToBuyOre(3));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it increases the quantity of ore stored in the inventory by 3 when the player has more than enough money and tries to buy 3 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostOre(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		player.attemptToBuyOre(3);
		new Verifications(){{
			market.buyOreFromMarket(3);times=1;
			playerInventory.increaseOreQuantity(3);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostOre(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		player.attemptToBuyOre(3);
		new Verifications(){{
			market.buyOreFromMarket(3);times=1;
			playerInventory.decreaseMoneyQuantity(10);times=1; // 10 = the cost of 3 ore as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it returns false if a negative value is passed to it.
	 */
	@Test
	public void testReturnFalseBuyOreNegativeQuantity(){
		assertFalse(player.attemptToBuyOre(-1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no money is taken from the inventory when the player attempts to buy a negative quantity.
	 */
	@Test
	public void testBuyOreDecreaseMoneyNegativeQuantity(){
		player.attemptToBuyOre(-1);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no ore is added to the inventory when the player attempts to buy a negative quantity
	 */
	@Test
	public void testBuyOreIncreaseOreNegativeQuantity(){
			
		player.attemptToBuyOre(-1);
		new Verifications(){{
			playerInventory.increaseOreQuantity(anyInt);times=0;
		}};
	}	
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyOreReturnFalseNotEnoughMoney(){
		new Expectations(){{
			market.getCostOre(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		assertFalse(player.attemptToBuyOre(1000000));
		
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no money is taken from the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyOreDecreaseMoneyNotEnoughMoney(){
		new Expectations(){{
			market.getCostOre(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		player.attemptToBuyOre(1000000);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no ore is added to the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyOreIncreaseOreNotEnoughMoney(){
		new Expectations(){{
			market.getCostOre(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		player.attemptToBuyOre(1000000);
		new Verifications(){{
			playerInventory.increaseOreQuantity(anyInt);times=0;
		}};
	}	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////// attempttoBuyRoboticons
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns true when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostRoboticons(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		assertTrue(player.attemptToBuyRoboticons(6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it increases the quantity of roboticons stored in the inventory by 6 when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostRoboticons(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};

		player.attemptToBuyRoboticons(6);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostRoboticons(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyRoboticons(6);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that player calls the market when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testCallMarketBuyRoboticonsHaveEnoughMoneySix(){
		new Expectations(){{
			market.getCostRoboticons(6); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyRoboticons(6);
		new Verifications(){{
			market.buyRoboticonsFromMarket(6);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns true when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostRoboticons(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		assertTrue(player.attemptToBuyRoboticons(1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it increases the quantity of roboticons stored in the inventory by 1 when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostRoboticons(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyRoboticons(1);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostRoboticons(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyRoboticons(1);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(10);times=1; // 10 = the cost of 1 roboticons as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that player calls the market when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testCallMarketBuyRoboticonsHaveEnoughMoneyOne(){
		new Expectations(){{
			market.getCostRoboticons(1); result = 10;
			playerInventory.getMoneyQuantity();result=100; // More than enough money
		}};
		
		player.attemptToBuyRoboticons(1);
		new Verifications(){{
			market.buyRoboticonsFromMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it returns true when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsZero(){
	
		assertTrue(player.attemptToBuyRoboticons(0));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that no roboticons is added to the inventory when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsZero(){
		
		player.attemptToBuyRoboticons(0);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that no money is removed from the inventory when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsZero(){
			
		player.attemptToBuyRoboticons(0);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns true when the player has exactly enough money to buy 3 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		assertTrue(player.attemptToBuyRoboticons(3));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it increases the quantity of roboticons stored in the inventory by 3 when the player has more than enough money and tries to buy 3 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		player.attemptToBuyRoboticons(3);
		new Verifications(){{
			market.buyRoboticonsFromMarket(3);times=1;
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 3);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsHaveExactlyEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(3); result = 10;
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		
		player.attemptToBuyRoboticons(3);
		new Verifications(){{
			market.buyRoboticonsFromMarket(3);times=1;
			playerInventory.decreaseMoneyQuantity(10);times=1; // 10 = the cost of 3 roboticons as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it returns false if a negative value is passed to it.
	 */
	@Test
	public void testReturnFalseBuyRoboticonsNegativeQuantity(){
		assertFalse(player.attemptToBuyRoboticons(-1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no money is taken from the inventory when the player attempts to buy a negative quantity.
	 */
	@Test
	public void testBuyRoboticonsDecreaseMoneyNegativeQuantity(){
		player.attemptToBuyRoboticons(-1);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no roboticons are added to the inventory when the player attempts to buy a negative quantity
	 */
	@Test
	public void testBuyRoboticonsIncreaseRoboticonsNegativeQuantity(){
			
		player.attemptToBuyRoboticons(-1);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, anyInt);times=0;
		}};
	}	
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsReturnFalseNotEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		assertFalse(player.attemptToBuyRoboticons(1000000));
		
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no money is taken from the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsDecreaseMoneyNotEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		player.attemptToBuyRoboticons(1000000);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no roboticons is added to the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsIncreaseRoboticonsNotEnoughMoney(){
		new Expectations(){{
			market.getCostRoboticons(anyInt); result = 1000;
			playerInventory.getMoneyQuantity(); result = 0;
		}};
		
		player.attemptToBuyRoboticons(1000000);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, anyInt);times=0;
		}};
	}		
	
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////////// attemptToSellEnergy
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns true when the player has more than enough energy and tries to sell 5 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyHaveEnoughEnergyFive(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		assertTrue(player.attemptToSellEnergy(5));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it decreases the quantity of energy stored in the inventory by 5 when the player has more than enough money and tries to sell 5 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyHaveEnoughEnergyFive(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};

		player.attemptToSellEnergy(5);
		new Verifications(){{
			playerInventory.decreaseEnergyQuantity(5);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough energy and tries to sell 5 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyHaveEnoughEnergyFive(){
		new Expectations(){{
			market.getCostEnergy(5); result = 10;
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		player.attemptToSellEnergy(5);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that player calls the market when the player has more than enough energy and tries to sell 5 energy.
	 */
	@Test
	public void testCallMarketSellEnergyHaveEnoughEnergyFive(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		player.attemptToSellEnergy(5);
		new Verifications(){{
			market.sellEnergyToMarket(5);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns true when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyHaveEnoughEnergyOne(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		assertTrue(player.attemptToSellEnergy(1));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it decreases the quantity of energy stored in the inventory by 1 when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyHaveEnoughEnergyOne(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		player.attemptToSellEnergy(1);
		new Verifications(){{
			playerInventory.decreaseEnergyQuantity(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyHaveEnoughEnergyOne(){
		new Expectations(){{
			market.getCostEnergy(1); result = 10;
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		player.attemptToSellEnergy(1);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(10);times=1; // 10 = the cost of 1 energy as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that player calls the market when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testCallMarketSellEnergyHaveEnoughEnergyOne(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=6; // More than enough energy
		}};
		
		player.attemptToSellEnergy(1);
		new Verifications(){{
			market.sellEnergyToMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it returns true when the player tries to sell 0 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyZero(){
	
		assertTrue(player.attemptToSellEnergy(0));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that no energy is removed from the inventory when the player tries to sell 0 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyZero(){
		
		player.attemptToSellEnergy(0);
		new Verifications(){{
			playerInventory.decreaseEnergyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that no money is added to the inventory when the player tries to sell 0 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyZero(){
			
		player.attemptToSellEnergy(0);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns true when the player has energy and tries to sell 3 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyHaveExactlyEnoughEnergy(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=3; // Exactly enough energy
		}};
		
		assertTrue(player.attemptToSellEnergy(3));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it decreases the quantity of energy stored in the inventory by 3 when the player has 3 energy and tries to sell 3 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyHaveExactlyEnoughEnergy(){
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=3; // Exactly enough energy
		}};
		
		player.attemptToSellEnergy(3);
		new Verifications(){{
			playerInventory.decreaseEnergyQuantity(3);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to sell 6 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyHaveExactlyEnoughEnergy(){
		new Expectations(){{
			market.getCostEnergy(3); result = 10;
			playerInventory.getEnergyQuantity();result=3; // Exactly enough energy
		}};
		
		player.attemptToSellEnergy(3);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(10);times=1; // 10 = the cost of 3 energy as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it returns false if a negative value is passed to it.
	 */
	@Test
	public void testReturnFalseSellEnergyNegativeQuantity(){
		assertFalse(player.attemptToSellEnergy(-1));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no money is added to the inventory when the player attempts to sell a negative quantity of energy.
	 */
	@Test
	public void testSellEnergyIncreaseMoneyNegativeQuantity(){
		
		player.attemptToSellEnergy(-1);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no energy is taken from the inventory when the player attempts to sell a negative quantity
	 */
	@Test
	public void testSellEnergyDecreaseEnergyNegativeQuantity(){
		
		player.attemptToSellEnergy(-1);
		new Verifications(){{
			playerInventory.decreaseEnergyQuantity(anyInt);times=0;
		}};
	}	
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns false when the player does not have enough energy.
	 */
	@Test
	public void testSellEnergyReturnFalseNotEnoughEnergy(){
		new Expectations(){{
			playerInventory.getEnergyQuantity(); result = 1000000-1;
		}};
		
		assertFalse(player.attemptToSellEnergy(1000000));
		
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no money is added to the inventory when the player does not have enough energy.
	 */
	@Test
	public void testBuyEnergyIncreaseMoneyNotEnoughEnergy(){
		new Expectations(){{
			playerInventory.getEnergyQuantity(); result = 1000000-1;
		}};
		
		player.attemptToSellEnergy(1000000);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no energy is taken from the inventory when the player does not have enough energy.
	 */
	@Test
	public void testSellEnergyDecreaseEnergyNotEnoughEnergy(){
		new Expectations(){{
			playerInventory.getEnergyQuantity(); result = 1000000-1;
		}};
		
		player.attemptToSellEnergy(1000000);
		new Verifications(){{
			playerInventory.decreaseEnergyQuantity(anyInt);times=0;
		}};
	}	
	
	
	
	///////////////////////////////////////////////////////////////// attemptToSellOre
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns true when the player has more than enough ore and tries to sell 5 ore.
	 */
	@Test
	public void testReturnTrueSellOreHaveEnoughOreFive(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		assertTrue(player.attemptToSellOre(5));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it decreases the quantity of ore stored in the inventory by 5 when the player has more than enough money and tries to sell 5 ore.
	 */
	@Test
	public void testDecreaseOreSellOreHaveEnoughOreFive(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};

		player.attemptToSellOre(5);
		new Verifications(){{
			playerInventory.decreaseOreQuantity(5);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough ore and tries to sell 5 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreHaveEnoughOreFive(){
		new Expectations(){{
			market.getCostOre(5); result = 10;
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		player.attemptToSellOre(5);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(10);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that player calls the market when the player has more than enough ore and tries to sell 5 ore.
	 */
	@Test
	public void testCallMarketSellOreHaveEnoughOreFive(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		player.attemptToSellOre(5);
		new Verifications(){{
			market.sellOreToMarket(5);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns true when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testReturnTrueSellOreHaveEnoughOreOne(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		assertTrue(player.attemptToSellOre(1));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it decreases the quantity of ore stored in the inventory by 1 when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testDecreaseOreSellOreHaveEnoughOreOne(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		player.attemptToSellOre(1);
		new Verifications(){{
			playerInventory.decreaseOreQuantity(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreHaveEnoughOreOne(){
		new Expectations(){{
			market.getCostOre(1); result = 10;
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		player.attemptToSellOre(1);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(10);times=1; // 10 = the cost of 1 ore as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that player calls the market when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testCallMarketSellOreHaveEnoughOreOne(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=6; // More than enough ore
		}};
		
		player.attemptToSellOre(1);
		new Verifications(){{
			market.sellOreToMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it returns true when the player tries to sell 0 ore.
	 */
	@Test
	public void testReturnTrueSellOreZero(){
	
		assertTrue(player.attemptToSellOre(0));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that no ore is removed from the inventory when the player tries to sell 0 ore.
	 */
	@Test
	public void testDecreaseOreSellOreZero(){
		
		player.attemptToSellOre(0);
		new Verifications(){{
			playerInventory.decreaseOreQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that no money is added to the inventory when the player tries to sell 0 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreZero(){
			
		player.attemptToSellOre(0);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(anyInt);times=0; 
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns true when the player has ore and tries to sell 3 ore.
	 */
	@Test
	public void testReturnTrueSellOreHaveExactlyEnoughOre(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=3; // Exactly enough ore
		}};
		
		assertTrue(player.attemptToSellOre(3));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it decreases the quantity of ore stored in the inventory by 3 when the player has 3 ore and tries to sell 3 ore.
	 */
	@Test
	public void testDecreaseOreSellOreHaveExactlyEnoughOre(){
		new Expectations(){{
			playerInventory.getOreQuantity();result=3; // Exactly enough ore
		}};
		
		player.attemptToSellOre(3);
		new Verifications(){{
			playerInventory.decreaseOreQuantity(3);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to sell 6 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreHaveExactlyEnoughOre(){
		new Expectations(){{
			market.getCostOre(3); result = 10;
			playerInventory.getOreQuantity();result=3; // Exactly enough ore
		}};
		
		player.attemptToSellOre(3);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(10);times=1; // 10 = the cost of 3 ore as set up in the expectations
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it returns false if a negative value is passed to it.
	 */
	@Test
	public void testReturnFalseSellOreNegativeQuantity(){
		assertFalse(player.attemptToSellOre(-1));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no money is added to the inventory when the player attempts to sell a negative quantity of ore.
	 */
	@Test
	public void testSellOreIncreaseMoneyNegativeQuantity(){
		
		player.attemptToSellOre(-1);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no ore is taken from the inventory when the player attempts to sell a negative quantity
	 */
	@Test
	public void testSellOreDecreaseOreNegativeQuantity(){
		
		player.attemptToSellOre(-1);
		new Verifications(){{
			playerInventory.decreaseOreQuantity(anyInt);times=0;
		}};
	}	
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns false when the player does not have enough ore.
	 */
	@Test
	public void testSellOreReturnFalseNotEnoughOre(){
		new Expectations(){{
			playerInventory.getOreQuantity(); result = 1000000-1;
		}};
		
		assertFalse(player.attemptToSellOre(1000000));
		
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no money is added to the inventory when the player does not have enough ore.
	 */
	@Test
	public void testBuyOreIncreaseMoneyNotEnoughOre(){
		new Expectations(){{
			playerInventory.getOreQuantity(); result = 1000000-1;
		}};
		
		player.attemptToSellOre(1000000);
		new Verifications(){{
			playerInventory.increaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no ore is taken from the inventory when the player does not have enough ore.
	 */
	@Test
	public void testSellOreDecreaseOreNotEnoughOre(){
		new Expectations(){{
			playerInventory.getOreQuantity(); result = 1000000-1;
		}};
		
		player.attemptToSellOre(1000000);
		new Verifications(){{
			playerInventory.decreaseOreQuantity(anyInt);times=0;
		}};
	}	
		
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////// attemptToCustomiseRoboticon energy
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
			playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
			
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
			new Expectations(){{
						market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
						playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
						playerInventory.getMoneyQuantity();result=10000; // More than enough money
			}};
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
			new Verifications(){{
				playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
			}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
	playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};	
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
			new Expectations(){{
						market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=8;
						playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
						playerInventory.getMoneyQuantity();result=10000; // More than enough money
			}};
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
			new Verifications(){{
				playerInventory.decreaseMoneyQuantity(8);times=1;
			}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=7;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
	playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(7);times=1;
		}};	
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=7;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(7);times=1;
		}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=7;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(7);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored by the correct amount when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
			new Expectations(){{
						market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
						playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
						playerInventory.getMoneyQuantity();result=10000; // More than enough money
			}};
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
			new Verifications(){{
				playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
			}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored by the correct amount when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
	playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
		}};	
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored by the correct amount when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
		}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored by the correct amount when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseRobpoticonsNotEnoughMoneyCustomiseRoboticonsEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(11);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of energy roboticons stored does not increase if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsEnergy(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseRobpoticonsNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(11);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of energy roboticons stored does not increase if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testReturnFalseNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}	
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testDecreaseMoneyNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of energy roboticons stored is not increased when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=0;
		}};
	}
	
	/////////////////////////////////////////////////////////////////// attemptToCustomiseRoboticon ore
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
			playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
			
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
			new Expectations(){{
						market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
						playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
						playerInventory.getMoneyQuantity();result=10000; // More than enough money
			}};
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
			new Verifications(){{
				playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
			}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
	playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};	
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
			new Expectations(){{
						market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=8;
						playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
						playerInventory.getMoneyQuantity();result=10000; // More than enough money
			}};
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
			new Verifications(){{
				playerInventory.decreaseMoneyQuantity(8);times=1;
			}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=7;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
	playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(7);times=1;
		}};	
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=7;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(7);times=1;
		}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseMoneyCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=7;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(7);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored by the correct amount when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
			new Expectations(){{
						market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
						playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=7; // More than enough money
						playerInventory.getMoneyQuantity();result=10000; // More than enough money
			}};
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
			new Verifications(){{
				playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
			}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored by the correct amount when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
	playerInventory.getMoneyQuantity();result=10000; // More than enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
		}};	
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored by the correct amount when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=10; // More than enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
		}};
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored by the correct amount when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=10;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // Exactly enough roboticons
			playerInventory.getMoneyQuantity();result=10; // Exactly enough money
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=1;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseRobpoticonsNotEnoughMoneyCustomiseRoboticonsOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(11);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of ore roboticons stored does not increase if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsOre(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=11;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsOre2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseRobpoticonsNotEnoughMoneyCustomiseRoboticonsOre2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsOre2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(11);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of ore roboticons stored does not increase if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsOre2(){
		new Expectations(){{
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=110;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1;
			playerInventory.getMoneyQuantity();result=10;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testReturnFalseNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}	
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testDecreaseMoneyNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseMoneyQuantity(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of ore roboticons stored is not increased when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0;
		}};
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, anyInt);times=0;
		}};
	}
	
	////////////////////////////////////////////////////////////////////////// attemptToPlaceRoboticon ore
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot is not owned by the player
	 */
	@Test
	public void testReturnFalsePlaceRoboticonUnownedPlotOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE); result=5; // Have enough to place one
			plot.hasRoboticon(); result = false;
			plot.getPlayer(); result = null;
		}};
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot already has a roboticon on it 
	 */
	@Test
	public void testReturnFalsePlaceRoboticonAlreadyHasRoboticonOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE); result=5; // Have enough to place one
			plot.hasRoboticon(); result = true;
		}};
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the player is attempting to place an ore roboticon on the plot and has no ore roboticons
	 */
	@Test
	public void testReturnFalseNoOreRoboticonsOre(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=0; // No ore roboticons
		}};
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the player is attempting to place an uncustomised roboticon on the plot
	 */
	@Test
	public void testReturnFalseUncustomisedRoboticonOre(){
		
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.UNCUSTOMISED));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns true if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have one ore roboticon)
	 */
	@Test
	public void testReturnTrueOreRoboticonOrePlotPlayerHasOneOreRoboticon(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=1; // Player has 1 ore roboticon
		}};
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of ore roboticons stored is reduced by 1 if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have one ore roboticon)
	 */
	@Test
	public void testReduceRoboticonsOreRoboticonOrePlotPlayerHasOneOreRoboticon(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=1; // Player has 1 ore roboticon
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, 1);times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an ore roboticon on a plot (for a plot that they own when they have one ore roboticon) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonOreRoboticonOrePlotPlayerHasOneOreRoboticon(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=1; // Player has 1 ore roboticon
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);
		new Verifications(){{
			plot.placeRoboticon(RoboticonCustomisation.ORE);times=1; // Check if roboticon placed
		}};
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns true if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have three ore roboticons)
	 */
	@Test
	public void testReturnTrueOreRoboticonOrePlotPlayerHasThreeOreRoboticons(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=3; // Player has 3 ore roboticons
		}};
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of ore roboticons stored is reduced by 1 if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have three ore roboticons)
	 */
	@Test
	public void testReduceRoboticonsOreRoboticonOrePlotPlayerHasHasThreeOreRoboticons(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=3; // Player has 3 ore roboticons
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, 1);times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an ore roboticon on a plot (for a plot that they own when they have three ore roboticons) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonOreRoboticonOrePlotPlayerHasThreeOreRoboticons(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=3; // Player has 1 ore roboticon
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);
		new Verifications(){{
			plot.placeRoboticon(RoboticonCustomisation.ORE);times=1; // Check if roboticon placed
		}};
		
	}
	
	
	////////////////////////////////////////////////////////////////////////// attemptToPlaceRoboticon energy
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot is not owned by the player
	 */
	@Test
	public void testReturnFalsePlaceRoboticonUnownedPlotEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY); result=5; // Have enough to place one
			plot.hasRoboticon(); result = false;
			plot.getPlayer(); result = null;
		}};
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot already has a roboticon on it 
	 */
	@Test
	public void testReturnFalsePlaceRoboticonAlreadyHasRoboticonEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY); result=5; // Have enough to place one
			plot.hasRoboticon(); result = true;
		}};
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the player is attempting to place an energy roboticon on the plot and has no energy roboticons
	 */
	@Test
	public void testReturnFalseNoEnergyRoboticonsEnergy(){
		new Expectations(){{
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=0; // No energy roboticons
		}};
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the player is attempting to place an uncustomised roboticon on the plot
	 */
	@Test
	public void testReturnFalseUncustomisedRoboticonEnergy(){
		
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.UNCUSTOMISED));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns true if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have one energy roboticon)
	 */
	@Test
	public void testReturnTrueEnergyRoboticonEnergyPlotPlayerHasOneEnergyRoboticon(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=1; // Player has 1 energy roboticon
		}};
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of energy roboticons stenergyd is reduced by 1 if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have one energy roboticon)
	 */
	@Test
	public void testReduceRoboticonsEnergyRoboticonEnergyPlotPlayerHasOneEnergyRoboticon(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=1; // Player has 1 energy roboticon
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1);times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an energy roboticon on a plot (for a plot that they own when they have one energy roboticon) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonEnergyRoboticonEnergyPlotPlayerHasOneEnergyRoboticon(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=1; // Player has 1 energy roboticon
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);
		new Verifications(){{
			plot.placeRoboticon(RoboticonCustomisation.ENERGY);times=1; // Check if roboticon placed
		}};
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns true if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have three energy roboticons)
	 */
	@Test
	public void testReturnTrueEnergyRoboticonEnergyPlotPlayerHasThreeEnergyRoboticons(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=3; // Player has 3 energy roboticons
		}};
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of energy roboticons stenergyd is reduced by 1 if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have three energy roboticons)
	 */
	@Test
	public void testReduceRoboticonsEnergyRoboticonEnergyPlotPlayerHasHasThreeEnergyRoboticons(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=3; // Player has 3 energy roboticons
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);
		new Verifications(){{
			playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1);times=1;
		}};
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an energy roboticon on a plot (for a plot that they own when they have three energy roboticons) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonEnergyRoboticonEnergyPlotPlayerHasThreeEnergyRoboticons(){
		new Expectations(){{
			plot.getPlayer();result=player;
			plot.hasRoboticon();result=false;
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=3; // Player has 1 energy roboticon
		}};
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);
		new Verifications(){{
			plot.placeRoboticon(RoboticonCustomisation.ENERGY);times=1; // Check if roboticon placed
		}};
		
	}
	
	/**
	 * Tests {@link Player#calculateScore} ensures that it returns 0 when the player has no money
	 */
	@Test
	public void testCalculateScoreNoMoney(){
		new Expectations(){{
			playerInventory.getMoneyQuantity();result=0; // No money in the player's inventory
		}};
		
		 // Doesn't seem to like the fact that calculateScore returns an Integer and not an int
		 // Therefore the result is cast to an int
		assertEquals(0,(int)player.calculateScore());
	}
	
	/**
	 * Tests {@link Player#calculateScore} ensures that it returns 1 when the player has 1 money in their inventory
	 */
	@Test
	public void testCalculateScore1Money(){
		new Expectations(){{
			playerInventory.getMoneyQuantity();result=1; // 1 money in the player's inventory
		}};
		
		 // Doesn't seem to like the fact that calculateScore returns an Integer and not an int
		 // Therefore the result is cast to an int
		assertEquals(1,(int)player.calculateScore());
	}
	
	/**
	 * Tests {@link Player#calculateScore} ensures that it returns 5 when the player has 5 money in their inventory
	 */
	@Test
	public void testCalculateScore5Money(){
		new Expectations(){{
			playerInventory.getMoneyQuantity();result=5; // 5 money in the player's inventory
		}};
		
		 // Doesn't seem to like the fact that calculateScore returns an Integer and not an int
		 // Therefore the result is cast to an int
		assertEquals(5,(int)player.calculateScore());
	}
}
