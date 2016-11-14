import static org.junit.Assert.*;
import org.junit.*;	
import java.util.EnumMap;
/**
 * Test case for the PlayerInventory class
 * @author jcn509
 *
 */
public class PlayerInventoryTestCase {
	private PlayerInventory inv;
	@Before
	public void setup(){
		inv = new PlayerInventory();
	}
	
	@Test
	public void testCreatePlayerInventoryValid(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities);
		assertEquals(1,inv2.getOreQuantity());
		assertEquals(2,inv2.getEnergyQuantity());
		assertEquals(3,inv2.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.ORE));
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerInventoryNegativeEnergy(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		PlayerInventory inv2 = new PlayerInventory(1,-3,roboticonQuantities);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerInventoryNegativeOre(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		PlayerInventory inv2 = new PlayerInventory(-2,3,roboticonQuantities);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerNegativeRoboticonQuantity(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ORE, -3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities);
	}
	@Test
	public void testCreatePlayerNoArguments(){
		assertEquals(0,inv.getOreQuantity());
		assertEquals(0,inv.getEnergyQuantity());
		assertEquals(0,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
		assertEquals(0,inv.getRoboticonQuantity(RoboticonCustomisation.ORE));
		assertEquals(0,inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	@Test
	public void testIncreaseRoboticonQuantityPositive(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 3);
		assertEquals(previousValue+3,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegative(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 6);// ensure that some roboticons with the ore customisation are stored (so that this is not the reason an exception is triggered)
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE,-5);
	}
	@Test
	public void testDecreaseRoboticonQuantityPositive(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6);
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6);
		assertEquals(quantityBefore-6,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegative(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, -6);
	}
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLow(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 5);// stores 0 of them right after initialisation, therfore an exception should be thrown
	}
}
