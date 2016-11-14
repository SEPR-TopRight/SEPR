import static org.junit.Assert.*;
import org.junit.*;	

/**
 * Test case for the Inventory class
 * @author jcn509
 *
 */
public class InventoryTestCase {
	private Inventory inv;
	@Before
	public void setup(){
		inv = new Inventory();
	}
	
	@Test
	public void testCreateInventory(){
		Inventory inv2 = new Inventory(2,3);
		assertEquals(2,inv2.getOreQuantity());
		assertEquals(3,inv2.getEnergyQuantity());
	}
	
	@Test
	public void testIncreaseEnergyPositive(){
		int energyBeforeIncrease = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(6);
		int expectedEnergyAfterIncrease = energyBeforeIncrease+6;
		assertEquals(expectedEnergyAfterIncrease,inv.getEnergyQuantity());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseEnergyNegative(){
		inv.increaseEnergyQuantity(-1);
	}
	@Test
	public void testDecreaseEnergyPositive(){
		int energyBeforeDecrease = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(6);
		int expectedEnergyAfterDecrease = energyBeforeDecrease - 6;
		assertEquals(expectedEnergyAfterDecrease,inv.getEnergyQuantity());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseEnergyNegative(){
		inv.decreaseEnergyQuantity(-1);
	}
	@Test
	public void testIncreaseOrePositive(){
		int oreBeforeIncrease = inv.getOreQuantity();
		inv.increaseOreQuantity(5);
		int expectedOreAfterIncrease = oreBeforeIncrease+5;
		assertEquals(expectedOreAfterIncrease,inv.getOreQuantity());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseOreNegative(){
		inv.increaseOreQuantity(-1);
	}
	@Test
	public void testDecreaseOrePositive(){
		int oreBeforeDecrease = inv.getOreQuantity();
		inv.decreaseOreQuantity(7);
		int expectedOreAfterDecrease = oreBeforeDecrease - 7;
		assertEquals(expectedOreAfterDecrease,inv.getOreQuantity());
	}
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseOreNegative(){
		inv.decreaseOreQuantity(-1);
	}
}