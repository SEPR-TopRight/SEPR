package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.scenes.scene2d.ui.TextButton;


/**
 * Integration tests for the {@link CustomiseRoboticonsMarket}, {@link Player} and 
 * {@link PlayerInventory}, {@link Market} and {@link MarketInventory} classes
 * @author jcn509
 */
public class CustomiseRoboticonMarketIntegrationTests extends GuiTest { 
	Market market;
	Player player;
	PlayerInventory playerInventory;
	MarketInventory marketInventory;
	private TextButton energyCustomisationButton;
	private TextButton oreCustomisationButton;
	private CustomiseRoboticonsMarket customiseRoboticonsMarket;
	
	/**
	 * Runs before every test creating the customiseRoboticonsMarket object as well as the
	 * mocked Player, PlayerInventory, Market and MarketInventory objects and extracting the customisation buttons
	 * from the customiseRoboticonMarket object
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.UNCUSTOMISED, 0); // Start with no uncustomised roboticons
		playerInventory = new PlayerInventory(0,1,roboticonQuantities, 2);
		player = new Player(playerInventory);
		market = Market.getInstance();
		marketInventory = new MarketInventory(1,2,3);
		market.setInventory(marketInventory);
		
		customiseRoboticonsMarket = new CustomiseRoboticonsMarket(player);
		
		energyCustomisationButton = customiseRoboticonsMarket.getEnergyCustomisationButton();
		oreCustomisationButton = customiseRoboticonsMarket.getOreCustomisationButton();
		stage.addActor(customiseRoboticonsMarket);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player has
	 * an uncustomised roboticon and enough money that 1 energy roboticon is added to the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonOneEnergyRoboticonAdded(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.increaseMoneyQuantity(customisationCost); // Enough money
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		clickActor(energyCustomisationButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		assertEquals(roboticonsBefore+1,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player has
	 * an uncustomised roboticon and enough money that 1 uncustomised roboticon is removed from the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonUncustomisedRoboticonRemoved(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.increaseMoneyQuantity(customisationCost); // Enough money
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		clickActor(energyCustomisationButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore-1,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player has
	 * an uncustomised roboticon and enough money that the correct quantity of money is 
	 * removed from the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonMoneyRemoved(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.increaseMoneyQuantity(customisationCost); // Enough money
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int moneyBefore = playerInventory.getMoneyQuantity();
		clickActor(energyCustomisationButton);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore-customisationCost,moneyAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player does 
	 * not have enough money no energy roboticons are added to the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonEnergyRoboticonAddedNotEnoughMoney(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // No money
		playerInventory.increaseMoneyQuantity(customisationCost-1); // Not enough money
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		clickActor(energyCustomisationButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player does 
	 * not have enough money no uncustomised roboticons are removed from the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonUncustomisedRoboticonRemovedNotEnoughMoney(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // No money
		playerInventory.increaseMoneyQuantity(customisationCost-1); // Not enough money
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		clickActor(energyCustomisationButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player does 
	 * not have enough money that no money is removed from the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonMoneyRemovedNotEnoughMoney(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // No money
		playerInventory.increaseMoneyQuantity(customisationCost-1); // Not enough money
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1);
		int moneyBefore = playerInventory.getMoneyQuantity();
		clickActor(energyCustomisationButton);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player does 
	 * not have any uncustomised roboticons no energy roboticons are added to the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonEnergyRoboticonAddedNoUncustomisedRoboticons(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.increaseMoneyQuantity(customisationCost); // Enough money

		// Left with no uncustomised roboticons
		int numberOfRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,numberOfRoboticonsInInventory);

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		clickActor(energyCustomisationButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player does 
	 * not have any uncustomised roboticons no uncustomised roboticons are removed from the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonUncustomisedRoboticonRemovedNoUncustomisedRoboticons(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.increaseMoneyQuantity(customisationCost); // Enough money
		
		// Left with no uncustomised roboticons
		int numberOfRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,numberOfRoboticonsInInventory);

		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		clickActor(energyCustomisationButton);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player does 
	 * not have any uncustomised roboticons that no money is removed from the players inventory
	 */
	@Test
	public void testClickEnergyCustomisationButtonMoneyRemovedNoUncustomisedRoboticons(){
		int customisationCost = market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);
		playerInventory.increaseMoneyQuantity(customisationCost); // Enough money
		
		// Left with no uncustomised roboticons
		int numberOfRoboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,numberOfRoboticonsInInventory);

		int moneyBefore = playerInventory.getMoneyQuantity();
		clickActor(energyCustomisationButton);
		int moneyAfter = playerInventory.getMoneyQuantity();
		assertEquals(moneyBefore,moneyAfter);
	}
	
}
