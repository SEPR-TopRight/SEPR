package com.topright.roboticon;

class AIPlayer extends Player{
	public AIPlayer(PlayerInventory inventory){
		super(inventory);
	}
	
	
	public void choosePlot(PlotManager plotManager){
		Plot[][] plots = plotManager.getPlots();
		// There is no reason to choose any one plot over another, so the first anacquired plot is chosen
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!plots[row][column].hasBeenAquired()){
					plotManager.aquirePlot(row,column);
					return; // Must only aquire one plot
				}
			}
		}
	}

	// Assume always customise then instantly place so that we don't need to see how many customised roboticons we have
	public void buyRoboticons(PlotManager plotManager){
		int numberOfEmptyPlots = getNumberOfEmptyPlots(plotManager);
		int numberOfRoboticonsToBuy = numberOfEmptyPlots - inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		while(numberOfRoboticonsToBuy > 0){
			if(inventory.getMoneyQuantity() < Market.getInstance().getCostRoboticons(1))
				return; // Can't afford any roboticons...
			else if(inventory.getMoneyQuantity() - Market.getInstance().getCostRoboticons(1) < Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE))
				return; // Don't buy a roboticon if can't afford to customise it!
			else if(inventory.getMoneyQuantity() - Market.getInstance().getCostRoboticons(1) < Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY))
				return; // Don't buy a roboticon if can't afford to customise it!
			else if(Market.getInstance().getRoboticonQuantity()<1){
				if(!Market.getInstance().attemptToProduceRoboticon())				
					return;
			}
			
			if(!attemptToBuyRoboticons(1))
				System.out.println("The AI has made a mistake!"); // Throw exception....
			
			numberOfRoboticonsToBuy--;
		}
	}
	
	public void customiseRoboticons(PlotManager plotManager){
		int numberOfEmptyEnergyPlots = getNumberOfEmptyPlots(plotManager,PlotSpecialism.ENERGY);
		int numberOfEmptyOrePlots = getNumberOfEmptyPlots(plotManager,PlotSpecialism.ORE);
		
		int numberOfEnergyRoboticonsNeeded = numberOfEmptyEnergyPlots - inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);	
		int numberOfOreRoboticonsNeeded = numberOfEmptyOrePlots - inventory.getRoboticonQuantity(RoboticonCustomisation.ORE);

		while(numberOfOreRoboticonsNeeded > 0){
			if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED)<1) // Have no roboticons to customise...
				return;
			else if(inventory.getMoneyQuantity() > Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ORE)){
				if(!attemptToCustomiseRoboticon(RoboticonCustomisation.ORE))
					System.out.println("The AI has made a mistake!"); // Throw exception....
			}
			else
				break;
			numberOfOreRoboticonsNeeded--;
		}
		
		while(numberOfEnergyRoboticonsNeeded > 0){
			if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED)<1) // Have no roboticons to customise...
				return;
			else if(inventory.getMoneyQuantity() > Market.getInstance().getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)){
				if(!attemptToCustomiseRoboticon(RoboticonCustomisation.ENERGY))
					System.out.println("The AI has made a mistake!"); // Throw exception....
			}
			else
				break;
			numberOfEnergyRoboticonsNeeded--;
		}
	}

	public void placeRoboticons(PlotManager plotManager){
		Plot[][] plots = plotManager.getPlots();
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].getPlayer() == this && !plots[row][column].hasRoboticon()){
					PlotSpecialism specialism = plots[row][column].getSpecialism();
					if(specialism == PlotSpecialism.ORE && inventory.getRoboticonQuantity(RoboticonCustomisation.ORE) >0){
						plotManager.placeOreRoboticon(column,row);
					}
					else if(specialism == PlotSpecialism.ENERGY && inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY) >0){
						plotManager.placeEnergyRoboticon(column,row);
					}
				}
			}
		}
	}

	public void buyAndSellResources(){
		if(inventory.getEnergyQuantity()>0) // No good reason to keep hold of energy...
			attemptToSellEnergy(inventory.getEnergyQuantity());
		
		if(inventory.getOreQuantity()>0) // Just sell all ore in its possession
			attemptToSellOre(inventory.getOreQuantity());
	}

	private int getNumberOfEmptyPlots(PlotManager plotManager,PlotSpecialism specialism){
		int numberOfEmptyPlots = 0;
		Plot[][] plots = plotManager.getPlots();
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].getPlayer() == this && !plots[row][column].hasRoboticon() && plots[row][column].getSpecialism() == specialism){
					numberOfEmptyPlots++;
				}
			}
		}
		return numberOfEmptyPlots;
	}
	

	private int getNumberOfEmptyPlots(PlotManager plotManager){
		return getNumberOfEmptyPlots(plotManager,PlotSpecialism.ORE) + getNumberOfEmptyPlots(plotManager,PlotSpecialism.ENERGY);
	}

}

