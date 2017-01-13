package com.topright.roboticon;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import mockit.*;
import mockit.integration.junit4.*;

@RunWith(JMockit.class)
public class MarketTestCase {
	/**
	 * Test case for the Market class
	 * @author Ben
	 *
	 */
	
	
	@Mocked MarketInventory inventory;

	@Before
	public void setup(){
		inventory = new MarketInventory(5,5,6);
		Market.getInstance().setInventory(inventory);		
	}
	@Test
	public void testBuyEnergy(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result=6;
		}};
		Market.getInstance().buyEnergy(3);
		
	}
	@Test(expected=IllegalStateException.class)
	public void testBuyEnergyFail(){
		new Expectations(){{
			inventory.getEnergyQuantity(); result = 2;
		}};
		Market.getInstance().buyEnergy(3);
	}
	@Test
	public void testSellEnergy(){
		Market.getInstance().sellEnergyToMarket(3);
		new Verifications(){{
			inventory.increaseEnergyQuantity(anyInt); times=1;
		}};
	}
	@Test
	public void testSellOre(){
		new Expectations(){{
			inventory.increaseOreQuantity(3); times=1;
		}};
		Market.getInstance().sellOreToMarket(3);
	}
	@Test
	public void testBuyOre(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 5;
		}};
		Market.getInstance().buyOreFromMarket(3);
		new Verifications(){{
			inventory.decreaseOreQuantity(3);
		}};
	}
	@Test(expected=IllegalStateException.class)
	public void testBuyOreFail(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 2;
		}};
		Market.getInstance().buyOreFromMarket(3);
	}
	@Test
	public void testEnergyCostZero(){
		assertEquals(0, Market.getInstance().getCostEnergy(0));
	}
	@Test
	public void testOreCostZero(){
		assertEquals(0, Market.getInstance().getCostOre(0));
	}
	@Test
	public void testRoboticonCostZero(){
		assertEquals(0, Market.getInstance().getCostRoboticons(0));
	}
	@Test
	public void testEnergyCostOne(){
		assertTrue(0 < Market.getInstance().getCostEnergy(1));
	}
	@Test
	public void testOreCostOne(){
		assertTrue(0 < Market.getInstance().getCostOre(1));
	}
	@Test
	public void testRoboticonCostOne(){
		assertTrue(0 < Market.getInstance().getCostRoboticons(1));
	}
	@Test
	public void testEnergyCostTwo(){
		assertEquals(2 * Market.getInstance().getCostEnergy(1), Market.getInstance().getCostEnergy(2));
	}
	@Test
	public void testOreCostTwo(){
		assertEquals(2 * Market.getInstance().getCostOre(1), Market.getInstance().getCostOre(2));
	}
	@Test
	public void testRoboticonCostTwo(){
		assertEquals(2 * Market.getInstance().getCostRoboticons(1), Market.getInstance().getCostRoboticons(2));
	}
	

	@Test
	public void testAttemptToProduceRoboticonsHaveEnoughOreReturnTrue(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 99;
		}};
		assertTrue(Market.getInstance().attemptToProduceRoboticon());
	}
	@Test
	public void testAttemptToProduceRoboticonsHaveEnoughOreRoboticonAdded(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 99;
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.increaseRoboticonQuantity(1); times=1;
		}};
	}
	@Test
	public void testAttemptToProduceRoboticonsHaveEnoughOreOreReduced(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 99;
		}};
		Market.getInstance().attemptToProduceRoboticon();
		new Verifications(){{
			inventory.decreaseOreQuantity(anyInt); times=1;
		}};
	}

	public void testProduceRoboticonsFail(){
		new Expectations(){{
			inventory.getOreQuantity(); result = 0;
		}};
		assertFalse(Market.getInstance().attemptToProduceRoboticon());
	}
}
