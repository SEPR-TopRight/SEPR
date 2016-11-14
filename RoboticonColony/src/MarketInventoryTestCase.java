import static org.junit.Assert.*;
import org.junit.*;	

/**
 * Test case for the MarketInventory class
 * @author jcn509
 *
 */
public class MarketInventoryTestCase {
	private MarketInventory inv;
	
	@Before
	public void setup(){
		inv = new MarketInventory();
	}
	
	@Test
	public void testCreateNewMarketInventory(){
		MarketInventory inv2 = new MarketInventory(1,2,3);
		assertEquals(1,inv2.getOreQuantity());
		assertEquals(2,inv2.getEnergyQuantity());
		assertEquals(3,inv2.getRoboticonQuantity());
	}
	
	@Test
	public void testIncreaseRoboticonQuantityPositive(){
		int roboticonQuantityBeforeIncrease = inv.getRoboticonQuantity();
		inv.increaseRoboticonQuantity(7);
		int expectedRoboticonQuantityAfterIncrease = roboticonQuantityBeforeIncrease +7;
		assertEquals(expectedRoboticonQuantityAfterIncrease, inv.getRoboticonQuantity());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegative(){
		inv.increaseRoboticonQuantity(-6);
	}
	@Test
	public void testDecreaseRoboticonQuantityPositive(){
		int roboticonQuantityBeforeDecrease = inv.getRoboticonQuantity();
		inv.decreaseRoboticonQuantity(9);
		int expectedRoboticonQuantityAfterDecrease = roboticonQuantityBeforeDecrease -9;
		assertEquals(expectedRoboticonQuantityAfterDecrease, inv.getRoboticonQuantity());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegative(){
		inv.decreaseRoboticonQuantity(-6);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNotEnoughStored(){
		inv.decreaseRoboticonQuantity(-5); // after initialisation stores 0 roboticons
	}
}