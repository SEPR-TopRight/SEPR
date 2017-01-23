package com.topright.roboticon;
import static org.junit.Assert.*;

import java.util.EnumMap;
import org.junit.*;	
import mockit.*;
import mockit.integration.junit4.*;
import org.junit.runner.RunWith;

/**
 * Integration tests for the {@link AIPlayer}, {@link Market}, {@link PlayerInventory}, 
 * {@link MarketInventory}, {@link PlotManager} and {@link Plot} classes
 * @author jcn509
 */

@RunWith(JMockit.class)
public class AIPlayerIntegrationTests extends GuiTest{
	
	private AIPlayer player;
	private PlayerInventory playerInventory;
	private Market market;
	private MarketInventory marketInventory;
	private PlotManager plotManager;
	private Plot[][] plots;
	

	/**
	 * Runs before every test, creates all necessary objects and an AIPlayer object
	 */
	@Before
	public void setup(){
		EnumMap<RoboticonCustomisation,Integer> roboticonQuantities = new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class);
		roboticonQuantities.put(RoboticonCustomisation.UNCUSTOMISED, 0);
		roboticonQuantities.put(RoboticonCustomisation.ORE, 0);
		roboticonQuantities.put(RoboticonCustomisation.ENERGY, 0);
				
		playerInventory = new PlayerInventory(5,4,roboticonQuantities,1000);
		player = new AIPlayer(playerInventory);
		market = Market.getInstance();
		market.setInventory(new MarketInventory(10,10,10));
		plots = CreatePlots.createPlots(4, 5);
		plotManager = new PlotManager("backgrounds/map.png",plots,null,player);
		marketInventory = new MarketInventory(0,0,12);
		market.setInventory(marketInventory);
		plotManager.setCurrentPlayer(player);
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that an exception is thrown if there are no unacquired plots
	 */
	@Test(expected=IllegalStateException.class)
	public void testChoosePlotExceptionThrownNoEmptyPlots(){
		
		// All plots intially acquired
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				plots[row][column].setPlayer(player);
			}
		}
		player.choosePlot(plotManager);
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that the first unacquired plot is acquired
	 * (when the first unacquired plot is the first plot)
	 */
	@Test
	public void testChoosePlotFirstEmptyPlotAcquired(){
		
		// All plots are initially unacquired
		player.choosePlot(plotManager);
		assertTrue(plots[0][0].getPlayer()==player);
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that all plots apart from the first unacquired plot are not acquired
	 * (when the first unacquired plot is the first plot)
	 */
	@Test
	public void testChoosePlotOnlyOneAcquired(){
		
		// All plots initially unacquired
		player.choosePlot(plotManager);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!(column==0 && row==0)){ // Don't check that the first plot is unacquired
					if(plots[row][column].hasBeenAcquired()){
						fail(); // Another plot was acquired
					}
				}
			}
		}
		// If we reach this point the test must have passed
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that the first unacquired plot is acquired 
	 * (when the first plot has already been acquired)
	 */
	@Test
	public void testChoosePlotFirstEmptyPlotAcquired2(){
		
		// All plots acquired in the first row and first part of second row 
		for(int column=0;column<plots[0].length;column++){
			plots[0][column].setPlayer(player);
		}
		for(int column=0;column<2;column++){
			plots[1][column].setPlayer(player);
		}
		player.choosePlot(plotManager);
		assertEquals(plots[1][2].getPlayer(),player);
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that all plots apart from the first unacquired plot are not acquired (when the first plot has already been acquired)
	 */
	@Test
	public void testChoosePlotOnlyOneAcquired2(){
		
		// All plots acquired in the first row and first part of second row 
		for(int column=0;column<plots[0].length;column++){
			plots[0][column].setPlayer(player);
		}
		for(int column=0;column<2;column++){
			plots[1][column].setPlayer(player);
		}
		player.choosePlot(plotManager);
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				// Don't check that the row, or first part of the second row is unacquired
				if(row!=0 && !(row==1 && column<=2)){ 
					if(plots[row][column].hasBeenAcquired()){
						fail(); // Another plot was acquired
					}
				}
			}
		}
		// If we reach this point the test must have passed
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that no roboticons are purchased 
	 * when the player has no plots without roboticons on them
	 */
	@Test
	public void testBuyRoboticonsNoEmptyPlots(){
		
		// All plots in the first 2 rows owned by the player and have either an energy or ore roboticon on them
		for(int row=0;row<2;row++){
			for(int column=0;column<plots[0].length;column++){
				plots[row][column].setPlayer(player);
				if(column==0){
					plots[row][column].placeRoboticon(RoboticonCustomisation.ORE);
				}
				else{
					plots[row][column].placeRoboticon(RoboticonCustomisation.ENERGY);
				}
			}
		}
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		player.buyRoboticons(plotManager);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(roboticonsBefore,roboticonsAfter);
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that no roboticons are purchased 
	 * when the player has as many roboticons as they have empty plots
	 */
	@Test
	public void testBuyRoboticonsManyRoboticonsInInventory(){
		// All plots in the first 2 rows owned by the player and have no roboticons on them (10 plots total)
		for(int row=0;row<2;row++){
			for(int column=0;column<plots[0].length;column++){
				plots[row][column].setPlayer(player);
				if(column==0){
					plots[row][column].placeRoboticon(RoboticonCustomisation.ORE);
				}
				else{
					plots[row][column].placeRoboticon(RoboticonCustomisation.ENERGY);
				}
			}
		}
		int roboticonsBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		
		// Leave the player with exactly 10 roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,10-roboticonsBefore);
		player.buyRoboticons(plotManager);
		int roboticonsAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(10,roboticonsAfter);
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that 1 roboticon is purchased when the player 
	 * has one empty plot (and no roboticons in their inventory)
	 */
	@Test
	public void testBuyRoboticonsOneEmptyPlot(){
		
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		plots[1][2].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with no roboticons in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		player.buyRoboticons(plotManager);
		assertEquals(1,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that 2 roboticons are purchased when the player has two empty plots (and no roboticons in their inventory)
	 */
	@Test
	public void testBuyRoboticonsTwoEmptyPlots(){
		playerInventory.increaseMoneyQuantity(100000); // Plenty of money
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[2][4].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with no roboticons in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		player.buyRoboticons(plotManager);
		assertEquals(2,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that 2 roboticons are purchased when the player has three empty plots and 1 roboticons in their inventory
	 */
	@Test
	public void testBuyRoboticonsThreeEmptyPlotsOneRoboticonInInvetory(){
		
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[2][4].setPlayer(player); // No roboticon on it initially
		plots[2][1].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with 1 roboticon in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
		player.buyRoboticons(plotManager);
		assertEquals(3,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the correct amount of money is removed from the 
	 * AIPlayers inventory when they buy 2 roboticons
	 */
	@Test
	public void testBuyRoboticonsMoneyTaken(){
		int moneyBefore = playerInventory.getMoneyQuantity();
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[2][4].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with 0 roboticons in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		player.buyRoboticons(plotManager);
		int moneyAfter = playerInventory.getMoneyQuantity();
		int moneySpent = market.getCostRoboticons(2);
		assertEquals(moneyBefore-moneySpent,moneyAfter);
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer causes the production of a roboticon
	 * if the market doesn't have any when it needs 1
	 */
	@Test
	public void testBuyRoboticonsProduceARoboticon(){
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		plots[1][2].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with no roboticons in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate()); // Enough ore to produce a roboticon
		int oreBefore = market.getOreQuantity();
		marketInventory.decreaseRoboticonQuantity(market.getRoboticonQuantity()); // No roboticons in the market
		player.buyRoboticons(plotManager);
		assertEquals(1,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
		assertEquals(oreBefore-market.getRoboticonOreConversionRate(),market.getOreQuantity());
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer causes the production of a roboticon 
	 * if the market only has 2 roboticon when it needs three
	 */
	@Test
	public void testBuyRoboticonsProduceRoboticon(){
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[3][2].setPlayer(player); // No roboticon on it initially
		plots[1][1].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with no roboticons in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		
		marketInventory.increaseOreQuantity(market.getRoboticonOreConversionRate()); // Enough ore to produce a roboticon
		
		int oreBefore = market.getOreQuantity();
		marketInventory.decreaseRoboticonQuantity(market.getRoboticonQuantity()); // No roboticons in the market
		marketInventory.increaseRoboticonQuantity(2);// 2 roboticons in the market
		player.buyRoboticons(plotManager);
		assertEquals(3,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
		assertEquals(oreBefore-market.getRoboticonOreConversionRate(),market.getOreQuantity());
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer doesn't attempt to buy a roboticon if the market doesn't have any and none can be produced
	 */
	@Test
	public void testBuyRoboticonsNoPurchaseIfRoboticonCantBeProduced(){
		int roboticonsInInventory = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[3][2].setPlayer(player); // No roboticon on it initially
		plots[1][1].setPlayer(player); // No roboticon on it initially
		
		// Leave the player with no roboticons in their inventory
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, roboticonsInInventory);
		
		int oreBefore = market.getOreQuantity();
		marketInventory.decreaseRoboticonQuantity(market.getRoboticonQuantity()); // No roboticons in the market
		marketInventory.decreaseOreQuantity(marketInventory.getOreQuantity()); // No ore to produce roboticons with
		player.buyRoboticons(plotManager);
		
		// No roboticons purchased
		assertEquals(0,playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED));
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer doesn't attempt to buy a roboticon 
	 * if it does not have enough money
	 */
	@Test
	public void testBuyRoboticonsNotEnoughMoneyToPurchase(){
		int roboticonsInInventoryBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,roboticonsInInventoryBefore); // has no roboticons
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // No money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1) - 1); // Not enough money to buy a roboticon
		player.buyRoboticons(plotManager);
		int roboticonsInInventoryAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(0,roboticonsInInventoryAfter);
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer doesn't attempt to buy a roboticon if 
	 * they wouldn't have enough money to customise it afterwards
	 */
	@Test
	public void testBuyRoboticonsNotEnoughMoneyToCustomise(){
		
		int roboticonsInInventoryBefore = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		playerInventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,roboticonsInInventoryBefore); // has no roboticons
		
		playerInventory.decreaseMoneyQuantity(playerInventory.getMoneyQuantity()); // No money
		playerInventory.increaseMoneyQuantity(market.getCostRoboticons(1)); // Enough money to buy a roboticon
		
		int customisationCost = Math.min(market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE),market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY));
		playerInventory.increaseMoneyQuantity(customisationCost-1); // Not enough to buy the cheapest customisation
		
		player.buyRoboticons(plotManager);
		
		int roboticonsInInventoryAfter = playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		assertEquals(0,roboticonsInInventoryAfter);
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer doesn't attempt to place any roboticons 
	 * if they don't have any customised roboticons to place
	 */
	@Test
	public void testPlaceRoboticonsNoRoboticons(){
		
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[3][2].setPlayer(player); // No roboticon on it initially
		plots[1][1].setPlayer(player); // No roboticon on it initially
		
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,1000);
		
		player.placeRoboticons(plotManager);
		// PlayerInventory contains no customised roboticons...
		assertFalse(plots[1][2].hasRoboticon());
		assertFalse(plots[3][2].hasRoboticon());
		assertFalse(plots[1][1].hasRoboticon());
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer doesn't attempt to place any roboticons 
	 * if they don't have any empty plots (when they have plenty of customised roboticons)
	 */
	@Test
	public void testPlaceRoboticonsNoEmptyPlots(){
		
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[3][2].setPlayer(player); // No roboticon on it initially
		plots[1][1].setPlayer(player); // No roboticon on it initially
		
		// No empty plots
		plots[1][2].placeRoboticon(RoboticonCustomisation.ORE); 
		plots[3][2].placeRoboticon(RoboticonCustomisation.ENERGY);  
		plots[1][1].placeRoboticon(RoboticonCustomisation.ORE); 
		
		// Plenty of customised roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1000);
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1000);
		
		player.placeRoboticons(plotManager);
		
		assertEquals(playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY),1000);
		assertEquals(playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE),1000);
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer attempts to place energy roboticons if they only have energy roboticons 
	 * (and have empty plots that specialise in both ore and energy production)
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveEnergyRoboticonsNoOreRoboticonsPlaced(){
		Plot plot1 = new Plot(PlotSpecialism.ENERGY);
		Plot plot2 = new Plot(PlotSpecialism.ENERGY);
		Plot plot3 = new Plot(PlotSpecialism.ORE);
		Plot plot4 = new Plot(PlotSpecialism.ORE);
		
		Plot[][] plots = {{plot3,plot4,plot4},{plot4,plot4,plot1},{plot4,plot4,plot4},{plot4,plot4,plot2}};
		plot1.setPlayer(player); // No roboticon on it initially
		plot2.setPlayer(player); // No roboticon on it initially
		plot3.setPlayer(player); // No roboticon on it initially
		
		plotManager = new PlotManager("backgrounds/map.png", plots, null, player);
		plotManager.setCurrentPlayer(player);
		
		// Plenty of energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1000);
		player.placeRoboticons(plotManager);
		
		assertEquals(plot1.getRoboticon(),RoboticonCustomisation.ENERGY);
		assertEquals(plot2.getRoboticon(),RoboticonCustomisation.ENERGY);
		assertFalse(plot3.hasRoboticon());
		assertFalse(plot4.hasRoboticon());
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AI player places 3 energy roboticons 
	 * if they have 3 energy roboticons and 3 empty plots that specialise in energy production
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveEnergyRoboticonsEnergyRoboticonsPlaced(){
		
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[3][2].setPlayer(player); // No roboticon on it initially
		plots[1][1].setPlayer(player); // No roboticon on it initially
		
		plots[1][2].placeRoboticon(RoboticonCustomisation.ENERGY); 
		plots[3][2].placeRoboticon(RoboticonCustomisation.ENERGY);  
		plots[1][1].placeRoboticon(RoboticonCustomisation.ENERGY); 
		
		// Plenty of energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ENERGY,1000);
		assertEquals(plots[1][2].getRoboticon(),RoboticonCustomisation.ENERGY);
		assertEquals(plots[3][2].getRoboticon(),RoboticonCustomisation.ENERGY);
		assertEquals(plots[1][1].getRoboticon(),RoboticonCustomisation.ENERGY);
	}
	
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer only attempts to place ore roboticons 
	 * if they only have ore roboticons (and have empty plots that specialise in both ore and energy production)
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveOreRoboticonsNoEnergyRoboticonsPlaced(){
		
		Plot plot1 = new Plot(PlotSpecialism.ORE);
		Plot plot2 = new Plot(PlotSpecialism.ENERGY);
		Plot plot3 = new Plot(PlotSpecialism.ORE);
		Plot plot4 = new Plot(PlotSpecialism.ENERGY);
		
		Plot[][] plots = {{plot3,plot4,plot4},{plot4,plot4,plot1},{plot4,plot4,plot4},{plot4,plot4,plot2}};
		plot1.setPlayer(player); // No roboticon on it initially
		plot2.setPlayer(player); // No roboticon on it initially
		plot3.setPlayer(player); // No roboticon on it initially
		
		plotManager = new PlotManager("backgrounds/map.png", plots, null, player);
		plotManager.setCurrentPlayer(player);
		
		// Plenty of energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1000);
		player.placeRoboticons(plotManager);
		
		assertEquals(plot1.getRoboticon(),RoboticonCustomisation.ORE);
		assertEquals(plot3.getRoboticon(),RoboticonCustomisation.ORE);
		assertFalse(plot2.hasRoboticon());
		assertFalse(plot4.hasRoboticon());
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AI player places 3 ore roboticons 
	 * if they have 3 ore roboticons and 3 empty plots that specialise in ore production
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveOreRoboticonsOreRoboticonsPlaced(){
		
		plots[1][2].setPlayer(player); // No roboticon on it initially
		plots[3][2].setPlayer(player); // No roboticon on it initially
		plots[1][1].setPlayer(player); // No roboticon on it initially
		
		plots[1][2].placeRoboticon(RoboticonCustomisation.ORE); 
		plots[3][2].placeRoboticon(RoboticonCustomisation.ORE);  
		plots[1][1].placeRoboticon(RoboticonCustomisation.ORE); 
		
		// Plenty of energy roboticons
		playerInventory.increaseRoboticonQuantity(RoboticonCustomisation.ORE,1000);
		assertEquals(plots[1][2].getRoboticon(),RoboticonCustomisation.ORE);
		assertEquals(plots[3][2].getRoboticon(),RoboticonCustomisation.ORE);
		assertEquals(plots[1][1].getRoboticon(),RoboticonCustomisation.ORE);
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player does not attempt to sell ore 
	 * if it hasn't got any
	 */
	@Test
	public void testBuyAndSellResourcesNoOreToSell(){
		int marketOreBefore = market.getOreQuantity();
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity()); // No ore
		player.buyAndSellResources();
		assertEquals(market.getOreQuantity(),marketOreBefore);		
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player does not attempt to sell energy 
	 * if it hasn't got any
	 */
	@Test
	public void testBuyAndSellResourcesNoEnergyToSell(){
		
		int marketEnergyBefore = market.getEnergyQuantity();
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity()); // No energy
		player.buyAndSellResources();
		assertEquals(market.getEnergyQuantity(),marketEnergyBefore);	
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell one ore 
	 * if it has exactly one ore in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesOneOre(){
		int marketOreBefore = market.getOreQuantity();
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity());
		playerInventory.increaseOreQuantity(1); // 1 ore
		player.buyAndSellResources();
		assertEquals(market.getOreQuantity()-1,marketOreBefore);		
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell Five ore if it has exactly Five ore in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesFiveOre(){
		int marketOreBefore = market.getOreQuantity();
		playerInventory.decreaseOreQuantity(playerInventory.getOreQuantity());
		playerInventory.increaseOreQuantity(5); // 5 ore
		player.buyAndSellResources();
		assertEquals(market.getOreQuantity()-5,marketOreBefore);
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell one energy if it has exactly energy ore in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesOneEnergy(){
		int marketEnergyBefore = market.getEnergyQuantity();
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity());
		playerInventory.increaseEnergyQuantity(1); // 1 energy
		player.buyAndSellResources();
		assertEquals(market.getEnergyQuantity()-1,marketEnergyBefore);	
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell Five energy if it has exactly Five energy in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesFiveEnergy(){
		int marketEnergyBefore = market.getEnergyQuantity();
		playerInventory.decreaseEnergyQuantity(playerInventory.getEnergyQuantity());
		playerInventory.increaseEnergyQuantity(5); // 5 energy
		player.buyAndSellResources();
		assertEquals(market.getEnergyQuantity()-5,marketEnergyBefore);	
	}
}
