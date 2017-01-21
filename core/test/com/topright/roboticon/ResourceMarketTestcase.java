package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import mockit.Mocked;

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
	private TextButton buyOreButton;
	private TextButton sellOreButton;
	private TextButton buyEnergyButton;
	private TextButton sellEnergyButton;
	private SpinBox buyEnergySpinBox;
	private SpinBox buyOreSpinBox;
	private SpinBox sellEnergySpinBox;
	private SpinBox sellOreSpinBox;
	
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
		
		marketInventory = new MarketInventory(0,0,0);
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
	}
	
	/**
	 * A helper method used to set the value of a SpinBox to a given value
	 * @param value The value that the SpinBox should hold
	 * @param spinBox The spinBox whose value is to be set
	 */
	private void setSpinBoxValue(int value,SpinBox spinBox){
		while(spinBox.getValue()<value){
			clickActor(spinBox.getIncreaseQuantityButton());
		}
		while(spinBox.getValue()>value){
			clickActor(spinBox.getDecreaseQuantityButton());
		}
	}
	
	@Test
	public void testClickBuyEnergyButtonZeroOre(){
		setSpinBoxValue(0,buyEnergySpinBox);
	}
}
