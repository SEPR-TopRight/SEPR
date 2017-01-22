package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;

/**
 * Test case for the MenuBar class
 * @author jcn509
 *
 */
public class MenuBarIntergrationTests extends GuiTest { 
	private MenuBar menuBar;
	Player player;
	PlayerInventory playerInventory;
	MessageDispatcher messageDispatcher;
	
	
	/**
	 * Runs before every test, creates the required MenuBar and (mocked) Player and PlayerInventory objects
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		playerInventory = new PlayerInventory(0,0,roboticonQuantities,0);
		
		player = new Player(playerInventory);
		
		menuBar = new MenuBar();
		stage.addActor(menuBar);
		
		messageDispatcher = MessageManager.getInstance();
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreOne(){
		playerInventory.increaseOreQuantity(1); // 0 ore initially stored
		menuBar.setPlayerInventoryData(player);
		assertEquals(1,Integer.parseInt(menuBar.getOreQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyTwo(){
		playerInventory.increaseEnergyQuantity(2); // 0 energy initially stored
		menuBar.setPlayerInventoryData(player);
		assertEquals(2,Integer.parseInt(menuBar.getEnergyQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct uncustomised roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataUncustomsiedRoboticonsThree(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,3); // 0 uncustomised roboticons stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(3,Integer.parseInt(menuBar.getUncustomisedRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreRoboticonsFour(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,4); // 0 ore roboticons stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(4,Integer.parseInt(menuBar.getOreRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyRoboticonsFive(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,5); // 0 energy roboticons stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(5,Integer.parseInt(menuBar.getEnergyRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct money value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataMoneySix(){
		playerInventory.increaseMoneyQuantity(6); // 0 money stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(6,Integer.parseInt(menuBar.getMoneyQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreTwo(){
		playerInventory.increaseOreQuantity(2); // 0 ore initially stored
		menuBar.setPlayerInventoryData(player);
		assertEquals(2,Integer.parseInt(menuBar.getOreQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyThree(){
		playerInventory.increaseEnergyQuantity(3); // 0 energy initially stored
		menuBar.setPlayerInventoryData(player);
		assertEquals(3,Integer.parseInt(menuBar.getEnergyQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct uncustomised roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataUncustomsiedRoboticonsFour(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,5); // 0 uncustomised roboticons stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(5,Integer.parseInt(menuBar.getUncustomisedRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreRoboticonsFive(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,5); // 0 ore roboticons stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(5,Integer.parseInt(menuBar.getOreRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyRoboticonsSix(){
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,6); // 0 energy roboticons stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(6,Integer.parseInt(menuBar.getEnergyRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct money value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataMoneySeven(){
		playerInventory.increaseMoneyQuantity(7); // 0 money stored initially
		menuBar.setPlayerInventoryData(player);
		assertEquals(7,Integer.parseInt(menuBar.getMoneyQuantityLabel().getText().toString()));
	}
	
}
