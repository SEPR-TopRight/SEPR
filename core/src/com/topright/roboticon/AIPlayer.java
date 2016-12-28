package com.topright.roboticon;

class AIPlayer extends Player{
	public AIPlayer(PlayerInventory inventory){
		super(inventory);
	}
	
	public void choosePlot(PlotManager plotManager){
		Plot[][] plots = plotManager.getPlots();
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(!plots[row][column].hasBeenAquired()){
					plotManager.aquirePlot(row,column); // Simply choose the first unaquired plot...
					return; // must only aquire one plot...
				}
			}
		}
	}

	// Assume always customise then instantly place so that we don't need to see how many customised roboticons we have
	public void buyRoboticons(Market market, PlotManager plotManager){
		int numberOfEmptyPlots = getNumberOfEmptyPlots(plotManager);
		int numberOfRoboticonsToBuy = numberOfEmptyPlots - inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED);
		while(numberOfRoboticonsToBuy > 0){
			if(inventory.getMoneyQuantity() < market.getCostRoboticons(1))
				return; // Can't afford any roboticons...
			else if(inventory.getMoneyQuantity() - market.getCostRoboticons(1) < market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE))
				return; // Don't buy a roboticon if can't afford to customise it!
			else if(inventory.getMoneyQuantity() - market.getCostRoboticons(1) < market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY))
				return; // Don't buy a roboticon if can't afford to customise it!
			else if(market.mark_inventory.getRoboticonQuantity()<1){
				if(!market.attemptToProduceRoboticon())				
					return;
			}
			
			if(!attemptToBuyRoboticons(market,1))
				System.out.println("The AI has made a mistake!"); // Throw exception....
			
			numberOfRoboticonsToBuy--;
		}
	}
	
	public void customiseRoboticons(Market market, PlotManager plotManager){
		int numberOfEmptyEnergyPlots = getNumberOfEmptyPlots(plotManager,"energy");
		int numberOfEmptyOrePlots = getNumberOfEmptyPlots(plotManager,"ore");
		
		int numberOfEnergyRoboticonsNeeded = numberOfEmptyEnergyPlots - inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY);	
		int numberOfOreRoboticonsNeeded = numberOfEmptyOrePlots - inventory.getRoboticonQuantity(RoboticonCustomisation.ORE);

		while(numberOfOreRoboticonsNeeded > 0){
			if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED)<1) // Have no roboticons to customise...
				return;
			else if(inventory.getMoneyQuantity() > market.getCostRoboticonCustomisation(RoboticonCustomisation.ORE)){
				if(!attemptToCustomiseRoboticon(market, RoboticonCustomisation.ORE))
					System.out.println("The AI has made a mistake!"); // Throw exception....
			}
			else
				break;
			numberOfOreRoboticonsNeeded--;
		}
		
		while(numberOfEnergyRoboticonsNeeded > 0){
			if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED)<1) // Have no roboticons to customise...
				return;
			else if(inventory.getMoneyQuantity() > market.getCostRoboticonCustomisation(RoboticonCustomisation.ENERGY)){
				if(!attemptToCustomiseRoboticon(market, RoboticonCustomisation.ENERGY))
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
					String specialism = plots[row][column].getBest();
					if(specialism == "ore" && inventory.getRoboticonQuantity(RoboticonCustomisation.ORE) >0){
						plotManager.placeOreRoboticon(column,row);
					}
					else if(specialism == "energy" && inventory.getRoboticonQuantity(RoboticonCustomisation.ENERGY) >0){
						plotManager.placeEnergyRoboticon(column,row);
					}
				}
			}
		}
	}

	public void buyAndSellResources(Market market){
		if(inventory.getEnergyQuantity()>0) // No good reason to keep hold of energy...
			attemptToSellEnergy(market, inventory.getEnergyQuantity());
		if(inventory.getOreQuantity()>0) // for now, just sell all ore in its possession
			attemptToSellOre(market, inventory.getOreQuantity());
	}

	private int getNumberOfEmptyPlots(PlotManager plotManager,String specialism){
		int numberOfEmptyPlots = 0;
		Plot[][] plots = plotManager.getPlots();
		for(int row=0;row<plots.length;row++){
			for(int column=0;column<plots[0].length;column++){
				if(plots[row][column].getPlayer() == this && !plots[row][column].hasRoboticon() && plots[row][column].getBest() == specialism){
					numberOfEmptyPlots++;
				}
			}
		}
		return numberOfEmptyPlots;
	}
	

	private int getNumberOfEmptyPlots(PlotManager plotManager){
		return getNumberOfEmptyPlots(plotManager,"ore") + getNumberOfEmptyPlots(plotManager,"energy");
	}

}

