package com.topright.roboticon;

/**
 * Class to store all player attributes (including their inventory) and handle the buying and selling of resources and the buying and customisation of roboticons
 * @author jcn509
 */
public class Player {
	protected PlayerInventory inventory;
	
	/**
	 * Constructor.
	 * @param inventory A PlayerInventory object that contains all money, resources and roboticons that the player is initially in possession of.
	 */
	public Player(PlayerInventory inventory){
		this.inventory = inventory;
	}
	
	/**
	 * Returns the quantity of ore that the player is in possession of
	 * @return The quantity of ore that the player is in possession of
	 */
	public int getOreQuantity(){
		return inventory.getOreQuantity();
	}

	/**
	 * Returns the quantity of energy that the player is in possession of
	 * @return The quantity of energy that the player is in possession of
	 */
	public int getEnergyQuantity(){
		return inventory.getEnergyQuantity();
	}

	/**
	 * Returns the amount of money that the player is in possession of
	 * @return The amount of money that the player is in possession of
	 */
	public int getMoneyQuantity(){
		return inventory.getMoneyQuantity();
	}

	/**
	 * Returns the number of roboticons of a given customisation that the player is in possession of
	 * @param customisation The customisation type of the roboticons
	 * @return The number of roboticons of a given customisation that the player is in possession of
	 */
	public int getRoboticonQuantity(RoboticonCustomisation customisation){
		return inventory.getRoboticonQuantity(customisation);
	}
	
	/**
	 * Attempt to purchase ore from the market (checks to see if the purchase is possible, if it is then the purchase is completed).
	 * <p>
	 * First finds out how much the ore will cost and checks to ensure that the player has enough money in their inventory.
	 * If the player has enough money the quantities of ore in the player's and market's inventory are updated and money is taken from the player's inventory.
	 * </p>
	 * <p>
	 * Assumes that the market has enough stock (handled elsewhere).
	 * </p>
	 * {@link Player#inventory}
	 * @param quantity The amount of ore that the player wants to purchase.
	 * @return A boolean value: true if the purchase was successful and false if not.
	 */
	public boolean attemptToBuyOre(int quantity){
		if(quantity == 0){
			return true; // Don't want to do anything
		}
		else if(quantity<0){
			return false; // Invalid use of the method
		}
		
		int cost = Market.getInstance().getCostOre(quantity);
		if(cost > inventory.getMoneyQuantity()){ // Not enough money to complete the purchase.
			return false; 
		}
		else{ // The player has enough money.
			Market.getInstance().buyOreFromMarket(quantity);
			inventory.increaseOreQuantity(quantity);
			inventory.decreaseMoneyQuantity(cost);
			return true;
		}
	}
	
	/**
	 * Attempt to purchase energy from the market (checks to see if the purchase is possible, if it is then the purchase is completed).
	 * <p>
	 * First finds out how much the energy will cost and checks to ensure that the player has enough money in their inventory.
	 * If the player has enough money the quantities of energy in the player's and market's inventory are updated and money is taken from the player's inventory.
	 * </p>
	 * <p>
	 * Assumes that the market has enough stock (handled elsewhere).
	 * </p>
	 * {@link Player#inventory}
	 * @param quantity The amount of energy that the player wants to purchase.
	 * @return A boolean value: true if the purchase was successful and false if not.
	 */
	public boolean attemptToBuyEnergy(int quantity){
		if(quantity == 0){
			return true; // Don't want to do anything
		}
		else if(quantity<0){
			return false; // Invalid use of the method
		}
		
		int cost = Market.getInstance().getCostEnergy(quantity);
		if(cost > inventory.getMoneyQuantity()){ // Not enough money to complete the purchase.
			return false;
		}
		else{ // The player has enough money.
			Market.getInstance().buyEnergyFromMarket(quantity);
			inventory.increaseEnergyQuantity(quantity);
			inventory.decreaseMoneyQuantity(cost);
			return true;
		}
	}
	
	/**
	 * Attempt to purchase roboticons from the market (checks to see if the purchase is possible, if it is then the purchase is completed).
	 * <p>
	 * First finds out how much the roboticons will cost and checks to ensure that the player has enough money in their inventory.
	 * If the player has enough money the quantities of uncustomised roboticons in the player's inventory and roboticons in the market's inventory are updated and money is taken from the player's inventory.
	 * </p>
	 * <p>
	 * Assumes that the market has enough stock (handled elsewhere).
	 * </p>
	 * {@link Player#inventory}
	 * @param quantity The the number of roboticons that the player wants to purchase.
	 * @return A boolean value: true if the purchase was successful and false if not.
	 */
	public boolean attemptToBuyRoboticons(int quantity){
		if(quantity == 0){
			return true; // Don't want to do anything
		}
		else if(quantity<0){
			return false; // Invalid use of the method
		}
		
		int cost = Market.getInstance().getCostRoboticons(quantity);
		if(cost > inventory.getMoneyQuantity()){ // Not enough money to complete the purchase.
			return false;
		}
		else{ // The player has enough money.
			Market.getInstance().buyRoboticonsFromMarket(quantity);
			inventory.increaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED,quantity);
			inventory.decreaseMoneyQuantity(cost);
			return true;
		}
	}
	
	/**
	 * Attempt to customise a roboticon in the players inventory (if the customisation is possible then it is carried out).
	 * <p>
	 * First checks to see if the player has any uncustomsied roboticons.
	 * If they do then the cost is fetched from the market and a check is performed to see if the player has enough money.
	 * If the player has enough money then the purchase is completed and the players inventory updated (1 less uncustomised roboticon and 1 more roboticon with the desired customisation).
	 * </p>
	 * @param customisation The customisation that the player wishes to apply to a roboticon {@link RoboticonCustomisation#RoboticonCustomisation}
	 * @return A boolean value: true if the customisation was carried out false if it was not.
	 */
	public boolean attemptToCustomiseRoboticon(RoboticonCustomisation customisation){
		
		// No roboticons to customise
		if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED) == 0){
			return false;
		}
		
		int cost = Market.getInstance().getCostRoboticonCustomisation(customisation);
		
		if(cost > inventory.getMoneyQuantity()){ // Not enough money to purchase the customisation
			return false;
		}
		else{
			inventory.decreaseRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED, 1);
			inventory.decreaseMoneyQuantity(cost);
			inventory.increaseRoboticonQuantity(customisation, 1);
			return true;
		}
	}
	
	/**
	 * Attempt to sell ore to the market (checks to see if the sale is possible, if it is then the sale is completed).
	 * <p>
	 * First checks how much ore the player has in their inventory.
	 * If the player has enough ore the quantities of ore in the player's and market's inventory are updated and money is added to the player's inventory.
	 * </p>
	 * {@link Player#inventory}
	 * @param quantity The amount of ore that the player wants to sell.
	 * @return A boolean value: true if the sale was successful and false if not.
	 */
	public boolean attemptToSellOre(int quantity){
		if(quantity == 0){
			return true; // Don't want to do anything
		}
		else if(quantity<0){
			return false; // Invalid use of the method
		}
		
		if(quantity > inventory.getOreQuantity()){ // Cannot sell ore that is not in player's possession.
			return false;
		}
		else{ // The player has enough ore
			Market.getInstance().sellOreToMarket(quantity);
			inventory.decreaseOreQuantity(quantity);
			inventory.increaseMoneyQuantity(Market.getInstance().getCostOre(quantity));
			return true;
		}
	}
	
	/**
	 * Attempt to sell energy to the market (checks to see if the sale is possible, if it is then the purchase is completed).
	 * <p>
	 * First checks to see if the player has enough energy in their inventory
	 * If the player has enough energy the quantities of energy in the player's and market's inventory are updated and money is added to the player's inventory.
	 * </p>
	 * <p>
	 * {@link Player#inventory}
	 * @param quantity The amount of energy that the player wants to sell.
	 * @return A boolean value: true if the sale was successful and false if not.
	 */
	public boolean attemptToSellEnergy(int quantity){
		if(quantity == 0){
			return true; // Don't want to do anything
		}
		else if(quantity<0){
			return false; // Invalid use of the method
		}
		
		if(quantity > inventory.getEnergyQuantity()){ // Cannot sell energy that is not in player's possession.
			return false;
		}
		else{ // The player has enough energy
			Market.getInstance().sellEnergyToMarket(quantity);
			inventory.decreaseEnergyQuantity(quantity);
			inventory.increaseMoneyQuantity(Market.getInstance().getCostEnergy(quantity));
			return true;
		}
	}
	
	/**
	 * Tries to place a roboticon of a given customisation type on a given plot
	 * <p>
	 * Will fail to do so if:
	 *   The player does not have a roboticon of the given customisation
	 *   The plot already has a roboticon on it
	 *   the plot is not owned by this player
	 * </p>
	 * @param plot
	 * @param roboticonCustomisation
	 * @return true if a roboticon was placed and false otherwise
	 */
	public boolean attemptToPlaceRoboticon(Plot plot, RoboticonCustomisation roboticonCustomisation){
		if(roboticonCustomisation == RoboticonCustomisation.UNCUSTOMISED){
			return false; // Can't place uncustomised roboticons
		}
		else if(inventory.getRoboticonQuantity(roboticonCustomisation) < 1){
			return false;
		}
		else if(plot.hasRoboticon()){
			return false;
		}
		else if(plot.getPlayer()!=this){ // Plot not owned by this player
			return false;
		}
		
		inventory.decreaseRoboticonQuantity(roboticonCustomisation, 1);
		plot.setRoboticonCustomisation(roboticonCustomisation);
		
		return true;
	}
	
	/**
	 * Calculates and returns the Player's score
	 * @return The Player's score
	 */
	public Integer calculateScore(){
		Integer score = inventory.getMoneyQuantity();
		return score;
	}
	
}
