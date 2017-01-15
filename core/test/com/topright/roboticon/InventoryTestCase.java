package com.topright.roboticon;
import static org.junit.Assert.*;
import org.junit.*;	

/**
 * Test case for {@link Inventory}
 * @author jcn509
 *
 */
public class InventoryTestCase {
	private Inventory inv;
	
	/**
	 * Runs before each unit test, creates a new Inventory object that stores 0 ore and 0 energy.
	 */
	@Before
	public void setup(){
		inv = new Inventory(0,0); // all attributes initially = 0
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that when an inventory is created the correct amount of ore is stored
	 */
	@Test
	public void testCreateInventoryCorrectOre(){
		Inventory inv2 = new Inventory(2,3);
		assertEquals(2,inv2.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that when an inventory is created the correct amount of energy is stored
	 */
	@Test
	public void testCreateInventoryCorrectEnergy(){
		Inventory inv2 = new Inventory(2,3);
		assertEquals(3,inv2.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative value is passed for the initial quantity of ore.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateInventoryNegativeOre(){
		Inventory inv2 = new Inventory(-2,6);
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative value is passed for the initial quantity of energy.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreateInventoryNegativeEnergy(){
		Inventory inv2 = new Inventory(6,-3);
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} to ensure that the correct 8 energy is added to the inventory.
	 */
	@Test
	public void testIncreaseEnergyPositiveEight(){
		int energyBeforeIncrease = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(8);
		int expectedEnergyAfterIncrease = energyBeforeIncrease+8;
		assertEquals(expectedEnergyAfterIncrease,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} to ensure that 6 energy is added to the inventory.
	 */
	@Test
	public void testIncreaseEnergyPositiveSix(){
		int energyBeforeIncrease = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(6);
		int expectedEnergyAfterIncrease = energyBeforeIncrease+6;
		assertEquals(expectedEnergyAfterIncrease,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} to ensure that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseEnergyNegative(){
		inv.increaseEnergyQuantity(-1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} to ensure that 6 energy are removed from the inventory.
	 */
	@Test
	public void testDecreaseEnergyPositiveSix(){
		inv.increaseEnergyQuantity(8); // ensure at least 8 units of ore initially
		int energyBeforeDecrease = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(6);
		int expectedEnergyAfterDecrease = energyBeforeDecrease - 6;
		assertEquals(expectedEnergyAfterDecrease,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} to ensure that 8 energy are removed from the inventory.
	 */
	@Test
	public void testDecreaseEnergyPositiveEight(){
		inv.increaseEnergyQuantity(9); // ensure at least 8 units of ore initially
		int energyBeforeDecrease = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(8);
		int expectedEnergyAfterDecrease = energyBeforeDecrease - 8;
		assertEquals(expectedEnergyAfterDecrease,inv.getEnergyQuantity());
	}

	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} to ensure that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseEnergyNegative(){
		inv.decreaseEnergyQuantity(-1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} to ensure that an exception is thrown when the amount of energy to be removed is greater than the amount stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseEnergyStoredValueTooLow(){
		int energyQuantity = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(energyQuantity + 1);
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} to ensure that 5 ore is added to the inventory.
	 */
	@Test
	public void testIncreaseOrePositiveFive(){
		int oreBeforeIncrease = inv.getOreQuantity();
		inv.increaseOreQuantity(5);
		int expectedOreAfterIncrease = oreBeforeIncrease+5;
		assertEquals(expectedOreAfterIncrease,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} to ensure that 7 ore is added to the inventory.
	 */
	@Test
	public void testIncreaseOrePositiveSeven(){
		int oreBeforeIncrease = inv.getOreQuantity();
		inv.increaseOreQuantity(7);
		int expectedOreAfterIncrease = oreBeforeIncrease+7;
		assertEquals(expectedOreAfterIncrease,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} to ensure that an exception is thrown when a negative value is passed to it. 
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseOreNegative(){
		inv.increaseOreQuantity(-1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} to ensure that 10 ore is removed from the inventory.
	 */
	@Test
	public void testDecreaseOrePositiveTen(){
		inv.increaseOreQuantity(15); // ensure have at least 10 units of ore in the beginning
		int oreBeforeDecrease = inv.getOreQuantity();
		inv.decreaseOreQuantity(10);
		int expectedOreAfterDecrease = oreBeforeDecrease - 10;
		assertEquals(expectedOreAfterDecrease,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} to ensure that 7 ore is removed from the inventory.
	 */
	@Test
	public void testDecreaseOrePositiveSeven(){
		inv.increaseOreQuantity(10); // ensure have at least 10 units of ore in the beginning
		int oreBeforeDecrease = inv.getOreQuantity();
		inv.decreaseOreQuantity(7);
		int expectedOreAfterDecrease = oreBeforeDecrease - 7;
		assertEquals(expectedOreAfterDecrease,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} to ensure that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseOreNegative(){
		inv.decreaseOreQuantity(-1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} to ensure that an exception is thrown when the amount of or to be removed from the inventory is greater than the amount stored in it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseOreStoredValueTooLow(){
		Inventory inv2 = new Inventory(1,1); // Initially  stores 1 ore and 1 energy
		int oreQuantity = inv2.getOreQuantity();
		inv2.decreaseOreQuantity(oreQuantity + 1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} ensures that exactly as much ore as is currently stored in the inventory can be removed from the inventory
	 */
	@Test
	public void testDecreaseOreByStoreValue(){
		Inventory inv2 = new Inventory(10,10); // Initially  stores 10 ore and 10 energy
		int oreQuantity = inv2.getOreQuantity();
		inv2.decreaseOreQuantity(oreQuantity);
		assertEquals(0,inv2.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} ensures that exactly as much energy as is currently stored in the inventory can be removed from the inventory
	 */
	@Test
	public void testDecreaseEnergyByStoreValue(){
		Inventory inv2 = new Inventory(10,10); // Initially  stores 10 ore and 10 energy
		int energyQuantity = inv2.getEnergyQuantity();
		inv2.decreaseEnergyQuantity(energyQuantity);
		assertEquals(0,inv2.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} to ensure that no energy is added when someone attempts to increase the energy stored by zero.
	 */
	@Test
	public void testIncreaseEnergyZero(){
		int energyQuantity = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(0);
		assertEquals(energyQuantity,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} to ensure that no energy is removed when someone attempts to decrease the energy stored by zero.
	 */
	@Test
	public void testDecreaseEnergyZero(){
		int energyQuantity = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(0);
		assertEquals(energyQuantity,inv.getEnergyQuantity());
	}

	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} to ensure that no ore is added when someone attempts to increase the ore stored by zero.
	 */
	@Test
	public void testIncreaseOreZero(){
		int energyQuantity = inv.getOreQuantity();
		inv.increaseOreQuantity(0);
		assertEquals(energyQuantity,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} to ensure that no ore is removed when someone attempts to decrease the ore stored by zero.
	 */
	@Test
	public void testDecreaseOreZero(){
		int energyQuantity = inv.getOreQuantity();
		inv.decreaseOreQuantity(0);
		assertEquals(energyQuantity,inv.getOreQuantity());
	}
}