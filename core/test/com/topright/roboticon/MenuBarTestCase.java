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
public class MenuBarTestCase extends GuiTest { 
	private MenuBar menuBar;
	@Mocked Player player;
	@Mocked PlayerInventory playerInventory;
	@Mocked MessageDispatcher messageDispatcher;
	
	
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
		new Expectations(){{
			player.getOreQuantity();result=1;
			player.getEnergyQuantity();result=2;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=5;
			player.getMoneyQuantity();result=6;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(1,Integer.parseInt(menuBar.getOreQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyTwo(){
		new Expectations(){{
			player.getOreQuantity();result=1;
			player.getEnergyQuantity();result=2;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=5;
			player.getMoneyQuantity();result=6;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(2,Integer.parseInt(menuBar.getEnergyQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct uncustomised roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataUncustomsiedRoboticonsThree(){
		new Expectations(){{
			player.getOreQuantity();result=1;
			player.getEnergyQuantity();result=2;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=5;
			player.getMoneyQuantity();result=6;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(3,Integer.parseInt(menuBar.getUncustomisedRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreRoboticonsFour(){
		new Expectations(){{
			player.getOreQuantity();result=1;
			player.getEnergyQuantity();result=2;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=5;
			player.getMoneyQuantity();result=6;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(4,Integer.parseInt(menuBar.getOreRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyRoboticonsFive(){
		new Expectations(){{
			player.getOreQuantity();result=1;
			player.getEnergyQuantity();result=2;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=5;
			player.getMoneyQuantity();result=6;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(5,Integer.parseInt(menuBar.getEnergyRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct money value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataMoneySix(){
		new Expectations(){{
			player.getOreQuantity();result=1;
			player.getEnergyQuantity();result=2;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=5;
			player.getMoneyQuantity();result=6;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(6,Integer.parseInt(menuBar.getMoneyQuantityLabel().getText().toString()));
	}
	

	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreTwo(){
		new Expectations(){{
			player.getOreQuantity();result=2;
			player.getEnergyQuantity();result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=5;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=6;
			player.getMoneyQuantity();result=7;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(2,Integer.parseInt(menuBar.getOreQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyThree(){
		new Expectations(){{
			player.getOreQuantity();result=2;
			player.getEnergyQuantity();result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=5;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=6;
			player.getMoneyQuantity();result=7;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(3,Integer.parseInt(menuBar.getEnergyQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct uncustomised roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataUncustomsiedRoboticonsFour(){
		new Expectations(){{
			player.getOreQuantity();result=2;
			player.getEnergyQuantity();result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=5;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=6;
			player.getMoneyQuantity();result=7;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(4,Integer.parseInt(menuBar.getUncustomisedRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct ore roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataOreRoboticonsFive(){
		new Expectations(){{
			player.getOreQuantity();result=2;
			player.getEnergyQuantity();result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=5;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=6;
			player.getMoneyQuantity();result=7;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(5,Integer.parseInt(menuBar.getOreRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct energy roboticon value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataEnergyRoboticonsSix(){
		new Expectations(){{
			player.getOreQuantity();result=2;
			player.getEnergyQuantity();result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=5;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=6;
			player.getMoneyQuantity();result=7;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(6,Integer.parseInt(menuBar.getEnergyRoboticonQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setPlayerInventoryData(Player)} ensures the correct money value is displayed
	 */
	@Test
	public void testUpdatePlayerInventoryDataMoneySeven(){
		new Expectations(){{
			player.getOreQuantity();result=2;
			player.getEnergyQuantity();result=3;
			player.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=4;
			player.getRoboticonQuantity(RoboticonCustomisation.ORE);result=5;
			player.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=6;
			player.getMoneyQuantity();result=7;
		}};
		menuBar.setPlayerInventoryData(player);
		assertEquals(7,Integer.parseInt(menuBar.getMoneyQuantityLabel().getText().toString()));
	}
	
	/**
	 * Tests {@link MenuBar#setMenuText(String)} ensures that the correct string is displayed ("test1")
	 */
	@Test
	public void testSetMenuTestTest1(){
		menuBar.setMenuText("test1");
		assertEquals("test1",menuBar.getMenuLabel().getText().toString());
	}
	
	/**
	 * Tests {@link MenuBar#setMenuText(String)} ensures that the correct string is displayed ("Test 2")
	 */
	@Test
	public void testSetMenuTestTest2(){
		menuBar.setMenuText("Test 2");
		assertEquals("Test 2",menuBar.getMenuLabel().getText().toString());
	}
	
	/**
	 * Ensures that the next stage button is hidden by default
	 */
	@Test
	public void testNextStageButtonHidden(){
		
		// The button will not be have a parent if it is not displayed
		assertEquals(menuBar.getNextStageButton().getParent(),null);
	}
	
	/**
	 * Tests {@link MenuBar#setAndShowNextStageButton(String, int)} ensures that the next stage button is shown once setAndShowNextStageButton is called
	 */
	@Test
	public void testNextShowAndSetStageButtonShow(){
	
		// The button will have menuBar as its parent if it is displayed on the menuBar
		menuBar.setAndShowNextStageButton("test", 0);
		assertEquals(menuBar.getNextStageButton().getParent(),menuBar);
	}
	
	/**
	 * Tests {@link MenuBar#setAndShowNextStageButton(String, int)} ensures that the next stage button displays the correct text ("test1") after setAndShowNextStageButton is called
	 */
	@Test
	public void testNextShowAndSetStageButtonCorrectTextTest1(){
	
		// The button will have menuBar as its parent if it is displayed on the menuBar
		menuBar.setAndShowNextStageButton("test1", 0);
		assertEquals(menuBar.getNextStageButton().getText().toString(),"test1");
	}
	
	/**
	 * Tests {@link MenuBar#setAndShowNextStageButton(String, int)} ensures that the next stage button displays the correct text ("Test 2") after setAndShowNextStageButton is called
	 */
	@Test
	public void testNextShowAndSetStageButtonCorrectTextTest2(){
	
		// The button will have menuBar as its parent if it is displayed on the menuBar
		menuBar.setAndShowNextStageButton("Test 2", 0);
		assertEquals(menuBar.getNextStageButton().getText().toString(),"Test 2");
	}
	
	/**
	 * Tests {@link MenuBar#setAndShowNextStageButton(String, int)} ensures that the correct message (0) is dispatched when the next stage button is clicked 
	 * after setAndShowNextStageButton is called
	 */
	@Test
	public void testNextShowAndSetStageButtonCorrectMessagePassedWhenClickedZero(){
	
		// The button will have menuBar as its parent if it is displayed on the menuBar
		menuBar.setAndShowNextStageButton("Test 2", 0);
		clickActor(menuBar.getNextStageButton());
		new Verifications(){{
			messageDispatcher.dispatchMessage(0);
		}};
	}
	
	/**
	 * Tests {@link MenuBar#setAndShowNextStageButton(String, int)} ensures that the correct message (9) is dispatched when the next stage button is clicked 
	 * after setAndShowNextStageButton is called
	 */
	@Test
	public void testNextShowAndSetStageButtonCorrectMessagePassedWhenClickedNine(){
	
		// The button will have menuBar as its parent if it is displayed on the menuBar
		menuBar.setAndShowNextStageButton("Test 2", 9);
		clickActor(menuBar.getNextStageButton());
		new Verifications(){{
			messageDispatcher.dispatchMessage(9);
		}};
	}
	
	/**
	 * Tests {@link MenuBar#hideNextStageButton()} ensures that the next stage button is hidden (if already shown) when the hideNextStageButton method is called
	 */
	@Test
	public void testHideNextStageButtonHidden(){
		menuBar.setAndShowNextStageButton("test", 0); // Show the button
		menuBar.hideNextStageButton();
		
		// If the button is not on screen (hidden) it will not have a parent
		assertEquals(menuBar.getNextStageButton().getParent(),null);
	}
	
	/**
	 * Tests {@link MenuBar#setTimer(int, int)} ensures that the timer label is displayed and updated/reduced every second
	 * @throws InterruptedException 
	 */
	@Test
	public void testSetTimerLabelDisplayedThree() throws InterruptedException{
		menuBar.setMenuText("test");
		int timeLeft =3; // The time to pause for
		menuBar.setTimer(0,timeLeft);
		TimeUnit.SECONDS.sleep(1); // Wait 1 second to ensure that the timer label is displayed
		
		while(timeLeft>1){
			timeLeft--;
			
			String menuLabelText = menuBar.getMenuLabel().getText().toString();
			
			// The timer label should be the last thing displayed
			String lastWordInMenuText = menuLabelText.replaceAll(".* ", "");
			String timeString = "time left: "+lastWordInMenuText;
			
			assertTrue(menuLabelText.toLowerCase().contains(timeString));
			
			TimeUnit.SECONDS.sleep(1);
		}
		

	}
	
	/**
	 * Tests {@link MenuBar#setTimer(int, int)} ensures that the timer label is displayed and updated/reduced every second for four seconds
	 * @throws InterruptedException 
	 */
	@Test
	public void testSetTimerLabelDisplayedFour() throws InterruptedException{
		menuBar.setMenuText("test");
		int timeLeft = 4; // The time to pause for
		menuBar.setTimer(0,timeLeft);
		TimeUnit.SECONDS.sleep(1); // Wait 1 second to ensure that the timer label is displayed
		
		while(timeLeft>1){
			timeLeft--;
			
			String menuLabelText = menuBar.getMenuLabel().getText().toString();
			
			// The timer label should be the last thing displayed
			String lastWordInMenuText = menuLabelText.replaceAll(".* ", "");
			String timeString = "time left: "+lastWordInMenuText;
			
			assertTrue(menuLabelText.toLowerCase().contains(timeString));
			
			TimeUnit.SECONDS.sleep(1);
		}
		

	}
	
	/**
	 * Tests {@link MenuBar#setTimer(int, int)} enures that the message to pass when the timer 
	 * runs out is not passed before the timer reaches 0
	 * @throws InterruptedException 
	 */
	@Test
	public void testSetTimerLabelMessageNotPassedBeforeFinishedZero() throws InterruptedException{
		menuBar.setMenuText("test");
		int timeLeft = 4; // The time to pause for
		menuBar.setTimer(0,timeLeft);
		TimeUnit.SECONDS.sleep(1); 
		
		while(timeLeft>1){
			timeLeft--;
			
			new Verifications(){{
				messageDispatcher.dispatchMessage(anyInt);times=0;
			}};
			TimeUnit.SECONDS.sleep(1);
		}
		

	}
	
	/**
	 * Tests {@link MenuBar#setTimer(int, int)} ensures that the correct message is passed 
	 * when the timer runs out is passed when the timer reaches 0
	 * @throws InterruptedException 
	 */
	@Test
	public void testSetTimerLabelMessagePassedWhenTimerRunsOut() throws InterruptedException{
		menuBar.setMenuText("test");
		int time = 4; // The time to pause for
		menuBar.setTimer(0,time);
		TimeUnit.SECONDS.sleep(time); 
		
		new Verifications(){{
			messageDispatcher.dispatchMessage(0);times=1;
		}};
	}
	
	/**
	 * Tests {@link MenuBar#setTimer(int, int)} ensures that the correct message is passed 
	 * when the timer runs out is passed when the timer reaches 0
	 * @throws InterruptedException 
	 */
	@Test
	public void testSetTimerLabelMessagePassedWhenTimerRunsOut2() throws InterruptedException{
		menuBar.setMenuText("test");
		int time = 3; // The time to pause for
		menuBar.setTimer(2,time);
		TimeUnit.SECONDS.sleep(time);
		
		new Verifications(){{
			messageDispatcher.dispatchMessage(2);times=1;
		}};
	}
	
	/**
	 * Tests {@link MenuBar#clearTimer()} ensures that the message that was to be passed when the
	 * timer runs out is not passed when the timer would have run out after the timer has been cleared
	 * @throws InterruptedException 
	 */
	@Test
	public void testClearTimerMessageNotDispatchedWhenTimerRunsOut() throws InterruptedException{
		menuBar.setMenuText("test");
		int timeLeft = 4; // The time to pause for
		menuBar.setTimer(0,timeLeft);
		menuBar.clearTimer();
		TimeUnit.SECONDS.sleep(1); 
		
		while(timeLeft>1){
			timeLeft--;		
			
			TimeUnit.SECONDS.sleep(1);
		}
		new Verifications(){{
			messageDispatcher.dispatchMessage(anyInt);times=0; 
			// Message that was supposed to be dispatched when the timer ran out is not
		}};
	}
	
	/**
	 * Tests {@link MenuBar#clearTimer()} ensures that the message that was to be passed when the
	 * timer runs out is not passed when the timer is cleared
	 */
	@Test
	public void testClearTimerMessageNotDispatchedInstantly(){		
		menuBar.setTimer(0,10);
		menuBar.clearTimer();
		new Verifications(){{
			messageDispatcher.dispatchMessage(anyInt);times=0; 
			// Message that was supposed to be dispatched when the timer ran out is not
		}};
	}
	
	/**
	 * Tests {@link MenuBar#clearTimer()} ensures the timer is not displayed on screen once cleared
	 * @throws InterruptedException 
	 */
	@Test
	public void testClearTimerNotDisplayed() throws InterruptedException{		
		menuBar.setMenuText("test");
		int timeLeft =3; // The time to pause for
		menuBar.setTimer(0,timeLeft);
		TimeUnit.SECONDS.sleep(1); // Wait 1 second to ensure that the timer label is displayed
		menuBar.clearTimer();
		
		String menuLabelText = menuBar.getMenuLabel().getText().toString();			
		assertFalse(menuLabelText.toLowerCase().contains("time left:"));

	}
	
	
}
