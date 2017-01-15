package com.topright.roboticon;

/**
 * Implements the AI to allow the computer controlled player to decide what moves to make
 * @author josh
 *
 */
class AIPlayer extends Player{
	
	/**
	 * Constructor
	 * @param inventory The PlayerInventory object that contains all the Player's esources, roboticons and money.
	 */
	public AIPlayer(PlayerInventory inventory){
		super(inventory);
	}
	
	/**
	 * Chooses a plot for the AI to acquire (and then acquires it)
	 * @param plotManager The PlotManager object that contains all the plots
	 */
	public void choosePlot(PlotManager plotManager){
		Plot[][] plots = plotManager.getPlots();
		// There is no reason to choose any one plot over another, so the first unacquired plot is chosen
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!plots[row][column].hasBeenAcquired()){
					plotManager.acquirePlot(row,column);
					return; // Must only acquire one plot
				}
			}
		}
		throw new IllegalStateException("Choose plot called when all plots have been acquired! Must be at least one empty plot!");
	}
	
	private int getTotalNumberOfRoboticons(){
		int numberOfRoboticons=0;
		for(RoboticonCustomisation customisation : RoboticonCustomisation.values()){
			numberOfRoboticons += inventory.getRoboticonQuantity(customisation);
		}
		return numberOfRoboticons;
	}
	
	/**
	 * Causes the player to buy a number of (uncustomised) roboticons
	 * <p>
	 * Decides how many roboticons to buy based upon the number of plots without roboticons on and the number of uncustomised roboticons that the Player is in possession of
	 * </p>	
	 * <p>
	 * Assumes that roboticons are customised right before they are placed. 
	 * Will ensure (as long as the market can supply them and the player has enough money) that the player has the same number of roboticons as they have plots without roboticons on them.
	 * </p>
	 * @param plotManager Needed to determine how many of the Player's plots don't have a roboticon on them
	 */
	public void buyRoboticons(PlotManager plotManager){
		int numberOfEmptyPlots = getNumberOfEmptyPlots(plotManager);
		
		
		int numberOfRoboticonsToBuy = numberOfEmptyPlots - getTotalNumberOfRoboticons();
		
		while(numberOfRoboticonsToBuy > 0){
			
			if(inventory.getMoneyQuantity() < Market.getInstance().getCostRoboticons(1)){// If can't afford any roboticons...
				return; 
			}
			else if(inventory.getMoneyQuantity() - Market.getInstance().getCostRoboticons(1) < Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE)){
				return; // Don't buy a roboticon if can't afford to customise it!
			}
			else if(inventory.getMoneyQuantity() - Market.getInstance().getCostRoboticons(1) < Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)){
				return; // Don't buy a roboticon if can't afford to customise it!
			}
			else if(Market.getInstance().getRoboticonQuantity()<1){ // Attempt to make the market produce a roboticon, if it doesn't have any
				if(!Market.getInstance().attemptToProduceRoboticon())				
					return; // Return if no more roboticons can be produced (and therefor can't be purchased)
			}
			
			if(!attemptToBuyRoboticons(1)){ // Attempt to buy a roboticon
				// If something has gone wrong
				throw new IllegalStateException("The AI has made an invalid move when buying roboticons!");
			}
			
			// If this point is reached then a roboticon must have been purchased
			numberOfRoboticonsToBuy--;
		}
	}
	
	/**
	 * Customises roboticons so that they can later be placed on the Player's plots
	 * <p>
	 * Only (at most) exactly as many roboticons of each customisation type will be produced as the Player has empty plots that specialise in producing the corresponding resource type
	 * </p>
	 * <p>
	 * If the player does not have enough money (or enough uncustomised roboticons) then fewer customised roboticons will be prodcued.
	 * </p>
	 * @param plotManager
	 */
	public void customiseRoboticons(PlotManager plotManager){
		int numberOfEmptyEnergyPlots = getNumberOfEmptyPlots(plotManager,PlotSpecialism.ENERGY); // Energy plots without roboticons
		int numberOfEmptyOrePlots = getNumberOfEmptyPlots(plotManager,PlotSpecialism.ORE); // Ore plots without roboticons
		
		int numberOfEnergyRoboticonsNeeded = numberOfEmptyEnergyPlots - inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);	
		int numberOfOreRoboticonsNeeded = numberOfEmptyOrePlots - inventory.getRoboticonQuantity(RoboticonCustomisation.ORE);

		// Attempt to produce as many ore roboticons as are required
		while(numberOfOreRoboticonsNeeded > 0){
			if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED)<1){ // Have no roboticons to customise
				return; // May as well return as no energy roboticons can be produced either
			}
			else if(inventory.getMoneyQuantity() > Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE)){
				// If the player has enough money for an ore customisation
				if(!attemptToCustomiseRoboticon(RoboticonCustomisation.ORE)){ // Customise a roboticon for ore production
					// If something has gone wrong
					throw new IllegalStateException("The AI has made an invalid move when customising roboticons for ore productio!");
				}
			}
			else{ // If the Player does not have enough money to produce an ore roboticon
				break; // (They may still have enough money to produce energy roboticons)
			}
			
			// If this point has been reached then an ore roboticon has been produced
			numberOfOreRoboticonsNeeded--;
		}
		
		// Attempt to produce as many energy roboticons as are required
		while(numberOfEnergyRoboticonsNeeded > 0){
			if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED)<1){ // Have no roboticons to customise...
				return; // Nothing more can be done
			}
			else if(inventory.getMoneyQuantity() > Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)){
				// If the player has enough money for an energy customisation
				if(!attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY)){ // Customise a roboticon for energy production
					// If something has gone wrong
					throw new IllegalStateException("The AI has made an invalid move when customising roboticons for energy production!");
				}
			}
			else{ // If the Player does not have enough money to produce an ore roboticon
				return; // Nothing more can be done
			}
			
			// If this point has been reached then an energy roboticon has been produced
			numberOfEnergyRoboticonsNeeded--;
		}
	}

	/**
	 * Places all customised roboticons on the Player's plots
	 * <p>
	 * Will place customised roboticons on the plots that they are best suited for (e.g. an ore roboticon will be placed on a plot that specialises in ore production)
	 * </p>
	 * <p>
	 * Keeps placing roboticons on plots until the player runs of out of specialised plots on which to place roboticons or runs our of the associated customised roboticons.
	 * (Will not place roboticons with a given customisation on plots that specialise in the production of a different resource)
	 * </p>
	 * @param plotManager
	 */
	public void placeRoboticons(PlotManager plotManager){
		Plot[][] plots = plotManager.getPlots();
		
		// For every plot in the game
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				// If the plot is owned by the player and doesn't have a roboticon on it
				if(plots[row][column].getPlayer() == this && !plots[row][column].hasRoboticon()){ 
					PlotSpecialism specialism = plots[row][column].getSpecialism();
					
					// If the plots specialises in ore and the player has at least 1 ore roboticon
					if(specialism == PlotSpecialism.ORE && inventory.getRoboticonQuantity(RoboticonCustomisation.ORE) >0){
						plotManager.placeOreRoboticon(column,row);
					}
					// If the plots specialises in energy and the player has at least 1 energy roboticon
					else if(specialism == PlotSpecialism.ENERGY && inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY) >0){
						plotManager.placeEnergyRoboticon(column,row);
					}
				}
			}
		}
	}

	/**
	 * Handles the buying and selling of resources to and from the market
	 */
	public void buyAndSellResources(){
		if(inventory.getEnergyQuantity()>0) // No good reason to keep hold of energy...
			attemptToSellEnergy(inventory.getEnergyQuantity());
		
		if(inventory.getOreQuantity()>0) // Just sell all ore in its possession
			attemptToSellOre(inventory.getOreQuantity());
	}

	/**
	 * Returns the number of empty plots with a given specialism (those without roboticons on them) that the player is in possession of
	 * @param plotManager The PlotManager object that contains all of the plots
	 * @param specialism The desired specialism of the plots
	 * @return The number of plots with the given specialism that the player is in possession of
	 */
	private int getNumberOfEmptyPlots(PlotManager plotManager,PlotSpecialism specialism){
		int numberOfEmptyPlots = 0;
		Plot[][] plots = plotManager.getPlots();
		
		// For each plot
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				// If the player is in possession of the current plot, it has the given specialism and it does not have a roboticon on it
				if(plots[row][column].getPlayer() == this && !plots[row][column].hasRoboticon() && plots[row][column].getSpecialism() == specialism){
					numberOfEmptyPlots++;
				}
			}
		}
		return numberOfEmptyPlots;
	}
	

	/**
	 * Returns the number of empty plots (those without roboticons on them) that the player is in possession of
	 * @param plotManager The PlotManager object that contains all of the plots
	 * @return The number of plots that the player is in possession of
	 */
	private int getNumberOfEmptyPlots(PlotManager plotManager){
		return getNumberOfEmptyPlots(plotManager,PlotSpecialism.ORE) + getNumberOfEmptyPlots(plotManager,PlotSpecialism.ENERGY);
	}

}

