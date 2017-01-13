package com.topright.roboticon;
/**
 * A singleton class used to manage the market allowing players to buy and sell resources to and from the market, buy roboticons and pay to have their roboticons customised
 * @author Ben
 *
 */
public class Market {
	private MarketInventory inventory;
	private int roboticonOreConversionRate = 4; // How many ore are needed to produce a roboticon
	private int oreCost = 5;
	private int energyCost = 6;
	private int uncustomisedRoboticonCost = 10;
	private int energyCustomisationCost = 11;
	private int oreCustomisationCost = 12;
	
	private static Market market = new Market(); 
	
	/**
	 * Constructor
	 * <p>
	 * Private as this is a singleton class so their should exist exactly one instance of the class (i.e. it should not be possible to create other instances)
	 * </p>
	 */
	private Market(){
		// Create with an empty inventory to stop null pointer exceptions when people attempt to use that market without first setting the inventory...
		this.inventory = new MarketInventory(0,0,0);
	} 
	
	/**
	 * Get the instance of this class (it is a singleton class)
	 * @return The only instance of this class
	 */
	public static Market getInstance(){
		return market;
	}
	
	/**
	 * Returns the amount of ore in the market's inventory
	 * @return The amount of ore in the market's inventory
	 */	 
	public int getOreQuantity(){
		return inventory.getOreQuantity();
	}
	
	/**
	 * Returns the amount of energy in the market's inventory
	 * @return The amount of energy in the market's inventory
	 */
	public int getEnergyQuantity(){
		return inventory.getEnergyQuantity();
	}
	
	/**
	 * Returns the number of roboticons in the market's inventory
	 * @return The number of roboticons in the market's inventory
	 */
	public int getRoboticonQuantity(){
		return inventory.getRoboticonQuantity();
	}
	
	/**
	 * Set the MarketInventory object to be used by the market
	 * <p>
	 * The MarketInventory object contains all of the resources and roboticons that the market is in possession of
	 * </p>
	 * @param inventory The MarketInventory object to be used by the market
	 */
	public void setInventory(MarketInventory inventory){
		this.inventory = inventory;
	}
	
	/**
	 * Ensures that the player is not trying to buy more energy than is available and subtracts the amount of energy the player is trying to buy from the market
	 * @param quantity The quantity of energy the player is trying to buy 
	 */
	public void buyEnergy(int quantity){
		if (inventory.getEnergyQuantity() >= quantity){
			inventory.decreaseEnergyQuantity(quantity);
		}
		else{
			throw new IllegalArgumentException("Trying to buy more energy than is available the market has "+Integer.toString(inventory.getEnergyQuantity())+" energy. The player tried to buy "+Integer.toString(quantity));
		}
	}
	
	/**
	 * Ensures that the player is not trying to buy more ore than is available and subtracts the amount of ore the player is trying to buy from the market
	 * @param quantity the quantity of ore the player is trying to buy 
	 */
	public void buyOreFromMarket(int quantity){
		if (inventory.getOreQuantity() >= quantity){
			inventory.decreaseOreQuantity(quantity);
		}
		else{
			throw new IllegalArgumentException("Trying to buy more ore than is available the market has "+Integer.toString(inventory.getOreQuantity())+" ore. The player tried to buy "+Integer.toString(quantity));
		}
	}
		
	/**
	* Adds the amount of Energy the player is trying to sell to the market's total
	* @param quantity the quantity of Energy the player is trying to sell 
	*/
	public void sellEnergyToMarket(int quantity){	
		inventory.increaseEnergyQuantity(quantity);	
	}
	
	/**
	* Adds the amount of Ore the player is trying to sell to he markets total
	* @param quantity The quantity of Ore the player is trying to sell 
	*/
	public void sellOreToMarket(int quantity){
		inventory.increaseOreQuantity(quantity);
	}
		
	/**
	*Ensures that the player is not trying to buy more roboticons than are available and subtracts the number of roboticons the player is trying to buy from the market
	* @param quantity The quantity of Roboticons the player is trying to buy 
	*/
	public void buyRoboticons(int quantity){
		if (inventory.getRoboticonQuantity() >= quantity){
			inventory.decreaseRoboticonQuantity(quantity);
		}
		else{
			throw new IllegalArgumentException("Trying to buy more roboticons than are available the market has "+Integer.toString(inventory.getRoboticonQuantity())+" roboticons. The player tried to buy "+Integer.toString(quantity));
		}
	}
	
	/**
	 * 
	 * Returns the cost of a given quantity of ore
	 * @param quantity The quantity of ore for which the cost is to be calculated
	 * @return The cost of the given quantity of ore
	 */
	public int getCostOre(int quantity){
		return oreCost * quantity;
	}
	
	/**
	 * Returns the cost of a given quantity of energy
	 * @param quantity The quantity of energy for which the cost is to be calculated
	 * @return The cost of the given quantity of energy
	 */
	public int getCostEnergy(int quantity){
		return energyCost * quantity;
	}
	
	/**
	 * Returns the cost of a given number of (uncustomised) roboticons
	 * @param quantity The number of roboticons for which the cost is to be calculated
	 * @return The cost of the given number of roboticons
	 */
	public int getCostRoboticons(int quantity){
		return uncustomisedRoboticonCost * quantity;
	}
	
	/**
	 * If the market has enough ore it will produce another roboticon (at the cost of ore)
	 * @return true if a roboticon was produced and false if not
	 */
	public boolean attemptToProduceRoboticon(){
		
		// If the market has enough ore to produce a roboticon
		if(inventory.getOreQuantity() >= roboticonOreConversionRate){ 
			inventory.increaseRoboticonQuantity(1);
			inventory.decreaseOreQuantity(roboticonOreConversionRate);
			return true;
		}
		return false;
	}		
	
	/**
	 * Returns the cost of applying a given customisation to a roboticon
	 * @param customisation the type of roboticon the market should produce
	 * @return The cost of applying a given customisation to a roboticon
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
