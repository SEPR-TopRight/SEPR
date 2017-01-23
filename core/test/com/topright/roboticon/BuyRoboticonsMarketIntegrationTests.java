package com.topright.roboticon;
import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Integration tests for the {@link BuyRoboticonsMarket}, {@link Market}, {@link Player},
 * {@link PlayerInventory}, {@link MarketInventory}, {@link MessageDispatcher} classes
 * @author jcn509
 */
public class BuyRoboticonsMarketIntegrationTests extends GuiTest { 
	private Market market;
	private Player player;
	private PlayerInventory playerInventory;
	private MarketInventory marketInventory;
	private TextButton completePurchaseButton;
	private TextButton produceRoboticonButton;
	private SpinBox transactionQuantitySpinBox;
	private BuyRoboticonsMarket buyRoboticonsMarket;
	
	/**
	 * Runs before every test, creates the BuyRoboticonsMarket object that is being tested and
	 * all other required objects
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		playerInventory = new PlayerInventory(0,1,roboticonQuantities, 2);
		player = new Player(playerInventory);
		market = Market.getInstance();
		marketInventory = new MarketInventory(1,2,12);
		market.setInventory(marketInventory);
		
		buyRoboticonsMarket = new BuyRoboticonsMarket(player);
		
		completePurchaseButton = buyRoboticonsMarket.getCompletePurchaseButton();
		produceRoboticonButton = buyRoboticonsMarket.getProduceRoboticonButton();
		transactionQuantitySpinBox = buyRoboticonsMarket.getTransactionQuantitySpinBox();
		stage.addActor(buyRoboticonsMarket);
	}
	
	
	/**
	 * A helper method used to set the value of the TransActionQuantitySpinBox to a given value
	 * @param value The value that the SpinBox should hold
	 */
	private void setTransactionQuantitySpinBoxValue(int value){
		while(transactionQuantitySpinBox.getValue()<value){
			clickActor(transactionQuantitySpinBox.getIncreaseQuantityButton());
		}
		while(transactionQuantitySpinBox.getValue()>value){
			clickActor(transactionQuantitySpinBox.getDecreaseQuantityButton());
		}
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 1 and the complete purchase button clicked
	 * the player has 1 roboticon added to their inventory (assuming the player has enough money)
	 */
	@Test
	public void TestClickCompletePurchaseButtonOneRoboticon(){
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1)); // Enough money
		setTransactionQuantitySpinBoxValue(1);
		clickActor(completePurchaseButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore+1,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 5 and the complete purchase button clicked
	 * the player has 5 roboticons added to their inventory (assuming the player has enough money)
	 */
	@Test
	public void TestClickCompletePurchaseButtonFiveRoboticons(){
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(5)); // Enough money
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore+5,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 1 and the complete purchase button clicked
	 * the market has 1 roboticon taken from its inventory
	 */
	@Test
	public void TestClickCompletePurchaseButtonOneRoboticonMarket(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1)); // Enough money
		int roboticonsBefore = market.getRoboticonQuantity();
		setTransactionQuantitySpinBoxValue(1);
		clickActor(completePurchaseButton);
		int roboticonsAfter = market.getRoboticonQuantity();
		assertEquals(roboticonsBefore-1,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 1 and the complete purchase button clicked
	 * the player has the correct amount of money taken from their inventory (assuming the player has enough money)
	 */
	@Test
	public void TestClickCompletePurchaseButtonOneRoboticonMoney(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1)); // Enough money
		int moneyBefore = playerInventory.getMoneyQuantity();
		setTransactionQuantitySpinBoxValue(1);
		clickActor(completePurchaseButton);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore-market.getCostRoboticons(1),moneyAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 5 and the complete purchase button clicked
	 * the player has the correct amount of money taken from their inventory  (assuming the player has enough money)
	 */
	@Test
	public void TestClickCompletePurchaseButtonFiveRoboticonsMoney(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(5)); // Enough money
		int moneyBefore = playerInventory.getMoneyQuantity();
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore-market.getCostRoboticons(5),moneyAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 5 and the complete purchase button clicked
	 * the market has 5 roboticons taken from its inventory
	 */
	@Test
	public void TestClickCompletePurchaseButtonFiveRoboticonsMarket(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(5)); // Enough money
		int roboticonsBefore = market.getRoboticonQuantity();
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		int roboticonsAfter = market.getRoboticonQuantity();
		assertEquals(roboticonsBefore-5,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 0 and the complete purchase button clicked
	 * the player has 0 roboticons added to their inventory
	 */
	@Test
	public void TestClickCompletePurchaseButtonZerosRoboticon(){
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		setTransactionQuantitySpinBoxValue(0);
		clickActor(completePurchaseButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 5 and the complete purchase button clicked
	 * the market has no roboticons taken from its inventory
	 */
	@Test
	public void TestClickCompletePurchaseButtonZeroRoboticonsMarket(){
		int roboticonsBefore = market.getRoboticonQuantity();
		setTransactionQuantitySpinBoxValue(0);
		clickActor(completePurchaseButton);
		int roboticonsAfter = market.getRoboticonQuantity();
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 0 and the complete purchase button clicked
	 * the player has no money taken from their inventory
	 */
	@Test
	public void TestClickCompletePurchaseButtonZeroRoboticonsMoney(){
		int moneyBefore = playerInventory.getMoneyQuantity();
		setTransactionQuantitySpinBoxValue(0);
		clickActor(completePurchaseButton);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
	/**
	 * Ensures that when the players purchase fails because the player does not have enough money
	 * that a MessagePopUp is created to alert the user to this.
	 */
	@Test
	public void TestClickCompletePurchaseNotEneoughMoney(){
		int moneyBefore = playerInventory.getMoneyQuantity();
		playerInventory.decreaseMoneyQuantity(moneyBefore);
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(5)-1); // Not enough money
		
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		
		// Pop up message created to tell the user
		assertTrue(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
	
	/**
	 * Ensures that when the players purchase does not fail
	 * that a MessagePopUp is not created.
	 */
	@Test
	public void TestClickCompletePurchaseNoPopUp(){
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1));
		setTransactionQuantitySpinBoxValue(1);
		clickActor(completePurchaseButton);
		
		// Pop up message not created to tell the user
		assertFalse(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
	
	/**
	 * Ensures that when the produce roboticon button is clicked and the market has enough ore a roboticon
	 * is produced
	 */
	@Test
	public void TestProduceRoboticonButton(){
		int roboticonsBefore = market.getRoboticonQuantity();
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate()); // Enough ore
		clickActor(produceRoboticonButton);
		int roboticonsAfter = market.getRoboticonQuantity();
		assertEquals(roboticonsBefore+1,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the produce roboticon button is clicked and the market does not have enough ore a roboticon
	 * is not produced
	 */
	@Test
	public void TestProduceRoboticonButtonFailNotProduced(){
		int roboticonsBefore = market.getRoboticonQuantity();
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate()-1); // Not enough ore
		clickActor(produceRoboticonButton);
		int roboticonsAfter = market.getRoboticonQuantity();
		assertEquals(roboticonsBefore+1,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the produce roboticon button is clicked and the market has enough ore the markets
	 * ore is reduced by the correct amount
	 */
	@Test
	public void TestProduceRoboticonButtonOreQuantity(){
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate()); // Enough ore
		int oreBefore = market.getOreQuantity();
		clickActor(produceRoboticonButton);
		int oreAfter = market.getOreQuantity();
		assertEquals(oreBefore-market.getRoboticonOreConversionRate(),oreAfter);
	}
	
	/**
	 * Ensures that when the produce roboticon button is clicked and the market does not have enough ore its
	 * ore quantity is not changed
	 */
	@Test
	public void TestProduceRoboticonButtonFailNotOreQuantity(){
		marketInventory.decreaseOreQuantity(market.getOreQuantity());
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate() -1); // Not enough ore
		int oreBefore = market.getOreQuantity();
		clickActor(produceRoboticonButton);
		int oreAfter = market.getOreQuantity();
		assertEquals(oreBefore,oreAfter);
	}
	
	/**
	 * Ensures that when the produce roboticon button is clicked and that market fails to produce a roboticon
	 * (because it does not have enough ore) that a MesagePopUp is created to alert the user to this.
	 */
	@Test
	public void TestProduceRoboticonFail(){
		marketInventory.decreaseOreQuantity(market.getOreQuantity());
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate() -1); // Not enough ore
		clickActor(produceRoboticonButton);
		// Pop up message created to tell the user
		assertTrue(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
	/**
	 * Ensures that when the market does not fail to produce a roboticon a MessagePopUp is not created
	 */
	@Test
	public void TestProduceRoboticonNoPopUp(){
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate()); // Enough ore
		
		clickActor(produceRoboticonButton);
		// Pop up message created to tell the user
		assertFalse(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
}
