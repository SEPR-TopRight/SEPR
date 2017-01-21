package com.topright.roboticon;
import java.util.EnumMap;
import org.junit.*;	
import mockit.*;
import mockit.integration.junit4.*;
import org.junit.runner.RunWith;

/**
 * Test case for {@link AIPlayer}
 * @author jcn509
 */

@RunWith(JMockit.class)
public class AIPlayerTestCase {
	
	private AIPlayer player;
	@Mocked private PlayerInventory playerInventory;
	@Mocked private Market market;
	@Mocked private MarketInventory marketInventory;
	@Mocked private PlotManager plotManager;
	@Mocked private Plot plot1;
	@Mocked private Plot plot2;
	@Mocked private Plot plot3;
	@Mocked private Plot[][] plots;
	

	/**
	 * Runs before every test, creates all necessary mocked objects and an AIPlayer object
	 */
	@Before
	public void setup(){
		playerInventory = new PlayerInventory(5,4,new EnumMap<RoboticonCustomisation,Integer>(RoboticonCustomisation.class),1000);
		player = new AIPlayer(playerInventory);
		market = Market.getInstance();
		market.setInventory(new MarketInventory(10,10,10));
		plots = CreatePlots.createPlots(4, 5);
		plotManager = new PlotManager("",plots,null,player);
		plot1 = new Plot(PlotSpecialism.ENERGY);
		plot2 = new Plot(PlotSpecialism.ORE);
		plot2 = new Plot(PlotSpecialism.ORE);
		marketInventory = new MarketInventory(0,0,0);
		market.setInventory(marketInventory);
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that an exception is thrown if there are no unacquired plots
	 */
	@Test(expected=IllegalStateException.class)
	public void testChoosePlotExceptionThrownNoEmptyPlots(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot2,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.hasBeenAcquired(); result = true;
			plot2.hasBeenAcquired(); result = true;
			
		}};
		player.choosePlot(plotManager);
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that the first unacquired plot is acquired
	 */
	@Test
	public void testChoosePlotFirstEmptyPlotAcquired(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot2,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.hasBeenAcquired(); result = false;
			
		}};
		player.choosePlot(plotManager);
		new Verifications(){{ // Ensure that the plot is acquired
			plotManager.acquirePlot(0,0); times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that all apart from the first unacquired plots are not acquired
	 */
	@Test
	public void testChoosePlotOnlyOneAcquired(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot2,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.hasBeenAcquired(); result = false;
			
		}};
		player.choosePlot(plotManager);
		new Verifications(){{ // Ensure that the second plot was not even looked at
			plot2.hasBeenAcquired(); times=0;
			plotManager.acquirePlot(0,1); times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that the first unacquired plot is acquired (when the first plot has already been acquired)
	 */
	@Test
	public void testChoosePlotFirstEmptyPlotAcquired2(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.hasBeenAcquired(); result = false;
			plot2.hasBeenAcquired(); result = true;
		}};
		player.choosePlot(plotManager);
		new Verifications(){{ // Ensure that the plot is acquired
			plotManager.acquirePlot(0,1); times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#choosePlot} ensures that the first unacquired plot is acquired (when the first plot has already been acquired)
	 */
	@Test
	public void testChoosePlotOnlyOneAcquired2(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.hasBeenAcquired(); result = false;
			plot2.hasBeenAcquired(); result = true;
		}};
		player.choosePlot(plotManager);
		new Verifications(){{ // Ensure that the plot is acquired
			plotManager.acquirePlot(0,0); times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that no roboticons are purchase when the player has no plots without roboticons on them
	 */
	@Test
	public void testBuyRoboticonsNoEmptyPlots(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot3 }
				};
			plot1.getPlayer(); result = null;
			plot2.getPlayer(); result = player;
			plot3.getPlayer(); result = player;
			plot2.hasRoboticon(); result = true;
			plot3.hasRoboticon(); result = true;
			
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ // Ensure that the plot is acquired
			market.buyRoboticonsFromMarket(anyInt); times=0; // no roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that no roboticons are purchase when the player has as many roboticons as they have empty plots
	 */
	@Test
	public void testBuyRoboticonsManyRoboticonsInInventory(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; // All plots owned by player
			plot2.getPlayer(); result = player;

			plot1.hasRoboticon(); result = false; // All plots empty
			plot2.hasRoboticon(); result = false;
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot2.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=9; // There are 9 plots
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ // Ensure that the plot is acquired
			market.buyRoboticonsFromMarket(anyInt); times=0; // no roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that 1 roboticon is purchased when the player has one empty plot (and no roboticons in their inventory)
	 */
	@Test
	public void testBuyRoboticonsOneEmptyPlot(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;

			plot1.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			
			market.getRoboticonQuantity();result=5; // Enough roboticons
			
			market.getCostRoboticons(1); result = 5;

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 10; // exactly enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.buyRoboticonsFromMarket(1); times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that 2 roboticons are purchased when the player has two empty plots (and no roboticons in their inventory)
	 */
	@Test
	public void testBuyRoboticonsTwoEmptyPlots(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot3 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;
			plot3.getPlayer(); result = player; 

			plot1.hasRoboticon(); result = false; 
			plot3.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot3.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			market.getRoboticonQuantity();result=5; // Enough roboticons
			
			market.getCostRoboticons(1); result = 5;

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 20; // exactly enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.buyRoboticonsFromMarket(1); times=2;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that 2 roboticons are purchased when the player has three empty plots and 1 roboticons in their inventory
	 */
	@Test
	public void testBuyRoboticonsThreeEmptyPlotsOneRoboticonInInvetory(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot3 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;
			plot3.getPlayer(); result = player; 

			plot1.hasRoboticon(); result = false; 
			plot3.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot3.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			market.getRoboticonQuantity();result=5; // Enough roboticons
			
			market.getCostRoboticons(1); result = 5;

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 20; // exactly enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.buyRoboticonsFromMarket(1); times=2;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the correct amount of money is removed from the AIPlayers inventory when they buy roboticons
	 */
	@Test
	public void testBuyRoboticonsMoneyTaken(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot3 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;
			plot3.getPlayer(); result = player; 

			plot1.hasRoboticon(); result = false; 
			plot3.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot3.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			market.getRoboticonQuantity();result=5; // Enough roboticons
			
			market.getCostRoboticons(1); result = 5;

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=1; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 20; // Exactly enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			playerInventory.decreaseMoneyQuantity(5);times=2; // 2 roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer causes the production of a roboticon doesn't have one when it needs one
	 */
	@Test
	public void testBuyRoboticonsProduceARoboticon(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;


			plot1.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			
			market.getRoboticonQuantity();result=0; // Not enough roboticons
			
			market.getCostRoboticons(1); result = 5;
			market.attemptToProduceRoboticon(); result = true;
			

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 20; // exactly enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.attemptToProduceRoboticon(); times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer causes the production of a roboticon if the market only has 2 roboticon when it needs three
	 */
	@Test
	public void testBuyRoboticonsProduceRoboticon(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot2 },
				  { plot1,plot2,plot2 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;

			plot1.hasRoboticon(); result = false;
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
		
			
			market.getRoboticonQuantity();result=0; // not enough roboticons
			
			market.getCostRoboticons(1); result = 5;

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 20; // exactly enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.attemptToProduceRoboticon(); times=1; // no roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer doesn't attempt to buy a roboticon if the market doesn't have any and none can be produced
	 */
	@Test
	public void testBuyRoboticonsNoPurchaseIfRoboticonCantBeProduced(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot3 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;
			plot3.getPlayer(); result = player; 

			plot1.hasRoboticon(); result = false; 
			plot3.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot3.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			market.getRoboticonQuantity();result=0; // not enough roboticons
			market.attemptToProduceRoboticon();result=false; // no roboticons can be produced
			
			market.getCostRoboticons(1); result = 5;

			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 200000; // enough money
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.buyRoboticonsFromMarket(anyInt); times=0; // no roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer doesn't attempt to buy a roboticon if it does not have enough money
	 */
	@Test
	public void testBuyRoboticonsNotEnoughMoneyToPurchase(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;
	

			plot1.hasRoboticon(); result = false;
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			
			
			
			market.getCostRoboticons(1); result = 5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 0; // not enough money to purchase
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.buyRoboticonsFromMarket(1); times=0; // no roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyRoboticons} ensures that the AIPlayer doesn't attempt to buy a roboticon if they wouldn't have enough money to customise it afterwards
	 */
	@Test
	public void testBuyRoboticonsNotEnoughMoneyToCustomise(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = null;
	
			plot1.hasRoboticon(); result = false; 
			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			
			market.getCostRoboticons(1); result = 5;
			market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE);result=5;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);result=0; // There are 9 plots
			playerInventory.getMoneyQuantity();result= 9; // enough money to purchase but not to customise afterwards
		}};
		player.buyRoboticons(plotManager);
		new Verifications(){{ 
			market.buyRoboticonsFromMarket(1); times=0; // no roboticons purchased
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer doesn't attempt to place any roboticons if they don't have any customised roboticons to place
	 */
	@Test
	public void testPlaceRoboticonsNoRoboticons(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = player;
	

			plot1.hasRoboticon(); result = false; 
			plot2.hasRoboticon(); result = false; 

			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot2.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=0; // No customised roboticons
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=0;
			
		}};
		player.placeRoboticons(plotManager);
		new Verifications(){{ 
			plotManager.placeEnergyRoboticon(anyInt, anyInt); times=0;
			plotManager.placeOreRoboticon(anyInt, anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer doesn't attempt to place any roboticons if they don't have any empty plots
	 */
	@Test
	public void testPlaceRoboticonsNoEmptyPlots(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = player;
	
			// No empty plots
			plot1.hasRoboticon(); result = true; 
			plot2.hasRoboticon(); result = true;
			
			
		}};
		player.placeRoboticons(plotManager);
		new Verifications(){{ 
			plotManager.placeEnergyRoboticon(anyInt, anyInt); times=0;
			plotManager.placeOreRoboticon(anyInt, anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer only attempts to place energy roboticons if they only have energy roboticons (and have empty plots that specialise in both ore and energy production)
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveEnergyRoboticonsNoOreRoboticonsPlaced(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = player;
	
			plot1.hasRoboticon(); result = false; 
			plot2.hasRoboticon(); result = false; 

			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot2.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=0; 
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=100;
			
		}};
		player.placeRoboticons(plotManager);
		new Verifications(){{ 
			
			plotManager.placeOreRoboticon(anyInt, anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AI player places 3 energy roboticons if they have 3 energy roboticons and 3 empty plots that specialise in energy production
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveEnergyRoboticonsEnergyRoboticonsPlaced(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = player;
	
			plot1.hasRoboticon(); result = false; 
			plot2.hasRoboticon(); result = false; 

			plot1.getSpecialism(); result = PlotSpecialism.ENERGY;
			plot2.getSpecialism(); result = PlotSpecialism.ORE;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=0; 
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=3;
			
		}};
		player.placeRoboticons(plotManager);
		new Verifications(){{ 
			plotManager.placeEnergyRoboticon(anyInt, anyInt); times=3;
		}};
	}
	
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AIPlayer only attempts to place ore roboticons if they only have ore roboticons (and have empty plots that specialise in both ore and energy production)
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveOreRoboticonsNoEnergyRoboticonsPlaced(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = player;
	
			plot1.hasRoboticon(); result = false; 
			plot2.hasRoboticon(); result = false; 

			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot2.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=100; 
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=0;
			
		}};
		player.placeRoboticons(plotManager);
		new Verifications(){{ 
			plotManager.placeEnergyRoboticon(anyInt, anyInt); times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#placeRoboticons} ensures that the AI player places 3 ore roboticons if they have 3 ore roboticons and 3 empty plots that specialise in ore production
	 */
	@Test
	public void testPlaceRoboticonsOnlyHaveOreRoboticonsOreRoboticonsPlaced(){
		
		new Expectations(){{
			plotManager.getPlots(); result = new Plot[][]{
				  { plot1,plot1,plot2 },
				  { plot2,plot2,plot2 },
				  { plot2,plot2,plot1 }
				};
			plot1.getPlayer(); result = player; 
			plot2.getPlayer(); result = player;
	
			plot1.hasRoboticon(); result = false; 
			plot2.hasRoboticon(); result = false; 

			plot1.getSpecialism(); result = PlotSpecialism.ORE;
			plot2.getSpecialism(); result = PlotSpecialism.ENERGY;
			
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ORE);result=100; 
			playerInventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);result=0;
			
		}};
		player.placeRoboticons(plotManager);
		new Verifications(){{ 
			plotManager.placeOreRoboticon(anyInt, anyInt); times=3;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player does not attempt to sell ore if it hasn't got any
	 */
	@Test
	public void testBuyAndSellResourcesNoOreToSell(){
		
		new Expectations(){{
			playerInventory.getOreQuantity();result=0;
		}};
		player.buyAndSellResources();
		new Verifications(){{ 
			market.sellOreToMarket(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player does not attempt to sell energy if it hasn't got any
	 */
	@Test
	public void testBuyAndSellResourcesNoEnergyToSell(){
		
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=0;
		}};
		player.buyAndSellResources();
		new Verifications(){{ 
			market.sellEnergyToMarket(anyInt);times=0;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell one ore if it has exactly one ore in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesOneOre(){
		
		new Expectations(){{
			playerInventory.getOreQuantity();result=1;
		}};
		player.buyAndSellResources();
		new Verifications(){{ 
			market.sellOreToMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell Five ore if it has exactly Five ore in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesFiveOre(){
		
		new Expectations(){{
			playerInventory.getOreQuantity();result=5;
		}};
		player.buyAndSellResources();
		new Verifications(){{ 
			market.sellOreToMarket(5);times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell one energy if it has exactly energy ore in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesOneEnergy(){
		
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=1;
		}};
		player.buyAndSellResources();
		new Verifications(){{ 
			market.sellEnergyToMarket(1);times=1;
		}};
	}
	
	/**
	 * Tests {@link AIPlayer#buyAndSellResources} ensures that the AI player attempts to sell Five energy if it has exactly Five energy in its inventory
	 */
	@Test
	public void testBuyAndSellResourcesFiveEnergy(){
		
		new Expectations(){{
			playerInventory.getEnergyQuantity();result=5;
		}};
		player.buyAndSellResources();
		new Verifications(){{ 
			market.sellEnergyToMarket(5);times=1;
		}};
	}
}
