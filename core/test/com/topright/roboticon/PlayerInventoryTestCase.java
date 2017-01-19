package com.topright.roboticon;
import static org.junit.Assert.*;
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
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial ore value is stored.
	 */
	@Test
	public void testCreatePlayerInventoryOre(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
		assertEquals(1,inv2.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial energy value is stored
	 */
	@Test
	public void testCreatePlayerInventoryEnergy(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
		assertEquals(2,inv2.getEnergyQuantity());
		assertEquals(3,inv2.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.ORE));
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
		assertEquals(10,inv2.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial energy roboticon value is stored
	 */
	@Test
	public void testCreatePlayerInventoryEnergyRoboticon(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
		assertEquals(3,inv2.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial ore roboticon value is stored
	 */
	@Test
	public void testCreatePlayerInventoryOreRoboticon(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	

	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial uncustomised roboticon value is stored
	 */
	@Test
	public void testCreatePlayerInventoryUncustomisedRoboticon(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
		assertEquals(0,inv2.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that the correct initial money value is stored
	 */
	@Test
	public void testCreatePlayerInventoryMoney(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
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
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial uncustomised roboticon value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerNegativeUncustomisedRoboticonQuantity(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.UNCUSTOMISED, -3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial ore roboticon value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerNegativeOreRoboticonQuantity(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ORE, -3);
		PlayerInventory inv2 = new PlayerInventory(1,2,roboticonQuantities,10);
	}
	
	/**
	 * Tests {@link PlayerInventory#PlayerInventory} ensures that an exception is thrown when a negative initial energy roboticon value is supplied.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testCreatePlayerNegativeEnergyRoboticonQuantity(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, -3);
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
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that three energy roboticons are added to inventory when a value of 3 is passed to it (for energy customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveThreeEnergy(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 3);
		assertEquals(previousValue+3,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that 1 energy roboticon is added to the inventory when a value of 1 is passed to it (for energy customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveOneEnergy(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1);
		assertEquals(previousValue+1,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that no energy robotiocons are added to the inventory when a value of 0 is passed to it (for energy customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityZeroEnergy(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1);
		assertEquals(previousValue+1,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that three ore roboticons are added to inventory when a value of 3 is passed to it (for ore customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveThreeOre(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ORE);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 3);
		assertEquals(previousValue+3,inv.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that 1 ore roboticon is added to the inventory when a value of 1 is passed to it (for ore customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveOneOre(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ORE);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 1);
		assertEquals(previousValue+1,inv.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that no ore robotiocons are added to the inventory when a value of 0 is passed to it (for ore customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityZeroOre(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.ORE);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 1);
		assertEquals(previousValue+1,inv.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that three uncustomised roboticons are added to inventory when a value of 3 is passed to it (for uncustomised customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveThreeUncustomised(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 3);
		assertEquals(previousValue+3,inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that 1 uncustomised roboticon is added to the inventory when a value of 1 is passed to it (for uncustomised customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityPositiveOneUncustomised(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		assertEquals(previousValue+1,inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that no uncustomised robotiocons are added to the inventory when a value of 0 is passed to it (for uncustomised customisation).
	 */
	@Test
	public void testIncreaseRoboticonQuantityZeroUncustomised(){
		int previousValue = inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		assertEquals(previousValue+1,inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it (for ore roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegativeOre(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 6);// ensure that some roboticons with the ore customisation are stored (so that this is not the reason an exception is triggered)
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE,-5);
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it  (for energy roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegativeEnergy(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6);// ensure that some roboticons with the energy customisation are stored (so that this is not the reason an exception is triggered)
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,-5);
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it  (for uncustomised roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseRoboticonQuantityNegativeUncustomised(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 6);// ensure that some uncustomised roboticons are stored (so that this is not the reason an exception is triggered)
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,-5);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures 1 energy roboticon is removed from the inventory, when a value of 1 and a customisation type of energy is passed to it and 1 energy roboticon is initially stored.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveOneEnergy(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1); // 1 roboticon stored
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1);
		assertEquals(quantityBefore-1,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures 6 energy roboticons are removed from the inventory, when a value of 6 and a customisation type of energy is passed to it and 6 energy roboticons are initially stored.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveSixenergy(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6); // 6 energy roboticons stored
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 6);
		assertEquals(quantityBefore-6,inv.getRoboticonQuantity(RoboticonCustomisation.ENERGY));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures 1 ore roboticon is removed from the inventory, when a value of 1 and a customisation type of ore is passed to it and 1 ore roboticon is initially stored.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveOneOre(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 1); // 1 roboticon stored
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.ORE);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, 1);
		assertEquals(quantityBefore-1,inv.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures 6 ore roboticons are removed from the inventory, when a value of 6 and a customisation type of ore is passed to it and 6 ore roboticons are initially stored.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveSixore(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 6); // 6 ore roboticons stored
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.ORE);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, 6);
		assertEquals(quantityBefore-6,inv.getRoboticonQuantity(RoboticonCustomisation.ORE));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures 1 uncustomised roboticon is removed from the inventory, when a value of 1 and a customisation type of uncustomised is passed to it and 1 uncustomised roboticon is initially stored.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveOneUncustomised(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1); // 1 roboticon stored
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		assertEquals(quantityBefore-1,inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures 6 uncustomised roboticons are removed from the inventory, when a value of 6 and a customisation type of uncustomised is passed to it and 6 uncustomised roboticons are initially stored.
	 */
	@Test
	public void testDecreaseRoboticonQuantityPositiveSixuncustomised(){
		inv.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 6); // 6 uncustomised roboticons stored
		int quantityBefore = inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 6);
		assertEquals(quantityBefore-6,inv.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it (for energy roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegativeEnergy(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, -1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it (for uncustomised roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegativeUncustomised(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, -1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when a negative value is passed to it (for ore roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityNegativeOre(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, -1);
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored (for energy roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLowFiveEnergy(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 5);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored (for energy roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLowOneEnergy(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ENERGY, 1);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored (for ore roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLowFiveOre(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, 5);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored (for ore roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLowOneOre(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.ORE, 1);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored (for uncustomised roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLowFiveUncustomised(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 5);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseRoboticonQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored (for uncustomised roboticons).
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseRoboticonQuantityStoredValueTooLowOneUncustomised(){
		inv.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}	
	

	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} ensures that three energy are added to inventory when a value of 3 is passed to it.
	 */
	@Test
	public void testIncreaseEnergyQuantityPositiveThree(){
		int previousValue = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(3);
		assertEquals(previousValue+3,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} ensures that 1 energy is added to the inventory when a value of 1 is passed to it.
	 */
	@Test
	public void testIncreaseEnergyQuantityPositiveOne(){
		int previousValue = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(1);
		assertEquals(previousValue+1,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} ensures that 1 energy is added to the inventory when a value of 0 is passed to it.
	 */
	@Test
	public void testIncreaseEnergyQuantityZero(){
		int previousValue = inv.getEnergyQuantity();
		inv.increaseEnergyQuantity(1);
		assertEquals(previousValue+1,inv.getEnergyQuantity());
	}
	

	
	/**
	 * Tests {@link PlayerInventory#increaseEnergyQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseEnergyQuantityNegative(){
		inv.increaseEnergyQuantity(6);// Ensure that some energy is stored (so that this is not the reason an exception is triggered)
		inv.increaseEnergyQuantity(-5);
	}
	

	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} ensures 1 energy is removed from the inventory, when a value of 1 is passed to it and 1 energy is initially stored.
	 */
	@Test
	public void testDecreaseEnergyQuantityPositiveOne(){
		inv.increaseEnergyQuantity(1); // 1 energy stored
		int quantityBefore = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(1);
		assertEquals(quantityBefore-1,inv.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} ensures 6 energy is removed from the inventory, when a value of 6 is passed to it and 6 energy are initially stored.
	 */
	@Test
	public void testDecreaseEnergyQuantityPositiveSixore(){
		inv.increaseEnergyQuantity(6); // 6 energy stored
		int quantityBefore = inv.getEnergyQuantity();
		inv.decreaseEnergyQuantity(6);
		assertEquals(quantityBefore-6,inv.getEnergyQuantity());
	}
	

	
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseEnergyQuantityNegative(){
		inv.decreaseEnergyQuantity(-1);
	}
	
	
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseEnergyQuantityStoredValueTooLowFive(){
		inv.decreaseEnergyQuantity(5);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseEnergyQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseEnergyQuantityStoredValueTooLowOne(){
		inv.decreaseEnergyQuantity(1);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	

	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} ensures that three ore are added to inventory when a value of 3 is passed to it.
	 */
	@Test
	public void testIncreaseOreQuantityPositiveThree(){
		int previousValue = inv.getOreQuantity();
		inv.increaseOreQuantity(3);
		assertEquals(previousValue+3,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} ensures that 1 ore is added to the inventory when a value of 1 is passed to it.
	 */
	@Test
	public void testIncreaseOreQuantityPositiveOne(){
		int previousValue = inv.getOreQuantity();
		inv.increaseOreQuantity(1);
		assertEquals(previousValue+1,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} ensures that 1 ore is added to the inventory when a value of 0 is passed to it.
	 */
	@Test
	public void testIncreaseOreQuantityZero(){
		int previousValue = inv.getOreQuantity();
		inv.increaseOreQuantity(1);
		assertEquals(previousValue+1,inv.getOreQuantity());
	}
	

	
	/**
	 * Tests {@link PlayerInventory#increaseOreQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseOreQuantityNegative(){
		inv.increaseOreQuantity(6);// Ensure that some ore is stored (so that this is not the reason an exception is triggered)
		inv.increaseOreQuantity(-5);
	}
	

	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} ensures 1 ore is removed from the inventory, when a value of 1 is passed to it and 1 ore is initially stored.
	 */
	@Test
	public void testDecreaseOreQuantityPositiveOne(){
		inv.increaseOreQuantity(1); // 1 ore stored
		int quantityBefore = inv.getOreQuantity();
		inv.decreaseOreQuantity(1);
		assertEquals(quantityBefore-1,inv.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} ensures 6 ore is removed from the inventory, when a value of 6 is passed to it and 6 ore are initially stored.
	 */
	@Test
	public void testDecreaseOreQuantityPositiveSixore(){
		inv.increaseOreQuantity(6); // 6 ore stored
		int quantityBefore = inv.getOreQuantity();
		inv.decreaseOreQuantity(6);
		assertEquals(quantityBefore-6,inv.getOreQuantity());
	}
	

	
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseOreQuantityNegative(){
		inv.decreaseOreQuantity(-1);
	}
	
	
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseOreQuantityStoredValueTooLowFive(){
		inv.decreaseOreQuantity(5);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseOreQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseOreQuantityStoredValueTooLowOne(){
		inv.decreaseOreQuantity(1);// stores 0 of them right after initialisation, therefore an exception should be thrown
	}
	

	
	/**
	 * Tests {@link PlayerInventory#increaseMoneyQuantity} ensures that three money are added to inventory when a value of 3 is passed to it.
	 */
	@Test
	public void testIncreaseMoneyQuantityPositiveThree(){
		int previousValue = inv.getMoneyQuantity();
		inv.increaseMoneyQuantity(3);
		assertEquals(previousValue+3,inv.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseMoneyQuantity} ensures that 1 money is added to the inventory when a value of 1 is passed to it.
	 */
	@Test
	public void testIncreaseMoneyQuantityPositiveOne(){
		int previousValue = inv.getMoneyQuantity();
		inv.increaseMoneyQuantity(1);
		assertEquals(previousValue+1,inv.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#increaseMoneyQuantity} ensures that 1 money is added to the inventory when a value of 0 is passed to it.
	 */
	@Test
	public void testIncreaseMoneyQuantityZero(){
		int previousValue = inv.getMoneyQuantity();
		inv.increaseMoneyQuantity(1);
		assertEquals(previousValue+1,inv.getMoneyQuantity());
	}
	

	
	/**
	 * Tests {@link PlayerInventory#increaseMoneyQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testIncreaseMoneyQuantityNegative(){
		inv.increaseMoneyQuantity(6);// Ensure that some money is stored (so that this is not the reason an exception is triggered)
		inv.increaseMoneyQuantity(-5);
	}
	

	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures 1 money is removed from the inventory, when a value of 1 is passed to it and 1 money is initially stored.
	 */
	@Test
	public void testDecreaseMoneyQuantityPositiveOne(){
		inv.increaseMoneyQuantity(1); // 1 money stored
		int quantityBefore = inv.getMoneyQuantity();
		inv.decreaseMoneyQuantity(1);
		assertEquals(quantityBefore-1,inv.getMoneyQuantity());
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures 6 money is removed from the inventory, when a value of 6 is passed to it and 6 money are initially stored.
	 */
	@Test
	public void testDecreaseMoneyQuantityPositiveSixore(){
		inv.increaseMoneyQuantity(6); // 6 money stored
		int quantityBefore = inv.getMoneyQuantity();
		inv.decreaseMoneyQuantity(6);
		assertEquals(quantityBefore-6,inv.getMoneyQuantity());
	}
	

	
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures that an exception is thrown when a negative value is passed to it.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseMoneyQuantityNegative(){
		inv.decreaseMoneyQuantity(-1);
	}
	
	
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseMoneyQuantityStoredValueTooLowFive(){
		inv.decreaseMoneyQuantity(5);// stores 0 money right after initialisation, therefore an exception should be thrown
	}
	
	/**
	 * Tests {@link PlayerInventory#decreaseMoneyQuantity} ensures that an exception is thrown when the value to be taken away is greater than the value stored.
	 */
	@Test(expected=IllegalArgumentException.class)
	public void testDecreaseMoneyQuantityStoredValueTooLowOne(){
		inv.decreaseMoneyQuantity(1);// stores 0 money right after initialisation, therefore an exception should be thrown
	}
	
}
