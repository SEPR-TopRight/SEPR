package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

/**
 * Test case for the ResourceMarket class
 * @author jcn509
 *
 */
public class ResourceMarketIntegrationTests extends GuiTest { 
	private ResourceMarket resourceMarket;
	private Player player;
	private PlayerInventory playerInventory;
	private Market market;
	private MarketInventory marketInventory;
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
		playerInventory = new PlayerInventory(15,16,roboticonQuantities,20);
		player = new Player(playerInventory);
		market = Market.getInstance();
		
		marketInventory = new MarketInventory(10,9,0);
		market.setInventory(marketInventory);
		
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
	 * set to 0 the quantity of energy in the Players inventory is not increased
	 */
	@Test
	public void testClickBuyEnergyButtonZeroEnergyPlayerNotBuyEnergy(){
		int energyBefore = playerInventory.getEnergyQuantity();
		setSpinBoxValue(0,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}

	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 1 the quantity of energy in the Players inventory is increased by 1
	 */
	@Test
	public void testClickBuyEnergyButtonOneEnergyBuyEnergy(){
		int energyBefore = playerInventory.getEnergyQuantity();
		setSpinBoxValue(1,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(energyBefore+1,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 1 the quantity of money in the players inventory is reduced by the correct amount
	 */
	@Test
	public void testClickBuyEnergyButtonOneEnergyBuyEnergyMoneyReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(1));
		int moneyBefore = playerInventory.getMoneyQuantity();
		setSpinBoxValue(1,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(moneyBefore-market.getCostEnergy(1),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 5 the quantity of energy in the Players inventory is increased by 5
	 * (when the player has enough money to buy 5 energy)
	 */
	@Test
	public void testClickBuyEnergyButtonFiveEnergyBuyEnergy(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(5)); // Have enough money
		int energyBefore = playerInventory.getEnergyQuantity();
		setSpinBoxValue(5,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(energyBefore+5,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 5 the quantity of money in the players inventory is reduced by the correct amount
	 */
	@Test
	public void testClickBuyEnergyButtonFiveEnergyBuyEnergyMoneyReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(5));
		int moneyBefore = playerInventory.getMoneyQuantity();
		setSpinBoxValue(5,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(moneyBefore-market.getCostEnergy(5),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 1 the quantity of energy in the markets inventory is reduced by 1
	 */
	@Test
	public void testClickBuyEnergyButtonOneEnergyBuyEnergyEnergyReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(1));
		int energyBefore = marketInventory.getEnergyQuantity();
		setSpinBoxValue(1,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(energyBefore-1,marketInventory.getEnergyQuantity());
	}
	
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy quantity SpinBox is
	 * set to 5 the quantity of energy in the markets inventory is reduced by 5
	 */
	@Test
	public void testClickBuyEnergyButtonFiveEnergyBuyEnergyEnergyReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(5));
		int energyBefore = marketInventory.getEnergyQuantity();
		setSpinBoxValue(5,buyEnergySpinBox);
		clickActor(buyEnergyButton);
		assertEquals(energyBefore-5,marketInventory.getEnergyQuantity());
	}
	
	
	/**
	 * Ensures that when the buy energy button is clicked and the buy energy SpinBox has a value of 1, 
	 * the player has enough money to buy the energy and the market has 1 energy
	 * the value stored in the label that displays how much energy the market has is updated
	 */
	 @Test
	 public void testClickBuyEnergyButtonEnergyStockUpdatedOne(){
		 int valueBefore = Integer.parseInt(energyInStockLabel.getText().toString());
		 playerInventory.increaseMoneyQuantity(market.getCostEnergy(1));
		 setSpinBoxValue(1,buyEnergySpinBox);
		 clickActor(buyEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),valueBefore-1);
	 }
	 
	 /**
	 * Ensures that when the buy energy button is clicked and the buy energy SpinBox has a value of 2, 
	 * the player has enough money to buy the energy and the market has 2 energy
	 * the value stored in the label that displays how much energy the market has is updated
	 */
	 @Test
	 public void testClickBuyEnergyButtonEnergyStockUpdatedTwo(){
		 int valueBefore = Integer.parseInt(energyInStockLabel.getText().toString());
		 playerInventory.increaseMoneyQuantity(market.getCostEnergy(2));
		 setSpinBoxValue(2,buyEnergySpinBox);
		 clickActor(buyEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),valueBefore-2);
	 }
	 
	 /**
	 * Ensures that when the buy energy button is clicked and the buy energy SpinBox has a value of 0, 
	 * the value stored in the label that displays how much energy the market has is not changed
	 */
	 @Test
	 public void testClickBuyEnergyButtonEnergyStockUpdatedZero(){
		 int valueBefore = Integer.parseInt(energyInStockLabel.getText().toString());
		 setSpinBoxValue(0,buyEnergySpinBox);
		 clickActor(buyEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),valueBefore);
	 }	 
	 
	 /**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 0 the quantity of energy in the Players inventory is not decreased
	 */
	@Test
	public void testClickSellEnergyButtonZeroEnergyPlayerNotSellEnergy(){
		int energyBefore = playerInventory.getEnergyQuantity();
		setSpinBoxValue(0,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		assertEquals(energyBefore,playerInventory.getEnergyQuantity());
	}

	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 1 the quantity of energy in the Players inventory is decreased by 1
	 */
	@Test
	public void testClickSellEnergyButtonOneEnergySellEnergy(){
		int energyBefore = playerInventory.getEnergyQuantity();
		setSpinBoxValue(1,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		assertEquals(energyBefore-1,playerInventory.getEnergyQuantity());
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 5 the quantity of energy in the Players inventory is decreased by 5
	 */
	@Test
	public void testClickSellEnergyButtonFiveEnergySellEnergy(){
		int energyBefore = playerInventory.getEnergyQuantity();
		setSpinBoxValue(5,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		assertEquals(energyBefore-5,playerInventory.getEnergyQuantity());
	}	
	
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy SpinBox has a value of 1
	 * and the player has 1 energy
	 * the value stored in the label that displays how much energy the market has is updated
	 */
	 @Test
	 public void testClickSellEnergyButtonEnergyStockUpdatedOne(){
		 int valueBefore = Integer.parseInt(energyInStockLabel.getText().toString());
		 playerInventory.increaseEnergyQuantity(1);
		 setSpinBoxValue(1,sellEnergySpinBox);
		 clickActor(sellEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),valueBefore+1);
	 }
	 
	 /**
	 * Ensures that when the sell energy button is clicked and the sell energy SpinBox has a value of 2
	 * and the player has 2 energy
	 * the value stored in the label that displays how much energy the market has is updated
	 */
	 @Test
	 public void testClickSellEnergyButtonEnergyStockUpdatedTwo(){
		 int valueBefore = Integer.parseInt(energyInStockLabel.getText().toString());
		 playerInventory.increaseEnergyQuantity(2);
		 setSpinBoxValue(2,sellEnergySpinBox);
		 clickActor(sellEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),valueBefore+2);
	 }
	 
	 /**
	 * Ensures that when the sell energy button is clicked and the sell energy SpinBox has a value of 0, 
	 * the value stored in the label that displays how much energy the market has is not changed
	 */
	 @Test
	 public void testClickSellEnergyButtonEnergyStockUpdatedZero(){
		 int valueBefore = Integer.parseInt(energyInStockLabel.getText().toString());
		 setSpinBoxValue(0,sellEnergySpinBox);
		 clickActor(sellEnergyButton);
		 assertEquals(Integer.parseInt(energyInStockLabel.getText().toString()),valueBefore);
	 }	 
	 
	 /**
	  * Ensures that the player cannot buy more energy than is in stock, by ensuring that they cannot
	  * set the buy energy SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the market 3 energy in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreEnergyThanHaveInInventoryAfterPurchase(){
		 // 4 energy left in the market
		 marketInventory.decreaseEnergyQuantity(market.getEnergyQuantity());
		 marketInventory.increaseEnergyQuantity(4);
		 
		 playerInventory.increaseMoneyQuantity(market.getCostEnergy(4));
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
		// 1 energy left in the market
		marketInventory.decreaseEnergyQuantity(market.getEnergyQuantity());
		marketInventory.increaseEnergyQuantity(1);
		playerInventory.increaseEnergyQuantity(3); // can sell 3 energy
		setSpinBoxValue(3,sellEnergySpinBox);
		clickActor(sellEnergyButton); // Sale made
		setSpinBoxValue(12,buyEnergySpinBox); // Initially 10 energy in stock see setup method
		assertEquals(4,buyEnergySpinBox.getValue()); // Not able to go beyond 4
	 }
	 
	 /**
	  * Ensures that the player cannot sell more energy they have in their inventory, by ensuring that they cannot
	  * set the sell energy SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the player has 3 energy in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreEnergyThanHaveInInventoryAfterPurchase(){
		// 1 energy left in the market
		marketInventory.decreaseEnergyQuantity(market.getEnergyQuantity());
		marketInventory.increaseEnergyQuantity(1);
		
		// 2 energy left in player's inventory
		playerInventory.decreaseEnergyQuantity(player.getEnergyQuantity());
		playerInventory.increaseEnergyQuantity(2);
		
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
		// 1 energy left in the market
		marketInventory.decreaseEnergyQuantity(market.getEnergyQuantity());
		marketInventory.increaseEnergyQuantity(1);
			
		// 4 energy left in player's inventory
		playerInventory.decreaseEnergyQuantity(player.getEnergyQuantity());
		playerInventory.increaseEnergyQuantity(4);
		
		resourceMarket = new ResourceMarket(player);
		
		setSpinBoxValue(1,sellEnergySpinBox);
		clickActor(sellEnergyButton); // Sale made
		setSpinBoxValue(19,sellEnergySpinBox); // Initially 15 energy in stock see setup method
		assertEquals(3,sellEnergySpinBox.getValue()); // Not able to go beyond 3
	 }
	 
	 
	 /**
	  * Ensures that the player cannot buy more ore than is in stock, by ensuring that they cannot
	  * set the buy ore SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the market 3 ore in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreOreThanHaveInInventoryAfterPurchase(){
		 // 4 ore left in the market
		 marketInventory.decreaseOreQuantity(market.getOreQuantity());
		 marketInventory.increaseOreQuantity(4);
		 
		 playerInventory.increaseMoneyQuantity(market.getCostOre(4));
		 setSpinBoxValue(1,buyOreSpinBox);
		 clickActor(buyOreButton); // Purchase made
		 setSpinBoxValue(12,buyOreSpinBox); // Initially 10 ore in stock see setup method
		 assertEquals(3,buyOreSpinBox.getValue()); // Not able to go beyond 3
		 
	 }
	 
	 /**
	  * Ensures that the player cannot buy more ore than is in stock, by ensuring that they cannot
	  * set the buy ore SpinBox to a value larger than that. Tested after a sale has been made
	  * When after the sale the market has 4 ore in its inventory afterwards.
	  */
	 @Test
	 public void testCannotBuyMoreOreThanHaveInInventoryAfterSale(){
		// 1 ore left in the market
		marketInventory.decreaseOreQuantity(market.getOreQuantity());
		marketInventory.increaseOreQuantity(1);
		playerInventory.increaseOreQuantity(3); // can sell 3 ore
		setSpinBoxValue(3,sellOreSpinBox);
		clickActor(sellOreButton); // Sale made
		setSpinBoxValue(12,buyOreSpinBox); // Initially 10 ore in stock see setup method
		assertEquals(4,buyOreSpinBox.getValue()); // Not able to go beyond 4
	 }
	 
	 /**
	  * Ensures that the player cannot sell more ore they have in their inventory, by ensuring that they cannot
	  * set the sell ore SpinBox to a value larger than that. Tested after a purchase has been made
	  * When after the purchase the player has 3 ore in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreOreThanHaveInInventoryAfterPurchase(){
		// 1 ore left in the market
		marketInventory.decreaseOreQuantity(market.getOreQuantity());
		marketInventory.increaseOreQuantity(1);
		
		// 2 ore left in player's inventory
		playerInventory.decreaseOreQuantity(player.getOreQuantity());
		playerInventory.increaseOreQuantity(2);
		
		playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		setSpinBoxValue(1,buyOreSpinBox);
		clickActor(buyOreButton); // Purchase made
		setSpinBoxValue(12,sellOreSpinBox); // Initially 10 ore in stock see setup method
		assertEquals(3,sellOreSpinBox.getValue()); // Not able to go beyond 3
		 
	 }
	 
	 /**
	  * Ensures that the player cannot sell more ore they have in their inventory, by ensuring that they cannot
	  * set the sell ore SpinBox to a value larger than that. Tested after a sale has been made
	  * When after the sale the player has 3 ore in their inventory afterwards.
	  */
	 @Test
	 public void testCannotSellMoreOreThanHaveInInventoryAfterSale(){
		// 1 ore left in the market
		marketInventory.decreaseOreQuantity(market.getOreQuantity());
		marketInventory.increaseOreQuantity(1);
			
		// 4 ore left in player's inventory
		playerInventory.decreaseOreQuantity(player.getOreQuantity());
		playerInventory.increaseOreQuantity(4);
		
		resourceMarket = new ResourceMarket(player);
		
		setSpinBoxValue(1,sellOreSpinBox);
		clickActor(sellOreButton); // Sale made
		setSpinBoxValue(19,sellOreSpinBox); // Initially 15 ore in stock see setup method
		assertEquals(3,sellOreSpinBox.getValue()); // Not able to go beyond 3
	 }
	 
	 /**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 0 the quantity of ore in the Players inventory is not increased
	 */
	@Test
	public void testClickBuyOreButtonZeroOrePlayerNotBuyOre(){
		int oreBefore = playerInventory.getOreQuantity();
		setSpinBoxValue(0,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}

	
	/**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 1 the quantity of ore in the Players inventory is increased by 1
	 * (if the player has enough money)
	 */
	@Test
	public void testClickBuyOreButtonOneOreBuyOre(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		int oreBefore = playerInventory.getOreQuantity();
		setSpinBoxValue(1,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(oreBefore+1,playerInventory.getOreQuantity());
	}
	
	/**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 5 the quantity of ore in the Players inventory is increased by 5
	 * (if the player has enough money)
	 */
	@Test
	public void testClickBuyOreButtonFiveOreBuyOre(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(5));
		int oreBefore = playerInventory.getOreQuantity();
		setSpinBoxValue(5,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(oreBefore+5,playerInventory.getOreQuantity());
	}	
	
	/**
	 * Ensures that when the buy ore button is clicked and the buy ore SpinBox has a value of 1, 
	 * the player has enough money to buy the ore and the market has 1 ore
	 * the value stored in the label that displays how much ore the market has is updated
	 */
	 @Test
	 public void testClickBuyOreButtonOreStockUpdatedOne(){
		 int valueBefore = Integer.parseInt(oreInStockLabel.getText().toString());
		 playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		 setSpinBoxValue(1,buyOreSpinBox);
		 clickActor(buyOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),valueBefore-1);
	 }
	 
	 /**
	 * Ensures that when the buy ore button is clicked and the buy ore SpinBox has a value of 2, 
	 * the player has enough money to buy the ore and the market has 2 ore
	 * the value stored in the label that displays how much ore the market has is updated
	 */
	 @Test
	 public void testClickBuyOreButtonOreStockUpdatedTwo(){
		 int valueBefore = Integer.parseInt(oreInStockLabel.getText().toString());
		 playerInventory.increaseMoneyQuantity(market.getCostOre(2));
		 setSpinBoxValue(2,buyOreSpinBox);
		 clickActor(buyOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),valueBefore-2);
	 }
	 
	 /**
	 * Ensures that when the buy ore button is clicked and the buy ore SpinBox has a value of 0, 
	 * the value stored in the label that displays how much ore the market has is not changed
	 */
	 @Test
	 public void testClickBuyOreButtonOreStockUpdatedZero(){
		 int valueBefore = Integer.parseInt(oreInStockLabel.getText().toString());
		 setSpinBoxValue(0,buyOreSpinBox);
		 clickActor(buyOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),valueBefore);
	 }	 
	 
	 /**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 0 the quantity of ore in the Players inventory is not decreased
	 */
	@Test
	public void testClickSellOreButtonZeroOrePlayerNotSellOre(){
		int oreBefore = playerInventory.getOreQuantity();
		setSpinBoxValue(0,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(oreBefore,playerInventory.getOreQuantity());
	}

	
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 1 the quantity of ore in the Players inventory is decreased by 1
	 */
	@Test
	public void testClickSellOreButtonOneOreSellOre(){
		int oreBefore = playerInventory.getOreQuantity();
		setSpinBoxValue(1,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(oreBefore-1,playerInventory.getOreQuantity());
	}
	
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 5 the quantity of ore in the Players inventory is decreased by 5
	 */
	@Test
	public void testClickSellOreButtonFiveOreSellOre(){
		int oreBefore = playerInventory.getOreQuantity();
		setSpinBoxValue(5,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(oreBefore-5,playerInventory.getOreQuantity());
	}	
	
	
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore SpinBox has a value of 1
	 * and the player has 1 ore
	 * the value stored in the label that displays how much ore the market has is updated
	 */
	 @Test
	 public void testClickSellOreButtonOreStockUpdatedOne(){
		 int valueBefore = Integer.parseInt(oreInStockLabel.getText().toString());
		 playerInventory.increaseOreQuantity(1);
		 setSpinBoxValue(1,sellOreSpinBox);
		 clickActor(sellOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),valueBefore+1);
	 }
	 
	 /**
	 * Ensures that when the sell ore button is clicked and the sell ore SpinBox has a value of 2
	 * and the player has 2 ore
	 * the value stored in the label that displays how much ore the market has is updated
	 */
	 @Test
	 public void testClickSellOreButtonOreStockUpdatedTwo(){
		 int valueBefore = Integer.parseInt(oreInStockLabel.getText().toString());
		 playerInventory.increaseOreQuantity(2);
		 setSpinBoxValue(2,sellOreSpinBox);
		 clickActor(sellOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),valueBefore+2);
	 }
	 
	 /**
	 * Ensures that when the sell ore button is clicked and the sell ore SpinBox has a value of 0, 
	 * the value stored in the label that displays how much ore the market has is not changed
	 */
	 @Test
	 public void testClickSellOreButtonOreStockUpdatedZero(){
		 int valueBefore = Integer.parseInt(oreInStockLabel.getText().toString());
		 setSpinBoxValue(0,sellOreSpinBox);
		 clickActor(sellOreButton);
		 assertEquals(Integer.parseInt(oreInStockLabel.getText().toString()),valueBefore);
	 }	 
	 
	 /**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 1 the quantity of money in the players inventory is reduced by the correct amount
	 */
	@Test
	public void testClickBuyOreButtonOneOreBuyOreMoneyReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		int moneyBefore = playerInventory.getMoneyQuantity();
		setSpinBoxValue(1,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(moneyBefore-market.getCostOre(1),playerInventory.getMoneyQuantity());
	}
		
	/**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 5 the quantity of money in the players inventory is reduced by the correct amount
	 */
	@Test
	public void testClickBuyOreButtonFiveOreBuyOreMoneyReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(5));
		int moneyBefore = playerInventory.getMoneyQuantity();
		setSpinBoxValue(5,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(moneyBefore-market.getCostOre(5),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 1 the quantity of ore in the markets inventory is reduced by 1
	 */
	@Test
	public void testClickBuyOreButtonOneOreBuyOreOreReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		int oreBefore = marketInventory.getOreQuantity();
		setSpinBoxValue(1,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(oreBefore-1,marketInventory.getOreQuantity());
	}
	
	/**
	 * Ensures that when the buy ore button is clicked and the buy ore quantity SpinBox is
	 * set to 5 the quantity of ore in the markets inventory is reduced by 5
	 */
	@Test
	public void testClickBuyOreButtonFiveOreBuyOreOreReduced(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(5));
		int oreBefore = marketInventory.getOreQuantity();
		setSpinBoxValue(5,buyOreSpinBox);
		clickActor(buyOreButton);
		assertEquals(oreBefore-5,marketInventory.getOreQuantity());
	}
	
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 1 the quantity of money in the players inventory is increased by the correct amount
	 */
	@Test
	public void testClickSellOreButtonOneOreSellOreMoneyIncreased(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		int moneyBefore = playerInventory.getMoneyQuantity();
		setSpinBoxValue(1,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(moneyBefore+market.getCostOre(1),playerInventory.getMoneyQuantity());
	}
		
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 5 the quantity of money in the players inventory is increased by the correct amount
	 */
	@Test
	public void testClickSellOreButtonFiveOreSellOreMoneyIncreased(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(5));
		int moneyBefore = playerInventory.getMoneyQuantity();
		setSpinBoxValue(5,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(moneyBefore+market.getCostOre(5),playerInventory.getMoneyQuantity());
	}
	
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 1 the quantity of ore in the markets inventory is increased by 1
	 */
	@Test
	public void testClickSellOreButtonOneOreSellOreOreIncreased(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(1));
		int oreBefore = marketInventory.getOreQuantity();
		setSpinBoxValue(1,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(oreBefore+1,marketInventory.getOreQuantity());
	}
	
	/**
	 * Ensures that when the sell ore button is clicked and the sell ore quantity SpinBox is
	 * set to 5 the quantity of ore in the markets inventory is increased by 5
	 */
	@Test
	public void testClickSellOreButtonFiveOreSellOreOreIncreased(){
		playerInventory.increaseMoneyQuantity(market.getCostOre(5));
		int oreBefore = marketInventory.getOreQuantity();
		setSpinBoxValue(5,sellOreSpinBox);
		clickActor(sellOreButton);
		assertEquals(oreBefore+5,marketInventory.getOreQuantity());
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 5 the quantity of energy in the markets inventory is increased by 5
	 */
	@Test
	public void testClickSellEnergyButtonOneEnergySellEnergyEnergyIncreased(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(1));
		int energyBefore = marketInventory.getEnergyQuantity();
		setSpinBoxValue(1,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		assertEquals(energyBefore+1,marketInventory.getEnergyQuantity());
	}
	
	/**
	 * Ensures that when the sell energy button is clicked and the sell energy quantity SpinBox is
	 * set to 5 the quantity of energy in the markets inventory is increased by 5
	 */
	@Test
	public void testClickSellEnergyButtonFiveEnergySellEnergyEnergyIncreased(){
		playerInventory.increaseMoneyQuantity(market.getCostEnergy(5));
		int energyBefore = marketInventory.getEnergyQuantity();
		setSpinBoxValue(5,sellEnergySpinBox);
		clickActor(sellEnergyButton);
		assertEquals(energyBefore+5,marketInventory.getEnergyQuantity());
	}

}
