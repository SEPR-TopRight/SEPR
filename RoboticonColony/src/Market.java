/**
 * A class used to manage the market
 * @author Ben
 *
 */

public class Market {
	public MarketInventory mark_inventory; // public for now, random events etc
                                           // no point creating other methdso 
	
	
	/**
	 * Constructor
	 * @param oreQuantity The initial quantity of ore to be stored in the market's inventory.
	 * @param energyQuantity The initial quantity of ore to be stored in the market's inventory.
	 * @param RoboticonQuantity The initial quantity of ore to be stored in the market's inventory.
	 */
	public Market(int oreQuantity, int energyQuantity, int RoboticonQuantity){
		mark_inventory = new MarketInventory(oreQuantity,energyQuantity,RoboticonQuantity);
	}
	
	/*
	/**
	 * 
	 * Returns the amount of ore in the market's inventory
	 
	public int getOreQuantity(){
		
		return mark_inventory.getOreQuantity();
	}
	/**
	 * 
	 * Returns the amount of energy in the market's inventory
	 
	public int getEnergyQuantity(){
		
		return mark_inventory.getEnergyQuantity();
	}
	/**
	 * 
	 * Returns the amount of Roboticons in the market's inventory
	 
	public int getRoboticonQuantity(){
	
	return mark_inventory.getRoboticonQuantity();
}
	*/
	
	
	
	/**
	 * 
	 * Subtracts the amount of ore the player is trying to buy from the market and performs validation checks
	 * @param quantity the quantity of ore the player is trying to buy 
	 */
		public void buyOre(int quantity){
			if (mark_inventory.getOreQuantity() >= quantity){
			mark_inventory.decreaseOreQuantity(quantity);
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
			
			mark_inventory.increaseEnergyQuantity(quantity);
			
		}
		/**
		 * 
		 * adds the amount of Ore the player is trying to sell to he markets total
		 * @param quantity the quantity of Ore the player is trying to sell 
		 */
		public void sellOre(int quantity){
			
			mark_inventory.increaseOreQuantity(quantity);
			
		}
		/**
		 * 
		 * Subtracts the amount of Energy the player is trying to buy from the market and performs validation checks
		 * @param quantity the quantity of Energy the player is trying to buy 
		 */
		public void buyEnergy(int quantity){
			if (mark_inventory.getEnergyQuantity() >= quantity){
			mark_inventory.decreaseEnergyQuantity(quantity);
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
			if (mark_inventory.getRoboticonQuantity() >= quantity){
			mark_inventory.decreaseRoboticonQuantity(quantity);
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
			return 5 * quantity;
			//TO DO change this when we know more
		}
		/**
		 * 
		 * multiplies the base cost of Energy by the amount the player is trying to buy
		 * @param quantity the quantity of Energy the player is trying to buy
		 */
		public int getCostEnergy(int quantity){
			return 5 * quantity;
			//TO DO change this when we know more
		}
		/**
		 * 
		 * multiplies the base cost of Roboticons by the amount the player is trying to buy
		 * @param quantity the quantity of Roboticons the player is trying to buy
		 */
		public int getCostRoboticons(int quantity){
			return 10 * quantity;
			//TO DO change this when we know more
		}
		/**
		 * 
		 * returns the cost of customising a roboticon
		 * @param customisation the type of roboticon the market should produce
		 */
		public int getCostRoboticonCustomisation(RoboticonCustomisation customisation){
			return 10;
			//TO DO change this when have roboticon method
		}
		/**
		 * 
		 * allows the player to produce roboticons from ore
		 * @param quantity the quantity of roboticons the player wishes to produce
		 */
		public void ProduceRoboticon(int quantity){
			if (mark_inventory.getOreQuantity() >= quantity){
				mark_inventory.increaseRoboticonQuantity(quantity);
				mark_inventory.decreaseOreQuantity(quantity);
			}
			else {
				throw new IllegalStateException("Must have ore to produce roboticons");
			}
		}
}

