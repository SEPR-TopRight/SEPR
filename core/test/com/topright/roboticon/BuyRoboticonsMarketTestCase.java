package com.topright.roboticon;
import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * Test case for the BuyRoboticonsMarket class
 * <p>Unfortunately due to the design of the BuyRoboticonsClass, many of these tests are not true unit tests
 * as the class depends on a SpinBox object and MessagePopUp objects that cannot be mocked out, although it would not make sense
 * for us to inject this dependency into the class in this case (i.e. pass the SpinBox to the class 
 * via its constructor). So we feel justified that the design decisions we made were sensible (even if they make unit testing hard).
 * </p>
 * @author jcn509
 */
public class BuyRoboticonsMarketTestCase extends GuiTest { 
	@Mocked private Market market;
	@Mocked private Player player;
	@Mocked private PlayerInventory playerInventory;
	@Mocked private MarketInventory marketInventory;
	@Mocked private MessageDispatcher messageDispatcher;
	private TextButton completePurchaseButton;
	private TextButton produceRoboticonButton;
	private SpinBox transactionQuantitySpinBox;
	private BuyRoboticonsMarket buyRoboticonsMarket;
	
	@Before
	public void setup(){
		messageDispatcher = MessageManager.getInstance();
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		playerInventory = new PlayerInventory(0,1,roboticonQuantities, 2);
		player = new Player(playerInventory);
		market = Market.getInstance();
		marketInventory = new MarketInventory(1,2,3);
		market.setInventory(marketInventory);
		new Expectations(){{
			market.getRoboticonQuantity();result=12; // 12 roboticons in stock (value needed by the BuyRoboticonsMarket constructor)
		}};
		
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
	 * the attemptToBuyRoboticons method from the player class is called with an input value of 1
	 */
	@Test
	public void TestClickCompletePurchaseButtonOneRoboticon(){
		new Expectations(){{
			player.attemptToBuyRoboticons(5);result=true;
		}};
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 5 and the complete purchase button clicked
	 * the attemptToBuyRoboticons method from the player class is called with an input value of 5
	 */
	@Test
	public void TestClickCompletePurchaseButtonFiveRoboticons(){
		new Expectations(){{
			player.attemptToBuyRoboticons(5);result=true;
		}};
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
	}
	
	/**
	 * Ensures that when the transaction quantity spinbox is set to 0 and the complete purchase button clicked
	 * the attemptToBuyRoboticons method from the player class is not called
	 */
	@Test
	public void TestClickCompletePurchaseButtonZeroRoboticons(){
		setTransactionQuantitySpinBoxValue(0);
		clickActor(completePurchaseButton);
		new Verifications(){{
			player.attemptToBuyRoboticons(anyInt);times=0;
		}};
	}
	
	/**
	 * Ensures that when the players purchase fails for whatever reason (i.e. the player does not have enough money)
	 * that a MessagePopUp is created to alert the user to this.
	 */
	@Test
	public void TestClickCompletePurchaseFailed(){
		new Expectations(){{
			player.attemptToBuyRoboticons(anyInt); result = false; // Purchase fails
		}};
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
		new Expectations(){{
			player.attemptToBuyRoboticons(anyInt); result = true; // Purchase does not fail
		}};
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		
		// Pop up message created to tell the user
		assertFalse(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
	/**
	 * Ensures a message is dispatched stating that the player's inventory has been updated when
	 * the player's purchase does not fail
	 */
	@Test
	public void TestClickCompletePurchaseMessageDispatched(){
		new Expectations(){{
			player.attemptToBuyRoboticons(anyInt); result = true; // Purchase does not fail
		}};
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		
		new Verifications(){{ // Message dispatched
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=1;
		}};
	}
	
	/**
	 * Ensures a message is not dispatched stating that the player's inventory has been updated when
	 * the player's purchase fails
	 */
	@Test
	public void TestClickCompletePurchaseFailNoMessageDispatched(){
		new Expectations(){{
			player.attemptToBuyRoboticons(anyInt); result = false; // Purchase fails
		}};
		setTransactionQuantitySpinBoxValue(5);
		clickActor(completePurchaseButton);
		
		new Verifications(){{ // Message not dispatched
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0;
		}};
	}
	
	
	/**
	 * Ensures that when the produce roboticon button is clicked that the appropriate method 
	 * in the market class is called
	 */
	@Test
	public void TestProduceRoboticonButton(){
		clickActor(produceRoboticonButton);
		new Verifications(){{
			market.attemptToProduceRoboticon(); times=1;
		}};
	}
	
	/**
	 * Ensures that when the produce roboticon button is clicked and that market fails to produce a roboticon
	 * (because it does not have enough ore) that a MesagePopUp is created to alert the user to this.
	 */
	@Test
	public void TestProduceRoboticonFail(){
		new Expectations(){{
			market.attemptToProduceRoboticon(); result = false;
		}};
		clickActor(produceRoboticonButton);
		// Pop up message created to tell the user
		assertTrue(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
	/**
	 * Ensures that when the market does not fail to produce a roboticon a MessagePopUp is not created
	 */
	@Test
	public void TestProduceRoboticonNoPopUp(){
		new Expectations(){{
			market.attemptToProduceRoboticon(); result = true;
		}};
		clickActor(produceRoboticonButton);
		// Pop up message created to tell the user
		assertFalse(stage.getActors().items[1] instanceof MessagePopUp);
	}
	
}
