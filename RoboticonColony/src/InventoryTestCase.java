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
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that when an inventory is created the correct values for energy and ore are stored.
	 */
	@Test
	public void testCreateInventory(){ // SHOULD SPLIT?
		Inventory inv2 = new Inventory(2,3);
		assertEquals(2,inv2.getOreQuantity());
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
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} to ensure that the correct quantity of energy is added to the inventory.
	 */
	@Test
	public void testIncreaseEnergyPositive(){
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
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} to ensure that the correct number of units of energy are removed from the inventory.
	 */
	@Test
	public void testDecreaseEnergyPositive(){
		inv.increaseEnergyQuantity(8); // ensure at least 8 units of ore initially
		int energyBeforeDecrease = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(6);
		int expectedEnergyAfterDecrease = energyBeforeDecrease - 6;
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
	 * Tests {@link PlayerInventory#increaseOreQuantity} to ensure that the correct quantity of ore is added to the inventory.
	 */
	@Test
	public void testIncreaseOrePositive(){
		int oreBeforeIncrease = inv.getOreQuantity();
		inv.increaseOreQuantity(5);
		int expectedOreAfterIncrease = oreBeforeIncrease+5;
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
	 * Tests {@link PlayerInventory#decreaseOreQuantity} to ensure that the correct quantity of ore is removed from the inventory.
	 */
	@Test
	public void testDecreaseOrePositive(){
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
}