package com.topright.roboticon;
import static org.junit.Assert.*;
import java.util.EnumMap;
import org.junit.*;	

/**
 * Integration tests involving  {@link Player} {@link PlayerInventory} {@link Market} {@link MarketInventory} {@link Plot}
 * @author jcn509
 */

public class PlayerIntegrationTests {
	
	private Player player;
	private PlayerInventory playerInventory;
	private Market market;
	private Plot plot;
	private MarketInventory marketInventory;

	/**
	 * Runs before every test, creates PlayerInventory and Player objects
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities;
		roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		
		// Initialise quantities
		roboticonQuantities.put(RoboticonCustomisation.UNCUSTOMISED, 0);
		roboticonQuantities.put(RoboticonCustomisation.ORE, 0);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 0);
		
		playerInventory = new PlayerInventory(1,1,roboticonQuantities,1);
		player = new Player(playerInventory);
		market = Market.getInstance();
		marketInventory = new MarketInventory(10,10,10);
		market.setInventory(marketInventory);
		plot = new Plot(PlotSpecialism.ORE);
	}
	
	///////////////////////////////////////////////////////////////// increaseOreQuantity
	
	/**
	 * Tests {@link Player#increaseOreQuantity} ensures that the quantity of ore in the players inventory is increased correctly (only needs one test as it is just a simple wrapper method)
	 */
	@Test
	public void testIncreaseOreQuantity(){
		int oreBefore = playerInventory.getOreQuantity();
		player.increaseOreQuantity(5);
		assertEquals(oreBefore+5,playerInventory.getOreQuantity());
	}
	
	///////////////////////////////////////////////////////////////// increaseOreQuantity
	/**
	 * Tests {@link Player#increaseEnergyQuantity} ensures that the quantity of energy in the players inventory is increased correctly (only needs one test as it is just a simple wrapper method)
	 */
	@Test
	public void testIncreaseEnergyQuantity(){
		int energyBefore = playerInventory.getEnergyQuantity();
		player.increaseEnergyQuantity(5);
		assertEquals(energyBefore+5,playerInventory.getEnergyQuantity());
	}
	
	///////////////////////////////////////////////////////////////// attemptToBuyEnergy
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveMenergyThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(7)); // more than enough money in the inventory
		
		assertTrue(player.attemptToBuyEnergy(6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it increases the quantity of energy stored in the inventory by 6 when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyHaveMenergyThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(7)); // more than enough money in the inventory
		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToBuyEnergy(6);
		assertEquals(energyBefore+6,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyHaveMenergyThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(7)); // more than enough money in the inventory
		int moneyBefore = playerInventory.getMoneyQuantity();
		
		player.attemptToBuyEnergy(6);
		assertEquals(moneyBefore-market.getCostEnergy(6),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that the quantity of energy in the market reduces by 6 when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testCallMarketBuyEnergyHaveMenergyThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(7)); // more than enough money in the inventory
		int energyBefore = marketInventory.getEnergyQuantity();
		
		player.attemptToBuyEnergy(6);
		assertEquals(energyBefore-6,marketInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveMenergyThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(2)); // more than enough money in the inventory
		
		assertTrue(player.attemptToBuyEnergy(1));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it increases the quantity of energy stored in the inventory by 1 when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyHaveMenergyThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(2)); // more than enough money in the inventory
		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToBuyEnergy(1);
		assertEquals(energyBefore+1,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyHaveMenergyThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(2)); // more than enough money in the inventory
		int moneyBefore = playerInventory.getMoneyQuantity();
		
		player.attemptToBuyEnergy(1);
		assertEquals(moneyBefore-market.getCostEnergy(1),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that the quantity of energy in the market reduces by 1 when the player has more than enough money and tries to buy 1 energy.
	 */
	@Test
	public void testCallMarketBuyEnergyHaveMenergyThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(2)); // more than enough money in the inventory
		int energyBefore = marketInventory.getEnergyQuantity();
		
		player.attemptToBuyEnergy(1);
		assertEquals(energyBefore-1,marketInventory.getEnergyQuantity());
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
		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToBuyEnergy(0);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that no money is removed from the inventory when the player tries to buy 0 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyZero(){
		int moneyQuantity = playerInventory.getMoneyQuantity();	
		player.attemptToBuyEnergy(0);
		assertEquals(moneyQuantity,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that no energy is removed from the market when the player tries to buy 0 energy.
	 */
	@Test
	public void testReduceMarketEnergyBuyEnergyZero(){
		int energyQuantity = marketInventory.getEnergyQuantity();	
		player.attemptToBuyEnergy(0);
		assertEquals(energyQuantity,marketInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when the player has exactly enough money to buy 3 energy.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(3)); // Left with the exact amount of money required
		
		assertTrue(player.attemptToBuyEnergy(3));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it increases the quantity of energy stored in the inventory by 3 when the player has more than enough money and tries to buy 3 energy.
	 */
	@Test
	public void testIncreaseEnergyBuyEnergyHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(3)); // Left with the exact amount of money required
		
		int energyBefore = playerInventory.getEnergyQuantity();
		
		player.attemptToBuyEnergy(3);
		assertEquals(energyBefore+3,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 energy.
	 */
	@Test
	public void testReduceMoneyBuyEnergyHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(3)); // Left with the exact amount of money required
		
		player.attemptToBuyEnergy(3);
		assertEquals(0,playerInventory.getMoneyQuantity());
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
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToBuyEnergy(-1);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no energy is added to the inventory when the player attempts to buy a negative quantity
	 */
	@Test
	public void testBuyEnergyIncreaseEnergyNegativeQuantity(){
		int energyBefore = playerInventory.getEnergyQuantity();	
		player.attemptToBuyEnergy(-1);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}	
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyReturnFalseNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(1000000) -1); // Not enough money
		
		assertFalse(player.attemptToBuyEnergy(1000000));
		
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no money is taken from the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyDecreaseMoneyNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(1000000) -1); // Not enough money
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToBuyEnergy(1000000);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures no energy is added to the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyIncreaseEnergyNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(1000000) -1); // Not enough money
		
		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToBuyEnergy(1000000);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	
	
	
    ///////////////////////////////////////////////////////////////// attemptToBuyOre
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testReturnTrueBuyOreHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(7)); // more than enough money in the inventory

		assertTrue(player.attemptToBuyOre(6));
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it increases the quantity of ore stored in the inventory by 6 when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(7)); // more than enough money in the inventory
		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToBuyOre(6);
		assertEquals(oreBefore+6,playerInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(7)); // more than enough money in the inventory
		int moneyBefore = playerInventory.getMoneyQuantity();

		player.attemptToBuyOre(6);
		assertEquals(moneyBefore-market.getCostOre(6),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that the quantity of ore in the market reduces by 6 when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testCallMarketBuyOreHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(7)); // more than enough money in the inventory
		int oreBefore = marketInventory.getOreQuantity();

		player.attemptToBuyOre(6);
		assertEquals(oreBefore-6,marketInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testReturnTrueBuyOreHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(2)); // more than enough money in the inventory

		assertTrue(player.attemptToBuyOre(1));
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it increases the quantity of ore stored in the inventory by 1 when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(2)); // more than enough money in the inventory
		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToBuyOre(1);
		assertEquals(oreBefore+1,playerInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(2)); // more than enough money in the inventory
		int moneyBefore = playerInventory.getMoneyQuantity();

		player.attemptToBuyOre(1);
		assertEquals(moneyBefore-market.getCostOre(1),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that the quantity of ore in the market reduces by 1 when the player has more than enough money and tries to buy 1 ore.
	 */
	@Test
	public void testCallMarketBuyOreHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(2)); // more than enough money in the inventory
		int oreBefore = marketInventory.getOreQuantity();

		player.attemptToBuyOre(1);
		assertEquals(oreBefore-1,marketInventory.getOreQuantity());
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
		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToBuyOre(0);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that no money is removed from the inventory when the player tries to buy 0 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreZero(){
		int moneyQuantity = playerInventory.getMoneyQuantity();	
		player.attemptToBuyOre(0);
		assertEquals(moneyQuantity,playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that no ore is removed from the market when the player tries to buy 0 ore.
	 */
	@Test
	public void testReduceMarketOreBuyOreZero(){
		int oreQuantity = marketInventory.getOreQuantity();	
		player.attemptToBuyOre(0);
		assertEquals(oreQuantity,marketInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when the player has exactly enough money to buy 3 ore.
	 */
	@Test
	public void testReturnTrueBuyOreHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostOre(3)); // Left with the exact amount of money required

		assertTrue(player.attemptToBuyOre(3));
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it increases the quantity of ore stored in the inventory by 3 when the player has more than enough money and tries to buy 3 ore.
	 */
	@Test
	public void testIncreaseOreBuyOreHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostOre(3)); // Left with the exact amount of money required

		int oreBefore = playerInventory.getOreQuantity();

		player.attemptToBuyOre(3);
		assertEquals(oreBefore+3,playerInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 ore.
	 */
	@Test
	public void testReduceMoneyBuyOreHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostOre(3)); // Left with the exact amount of money required

		player.attemptToBuyOre(3);
		assertEquals(0,playerInventory.getMoneyQuantity());
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
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToBuyOre(-1);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no ore is added to the inventory when the player attempts to buy a negative quantity
	 */
	@Test
	public void testBuyOreIncreaseOreNegativeQuantity(){
		int oreBefore = playerInventory.getOreQuantity();	
		player.attemptToBuyOre(-1);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}	

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyOreReturnFalseNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostOre(1000000) -1); // Not enough money

		assertFalse(player.attemptToBuyOre(1000000));

	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no money is taken from the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyOreDecreaseMoneyNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostOre(1000000) -1); // Not enough money

		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToBuyOre(1000000);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyOre} ensures no ore is added to the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyOreIncreaseOreNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostOre(1000000) -1); // Not enough money

		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToBuyOre(1000000);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}		
	

///////////////////////////////////////////////////////////////// attemptToBuyRoboticons
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns true when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(7)); // more than enough money in the inventory

		assertTrue(player.attemptToBuyRoboticons(6));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it increases the quantity of roboticons stored in the inventory by 6 when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(7)); // more than enough money in the inventory
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToBuyRoboticons(6);
		assertEquals(roboticonsBefore+6,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(7)); // more than enough money in the inventory
		int moneyBefore = playerInventory.getMoneyQuantity();

		player.attemptToBuyRoboticons(6);
		assertEquals(moneyBefore-market.getCostRoboticons(6),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that the quantity of roboticons in the market reduces by 6 when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testMarketRoboticonsBuyRoboticonsHaveMoreThanEnoughMoneySix(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(7)); // more than enough money in the inventory
		int roboticonsBefore = marketInventory.getRoboticonQuantity();

		player.attemptToBuyRoboticons(6);
		assertEquals(roboticonsBefore-6,marketInventory.getRoboticonQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns true when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(2)); // more than enough money in the inventory

		assertTrue(player.attemptToBuyRoboticons(1));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it increases the quantity of roboticons stored in the inventory by 1 when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(2)); // more than enough money in the inventory
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToBuyRoboticons(1);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore+1,roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(2)); // more than enough money in the inventory
		int moneyBefore = playerInventory.getMoneyQuantity();

		player.attemptToBuyRoboticons(1);
		assertEquals(moneyBefore-market.getCostRoboticons(1),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that the quantity of ore in the market reduces by 1 when the player has more than enough money and tries to buy 1 roboticons.
	 */
	@Test
	public void testCallMarketBuyRoboticonsHaveMoreThanEnoughMoneyOne(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(2)); // more than enough money in the inventory
		int roboticonsBefore = marketInventory.getRoboticonQuantity();

		player.attemptToBuyRoboticons(1);
		assertEquals(roboticonsBefore-1,marketInventory.getRoboticonQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it returns true when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsZero(){

		assertTrue(player.attemptToBuyRoboticons(0));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that no roboticons are added to the inventory when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsZero(){
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToBuyRoboticons(0);
		assertEquals(roboticonsBefore,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that no money is removed from the inventory when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsZero(){
		int moneyQuantity = playerInventory.getMoneyQuantity();	
		player.attemptToBuyRoboticons(0);
		assertEquals(moneyQuantity,playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that no ore is removed from the market when the player tries to buy 0 roboticons.
	 */
	@Test
	public void testReduceMarketOreBuyRoboticonsZero(){
		int oreQuantity = marketInventory.getOreQuantity();	
		player.attemptToBuyRoboticons(0);
		assertEquals(oreQuantity,marketInventory.getOreQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns true when the player has exactly enough money to buy 3 roboticons.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(3)); // Left with the exact amount of money required

		assertTrue(player.attemptToBuyRoboticons(3));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it increases the quantity of roboticons stored in the inventory by 3 when the player has more than enough money and tries to buy 3 roboticons.
	 */
	@Test
	public void testIncreaseRoboticonsBuyRoboticonsHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(3)); // Left with the exact amount of money required

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);

		player.attemptToBuyRoboticons(3);
		assertEquals(roboticonsBefore+3,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it decreases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to buy 6 roboticons.
	 */
	@Test
	public void testReduceMoneyBuyRoboticonsHaveExactlyEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money in the inventory
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(3)); // Left with the exact amount of money required

		player.attemptToBuyRoboticons(3);
		assertEquals(0,playerInventory.getMoneyQuantity());
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
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToBuyRoboticons(-1);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no roboticons are added to the inventory when the player attempts to buy a negative quantity
	 */
	@Test
	public void testBuyRoboticonsIncreaseRoboticonsNegativeQuantity(){
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);	
		player.attemptToBuyRoboticons(-1);
		assertEquals(roboticonsBefore,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}	

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsReturnFalseNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1000000) -1); // Not enough money

		assertFalse(player.attemptToBuyRoboticons(1000000));

	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no money is taken from the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsDecreaseMoneyNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1000000) -1); // Not enough money

		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToBuyRoboticons(1000000);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures no roboticons are added to the inventory when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsIncreaseRoboticonsNotEnoughMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1000000) -1); // Not enough money

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToBuyRoboticons(1000000);
		assertEquals(roboticonsBefore,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}	
	
	
	///////////////////////////////////////////////////////////////// attemptToSellEnergy
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns true when the player has more than enough energy and tries to sell 5 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyHaveMoreThanEnoughEnergyFive(){
		playerInventory.increaseEnergyQuantity(6); // Have more than enough energy
		
		assertTrue(player.attemptToSellEnergy(5));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it decreases the quantity of energy stored in the inventory by 5 when the player has more than enough money and tries to sell 5 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyHaveMoreThanEnoughEnergyFive(){
		playerInventory.increaseEnergyQuantity(6); // Have more than enough energy

		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToSellEnergy(5);
		assertEquals(energyBefore - 5,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough energy and tries to sell 5 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyHaveMoreThanEnoughEnergyFive(){
		playerInventory.increaseEnergyQuantity(6); // Have more than enough energy
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellEnergy(5);
		assertEquals(moneyBefore+market.getCostEnergy(5),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that the quantity of energy stored by the market is increased by 5 when the player has more than enough energy and tries to sell 5 energy.
	 */
	@Test
	public void testCallMarketSellEnergyHaveMoreThanEnoughEnergyFive(){
		playerInventory.increaseEnergyQuantity(6); // Have more than enough energy 
		
		int energyBefore = marketInventory.getEnergyQuantity();
		player.attemptToSellEnergy(5);
		assertEquals(energyBefore+5,marketInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns true when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyHaveMoreThanEnoughEnergyOne(){
		playerInventory.increaseEnergyQuantity(2); // Have more than enough energy
		
		assertTrue(player.attemptToSellEnergy(1));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it decreases the quantity of energy stored in the inventory by 1 when the player has more than enough money and tries to sell 1 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyHaveMoreThanEnoughEnergyOne(){
		playerInventory.increaseEnergyQuantity(2); // Have more than enough energy

		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToSellEnergy(1);
		assertEquals(energyBefore - 1,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyHaveMoreThanEnoughEnergyOne(){
		playerInventory.increaseEnergyQuantity(2); // Have more than enough energy
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellEnergy(1);
		assertEquals(moneyBefore+market.getCostEnergy(1),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that the quantity of energy stored by the market is increased by 1 when the player has more than enough energy and tries to sell 1 energy.
	 */
	@Test
	public void testCallMarketSellEnergyHaveMoreThanEnoughEnergyOne(){
		playerInventory.increaseEnergyQuantity(2); // Have more than enough energy 
		
		int energyBefore = marketInventory.getEnergyQuantity();
		player.attemptToSellEnergy(1);
		assertEquals(energyBefore+1,marketInventory.getEnergyQuantity());
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
		
		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToSellEnergy(0);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that no money is added to the inventory when the player tries to sell 0 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyZero(){
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellEnergy(0);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns true when the player has energy and tries to sell 3 energy.
	 */
	@Test
	public void testReturnTrueSellEnergyHaveExactlyEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy
		playerInventory.increaseEnergyQuantity(3); // Left with exactly 3 energy
		
		assertTrue(player.attemptToSellEnergy(3));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it decreases the quantity of energy stored in the inventory by 3 when the player has 3 energy and tries to sell 3 energy.
	 */
	@Test
	public void testDecreaseEnergySellEnergyHaveExactlyEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy
		playerInventory.increaseEnergyQuantity(3); // Left with exactly 3 energy
		
		player.attemptToSellEnergy(3);
		assertEquals(0,player.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to sell 6 energy.
	 */
	@Test
	public void testIncreaseMoneySellEnergyHaveExactlyEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy
		playerInventory.increaseEnergyQuantity(3); // Left with exactly 3 energy
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellEnergy(3);
		assertEquals(moneyBefore+market.getCostEnergy(3),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that the quantity of energy stored in the market is increased by 3 when the player has exactly 3 energy and tires to sell 3 energy
	 */
	@Test
	public void testIncreaseMarketEnergySellEnergyHaveExactlyEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy
		playerInventory.increaseEnergyQuantity(3); // Left with exactly 3 energy
		
		int energyBefore = marketInventory.getEnergyQuantity();
		player.attemptToSellEnergy(3);
		assertEquals(energyBefore+3,marketInventory.getEnergyQuantity());
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
		int moneyBefore = playerInventory.getMoneyQuantity();
		
		player.attemptToSellEnergy(-1);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no energy is taken from the inventory when the player attempts to sell a negative quantity
	 */
	@Test
	public void testSellEnergyDecreaseEnergyNegativeQuantity(){
		int energyBefore = playerInventory.getEnergyQuantity();
		
		player.attemptToSellEnergy(-1);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}	
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures that that it returns false when the player does not have enough energy.
	 */
	@Test
	public void testSellEnergyReturnFalseNotEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy in the inventory
		playerInventory.increaseEnergyQuantity(1000000-1); // Left with 1 less energy than is needed
		
		assertFalse(player.attemptToSellEnergy(1000000));
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no money is added to the inventory when the player does not have enough energy.
	 */
	@Test
	public void testBuyEnergyIncreaseMoneyNotEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy in the inventory
		playerInventory.increaseEnergyQuantity(1000000-1); // Left with 1 less energy than is needed
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellEnergy(1000000);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellEnergy} ensures no energy is taken from the inventory when the player does not have enough energy.
	 */
	@Test
	public void testSellEnergyDecreaseEnergyNotEnoughEnergy(){
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // Left with no energy in the inventory
		playerInventory.increaseEnergyQuantity(1000000-1); // Left with 1 less energy than is needed
		
		int energyBefore = playerInventory.getEnergyQuantity();
		player.attemptToSellEnergy(1000000);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}	
	
	
	///////////////////////////////////////////////////////////////// attemptToSellOre
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns true when the player has more than enough ore and tries to sell 5 ore.
	 */
	@Test
	public void testReturnTrueSellOreHaveMoreThanEnoughOreFive(){
		playerInventory.increaseOreQuantity(6); // Have more than enough ore
		
		assertTrue(player.attemptToSellOre(5));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it decreases the quantity of ore stored in the inventory by 5 when the player has more than enough money and tries to sell 5 ore.
	 */
	@Test
	public void testDecreaseOreSellOreHaveMoreThanEnoughOreFive(){
		playerInventory.increaseOreQuantity(6); // Have more than enough ore

		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToSellOre(5);
		assertEquals(oreBefore - 5,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough ore and tries to sell 5 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreHaveMoreThanEnoughOreFive(){
		playerInventory.increaseOreQuantity(6); // Have more than enough ore
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellOre(5);
		assertEquals(moneyBefore+market.getCostOre(5),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that the quantity of ore stored by the market is increased by 5 when the player has more than enough ore and tries to sell 5 ore.
	 */
	@Test
	public void testIncreaseMarketsOreSellOreHaveMoreThanEnoughOreFive(){
		playerInventory.increaseOreQuantity(6); // Have more than enough ore 
		
		int oreBefore = marketInventory.getOreQuantity();
		player.attemptToSellOre(5);
		assertEquals(oreBefore+5,marketInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns true when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testReturnTrueSellOreHaveMoreThanEnoughOreOne(){
		playerInventory.increaseOreQuantity(2); // Have more than enough ore
		
		assertTrue(player.attemptToSellOre(1));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it decreases the quantity of ore stored in the inventory by 1 when the player has more than enough money and tries to sell 1 ore.
	 */
	@Test
	public void testDecreaseOreSellOreHaveMoreThanEnoughOreOne(){
		playerInventory.increaseOreQuantity(2); // Have more than enough ore

		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToSellOre(1);
		assertEquals(oreBefore - 1,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreHaveMoreThanEnoughOreOne(){
		playerInventory.increaseOreQuantity(2); // Have more than enough ore
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellOre(1);
		assertEquals(moneyBefore+market.getCostOre(1),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that the quantity of ore stored by the market is increased by 1 when the player has more than enough ore and tries to sell 1 ore.
	 */
	@Test
	public void testIncreaseMarketsOreSellOreHaveMoreThanEnoughOreOne(){
		playerInventory.increaseOreQuantity(2); // Have more than enough ore 
		
		int oreBefore = marketInventory.getOreQuantity();
		player.attemptToSellOre(1);
		assertEquals(oreBefore+1,marketInventory.getOreQuantity());
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
		
		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToSellOre(0);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that no money is added to the inventory when the player tries to sell 0 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreZero(){
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellOre(0);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns true when the player has ore and tries to sell 3 ore.
	 */
	@Test
	public void testReturnTrueSellOreHaveExactlyEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore
		playerInventory.increaseOreQuantity(3); // Left with exactly 3 ore
		
		assertTrue(player.attemptToSellOre(3));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it decreases the quantity of ore stored in the inventory by 3 when the player has 3 ore and tries to sell 3 ore.
	 */
	@Test
	public void testDecreaseOreSellOreHaveExactlyEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore
		playerInventory.increaseOreQuantity(3); // Left with exactly 3 ore
		
		player.attemptToSellOre(3);
		assertEquals(0,player.getOreQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that it increases the quantity of money stored in the inventory by the correct amount when the player has more than enough money and tries to sell 6 ore.
	 */
	@Test
	public void testIncreaseMoneySellOreHaveExactlyEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore
		playerInventory.increaseOreQuantity(3); // Left with exactly 3 ore
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellOre(3);
		assertEquals(moneyBefore+market.getCostOre(3),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that the quantity of ore stored in the market is increased by 3 when the player has exactly 3 ore and tires to sell 3 ore
	 */
	@Test
	public void testIncreaseMarketOreSellOreHaveExactlyEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore
		playerInventory.increaseOreQuantity(3); // Left with exactly 3 ore
		
		int oreBefore = marketInventory.getOreQuantity();
		player.attemptToSellOre(3);
		assertEquals(oreBefore+3,marketInventory.getOreQuantity());
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
		int moneyBefore = playerInventory.getMoneyQuantity();
		
		player.attemptToSellOre(-1);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no ore is taken from the inventory when the player attempts to sell a negative quantity
	 */
	@Test
	public void testSellOreDecreaseOreNegativeQuantity(){
		int oreBefore = playerInventory.getOreQuantity();
		
		player.attemptToSellOre(-1);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}	
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures that that it returns false when the player does not have enough ore.
	 */
	@Test
	public void testSellOreReturnFalseNotEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore in the inventory
		playerInventory.increaseOreQuantity(1000000-1); // Left with 1 less ore than is needed
		
		assertFalse(player.attemptToSellOre(1000000));
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no money is added to the inventory when the player does not have enough ore.
	 */
	@Test
	public void testBuyOreIncreaseMoneyNotEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore in the inventory
		playerInventory.increaseOreQuantity(1000000-1); // Left with 1 less ore than is needed
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToSellOre(1000000);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToSellOre} ensures no ore is taken from the inventory when the player does not have enough ore.
	 */
	@Test
	public void testSellOreDecreaseOreNotEnoughOre(){
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // Left with no ore in the inventory
		playerInventory.increaseOreQuantity(1000000-1); // Left with 1 less ore than is needed
		
		int oreBefore = playerInventory.getOreQuantity();
		player.attemptToSellOre(1000000);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}	
		
	
	
	
	
	
	
	
	/////////////////////////////////////////////////////////////////// attemptToCustomiseRoboticon energy
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 energy roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
				
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
			
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
			
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
					
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
				
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
				
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
			
		// Left with 1 energy roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
				
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonEnergy(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
				
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
			
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of energy roboticons stored by 1 stored when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for energy production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonEnergy(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsEnergy(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
		
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-1);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsEnergy(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-1);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
				
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsEnergy(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-1);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of energy roboticons stored does not increase if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsEnergy(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
						
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-1);
				
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
		
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-5);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-5);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
				
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-5);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of energy roboticons stored does not increase if player does not have enough money in their inventory to get an energy customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsEnergy2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
						
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)-5);
				
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testReturnFalseNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
		
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
		
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY));
	}	
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
				
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
				
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
				
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		assertEquals(0,player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testDecreaseMoneyNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
						
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
						
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of energy roboticons stored is not increased when the player does not have any uncustomised roboticons in their inventory and tries to get an energy customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsEnergy(){
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
								
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
								
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/////////////////////////////////////////////////////////////////// attemptToCustomiseRoboticon ore
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 ore roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns true when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testReturnTrueCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		assertTrue(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
				
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
			
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
			
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
					
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the number of uncustomised roboticons stored by 1 when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		int uncustomisedRoboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int uncustomisedRoboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(uncustomisedRoboticonsBefore-1,uncustomisedRoboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
				
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
				
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
			
		// Left with 1 ore roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE),playerInventory.getMoneyQuantity());
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it decreases the quantity of money stored by the correct amount when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testDecreaseMoneyCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		assertEquals(moneyBefore-market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 when the player has more than enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndRoboticonsOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
				
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored when the player has more than enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveMoreThanEnoughMoneyAndOneRoboticonOre(){
		// Left with more than enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
				
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
			
		// Left with 1 ore roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored when the player has exactly enough money and more than 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndMoreThanOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,2); // 2 uncustomised roboticons (only need 1)

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}

	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it increases the number of ore roboticons stored by 1 stored when the player has exactly enough money and exactly 1 roboticon and tries to customise a roboticon for ore production.
	 */
	@Test
	public void testIncreaseRoboticonsCustomiseRoboticonHaveExactMoneyAndOneRoboticonOre(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)); // Left with exact money
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
		// Left with 1 uncustomised roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1); 
		

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);	
		int roboticonsAfter= playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		assertEquals(roboticonsBefore+1, roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsOre(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
		
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-1);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsOre(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-1);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
				
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsOre(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-1);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of ore roboticons stored does not increase if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsOre(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
						
		// Left with not enough money (cost -1)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-1);
				
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testReturnFalseNotEnoughMoneyCustomiseRoboticonsOre2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
		
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-5);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsOre2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-5);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
				
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored does not decrease if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testDecreaseMoneyNotEnoughMoneyCustomiseRoboticonsOre2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
				
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-5);
		
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of ore roboticons stored does not increase if player does not have enough money in their inventory to get an ore customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNotEnoughMoneyCustomiseRoboticonsOre2(){
		// Left with no money
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity());
						
		// Left with not enough money (cost -5)
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)-5);
				
		// Have enough roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testReturnFalseNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
		
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
		
		assertFalse(player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE));
	}	
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the number of uncustomised roboticons stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testDecreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
				
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
				
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
				
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		assertEquals(0,player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of money stored is not reduced when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testDecreaseMoneyNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
						
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
						
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
		
		int moneyBefore = playerInventory.getMoneyQuantity();
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		assertEquals(moneyBefore,playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of ore roboticons stored is not increased when the player does not have any uncustomised roboticons in their inventory and tries to get an ore customisation.
	 */
	@Test
	public void testIncreaseRoboticonsNoUncustomisedRoboticonsCustomiseRoboticonsOre(){
		// Has enough money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE));
								
		int uncustomisedRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
								
		// Left with no uncustomised roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, uncustomisedRoboticonsInInventory);
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	////////////////////////////////////////////////////////////////////////// attemptToPlaceRoboticon ore
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot is not owned by the player
	 */
	@Test
	public void testReturnFalsePlaceRoboticonUnownedPlotOre(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 1); // Enough energy roboticons
		// plot initially unowned
		
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot already has a roboticon on it 
	 */
	@Test
	public void testReturnFalsePlaceRoboticonAlreadyHasRoboticonOre(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 1); // Enough energy roboticons
		plot.placeRoboticon(RoboticonCustomisation.ORE);
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the player is attempting to place an ore roboticon on the plot and has no ore roboticons
	 */
	@Test
	public void testReturnFalseNoOreRoboticonsOre(){
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
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
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
		// Left with 1 ore roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of ore roboticons stored is reduced by 1 if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have one ore roboticon)
	 */
	@Test
	public void testReduceRoboticonsOreRoboticonOrePlotPlayerHasOneOreRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
		// Left with 1 ore roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		assertEquals(roboticonsBefore-1, roboticonsAfter);
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an ore roboticon on a plot (for a plot that they own when they have one ore roboticon) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonOreRoboticonOrePlotPlayerHasOneOreRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
		// Left with 1 ore roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);

		assertEquals(RoboticonCustomisation.ORE,plot.getRoboticon());
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns true if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have three ore roboticons)
	 */
	@Test
	public void testReturnTrueOreRoboticonOrePlotPlayerHasThreeOreRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
		// Left with 3 ore roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,3);
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of ore roboticons stored is reduced by 1 if the player is attempting to place an ore roboticon on the plot (for a plot that they own when they have three ore roboticons)
	 */
	@Test
	public void testReduceRoboticonsOreRoboticonOrePlotPlayerHasThreeOreRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
		// Left with 3 ore roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,3);
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		assertEquals(roboticonsBefore-1, roboticonsAfter);
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an ore roboticon on a plot (for a plot that they own when they have three ore roboticons) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonOreRoboticonOrePlotPlayerHasThreeOreRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int oreRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		
		// Left with no ore roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ORE,oreRoboticonQuantity);
		
		// Left with 3 ore roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,3);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ORE);

		assertEquals(RoboticonCustomisation.ORE,plot.getRoboticon());
		
	}
	
	////////////////////////////////////////////////////////////////////////// attemptToPlaceRoboticon energy
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot is not owned by the player
	 */
	@Test
	public void testReturnFalsePlaceRoboticonUnownedPlotEnergy(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1); // Enough energy roboticons
		// plot initially unowned
		
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the plot already has a roboticon on it 
	 */
	@Test
	public void testReturnFalsePlaceRoboticonAlreadyHasRoboticonEnergy(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1); // Enough energy roboticons
		plot.placeRoboticon(RoboticonCustomisation.ENERGY);
		assertFalse(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns false if the player is attempting to place an energy roboticon on the plot and has no energy roboticons
	 */
	@Test
	public void testReturnFalseNoEnergyRoboticonsEnergy(){
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
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
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
		// Left with 1 energy roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of energy roboticons stored is reduced by 1 if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have one energy roboticon)
	 */
	@Test
	public void testReduceRoboticonsEnergyRoboticonEnergyPlotPlayerHasOneEnergyRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
		// Left with 1 energy roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		assertEquals(roboticonsBefore-1, roboticonsAfter);
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an energy roboticon on a plot (for a plot that they own when they have one energy roboticon) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonEnergyRoboticonEnergyPlotPlayerHasOneEnergyRoboticon(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
		// Left with 1 energy roboticon
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);

		assertEquals(RoboticonCustomisation.ENERGY,plot.getRoboticon());
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that it returns true if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have three energy roboticons)
	 */
	@Test
	public void testReturnTrueEnergyRoboticonEnergyPlotPlayerHasThreeEnergyRoboticons(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
		// Left with 3 energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,3);
		assertTrue(player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY));
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that the number of energy roboticons stored is reduced by 1 if the player is attempting to place an energy roboticon on the plot (for a plot that they own when they have three energy roboticons)
	 */
	@Test
	public void testReduceRoboticonsEnergyRoboticonEnergyPlotPlayerHasThreeEnergyRoboticons(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
		// Left with 3 energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,3);
		
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		assertEquals(roboticonsBefore-1, roboticonsAfter);
		
	}
	
	/**
	 * Tests {@link Player#attemptToPlaceRoboticon} ensures that when the player is attempting to place an energy roboticon on a plot (for a plot that they own when they have three energy roboticons) that the plot actually has a roboticon placed on it
	 */
	@Test
	public void testPlotHasRoboticonEnergyRoboticonEnergyPlotPlayerHasThreeEnergyRoboticons(){
		plot.setPlayer(player);
		
		// Plot initially has no roboticon on it
		
		int energyRoboticonQuantity = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		
		// Left with no energy roboticons
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY,energyRoboticonQuantity);
		
		// Left with 3 energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,3);
		player.attemptToPlaceRoboticon(plot, RoboticonCustomisation.ENERGY);

		assertEquals(RoboticonCustomisation.ENERGY,plot.getRoboticon());
		
	}
	
	/**
	 * Tests {@link Player#calculateScore} ensures that it returns 0 when the player has no money
	 */
	@Test
	public void testCalculateScoreNoMoney(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		
		 // Doesn't seem to like the fact that calculateScore returns an Integer and not an int
		 // Therefore the result is cast to an int
		assertEquals(0,(int)player.calculateScore());
	}
	
	/**
	 * Tests {@link Player#calculateScore} ensures that it returns 1 when the player has 1 money in their inventory
	 */
	@Test
	public void testCalculateScore1Money(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(1); // Left with 1 money
		
		 // Doesn't seem to like the fact that calculateScore returns an Integer and not an int
		 // Therefore the result is cast to an int
		assertEquals(1,(int)player.calculateScore());
	}
	
	/**
	 * Tests {@link Player#calculateScore} ensures that it returns 5 when the player has 5 money in their inventory
	 */
	@Test
	public void testCalculateScore5Money(){
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // Left with no money
		playerInventory.increaseMoneyQuantity(5); // Left with 1 money
		
		 // Doesn't seem to like the fact that calculateScore returns an Integer and not an int
		 // Therefore the result is cast to an int
		assertEquals(5,(int)player.calculateScore());
	}
}
