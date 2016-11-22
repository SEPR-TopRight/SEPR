package com.topright.roboticon;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.junit.*;	
import java.util.EnumMap;

/**
 * Test case for the PlayerInventory class
 * @author jcn509
 */

public class PlayerInventoryTestCase {
	private PlayerInventory inv;
	
	/**
	 * Runs before each test case, creates a PlayerInventory object that does not store anything.
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		inv = new PlayerInventory(0,0,roboticonQuantities,0); // initial quantity of 0 for food, ore, roboticons (of all customisations) and money
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial values are stored.
	 */
	@Test
	public void testCreatePlayerInventoryValid(){ // SHOULD SPLIT THIS!
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
		assertEquals(1,inv2.getOreQuantity());
		assertEquals(2,inv2.getEnergyQuantity());
		assertEquals(3,inv2.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.ORE));
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
		assertEquals(10,inv2.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial energy value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerInventoryNegativeEnergy(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		PlayerInventory inv2 = new PlayerInventory(1,-3,roboticonQuantities,10);
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial ore value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerInventoryNegativeOre(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		PlayerInventory inv2 = new PlayerInventory(-2,3,roboticonQuantities,10);
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial roboticon value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerNegativeRoboticonQuantity(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ORE, -3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial money value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerNegativeMoneyQuantity(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,-10);
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that the correct number of roboticons are added.
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositive(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 3);
		assertEquals(previousValue+3,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegative(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 6);// ensure that some roboticons with the ore customisation are stored (so that this is not the reason an exception is triggered)
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE,-5);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that the correct number of roboticons are removed from the inventory.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositive(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6);
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6);
		assertEquals(quantityBefore-6,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegative(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, -6);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLow(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 5);// stores 0 of them right after initialisation, therfore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseMoneyQuantity} ensures that the correct amount of money is added to the inventory.
	 */
	@Test
	public void testIncreaseMoneyQuantityPositive(){
		int quantityBefore = inv.getMoneyQuantity();
		inv.increaseMoneyQuantity(6);
		assertEquals(quantityBefore+6,inv.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseMoneyQuantity} ensures that an exception is thrown if a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseMoneyQuantityNegative(){
		inv.increaseMoneyQuantity(-4);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures that the correct amount of money is removed from the inventory.
	 */
	@Test
	public void testDecreaseMoneyQuantityPositive(){
		inv.increaseMoneyQuantity(7); //ensure at least 7 money
		int quantityBefore = inv.getMoneyQuantity();
		inv.decreaseMoneyQuantity(6);
		assertEquals(quantityBefore - 6, inv.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures that an exception is thrown if a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseMoneyQuantityNegative(){
		inv.decreaseMoneyQuantity(-6);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures that an exception is thrown if the amount of money to be removed from the inventory is greater than the amount of money stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseMoneyQuantityStoredValueTooLow(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		PlayerInventory inv2 = new PlayerInventory(1,1,roboticonQuantities,5); // initially have 5 units of money
		inv2.decreaseMoneyQuantity(6);
	}
	
}
