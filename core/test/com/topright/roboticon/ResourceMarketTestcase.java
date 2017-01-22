package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * Test case for the ResourceMarket class
 * @author jcn509
 *
 */
public class ResourceMarketTestcase extends GuiTest { 
	private ResourceMarket resourceMarket;
	@Mocked private Player player;
	@Mocked private PlayerInventory playerInventory;
	@Mocked private Market market;
	@Mocked private MarketInventory marketInventory;
	@Mocked private MessageDispatcher messageDispatcher;
	private TextButton buyOreButton;
	private TextButton sellOreButton;
	private TextButton buyEnergyButton;
	private TextButton sellEnergyButton;
	private SpinBox buyEnergySpinBox;
	private SpinBox buyOreSpinBox;
	private SpinBox sellEnergySpinBox;
	private SpinBox sellOreSpinBox;
	private Label oreInStockLabel;
	private Label energyInStockLabel;
	
	/**
	 * Runs before every test creates the resourceMarket object that is being tested
	 * as well as all other (mocked) objects that are required
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		playerInventory = new PlayerInventory(0,0,roboticonQuantities,20);
		player = new Player(playerInventory);
		market = Market.getInstance();
		
		messageDispatcher = MessageManager.getInstance();
		
		marketInventory = new MarketInventory(0,0,0);
		market.setInventory(marketInventory);
		
		new Expectations(){{
			market.getEnergyQuantity();result=10;
			market.getOreQuantity();result=9;
			player.getEnergyQuantity();result=15;
			player.getOreQuantity();result=16;
		}};
		resourceMarket = new ResourceMarket(player);
		
		stage.addActor(resourceMarket);
		
		getWidgets();
	}
	
	/**
	 * Gets all the widgets from the resourceMarket window that the user would normally use to interact with
	 * The window
	 */
	private void getWidgets(){
		buyOreButton = resourceMarket.getPurchaseOreTextButton();
		buyEnergyButton = resourceMarket.getPurchaseEnergyTextButton();
		sellOreButton = resourceMarket.getSellOreTextButton();
		sellEnergyButton = resourceMarket.getSellEnergyTextButton();
		buyOreSpinBox = resourceMarket.getPurchaseOreQuantitySpinBox();
		buyEnergySpinBox = resourceMarket.getPurchaseEnergyQuantitySpinBox();
		sellOreSpinBox = resourceMarket.getSellOreQuantitySpinBox();
		sellEnergySpinBox = resourceMarket.getSellEnergyQuantitySpinBox();
		
		oreInStockLabel = resourceMarket.getMarketOreQuantityLabel();
		energyInStockLabel = resourceMarket.getMarketEnergyQuantityLabel();
	}
	
	/**
	 * A helper method used to set the value of a SpinBox to a given value
	 * <p>
	 * Not that the method will exit either loop if the SpinBox has the same value after
	 * 2 consecutive iterations. This happens if the max value is smaller than the value
	 * that the user is trying to set the SpinBox too or if the min value is smaller.
	 * </p>
	 * @param value The value that the SpinBox should hold
	 * @param spinBox The spinBox whose value is to be set
	 */
	private void setSpinBoxValue(int value,SpinBox spinBox){
		int valueOnLastIteration = spinBox.getValue();
		while(spinBox.getValue()<value){
			clickActor(spinBox.getIncreaseQuantityButton());
			if(valueOnLastIteration == spinBox.getValue()){
				break;
			}
			valueOnLastIteration=spinBox.getValue();
		}
		while(spinBox.getValue()>value){
			clickActor(spinBox.getDecreaseQuantityButton());
			if(valueOnLastIteration == spinBox.getValue()){
				break;
			}
			valueOnLastIteration=spinBox.getValue();
		}
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 0 the attemptToBuyEnergy method from the Player class is not called.
	 */
	@Test
	public void testClickBuyEnergyButtonZeroEnergyPlayerNotBuyEnergy(){
		setSpinBoxValue(0,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			player.attemptToBuyEnergy(anyInt);times=0;
		}};
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 0 a message is not dispatched that states that the players inventory has been updated
	 */
	@Test
	public void testClickBuyEnergyButtonZeroEnergyMessageNotDispatched(){
		setSpinBoxValue(0,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0;
		}};
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 1 the attemptToBuyEnergy method from the Player class is called with an input value of 1
	 */
	@Test
	public void testClickBuyEnergyButtonOneEnergyBuyEnergy(){
		setSpinBoxValue(1,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			player.attemptToBuyEnergy(1);times=1;
		}};
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 1 a message is dispatched that states that the players inventory has been updated
	 * if the sale is successful.
	 */
	@Test
	public void testClickBuyEnergyButtonOneEnergyMessageDispatched(){
		new Expectations(){{
			player.attemptToBuyEnergy(anyInt);result=true;  // Successful sale
		}};
		setSpinBoxValue(1,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=1;
		}};
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 5 the attemptToBuyEnergy method from the Player class is called with an input value of 5
	 */
	@Test
	public void testClickBuyEnergyButtonFiveEnergyBuyEnergy(){
		setSpinBoxValue(5,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			player.attemptToBuyEnergy(5);times=1;
		}};
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 5 a message is dispatched that states that the players inventory has been updated
	 * if the sale was successful
	 */
	@Test
	public void testClickBuyEnergyButtonFiveEnergyMessageDispatched(){
		new Expectations(){{
			player.attemptToBuyEnergy(anyInt);result=true; // Successful sale
		}};
		setSpinBoxValue(5,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=1;
		}};
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to a positive value a message is not dispatched that states that the players inventory has been updated
	 * if the sale was not successful
	 */
	@Test
	public void testClickBuyEnergyFailedSaleMessageNotDispatched(){
		new Expectations(){{
			player.attemptToBuyEnergy(anyInt);result=false; // Unsuccessful sale
		}};
		setSpinBoxValue(5,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0;
		}};
	}
	
	
	/**
	 * Ensures that when the buy energy button is clicked (and the buy energy SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of energy in stock at the market is updated
	 * to the new value (when the amount in stock is 100000000).
	 */
	 @Test
	 public void testClickBuyEnergyButtonEnergyStockUpdatedHugeNumber(){
		 new Expectations(){{
			player.attemptToBuyEnergy(anyInt); result = true; // Sale successful 
			
			// Initial energy value is 10 (see setup method)
			market.getEnergyQuantity();result=100000000; // New value to be displayed
		 }};
		 setSpinBoxValue(10,buyEnergySpinBox);
		 clickActor(buyEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),100000000);
	 }
	 
	 /**
	 * Ensures that when the buy energy button is clicked (and the buy energy SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of energy in stock at the market is updated
	 * to the new value (when the amount in stock is small).
	 */
	 @Test
	 public void testClickBuyEnergyButtonEnergyStockUpdatedSmallNumber(){
		 new Expectations(){{
			player.attemptToBuyEnergy(anyInt); result = true; // Sale successful 
			
			// Initial energy value is 10 (see setup method)
			market.getEnergyQuantity();result=1; // New value to be displayed
		 }};
		 setSpinBoxValue(10,buyEnergySpinBox);
		 clickActor(buyEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),1);
	 }
	 
	 /**
	 * Ensures that when the buy energy button is clicked (and the buy energy SpinBox has a value > 0) that if
	 * the sale is not successful then the label that denotes the amount of energy in stock at the market is not updated
	 */
	 @Test
	 public void testClickBuyEnergyButtonEnergySaleFailedStockNotUpdated(){
		 new Expectations(){{
			player.attemptToBuyEnergy(anyInt); result = false; // Sale successful 
		 }};
		 setSpinBoxValue(10,buyEnergySpinBox);
		 clickActor(buyEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),10); // Value unchaged
	 }
	 
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 0 the attemptToSellEnergy method from the Player class is not called.
	 */
	@Test
	public void testClickSellEnergyButtonZeroEnergyPlayerNotSellEnergy(){
		setSpinBoxValue(0,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			player.attemptToSellEnergy(anyInt);times=0;
		}};
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 0 a message is not dispatched that states that the players inventory has been updated
	 */
	@Test
	public void testClickSellEnergyButtonZeroEnergyMessageNotDispatched(){
		setSpinBoxValue(0,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0;
		}};
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 1 the attemptToSellEnergy method from the Player class is called with an input value of 1
	 */
	@Test
	public void testClickSellEnergyButtonOneEnergySellEnergy(){
		setSpinBoxValue(1,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			player.attemptToSellEnergy(1);times=1;
		}};
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 1 a message is dispatched that states that the players inventory has been updated
	 * if the sale is successful.
	 */
	@Test
	public void testClickSellEnergyButtonOneEnergyMessageDispatched(){
		new Expectations(){{
			player.attemptToSellEnergy(anyInt);result=true;  // Successful sale
		}};
		setSpinBoxValue(1,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=1;
		}};
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 5 the attemptToSellEnergy method from the Player class is called with an input value of 5
	 */
	@Test
	public void testClickSellEnergyButtonFiveEnergySellEnergy(){
		setSpinBoxValue(5,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			player.attemptToSellEnergy(5);times=1;
		}};
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 5 a message is dispatched that states that the players inventory has been updated
	 * if the sale was successful
	 */
	@Test
	public void testClickSellEnergyButtonFiveEnergyMessageDispatched(){
		new Expectations(){{
			player.attemptToSellEnergy(anyInt);result=true; // Successful sale
		}};
		setSpinBoxValue(5,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=1;
		}};
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to a positive value a message is not dispatched that states that the players inventory has been updated
	 * if the sale was not successful
	 */
	@Test
	public void testClickSellEnergyFailedSaleMessageNotDispatched(){
		new Expectations(){{
			player.attemptToSellEnergy(anyInt);result=false; // Unsuccessful sale
		}};
		setSpinBoxValue(5,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0;
		}};
	}
	
	
	/**
	 * Ensures that when the sell energy button is clicked (and the sell energy SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of energy in stock at the market is updated
	 * to the new value (when the amount in stock is 100000000).
	 */
	 @Test
	 public void testClickSellEnergyButtonEnergyStockUpdatedHugeNumber(){
		 new Expectations(){{
			player.attemptToSellEnergy(anyInt); result = true; // Sale successful 
			
			// Initial energy value is 10 (see setup method)
			market.getEnergyQuantity();result=100000000; // New value to be displayed
		 }};
		 setSpinBoxValue(10,sellEnergySpinBox);
		 clickActor(sellEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),100000000);
	 }
	 
	 /**
	 * Ensures that when the sell energy button is clicked (and the sell energy SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of energy in stock at the market is updated
	 * to the new value (when the amount in stock is small).
	 */
	 @Test
	 public void testClickSellEnergyButtonEnergyStockUpdatedSmallNumber(){
		 new Expectations(){{
			player.attemptToSellEnergy(anyInt); result = true; // Sale successful 
			
			// Initial energy value is 15 (see setup method)
			market.getEnergyQuantity();result=1; // New value to be displayed
		 }};
		 setSpinBoxValue(10,sellEnergySpinBox);
		 clickActor(sellEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),1);
	 }
	 
	 /**
	 * Ensures that when the sell energy button is clicked (and the sell energy SpinBox has a value > 0) that if
	 * the sale is not successful then the label that denotes the amount of energy in stock at the market is not updated
	 */
	 @Test
	 public void testClickSellEnergyButtonEnergySaleFailedStockNotUpdated(){
		 new Expectations(){{
			player.attemptToSellEnergy(anyInt); result = false; // Sale successful 
		 }};
		 setSpinBoxValue(10,sellEnergySpinBox);
		 clickActor(sellEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),10); // Value unchaged
	 }
	 
	 /**
	  * Ensures that the player cannot buy more energy than is in stock, by ensuring that they cannot
	  * set the buy energy SpinBox to a value larger than that. Tested before any purchases or sales have been made.
	  * When the market has 10 energy in its inventory initially.
	  */
	 @Test
	 public void testCannotBuyMoreEnergyThanHaveInInventoryInitially(){
		 // Market has 10 ore initially - see setup method
		 setSpinBoxValue(12,buyEnergySpinBox);
		 assertEquals(10,buyEnergySpinBox.getValue()); // Not able to go beyond 10
		 
	 }
	 
	 /**
	  * Ensures that the player cannot buy more energy than is in stock, by ensuring that they cannot
	  * set the buy energy SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the market 3 energy in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreEnergyThanHaveInInventoryAfterPurchase(){
		 new Expectations(){{
			market.getEnergyQuantity();result=3; // New value after purchase
			player.attemptToBuyEnergy(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,buyEnergySpinBox);
		 clickActor(buyEnergyButton); // Purchase made
		 setSpinBoxValue(12,buyEnergySpinBox); // Initially 10 energy in stock see setup method
		 assertEquals(3,buyEnergySpinBox.getValue()); // Not able to go beyond 3
		 
	 }
	 
	 /**
	  * Ensures that the player cannot buy more energy than is in stock, by ensuring that they cannot
	  * set the buy energy SpinBox to a value larger than that. Tested after a sale has been made
	  * When after the sale the market has 4 energy in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreEnergyThanHaveInInventoryAfterSale(){
		 new Expectations(){{
			market.getEnergyQuantity();result=4; // New value after sale
			player.attemptToSellEnergy(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,sellEnergySpinBox);
		 clickActor(sellEnergyButton); // Sale made
		 setSpinBoxValue(12,buyEnergySpinBox); // Initially 10 energy in stock see setup method
		 assertEquals(4,buyEnergySpinBox.getValue()); // Not able to go beyond 4
	 }
	 
	 /**
	  * Ensures that the player cannot sell more energy they have in their inventory, by ensuring that they cannot
	  * set the sell energy SpinBox to a value larger than that. Tested before any purchases or sales have been made.
	  * When the player has 15 energy in its inventory initially.
	  */
	 @Test
	 public void testCannotSellMoreEnergyThanHaveInInventoryInitially(){
		 // Player has 15 energy originally - see setup method
		 setSpinBoxValue(18,sellEnergySpinBox);
		 assertEquals(15,sellEnergySpinBox.getValue()); // Not able to go beyond 15
		 
	 }
	 
	 /**
	  * Ensures that the player cannot sell more energy they have in their inventory, by ensuring that they cannot
	  * set the sell energy SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the player has 3 energy in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreEnergyThanHaveInInventoryAfterPurchase(){
		 new Expectations(){{
			player.getEnergyQuantity();result=3; // New value after purchase
			player.attemptToBuyEnergy(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,buyEnergySpinBox);
		 clickActor(buyEnergyButton); // Purchase made
		 setSpinBoxValue(12,sellEnergySpinBox); // Initially 10 energy in stock see setup method
		 assertEquals(3,sellEnergySpinBox.getValue()); // Not able to go beyond 3
		 
	 }
	 
	 /**
	  * Ensures that the player cannot sell more energy they have in their inventory, by ensuring that they cannot
	  * set the sell energy SpinBox to a value larger than that. Tested after a sale has been made
	  * When after the sale the player has 3 energy in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreEnergyThanHaveInInventoryAfterSale(){
		 new Expectations(){{
			player.getEnergyQuantity();result=4; // New value after sale
			player.attemptToSellEnergy(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,sellEnergySpinBox);
		 clickActor(sellEnergyButton); // Sale made
		 setSpinBoxValue(19,sellEnergySpinBox); // Initially 15 energy in stock see setup method
		 assertEquals(4,sellEnergySpinBox.getValue()); // Not able to go beyond 4
	 }
	 
	 /**
	  * Ensures that the player cannot buy more ore than is in stock, by ensuring that they cannot
	  * set the buy ore SpinBox to a value larger than that. Tested before any purchases or sales have been made.
	  * When the market has 9 ore in its inventory initially.
	  */
	 @Test
	 public void testCannotBuyMoreOreThanHaveInInventoryInitially(){
		 // Market has 9 ore initially - see setup method
		 setSpinBoxValue(12,buyOreSpinBox);
		 assertEquals(9,buyOreSpinBox.getValue()); // Not able to go beyond 9
		 
	 }
	 
	 /**
	  * Ensures that the player cannot buy more ore than is in stock, by ensuring that they cannot
	  * set the buy ore SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the market 3 ore in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreOreThanHaveInInventoryAfterPurchase(){
		 new Expectations(){{
			market.getOreQuantity();result=3; // New value after purchase
			player.attemptToBuyOre(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,buyOreSpinBox);
		 clickActor(buyOreButton); // Purchase made
		 setSpinBoxValue(12,buyOreSpinBox); // Initially 9 ore in stock see setup method
		 assertEquals(3,buyOreSpinBox.getValue()); // Not able to go beyond 3
		 
	 }
	 
	 /**
	  * Ensures that the player cannot buy more ore than is in stock, by ensuring that they cannot
	  * set the buy ore SpinBox to a value larger than that. Tested after a sale has been made
	  * When after the sale the market has 4 ore in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreOreThanHaveInInventoryAfterSale(){
		 new Expectations(){{
			market.getOreQuantity();result=4; // New value after sale
			player.attemptToSellOre(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,sellOreSpinBox);
		 clickActor(sellOreButton); // Sale made
		 setSpinBoxValue(12,buyOreSpinBox); // Initially 9 ore in stock see setup method
		 assertEquals(4,buyOreSpinBox.getValue()); // Not able to go beyond 4
	 }
	 
	 /**
	  * Ensures that the player cannot sell more ore they have in their inventory, by ensuring that they cannot
	  * set the sell ore SpinBox to a value larger than that. Tested before any purchases or sales have been made.
	  * When the player has 16 ore in its inventory initially.
	  */
	 @Test
	 public void testCannotSellMoreOreThanHaveInInventoryInitially(){
		 // Player has 16 ore originally - see setup method
		 setSpinBoxValue(18,sellOreSpinBox);
		 assertEquals(16,sellOreSpinBox.getValue()); // Not able to go beyond 16
		 
	 }
	 
	 /**
	  * Ensures that the player cannot sell more ore they have in their inventory, by ensuring that they cannot
	  * set the sell ore SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the player has 3 ore in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreOreThanHaveInInventoryAfterPurchase(){
		 new Expectations(){{
			player.getOreQuantity();result=3; // New value after purchase
			player.attemptToBuyOre(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,buyOreSpinBox);
		 clickActor(buyOreButton); // Purchase made
		 setSpinBoxValue(12,sellOreSpinBox); // Initially 9 ore in stock see setup method
		 assertEquals(3,sellOreSpinBox.getValue()); // Not able to go beyond 3
		 
	 }
	 
	 /**
	  * Ensures that the player cannot sell more ore they have in their inventory, by ensuring that they cannot
	  * set the sell ore SpinBox to a value larger than that. Tested after a sale has been made
	  * When after the sale the player has 3 ore in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreOreThanHaveInInventoryAfterSale(){
		 new Expectations(){{
			player.getOreQuantity();result=4; // New value after sale
			player.attemptToSellOre(anyInt);result=true;
		 }};
		 setSpinBoxValue(1,sellOreSpinBox);
		 clickActor(sellOreButton); // Sale made
		 setSpinBoxValue(19,sellOreSpinBox); // Initially 16 ore in stock see setup method
		 assertEquals(4,sellOreSpinBox.getValue()); // Not able to go beyond 4
	 }
	 
	 /**
	 * Ensures that when the buy ore button is clicked (and the buy ore SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of ore in stock at the market is updated
	 * to the new value (when the amount in stock is 100000000).
	 */
	 @Test
	 public void testClickBuyOreButtonOreStockUpdatedHugeNumber(){
		 new Expectations(){{
			player.attemptToBuyOre(anyInt); result = true; // Sale successful 
			
			// Initial ore value is 10 (see setup method)
			market.getOreQuantity();result=100000000; // New value to be displayed
		 }};
		 setSpinBoxValue(10,buyOreSpinBox);
		 clickActor(buyOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),100000000);
	 }
	 
	 /**
	 * Ensures that when the buy ore button is clicked (and the buy ore SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of ore in stock at the market is updated
	 * to the new value (when the amount in stock is small).
	 */
	 @Test
	 public void testClickBuyOreButtonOreStockUpdatedSmallNumber(){
		 new Expectations(){{
			player.attemptToBuyOre(anyInt); result = true; // Sale successful 
			
			// Initial ore value is 10 (see setup method)
			market.getOreQuantity();result=1; // New value to be displayed
		 }};
		 setSpinBoxValue(10,buyOreSpinBox);
		 clickActor(buyOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),1);
	 }
	 
	 /**
	 * Ensures that when the buy ore button is clicked (and the buy ore SpinBox has a value > 0) that if
	 * the sale is not successful then the label that denotes the amount of ore in stock at the market is not updated
	 */
	 @Test
	 public void testClickBuyOreButtonOreSaleFailedStockNotUpdated(){
		 new Expectations(){{
			player.attemptToBuyOre(anyInt); result = false; // Sale successful 
		 }};
		 setSpinBoxValue(10,buyOreSpinBox);
		 clickActor(buyOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),10); // Value unchaged
	 }

	 /**
	 * Ensures that when the sell ore button is clicked (and the sell ore SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of ore in stock at the market is updated
	 * to the new value (when the amount in stock is 100000000).
	 */
	 @Test
	 public void testClickSellOreButtonOreStockUpdatedHugeNumber(){
		 new Expectations(){{
			player.attemptToSellOre(anyInt); result = true; // Sale successful 
			
			// Initial ore value is 10 (see setup method)
			market.getOreQuantity();result=100000000; // New value to be displayed
		 }};
		 setSpinBoxValue(10,sellOreSpinBox);
		 clickActor(sellOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),100000000);
	 }
	 
	 /**
	 * Ensures that when the sell ore button is clicked (and the sell ore SpinBox has a value > 0) that if
	 * the sale is successful then the label that denotes the amount of ore in stock at the market is updated
	 * to the new value (when the amount in stock is small).
	 */
	 @Test
	 public void testClickSellOreButtonOreStockUpdatedSmallNumber(){
		 new Expectations(){{
			player.attemptToSellOre(anyInt); result = true; // Sale successful 
			
			// Initial ore value is 15 (see setup method)
			market.getOreQuantity();result=1; // New value to be displayed
		 }};
		 setSpinBoxValue(10,sellOreSpinBox);
		 clickActor(sellOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),1);
	 }
	 
	 /**
	 * Ensures that when the sell ore button is clicked (and the sell ore SpinBox has a value > 0) that if
	 * the sale is not successful then the label that denotes the amount of ore in stock at the market is not updated
	 */
	 @Test
	 public void testClickSellOreButtonOreSaleFailedStockNotUpdated(){
		 new Expectations(){{
			player.attemptToSellOre(anyInt); result = false; // Sale successful 
		 }};
		 setSpinBoxValue(10,sellOreSpinBox);
		 clickActor(sellOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),10); // Value unchaged
	 }

}
