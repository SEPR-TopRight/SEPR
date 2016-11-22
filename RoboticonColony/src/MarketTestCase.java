import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class MarketTestCase {
	/**
	 * Test case for the Market class
	 * @author Ben
	 *
	 */
	
	private Market mk;
	@Before
	public void setup(){
		mk = new Market(0,16,12);		
	}
	@Test
	public void testBuyEnergy(){
		int EnergyQuantityBeforeIncrease = mk.mark_inventory.getEnergyQuantity();
		mk.buyEnergy(3);
		int expectedEnergyQuantityAfterIncrease = EnergyQuantityBeforeIncrease -3;
		assertEquals(expectedEnergyQuantityAfterIncrease, mk.mark_inventory.getEnergyQuantity());
	}
	@Test(expected=IllegalStateException.class)
	public void testBuyEnergyFail(){
		mk.buyEnergy(30);
	}
	@Test
	public void testSellEnergy(){
		int EnergyQuantityBeforeDecrease = mk.mark_inventory.getEnergyQuantity();
		mk.sellEnergy(3);
		int expectedEnergyQuantityAfterDecrease = EnergyQuantityBeforeDecrease +3;
		assertEquals(expectedEnergyQuantityAfterDecrease, mk.mark_inventory.getEnergyQuantity());
	}
	@Test
	public void testSellOre(){
		int OreQuantityBeforeDecrease = mk.mark_inventory.getOreQuantity();
		mk.sellOre(3);
		int expectedOreQuantityAfterDecrease = OreQuantityBeforeDecrease +3;
		assertEquals(expectedOreQuantityAfterDecrease, mk.mark_inventory.getOreQuantity());
	}
	@Test
	public void testBuyOre(){
		mk.sellOre(3);
		int OreQuantityBeforeIncrease = mk.mark_inventory.getOreQuantity();
		mk.buyOre(3);
		int expectedOreQuantityAfterIncrease = OreQuantityBeforeIncrease -3;
		assertEquals(expectedOreQuantityAfterIncrease, mk.mark_inventory.getOreQuantity());
	}
	@Test(expected=IllegalStateException.class)
	public void testBuyOreFail(){
		mk.buyOre(3);
	}
	@Test
	public void testEnergyCost(){
		assertEquals(15, mk.getCostEnergy(3));
	}
	@Test
	public void testOreCost(){
		assertEquals(15, mk.getCostOre(3));
	}
	@Test
	public void testRoboticonCost(){
		assertEquals(30, mk.getCostRoboticons(3));
	}
	@Test
	public void testProduceRoboticons(){
		int initialRoboticons = mk.mark_inventory.getRoboticonQuantity();
		mk.sellOre(5);
		int initialOre = mk.mark_inventory.getOreQuantity();
		mk.ProduceRoboticon(5);
		int ExpectedRoboticons = initialRoboticons + 5;
		int ExpectedOre = initialOre - 5;
		assertEquals(ExpectedRoboticons, mk.mark_inventory.getRoboticonQuantity());
		assertEquals(ExpectedOre, mk.mark_inventory.getOreQuantity());
	}
	@Test(expected=IllegalStateException.class)
	public void testProduceRoboticonsFail(){
		mk.ProduceRoboticon(5);
	}
}
