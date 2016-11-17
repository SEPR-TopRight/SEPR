import static org.junit.Assert.*;
import java.util.EnumMap;
import org.junit.*;	
/**
 * Test case for {@link Player}
 * @author jcn509
 */

// NOTHING TO TEST (WHEN BUYING/SELLING) that the market is correctly updated

public class PlayerTestCase {
	
	private Player player;
	private Market market;
	private EnumMap<RoboticonCustomisation,Integer> roboticonQuantities;

	/**
	 * Runs before every test, creates a new market object and an empty mapping from RoboticonCustomisation to Integer.
	 */
	@Before
	public void createPlayer(){
		market = new Market(); // MY FAKE MARKET OBJECT - MOCK LATER
		roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that the correct amount of money is taken from the players inventory.
	 */
	@Test
	public void testCorrectMoneyBuyEnergyHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		int cost = market.getCostEnergy(6);
		int initialMoney = player.inventory.getMoneyQuantity();
		player.attemptToBuyEnergy(market,6);
		assertEquals(initialMoney - cost,player.inventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that the correct amount of energy is added to the players inventory.
	 */
	@Test
	public void testCorrectEnergyBuyEnergyHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		int initialEnergy = player.inventory.getEnergyQuantity();
		player.attemptToBuyEnergy(market,6);
		assertEquals(initialEnergy+6,player.inventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns true when used correctly.
	 */
	@Test
	public void testReturnTrueBuyEnergyHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		assertTrue(player.attemptToBuyEnergy(market,6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyEnergy} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyEnergyNotEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10);
		assertFalse(player.attemptToBuyEnergy(market,1000000));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that the correct amount of money is taken from the players inventory.
	 */
	@Test
	public void testCorrectMoneyBuyOreHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		int cost = market.getCostOre(6);
		int initialMoney = player.inventory.getMoneyQuantity();
		player.attemptToBuyOre(market,6);
		assertEquals(initialMoney - cost,player.inventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that the correct amount of ore is added to the players inventory.
	 */
	@Test
	public void testCorrectOreBuyOreHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		int initialOre = player.inventory.getOreQuantity();
		player.attemptToBuyOre(market,6);
		assertEquals(initialOre+6,player.inventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns true when used correctly.
	 */
	@Test
	public void testReturnTrueBuyOreHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		assertTrue(player.attemptToBuyOre(market,6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyOre} ensures that that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyOreNotEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10);
		assertFalse(player.attemptToBuyOre(market,1000000));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that the correct amount of money is taken from the players inventory.
	 */
	@Test
	public void testCorrectMoneyBuyRoboticonsHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		int cost = market.getCostRoboticons(6);
		int initialMoney = player.inventory.getMoneyQuantity();
		player.attemptToBuyRoboticons(market,6);
		assertEquals(initialMoney - cost,player.inventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that the correct number of roboticons are added to the players inventory.
	 */
	@Test
	public void testCorrectRoboticonsBuyRoboticonsHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		int initialRoboticons = player.inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.attemptToBuyRoboticons(market,6);
		assertEquals(initialRoboticons+6,player.inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that returns true when used correctly.
	 */
	@Test
	public void testReturnTrueBuyRoboticonsHaveEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10000);
		assertTrue(player.attemptToBuyRoboticons(market,6));
	}
	
	/**
	 * Tests {@link Player#attemptToBuyRoboticons} ensures that it returns false when the player does not have enough money.
	 */
	@Test
	public void testBuyRoboticonsNotEnoughMoney(){
		player = new Player(5,4,roboticonQuantities,10);
		assertFalse(player.attemptToBuyRoboticons(market,1000000));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of uncustomised roboticons in the player's inventory is reduced by 1.
	 */
	@Test
	public void TestUncustomisedRoboticonQuantitiesCustomiseRoboticons(){
		player = new Player(5,4,roboticonQuantities,10);
		player.inventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int uncustomisedQuantity = player.inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		int customisedQuantity = player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE);
		assertEquals(uncustomisedQuantity-1,player.inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the quantity of roboticons with the correct customisation in player's inventory is increased by 1.
	 */
	@Test
	public void TestCustomisedRoboticonQuantitiesCustomiseRoboticons(){
		player = new Player(5,4,roboticonQuantities,10);
		player.inventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int uncustomisedQuantity = player.inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		int customisedQuantity = player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE);
		assertEquals(customisedQuantity+1,player.inventory.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures that the correct amount of money is removed from the player's inventory.
	 */
	@Test
	public void TestMoneyQuantityCustomiseRoboticons(){
		player = new Player(5,4,roboticonQuantities,10);
		player.inventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int moneyBefore = player.inventory.getMoneyQuantity();
		int cost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);
		player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE);
		assertEquals(moneyBefore-cost,player.inventory.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player has no uncustomised roboticons in their inventory.
	 */
	@Test
	public void TestNoUncustomisedRoboticonsCustomiseRoboticons(){
		player = new Player(5,4,roboticonQuantities,10);
		assertFalse(player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link Player#attemptToCustomiseRoboticon} ensures it returns false when the player does not have enough money in their inventory.
	 */
	@Test
	public void TestNotEnoughMoneyCustomiseRoboticons(){
		player = new Player(5,4,roboticonQuantities,0);
		player.inventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		assertFalse(player.attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE));
	}
}
