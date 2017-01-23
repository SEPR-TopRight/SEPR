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

/**
 * Integration tests for the {@link PlotManager}, {@link Player}, {@link PlayerInventory} and  
 * {@link Plot} classes
 * 
 * @author jcn509
 */
public class PlotManagerIntegrationTests extends GuiTest { 
	private PlotManager plotManager;
	private Player humanPlayer;
	private Player AIPlayer;
	private PlayerInventory humanPlayerInventory;
	private PlayerInventory AIPlayerInventory;
	private Plot[][] plots;
	private ButtonWithIcon[][] buttons;
	
	/**
	 * Runs before every test creates the required PlotManager object and all other required objects.
	 */
	@Before
	public void setup(){
		
		EnumMap<RoboticonCustomisation,Integer> humanRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		humanPlayerInventory = new PlayerInventory(0,1,humanRoboticonQuantities, 2);
		humanPlayer = new Player(humanPlayerInventory);
		
		EnumMap<RoboticonCustomisation,Integer> AIRoboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		AIPlayerInventory = new PlayerInventory(0,1,AIRoboticonQuantities, 2);
		AIPlayer = new Player(AIPlayerInventory);
		
		plots = CreatePlots.createPlots(4,5);
		plotManager = new PlotManager("assets/backgrounds/map.png", plots, humanPlayer, AIPlayer);
		buttons = plotManager.getPlotButtons();
		stage.addActor(plotManager);
	}
	
	/**
	 * Ensures that when the plot in row 0 column 1 is clicked and the plot click mode 
	 * is set to acquire that plot is acquired by the current player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickAcquiredHuman(){
		// All plots initially unacquired
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[0][1]);
		assertEquals(plots[0][1].getPlayer(),humanPlayer);
	}
	
	/**
	 * Ensures that when the plot in row 3 column 3 is clicked and the plot click mode 
	 * is set to acquire that plot is acquired by the current player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickAcquiredHumanTwo(){
		// All plots initially unacquired
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[3][3]);
		assertEquals(plots[3][3].getPlayer(),humanPlayer);
	}
	
	/**
	 * Ensures that when the plot in row 0 column 0 is clicked and the plot click mode 
	 * is set to acquire that no other plots are acquired by the human player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredHuman(){
		// All plots initially unacquired
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[0][0]);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!(row==0 && column==0)){// Ignore the acquired plot
					assertFalse(plots[row][column].hasBeenAcquired());
				}
			}
		}
	}
	

	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode 
	 * is set to acquire that no other plots are acquired by the human player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredHuman2(){
		// All plots initially unacquired
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[1][2]);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!(row==1 && column==2)){// Ignore the acquired plot
					assertFalse(plots[row][column].hasBeenAcquired());
				}
			}
		}
	}
	
	/**
	 * Ensures that when the plot in row 0 column 0 is clicked and the plot click mode 
	 * is set to acquire that no plots are acquired by the AI player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredAI(){
		// All plots initially unacquired
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[0][0]);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				assertNotEquals(AIPlayer,plots[row][column].getPlayer());
			}
		}
	}
	
	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode 
	 * is set to acquire that no plots are acquired by the AI player
	 * (when the current player is the human player, 
	 * there is no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void plotClickNoOthersAcquiredAI2(){
		// All plots initially unacquired
		plotManager.setPlotClickMode(PlotClickMode.ACQUIRE);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[1][2]);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				assertNotEquals(AIPlayer,plots[row][column].getPlayer());
			}
		}
	}
	
	/**
	 * Ensures that when the plot in row 1 column 2 is clicked and the plot click mode is set to
	 * place roboticon and the plot does not belong to the human player, 
	 * that no roboticon place menus are opened
	 * (when the current player is the human player, no need to test this for the AI player as it cannot click plots)
	 */
	@Test
	public void testClickPlotModePlaceRoboticonsPlotNotOwnedByHuman(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		plots[1][2].setPlayer(AIPlayer);
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
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 2);
		plots[1][2].setPlayer(humanPlayer);
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
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 2);
		plots[1][2].setPlayer(humanPlayer);
		plots[2][3].setPlayer(humanPlayer);
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
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 2);
		plots[1][2].setPlayer(humanPlayer);
		plots[2][3].setPlayer(humanPlayer);
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
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 2);
		plots[1][2].setPlayer(humanPlayer);
		plots[2][3].setPlayer(AIPlayer);
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
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 2);
		plots[1][2].setPlayer(humanPlayer);
		plots[2][3].setPlayer(AIPlayer);
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
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE, 2);
		plots[1][2].setPlayer(humanPlayer);
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
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an ore roboticon)
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceOreRoboticonRoboticonPlacedHumanPlayer(){
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		plots[1][2].setPlayer(humanPlayer);
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeOreRoboticon(2, 1); 
	}
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an ore roboticon)
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledHumanPlayer2(){
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		plots[3][3].setPlayer(humanPlayer);
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeOreRoboticon(3, 3); 
	}
	
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an ore roboticon)
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledAIPlayer(){
		AIPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		plots[1][2].setPlayer(AIPlayer);
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeOreRoboticon(2, 1); 
	}
	
	
	/**
	 * Tests {@link PlotManager#placeOreRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an ore roboticon)
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceOreRoboticonMethodCalledAIPlayer2(){
		AIPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1);
		plots[3][3].setPlayer(AIPlayer);
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeOreRoboticon(3, 3); 
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an energy roboticon)
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceEnergyRoboticonRoboticonPlacedHumanPlayer(){
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		plots[1][2].setPlayer(humanPlayer);
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeEnergyRoboticon(2, 1); 
	}
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an energy roboticon)
	 * on the plot.
	 * (when the current player is the human player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledHumanPlayer2(){
		humanPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		plots[3][3].setPlayer(humanPlayer);
		plotManager.setCurrentPlayer(humanPlayer);
		plotManager.placeEnergyRoboticon(3, 3); 
	}
	
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 1 column 2
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an energy roboticon)
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledAIPlayer(){
		AIPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		plots[1][2].setPlayer(AIPlayer);
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeEnergyRoboticon(2, 1); 
	}
	
	
	/**
	 * Tests {@link PlotManager#placeEnergyRoboticon(int, int)} for the plot in row 3 column 3
	 * ensures that a roboticon is placed on the plot (if then plot belongs to the current player
	 * and the current player has an energy roboticon)
	 * on the plot.
	 * (when the current player is the AI player)
	 */
	@Test
	public void testPlaceEnergyRoboticonMethodCalledAIPlayer2(){
		AIPlayerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1);
		plots[3][3].setPlayer(AIPlayer);
		plotManager.setCurrentPlayer(AIPlayer);
		plotManager.placeEnergyRoboticon(3, 3); 
	}
	
	/**
	 * Tests {@link PlotManager#produceResources()} ensures that 6 energy are added to the players inventory
	 * when they own 3 energy plots with energy roboticons on them
	 */
	@Test
	public void testProduceResourcesSixEnergyThreeEnergyRoboticonsThreeEnergyPlots(){
		Plot plot1 = new Plot(PlotSpecialism.ENERGY);
		Plot plot2 = new Plot(PlotSpecialism.ENERGY);
		Plot plot3 = new Plot(PlotSpecialism.ENERGY);
		Plot plot4 = new Plot(PlotSpecialism.ENERGY);
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot4,plot4},{plot4,plot4,plot4}};
		plotManager = new PlotManager("assets/backgrounds/map.png", plots, humanPlayer, AIPlayer);
		plot1.setPlayer(humanPlayer);
		plot2.setPlayer(humanPlayer);
		plot3.setPlayer(humanPlayer);
		
		plot1.placeRoboticon(RoboticonCustomisation.ENERGY);
		plot2.placeRoboticon(RoboticonCustomisation.ENERGY);
		plot3.placeRoboticon(RoboticonCustomisation.ENERGY);
		
		int energyBefore = humanPlayerInventory.getEnergyQuantity();
		plotManager.produceResources();
		assertEquals(energyBefore+6,humanPlayerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlotManager#produceResources()} ensures that 5 energy are added to the players inventory
	 * when they own 2 energy plots with energy roboticons on them and ore plot with an energy roboticon on it
	 */
	@Test
	public void testProduceResourcesFiveEnergyThreeEnergyRoboticonsOneOrePlotTwoEnergyPlots(){
		Plot plot1 = new Plot(PlotSpecialism.ENERGY);
		Plot plot2 = new Plot(PlotSpecialism.ORE);
		Plot plot3 = new Plot(PlotSpecialism.ENERGY);
		Plot plot4 = new Plot(PlotSpecialism.ENERGY);
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot4,plot4},{plot4,plot4,plot4}};
		plotManager = new PlotManager("assets/backgrounds/map.png", plots, humanPlayer, AIPlayer);
		plot1.setPlayer(humanPlayer);
		plot2.setPlayer(humanPlayer);
		plot3.setPlayer(humanPlayer);
		
		plot1.placeRoboticon(RoboticonCustomisation.ENERGY);
		plot2.placeRoboticon(RoboticonCustomisation.ENERGY);
		plot3.placeRoboticon(RoboticonCustomisation.ENERGY);
		
		int energyBefore = humanPlayerInventory.getEnergyQuantity();
		plotManager.produceResources();
		assertEquals(energyBefore+5,humanPlayerInventory.getEnergyQuantity());
	}
	
	/**
	 * Tests {@link PlotManager#produceResources()} ensures that 6 ore are added to the players inventory
	 * when they own 3 ore plots with ore roboticons on them
	 */
	@Test
	public void testProduceResourcesSixOreThreeOreRoboticonsThreeOrePlots(){
		Plot plot1 = new Plot(PlotSpecialism.ORE);
		Plot plot2 = new Plot(PlotSpecialism.ORE);
		Plot plot3 = new Plot(PlotSpecialism.ORE);
		Plot plot4 = new Plot(PlotSpecialism.ENERGY);
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot4,plot4},{plot4,plot4,plot4}};
		plotManager = new PlotManager("assets/backgrounds/map.png", plots, humanPlayer, AIPlayer);
		plot1.setPlayer(humanPlayer);
		plot2.setPlayer(humanPlayer);
		plot3.setPlayer(humanPlayer);
		
		plot1.placeRoboticon(RoboticonCustomisation.ORE);
		plot2.placeRoboticon(RoboticonCustomisation.ORE);
		plot3.placeRoboticon(RoboticonCustomisation.ORE);
		
		int oreBefore = humanPlayerInventory.getOreQuantity();
		plotManager.produceResources();
		assertEquals(oreBefore+6,humanPlayerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlotManager#produceResources()} ensures that 5 ore added to the players inventory
	 * when they own 2 ore plots with ore roboticons on them and one energy plot with an ore roboticon on it
	 */
	@Test
	public void testProduceResources5Energy2EnergyRoboticons1OreRoboticon3EnergyPlots(){
		Plot plot1 = new Plot(PlotSpecialism.ORE);
		Plot plot2 = new Plot(PlotSpecialism.ENERGY);
		Plot plot3 = new Plot(PlotSpecialism.ORE);
		Plot plot4 = new Plot(PlotSpecialism.ORE);
		Plot[][] plots = {{plot1,plot2,plot3},{plot4,plot4,plot4},{plot4,plot4,plot4}};
		plotManager = new PlotManager("assets/backgrounds/map.png", plots, humanPlayer, AIPlayer);
		plot1.setPlayer(humanPlayer);
		plot2.setPlayer(humanPlayer);
		plot3.setPlayer(humanPlayer);
		
		plot1.placeRoboticon(RoboticonCustomisation.ORE);
		plot2.placeRoboticon(RoboticonCustomisation.ORE);
		plot3.placeRoboticon(RoboticonCustomisation.ORE);
		
		int oreBefore = humanPlayerInventory.getOreQuantity();
		plotManager.produceResources();
		assertEquals(oreBefore+5,humanPlayerInventory.getOreQuantity());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when the first plot has not been acquired
	 */
	@Test
	public void testAllPlotsAcquiredFirstNotAcquired(){
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!(row==0 && column==0)){ // Ignore the first plot (leave it unacquired)
					plots[row][column].setPlayer(humanPlayer);
				}
			}
		}
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} only the first plot has been acquired (all others unacquired)
	 */
	@Test
	public void testAllPlotsAcquiredFirstAcquiredSecondNot(){
		plots[0][0].setPlayer(AIPlayer);// First plot acquired (all others not)
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when only the last plot has not been acquired
	 */
	@Test
	public void testAllPlotsAcquiredLastNotAcquired(){
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!(row==plots.length-1 && column==plots[0].length-1)){ // Ignore the last plot (leave it unacquired)
					plots[row][column].setPlayer(humanPlayer);
				}
			}
		}
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns false when all plots
	 * apart from the plot in the second row and second column have been acquired
	 */
	@Test
	public void testAllPlotsAcquiredAllButOneAcquired(){
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!(row==1 && column==1)){ // Ignore the plot in the second row and column (leave it unacquired)
					plots[row][column].setPlayer(humanPlayer);
				}
			}
		}
		assertFalse(plotManager.allPlotsAquired());
	}
	
	/**
	 * Tests {@link PlotManager#allPlotsAquired()} ensures that it returns true when plots
	 * have been acquired
	 */
	@Test
	public void testAllPlotsAcquiredAllAcquired(){
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				plots[row][column].setPlayer(AIPlayer); // All plots acquired by AIPlayer
			}
		}
		assertTrue(plotManager.allPlotsAquired());
	}
}
