package com.topright.roboticon;
import java.util.EnumMap;
import java.util.ArrayList;

/**
 * Class to store all player attributes (including their inventory) and perform all the required actions.
 * @author jcn509
 *
 */
public class Player {
	public PlayerInventory inventory; // Public as classes may need to access the inventory for random events etc.
	//private int score; may not be needed may just have method to calculate the score?
	
	/**
	 * Constructor.
	 * @param oreQuantity The quantity of ore initially in the players' inventory.
	 * @param energyQuantity The quantity of energy initially in the player's inventory.
	 * @param roboticonQuantities The quantity of (uncustomised roboticons) initially in the players inventory.
	 * @param moneyQuantity The amount of money initially in the player's inventory.
	 */
	public Player(PlayerInventory inventory){
		this.inventory = inventory;
		//score = 0;
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
	 * @param market The market object from which the player is purchasing ore.
	 * @param quantity The amount of ore that the player wants to purchase.
	 * @return A boolean value: true if the purchase was successful and false if not.
	 */
	public boolean attemptToBuyOre(Market market, int quantity){
		int cost = market.getCostOre(quantity);
		if(cost > inventory.getMoneyQuantity()) // Not enough money to complete the purchase.
			return false; 
		else{ // The player has enough money.
			market.buyOre(quantity);
			inventory.increaseOreQuantity(quantity);
			inventory.decreaseMoneyQuantity(cost);
			return true;
		}
	}
	
	/**
	 * Attempt to purchase ore from the market (checks to see if the purchase is possible, if it is then the purchase is completed).
	 * <p>
	 * First finds out how much the energy will cost and checks to ensure that the player has enough money in their inventory.
	 * If the player has enough money the quantities of energy in the player's and market's inventory are updated and money is taken from the player's inventory.
	 * </p>
	 * <p>
	 * Assumes that the market has enough stock (handled elsewhere).
	 * </p>
	 * {@link Player#inventory}
	 * @param market The market object from which the player is purchasing energy.
	 * @param quantity The amount of energy that the player wants to purchase.
	 * @return A boolean value: true if the purchase was successful and false if not.
	 */
	public boolean attemptToBuyEnergy(Market market, int quantity){
		int cost = market.getCostEnergy(quantity);
		if(cost > inventory.getMoneyQuantity()) // Not enough money to complete the purchase.
			return false;
		else{ // The player has enough money.
			market.buyEnergy(quantity);
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
	 * @param market The market object from which the player is purchasing roboticons.
	 * @param quantity The the number of roboticons that the player wants to purchase.
	 * @return A boolean value: true if the purchase was successful and false if not.
	 */
	public boolean attemptToBuyRoboticons(Market market, int quantity){
		int cost = market.getCostRoboticons(quantity);
		if(cost > inventory.getMoneyQuantity()) // Not enough money to complete the purchase.
			return false;
		else{ // The player has enough money.
			market.buyRoboticons(quantity);
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
	 * @param market The market object through which the customisation is taking placed.
	 * @param customisation The customisation that the player wishes to apply to a roboticon {@link RoboticonCustomisation#RoboticonCustomisation}
	 * @return A boolean value: true if the customisation was carried out false if it was not.
	 */
	public boolean attemptToCustomiseRoboticon(Market market,RoboticonCustomisation customisation){
		if(inventory.getRoboticonQuantity(RoboticonCustomisation.UNCUSTOMISED) == 0)
			return false;
		
		int cost = market.getCostRoboticonCustomisation(customisation);
		
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
	 * @param market The market object to which the player is selling ore.
	 * @param quantity The amount of ore that the player wants to sell.
	 * @return A boolean value: true if the sale was successful and false if not.
	 */
	public boolean attemptToSellOre(Market market, int quantity){
		if(quantity > inventory.getOreQuantity()) // Cannot sell energy that is not in player's possession.
			return false;
		else{ // The player has enough money.
			market.sellOre(quantity);
			inventory.decreaseOreQuantity(quantity);
			inventory.increaseMoneyQuantity(market.getCostOre(quantity));
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
	 * @param market The market object to which the player is selling energy.
	 * @param quantity The amount of energy that the player wants to sell.
	 * @return A boolean value: true if the sale was successful and false if not.
	 */
	public boolean attemptToSellEnergy(Market market, int quantity){
		if(quantity > inventory.getEnergyQuantity()) // Cannot sell energy that is not in player's possession.
			return false;
		else{ // The player has enough money.
			market.sellEnergy(quantity);
			inventory.decreaseEnergyQuantity(quantity);
			inventory.increaseMoneyQuantity(market.getCostEnergy(quantity));
			return true;
		}
	}
	
	
	
	
	//not yet implemented - not sure if should be here
	public int calculateScore(){
		return 0;
	}
	/*not yet implemented - not sure if should be here or done in the engine itself.
	public boolean placeRoboticon(Roboticon/RoboticonCusomisation,Plot){
		return something
	}
	 */
	
}
