package com.topright.roboticon;
import java.util.EnumMap;

/**
 * A data structure to store the contents of a player's inventory.
 * Keeps track of:
 * 	The number of units of ore the player is in possession of
 * 	The number of units of energy the player is in possession of
 * 	The number of roboticons with a specific customisation (or no customisation) that the player is in possession of
 * <p>
 * @author jcn509
 */

public class PlayerInventory extends Inventory{
	private EnumMap<RoboticonCustomisation, Integer> roboticons = new EnumMap<RoboticonCustomisation, Integer>(RoboticonCustomisation.class);
	private int moneyQuantity;
	/**
	 * Constructor.
	 * @param oreQuantity The initial quantity of ore stored in the inventory.
	 * @param energyQuantity The initial quantity of energy stored in the inventory.
	 * @param roboticons Maps The customisation type of roboticons onto the initial quantity of each roboticon of that type to be stored in the inventory.
	 */
	public PlayerInventory(int oreQuantity, int energyQuantity, EnumMap<RoboticonCustomisation, Integer> roboticons, int moneyQuantity){
		super(oreQuantity,energyQuantity);
		initialiseRoboticonQuantities(roboticons);
		initialiseMoneyQuantity(moneyQuantity);
	}
	/**
	 * Initialise the amount of money in the players inventory.
	 * <p>
	 * Ensures that the initial amount of money is positive.
	 * </p>
	 * @param moneyQuantity The amount of money to be placed in the player's inventory.
	 */
	private void initialiseMoneyQuantity(int moneyQuantity){
		if(moneyQuantity<0) // Cannot have a negative quantity of money.
			throw new IllegalArgumentException("moneyQuantity must be >= 0");
		else
			this.moneyQuantity = moneyQuantity;
	}
	/**
	 * Initialise the quantities of roboticons with given customisations.
	 * <p> 
	 * Ensures that the quantity of roboticons (for any customisation is not negative).
	 * </p>
	 * @param roboticons Maps the customisation type of roboticons onto the initial quantity of each roboticon of that type to be stored in the inventory
	 */
	private void initialiseRoboticonQuantities(EnumMap<RoboticonCustomisation, Integer> roboticons){
		for (EnumMap.Entry<RoboticonCustomisation, Integer> entry : roboticons.entrySet()) // For each type of roboticon
		{
		    if(entry.getValue()<0) // Player may not have a negative quantity of any type of roboticon.
		    	throw new IllegalArgumentException(entry.getKey().name()+ " is mapped to a value less than 0 in the roboticons map");
		}
		this.roboticons = roboticons;
	}
	/**
	 * Adds roboticons with the specified customisation type to the inventory.
	 * <p>
	 * Ensures that that that number of roboticons to bed added is not negative.
	 * </p>
	 * @param customisation The customisation type of the roboticons to be added to the inventory.
	 * @param roboticonQuantityIncrease The number of roboticons to add to the inventory.
	 */
	public void increaseRoboticonQuantity(RoboticonCustomisation customisation, int roboticonQuantityIncrease){
		if(roboticonQuantityIncrease < 0) // Cannot increase the quantity of roboticons by a negative amount.
			throw new IllegalArgumentException("roboticonQuantityIncrease must be positive");
		else if(roboticons.containsKey(customisation)) // If some roboticons with this customisation are already stored (or have been stored previously).
			roboticons.put(customisation,roboticons.get(customisation)+roboticonQuantityIncrease);
		else // If no roboticons with this customisation are stored.
			roboticons.put(customisation, roboticonQuantityIncrease);
	}
	/**
	 * Removes roboticons of a specified customisation type from the inventory.
	 * <p>
	 * Ensures that the number of roboticons to be removed is not negative.
	 * Ensures that the quantity of roboticons to be removed is not greater than the number stored.
	 * </p>
	 * @param customisation specifies the customisation type that the roboticon to be removed has
	 */
	public void decreaseRoboticonQuantity(RoboticonCustomisation customisation, int roboticonQuantityDecrease){
		if((!roboticons.containsKey(customisation)) || (roboticons.get(customisation) < roboticonQuantityDecrease))// Inventory contains no roboticons with this customisation.
			throw new IllegalArgumentException("The inventory does not contain enough roboticons with customisation "+customisation.name());
		else if(roboticonQuantityDecrease < 0) // Cannot decrease the number of roboticons by a negative amount.
			throw new IllegalArgumentException("roboticonQuantityDecrease must be positive");
		roboticons.put(customisation, roboticons.get(customisation)-roboticonQuantityDecrease);
	}
	/**
	 * Returns the quantity of roboticons stored in the inventory with a given customisation.
	 * @param customisation The customisation type of the roboticons.
	 * @return The quantity of roboticons stored with the specified customisation type.
	 */
	public int getRoboticonQuantity(RoboticonCustomisation customisation){
		if(roboticons.containsKey(customisation)) // If roboticons of this type are stored (or have previously been stored) in the inventory.
			return roboticons.get(customisation);
		return 0;
	}
	
	/**
	 * Returns the quantity of money stored in the inventory.
	 * @return The quantity of money stored in the inventory.
	 */
	public int getMoneyQuantity(){
		return moneyQuantity;
	}
	
	/**
	 * Increases the quantity of money stored in the inventory.
	 * <p>
	 * Checks to ensure that the amount of money to increase the stored amount by is not negative. 
	 * </p>
	 * @param moneyQuantityIncrease The amount of money to add to the inventory.
	 */
	public void increaseMoneyQuantity(int moneyQuantityIncrease){
		if(moneyQuantityIncrease < 0) // Cannot add a negative quantity of money to the inventory.
			throw new IllegalArgumentException("moneyQuantityIncrease must be positive");
		else
			moneyQuantity +=  moneyQuantityIncrease;
	}
	
	/**
	 * Decreases the quantity of money stored in the inventory.
	 * <p>
	 * Checks to ensure that the amount of money to Decrease the stored amount by is not negative.
	 * Checks to ensure that taking this much money from the inventory will not cause the amount stored to be negative. 
	 * </p>
	 * @param moneyQuantityDecrease The amount of money to take from the inventory.
	 */
	public void decreaseMoneyQuantity(int moneyQuantityDecrease){
		if(moneyQuantityDecrease < 0) // Cannot take a negative quantity of money from the inventory.
			throw new IllegalArgumentException("moneyQuantityDecrease must be positive");
		else if (moneyQuantity - moneyQuantityDecrease < 0) // Cannot cause the amount of money stored in the inventory to be negative.
			throw new IllegalArgumentException("moneyQuantityDecrease must not be greater than moneyQuantity");
		else
			moneyQuantity -= moneyQuantityDecrease;
	}
}
