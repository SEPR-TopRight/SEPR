package com.topright.roboticon;
import java.util.EnumMap;
import java.util.ArrayList;

/**
 * Class to store all player attributes (including their inventory) and perform all the required actions.
 * @author jcn509
 *
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
	
	public int getOreQuantity(){
		return inventory.getOreQuantity();
	}

	public int getEnergyQuantity(){
		return inventory.getEnergyQuantity();
	}

	public int getMoneyQuantity(){
		return inventory.getMoneyQuantity();
	}

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
		int cost = Market.getInstance().getCostOre(quantity);
		if(cost > inventory.getMoneyQuantity()) // Not enough money to complete the purchase.
			return false; 
		else{ // The player has enough money.
			Market.getInstance().buyOre(quantity);
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
		int cost = Market.getInstance().getCostEnergy(quantity);
		if(cost > inventory.getMoneyQuantity()) // Not enough money to complete the purchase.
			return false;
		else{ // The player has enough money.
			Market.getInstance().buyEnergy(quantity);
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
		int cost = Market.getInstance().getCostRoboticons(quantity);
		if(cost > inventory.getMoneyQuantity()) // Not enough money to complete the purchase.
			return false;
		else{ // The player has enough money.
			Market.getInstance().buyRoboticons(quantity);
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
		if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED) == 0)
			return false;
		
		int cost = Market.getInstance().getCostRoboticonCustomisation(customisation);
		
		if(cost > inventory.getMoneyQuantity())
			return false;
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
		if(quantity > inventory.getOreQuantity()) // Cannot sell energy that is not in player's possession.
			return false;
		else{ // The player has enough money.
			Market.getInstance().sellOre(quantity);
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
		if(quantity > inventory.getEnergyQuantity()) // Cannot sell energy that is not in player's possession.
			return false;
		else{ // The player has enough money.
			Market.getInstance().sellEnergy(quantity);
			inventory.decreaseEnergyQuantity(quantity);
			inventory.increaseMoneyQuantity(Market.getInstance().getCostEnergy(quantity));
			return true;
		}
	}
	
	public boolean attemptToPlaceRoboticon(Plot plot, RoboticonCustomisation roboticonCustomisation){
		if(inventory.getRoboticonQuantity(roboticonCustomisation) < 1)
			return false;
		else if(plot.hasRoboticon())
			return false;
		
		inventory.decreaseRoboticonQuantity(roboticonCustomisation, 1);
		plot.setRoboticon(roboticonCustomisation);
		
		return true;
	}
	
	
	
	
	//not yet implemented - not sure if should be here
	public Integer calculateScore(){
		Integer score = inventory.getMoneyQuantity();
		return score;
	}
	/*not yet implemented - not sure if should be here or done in the engine itself.
	public boolean placeRoboticon(Roboticon/RoboticonCusomisation,Plot){
		return something
	}
	 */
	
}
