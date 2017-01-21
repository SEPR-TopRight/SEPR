package com.topright.roboticon;

import static org.junit.Assert.*;

import java.util.EnumMap;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.badlogic.gdx.ai.msg.MessageDispatcher;
import com.badlogic.gdx.ai.msg.MessageManager;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

/**
 * Test case for the PlotManger class]
 * <p>
 * Can't be certain that the background is correct via automated tests or that the images placed on the plot buttons
 * are correct (both the background/border images and the roboticon images).
 * </p>
 * <p>
 * Tests that involve opening a roboticon place menu are not true unit tests as the RoboticonPlaceMenus 
 * cannot be mocked out due to the design of the class. However it would be very odd to inject this dependency
 * (and would result in ugly strange code if its even possible) so we feel justified in this decision.
 * </p>
 * @author jcn509
 */
@RunWith(JMockit.class)
public class PlotManagerTestCase extends GuiTest { 
	private PlotManager plotManager;
	@Mocked private Player humanPlayer;
	@Mocked private Player AIPlayer;
	@Mocked private PlayerInventory humanPlayerInventory;
	@Mocked private PlayerInventory AIPlayerInventory;
	@Mocked private MessageDispatcher messageDispatcher;
	@Mocked private Plot[][] plots;
	@Mocked private Plot plot1;
	@Mocked private Plot plot2;
	@Mocked private Plot plot3;
	@Mocked private Plot plot4;
	@Mocked private Plot plot5;
	@Mocked private Plot plot6;
	private ButtonWithIcon[][] buttons;
	
	/**
	 * Runs before every test creates the required PlotManager object and all other required (mocked) objects.
	 */
	@Before
	public void setup(){
		messageDispatcher = MessageManager.getInstance();
		
		EnumMap<RoboticonCustomisation,Integer> humanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		humanPlayerInventory = new PlayerInventory(0,1,humanRoboticonQuantities, 2);
		humanPlayer = new Player(humanPlayerInventory);
		
		EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		AIPlayerInventory = new PlayerInventory(0,1,AIRoboticonQuantities, 2);
		AIPlayer = new Player(AIPlayerInventory);
		
		plots = CreatePlots.createPlots(4,5);
		plotManager = new PlotManager("backgrounds/map.png", plots, humanPlayer, AIPlayer);
		buttons = plotManager.getPlotButtons();
		stage.addActor(plotManager);
		plot1 = new Plot(PlotSpecialism.ORE);
		plot2 = new Plot(PlotSpecialism.ENERGY);
		plot3 = new Plot(PlotSpecialism.ORE);
		plot4 = new Plot(PlotSpecialism.ORE);
		plot5 = new Plot(PlotSpecialism.ORE);
		plot6 = new Plot(PlotSpecialism.ORE);
	}
	
	/**
	 * Tests {@link PlotManager#PlotManager(String, Plot[][], Player, Player)} ensures that the button
	 * grid (that the player uses to interact with the plots) contains the same number
	 * of rows as they plot array that is passed to it when the number of rows (in the plot array) is the same
	 * as the number of columns.
	 */
	@Test
	public void testCorrectRowsButtonGridCreatedEqualRowsAndColumns(){
		plots = CreatePlots.createPlots(4, 4);
		plotManager = new PlotManager("backgrounds/map.png", plots, humanPlayer, AIPlayer);
		assertEquals(4, plotManager.getPlotButtons().length);
	}
	
	/**
	 * Tests {@link PlotManager#PlotManager(String, Plot[][], Player, Player)} ensures that the button
	 * grid (that the player uses to interact with the plots) contains the same number
	 * of columns as they plot array that is passed to it when the number of rows (in the plot array) is the same
	 * as the number of columns.
	 */
	@Test
	public void testCorrectColumnsButtonGridCreatedEqualRowsAndColumns(){
		plots = CreatePlots.createPlots(4, 4);
		plotManager = new PlotManager("backgrounds/map.png", plots, humanPlayer, AIPlayer);
		assertEquals(4, plotManager.getPlotButtons()[0].length);
	}
	
	/**
	 * Tests {@link PlotManager#PlotManager(String, Plot[][], Player, Player)} ensures that the button
	 * grid (that the player uses to interact with the plots) contains the same number
	 * of rows as they plot array that is passed to it when the number of rows (in the plot array) is not the same
	 * as the number of columns.
	 */
	@Test
	public void testCorrectRowsButtonGridCreatedUnequalRowsAndColumns(){
		plots = CreatePlots.createPlots(3, 5);
		plotManager = new PlotManager("backgrounds/map.png", plots, humanPlayer, AIPlayer);
		assertEquals(3, plotManager.getPlotButtons().length);
	}
	
	/**
	 * Tests {@link PlotManager#PlotManager(String, Plot[][], Player, Player)} ensures that the button
	 * grid (that the player uses to interact with the plots) contains the same number
	 * of columns as they plot array that is passed to it when the number of rows (in the plot array) is not the same
	 * as the number of columns.
	 */
	@Test
	public void testCorrectColumnsButtonGridCreatedUnequalRowsAndColumns(){
		plots = CreatePlots.createPlots(3, 5);
		plotManager = new PlotManager("backgrounds/map.png", plots, humanPlayer, AIPlayer);
		assertEquals(5, plotManager.getPlotButtons()[0].length);
	}
	
	/**
	 * Ensures that when the plot in row 0 column 1 is clicked and the plot click mode 
	 * is set to acquire that plot is acquired by the current player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickAcquiredHuman(){
		new Expectations(){{
			plots[0][1].setPlayer(humanPlayer);
		}};
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[0][1]);
		
	}
	
	/**
	 * Ensures that when the plot in row 3 column 3 is clicked and the plot click mode 
	 * is set to acquire that plot is acquired by the current player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickAcquiredHumanTwo(){
		new Expectations(){{
			plots[3][3].setPlayer(humanPlayer);
		}};
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[3][3]);
	}
	
	/**
	 * Ensures that when the plot in row 0 column 1 is clicked and the plot click mode 
	 * is set to acquire that no other plots are acquired by the human player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredHuman(){
		Plot[][] plots = {{plot1,plot2,plot2},{plot2,plot2,plot2}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		buttons = plotManager.getPlotButtons();
		
		clickActor(buttons[0][0]);
		new Verifications(){{
			plot2.setPlayer(humanPlayer);times=0;
		}};
	}
	

	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode 
	 * is set to acquire that no other plots are acquired by the human player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredHuman2(){
		Plot[][] plots = {{plot2,plot2,plot2},{plot2,plot2,plot1},{plot2,plot2,plot2}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		buttons = plotManager.getPlotButtons();
		
		clickActor(buttons[1][2]);
		new Verifications(){{
			plot2.setPlayer(humanPlayer);times=0;
		}};
	}
	
	/**
	 * Ensures that when the plot in row 0 column 0 is clicked and the plot click mode 
	 * is set to acquire that no plots are acquired by the AI player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredAI(){
		Plot[][] plots = {{plot2,plot2,plot2},{plot2,plot2,plot1},{plot2,plot2,plot2}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		buttons = plotManager.getPlotButtons();
		
		clickActor(buttons[0][0]);
		new Verifications(){{
			plot1.setPlayer(AIPlayer);times=0;
			plot2.setPlayer(AIPlayer);times=0;
		}};
	}
	
	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode 
	 * is set to acquire that no plots are acquired by the AI player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredAI2(){
		Plot[][] plots = {{plot2,plot2,plot2},{plot2,plot2,plot1},{plot2,plot2,plot2}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		buttons = plotManager.getPlotButtons();
		
		clickActor(buttons[1][2]);
		new Verifications(){{
			plot1.setPlayer(AIPlayer);times=0;
			plot2.setPlayer(AIPlayer);times=0;
		}};
	}
	
	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode is set to
	 * place roboticon and the plot does not belong to the human player, that no roboticon place menus are opened
	 * (when the current player is the human player, no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonsPlotNotOwnedByHuman(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			plots[1][2].getPlayer(); result = AIPlayer;
		}};
		clickActor(buttons[1][2]);
		Array<Actor> plotManagerChildren = plotManager.getChildren();
	
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only care about children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		fail(); // Menu opened! Test fails 
	        }
	    }
		// If this point has been reached then the test must have passed
	}
	

	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode is set to
	 * place roboticon, the plot belongs to the human player that one roboticon place menu is opened
	 * (when the current player is the human player, no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonsPlotOwnedByHumanNoRoboticon(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.getRoboticonQuantity(RoboticonCustomisation.ORE);result=2;
			plots[1][2].getPlayer();result=humanPlayer;
		}};
		clickActor(buttons[1][2]);
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		
		int numberOfRoboticonPlaceMenus = 0;
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		numberOfRoboticonPlaceMenus++; // Menu opened!
	        }
	    }
		if(numberOfRoboticonPlaceMenus == 1){
			return; // Test passed
		}
		else{
			fail();
		}
	}
	
	/**
	 * Ensures that when a roboticon place menu is opened and then another plot is clicked that the old menu is closed
	 * (when the second plot belongs to the human player and the current player is the human player,
	 * there is no need to test this for AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonSecondPlotClickedOldMenuClosed(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.getRoboticonQuantity(RoboticonCustomisation.ORE);result=2;
			plots[1][2].getPlayer();result=humanPlayer;
			plots[2][3].getPlayer();result=humanPlayer;
		}};
		clickActor(buttons[1][2]);
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		
		RoboticonPlaceMenu roboticonPlaceMenu = null;
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		roboticonPlaceMenu = (RoboticonPlaceMenu) plotManagerChildren.get(child);
	        }
	    }
		clickActor(buttons[2][3]);
		assertEquals(roboticonPlaceMenu.getStage(),null); // Menu closed as it it not on any stage
	}
	
	/**
	 * Ensures that when a roboticon place menu is opened and then another plot is clicked that a new menu is opened
	 * (when the second plot belongs to the human player and the current player is the human player,
	 * there is no need to test this for AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonSecondPlotClickedNewMenu(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.getRoboticonQuantity(RoboticonCustomisation.ORE);result=2;
			plots[1][2].getPlayer();result=humanPlayer;
			plots[2][3].getPlayer();result=humanPlayer;
		}};
		clickActor(buttons[1][2]); // First plot clicked
		clickActor(buttons[2][3]); // Second plot clicked
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		return; // A new menu is opened so we can return as the test has passed
	        }
	    }
		fail(); // A new menu was not opened so we should fail
	}
	
	/**
	 * Ensures that when a roboticon place menu is opened and then another plot is clicked that the old menu is not closed
	 * when the second plot does not belong to the human player 
	 * (and the current player is the human player,
	 * there is no need to test this for AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonSecondPlotClickedOldMenuNotClosed(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.getRoboticonQuantity(RoboticonCustomisation.ORE);result=2;
			plots[1][2].getPlayer();result=humanPlayer;
			plots[2][3].getPlayer();result=AIPlayer;
		}};
		clickActor(buttons[1][2]);
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		
		RoboticonPlaceMenu roboticonPlaceMenu = null;
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		roboticonPlaceMenu = (RoboticonPlaceMenu) plotManagerChildren.get(child);
	        }
	    }
		clickActor(buttons[2][3]);
		assertEquals(roboticonPlaceMenu.getStage(),stage); // Old menu still open
	}
	
	/**
	 * Ensures that when a roboticon place menu is opened and then another plot is clicked that a new menu is not opened
	 * when the second plot does not belong to the human player 
	 * (and the current player is the human player,
	 * there is no need to test this for AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonSecondPlotClickedNoNewMenu(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.getRoboticonQuantity(RoboticonCustomisation.ORE);result=2;
			plots[1][2].getPlayer();result=humanPlayer;
			plots[2][3].getPlayer();result=AIPlayer;
		}};
		clickActor(buttons[1][2]); // First plot clicked
		
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		RoboticonPlaceMenu roboticonPlaceMenuBefore = null;
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		roboticonPlaceMenuBefore = (RoboticonPlaceMenu) plotManagerChildren.get(child);
	        }
	    }
		
		clickActor(buttons[2][3]);
		plotManagerChildren = plotManager.getChildren();
		RoboticonPlaceMenu roboticonPlaceMenuAfter = null;
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		roboticonPlaceMenuAfter = (RoboticonPlaceMenu) plotManagerChildren.get(child);
	        }
	    }
		assertNotEquals(roboticonPlaceMenuAfter,null); // To be certain that a menu is open
		assertEquals(roboticonPlaceMenuBefore,roboticonPlaceMenuAfter);
		
	}
	
	/**
	 * Tests {@link PlotManager#removeRoboticonPlaceMenu()} ensures that a open roboticon place menu is removed
	 * (when a roboticon place menu is open)
	 */
	@Test
	public void testRemoveRoboticonPlaceMenuOpen(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.getRoboticonQuantity(RoboticonCustomisation.ORE);result=2;
			plots[1][2].getPlayer();result=humanPlayer;
		}};
		clickActor(buttons[1][2]); // Plot clicked (menu opened)
		
		plotManager.removeRoboticonPlaceMenu();
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		fail(); // Menu still open, test failed
	        }
	    }
		
		// If this point has been reached then the menu has been closed and the test has passed
		
	}
	
	/**
	 * Tests {@link PlotManager#removeRoboticonPlaceMenu()} ensures that it does not throw an exception
	 * if a roboticon place menu is not open
	 */
	@Test
	public void testRemoveRoboticonPlaceMenuNotOpen(){
		plotManager.removeRoboticonPlaceMenu();
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an ore roboticon
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledHumanPlayer(){
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ORE);
		}};
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeOreRoboticon(2, 1); 
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 1 column 2 
	 * ensures that a player inventory update message is dispatched if the current player is the human player
	 */
	@Test
	public void testPlaceOreRoboticonMessageDispatchedHuman(){
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ORE);result=true; // Able to place it
		}};
		plotManager.placeOreRoboticon(2, 1); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an ore roboticon
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledHumanPlayer2(){
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ORE);
		}};
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeOreRoboticon(3, 3); 
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 3 column 3 
	 * ensures that a player inventory update message is dispatched if the current player is the human player
	 */
	@Test
	public void testPlaceOreRoboticonMessageDispatchedHuman2(){
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ORE);result=true; // Able to place it
		}};
		plotManager.placeOreRoboticon(3, 3); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an ore roboticon
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledAIPlayer(){
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ORE);
		}};
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeOreRoboticon(2, 1); 
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 1 column 2 
	 * ensures that a player inventory update message is not dispatched if the current player is the AI player
	 */
	@Test
	public void testPlaceOreRoboticonMessageDispatchedAI(){
		plotManager.setCurrentPlayer(AIPlayer);
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ORE);result=true; // Able to place it
		}};
		plotManager.placeOreRoboticon(2, 1); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); times=0;
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an ore roboticon
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledAIPlayer2(){
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ORE);
		}};
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeOreRoboticon(3, 3); 
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 3 column 3 
	 * ensures that a player inventory update message is dispatched if the current player is the AI player
	 */
	@Test
	public void testPlaceOreRoboticonMessageDispatchedAI2(){
		plotManager.setCurrentPlayer(AIPlayer);
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ORE);result=true; // Able to place it
		}};
		plotManager.placeOreRoboticon(3, 3); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0; // Not dispatched
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 1 column 2 
	 * ensures that the roboticon place menu is removed if the current player is the human player
	 * (there is no need to test this for the AI player is cannot click plots and therefore cannot
	 * open a roboticon place menu)
	 */
	@Test
	public void testPlaceOreRoboticonMenuClosedHumanPlayer(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[1][2]); // Open a roboticon place menu
		
		plotManager.placeOreRoboticon(2, 1); 
		
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		fail(); // Menu still open, test failed
	        }
	    }
		
		// If this point has been reached then the menu has been closed and the test has passed
		
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an energy roboticon
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledHumanPlayer(){
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ENERGY);
		}};
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeEnergyRoboticon(2, 1); 
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2 
	 * ensures that a player inventory update message is dispatched if the current player is the human player
	 */
	@Test
	public void testPlaceEnergyRoboticonMessageDispatchedHuman(){
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ENERGY);result=true; // Able to place it
		}};
		plotManager.placeEnergyRoboticon(2, 1); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an energy roboticon
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledHumanPlayer2(){
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ENERGY);
		}};
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeEnergyRoboticon(3, 3); 
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 3 column 3 
	 * ensures that a player inventory update message is dispatched if the current player is the human player
	 */
	@Test
	public void testPlaceEnergyRoboticonMessageDispatchedHuman2(){
		plotManager.setCurrentPlayer(humanPlayer);
		new Expectations(){{
			humanPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ENERGY);result=true; // Able to place it
		}};
		plotManager.placeEnergyRoboticon(3, 3); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an energy roboticon
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledAIPlayer(){
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ENERGY);
		}};
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeEnergyRoboticon(2, 1); 
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2 
	 * ensures that a player inventory update message is not dispatched if the current player is the AI player
	 */
	@Test
	public void testPlaceEnergyRoboticonMessageDispatchedAI(){
		plotManager.setCurrentPlayer(AIPlayer);
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[1][2],RoboticonCustomisation.ENERGY);result=true; // Able to place it
		}};
		plotManager.placeEnergyRoboticon(2, 1); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal()); times=0;
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that the attemptToPlaceRoboticon method from the player class is called to attempt to place an energy roboticon
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledAIPlayer2(){
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ENERGY);
		}};
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeEnergyRoboticon(3, 3); 
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 3 column 3 
	 * ensures that a player inventory update message is dispatched if the current player is the AI player
	 */
	@Test
	public void testPlaceEnergyRoboticonMessageDispatchedAI2(){
		plotManager.setCurrentPlayer(AIPlayer);
		new Expectations(){{
			AIPlayer.attemptToPlaceRoboticon(plots[3][3],RoboticonCustomisation.ENERGY);result=true; // Able to place it
		}};
		plotManager.placeEnergyRoboticon(3, 3); 
		new Verifications(){{
			messageDispatcher.dispatchMessage(GameEvents.PLAYERINVENTORYUPDATE.ordinal());times=0; // Not dispatched
		}};
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2 
	 * ensures that the roboticon place menu is removed if the current player is the human player
	 * (there is no need to test this for the AI player is cannot click plots and therefore cannot
	 * open a roboticon place menu)
	 */
	@Test
	public void testPlaceEnergyRoboticonMenuClosedHumanPlayer(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[1][2]); // Open a roboticon place menu
		
		plotManager.placeEnergyRoboticon(2, 1); 
		
		Array<Actor> plotManagerChildren = plotManager.getChildren();
		for(int child=0;child<plotManagerChildren.size;child++)
	    {
	    	// Only want children that are RoboticonPlaceMenu's
	    	if(plotManagerChildren.get(child) instanceof RoboticonPlaceMenu){
	    		fail(); // Menu still open, test failed
	        }
	    }
		
		// If this point has been reached then the menu has been closed and the test has passed
		
	}
	
	/**
	 * Tests {@link PlotManager#produceResources()} ensures that all plots are told to produce resources.
	 */
	@Test
	public void testProduceResources(){
		
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot5,plot6}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		plotManager.produceResources();
		new Verifications(){{
			plot1.produce();
			plot2.produce();
			plot3.produce();
			plot4.produce();
			plot5.produce();
			plot6.produce();
		}};
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when the first plot has not been acquired
	 */
	@Test
	public void testAllPlotsAcquiredFirstNotAcquired(){
		new Expectations(){{
			plot1.hasBeenAcquired();result=false;
			
		}};
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot5,plot6}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when the second plot has not been acquired
	 * (but the first plot has).
	 */
	@Test
	public void testAllPlotsAcquiredFirstAcquiredSecondNot(){
		new Expectations(){{
			plot1.hasBeenAcquired();result=true;
			plot2.hasBeenAcquired();result=false;
		}};
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot5,plot6}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when only the last plot has not been acquired
	 */
	@Test
	public void testAllPlotsAcquiredLastNotAcquired(){
		new Expectations(){{
			plot1.hasBeenAcquired();result=true;
			plot2.hasBeenAcquired();result=true;
			plot3.hasBeenAcquired();result=true;
			plot4.hasBeenAcquired();result=true;
			plot5.hasBeenAcquired();result=true;
			plot6.hasBeenAcquired();result=false;
		}};
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot5,plot6}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when all but one of the plots
	 * have been acquired
	 */
	@Test
	public void testAllPlotsAcquiredAllButOneAcquired(){
		new Expectations(){{
			plot1.hasBeenAcquired();result=true;
			plot2.hasBeenAcquired();result=true;
			plot3.hasBeenAcquired();result=true;
			plot4.hasBeenAcquired();result=false; 
			// Assume 5 and 6 acquired will terminate at this point anyway
			// (an error is produced if plot.hasBeenAcquired() is expected for 
			// plot 5 and 6 as it wont be executed for plot 5 and 6)
		}};
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot5,plot6}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns true when plots
	 * have been acquired
	 */
	@Test
	public void testAllPlotsAcquiredAllAcquired(){
		new Expectations(){{
			plot1.hasBeenAcquired();result=true;
			plot2.hasBeenAcquired();result=true;
			plot3.hasBeenAcquired();result=true;
			plot4.hasBeenAcquired();result=true;
			plot5.hasBeenAcquired();result=true;
			plot6.hasBeenAcquired();result=true;
		}};
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot5,plot6}};
		plotManager = new PlotManager("backgrounds/map.png",plots,humanPlayer,AIPlayer);
		assertTrue(plotManager.allPlotsAquired());
	}
}
