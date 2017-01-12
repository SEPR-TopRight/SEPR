package com.topright.roboticon;
/**
 * A class used to manage the market
 * @author Ben
 *
 */

public class Market {
	private MarketInventory inventory; // public as other classes need access
	private int roboticonOreCost = 4; // How many ore needed to produce a roboticon
	private int oreCost = 5;
	private int energyCost = 6;
	private int uncustomisedRoboticonCost = 10;
	private int energyCustomisationCost = 11;
	private int oreCustomisationCost = 12;
	private static Market market = new Market(new MarketInventory(0,0,0)); 
	// Create with an empty inventory to stop null pointer exceptions when people attempt to use that market without first setting the inventory...
	
	private Market(MarketInventory inventory){this.inventory = inventory;} // Don't allow any other instances of this class to be created
	
	public static Market getInstance(){
		return market;
	}
	
	/**
	 * 
	 * Returns the amount of ore in the market's inventory
	 */
	 
	public int getOreQuantity(){
		
		return inventory.getOreQuantity();
	}
	/**
	 * 
	 * Returns the amount of energy in the market's inventory
	 */
	 
	public int getEnergyQuantity(){
		return inventory.getEnergyQuantity();
	}
	/**
	 * 
	 * Returns the amount of Roboticons in the market's inventory
	 */
	 
	public int getRoboticonQuantity(){
		return inventory.getRoboticonQuantity();
	}
	
	public void setInventory(MarketInventory inventory){
		this.inventory = inventory;
	}
	
	
	
	/**
	 * 
	 * Subtracts the amount of ore the player is trying to buy from the market and performs validation checks
	 * @param quantity the quantity of ore the player is trying to buy 
	 */
		public void buyOre(int quantity){
			if (inventory.getOreQuantity() >= quantity){
				inventory.decreaseOreQuantity(quantity);
			}
			else{
				throw new IllegalStateException("Trying to buy more ore than is available");

			}
		}
		/**
		 * 
		 * adds the amount of Energy the player is trying to sell to he markets total
		 * @param quantity the quantity of Energy the player is trying to sell 
		 */
		public void sellEnergy(int quantity){
			
			inventory.increaseEnergyQuantity(quantity);
			
		}
		/**
		 * 
		 * adds the amount of Ore the player is trying to sell to he markets total
		 * @param quantity the quantity of Ore the player is trying to sell 
		 */
		public void sellOre(int quantity){
			
			inventory.increaseOreQuantity(quantity);
			
		}
		/**
		 * 
		 * Subtracts the amount of Energy the player is trying to buy from the market and performs validation checks
		 * @param quantity the quantity of Energy the player is trying to buy 
		 */
		public void buyEnergy(int quantity){
			if (inventory.getEnergyQuantity() >= quantity){
				inventory.decreaseEnergyQuantity(quantity);
			}
			else{
				throw new IllegalStateException("Trying to buy more Energy than is available");
			}
		}
		/**
		 * 
		 * Subtracts the amount of Roboticons the player is trying to buy from the market and performs validation checks
		 * @param quantity the quantity of Roboticons the player is trying to buy 
		 */
		public void buyRoboticons(int quantity){
			if (inventory.getRoboticonQuantity() >= quantity){
			inventory.decreaseRoboticonQuantity(quantity);
			//TO DO finish when have robotion class
			}
			else{
				throw new IllegalStateException("Trying to buy more Roboticons than is available");
			}
		}
		/**
		 * 
		 * multiplies the base cost of ore by the amount the player is trying to buy
		 * @param quantity the quantity of ore the player is trying to buy
		 */
		public int getCostOre(int quantity){
			return oreCost * quantity;
			//TO DO change this when we know more
		}
		/**
		 * 
		 * multiplies the base cost of Energy by the amount the player is trying to buy
		 * @param quantity the quantity of Energy the player is trying to buy
		 */
		public int getCostEnergy(int quantity){
			return energyCost * quantity;
			//TO DO change this when we know more
		}
		/**
		 * 
		 * multiplies the base cost of Roboticons by the amount the player is trying to buy
		 * @param quantity the quantity of Roboticons the player is trying to buy
		 */
		public int getCostRoboticons(int quantity){
			return uncustomisedRoboticonCost * quantity;
			//TO DO change this when we know more
		}
		
		/**
		 * 
		 * If the market has enough ore it will produce another roboticon (at the cost of ore)
		 * @return true if a roboticon was produced and false if not
		 */
		public boolean attemptToProduceRoboticon(){
			if(inventory.getOreQuantity() >= roboticonOreCost){
				inventory.increaseRoboticonQuantity(1);
				inventory.decreaseOreQuantity(roboticonOreCost);
				return true;
			}
			return false;
		}		
		
		/**
		 * 
		 * returns the cost of customising a roboticon
		 * @param customisation the type of roboticon the market should produce
		 */
		public int getCostRoboticonCustomisation(RoboticonCustomisation customisation){
			switch (customisation){
				case ORE:
					return oreCustomisationCost;
				case ENERGY:
					return energyCustomisationCost;
				default:
					throw new IllegalArgumentException("Invalid customisation type " + customisation);
			}
		}
}
