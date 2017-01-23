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
	 * Tests {@link MarketInventory#MarketInventory} ensures that the correct initial quantity of ore is stored in the market.
	 */
	@Test
	public void testCreateNewMarketInventoryCorectOre(){
		MarketInventory inv2 = new MarketInventory(1,2,3);
		assertEquals(1,inv2.getOreQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#MarketInventory} ensures that the correct initial quantity of energy is stored in the market.
	 */
	@Test
	public void testCreateNewMarketInventoryCorectEnergy(){
		MarketInventory inv2 = new MarketInventory(1,2,3);
		assertEquals(2,inv2.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#MarketInventory} ensures that the correct initial number of roboticons are stored in the market.
	 */
	@Test
	public void testCreateNewMarketInventoryCorectRoboticons(){
		MarketInventory inv2 = new MarketInventory(1,2,3);
		assertEquals(3,inv2.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#increaseRoboticonQuantity} ensures that 7 roboticons are added to the inventory when increaseRoboticonQuantity(7) is called.
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveSeven(){
		int roboticonQuantityBeforeIncrease = inv.getRoboticonQuantity();
		inv.increaseRoboticonQuantity(7);
		int expectedRoboticonQuantityAfterIncrease = roboticonQuantityBeforeIncrease +7;
		assertEquals(expectedRoboticonQuantityAfterIncrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#increaseRoboticonQuantity} ensures that 1 roboticon is added to the inventory when increaseRoboticonQuantity(1) is called.
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveOne(){
		int roboticonQuantityBeforeIncrease = inv.getRoboticonQuantity();
		inv.increaseRoboticonQuantity(1);
		int expectedRoboticonQuantityAfterIncrease = roboticonQuantityBeforeIncrease +1;
		assertEquals(expectedRoboticonQuantityAfterIncrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#increaseRoboticonQuantity} ensures that no roboticons are added to the inventory when increaseRoboticonQuantity(0) is called.
	 */
	@Test
	public void testIncreaseRoboticonQuantityZero(){
		int roboticonQuantityBeforeIncrease = inv.getRoboticonQuantity();
		inv.increaseRoboticonQuantity(0);
		int expectedRoboticonQuantityAfterIncrease = roboticonQuantityBeforeIncrease;
		assertEquals(expectedRoboticonQuantityAfterIncrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#increaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegative(){
		inv.increaseRoboticonQuantity(-1);
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that 9 roboticons are removed from the inventory when decreaseRoboticonQuantity(9) is called.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveNine(){
		inv.increaseRoboticonQuantity(10); // Ensure at least 10 roboticons are in the inventory initially.
		int roboticonQuantityBeforeDecrease = inv.getRoboticonQuantity();
		inv.decreaseRoboticonQuantity(9);
		int expectedRoboticonQuantityAfterDecrease = roboticonQuantityBeforeDecrease -9;
		assertEquals(expectedRoboticonQuantityAfterDecrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that 1 roboticon is removed from the inventory when decreaseRoboticonQuantity(1) is called.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveOne(){
		inv.increaseRoboticonQuantity(10); // Ensure at least 10 roboticons are in the inventory initially.
		int roboticonQuantityBeforeDecrease = inv.getRoboticonQuantity();
		inv.decreaseRoboticonQuantity(1);
		int expectedRoboticonQuantityAfterDecrease = roboticonQuantityBeforeDecrease -1;
		assertEquals(expectedRoboticonQuantityAfterDecrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that no roboticons are removed from the inventory when decreaseRoboticonQuantity(0) is called.
	 */
	@Test
	public void testDecreaseRoboticonQuantityZero(){
		inv.increaseRoboticonQuantity(10); // Ensure at least 10 roboticons are in the inventory initially.
		int roboticonQuantityBeforeDecrease = inv.getRoboticonQuantity();
		inv.decreaseRoboticonQuantity(0);
		int expectedRoboticonQuantityAfterDecrease = roboticonQuantityBeforeDecrease;
		assertEquals(expectedRoboticonQuantityAfterDecrease, inv.getRoboticonQuantity());
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegative(){
		inv.decreaseRoboticonQuantity(-1);
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the number of roboticons to be removed is less than the number that are stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityZeroStored(){
		inv.decreaseRoboticonQuantity(1); // After initialisation stores 0 roboticons.
	}
	
	/**
	 * Tests {@link MarketInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the number of roboticons to be removed is less than the number that are stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNotEnoughStored(){
		MarketInventory inv2 = new MarketInventory(0,0,2);
		inv2.decreaseRoboticonQuantity(3); // After initialisation stores 2 roboticons.
	}
}
