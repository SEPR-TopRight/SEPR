package com.topright.roboticon;
import static org.junit.Assert.*;
import org.junit.*;	

/**
 * Test case for {@link MarketInventory}
 * @author jcn509
 *
 */
public class MarketInventoryTestCase {
	private MarketInventory inv;
	
	/**
	 * Runs before each test, creates a MarketInventory that initially stores 0 ore, 0 energy and 0 roboticons.
	 */
	@Before
	public void setup(){
		inv = new MarketInventory(0,0,0); // Initially not storing anything.
	}
	
	/**
	 * Tests {@link MarketInventory#MarketInventory} ensures that the correct quantities of ore, energy and roboticons are stored in the market.
	 */
	@Test
	public void testCreateNewMarketInventory(){ // SHOULD SPLIT
		MarketInventory inv2 = new MarketInventory(1,2,3);
		assertEquals(1,inv2.getOreQuantity());
		assertEquals(2,inv2.getEnergyQuantity());
		assertEquals(3,inv2.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#increaseRoboticonQuantity} ensures that the correct number of roboticons are added to the inventory.
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositive(){
		int roboticonQuantityBeforeIncrease = inv.getRoboticonQuantity();
		inv.increaseRoboticonQuantity(7);
		int expectedRoboticonQuantityAfterIncrease = roboticonQuantityBeforeIncrease +7;
		assertEquals(expectedRoboticonQuantityAfterIncrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#increaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegative(){
		inv.increaseRoboticonQuantity(-6);
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that the correct number of roboticons are removed from the market.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositive(){
		inv.increaseRoboticonQuantity(10); // Ensure at least 10 roboticons are in the inventory initially.
		int roboticonQuantityBeforeDecrease = inv.getRoboticonQuantity();
		inv.decreaseRoboticonQuantity(9);
		int expectedRoboticonQuantityAfterDecrease = roboticonQuantityBeforeDecrease -9;
		assertEquals(expectedRoboticonQuantityAfterDecrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegative(){
		inv.decreaseRoboticonQuantity(-6);
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the number of roboticons to be removed is less than the number that are stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNotEnoughStored(){
		inv.decreaseRoboticonQuantity(5); // After initialisation stores 0 roboticons.
	}
}
