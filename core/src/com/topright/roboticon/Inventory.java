package com.topright.roboticon;
/**
 * Generic inventory class that can keep track of the quantity of ore and energy held.
 * <p>
 * A base class for the MarketInventory and PlayerInventory classes.
 * </p> 
 * @author jcn509
 */
public class Inventory {
	private int oreQuantity;
	private int energyQuantity;
	
	/**
	 * Constructor
	 * @param oreQuantity The initial quantity of ore stored in the inventory.
	 * @param energyQuantity The initial quantity of energy stored in the inventory.
	 */
	public Inventory(int oreQuantity,int energyQuantity){
		initialiseOreQuantity(oreQuantity);
		initialiseEnergyQuantity(energyQuantity);
	}
	/**
	 * Sets the initial quantity of ore stored in the inventory.
	 * <p>
	 * Ensurest that the quantity of ore to be stored is not negative.
	 * </p>
	 * @param ore Quantity The initial quantity of ore to be stored in the inventory.
	 */
	private void initialiseOreQuantity(int oreQuantity){
		if (oreQuantity < 0) // Not allowed to store a negative quantity of ore
			throw new IllegalArgumentException("oreQuantity must be >=0");
		this.oreQuantity = oreQuantity;
	}
	/**
	 * Sets the initial quantity of energy stored in the inventory.
	 * @param energyQuantity The initial quantity of energy to be stored in the inventory
	 */
	private void initialiseEnergyQuantity(int energyQuantity){
		if (energyQuantity < 0) // Not allowed to store a negative quantity of energy
			throw new IllegalArgumentException("energyQuantity must be >=0");
		this.energyQuantity = energyQuantity;
	}
	
	/**
	 * Returns the quantity of ore stored in the inventory.
	 * @return The quantity of ore stored in the inventory.
	 */
	public int getOreQuantity(){
		return oreQuantity;
	}
	/**
	 * Returns the quantity of energy stored in the inventory.
	 * @return The quantity of energy stored in the inventory.
	 */
	public int getEnergyQuantity(){
		return energyQuantity;
	}
	/**
	 * Increases the quantity of energy stored in the inventory by the specified amount.
	 * <p>
	 * Ensures that the amount of extra energy to be stored is not negative.
	 * </p>
	 * @param energyQuantityIncrease The number of units of energy to be added to the inventory.
	 */
	public void increaseEnergyQuantity(int energyQuantityIncrease){
		if(energyQuantityIncrease < 0){ // Cannot add a negative quantity of energy
			throw new IllegalArgumentException("energyQuantityIncrease must be positive");
		}
		energyQuantity += energyQuantityIncrease;
	}
	/**
	 * Decreases the quantity of energy stored in the inventory by the specified amount.
	 * <p>
	 * Ensures that the amount of energy to be taken away is not negative.
	 * </p>
	 * @param energyQuantityDecrease The number of units of energy to be remove from the inventory.
	 */
	public void decreaseEnergyQuantity(int energyQuantityDecrease){
		if(energyQuantityDecrease < 0) // Cannot take away a negative quantity of energy.
			throw new IllegalArgumentException("energyQuantityDecrease must be positive");
		else if(energyQuantity - energyQuantityDecrease < 0) // If taking away this much energy would cause there to be a negative amount of energy stored
			throw new IllegalArgumentException("energyQuantityDecrease must not be greater than MoneyQuantity");
		energyQuantity -= energyQuantityDecrease;
	}
	/**
	 * Increases the quantity of ore stored in the inventory by the specified amount.
	 * <p>
	 * Ensures that the amount of extra ore to be stored is not negative.
	 * </p>t
	 * @param oreQuantityIncrease The number of units of ore to be added to the inventory.
	 */
	public void increaseOreQuantity(int oreQuantityIncrease){
		if(oreQuantityIncrease < 0) // Cannot add a negative quantity of ore.
			throw new IllegalArgumentException("preQuantityIncrease must be positive");
		oreQuantity += oreQuantityIncrease;
	}
	/**
	 * Decreases the quantity of ore stored in the inventory by the specified amount.
	 * <p>
	 * Ensures that the amount of ore to be taken away is not negative.
	 * </p>
	 * @param oreQuantityDecrease The number of units of ore to remove from the inventory.
	 */
	public void decreaseOreQuantity(int oreQuantityDecrease){
		if(oreQuantityDecrease < 0) // Cannot take away a negative amount of ore.
			throw new IllegalArgumentException("oreQuantityDecrease must be positive");
		else if(oreQuantity - oreQuantityDecrease <0) // If taking away this much ore would cause there to be a negative amount of ore stored
			throw new IllegalArgumentException("oreQuantityDecrease must not be greater than oreQuantity");
		oreQuantity -= oreQuantityDecrease;
	}
}


