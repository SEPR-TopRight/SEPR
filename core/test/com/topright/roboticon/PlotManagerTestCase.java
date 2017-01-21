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
 * Can't be certain that the background is correct via automated tests.
 * </p>
 * @author jcn509
 */
@RunWith(JMockit.class)
public class PlotManagerTestCase extends GuiTest { 
	private PlotManager plotManager;
	@Mocked Player humanPlayer;
	@Mocked Player AIPlayer;
	@Mocked PlayerInventory humanPlayerInventory;
	@Mocked PlayerInventory AIPlayerInventory;
	@Mocked MessageDispatcher messageDispatcher;
	@Mocked Plot[][] plots;
	@Mocked Plot plot1;
	@Mocked Plot plot2;
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
	 * place roboticon, that one roboticon place menu is opened
	 */
	@Test
	public void testClickPlotModePlaceRoboticons(){
		plotManager.setPlotClickMode(PlotClickMode.PLACEROBOTICON);
		plotManager.setCurrentPlayer(humanPlayer);
		clickActor(buttons[0][0]);
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
	
	
	
}
