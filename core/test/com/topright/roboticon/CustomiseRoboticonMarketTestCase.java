package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;

import org.junit.Before;
import org.junit.Test;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * Test case for the CustomiseRoboticonsMarket class
 * <p>Unfortunately due to the design of the CustomiseRoboticonsClass, many of these tests are not true unit tests
 * as the class depends on a SpinBox object and MessagePopUp objects that cannot be mocked out, although it would not make sense
 * for us to inject this dependency into the class in this case (i.e. pass the SpinBox to the class 
 * via its constructor). So we feel justified that the design decisions we made were sensible (even if they make unit testing hard).
 * </p>
 * @author jcn509
 */
public class CustomiseRoboticonMarketTestCase extends GuiTest { 
	@Mocked Market market;
	@Mocked Player player;
	@Mocked PlayerInventory playerInventory;
	@Mocked MarketInventory marketInventory;
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
	 * Ensures that when the energy customisation button is clicked and the player is able to produce an energy roboticon
	 * The attemptToProduce roboticon method from the player class is called with an input value of 
	 * RoboticonCustomisation.ENERGY
	 */
	@Test
	public void testClickEnergyCustomisationButtonOneEnergyRoboticonProduced(){
		new Expectations(){{
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);result=true;
		}};
		clickActor(energyCustomisationButton);
	}
	
	/**
	 * Ensures that when the energy customisation button is clicked and the player is able to produce an energy roboticon
	 * The attemptToProduce roboticon method from the player class is not called just once with an input value of 
	 * RoboticonCustomisation.ORE
	 */
	@Test
	public void testClickEnergyCustomisationButtonNoOreRoboticonProduced(){
		new Expectations(){{
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);result=true;
		}};
		clickActor(energyCustomisationButton);
		new Verifications(){{
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);times=0;
		}};
	}
	
	/**
	 * Ensures that when the ore customisation button is clicked and the player is able to produce an ore roboticon
	 * The attemptToProduce roboticon method from the player class is called with an input value of 
	 * RoboticonCustomisation.ORE
	 */
	@Test
	public void testClickOreCustomisationButtonOneOreRoboticonProduced(){
		new Expectations(){{
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);result=true;
		}};
		clickActor(oreCustomisationButton);
	}
	
	/**
	 * Ensures that when the ore customisation button is clicked and the player is able to produce an ore roboticon
	 * The attemptToProduce roboticon method from the player class is not called just once with an input value of 
	 * RoboticonCustomisation.ENERGY
	 */
	@Test
	public void testClickOreCustomisationButtonNoEnergyRoboticonProduced(){
		new Expectations(){{
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ORE);result=true;
		}};
		clickActor(oreCustomisationButton);
		new Verifications(){{
			player.attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY);times=0;
		}};
	}
}
